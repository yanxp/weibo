package com.bigdata.locationweibo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class BlogDataTableFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -139232850717826719L;

	private Vector<String> title=new Vector<String>();
	{
		title.add("微博编号");title.add("微博内容");title.add("创建时间");
		title.add("地理url");title.add("微博mid");
	}
	private Vector<Vector<String>> blogData;
	private DefaultTableModel tableModel=null;
	private JTable blogTable=null;
	private JScrollPane tableScroll=null;
	
	public BlogDataTableFrame() {
		super("管理员");
		onCreate();
	}
	
	public void onCreate(){
		initView();
	}
	
	@SuppressWarnings("serial")
	private void initTable() {
		blogData = new Vector<Vector<String>>();
		tableModel=new DefaultTableModel(blogData,title){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		blogTable = new JTable(tableModel){
			@Override
			public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
				DefaultTableCellRenderer cr
				=(DefaultTableCellRenderer)super.getDefaultRenderer(columnClass);
				cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
				cr.setOpaque(false);
				return cr;
			}
			@Override
			public JTableHeader getTableHeader() {
				JTableHeader header=super.getTableHeader();
				header.setReorderingAllowed(false);
				DefaultTableCellRenderer cr
				=(DefaultTableCellRenderer)header.getDefaultRenderer();
				cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
				header.setFont(new Font("楷体",Font.PLAIN,14));
				header.setBackground(Color.white);
				header.setEnabled(false);
				return header;
			}
		};
		blogTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		blogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		blogTable.setBackground(Color.white);
		blogTable.setSelectionForeground(Color.red);
		blogTable.setRowHeight(20);
		blogTable.setFont(new Font("宋体",Font.PLAIN,12));
		tableScroll=new JScrollPane();
		tableScroll.setViewportView(blogTable);
	}

	private void initView() {
		ImageIcon icon = new ImageIcon(this.getClass()
        		.getClassLoader()
        		.getResource("res/label.png"));
        Image img = icon.getImage();
        setIconImage(img);
        setSize(1000, 500);
        requestFocus();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width-1000)/2, (dimension.height-600)/2);
        setLayout(new BorderLayout(5, 5));
       
        initTable();
        add(tableScroll, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
	}
	
	public static void main(String[] args) {
		new BlogDataTableFrame().setVisible(true);
	}
}
