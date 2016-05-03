package com.bigdata.locationweibo.ui.muticrawlui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

import org.apache.http.client.CookieStore;

import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.api.Account;
import com.bigdata.locationweibo.api.PreLoginInfo;
import com.bigdata.locationweibo.enumapi.LoginStatus;
import com.bigdata.locationweibo.services.AccountService;
import com.bigdata.locationweibo.services.TestJsonService;
import com.bigdata.locationweibo.services.WeiboLoginService;
import com.bigdata.locationweibo.services.mutiaccount.CookieStoreService;
import com.bigdata.locationweibo.ui.compnent.ImageLabel;
import com.bigdata.locationweibo.ui.compnent.MPanel;
import com.bigdata.locationweibo.ui.filter.CSVFileFilter;
import com.bigdata.locationweibo.ui.listener.FrameListener;

public class MutiLoginFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7878827734470769114L;
	/**2014.12.31 11:31
	 * login function
	 */
	private JButton loginButton,resetButton,saveButton;
	private JPanel back;
	private JLabel accountLabel;
    private JLabel passwordLabel;
	private JTextField accountText;
	private JPasswordField passwordtext;
	private ImageLabel verifyLabel;
	private JTextField verifyField;
	private boolean isVerifying;
	
	private JMenuBar bar=null;
	private JMenu operateMenu=null;
	private JMenuItem chooseFileItem,testItem,beginItem;
	private JMenu sysopMenu=null;
	private JMenuItem exitItem,winStyleItem;
	private JLabel sysTimeLabel;
	
	private PreLoginInfo info;
	
	private Preferences pref;
    private JFileChooser fileChooser;
    private FileFilter filter = new CSVFileFilter();
    
    private JLabel msgLabel;
    
    private Map<Integer, Account> accountMap;
    private CookieStoreService storeService;
    private int accountRound;
	
	public MutiLoginFrame(){
		accountMap = new HashMap<Integer, Account>();
		storeService = new CookieStoreService();
        initView();
        initMenu();
        initListener();
	}

	private void initMenu() {
		bar=new JMenuBar();
		
		operateMenu=new JMenu("系统操作");
		chooseFileItem=new JMenuItem("选择用户文件");
		testItem=new JMenuItem("抓取测试");
		beginItem=new JMenuItem("开始抓取");
		operateMenu.add(chooseFileItem);
		operateMenu.add(testItem);
		operateMenu.add(beginItem);
		
		sysopMenu=new JMenu("窗口");
		exitItem=new JMenuItem("退出");
		winStyleItem = new JMenuItem("窗体风格");
		sysopMenu.add(winStyleItem);
		sysopMenu.add(exitItem);
		
		operateMenu.setFont(new Font("楷体",Font.PLAIN,14));
		sysopMenu.setFont(new Font("楷体",Font.PLAIN,14));
		chooseFileItem.setFont(new Font("楷体",Font.PLAIN,14));
		testItem.setFont(new Font("楷体",Font.PLAIN,14));
		beginItem.setFont(new Font("楷体",Font.PLAIN,14));
		exitItem.setFont(new Font("楷体",Font.PLAIN,14));
		winStyleItem.setFont(new Font("楷体",Font.PLAIN,14));
		
		@SuppressWarnings("deprecation")
		String time=Calendar.getInstance().getTime().toLocaleString();
		Timer t=new Timer(1000, this);
		t.start();
		sysTimeLabel = new JLabel("当前时间："+time+"  ");
		sysTimeLabel.setFont(new Font("楷体",Font.PLAIN,14));
		
		bar.add(operateMenu);
		bar.add(sysopMenu);
		bar.add(sysTimeLabel);
		setJMenuBar(bar);
	}

	private void initView() {
		/*
		 * 窗体布局设置
		 */
		setTitle("多用户登陆");
		ImageIcon backic=new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("res/loginback.jpg"));
		back=new MPanel(backic);
		ImageIcon ic=new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("res/label.png")); 
		Image image=ic.getImage();
		setIconImage(image);
		setContentPane(back);
		ImageIcon img=new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("res/loginback.jpg"));
		int width=img.getIconWidth();
		int height=img.getIconHeight();
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width-1000)/2, (dimension.height-600)/2);
		setLayout(null);
		setResizable(false);
		setUndecorated(true);
		//设置窗口监听器
		new FrameListener(this).addMouseAndMotionListener();

		/*
		 * 按键设置
		 */
        loginButton=new JButton("登录");
        loginButton.setBounds(250, 280, 65, 30);
        resetButton=new JButton("重置");
        resetButton.setBounds(350, 280, 65, 30);
        saveButton = new JButton("保存");
        saveButton.setBounds(450, 280, 65, 30);
        back.add(loginButton);
        back.add(resetButton);
        back.add(saveButton);
        /*
         * 输入和选择设置
         */
        accountLabel = new JLabel("账号");
        passwordLabel = new JLabel("密码");
        accountLabel.setFont(new Font("楷体", Font.BOLD, 20));
        passwordLabel.setFont(new Font("楷体", Font.BOLD, 20));
        accountLabel.setBounds(205, 138, 65, 30);
        passwordLabel.setBounds(205, 180, 65, 30);
        accountText=new JTextField(Constans.sina_name);
        passwordtext=new JPasswordField(Constans.sina_password);
        accountText.setBounds(255, 138, 170, 25);
	    passwordtext.setBounds(255, 180, 170, 25);
	    msgLabel = new JLabel();
	    msgLabel.setFont(new Font("宋体", Font.PLAIN, 13));
	    msgLabel.setForeground(Color.RED);
	    msgLabel.setBounds(160, 230, 200, 30);
        back.add(accountLabel);
        back.add(passwordLabel);
        back.add(accountText);
        back.add(passwordtext);
        back.add(msgLabel);
        /*
         * 验证码区域
         */
        verifyLabel = new ImageLabel(new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("res/pin.png")));
        verifyLabel.setLocation(205,180);
        back.add(verifyLabel);
        verifyField = new JTextField();
        verifyField.setBounds(320, 180, 100, 35);
        verifyField.setFont(new Font("宋体", Font.PLAIN, 20));
        back.add(verifyField);
        verifyLabel.setVisible(false);
        verifyField.setVisible(false);
        
        /*
         * 选择csv文件
         */
        String saveFolder = "C:\\";
        pref = Preferences.userRoot().node(this.getClass().getName());
        String lastPath = pref.get("lastPath", "");
        if (!lastPath.equals("")) {
        	fileChooser = new JFileChooser(lastPath);
        } else{
        	fileChooser = new JFileChooser(saveFolder);
        }  
        fileChooser.setFileFilter(filter);
	}

	private void initListener() {
		accountText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()=='\n'){
					passwordtext.requestFocus();
				}
			}   	
		});
        passwordtext.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()=='\n'){
					loginButton.doClick();;
				}
			}   	
		});
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        saveButton.addActionListener(this);
        chooseFileItem.addActionListener(this);
        testItem.addActionListener(this); 
        beginItem.addActionListener(this);
        exitItem.addActionListener(this);
        winStyleItem.addActionListener(this);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public void actionPerformed(ActionEvent e) {
		sysTimeLabel.setText("当前时间："+Calendar.getInstance().getTime().toLocaleString()+" ");
		if(e.getSource()==loginButton){
			msgLabel.setText("");
			if (Tools.networkUsable()) {
				String account = accountText.getText().toString();
				String password = passwordtext.getText().toString();
				if(account.equals("")||password.equals("")){
					JOptionPane.showMessageDialog(null, "请您输入您的信息");
				} else {
					WeiboLoginService loginServices = new WeiboLoginService(account, password);
					String door = null;
					if (isVerifying) {
						door = verifyField.getText().toString();
						if (door == null || door.equals("")) {
							msgLabel.setText("请输入验证码");
							return;
						}
					} else {
						info = loginServices.getPreLoginInfo();
						if (info == null) {
							msgLabel.setText("账号存在问题,格式不正确");
							return;
						}
						if (loginServices.isPreLoginNeedVerifyCode(info)) {
							needVerifyView(loginServices.getVerifyIcon(info));
							return;
						}
						else {
							noNeedVerifyView();
						}
					} 
					
					String entity = loginServices.getLoginEntity(info, door);
					LoginStatus loginStatus = loginServices.getLoginStatus(entity, info);
					if (loginStatus == LoginStatus.NEED_VERIFY && !isVerifying) {
						needVerifyView(loginServices.getVerifyIcon(info));
						msgLabel.setText("请输入验证码");
						return;
					} else if (loginStatus == LoginStatus.WRONG_VERIFY) {
						needVerifyView(loginServices.getVerifyIcon(info));
						msgLabel.setText("输入验证码错误");
						return;
					} else if (loginStatus == LoginStatus.WRONG_PASSWORD_OR_ACC) {
						msgLabel.setText("输入账号或者密码错误");
						return;
					} else if (loginStatus == LoginStatus.LOGIN_TOO_MANY_TIMES) {
						msgLabel.setText("登陆过于频繁");
						return;
					}
					if (loginStatus == LoginStatus.LOGIN_OK) {
						CookieStore cookieStore = loginServices.getCookieStore(entity);
						if (cookieStore != null) {
							if (TestJsonService.isOkToRetrive(cookieStore)) {
								msgLabel.setText("登陆成功,该账号可以用以爬取");
								storeService.add(accountMap.get(accountRound).getAccount(), cookieStore);
							}else{
								msgLabel.setText("登陆成功,该账号无法爬取内容");
							}		
						} else {
							JOptionPane.showMessageDialog(null, "登陆失败,请确保账号密码正确", 
									"错误", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			} else{
				JOptionPane.showMessageDialog(null, "您的网络不可用,无法进行爬虫", 
						"错误", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getSource() == resetButton){
			msgLabel.setText("");
			if (isVerifying) {
				noNeedVerifyView();
			}
			if (accountMap.size() != 0 && accountRound <= accountMap.size()) {
				if (accountRound < accountMap.size()) {
					Account account = accountMap.get(accountRound);
					accountText.setText(account.getAccount());
					passwordtext.setText(account.getPassword());
					accountRound++;
				}else{
					msgLabel.setText("已经是最后一个账号了,请保存账号cookies信息");
				}
			} else{
				accountText.setText("");
				passwordtext.setText("");
				accountText.requestFocus();
			}
		}
		if (e.getSource() == saveButton) {
			if (storeService.getSize() > 0) {
				if (storeService.saveCookieStore()) {
					msgLabel.setText("账号cookies信息保存成功");
				} else{
					msgLabel.setText("账号cookies信息保存失败");
				}
			}else{
				msgLabel.setText("没有任何可抓取账号登陆");
			}
		}
		if (e.getSource() == chooseFileItem) {
			msgLabel.setText("");
			int i = fileChooser.showOpenDialog(null);
        	if(i == fileChooser.APPROVE_OPTION){ 
        		if(fileChooser.getSelectedFile()!=null) {
                	pref.put("lastPath",fileChooser.getSelectedFile().getPath());
                	if(!fileChooser.getSelectedFile().getName().endsWith(".csv")){
                		JOptionPane.showMessageDialog(null, "文件格式不正确，请重新选择文件!!");
                    }else {
                    	String fileName = fileChooser.getSelectedFile().getName();
        				msgLabel.setText("打开文件:"+fileName);
        				accountMap = AccountService.getAccounts(fileChooser.getSelectedFile().getParent()+"\\", 
        						fileChooser.getSelectedFile().getName());
        				accountRound = 1;
                    }
        		}
        	}
		}
		if (e.getSource() == testItem) {
			msgLabel.setText("");
			if (Tools.networkUsable()) {
				if (TestJsonService.isOkToRetrive()) {
					JOptionPane.showMessageDialog(null, "测试成功,可以进行抓取O(∩_∩)O");
				} else{
					JOptionPane.showMessageDialog(null, "暂时出现网络异常,新浪接口容易跳转/(ㄒoㄒ)/", 
							"错误", JOptionPane.ERROR_MESSAGE);
				}
			} else{
				JOptionPane.showMessageDialog(null, "您的网络不可用,无法进行爬虫", 
						"错误", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == beginItem) {
			new MutiControlFrame(this).setVisible(true);
			this.setVisible(false);
			
		}
		if (e.getSource() == exitItem) {
			System.exit(0);
		}
	}
	
	private void needVerifyView(ImageIcon imageIcon){
		accountLabel.setBounds(205, 100, 65, 30);
        passwordLabel.setBounds(205, 145, 65, 30);
		accountText.setBounds(255, 100, 170, 25);
	    passwordtext.setBounds(255, 145, 170, 25);
	    verifyLabel.setIcon(imageIcon);
	    verifyLabel.validate();
	    verifyLabel.setVisible(true);
        verifyField.setVisible(true);
        isVerifying = true;
	}
	
	private void noNeedVerifyView(){
		accountLabel.setBounds(205, 138, 65, 30);
        passwordLabel.setBounds(205, 180, 65, 30);
		accountText.setBounds(255, 138, 170, 25);
	    passwordtext.setBounds(255, 180, 170, 25);
	    verifyLabel.setVisible(false);
        verifyField.setVisible(false);
        isVerifying = false;
	}
	
}