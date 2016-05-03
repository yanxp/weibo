package com.bigdata.locationweibo.services.retriveutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigdata.common.util.Constans;
import com.bigdata.locationweibo.services.CookieConfigurationService;
import com.bigdata.locationweibo.services.SinaCookieService;

public class RetriveUtil {

	private static Logger logger = LoggerFactory.getLogger(RetriveUtil.class);
	
	/**
	 * 实现对内容的抓取
	 * 首先对httpclient进行配置
	 * @param cookieStore
	 * @param purl
	 * @param barNum
	 * @return
	 */
	public static StringBuffer getContent(CookieStore cookieStore, String cookie, String url, int barNum) {

		url = url + Integer.toString(barNum);
/*
 * **********************************************************************************************************************************
 * 整个城市的微博
 * http://m.weibo.cn/p/index?containerid=100101116.555_40.045&ep=Cxsw3BJbN%2C1953952701%2CCxsw3BJbN%2C1953952701
 * 具体位置的微博
 * http://m.weibo.cn/page/pageJson?containerid=100101121.165_31.35&luicode=20000174&containerid=100101121.165_31.35&v_p=11&ext=&fid=100101121.165_31.35&uicode=10000011&page=2
 */
		HttpGet httpGet = new HttpGet(url);
		setHeaders(httpGet, cookie);
		/*
		 * 设置httpclient
		 * (ColseableHttpClient client = HttpClients.createDefault()
		 * 设置的是default的httpclient
		 */
		CloseableHttpClient client = CookieConfigurationService.configeClient(cookieStore);
		
		/*
		 * 获取内容
		 */
		HttpResponse response = null;
		try{
			response = client.execute(httpGet);
		}catch (HttpHostConnectException ee){
			logger.info("getContent:连接超时!!!网络不稳定...稍后重试...");
			return null;
		} catch (ClientProtocolException e) {
			System.err.println("getContent error..\n"+e.toString());
			return null;
		} catch (IOException e) {
			System.err.println("getContent error..\n"+e.toString());
			return null;
		}
		HttpEntity entity = response.getEntity();
		StringBuffer sb = new StringBuffer();
		if(entity == null) {
			logger.info("Error happened in crawler" + httpGet.getURI());
			
			System.out.println("Error happened in crawler" + httpGet.getURI());
			
			return null;
		} else {
			try {
				InputStream dataHtml = entity.getContent();
				BufferedReader htmlBufferReader = new BufferedReader(new InputStreamReader(dataHtml));
				for(String e = htmlBufferReader.readLine(); e != null; e = htmlBufferReader.readLine()) {
					sb.append(e + '\r' + '\n');
				}
			} catch (Exception e) {
				/*
				 * close the get method
				 */
				httpGet.abort();
				return null;
			}
		}

		/*
		 * 速度上StringBuilder > StringBuffer
		 * StringBuilder：线程非安全的
		 * StringBuffer：线程安全的
		 *  1.单线程操作字符串缓冲区 下操作大量数据 => StringBuilder
		 *  2.多线程操作字符串缓冲区 下操作大量数据 => StringBuffer
		 */
		String dataHtml = new String(sb);
		StringBuffer htmlsb = new StringBuffer(dataHtml);
		return htmlsb;
	}
	
	public static Integer getMaxPage(String purl, CookieStore cookieStore){
		int maxPage = 0;
		StringBuffer jsonPage = getContent(cookieStore, SinaCookieService.generateHeaderCookie(cookieStore), purl, 1);
		JSONObject json = null;
		if (jsonPage == null || jsonPage.equals("")) {
			return 0;
		}

		try {
			json = JSONObject.parseObject(new String(jsonPage));
		} catch (Exception ee) {
			logger.info("getMaxPage:json格式出错;"+ee.toString());
			return -1;
		}
		JSONArray bigList = json.getJSONArray("cards");
		json = (JSONObject)bigList.iterator().next();
		try{
			maxPage = json.getInteger("maxPage");
			if (maxPage == 0) {
				maxPage = 1;
			}
		}catch (Exception e){
			logger.info("getMaxPage..."+e.toString());
			return 1;
		}
		return maxPage;
	}
	
	private static void setHeaders(HttpGet httpGet, String cookie){
		
		String userAgent = Constans.userAgent;
		String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
		String acceptEncoding = "gzip,deflate,sdch";
		String acceptLanguage = "zh-CN,zh;q=0.8";
		String cacheControl = "max-age=0";
		String connection = "keep-alive";
		String host = "m.weibo.cn";
		
		httpGet.setHeader("User-Agent", userAgent);
		httpGet.setHeader("Cookie", cookie);
		httpGet.setHeader("Connection", connection);
		httpGet.setHeader("Accept", accept);
		httpGet.setHeader("Host", host);
		httpGet.setHeader("Accept-Encoding", acceptEncoding);
		httpGet.setHeader("Accept-Language", acceptLanguage);
		httpGet.setHeader("Cache-Control", cacheControl);
		httpGet.setHeader("HTTPS", "1");
	}
}
