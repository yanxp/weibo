package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Mblog implements Serializable{

	/**
	 * mblog  整条微博的基本信息
	 */
	private static final long serialVersionUID = -7392184138821586488L;

	//发表的时间  一般是形式如：2分钟以前
	private String created_at = null;
    private Long id;
    private String mid = null;
    private String idstr = null;
    //微博的内容
    private String text = null;
    private int source_type;
    //发布微博的设备源
    private String source = null;
    private boolean favorited;
    //发布微博包含的图片url
    private Set<String> pic_ids = new HashSet<>();
    private String thumbnail_pic = null;
    private String bmiddle_pic = null;
    private String original_pic = null;
    private User user;
    //转发量
    private int reposts_count;
    //评论人数
    private int comments_count;
    //点赞用户数
    private int attitudes_count;
    private Set<Integer> biz_ids = new HashSet<>();
    private Long biz_feature;
    private String rid = null;
    private int userType;
    private Set<UrlStruct> url_struct = new HashSet<UrlStruct>();
    private PageInfo page_info;
    private Long created_timestamp;
    private String bid = null;
    private Set<Picture> pics = new HashSet<>();
	private int like_count;
    private int attitudes_status;
    

    public String getThumbnail_pic() {
		return thumbnail_pic;
	}
	public void setThumbnail_pic(String thumbnail_pic) {
		this.thumbnail_pic = thumbnail_pic;
	}
	public String getBmiddle_pic() {
		return bmiddle_pic;
	}
	public void setBmiddle_pic(String bmiddle_pic) {
		this.bmiddle_pic = bmiddle_pic;
	}
	public String getOriginal_pic() {
		return original_pic;
	}
	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}
	public Set<Picture> getPics() {
		return pics;
	}
	public void setPics(Set<Picture> pics) {
		this.pics = pics;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getIdstr() {
		return idstr;
	}
	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public int getSource_type() {
		return source_type;
	}
	public void setSource_type(int source_type) {
		this.source_type = source_type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isFavorited() {
		return favorited;
	}
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}
	public Set<String> getPic_ids() {
		return pic_ids;
	}
	public void setPic_ids(Set<String> pic_ids) {
		this.pic_ids = pic_ids;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getReposts_count() {
		return reposts_count;
	}
	public void setReposts_count(int reposts_count) {
		this.reposts_count = reposts_count;
	}
	public int getComments_count() {
		return comments_count;
	}
	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}
	public int getAttitudes_count() {
		return attitudes_count;
	}
	public void setAttitudes_count(int attitudes_count) {
		this.attitudes_count = attitudes_count;
	}
	public Set<Integer> getBiz_ids() {
		return biz_ids;
	}
	public void setBiz_ids(Set<Integer> biz_ids) {
		this.biz_ids = biz_ids;
	}
	public Long getBiz_feature() {
		return biz_feature;
	}
	public void setBiz_feature(Long biz_feature) {
		this.biz_feature = biz_feature;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public Set<UrlStruct> getUrl_struct() {
		return url_struct;
	}
	public void setUrl_struct(Set<UrlStruct> url_struct) {
		this.url_struct = url_struct;
	}
	public PageInfo getPage_info() {
		return page_info;
	}
	public void setPage_info(PageInfo page_info) {
		this.page_info = page_info;
	}
	public Long getCreated_timestamp() {
		return created_timestamp;
	}
	public void setCreated_timestamp(Long created_timestamp) {
		this.created_timestamp = created_timestamp;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getAttitudes_status() {
		return attitudes_status;
	}
	public void setAttitudes_status(int attitudes_status) {
		this.attitudes_status = attitudes_status;
	}

}
