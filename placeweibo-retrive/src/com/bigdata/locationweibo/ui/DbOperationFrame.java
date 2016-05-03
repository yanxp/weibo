package com.bigdata.locationweibo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.bigdata.common.util.Constans;
import com.bigdata.common.util.Tools;
import com.bigdata.locationweibo.persistent.dao.MblogJsonDAO;
import com.bigdata.locationweibo.ui.compnent.MDialog;

public class DbOperationFrame extends JFrame implements ActionListener{

	/**
	 * @author Chen Zhiling
	 */
	private static final long serialVersionUID = -139232850717826719L;
	
	private JSplitPane splitPane;
	
	private JPanel topPanel;
	private JButton searchButton;
	private JButton clearButton;
	private JButton deleteButton;
	private JButton exitButton;
	private JButton generateButton;
	private JRadioButton deleteRadioButton;
	private JRadioButton simpleRadioButton;
	private JRadioButton complexRadioButton;
	private JRadioButton generateRadioButton;
	private JComboBox<String> blogBox;
	private String[] boxinfo={"placeUrl","mid"};
	private JTextField delPlaceUrlTextField;
	private JTextField simpleTextField;
	private JTextField midTextField;
	private JTextField placeUrlTextField;
	private JTextField apiPlaceUrlField;
	
	private JPanel bottomPanel;
	private JScrollPane scrollPane;
	private JTextArea textArea;

	public DbOperationFrame() {
		super("管理员");
		onCreate();
	}

	private void onCreate() {
		initView();
		initListener();
	}
	
