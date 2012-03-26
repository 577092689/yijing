package org.boc.dao.bz;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.BaZhai;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;

public class DaoFS_BaZhai {
  private DaoYiJingMain daoyj;
  private StringBuffer str;

  public DaoFS_BaZhai() {
    daoyj = new DaoYiJingMain();
    str = new StringBuffer();
  }

  /**
   * סլ�۶�
   * @param year
   * @param sex
   * @param wx
   * @return
   */
  public String analyse(int year, int year2, boolean sex, int[] hj,
                        int wuxiang, int dmw, int dmx, int cesuo, int jiuzhai,
                             int woshi, int fangmen,  int chuang,
                             int chufang, int zaowei, int zaoxiang)  {
    str.delete(0, str.length());

    str.append("\r\n 1. ����ͷ��");
    if(hj!=null)
      for(int i=0; i<hj.length; i++) {
        if(hj[i]==0)
          continue;
        str.append("\r\n    " + BaZhai.hj1[hj[i]]);
      }
    str.append("\r\n");

    str.append("\r\n 2. լ�����䣺");
    int zg = this.getZhaiGua(wuxiang);
    int mg = this.getMingGua(year, sex);
    if(BaZhai.dxsg[zg] == BaZhai.dxsg[mg]) {
      str.append("\r\n    "+BaZhai.dxsm[BaZhai.dxsg[mg]]+"ס"+BaZhai.dxsz[BaZhai.dxsg[zg]]+"��Ϊ�ø�Ԫ���������������󼪡�");
    }else{
      str.append("\r\n    "+BaZhai.dxsm[BaZhai.dxsg[mg]]+"ס"+BaZhai.dxsz[BaZhai.dxsg[zg]]+"��լ�����䣬�ɴӴ��š���������Ա�ƽ������Ҫ��������������Ǩ�ӡ�");
    }
    str.append("\r\n");

    str.append("\r\n 3. ���ż��ף�");
    str.append("\r\n    1�� ��λ��");
    int dmwShen = this.getShen(mg, dmw);
    xiangDuan(dmwShen, dmw);
    str.append("\r\n    2�� ����");
    int dmxShen = this.getShen(mg, dmx);
    xiangDuan(dmxShen, dmx);
    str.append("\r\n    3�� ��λ��");
    int caiw = BaZhai.hxtg[10 - BaZhai.xhtg[dmw]];
    str.append("\r\n        ��λ�ڴ��ŶԽ��ߵ�"+BaZhai.fx[caiw]+
               "λ���˰ڷ�����Ϊ"+YiJing.WUXINGNAME[YiJing.jingguawx[caiw]]+
               "��ˮ��Ʒ����"+BaZhai.cwfs[YiJing.jingguawx[caiw]]);

    str.append("\r\n");

    str.append("\r\n 4. ���Ҽ��ף�");
    str.append("\r\n    1�� ��λ��");
    int wsShen = this.getShen(mg, woshi);
    xiangDuan(wsShen, woshi);
    str.append("\r\n    2�� ��λ��");
    int fmShen = this.getShen(mg, fangmen);
    xiangDuan(fmShen, fangmen);
    str.append("\r\n    3�� ��λ��");
    int cwShen = this.getShen(mg, chuang);
    xiangDuan(cwShen, chuang);
    str.append("\r\n");

    str.append("\r\n 5. �������ף�");
    str.append("\r\n    1�� ��λ��");
    int cfShen = this.getShen(mg, chufang);
    if(chufang==3 || chufang==6 || chufang==4 || chufang==2)
      str.append("\r\n        ˮ����ʮ���ߣ������������λ֮"+BaZhai.fx[chufang]+"���������࣬������Ƣ�����ꡣ");
    xiangDuan(cfShen, chufang);
    str.append("\r\n    2�� ��λ��");
    int zwShen = this.getShen(mg, zaowei);
    xiangDuan2(zwShen, zaowei);
    str.append("\r\n    3�� ����");
    int zxShen = this.getShen(mg, zaoxiang);
    xiangDuan(zxShen, zaoxiang);
    str.append("\r\n");

    //str.append("\r\n 6. �������ף�");
    //str.append("\r\n    1�� ��λ��");
    //int csShen = this.getShen(mg, cesuo);
    //xiangDuan(csShen, cesuo);
    //str.append("\r\n");

    str.append("\r\n 6. ������");
    int mg2 = this.getMingGua(year2, !sex);
    int hyShen = this.getShen(mg, mg2);
    str.append("\r\n    ����Ϊ"+YiJing.JINGGUANAME[mg]+"����");
    str.append("�ڶ�����Ϊ"+YiJing.JINGGUANAME[mg2]+"����");
    str.append("���޶��˺ϳ�"+BaZhai.yxp[hyShen]+"�飬Ϊ"+BaZhai.hunyin[hyShen]);
    int fqcw = BaZhai.fqcw[mg][mg2];
    String _fq = "";
    if(fqcw>=1000)
      _fq += "��"+BaZhai.fx[fqcw/1000];
    if(fqcw>=100)
      _fq += "��"+BaZhai.fx[(fqcw/100)%10];
    if(fqcw>=10)
      _fq += "��"+BaZhai.fx[(fqcw/10)%10];
    if(fqcw>=1)
      _fq += "��"+BaZhai.fx[fqcw%10];

    str.append("\r\n    ���޴�λ��������С̫�����з���"+_fq);
    str.append("\r\n");

    str.append("\r\n 7. ��Ϣ��");
    str.append("\r\n    "+BaZhai.yxp[hyShen]+"�飬"+BaZhai.zixi[hyShen]);
    str.append("\r\n");

    str.append("\r\n 8. ������");
    getJiBing(mg, dmw, dmwShen, "����λ����סլ��̫��");
    getJiBing(mg, dmx, dmxShen, "���ſ��ŷ�����סլ��̫��");
    getJiBing(mg, woshi, wsShen, "����λ����סլ��̫��");
    getJiBing(mg, fangmen, fmShen, "����λ��������С̫��");
    getJiBing(mg, chuang, cwShen, "��ͷλ��������С̫��");
    getJiBing(mg, chufang, cfShen, "����λ����סլ��̫��");
    //getJiBing(mg, zaowei, zwShen, "��λ��סլ��̫��");
    getJiBing(mg, zaoxiang, zxShen, "������סլ��̫��");
    str.append("\r\n");

    str.append("\r\n 9. �ֻ���");
    getZaiHuo(mg, dmw, dmwShen, "����λ����סլ��̫��");
    getZaiHuo(mg, dmx, dmxShen, "���ſ��ŷ�����סլ��̫��");
    getZaiHuo(mg, woshi, wsShen, "����λ����סլ��̫��");
    getZaiHuo(mg, fangmen, fmShen, "����λ����סլ��̫��");
    getZaiHuo(mg, chuang, cwShen, "��ͷλ��������С̫��");
    getZaiHuo(mg, chufang, cfShen, "����λ����סլ��̫��");
    //getZaiHuo(mg, zaowei, zwShen, "��λ��סլ��̫��");
    getZaiHuo(mg, zaoxiang, zxShen, "������סլ��̫��");
    str.append("\r\n");

    str.append("\r\n 10. Ǩ�ƣ�");
    str.append(this.getQianYi(year, sex , zaoxiang, jiuzhai, wuxiang));
    str.append("\r\n");

    return str.toString();
  }

