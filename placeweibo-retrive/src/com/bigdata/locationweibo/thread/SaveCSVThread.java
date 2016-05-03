package com.bigdata.locationweibo.thread;

import java.util.Date;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.services.WeiboContentService;

public class SaveCSVThread implements Runnable{

	private String dir;
	private String saveFile;
	
	private final static Logger logger = LoggerFactory.getLogger(SaveCSVThread.class);
	
	public SaveCSVThread(String dir, String pathload, String savaFile) {		
		this.dir = dir;
		this.saveFile = savaFile;
	}
	@Override
	public void run() {
		boolean flag = false;
		try{
			flag = WeiboContentService.getWeiboContentAndSave(dir, saveFile);
		}catch (Exception error){
            logger.error("happen in crawlerThread:"+new Date()+":"+error.toString());
            error.printStackTrace();
            JOptionPane.showMessageDialog(null, "������������������....��ֹͣ����....");
            return ;
        }
		if (flag) {
			JOptionPane.showMessageDialog(null, "�������....��ֹͣ����....");
		} else{
			JOptionPane.showMessageDialog(null, "������������������....��ֹͣ����....");
		}
	}

}
