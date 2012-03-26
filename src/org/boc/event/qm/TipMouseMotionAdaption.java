package org.boc.event.qm;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.ui.qm.QiMenFrame;
import org.boc.ui.qm.Tip;

public class TipMouseMotionAdaption extends MouseMotionAdapter{
	private Tip tip;
	private QiMenFrame frame;
	//private MyTextPane textPane;  //��ʾ��Ϣ���ı���
	
	private static JDialog jdTip ;  //��ʾ�Ի���
	private JLabel lblText;  //��ʾ��ʾ���ı���ǩ
	
	private int w; //������ʾ���x����
	private int h; //������ʾ���y����
	
	public static JDialog getTipwindow() {
		return jdTip;
	}
	public TipMouseMotionAdaption(MyTextPane textPane,BasicJPanel bframe) {
		this.frame = (QiMenFrame)bframe;
		tip = new Tip(textPane, frame);
		w = 30;
		h = 135;
				
		lblText = new JLabel();
		lblText.setBorder(BorderFactory.createEtchedBorder());
		jdTip = new JDialog();
		jdTip.setUndecorated(true);
		jdTip.add(lblText);	
		jdTip.addMouseListener(new MouseListener() {			
			public void mouseReleased(MouseEvent e) {				
			}			
			public void mousePressed(MouseEvent e) {				
			}			
			public void mouseExited(MouseEvent e) {				
			}			
			public void mouseEntered(MouseEvent e) {
				((JDialog)e.getComponent()).dispose();
			}			
			public void mouseClicked(MouseEvent e) {
				((JDialog)e.getComponent()).dispose();
			}
		});
	}
	
	
	
	//����ƶ��¼�������ʾ
	public void mouseMoved(MouseEvent e) {
		int x=e.getX(), y = e.getY();
		if(x>=89 && x<=199 && y>=21 && y<=32) {
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			return;
		}
		
		String text = tip.getToolTip(e);
		if (text != null) {
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			//textPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			//textPane.setToolTipText(text);
			//ToolTipManager.sharedInstance().setDismissDelay(600000);// ����Ϊ5��			
			mytooltip(e.getPoint(), text);
		} else {
			jdTip.dispose();		 //������ʾ
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			//textPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			//textPane.setToolTipText(null);			
		}
	}
	/**
	 * ����һ����ʾ�ĶԻ��������ʾ
	 * pΪ��ǰ����λ�ã�textΪҪ��ʾ���ı�
	 * @param p
	 * @param text
	 */
	public void mytooltip(Point p, String text) {		
		lblText.setText(text);
		jdTip.pack();  //������ʾ
		//System.out.println("w="+w+":h="+h);
		jdTip.setLocation(p.x+w,p.y+h);  //��ʾλ��
		jdTip.setAlwaysOnTop(true); //����ǰ����ʾ
		jdTip.setVisible(true);
	}
	
	@Override
	protected void finalize() throws Throwable {		
		super.finalize();
		jdTip = null;
		lblText = null;
		tip = null;
	}
}
