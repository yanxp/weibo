package com.bigdata.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.persistent.entity.DataBaseConfiguration;
import com.bigdata.locationweibo.services.DataBaseConfigurationService;

public class BaseDAO {
	private Logger logger = LoggerFactory.getLogger(BaseDAO.class);

	private Connection connection = null;
	
	private String driver =null;
	
	private String url = null;
	
	private String username = null;
	
	private String password = null;
	
	public BaseDAO() throws Exception {
		DataBaseConfiguration config = DataBaseConfigurationService.getDataBaseConfiguration();;
		if (config != null) {
			setDriver(config.getDriver());
			setPassword(config.getPassword());
			setUrl(config.getUrl());
			setUsername(config.getUsername());
		} else {
			logger.info("--database configuration error--");
		}
	}
	
	public BaseDAO( String driver , String url , String username , String password ){
		
		this.driver = driver; 
		
		this.url = url; 
		
		this.username = username ; 
		
		this.password = password;
		
	}

	//��ȡ���ݿ�����
	public Connection getConnection() throws Exception {
		if(connection == null){
			
			if (driver == null || url == null || username == null || password == null) {
				logger.info("-----the params of connection to database has null-----------");
				
				return null;
			}
			try{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, password);
			}catch (Exception e){
				logger.info("---BaseDAO(getConnection)---数据库服务未开启或者配置文件存在问题,无法连接数据库------");
				return null;
			}		
		}
		Statement stm = connection.createStatement();
		stm.execute("set names utf8mb4");
		return connection;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//�����¼
	public boolean update (String sql , Object... args) throws SQLException, Exception{
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		
		for( int i = 0 ; i < args.length ; i ++){
			ps.setObject(i+1, args[i]);
		}
		
		if(ps.executeUpdate() != 1){
			return false;
		}
		
		return true;
	}
	
	//�����¼
	public boolean update (String sql)throws SQLException, Exception{
		Statement stm = getConnection().createStatement();
		int exeCallBack = stm.executeUpdate(sql);
		if(exeCallBack != 1){
			return false;
		}
		return true;
	}
	
	//ִ�в�ѯ
	public ResultSet query( String sql , Object... args) throws SQLException, Exception{
		PreparedStatement ps = getConnection().prepareStatement(sql);
		
		for( int i = 0; i < args.length ; i++){
			
			ps.setObject(i+1, args[i]);
			
		}
		
		return ps.executeQuery();
		
	}
	
	//ִ�в�ѯ
	public ResultSet query(String sql) throws SQLException, Exception{
		
		Statement stm = getConnection().createStatement();
		
		return stm.executeQuery(sql);
	}
	
	public void closeConnection(){
		
		try {
			if(connection != null && !connection.isClosed() ){
				connection.close();
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	/**
	 * update table
	 * @param sql
	 * @param objects
	 * @return
	 */
	public boolean updateTable(String sql, Object ...objects ){
		boolean flag = false;
		try {
			if (objects != null) {
				flag = update(sql, objects);
			}
			else if (objects == null) {
				flag = update(sql);
			}
		} catch (SQLException e) {
			logger.info("---updateTable---\n"+e.toString());
			return false;
		} catch (Exception e) {
			logger.info("---updateTable---\n"+e.toString());
			return false;
		}
		return flag;
	}
}
