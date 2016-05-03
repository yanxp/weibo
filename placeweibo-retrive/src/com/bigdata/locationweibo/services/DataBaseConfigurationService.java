package com.bigdata.locationweibo.services;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.persistent.entity.DataBaseConfiguration;
import com.google.gson.Gson;

public class DataBaseConfigurationService {

	
	public static DataBaseConfiguration getDataBaseConfiguration() throws Exception{
		DataBaseConfiguration baseConfiguration = new DataBaseConfiguration();
		Gson gson = new Gson();

		String config = Tools.readFile(Constans.configFilePath);
		JSONObject json = JSONObject.parseObject(config);
		baseConfiguration = gson.fromJson(json.toString(), DataBaseConfiguration.class);
		
		return baseConfiguration;
	}
}
