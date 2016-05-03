package com.bigdata.locationweibo.services.mutiaccount;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.api.CookieInfo;
import com.bigdata.locationweibo.services.SinaCookieService;

public class CookieStoreService {
	private final static Logger logger = LoggerFactory.getLogger(CookieStoreService.class);
	
	private Map<Object, Object> map;
	
	public CookieStoreService() {
		map = new HashMap<Object, Object>();
	}
	
	public boolean add(String user, CookieStore cookieStore){
		try{
			map.put(user, cookieStore);
		}catch(Exception e){
			logger.info("add cookiestore fail.."+"\n"+ e.toString());
			return false;
		}
		return true;
	}
	
	public boolean remove(String user){
		try{
			map.remove(user);
		}catch(Exception e){
			logger.info("add cookiestore fail.."+"\n"+ e.toString());
			return false;
		}
		return true;
	}
	
	public int getSize(){
		return map.size();
	}
	
	public boolean saveCookieStore(){
		boolean save = false;
		try{
			File file = Tools.createFile(Constans.cookiestoreFileName);
			save = Tools.writeObject(map, file);
		}catch(Exception e){
			logger.info("saveCookieStore fail.."+"\n"+ e.toString());
			return false;
		}
		return save;
	}
	
	public ArrayList<CookieInfo> getCookieInfo(){
		ArrayList<CookieInfo> cookieInfos = new ArrayList<CookieInfo>();
		try{
			if (getCookieStore()) {
				for (Object i : map.keySet()) {
					CookieStore cookieStore = (CookieStore) map.get(i);
					String headerCookie = SinaCookieService.generateHeaderCookie(cookieStore);
					CookieInfo cookieInfo = new CookieInfo();
					cookieInfo.setCookieStore(cookieStore);
					cookieInfo.setHeaderCookie(headerCookie);
					cookieInfos.add(cookieInfo);
				}
			}
		}catch(Exception e){
			logger.info("getCookieInfo fail..."+e.toString());
			return null;
		}
		return cookieInfos;
	}
	
	private boolean getCookieStore(){
		if (Tools.fileExists(Constans.cookiestoreFileName)) {
			try{
				map = Tools.readObject(new File(Constans.cookiestoreFileName));
			}catch(Exception e){
				logger.info("getCookieStore fail..."+e.toString());
				return false;
			}
			return true;
		}
		logger.info("getCookieStore fail..."+"file does not exists");
		return false;
	}
	
	public String wrapCookieFileName(){
		Date date = new Date();
		String dateTime = "1970-01-01";
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
		try{
			dateTime = time.format(date);
		}catch (Exception e){
			logger.info("generate file name fail..."+"\n"+e.toString());
			return null;
		}
		return dateTime;
	}
}
