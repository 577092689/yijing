package org.boc.dao;

import java.io.PrintWriter;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.TieBan;
import org.boc.db.YiJing;
import org.boc.delegate.DelLog;
import org.boc.util.Messages;

public class DaoTieBan {
  private DaoPublic pub ;
  private DaoYiJingMain ly;
  private PrintWriter pw;

  public DaoTieBan() {
    pub = new DaoPublic();
    ly = new DaoYiJingMain();
    pw = DelLog.getLogObject();
  }

  /**
   * �õ������ԣ�0���ԣ�1����
   * @param bz int[] ����
   * @param y int    �������
   * @param boy boolean  �к�orŮ��
   * @param jq int       �����·ݵĽ���
   * @return int[]
   */
  public int[] getXianTianGua(int[] bz, int y, boolean boy, int jq) {
    int[] xtys = getXianTianYuShu(bz,y,boy);
    int yt = getYuanDang(xtys[0],xtys[1], bz[8],boy,jq);
    return new int[]{xtys[0],xtys[1],yt};
  }

  /**
   * �õ������ԣ�Ԫ�������仯��������ת
   * �����͡������ԣ�����س���»����س���³���������س��������
   * @param xtg �������ϡ��¡�����س
   * @param yz int  ��֧
   */
  public int[] getHouTianGua(int[] xtg, int yz) {
    if((xtg[0]==6 && xtg[1]==6) ||
       (xtg[0]==6 && xtg[1]==4) ||
       (xtg[0]==6 && xtg[1]==2) ) {
      if((xtg[2]==6 && TieBan.yzyy[yz]) ||
         (xtg[2]==5 && !TieBan.yzyy[yz])) {
        return new int[]{xtg[1],xtg[0]};
      }
    }
    int up = ly.getBianGuaUpOrDown(xtg[0],xtg[1],new int[]{xtg[2]},"UP");
    int down = ly.getBianGuaUpOrDown(xtg[0],xtg[1],new int[]{xtg[2]},"DOWN");
    return new int[]{down,up};
  }

  /**
   * �õ�����
   */
  public int[] getHuGua(int[] xtg) {
    int up = ly.getHuGuaUpOrDown(xtg[0],xtg[1],"UP");
    int down = ly.getHuGuaUpOrDown(xtg[0],xtg[1],"DOWN");
    return new int[]{up,down};
  }

  /**
   * �õ����Ե�̫����
   */
  public String getGuaTaiHuDesc(int[] gua) {
    int[] tg = getGuaTianGan(gua);
    int[] dz = getGuaDiZi(gua);
    String s = "";
    int sgs=0, xgs=0;

    for(int i=6; i>=4; i--) {
      sgs += TieBan.thtgs[tg[i]] + TieBan.thdzs[dz[i]];
      s += YiJing.TIANGANNAME[tg[i]]+TieBan.thtgs[tg[i]]+"+"+
           YiJing.DIZINAME[dz[i]]+TieBan.thdzs[dz[i]]+" +";
    }
    s = "������="+s.substring(0,s.length()-1)+"="+sgs;
    s = s + "��������=";
    for(int i=3; i>=1; i--) {
      xgs += TieBan.thtgs[tg[i]] + TieBan.thdzs[dz[i]];
      s = s + YiJing.TIANGANNAME[tg[i]]+TieBan.thtgs[tg[i]]+"+"+
           YiJing.DIZINAME[dz[i]]+TieBan.thdzs[dz[i]]+" +";
    }
    s = s.substring(0,s.length()-1)+"="+xgs;
    int tspgs = sgs*100+xgs;
    s = s+"������="+tspgs;
    return s;
  }

  /**
   * �õ����Ե�̫����
   */
  public int getGuaTaiHuShu(int[] gua) {
    int[] tg = getGuaTianGan(gua);
    int[] dz = getGuaDiZi(gua);
    int sgs=0, xgs=0;

    for(int i=6; i>=4; i--)
      sgs += TieBan.thtgs[tg[i]] + TieBan.thdzs[dz[i]];
    for(int i=3; i>=1; i--)
      xgs += TieBan.thtgs[tg[i]] + TieBan.thdzs[dz[i]];

    return sgs*100+xgs;
  }

