package org.boc.ui.ly;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.ly.Liuyao;
import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.PrintWriter;

public class LyTip {
	private MyTextPane textPane;
	private StyledDocument styledDoc; // �ı�����ĵ�
	private LiuYaoFrame frame;
	private LiuyaoPublic pub;
	private DaoYiJingMain daoly;
	private final String sReturn = System.getProperty("line.separator"); // ���з�
	private HtmlMultiLineControl html;

	public LyTip(MyTextPane textPane, BasicJPanel frame) {
		this.textPane = textPane;
		this.frame = (LiuYaoFrame) frame;
		html = new HtmlMultiLineControl();
	}

	/**
	 * �õ���ʾ��Ϣ������ָ�ŵ�һ���ַ���һ��ȡ4���ַ�����Ϊ��Ĳ�4���ְ�
	 * 
	 * @param e
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String getToolTip(MouseEvent e) {
		if (pub == null) {
			pub = this.frame.getDelYiJingMain().getLiuyaoPublic();
			daoly = pub.getDaoYiJingMain();
		}

		Point p = e.getPoint();
		int pos = textPane.viewToModel(p);
		String rs = null;
		if (styledDoc == null)
			styledDoc = (StyledDocument) textPane.getDocument();
		((AbstractDocument) styledDoc).readLock();
		AttributeSet as = null;
		try {
			as = styledDoc.getCharacterElement(pos).getAttributes();
		} finally {
			((AbstractDocument) styledDoc).readUnlock();
		}

		Enumeration en = as.getAttributeNames();
		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (key.toString().equals("foreground"))
				if (as.getAttribute(key).equals(Color.WHITE))
					return null;
				else
					break;
		}

		try {
			rs = getTipinfo(pos, as);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
//		if(rs!=null && rs.trim().equals("null")) rs = null;
//		System.out.println(rs);
		return rs;
	}

	/**
	 * �㷨��Ҫ�Զ��ϴʾ� 305-502 710-905 1114-1310֮�������ֻȡһ���־���
	 * �����ױ�ͷ�������й�ϵ�����Ա�ͷ����Ҫ�̶�
	 */
	private String geyKey(int pos, AttributeSet as) throws BadLocationException {
		if (pos < 10)
			return null; // С��4����ȡ���ᱨ��
		//System.out.println(pos);
		
		//����Ǵ����壬ֻ���ص�ǰ��һ���ַ�
		Enumeration en = as.getAttributeNames();	
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					return styledDoc.getText(pos, 1);
				}
				break;
			}
		}

		if ((pos > 305 && pos < 502) || (pos > 710 && pos < 905) || (pos > 1114 && pos < 1310)) {
			return styledDoc.getText(pos, 1);
		}

		StringBuilder sb = new StringBuilder();
		String[] splits = { "��", "��", "��", Liuyao.FILL1, Liuyao.FILL2 };
		for (int i = 0; i < 4; i++) {// ��ǰȡ4���ַ�
			String tmp = null;
			try {
				tmp = styledDoc.getText(pos + i, 1);
			} catch (Exception e) {
				return null;
			}
			boolean isSame = false;
			for (String split : splits) {
				if (tmp.equals(split)) {
					isSame = true;
					break;
				}
			}
			if (!isSame)
				sb.append(tmp);
			else
				break;
		}

		for (int i = 1; i <= 4; i++) {// ���ȡ4���ַ�
			String tmp = styledDoc.getText(pos - i, 1);
			boolean isSame = false;
			for (String split : splits) {
				if (tmp.equals(split)) {
					isSame = true;
					break;
				}
			}
			if (!isSame)
				sb.insert(0, tmp);
			else
				break;
		}

		return sb.toString();
	}
	
	/**
	 * ���еĴ�����ֱ�������ġ� 9+8+8+9=34
	 */
	public static final String[] BIGWORD = {"��","��","��","��","��","��","��","��",
			"��", "��", "��", "��", "��", "��", "��", "��", "Ӣ",
			"��", "��", "��", "��","��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��"};
	/**
	 * ����ˮ��ɫ�����Ӧ��pos��ֵ���ֱ����ڵڼ���
	 */
	public static final int[] REDWORD = {0,979,184,557,152,0,995,589,963,168};
	@SuppressWarnings("unchecked")
	/**
	 * ��ʾ���Ⱥ�˳��Ϊ��
	 * 1. ��ʾ��ֵ�ʱ��ͷ����Ϣ
	 * 2. ���ȿ��������壬���Ӧ����ʾ��Ϣ
	 * 3. �ж��Ƿ���ˮ��ɫ���壬��ʾ�˹�����ϸ��Ϣ
	 * 4. �ж��Ƿ��Ǽ��׸�ʮ�ɿ�Ӧ
	 * 5. �ж��Ƿ������ǽ�������
	 * 6. �ж��Ƿ���ʮ����
	 * 7. ���֧������
	 * 8. �̳��Ĺ��������R�i
	 */
	private String getTipinfo(int pos, AttributeSet as)
			throws BadLocationException {
		//1. ����Ǳ�ͷ���֣�����ʾ��ϸ�ľ���Ϣ
		//System.out.println(pos);
		if(pos>=3 && pos<=6) {
			String t = null;
			try{t = styledDoc.getText(3, 3);}catch(Exception e) {
				e.printStackTrace();
			}
			//if(t!=null && t.indexOf("")!=-1)
				return html.CovertDestionString(getDateHead());  //��ʱ������֣�Ȼ�������ͷ��
		}
			
		String key = geyKey(pos, as);
		if (key == null || key.trim().length() == 0)
			return null;
		
		//System.out.println("-- pos="+pos+";key="+key);

		// 2. �������壬�ж��Ƿ������ҵ�Ǭ���ż��š��ż����̸�/���̸ɡ��Ǽ��š������
		Enumeration en = as.getAttributeNames();
		boolean isBigFont = false;		
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					isBigFont = true;
				}
				break;
			}
		}
		if (isBigFont) {  //ֻ�д�������ǵģ���ֻ�а��š����ǡ�����ʮ��ɲ��Ǵ�����
			//System.out.println(pos+";key="+key);
			for (int i = 0; i <BIGWORD.length; i++) {
				if (BIGWORD[i].equals(key)){
					//System.out.println("isbigfont pos="+pos+";key="+key);
					//return html.CovertDestionString(getDesc(pos, as, key));
				}										
			}
		}
		
		return null;
	}
	
	//�����ϸ��ʱ��ͷ����Ϣ
	private String getDateHead() {
    //���ʱ����Ϣ
		return "";
	}
}
