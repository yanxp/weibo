package com.bigdata.locationweibo.services;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.persistent.entity.SinaCookie;

public class SinaCookieService {

	private final static Logger logger = LoggerFactory.getLogger(SinaCookieService.class);
	
	public static SinaCookie getSinaCookieFromCookie(Cookie cookie){
		if (cookie == null) {
			logger.info("SinaCookieService:getSinaCookieFromCookie:cookie is null");
			return null;
		}
		SinaCookie sinaCookie = new SinaCookie();
		try{
			sinaCookie.setName(cookie.getName());
			sinaCookie.setValue(cookie.getValue());
			sinaCookie.setExpiry(cookie.getExpiryDate());
			sinaCookie.setVersion(cookie.getVersion());
			sinaCookie.setDomain(cookie.getDomain());
			sinaCookie.setPath(cookie.getPath());
		}catch(Exception e){
			logger.info("SinaCookieService:getSinaCookieFromCookie:set function error"+e.toString());
			return null;
		}
		return sinaCookie;
	}

	/**
	 * 通过cookieStore获取header cookie
	 * @param cookieStore
	 * @return
	 */
	public static String generateHeaderCookie(CookieStore cookieStore){
		if (cookieStore == null) {
			logger.info("SinaCookieService:generateHeaderCookie:cookieStore is null");
			return null;
		}
		List<Cookie> cookies = cookieStore.getCookies();
		String tail = "; ";
		/*
		 * String ipushTs = "IPUSH_TS=1443691698";
		 */
		String tWm = "_T_WM="+"dacdea64fd80c256a08468b65a651d86";
		String sub = "SUB=";
		String subp = "SUBP=";
		String suhb = "SUHB=";
		String ssoLoginState = "SSOLoginState=";
		try{
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.equals("SUB")) {
					sub = sub + cookie.getValue();
				} else if (cookieName.equals("SUBP")) {
					subp = subp + cookie.getValue();
				} else if (cookieName.equals("SUHB")) {
					suhb = suhb+cookie.getValue();
				}else if (cookieName.equals("SUP")) {
					String supBtValue = cookie.getValue();
					supBtValue = supBtValue.substring(supBtValue.indexOf("bt%3D")+5, supBtValue.indexOf("%26et"));
					ssoLoginState = ssoLoginState+supBtValue;
				} 
			}
		}catch(Exception e){
			logger.info("generateHeaderCookie: fail..."+e.toString());
			return null;
		}
		
		String headerCookie = tWm + tail + sub + tail + subp + tail + suhb + tail + ssoLoginState;
		return headerCookie;
	}
	
	/**
	 * 通过对cookie的对象列表进行获取Header Cookie
	 * @param sinaCookies
	 * @return
	 */
	public static String generateHeaderCookie(List<SinaCookie> sinaCookies){
		if (sinaCookies == null) {
			logger.info("SinaCookieService:generateHeaderCookie:sinaCookies is null");
			return null;
		}
		String tail = "; ";
		String tWm = "_T_WM="+"dacdea64fd80c256a08468b65a651d86";
		String sub = "SUB=";
		String subp = "SUBP=";
		String suhb = "SUHB=";
		String ssoLoginState = "SSOLoginState=";
		for (SinaCookie sinaCookie : sinaCookies) {
			String cookieName = sinaCookie.getName();
			if (cookieName.equals("SUB")) {
				sub = sub + sinaCookie.getValue();
			} else if (cookieName.equals("SUBP")) {
				subp = subp + sinaCookie.getValue();
			} else if (cookieName.equals("SUHB")) {
				suhb = suhb+sinaCookie.getValue();
			}else if (cookieName.equals("SUP")) {
				String supBtValue = sinaCookie.getValue();
				supBtValue = supBtValue.substring(supBtValue.indexOf("bt%3D")+5, supBtValue.indexOf("%26et"));
				ssoLoginState = ssoLoginState+supBtValue;
			} 
		}
		String headerCookie = tWm + tail + sub + tail + subp + tail + suhb + tail + ssoLoginState;
		return headerCookie;
	}
	
	public static String generateHeaderCookie(String filePath){
		if (filePath == null) {
			logger.info("SinaCookieService:generateHeaderCookie:filePath is null");
			return null;
		}
		String headerCookie = Tools.readFile(filePath);
		return headerCookie;
	}
	
	public static String getHeaderCookieFromLocal(){
		String cookieFile = Constans.buildConfigDir+wrapCookieFileName()+Constans.cookiesFileName;
		String cookie = null;		
		if (Tools.fileExists(cookieFile)) {
			String cookieTemp = generateHeaderCookie(cookieFile);
			if (cookieTemp != null) {
				cookie = cookieTemp;
			}
		} else{
			Tools.createFile(Constans.buildConfigDir, wrapCookieFileName()+Constans.cookiesFileName);
		}
		return cookie;
	}
	
	public static boolean saveCookie(String cookie, boolean append){
		
		String dateTime = wrapCookieFileName();
		if (dateTime == null || dateTime.equals("")) {
			return false;
		}
		File cookieFile = Tools.createFile(Constans.buildConfigDir, dateTime+Constans.cookiesFileName);
		if (cookieFile != null) {
			try {
				Tools.writeFile(cookieFile, cookie+"\n", append);
			} catch (IOException e) {
				logger.info("saveCookie:writeFile fail...\n"+e.toString());
				return false;
			}
			return true;
		}
		return false;
	}
	
	private static String wrapCookieFileName(){
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