  /**
   * ����ɺ͵�֧���Եõ�������
   * @param type �ֵ�1 ����2 �޲�3 �ٹ�4 ��ĸ5
   * @param isShi �Խӽ���س����ӦسΪ����
   */
  public String guaShuByGanZi(int g, int z, int type, boolean isShi) {
    String s ;
    int ts = TieBan.tgs[g];
    int ds = TieBan.dzs[z][0]+TieBan.dzs[z][1];
    ts = ts%10==0?ts/10:ts%10;
    ds = ds%10==0?ds/10:ds%10;
    if(ts==5) ts = 6; //�����5һ�ɰ�����Ǭ����������?????
    if(ds==5) ds = 6;
    int[] gua = new int[]{TieBan.htxt[ts], TieBan.htxt[ds]};
    int[] sy = getGuaShiYing(gua);
    int[] dz = getGuaDiZi(gua);
    int[] lq = getGuaLiuQin(gua,gua);
    int[] yao = new int[2];
    int lqYao = 0;
    int syYao = 0;

    s = "����"+YiJing.TIANGANNAME[g]+TieBan.tgs[g];
    s += "������"+YiJing.DIZINAME[z]+
        "("+TieBan.dzs[z][0]+"+"+TieBan.dzs[z][1]+
        "="+(TieBan.dzs[z][0]+TieBan.dzs[z][1])+")Ϊ"+
        YiJing.GUA64NAME[gua[0]][gua[1]]+"��";

    for(int i=1; i<=6; i++) {
      if(lq[i]==type)
        yao[lqYao++] = i;
    }

    syYao = isShi?sy[0]:sy[1];
    if(yao[1]!=0) {
      lqYao = Math.abs(yao[0] - syYao) >= Math.abs(yao[1] - syYao) ? yao[1] :
          yao[0];
      s += "��ȡ��"+lqYao+"س";
    }
    else if(yao[0]==0) { //û�д������ԣ�ȡ��س
      int gong = ly.getWhichGong(gua[0], gua[1]);
      int[] diziG = getGuaDiZi(new int[] {gong, gong});
      int[] lqG = getGuaLiuQin(new int[] {gong, gong}
                               , new int[] {gong, gong});
      for (int i = 1; i <= 6; i++) {
        if (lqG[i] == type) {
          s += "��ȡ����"+YiJing.GUA64NAME[gong][gong]+"��"+i+"س";
          return YiJing.LIUQINNAME[lqG[i]]+
              YiJing.DIZINAME[diziG[i]]+
              YiJing.WUXINGNAME[YiJing.DIZIWH[diziG[i]]];
        }
      }
    } else {
      lqYao = yao[0];
      s += "��ȡ��"+lqYao+"س";
    }

    return s+YiJing.LIUQINNAME[lq[lqYao]]+
           YiJing.DIZINAME[dz[lqYao]]+
           YiJing.WUXINGNAME[YiJing.DIZIWH[dz[lqYao]]];
  }

