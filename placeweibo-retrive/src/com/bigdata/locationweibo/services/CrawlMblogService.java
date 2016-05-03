package com.bigdata.locationweibo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.api.CookieInfo;
import com.bigdata.locationweibo.enumapi.CrawlerStatus;
import com.bigdata.locationweibo.enumapi.SaveMblogStatus;
import com.bigdata.locationweibo.enumapi.SaveStatus;
import com.bigdata.locationweibo.persistent.dao.MblogJsonDAO;
import com.bigdata.locationweibo.persistent.dbentity.MblogJson;
import com.bigdata.locationweibo.persistent.entity.Mblog;
import com.bigdata.locationweibo.services.retriveutil.RetriveUtil;

public class CrawlMblogService {
	
	private static Logger logger = LoggerFactory.getLogger(CrawlMblogService.class);
	
	private ArrayList<CookieInfo> cookies;
	private int index;
	
	public CrawlMblogService() {
		this.index = 0;
	}

	public CrawlMblogService(ArrayList<CookieInfo> cookies) {
		this.cookies = cookies;	
		this.index = 0;
	}
	
	public ArrayList<CookieInfo> getCookies() {
		return cookies;
	}

	public void setCookies(ArrayList<CookieInfo> cookies) {
		this.cookies = cookies;
	}
	
	private CookieInfo getCookieInfo(){
		return cookies.get(index%cookies.size());
	}

	public CrawlerStatus crawler(String placeUrl) throws Exception {
		int page = 2;
		int maxPage = 125;
		String url = Tools.wrapUrl(placeUrl);
		System.out.print("placeUrl: "+placeUrl);
		if (!placeUrl.contains("_")) {
			maxPage = 0;
			boolean loop = true;
			while (loop){
				maxPage = RetriveUtil.getMaxPage(url, getCookieInfo().getCookieStore());
				if (maxPage > 0) {
					loop = false;
				} else if (maxPage == -1 && cookies.size() > 0) {
					loop = true;
					cookies.remove(index%cookies.size());
				} else if (maxPage == 0) {
					loop = true;
				} else if (maxPage == 1) {
					return CrawlerStatus.DONE;
				} else {
					return CrawlerStatus.ERROR;
				}
			}
		}
		MblogJsonDAO mblogJsonDAO = new MblogJsonDAO();
		Long timestamp = mblogJsonDAO.getLatestTimeByPlaceUrl(placeUrl);

		int retryCount = 0 ;
		boolean flag = true;
		System.out.println("  maxPage:"+maxPage);
		while( flag && page < maxPage+1 && retryCount < 5) {
			System.out.print("page_"+page+" 、 ");
			if (page % 10 ==0) {
				System.out.println();
				index++;
			}
			if (cookies.size() == 0) {
				flag = false;
				logger.info("cookie信息已经用完,全部被狗日的新浪暂封了...");
				return CrawlerStatus.ERROR;
			}
			CookieInfo cookieInfo = getCookieInfo();
			StringBuffer buffer = RetriveUtil.getContent(cookieInfo.getCookieStore(), cookieInfo.getHeaderCookie(), url, page);
			if (buffer != null) {
				SaveMblogStatus status = saveMblogJsonPage(buffer, placeUrl, timestamp, mblogJsonDAO, page, maxPage);
				if (status == SaveMblogStatus.FAIL) {
					flag = false;
				} else if(status == SaveMblogStatus.SUCCESS){
					flag = true;
					page ++;
					retryCount = 0;
				} else if (status == SaveMblogStatus.TRAP) {
					retryCount++;
					flag = true;
				} else if (status == SaveMblogStatus.ABORT) {
					if (cookies.size() > 0) {
						flag = true;
						cookies.remove(index%cookies.size());
					} else {
						flag = false;
						return CrawlerStatus.ERROR;
					}
				}
			} else{
				logger.info("获取的JSON页面内容为空，有可能是网络原因,请稍候...."+"第"+retryCount+"次重抓");
				retryCount++;
				Thread.sleep(3000);
			}
		}
		index++; 
		System.out.println("成功抓取完毕,一共抓了:"+(page-1)+"页");
		Tools.printStarLine(50);
		mblogJsonDAO.closeConnection();
		
		try {
			int e = (int)(Math.random() * 2.0D) + 2;
			Thread.sleep((long)e * 1000L);
		} catch (InterruptedException ee) {
			ee.printStackTrace();
		}
		return CrawlerStatus.DONE;
	}
	
