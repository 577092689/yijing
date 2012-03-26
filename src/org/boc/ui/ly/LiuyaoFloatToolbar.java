package org.boc.ui.ly;

import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.boc.event.ly.LyFloatMouseListener;
import org.boc.ui.UIPublic;

public class LiuyaoFloatToolbar {
	private LyFloatMouseListener flistener;
	
	public LiuyaoFloatToolbar(JComponent frame) {
		flistener = new LyFloatMouseListener(frame);
	}
	public JToolBar getFloatToolbar() {
    JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    toolBar.add(getImageButton("go", "go","��س/÷��",flistener));
    toolBar.add(getImageButton("save", "save1","���浱ǰ����",flistener));
    toolBar.add(getImageButton("now", "td","����/�ִ�",flistener));
    toolBar.add(new JLabel(" "));    
    toolBar.add(getImageButton("head","m1","����/��ʾ:���ͷ��",flistener));
    toolBar.add(getImageButton("tg","m2","����/��ʾ:���",flistener));    
    toolBar.add(getImageButton("tg","m3","����/��ʾ:��֧",flistener));
    toolBar.add(getImageButton("wh","m4","����/��ʾ������",flistener));
    toolBar.add(getImageButton("wh","m5","����/��ʾ������",flistener));
    toolBar.add(getImageButton("wh","m6","����/��ʾ������",flistener));
    toolBar.add(getImageButton("ma","m7","����/��ʾ:��ɷ����",flistener));    
    toolBar.add(getImageButton("jxge","m8","����/��ʾ����˥����",flistener));
    toolBar.add(getImageButton("wsxq","m9","����/��ʾ������",flistener));    
    toolBar.add(getImageButton("all", "m0","����/��ʾ������",flistener));
    toolBar.add(new JLabel(" "));
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