  /**
   * �������
   */
  public String printGuaXiang(int rigan, int[] zg, int[] bg, int[] hg) {
    StringBuffer str = new StringBuffer();
    try{
      //2. �������Ե����������˹����Ե��ϡ�����
      int whichGongZhu = ly.getWhichGong(zg[0], zg[1]);
      int whichGongHu = ly.getWhichGong(hg[0], hg[1]);
      int whichGongBian = ly.getWhichGong(bg[0], bg[1]);
      int upGong = whichGongZhu;
      int downGong = whichGongZhu;

      //3. ���������˹��Ե�����
      int whichWh = ly.getWhichWH(whichGongZhu);

      //4. �õ���/��/��������
      int[] upGuaXiang = ly.getGuaXiang(zg[0]);
      int[] downGuaXiang = ly.getGuaXiang(zg[1]);
      int[] guaXiang = ly.mergeIntArray(upGuaXiang, downGuaXiang);

      int[] upGuaXiangHu = ly.getGuaXiang(hg[0]);
      int[] downGuaXiangHu = ly.getGuaXiang(hg[1]);
      int[] guaXiangHu = ly.mergeIntArray(upGuaXiangHu, downGuaXiangHu);

      int[] upGuaXiangBian = ly.getGuaXiang(bg[0]);
      int[] downGuaXiangBian = ly.getGuaXiang(bg[1]);
      int[] guaXiangBian = ly.mergeIntArray(upGuaXiangBian, downGuaXiangBian);

      //5. �õ���/��/������س��Ӧس��λ��
      int shiYao = ly.getShiYao(zg[0], zg[1]);
      int yingYao = ly.getYingYao(shiYao);

      int shiYaoHu = ly.getShiYao(hg[0], hg[1]);
      int yingYaoHu = ly.getYingYao(shiYaoHu);

      int shiYaoBian = ly.getShiYao(bg[0], bg[1]);
      int yingYaoBian = ly.getYingYao(shiYaoBian);

      //6. �õ���/��/��/�����Եĵ�֧
      int[] upDizi = ly.getGuaDiZi(zg[0], 1);
      int[] downDizi = ly.getGuaDiZi(zg[1], 0);
      int[] diZi = ly.mergeIntArray(upDizi, downDizi);

      int[] upDiziHu = ly.getGuaDiZi(hg[0], 1);
      int[] downDiziHu = ly.getGuaDiZi(hg[1], 0);
      int[] diZiHu = ly.mergeIntArray(upDiziHu, downDiziHu);

      int[] upDiziBian = ly.getGuaDiZi(bg[0], 1);
      int[] downDiziBian = ly.getGuaDiZi(bg[1], 0);
      int[] diZiBian = ly.mergeIntArray(upDiziBian, downDiziBian);

      int[] upDiziGong = ly.getGuaDiZi(upGong, 1);
      int[] downDiziGong = ly.getGuaDiZi(downGong, 0);
      int[] diZiGong = ly.mergeIntArray(upDiziGong, downDiziGong);

      //7. �õ���/��/��/���Ե�����
      int[] liuQin = ly.getLiuQin(diZi, whichWh);
      int[] liuQinHu = ly.getLiuQin(diZiHu, whichWh);
      int[] liuQinBian = ly.getLiuQin(diZiBian, whichWh);
      int[] liuQinGong = ly.getLiuQin(diZiGong, whichWh);

      //8. �õ�����
      int[] liuShen = ly.getLiuShen(rigan);

      //11. ���񡢷���
      int[] ff = ly.howManyFeiFu(liuQin, liuQinGong);

      //20. �õ������Դ���س��
      String[] guaCi = ly.getGuaCiAndYaoCi(zg[0], zg[1]);

      //1). �õ�����
      ly.getGuaXiangOut(
          str,
          zg[0], zg[1], new int[]{zg[2]},
          bg[0], bg[1],
          hg[0], hg[1],
          whichGongZhu, whichGongBian, whichGongHu,
          shiYao, yingYao,
          shiYaoBian, yingYaoBian,
          shiYaoHu, yingYaoHu,
          diZi, diZiBian, diZiHu, diZiGong,
          guaXiang, guaXiangBian, guaXiangHu,
          liuQin, liuQinBian, liuQinHu, liuQinGong,
          liuShen,
          ff);
      //2). �Դ���س��
      ly.getGuaYaoCiOut(str, guaCi, guaXiang, new int[]{zg[2]}, zg[0], zg[1]);
      str.append("\r\n\n");
    }catch(Exception ex) {
      Messages.error("DaoTeiBan("+ex+")");
    }
    return str.toString();
  }

