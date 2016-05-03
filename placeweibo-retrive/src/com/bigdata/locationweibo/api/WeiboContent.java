package com.bigdata.locationweibo.api;

import java.util.Date;

import com.bigdata.common.util.Tools;


public class WeiboContent {

	private String placeUrl;
	private Long createTime;
	private String userNickname;
	private String userHomeUrl;
	private String createSource;
	private String postText;
	private String weiboUrl;
	private int pictureNum;
	private boolean isVerified;
	private String verifiedReason;
	private String gender;
	private String description;
	
	public WeiboContent() {}

	
	public String getPlaceUrl() {
		return placeUrl;
	}


	public void setPlaceUrl(String placeUrl) {
		this.placeUrl = placeUrl;
	}


	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserHomeUrl() {
		return userHomeUrl;
	}

	public void setUserHomeUrl(String userHomeUrl) {
		this.userHomeUrl = userHomeUrl;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getWeiboUrl() {
		return weiboUrl;
	}

	public void setWeiboUrl(String weiboUrl) {
		this.weiboUrl = weiboUrl;
	}

	public int getPictureNum() {
		return pictureNum;
	}

	public void setPictureNum(int pictureNum) {
		this.pictureNum = pictureNum;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerifiedReason() {
		return verifiedReason;
	}

	public void setVerifiedReason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String sex) {
		this.gender = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		String str = "";
		str = (new StringBuilder())
			.append(placeUrl).append(",")
			.append(Tools.wrapTime(new Date(createTime*1000))).append(",")
			.append(userNickname).append(",")
			.append(userHomeUrl).append(",")
			.append(createSource).append(",")
			.append(postText.replaceAll(",", " ")).append(",")
			.append(weiboUrl).append(",")
			.append(pictureNum).append(",")
			.append(isVerified).append(",")
			.append(verifiedReason).append(",")
			.append(gender).append(",")
			.append(description)
			.toString();
		return str.replaceAll("\r|\n", " ");
	}
}
