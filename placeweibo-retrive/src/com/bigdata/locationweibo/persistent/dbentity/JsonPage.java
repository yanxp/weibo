package com.bigdata.locationweibo.persistent.dbentity;

import java.io.Serializable;

public class JsonPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3201382315634416019L;

	private Long latestTime;
	private String jsonPage;
	private String placeUrl;
	private int pageNum;
	
	public Long getLatestTime() {
		return latestTime;
	}
	public void setLatestTime(Long latestTime) {
		this.latestTime = latestTime;
	}
	public String getJsonPage() {
		return jsonPage;
	}
	public void setJsonPage(String jsonPage) {
		this.jsonPage = jsonPage;
	}
	public String getPlaceUrl() {
		return placeUrl;
	}
	public void setPlaceUrl(String placeUrl) {
		this.placeUrl = placeUrl;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

}