  /**
   * ���ֵ����
   * ���������ԣ����ԣ�Ԫ��
   */
  public String getDaZhiYunGua(int[] zg, int[] bg, int year) {
    String str = "";
    int[] sy = getGuaShiYing(zg);
    int[] gxzg = getGuaXiang(zg);  //����
    int[] gxbg = getGuaXiang(bg);
    int[] lqzg = getGuaLiuQin(zg,zg); //����
    int[] lqbg = getGuaLiuQin(bg,zg);
    int[] tgzg = getGuaTianGan(zg);//���
    int[] tgbg = getGuaTianGan(bg);
    int[] dzzg = getGuaDiZi(zg);   //��֧
    int[] dzbg = getGuaDiZi(bg);
    int[] wxzg = getGuaWuXing(zg); //����
    int[] wxbg = getGuaWuXing(bg);
    int x = 0, y = 0;

    for(int i=sy[0]; i<=6; i++) {
      x = (gxzg[i]==1)?9:6;
      y += x;
      str += "\r\n    "+(y-x+1<10?"0"+(y-x+1):""+(y-x+1))+
          "-"+(y<10?"0"+y:""+y)+"�꣺���Ե�"+i+"س��"+
          YiJing.LIUQINNAME[lqzg[i]]+
          YiJing.TIANGANNAME[tgzg[i]]+
          YiJing.DIZINAME[dzzg[i]]+
          YiJing.WUXINGNAME[wxzg[i]]+
          getZhiNianGua(zg,i, y-x+1,gxzg, year);
    }
    for(int i=1; i<sy[0]; i++) {
      x = (gxzg[i]==1)?9:6;
      y += x;
      str += "\r\n    "+(y-x+1<10?"0"+(y-x+1):""+(y-x+1))+
          "-"+(y<10?"0"+y:""+y)+"�꣺���Ե�"+i+"س��"+
          YiJing.LIUQINNAME[lqzg[i]]+
          YiJing.TIANGANNAME[tgzg[i]]+
          YiJing.DIZINAME[dzzg[i]]+
          YiJing.WUXINGNAME[wxzg[i]]+
          getZhiNianGua(zg,i, y-x+1,gxzg, year);
    }
    for(int i=1; i<=6; i++) {
      x = (gxbg[i]==1)?9:6;
      y += x;
      str += "\r\n    "+(y-x+1<10?"0"+(y-x+1):""+(y-x+1))+
          "-"+(y<10?"0"+y:""+y)+"�꣺���Ե�"+i+"س��"+
          YiJing.LIUQINNAME[lqbg[i]]+
          YiJing.TIANGANNAME[tgbg[i]]+
          YiJing.DIZINAME[dzbg[i]]+
          YiJing.WUXINGNAME[wxbg[i]]+
          getZhiNianGua(bg,i, y-x+1,gxbg, year);
    }

    return str;
  }

  /**
   * �õ�ֵ����
   * @param gua int[] ����
   * @param yao int   �ڼ�س��Ϊֵ���ԵĿ�ʼس
   * @param sui int   ��س�ĳ�ʼ��
   * @param gx int[]  ����
   * @param year      �������
   * @return String
   */
  public String getZhiNianGua(int[] gua, int yao ,int sui,
                              int[] gx, int year) {
    String str = "";
    String[] sz = {null,"��","��","��","��","��","��"};
    int x1 = yao+3>6?yao-3:yao+3;
    int x2 = x1+3>6?x1-3:x1+3;
    int[] bianYao = {0,yao, x1, x2,
        (x2+1)%6==0?6:(x2+1)%6, (x2+2)%6==0?6:(x2+2)%6, (x2+3)%6==0?6:(x2+3)%6,
        (x2+4)%6==0?6:(x2+4)%6, (x2+5)%6==0?6:(x2+5)%6, (x2+6)%6==0?6:(x2+6)%6};

    for(int i=1; i<=(gx[yao]==1?9:6); i++) {
      year += sui+i; //����
      /**
       * ��1س���ñ���
       * �������������س��������ͬ���ñ�
       */
      if(i>1 ||
         (gx[bianYao[i]]==1 && isYangNian(year)) ||
         (gx[bianYao[i]]==0 && !isYangNian(year))) {
        gua = getBianGua(gua, bianYao[i]);
        gx = getGuaXiang(gua);
      }
      str += sui++ +"-"+
        YiJing.GUA64NAME[gua[0]][gua[1]]+
        (gx[bianYao[i]]==1?"��":"��")+sz[bianYao[i]]+"��";
    }

    return "\r\n        "+str.substring(0,str.length()-1);
  }

