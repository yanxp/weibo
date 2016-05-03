package com.bigdata.locationweibo.ui.compnent;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5977105810403992864L;
	
	private Container c=null;
	private JPanel panel=null;
	private JButton sureButton,refuseButton;
	private JLabel info=null;
	public boolean yes=false;
	
	public MDialog(Frame frame, String dialog){
		super(frame,true);
		c=getContentPane();
		ImageIcon backic=new ImageIcon(this.getClass()
				.getClassLoader()
				.getResource("res/dialogback.jpg"));
		panel=new MPanel(backic);
		setLayout(new BorderLayout());
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		c.add(panel,BorderLayout.CENTER);
		panel.setLayout(null);
		
		info=new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("res/label.png")));
		info.setBounds( 45, 40, 200, 25);
		info.setText(dialog);
		sureButton=new JButton("确定");
		refuseButton=new JButton("取消");
		sureButton.setFont(new Font("楷体",Font.PLAIN,14));
		refuseButton.setFont(new Font("楷体",Font.PLAIN,14));
		sureButton.setBounds(70, 100, 65, 30);
		refuseButton.setBounds(150, 100, 65, 30);
		panel.add(info);
		panel.add(sureButton);
		panel.add(refuseButton);
		
		sureButton.addActionListener(this);
		refuseButton.addActionListener(this);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sureButton){
			yes=true;
			dispose();
		}
		else if(e.getSource()==refuseButton){
			dispose();
		}
	}
}
