package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

public class CardGroupCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 851680525660294242L;

	private int card_type;
	private String card_type_name;
	private String card_id;
	private String title;
	//个人的地理具体微博Url
	private String scheme;
	private int display_arrow;
	private int is_asyn;
	private String weibo_need;
	//one to one
	private Mblog mblog;
	private int hidebtns;
	private String itemid;
	private int show_type;
	private String openurl;
	
	
	public int getCard_type() {
		return card_type;
	}
	public void setCard_type(int card_type) {
		this.card_type = card_type;
	}
	public String getCard_type_name() {
		return card_type_name;
	}
	public void setCard_type_name(String card_type_name) {
		this.card_type_name = card_type_name;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public int getDisplay_arrow() {
		return display_arrow;
	}
	public void setDisplay_arrow(int display_arrow) {
		this.display_arrow = display_arrow;
	}
	public int getIs_asyn() {
		return is_asyn;
	}
	public void setIs_asyn(int is_asyn) {
		this.is_asyn = is_asyn;
	}
	public String getWeibo_need() {
		return weibo_need;
	}
	public void setWeibo_need(String weibo_need) {
		this.weibo_need = weibo_need;
	}
	public Mblog getMblog() {
		return mblog;
	}
	public void setMblog(Mblog mblog) {
		this.mblog = mblog;
	}
	public int getHidebtns() {
		return hidebtns;
	}
	public void setHidebtns(int hidebtns) {
		this.hidebtns = hidebtns;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getShow_type() {
		return show_type;
	}
	public void setShow_type(int show_type) {
		this.show_type = show_type;
	}
	public String getOpenurl() {
		return openurl;
	}
	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}

}
