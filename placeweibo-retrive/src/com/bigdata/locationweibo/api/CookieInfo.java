package com.bigdata.locationweibo.api;

import org.apache.http.client.CookieStore;

import com.bigdata.locationweibo.services.SinaCookieService;

public class CookieInfo {

	private String headerCookie;
	private CookieStore cookieStore;
	public String getHeaderCookie() {
		return headerCookie;
	}
	public void setHeaderCookie(String headerCookie) {
		this.headerCookie = headerCookie;
	}
	public CookieStore getCookieStore() {
		return cookieStore;
	}
	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public void wrapCookie(){
		this.setHeaderCookie(SinaCookieService.generateHeaderCookie(cookieStore));
	}
}
