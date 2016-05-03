package com.bigdata.locationweibo.persistent.dao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.common.util.Tools;
import com.bigdata.db.BaseDAO;
import com.bigdata.locationweibo.api.WeiboContent;
import com.bigdata.locationweibo.enumapi.SaveStatus;

public class WeiboContentDAO extends BaseDAO{

	private static Logger logger = LoggerFactory.getLogger(WeiboContentDAO.class);
	
	public WeiboContentDAO() throws Exception {
		super();
	}
	
	public SaveStatus save(WeiboContent content){
		if (content == null) {
			logger.info("---WeiboContentDAO---save(WeiboContent content)--content is null--\n");
			return SaveStatus.IS_NULL;
		} else if(content.getPlaceUrl() == null
				|| content.getPostText() == null || content.getPostText().equals("")){
			logger.info("---WeiboContentDAO---save(WeiboContent content)--content is null--\n");
			return SaveStatus.IS_NULL;
		} 
		
		String sql = "INSERT INTO `weibopage`.`weibo_content` (`placeUrl`, `createTime`, "
				+ "`userNickname`, `userHomeUrl`, `createSource`, `postText`, `weiboUrl`, "
				+ "`pictureNum`, `isVerified`, `verifiedReason`, `gender`, `description`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Object objects[] = {content.getPlaceUrl(), Tools.wrapTime(new Date(content.getCreateTime()*1000)), content.getUserNickname(), 
				content.getUserHomeUrl(), content.getCreateSource(),content.getPostText(), 
				content.getWeiboUrl(), content.getPictureNum(), content.isVerified(),
				content.getVerifiedReason(), content.getGender(), content.getDescription()};
		try{
			updateTable(sql, objects);
		} catch (Exception e){
			logger.info("---WeiboContentDAO---save(WeiboContent content)-----\n"+e.toString());
			return SaveStatus.IS_FAILED;
		}
		return SaveStatus.IS_OK;
	}

}
