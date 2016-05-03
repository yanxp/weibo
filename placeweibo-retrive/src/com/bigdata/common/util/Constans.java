package com.bigdata.common.util;

public class Constans {

	/*
	 * db配置
	 */
	public static final String driver = "com.mysql.jdbc.Driver";
	
	public static final String url = "jdbc:mysql://localhost/weibopage?useUnicode=true&"
			+ "amp&characterEncoding=UTF8";
			
    public static final String user = "root";
	
    public static final String password = "123456";

    /*
     * 系统配置信息
     * C:\\Program Files/Weibo Conf/
     */
    public static final String pathFilePath = "pathsave.bin";
    
	public static final String buildConfigDir = "conf\\";
	
	public static final String buildConfigFileName = "database_configuration.bin";
	
	public static String configFilePath = "conf\\database_configuration.bin";
	
	public static String cookieFilePath = "cookie.txt";

	public static String cookiestoreFileName = "cookiestore.dat";

	public static String cookiesFileName = "cookies.dat";
	
	/*
	 * 常量
	 */
	public static final String encUrl = "http://weibo.com/p/100101B2094653D26CA6FB439A";
	
	public static final String norUrl = "http://weibo.com/p/100101121.15_31.35";
	
	public static final String sina_name = "13660134991@163.com";
	public static final String sina_password = "ZHANGjing6898448";
	
	/*
	 * UserAgent
	 */
	public static final String userAgentSougou = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
			+ "Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0";
	public static final String userAgentChrome = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
			+ "Chrome/45.0.2454.7 Safari/537.36";
	public static final String userAgentIPhone = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 "
			+ "(KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
	public static final String userAgentHTC = "HTC_Amaze_4G_4.0.3_weibo_5.5.0_android";
	public static final String userAgent = userAgentIPhone;
	
	public static final String defaultCookie = "IPUSH_TS=1443691698; "
			+ "_T_WM=f9e15e3eda316365f4fed7980745c5a6; "
			+ "SUB=_2A257CIZWDeTxGeVP6lAX8ynNwzuIHXVY8ioerDV6PUJbrdAKLVbikW18Y-YVnsTAaKefxeeC9A6f8hSONQ..; "
			+ "SUHB=079zGI7-wwwMGR";
}
