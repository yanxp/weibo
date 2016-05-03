package com.bigdata.locationweibo.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.db.BaseDAO;
import com.bigdata.locationweibo.persistent.dbentity.JsonPage;

public class JsonPageDAO extends BaseDAO{

	private Logger logger = LoggerFactory.getLogger(JsonPageDAO.class);
	
	public JsonPageDAO() throws Exception {
		super();
	}
	public JsonPageDAO(String driver, String password, String url, String user) {
		super(driver, url, user, password);
	}
	
	public JsonPage findJsonPageByPlaceUrlAndPageNumAndTimestamp(String placeUrl, int PageNum, Long timestamp){
		
		if (placeUrl == null || placeUrl.equals("") || timestamp < 0) {
			logger.info("---findJsonPageByPlaceUrlAndPageNumAndTimestamp---\n"+"params not allow");
			return null;
		}
		JsonPage jsonPage = null;
		String sql = "select * from json_page where place_url = ? and page_num = ? and latest_time = ?;";
		Object objects[] = {placeUrl, PageNum, new Date(timestamp)};
		try{
			jsonPage = getJsonPage(sql , objects);
		}catch (SQLException e) {
			logger.info("---findJsonPageByPlaceUrlAndPageNumAndTimestamp---\n"+e.toString());
		} catch (Exception e) {
			logger.info("---findJsonPageByPlaceUrlAndPageNumAndTimestamp---\n"+e.toString());
		}
		return jsonPage;
	}
	
	public List<JsonPage> findJsonPageByPlaceUrl(String placeUrl){
		List<JsonPage> jsonPages = null;
		String sql = "select * from json_page where place_url = ?;";
		Object objects[] = {placeUrl};
		try {
			jsonPages = getJsonPages(sql, objects);
		} catch (SQLException e) {
			logger.info("---findJsonPageByPlaceUrl---\n"+e.toString());
		} catch (Exception e) {
			logger.info("---findJsonPageByPlaceUrl---\n"+e.toString());
		}
		return jsonPages;
	}

	public boolean save(JsonPage jsonPage){
		
		if (jsonPage == null) {
			logger.info("---save(JsonPage jsonPage)!!!jsonpage is null---\n");
			
			return false;
		} else if (jsonPage.getJsonPage() == null || jsonPage.getJsonPage().equals("") 
				|| jsonPage.getLatestTime() == null || jsonPage.getPlaceUrl() == null ) {
			logger.info("---save(JsonPage jsonPage)!!!the property of jsonpage has null---\n");
			return false;
		}
		
		String sql  = "insert into json_page(json_page,latest_time,page_num,place_url) values(?,?,?,?);";
		Object objects[] = {jsonPage.getJsonPage(), new Date(jsonPage.getLatestTime()), 
				jsonPage.getPageNum(), jsonPage.getPlaceUrl()};
		
		return updateJsonPageTable(sql, objects);
	}

	public boolean updateJsonPageByOldJsonPage(JsonPage oldJsonPage){
		if (oldJsonPage == null) {
			logger.info("---save(JsonPage jsonPage)!!!jsonpage is null---\n");
			
			return false;
		} else if (oldJsonPage.getJsonPage() == null || oldJsonPage.getJsonPage().equals("") 
				|| oldJsonPage.getLatestTime() == null || oldJsonPage.getPlaceUrl() == null ) {
			logger.info("---save(JsonPage jsonPage)!!!the property of jsonpage has null---\n");
			return false;
		}
		String sql  = "update json_page set json_page = ? where latest_time = ? and page_num = ? and place_url = ?;";
		Object objects[] = {oldJsonPage.getJsonPage(), oldJsonPage.getLatestTime(), 
				oldJsonPage.getPageNum(), oldJsonPage.getPlaceUrl()};
		
		return updateJsonPageTable(sql, objects);
	}
	
	/**
	 * update table
	 * @param sql
	 * @param objects
	 * @return
	 */
	private boolean updateJsonPageTable(String sql, Object ...objects ){
		boolean flag = false;
		try {
			if (objects != null) {
				flag = update(sql, objects);
			}
			else if (objects == null) {
				flag = update(sql);
			}
		} catch (SQLException e) {
			logger.info("---updateJsonPageTable---\n"+e.toString());
			return false;
		} catch (Exception e) {
			logger.info("---updateJsonPageTable---\n"+e.toString());
			return false;
		}
		return flag;
	}
	
	/**
	 * private 方法  通过传入sql语句和参数来查询jsonpage的list对象
	 * @throws Exception 
	 * @throws SQLException 
	 */
	private List<JsonPage> getJsonPages(String sql, Object ...objects) throws SQLException, Exception{
		List<JsonPage> jsonPages = new ArrayList<>();
		ResultSet rs = null;
		if (objects != null) {
			rs = query(sql, objects);
		} else {
			rs = query(sql);
		}
		while (rs.next()) {
			JsonPage jsonPage = new JsonPage();
			jsonPage.setJsonPage(rs.getString("json_page"));
			jsonPage.setLatestTime(rs.getLong("latest_time"));
			jsonPage.setPageNum(rs.getInt("page_num"));
			jsonPage.setPlaceUrl(rs.getString("place_url"));
			jsonPages.add(jsonPage);
		}
		return jsonPages;
	}
	
	/**
	 * private方法  通过传入sql语句和参数来查询单个的Jsonpage对象
	 */
	private JsonPage getJsonPage(String sql, Object ...objects) throws SQLException, Exception {
		JsonPage jsonPage = new JsonPage();
		ResultSet rs = null;
		int count = 0;
		if (objects != null) {
			rs = query(sql, objects);
		} else {
			rs = query(sql);
		}
		while (rs.next()) {
			count++;
			jsonPage.setJsonPage(rs.getString("json_page"));
			jsonPage.setLatestTime(rs.getLong("latest_time"));
			jsonPage.setPageNum(rs.getInt("page_num"));
			jsonPage.setPlaceUrl(rs.getString("place_url"));
		}
		if (count != 1) {
			return null;
		}
		return jsonPage;
	}
}
