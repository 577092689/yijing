package org.boc.dao.sz;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuMain {
  private DaoCalendar daoC;
  private DaoYiJingMain daoY;

  public DaoSiZhuMain() {
    daoC = new DaoCalendar();
    daoY = new DaoYiJingMain();
  }

  public DaoCalendar getDaoCalendar() {
  	return daoC;
  }
  /**
   * ��С��
   * @param str
   */
  public void getQiZhuOut(StringBuffer str) {
    //��С��
    getXiaoYun();
    //�õ�7����������+����+С��+����
    getQiZhu();
    //��7�����ʮ��
    str.append("\n");
    str.append(SiZhu.XYNAME);
    str.append("\n");

    for(int i=SiZhu.LNBEGIN; i<SiZhu.QIZHU.length; i++) {
      str.append(daoY.getRepeats(" ", YiJing.INTER[4]));
      for(int j=1; j<SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          str.append(daoY.getRepeats(" ", YiJing.INTER[4] + 4));
        }else{
          str.append(getTGShen(SiZhu.QIZHU[i][j][1]) +
                     daoY.getRepeats(" ", YiJing.INTER[4] + 2));
        }
      }
      str.append("\n");
      //��7����֧
      String ll = "";
      if(i<10)
        ll = "0"+i+"�꣺";
      else
        ll = i + "�꣺";
      str.append(ll+daoY.getRepeats(" ", YiJing.INTER[4]-ll.getBytes().length));
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] > 0) {
          str.append(YiJing.TIANGANNAME[SiZhu.QIZHU[i][j][1]] +
                     YiJing.DIZINAME[SiZhu.QIZHU[i][j][2]] +
                     daoY.getRepeats(" ", YiJing.INTER[4]));
        }else{
          str.append(daoY.getRepeats(" ", YiJing.INTER[4]+4));
        }
      }
      str.append("\n");
      //��7����֧ѭ�ء�ѭ��ʮ��
      int[] zcg ;
      String zc, zs;
      String _zcOut="", _zsOut="";
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          _zcOut += pCenter(_zcOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
          _zsOut += pCenter(_zsOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
        }else{
          zc = "";
          zs = "";
          zcg = SiZhu.DZXUNCANG[SiZhu.QIZHU[i][j][2]];
          for (int k = 0; k < zcg.length; k++) {
            zc += YiJing.TIANGANNAME[zcg[k]];
            zs += getTGShen(zcg[k]);
          }
          _zcOut += pCenter(_zcOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), zc);
          _zsOut += pCenter(_zsOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), zs);
        }
      }
      str.append(_zcOut);
      str.append("\n");
      str.append(_zsOut);
      str.append("\n");
      //���7��������ڸ�֧�е���˥
      String gws="", _s="";
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
        }else{
          gws = SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.nz]];
          gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.yz]];
          gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.rz]];
          gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.sz]];
          if(j>=5)
            gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.QIZHU[i][j][2]]];
          _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), gws);
        }
      }
      str.append(_s);
      str.append("\n");
      //���7����֧����ɷ
      String tgShen ,dzShen;
      String _sShen ="";
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          _sShen += pCenter(_sShen.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
        }else{
          tgShen = getShenSha1(SiZhu.QIZHU[i][j][1], true, j);
          dzShen = getShenSha1(SiZhu.QIZHU[i][j][2], false, j);
          if(j<5) {
            tgShen += getShenSha2(j, true);
            dzShen += getShenSha2(j, false);
          }else{
            tgShen += getShenSha3(SiZhu.QIZHU[i][j][1], SiZhu.QIZHU[i][j][2], true);
            dzShen += getShenSha3(SiZhu.QIZHU[i][j][1], SiZhu.QIZHU[i][j][2], false);
          }
          _sShen += pCenter(_sShen.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), tgShen + "|" + dzShen);
        }
      }
      str.append(_sShen);
      str.append("\n");
      //�������������
    String nayin = "";
    String _sn = "",_ss = "";
    for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
      if(j==1) _ss = "������";
        if(j==2) _ss = "������";
          if(j==3) _ss = "������";
            if(j==4) _ss = "ʱ����";
              if(j==5) _ss = "���ˣ�";
              if(j==6) _ss = "С�ˣ�";
                if(j==7) _ss = "���꣺";
      if(SiZhu.QIZHU[i][j][1] == 0) {
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
      }else{
        nayin = _ss + SiZhu.NAYIN[SiZhu.QIZHU[i][j][1]][SiZhu.QIZHU[i][j][2]];
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * j + 2 + 4 * (j - 1), nayin);
      }
    }
    str.append(_sn);
    str.append("\n");
    str.append("\n");
    }
  }


  /**
   * �Ŵ���
   * @param str
   */
  public void getDaYunOut(StringBuffer str) {
    //�õ�����
    getDaYun();
    //�Ŵ������ʮ��
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[4]));
    for(int i=1; i<=8; i++) {
      str.append(getTGShen(SiZhu.DAYUN[i][1]) +
                 daoY.getRepeats(" ", YiJing.INTER[4] + 2));
    }
    str.append("\n");
    //�Ŵ��˸�֧
    str.append(SiZhu.DYNAME+daoY.getRepeats(" ", YiJing.INTER[4]-SiZhu.DYNAME.getBytes().length));
    for(int i=1; i<=8; i++) {
      str.append(YiJing.TIANGANNAME[SiZhu.DAYUN[i][1]] +
                 YiJing.DIZINAME[SiZhu.DAYUN[i][2]] +
                 daoY.getRepeats(" ", YiJing.INTER[4]));
    }
    str.append("\n");
    //�ŵ�֧ѭ�ء���ѭ��ʮ��
    int[] zcg ;
    String zc, zs;
    String _zcOut="", _zsOut="";
    for(int i=1; i<=8; i++) {
      zc="";
      zs="";
      zcg = SiZhu.DZXUNCANG[SiZhu.DAYUN[i][2]];
      for (int j = 0; j < zcg.length; j++) {
        zc += YiJing.TIANGANNAME[zcg[j]];
        zs += getTGShen(zcg[j]);
      }
      _zcOut += pCenter(_zcOut.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), zc);
      _zsOut += pCenter(_zsOut.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), zs);
    }
    str.append(_zcOut);
    str.append("\n");
    str.append(_zsOut);
    str.append("\n");
    //���������ڸ�֧�е���˥
    String gws="", _s="";
    for(int i=1; i<=8; i++) {
      gws = SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.nz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.yz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.rz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.sz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.DAYUN[i][2]]];
      _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), gws);
    }
    str.append(_s);
    str.append("\n");
    //�����֧����ɷ
    String tgShen ,dzShen;
    String _sShen ="";
    for(int i=1; i<=8; i++) {
      tgShen = getShenSha1(SiZhu.DAYUN[i][1], true, i);
      tgShen += getShenSha3(SiZhu.DAYUN[i][1],SiZhu.DAYUN[i][2], true);
      dzShen = getShenSha1(SiZhu.DAYUN[i][2], false, i);
      dzShen += getShenSha3(SiZhu.DAYUN[i][1],SiZhu.DAYUN[i][2], false);
      _sShen += pCenter(_sShen.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), tgShen+"|"+dzShen);
    }
    str.append(_sShen);
    str.append("\n");
    //�õ����˵�����,�����ۺ�һ��ƣ���һ���ۺ��ĸ��£�һСʱ�ۺ�����
    //�����ֱ��¼����֣���������������
    if(Calendar.YEARN>0 && Calendar.MONTHN>0 && Calendar.DAYN>0 && Calendar.HOUR>0) {
      int days = getQiYunSui();
      int nian = days / 3;
      int yue = days % 3 * 4;
      String _s1 = "", _s2 = "";
      for (int i = 1; i <= 8; i++) {
        if (yue > 0)
          _s1 = nian + (i - 1) * 10 + "��" + yue + "��";
        else
          _s1 = nian + (i - 1) * 10 + "��";
        _s2 +=
            pCenter(_s2.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1),
                    _s1);
      }
      str.append(_s2);
      str.append("\n");
    }
    //�������������
    String nayin = "";
    String _sn = "",_ss = "";
    for(int i=1; i<SiZhu.DAYUN.length; i++) {
      if(SiZhu.DAYUN[i][1] == 0) {
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        nayin = _ss + SiZhu.NAYIN[SiZhu.DAYUN[i][1]][SiZhu.DAYUN[i][2]];
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), nayin);
      }
    }
    str.append(_sn);
    str.append("\n");

  }

  /**
   * �õ���������
   */
  public int getQiYunSui() {
    int days = daoC.getDiffDates();
    SiZhu.QIYUNSUI = days / 3;
    return days;
  }

  /**
   * ������������̥Ԫ
   * @param str
   */
  public void getSiZhuTaiMingOut(StringBuffer str) {
    //�õ�����
    int[][] liu = getSixZhu();
    //���������ʮ��
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[4]));
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        str.append(daoY.getRepeats(" ", YiJing.INTER[4] + 4));
      }else{
        if(i==3)
          str.append(getTGShen(0) +
                     daoY.getRepeats(" ", YiJing.INTER[4] + 2));
        else
          str.append(getTGShen(liu[i][1]) +
                     daoY.getRepeats(" ", YiJing.INTER[4] + 2));
      }
    }
    str.append("\n");
    //��������֧
    str.append(SiZhu.SZNAME+daoY.getRepeats(" ", YiJing.INTER[4]-SiZhu.DYNAME.getBytes().length));
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        str.append(daoY.getRepeats(" ", YiJing.INTER[4]+4));
      }else{
        str.append(YiJing.TIANGANNAME[liu[i][1]] +
                   YiJing.DIZINAME[liu[i][2]] +
                   daoY.getRepeats(" ", YiJing.INTER[4]));
      }
    }
    str.append("\n");
    //�ŵ�֧ѭ�ء���ѭ��ʮ��
    int[] zcg ;
    String zc, zs;
    String _zcOut="", _zsOut="";
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        _zcOut += pCenter(_zcOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
        _zsOut += pCenter(_zsOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        zc = "";
        zs = "";
        zcg = SiZhu.DZXUNCANG[liu[i][2]];
        for (int j = 0; j < zcg.length; j++) {
          zc += YiJing.TIANGANNAME[zcg[j]];
          zs += getTGShen(zcg[j]);
        }
        _zcOut += pCenter(_zcOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), zc);
        _zsOut += pCenter(_zsOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), zs);
      }
    }
    str.append(_zcOut);
    str.append("\n");
    str.append(_zsOut);
    str.append("\n");
    //���������ڸ�֧�е���˥
    String gws="", _s="";
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        gws = SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.nz]];
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.yz]];
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.rz]];
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.sz]];
        if(i>=5)
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][liu[i][2]]];
        _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), gws);
      }
    }
    str.append(_s);
    str.append("\n");
    //�����֧����ɷ
    String tgShen ,dzShen;
    String _sShen ="";
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        _sShen += pCenter(_sShen.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        tgShen = getShenSha1(liu[i][1], true, i);
        tgShen += getShenSha2(i, true);
        dzShen = getShenSha1(liu[i][2], false, i);
        dzShen += getShenSha2(i, false);
        _sShen += pCenter(_sShen.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), tgShen + "|" + dzShen);
      }
    }
    str.append(_sShen);
    str.append("\n");
    //�������������
    String nayin = "";
    String _sn = "",_ss = "";
    for(int i=1; i<liu.length; i++) {
      if(i==1) _ss = "������";
        if(i==2) _ss = "������";
          if(i==3) _ss = "������";
            if(i==4) _ss = "ʱ����";
              if(i==6) _ss = "̥Ԫ��";
                if(i==7) _ss = "������";
      if(liu[i][1] == 0) {
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        nayin = _ss + SiZhu.NAYIN[liu[i][1]][liu[i][2]];
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), nayin);
      }
    }
    str.append(_sn);
    str.append("\n");


  }

  /**
   * ���������
   * @param type ����Ϊ�棬����Ϊ��
   * @param yuen ֻ��Ϊ����ʱ�˲�������Ч
   * @param isMan �����˻���Ů��
   */
  public void getDateOut(int y, int m, int d, int h, int mi,
                         boolean type, boolean yun, boolean isMan,
                         int sheng, int shi,
                         StringBuffer str) {
    daoC.calculate(y, m, d, h, mi, type, yun, sheng, shi);
    getTaiYuan();
    getMingGong();
    SiZhu.ISMAN = isMan;
    SiZhu.sex = isMan ? "Ǭ�죺" : "���죺";
    //SiZhu.sex += "("+SiZhu.NAYIN[SiZhu.ng][SiZhu.nz]+")";
    String nayin = SiZhu.NAYIN[SiZhu.ng][SiZhu.nz];
    if(nayin.indexOf("��")!=-1) SiZhu.NAYININT = 1;
      if(nayin.indexOf("ľ")!=-1) SiZhu.NAYININT = 2;
        if(nayin.indexOf("ˮ")!=-1) SiZhu.NAYININT = 3;
          if(nayin.indexOf("��")!=-1) SiZhu.NAYININT = 4;
            if(nayin.indexOf("��")!=-1) SiZhu.NAYININT = 5;
    str.append(SiZhu.sex);
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(SiZhu.yinli);
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(SiZhu.yangli);
    str.append("\n");
  }

  /**
   * ���������
   * @param type ����Ϊ�棬����Ϊ��
   * @param yuen ֻ��Ϊ����ʱ�˲�������Ч
   * @param isMan �����˻���Ů��
   */
  public void getDateOut(int y, int m, int d, int h, int mi,
                         boolean type, boolean yun, boolean isMan,
                         int sheng, int shi) {
    daoC.calculate(y, m, d, h, mi, type, yun, sheng, shi);
    SiZhu.ISMAN = isMan;
  }


  /**
   * ���������
   * @param type ����Ϊ�棬����Ϊ��
   * @param yuen ֻ��Ϊ����ʱ�˲�������Ч
   * @param isMan �����˻���Ů��
   */
  public void getDateOut(int ng, int nz, int yg, int yz, int rg, int rz,
                         int sg, int sz, boolean yun, boolean isMan,
                         StringBuffer str) {
    Calendar.YUN = yun;
    SiZhu.ISMAN = isMan;
    SiZhu.sex = isMan ? "Ǭ�죺" : "���죺";
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;
    getTaiYuan();
    getMingGong();
    String nayin = SiZhu.NAYIN[SiZhu.ng][SiZhu.nz];
    if(nayin.indexOf("��")!=-1) SiZhu.NAYININT = 1;
      if(nayin.indexOf("ľ")!=-1) SiZhu.NAYININT = 2;
        if(nayin.indexOf("ˮ")!=-1) SiZhu.NAYININT = 3;
          if(nayin.indexOf("��")!=-1) SiZhu.NAYININT = 4;
            if(nayin.indexOf("��")!=-1) SiZhu.NAYININT = 5;
    str.append(SiZhu.sex);
    str.append("\n");
  }

  /**
   * ���������
   * @param type ����Ϊ�棬����Ϊ��
   * @param yuen ֻ��Ϊ����ʱ�˲�������Ч
   * @param isMan �����˻���Ů��
   */
  public void getDateOut(int ng, int nz, int yg, int yz, int rg, int rz,
                         int sg, int sz, boolean yun, boolean isMan) {
    Calendar.YUN = yun;
    SiZhu.ISMAN = isMan;
    SiZhu.sex = isMan ? "Ǭ�죺" : "���죺";
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;
  }

  /**
   * �������
   * @return
   */
  private String pCenter(int left, int center, String str) {
    int len = str.getBytes().length;
    return daoY.getRepeats(" ",center-left-len/2)+str;
  }

  /**
   * �Ըɻ�֧��������ɷ������ȥ��ͬһ֧����������ͬ����ɷ
   * 1Ϊ������Ů��2Ϊ��Ů����
   * @param gz ��ɻ��֧
   * @param type trueΪ�ɡ�falseΪ֧
   * @param local �ɻ�֧��λ��
   */
  private String getShenSha1(int gz, boolean type, int local) {
    String str = "";
    int sex = getSexChar();

    if(type) {
      if(SiZhu.TIANDE[1][SiZhu.yz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.YUEDE[SiZhu.yz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.DEXIU[SiZhu.yz][1][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.DEXIU[SiZhu.yz][2][gz]) str += "��"+SiZhu.SPLIT;
      return str;
    }else{
      if(SiZhu.TIANYI[SiZhu.rg][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.TAIJI[SiZhu.rg][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.TIANDE[2][SiZhu.yz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.FUXING[SiZhu.ng][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.FUXING[SiZhu.rg][gz] && str.indexOf("��")==-1) str += "��"+SiZhu.SPLIT;
      if(SiZhu.WENCANG[SiZhu.ng][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.WENCANG[SiZhu.rg][gz] && str.indexOf("��")==-1) str += "��"+SiZhu.SPLIT;
      if(SiZhu.GUOYIN[SiZhu.ng][gz]) str += "ӡ"+SiZhu.SPLIT;
      if(SiZhu.WENCANG[SiZhu.rg][gz] && str.indexOf("ӡ")==-1) str += "ӡ"+SiZhu.SPLIT;
      if(SiZhu.YIMA[SiZhu.nz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.YIMA[SiZhu.rz][gz] && str.indexOf("��")==-1) str += "��"+SiZhu.SPLIT;
      if(SiZhu.HUAGAI[SiZhu.nz][gz] && local!=1) str += "��"+SiZhu.SPLIT;
      if(SiZhu.HUAGAI[SiZhu.rz][gz] && str.indexOf("��")==-1 && local!=3) str += "��"+SiZhu.SPLIT;
      if(SiZhu.JIANG[SiZhu.nz][gz] && local!=1) str += "��"+SiZhu.SPLIT;
      if(SiZhu.JIANG[SiZhu.rz][gz] && str.indexOf("��")==-1 && local!=3) str += "��"+SiZhu.SPLIT;
      if(SiZhu.JINYU[SiZhu.rg][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.TIANYI1[SiZhu.yz][gz]) str += "ҽ"+SiZhu.SPLIT;
      if(SiZhu.LUSHEN[SiZhu.rg][gz]) str += "»"+SiZhu.SPLIT;
      if(SiZhu.YANGREN[SiZhu.rg][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.WANGSHEN[SiZhu.nz][gz]) str += "��";
      if(SiZhu.WANGSHEN[SiZhu.rz][gz] && str.indexOf("��")==-1) str += "��"+SiZhu.SPLIT;
      if(SiZhu.JIESHA[SiZhu.nz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.JIESHA[SiZhu.rz][gz] && str.indexOf("��")==-1) str += "��"+SiZhu.SPLIT;
      if(SiZhu.ZAISHA[SiZhu.nz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.GOUSHA[sex][SiZhu.nz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.JIAOSHA[sex][SiZhu.nz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.TIANLUO[SiZhu.NAYININT][1][gz][1]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.TIANLUO[SiZhu.NAYININT][2][gz][1]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.YUANCHEN[sex][SiZhu.nz][gz]) str += "Ԫ"+SiZhu.SPLIT;
      if(daoY.isXunKong(gz,SiZhu.rg,SiZhu.rz)) str += "��"+SiZhu.SPLIT;
      if(SiZhu.XIANCI[SiZhu.nz][gz]) str += "��"+SiZhu.SPLIT;
      if(SiZhu.XIANCI[SiZhu.rz][gz] && str.indexOf("��")==-1) str += "��"+SiZhu.SPLIT;
    }

    return str;
  }

  /**
   * ��������ɷ,רΪ��������
   * @param zhu 1Ϊ������2������3������4ʱ��
   * @param type trueΪ�ɡ�falseΪ֧
   */
  private String getShenSha2(int zhu, boolean type) {
    String str = "";

    if(zhu==1) {
      if(type) {
        if (SiZhu.SHANJI[SiZhu.ng][SiZhu.yg][SiZhu.rg]) {
          if (str.indexOf("��") == -1)
            str += "��"+SiZhu.SPLIT;
        }
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.ng][SiZhu.nz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.ng][SiZhu.nz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.ng][SiZhu.nz] &&
            str.indexOf("��") == -1)
          str += "��"+SiZhu.SPLIT;

      }else{
        if (SiZhu.SHANJI[SiZhu.nz][SiZhu.yz][SiZhu.rz]) {
          if (str.indexOf("��") == -1)
            str += "��"+SiZhu.SPLIT;
        }
        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
      }
    }else if(zhu==2) {
      if(type) {
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.yg][SiZhu.yz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.yg][SiZhu.yz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.yg][SiZhu.yz] &&
            str.indexOf("��") == -1)
          str += "��"+SiZhu.SPLIT;

      }else{
        if (SiZhu.SHANJI[SiZhu.yz][SiZhu.rz][SiZhu.sz]) {
          if (str.indexOf("��") == -1)
            str += "��"+SiZhu.SPLIT;
        }
        if (SiZhu.SHANJI[SiZhu.yg][SiZhu.rg][SiZhu.sg]) {
          if (str.indexOf("��") == -1)
            str += "��"+SiZhu.SPLIT;
        }
        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.yz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.yz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.yz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.yz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GCGX[1][SiZhu.nz][SiZhu.yz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GCGX[2][SiZhu.nz][SiZhu.yz])
          str += "��"+SiZhu.SPLIT;
      }
    }else if(zhu==3) {
      if(type) {
        if (SiZhu.KUIGANG[SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.rg][SiZhu.rz] &&
          str.indexOf("��") == -1)
        str += "��"+SiZhu.SPLIT;
        if (SiZhu.JINSHEN[SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GONGLU[SiZhu.rg][SiZhu.rz][SiZhu.sg][SiZhu.sz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANSE[SiZhu.yz][SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.DABAI[SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GULUAN[SiZhu.rg][SiZhu.rz] && SiZhu.GULUAN[SiZhu.sg][SiZhu.sz])
          str += "�"+SiZhu.SPLIT;
        if (SiZhu.YYCACUO[SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.SIFEI[SiZhu.yz][SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;

      }else{

        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.rz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.rz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.rz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.rz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GCGX[1][SiZhu.nz][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GCGX[2][SiZhu.nz][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
      }
    }else if(zhu==4) {
      if(type) {
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.sg][SiZhu.sz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.sg][SiZhu.sz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.sg][SiZhu.sz] &&
            str.indexOf("��") == -1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.JINSHEN[SiZhu.rg][SiZhu.rz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GONGLU[SiZhu.rg][SiZhu.rz][SiZhu.sg][SiZhu.sz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GULUAN[SiZhu.rg][SiZhu.rz] && SiZhu.GULUAN[SiZhu.sg][SiZhu.sz])
          str += "�"+SiZhu.SPLIT;
      }else{
        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.sz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.sz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.sz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.sz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.nz] && str.indexOf("��")==-1)
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GCGX[1][SiZhu.nz][SiZhu.sz])
          str += "��"+SiZhu.SPLIT;
        if (SiZhu.GCGX[2][SiZhu.nz][SiZhu.sz])
          str += "��"+SiZhu.SPLIT;
      }
    }
    return str;
  }

  /**
   * ��������ɷ,רΪ���˻�С�������������ò���
   * @param zhu 1Ϊ������2������3������4ʱ��
   */
  private String getShenSha3(int g, int z, boolean type) {
    String str = "";

    if(type) {
      if (SiZhu.JINSHEN[g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.TIANSE[SiZhu.yz][g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.DABAI[g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.YYCACUO[g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.SIFEI[SiZhu.yz][g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.KUIGANG[g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.XUETANG[SiZhu.NAYININT][g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.CIGUAN[SiZhu.ng][g][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.CIGUAN[SiZhu.rg][g][z] &&
          str.indexOf("��") == -1)
        str += "��"+SiZhu.SPLIT;
    }else{
      if (SiZhu.TIANLUO[0][1][z][SiZhu.sz] && str.indexOf("��")==-1)
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.sz] && str.indexOf("��")==-1)
        str += "��" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][1][z][SiZhu.nz] && str.indexOf("��")==-1)
        str += "��" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.nz] && str.indexOf("��")==-1)
        str += "��" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][1][z][SiZhu.yz] && str.indexOf("��")==-1)
        str += "��" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.yz] && str.indexOf("��")==-1)
        str += "��" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][1][z][SiZhu.rz] && str.indexOf("��")==-1)
        str += "��" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.rz] && str.indexOf("��")==-1)
        str += "��" + SiZhu.SPLIT;
      if (SiZhu.GCGX[1][SiZhu.nz][z])
        str += "��"+SiZhu.SPLIT;
      if (SiZhu.GCGX[2][SiZhu.nz][z])
        str += "��"+SiZhu.SPLIT;
    }
    return str;
  }


  /**
   * �õ�1Ϊ������Ů��2Ϊ������Ů
   * @return
   */
  public int getSexChar() {
    int zyy = SiZhu.ZIYINYANG[SiZhu.nz];
    int sex = 0;

    if((zyy == 1 && SiZhu.ISMAN) || (zyy == 0 && !SiZhu.ISMAN)) {
      sex = 1;
    }else {
      sex = 2;
    }
    return sex;
  }

  /**
   * �Ŵ���
   */
  public void getDaYun() {
    int sex = getSexChar();
    int j=0;
    int g = SiZhu.yg+10;
    int z = SiZhu.yz+12;

    if(sex==1) {
      while(++j<=8) {
        SiZhu.DAYUN[j][1] = (++g) % 10 == 0 ? 10 : g % 10;
        SiZhu.DAYUN[j][2] = (++z) % 12 == 0 ? 12 : z % 12;
      }
    }else{
      while(++j<=8) {
        SiZhu.DAYUN[j][1] = (--g) % 10 == 0 ? 10 : g % 10;
        SiZhu.DAYUN[j][2] = (--z) % 12 == 0 ? 12 : z % 12;
      }
    }
  }

  /**
   * ��С��
   */
  public void getXiaoYun() {
    int sex = getSexChar();
    int j=0;
    int g = SiZhu.sg+100;
    int z = SiZhu.sz+120;

    if(sex==1) {
      while(++j<=SiZhu.LNEND-1) {
        SiZhu.XIAOYUN[j][1] = (++g) % 10 == 0 ? 10 : g % 10;
        SiZhu.XIAOYUN[j][2] = (++z) % 12 == 0 ? 12 : z % 12;
      }
    }else{
      while(++j<=SiZhu.LNEND-1) {
        SiZhu.XIAOYUN[j][1] = (--g) % 10 == 0 ? 10 : g % 10;
        SiZhu.XIAOYUN[j][2] = (--z) % 12 == 0 ? 12 : z % 12;
      }
    }
  }

  /**
   * ��������ϸ������
   * ���� ���� ���� ʱ�� ���� С�� ����
   * С��Ϊ���꣬������������Ϊ׼
   */
  public void getQiZhu() {
    int dy = 0;

    for(int i=1; i<SiZhu.LNEND; i++) {
      SiZhu.QIZHU[i][1][1] = SiZhu.ng;
      SiZhu.QIZHU[i][1][2] = SiZhu.nz;

      SiZhu.QIZHU[i][2][1] = SiZhu.yg;
      SiZhu.QIZHU[i][2][2] = SiZhu.yz;

      SiZhu.QIZHU[i][3][1] = SiZhu.rg;
      SiZhu.QIZHU[i][3][2] = SiZhu.rz;

      SiZhu.QIZHU[i][4][1] = SiZhu.sg;
      SiZhu.QIZHU[i][4][2] = SiZhu.sz;

      if(i -1 >= SiZhu.QIYUNSUI) {
        dy = ((i-1-SiZhu.QIYUNSUI)/10)+1;
        SiZhu.QIZHU[i][5][1] = SiZhu.DAYUN[dy][1];
        SiZhu.QIZHU[i][5][2] = SiZhu.DAYUN[dy][2];
      }else{
        SiZhu.QIZHU[i][5][1] = 0;
        SiZhu.QIZHU[i][5][2] = 0;
      }

      SiZhu.QIZHU[i][6][1] = SiZhu.XIAOYUN[i][1];
      SiZhu.QIZHU[i][6][2] = SiZhu.XIAOYUN[i][2];;

      SiZhu.QIZHU[i][7][1] = (SiZhu.ng + i - 1) % 10 == 0 ? 10 : (SiZhu.ng + i - 1) % 10;
      SiZhu.QIZHU[i][7][2] = (SiZhu.nz + i - 1) % 12 == 0 ? 12 : (SiZhu.nz + i - 1) % 12;
    }
  }

  /**
   * ������
   * ���� ���� ���� ʱ�� ̥Ԫ ����
   * С��Ϊ���꣬������������Ϊ׼
   */
  public int[][] getSixZhu() {
    int[][] liuzhu = new int[8][3];

    liuzhu[1][1] = SiZhu.ng;
    liuzhu[1][2] = SiZhu.nz;

    liuzhu[2][1] = SiZhu.yg;
    liuzhu[2][2] = SiZhu.yz;

    liuzhu[3][1] = SiZhu.rg;
    liuzhu[3][2] = SiZhu.rz;

    liuzhu[4][1] = SiZhu.sg;
    liuzhu[4][2] = SiZhu.sz;

    liuzhu[6][1] = SiZhu.tyg;
    liuzhu[6][2] = SiZhu.tyz;

    liuzhu[7][1] = SiZhu.mgg;
    liuzhu[7][2] = SiZhu.mgz;

    return liuzhu;
  }

  /**
   * �õ������
   * @param g
   */
  public String getTGShen(int g) {
    return SiZhu.SHISHEN[SiZhu.TGSHISHEN[SiZhu.rg][g]];
  }

  /**
   * �õ�̥Ԫ
   */
  public void getTaiYuan() {
    SiZhu.tyg = (SiZhu.yg+1)%10 == 0 ? 10 : (SiZhu.yg+1)%10;
    SiZhu.tyz = (SiZhu.yz+3)%12 == 0 ? 12 : (SiZhu.yz+3)%12;
  }

  /**
   * �õ�����
   * ���֧ <=4Ϊ4-y, >4Ϊ4-y+12
   */
  public void getMingGong() {
    //������������
    int[] mg = {0,1,12,11,10,9,8,7,6,5,4,3,2,1,0};
    //�õ�����(Ӧ��ũ���Ľ����·�),����ʱ����������˳����î
    if(SiZhu.yz >= 3) SiZhu.mgz = mg[SiZhu.yz - 2];
    if(SiZhu.yz < 3) SiZhu.mgz = mg[SiZhu.yz + 10];
    if(SiZhu.sz <= 4)
      SiZhu.mgz += 4 - SiZhu.sz;
    else
      SiZhu.mgz += 16 - SiZhu.sz;
    SiZhu.mgz = SiZhu.mgz%12==0?12:SiZhu.mgz%12;
    //�õ�������
    int z = SiZhu.mgz;
    int g = SiZhu.yueByNian[SiZhu.ng];
    if(z < YiJing.YIN)
      z += 12;
    SiZhu.mgg = (g+z-3)%10 == 0 ? 10 : (g+z-3)%10;
  }

}