  /**
   * ������ֻ�������
   * @param mg
   * @param fx
   * @param shen
   * @param wuti
   */
  private void getZaiHuo(int mg, int fx,int shen, String wuti) {
    String s = null;

    s = BaZhai.zaihuo[mg][fx];
    if(s!=null)
      str.append("\r\n    "+wuti+BaZhai.fx[fx]+"����"+BaZhai.yxp[shen]+"λ����"+s);
  }

  /**
   * ����Լ���������
   * @param mg
   * @param fx
   * @param shen
   * @param wuti
   */
  private void getJiBing(int mg, int fx,int shen, String wuti) {
    String s = null;

    s = BaZhai.jibing[mg][fx];
    if(s!=null)
      str.append("\r\n    "+wuti+BaZhai.fx[fx]+"����"+BaZhai.yxp[shen]+"λ����"+s);
  }

  /**
   * �����ϸ�ļ�������
   * @param shen
   * @param fx
   */
  private void xiangDuan(int shen, int fx) {
    String[] _s = null;

    str.append("\r\n        a�� ���Ǽ��ף�");
    str.append(BaZhai.fx[fx]+"����"+BaZhai.yxp3[shen][0]);

    str.append("\r\n        b�� �������ף�");
    _s = BaZhai.yxp3[shen];
    for(int i=1; i<_s.length; i++) {
      str.append("\r\n            "+_s[i]);
    }

    str.append("\r\n        c�� ��λ���ˣ�");
    str.append("\r\n            ");
    int gwx = YiJing.jingguawx[fx];
    int xwx = BaZhai.yxpwx[shen];
    str.append(YiJing.JINGGUANAME[fx]+"��������"+YiJing.WUXINGNAME[gwx]+"��");
    str.append(BaZhai.yxp[shen]+"������"+YiJing.WUXINGNAME[xwx]+"��");
    if(YiJing.WXDANKE[gwx][xwx]==1 || YiJing.WXDANSHENG[xwx][gwx]==1 ||
       YiJing.WXDANKE[xwx][gwx]==1)
      str.append("�����ܵ���й������������������");
    else
      str.append("�����ܵ�����������������ǿ��");

    str.append("\r\n        d�� ���꼪�ף�");
    str.append("\r\n            ��������"+YiJing.WUXINGNAME[xwx]+"֮�������£����ױ�����Ӧ��");
  }

