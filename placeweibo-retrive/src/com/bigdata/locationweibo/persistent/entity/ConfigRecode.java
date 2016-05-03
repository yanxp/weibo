package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;
import java.util.Date;

public class ConfigRecode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8033872989591920488L;

	private String savePath;
	
	private Date date;

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
