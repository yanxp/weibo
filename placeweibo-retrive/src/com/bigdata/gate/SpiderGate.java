package com.bigdata.gate;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.bigdata.locationweibo.ui.LoginFrame;

/**
 * generate on 2015-09-05 12:47
 * @author CHEN ZHILING
 *
 */
public class SpiderGate {

	/**
	 * the gate of the program
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new LoginFrame().setVisible(true);
	}
}
