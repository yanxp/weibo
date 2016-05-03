package com.bigdata.locationweibo.ui.muticrawlui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.thread.MutiCrawlerThread;
import com.bigdata.locationweibo.thread.SaveCSVThread;
import com.bigdata.locationweibo.ui.DbOperationFrame;
import com.bigdata.locationweibo.ui.filter.CSVFileFilter;
import com.bigdata.locationweibo.ui.util.GUIPrintStream;

public class MutiControlFrame extends JFrame{

	/**
	 * @author ChenZhiling
	 */
	private static final long serialVersionUID = -2542603205155579115L;
	
	private JFrame fatherFrame;
	
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private JTextField savename;
    private JLabel saveLabel;
    
    private Thread retriveThread;
    private Thread saveCSVThread;
    
    private JButton openButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton operateButton;
    
    private TextArea statusTextArea;
    private GUIPrintStream guiPrint;
    private JButton backButton;
    private JButton exitButton;
    private JButton getCSVButton;
    private JButton stopGetCSVButton;
    
    private MutiCrawlerThread mutiCrawlThread;
    
    private Preferences pref;
    private JFileChooser fileChooser;
    private FileFilter filter = new CSVFileFilter();
    
    public MutiControlFrame(JFrame frame) {
    	this.fatherFrame = frame;
		this.onCreate();
	}
    
    public void onCreate()
    {
        initView();
        addComponent(); 
        initListener(); 
        initThread();
        guiPrint=new GUIPrintStream(System.out,statusTextArea);
        System.setErr(guiPrint);
        System.setOut(guiPrint);
    }
    
    private void initThread() {
    	mutiCrawlThread = new MutiCrawlerThread();
	}

	private void addComponent() {
    	add(topPanel, BorderLayout.NORTH);
    	add(centerPanel, BorderLayout.CENTER);
    	add(bottomPanel, BorderLayout.SOUTH);
    	
    	JPanel ttopPanel = new JPanel(new FlowLayout());
    	ttopPanel.add(openButton);
    	ttopPanel.add(startButton);
    	ttopPanel.add(stopButton);  
    	ttopPanel.add(operateButton);
    	topPanel.add(ttopPanel);

        centerPanel.add(statusTextArea);
        
        JPanel wrapPanel = new JPanel(new FlowLayout(5, 5, 5));
        wrapPanel.add(saveLabel);
        wrapPanel.add(savename);
        wrapPanel.add(getCSVButton);
        wrapPanel.add(stopGetCSVButton);
        wrapPanel.add(backButton);
        wrapPanel.add(exitButton);
        bottomPanel.add(wrapPanel, BorderLayout.EAST);        
	}