  /**
   * �Ƿ��������
   */
  private boolean isYangNian(int year) {
    year = year - Calendar.IYEAR;
    int g = (Calendar.IYEARG+year)%10==0?10:(Calendar.IYEARG+year)%10;
    //Debug.out(YiJing.TIANGANNAME[g]);
    return g%2==1;
  }

  //������Ͷ�س�õ�����
  private int[] getBianGua(int[] gua, int yao) {
    if(yao<=0)
      return new int[]{gua[0],gua[1]};
    int sbg = ly.getBianGuaUpOrDown(gua[0],gua[1],new int[]{yao},"UP");
    int xbg = ly.getBianGuaUpOrDown(gua[0],gua[1],new int[]{yao},"DOWN");
    return new int[]{sbg, xbg};
  }

  /**
   * �õ����Եĵ�֧���ӳ�س����س
   */
  public int[] getGuaDiZi(int[] gua) {
    int[] upDizi = ly.getGuaDiZi(gua[0], 1);
    int[] downDizi = ly.getGuaDiZi(gua[1], 0);
    int[] diZi = ly.mergeIntArray(upDizi, downDizi);
    return diZi;
  }

  /**
   * �õ����Ե���ɣ��ӳ�س����س
   */
  public int[] getGuaTianGan(int[] gua) {
    int[] upTg = ly.getGuaTG(gua[0], 1);
    int[] downTg = ly.getGuaTG(gua[1], 0);
    int[] tg = ly.mergeIntArray(upTg, downTg);
    return tg;
  }

  /**
   * �õ����Ե���Ӧ
   * @param gua int[]
   * @return int[]
   */
  public int[] getGuaShiYing(int[] gua) {
    int shiYao = ly.getShiYao(gua[0], gua[1]);
    int yingYao = ly.getYingYao(shiYao);
    return new int[]{shiYao,yingYao};
  }

  //�õ��Ե�����
  public int[] getGuaLiuQin(int[] gua, int[] zg) {
    int[] dizi = getGuaDiZi(gua);
    int whichGongZhu = ly.getWhichGong(zg[0], zg[1]);
    int whichWh = ly.getWhichWH(whichGongZhu);
    return ly.getLiuQin(dizi, whichWh);
  }

  //�õ��Ե�����
  public int[] getGuaWuXing(int[] gua) {
    int[] dizi = getGuaDiZi(gua);
    for(int i=1; i<dizi.length; i++) {
      dizi[i] = YiJing.DIZIWH[dizi[i]];
    }
    return dizi;
  }

  //�õ��Ե�����
  public int[] getGuaXiang(int[] gua) {
    int[] sg = ly.getGuaXiang(gua[0]);
    int[] xg = ly.getGuaXiang(gua[1]);
    return ly.mergeIntArray(sg,xg);
  }

  /**
   * �õ������Ե���������������������������Ů���ϵ��£�����֮
   * @param bz int ������֧
   * @param y int  ��λ�������
   * @param boy boolean �к�true Ů��false
   */
  private int[] getXianTianYuShu(int[] bz, int y, boolean boy) {
    int ts=0;
    int ds=0;

    for(int i=1; i<9; i+=2) {
      if(TieBan.tgs[bz[i]]%2==1)
        ts += TieBan.tgs[bz[i]];
      else
        ds += TieBan.tgs[bz[i]];
      for(int j=0; j<2; j++) {
        if(TieBan.dzs[bz[i+1]][j]%2==1)
        ts += TieBan.dzs[bz[i+1]][j];
      else
        ds += TieBan.dzs[bz[i+1]][j];
      }
    }

    ts = ts>25?ts-25:ts;
    ds = ds>30?ds-30:ds;
    ts = ts%10==0?ts/10:ts%10;
    ds = ds%10==0?ds/10:ds%10;
    ts = procFive(ts,y,bz[1],boy);
    ds = procFive(ds,y,bz[1],boy);

    if(pub.isYnanYinlv(bz[1],boy))
      return new int[]{TieBan.htxt[ts], TieBan.htxt[ds]};
    return new int[]{TieBan.htxt[ds], TieBan.htxt[ts]};
  }

