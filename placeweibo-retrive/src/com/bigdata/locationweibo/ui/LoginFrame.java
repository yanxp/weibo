package com.bigdata.locationweibo.ui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.http.client.CookieStore;

import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.db.BaseDAO;
import com.bigdata.locationweibo.api.PreLoginInfo;
import com.bigdata.locationweibo.enumapi.LoginStatus;
import com.bigdata.locationweibo.persistent.entity.ConfigRecode;
import com.bigdata.locationweibo.services.SinaCookieService;
import com.bigdata.locationweibo.services.TestJsonService;
import com.bigdata.locationweibo.services.WeiboLoginService;
import com.bigdata.locationweibo.ui.compnent.ImageLabel;
import com.bigdata.locationweibo.ui.compnent.MPanel;
import com.bigdata.locationweibo.ui.listener.FrameListener;
import com.bigdata.locationweibo.ui.muticrawlui.MutiLoginFrame;

public class LoginFrame extends JFrame implements ActionListener{
	/**2014.12.31 11:31
	 * login function
	 */
	private static final long serialVersionUID = 4554821229336471464L;
	private JButton sureButton,resetButton;
	private JPanel back;
	private JLabel accountLabel;
    private JLabel passwordLabel;
	private JTextField accountText;
	private JPasswordField passwordtext;
	private ImageLabel verifyLabel;
	private JTextField verifyField;
	private JRadioButton mutiLogin,singleLogin,noLogin;
	private ButtonGroup group;
	private boolean isVerifying;
	
	private JMenuBar bar=null;
	private JMenu operateMenu=null;
	private JMenuItem configItem,testItem,nothingItem;
	private JMenu sysopMenu=null;
	private JMenuItem exitItem,winStyleItem;
	private JLabel sysTimeLabel;
	
	private PreLoginInfo info;
	
	public LoginFrame(){
        initView();
        initMenu();
        initListener();
	}

	private void initMenu() {
		bar=new JMenuBar();
		
		operateMenu=new JMenu("系统操作");
		configItem=new JMenuItem("系统配置");
		testItem=new JMenuItem("抓取测试");
		nothingItem=new JMenuItem("待定功能");
		operateMenu.add(configItem);
		operateMenu.add(testItem);
		operateMenu.add(nothingItem);
		
		sysopMenu=new JMenu("窗口");
		exitItem=new JMenuItem("退出");
		winStyleItem = new JMenuItem("窗体风格");
		sysopMenu.add(winStyleItem);
		sysopMenu.add(exitItem);
		
		operateMenu.setFont(new Font("楷体",Font.PLAIN,14));
		sysopMenu.setFont(new Font("楷体",Font.PLAIN,14));
		configItem.setFont(new Font("楷体",Font.PLAIN,14));
		testItem.setFont(new Font("楷体",Font.PLAIN,14));
		nothingItem.setFont(new Font("楷体",Font.PLAIN,14));
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
		setTitle("新浪蜘蛛");
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
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setUndecorated(true);
		//设置窗口监听器
		new FrameListener(this).addMouseAndMotionListener();

		/*
		 * 按键设置
		 */
        sureButton=new JButton("开始");
        sureButton.setBounds(350, 280, 65, 30);
        resetButton=new JButton("重置");
        resetButton.setBounds(450, 280, 65, 30);
        back.add(sureButton);
        back.add(resetButton);
        
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
        back.add(accountLabel);
        back.add(passwordLabel);
        back.add(accountText);
        back.add(passwordtext);
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
        
        mutiLogin = new JRadioButton("多用户爬取");
        singleLogin=new JRadioButton("单用户爬取");
        noLogin=new JRadioButton("浏览器爬取");
        group=new ButtonGroup();
        group.add(singleLogin);
        group.add(mutiLogin);
        group.add(noLogin);
        singleLogin.setBounds(160, 230, 100, 25);
        mutiLogin.setBounds(260, 230, 100, 25);
        noLogin.setBounds(360, 230, 100, 25);
        singleLogin.setOpaque(false);
        noLogin.setOpaque(false);
        mutiLogin.setOpaque(false);
        singleLogin.setSelected(true);
        back.add(singleLogin);
        back.add(mutiLogin);
        back.add(noLogin);
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
					sureButton.doClick();;
				}
			}   	
		});
        sureButton.addActionListener(this);
        resetButton.addActionListener(this);
        configItem.addActionListener(this);
        testItem.addActionListener(this); 
        exitItem.addActionListener(this);
        winStyleItem.addActionListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		sysTimeLabel.setText("当前时间："+Calendar.getInstance().getTime().toLocaleString()+" ");
		if(e.getSource()==sureButton){
			if (!Tools.fileExists(Constans.pathFilePath)) {
				JOptionPane.showMessageDialog(null, "您的数据库配置文件不存在,请配置文件", 
						"错误", JOptionPane.ERROR_MESSAGE);
				return;
			} else{
				ConfigRecode configRecode = 
						(ConfigRecode) Tools
						.jsonToPojo(Tools.readFile(Constans.pathFilePath), ConfigRecode.class);
				Constans.configFilePath = configRecode.getSavePath();
			}
			Connection connection = null;
			BaseDAO baseDAO = null;
			try{
				baseDAO = new BaseDAO();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "配置文件存在问题,无法连接数据库", 
						"错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				connection = baseDAO.getConnection();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "无法连接数据库", 
						"错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (connection != null) {
				baseDAO.closeConnection();
				if (Tools.networkUsable()) {
					if(singleLogin.isSelected()){
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
									JOptionPane.showMessageDialog(null, "请您输入verifyCode");
									return;
								}
							} else {
								info = loginServices.getPreLoginInfo();
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
								return;
							} else if (loginStatus == LoginStatus.WRONG_VERIFY) {
								needVerifyView(loginServices.getVerifyIcon(info));
								return;
							} else if (loginStatus == LoginStatus.WRONG_PASSWORD_OR_ACC) {
								return;
							} else if (loginStatus == LoginStatus.LOGIN_TOO_MANY_TIMES) {
								return;
							}
							if (loginStatus == LoginStatus.LOGIN_OK) {
								CookieStore cookieStore = loginServices.getCookieStore(entity);
								if (cookieStore != null) {
									if (SinaCookieService.saveCookie(SinaCookieService.generateHeaderCookie(cookieStore), false)) {
										new ControlFrame(cookieStore).setVisible(true);
										this.dispose();
									}
								} else {
									JOptionPane.showMessageDialog(null, "登陆失败,请确保账号密码正确", 
											"错误", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
					else if(noLogin.isSelected()){
						noNeedLogin();
					} else if(mutiLogin.isSelected()){
						new MutiLoginFrame().setVisible(true);
						this.dispose();
					}
				} else{
					JOptionPane.showMessageDialog(null, "您的网络不可用,无法进行爬虫", 
							"错误", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "数据库服务未开启或者配置文件存在问题,无法连接数据库", 
						"错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if(e.getSource() == resetButton){
			accountText.setText(Constans.sina_name);
			passwordtext.setText(Constans.sina_password);
		}
		if (e.getSource() == configItem) {
			new ConfigFrame(this).setVisible(true);
			this.setEnabled(false);
		}
		if (e.getSource() == testItem) {
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
		if (e.getSource() == exitItem) {
			System.exit(0);
		}
	}
	
	private void noNeedLogin(){
	    CookieStore cookieStore = null;
	    new ControlFrame(cookieStore).setVisible(true);
		this.dispose();
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

