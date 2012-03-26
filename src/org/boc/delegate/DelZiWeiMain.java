package org.boc.delegate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.PrintWriter;
import org.boc.util.Debug;
import org.boc.dao.DaoPublic;
import org.boc.dao.DaoZiWeiMain;
import org.boc.dao.DaoCalendar;
import org.boc.util.Messages;

public class DelZiWeiMain {
  private StringBuffer str;
  private PrintWriter pw;
  private DaoPublic pub;
  private DaoZiWeiMain zw;
  private DaoCalendar cal;

  public DelZiWeiMain() {
    pub = new DaoPublic();
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    zw = new DaoZiWeiMain();
    cal = new DaoCalendar();
  }

  /**
   * �������
   * @param y ��
   * @param m ��
   * @param d ��
   * @param h ʱ
   * @param mi ��
   * @param type ������������ʱ�䣬trueΪ����
   * @param yun  �����»��Ƿ����£�trueΪ����
   * @param isMan �Ƿ���Ů
   * @param str
   */
  public String getMingYun(
      int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    try{
      str.delete(0,str.length());
      //���ʱ����Ϣ
      //str.append(pub.getStandHead(y,m,d,h,mi,type,yun,sheng,shi));

      int[] bz = cal.getSiZhu(y,m,d,h,mi,yun,type);
      /**
       * ����һ�㲻�۽����������������¾䡣
       * תΪ���۽�������֧
       */
      bz[4]=(bz[13]+2)%12==0?12:(bz[13]+2)%12;
      Calendar c = new GregorianCalendar();
      int ly = c.get(Calendar.YEAR);
      int[] lnbz = cal.getSiZhu(ly,6,6,6,6,true,false);
      printOut(bz, lnbz, bz[14], isMan,
               pub.getStandHead(y,m,d,h,mi,type,yun,sheng,shi));

      //Debug.out(str.toString());
    }catch(Exception ex) {
      Messages.error("DelZiWeiMain("+ex+")");
    }

    return str.toString();
  }

  /**
   * �ɰ�����������
   */
  public String getMingYun(
      int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, boolean isMan) {
    try{
      str.delete(0,str.length());
      //���ʱ����Ϣ
      //dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      //printOut();
    }catch(Exception ex) {
      Messages.error("DelZiWeiMain("+ex+")");
    }

    return str.toString();
  }

  /**
   * ��ӡ�����еĻ�����Ϣ����ˣ�С�ˣ������
   * @return
   * @throws java.lang.Exception
   */
  private void printOut(int[] bz, int[] lnbz, int day,
                        boolean sex, String head) throws Exception{
    str.append("\r\n");
    str.append(zw.getTianPan(bz, lnbz, day, sex, head));
  }

  public static void main(String[] args) {
    DelZiWeiMain del = new DelZiWeiMain();
    String s = del.getMingYun(1977,3,23,6,30,true,true,0,1,true); //���� �� ��Ů
    System.out.println(s);
  }
}
