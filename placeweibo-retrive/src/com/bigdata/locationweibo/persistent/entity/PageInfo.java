package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5915025083629803321L;

	private String page_id;
    private int is_asyn;
    private String page_pic;
    private int type;
    private String page_url;
    private String page_title;
    private String page_desc;
    private String tips;
    private String object_type;
    private String object_id;
    private Set<Button> buttons = new HashSet<>();
    private Actionlog actionlog;
    private String content1;
    private String content2;
     
    public String getContent1() {
		return content1;
	}
	public void setContent1(String content1) {
		this.content1 = content1;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	public Set<Button> getButtons() {
		return buttons;
	}
	public void setButtons(Set<Button> buttons) {
		this.buttons = buttons;
	}
	public Actionlog getActionlog() {
		return actionlog;
	}
	public void setActionlog(Actionlog actionlog) {
		this.actionlog = actionlog;
	}
	public String getPage_id() {
		return page_id;
	}
	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}
	public int getIs_asyn() {
		return is_asyn;
	}
	public void setIs_asyn(int is_asyn) {
		this.is_asyn = is_asyn;
	}
	public String getPage_pic() {
		return page_pic;
	}
	public void setPage_pic(String page_pic) {
		this.page_pic = page_pic;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPage_url() {
		return page_url;
	}
	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
	public String getPage_title() {
		return page_title;
	}
	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}
	public String getPage_desc() {
		return page_desc;
	}
	public void setPage_desc(String page_desc) {
		this.page_desc = page_desc;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getObject_type() {
		return object_type;
	}
	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
       
}
