package com.bigdata.locationweibo.persistent.entity;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 微博jsonPage里面的user对象
	 */
	private static final long serialVersionUID = 2464437406585547620L;
	
	private Long id;
	private int statuses_count;
	private int mbtype;
	private int ismember;
	/*
	 * 有中文有数字的粉丝数  fuck！
	 * //粉丝数目
	 *  private int fansNum;
	 */
    
    //是否认证微博
    private boolean verified;
    //---------------------------follow_me（bolean）属性忽略---------------------
    private boolean follow_me;
    //---------------------------following（boolean)属性忽略---------------------
    private boolean following;
    
    //用户名
	private String screen_name;
	//头像图片路径
	private String profile_image_url;
	//微博用户的url：如http://m.weibo.cn/u/2259325480
    private String profile_url;
    private String verified_reason;
    //签名
    private String description;
    private String remark;
    //性别
    private String gender;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	public int getMbtype() {
		return mbtype;
	}

	public void setMbtype(int mbtype) {
		this.mbtype = mbtype;
	}

	public int getIsmember() {
		return ismember;
	}

	public void setIsmember(int ismember) {
		this.ismember = ismember;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isFollow_me() {
		return follow_me;
	}

	public void setFollow_me(boolean follow_me) {
		this.follow_me = follow_me;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

	public String getVerified_reason() {
		return verified_reason;
	}

	public void setVerified_reason(String verified_reason) {
		this.verified_reason = verified_reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