	private void initView() {
		ImageIcon icon = new ImageIcon(this.getClass()
        		.getClassLoader()
        		.getResource("res/label.png"));
        Image img = icon.getImage();
        setIconImage(img);
        setSize(800, 550);
        requestFocus();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width-1000)/2, (dimension.height-600)/2);
        setLayout(new BorderLayout(5, 5));
        
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	splitPane.setOpaque(false);
    	splitPane.setEnabled(true);
    	splitPane.setDividerSize(2);
    	splitPane.setDividerLocation(320);
    	this.add(splitPane, BorderLayout.CENTER);
        
        topPanel = new JPanel();
        topPanel.setLayout(null);
        splitPane.add(topPanel);
        
        deleteRadioButton = new JRadioButton("删除记录");
        simpleRadioButton = new JRadioButton("简单查询");
        complexRadioButton = new JRadioButton("高级查询");
        generateRadioButton = new JRadioButton("生成接口");
        deleteRadioButton.setFont(new Font("楷体",Font.PLAIN,14));
		simpleRadioButton.setFont(new Font("楷体",Font.PLAIN,14));
		complexRadioButton.setFont(new Font("楷体",Font.PLAIN,14));
		generateRadioButton.setFont(new Font("楷体",Font.PLAIN,14));
		deleteRadioButton.setBounds(50, 20, 100, 25);
		simpleRadioButton.setBounds(50, 70, 100, 25);
		complexRadioButton.setBounds(50, 120, 100, 25);
		generateRadioButton.setBounds(50, 270, 100, 25);
		ButtonGroup delButtonGroup = new ButtonGroup();
		delButtonGroup.add(deleteRadioButton);
		ButtonGroup searchButtonGroup = new ButtonGroup();
		searchButtonGroup.add(simpleRadioButton);
		searchButtonGroup.add(complexRadioButton);
		deleteRadioButton.setSelected(true);
		simpleRadioButton.setSelected(true);
		generateRadioButton.setSelected(true);
		
		topPanel.add(deleteRadioButton);
		topPanel.add(simpleRadioButton);
		topPanel.add(complexRadioButton);
		topPanel.add(generateRadioButton);
		
		//delete
		JLabel delPlaceUrlLabel = new JLabel("地理Url");
		delPlaceUrlLabel.setFont(new Font("楷体",Font.PLAIN,14));
		delPlaceUrlLabel.setBounds(180, 20, 50, 25);
		delPlaceUrlTextField = new JTextField();
		delPlaceUrlTextField.setBounds(270, 20, 380, 25);
		deleteButton = new JButton("删除");
		deleteButton.setBounds(700, 18, 65, 30);
		deleteButton.setFont(new Font("楷体",Font.PLAIN,14));
		topPanel.add(delPlaceUrlLabel);
		topPanel.add(delPlaceUrlTextField);
		topPanel.add(deleteButton);
		
		//simpleSearch
		blogBox=new JComboBox<String>(boxinfo);
		simpleTextField=new JTextField();
		blogBox.setBounds(180, 70, 100, 25);
		simpleTextField.setBounds(350, 70, 380, 25);
		blogBox.setForeground(Color.black);
		blogBox.setBackground(Color.white);
		topPanel.add(blogBox);
		topPanel.add(simpleTextField);
		//complexSearch
		JLabel midLabel = new JLabel("mid");
		JLabel placeLabel = new JLabel("placeUrl");
		midLabel.setBounds(180, 120, 50, 25);
		placeLabel.setBounds(180, 170, 50, 25);
		topPanel.add(midLabel);
		topPanel.add(placeLabel);
		midTextField = new JTextField();
		placeUrlTextField = new JTextField();
		midTextField.setBounds(270, 120, 460, 25);
		placeUrlTextField.setBounds(270, 170, 460, 25);
		topPanel.add(midTextField);
		topPanel.add(placeUrlTextField);
		//searchButton
		searchButton = new JButton("查询");
		clearButton = new JButton("重置");
		searchButton.setBounds(550, 220, 65, 30);
		clearButton.setBounds(650, 220, 65, 30);
		searchButton.setFont(new Font("楷体",Font.PLAIN,14));
		clearButton.setFont(new Font("楷体",Font.PLAIN,14));
		topPanel.add(searchButton);
		topPanel.add(clearButton);
		//generate
		generateButton = new JButton("生成");
		generateButton.setFont(new Font("楷体",Font.PLAIN,14));
		generateButton.setBounds(550, 270, 65, 30);
		topPanel.add(generateButton);
		apiPlaceUrlField = new JTextField();
		apiPlaceUrlField.setBounds(180, 270, 300, 25);
		apiPlaceUrlField.setText(Constans.norUrl);
		topPanel.add(apiPlaceUrlField);
		//exitButton
		exitButton = new JButton("返回");
		exitButton.setFont(new Font("楷体",Font.PLAIN,14));
		exitButton.setBounds(650, 270, 65, 30);
		topPanel.add(exitButton);
		
		//bottomPanel
		bottomPanel = new JPanel(new GridLayout(1, 2, 20, 20));
		textArea = new JTextArea(100, 20);
		textArea.setFont(new Font("宋体", Font.PLAIN, 13));
		textArea.setColumns(100);
		textArea.setRows(20);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		bottomPanel.add(scrollPane);
		splitPane.add(bottomPanel);
	}
	
	private void initListener() {
		generateRadioButton.addActionListener(this);
		deleteButton.addActionListener(this);
		searchButton.addActionListener(this);
		clearButton.addActionListener(this);
		generateButton.addActionListener(this);
		exitButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteButton) {
			String placeUrl = delPlaceUrlTextField.getText().toString();
			if (placeUrl != null && !placeUrl.equals("")) {
				MDialog mDialog = new MDialog(this, "确定输入的Url无误且删除？");
				if (mDialog.yes) {				
					int count = getRecodeCounts(placeUrl);
					if (count < 0) {
						JOptionPane.showMessageDialog(null, "查询失败", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (count > 0) {
						if (deleteBlogData(placeUrl)) {
							JOptionPane.showMessageDialog(null, "删除成功");
						} else{
							JOptionPane.showMessageDialog(null, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					textArea.setText("DELETE from mblog_json where mblog_json.place_url = "
							+ "'"+placeUrl+"';\n受影响的行: "+count);
				}
			} else{
				JOptionPane.showMessageDialog(null, "请输入PlaceUrl/(ㄒoㄒ)/");
			}
		}
		if (e.getSource() == searchButton) {
			JOptionPane.showMessageDialog(null, "功能仍然在开发中,敬请期待");
		}
		if (e.getSource() == clearButton) {
			if (simpleRadioButton.isSelected()) {
				simpleTextField.setText("");
			} else{
				placeUrlTextField.setText("");
				midTextField.setText("");
			}
		}
		if (e.getSource() == generateButton) {
			String url = apiPlaceUrlField.getText().toString();
			if (url != null && !url.equals("")) {
				textArea.setText(textArea.getText().toString()+Tools.wrapUrl(url)+"2\n");
			} else {
				JOptionPane.showMessageDialog(null, "请输入PlaceUrl/(ㄒoㄒ)/");
			}
		}
		if (e.getSource() == exitButton) {
			this.dispose();
		}
		if (e.getSource() == generateRadioButton) {
			if (generateRadioButton.isSelected()) {
				apiPlaceUrlField.setText(Constans.norUrl);
			} else{
				apiPlaceUrlField.setText(Constans.encUrl);
			}
		}
	}

	/**
	 * @param placeUrl
	 * @return
	 */
	private int getRecodeCounts(String placeUrl) {
		MblogJsonDAO mblogJsonDAO = null;
		int count = 0;
		try {
			mblogJsonDAO = new MblogJsonDAO();
			count = mblogJsonDAO.countByPlaceUrl(placeUrl);
		} catch (Exception e) {
			textArea.setText("DboperationFrame:deleteBlogData:\n"+e.toString());
			return -1;
		} finally{
			if (mblogJsonDAO != null) {
				mblogJsonDAO.closeConnection();
			}
		}
		return count;
	}

	/**
	 * @param placeUrl
	 * @return
	 */
	private boolean deleteBlogData(String placeUrl) {
		MblogJsonDAO mblogJsonDAO = null;
		try {
			mblogJsonDAO = new MblogJsonDAO();
			if (!mblogJsonDAO.delete(placeUrl)) {
				return false;
			}
		} catch (Exception e) {
			textArea.setText("DboperationFrame:deleteBlogData:\n"+e.toString());
			return false;
		} finally{
			if (mblogJsonDAO != null) {
				mblogJsonDAO.closeConnection();
			}
		}
		return true;
	}
}
