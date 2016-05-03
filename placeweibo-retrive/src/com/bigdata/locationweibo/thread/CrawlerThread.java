package com.bigdata.locationweibo.thread;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.http.client.CookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.api.CookieInfo;
import com.bigdata.locationweibo.enumapi.CrawlerStatus;
import com.bigdata.locationweibo.services.CrawlMblogService;
import com.opencsv.CSVReader;

public class CrawlerThread implements Runnable{

	private CookieStore cookieStore;
    private String pathload;
    private String dir;
    
    private static Logger logger = LoggerFactory.getLogger(CrawlerThread.class);
    
    public CrawlerThread() {}
    
    public CrawlerThread(CookieStore cookieStore, String dir, String pathload)
    {
        this.cookieStore = cookieStore;
        this.pathload=pathload;
        this.dir=dir;
    }
    
	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
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
            ArrayList<String> urls = new ArrayList<String>();
            
            @SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(dir+pathload));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                urls.add(nextLine[0]);
            }

            CrawlerStatus status = null;   
            
            CrawlMblogService crawlService = new CrawlMblogService();
            ArrayList<CookieInfo> cookieInfos = new ArrayList<>();
            CookieInfo cookieInfo = new CookieInfo();
            cookieInfo.setCookieStore(cookieStore);
            cookieInfo.wrapCookie();
            cookieInfos.add(cookieInfo);
            crawlService.setCookies(cookieInfos);
            for (String url : urls) {
            	status = crawlService.crawler(url);
            	if (status == CrawlerStatus.ERROR) break;
			}
            if (status == CrawlerStatus.ERROR) {
            	JOptionPane.showMessageDialog(null, "抓取过程中遇到错误,json页面出问题....请停止抓取....");
            	return;
			}
        }
        catch (Exception error){
            logger.error("happen in crawlerThread:"+new Date()+":"+error.toString());
            JOptionPane.showMessageDialog(null, "抓取过程中遇到错误....请停止抓取....");
        }
		JOptionPane.showMessageDialog(null, "该城市微博抓取完毕....停止抓取....");
    }
	
}
