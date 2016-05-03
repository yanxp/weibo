package com.bigdata.locationweibo.services;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.bigdata.common.util.BigIntegerRSA;
import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.api.PreLoginInfo;
import com.bigdata.locationweibo.enumapi.LoginStatus;

@SuppressWarnings("deprecation")
public class WeiboLoginService {
	
	private static String SINA_PK = "EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090"
			+ "CB2D245A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F"
			+ "6D63C55FE2F08A49B353F444AD3993CACC02DB784ABBB8E42A9B1BBFFFB38BE18"
			+ "D78E87A0E41B9B8F73A928EE0CCEE1F6739884B9777E4FE9E88A1BBE495927AC4"
			+ "A799B3181D6442443";
	private static String loginAjaxUrl = "http://weibo.com/ajaxlogin.php?framelogin=1"
			+ "&callback=parent.sinaSSOController.feedBackUrlCallBack";
	
	private static final Log logger = LogFactory.getLog(WeiboLoginService.class);
	
	private String username;
	private String password;
	private CloseableHttpClient client;
	
	public WeiboLoginService(String username, String password) {
		this.username = username;
		this.password = password;
		client = CookieConfigurationService.configeClient(null);
	}

	public boolean saveVerifyCode(PreLoginInfo info){
		Long r_num = Math.round(Math.random() * (99999999 - 20000000) + 20000000);
		String verifycodeUrl = "http://login.sina.com.cn/cgi/pin.php?r="+r_num+"&s=0&p="+info.getPcid();
		if (Tools.fetchImage(verifycodeUrl, "pin.png")) {
			return true;
		}
		return false;
	}
	
	public String createVerifyUrl(PreLoginInfo info){
		if (info == null || info.equals("")) {
			return null;
		}
		Long r_num = Math.round(Math.random() * (99999999 - 20000000) + 20000000);
		String verifycodeUrl = "http://login.sina.com.cn/cgi/pin.php?r="+r_num+"&s=0&p="+info.getPcid();
		return verifycodeUrl;
	}
	
	public ImageIcon getVerifyIcon(PreLoginInfo info){
		ImageIcon imageIcon = null;
		String verifyUrl = createVerifyUrl(info);
		try{
			BufferedImage bufferedImage = Tools.getImageFromUrl(verifyUrl);
			imageIcon = new ImageIcon(bufferedImage);
		}catch (Exception e){
			logger.info("getVerifyIcon fail:"+e.toString());
			return null;
		}
		return imageIcon;
	}
	
	public boolean isPreLoginNeedVerifyCode(PreLoginInfo info){
		if (info != null) {
			if (info.getShowpin() == 1) {
				logger.info("showpin="+1+",need input verifyCode");
				return true;
			}
		}
		return false;
	}
	
	//retcode=2070&reason=输入的验证码不正确
	//retcode=4049&reason=需要输入验证码
	public LoginStatus getLoginStatus(String entity, PreLoginInfo info){
		if(entity.indexOf("code=0") == -1) {
			String retcode = null;
			try{
				retcode = URLDecoder.decode(entity).substring(entity.indexOf("retcode=")+8, entity.indexOf("&reason"));
			} catch (Exception e){
				return LoginStatus.LOGIN_NO;
			}
			logger.error("登陆失败:" + URLDecoder.decode(entity.substring(entity.indexOf("reason=") + 7,
					entity.indexOf("&#39;\"/>")))+"The retcode is:"+ retcode);
			if (retcode.equals("4049")) {
				info.setNonce(generateNonce());
				return LoginStatus.NEED_VERIFY;
			} else if (retcode.equals("2070")) {
				info.setNonce(generateNonce());
				return LoginStatus.WRONG_VERIFY;
			} else if (retcode.equals("101")) {
				return LoginStatus.WRONG_PASSWORD_OR_ACC;
			} else if (retcode.equals("4040")) {
				return LoginStatus.LOGIN_TOO_MANY_TIMES;
			}
			return LoginStatus.LOGIN_NO;
		}
		return LoginStatus.LOGIN_OK;
	}
	