  /**
   * �õ�Ԫ�ã�����س��
   * ���������ԣ����ԣ�ʱ֧
   */
  public int getYuanDang(int sg, int xg, int sz,boolean sex, int jq) {
    int[] gx = combine(sg,xg);
    int[] yys = getHowManYY(gx);  //0-�� 1-��
    int yt = 0;
    int type = 0;

    //��ʱ��س����ʱ��س
    if((TieBan.szyy[sz] && yys[1]==1) || (!TieBan.szyy[sz] && yys[0]==1))
      yt = getYangYinShi1(gx,sz,TieBan.szyy[sz]);
    else if((TieBan.szyy[sz] && yys[1]==2) || (!TieBan.szyy[sz] && yys[0]==2))
      yt = getYangYinShi2(gx,sz,TieBan.szyy[sz]);
    else if((TieBan.szyy[sz] && yys[1]==3) || (!TieBan.szyy[sz] && yys[0]==3))
      yt = getYangYinShi3(gx,sz,TieBan.szyy[sz]);
    else if((TieBan.szyy[sz] && yys[1]==4) || (!TieBan.szyy[sz] && yys[0]==4))
      yt = getYangYinShi4(gx,sz,TieBan.szyy[sz]);
    else if((TieBan.szyy[sz] && yys[1]==5) || (!TieBan.szyy[sz] && yys[0]==5))
      yt = getYangYinShi5(gx,sz,TieBan.szyy[sz]);
    else if(yys[1]==6) {
      type = getQianType(sz,sex,jq);
      yt = getQianKunYuanTang(sz,type);
    }else if(yys[0]==6) {
      type = getKunType(sz,sex,jq);
      yt = getQianKunYuanTang(sz,type);
    }

    return yt;
  }

  /**
   * ��ʱһ��س,��ʱһ��س
   * ��������������ʱ֧��һ��س����һ��س
   */
  private int getYangYinShi1(int[] gx, int sz, boolean yy){
    int yao = 0;
    int yyBase = 0;  //����������һ��س3��һ��س9
    int yaoYY = 0;
    if(yy) {
      yyBase = 3;
      yaoYY = YiJing.YANGYAO;
    }else {
      yyBase = 9;
      yaoYY = YiJing.YINYAO;
    }

    for(yao=1; yao<gx.length; yao++) {
      if(gx[yao]==yaoYY) break;
    }

    return sz<yyBase?yao:(yao<sz-yyBase+2?sz-yyBase+2:sz-yyBase+1);
  }

  /**
   * ��ʱ����س,��ʱ����س
   */
  private int getYangYinShi2(int[] gx, int sz, boolean yy){
    int[] yao = new int[6];    //��һ����ڶ�����������س
    int[] yao_ = new int[6];
    int yaoYY = YiJing.YANGYAO;   //��ʱ����س����ʱ����س
    int yaoYY_ = YiJing.YANGYAO;  //��ʱ����س����ʱ����س
    int j=0, j_ = 0;

    if(yy) yaoYY_ = YiJing.YINYAO;
    else yaoYY = YiJing.YINYAO;

    for(int i=1; i<gx.length; i++) {
      if(gx[i]==yaoYY) yao[j++] = i;
      else yao_[j_++] = i;
    }
    int[] rs = new int[]{0,yao[0],yao[1],yao[0],yao[1],yao_[0],yao_[1],
                           yao[0],yao[1],yao[0],yao[1],yao_[0],yao_[1]};

    return rs[sz];
  }

