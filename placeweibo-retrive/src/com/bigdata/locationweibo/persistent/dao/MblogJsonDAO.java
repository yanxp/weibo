package com.bigdata.locationweibo.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.db.BaseDAO;
import com.bigdata.locationweibo.enumapi.SaveStatus;
import com.bigdata.locationweibo.persistent.dbentity.MblogJson;

public class MblogJsonDAO extends BaseDAO{
	
	private Logger logger = LoggerFactory.getLogger(MblogJsonDAO.class);
	
	public MblogJsonDAO() throws Exception {
		super();
	}
	
	/**
	 * ±£¥Êmblog∂‘œÛ
	 * 
	 * @param mblogJson
	 * @return
	 */
	public synchronized SaveStatus save(MblogJson mblogJson){
		
		if (mblogJson == null) {
			logger.info("MblogJsonDAO:save(MblogJson mblog)!!!mblog is null\n");
			return SaveStatus.IS_NULL;
		} else if (mblogJson.getCreateTime() == null || mblogJson.getMblogJsonText() == null 
				||mblogJson.getMid() == null || mblogJson.getPlace_url() == null
				||"".equals(mblogJson.getMid()) || mblogJson.getPlace_url().equals("")
				||"".equals(mblogJson.getMblogJsonText())) {
			logger.info("save(MblogJson mblog)!!!the property of mblog has null:\n");
			return SaveStatus.IS_NULL;
		} 
		/*else if (isExists(mblogJson.getMid(), mblogJson.getPlace_url())) {
			logger.info(mblogJson.getPlace_url()+"****mblog.mid="+mblogJson.getMid()+" is exists");
			return SaveStatus.IS_EXISTS;
		}*/
		
		String sql = "insert into mblog_json(mblog_json_text,create_time,place_url,mid) values(?,?,?,?);";
		Object objects[] = {mblogJson.getMblogJsonText(), new Date(mblogJson.getCreateTime()*1000),
				mblogJson.getPlace_url(), mblogJson.getMid()};
		try{
			updateTable(sql, objects);
		} catch (Exception e){
			logger.info("save(MblogJson mblog):\n"+e.toString());
			return SaveStatus.IS_FAILED;
		}
		return SaveStatus.IS_OK;
	}
	
	public boolean isExists(String mid, String placeUrl){
		if(findByMidAndPlaceUrl(mid, placeUrl) !=null){
			return true;
		}
		return false;
	}
	
	public MblogJson findByMidAndPlaceUrl(String mid, String placeUrl){
		MblogJson mblogJson = null;
		String sql = "select * from mblog_json where mid = ? and place_url = ?;";
		Object objects[] = {mid, placeUrl};
		try {
			mblogJson = getMblogJson(sql, objects);
		} catch (SQLException e) {
			logger.info("findByMid(String mid):\n"+e.toString());
			return null;
		} catch (Exception e) {
			logger.info("findByMid(String mid):\n"+e.toString());		
			return null;
		}
		return mblogJson;
	}
	
	public MblogJson findById(int id){
		MblogJson mblogJson = null;
		String sql = "select * from mblog_json where mblog_json.id = ?;";
		Object objects[] = {id};
		try {
			mblogJson = getMblogJson(sql, objects);
		} catch (SQLException e) {
			logger.info("findById:\n"+e.toString());
			return null;
		} catch (Exception e) {
			logger.info("findById:\n"+e.toString());		
			return null;
		}
		return mblogJson;
	}
	
	public int getMaxId(){
		int maxId = 0;
		String sql = "SELECT MAX(mblog_json.id) from mblog_json;";
		try {
			ResultSet rs = query(sql);
			while(rs.next()){
				maxId = rs.getInt("MAX(mblog_json.id)");
			}
		} catch (SQLException e) {
			logger.info("findMidByNewestTime:\n"+e.toString());
			return 0;
		} catch (Exception e) {
			logger.info("findMidByNewestTime:\n"+e.toString());
			e.printStackTrace();
			return 0;
		}
		return maxId;
	}

	public List<MblogJson> findByPlaceUrlAndCreateTimeLargerThan(String placeUrl, Long timestamp){
		String sql = "select * from mblog_json where place_url like ? and create_time > ? order by create_time desc;";
		Object objects[] = {placeUrl,timestamp};
		List<MblogJson> mblogJsons = null;
		try {
			mblogJsons = getMblogJsons(sql, objects);
		} catch (SQLException e) {
			logger.info("findByPlaceUrlAndCreateTimeLargerThan:\n"+e.toString());
			return null;
		} catch (Exception e) {
			logger.info("findByPlaceUrlAndCreateTimeLargerThan:\n"+e.toString());
			return null;
		}
		return mblogJsons;
	}
	
