package org.boc.ui.ly;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.boc.db.SiZhu;
import org.boc.db.ly.Liuyao;
import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.PrintWriter;

public class GuaBase {
	protected ResultPanel rp;  //չ�ֽ�������
	protected MyTextPane text;				 //��ʾ�����������Ϣ
	protected Document doc ;					 //��ʾ�ṹ���ĵ�
	protected PrintWriter pw ;
	
	protected int mode;
	protected int yshen;
	protected String mzhu;
	protected boolean isBoy, isYin, isYun;
	protected int sheng, shi;
	protected int y, m, d, h, mi;
	protected int up, down;
	protected int[] acts;
	
	public void setParameter(ResultPanel rp,
  		int mode, int yshen, String mzhu,
  		boolean isBoy, int sheng, int shi,
  		int y, int m, int d, int h, int mi,
  		boolean isYin, boolean isYun,
      int up, int down, int[] acts) {
		this.rp = rp;  	
		this.mode = mode;
		this.yshen = yshen;
		this.mzhu = mzhu;
		this.isBoy = isBoy;
		this.sheng = sheng;
		this.shi = shi;
		this.y = y;
		this.m = m;
		this.d =d ;
		this.h = h;
		this.mi = mi;
		this.isYin = isYin;
		this.isYun = isYun;
		this.up = up;
		this.down = down;
		this.acts = acts;
	}
	protected void init() throws BadLocationException {		
		text = rp.getTextPane();
		//text.setEditable(false);
		doc = text.getDocument();
		doc.remove(0, doc.getLength());

		pw = new PrintWriter();
		pw.setDocument(doc);
	}
	/**
	 * ѭ����������ո��ָ�����ַ�
	 */
	protected String speat(int len, String fillstr) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<len; i++) sb.append(fillstr);
		
		return sb.toString();
	}
	
	 /**
	  * �Ƿ�ǰس�Ƕ�س
	  */
	 public boolean isDongYao(int[] changes, int current) {
	   for(int i=0; i<changes.length; i++) {
	     if(changes[i] == current) {
	       return true;
	     }
	   }
	   return false;
	 }
	  /**
	   * ���������ַ�ʹ��������center��
	   * @param str
	   * @param center
	   * @return
	   */
	  public String makeCenter(String str, int center) {
	    int len = str.getBytes().length;
	    return speat(center+len/2 - len,Liuyao.FILL2)+str;
	  }
	
	protected void p11(String s) throws Exception{
		pw.print(s, pw.SBLACK, false);
	}
	protected void p12(String s) throws Exception{
		pw.print(s, pw.MBLACK, false);
	}
	protected void p121(String s) throws Exception{
		pw.print(s, pw.MBLACK, true);
	}
	protected void p13(String s) throws Exception{
		pw.print(s, pw.BBLACK, false);
	}
	protected void p21(String s) throws Exception{
		pw.print(s, pw.SBLUE, false);
	}
	protected void p22(String s) throws Exception{
		pw.print(s, pw.MBLUE, false);
	}
	protected void p23(String s) throws Exception{
		pw.print(s, pw.BBLUE, false);
	}
	protected void p31(String s) throws Exception{
		pw.print(s, pw.SRED, false);
	}
	protected void p32(String s) throws Exception{
		pw.print(s, pw.MRED, false);
	}
	protected void p33(String s) throws Exception{
		pw.print(s, pw.BRED, false);
	}
	protected void p41(String s) throws Exception{
		pw.print(s, pw.SPINK, false);
	}
	protected void p42(String s) throws Exception{
		pw.print(s, pw.MPINK, false);
	}
	protected void p43(String s) throws Exception{
		pw.print(s, pw.BPINK, false);
	}
	
	protected void p3(String s) throws Exception{
		pw.print(s, pw.MBLACK, false);
	}
}
