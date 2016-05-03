package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

public class Geo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8384478940101435543L;
	private int width;
	private int height;
	private boolean croped;
	/*
	 * 遇到有byte属性，为关键字，故不作为属性之一
	 */
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isCroped() {
		return croped;
	}
	public void setCroped(boolean croped) {
		this.croped = croped;
	}
	
}
