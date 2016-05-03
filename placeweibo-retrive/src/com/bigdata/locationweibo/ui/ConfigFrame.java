package com.bigdata.locationweibo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.persistent.entity.DataBaseConfiguration;

public class ConfigFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3702682415176338378L;

	private JFrame parentFrame;
	
	private JLabel titleLabel;
	
	private JLabel driverLabel;
	private JLabel urlLabel;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JLabel configPathLabel;
	
	private JTextField driverField;
	private JTextField urlfField;
	private JTextField userNameField;
	private JTextField passwordField;
	private JTextField configPathField;
	
	private JButton okButton;
	private JButton resetButton;
	private JButton exitButton;
	
	private JSplitPane littleSplitPane;
	private JSplitPane mainSplitPane;
	
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private Preferences pref;
    private JFileChooser fileChooser;
    
    private String path = "conf\\";
    
	public ConfigFrame(JFrame parentFrame) {
		super("首选项");
		this.parentFrame = parentFrame;
		this.initView();
		this.initField();
		this.addComponent();
		this.initListener();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			DataBaseConfiguration baseConfiguration = new DataBaseConfiguration();
            baseConfiguration.setDriver(driverField.getText().toString());
            baseConfiguration.setPassword(passwordField.getText().toString());
            baseConfiguration.setUrl(urlfField.getText().toString());
            baseConfiguration.setUsername(userNameField.getText().toString());
            if (Tools.createConfFile(path, baseConfiguration)) {
				JOptionPane.showMessageDialog(null, "保存成功,请退出开始爬取Sina");
				parentFrame.setEnabled(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "保存失败,请退出并联系程序管理员");
				return ; 
			}
		}
		if (e.getSource() == resetButton) {
			this.initField();
		}
		if (e.getSource() == exitButton) {
			parentFrame.setEnabled(true);
			this.dispose();
		}
	}
	
	private void initView() {
		ImageIcon ic=new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("res/label.png")); 
		Image image=ic.getImage();
		setIconImage(image);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true); 
        setSize(500, 350);   
        this.setFocusableWindowState(true);
        this.setFocusable(true);
        setLocationRelativeTo(null);
        
        titleLabel = new JLabel("Preference");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        
        driverLabel = new JLabel("driver");
        urlLabel = new JLabel("url");
    	userNameLabel = new JLabel("username");
    	passwordLabel = new JLabel("password");
    	configPathLabel = new JLabel("配置路径");
    	
    	driverField = new JTextField(30);
    	urlfField = new JTextField(30);
    	userNameField = new JTextField(30);
    	passwordField = new JTextField(30);
    	configPathField = new JTextField(30);
    	
    	String saveFolder = "C:\\";
        pref = Preferences.userRoot().node(
        this.getClass().getName());
        String lastPath = pref.get("lastPath", "");
        if (!lastPath.equals("")) {
        	fileChooser = new JFileChooser(lastPath);
        } else{
        	fileChooser = new JFileChooser(saveFolder);
        }  
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	
    	okButton = new JButton("保存设置");
    	resetButton = new JButton("重置设置");
    	exitButton = new JButton ("退出");
    	
    	littleSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	littleSplitPane.setOpaque(false);
    	littleSplitPane.setEnabled(false);
    	littleSplitPane.setDividerSize(2);
    	
    	mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	mainSplitPane.setOpaque(false);
    	mainSplitPane.setEnabled(false);
    	mainSplitPane.setDividerSize(0);
    	
    	topPanel = new JPanel(new FlowLayout(1, 10, 20));
    	centerPanel = new JPanel(new FlowLayout(1, 10, 10));
    	bottomPanel = new JPanel(new BorderLayout());
	}
	
	private void addComponent() {
		littleSplitPane.setDividerLocation(60);
		this.add(mainSplitPane, BorderLayout.CENTER);
		topPanel.add(titleLabel);
		
		JPanel gridPanel = new JPanel(new GridLayout(5, 1, 5, 15));
		JPanel labelPanel = new JPanel(new GridLayout(5, 1, 5, 10));
		gridPanel.add(driverLabel);
		labelPanel.add(driverField);
		gridPanel.add(urlLabel);
		labelPanel.add(urlfField);
		gridPanel.add(userNameLabel);
		labelPanel.add(userNameField);
		gridPanel.add(passwordLabel);
		labelPanel.add(passwordField);
		gridPanel.add(configPathLabel);
		labelPanel.add(configPathField);
		centerPanel.add(gridPanel);
		centerPanel.add(labelPanel);
		
		littleSplitPane.add(topPanel);
		littleSplitPane.add(centerPanel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(1, 10, 30));
		buttonPanel.add(okButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(exitButton);
		bottomPanel.add(buttonPanel, BorderLayout.CENTER);
		
		mainSplitPane.add(littleSplitPane);
		mainSplitPane.add(bottomPanel);
	}

	private void initField() {
		driverField.setText(Constans.driver);
		urlfField.setText(Constans.url);
		userNameField.setText(Constans.user);
		passwordField.setText(Constans.password);
		configPathField.setText(Constans.buildConfigDir+Constans.buildConfigFileName);
	}

	private void initListener() {
		okButton.addActionListener(this);
		resetButton.addActionListener(this);
		exitButton.addActionListener(this);
		configPathField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int flag = 0;
				try{     
		            flag=fileChooser.showOpenDialog(null);     
		        }    
		        catch(HeadlessException head){     
		             JOptionPane.showMessageDialog(null, "Open File Dialog ERROR!"); 
		             return;
		        }        
		        if(flag==JFileChooser.APPROVE_OPTION){
		            path=fileChooser.getSelectedFile().getPath()+"\\";
		        }    
		        configPathField.setText(path+Constans.buildConfigFileName);
			}
		});
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	super.windowClosing(e);
            	parentFrame.setEnabled(true);
                setVisible(false);
                dispose();
            }
        });
	}
}
