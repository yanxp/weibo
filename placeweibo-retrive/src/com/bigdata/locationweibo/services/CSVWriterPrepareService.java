package com.bigdata.locationweibo.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVWriterPrepareService {

	private static Logger logger = LoggerFactory.getLogger(CSVWriterPrepareService.class);
	
	private BufferedWriter writer = null;
	
	private FileOutputStream fos = null;
	
	private OutputStreamWriter osw = null;
	
	private String saveDir;
	
	private String fileName;
	
	private String titles;

	public CSVWriterPrepareService(String saveDir, String fileName, String titles) {
		this.saveDir = saveDir;
		this.fileName = fileName;
		this.titles = titles;
		this.prepareCSV();
	}
	
	public boolean writeCSV(String content) {
		
		try{
			writer.write(content + "\n");
			writer.flush();
		} catch (Exception e){
			logger.info("--CSVWriterService----writeCSV----"+e.toString());
			return false;
		}
		return true;
	}
	
	public void closeWriter(){
		try{
			writer.close();
			osw.close();
			fos.close();
		} catch (Exception e){
			logger.info("--CSVWriterService--closeWriter ---"+e.toString());
		}
	}
	
	/**
	 * ����������csv�ļ�
	 * 
	 */
	private void prepareCSV(){		
		/*
		 * ����Ŀ¼
		 */
		File dirfile = new File(saveDir+fileName+"csv/");
        if (!dirfile.exists() && !dirfile.isDirectory()) {
            System.out.println("��(��o��)��  ������,���ڴ���....");
            dirfile.mkdir();
            System.out.println("O(��_��)O�����ɹ�");
        } else {
            System.out.println("O(��_��)OĿ¼����:"+saveDir+"place/");
        }
        /*
         * �����ļ�
         */
        File file = new File(saveDir+fileName+"csv/"+fileName+".csv");
        try {
            file.createNewFile();
        } catch (IOException e) {
        	logger.info("prepareCSV..\n"+e.toString());
        	return ;
        }
        
		try {
			fos = new FileOutputStream(file, true);
			osw = new OutputStreamWriter(fos, "GBK");
	        writer = new BufferedWriter(osw);
	        writer.write(titles+"\n");
		} catch (Exception e) {
			logger.info("prepareCSV...\n"+e.toString());
			return ;
		}
	}
}
