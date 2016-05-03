package com.bigdata.locationweibo.services;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.locationweibo.api.Account;
import com.opencsv.CSVReader;

public class AccountService {
	
	private final static Logger logger = LoggerFactory.getLogger(AccountService.class);	

	public static Map<Integer, Account> getAccounts(String dir, String pathload) {
		Map<Integer, Account> maps = new HashMap<Integer, Account>();
		try{
			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(dir+pathload));
			String[] nextLine;
			int i = 0;
            while ((nextLine = reader.readNext()) != null) {
            	i++;
            	Account account = new Account();
            	account.setAccount(nextLine[0]);
            	account.setPassword(nextLine[1]);
            	maps.put(i, account);
            }
		}catch (Exception e){
			logger.info("prepareAccount error \n"+e.toString());
			return new HashMap<Integer, Account>();
		}
		return maps;
	}
}
