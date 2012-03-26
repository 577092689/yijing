package org.boc.db.qm;

import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.boc.event.qm.FloatMouseListener;
import org.boc.ui.UIPublic;

public class QimenFloatToolbar {
	private FloatMouseListener flistener;
	
	public QimenFloatToolbar(JComponent frame) {
		flistener = new FloatMouseListener(frame);
	}
	public JToolBar getFloatToolbar() {
    JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    toolBar.add(getImageButton("go", "go","����/����",flistener));
    toolBar.add(getImageButton("save", "save1","���浱ǰ����",flistener));
    toolBar.add(getImageButton("zf", "zf","ת��/����",flistener));
    toolBar.add(getImageButton("rb", "rb","����/��",flistener));
    toolBar.add(getImageButton("td", "td","Сֵ��������/����",flistener));
    toolBar.add(getImageButton("kg", "kg","�й���������/��������",flistener));
    //toolBar.add(new JLabel(" "));    
    toolBar.add(getImageButton("head","m1","����/��ʾ:���ͷ��",flistener));
    toolBar.add(getImageButton("ma","m2","����/��ʾ:�����������˥",flistener));    
    toolBar.add(getImageButton("jxge","m3","����/��ʾ�������׸�",flistener));
    toolBar.add(getImageButton("sgky", "m4","����/��ʾ��ʮ�ɿ�Ӧ",flistener));
    toolBar.add(getImageButton("wsxq","m5","����/��ʾ����˥����",flistener)); 
    toolBar.add(getImageButton("xchm","m6","����/��ʾ���̳��Ĺ��",flistener)); 
    toolBar.add(getImageButton("jtxms","m7","����/��ʾ���������ŵ�����",flistener)); 
    toolBar.add(getImageButton("huo","m8","����/��ʾ�����ɻ������",flistener)); 
    toolBar.add(getImageButton("sj", "m9","����/��ʾ��ʮ����",flistener));    
    toolBar.add(getImageButton("all", "m0","����/��ʾ������",flistener));
    //toolBar.add(new JLabel(" "));
    toolBar.add(getImageButton("reset","o0","���͸�λ",flistener));
    toolBar.add(getImageButton("xmhw","o1","������������ȥ",flistener));
    toolBar.add(getImageButton("bian","o2","�����72��",flistener));
    //toolBar.add(new JLabel(" "));
    toolBar.add(getImageButton("tip","tip","�ر�/��ʾ��ʾ��Ϣ",flistener));
    toolBar.add(getImageButton("io","io","ȫ����/ȫ����",flistener));
    toolBar.add(getImageButton("flush","flush","����������򡢸�֡���ʾ�ȶ����ļ�",flistener));
    
    toolBar.setFloatable(true);
    toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return toolBar;
  }
	/**
	 * �������İ�ť
	 */
	public JButton getImageButton(String cmdname, String imganme, 
			String tip,ActionListener listener) {
		return UIPublic.getCustomImageButton(cmdname, imganme, tip, listener, 18, 14);
	}
}
