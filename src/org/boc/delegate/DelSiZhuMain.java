package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.sz.DaoSiZhuHunYin;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.dao.sz.DaoSiZhuWangShuai;
import org.boc.dao.sz.DaoSiZhuXingGe;
import org.boc.dao.sz.DaoSiZhuYongShen;
import org.boc.db.Calendar;
import org.boc.db.ChengGu;
import org.boc.db.GuiGu;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Messages;

public class DelSiZhuMain {
  private StringBuffer str;
  private PrintWriter pw;
  private DaoYiJingMain daoY;
  private DaoCalendar daoC;
  private DaoSiZhuMain dao;
  private DaoSiZhuWangShuai dao2;
  private DaoSiZhuYongShen daoys;
  private DaoSiZhuXingGe daox;
  private DaoSiZhuHunYin daoh;

  public DelSiZhuMain() {
    dao = new DaoSiZhuMain();
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    daoY = new DaoYiJingMain();
    daoC = new DaoCalendar();
    dao2 = new DaoSiZhuWangShuai();
    daoys = new DaoSiZhuYongShen();
    daox = new DaoSiZhuXingGe();
    daoh = new DaoSiZhuHunYin();
  }

  public String getMingYunForHtml(
      int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    String s = "";
    s += getMingYun(y,m,d,h,mi,type,yun,sheng,shi,isMan);
    s += getGlobleInfo(y, m, d, h,mi, type, yun, sheng ,shi, isMan);
    s += getXingGeInfo(y, m, d, h,mi, type, yun, sheng ,shi, isMan);
    s += getHunYinInfo(y, m, d, h,mi, type, yun, sheng ,shi, isMan);

    return s;
  }

  public String getMingYunForHtml(
      int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, boolean isMan) {
    String s = "";
    s += getMingYun(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);
    s += getGlobleInfo(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);
    s += getXingGeInfo(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);
    s += getHunYinInfo(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);

    return s;
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
      dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
      printOut();
    }catch(Exception ex) {
      Messages.error("DelSiZhuMain("+ex+")");
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
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      printOut();
    }catch(Exception ex) {
      Messages.error("DelSiZhuMain("+ex+")");
    }