  /**
   * ��ʱ����س,��ʱ����س
   */
  private int getYangYinShi3(int[] gx, int sz, boolean yy){
    int[] yao = new int[6];       //��x����������س
    int yaoYY = YiJing.YANGYAO;   //��ʱ����س����ʱ����س
    int j=0;

    if(!yy)
      yaoYY = YiJing.YINYAO;

    for(int i=1; i<gx.length; i++) {
      if(gx[i]==yaoYY)
        yao[j++] = i;
    }
    int[] rs = new int[]{0,yao[0],yao[1],yao[2],yao[0],yao[1],yao[2],
                           yao[0],yao[1],yao[2],yao[0],yao[1],yao[2]};

    return rs[sz];
  }

  /**
   * ��ʱ����س,��ʱ����س
   */
  private int getYangYinShi4(int[] gx, int sz, boolean yy){
    int[] yao = new int[6];    //��һ����ڶ�����������س
    int[] yao_ = new int[6];
    int yaoYY = YiJing.YANGYAO;   //��ʱ����س����ʱ����س
    int yaoYY_ = YiJing.YANGYAO;  //��ʱ����س����ʱ����س
    int j=0, j_ = 0;

    if(yy) yaoYY_ = YiJing.YINYAO;
    else yaoYY = YiJing.YINYAO;

    for(int i=1; i<gx.length; i++) {
      if(gx[i]==yaoYY) yao[j++] = i;
      else yao_[j_++] = i;
    }
    int[] rs = new int[]{0,yao[0],yao[1],yao[2],yao[3],yao_[0],yao_[1],
                           yao[0],yao[1],yao[2],yao[3],yao_[0],yao_[1]};

    return rs[sz];
  }

  /**
   * ��ʱ����س,��ʱ����س
   */
  private int getYangYinShi5(int[] gx, int sz, boolean yy){
    int[] yao = new int[6];    //��һ����ڶ�����������س
    int[] yao_ = new int[6];
    int yaoYY = YiJing.YANGYAO;   //��ʱ����س����ʱ����س
    int yaoYY_ = YiJing.YANGYAO;  //��ʱ����س����ʱ����س
    int j=0, j_ = 0;

    if(yy) yaoYY_ = YiJing.YINYAO;
    else yaoYY = YiJing.YINYAO;

    for(int i=1; i<gx.length; i++) {
      if(gx[i]==yaoYY) yao[j++] = i;
      else yao_[j_++] = i;
    }
    int[] rs = new int[]{0,yao[0],yao[1],yao[2],yao[3],yao[4],yao_[0],
                           yao[0],yao[1],yao[2],yao[3],yao[4],yao_[0]};

    return rs[sz];
  }

  /**
   * �õ�Ǭ�Ե�����
   * Ǭ��1���� Ů��(����-����) 2Ů��(����-����) 3���� Ů��(����-����) 4Ů��(����-����)
   * ������ʱ֧���Ա𣬱��½���
   */
  private int getQianType(int sz, boolean sex, int jq) {
    boolean xzdz = false;
    if(jq>=10 && jq<22)
      xzdz = true;

    if((sz%2==1 && sex) || (sz%2==1 && !sex && xzdz))
      return 1;
    else if(sz%2==1 && !sex && !xzdz)
      return 2;
    else if((sz%2==0 && sex) || (sz%2==0 && !sex && xzdz))
      return 3;
    else
      return 4;
  }

  /**
   * �õ����Ե�����
   * ����1Ů�� ����(����-����) 2����(����-����) 3Ů�� ����(����-����) 4����(����-����)
   * ������ʱ֧���Ա𣬱��½���
   */
  private int getKunType(int sz, boolean sex, int jq) {
    boolean xzdz = false;
    if(jq>=10 && jq<22)
      xzdz = true;

    if((sz%2==1 && !sex) || (sz%2==1 && sex && !xzdz))
      return 1;
    else if(sz%2==1 && sex && xzdz)
      return 2;
    else if((sz%2==0 && !sex) || (sz%2==0 && sex && !xzdz))
      return 3;
    else
      return 4;
  }

