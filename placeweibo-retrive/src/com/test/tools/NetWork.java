package com.test.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 测试网络连通性
 * @author SKY_WEI
 */
public class NetWork {
	static BufferedReader bufferedReader;
	
	public static void main(String[] args) throws IOException {

		String address = "baidu.com";
		try {
			Process process = Runtime.getRuntime()
					.exec("ping " + address);
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

		if (process.getErrorStream() == null) {
			System.out.println("链接成功");
		}
		String connectionStr =null;
		while ((connectionStr = bufferedReader.readLine()) != null) {
			System.out.println(connectionStr);
		}

	} catch (IOException e) {
		System.out.println("链接失败");
		e.printStackTrace();
		} finally {
			bufferedReader.close();
		}
	}
	
}