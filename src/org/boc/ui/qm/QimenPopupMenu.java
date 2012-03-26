package org.boc.ui.qm;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;
import org.boc.db.qm.QimenCaiyun;
import org.boc.db.qm.QimenGongzuo;
import org.boc.db.qm.QimenHunyin;
import org.boc.db.qm.QimenJibing;
import org.boc.db.qm.QimenJinyun;
import org.boc.db.qm.QimenMingyun;
import org.boc.db.qm.QimenPublic;
import org.boc.db.qm.QimenShidao;
import org.boc.db.qm.QimenXuexi;
import org.boc.delegate.DelQiMenMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.ui.PopPanel;
import org.boc.ui.PublicFoumulas;
import org.boc.util.Messages;
import org.boc.util.PrintWriter;

public class QimenPopupMenu extends JPopupMenu 
	implements MouseListener, ActionListener {
	private PopPanel pop;
	PrintWriter pw;
	QiMenFrame frame;
	QimenPublic qmpub;
	DaoQiMen daoqm;
	DaoSiZhuMain daosz;
	
	JMenu koujueItem; 
	JMenu analyse;
	JMenuItem cutItem, copyItem, pasteItem, deleteItem, selectAllItem, printItem;
	JMenuItem preference;
	JMenuItem swsjb2;  //ʮ�������������
	JMenuItem nsqy2;   //�������·�
	JMenuItem rsqs2;	  //������ʱ��
	JMenuItem yima2; 
	JMenuItem taohua2;
	JMenuItem sgky2;
	JMenuItem jz602;
	JMenuItem jxzs2;
	JMenuItem qmyj2;	
	
	JMenuItem zsmy2;
	JMenuItem jqmy2;
	JMenuItem swbd2;
	JMenuItem qxks2;
	JMenuItem gzjy2;
	JMenuItem jyqc2;
	JMenuItem lahy2;
	JMenuItem rtjb2;
	JMenuItem cxcg2;
	JMenuItem tsqx2;
	JMenuItem dlfs2;
	JMenuItem xrzs2;
	JMenuItem hyfw2;
	JMenuItem tyjs2;
	
	JMenuItem tool2;
	JMenuItem input2;
	
	public void setFrame(BasicJPanel frame) {
		this.frame = (QiMenFrame)frame;
		DelQiMenMain delqm = this.frame.getDelQiMenMain();
		qmpub = delqm.getQimenPublic();
		daoqm = delqm.getDaoQiMen();
		daosz = delqm.getDaoSiZhuMain();
	}
	
	private static QimenPopupMenu sharedInstance = null;
	public static QimenPopupMenu getSharedInstance() { // ����ģʽ
		if (sharedInstance == null) {
			sharedInstance = new QimenPopupMenu();			
		}
		return sharedInstance;
	}
	
	public static void QimenPopupMenu(JTextComponent c) {
		if (c instanceof JTextPane && !(c instanceof JPasswordField)) {
			c.addMouseListener(QimenPopupMenu.getSharedInstance());
		}
	}
	public static void uninstallFromComponent(JTextComponent c) {
		if (c instanceof JTextPane && !(c instanceof JPasswordField)) {
			c.removeMouseListener(getSharedInstance());
		}
	}

	public QimenPopupMenu() {
		pop = new PopPanel();
		pw = new PrintWriter();		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		analyse = new JMenu("��������");
		zsmy2 = new JMenuItem("��������");
		jqmy2 = new JMenuItem("��������");
		swbd2 = new JMenuItem("ʧ�ﱻ��");
		lahy2 = new JMenuItem("��������");
		qxks2 = new JMenuItem("��ѧ����");
		gzjy2 = new JMenuItem("������ҵ");
		jyqc2 = new JMenuItem("��Ӫ���");
		cxcg2 = new JMenuItem("���г���");
		rtjb2 = new JMenuItem("���弲��");
		tsqx2 = new JMenuItem("��ʱ����");
		dlfs2 = new JMenuItem("�����ˮ");
		xrzs2 = new JMenuItem("������ʧ");
		hyfw2 = new JMenuItem("���з���");
		tyjs2 = new JMenuItem("��������");
		analyse.add(zsmy2);
		analyse.add(jqmy2);
		analyse.add(swbd2);
		analyse.add(lahy2);
		analyse.add(qxks2);
		analyse.add(gzjy2);
		analyse.add(jyqc2);
		analyse.add(cxcg2);
		analyse.add(rtjb2);
		analyse.add(tsqx2);
		analyse.add(dlfs2);
		analyse.add(xrzs2);
		analyse.add(hyfw2);
		analyse.add(tyjs2);
		
		koujueItem = new JMenu("���ÿھ�");		
		swsjb2 = new JMenuItem("����������");		
		jz602 = new JMenuItem("��ʮ����������");		
		nsqy2 = new JMenuItem("�������·�");
		rsqs2 = new JMenuItem("������ʱ��");
		qmyj2 = new JMenuItem("�����þֱ�");
		sgky2 = new JMenuItem("ʮ�ɿ�Ӧ��");
		jxzs2 = new JMenuItem("����ֵʱ��Ӧ");  //���ſ�Ӧ�����ǿ�Ӧ���Ÿɿ�Ӧ
		yima2 = new JMenuItem("����");
		taohua2 = new JMenuItem("�һ�");
		
		koujueItem.add(jz602);  //����������
		koujueItem.add(swsjb2);	//����������		
		koujueItem.add(nsqy2);
		koujueItem.add(rsqs2);		
		koujueItem.add(yima2);
		koujueItem.add(taohua2);	
		addSeparator();
		koujueItem.add(qmyj2);
		koujueItem.add(sgky2);
		koujueItem.add(jxzs2);		
		
		add(analyse);		
		add(koujueItem);
		addSeparator();
		
		add(tool2 = new JMenuItem(QiMen2.TOOL?"���ع�����":"��ʾ������ "));
		add(input2 = new JMenuItem(QiMen2.INPUT?"���ظ߼����":"��ʾ�߼���� "));
		add(preference = new JMenuItem("��������"));
		addSeparator();
		
		add(cutItem = new JMenuItem("���� "));
		add(copyItem = new JMenuItem("����"));
		add(pasteItem = new JMenuItem("ճ��"));
		add(deleteItem = new JMenuItem("ɾ��"));
		add(selectAllItem = new JMenuItem("ȫѡ"));
		add(printItem = new JMenuItem("��ӡ   (R)"));

//		cutItem.setMnemonic('T');
//		copyItem.setMnemonic('C');
//		pasteItem.setMnemonic('P');
//		deleteItem.setMnemonic('D');
//		selectAllItem.setMnemonic('A');
//		printItem.setMnemonic('R');

		tool2.addActionListener(this);
		input2.addActionListener(this);
		preference.addActionListener(this);
		
		cutItem.addActionListener(this);
		copyItem.addActionListener(this);
		pasteItem.addActionListener(this);
		deleteItem.addActionListener(this);
		selectAllItem.addActionListener(this);
		printItem.addActionListener(this);
		
		swsjb2.addActionListener(this);
		nsqy2.addActionListener(this);
		rsqs2.addActionListener(this);
		yima2.addActionListener(this);
		taohua2.addActionListener(this);
		sgky2.addActionListener(this);
		jxzs2.addActionListener(this);
		jz602.addActionListener(this);
		qmyj2.addActionListener(this);
		
		zsmy2.addActionListener(this);
		jqmy2.addActionListener(this);
		swbd2.addActionListener(this);
		lahy2.addActionListener(this);
		qxks2.addActionListener(this);
		gzjy2.addActionListener(this);
		jyqc2.addActionListener(this);
		cxcg2.addActionListener(this);
		rtjb2.addActionListener(this);
		tsqx2.addActionListener(this);
		dlfs2.addActionListener(this);
		xrzs2.addActionListener(this);
		hyfw2.addActionListener(this);
		tyjs2.addActionListener(this);
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger() && e.getSource() instanceof JTextPane) { // e.isPopupTrigger()����Ҽ�
			JTextPane textfield = (JTextPane) e.getSource();
			if (Boolean.TRUE.equals(textfield.getClientProperty("DisablePopupMenu"))) {
				return;
			}
			textfield.requestFocusInWindow();
			this.show(textfield, e.getX(), e.getY()); // ��������λ��
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void show(Component invoker, int x, int y) {
		JTextComponent tc = (JTextComponent) invoker;
		String sel = tc.getSelectedText();

		boolean selected = sel != null && !sel.equals("");
		boolean enableAndEditable = tc.isEnabled() && tc.isEditable();
		// ����������
		cutItem.setEnabled(selected && enableAndEditable);
		copyItem.setEnabled(selected && tc.isEnabled());
		deleteItem.setEnabled(selected && enableAndEditable);
		pasteItem.setEnabled(enableAndEditable);
		selectAllItem.setEnabled(tc.isEnabled());

		super.show(invoker, x, y);
	}

	public void actionPerformed(ActionEvent e){
		JTextComponent tc = (JTextComponent) getInvoker();
		String sel = tc.getSelectedText();
		try{
			if (e.getSource() == cutItem) {
				tc.cut();
			} else if (e.getSource() == copyItem) {
				tc.copy();
			} else if (e.getSource() == pasteItem) {
				tc.paste();
			} else if (e.getSource() == selectAllItem) {
				tc.selectAll();
			} else if (e.getSource() == deleteItem) {
				Document doc = tc.getDocument();
				int start = tc.getSelectionStart();
				int end = tc.getSelectionEnd();
	
				try {
					Position p0 = doc.createPosition(start);
					Position p1 = doc.createPosition(end);
	
					if ((p0 != null) && (p1 != null) && (p0.getOffset() != p1.getOffset())) {
						doc.remove(p0.getOffset(), p1.getOffset() - p0.getOffset());
					}
				} catch (BadLocationException be) {
				}
			}	else if (e.getSource() == printItem) {
				try {
					tc.print(null);
				} catch (Exception e1) {
					Messages.info("�ù���Ŀǰ��֧�֣�");
					return;
				}
			} else if (e.getSource() == tool2)	tool();
			else if (e.getSource() == input2)	input();
			else if (e.getSource() == preference)	preference();
			
			else if (e.getSource() == swsjb2)	swsjb();
			else if(e.getSource() == sgky2) sgky();
			else if(e.getSource() == qmyj2) qmyj();
			else if(e.getSource() == jz602) jz60();
			else if(e.getSource() == jxzs2) jxzs();
			else if(e.getSource() == nsqy2)	nsqy();
			else if(e.getSource() == rsqs2)	rsqs();
			else if(e.getSource() == yima2)	yima();
			else if(e.getSource() == taohua2) taohua();		
			
			else {
				if(daoqm.gInt==null || daoqm.gInt[2][3][1]==0) {
					Messages.info("��δ��֣��뵥���������⡱ҳ��ֺ��ٽ��д˲�����");
					return;
				}	
				if(e.getSource() == zsmy2) zsmy();
				else if(e.getSource() == jqmy2) jqmy();
				else if(e.getSource() == swbd2) swbd();
				else if(e.getSource() == lahy2) lahy();
				else if(e.getSource() == qxks2) qxks();
				else if(e.getSource() == gzjy2) gzjy();
				else if(e.getSource() == jyqc2) jyqc();
				else if(e.getSource() == cxcg2) cxcg();
				else if(e.getSource() == rtjb2) rtjb();
				else if(e.getSource() == tsqx2) tsqx();
				else if(e.getSource() == dlfs2) dlfs();
				else if(e.getSource() == xrzs2) xrzs();
				else if(e.getSource() == hyfw2) hyfw();
				else if(e.getSource() == tyjs2) tyjs();
			}
		}
		catch(Exception ee) {
			ee.printStackTrace();
			Messages.error(ee.getMessage());
			return;
		}
	}
	
	//
	private void swsjb() throws Exception{
		pop.setAll("ʮ�������������",WIDTH1-150,HEIGHT1-100);
		MyTextPane text = pop.getTextpane();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printShiTianganSWSJB(pw);
	  
	  text.roll20();
	  pop.setVisible(true);
	}
	
	//�������·�
	private void nsqy() throws Exception {
		pop.setAll("�������·�",360,200);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		pw.setDocument(doc);
		doc.remove(0, doc.getLength());
		PublicFoumulas.printNsqyf(pw);
		
		pop.setVisible(true);
	}
	//ʮ�ɿ�Ӧ
	private void sgky() throws Exception{
		pop.setAll("ʮ�ɿ�Ӧ��",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printSgkyb(pw);
		text.roll20();
		pop.setVisible(true);
	}
	
	private void qmyj() throws Exception{
		pop.setAll("�����þֱ�",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		pw.bblack("                  �����þֱ�");
		pw.newLine();
		pw.newLine();
		
		for(int i=22; i<=33; i++) {
			int k = i%24==0 ? 24 : i%24;
			pw.mred(QiMen.JIEQI24[k]);
			pw.mred("          ");
			pw.mred(QiMen.jieqiyy[k]>0? "����":"����");
			pw.mred("          ");
			for(int j=1; j<=3; j++) {
				pw.mred(Math.abs(QiMen.yydun[k][j])+"  ");
			}
			pw.newLine();
		}
		pw.newLine();
		for(int i=10; i<=21; i++) {
			pw.mblue(QiMen.JIEQI24[i]);
			pw.mblue("          ");
			pw.mblue(QiMen.jieqiyy[i]>0? "����":"����");
			pw.mblue("          ");
			for(int j=1; j<=3; j++) {
				pw.mblue(Math.abs(QiMen.yydun[i][j])+"  ");
			}
			pw.newLine();
		}
		text.roll20();
		pop.setVisible(true);
	}
	private void jz60() throws Exception{
		pop.setAll("��ʮ����������",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printNayin(pw);
		text.roll20();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		pop.setVisible(true);
	}

	//����ֵʱ��Ӧ
	private void jxzs() throws Exception{
		pop.setAll("����ֵʱ��Ӧ��",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		pw.bblack("                 ����ֵʱ��Ӧ��");
		pw.newLine();
		pw.newLine();
		for(int i=1; i<=9; i++) {
			pw.mred("                            ��"+QiMen.jx1[i]+"��");
			pw.newLine();
			for(int j=1; j<=12; j++) {
				pw.sblue("  ֵ"+YiJing.DIZINAME[j]+"ʱ��");
				pw.sblue(QiMen2.JXZS[i][j].replaceAll(QiMen2.HH, ""));
				pw.newLine();
			}
			pw.newLine();
		}
		text.roll20();
		pop.setVisible(true);
	}
	
	//������ʱ��
	private void rsqs() throws Exception{
		pop.setAll("������ʱ��",300,200);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printRsqsf(pw);
	  pop.setVisible(true);
	}
	//����
	private void yima() throws Exception{
		pop.setAll("����",200,250);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printYima(pw);
		pop.setVisible(true);
	}
	//�һ�
	private void taohua()  throws Exception{
		pop.setAll("�һ�",200,260);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		pw.println("   �һ�",PrintWriter.BRED);
		pw.newLine();
		pw.println("��-�ӣ���-�ȣ�",PrintWriter.BBLUE);
		pw.println("��-î����-�ꣻ",PrintWriter.BBLUE);
		pw.println("��-î����-�ꣻ",PrintWriter.BBLUE);
		pw.println("��-�磬��-����",PrintWriter.BBLUE);
		pw.println("��-�ϣ���-����",PrintWriter.BBLUE);
		pop.setVisible(true);
	}
	
	public static final int WIDTH1 = 550;
	public static final int HEIGHT1 = 550;
	//��������
	QimenMingyun mymingyun;
	private void zsmy() throws BadLocationException {
		pop.setAll("��������",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {
			StringBuffer sb = new StringBuffer();			
			if(mymingyun==null) mymingyun = new QimenMingyun(qmpub);
			mymingyun.getLife(sb, frame.getMztext(), frame.getYsnum(), frame.isBoy());
			pw.bred("                 ��������\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ��������\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.ZSMY);
			if(rs==null) return;
			pw.sblue(rs);
		}		
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//��������
	QimenJinyun myjinyun;
	private void jqmy() throws BadLocationException {
		pop.setAll("��������",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();				
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);		
		if(QiMen2.IO) {
			StringBuffer sb = new StringBuffer();			
			if(myjinyun==null) myjinyun = new QimenJinyun(qmpub);
			myjinyun.getNow(sb, frame.getMztext(), frame.getYsnum(), frame.isBoy(), frame.getZhuanFei());
			pw.bred("                 ��������\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ��������\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.JDYQ);
			if(rs==null) return;
			pw.sblue(rs);
		}			
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//ʧ�ﱻ��
	QimenShidao mysd;
	private void swbd() throws BadLocationException {
		pop.setAll("ʧ�ﱻ��",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {		
			if(mysd==null) mysd = new QimenShidao(qmpub);		
			StringBuffer sb = new StringBuffer();
			mysd.getThing(sb, frame.getMztext(), frame.getYsnum(), frame.isBoy());
			pw.bred("                 ʧ�ﱻ��\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ʧ�ﱻ��\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.SWBD);
			if(rs==null) return;
			pw.sblue(rs);
		}
		
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//��������
	QimenHunyin myhunyin;
	private void lahy() throws BadLocationException {	
		pop.setAll("��������",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		if(QiMen2.IO) {		
			if(myhunyin==null) myhunyin = new QimenHunyin(daoqm,qmpub);		
			StringBuffer sb = new StringBuffer();
			myhunyin.getHunyin(sb, frame.getMztext(), frame.getYsnum(), frame.isBoy());
			pw.bred("                 ��������\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ��������\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.LAHY);
			if(rs==null) return;
			pw.sblue(rs);
		}
		
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//��ѧ����
	QimenXuexi myks;
	private void qxks() throws BadLocationException {
		pop.setAll("��ѧ����",500,570);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {		
			if(myks==null) myks = new QimenXuexi(daoqm,qmpub,daosz);
			StringBuffer sb = new StringBuffer();
			myks.getXuexi(sb, frame.getMztext(), frame.getYsnum());
			pw.bred("                 ��ѧ����\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ��ѧ����\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.QXKS);
			if(rs==null) return;
			pw.sblue(rs);
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//������ҵ
	QimenGongzuo gongz;	
	private void gzjy() throws BadLocationException {
		pop.setAll("������ҵ",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {		
			if(gongz==null) gongz = new QimenGongzuo(daoqm,qmpub);
			StringBuffer sb = new StringBuffer();
			gongz.getWork(sb, frame.getMztext(), frame.getYsnum(), frame.isBoy());
			pw.bred("                 ������ҵ\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ������ҵ\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.GZJY);
			if(rs==null) return;
			pw.sblue(rs);
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//��Ӫ���
	QimenCaiyun mycai;
	private void jyqc() throws BadLocationException {
		pop.setAll("��Ӫ���",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {		
			if(mycai==null) mycai = new QimenCaiyun(daoqm,qmpub);
			StringBuffer sb = new StringBuffer();
			mycai.getCaiyun(sb, frame.getMztext(), frame.getYsnum(), frame.isBoy(), frame.getZhuanFei());
			pw.bred("                 ��Ӫ���\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ��Ӫ���\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.JYQC);
			if(rs==null) return;
			pw.sblue(rs);
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//���г���
	private void cxcg() throws BadLocationException {
		pop.setAll("���г���",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {					
			pw.bred("                 ���г���\r\n");
			pw.sblue("����������...");
		}else{
			pw.bred("                 ���г���\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.CXCG);
			if(rs==null) return;
			pw.sblue(rs);
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//���弲��
	QimenJibing myjb;
	private void rtjb() throws BadLocationException {
		pop.setAll("���弲��",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {		
			if(myjb==null) myjb = new QimenJibing(qmpub);
			StringBuffer sb = new StringBuffer();
			myjb.getBing(sb, frame.getMztext(), frame.getYsnum(), frame.isBoy());
			pw.bred("                 ���弲��\r\n");
			pw.sblue(sb.toString());
		}else{
			pw.bred("                 ���弲��\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.RTJB);
			if(rs==null) return;
			pw.sblue(rs);
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//��ʱ����
	private void tsqx() throws BadLocationException {
		pop.setAll("��ʱ����",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {					
			pw.bred("                 ��ʱ����\r\n");
			pw.sblue("����������...");
		}else{
			pw.bred("                 ��ʱ����\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.TSQX);
			if(rs==null) return;
			pw.sblue(rs);
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//�����ˮ
	private void dlfs()  throws BadLocationException {
		pop.setAll("�����ˮ",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {					
			pw.bred("                 �����ˮ\r\n");
			pw.sblue("����������...");
		}else{
			pw.bred("                 �����ˮ\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.DLFS);
			if(rs==null) return;
			pw.sblue(rs);			
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//������ʧ
	private void xrzs()  throws BadLocationException {
		pop.setAll("������ʧ",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {					
			pw.bred("                 ������ʧ\r\n");
			pw.sblue("����������...");
		}else{
			pw.bred("                 ������ʧ\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.XRZS);
			if(rs==null) return;
			pw.sblue(rs);			
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//���з���
	private void hyfw() throws BadLocationException  {
		pop.setAll("���з���",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {					
			pw.bred("                 ���з���\r\n");
			pw.sblue("����������...");
		}else{
			pw.bred("                 ���з���\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.HYFW);
			if(rs==null) return;
			pw.sblue(rs);			
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	//��������
	private void tyjs() throws BadLocationException {
		pop.setAll("��������",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		if(QiMen2.IO) {					
			pw.bred("                 ��������\r\n");
			pw.sblue("����������...");
		}else{
			pw.bred("                 ��������\r\n");
			String rs = frame.getQimenInputPanel().runRule(QiMen.TYJS);
			if(rs==null) return;
			pw.sblue(rs);			
		}
		text.roll20();  //������һ��
		pop.setVisible(true);
	}
	
	//��ʾ�����ع�����
	private void tool() {
		if(QiMen2.TOOL = !QiMen2.TOOL) {
			frame.addTool(frame.getToolbar());
			tool2.setText("���ع�����");
		}else{
			frame.delTool(frame.getToolbar());
			tool2.setText("��ʾ������");
		}
	}
	//��ʾ�����ѡ������
	QimenPreference pref;
	private void preference() {
		if(pref==null) pref = new QimenPreference();
		pref.setVisible(true);
	}
	
	//��ʾ�����ظ߼����
	private void input() {
		if(QiMen2.INPUT = !QiMen2.INPUT) {
			frame.addInput(frame.getInputpanel());
			input2.setText("���ظ߼����");
		}else{
			frame.delInput(frame.getInputpanel());
			input2.setText("��ʾ�߼����");
		}
	}
	
//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // �ʺ�����ƽ̨�Ĺ۸�
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		MyTextPane tf = new MyTextPane();
//		tf.addMouseListener(QimenPopupMenu.getSharedInstance());
//		JFrame f = new JFrame();
//		f.getContentPane().add(tf, BorderLayout.CENTER);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setSize(300, 500);
//		f.pack();
//		f.setLocationRelativeTo(null);
//		f.setVisible(true);
//	}
}
