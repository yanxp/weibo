package com.test.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	public static String userJson(){
		String userJson = "";
		
		String filePath = "userJson.txt";
		userJson = readFile(filePath);
		
		if (userJson == null || "".equals(userJson)) {
			logger.info("user json is nothing.........");
		}
		return userJson;
	}
	
	public static String mblogJson(){
		String mblogJson = "";
		
		String filePath = "mblogJson.txt";
		mblogJson = readFile(filePath);
		
		if (mblogJson == null || "".equals(mblogJson)) {
			logger.info("user json is nothing.........");
		}
		return mblogJson;
	}
	
	public static String readFile(String filePath){
		String fileContent = "";
		File file = new File(filePath);
		BufferedReader reader = null;
		if (!file.exists()) {
			logger.info("-------file is not exists---------");
			return "";
		}
		try {
			reader = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String str = new String();
			while( (str = reader.readLine()) != null){
				sb.append(str);
			}
			fileContent = sb.toString();
		} catch (FileNotFoundException e) {
			logger.error("file not found..............");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("io exception..............");
			e.printStackTrace();
		}finally{
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return fileContent;
	}
}
