package com.bigdata.locationweibo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.persistent.entity.Mblog;
import com.google.gson.Gson;

public class MblogService {

	private static Logger logger = LoggerFactory.getLogger(MblogService.class);
	
	public static Mblog getMblogFromJson(String mblogJson){
		Mblog mblog = new Mblog();
		Gson gson = new Gson();
		try{
			mblog = gson.fromJson(mblogJson, Mblog.class);
		}catch (Exception e){
			e.printStackTrace();
			logger.info("------the param mblogJson you put is wrong-----");
			return null;
		}
		
		return mblog;
	}
}
