package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

public class UrlStruct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5137985233729730867L;

	private String url_title;
    private String ori_url;
    private String page_id;
    private String url_type_pic;
    private String short_url;
    /*
     * private int url_type;
     * 会报错   因为json格式中尽然有的urltype是int型  有的urltype是String型....
     */
    private boolean result;
    private int hide;
	public String getUrl_title() {
		return url_title;
	}
	public void setUrl_title(String url_title) {
		this.url_title = url_title;
	}
	public String getOri_url() {
		return ori_url;
	}
	public void setOri_url(String ori_url) {
		this.ori_url = ori_url;
	}
	public String getPage_id() {
		return page_id;
	}
	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}
	public String getUrl_type_pic() {
		return url_type_pic;
	}
	public void setUrl_type_pic(String url_type_pic) {
		this.url_type_pic = url_type_pic;
	}
	public String getShort_url() {
		return short_url;
	}
	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getHide() {
		return hide;
	}
	public void setHide(int hide) {
		this.hide = hide;
	}
    
    
}
