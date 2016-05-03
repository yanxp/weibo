package com.bigdata.locationweibo.services;

import org.apache.http.client.CookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.services.retriveutil.RetriveUtil;

public class TestJsonService {

	private static Logger logger = LoggerFactory.getLogger(TestJsonService.class);
	
	public static boolean isOkToRetrive(){
		String testUrl = "http://m.weibo.cn/page/pageJson?containerid=100101121.165_31.35&luicode=20000174&containerid=100101121.165_31.35&v_p=11&ext=&fid=100101121.165_31.35&uicode=10000011&page=";
		String jsonContent = null;
		try{
			jsonContent = RetriveUtil.getContent(null, SinaCookieService.getHeaderCookieFromLocal(), testUrl, 2).toString();
		}catch(Exception e){
			logger.error("TestJsonService: error happend in function isOkToRetrive..."+e.toString());
			return false;
		}
		if (jsonContent.startsWith("{\"ok\":")) {
			return true;
		}
		return false;
	}
	
	public static boolean isOkToRetrive(CookieStore cookieStore){
		String testUrl = "http://m.weibo.cn/page/pageJson?containerid=100101121.165_31.35&luicode=20000174&containerid=100101121.165_31.35&v_p=11&ext=&fid=100101121.165_31.35&uicode=10000011&page=";
		String jsonContent = null;
		try{
			jsonContent = RetriveUtil.getContent(cookieStore, SinaCookieService.generateHeaderCookie(cookieStore), testUrl, 2).toString();
		}catch(Exception e){
			logger.error("TestJsonService: error happend in function isOkToRetrive..."+e.toString());
			return false;
		}
		if (jsonContent.startsWith("{\"ok\":")) {
			return true;
		}
		return false;
	}
}