    return str.toString();
  }

  /**
   * ��ʱ������������Ϣ
   * @return
   */
  public String getGlobleInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getGeJuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * �ɰ�������������Ϣ
   * @return
   */
  public String getGlobleInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                              boolean yun, boolean isMan) {
    str.delete(0,str.length());
    //���ʱ����Ϣ
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
    getGeJuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * ��ʱ���������Ը���ߵ��������ڵ���Ϣ
   * @return
   */
  public String getXingGeInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //���ʱ����Ϣ
    str.append("\r\n");
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getXingGeOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * �ɰ����������Ը���ߵ��������ڵ���Ϣ
   * @return
   */
  public String getXingGeInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //���ʱ����Ϣ
      str.append("\r\n");
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getXingGeOut();

      //Debug.out(str.toString());
      return str.toString();
  }

  /**
   * ��ʱ������������Ļ�����Ϣ
   * @return
   */
  public String getHunYinInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //���ʱ����Ϣ
    str.append("\r\n");
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getHunYinOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * �ɰ�������������Ļ�����Ϣ
   * @return
   */
  public String getHunYinInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //���ʱ����Ϣ
      str.append("\r\n");
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getHunYinOut();

      //Debug.out(str.toString());
      return str.toString();
    }

  /**
   * ��ʱ������ƹ�
   * @return
   */
  public String getChengGuInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getChengGuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * �ɰ�����������ƹ�
   * @return
   */
  public String getChengGuInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //���ʱ����Ϣ
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getChengGuOut();

      //Debug.out(str.toString());
      return str.toString();
    }

    /**
   * ��ʱ������ƹ�
   * @return
   */
  public String getGuiGuInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getGuiGuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * �ɰ�����������ƹ�
   * @return
   */
  public String getGuiGuInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //���ʱ����Ϣ
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getGuiGuOut();

      //Debug.out(str.toString());
      return str.toString();
    }


  /**
   * ��ӡ�����еĻ�����Ϣ����ˣ�С�ˣ������
   * @return
   * @throws java.lang.Exception
   */
  private void printOut() throws Exception{
    //������������̥Ԫ
    dao.getSiZhuTaiMingOut(str);
    //�Ŵ���
    dao.getDaYunOut(str);
    //����������̥Ԫ������С�����������
    dao.getQiZhuOut(str);

    //Debug.out(str.toString());
    //return str.toString();
  }

  /**
   * ��������۶�
   ($�ո�|��)($�ո�����|ˮ)����($����|����)($����|��ʮ��)�գ�����($˾���$˾������|��ľ)����˾�
   ($�ո�����|��)��($�ļ�|��)�£�($���������|��ˮͨԴ)
   (%ȡ��)�����У�$��������|1ľ4ˮ��
   for($��͸��|ʳ����)͸�ɣ�($�Ƿ�ͨ��|��֧����ͨ��),($�츲����)��($�Ƿ�ϻ�),($��������),($ǿ��),
      ($�Ƿ���»�г���), ($�Ƿ��), ($�Ƿ����), ($�Ƿ��ܿ�), ($�Ƿ���˥)
   for($֧����|ʳ����)��أ�($�Ƿ�ϻ�),($��������),($ǿ��),($�Ƿ����),
      ($�Ƿ���»), ($�Ƿ�����), ($�Ƿ��), ($�Ƿ����), ($�Ƿ��ܿ�), ($�Ƿ���˥)
   ($�ո�),($�Ƿ�ͨ��|��֧����ͨ��),($�츲����)��($�Ƿ�ϻ�),($��������),($ǿ��),
      ($�Ƿ���»�г���), ($�Ƿ��), ($�Ƿ����), ($�Ƿ��ܿ�), ($�Ƿ���˥)
   ($�Ƿ��к�ƫ��)��($Դ��),($ͨ��),($����),($��ҿ���)
   ($��ǿ��ģʽȡ����|����ɱ����ȡ�Ƚ�Ϊ��)
   ($����˥�ƺ�ȡ��)
   ($�������),($����֮��),($ϲ��֮��),($����֮��),($����)
   ($Ӧ�кδ���),($���׶�ϲ��)��($��ʫ)
   */
  private void getGeJuOut() {
    int jiehourishu = daoC.getDiffDatesForSiLing();
    int silinggan = Calendar.SILING[SiZhu.yz][jiehourishu];

    str.append("\n");
    //1. ������
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("�ո�"+YiJing.TIANGANNAME[SiZhu.rg]+YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.rg]]);
    str.append("����"+Calendar.JIEQINAME[SiZhu.yz]+"��"+jiehourishu+"�գ�");
    str.append("����"+YiJing.TIANGANNAME[silinggan]+YiJing.WUXINGNAME[YiJing.TIANGANWH[silinggan]]+"����˾�");
    str.append("����ν"+YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.rg]]+"��"+Calendar.SIJI[SiZhu.yz]+","+dao2.sijiDesc());
    //2. ���д��
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("������˥��");
    str.append(dao2.getWuXingCent());
    //3. ���
    str.append("\r\n");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("�����֣�");
    str.append(dao2.getGeJu());
    //4.����
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("����ο���");
    str.append(daoys.analyseYongShen());
    //5.�˳�
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("�˳�ϲ�ɣ�");
    str.append(daoys.analyseYunCheng());
    //5.��������
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("����������");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("ԭ�֣�"+YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+
               " "+YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+
               " "+YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+
               " "+YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+
               "    ľ��"+SiZhu.muCent +
               "  ��"+SiZhu.huoCent +
               "  ����"+SiZhu.tuCent +
               "  ��"+SiZhu.jinCent +
               "  ˮ��"+SiZhu.shuiCent );
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoys.analyseSameBaZi());

    //Debug.out(str.toString());
    //return str.toString();
  }

  /**
   * ����Ը���ߵ���Ϣ
   */
  private void getXingGeOut() {
    //���
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("��߲ο���");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daox.getXingGeOut());

  }

  /**
   * ��������������Ϣ
   */
  private void getHunYinOut() {
    //���
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("���ڲο���");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoh.getSiZhuHunYin());
  }

  /**
   * ����ƹǵ������Ϣ
   */
  private void getChengGuOut() {
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    ChengGu cg = new ChengGu();
    int izhong = cg.yz[SiZhu.ng][SiZhu.nz]+cg.mz[Calendar.MONTHN1]+cg.dz[Calendar.DAYN1]+cg.hz[SiZhu.sz];
    int jin = izhong/10;
    int liang = izhong%10;
    String zhong = "����Ϊ��"+jin+"��"+liang+"Ǯ��";
    str.append("Ԭ��ƹǣ�");
    str.append(zhong);
    str.append("\r\n");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(cg.desc[izhong]);
  }

  /**
   * �����ȷֶ���
   */
  private void getGuiGuOut() {
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    GuiGu gg = new GuiGu();
    str.append("������"+YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+"      "
               +YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+"      "
               +YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+"      "
               +YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"      ");
    str.append("\r\n");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(gg.jing[SiZhu.ng][SiZhu.sg]);
  }




  public static void main(String[] args) {
    DelSiZhuMain del = new DelSiZhuMain();
    System.out.println(del.getMingYunForHtml(1977,5,10,6,30,false,true,0,6,true)); //���� �� ��Ů
    //System.out.println(del.getMingYunForHtml(1,1,1,1,1,1,1,1,true,true)); //���� �� ��Ů
  }
}
