package org.boc.ui;

import java.awt.Dimension;

import javax.swing.JTextPane;
/**
 * JTextPane��֧�ֲ��Զ����У���Ҫ�˹��ܣ��������أ�Ȼ��ָ�����ȼ���
 * @author Administrator
 *
 */
public class MyTextPane extends JTextPane {
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	public void setSize(Dimension d) {
		d.width = 5000; //�еĿ����Ҫ������ı�������һ���Ƕ���
		super.setSize(d);
	}
	
	public void roll20() {
		this.setCaretPosition(0);
		this.setSelectionStart(0);
		this.setSelectionEnd(0);
	}
}
