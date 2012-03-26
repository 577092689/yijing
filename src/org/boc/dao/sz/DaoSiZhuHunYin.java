package org.boc.dao.sz;

import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuHunYin {
  private DaoSiZhuYongShen daoys;
  private DaoSiZhuPublic daop;
  private DaoSiZhuWangShuai daow;
  private DaoSiZhuMain daom;
  private StringBuffer buf;
  private int[][] ssnum;
  private int[][][] ssloc;
  private int[] ssws;
  private int[] sscent;
  private String _t = "";
  private final String sep = "\r\n    ";
  private int[] hunqisui;         //Ӧ����õĻ���
  private int[] hunqisui2;        //û�кõģ���ѡ�����
  private final int MINSUI1 = 20;
  private final int MAXSUI1 = 25;
  private final int MINSUI2 = 22;
  private final int MAXSUI2 = 27;
  private final int MINSUI3 = 25;
  private final int MAXSUI3 = 35;
  private int count;
  private boolean sex ;

  public DaoSiZhuHunYin() {
    daoys = new DaoSiZhuYongShen();
    daop = new DaoSiZhuPublic();
    daow = new DaoSiZhuWangShuai();
    daom = new DaoSiZhuMain();
    buf = new StringBuffer();
    //��ɷ��ɵ�֧������0��,1�ȼ� 2�ٲ�3 ʳ�� 4�˹� 5ƫ�� 6���� 7ƫ�� 8���� 9ƫӡ 10��ӡ
    ssnum = new int[11][3];
    //��ɷ��ɵ�֧λ�� [ʮ��][�ɻ�֧1��֧2][������������ʱ��]
    ssloc = new int[11][4][4];
    //��ɷ��˥ 1��֮���� 2ƫ�� 3���� 4ǿ�� 5��֮����
    ssws = new int[5];
    //��ɷ�� 0Ϊ�Ƚ� 1Ϊʳ�� 2�Ʋ� 3��ɱ 4ӡ��
    sscent = new int[5];
    hunqisui = new int[50];
    hunqisui2 = new int[50];
    count = 0;
  }

  private void init() {
    for(int i=1; i<11; i++) {
      ssnum[i][1] = daop.getGzNum(daop.getShenShaName(i,1)[0],1);
      ssnum[i][2] = daop.getGzNum(daop.getShenShaName(i,2),2);
      if(i==1) ssnum[i][1]=ssnum[i][1]-1;
    }

    for(int i=1; i<11; i++) {
      ssloc[i][1] = daop.getGzLocation(daop.getShenShaName(i,1)[0],1);
      ssloc[i][2] = daop.getGzLocation(daop.getShenShaName(i,2)[0],2);
      ssloc[i][3] = daop.getGzLocation(daop.getShenShaName(i,2)[1],2);
      if(ssloc[i][2][0]==0) {
        ssloc[i][2][0]=ssloc[i][3][0];
        ssloc[i][2][1]=ssloc[i][3][1];
        ssloc[i][2][2]=ssloc[i][3][2];
        ssloc[i][3][0] = 0;
        ssloc[i][3][1] = 0;
        ssloc[i][3][2] = 0;
      }
    }
    //String[] ss5 = new String[]{"�Ƚ�","ʳ��","�Ʋ�","��ɱ","ӡ��"};
    for(int i=0; i<5; i++) {
      ssws[i] = daop.getShiShenWS(i);
      //Debug.out(ss5[i]+"��ɷ��˥��"+ssws[i]);
    }

    for(int i=0; i<5; i++) {
      sscent[i] = daop.getShiShenCent(i);
      //Debug.out(ss5[i]+"��ɷ������"+sscent[i]);
    }

  }

  public String getSiZhuHunYin() {
    //�õ������ꡢ���˵Ȼ�����Ϣ
    getBasicInfo();
    init();
    buf.delete(0,buf.toString().length());
    sex = SiZhu.sex.indexOf("Ǭ")!=-1;

    getSiZhuHunQi();
    getFuXingDesc();

    return buf.toString();
  }

  /**
   * Ů��һ������ˮΧ���������ǡ������ص�����������֮�����˶�ü���ס�����Ы�ġ���չһ�����˵���
   * ������֮����Ҳ���������ˡ���ˮ��Դ�������ġ�����������Գ����¤����������κ������ӭ�����ѡ�
   * ��ʮ���껻�����ˡ��̰����ɡ������ֹۡ����¸��򻨾������ܴ�һ���ա�
   * @return
   */
  private void getFuXingDesc() {
    buf.append("\r\n    ");
    buf.append("��ż�ο���\r\n        ");
    if(!sex && ssws[3]<2 && ssnum[7][1]+ssnum[7][2]+ssnum[8][1]+ssnum[8][2]>0) {
      add("�����������˷�����֮�񣬻�ֲ�������");
    }else if(!sex && ssnum[7][1]+ssnum[7][2]+ssnum[8][1]+ssnum[8][2]>3 && ssws[0]<3) {
      add("�������ǣ������ص�����������֮�����˶�ü���ף�����Ы�ģ���չһ�����˵���������֮����Ҳ");
    }else if(!sex && ssws[3]<3 && ssnum[7][1]+ssnum[7][2]+ssnum[8][1]+ssnum[8][2]>0) {
      add("����˥�������Ѽ�����֮���");
    }else if(!sex && sscent[2]+sscent[3]==0) {
      add("�ƿչٿգ�������˳��������Ҳ");
    }else if(!sex && ssws[2]>2 && sscent[3]==0) {
      add("���ǲ��֣����Ǽ��ǹ��ǣ����࣬�����������֮��");
    }else if(!sex && sscent[2]==0 && ssws[3]>2) {
      add("�������࣬���ӷ��֮��");
    }else if(!sex && sscent[2]>0 && ssws[3]>2) {
      add("�������٣������������֮��");
    }

  }


  /**
   * ����
   */
  private void getSiZhuHunQi() {
    int yearNum=0, hourNum=0;                 //�������������޻���ǣ���ʱ�ϵ��޻����
    int yearNumjs = 0, hourNumjs = 0;         //�����������ϱȽ���ʳ����ʱ�ϵ�
    int ss = 0;    //�޻���� ��ƫ�� ���� ƫ�� ���٣��˷ֵ�ϸ��ss11�ֵô�
    int js = 0;    //�Ƚٻ���ʳ
    int ss11 = 0;  //��� �� ��
    int js11 = 0;  //��Ȼ�ʳ
    int ws = 0, jsws = 0; //�޻���ǵ���˥���Ƚٻ�ʳ�˵���˥
    int selws = ssws[0];; //�Լ�����˥
    String ssname = null;
    String jsname = null;
    int hunZaoOrChi = 1;

    //add("��쿼��㷨");
    add("    ����1���������ͷ��ף�һ���������磬��������");
    if(SiZhu.sex.indexOf("Ǭ")!=-1) {
      ss = 5;
      js = 1;
      ws = ssws[2];
      jsws = ssws[0];
      ssname = "����";
      jsname = "�Ƚ�";
      ss11 = 2;
      js11 = 0;
    }else{
      ss = 7;
      js = 3;
      ws = ssws[3];
      jsws = ssws[1];
      ssname = "����";
      jsname = "ʳ��";
      ss11 = 3;
      js11 = 1;
    }
    for(int k=ss; k<ss+2; k++) {
      for(int i=1; i<3; i++) {
        for(int j=0; j<3; j++) {
          if(ssloc[k][i][j]<3 && ssloc[k][i][j]>0) {
            yearNum++;
          }else if(ssloc[k][i][j]==4){
            hourNum++;
          }
        }
      }
    }

    for(int k=js; k<js+2; k++) {
      for(int i=1; i<3; i++) {
        for(int j=0; j<3; j++) {
          if(ssloc[k][i][j]<3 && ssloc[k][i][j]>0) {
            yearNumjs++;
          }else if(ssloc[k][i][j]==4){
            hourNumjs++;
          }
        }
      }
    }

    if(yearNum>0 && ws>2 && ws<4) {
      add("����2��"+ssname+"͹�����£������࣬���");
      hunZaoOrChi = 1;
    }else if(yearNum>0 && ws >= 4 && selws<3) {
      add("����2��"+ssname+"͹�����£���֮���Ӷ��������ٻ�֮��");
      hunZaoOrChi = 3;
    }else if(yearNum>0 && jsws<3) {
      add("����2��"+ssname+"͹�����£����Ӳ������Ͼ�"+jsname+"���������");
      hunZaoOrChi = 1;
    }else if(yearNum>0 && yearNumjs==0 && jsws>2) {
      add("����2��"+ssname+"͹�����£��������������Ҽ�����������֣����");
      hunZaoOrChi = 1;
    }else if(yearNum>0) {
      add("����2��"+ssname+"͹�����£�������������"+jsname+"���࣬����鵫����̫��");
      hunZaoOrChi = 2;
    }else if(hourNum>0 && ws>2 && jsws<3) {
      add("����2��"+ssname+"����ʱ�����࣬����"+jsname+"��������鵫����̫��");
      hunZaoOrChi = 2;
    }else if(hourNum>0) {
      add("����2��"+ssname+"����ʱ�գ�����־����");
      hunZaoOrChi = 3;
    }else if(yearNumjs>0 && jsws<3 && ws>2) {
      add("����2��"+jsname+"͹�����£��䲻���࣬��"+ssname+"���࣬���ڲ���̫���˷�����֮����");
      hunZaoOrChi = 2;
    }else if(yearNumjs>0) {
      add("����2��"+jsname+"͹�����£������࣬�ٻ�֮��");
      hunZaoOrChi = 3;
    }else if(ws>2){
      add("����2��"+ssname+"�������ز��ֵ����࣬����"+jsname+"��������鵫��̫��");
      hunZaoOrChi = 2;
    }else if(isHasCang(ss)) {
      add("����2��"+ssname+"��֧�����£�����"+jsname+"��������鵫����̫��");
      hunZaoOrChi = 2;
    }else{
      add("����2��"+ssname+"�������ز������������������");
      hunZaoOrChi = 3;
    }

    buf.append("����3��\r\n        ");
    _getHunQis(ss,js,
               new int[]{SiZhu.DAYUN[1][1],SiZhu.DAYUN[2][1],SiZhu.DAYUN[3][1],SiZhu.DAYUN[4][1]},
               new int[]{SiZhu.DAYUN[1][2],SiZhu.DAYUN[2][2],SiZhu.DAYUN[3][2],SiZhu.DAYUN[4][2]},
               SiZhu.QIZHU,SiZhu.QIYUNSUI,
               ws, jsws, selws);

    //���û�кõ����ѡ�񣬾�ѡ�������ˡ�
    if(!_getHunqi(hunZaoOrChi,hunqisui))
      _getHunqi(hunZaoOrChi,hunqisui2);

    //return buf.toString();
  }

  private boolean _getHunqi(int hunZaoOrChi, int[] hunqisui) {
    for(int i=0; i<hunqisui.length; i++) {
      if (hunZaoOrChi==1) {
        if (hunqisui[i] <= MAXSUI1 && hunqisui[i] >= MINSUI1) {
          add("����պ��жϣ������� " + hunqisui[i] + "�� ���");
          return true;
        }
      }else if (hunZaoOrChi==2) {
        if (hunqisui[i] <= MAXSUI2 && hunqisui[i] >= MINSUI2) {
          add("����պ��жϣ������� " + hunqisui[i] + "�� ���");
          return true;
        }
      }else if (hunZaoOrChi==3) {
        if (hunqisui[i] <= MAXSUI3 && hunqisui[i] >= MINSUI3) {
          add("����պ��жϣ������� " + hunqisui[i] + "�� ���");
          return true;
        }
      }
    }
    return false;
  }

  /**
   * �Ƿ�֧����ż��
   */
  private boolean isHasCang(int ss) {
    int wx = YiJing.TIANGANWH[daop.getShenShaName(ss,1)[0]];
    int[] ycang = SiZhu.DZXUNCANG[SiZhu.nz];
    int[] mcang = SiZhu.DZXUNCANG[SiZhu.yz];
    for(int i=0; i<ycang.length; i++) {
      if(YiJing.TIANGANWH[ycang[i]]==wx)
        return true;
    }
    for(int i=0; i<mcang.length; i++) {
      if(YiJing.TIANGANWH[mcang[i]]==wx)
        return true;
    }
    return false;
  }

  /**
   * �Ƿ������������������
   * �Ƿ�����֧�뺬��֧������������
   * �Ƿ�����֧����֧������
   * �Ƿ��������������������� ǰ���߾�������ʱ
   * �Ƿ����������������� ǰ���߾�������ʱ
   * �Ƿ�����֧������������ ǰ���߾�������ʱ
   * �Ƿ�����֧������������ ǰ���߾�������ʱ
   * ��������������֧����ϣ�����������������ϡ�������֧����ϽԲ�����
   * ��Ҫ������a.��ż��������֮ b.��������֮ c.����ǿ��ż��ǿ �Կɳ�Ҳ���������ѳ�
   * @param ss
   * @param js
   * @param dyg
   * @param dyz
   * @param ln
   * @param qiyunsui
   */
  private void _getHunQis(int ss, int js, int[] dyg, int[] dyz, int[][][] ln,
                          int qiyunsui, int ssws, int jsws, int selws) {
    int wx = 0;
    String[] res = new String[2];
    int sswx = 0;
    int jswx = 0;
    int year = Calendar.YEARN1;
    boolean isHasCur = false; //ͬΪ���Ͼ֣����������֧���ѺϾ֣�������֧���򲻱���ʾ��
    boolean isHasCur2 = false;

    //�õ�����ż�ǵ�����
    int ssname = daop.getShenShaName(ss,1)[0];
    int jsname = daop.getShenShaName(js,1)[0];
    int selwx = YiJing.TIANGANWH[SiZhu.rg];
    sswx = YiJing.TIANGANWH[ssname];
    jswx = YiJing.TIANGANWH[jsname];

    for(int i=0; i<dyz.length; i++) {
      for (int j = (qiyunsui+i*10); j < ((i+1)*10+qiyunsui); j++) {
        isHasCur = false;
        isHasCur2 = false;

        if(j<MINSUI1 || j>MAXSUI3)
          continue;
        res = isShengKeOfGan(ss, js, dyg[i], SiZhu.QIZHU[j][7][1]);
        //Debug.out(j+"�꣺"+YiJing.TIANGANNAME[SiZhu.QIZHU[j][7][1]]+YiJing.DIZINAME[SiZhu.QIZHU[j][7][2]]);
        if(res==null && res[0]==null)
          return ;

        //1 �Ƿ������������������
        wx = daop.isSanHeOfRizhu(dyz[i], SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ��ż������������");
          isHasCur = true;
        }

        //2 �Ƿ�����֧�뺬��֧������������
        wx = daop.isSanHeOfRizhu(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ��ż������������");
          isHasCur = true;
        }

        //3 �Ƿ�����֧����֧������
        wx = daop.isLiuHe(SiZhu.rz, SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ��ż������");
          isHasCur = true;
        }

        //4 �Ƿ�����������������������������ż�� ǰ���߾�������ʱ
        wx = daop.isSanHe(dyz[i], SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ���ˡ���������");
          isHasCur = true;
        }

        //5 �Ƿ�������������������������ż�� ǰ���߾�������ʱ
        wx = daop.isSanHe(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx,  "�꣺���ꡢ��������");
          isHasCur = true;
        }

        //6 �Ƿ�����֧��������������������ż�� ǰ���߾�������ʱ
        wx = daop.isLiuHe(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ��������");
          isHasCur = true;
        }

        //7 ����֧���������ż��������
        wx = daop.isSanHuiOfRizhu(dyz[i], SiZhu.QIZHU[j][7][2], SiZhu.rz);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ���ˡ���ż������");
          isHasCur = true;
        }
        //8 ����֧����ż��������������
        wx = daop.isSanHuiOfRizhu(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ���֡���ż������");
          isHasCur = true;
        }
        //9 ����֧�����������������
        wx = daop.isSanHui(SiZhu.QIZHU[j][7][2], dyz[i]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ���֡���������");
          isHasCur = true;
        }
        //10 ����֧������������
        wx = daop.isSanHui(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "�꣺���ꡢ��������");
          isHasCur = true;
        }
        //11 ���������ż���������ո��������ż���и�
        if(daop.isPOX(ss, SiZhu.QIZHU[j][7][1])) {
          wx = YiJing.TGWUHE[SiZhu.rg][SiZhu.QIZHU[j][7][1]];
          if(wx > 0 && !isHasCur2 && ssws>2) {
            _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                       selwx, "�꣺���������ż�����ոɻ���");
            isHasCur2 = true;
          }
        }

        int[] pos;
        //11 ���������ż���������������ż���и�
        if(daop.isPOX(ss, SiZhu.QIZHU[j][7][1])) {
          pos = new int[] {
              SiZhu.ng, SiZhu.yg, SiZhu.sg};
          for (int x = 0; x < pos.length; x++) {
            wx = YiJing.TGWUHE[pos[x]][SiZhu.QIZHU[j][7][1]];
            if (wx > 0 && !isHasCur2 && ssws > 2) {
              _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                         selwx, "�꣺���������ż�������ֻ���");
              isHasCur2 = true;
            }
          }
        }

        //12 ���������ż������������������ż���и�
        pos = daop.isHasPOXOfMing(ss);
        for(int x=0; x<pos.length; x++) {
          if(pos[x]==0)
            break;
          wx = YiJing.TGWUHE[pos[x]][SiZhu.QIZHU[j][7][1]];
          if(wx > 0 && !isHasCur2 && ssws>2) {
            _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                       selwx, "�꣺���������ż����������ɻ���");
            isHasCur2 = true;
          }
        }

        //13 ���������ż������������������ż���и�
        if(daop.isPOX(ss,dyg[i])) {
          wx = YiJing.TGWUHE[dyg[i]][SiZhu.QIZHU[j][7][1]];
          if(wx > 0 && !isHasCur2 && ssws>2) {
            _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                       selwx, "�꣺���������ż�����������");
            isHasCur2 = true;
          }
        }

      }
    }
  }

  private void _getHunqi_(int wx, String[] res,
                          int sswx, int jswx, int year, int j,
                          int ssws, int jsws, int selws, int selwx, String desc) {
    String sep = "--";
    String nian = "";
    if(year>Calendar.IYEAR && Calendar.MONTHN>=Calendar.IMONTH)
      nian = (year+j-1)+"��";

    if((ssws<3 && (YiJing.WXDANSHENG[wx][sswx]>0 || wx == sswx || YiJing.WXDANKE[wx][jswx]>0)) ||
       (selws<3 && (YiJing.WXDANSHENG[wx][selwx]>0 || wx == selwx)) ||
       (selws>2 && ssws>2)) {
      add(nian+YiJing.TIANGANNAME[SiZhu.QIZHU[j][7][1]]+YiJing.DIZINAME[SiZhu.QIZHU[j][7][2]]+sep+
          j+desc+YiJing.WUXINGNAME[wx]+"�֣�"+res[1]+"��Ϊ����֮Ӧ");
      hunqisui[count++] = j;
    }else {
      add(nian+YiJing.TIANGANNAME[SiZhu.QIZHU[j][7][1]]+YiJing.DIZINAME[SiZhu.QIZHU[j][7][2]]+sep+
          j+desc+YiJing.WUXINGNAME[wx]+"�֣���������ż�ǣ�"+res[1]+"����������");
      hunqisui2[count++] = j;
    }
  }

  /**
   * ��������������ż�����˹�ϵ
   * ������֧������֧������֧���������ʱ���˸ɡ�����ɡ������Ƿ���������������ż��
   * @param peiouwx ��ż�� 0��,1�ȼ� 2�ٲ�3 ʳ�� 4�˹� 5ƫ�� 6���� 7ƫ�� 8���� 9ƫӡ 10��ӡ
   * @param dyg: �������
   * @param lng: �������
   * @return 10:����������ɺ�����ż�� 11 ͬ��ż������ 12 �˼��� 13����ż�� 14 ������
   *         20 �����������к�
   *         30 �����������к�
   *         40 ������������ɾ�����ż��
   *         50 �������������һ��һ����ż��
   *         60 ������������ɾ�����ż��
   */
  private String[] isShengKeOfGan(int peiou, int jis, int dyg, int lng) {
    int wx = 0;
    int poname = daop.getShenShaName(peiou, 1)[0];
    int jiname = daop.getShenShaName(jis, 1)[0];
    String[] rets = new String[2];

    wx = YiJing.TGWUHE[dyg][lng];
    if(wx > 0) {
      rets = _g(wx, poname, jiname, "�������������"+YiJing.TIANGANNAME[dyg]+YiJing.TIANGANNAME[lng]+"���");
      if (rets != null) {
        return rets;
      }
    }

    wx = _he(dyg);
    if(wx > 0) {
      rets = _g(wx, poname, jiname, "�������"+YiJing.TIANGANNAME[dyg]+"������������");
      if (rets != null) {
        return rets;
      }
    }

    wx = _he(lng);
    if(wx > 0) {
      rets = _g(wx, poname, jiname, "�������"+YiJing.TIANGANNAME[lng]+"�����������ϣ�");
      if (rets != null) {
        return rets;
      }
    }

    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[poname]]>0 &&
       YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = "������������ɾ�������ż��";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = "�������������ż��";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = "�������������ż��";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname] &&
             YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "��������������ɾ�Ϊ��ż��";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "�������͹����ż��";
      return rets;
    }else if(YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "�������͹����ż��";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname] &&
             YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "��������������ɾ�Ϊ��ż��";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "�������͹����ż��";
      return rets;
    }else if(YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "�������͹����ż��";
      return rets;
    }else if(YiJing.WXDANKE[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0 &&
       YiJing.WXDANKE[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = "������������ɾ���й����";
      return rets;
    }else
    if(YiJing.WXDANKE[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = "������ɿ�й����";
      return rets;
    }else
    if(YiJing.WXDANKE[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = "������ɿ�й����";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[jiname] &&
             YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[jiname]) {
      rets[0] = "0";
      rets[1] = "��������������ɾ�Ϊ����";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[jiname]) {
      rets[0] = "0";
      rets[1] = "�������͹�ּ���";
      return rets;
    }else if(YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[jiname]) {
      rets[0] = "0";
      rets[1] = "�������͹�ּ���";
      return rets;
    }else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0 &&
       YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = "������������ɾ���������";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = "���������������";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = "���������������";
      return rets;
    }

    return rets;
  }

  private int _he(int g) {
    int[] gs = {SiZhu.ng, SiZhu.yg, SiZhu.rg, SiZhu.sg};
    for(int i=0; i<gs.length; i++) {
      if(YiJing.TGWUHE[gs[i]][g]>0)
        return YiJing.TGWUHE[gs[i]][g];
    }
    return 0;
  }

  private String[] _g(int wx, int poname, int jiname, String desc) {
    String[] rets = new String[2];

    if(YiJing.WXDANSHENG[wx][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = desc+"������ż��";
    }else if(wx == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = desc+"�ϻ�����ż��";
    }else if(YiJing.WXDANKE[wx][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = desc+"��й����";
    }else if(YiJing.WXDANKE[wx][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "0";
      rets[1] = desc+"������ż��";
    }else if(YiJing.WXDANSHENG[wx][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = desc+"��������";
    }else {
      return null;
    }

    return rets;
  }

  /**
   * �õ�������������������������
   * //�ҳ���2��3��4�����ˣ���11-42�꣬�����鿴��2��3���ˣ������3��4����
    //SiZhu.DAYUN[2][1], SiZhu.DAYUN[2][2];
    //�ҳ�15-45�����꣬��������15-25�꣬�ٻ���22-45�꣬����ϴ��˿�
    //SiZhu.QIZHU[15][7][1],SiZhu.QIZHU[15][7][2]
    //��������
    //SiZhu.QIYUNSUI;
   */
  private void getBasicInfo() {
    daom.getQiYunSui();
    daom.getDaYun();
    daom.getQiZhu();
    daow.getWuXingCent();
  }

  private void add(String s) {
    buf.append(s+"��\r\n        ");
  }

}
