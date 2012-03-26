package org.boc.ui.qm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Document;

import org.boc.ui.MyTextPane;
import org.boc.ui.PopPanel;
import org.boc.util.PrintWriter;
import org.boc.util.Public;

public class AboutFrame extends JDialog{
	private final int w = 460, h = 290;
	private static AboutFrame about;
	private PopPanel panel;
	private int code = 0;  //0Ϊ�˳�������Ϊ����
	PrintWriter pw = new PrintWriter();
	
	public static void show(int code) {
		if(about==null){
			about = new AboutFrame();
		}

		about.code = code;				
		about.setAlwaysOnTop(true);
		about.setVisible(true); 
	}

	/**
	 * ʼ��Ԥ��רҵ��
	 *   ������ѧ�Ļ����ٽ������г��
	 * ����֧�֣�
	 *   ����=http://blog.sina.com.cn/u/2479027277
	 *   QQ=52288572; EMAIL=vandh@163.com; Tel=18975642299
	 * �ʽ������
	 *   �����������У��ʻ���6225880118535856�����������Ⱥ�;
	 */
	private AboutFrame() {				
		JPanel pane = new JPanel();
		JLabel f2;
		Box box = new Box(BoxLayout.Y_AXIS);
		
		JLabel newline = new JLabel("   ");
		newline.setFont(new Font("����",Font.PLAIN,6));
		
		JLabel t1 = new JLabel("  ʼ��Ԥ��רҵ��:");
		t1.setForeground(Color.BLUE);
		t1.setFont(new Font("����",Font.BOLD,18)); //��������
		box.add(new JLabel("   "));
		box.add(t1);      
    JLabel t2 = new JLabel("        ������ѧ�Ļ����ٽ������г��");
    t2.setForeground(Color.BLACK);
    t2.setFont(new Font("����",Font.BOLD,14)); //��������
    box.add(newline);
    box.add(t2); 
    
    t2 = new JLabel("        ������ѣ��Ͻ�������ҵ��;��");
    t2.setForeground(Color.BLACK);
    t2.setFont(new Font("����",Font.BOLD,14)); //��������
    box.add(newline);
    box.add(t2);
    
    t1 = new JLabel("  ����֧�֣�");
		t1.setForeground(Color.BLUE);
		t1.setFont(new Font("����",Font.BOLD,18)); //��������
		box.add(new JLabel("   "));
		box.add(t1);      
    f2 = new JLabel("        ����=http://blog.sina.com.cn/u/2479027277");
    f2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    //f2.addMouseListener(copy);
    f2.setForeground(Color.BLACK);
    f2.setFont(new Font("����",Font.BOLD,14)); //��������
    box.add(newline);
    box.add(f2); 
    f2 = new JLabel("        QQȺ:218536197 mail:vandh@163.com");
    f2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    //f2.addMouseListener(copy);
    f2.setForeground(Color.BLACK);
    f2.setFont(new Font("����",Font.BOLD,14)); //��������
    box.add(newline);
    box.add(f2); 
    
    t1 = new JLabel("  �ʽ������");
		t1.setForeground(Color.BLUE);
		t1.setFont(new Font("����",Font.BOLD,18)); //��������
		box.add(new JLabel("   "));
		box.add(t1);      
		f2 = new JLabel("        ������������  �ʻ���6225880118535856 ���������Ⱥ� ");
		f2.setBorder(BorderFactory.createEmptyBorder());
		f2.setBackground(Color.CYAN);
		//f2.addMouseListener(copy);
		f2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		f2.setForeground(Color.BLACK);
		f2.setFont(new Font("����",Font.BOLD,14)); //��������
    box.add(newline);
    box.add(f2); 
    
    JButton b2 = new JButton("��������");
    b2.setForeground(Color.RED);
    b2.setFont(new Font("����",Font.BOLD,20)); //��������
    b2.setAlignmentX(CENTER_ALIGNMENT);
    b2.addMouseListener(new MyMouseAdapter());
    box.add(new JLabel("   "));
    
    pane.setEnabled(true);
    pane.add(box,BorderLayout.NORTH);
    pane.add(b2,BorderLayout.CENTER);
    this.setContentPane(pane);    
    this.setTitle(Public.title); 
    this.setSize(w,h);      
    //this.setModal(false);
    this.setLocationRelativeTo(null);  
    this.setResizable(false);        
    //this.setUndecorated(true);
    //this.setDefaultLookAndFeelDecorated(true);
	}
	
	class MyMouseAdapter extends MouseAdapter {
  	public void mouseClicked(MouseEvent e) {
  		if(panel==null) 
  			newList();
  		about.setVisible(false);  	
  		panel.setVisible(true);
  	}
	}
	
	private void newList() {
		panel = new PopPanel(QimenPopupMenu.WIDTH1,QimenPopupMenu.HEIGHT1, about);
		panel.setTitle("�����б�");
		MyTextPane text = panel.getTextpane();		
		Document doc = text.getDocument();
		pw.setDocument(doc);
		try{
  		pw.bred("                 �����б�");
  		pw.newLine();
  		pw.newLine();
  		pw.print("  �����У� ������������",PrintWriter.SBLUE,false);
  		pw.print("         �ʻ���6225880118535856",PrintWriter.SBLUE,false);
  		pw.println("          ���������Ⱥ�",PrintWriter.SBLUE,false);
  		pw.println("  ���͵�ַ�� http://blog.sina.com.cn/u/2479027277",PrintWriter.SBLUE,false);
  		pw.print("  QQ�� 52288572",PrintWriter.SBLUE,false);
  		pw.print("           mail=vandh@163.com",PrintWriter.SBLUE,false);
  		pw.print("           Tel=18975642299",PrintWriter.SBLUE,false);
  		pw.newLine();
  		pw.newLine();
  		pw.sred("   ------------------------------------------------------------------------------------");
  		pw.newLine();
  		pw.mblue("  ������λ�����  ����ʱ��                 ��������         ");
  		pw.newLine();
  		pw.sred("   ------------------------------------------------------------------------------------");
  		pw.newLine();
  		pw.sred("    Phoebe        2011             ���ָ�ʽ���������õ���ཨ�飬�����ṩ��������Եȣ�");
  		pw.newLine();
  		pw.sred("    Ŀ����        2012             ���ָ�ʽ���������õ���ཨ�飬����¼�뼰�ṩ��������Եȣ�");
  		pw.newLine();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		text.roll20();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); 
		//panel.setUndecorated(true);
	}
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			if(about.code==0) System.exit(0);			
			else {
				JDialog d = (JDialog)e.getComponent();
				d.setVisible(false);
			}
		}
	}
}