  private void xiangDuan2(int shen, int fx) {
    String[] _s = null;

    str.append("\r\n        a�� ���Ǽ��ף�");
    str.append(BaZhai.fx[fx]+"����"+BaZhai.zaoweiJX[shen]);

    str.append("\r\n        b�� �������ף�");
    _s = BaZhai.yxp3[shen];
    for(int i=1; i<_s.length; i++) {
      str.append("\r\n            "+_s[i]);
    }

    str.append("\r\n        c�� ��λ���ˣ�");
    str.append("\r\n            ");
    int gwx = YiJing.jingguawx[fx];
    int xwx = BaZhai.yxpwx[shen];
    str.append(YiJing.JINGGUANAME[fx]+"��������"+YiJing.WUXINGNAME[gwx]+"��");
    str.append(BaZhai.yxp[shen]+"������"+YiJing.WUXINGNAME[xwx]+"��");
    if(YiJing.WXDANKE[gwx][xwx]==1 || YiJing.WXDANSHENG[xwx][gwx]==1 ||
       YiJing.WXDANKE[xwx][gwx]==1)
      str.append("�����ܵ���й������������������");
    else
      str.append("�����ܵ�����������������ǿ��");

    str.append("\r\n        d�� ���꼪�ף�");
    str.append("\r\n            ��������"+YiJing.WUXINGNAME[xwx]+"֮�������£����ױ�����Ӧ��");
  }


  /**
   * �õ�ͷ�����
   * @return
   */
  public String getHead(int year, boolean sex, int wx) {
    String s= "";
    int gua = getMingGua(year,sex);

    s += "���������̣�";
    s += sex ? "Ǭ��" : "����";
    s += "��"+year+"������"+YiJing.JINGGUANAME[gua] +
          "��"+BaZhai.dxsm[BaZhai.dxsg[gua]];

    //�õ�������������������ԣ������������������Ϊ9
    int zx = BaZhai.hxtg[10 - BaZhai.xhtg[wx]];
    s += "\r\n    լ�������̣�������"+BaZhai.fx[zx]+"��"+BaZhai.fx[wx]+"��"+
        YiJing.JINGGUANAME[zx]+"��"+BaZhai.dxsz[BaZhai.dxsg[zx]];
    return s;
  }

