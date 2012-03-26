package org.boc.dao.sz;

import org.boc.db.BaZiKu;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuYongShen {
  private final String[] ss5 = new String[]{"�Ƚ�","ʳ��","�Ʋ�","��ɱ","ӡ��"};
  private final String[] ss10 = new String[]{"","�ȼ�","�ٲ�","ʳ��","�˹�","ƫ��","����","ƫ��","����","ƫӡ","��ӡ"};
  private DaoSiZhuMain daos;
  private DaoSiZhuPublic daop;
  private DaoSiZhuWangShuai daow;
  private StringBuffer buf;
  private int[] xyShen;
  private int[] jiShen;
  private int[][] ssnum;
  private int[][][] ssloc;
  private int[] ssws;
  private int[] sscent;
  private String _t = "";
  private BaZiKu bazi ;
  private final int cent = 30;
  private final String sep = "\r\n    ";
  int geju = 0;

  public DaoSiZhuYongShen() {
    bazi = new BaZiKu();
    daos = new DaoSiZhuMain();
    daop = new DaoSiZhuPublic();
    daow = new DaoSiZhuWangShuai();
    xyShen = new int[5];
    jiShen = new int[5];
    buf = new StringBuffer();
    //��ɷ��ɵ�֧����
    ssnum = new int[11][3];
    //��ɷ��ɵ�֧λ�� [ʮ��][�ɻ�֧1��֧2][������������ʱ��]
    ssloc = new int[11][4][4];
    //��ɷ��˥ 1��֮���� 2ƫ�� 3���� 4ǿ�� 5��֮����
    ssws = new int[5];
    //��ɷ��
    sscent = new int[5];
  }

  private void init() {
    for(int i=1; i<11; i++) {
      ssnum[i][1] = daop.getGzNum(daop.getShenShaName(i,1)[0],1);
      ssnum[i][2] = daop.getGzNum(daop.getShenShaName(i,2),2);
      //�ȼ粻���������Լ�
      if(i==1) ssnum[i][1]=ssnum[i][1]-1;
      //System.out.println(ss10[i]+"��ɷ������"+ssnum[i][1]+":"+ssnum[i][2]);
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
      //System.out.println(ss10[i]+"��ɷλ�ã�"+ssloc[i][1][0]+ssloc[i][1][1]+ssloc[i][1][2]+
      //                   ":"+ssloc[i][2][0]+ssloc[i][2][1]+ssloc[i][2][2]+
      //                   ":"+ssloc[i][3][0]+ssloc[i][3][1]+ssloc[i][3][2]);
    }
    for(int i=0; i<5; i++) {
      ssws[i] = daop.getShiShenWS(i);
      //System.out.println(ss5[i]+"��ɷ��˥��"+ssws[i]);
    }
    for(int i=0; i<5; i++) {
      sscent[i] = daop.getShiShenCent(i);
      //System.out.println(ss5[i]+"��ɷ������"+sscent[i]);
    }

  }

  private void uninit() {
    xyShen = new int[5];
    jiShen = new int[5];
    //��ɷ��ɵ�֧����
    ssnum = new int[11][3];
    //��ɷ��ɵ�֧λ�� [ʮ��][�ɻ�֧1��֧2][������������ʱ��]
    ssloc = new int[11][4][4];
    //��ɷ��˥ 1��֮���� 2ƫ�� 3���� 4ǿ�� 5��֮����
    ssws = new int[5];
    //��ɷ��
    sscent = new int[5];
  }


  /**
   * ��������
   * 1 ��ȫ������ 2��һ���� 3������� 4����ͬ����
   * @return
   */
  public String analyseSameBaZi() {
    uninit();
    init();
    buf.delete(0,buf.length());
    init();
    String str = "";
    int i1,i2,i3,i4,i5,i6,i7,i8;
    int j = 0;
    int k = 0;

    for (int i = 0; i < bazi.bazi.length; i++) {
      if (bazi.bazi[i][0] == null) continue;
      _t = null;
      j = 0;
      i1 = Integer.valueOf(bazi.bazi[i][0]).intValue();
      i2 = Integer.valueOf(bazi.bazi[i][1]).intValue();
      i3 = Integer.valueOf(bazi.bazi[i][2]).intValue();
      i4 = Integer.valueOf(bazi.bazi[i][3]).intValue();
      i5 = Integer.valueOf(bazi.bazi[i][4]).intValue();
      i6 = Integer.valueOf(bazi.bazi[i][5]).intValue();
      i7 = Integer.valueOf(bazi.bazi[i][6]).intValue();
      i8 = Integer.valueOf(bazi.bazi[i][7]).intValue();

      if (i1 == SiZhu.ng)  j++;
      if (i2 == SiZhu.nz)  j++;
      if (i3 == SiZhu.yg)  j++;
      if (i4 == SiZhu.yz)  j++;
      if (i5 == SiZhu.rg)  j++;
      if (i6 == SiZhu.rz)  j++;
      if (i7 == SiZhu.sg)  j++;
      if (i8 == SiZhu.sz)  j++;
      if (j >= 6)  {
        //System.out.println(bazi.bazi[i][8]);
        str += sep + "��"+ ++k + " ��" +
            YiJing.TIANGANNAME[i1]+YiJing.DIZINAME[i2] + " " +
            YiJing.TIANGANNAME[i3]+YiJing.DIZINAME[i4] + " " +
            YiJing.TIANGANNAME[i5]+YiJing.DIZINAME[i6] + " " +
            YiJing.TIANGANNAME[i7]+YiJing.DIZINAME[i8] + " " +
            sep +
            bazi.bazi[i][8] +
            sep;
      }
    }

    int mucent = SiZhu.muCent;
    int jincent = SiZhu.jinCent;
    int shuicent =SiZhu.shuiCent;
    int huocent = SiZhu.huoCent;
    int tucent = SiZhu.tuCent;
    int mucent1 = SiZhu.muCent1;
    int jincent1 = SiZhu.jinCent1;
    int shuicent1 =SiZhu.shuiCent1;
    int huocent1 = SiZhu.huoCent1;
    int tucent1 = SiZhu.tuCent1;
    int ng = SiZhu.ng;
    int nz = SiZhu.nz;
    int yg = SiZhu.yg;
    int yz = SiZhu.yz;
    int rg = SiZhu.rg;
    int rz = SiZhu.rz;
    int sg = SiZhu.sg;
    int sz = SiZhu.sz;

    for (int i = 0; i < bazi.bazi.length; i++) {
      if(bazi.bazi[i][0]==null) continue;
      i1 = Integer.valueOf(bazi.bazi[i][0]).intValue();
      i2 = Integer.valueOf(bazi.bazi[i][1]).intValue();
      i3 = Integer.valueOf(bazi.bazi[i][2]).intValue();
      i4 = Integer.valueOf(bazi.bazi[i][3]).intValue();
      i5 = Integer.valueOf(bazi.bazi[i][4]).intValue();
      i6 = Integer.valueOf(bazi.bazi[i][5]).intValue();
      i7 = Integer.valueOf(bazi.bazi[i][6]).intValue();
      i8 = Integer.valueOf(bazi.bazi[i][7]).intValue();

      if(YiJing.TIANGANWH[i5]==YiJing.TIANGANWH[rg] &&
         YiJing.DIZIWH[i4] == YiJing.DIZIWH[yz]) {
        getMainInfo(i1,i2,i3,i4,i5,i6,i7,i8);
        if((jincent >= SiZhu.jinCent - cent && jincent <= SiZhu.jinCent + cent) &&
           (mucent >= SiZhu.muCent - cent && mucent <= SiZhu.muCent + cent) &&
           (shuicent >= SiZhu.shuiCent - cent && shuicent <= SiZhu.shuiCent + cent) &&
           (huocent >= SiZhu.huoCent - cent && huocent <= SiZhu.huoCent + cent) &&
           (tucent >= SiZhu.tuCent - cent && tucent <= SiZhu.tuCent + cent)) {
          //System.out.println( bazi.bazi[i][8]);
          str += sep + "��"+ ++k + " ��" +
              YiJing.TIANGANNAME[i1]+YiJing.DIZINAME[i2] + " " +
              YiJing.TIANGANNAME[i3]+YiJing.DIZINAME[i4] + " " +
              YiJing.TIANGANNAME[i5]+YiJing.DIZINAME[i6] + " " +
              YiJing.TIANGANNAME[i7]+YiJing.DIZINAME[i8] +
              "    ľ��"+SiZhu.muCent +
              "  ��"+SiZhu.huoCent +
              "  ����"+SiZhu.tuCent +
              "  ��"+SiZhu.jinCent +
              "  ˮ��"+SiZhu.shuiCent +
              sep +
              bazi.bazi[i][8] +
              sep;
        }
      }
    }

    SiZhu.muCent = mucent;
    SiZhu.jinCent = jincent;
    SiZhu.shuiCent = shuicent;
    SiZhu.huoCent = huocent;
    SiZhu.tuCent = tucent;
    SiZhu.muCent1 = mucent1;
    SiZhu.jinCent1 = jincent1;
    SiZhu.shuiCent1 = shuicent1;
    SiZhu.huoCent1 = huocent1;
    SiZhu.tuCent1 = tucent1;
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;

    return str;
  }

  private void getMainInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz) {
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;
    daow.getWuXingCent();
  }

  /**
   * �õ�ϲ����
   * @return
   */
  public int[] getXYShen() {
    analyseYongShen();
    return xyShen;
  }

  /**
   * ȡ����
   * 0��,1�ȼ� 2�ٲ� 3ʳ�� 4�˹� 5ƫ�� 6���� 7ƫ�� 8���� 9ƫӡ 10��ӡ
   * @return
   */
  public String analyseYongShen() {
    buf.delete(0,buf.length());
    uninit();
    init();
    int ysType = 0;

    //1������
    ysType = daow.getBaGe();
    if(ysType==3) {
      getShiGeYongShen();
    }else if(ysType==4) {
      getShangGeYongShen();
    }else if(ysType==5 || ysType==6) {
      getCaiGeYongShen();
    }else if(ysType==7){
      getShaGeYongShen();
    }else if(ysType==8) {
      getGuanGeYongShen();
    }else if(ysType==9 || ysType==10) {
      getYinGeYongShen();
    }
    //2���
    ysType = daow.getWuGe();
    if(ysType==YiJing.MU) {
      getRenShouGe();
      geju = 52;
    }else if(ysType==YiJing.HUO) {
      getYanShangGe();
      geju = 54;
    }else if(ysType==YiJing.TU) {
      getJiaQiangGe();
      geju = 55;
    }else if(ysType==YiJing.JIN) {
      getCongGeGe();
      geju = 51;
    }else if(ysType==YiJing.SHUI) {
      getRunXiaGe();
      geju = 53;
    }
    //3�Ӹ�
    ysType = daow.getCongGe();
    if(ysType==1) {
      getCongErGe();
      geju = 41;
    }else if(ysType==2) {
      getCongCaiGe();
      geju = 42;
    }else if(ysType==3) {
      getCongGuanGe();
      geju = 43;
    }else if(ysType==4) {
      getCongQiangGe();
      geju = 44;
    }
    else if(ysType==5) {
      getCongWangGe();
      geju = 45;
    }

    //4������
    ysType = daow.getHuaQiGe();
    if(ysType>0) {
      getHuaQiGe();
      geju = 50;
    }
    //5��»��
    if(daow.isJianLu()) {
      //todo
    }
    //6���и�
    if(daow.isYueRen()) {
      getRenGeYongShen();
    }
    //7�½�
    if(daow.isYueJie()) {
      getJieGeYongShen();
    }
    //8�Ӹ���
    //9��������
    {
      getTongGuan();
      isGuanShaHunZa();
      getXieShangBangZhu();
      getWuJiBiFan();
      getZhuoQingKu();
      getZhenJiaShen();
      getUniversalCanKao();
    }
    return buf.toString();
  }

  /**
   * �˳̷���
   */
  public String analyseYunCheng() {
    uninit();
    init();
    buf.delete(0,buf.length());

    if(geju==41) {
      add("�Ӷ���ϲؔ��������У��Ƚ�����ʳ�����ɣ�ʳ����������ã��ٚ��˄w������ʳ�����w�p�Ĳ���;ӡ�R�˄wʳ���ʴ��");
    }else if(geju==42) {
      add("�ӲƸ�ϲؔ���hؔ��ʳ������ؔ������������У���ɱȽ��˄wؔ��ӡ������֮���˲�");
    }else if(geju==43) {
      add("�ӹ�ɱ��ϲ�ٚ���������ؔ�����ٚ��󼪣���ӡ�˛��ٚ����������Ƚ��˲�����ʳ�����Ƹ�Ҳ");
    }else if(geju==45) {
      add("������ϲӡ�ˡ��Ƚ��ˣ�ʳ�����cӡ���w������ؔ�˿�������ף��ٚ�����");
    }else if(geju==44) {
      add("��ǿ��ϲӡ�ˡ��Ƚ��ˣ�ʳ�����cӡ���w������ؔ�˿�������ף��ٚ�����");
    } else if(geju==50) {
      if(SiZhu.rg==YiJing.JIA || SiZhu.rg==YiJing.JI) {
        add("�׼����������л����ˣ����Ϸ�����");
      }else if(SiZhu.rg==YiJing.YI || SiZhu.rg==YiJing.GENG) {
        add("�Ҹ���������������ˣ�����������");
      } else if(SiZhu.rg==YiJing.BING || SiZhu.rg==YiJing.XIN) {
        add("������ˮ������������ˮ��");
      } else if(SiZhu.rg==YiJing.DING || SiZhu.rg==YiJing.REN) {
        add("���ɻ�ľ�����ж���ˮľ��");
      } else if(SiZhu.rg==YiJing.WUG || SiZhu.rg==YiJing.GUI) {
        add("��ﻯ�������ľ���ˣ�������ů����");
      }
    }else if(geju==51) {
      add("�Ӹ��ϲ������������Ҳϲˮ�˛��㣬ľؔ�˱����ˮͨ�P����ɻ���");
    }else if(geju==52) {
      add("��ֱ�ʉ۸�ϲˮľ�����������˛����༪����ؔ�˱���л�ͨ�P����ɽ���");
    } else if(geju==53) {
      add("���¸�ϲ��ˮ��������Ҳϲľ�˛��㣬��ؔ�˱����ľͨ�P���������");
    } else if(geju==54) {
      add("���ϸ�ϲľ����������Ҳϲ���˛��㣬���������F����ؔ�˱������ͨ�P�����ˮ��");
    } else if(geju==55) {
      add("��ǽ��ϲ������������Ҳϲ���˛��㣬ˮؔ�˱���н�ͨ�P�����ľ��");
    }else if(geju==66) {
      add("ƫ��֮�죬�캮�ض������ж���ľ���ˣ�ů�����ΪҪ");
    }else if(geju==81) {
      add("����ӡ��������������ӡ��ʳ��������й�㣬��ɱ����������ӡ���Ƚ�ǿ����");
    }else if(geju==82) {
      add("����������ʳ��������й�㣬��ɱ���ƱȽ٣��������٣���ӡ���Ƚ�ǿ����");
    }else if(geju==83) {
      add("������ɱ�ݺᣬ����ӡ��йɱ�����Ƚ�ǿ����������ʳ����й����ɱ����ģ����˿�ӡ��������");
    }else if(geju==84) {
      add("����������ӡ�������Ƚ�ǿ����������ʳ����й����ɱ����ģ����˿�ӡ��������");
    }else if(geju==99) {
      add("ƫ��֮�죬�����������������ˮ�ˣ�������֮�ص���ΪҪ");
    }

    int ig = 0, iz = 0;
    int ssg1 = 0, ssg2 = 0, ssz1 = 0, ssz2 = 0, ssz3 = 0, ssz0 = 0;
    String sg = null, sz = null;
    int ij=0,ik=0;
    if(SiZhu.DAYUN[1][1]==0) {
      daos.getDaYun();
      daos.getQiYunSui();
    }
    int qiyun = SiZhu.QIYUNSUI;
    for(int i=1; i<SiZhu.DAYUN.length; i++) {
      ij=0;
      ik=0;
      _t = null;
      ig = SiZhu.DAYUN[i][1];
      iz = SiZhu.DAYUN[i][2];
      sg = YiJing.TIANGANNAME[ig];
      sz = YiJing.DIZINAME[iz];

      for(int j=0; j<xyShen.length; j++) {
        if(xyShen[j]==-1)
          break;
        ssg1 = daop.getShenShaName2(xyShen[j],1)[0];
        ssg2 = daop.getShenShaName2(xyShen[j],1)[1];
        ssz0 = daop.getShenShaName2(xyShen[j],2)[0];
        ssz1 = daop.getShenShaName2(xyShen[j],2)[1];
        ssz2 = daop.getShenShaName2(xyShen[j],2)[2];
        ssz3 = daop.getShenShaName2(xyShen[j],2)[3];
        if(ssg1==ig || ssg2==ig) {
          ij++;
        }
        if(ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz) {
          ik++;
        }
        if(ij>0 && ik>0) {
          _t = getJiXiongCi(true, i);
          break;
        }
      }

      for(int j=0; j<xyShen.length; j++) {
        if(ij>0 && ik>0) {
          ij = 0;
          ik = 0;
          break;
        }
        if(xyShen[j]==-1)
          break;
        ssg1 = daop.getShenShaName2(xyShen[j],1)[0];
        ssg2 = daop.getShenShaName2(xyShen[j],1)[1];
        ssz0 = daop.getShenShaName2(xyShen[j],2)[0];
        ssz1 = daop.getShenShaName2(xyShen[j],2)[1];
        ssz2 = daop.getShenShaName2(xyShen[j],2)[2];
        ssz3 = daop.getShenShaName2(xyShen[j],2)[3];
        //System.err.println("����"+xyShen[j] +";"+ssg1+":"+ssg2+":"+ssz0+":"+ssz1+":"+ssz2+":"+ssz3);
        if(((ssg1==ig || ssg2==ig) && daop.getGaiTouJieJiao(ig,iz,1)!=2) ||
           ((ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz) && daop.getGaiTouJieJiao(ig,iz,2)!=1) ||
           ((ssg1==ig || ssg2==ig) &&(ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz))) {
          _t = getJiXiongCi(true, i);
          break;
        }else if((ssg1==ig || ssg2==ig) && daop.getGaiTouJieJiao(ig,iz,1)==2) {
          _t = getGaiTou(i);
          break;
        }else if((ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz) && daop.getGaiTouJieJiao(ig,iz,2)==1) {
          _t = getJiejiao(i);
          break;
        }
      }
      if(_t == null) _t = getJiXiongCi(false, i);
      add( ((i-1) * 10 + qiyun) + "��-" + (qiyun + 10 * i) + "�꣬����" + sg + sz + "��"+_t);
    }

    return buf.toString();
  }

  private String getJiejiao(int i) {
    String gaitou[] = new String[] {
        "��ϲ��֧�����ͷ�����׼��룬����ƽƽ��������������������ټ��룬�ٷ������ƻ��������ӣ�����֧�����ֳ�����",
        "��֧���ͷ�����׼��룬ƽ�ˣ�����������Ƽ����ټ��룬�����ƻ����ף���֧�����ֳ巴��",
        "��ͷ�ˣ�����ƽƽ��������������������ټ��룬�ٷ������ƻ��������ӣ�����֧�����ֳ�����",
    };
    if(i%3==0) return gaitou[2];
    if(i%2==0) return gaitou[1];
    else return gaitou[0];
  }

  private String getGaiTou(int i) {
    String gaitou[] = new String[] {
        "��ϲ�˸ɶ���֧���أ�ν֮�ؽţ����˲����ӣ�����������������ƽ���ף����з�����ʮ�����",
        "��ϲ�˸ɷ�ؽţ�����ƽƽ������������������ף���������",
        "�˸ɼ���֧���أ�ν�ؽţ�����������������������ƽ���ף����з�����ʮ�����",
    };
    if(i%3==0) return gaitou[2];
    if(i%2==0) return gaitou[1];
    else return gaitou[0];
  }

  private String getJiXiongCi( boolean b, int i) {
    String str = null;
    String[] jis = new String[] {
        "",
        "�������",
        "������",
        "��ϲ��꣣����³���",
        "���紵��������׹���֮�ѣ���¶մ�£�����������֮��",
        "��Ӧ̨�׸�����ӳ��ޱ",
        "߶����֮�⣬������ʳ",
        "������������ɰؾ�����ï",
        "����ԣ��",
        "����õأ���",
        "����������þ���ʱ������Ҳ�ش�",
        };
    String[] chas = new String[] {
        "",
        "��������׺���˳������",
        "��������ɾ�������·�಻��ͨ��",
        "�̺��쳣",
        "���۲���",
        "��־��ƣ���»",
        "��򶻯������ҵ����",
        "��������˪ѩ����ҵ����",
        "��������ɫ�����Ǵ����ﻨ",
        "�𵹲�������ɥ����"
        };

    if(b)  str = jis[i];
    else  str = chas[i];

    return str;
  }

  /**
   * �Ƹ�
   */
  private void getCaiGeYongShen() {
    //buf.delete(0,buf.length());
    add("������ǣ���ϲʳ���������������Ի��ơ���ϲ�������̫¶��Ȼ͸һλ�����ã�������ϲ����Ϊ֮¶��̫����¶��");
    if((SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU)
      add("��ľ�òƣ��������������");
    if((ssnum[5][1]>0 || ssnum[6][1]>0) && (ssnum[9][1]>0 || ssnum[10][1]>0)) {
      if(daop.getShenShaIsKe(9,1)>0 || daop.getShenShaIsKe(10,1)>0) {
        add("�Ƹ���ӡ�ߣ��ǹ²Ʋ�����ӡ������ӡȡ��Ȼ��ӡ�����ಢ(����)�����кô���С������");
      }else if(daop.getShenShaIsKe(9,1)==0 && daop.getShenShaIsKe(10,1)==0){
        add("�Ƹ���ӡ����ӡ�����భ���ǹ²Ʋ�����ӡ������ӡȡ��");
      }
    }
    if(ssws[2]<=2 && ssws[0]>=4)
      add("������أ���Ʊ��ֶᣬ�˹ٿɽ⣬��ʳ������Ϊ�������ܳɸ�Ҳ");
    if(ssnum[7][1]>0 && daop.getShenShaIsKe(7,1)>0)
      add("�ƴ���ɷ��ϲʳ��͸����ɷ���ƣ�ʹ�Ʋ�й��ɷ���ɹ��Ҳ������Ʋ�Ϊ���ö���ɷ��Ϊ�Ƹ�����");
    if(ssnum[7][1]>0 && daop.getShenShaIsHe(7,1)>0)
      add("�ƴ���ɷ���и�͸��ɷ��ƣ�ʹ�Ʋ�й��ɷ���ɹ��Ҳ������Ʋ�Ϊ���ö���ɷ��Ϊ�Ƹ�����");
    if(ssnum[7][1]>0 && daop.isHasRen(SiZhu.rg))
      add("�ƴ���ɷ��������������Ҳ������Ʋ�Ϊ���ö���ɷ��Ϊ�Ƹ�����");
    if(ssnum[3][1]>0 || ssnum[4][1]>0)
      add("����ɷ�ˣ�����ʳ��͸�����Ƽɶ�ʵϲ");

    if(ssnum[8][1]>0 && (ssnum[3][1]>0 || ssnum[4][1]>0))
      add("�������٣��Բ�Ϊ�ã���Ϊ�࣬�����ʳ��¶���Ƹ�Ҳ");
    if(ssnum[8][1]>0 && (ssnum[5][1]>0 || ssnum[6][1]>0))
      add("�������٣���ɲƶ��¶���ɣ��й������");
    if(ssws[2]>=4 && ssnum[1][1]==0 && ssnum[2][1]==0 && (ssnum[3][1]>0 || ssnum[4][1]>0))
      add("�����޽ٶ�͸�ˣ�������ӡ");
    if(ssws[2]>=4 && ssnum[1][1]==0 && ssnum[2][1]==0 && ssnum[9][1]>0 && ssnum[10][1]>0)
      add("�����޽�����ӡ���ƶ���������������");
    if(ssws[0]<=2 && sscent[3]>0 && sscent[4]>0)
      add("�в�������ɷӡ����ɷΪ�ɣ�ӡ�Ի�֮����ɸ���");
    if(ssws[0]>=4 && daop.isHasRen(SiZhu.rg))
      add("����̫�أ����ƾ�ɷ����ɷΪȨ���Ŵ�ɷ�����˲Ƹ����ƫ�ٸ���");
    _getYongShen();
  }

  /**
   * �ٸ�
   */
  private void getGuanGeYongShen() {
    add("������ǣ���ϲ͸������������ӡ�Ի���");
    if(ssnum[8][1]>1 || ssnum[8][2]>1)
      add("�ٶ�ͬɷ");
    if((ssnum[5][1]>0 || ssnum[6][1]>0) && daop.getShenShaIsKe(8,1)>10)
      add("�˹ټ��٣�͸�ƶ���λ���ú��ˣ����˹�����������");
    if(daop.getShenShaIsKe(8,1)>0 && ssnum[7][1]>0)
      add("�ùٱ����˹٣����ǲ������ɷ�򲻼ɣ�ȡ�������ɷҲ");
    if(daop.getShenShaIsKe(8,1)>0 && ssnum[5][1]==0 && ssnum[6][1]==0)
      add("�˹ټ��ٶ���͸�ƣ��Ƹ���");
    if(ssnum[3][1]>0 || ssnum[4][1]>0)
      add("�ùٶ�����ʳ����ϲ����ӡ�֮�磬��ʳΪ������ɷ������");
    if(ssnum[8][1]>0 && (daop.getShenShaIsHe(5,1)>0 || daop.getShenShaIsHe(6,1)>0))
      add("�ù�͸�ƣ����Ʒ�Ϲ¹��޸����Ƹ�Ҳ");
    if(daop.getShenShaIsChong(8)==10)
      add("���г���ǣ��Ƹ�Ҳ");
    if(daop.getShenShaIsKe(8,1)>0 && (ssnum[9][1]>0 || ssnum[10][1]>0))
      add("�������ˣ�������ӡ������ӡ���������������֮��");
    if(daop.getShenShaIsKe(8,1)>0 && daop.getShenShaIsHe(4,1)>0)
      add("�������ˣ�������ӡ��������ӡһ������٣����������������Ҳ");
    if(ssws[3]>=3 && (ssnum[5][1]>0 || ssnum[6][1]>0) && ssws[0]>=3)
      add("��ǿ��͸�����»�У����߽Ծ�����ɴ����������Ҳ");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU
       && (SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU))
      add("ӡ����٣���ν��ӡ˫ȫ�����˲��󡣶���ľ��ˮ����͸���ǣ����ѱع󣬸ǽ�ˮ������ľҲ");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.MU)
      add("ӡ����٣���ν��ӡ˫ȫ�����˲���");
    if(daop.getShenShaIsHe(9,1)!=0 || daop.getShenShaIsHe(10,1)!=0)
      add("�ٷ�ӡ�ˣ��������кϣ���ϲ��ʵ��");
    if(ssnum[9][1]>0 || ssnum[10][1]>0)
      add("�ٷ����ˣ�����͸ӡ���Ƽɶ�ʵϲ");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && ssws[0]>=3 && ssws[4]>=3)
      add("������ӡ������ӡ�أ���ϲ������ӡ����ʳй������Ϊ����");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && ssws[0]<=2 && ssws[4]>=3)
      add("�����������ӡ�����бȽ�»ӡ֮��");
    if(sscent[1]>0 && (ssnum[9][1]>0 || ssnum[10][1]>0) &&
       ssws[1]>=3 && ssws[4]<=2)
      add("���ٴ���ʳ����ӡ��������ӡ��ϲ��ӡ�أ���������ӡ��ϲ��������ӡ���");
    if(ssnum[9][1]+ssnum[10][1]>1 || ssnum[9][2]+ssnum[10][2]>1)
      add("ӡ��ص�����ʳ��й�㣬������ӡ����");
    if(daop.getShenShaIsHe(7,1)>0 && daop.getShenShaName(7,1)[0]%2==1)
      add("��ɷ�ж������ɺ�ɷ�ý٣����ɺ�ɷ���ˡ������ýٺ�ɷ���������ɷ��");
    if(sscent[2]>0 && sscent[4]>0)
      add("�йٶ������ӡ�ߣ���ν��ǿֵ���棬��Ϊ�����������ߣ��ƹ�ӡҲ��ֻҪ�Թٸ�֮��ʹ��ӡ�������ˣ������");
    if(ssws[0]<=2 && ssws[3]>=4)
      add("ɱ�ض����ᣬ��ƶ��ز");
    if(ssws[3]<=2 && ssws[1]>=4)
      add("ɱ΢���ƹ�����ѧ�޳�");
    _getYongShen();
  }

  /**
   * ӡ��
   */
  private void getYinGeYongShen() {
    add("����ӡ�ǣ�ϲ��ɷ���������ٲ��Ի�ӡ");
    if(ssws[4]<=2 && sscent[3]>0)
      add("ӡ���ɷ��ӡ���Ҳ");
    if(ssnum[8][1]>0)
      add("���ӡ˫ȫ��ӡ���Ҳ");
    if(ssws[0]>=3 && ssws[4]>=3 && sscent[1]>0)
      add("��ӡ��������ʳ��й����ӡ���Ҳ");
    if(ssws[4]>=3 && ssnum[5][1]+ssnum[6][1]>0 && ssws[2]<=2)
      add("ӡ���ƶ���͸���ᣬӡ���Ҳ");
    if(ssws[4]<=2 && ssws[2]>=3)
      add("ӡ������ӡ�����ƣ�Ϊӡ��֮��Ҳ");
    if(ssnum[9][1]>0 && ssnum[5][1]+ssnum[6][1]>0)
      add("͸ɷ����͸�������ӡ��ɷ��Ϊӡ��֮��Ҳ");
    if(ssws[0]>=3 && ssws[4]>2 && sscent[1]==0 && ssnum[7][1]>0)
      add("��ǿӡ�ز���ʳ���͸ɷ��ӡ������Ϊӡ��֮��Ҳ");
    if(ssws[0]>=3 && ssws[4]>2)
      add("��ǿӡ����͸ɷ��ƶ������������ӡ����ӡ������ɷ����ƫ֮��ƫ����������Ҳ");
    if(ssws[0]<=2 && ssnum[7][1]>0 && daop.getShenShaIsKe(9,1)>10 && daop.getShenShaIsKe(10,1)>10)
      add("ӡ����ɷ����������ӡ�Ի�ɷҲ����������ӡ��ɷ��Ϊ���ɣ���λ����������ɷ��ӡ����Ϊ��ɵð�");
    if(ssws[0]<=2 && ssnum[7][1]>0 && (daop.getShenShaIsKe(9,1)==10 || daop.getShenShaIsKe(10,1)==10))
      add("ӡ����ɷ����������ӡ�Ի�ɷҲ�����ƽ�����ӡ��ɷ���");
    if(ssws[0]<2 && ssnum[7][1]>0)
      add("ӡ����ˣ���������ɷ����ϲ��ʵ��");
    if(ssws[0]<=2 && ssws[3]>=3 && daop.getShenShaIsKe(7,1)>0)
      add("������ӡ������ɷ֮����ӡ���ߣ��������˶�ʳҲ");
    if(ssws[4]<3 && ssws[1]>=3)
      add("ӡǳ���ᣬ���ò����ʳ����ƶ֮����");
    if(ssws[4]>=3 && ssnum[5][1]+ssnum[6][1]>0)
      add("ӡ����ò��ߣ�ӡ����ǿ��͸������̫����Ȩ����֮��ֻҪӡ����޷�����");
    if(ssws[4]<3 && ssws[2]>2)
      add("ӡ����أ����޽ٲ��Ծȣ���Ϊ̰����ӡ��ƶ��֮��Ҳ");
    if(ssws[4]>2 && ssws[2]<3 && ssnum[3][1]+ssnum[4][1]>0)
      add("ӡ�ز������¶��ʳ������ʳ������������ᣬ���ɾ͸����಻���ӡ������ʳӡһ�ϲ��������Ϊ��");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)>0)
      add("��ӡ����͸��ɷ��ɷ���ϣ�Ϊ���");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsKe(7,1)>0)
      add("��ӡ����͸��ɷ��ɷ���ƣ�Ϊ���");
    if(ssws[0]>2 && ssnum[8][1]>0 && ssws[4]>2)
      add("ӡ��ù��ߣ���¶ӡ�أ����˷�������ʳ֮������Ϊ����?��ʳ���ƿ�ӡ������ʳ�ƹ٣�����ǿӡ�Ʋ���");
    if(ssws[0]>2 && ssnum[3][1]+ssnum[4][1]>0)
      add("ӡ緶�����ʳ�������������˷�������ʳ�������й��ˣ��������֣�ɷ������Ϊ����");
    if(ssws[0]>2 && ssnum[7][1]>0)
      add("ӡ����ɷ����ϲ��ʳ������֮������Ϊ���أ�һ�����磬���ӡ���ܻ�ɷ����������");
    if(ssws[0]<3)
      add("ӡ����ƣ���ϲ�ٵأ���ӡ��࣬�������");
    _getYongShen();
  }

  /**
   * ʳ��
   */
  private void getShiGeYongShen() {
    add("����ʳ��ʳϲ�����������������Ի�ʳ");
    if(sscent[3]>0 && sscent[4]>0 && sscent[2]==0)
      add("ʳ��ɷӡ���޲ƣ�ʳΪ��ӡ���ᣬֻ����ʳ��ɷ��ʳ���Ҳ");
    if(ssnum[7][1]+ssnum[7][2]>0 && sscent[2]>0 && sscent[4]>0 && daop.getShenShaIsSheng(7,1)==10)
      add("ʳ��ɷӡ���ƣ�ʳ���ƣ���ɷ������ɷ������ӡ���������");
    if(ssnum[7][1]+ssnum[7][2]>0 && sscent[2]>0 && sscent[4]>0 && daop.getShenShaIsSheng(7,1)!=10)
      add("ʳ��ɷӡ���ƣ�ʳ���ƣ�������ɷ��ʳ�Լ�֮���Ʋ���ɷ���ɹ�Ҳ");
    if(ssws[0]>2 && ssws[4]>0 && ssnum[3][1]>0 &&
       (SiZhu.yz==YiJing.HAI ||SiZhu.yz==YiJing.ZI ||SiZhu.yz==YiJing.CHOU) &&
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU)
      add("��ӡ������͸ʳ��󣬷�ӡ���Ȼ���ֱ���Ϊ��ľ����Ϊ�������Զ�ľ��𣬲�Ω����й���������Ե���Ҳ");
    else if(ssws[0]>2 && ssws[4]>0 && ssnum[3][1]>0)
      add("��ӡ������͸ʳ��󣬷�ӡ���Ȼ");
    if((ssnum[3][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && ssnum[1][1]+ssnum[2][1]==0) ||
      (ssnum[3][2]>0 && ssnum[9][2]+ssnum[10][2]>0 && ssnum[1][2]+ssnum[2][2]==0))
      add("ʳ���ӡ���ޱȣ�ν��ӡ��ʳ��������");
    if(ssnum[3][1]>0 && ssnum[9][1]+ssnum[10][1]>0 &&
       (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU)
      add("ʳ�������ӡ����ν��ʳ��������ľ��ʢ������֮���������ľ���˹�ϲ��ˮͬ�ۣ������֮νҲ");
    if(ssws[1]>2 && ssws[3]<3)
      add("ʳ̫�ض�ɷ�ᣬӡ����������Ʒ�����");
    if(ssws[0]>2 && ssws[3]>2)
      add("����ɷǿ����ʳ����ɷ����Ϊ�����ϲʳ�ˣ�Ψ�ɲ���");
    if(ssws[3]<3 && ssws[1]>2)
      add("ʳ����ɷ̫������ɷ��ʳ��Ҳ�������ɷ���ʲ��˷�����Ȼ����ӡ��֮������ӡ����ȥʳ̫֮������ɷ����һ������Ҳ");
    _getYongShen();
  }

  /**
   * ɷ��
   */
  private void getShaGeYongShen() {
    add("������ɱ����ɷϲʳ�����Ʒ����ɲ�ӡ���ʷ�");
    if(sscent[1]==0 && sscent[2]>0)
      add("��ɷ����Ϊ�ã��в�֮�������ƣ���ɷ���Ѷ���Σ��");
    if(ssws[0]>2 && ssnum[7][1]>0 && ssws[1]>2)
      add("��ǿɷ¶��ʳ�����������߽Ա�������֮����������Ҳ");
    if(ssws[1]>2 && ssws[3]>2 && ssws[0]<3)
      add("ɷǿʳ�������޸�����ز��ƶ����������Ҳ���ǽԸ�֮�Ͷ�������Ҳ");
    if(daop.getShenShaIsHe(3,1)>0)
      add("ɷ�з�ʳ����֮��Ҳ��Ȼʳ��Ϲ���ɷ������ɷ���ӣ�ɷ�о��壬����ܵó���");
    if(ssnum[3][1]>0 && daop.getShenShaIsHe(3,1)==0)
      add("ɷ�з�ʳ����֮��Ҳ");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)==0 && daop.getShenShaIsKe(7,1)==0)
      add("��ɷ���ӣ�ɷ�в��壬��֮����");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(8,1)==0 && daop.getShenShaIsKe(8,1)==0)
      add("��ɷ���ӣ�ɷ�в��壬��֮����");
    if(daop.getShenShaIsKe(7,1)>0)
      add("ɷ��ʳ�ƣ���ɷΪ�ã�ʳΪ��");
    if(daop.getShenShaIsKe(7,1)>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("ɷ��ʳ�ƣ���ɷΪ�ã�ʳΪ�ࡣɷ��ʳ�ƣ�͸ӡ�޹���ӡ�Ƹ�Ҳ");
    if(ssws[0]>2 && ssws[4]<3)
      add("��ɷ�������������ӡ�����Ѷ���֮����������ӡ���������㣬��Ϊ����");
    if(ssws[0]<3 && ssws[4]>2)
      add("��ɷ�������������ӡ�����Ѷ���֮����������ӡ���������㣬��Ϊ����");
    if(ssws[0]>2 && ssws[4]>2 && sscent[2]==0)
      add("��ӡ���ض�����ɷ���ǹ���ƶ�ӡ���ӡ���ض�����ɷ�����ַǲƲ��ɡ��ò���ӡ��ɷ������ɷ��ӡ����Ȼ��ͬ");
    if(ssws[0]>2 && ssws[4]>2 && sscent[2]>0)
      add("��ӡ���ض�����ɷ���ּ�����ǲƲ��ɡ��ò���ӡ��ɷ������ɷ��ӡ����Ȼ��ͬ");
    if(ssws[0]>2 && sscent[1]>0)
      add("��ɷ�������ʳ��ɷ���ƣ��������й��Ϊ���");
    if(ssws[0]>2)
      add("ɷ�Թ����Ʒ�����ٴ��֮�񣬶����ɷ");
    if(ssws[0]<3)
      add("��ɷ��ӡ��ӡ�ܻ�ɷ���������ˣ���ӡ���飬��Ϊ���");
    if(ssws[3]>2 && ssws[0]<3 && sscent[4]==0 && sscent[1]>0)
      add("ɷ�����ᣬ��ʳ����ӡ����й�������ܵ���������ӡ��");
    if(ssws[3]>2 && ssws[0]<3 && sscent[4]>0)
      add("ɷ�����ᣬ��ʳ���й�������ܵ�������ӡת����֮������ͨ���������Ϊ��������顣������󣬵������");
    if(ssws[0]>2 && sscent[2]>0 && sscent[1]==0)
      add("��ɷ���òƣ����Ե�ɷ��������ϲҲ");
    if(ssws[0]>2 && sscent[2]>0 && daop.getShenShaIsKe(1,1)>0)
      add("��ɷ���ò��ߣ����Ե�ɷ��������ϲҲ����ʳ���ƣ����ܷ�ɷ��������ȥӡ��ʳ����Ϊ���");
    if(ssws[0]>2 && ssws[3]<3 && sscent[4]>0 && sscent[2]>0)
      add("����ɷ�ᣬɷ�ֻ�ӡ�������壬������������Ϊ���");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)>0 && daop.getShenShaIsKe(7,1)>0)
      add("ɷ���ӹ٣�����ɷ���ƺϣ�ȡ�����");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)>0 && daop.getShenShaIsKe(7,1)>0)
      add("ɷ���ӹ��ߣ�����ٷ��ƺϣ�ȡ�����");
    if(ssws[0]>2 && ssws[3]<3 && sscent[2]>0)
      add("��ǿɱǳ���Բ�����ɱ�����");
    if(ssws[0]>2 && ssws[3]>2 && sscent[1]>0)
      add("��ɱ��ͣ����ʳ����ɱ�����");
    if(ssws[0]<3 && ssws[3]>2 && sscent[4]>0)
      add("ɱǿ��������ӡ緻�ɱ�����");
    if(ssws[3]>2 && ssws[0]<3)
      add("����ɱ�����ᣬ��ƶ��ز");
    if(ssws[1]>2 && ssws[3]<3)
      add("������ɱ̫������ѧ�޳�");
    if(ssws[0]<3 && ssws[3]>3)
      add("����ɱ��������ɱ�أ���������");
    if(daop.getShenShaIsKe(7,1)>0)
      add("��ɱ�������磬�����");
    _getYongShen();
  }

  /**
   * �˸�
   */
  private void getShangGeYongShen() {
    add("�����˹٣�ϲ��ӡ���Ʒ��������Ի���");
    add("�˹���Ǽ���ʵΪ������������ѧʿ�������˹ٸ��ڵ�֮");
    if(ssnum[9][1]+ssnum[10][1]>0 && ssws[1]>2 && ssws[4]>2)
      add("�˹���ӡ��������ӡ�и��˹ٸ��Ҳ");
    if(ssws[1]>2 && ssws[0]<3 && ssnum[7][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("��������������͸ɷӡ���˹ٸ��Ҳ");
    if(ssnum[7][1]>0 && sscent[2]==0 && sscent[4]>0)
      add("�˹ٴ�ɷ���޲���ӡ���˹ٸ��Ҳ");
    if(ssws[0]>2 && ssnum[9][1]+ssnum[10][1]>0)
      add("�������ˣ���������ӡ����ӡ���Ƹ�Ҳ");
    if(ssws[1]<3 && ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("�����ӡ������Ϊӡ���ƣ����ܷ���������");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && ssws[0]<3)
      add("�˹���ӡ������ҹ�");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && ssws[0]>2 && ssws[1]<3 && ssws[4]>2)
      add("�˹���ӡ��������󣬶�������ǳӡ�أ������㣬������������ǿ����������ǳ����ӡ�����������飬Ϊ��֮��Ҳ");
    if(ssnum[4][1]>0 && ssnum[8][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.JIN &&
       ssnum[5][1]+ssnum[6][1]>0)
      add("��ǽ�ˮ�˹ٶ����٣���͸������Ϊ�ƣ��˷����ˣ����������ٶ������˹ټ���");
    if(ssnum[4][1]>0 && ssnum[8][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.JIN &&
       ssnum[5][1]+ssnum[6][1]==0)
      add("�˹ټ��٣�Ϊ���ٶ�");
    if(ssnum[4][1]>0 && ssnum[8][1]>0 && YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN)
      add("�˹ټ��٣�Ϊ���ٶˣ�����ˮ�˹�ϲ����ɷ����Ϊ�������ǲ�η�ˣ�����Ϊ����Ȩ����֮Ҳ");
    if(ssnum[4][1]>0 && ssnum[7][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.JIN &&
       (SiZhu.yz!=YiJing.HAI && SiZhu.yz!=YiJing.ZI && SiZhu.yz!=YiJing.CHOU))
      add("�˹ٴ�ɷ����ʱ����");
    if(ssnum[4][1]>0 && ssnum[7][1]>0 && YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN &&
       (SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU))
      add("�˹ٴ�ɷ����ʱ���ã�����֮��������ٱ�");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.MU &&
       SiZhu.yz!=YiJing.SI && SiZhu.yz!=YiJing.WUZ && SiZhu.yz!=YiJing.WEI)
      add("�˹���ӡ����ʱ����");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU &&
       (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI))
      add("�˹���ӡ����ʱ���ã�����֮��ľ������ٱ������ˮ��ˮ�û�Ҳ");
    if(ssnum[4][1]>0 && ssnum[5][1]+ssnum[6][1]>0) {
      add("�˹��òƣ���Ϊ���");
      if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.SHUI)
        add("��֮ˮľ�˹٣���Ϊ������ˮľ�˹�ϲ����Ҳ");
      if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN)
        add("����֮��ˮ����ʹС����಻�󣬶�ˮ������ľҲ");
      if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU &&
         (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI))
        add("����֮��ľ��������㣬������������Ҳ��");
    }
    if(ssws[1]<3 && ssws[0]>2 && ssws[4]>2)
      add("�������ض�ӡ緶����ƶ��֮��Ҳ");
    if(daop.getShenShaIsKe(5,1)>10 &&  daop.getShenShaIsKe(6,1)>10)
      add("�˹ټ��ò�ӡ�ߣ���ӡ��ˣ��������ã�����ͷ��������భ");
    if(ssws[4]>2 && ssnum[9][1]+ssnum[10][1]>0 && ssnum[5][1]+ssnum[6][1]>0)
      add("��ӡ��ӡ̫�ض����ƣ���ͣ�кͣ���Ϊ���");
    if(ssws[1]>2 && ssws[0]<3 && ssnum[7][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("�˹���ɷӡ���˶���������ɷ��ӡ�԰�������ˡ��зǽ�ˮ�����٣�����Ϊ�ƣ��˷����ˣ����������ٶ������˹ټ���");
    _getYongShen();
  }

   /**
    * ��ֱ�ʉ۸�
    */
   private void getRenShouGe() {
     add("��ֱ�ʉ۸�---��ľ������,�ɽ��w,ϲˮľ����,�����༪,��Ҋ��ؔ,����л�ͨ�P");
     add("����ֱ�ʉ۸�,��ȡ��ľ,��ľ�t����˸�,�w���ľ����,�˄w�˛�,�w�r��ؔ��,���r��ʳ��,���Ԍ���֮��ֱ��Փ");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * ���ϸ�
    */
   private void getYanShangGe() {
     add("���ϸ�---�Ի������,��ˮ��w,ϲľ������,�������ؔ,�������ͨ�P,Ҋ��֮ʳ��,�������F,�w���޻�֮��,�yȡ�F,����������ؔ,�l��");
     add("���۶����˥��֮��,�������ϸ�,�o��������Փ,���F���d����ϸ�");
     add("�֟oՓ������,���փ�Ҋ����޻�,��ý�ؔ�ԛ�����,���ǻ�������,�಻�����ϸ�Փ");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * �ڷw��
    */
   private void getJiaQiangGe() {
     add("�ڷw��---����������,��ľ��w,ϲ��������,�������⼪,����ˮ��ؔ,����н�ͨ�P");
     add("���Լ�������,����������������￴");
     add("�ּڷw�����δ�����ø��������,�����,�������ñ������{��,����춳���,ľ���N���܄w��,�����s��ؔ��Փ,�r�гɸ���");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * �ĸ��
    */
   private void getCongGeGe() {
     add("�ĸ��---�Խ������,�ɻ���w,ϲ��������,ϲˮ����,����ľ��ؔ,�����ˮͨ�P");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * ���¸�
    */
   private void getRunXiaGe() {
     add("���¸�---��ˮ������,������w,ϲ��ˮ����,ľ������,�������ؔ,�����ľͨ�P");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

  /**
   * �ӹ�ɱ��
   */
   private void getCongGuanGe() {
     add("�Ӹ��ȡ����֮��Ϊ����ϲ�����߷�������ɿ˵�й�ء�");
     add("�Ӹ���˵؞��Ƹ�һ����������Փ֮������Ҋ�����֮�񣬄t�ɷ��ƞ�ɡ���ʱ�Է��ƞ���ߞ���������֮�����ϲ��");
     add("���ĸ񼃴��߸�֞���,��Ȼ���F���.�ُĸ��߲�����,������Ҋ��ԓ�������֮������,�����������ͨ���,�քe�o�����ȡ��,�o�Ï���������֮��,�^֮�ُĸ�");
     add("�ُĸ���,��փ�֮����鲡,�ٹ���\ȥ֮���l���F,�\�^�t�֔���.����ĸ���,��ֱ���,�m���\����,��Ȼ���F,�o�����_�p���B����,�w��ԭ���ஔ֮���F����,�Mȫ�����\! ");
     add("�Ĺٚ���---�ȏ�֮,�t�Թٚ�������,ϲ�ٚ���ؔ���ٚ�,��ӡ������������");
     xyShen = new int[]{3,2,-1,-1,-1};
     jiShen = new int[]{1,4,0,-1,-1};
   }

   /**
   * �Ӷ���
   */
   private void getCongErGe() {
     add("�Ӹ��ȡ����֮��Ϊ����ϲ�����߷�������ɿ˵ء�");
     add("�Ӹ���˵؞��Ƹ�һ����������Փ֮������Ҋ�����֮�񣬄t�ɷ��ƞ�ɡ���ʱ�Է��ƞ���ߞ���������֮�����ϲ��");
     add("���ĸ񼃴��߸�֞���,��Ȼ���F���.�ُĸ��߲�����,������Ҋ��ԓ�������֮������,�����������ͨ���,�քe�o�����ȡ��,�o�Ï���������֮��,�^֮�ُĸ�");
     add("�ُĸ���,��փ�֮����鲡,�ٹ���\ȥ֮���l���F,�\�^�t�֔���.����ĸ���,��ֱ���,�m���\����,��Ȼ���F,�o�����_�p���B����,�w��ԭ���ஔ֮���F����,�Mȫ�����\! ");
     add("�ă���---ȡʳ��������,ϲؔ����ʳ��֮��,���������;�Ƚٲ���,�w����ʳ��;Ҋʳ����ϲ,�w�������;��ٚ�����,���w����,��ʳ���c�ٚ����w,�p��ʳ��֮��;ӡ�R�ȼ�,�wʳ����Ҳ");
     xyShen = new int[]{1,2,0,-1,-1};
     jiShen = new int[]{4,3,-1,-1,-1};
   }

   /**
   * �ӲƸ�
   */
   private void getCongCaiGe() {
     add("�Ӹ��ȡ����֮��Ϊ����ϲ�����߷�������ɿ˵�");
     add("���ӲƸ񣬱�Ҫʳ�����㣬���������Դ����һ���޴������֡���ɱȽ��ˣ�������ʳ�ˣ��ܻ��Ƚ�����֮��Ҳ��������ʳ�����㣬�������죬һ��Ƚ٣�������֮�飬����������Ҳ��");
     add("�Ӹ���˵؞��Ƹ�һ����������Փ֮������Ҋ�����֮�񣬄t�ɷ��ƞ�ɡ���ʱ�Է��ƞ���ߞ���������֮�����ϲ��");
     add("���ĸ񼃴��߸�֞���,��Ȼ���F���.�ُĸ��߲�����,������Ҋ��ԓ�������֮������,�����������ͨ���,�քe�o�����ȡ��,�o�Ï���������֮��,�^֮�ُĸ�");
     add("�ُĸ���,��փ�֮����鲡,�ٹ���\ȥ֮���l���F,�\�^�t�֔���.����ĸ���,��ֱ���,�m���\����,��Ȼ���F,�o�����_�p���B����,�w��ԭ���ஔ֮���F����,�Mȫ�����\! ");
     add("��ؔ��---��ؔ������,ϲؔ���hؔ��ʳ������ؔ,�ɱȽف�wؔ��ӡ����������");
     xyShen = new int[]{2,1,3,-1,-1};
     jiShen = new int[]{0,4,-1,-1,-1};
   }

   /**
    * ������
    */
   private void getCongWangGe() {
     add("�Ӹ��ȡ����֮��Ϊ����ϲ�����߷�������ɿ˵�й�ء�");
     add("�Ӹ���˵؞��Ƹ�һ����������Փ֮������Ҋ�����֮�񣬄t�ɷ��ƞ�ɡ���ʱ�Է��ƞ���ߞ���������֮�����ϲ��");
     add("���ĸ񼃴��߸�֞���,��Ȼ���F���.�ُĸ��߲�����,������Ҋ��ԓ�������֮������,�����������ͨ���,�քe�o�����ȡ��,�o�Ï���������֮��,�^֮�ُĸ�");
     add("�ُĸ���,��փ�֮����鲡,�ٹ���\ȥ֮���l���F,�\�^�t�֔���.����ĸ���,��ֱ���,�m���\����,��Ȼ���F,�o�����_�p���B����,�w��ԭ���ஔ֮���F����,�Mȫ�����\! ");
     add("������---���бȽ�ӡ����򼪣������ӡ�ᣬ����ʳ��ѣ�ӡ��������ӡ�˵������룻��ɱ�ˣ�ν֮�������׻������������ǣ�Ⱥ������������һ��");
     add("�򷽾ִ����������ؿ�أ����ܷ���������������Ϊ��");
     add("���������вƹ���������µµ���������޳ɡ��������ؿ��֮�ˣ��������ܷ����������̺Ķ��");
     add("��������ȥ����ǣ���ɷ����Ҫ�����ȼ�ʳ�ˣ�Ȼ������ȥ����ɷ֮������������");
     add("��ľ�ּ����ˣ�˹�������������Ҫ������ʳ���ˣ����޷���֮��");
     add("�����ˣ�νӢ�����㣬�뿴ԭ���в���ӡ�����ⷴ��Ϊ�꣬��������");
     add("�����ˣ�ν�ƾ֣��׶༪��");
     add("��ˮ�ˣ��������޻���������ǿ��������࣬����������ʳ�ˣ������׻�����");
     xyShen = new int[]{4,0,-1,-1,-1};
     jiShen = new int[]{1,2,3,-1,-1};
   }

   /**
    * ��ǿ��
    */
   private void getCongQiangGe() {
     add("�Ӹ��ȡ����֮��Ϊ����ϲ�����߷�������ɿ˵�й�ء�");
     add("�Ӹ���˵؞��Ƹ�һ����������Փ֮������Ҋ�����֮�񣬄t�ɷ��ƞ�ɡ���ʱ�Է��ƞ���ߞ���������֮�����ϲ��");
     add("���ĸ񼃴��߸�֞���,��Ȼ���F���.�ُĸ��߲�����,������Ҋ��ԓ�������֮������,�����������ͨ���,�քe�o�����ȡ��,�o�Ï���������֮��,�^֮�ُĸ�");
     add("�ُĸ���,��փ�֮����鲡,�ٹ���\ȥ֮���l���F,�\�^�t�֔���.����ĸ���,��ֱ���,�m���\����,��Ȼ���F,�o�����_�p���B����,�w��ԭ���ஔ֮���F����,�Mȫ�����\! ");
     add("�ď���---��ӡ�Ǟ�����,��˳��������Ҳ���ƴ��бȽ��˲Ƽ���ӡ�����ѣ�ʳ������ӡ緳�˱��ף��ƹ���Ϊ��ŭǿ�񣬴���");
     add("�򷽾ִӏ��������ؿ�أ����ܷ���������������Ϊ��");
     add("���������вƹ���������µµ���������޳ɡ��������ؿ��֮�ˣ��������ܷ����������̺Ķ��");
     add("��������ȥ����ǣ���ɷ����Ҫ�����ȼ�ʳ�ˣ�Ȼ������ȥ����ɷ֮������������");
     add("��ľ�ּ����ˣ�˹�������������Ҫ������ʳ���ˣ����޷���֮��");
     add("�����ˣ�νӢ�����㣬�뿴ԭ���в���ӡ�����ⷴ��Ϊ�꣬��������");
     add("�����ˣ�ν�ƾ֣��׶༪��");
     add("��ˮ�ˣ��������޻���������ǿ��������࣬����������ʳ�ˣ������׻�����");
     xyShen = new int[]{0,4,-1,-1,-1};
     jiShen = new int[]{1,2,3,-1,-1};
   }


   /**
    * ������
    */
   private void getHuaQiGe() {
     add("�����횾փ���������֮�����,���ɲ���,�ֶʺ��಻��");
     add("������Լ���o�s��,����F֮��,�v�����\��������,�o�p�书�I,ֻ�D������");
     add("���N��������s��,�˂S�ٻ�,�ه���\��������,�t�l���l�F,���o�\���t�ֲ����Ҳ");
     add("����������,���������ߞ����񡣻���֮�ɸ�,����֮����������,�������ˮ,��������ˮ��,��Ҋ���ؔ");
     add("����w�������Ƹ���,���훪����֮���ߞ�ēp���");
     if(SiZhu.rg==YiJing.JIA || SiZhu.rg==YiJing.GUI) {
       xyShen = new int[] {1,2,-1, -1, -1};
       jiShen = new int[] {0,3, 4, -1, -1};
     }
     if(SiZhu.rg==YiJing.JI || SiZhu.rg==YiJing.GENG) {
       xyShen = new int[] {4,0,-1, -1, -1};
       jiShen = new int[] {3,1,2,-1, -1};
     }
     if(SiZhu.rg==YiJing.YI || SiZhu.rg==YiJing.BING) {
       xyShen = new int[] {2,3,-1, -1, -1};
       jiShen = new int[] {1,4,0,-1, -1};
     }
     if(SiZhu.rg==YiJing.XIN || SiZhu.rg==YiJing.REN) {
       xyShen = new int[] {0,1,-1, -1, -1};
       jiShen = new int[] {4,2,3,-1, -1};
     }
     if(SiZhu.rg==YiJing.DING || SiZhu.rg==YiJing.WUG) {
       xyShen = new int[] {3,4,-1, -1, -1};
       jiShen = new int[] {2,0,1,-1, -1};
     }
   }

  /**
   * �и�
   */
  private void getRenGeYongShen() {
    add("�������У����Թ�ɷΪ����");
    if(ssnum[7][1]+ssnum[8][1]>0 &&
       (ssnum[5][1]+ssnum[6][1]>0 || ssnum[9][1]+ssnum[10][1]>0) &&
       sscent[1]==0)
      add("����͸��ɷ��¶��ӡ�������˹٣����и��Ҳ");
    if(ssnum[8][1]>0 && ssnum[4][1]+ssnum[3][1]>0)
      add("͸�ٶ����˹٣�ʧ����֮Ч����");
    if(ssnum[7][1]>0 && daop.getShenShaIsHe(7,1)>0)
      add("͸ɷ��ɷ���ϣ�ʧ����֮Ч����");
    if(ssws[0]>2 && ssws[3]>2 && ssws[1]<3 && ssws[2]<3 && ssws[4]<3)
      add("ɷ����ͣ�����٣���ʹ��ɷ����ͣ������ӡ��Ϊ����");
    if(ssws[0]>2 && ssws[3]>2 && ssnum[7][1]+ssnum[8][1]>0)
      add("������������ɷ����ɷ¶���������Ҳ��");
    if(ssws[0]>2 && ssws[3]<3 && ssnum[7][1]+ssnum[8][1]>0)
      add("������������ɷ����ɷ¶����ǳ���ҲС");
    if(ssws[0]>2 && ssnum[7][1]+ssnum[8][1]==0)
      add("������������ɷ����ɷ�ض���¶���ҲС");
    if(ssws[0]>2 && sscent[3]==0)
      add("������������ɷ�����޹�ɷ���������޲���֮����");
  }

  /**
   * �ٸ�
   */
  private void getJieGeYongShen() {
    add("����ٲƣ�ϲ͸�����Ʒ�");
    if(sscent[1]>0 && sscent[2]>0)
      add("�½��òƣ�������ʳ��Ϊת�࣬��ʳ���٣�ת�����ƣ���Ϊ����");
    if(ssws[0]>2 && ssws[3]>0 && sscent[1]>0)
      add("��ɷ����ɷ��ͣ������ʳ�ơ�����ɷǿ����ʳ����ɷΪ��");
    if(ssws[0]>2 && ssnum[5][1]+ssnum[6][1]==0 && ssnum[8][1]==0 &&
       ssnum[7][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("��»�½٣�����������ϲ�����٣��޲ƹٶ�͸ɷӡ����ɷ��ӡ��ת�����������޼�����Ϊ�Ƹ�Ҳ");
    if(ssws[0]>3 && ssws[2]<3)
      add("��ǿ���ض�����������ز��ƶ����������Ҳ���ǽԸ�֮�Ͷ�������Ҳ");
    if(ssnum[7][1]>0 && ssnum[5][1]+ssnum[6][1]>0 &&
       (daop.getShenShaIsHe(7,1)>0 || daop.getShenShaIsKe(7,1)>0))
      add("��ɷ���ֲƣ���Ϊ������Ȼ��ȥɷ��ƣ��ֳɹ��");
    if(sscent[2]+sscent[3]==0 && sscent[1]>0)
      add("��»��֮���޲ƹٶ�����ʳ��й��̫������Ϊ����");
    if(ssnum[7][1]>0 && ssnum[8][1]>0)
      add("��»�ٶ���ɷ��������ȡ�巽Ϊ���");
    if(ssnum[8][1]>1)
      add("��»�ٶ����پ����������Ʒ�����ν�����ٲ�������Ҳ");
    if(ssnum[5][1]+ssnum[6][1]>0 && ssnum[3][1]+ssnum[4][1]==0)
      add("»���òƶ���͸��ʳ�������ڷ���");
    if(ssnum[5][1]+ssnum[6][1]==0 && ssws[2]>2)
      add("»���òƣ���ͷ͸һλ�����ӣ���֧���࣬���ȡ�����������");
    if(ssws[3]>2 && sscent[1]==0)
      add("��ɷ�ض����Ʒ��������Ʒ�����ɷ��ƣ������ɹ�ɷ̫�أ�������ΣҲ");
  }


  /**
   * ͨ�P
   */
  public boolean getTongGuan() {
    if(xyShen[0]>0 && jiShen[0]>0)
      return false;
    if (ssws[2] >= 4 && ssws[0] >= 4 &&
        sscent[3] == 0 && sscent[4] == 0) {
      add("���бȽ�ؔ�ǃ�������ȡʳ��ͨ��Ϊ����");
      xyShen = new int[] { 1, 3, -1, -1, -1};
      jiShen = new int[] { 4, 2, 0, -1, -1};
      return true;
    }
    if (ssws[4] >= 4 && ssws[1] >= 4 &&
        sscent[2] == 0 && sscent[3] == 0){
      add("����ӡ�Rʳ����������ȡ�Ƚ�ͨ��Ϊ����");
      xyShen = new int[] { 0, 4, 1, 2, -1};
      jiShen = new int[] { 3, -1, -1, -1, -1};
      return true;
    }
    if (ssws[4] >= 4 && ssws[2] >= 4 &&
        sscent[1] == 0){
      add("����ӡ�Rؔ�ǃ�������ȡ��ɱͨ��Ϊ����");
      if(sscent[3]==0) {
        xyShen = new int[] { 3, 0, -1, -1, -1};
        jiShen = new int[] { 2, 4, 1, -1, -1};
      }else{
        xyShen = new int[] { 3, 0, 2, 4, -1};
        jiShen = new int[] { 1, -1, -1, -1, -1};
      }
      return true;
    }
    if (ssws[3] >= 4 && ssws[1] >= 4 &&
        sscent[4] == 0){
      add("����ʳ���ٚ���������ȡ����ͨ��Ϊ����");
      xyShen = new int[] { 2, 0, 4, -1, -1};
      jiShen = new int[] { 1, 3, -1, -1, -1};
      return true;
    }
    if (ssws[3] >= 4 && ssws[0] >= 4 &&
        sscent[1] == 0 && sscent[3] == 0){
      add("���йٚ��Ƚك�������ȡӡ�Rͨ��Ϊ����");
      xyShen = new int[] { 4, 1,  3, -1, -1};
      jiShen = new int[] { 2, 0, -1, -1, -1};
      return true;
    }
    return false;
  }

  /**
   * ̫�����ƿ�����������������ϲ�ܿˣ���̫����ϲй�������ߴ�����ϲ����
   * ̫˥��������˥�������ҿ˾�ϲ��������̫˥��ϲ�ˣ�˥�����˴�����ϲй��
   */
  private void getWuJiBiFan() {
    int max = SiZhu.baifen[3];
    int min = SiZhu.baifen[0];
    int midmax = SiZhu.baifen[2];
    int midmin = SiZhu.baifen[1];
    if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU) {
      if (SiZhu.muCent > midmax && SiZhu.muCent < max)
        add("ľ̫���߶��ƽ�ϲ��֮��Ҳ");
      if (SiZhu.muCent > max)
        add("ľ�����߶��ƻ�ϲˮ֮��Ҳ");
      if (SiZhu.muCent > min && SiZhu.muCent < midmin)
        add("ľ̫˥�߶���ˮҲ���˽�����֮");
      if (SiZhu.muCent < min)
        add("ľ˥���߶�����Ҳ���˻�����֮");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.HUO) {
      if (SiZhu.huoCent > midmax && SiZhu.huoCent < max)
        add("��̫���߶���ˮ��ϲ��ֹ֮Ҳ");
      if (SiZhu.huoCent > max)
        add("�������߶�������ϲľ֮��Ҳ");
      if (SiZhu.huoCent > min && SiZhu.huoCent < midmin)
        add("��̫˥�߶���ľҲ����ˮ����֮");
      if (SiZhu.huoCent < min)
        add("��˥���߶��ƽ�Ҳ����������֮");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.TU) {
      if (SiZhu.tuCent > midmax && SiZhu.tuCent < max)
        add("��̫���߶���ľ��ϲ��֮��Ҳ");
      if (SiZhu.tuCent > max)
        add("�������߶��ƽ�ϲ��֮��Ҳ");
      if (SiZhu.tuCent > min && SiZhu.tuCent < midmin)
        add("��̫˥�߶��ƻ�Ҳ����ľ����֮");
      if (SiZhu.tuCent < min)
        add("��˥���߶���ˮҲ���˽�����֮");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN) {
      if (SiZhu.jinCent > midmax && SiZhu.jinCent < max)
        add("��̫���߶��ƻ�ϲˮ֮��Ҳ");
      if (SiZhu.jinCent > max)
        add("�������߶���ˮ��ϲ��ֹ֮Ҳ");
      if (SiZhu.jinCent > min && SiZhu.jinCent < midmin)
        add("��̫˥�߶��������˻�����֮");
      if (SiZhu.jinCent < min)
        add("��˥���߶���ľҲ����ˮ����֮");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.SHUI) {
      if (SiZhu.shuiCent > midmax && SiZhu.shuiCent < max)
        add("ˮ̫���߶�������ϲľ֮��Ҳ");
      if (SiZhu.shuiCent > max)
        add("ˮ�����߶���ľ��ϲ��֮��Ҳ");
      if (SiZhu.shuiCent > min && SiZhu.shuiCent < midmin)
        add("ˮ̫˥�߶��ƽ�Ҳ����������֮");
      if (SiZhu.shuiCent < min)
        add("ˮ˥���߶��ƻ�Ҳ����ľ����֮");
    }
  }

  /**
   й��ʳ��Ҳ�����߹�ɱҲ��
   ���߱Ƚ�Ҳ������ӡ�Ҳ��
   */
  private void getXieShangBangZhu() {
    if(ssws[0]>=3 && ssws[3]<=2)
      add("�������࣬���й�ɱ������й֮�������������ȥ�Ƚ�֮���࣬������֮���㣬��ν��֮��������й֮�к�Ҳ");
    if(ssws[0]>=3 && sscent[3]==0 && sscent[2]==0)
      add("�������࣬���вƹٲ��������ֱȽ٣���֮�򼤶��к�������й֮��˳�����ƣ���ν��֮�к�����й֮����Ҳ");
    if(ssws[0]<=2 && ssws[2]>=3)
      add("����˥�������в����ص���ӡ���֮����������ȥ����֮���࣬������֮���㣬���԰�֮�򼪣�����֮����Ҳ");
    if(ssws[0]<=2 && ssws[3]>=3)
      add("����˥�������й�ɱ���ӣ�����ɱ�ƣ���֮�ַ������飬������֮�Ի���ǿ�������԰�֮���ף�����֮��Ҳ");
  }

  public void getUniversalCanKao() {
    if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU &&
       (SiZhu.yz==YiJing.YIN || SiZhu.yz==YiJing.MAO || SiZhu.yz==YiJing.CHEN) &&
       ssnum[8][1]>0 && ssnum[3][1]+ssnum[4][1]>0)
      add("��ľ���ľ��ͨ�����������٣�������ɣ������Ƹ�Ҳ");
    if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN &&
       (SiZhu.yz==YiJing.SHEN || SiZhu.yz==YiJing.YOU || SiZhu.yz==YiJing.XU) &&
       ssnum[3][1]+ssnum[4][1]>0 && ssnum[8][1]>0)
       add("�������ˮ����ˮ�ອ�������ް�");
     if(SiZhu.rg==YiJing.JIA && daop.getGzNum(YiJing.YI,1)>0 && daop.getGzNum(YiJing.GENG,1)>0)
       add("���������޸�����Ϊ����");
     if(SiZhu.rg==YiJing.YI && SiZhu.yz==YiJing.YOU &&
        daop.getGzNum(YiJing.XIN,1)>0 && daop.getGzNum(YiJing.DING,1)>0)
       add("�������£�����͸������գ���ľʢ�����߽Ա�������֮��");
     if(SiZhu.rg==YiJing.BING && SiZhu.yz==YiJing.ZI &&
        daop.getGzNum(YiJing.GUI,1)>0 && daop.getGzNum(YiJing.GENG,1)>0 &&
        SiZhu.yz==YiJing.BING && SiZhu.yz==YiJing.YIN)
	add("�������£���ˮ͸������¶���������磬���߽Ծ�����ɴ��");
  }

  /**
   * �Ƿ��ɱ����
   */
  private boolean isGuanShaHunZa() {
    if(ssnum[7][1]==1 && ssloc[7][1][0]+ssloc[7][1][1]<3 &&
       ssnum[5][2]+ssnum[6][2]>0 &&
       ssnum[8][1]==1 && ssloc[8][1][0]==4 && !daop.isHasLu(SiZhu.sg)) {
      add("��������͸һɱ������֧���вƣ�ʱ�������޸����˹ٴ�ɱ�ƣ��ǻ�Ҳ");
      return false;
    }else if(ssnum[8][1]==1 && ssloc[8][1][0]+ssloc[7][1][1]<3 &&
       ssnum[5][2]+ssnum[6][2]>0 &&
       ssnum[7][1]==1 && ssloc[7][1][0]==4 && !daop.isHasLu(SiZhu.sg)) {
      add("��������͸һ�٣�����֧���вƣ�ʱ��ɱ���޸�����ɱ�ӹ��ƣ��ǻ�Ҳ");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsHe(7,1)>0) {
      add("���ɱ���ӣ����нٲƺ�ɱ���ٿɻ�Ҳ");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsKe(7,1)>0) {
      add("���ɱ���ӣ����бȼ��ɱ���ٿɻ�Ҳ");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsHe(8,1)>0) {
      add("���ɱ���ӣ����бȼ�Ϲ٣�ɱ�ɻ�Ҳ");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsHe(8,1)>0) {
      add("���ɱ���ӣ����нٲƵ��٣�ɱ�ɻ�Ҳ");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]==1 &&
             ssnum[9][1]+ssnum[10][1]+ssnum[9][2]+ssnum[10][2]>1) {
      add("һ�ٶ�ӡ��ط꣬����й����ɱ��֮���ǻ�Ҳ");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]==1 && ssnum[8][1]+ssnum[8][2]>0 &&
             ssnum[3][1]+ssnum[4][1]+ssnum[3][2]+ssnum[4][2]>1) {
      add("һɱ��ʳ�˲�������ɱ̫��������֮���ǻ�Ҳ");
      return false;
    }else if(ssws[3]<3 && (ssws[0]>2 || ssws[4]>2) &&
             ssnum[7][1]>0 && ssnum[8][1]>0) {
      add("����ɱ��͸�޸���������ӡ�ط꣬����ϲ�죬���˲�����ɱ��Ҳ");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0) {
      return true;
    }
    return false;
  }

  private void _getYongShen() {
    if(getTongGuan())
      return ;
    if(xyShen[0]>0 && jiShen[0]>0)
      return ;

    if((SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
        SiZhu.huoCent>SiZhu.baifen[3] && SiZhu.shuiCent<SiZhu.baifen[0]) {
        geju = 99;
        xyShen = new int[]{daop.getShenShaByWX(YiJing.JIN),
          daop.getShenShaByWX(YiJing.SHUI),-1,-1,-1};
        jiShen = new int[]{daop.getShenShaByWX(YiJing.HUO),
          daop.getShenShaByWX(YiJing.MU),
          daop.getShenShaByWX(YiJing.TU),-1,-1};
      return;
    }
    if((SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU) &&
        SiZhu.shuiCent>SiZhu.baifen[3] && SiZhu.huoCent<SiZhu.baifen[0]) {
        geju = 66;
        xyShen = new int[]{daop.getShenShaByWX(YiJing.HUO),
           daop.getShenShaByWX(YiJing.MU),-1,-1,-1};
        jiShen = new int[]{daop.getShenShaByWX(YiJing.SHUI),
           daop.getShenShaByWX(YiJing.JIN),
           daop.getShenShaByWX(YiJing.TU),-1,-1};
       return;
    }

    if(ssws[0]>2 && ssws[4]>2) {
      geju = 81;
      xyShen = new int[]{2,1,3,-1,-1};
      jiShen = new int[]{4,0,-1,-1,-1};
    }else if(ssws[0]>2) {
      geju = 82;
      xyShen = new int[]{1,3,2,-1,-1};
      jiShen = new int[]{4,0,-1,-1,-1};
    }else if(ssws[0]<3 && ssws[3]>2) {
      geju = 83;
      xyShen = new int[]{4,0,-1,-1,-1};
      jiShen = new int[]{1,2,3,-1,-1};
    }else if(ssws[0]<3) {
      geju = 84;
      xyShen = new int[]{0,4,-1,-1,-1};
      jiShen = new int[]{1,2,3,-1,-1};
    }
  }

  /**
   * ���ǿ���
   */
  private void getZhuoQingKu() {
    _t = "";
    String _temp = "";
    int j = 0;
    if(sscent[2]>0 && sscent[4]>0) _t += "��ӡ�భ��";
    if(sscent[1]>0 && sscent[3]>0) _t += "ʳ�˼���ɱ��";
    if(isGuanShaHunZa()) _t += "��ɱ���ӣ�";
    if(sscent[1]>0 && sscent[4]>0) _t += "��ӡ��ʳ��";
    if(_t.length()>1) _t = "����"+_t+"���Ի���";

    if(!daop.isYouGen(SiZhu.rg)) _temp+="�ո��ڸ�֧�в��ó�����»���С�Ĺ����������ν�޸���";
    for(int i=0; i<xyShen.length; i++) {
      if(xyShen[i]==-1 || i==xyShen.length-1)
        break;
      if(ssws[xyShen[i]]>1) {
        j++;
        break;
      }
    }
    if(j==0) _temp += "����������";
    if((SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
        SiZhu.huoCent>SiZhu.baifen[3] && SiZhu.shuiCent<SiZhu.baifen[0]) {
      if(SiZhu.tuCent>0 && SiZhu.jinCent>0) _temp += "���������࣬";
      else if(SiZhu.tuCent>0) _temp += "�������";
      else if(SiZhu.jinCent>0) _temp += "���׽�࣬";
      else _temp += "�������ȣ�";
    }
    if((SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU) &&
    SiZhu.shuiCent>SiZhu.baifen[3] && SiZhu.huoCent<SiZhu.baifen[0]) {
      if(SiZhu.tuCent>0 && SiZhu.jinCent>0) _temp += "����ˮ��𺮣�";
      else if(SiZhu.tuCent>0) _temp += "ˮ��������";
      else if(SiZhu.jinCent>0) _temp += "������ˮ��";
      else _temp += "����ѩ�أ�";
    }
    if(_temp.length()>1) _t = "����"+_temp+"ƫ��֮��";

    if(_t.length()>1) {
      add(_t);
      add("������ݶ�����֮����ʹ�����ǣ��������пݡ���֮�������ߣ���ƶ��ز��������ߣ���ƶ����");
      add("�������о����ձط���ƫ�������Ϲ�ƶ�����������뿴�ˣ����Ƿ���Ҳ�ɺ�");
    }
  }

  /**
   * ���ߣ���ʱ����֮��Ҳ�����ߣ�ʧʱ����֮��Ҳ��
   */
  private void getZhenJiaShen() {
    add("������жϣ�����������֮�������˾���͸����ɣ�ν�۵��棬��Ϊ�������𣬵�֮����ƽ����");
  }

  /**
   * ֻ�ɳ��䲻ǿ�������ʼ�������ϲ�ɡ�����������֮�ݾ̡�Ȼ��һ���ɡ��а����ˡ����ƹ�֮�ꡣ�вƹ��ˡ���ϲ����֮�ꡣ�����˽����������ִ죬������ƫ�ݡ������кͰ��������ӡ�
   * Ϊ�ҳ�î�Һ����硣����»��î����»���硣��»�ں���������»��������ϵ����ľ��ˮ������ˮй�㡣
   * ��֮Ϊ����������ѱ�������ٷ�塣Ϊ�����ҡ����糯¶֮��ϡ������ǰ�̡��򲻿ɵá�ϧ�ա����Ŵ˺�Ⱦ�߶�������һ�ն�������֮��塣���޽�ȡ�΢����ǿ��������������������Ӱ���Ρ�����˹Ӧ��
   *����֮ľ�����˶�ˮ���oϲľ�𣬳�����ľ�º�֮������ˮ�˶���ҵ�������ľ���˶����������ߡ���֪������������˥��ϲ�����ӡΪϲ���ߡ�ʧ����塣����ǧ���ӡ�
   * ������ʳ�����������ΐ����������
   *����������֮»����ȥ�̿ɡ�����֮ĸ���򲻿ɳ壬��������һ�������ӡ�
   * @param s
   */

  private void add(String s) {
    buf.append(s+"��\r\n    ");
  }
}