	private void initView()
    {
        ImageIcon icon = new ImageIcon(this.getClass()
        		.getClassLoader()
        		.getResource("res/label.png"));
        Image img = icon.getImage();
        setIconImage(img);
        setTitle("位置微博抓取");
        setSize(900, 450);
        requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5,5));
        setLocationRelativeTo(null);
        
        topPanel = new JPanel();
        centerPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        centerPanel.setLayout(new FlowLayout());
        bottomPanel.setLayout(new BorderLayout(20,5));
        
        saveLabel=new JLabel(" 另存为 ");
        stopButton=new JButton("停止抓取");
        stopButton.setEnabled(false);
        openButton=new JButton("打开文件");
        startButton=new JButton("开始抓取");
        operateButton = new JButton("操作数据库");
 
        savename=new JTextField();
        savename.setColumns(20);

        String saveFolder = "C:\\";
        pref = Preferences.userRoot().node(this.getClass().getName());
        String lastPath = pref.get("lastPath", "");
        if (!lastPath.equals("")) {
        	fileChooser = new JFileChooser(lastPath);
        } else{
        	fileChooser = new JFileChooser(saveFolder);
        }  
        fileChooser.setFileFilter(filter);
        
        statusTextArea=new TextArea();
        statusTextArea.setFont(new Font("宋体", Font.PLAIN, 14));
        statusTextArea.setEditable(false);
        statusTextArea.setColumns(105);
        statusTextArea.setRows(20);
        
        getCSVButton = new JButton("获取CSV文件");
        stopGetCSVButton = new JButton("停止获取");
        backButton = new JButton("返回");
        exitButton = new JButton("退出");
        stopGetCSVButton.setEnabled(false);
    }
    private void initListener()
    {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
 
                if (fileChooser.getSelectedFile() == null) {
                	JOptionPane.showMessageDialog(null, "请先选择文件,否则无法抓取");
                	return;
				}
                crawlering();
                mutiCrawlThread.setDir(fileChooser.getSelectedFile().getParent()+"\\");
                mutiCrawlThread.setPathload(fileChooser.getSelectedFile().getName());
                retriveThread = new Thread(mutiCrawlThread);
                retriveThread.start();
            }
            
        });
        
        stopButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
            public void actionPerformed(ActionEvent e) {
            	if(retriveThread != null){
					retriveThread.stop();
				}
                notCrawler();
            }
        });
        
        openButton.addActionListener(new ActionListener() {
            @SuppressWarnings("static-access")
			@Override
            public void actionPerformed(ActionEvent e) {
            	int i = fileChooser.showOpenDialog(null);
            	if(i == fileChooser.APPROVE_OPTION){ 
                    printFilePath();
                }
            }
        });
        
        getCSVButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopGetCSVButton.setEnabled(true);
				if (fileChooser.getSelectedFile() == null) {
                	JOptionPane.showMessageDialog(null, "请先选择文件,否则无法提取对应用户的微博信息");
                	return;
				} else if(savename.getText().equals("")){
                    System.out.println("请先输入要保存的文件名");
                    return;
                }
				
				SaveCSVThread csvThread = new SaveCSVThread(
						fileChooser.getSelectedFile().getParent()+"\\", 
						fileChooser.getSelectedFile().getName(), 
						savename.getText().toString());
				saveCSVThread = new Thread(csvThread);
				saveCSVThread.start();
			}
		});
        stopGetCSVButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (saveCSVThread != null) {
					saveCSVThread.stop();
				}
				stopGetCSVButton.setEnabled(false);
			}
		});
        
        backButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(retriveThread != null){
					retriveThread.stop();
				}
				fatherFrame.setVisible(true);
				dispose();
			}
		});
        
        exitButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(retriveThread != null){
					retriveThread.stop();
				}
				if (saveCSVThread != null) {
					saveCSVThread.stop();
				}
				System.exit(0);
			}
		});
        operateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DbOperationFrame().setVisible(true);
			}
		});
        addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent state) {
				if(state.getNewState() == 1 || state.getNewState() == 7) {
                    return;
                }else if(state.getNewState() == 0) {
                	statusTextArea.setColumns(105);
                    statusTextArea.setRows(20);
                }else if(state.getNewState() == 6) {
                	statusTextArea.setColumns(150);
                    statusTextArea.setRows(36);
                }
			}
		});
        addWindowListener(new WindowAdapter() {
            @SuppressWarnings("deprecation")
			@Override
            public void windowClosing(WindowEvent e) {
            	if(retriveThread != null){
					retriveThread.stop();
				}
				if (saveCSVThread != null) {
					saveCSVThread.stop();
				}
                setVisible(false);
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }
    
    private final void printFilePath()
    {
    	if(fileChooser.getSelectedFile()!=null) {
        	System.out.println("打开文件："+fileChooser.getSelectedFile().getParent()
        			+"\\"
        			+fileChooser.getSelectedFile().getName());
        	pref.put("lastPath",fileChooser.getSelectedFile().getPath());
        	if(!fileChooser.getSelectedFile().getName().endsWith(".csv")){
            	System.err.println("文件格式不正确，请重新选择文件\n");
            	startButton.setEnabled(false);
            }else {
				startCrawler();
				Tools.printStarLine(100);
				String fileName = fileChooser.getSelectedFile().getName();
				savename.setText(fileName.substring(0, fileName.indexOf(".csv"))+"data");
			}
        } 
    }
    
    private void startCrawler(){
    	openButton.setEnabled(true);
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
    }
    
    private void crawlering(){
    	openButton.setEnabled(false);
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
    }
    
    private void notCrawler(){
    	openButton.setEnabled(true);
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
    }
   
}