	public List<MblogJson> findByPlaceUrl(String placeUrl){
		String sql = "select * from mblog_json where place_url = ? order by create_time desc;";
		Object objects[] = {placeUrl};
		List<MblogJson> mblogJsons = null;
		try {
			mblogJsons = getMblogJsons(sql, objects);
		} catch (SQLException e) {
			logger.info("findByPlaceUrl:\n"+e.toString());
			return null;
		} catch (Exception e) {
			logger.info("findByPlaceUrl:\n"+e.toString());
			return null;
		}
		return mblogJsons;
	}

	public List<MblogJson> findByIdRange(int beginId, int endId) {
		String sql = "SELECT * from mblog_json where mblog_json.id > ? and mblog_json.id <= ?;";
		Object objects[] = {beginId, endId};
		List<MblogJson> mblogJsons = new ArrayList<MblogJson>();
		try {
			mblogJsons = getMblogJsons(sql, objects);
		} catch (SQLException e) {
			logger.info("findByPlaceUrl:\n"+e.toString());
			return null;
		} catch (Exception e) {
			logger.info("findByPlaceUrl:\n"+e.toString());
			return null;
		}
		return mblogJsons;
	}

	public String findMidByNewestTime(){
		String mid = "";
		String sql = "select mblog_json.mid from mblog_json where mblog_json.create_time = "
				+ " (select MAX(mblog_json.create_time) from mblog_json);";
		try {
			ResultSet rs = query(sql);
			while(rs.next()){
				mid = rs.getString("mid");
			}
		} catch (SQLException e) {
			logger.info("findMidByNewestTime:\n"+e.toString());
			return null;
		} catch (Exception e) {
			logger.info("findMidByNewestTime:\n"+e.toString());
			e.printStackTrace();
			return null;
		}
		return mid;
	}
	
	public Long getLatestTimeByPlaceUrl(String url){
		
		Long latestTime = 0L;
		String sql = "select MAX(mblog_json.create_time) max_time from mblog_json where mblog_json.place_url = ?";
		Object args[] = {url};
		try{
			ResultSet rs = query(sql, args);
			while(rs.next()){
				try{
					latestTime = rs.getTimestamp("max_time").getTime();
				}catch (NullPointerException e){
					latestTime = 0L;
				}	
			}
		}catch (Exception e){
			logger.info("\ngetLatestTimeByPlaceUrl:\n"+e.toString());
		}
		return latestTime/1000;
	}
	
	public boolean delete(String placeUrl){
		if (placeUrl ==null || "".equals(placeUrl)) {
			return false;
		}
		String sql = "DELETE from mblog_json where mblog_json.place_url = ?;";
		Object objects[] = {placeUrl};
		try{
			updateTable(sql, objects);
		} catch (Exception e){
			logger.info("save(MblogJson mblog)\n"+e.toString());
			return false;
		}
		return true;
	}
	
	public int countByPlaceUrl(String placeUrl){
		if (placeUrl ==null || "".equals(placeUrl)) {
			return -1;
		}
		String sql = "select COUNT(*) from mblog_json where mblog_json.place_url = ?;";
		Object objects[] = {placeUrl};
		int count = 0;
		try{
			ResultSet rs = query(sql, objects);
			while(rs.next()){
				try{
					count = rs.getInt("COUNT(*)");
				}catch (NullPointerException e){
					return -1;
				}	
			}
		}catch (Exception e){
			logger.info("\ngetLatestTimeByPlaceUrl:\n"+e.toString());
			return -1;
		}
		return count;
	}
	
	private List<MblogJson> getMblogJsons (String sql, Object ...objects) throws SQLException, Exception {
		List<MblogJson> mblogJsons = new ArrayList<MblogJson>();
		ResultSet rs = null;
		if (objects != null) {
			rs = query(sql, objects);
		} else {
			rs = query(sql);
		}
		while (rs.next()) {
			MblogJson json = new MblogJson();
			json.setCreateTime(rs.getTimestamp("create_time").getTime());
			json.setMblogJsonText(rs.getString("mblog_json_text"));
			json.setMid(rs.getString("mid"));
			json.setPlace_url(rs.getString("place_url"));
			mblogJsons.add(json);
		}
		return mblogJsons;
	}
	
	private MblogJson getMblogJson (String sql, Object ...objects) throws SQLException, Exception {
		MblogJson json = new MblogJson();
		ResultSet rs = null;
		int count = 0;
		if (objects != null) {
			rs = query(sql, objects);
		} else {
			rs = query(sql);
		}
		while (rs.next()) {
			count ++ ;
			json.setCreateTime(rs.getTimestamp("create_time").getTime());
			json.setMblogJsonText(rs.getString("mblog_json_text"));
			json.setMid(rs.getString("mid"));
			json.setPlace_url(rs.getString("place_url"));
		}
		if(count != 1){
			return null;
		}
		return json;
	}

}
