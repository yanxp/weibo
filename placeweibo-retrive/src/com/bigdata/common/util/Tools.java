package com.bigdata.common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.locationweibo.persistent.entity.ConfigRecode;
import com.bigdata.locationweibo.persistent.entity.DataBaseConfiguration;
import com.google.gson.Gson;

public class Tools {
	private static Logger logger = LoggerFactory.getLogger(Tools.class);
	
	/**
	 * @param s
	 * 对空白进行过滤
	 */
	public static String filterBlankCharacters(String s){
		if (s == null || s.equals("")) {
			return s;
		}
		
		String goalStr = "";
		for (int i = 0; i < s.length(); i++) {
			if (33 <= s.charAt(i) && s.charAt(i) <= 127) {
				goalStr += s.charAt(i);
			}
		}
		
		return goalStr;
	}
	
	/**
	 * 获得一个实体的json格式的String
	 */
	public static String getEntityJsonStr (String wholeJson, String pojoName){
		String entityStr = "";
		
		if (wholeJson == null || "".equals(wholeJson)) {
			logger.info("----------the json father is null-----------");
			
			return null;
		}
		if (pojoName == null || "".equals(pojoName)) {
			logger.info("----------the pojoName is null-----------");
			
			return null;
		}
		
		JSONObject jsonObject = null;
		JSONObject json = null;
		
		try{
			jsonObject = JSONObject.parseObject(wholeJson);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("------------your wholejson is not allow-------------");
			return null;
		}
		try{
			json = jsonObject.getJSONObject(pojoName);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("------something happend in pojoName------your pojoName is not allow-------------");
			return null;
		}
		
		entityStr = json.toString();
		return entityStr;
	}
	
	/**
	 * pojoToJson  将实体转变为json格式
	 */
	public static String pojoToJson (Object object){
		String json = "";
		
		if (object == null) {
			logger.info("--------your object is null-----------");
			
			return null;
		}
		Gson gson = new Gson();
		json = gson.toJson(object);
		return json;
	}
	
