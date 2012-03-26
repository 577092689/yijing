package org.boc.event.ly;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import org.boc.calendar.ui.CalendarForm;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.ly.Liuyao;
import org.boc.ui.MyTextPane;
import org.boc.ui.PopPanel;
import org.boc.ui.ly.LiuYaoFrame;
import org.boc.ui.ly.LiuyaoPopupMenu;
import org.boc.ui.ly.LyCalendarForm;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.PrintWriter;

public class LyClickListener implements MouseListener{
	private LiuYaoFrame frame;
	private PrintWriter pw ;
	private LiuyaoPublic pub;
	private DaoYiJingMain daoly;	
	
	public static final int w0 = 633;  //��ʼ��ȣ�����
	public static final int h0 = 140;  //��ʼ�߶ȣ���ͷ
	public static final int y_nohead = 30;   //��ͷ��ʱ�ģ�y����30
	private static PopPanel pop ;					//˫������ĵ�����
	private static PopPanel pop2;					//˫���������ˮ��ɫ��������ʾ������
	private static LyCalendarForm calendar;	//˫����������������
	private StyledDocument styledDoc;
	private MyTextPane textPane;
	private String bigFont ;	//�����壬Ϊ���š����ǡ��������
	private int pinkGong;			//ˮ��ɫ���壬Ϊ�������
	private HtmlMultiLineControl html = new HtmlMultiLineControl();
	private final String NLINE =  "\r\n           ";
	private final String NLINE2 = "\r\n                 ";
	private final String NLINE3 =  "\r\n";
	
	public static PopPanel getPop() {
		return pop;
	}
	public static PopPanel getPop2() {
		return pop2;
	}
	public static LyCalendarForm getCalendarForm() {
		return calendar;
	}
	
	public LyClickListener(JPanel frame2) {
		this.frame = (LiuYaoFrame)frame2;
		pub = frame.getDelYiJingMain().getLiuyaoPublic();
		daoly = pub.getDaoYiJingMain();
		
		pw = new PrintWriter();
		pop = new PopPanel(LiuyaoPopupMenu.WIDTH1+175-Liuyao.LEFT, LiuyaoPopupMenu.HEIGHT1-97);
		pop.setLocation(w0 + Liuyao.LEFT, h0 + (Liuyao.HEAD ? y_nohead : 0));  
		pop.setUndecorated(true);  		
		pop2 = new PopPanel();		
		pop2.setLocation(w0 + Liuyao.LEFT, h0 + (Liuyao.HEAD ? y_nohead : 0));
		pop2.setUndecorated(true);
		
		calendar = new LyCalendarForm();
//		calendar.setVisible(true);
		calendar.setParent(frame);
//		calendar.setAlwaysOnTop(true);		
//		calendar.setLocation(635+Liuyao.LEFT,170);
//		calendar.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	}
	
//	public void click1(MouseEvent e) {
//	//89,21~199,32֮��˫�����򵯳����ڿ�
//		
//	}
	
	public void click2(MouseEvent e) {
		int x=e.getX(), y = e.getY();
		//1. ˫����ʾ��ʱ�䣬�����������ر�
		if(x>=89 && x<=199 && y>=21 && y<=32) {		
			if(calendar.isVisible()) calendar.setVisible(false);
			else {								
				//TipMouseMotionAdaption.getTipwindow().setVisible(false);
				frame.update2();  //����ʱ����壬trueΪ��ʾ
			}
			if(pop.isVisible())	pop.setVisible(false);
			if(pop2.isVisible())	pop2.setVisible(false);
			return;
		}
		
		//2. ˫���˴������ˮ��ɫ���壬��������ǣ���ر�
		if(textPane==null) textPane = frame.getResultPane().getTextPane();
		int pos = textPane.viewToModel(e.getPoint());	
		if(isBigfont(e , pos)) { 
			//System.out.println(bigFont);
			if(pop.isVisible()) pop.setVisible(false);
			showPop2(getDesc(bigFont),e);
			return;
		}else if(isPinkfont(pos)) { 
			if(pop.isVisible()) pop.setVisible(false);
			showPop2("",e);			
			return;
		}else{
			if(pop2.isVisible()) pop2.setVisible(false);
		}
		
		//3. ˫��������Ϣ��ʾ��ʾ��pop��������ڹ��ڣ���ر�pop
		this.showPop(e);
	}
	
	/**
	 * �Ƿ�����壬��ʾ���š����ǡ����������Ϣ
	 */
	public boolean isBigfont(MouseEvent e,int pos) {		
		if (styledDoc == null) {
			styledDoc = (StyledDocument) textPane.getDocument();
		}
		((AbstractDocument) styledDoc).readLock();
		AttributeSet as = null;
		try {
			as = styledDoc.getCharacterElement(pos).getAttributes();
		} finally {
			((AbstractDocument) styledDoc).readUnlock();
		}
		Enumeration en = as.getAttributeNames();
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					try {
						bigFont = styledDoc.getText(pos, 1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					return true;
				}
				break;
			}
		}
		return false;
	}
	/**
	 * �Ƿ���ˮ��ɫ���壬��ʾ�Ź���Ϣ
	 */
	public boolean isPinkfont(int pos) {
		
		return false;
	}
	
	public String getDesc(String key) {
		// 1. �Ƿ������̸ɻ���̸�
		
		return null;
	}

	public void mouseClicked(MouseEvent e) {	
		if (e.getClickCount() == 2 ) click2(e);
	}

	private void showPop(MouseEvent e) {			
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		pw.setDocument(doc);		
	}
	
	private void showPop2(String desc, MouseEvent e) {
		if(desc==null) return;
		desc = desc.trim().replaceAll(Liuyao.HH, NLINE3);
		MyTextPane text = pop2.getTextpane();	
		Document doc = text.getDocument();				
		pw.setDocument(doc);
		try {
			doc.remove(0, doc.getLength());
			pw.print(desc,pw.MBLUE,false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		pop2.pack(); 
		pop2.setVisible(true);
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}