	public PreLoginInfo getPreLoginInfo(){
		PreLoginInfo info = new PreLoginInfo();
		try {
			info = getPreLoginBean(client, username);
		} catch (HttpException var23) {
			logger.error(var23);
			return null;
		} catch (IOException var24) {
			logger.error("登陆失败,请确认已连接正确网络！" + var24);
			return null;
		} catch (JSONException var25) {
			logger.error("Json解析失败！" + var25);
			return null;
		}
		return info;
	}
	
	public String getLoginEntity(PreLoginInfo info, String door){
		if (info == null) {
			return null;
		}
		long servertime = info.getServertime();
		String nonce = info.getNonce();
		String pwdString = servertime + "\t" + nonce + "\n" + password;
		String sp = (new BigIntegerRSA()).rsaCrypt(SINA_PK, "10001", pwdString);
		ArrayList<BasicNameValuePair> valuePairs = new ArrayList<BasicNameValuePair>();
		valuePairs.add(new BasicNameValuePair("entry", "weibo"));
		valuePairs.add(new BasicNameValuePair("gateway", "1"));
		valuePairs.add(new BasicNameValuePair("from", ""));
		valuePairs.add(new BasicNameValuePair("savestate", "7"));
		valuePairs.add(new BasicNameValuePair("useticket", "1"));
		if (door != null && !door.equals("")) {
			valuePairs.add(new BasicNameValuePair("pcid", info.getPcid()));
			valuePairs.add(new BasicNameValuePair("door", door.toLowerCase()));
		}
		valuePairs.add(new BasicNameValuePair("ssosimplelogin", "1"));
		valuePairs.add(new BasicNameValuePair("vsnf", "1"));
		valuePairs.add(new BasicNameValuePair("su", encodeUserName(username)));
		//miniblog是新浪微博的服务名
		valuePairs.add(new BasicNameValuePair("service", "miniblog"));
		valuePairs.add(new BasicNameValuePair("servertime", 1440473992 + ""));
		valuePairs.add(new BasicNameValuePair("nonce", nonce));
		valuePairs.add(new BasicNameValuePair("pwencode", "rsa2"));
		valuePairs.add(new BasicNameValuePair("rsakv", info.getRsakv()));
		valuePairs.add(new BasicNameValuePair("sp", sp));
		valuePairs.add(new BasicNameValuePair("encoding", "UTF-8"));
		valuePairs.add(new BasicNameValuePair("prelt", "219"));
		valuePairs.add(new BasicNameValuePair("returntype", "META"));
		valuePairs.add(new BasicNameValuePair("url", loginAjaxUrl));

		String entity = null;
		HttpPost post = new HttpPost("http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.2)");
		post.setHeader("User-Agent", Constans.userAgent);
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setHeader("Cache-Control", "max-age=0");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			post.setEntity(new UrlEncodedFormEntity(valuePairs, "UTF-8"));
			HttpResponse response = client.execute(post);
			entity = EntityUtils.toString(response.getEntity());
		} catch (ParseException e1) {
			e1.printStackTrace();
			logger.error(e1);
			return null;
		} catch (ClientProtocolException e2) {
			e2.printStackTrace();
			logger.error(e2);
			return null;
		} catch (IOException e3) {
			e3.printStackTrace();
			logger.error(e3);
			return null;
		}
		return entity;
	}
	
	public CookieStore getCookieStore(String entity) {
		if (entity == null || "".equals(entity)) {
			return null;
		}
		BasicCookieStore cookieStore = null;
		try {
			CloseableHttpClient client = CookieConfigurationService.configeClient(null);
			String url = entity.substring(entity.indexOf("http://weibo.com/ajaxlogin.php?"), entity.indexOf("code=0") + 6);
			HttpGet getMethod = new HttpGet(url);
			cookieStore = new BasicCookieStore();
			BasicHttpContext localContext = new BasicHttpContext();
			localContext.setAttribute("http.cookie-store", cookieStore);
			HttpResponse response = client.execute(getMethod, localContext);
			entity = EntityUtils.toString(response.getEntity());
			logger.info("用户名:" + username + "登陆成功！\n" + entity.substring(entity.indexOf("({"), entity.indexOf(";</script>")));
		} catch (ParseException e1) {
			e1.printStackTrace();
			logger.error(e1);
			return null;
		} catch (ClientProtocolException e2) {
			e2.printStackTrace();
			logger.error(e2);
			return null;
		} catch (IOException e3) {
			e3.printStackTrace();
			logger.error(e3);
			return null;
		}

		return cookieStore;
	}

	private PreLoginInfo getPreLoginBean(HttpClient client, String username) 
			throws HttpException, IOException, JSONException {
		String serverTime = getPreLoginInfo(client, username);
		JSONObject jsonInfo = new JSONObject(serverTime);
		PreLoginInfo info = new PreLoginInfo();
		info.setNonce(jsonInfo.getString("nonce"));
		info.setPcid(jsonInfo.getString("pcid"));
		info.setPubkey(jsonInfo.getString("pubkey"));
		//retcode=102  账号冻结    retcode=103 正常连接 没有消息    其余有可能是掉线
		info.setRetcode(jsonInfo.getInt("retcode"));
		info.setRsakv(jsonInfo.getString("rsakv"));
		info.setServertime(jsonInfo.getLong("servertime"));
		int showpin = 0;
		try{
			showpin = jsonInfo.getInt("showpin");
		}catch(Exception e){
			showpin = 0;
		}
		info.setShowpin(showpin);
		return info;
	}

	private String getPreLoginInfo(HttpClient client, String username) 
			throws ParseException, IOException {
		String preloginurl = "http://login.sina.com.cn/sso/prelogin.php?"
				+ "entry=sso&callback=sinaSSOController.preloginCallBack"
				+ "&su="+encodeUserName(username)+"&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.2)"
				+ "&_=" + getCurrentTime();
       	/*
       	 * String preloginurl = "http://login.sina.com.cn/sso/prelogin.php?"
				+ "entry=weibo&callback=sinaSSOController.preloginCallBack"
				+ "&su=dW5kZWZpbmVk&rsakt=mod&checkpin=1"
				+ "&client=ssologin.js(v1.4.18)&_="+getCurrentTime();
       	 *      	
       	 */
		HttpGet get = new HttpGet(preloginurl);
		get.setHeader("Host", "login.sina.com.cn");
		get.setHeader("Accept", "*/*");
		get.setHeader("Referer", "http://weibo.com/");
		get.setHeader("User-Agent", Constans.userAgent);
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		get.setHeader("Connection", "keep-alive");
		HttpResponse response = client.execute(get);
		String getResp = EntityUtils.toString(response.getEntity());
		int firstLeftBracket = getResp.indexOf("(");
		int lastRightBracket = getResp.lastIndexOf(")");
		String jsonBody = getResp.substring(firstLeftBracket + 1, lastRightBracket);
		return jsonBody;
	}

	private String getCurrentTime() {
		long servertime = (new Date()).getTime() / 1000L;
		return String.valueOf(servertime);
	}

	private String encodeUserName(String email) {
		email = email.replaceFirst("@", "%40");
		email = Base64.encodeBase64String(email.getBytes());
		return email;
	}

	private String generateNonce(){
		String nonce = "";
		String []Sequence={"Q","W","E","R","T","Y",
				"U","I","O","P","A","S",
				"D","F","G","H","J","K",
				"L","Z","X","C","V","B","N","M",
				"1","2","3","4","5","6","7","8","9","0",} ;
		  TreeSet<String> treeSet = new TreeSet<String>() ;
		  while(treeSet.size() < 6 ){
			  int n = (int) (Math.random() * 36) ;
			  treeSet.add(Sequence[n]) ;
		  }
		  Iterator<String> iter;
		  for(iter = treeSet.iterator() ; iter.hasNext() ;){
			  nonce = nonce + iter.next();
		  }
		  return nonce;
	}
}
