package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

public class Button implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3125746779155920265L;


	 private String name;
	 private String pic;
	 private String type;
	 private Params params;
	 private Actionlog actionlog;
	 private String scheme;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Params getParams() {
		return params;
	}
	public void setParams(Params params) {
		this.params = params;
	}
	public Actionlog getActionlog() {
		return actionlog;
	}
	public void setActionlog(Actionlog actionlog) {
		this.actionlog = actionlog;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	 
	 
}