  /**
   * ��������
   * @param year
   * @param sex
   * @return
   */
  public String pp(int year, boolean sex, int wx) {
    String s = "";
    str.delete(0, str.length());

    out1("�������������������ש����������������ש�����������������");
    out1("��41��91��21��");
    out1("��42��92��22��");
    out1("��43��93��23��");
    out1("��44��94��24��");
    out1("��45��95��25��");
    out1("��46��96��26��");
    out1("�ǩ����������������贈���������������贈����������������");
    out1("��31��51��71��");
    out1("��32��52��72��");
    out1("��33��53��73��");
    out1("��34��54��74��");
    out1("��35��55��75��");
    out1("��36��56��76��");
    out1("�ǩ����������������贈���������������贈����������������");
    out1("��81��11��61��");
    out1("��82��12��62��");
    out1("��83��13��63��");
    out1("��84��14��64��");
    out1("��85��15��65��");
    out1("��86��16��66��");
    out1("�������������������ߩ����������������ߩ�����������������");

    s = str.toString();
    int hxnum = 0;

    //������
    int mg = this.getMingGua(year,sex);
    int[] x = BaZhai.yxp2[mg];
    for(int i = 1; i<x.length; i++) {
      hxnum = BaZhai.xhtg[i];
      s = s.replaceAll(hxnum+"3",format(BaZhai.yxp[x[i]]+"  "+YiJing.WUXINGNAME[BaZhai.yxpwx[x[i]]]));
    }
    s = s.replaceAll("53",format(YiJing.JINGGUANAME[mg]+"��    "));

    //��լ��
    int zg = this.getZhaiGua(wx);
    x = BaZhai.yxp2[zg];
    for(int i = 1; i<x.length; i++) {
      hxnum = BaZhai.xhtg[i];
      s = s.replaceAll(hxnum+"4",format(BaZhai.yxp[x[i]]+"  "+YiJing.WUXINGNAME[BaZhai.yxpwx[x[i]]]));
    }
    s = s.replaceAll("54",format(YiJing.JINGGUANAME[zg]+"լ    "));

    //�ŵ���
    for(int i = 1; i<=x.length; i++) {
      if(i==5)
        continue;
      s = s.replaceAll(i+"5",format(QiMen.dpjg[i]+"  "+YiJing.WUXINGNAME[QiMen.jgwh[i]]));
    }

    for(int i=0; i<=9; i++) {
      for(int j=0; j<=6; j++) {
        s = s.replaceAll(""+i+j, format(""));
      }
    }

    return s;
  }

  /**
   * �ڰ˹�ͼ����������������
   * @param o
   * @return
   */
  private String format(String o) {
    if(o==null)
      o="";
    if(o.trim().equals("null"))
       o="";
    if(o.getBytes().length<8)
      o=daoyj.getRepeats(" ", (8-o.getBytes().length))+o;

    int len = o.getBytes().length;
    int hlen = (16-len)/2;
    return (daoyj.getRepeats(" ",hlen)+o+daoyj.getRepeats(" ",hlen));
  }

  /**
   * ����Ź�ͼ
   * @param o
   */
  private void out1(Object o) {
    str.append("    "+o.toString()+"\r\n");
  }

  /**
   * ����լ��
   * @return
   */
  public int getZhaiGua(int wx) {
    return BaZhai.hxtg[10 - BaZhai.xhtg[wx]];
  }

  /**
   * ��������, ������������� Ǭ1��8
   * @param year
   * @return
   */
  public int getMingGua(int year, boolean sex) {
    int i = year/1000;
    int j = (year/100)%10;
    int k = (year/10)%(year/100);
    int n = year%(year/10);

    int m = (11-(i+j+k+n)%9)%9;
    if(!sex)
      m = BaZhai.nlmg[m];
    if(sex && m==5)
      m = 2;
    else if(!sex && m==5)
      m = 8;

    return BaZhai.hxtg[m];
  }

  /**
   * ��լ�Ի����Լ��ڼ����õ�����
   * @param zhaiOrMing
   * @param gong
   * @return
   */
  public int getShen(int zhaiOrMing, int gong) {
    return BaZhai.yxp2[zhaiOrMing][gong];
  }

  /**
   * �õ�����
   * @param zm
   * @param gong
   * @return
   */
  public String getJiBing(int zm, int gong) {
    return BaZhai.jibing[zm][gong];
  }

  /**
   * �õ��ֻ�
   * @param zm
   * @param gong
   * @return
   */
  public String getZaiHuo(int zm, int gong) {
    return BaZhai.jibing[zm][gong];
  }

  /**
   * �ж��Ƿ�����������
   * @param gua
   * @return 1Ϊ��س 2Ϊ��س
   */
  public int isYangGua(int gua) {
    boolean bl =  gua == YiJing.QIAN ||
        gua == YiJing.GEN ||
        gua == YiJing.ZHEN ||
        gua == YiJing.KAN;
    return bl?1:2;
  }

