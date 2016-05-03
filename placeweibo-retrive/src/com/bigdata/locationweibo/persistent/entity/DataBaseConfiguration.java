package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class DataBaseConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7843510020219182965L;

	private String driver;
	private String url;
	@SerializedName(value = "user")
	private String username;
	private String password;
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
