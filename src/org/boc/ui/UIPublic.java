package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class UIPublic {
	
	public static JDialog getInputWindow(String title,int w, int h, 
			boolean istop,boolean isUndecorate, boolean isdispose) {		
		return new NormalInputWindow(title,w,h,istop,isUndecorate,isdispose);
	}
	/**
	 * �õ��Զ����С��ͼƬ��ť
	 */
	public static JButton getCustomImageButton(String cmdname, String imganme, 
			String tip,ActionListener listener,int w, int h) {
		// ����ͼƬ
		String imgLocation = "/images/" + imganme + ".gif";
		URL imageURL = UIPublic.class.getResource(imgLocation);
		// ��ʼ�����߰�ť
		JButton button = new JButton();
		// ���ð�ť������
		button.setActionCommand(cmdname);
		// ������ʾ��Ϣ
		button.setToolTipText(tip);
		button.addActionListener(listener);
		if (imageURL != null) { // �ҵ�ͼ��			
			java.awt.Image ii = (new javax.swing.ImageIcon(imageURL)).getImage();
			java.awt.Image tmpImg= ii.getScaledInstance(w,h,java.awt.Image.SCALE_SMOOTH);
			ImageIcon img = new ImageIcon(tmpImg);
			button.setIcon(img);
		} else { // û��ͼ��
			button.setText(cmdname);
		}

		return button;
	}
	
	/**
	 * �õ�ԭʼͼƬ��С�İ�ť
	 * @return
	 */
	public static JButton getInitImageButton(String cmdname, String imganme, 
			String tip,ActionListener listener) {
		// ����ͼƬ
		String imgLocation = "/images/" + imganme + ".gif";
		URL imageURL = UIPublic.class.getResource(imgLocation);
		// ��ʼ�����߰�ť
		JButton button = new JButton();
		// ���ð�ť������
		button.setActionCommand(cmdname);
		// ������ʾ��Ϣ
		button.setToolTipText(tip);
		button.addActionListener(listener);
		if (imageURL != null) { // �ҵ�ͼ��			
			ImageIcon img = new ImageIcon(imageURL);
			button.setIcon(img);
		} else { // û��ͼ��
			button.setText(cmdname);
		}

		return button;
	}
	
	/**
	 * ����һ��������ʽ���ı�
	 * @param text
	 * @return
	 */
	public static JLabel getLinkText(final String text, 
			final Object obj, final Method method, final Object... param) {
		final String PRE="<html><div style='vertical-align:middle;'><font color=blue><u>";
		final String END="</u></font></div></html>";
		JLabel label = new JLabel();
		label.setText(PRE+text+END);
		label.addMouseListener(new MouseAdapter() {			
			public void mouseEntered(MouseEvent e) {
				setText(e,true);
			}
			public void mouseExited(MouseEvent e) {
				setText(e,false);
			}
			public void mouseClicked(MouseEvent e) {
				try{
					method.invoke(obj, param);
				}catch(Exception exception) {
					exception.printStackTrace();
				}
			}
			private void setText(MouseEvent e, boolean b) {
				JLabel o = (JLabel)e.getComponent();			
				o.setText(PRE +(!b?"":"<b>") + text +(!b?"":"</b>")+ END);
			}
		});
		return label;
	}
	
	public static final class NormalInputWindow extends JDialog {
		private MyTextPane text;
		private boolean isdispose;
		private Object cbObject;
		private Method cbMethod;
		private Object[] cbParam;
		public NormalInputWindow(String title, int w, int h, 
				boolean istop,boolean isUndecorate, boolean isdispose) {
			this.isdispose = isdispose;
			Box box = new Box(BoxLayout.Y_AXIS);
	    text = new MyTextPane();        
	    JScrollPane jScrollPane = new JScrollPane(text);        
	    box.add(jScrollPane, BorderLayout.CENTER);   
	    this.getContentPane().add(box,BorderLayout.CENTER);
	    this.setUndecorated(isUndecorate);  
	    this.setSize(w, h);  
	    this.setTitle(title);
	    this.setLocationRelativeTo(null);  
	    this.setAlwaysOnTop(istop);
		}						
		public MyTextPane getTextpane() {
			return text;
		}
		//�ر�ʱҪ�ص��ķ���
		public void setCloseCallback(Object obj, Method method, Object...param) {
			this.cbObject = obj;
			this.cbMethod = method;
			this.cbParam = param;
		}
		protected void processWindowEvent(WindowEvent e) {
			if(e.getID()==WindowEvent.WINDOW_CLOSING){
				JDialog dialog = (JDialog)e.getComponent();
				if(isdispose)
					dialog.dispose();
				else
					dialog.setVisible(false);
				try {
					cbMethod.invoke(cbObject, cbParam);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}			
	}
}
