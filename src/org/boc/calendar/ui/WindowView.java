package org.boc.calendar.ui;

import java.awt.Component;
import java.awt.Dimension;

/**
 * �ṩһЩ UI ��ط���
 */
public class WindowView {
	/**
	 * ��ָ�������ƶ�����Ļ����
	 * @param win ָ������
	 */
	public static void moveToScreenCenter(Component win) {
		Dimension de = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		win.setLocation((de.width - win.getWidth()) / 2, (de.height
				- win.getHeight() - 40) / 2);
		
	}

}
