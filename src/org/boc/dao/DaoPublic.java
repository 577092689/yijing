package org.boc.dao;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.boc.db.Calendar;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.util.Messages;

public class DaoPublic {

  private DaoCalendar daoc;
  private static String path;

  public DaoPublic() {
    daoc = new DaoCalendar();
  }

  /**
   * �Ƿ�������Ů
   */
  public boolean isYnanYinlv(int ng, boolean sex) {
    if ( (ng % 2 == 1 && sex) || (ng % 2 == 0 && !sex))
      return true;

    return false;
  }

  /**
   * ���ַ���ת����Unicode-utf-8����
   * @param args String[]
   */
  public static String String2Unicode(String str) {
    try {
      return new String(str.getBytes("utf-16"));
    }
    catch (UnsupportedEncodingException ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
      return null;
    }
  }

  /**
   * ���ַ���ת����ASCII����
   * @param args String[]
   */
  public static String String2Ascii(String str) {
    String s = "";
    String _s = null;
    try {
      byte[] b = str.getBytes("utf-8");
      for (int i = 0; i < b.length; i++) {
        _s = Integer.toHexString( (int) b[i]);
        s += "%" + _s.substring(_s.length() - 2);
      }
      //System.out.println(s + "\t");
      //System.out.println(new DaoPublic().unicode2String(s));
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
    }
    return s;
  }

  /**
   * ��unicode��utf-8ת�����ַ���
   * @param s String
   * @return String
   */
  public static String unicode2String(String s) {
    String s1 = null;
    String s2 = null;
    int i=0,j=0;

    try {
      while(s.indexOf("%")!=-1) {
        i = s.indexOf("%")+1;
        j = i+8;
        s1 = s.substring(0,i-1);
        s2 = s.substring(j);
        s = s.substring(i,j);

        s = s1+
            new String(new byte[] { (byte) Integer.parseInt(s.substring(0, 2),16),
                       (byte) Integer.parseInt(s.substring(3, 5), 16),
                       (byte) Integer.parseInt(s.substring(6, 8), 16)}, "utf-8")+
            s2;
      }
    } catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
    }

