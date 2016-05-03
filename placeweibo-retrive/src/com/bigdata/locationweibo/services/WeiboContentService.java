package com.bigdata.locationweibo.services;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.api.WeiboContent;
import com.bigdata.locationweibo.persistent.dao.MblogJsonDAO;
import com.bigdata.locationweibo.persistent.dbentity.MblogJson;
import com.bigdata.locationweibo.persistent.entity.Mblog;
import com.google.gson.Gson;
import com.opencsv.CSVReader;

public class WeiboContentService {
	private static Logger logger = LoggerFactory.getLogger(WeiboContentService.class);
	

	public static WeiboContent getWeiboContent (Mblog mblog){
		WeiboContent weibo = new WeiboContent();
		
		if (mblog ==null) {
			logger.info("getWeiboContent-----your mblog is null--------");
			
			return null;
		}
		//微博内容
		String text = Jsoup.parse(mblog.getText()).text();
		
//		if (mblog.getPage_info() == null) {
//			weibo.setPlaceUrl("NOT FOUND");
//		} else {
//			weibo.setPlaceUrl(mblog.getPage_info().getPage_url());
//		}
		weibo.setCreateSource(mblog.getSource());
		weibo.setCreateTime(mblog.getCreated_timestamp());
		weibo.setDescription(mblog.getUser().getDescription());
		weibo.setGender(mblog.getUser().getGender());
		weibo.setPictureNum(mblog.getPic_ids().size());
		weibo.setPostText(text);
		weibo.setUserHomeUrl("http://m.weibo.cn/"+mblog.getUser().getProfile_url());
		weibo.setUserNickname(mblog.getUser().getScreen_name());
		weibo.setVerified(mblog.getUser().isVerified());
		weibo.setVerifiedReason(mblog.getUser().getVerified_reason());
		weibo.setWeiboUrl("http://m.weibo.cn/"+mblog.getUser().getId()+"/"+mblog.getId()+"?");
		
		return weibo;
	}
	
	public static boolean getWeiboContentAndSave(String dir, String saveFile){
		 MblogJsonDAO mblogJsonDAO = null;
		 CSVWriterPrepareService csvWriterService = null;
		try{
			mblogJsonDAO = new MblogJsonDAO();
			String titles = "序号,地点,发送时间,用户昵称,用户主页,客户端,微博正文,微博链接,图片数,是否认证,认证原因,性别,个人简介";
			csvWriterService = new CSVWriterPrepareService(dir, saveFile, titles);
			int maxId = mblogJsonDAO.getMaxId();
			int j = 0;
			for (int i = 1; i < maxId+1; i+=5000) {
				int beginId = i;
				int endId = i+5000;
				List<WeiboContent> contents = getWeiboContentByIdRange(beginId, endId, mblogJsonDAO);
				if (contents != null) {
	            	try{
	                	for (WeiboContent weiboContent : contents) {
	        				j++;
	        				if (!csvWriterService.writeCSV(j+","+weiboContent.toString())) {
	        					j--;
	        				}  
	        			}
	                	System.out.print("完成的范围+"+beginId+"--"+endId+" ");
	                	if (i % 20000 == 0) {
							System.out.println();
						}
	            	}catch (Exception e){
	            		logger.info("getWeiboContent-----\n"+e.toString());
	            		return false;
	            	} 
	    		}  else{
	    			return false;
	    		}
			}
		}catch (Exception e){
			logger.info("getWeiboContent has error...\n"+e.toString());
			return false;
		} finally{
			if (csvWriterService != null) {
				csvWriterService.closeWriter();
			}
			if (mblogJsonDAO != null) {
				mblogJsonDAO.closeConnection();
		}
		}
		return true;
	}
	public static boolean getWeiboContentAndSave(String dir, String pathload, String saveFile) throws Exception{
		ArrayList<String> urls = new ArrayList<String>();
        try{
        	@SuppressWarnings("resource")
    		CSVReader reader = new CSVReader(new FileReader(dir+pathload));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                urls.add(nextLine[0]);
            }
            }catch (Exception e){
        	logger.info("getWeiboContent-----\n"+e.toString());
        	return false;
        }        
        String titles = "序号,地点,发送时间,用户昵称,用户主页,客户端,微博正文,微博链接,图片数,是否认证,认证原因,性别,个人简介";
        CSVWriterPrepareService csvWriterService = new CSVWriterPrepareService(dir, saveFile, titles);
        MblogJsonDAO mblogJsonDAO = new MblogJsonDAO();
        int i = 0;
        
        for (String placeUrl : urls) {
        	List<WeiboContent> contents = getWeiboContentsByPlaceUrl(placeUrl, mblogJsonDAO);
            if (contents != null) {
            	try{
                	for (WeiboContent weiboContent : contents) {
        				i++;
        				if (!csvWriterService.writeCSV(i+","+weiboContent.toString())) {
        					i--;
        				}  
        			}
                	System.out.println("完成的地理位置:"+placeUrl+"...");
            	}catch (Exception e){
            		logger.info("getWeiboContent-----\n"+e.toString());
            		System.out.println("getWeiboContent-----\n"+e.toString());
            		return false;
            	} 
    		}  else{
    			return false;
    		}
		}
        System.out.println("全部完成,一共"+i+"条记录...");
        if (csvWriterService != null) {
			csvWriterService.closeWriter();
		}
        if (mblogJsonDAO != null) {
			mblogJsonDAO.closeConnection();
		}
        return true;
	}
	
	private static List<WeiboContent> getWeiboContentsByPlaceUrl(String placeUrl, MblogJsonDAO mblogJsonDAO){
		List<WeiboContent> contents = new ArrayList<WeiboContent>();
		
		List<MblogJson> jsons = new ArrayList<MblogJson>();
		try{
			jsons = mblogJsonDAO.findByPlaceUrl(placeUrl);
		}catch(Exception e){
			logger.info("getWeiboContentsByPlaceUrl:\n"+e.toString());
			return null;
		}
		Gson gson = new Gson();
		for (MblogJson json : jsons) {
			Mblog mblog = gson.fromJson(json.getMblogJsonText(), Mblog.class);
			WeiboContent content = WeiboContentService.getWeiboContent(mblog);
			content.setPlaceUrl(placeUrl);
			if (content != null) {
				contents.add(content);
			}	
		}
		return contents;
	}
	
	private static List<WeiboContent> getWeiboContentByIdRange(int beginId, int endId, MblogJsonDAO mblogJsonDAO){
		List<WeiboContent> contents = new ArrayList<WeiboContent>();
		
		List<MblogJson> jsons = new ArrayList<MblogJson>();
		try{
			jsons = mblogJsonDAO.findByIdRange(beginId, endId);
		}catch(Exception e){
			logger.info("getWeiboContentByIdRange-----\n"+e.toString());
			return null;
		}
		Gson gson = new Gson();
		for (MblogJson json : jsons) {
			Mblog mblog = gson.fromJson(json.getMblogJsonText(), Mblog.class);
			WeiboContent content = WeiboContentService.getWeiboContent(mblog);
			content.setPlaceUrl(json.getPlace_url());
			if (content != null) {
				contents.add(content);
			}	
		}
		return contents;
	}
}