  /**
   * Ǩ�ƣ�����װ��
   * jiuzhai ��2س ��լ����լ֮����������������س
   * ��3س �»���լ�ھ�լ֮������
   * zaoxiang ��1س ��լ��������֮����������һ��һ��������Ϊ��
   * xinzhaiwx ����   ��լ����������Ϊ�ԣ�����������Ϊ���ԡ�
   * year: ��һ������
   * @return
   */
  public String getQianYi(int year, boolean sex, int zaoxiang,
                             int jiuzhai, int xinzhaiwx) {
    String str = "";

    int dxsm = BaZhai.dxsg[getMingGua(year, sex)];
    if(dxsm==1) {
      if(isYangGua(jiuzhai)==1) {
        str += "\r\n    1. ��·��"+BaZhai.dxsm[dxsm]+
            "������լ������լ������������ҷ�֮"+BaZhai.fx[jiuzhai]+
            "������·�޸��������ۣ����˲ơ�������\r\n    "+
            "����취������լ֮���������������ס������ʮ��������Ǩ�ӡ�";
      }else{
        str += "\r\n    1. ��·��"+BaZhai.dxsm[dxsm]+
            "������լ������լ������Ǭ���޷�֮"+BaZhai.fx[jiuzhai]+
            "������·�и����Լ��ۣ������������ơ�";
      }
    }else{
      if(isYangGua(jiuzhai)==1) {
        str += "\r\n    1. ��·��"+BaZhai.dxsm[dxsm]+
            "������լ������լ������Ǭ���޷�֮"+BaZhai.fx[jiuzhai]+
            "������·�и����Լ��ۣ������������ơ�";
      }else{
        str += "\r\n    1. ��·��"+BaZhai.dxsm[dxsm]+
            "������լ������լ������������ҷ�֮"+BaZhai.fx[jiuzhai]+
            "������·�޸��������ۣ����˲ơ�������\r\n    "+
            "����취������լ֮������Ǭ������ס������ʮ��������Ǩ�ӡ�";
      }
    }

    str += "\r\n    2. ����װ�ԡ�";
    int zaoyao = isYangGua(zaoxiang);
    int jzyao = isYangGua(jiuzhai);
    int xg = YiJing.sanyao[zaoyao][jzyao][jzyao==1?2:1];
    str += "\r\n        ��һس����լ������"+BaZhai.fx[zaoxiang]+"Ϊ"+
        (zaoyao==1?"����":"����")+"��Ϊ"+YiJing.YAONAME3[zaoyao]+" "+YiJing.YAONAME[zaoyao];
    str += "\r\n        �ڶ�س����լ����լ֮"+BaZhai.fx[jiuzhai]+
        (jzyao==1?"����":"����")+"��Ϊ"+YiJing.YAONAME3[jzyao]+" "+YiJing.YAONAME[jzyao];
    str += "\r\n        ����س����լ��Ծ�լΪ"+(jzyao==1?"����":"����") +
        "��Ϊ"+YiJing.YAONAME3[jzyao==1?2:1]+" "+YiJing.YAONAME[jzyao==1?2:1];
    str += "\r\n        ��  �ԣ���լ������"+BaZhai.fx[xinzhaiwx]+",Ϊ"+
        YiJing.JINGGUANAME[xinzhaiwx];

    str += "\r\n    3. ���ԡ�";
    int sg = YiJing.sanyao[zaoyao][jzyao][jzyao==1?2:1];
    int sgdxsg = BaZhai.dxsg[sg];
    int xgdxsg = BaZhai.dxsg[xinzhaiwx];
    int shen = BaZhai.yxp2[xinzhaiwx][sg];
    if(sgdxsg == xgdxsg)
      str += "ͬΪ"+BaZhai.dxsg2[sgdxsg]+"������������"+YiJing.JINGGUANAME[xinzhaiwx]+
          "Ϊ����"+YiJing.JINGGUANAME[sg]+"��"+BaZhai.yxp[shen];
    else
      str += "����Ϊ"+BaZhai.dxsg2[sgdxsg]+"������Ϊ"+BaZhai.dxsg2[xgdxsg]+
          "������"+YiJing.JINGGUANAME[xinzhaiwx]+
          "Ϊ����"+YiJing.JINGGUANAME[sg]+"��"+BaZhai.yxp[shen];
    if(shen > 4) {
      str += "��Ϊ���񣬲�����";
      str += "\r\n    4. ��س�����˸Ķ�����������Դ˸ı伪�ס�";
    }else{
      str += "��Ϊ�����Լ��ۡ�";
      str += "\r\n    4. ��س���󡣼�������Ҫ�Ķ���";
    }

    return str;
  }
}