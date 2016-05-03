package com.bigdata.locationweibo.ui.compnent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageLabel extends JLabel {

/**
	 * 
	 */
	private static final long serialVersionUID = 7477712285034809914L;
	
	public ImageLabel(ImageIcon icon) {
		setSize(icon.getImage().getWidth(null),
			icon.getImage().getHeight(null));
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);
	}

	public void refreshImage(ImageIcon icon){
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);
	}
}