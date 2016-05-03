package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

public class Actionlog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7549980517791778550L;

	private String oid;
    private int act_code;
    private int act_type;
    private String ext;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public int getAct_code() {
		return act_code;
	}
	public void setAct_code(int act_code) {
		this.act_code = act_code;
	}
	public int getAct_type() {
		return act_type;
	}
	public void setAct_type(int act_type) {
		this.act_type = act_type;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
    
    
}
