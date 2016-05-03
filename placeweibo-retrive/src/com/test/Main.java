package com.test;

import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.persistent.entity.Mblog;
import com.bigdata.locationweibo.persistent.entity.User;
import com.bigdata.locationweibo.services.MblogService;
import com.google.gson.Gson;
import com.test.tools.FileUtils;

public class Main {
	
	public static String username = "13660134991@163.com";
	public static String password = "ZHANGjing6898448";
	public static String url = "http://weibo.com/p/100101B2094653D26CA6FB439A";
	
	public static void get_user_json(){

		String userJson = FileUtils.userJson();
		println(userJson);
		
		JSONObject json = JSONObject.parseObject(userJson);
		User user = new User();
		Gson gson = new Gson();
		user = gson.fromJson(json.toString(), User.class);
		String userj = gson.toJson(user);
		System.out.println(userj);
	}
	public void get_mblog_json(){
		String mblogJson = FileUtils.mblogJson();
		
		Mblog mblog = MblogService.getMblogFromJson(mblogJson);
		
		String mblogj = Tools.pojoToJson(mblog);
		mblogj = Jsoup.parse(mblogj).text();
		println(mblogj);
	}

	public static void println(String str){
		System.out.println(str);
	}
	
	public static void main(String[] args) {
	}
		
}
