package com.bigdata.locationweibo.ui.listener;

import java.awt.Cursor;
import java.awt.Point;

import javax.swing.JFrame;


/**
 * ���ڵļ������İ�����
 * �Դ���Ĵ��ڽ��������ƶ�����
 * @author Administrator
 *
 */
public class FrameListener {

	private JFrame jFrame;
	
	//����ƶ�����
	private boolean isMoved;  
    private Point pre_point;  
    private Point end_point;  
	
	public FrameListener(JFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	public void addMouseAndMotionListener() {
		//TODO
		jFrame.addMouseListener(new java.awt.event.MouseAdapter() {  
            public void mouseReleased(java.awt.event.MouseEvent e) {  
                isMoved = false;  
                jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  
            }  
  
            public void mousePressed(java.awt.event.MouseEvent e) {  
                isMoved = true;  
                pre_point = new Point(e.getX(), e.getY());  
                jFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));  
            }  
        });  
		//TODO
		jFrame.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {  
            public void mouseDragged(java.awt.event.MouseEvent e) {  
                if (isMoved) {
                    end_point = new Point(jFrame.getLocation().x + e.getX() - pre_point.x,  
                    		jFrame.getLocation().y + e.getY() - pre_point.y);  
                    jFrame.setLocation(end_point);  
                }  
            }  
        });  
		//end
	}
	
}