  /**
   * Ǭ����
   * type 1-��3س�����ϣ�2-��3س������ 3-��3س������ 4-��3س������
   */
  private int getQianKunYuanTang(int sz, int type) {
    int[] rs = {0,0,0,0,0,0,0};
    switch(type) {
      case 1:
        rs = new int[] {0, 1, 2, 3, 1, 2, 3};
        break;
      case 2:
        rs = new int[] {0, 6, 5, 4, 6, 5, 4};
        break;
      case 3:
        rs = new int[] {0,0,0,0,0,0,0, 4, 5, 6, 4, 5, 6};
        break;
      case 4:
        rs = new int[] {0,0,0,0,0,0,0, 3, 2, 1, 3, 2, 1};
        break;
      default:
        break;
    }
    return rs[sz];
  }

  private int[] combine(int sg, int xg) {
    int[] sggx = YiJing.GUAXIANG[sg];
    int[] xggx = YiJing.GUAXIANG[xg];
    return new int[]{0,xggx[1],xggx[2],xggx[3],
        sggx[1],sggx[2],sggx[3]};
  }

  //���� [0]��س�� [1]��س��
  private int[] getHowManYY(int[] gx) {
    int yin=0, yang=0;

    for(int i=0; i<gx.length; i++) {
      if(gx[i]==1)  yang++;
      else  yin++;
    }
    return new int[]{yin,yang};
  }

  /**
   * 5����Ϊ����Ҫ����
   * @param num int
   */
  private int procFive(int num, int y, int ng, boolean boy) {
    if(num!=5) return num;
    int yuan = whichYuan(y);

    if((boy && yuan==1) || (pub.isYnanYinlv(ng,boy) && yuan==2))
      return 8;
    else if((!boy && yuan==1) || (!pub.isYnanYinlv(ng,boy) && yuan==2))
      return 2;
    else if(boy && yuan==3)
      return 9;
    else
      return 7;
  }

  /**
   * ������ж�����һ�ޣ�1Ϊ��Ԫ��2Ϊ��Ԫ��3Ϊ��Ԫ
   * ��֪��Ԫ4��Ϊ��Ԫ��㣬ÿԪ60�꣬��3Ԫ��ѭ��
   */
  private int whichYuan(int y) {
    return ((y-4)/60)%3==0?3:((y-4)/60)%3;
  }

  public static void main(String[] args) {
    DaoTieBan tb = new DaoTieBan();
    /*System.out.println(tb.getYangYinShi2(new int[]{0,1,1,2,2,2,2},YiJing.SI,true));
    //System.out.println(tb.getYangYinShi3(new int[]{0,2,1,1,2,2,1},YiJing.SI,true));
    //System.out.println(tb.getYangYinShi4(new int[]{0,2,1,2,2,1,2},YiJing.HAI,false));
    //System.out.println(tb.getYangYinShi5(new int[]{0,2,2,2,2,1,2},YiJing.XU,false));
    //System.out.println(tb.getQianKunYuanTang(YiJing.YOU,4));
    int[] zg = tb.getXianTianGua(new int[]{0,4,6,2,6,4,4,10,4},1977,true,7);
    System.out.println(YiJing.GUA64NAME[zg[0]][zg[1]]+" ��س��"+zg[2]);
    int[] bg = tb.getHouTianGua(zg, 6);
    System.out.println(YiJing.GUA64NAME[bg[0]][bg[1]]);
    int[] hg = tb.getHuGua(zg);
    System.out.println(YiJing.GUA64NAME[hg[0]][hg[1]]);
    System.out.println("����"+tb.getGuaTaiHuShu(zg));
    System.out.println("����"+tb.getGuaTaiHuShu(bg));
    System.out.println("����"+tb.getGuaTaiHuShu(hg));
    System.out.println(YiJing.GUA64NAME[4][8]+tb.getGuaTaiHuShu(new int[]{4,8}));
    System.out.println();
    String str = tb.printGuaXiang(4,zg,bg,hg);
    System.out.println(str);
    */
    //String s = tb.getDaZhiYunGua(new int[]{3,3},new int[]{4,4} , 1999);
    //System.out.println(s);
    System.out.println(tb.isYangNian(1977));
  }

}
