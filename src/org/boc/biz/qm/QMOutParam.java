package org.boc.biz.qm;


/**
 * ����������������ʼ��
 */
public class QMOutParam {
	public static final String OUTPARAM1 = "���";
	private static StringBuilder sb = new StringBuilder();
	
	public static void append(String info) {
		sb.append(info+"\r\n");
	}
	
	public static String getInfo() {
		return sb.toString();
	}
	
	public static void clean() {
		sb.delete(0, sb.length());
	}
}