	/**
	 * 对html文件进行解析，获取内容放进weiboId中并打印
	 * @param jsonPage
	 * @param bw
	 * @param placeUrl
	 * @return
	 * @throws IOException
	 */
	private  SaveMblogStatus saveMblogJsonPage(StringBuffer jsonPage, String placeUrl, Long timestamp, 
			MblogJsonDAO mblogJsonDAO, int count,int maxPage) {
		
		JSONObject json;
		try {
			json = JSONObject.parseObject(new String(jsonPage));
		} catch (Exception ee) {
			String bigList = new String(jsonPage);
			logger.info("alyseJsonPage:json格式出错,新浪为了反爬虫,实行跳转页面不让抓取,请改时间开启抓取程序\n"+ee.toString());
			if(bigList.lastIndexOf("Time out was reached") == -1 && count <= maxPage) {
				return SaveMblogStatus.ABORT;
			}
			return SaveMblogStatus.SUCCESS;
		}

		try {
			int weiboCount = 0;
			try{
				weiboCount = json.getInteger("count").intValue();
			}catch (NullPointerException exception){
				logger.info("掉进了微博切换页面的陷阱,请稍后......");
				Thread.sleep(3000);
				return SaveMblogStatus.TRAP;
			}
			if(weiboCount == 0) {
				if (count <= maxPage) {
					logger.info("微博切换页面意外返回了count=0,请稍后...");
					Thread.sleep(2000);
					return SaveMblogStatus.TRAP;
				} else {
					System.out.println("the \"count\" is == 0 ,retrive nothing!");
					return SaveMblogStatus.FAIL;
				}
			}

			JSONArray bigList = json.getJSONArray("cards");
			json = (JSONObject)bigList.iterator().next();
			bigList = json.getJSONArray("card_group");
			@SuppressWarnings("rawtypes")
			Iterator iterator = bigList.iterator();

			while(iterator.hasNext()) {
				try {
					JSONObject cardGroupCard = (JSONObject)iterator.next();

					String mblogContent = cardGroupCard.getJSONObject("mblog").toJSONString();
					Mblog mblog = MblogService.getMblogFromJson(mblogContent);
					Long createTime = mblog.getCreated_timestamp();
				
					/*
					 * String weiboUrl = "http://m.weibo.cn/1641537045/"+mblog.getMid()+"?";
					 */
					if (createTime <= timestamp || createTime <= 1388505600L) {
						return SaveMblogStatus.FAIL;
					}
					else{
						MblogJson mblogJson = new MblogJson();
						mblogJson.setCreateTime(createTime);
						mblogJson.setMblogJsonText(Tools.pojoToJson(mblog));
						mblogJson.setMid(mblog.getMid());
						mblogJson.setPlace_url(placeUrl);

						SaveStatus status = mblogJsonDAO.save(mblogJson);
						
						if (status == SaveStatus.IS_NULL || status == SaveStatus.IS_FAILED) {
							return SaveMblogStatus.FAIL;
						}

						/*
						 * WeiboContent weiboContent = WeiboContentService.getWeiboContent(mblog);
						if(weiboContent != null){
							new WeiboContentDAO().save(weiboContent);
						}
						 */
					}
				} catch (Exception ee) {
					logger.error("error happended in CrawlerService when analyse Json,the error is: \n"+ee.toString());
					if (count < maxPage) {
						return SaveMblogStatus.TRAP;
					}
					return SaveMblogStatus.FAIL;
				}
			}
		} catch (Exception ee) {
			logger.error("error happended in CrawlerService,the error is:"+ee.toString());
			if (count < maxPage) {
				return SaveMblogStatus.TRAP;
			}
			return SaveMblogStatus.FAIL;
		}
		return SaveMblogStatus.SUCCESS;
	}
	
}