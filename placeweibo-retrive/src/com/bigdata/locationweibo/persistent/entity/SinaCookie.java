package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;
import java.util.Date;

public class SinaCookie implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5749416437101617624L;

	private int version;
	private String name;
	private String value;
	private String domain;
	private String path;
	private Date expiry;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getExpiry() {
		return expiry;
	}
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	
	
}
