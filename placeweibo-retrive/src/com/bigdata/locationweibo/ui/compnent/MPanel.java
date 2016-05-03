package com.bigdata.locationweibo.ui.compnent;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MPanel extends JPanel{

	ImageIcon imgIcon;
	Image img;
	
	public MPanel(ImageIcon imgIcon){
		this.imgIcon=imgIcon;
		img=imgIcon.getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
}

