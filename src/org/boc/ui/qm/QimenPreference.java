package org.boc.ui.qm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.boc.db.qm.QiMen2;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.xml.XmlProc;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * RB: �����ǲ𲹣�Ĭ���ǲ𲹷�Ϊfalse
 * XMHW: 0�������ţ�1.������
 * �������ѡ�������ĸ� XmlProc::loadFromOnFile()����
 */
public class QimenPreference extends JDialog {
	String[] sgroups = {"ma","zf", "jxge","rb","sgky", "td","wsxq", "kg", 
			"xchm",	"tip", "jtxms", "xmhw", "huo", "tool", "sj",  "input", "head", 
			"io", "left","calendar","yang"};
	String[] groupName = {"��������","ת�̷���","�����׸�","�����","ʮ�ɿ�Ӧ","  Сֵ��","��˥����","�й��Ĺ�",
												"�̳��Ĺ","��ʾ��Ϣ",	"��������","����˳��","�������","  ������","ʮ����","�߼����",
												"���ͷ��","���ö���","  Ŀ¼��","ʱ�����","��������"};
	String[][] radioName = new String[][]{
			{"��ʾ","����"},{"ת��","����"},{"��ʾ","����"},{"��","����"},
			{"��ʾ","����"},{"����","����"},{"��ʾ","����"},{"����","�޹�"},
			{"��ʾ","����"},{"��","�ر�"},{"��ʾ","����"},{"����","����"},
			{"��ʾ","����"},{"��ʾ","����"},{"��ʾ","����"},{"��ʾ","����"},
			{"��ʾ","����"},{"����","����"},{"��ʾ","����"},{"��ʾ","����"},
			{"����","����"}};
	boolean[] bgroups = {QiMen2.MA,QiMen2.ZF,QiMen2.JXGE,!QiMen2.RB,QiMen2.SGKY,QiMen2.TD,QiMen2.WSXQ,QiMen2.KG,
			QiMen2.XCHM,QiMen2.TIP,QiMen2.JTXMS,QiMen2.XMHW==0,QiMen2.HUO,QiMen2.TOOL,QiMen2.SJ,QiMen2.INPUT,
			QiMen2.HEAD,QiMen2.IO,QiMen2.LEFT>10,QiMen2.CALENDAR,QiMen2.YANG};
	
	ButtonGroup[] groups = new ButtonGroup[sgroups.length];
	
	public QimenPreference() {
		Box box1 = new Box(BoxLayout.Y_AXIS);
		Box box2 = new Box(BoxLayout.X_AXIS);
		for(int i=0; i<groups.length; i++) {
			groups[i] = new ButtonGroup();
			box2.add(create(groupName[i], radioName[i][0],radioName[i][1], groups[i], bgroups[i]));
			
			if(i%2==1) {
				box2.setAlignmentX(LEFT_ALIGNMENT);				
				box1.add(box2, BorderLayout.CENTER);
				box2 = new Box(BoxLayout.X_AXIS);
			}else{
				box2.add(new JLabel("      |      "));
				box2.setAlignmentX(LEFT_ALIGNMENT);		
				if(i==groups.length-1) box1.add(box2, BorderLayout.CENTER);
			}			
		}
		
		Box box4 = new Box(BoxLayout.X_AXIS);
		JButton save = new JButton("ȷ��");
		save.addActionListener(new ActionListener() {				
			public void actionPerformed(ActionEvent e) {
				savePref();
			}
		});
		box4.add(save);
		
		Box box = new Box(BoxLayout.Y_AXIS);
		box1.setAlignmentX(CENTER_ALIGNMENT);
		box.add(box1,BorderLayout.CENTER);
		box.add(box4,BorderLayout.CENTER);

    this.getContentPane().add(box,BorderLayout.CENTER);
    this.setUndecorated(true);  
    this.setSize(600, 400);  
    this.setLocationRelativeTo(null);  
    this.setAlwaysOnTop(true);
    //this.setVisible(true);
    this.pack();
	}
	
	private File getFile() {
  	return new File(Public.QMQDSZ);
  }
	
	private void savePref(){
		XmlProc xml = XmlProc.getInstance();
		try {
			Document doc = xml.createNewDocument();
			Element root = doc.createElement("root");
			for(int i=0; i<groups.length; i++) {
				String val = groups[i].getSelection().getActionCommand().equals("show")?"1":"0";
				Element e = xml.createElement(doc, root,sgroups[i]);
				e.setTextContent(val);
			}
			doc.appendChild(root);
			xml.save2File(doc, getFile());
		} catch (Exception e) {
			e.printStackTrace();
			this.setVisible(false);
			Messages.error(e.toString());			
			this.setVisible(true);
		}
		this.setVisible(false);
	}
	
	private Box create(String g1,String n1, String n2,  ButtonGroup group, boolean b) {
		String v1="show", v2="hide";
		return create(g1,n1,n2,v1,v2,group,b);
	}
	private Box create(String g1,  String n1, String n2, String v1, String v2, ButtonGroup group, boolean b) {
		Box box = new Box(BoxLayout.X_AXIS);
		JRadioButton r1 = new JRadioButton(n1, b);
		JRadioButton r2 = new JRadioButton(n2, !b); //�� ��Ů
		r1.setActionCommand(v1);
    r2.setActionCommand(v2);
    box.add(new JLabel(g1+"��"));  //"�Ա�"
    group.add(r1);
    group.add(r2);  
    box.add(r1);
    box.add(r2);     
    box.setAlignmentX(LEFT_ALIGNMENT);
    return box;
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		new QimenPreference().setVisible(true);
	}
}