	/**
	 * json转化为对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static Object jsonToPojo(String jsonStr, Class<?> clazz){
		Gson gson = new Gson();
		JSONObject json = JSONObject.parseObject(jsonStr);
		Object object = new Object();
		object = gson.fromJson(json.toString(), clazz);
		return object;
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static File createFile(String fileName){
        /*
         * 创建文件
         */
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            	logger.info("createFile:"+e.toString());
            	return null;
            }
        }
        return file;
	}
	/**
	 * @param dir
	 * @param fileName
	 * @param suffix
	 * @return
	 */
	public static File createFile(String dir, String fileName){
		/*
		 * 创建目录
		 */
		File dirFile = new File(dir);
        if (!dirFile.exists() && !dirFile.isDirectory()) {
            logger.info("】(ㄒoㄒ)【  不存在,正在创建....");
            try{
            	dirFile.mkdir();
            }catch (Exception e){
            	logger.info("文件创建失败");
                return null;
            }
            logger.info("O(∩_∩)O创建成功");
        } 
        /*
         * 创建文件
         */
        File file = new File(dir+fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            	logger.info("createFile"+e.toString());
            	return null;
            }
        }
        return file;
	}
	/**
	 * 读取文件内容
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath){
		String fileContent = "";
		File file = new File(filePath);
		BufferedReader reader = null;
		if (!file.exists()) {
			logger.info("readFile:the file:"+filePath+" is not exists");
			return null;
		}
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
			reader=new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
			fileContent += line;
			} 
			read.close();
		} catch (FileNotFoundException e) {
			logger.error("Tools:readFile:file not found.............."+e.toString());
			return null;
		} catch (IOException e) {
			logger.error("Tools:readFile:io exception.............."+e.toString());
			return null;
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

	/**
	 * 打印一定长度的*号
	 * @param len
	 */
	public static void printStarLine(int len){
		for(int i = 0;i<len;i++){
			System.out.print("*");
		}
		System.out.println("\n");
	}
	/**
	 * 创建配置文件
	 * @param buildConfigDir
	 * @param baseConfiguration
	 * @return
	 */
	public static boolean createConfFile(String buildConfigDir, DataBaseConfiguration baseConfiguration){
		 File pathFile = new File(Constans.pathFilePath);
		 if (!pathFile.exists()) {
	            try {
	                pathFile.createNewFile();
	            } catch (IOException e) {
	            	logger.info("--Tools----createConfFile----------"+e.toString());
	            	return false;
	            }
	     } 
		 try {
			 ConfigRecode configRecode = new ConfigRecode();
			 configRecode.setSavePath(buildConfigDir+Constans.buildConfigFileName);
			 configRecode.setDate(new Date());
			 writeFile(pathFile, pojoToJson(configRecode), false);
	     } catch (IOException e) {
	    	 logger.info("--Tools----createConfFile----writeFile error------"+e.toString());
	    	 return false;
		}
		/*
		 * 创建目录
		 */
		File dirfile = new File(buildConfigDir);
        if (!dirfile.exists() && !dirfile.isDirectory()) {  
            dirfile.mkdir();
        }
        /*
         * 创建文件
         */
        File file = new File(buildConfigDir+Constans.buildConfigFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            	logger.info("--Tools----createConfFile----------"+e.toString());
            	return false;
            }
        } 
        if (baseConfiguration != null) {
        	try {
				writeFile(file, pojoToJson(baseConfiguration), false);
			} catch (IOException e) {
				logger.info("--Tools----createConfFile----writeFile error------"+e.toString());
				return false;
			}
		} else{
			return false;
		}
        return true;
	}
	/**
	 * @param file
	 * @param content
	 * @param appendok
	 * @throws IOException
	 */
	public static void writeFile(File file, String content, boolean appendok) throws IOException{
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file, appendok),"UTF-8");
		BufferedWriter writer=new BufferedWriter(write); 
		writer.write(content);
		writer.close();
	}
	/**
	 * @param path
	 * @return
	 */
	public static boolean fileExists(String path){
		 File pathFile = new File(path);
		 if (pathFile.exists()) {
			 return true;
	     } 
		 else{
			 return false;
		 }
	}
	/**
	 * @param file
	 * @return
	 */
	public static boolean fileExists(File file){
		 if (file.exists()) {
			 return true;
	     } 
		 else{
			 return false;
		 }
	}
	/**
	 * 检查网络是否可用
	 * @return
	 */
	public synchronized static boolean networkUsable() {
		String urlStr = "https://www.baidu.com/";
		int state = -1;
		HttpURLConnection connection = null;
		try{
			URL url = new URL(urlStr);
			connection = (HttpURLConnection)url.openConnection();
			state = connection.getResponseCode();
		}catch (Exception e){
			logger.error("网络不可用.....");
			return false;
		} finally{
			if (connection != null) {
				connection.disconnect();
			}
		}
		if(state == HttpStatus.SC_OK){
			return true;
		} else{
			logger.error("尝试访问搜狗首页失败,网络异常");
			return false;
		}
	}
	/**
	 * 
	 * @param purl
	 * @return
	 */
	public static String wrapUrl(String purl){
		String placeId = purl.replace("http://weibo.com/p/", "");
		String url = null;
		if (placeId.contains("_")) {
			url = "http://m.weibo.cn/page/pageJson?containerid=1001018008611000000000000&containerid=";
			url = url + placeId;
			url = url + "&ep=Cxsw3BJbN%2C1953952701%2CCxsw3BJbN%2C1953952701&v_p=11&ext=&fid=";
			url = url + placeId;
			url = url + "&uicode=10000011&page=";
		} else{
			url = "http://m.weibo.cn/page/json?containerid=";
			url = url +placeId;
			url = url +"_-_poistatuses&page=";
			
//			url = "http://m.weibo.cn/page/pageJson?containerid=";
//			url = url +placeId;
//			url = url +"_-_loading_poi_weibo&page=";
			
//			url = "http://m.weibo.cn/page/pageJson?containerid=";
//			url += placeId;
//			url += "&containerid="+placeId;
//			url += "&v_p=11&ext=&fid="+placeId;
//			url += "&uicode=10000011&page=";
		}
		return url;
	}
	
	public static String wrapTime(Date date){
		String dateTime = "1970-01-01 00:00:00";
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try{
			dateTime = time.format(date);
		}catch (Exception e){
			System.err.println(e.toString());
			return null;
		}
		return dateTime;
	}
	

	/**
	 * @param map
	 * @param file
	 */
	public static boolean writeObject(Map<Object, Object> map, File file) {
		FileOutputStream outStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {  
			outStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(outStream);
			objectOutputStream.writeObject(map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally{
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<Object, Object> readObject(File file){
		FileInputStream freader = null;
		ObjectInputStream objectInputStream = null;
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try {
			freader = new FileInputStream(file);
		    objectInputStream = new ObjectInputStream(freader);
		    if (objectInputStream != null) {
		    	map = (HashMap<Object, Object>) objectInputStream.readObject();	
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {	  
			e.printStackTrace();
			return null;
		} finally{
			try{
				if (objectInputStream != null) {
					objectInputStream.close();
				}
				if (freader != null) {
					freader.close();
				}
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return map;
	}
	/**
	 * @param imageUrl
	 * @param imageName
	 * @return
	 */
	public static boolean fetchImage(String imageUrl, String imageName){
		try{
			//new一个URL对象  
	        URL url = new URL(imageUrl);  
	        //打开链接  
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        //设置请求方式为"GET"  
	        conn.setRequestMethod("GET");  
	        //超时响应时间为5秒  
	        conn.setConnectTimeout(5 * 1000);  
	        //通过输入流获取图片数据  
	        InputStream inStream = conn.getInputStream();  
	        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
	        byte[] data = null;
	        try{
	        	data = readInputStream(inStream);  
	        }catch (Exception e){
	        	logger.info("readInputStream:inStream to byte[] error");
	        	return false;
	        }
	        //new一个文件对象用来保存图片，默认保存当前工程根目录  
	        File imageFile = new File(imageName);  
	        //创建输出流  
	        FileOutputStream outStream = new FileOutputStream(imageFile);  
	        //写入数据  
	        outStream.write(data);  
	        //关闭输出流  
	        outStream.close();  
		}catch(Exception ee){
			logger.info("fetchImage:fetch image error");
			return false;
		}
		return true;
	}
	/** 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static BufferedImage getImageFromUrl(String url){
		BufferedImage image = null;
        try {
            URL imageURL = new URL(url);
            InputStream is = imageURL.openConnection().getInputStream();
            image = ImageIO.read(is);
        } catch (Exception e) {
            logger.info("get image from net fail:"+e.toString());
            return null;
        }
        return image;
	}
}
