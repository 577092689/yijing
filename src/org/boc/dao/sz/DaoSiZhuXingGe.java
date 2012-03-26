package org.boc.dao.sz;

import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuXingGe {
  private DaoSiZhuYongShen daoys;
  private DaoSiZhuPublic daop;
  private DaoSiZhuWangShuai daow;
  private StringBuffer buf;
  private int[][] ssnum;
  private int[][][] ssloc;
  private int[] ssws;
  private int[] sscent;
  private boolean sex ;
  private String _t = "";
  private final String sep = "\r\n    ";
  private int[] xyshen;   //0Ϊ�Ƚ� 1Ϊʳ�� 2�Ʋ� 3��ɱ 4ӡ��
  private String kg = "    ";

  public DaoSiZhuXingGe() {
    daoys = new DaoSiZhuYongShen();
    daop = new DaoSiZhuPublic();
    daow = new DaoSiZhuWangShuai();
    buf = new StringBuffer();
    //��ɷ��ɵ�֧���� 0��,1�ȼ� 2�ٲ� 3ʳ�� 4�˹� 5ƫ�� 6���� 7ƫ�� 8���� 9ƫӡ 10��ӡ
    ssnum = new int[11][3];
    //��ɷ��ɵ�֧λ�� [ʮ��][�ɡ�֧1��֧2][������������������ʱ��]
    ssloc = new int[11][4][4];
    //��ɷ��˥ 1��֮���� 2ƫ�� 3���� 4ǿ�� 5��֮����
    ssws = new int[5];
    //��ɷ�� 0Ϊ�Ƚ� 1Ϊʳ�� 2�Ʋ� 3��ɱ 4ӡ��
    sscent = new int[5];
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
    }

  }

  /**
   * ������е��Ը����
   * @return
   */
  public String getXingGeOut() {
    daow.getWuXingCent();
    init();
    buf.delete(0,buf.length());
    sex = SiZhu.sex.indexOf("Ǭ")!=-1;
    xyshen = daoys.getXYShen();

    getShenGaoOfQuwei();
    getXingGe();
    getWXYiJi();
    getWXXinXing();
    getMGXinXing();
    getSSXinXing();

    return buf.toString();
  }

  /**
   * ��쿼��㷨
   * 1������ �״���1
   * 2. ���� ������1��î����2�������9��������10���Ӵ���11
   * 3������ ����һ����1����
   * 4��ʱ�� ͬ����
   * 5����Ů���� Ů1.55��1.66Ϊ�������������+��+�ձ�8��������������ʱ������8������
   *    �����ٱ�6��������������л�Ů�Ļ�����Ů1.55����1.66�����Ϊ�������
   * 6��ǿ�� �ո���ǿ��������ֻ��������������С���������������������ã�
   * @return
   */
  private void getShenGaoOfQuwei() {
    //buf.delete(0,buf.length());

    int jishu = sex ? 166 : 155;
    int up ,down, yao;
    int yz,sz;
    int up1, down1;

    if(SiZhu.yz<3)
      yz = SiZhu.yz+10;
    else
      yz = SiZhu.yz-2;
    up = SiZhu.ng+yz+Calendar.DAYN1;

    if(SiZhu.sz<3)
      sz = SiZhu.sz+10;
    else
      sz = SiZhu.sz-2;
    down = up+sz;

    down1 = down%8==0?0:down%8;
    up1 = up%8==0?0:up%8;
    yao = down%6==0?0:down%6;

    //add("����(1)����쿼��㷨--��166cmŮ155cmΪ����������ɼ���1����ʱ֧����1����11���������������ԡ������������Լ���س���������������С��");
    add(kg+YiJing.TIANGANNAME[SiZhu.ng]+"+"+YiJing.DIZINAME[SiZhu.yz]+"+"+
          Calendar.DAYN1+"="+SiZhu.ng+"+"+yz+"+"+Calendar.DAYN1+"="+up+
          "/8������������"+up1);
    add(kg+YiJing.TIANGANNAME[SiZhu.ng]+"+"+YiJing.DIZINAME[SiZhu.yz]+"+"+
        Calendar.DAYN1+"+"+YiJing.DIZINAME[SiZhu.sz]+"="+
        SiZhu.ng+"+"+yz+"+"+Calendar.DAYN1+"+"+sz+"="+down+
        "/8������������"+down1);
    add(kg+YiJing.TIANGANNAME[SiZhu.ng]+"+"+YiJing.DIZINAME[SiZhu.yz]+"+"+
        Calendar.DAYN1+"+"+YiJing.DIZINAME[SiZhu.sz]+"="+
        SiZhu.ng+"+"+yz+"+"+Calendar.DAYN1+"+"+sz+"="+down+
        "/6������������"+yao);

    if(ssws[0]==3 || ssws[0]==4) {
      add(kg+"���϶����Ϊ�� "+jishu+"+min("+up1+","+down1+","+yao+")="+jishu+"+"+
          daop.getMin(up1,down1,yao)+"="+(jishu+daop.getMin(up1,down1,yao))+"����");
    }else{
      add(kg+"���϶����Ϊ�� "+jishu+"+"+up1+"+"+down1+"+"+yao+"="+
          (jishu+up1+down1+yao)+"����");
    }

    //return buf.toString();

  }

  /**
   * ר���Ը�
   * @return
   */
  private void getXingGe(){
    //buf.delete(0,buf.length());
    add("");
    add("�Ը�ο���");
    if(!sex && (ssws[2]>3 || ssws[3]>3)) {
      add(kg+"�ƻ���˼��������伫�в��ܣ����¸����������ֶ����");
    }
    if(!sex && daop.isTooManyWX(YiJing.SHUI)) {
      add(kg+"Ů��ˮ�࣬��ͬ��ȸ���Ӷ����");
    }
    if(ssws[1]>=3 && ssws[3]>=3) {
      add(kg+"ʳ�����ɱ����������־����");
    }
    if(ssnum[3][1]>1 && ssnum[4][1]>1 && ssws[1]>=3) {
      add(kg+"��ʳ��¶��������Խ���������������վ��ס���Ϊ��̳���䣬��������ȹ����֮�̳�Ҳ");
    }
    if(ssws[1]>3 && !sex) {
      add(kg+"��й̫������������������������֮��");
    }
    if(ssnum[4][1]>0 && ssws[1]>=3) {
      add(kg+"�˹�͸¶�����࣬�����Ը�");
    }
    if(ssnum[3][1]+ssnum[4][1]>1 && ssws[1]>=3 &&
       (SiZhu.WENCANG[SiZhu.rg][SiZhu.nz] || SiZhu.WENCANG[SiZhu.rg][SiZhu.yz] ||
        SiZhu.WENCANG[SiZhu.rg][SiZhu.rz] || SiZhu.WENCANG[SiZhu.rg][SiZhu.sz])) {
      add(kg+"ʳ��͸¶�����֧࣬���Ĳ���������������ʯ�黭����ѧ����֮Ҳ");
    }
    if(ssws[3]>=3 && sscent[4]==0 &&
       (ssloc[7][1][0]<3 && ssloc[7][1][1]<3 && ssloc[7][1][2]<3 &&
        ssloc[7][2][0]<3 && ssloc[7][2][1]<3 && ssloc[7][2][2]<3 &&
        ssloc[8][1][0]<3 && ssloc[8][1][1]<3 && ssloc[8][1][2]<3 &&
        ssloc[8][2][0]<3 && ssloc[8][2][1]<3 && ssloc[8][2][2]<3)) {
      add(kg+"ɱ��ӡ�����������º��ƽ����Ȼ���ף���Ȩ���ã��ǹ�ɱ������Ҳ");
    }else if(ssws[3]>=3 && sscent[4]==0) {
      add(kg+"ɱ��ӡ�����������º��ƽ����Ȼ���ף���Ȩ���ã��ǹ�ɱ����ʱҲ");
    }
    //return buf.toString();
  }

  /**
   * ����֮�˼�
   * @return
   */
  private void getWXYiJi() {
    int name;
    String wxyj = "";
    add("");
    add("�����˼ɣ�");
    for(int i=0; i<xyshen.length; i++) {
      if(xyshen[i]<=0)
        break;
      name = daop.getShenShaName2(xyshen[i],1)[0];
      wxyj = daop.getWuXingYiJi(YiJing.TIANGANWH[name]);
      add(wxyj);
    }
  }

  /**
   * ��������
   * @return
   */
  private void getWXXinXing() {
    int wx ;
    String[] wxxx ;

    add("");
    add("�������ԣ�");
    //���̲���
    wx = YiJing.TIANGANWH[SiZhu.rg];
    wxxx = daop.getWuXingXing(wx);
    if (ssws[0] == 3 || ssws[0] == 4) {
      add(wxxx[0]+wxxx[1]);
    }else if(ssws[0] == 5){
      add(wxxx[0]+wxxx[2]);
    }else {
      add(wxxx[0]+wxxx[3]);
    }
  }

  /**
   * ��������
   * @return
   */
  private void getMGXinXing() {
    add("");
    add("�������ԣ�");
    add(daop.getMGXinXing(SiZhu.mgz));
  }

  /**
   * ʮ������
   * 0��,1�ȼ� 2�ٲ� 3ʳ�� 4�˹� 5ƫ�� 6���� 7ƫ�� 8���� 9ƫӡ 10��ӡ
   * @return
   */
  private void getSSXinXing() {
    add("");
    add("ʮ�����ԣ�");
    for(int i=0; i<=10; i++) {
      if(ssnum[i][1]+ssnum[i][2] > 0) {
        add(daop.getShiShenXing(i));
      }
    }
  }

  private void add(String s) {
    buf.append(s+"\r\n    ");
  }

  public static void main(String[] args) {
    DaoSiZhuXingGe dao = new DaoSiZhuXingGe();
    dao.getXingGe();
  }
}