    return s;
  }

  /**
   * ��ĳ����Թ�
   * @param z int
   * @return int
   */
  public int getDuiGong(int z) {
    int[] dg = {0,YiJing.WUZ,YiJing.WEI,YiJing.SHEN,YiJing.YOU,
        YiJing.XU,YiJing.HAI,YiJing.ZI,YiJing.CHOU,
        YiJing.YIN,YiJing.MAO,YiJing.CHEN,YiJing.SI};
    return dg[z];
  }

  /**
   * x1����y����x1������x2��y��˳��
   */
  public int getNiShu(int x1, int y, int x2) {
    return (y+(x1-x2)+120)%12==0?12:(y+(x1-x2)+120)%12;
  }

  /**
   * x1����y����x1˳����x2��y��˳��
   */
  public int getShunShu(int x1, int y, int x2) {
    return (y+x2+120-x1)%12==0?12:(y+x2+120-x1)%12;
  }

  /**
   * �Ե�֧˳������֧12���ڣ����ʮ����
   * @param dz1 int
   * @param tg int
   * @param dz22 int
   * @return int
   */
  public int getShunShu2(int dz1, int tg, int dz2) {
    int dz = dz2-dz1<0?dz2+12-dz1:dz2-dz1;
    return (tg+dz)%10==0?10:(tg+dz)%10;
  }


  /**
   * �����ظ���Ԫ��
   * @param str
   * @param len
   * @return
   */
  public String getRepeats(char c, int len) {
    String retStr = "";
    for(int i = 0; i<len; i++) {
      retStr += c;
    }
    return retStr;
  }


  /**
   * �õ���׼��ͷ����Ϣ
   * @return String
   */
  public String getStandHead(int y, int m, int d, int h, int mi,
                             boolean type, boolean yun,
                             int sheng, int shi) {
    int[] sunTime = getSunTime(h, mi, sheng, shi);
    int[] dt = daoc.getSiZhu(y,m,d,sunTime[0],sunTime[1],yun,type);
    StringBuffer str = new StringBuffer();

    str.append("\r\n    ��  ���� ");
    str.append(dt[9]+"-"+dt[10]+"-"+dt[11]+" "+h+":"+mi);
    str.append("\r\n    ũ  ���� ");
    str.append(dt[12]+"��"+(dt[0]==1? "��":"") +dt[13]+"�³�"+dt[14]);
    str.append(" "+h+":"+mi+" "+ Calendar.WEEKNAME[dt[18]]);
    if(sheng>=0 && shi>0) {
      str.append("\r\n    ��  ���� ");
      str.append(Calendar.YEARN1 + "��" + (dt[0]==1 ? "��" : ""));
      str.append(dt[13] + "�³�" +dt[14] + " " + sunTime[0] + ":" + sunTime[1]);
      str.append(" " + Calendar.WEEKNAME[dt[18]]);
    }
    str.append("\r\n    ��  ֧�� ");
    str.append(YiJing.TIANGANNAME[dt[1]]);
    str.append(YiJing.DIZINAME[dt[2]]);
    str.append("  ");
    str.append(YiJing.TIANGANNAME[dt[3]]);
    str.append(YiJing.DIZINAME[dt[4]]);
    str.append("  ");
    str.append(YiJing.TIANGANNAME[dt[5]]);
    str.append(YiJing.DIZINAME[dt[6]]);
    str.append("  ");
    str.append(YiJing.TIANGANNAME[dt[7]]);
    str.append(YiJing.DIZINAME[dt[8]]);

    str.append("\r\n    Ѯ  �գ� ");
    str.append(getXunKongOut(dt[1],dt[2])[0]);
    str.append(getXunKongOut(dt[1],dt[2])[1]);
    str.append("  ");
    str.append(getXunKongOut(dt[3],dt[4])[0]);
    str.append(getXunKongOut(dt[3],dt[4])[1]);
    str.append("  ");
    str.append(getXunKongOut(dt[5],dt[6])[0]);
    str.append(getXunKongOut(dt[5],dt[6])[1]);
    str.append("  ");
    str.append(getXunKongOut(dt[7],dt[8])[0]);
    str.append(getXunKongOut(dt[7],dt[8])[1]);

    int[] jq = this.getJieQi(y,m,d,h,mi,yun,type);
    str.append("\r\n    ��  �ڣ� ");
    str.append(QiMen.JIEQI24[jq[1]]);
    str.append(" ");
    str.append(jq[2]+"��");
    str.append(isYunJieqi(jq[2], jq[1]) ? "��":"");
    str.append(jq[3]+"�³�"+ jq[4] + "��"+ jq[5] +"ʱ"+ jq[6] +"��");

    str.append("\r\n    ��  �ڣ� ");
    str.append(QiMen.JIEQI24[jq[7]]);
    str.append(" ");
    str.append(jq[8]+"��");
    str.append(isYunJieqi(jq[8], jq[7]) ? "��":"");
    str.append(jq[9]+"�³�"+ jq[10] + "��"+ jq[11] +"ʱ"+ jq[12] +"��");

    return str.toString();
  }

  public int[] getSunTime(int h, int mi, int sheng, int shi) {
    if(sheng<=0 && shi<=0)
      return new int[]{h,mi};
    int sunTrue = (Calendar.jingdu[sheng][shi] - 120 * 60) * 4;
    sunTrue += Calendar.zpsc[Calendar.MONTHP][Calendar.DAYP];

    int h1 = (h*60*60+mi*60+sunTrue)/60/60;
    int mi1 = (h*60*60+mi*60+sunTrue)/60 - h1*60;
    return new int[]{h1, mi1};
  }

  /**
   *�õ���ǰclass��·��
   * @return
   */
  public String getCurrentClassPath(String cls) {
    URL url = null;
    try {
      if(cls==null) {
        url = this.getClass().getResource("");
      }else{
        url = Class.forName(cls).getResource("");
      }
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("DaoPublic("+ex+")");
    }
    String path = null;
    if(url==null)
      System.err.println("��ȡxml�ļ�����");
    else
        path = url.getPath();
    //System.err.println("path="+path);
    return path;
  }

  /**
   *�õ���ǰclass��·��
   * @return
   */
  public static String getClassRootPath() {
    if(path==null) {
      URL url = null;
      try {
        url = new DaoPublic().getClass().getResource("/");
      	if(url==null) path = "./";
      	else
      		path = unicode2String(url.getPath());
      }
      catch (Exception ex) {
      	ex.printStackTrace();
        Messages.error("DaoPublic("+ex+")");
      }
    }
    return path;
  }



  /**
   * �ɸ�֧�õ���һ��
   */
  public int getYear(int g,int z) {
    int y = new java.util.Date().getYear()+1900;
    int cg = (y-Calendar.IYEAR+Calendar.IYEARG)%10==0?10:(y-Calendar.IYEAR+Calendar.IYEARG)%10;
    int cz = (y-Calendar.IYEAR+Calendar.IYEARZ)%12==0?12:(y-Calendar.IYEAR+Calendar.IYEARZ)%12;
    int cdays = getJiaziDay(cg,cz);
    int days = getJiaziDay(g,z);
    return y-(cdays-days<0?cdays-days+60:cdays-days);
  }

  /**
   * �õ�������ӵ�����
   */
  private int getJiaziDay(int g, int z) {
    int[] xun = {0, 0, 50,0,40,0,30,0,20,0,10};
    return xun[(z-g+12)%12] + g - YiJing.JIA;
  }

  /**
   * �ж�xyz�����Ƿ���ָ���Ľ�
   */
  public boolean xyzMaking(int x,int y,int z,int min,int max,int sum) {
    if(x+y+z==sum && getMin(x,y,z)==min && getMax(x,y,z)==max)
      return true;
    return false;
  }

  /**
   * ������С��
   */
  public int getMin(int i1,int i2,int i3){
    int i_1 = Math.min(i1,i2);
    int i_2 = Math.min(i1,i3);
    return Math.min(i_1,i_2);
  }
  public int getMin(int i1,int i2,int i3,int i4){
    int i_1 = Math.min(i1,i2);
    int i_2 = Math.min(i3,i4);
    return Math.min(i_1,i_2);
  }

  /**
   * ��������
   */
  public int getMax(int i1,int i2,int i3){
    int i_1 = Math.max(i1,i2);
    int i_2 = Math.max(i1,i3);
    return Math.max(i_1,i_2);
  }
  public int getMax(int i1,int i2,int i3,int i4){
    int i_1 = Math.max(i1,i2);
    int i_2 = Math.max(i3,i4);
    return Math.max(i_1,i_2);
  }

  /**
   * �õ�Ѯ��
   */
  public String[] getXunKongOut(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    int zi1 = xk/100;
    int zi2 = xk - zi1*100;

    String[] str = new String[2];
    str[0] = YiJing.DIZINAME[zi1];
    str[1] = YiJing.DIZINAME[zi2];
    return str;
  }
  public int[] getXunKong(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    return new int[]{xk/100, xk%100};
  }


  public String checks(String _y, String _m, String _d, String hms,
                       boolean isYY, boolean isYun) {
    String err = null;
    int yg=0,yz=0,mg=0,mz=0,dg=0,dz=0,hg=0,hz=0;
    int year=0, month=0, day=0, hour=0, minute=0, second=0;
    boolean typeSjOrgz = false;

    if(_y==null ||  _y.trim().equals("")) {
      return "��ݿ�����ʱ����֧������ʱ�������" + Calendar.MAXYEAR + "��" +
          Calendar.IYEAR +"֮�䣬��֧�����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }
    if(_y.indexOf(",")==-1){
      typeSjOrgz = false;
      year = _y.equals("")?0:Integer.valueOf(_y).intValue();
      if (year > Calendar.MAXYEAR || year < Calendar.IYEAR)
        return "��ݱ�����" + Calendar.MAXYEAR + "��" + Calendar.IYEAR +"֮��";
    }else{
      typeSjOrgz = true;
      String _y1 = _y.substring(0,_y.indexOf(","));
      String _y2 = _y.substring(_y.indexOf(",")+1);
      yg = Integer.valueOf(_y1).intValue();
      yz = Integer.valueOf(_y2).intValue();
      if(yg<=0 || yg>10 || yz<=0 || yz>12)
        return "��ݱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    if(_m==null ||  _m.trim().equals("")) {
      return "�·ݿ�����ʱ����֧������ʱ�������1��12֮�䣬��֧�����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }
    if(!typeSjOrgz){
      if (_m.indexOf(",")>-1)
        return "�·ݱ�����1��12֮��";
      month = _m.equals("") ? 0 :Integer.valueOf(_m).intValue();
      if (month < 1 || month > 12)
        return "�·ݱ�����1��12֮��";
    }else{
      if (_m.indexOf(",")==-1)
        return "�·ݱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
      String _y1 = _m.substring(0,_m.indexOf(","));
      String _y2 = _m.substring(_m.indexOf(",")+1);
      mg = Integer.valueOf(_y1).intValue();
      mz = Integer.valueOf(_y2).intValue();
      if(mg<=0 || mg>10 || mz<=0 || mz>12)
        return "�·ݱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    if(_d==null ||  _d.trim().equals("")) {
      return "���ڿ�����ʱ����֧������ʱ�������1��31֮�䣬��֧�����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }
    if(!typeSjOrgz){
      if (_d.indexOf(",")>-1 )
        return "���ڱ�����1��31֮��";
      day = _d.equals("") ? 0 : Integer.valueOf(_d).intValue();
      if (_d.indexOf(",")>-1 || day < 1 || day > 31)
        return "���ڱ�����1��31֮��";

      if(isYY) {
        if(day>daoc.getYinDays(year,month,isYun))
          return "ũ��: "+year+"��"+month+"�����ֻ��"+daoc.getYinDays(year,month,isYun)+"��";
      }else{
        if(day>daoc.getYangDays(year,month))
          return "����: "+year+"��"+month+"�����ֻ��"+daoc.getYangDays(year,month)+"��";
      }
    }else{
      if (_d.indexOf(",")==-1)
        return "���ڱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
      String _y1 = _d.substring(0,_d.indexOf(","));
      String _y2 = _d.substring(_d.indexOf(",")+1);
      dg = Integer.valueOf(_y1).intValue();
      dz = Integer.valueOf(_y2).intValue();
      if(dg<=0 || dg>10 || dz<=0 || dz>12)
        return "���ڱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    if(hms==null ||  hms.trim().equals("")) {
      return "ʱ��������ʱ����֧������ʱ�������hh:mm:ss����ʽ����֧�����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }
    if(!typeSjOrgz){
      if (hms == null || hms.trim().equals(""))
        return "ʱ���벻��Ϊ��";
      if (hms.startsWith(":"))
        return "ʱ���벻����':'��ͷ����ʱ������':'�ָ�����18:3:57��0:9:1";
      if(hms.indexOf(",")>-1) {
        return "ʱ���������':'�ָ�����18:3:57��0:9:1";
      }
      String[] h = hms.split(":");
      if(h.length == 0)
        return "ʱ���벻��Ϊ��";
      if(h.length == 1) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        if (hour < 0 || hour > 23)
          return "ʱ�����е�Сʱ����С��0�����23";
      }
      if(h.length == 2) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        if (hour < 0 || hour > 23)
          return "ʱ�����е�Сʱ����С��0�����23";
        if (minute < 0 || minute > 59)
          return "ʱ�����еķ��Ӳ���С��0�����59";
      }
      if(h.length == 3) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        second = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
        if (hour < 0 || hour > 23)
          return "ʱ�����е�Сʱ����С��0�����23";
        if (minute < 0 || minute > 59)
          return "ʱ�����еķ��Ӳ���С��0�����59";
        if (second < 0 || second > 59)
          return "ʱ�����е��벻��С��0�����59";
      }
    }else{
      if(hms.indexOf(",")==-1) {
        return "ʱ������ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
      }
      String _y1 = hms.substring(0,hms.indexOf(","));
      String _y2 = hms.substring(hms.indexOf(",")+1);
      hg = Integer.valueOf(_y1).intValue();
      hz = Integer.valueOf(_y2).intValue();
      if(hg<=0 || hg>10 || hz<=0 || hz>12)
        return "ʱ������ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    return null;
  }

  public String checks(String _y, String _m, String _d, String hms,
                       boolean isYY, boolean isYun, boolean sj) {
    String err = null;
    int yg=0,yz=0,mg=0,mz=0,dg=0,dz=0,hg=0,hz=0;
    int year=0, month=0, day=0, hour=0, minute=0, second=0;

    if(sj){
      if(_y.indexOf(",")!=-1)
        return "��ݱ��������֣��ұ�����"+Calendar.MAXYEAR+"��"+Calendar.IYEAR+"֮��";
      year = _y.equals("")?0:Integer.valueOf(_y).intValue();
      if (year > Calendar.MAXYEAR || year < Calendar.IYEAR)
        return "��ݱ�����" + Calendar.MAXYEAR + "��" + Calendar.IYEAR +"֮��";
    }else{
      if(_y.indexOf(",")==-1)
        return "��ݱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
      String _y1 = _y.substring(0,_y.indexOf(","));
      String _y2 = _y.substring(_y.indexOf(",")+1);
      yg = Integer.valueOf(_y1).intValue();
      yz = Integer.valueOf(_y2).intValue();
      if(yg<=0 || yg>10 || yz<=0 || yz>12)
        return "��ݱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    if(sj){
      if (_m.indexOf(",")>-1)
        return "�·ݱ�����1��12֮��";
      month = _m.equals("") ? 0 :Integer.valueOf(_m).intValue();
      if (month < 1 || month > 12)
        return "�·ݱ�����1��12֮��";
    }else{
      if (_m.indexOf(",")==-1)
        return "�·ݱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
      String _y1 = _m.substring(0,_m.indexOf(","));
      String _y2 = _m.substring(_m.indexOf(",")+1);
      mg = Integer.valueOf(_y1).intValue();
      mz = Integer.valueOf(_y2).intValue();
      if(mg<=0 || mg>10 || mz<=0 || mz>12)
        return "�·ݱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    if(sj){
      if (_d.indexOf(",")>-1 )
        return "���ڱ�����1��31֮��";
      day = _d.equals("") ? 0 : Integer.valueOf(_d).intValue();
      if (_d.indexOf(",")>-1 || day < 1 || day > 31)
        return "���ڱ�����1��31֮��";

      if(isYY) {
        if(day>daoc.getYinDays(year,month,isYun))
          return "ũ��: "+year+"��"+month+"�����ֻ��"+daoc.getYinDays(year,month,isYun)+"��";
      }else{
        if(day>daoc.getYangDays(year,month))
          return "����: "+year+"��"+month+"�����ֻ��"+daoc.getYangDays(year,month)+"��";
      }
    }else{
      if (_d.indexOf(",")==-1)
        return "���ڱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
      String _y1 = _d.substring(0,_d.indexOf(","));
      String _y2 = _d.substring(_d.indexOf(",")+1);
      dg = Integer.valueOf(_y1).intValue();
      dz = Integer.valueOf(_y2).intValue();
      if(dg<=0 || dg>10 || dz<=0 || dz>12)
        return "���ڱ����ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    if(sj){
      if (hms == null || hms.trim().equals(""))
        return "ʱ���벻��Ϊ��";
      if (hms.startsWith(":"))
        return "ʱ���벻����':'��ͷ����ʱ������':'�ָ�����18:3:57��0:9:1";
      if(hms.indexOf(",")>-1) {
        return "ʱ���������':'�ָ�����18:3:57��0:9:1";
      }
      String[] h = hms.split(":");
      if(h.length == 0)
        return "ʱ���벻��Ϊ��";
      if(h.length == 1) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        if (hour < 0 || hour > 23)
          return "ʱ�����е�Сʱ����С��0�����23";
      }
      if(h.length == 2) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        if (hour < 0 || hour > 23)
          return "ʱ�����е�Сʱ����С��0�����23";
        if (minute < 0 || minute > 59)
          return "ʱ�����еķ��Ӳ���С��0�����59";
      }
      if(h.length == 3) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        second = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
        if (hour < 0 || hour > 23)
          return "ʱ�����е�Сʱ����С��0�����23";
        if (minute < 0 || minute > 59)
          return "ʱ�����еķ��Ӳ���С��0�����59";
        if (second < 0 || second > 59)
          return "ʱ�����е��벻��С��0�����59";
      }
    }else{
      if(hms.indexOf(",")==-1) {
        return "ʱ������ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
      }
      String _y1 = hms.substring(0,hms.indexOf(","));
      String _y2 = hms.substring(hms.indexOf(",")+1);
      hg = Integer.valueOf(_y1).intValue();
      hz = Integer.valueOf(_y2).intValue();
      if(hg<=0 || hg>10 || hz<=0 || hz>12)
        return "ʱ������ǻ�x,y��ʽ�� ����0<x<=10��0<y<=12";
    }

    return null;
  }

   /**
    * �õ��Ͻ��½ڣ�ȡ��������������£����½������ڱ��£����ֻ������ݼ���
    * 1-6: �Ͻڽ�����ţ��Ͻ��ꡢ�¡��ա�ʱ����
    * 7-12: �½�
    * @param year2
    * @param month2
    * @param day2
    * @param hour2
    * @param min2
    * @param isYun
    * @param yytype ����Ϊ�棬����Ϊ��
    * @return
    */
   public int[] getJieQi(int year2, int month2, int day2, int hour2,int min2,
                         boolean isYun, boolean yytype) {
     int lastjie = 0; int nextjie = 0, lastjiename=0, nextjiename=0;
     int lastjieyear=0, nextjieyear=0;
     //int[] jq = new int[9];

     //�õ���������ʱ��ĸ�֧/9-11���������ա�12-14����������
     int[] sizhu = daoc.getSiZhu(year2,month2,day2,hour2,min2,isYun,yytype);
     //�õ��·�����ݣ����������������
     int z = sizhu[16];
     int year = sizhu[15]-Calendar.IYEAR;
     //ȡ��֧����һ������
     int _date2 = Calendar.jieqi2[year][z*2-1 + 1];
     if(!isMoreBig(sizhu[12], sizhu[13],sizhu[14],hour2, min2, z*2-1+1, sizhu[0])) {
       lastjie = Calendar.jieqi2[year][z * 2 - 1];
       nextjie = _date2;
       lastjiename = z * 2 - 1;
       nextjiename = z * 2 - 1 + 1;
     }else{
       lastjie = _date2;
       if (z == 12) {
         nextjie = Calendar.jieqi2[year + 1][1];
         lastjiename = 24; //����_maxMonth-1
         nextjiename = 1;
       }
       else {
         nextjie = Calendar.jieqi2[year][z * 2 - 1 + 2];
         lastjiename = z * 2 - 1 + 1;
         nextjiename = z * 2 - 1 + 2;
       }
     }

    int lys = lastjie/1000000;
    int lrs = lastjie/10000 - lys*100;
    int lss = lastjie/100 - lys*10000 - lrs*100;
    int lfs = lastjie - lys*1000000 - lrs*10000 - lss*100;

    int lyx = nextjie/1000000;
    int lrx = nextjie/10000 - lyx*100;
    int lsx = nextjie/100 - lyx*10000 - lrx*100;
    int lfx = nextjie - lyx*1000000 - lrx*10000 - lsx*100;

    /**
     * ����Ͻ�����12�£����½�����1�£�����ܣ�
     * 1. ����1�£�����12�� 2004.12 - 2005.1
     * 2. ����12�£�����1�� 2005.12 - 2006.1
     */
    if(lys==12 && lyx==1) {
      if(sizhu[16]==1) {
        lastjieyear = sizhu[15] - 1;
        nextjieyear = sizhu[15];
      }else if(sizhu[16]==12) {
        lastjieyear = sizhu[15];
        nextjieyear = sizhu[15] + 1;
      }
    }else{
      lastjieyear = sizhu[12];
      nextjieyear = sizhu[12];
    }

    return new int[]{0,lastjiename,lastjieyear,lys,lrs,lss,lfs,
                       nextjiename,nextjieyear,lyx,lrx,lsx,lfx};
  }

  /**
   * �жϵ�ǰʱ���뵱��֮�����Ŷ�Ӧ�Ľ���˭��˭С
   * @param y
   * @param mn
   * @param dn
   * @param h
   * @param mi
   * @param jieNo
   * @param yun2
   * @return
   */
  private boolean isMoreBig(int y, int mn, int dn, int h, int mi,
                            int jieNo, int yun2) {
    boolean isYun = yun2==1?true:false;
    int yun = daoc.getYunYue(y);
    int _date2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    int _m2 = _date2/1000000;

    if((mn*1000000+dn*10000+h*100+mi) < _date2 &&
       Math.abs(_m2 - mn)<=2) {
      if(isYun && !isYunJieqi(y,jieNo)) {
        return true;
      }else{
        return false;
      }
    }else{
      if(!isYun && isYunJieqi(y,jieNo)) {
        return false;
      }else{
        return true;
      }
    }
  }

  /**
   * ������������֮��
   * @param y
   * @param jieNo
   * @return
   */
  public boolean isYunJieqi(int y, int jieNo) {
    if(jieNo<2)
      return false;
    int yun = daoc.getYunYue(y);
    int jieqi0 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-2];
    int jieqi1 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-1];
    int jieqi2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    if(jieqi2<jieqi1 && jieqi2/1000000==yun)
      return true;
    if(jieqi2>jieqi1 && jieqi1<jieqi0 && jieqi2/1000000==yun)
      return true;

    return false;
  }

  /**
   * �õ���Ѯ�׵ĵ�֧
   */
  public int getXunShou(int g, int z) {
    return QiMen.xunzi[(z-g+12)%12];
  }

  public static void main1(String[] args) {
    DaoPublic pub = new DaoPublic();
    int[] jq = new int[13];
    //jq = pub.getJieQi(2004,2,1,9,30,true,false);
    //jq = pub.getJieQi(2005,1,10,18,30,true,true);
    jq = pub.getJieQi(2005,2,1,18,30,true,true);
    System.out.println("\r\n    ��  �ڣ� "+
              QiMen.JIEQI24[jq[1]]+" "+jq[2]+"��"+
              (pub.isYunJieqi(jq[2], jq[1]) ? "��":"") + jq[3]+
              "�³�"+jq[4]+"��"+jq[5]+"ʱ"+jq[6]+"��");
    System.out.println("\r\n    ��  �ڣ� "+
              QiMen.JIEQI24[jq[7]]+" "+jq[8]+"��"+
              (pub.isYunJieqi(jq[8], jq[7]) ? "��":"") + jq[9]+
              "�³�"+jq[10]+"��"+jq[11]+"ʱ"+jq[12]+"��");

  }
  
  

  public static void main(String[] args) {
    DaoPublic pub = new DaoPublic();
    System.out.println("=\r\n"+pub.String2Ascii("����"));
    //System.out.println(pub.getCurrentClassPath(""));
  }
}
