package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

public class Picture implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5890372797293120473L;

	private String pid;
	private String url;
	private String size;
    private Geo geo;
    
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Geo getGeo() {
		return geo;
	}
	public void setGeo(Geo geo) {
		this.geo = geo;
	}
    
    
}
