package com.bigdata.locationweibo.thread;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.api.CookieInfo;
import com.bigdata.locationweibo.enumapi.CrawlerStatus;
import com.bigdata.locationweibo.services.CrawlMblogService;
import com.bigdata.locationweibo.services.mutiaccount.CookieStoreService;
import com.opencsv.CSVReader;

public class MutiCrawlerThread implements Runnable{

    private String pathload;
    private String dir;
    private CookieStoreService cookieStoreService;
    
	private static Logger logger = LoggerFactory.getLogger(MutiCrawlerThread.class);
    
    public MutiCrawlerThread() {
    	cookieStoreService = new CookieStoreService();
    }
    
    public MutiCrawlerThread(String dir, String pathload)
    {
    	cookieStoreService = new CookieStoreService();
        this.pathload=pathload;
        this.dir=dir;
    }
    
	public String getPathload() {
		return pathload;
	}

	public void setPathload(String pathload) {
		this.pathload = pathload;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	@Override
	public void run() {
		try {
			ArrayList<CookieInfo> cookies = cookieStoreService.getCookieInfo();
			if (cookies == null || cookies.size() <= 0) {
				logger.info("������,�޷���ȡ���û���cookie��Ϣ...");
				JOptionPane.showMessageDialog(null, "��ȡ���û���cookie��Ϣ��������,��ֹͣץȡ");
				return;
			} 
			
            ArrayList<String> urls = new ArrayList<String>();
            
            @SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(dir+pathload));

            String[] nextLine;
            CrawlerStatus status = null;
            while ((nextLine = reader.readNext()) != null) {
                urls.add(nextLine[0]);
            }

            CrawlMblogService mblogService = new CrawlMblogService(cookies);
            for (String url : urls) {
				status = mblogService.crawler(url);
				if (status == CrawlerStatus.ERROR) break;
			}
            
            if (status == CrawlerStatus.ERROR) {
            	JOptionPane.showMessageDialog(null, "ץȡ��������������,��ֹͣץȡ....");
            	return;
			}
        }
        catch (Exception error){
            logger.error("happen in crawlerThread:"+new Date()+":"+error.toString());
            error.printStackTrace();
            JOptionPane.showMessageDialog(null, "ץȡ��������������....��ֹͣץȡ....");
            return ;
        }
		JOptionPane.showMessageDialog(null, "ץȡ���....��ֹͣץȡ....");
    }
	
}
