package org.boc.delegate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.PrintWriter;
import org.boc.util.Debug;
import org.boc.dao.DaoPublic;
import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoTieBan;
import org.boc.util.Messages;

public class DelTieBanMain {
  private StringBuffer str;
  private PrintWriter pw;
  private DaoPublic pub;
  private DaoTieBan tb;
  private DaoCalendar cal;
  private String kg ;

  public DelTieBanMain() {
    pub = new DaoPublic();
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    tb = new DaoTieBan();
    cal = new DaoCalendar();
    kg = "\r\n    ";
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
        str.append("\r\n������Ϣ��");
        str.append(pub.getStandHead(y,m,d,h,mi,type,yun,sheng,shi));

        int[] bz = cal.getSiZhu(y,m,d,h,mi,yun,type);
        int jq = pub.getJieQi(y,m,d,h,mi,yun,isMan)[1];  //���ڽ���
        printOut(bz,y,isMan,jq, y);

        //Debug.out(str.toString());
      }catch(Exception ex) {
        Messages.error("DelTieBanMain("+ex+")");
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
        Messages.error("DelTieBanMain("+ex+")");
      }

      return str.toString();
    }

    /**
     * ��ӡ�����еĻ�����Ϣ����ˣ�С�ˣ������
     * @return
     * @throws java.lang.Exception
     */
    private void printOut(int[] bz,int y, boolean boy,
                          int jq,int year) throws Exception{
      int ths ;

      int[] zg = tb.getXianTianGua(bz, y,boy,jq);
      ths = tb.getGuaTaiHuShu(zg);
      str.append("\r\n\r\n̫�������ԣ�");
      str.append(kg+"����̫��������������");
      str.append(kg+"    "+tb.getGuaTaiHuDesc(zg));
      str.append(kg+"    ����Ի������Ϊ��ı���β���Ϊ֮��");
      str.append("\r\n");
      int[] bg = tb.getHouTianGua(zg, bz[4]);
      ths = tb.getGuaTaiHuShu(bg);
      str.append(kg+"����̫��������������");
      str.append(kg+"    "+tb.getGuaTaiHuDesc(bg));
      str.append(kg+"    ����Ի��������������������������");
      str.append("\r\n");
      int[] hg = tb.getHuGua(zg);
      ths = tb.getGuaTaiHuShu(hg);
      str.append(kg+"����̫��������������");
      str.append(kg+"    "+tb.getGuaTaiHuDesc(hg));
      str.append(kg+"    ����Ի��������������������������");
      str.append("\r\n");
      str.append("\r\n��ֵ������ֵ���ԣ�");
      str.append(tb.getDaZhiYunGua(zg, bg, year));
      str.append("\r\n");
      str.append("\r\n�����ԣ�");
      str.append(kg+"��ĸ�ԣ�"+tb.guaShuByGanZi(bz[1],bz[2],5,true)+"���Բ�Ǭ�������ȣ����������ȡ�");
      str.append(kg+"�����ԣ�"+tb.guaShuByGanZi(bz[3],bz[4],1,false)+"��");
      if(boy)
        str.append(kg+"�����ԣ�"+tb.guaShuByGanZi(bz[5],bz[6],3,false)+"���Բ�ľ�����Ҷȡ�");
      else
        str.append(kg+"�����ԣ�"+tb.guaShuByGanZi(bz[5],bz[6],4,false)+"��");
      str.append(kg+"�����ԣ�"+tb.guaShuByGanZi(bz[7],bz[8],2,false)+"��");
      str.append("\r\n");
      str.append("\r\n�Ⱥ����Ի��ԣ�");
      str.append("\r\n");
      str.append(tb.printGuaXiang(bz[5],zg,bg,hg));
    }

    public static void main(String[] args) {
      DelTieBanMain del = new DelTieBanMain();
      String s = del.getMingYun(1977,3,23,6,30,true,true,0,1,true); //���� �� ��Ů
      System.out.println(s);
    }

}
