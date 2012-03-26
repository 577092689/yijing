package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.ly.Guaing;
import org.boc.db.ly.Liuyao;
import org.boc.ui.ResultPanel;
import org.boc.util.Messages;

public class DelYiJingMain {

  private DaoYiJingMain daoly;
  private LiuyaoPublic pub;
  private DaoCalendar daocal;
  private PrintWriter pw;
  private Guaing gua;
  StringBuffer str ;

  public DelYiJingMain() {
  	daoly = new DaoYiJingMain();    
    pw = DelLog.getLogObject();
    daocal = new DaoCalendar();
    pub = new LiuyaoPublic(daoly, daocal);
    gua = new Guaing(pub);
    str = new StringBuffer();
  }
  
  public DaoCalendar getDaoCalendar() {
  	return daocal;
  }
  public DaoYiJingMain getDaoYiJingMain() {
  	return daoly;
  }
  public LiuyaoPublic getLiuyaoPublic() {
  	return pub;
  }
  public void setHead(int[] sj, int h, int mi, int sheng ,int shi,String born,boolean sex) {
    str = new StringBuffer();
    //if (SiZhu.yinli == null || "".equals(SiZhu.yinli.trim())) {
    str.append("\n    "+daocal.getShiJian(sj, h, mi, sheng, shi,born,sex));
    //}
  }
  
  /**
   * �����з�ʽ����
   */
  public void getGuaXiang(ResultPanel rp,
  		int mode, int yshen, String mzhu,
  		boolean isBoy, int sheng, int shi,
  		int y, int m, int d, int h, int mi,
  		boolean isYin, boolean isYun,
      int up, int down, int[] acts) {
  	
  	gua.setParameter(rp, mode, yshen, mzhu, isBoy, sheng, shi, y, m, d, h, mi, isYin, isYun, up, down, acts);
  	gua.getGua();
  }

  /**
   * ��ʱ��+��֧|��� ����
   * type: ��ɻ���֧
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int sheng, int shi,String mzhu,boolean sex, int type) {
    str = new StringBuffer();
    
    //0�Ƿ����� 1-8���� 9-11���������� 12-14ȡ��ũ��������
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    //�õ��ǰ���ɻ�����֧����
    int nian = type==Liuyao.SJTG ? sz[1] : type==Liuyao.SJDZ ? sz[2] : sz[2];
    int up = (nian+sz[13]+sz[14])%8 == 0 ? 8 : (nian+sz[13]+sz[14])%8;
    int down = (up+sz[8])%8 == 0 ? 8 : (up+sz[8])%8;
    int change = (nian+sz[10]+sz[11]+sz[8])%6 == 0 ? 6 : (nian+sz[10]+sz[11]+sz[8])%6;
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    return getGuaXiang(up,down,new int[]{change},sz,ys);
  }
  /**
   * �ֹ�ҡ�Ի����ҡ��
   * ǰ̨����1���� ������ 2���� ������ 3���������� �� 4���������� �w
   * ����Ҫ��1Ϊ��,2Ϊ��
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int[] yg,
                            int sheng, int shi,String mzhu,boolean sex) {
    str = new StringBuffer();
    int i=0, j=0;
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    int up = YiJing.XIANGGUA[yg[4]%2==0?2:yg[4]%2]
                            [yg[5]%2==0?2:yg[5]%2]
                            [yg[6]%2==0?2:yg[6]%2];
    int down = YiJing.XIANGGUA[yg[1] % 2 == 0 ? 2 : yg[1] % 2]
                            [yg[2] % 2 == 0 ? 2 : yg[2] % 2]
                            [yg[3] % 2 == 0 ? 2 : yg[3] % 2];

    for(i=0; i<yg.length; i++) {
      if(yg[i]>2) j++;
    }
    int[] change ;
    if(j==0) {
      change = new int[]{0};
    }else{
      change = new int[j];
      j=0;
      for (i = 0; i < yg.length; i++) {
        if (yg[i] > 2) change[j++] = i;
      }
    }

    return getGuaXiang(up,down,change,sz,ys);
  }

  /**
   * ������װ��
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int up, int down,int[] change,
                            int sheng, int shi,String mzhu,boolean sex) {
    str = new StringBuffer();
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    return getGuaXiang(up,down,change,sz,ys);
  }

  /**
   * ��������
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int sg, int xg, boolean isAdd,
                            int sheng, int shi,String mzhu,boolean sex) {
    str = new StringBuffer();
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    int up = sg % 8 == 0 ? 8 : sg % 8;
    int down = xg % 8 == 0 ? 8 : xg % 8;
    int change = 0;
    if(isAdd)
      change = (sg+xg+sz[8])%6==0?6:(sg+xg+sz[8])%6;
    else
      change = (sg+xg)%6==0?6:(sg+xg)%6;
    return getGuaXiang(up,down,new int[]{change},sz,ys);
  }


  /**
   * �������Ե�������س���ոɵõ�����
   * 1. �������Եõ������˹��ԡ�
   * 2. ��������
   * 3. ����
   * 4. ������س��Ӧ����س
   * 5. ��س��֧�������
   * 6. ��س����
   * 7. ����������
   * 8. װ����
   * 9. װ��س������
   * 10. װ�ɷ�
   * @param b ���Ϊ�棬��Ҫ���str���棬����վ����publicȡ������ͷ��������������ȫ�ֺ���
   * @return
   */
  public String getGuaXiang(int up, int down, int[] changes,
                            int[] shijian, int yongshen,boolean b) {
    if(b)  str = new StringBuffer();
    return getGuaXiang(up,down,changes,shijian,yongshen);
  }
  public String getGuaXiang(int up, int down, int[] changes,
                            int[] shijian, int yongshen) {
    try{
      //1. ��/��/��/�˹����Ե��ϡ����Լ���س
      up = up % 8 == 0 ? 8 : up % 8;
      down = down % 8 == 0 ? 8 : down % 8;
      //for(int i=0; i<changes.length; i++) {
      //  changes[i] = (changes[i]%6 == 0 && changes[i]>0)? 6 : changes[i]%6;
      //}
      int rigan = shijian[5];
      int yueZi = shijian[4];

      int upHu = daoly.getHuGuaUpOrDown(up, down, "UP");
      int downHu = daoly.getHuGuaUpOrDown(up, down, "DOWN");

      int upBian = daoly.getBianGuaUpOrDown(up, down, changes, "UP");
      int downBian = daoly.getBianGuaUpOrDown(up, down, changes, "DOWN");

      //2. �������Ե����������˹����Ե��ϡ�����
      int whichGongZhu = daoly.getWhichGong(up, down);
      int whichGongHu = daoly.getWhichGong(upHu, downHu);
      int whichGongBian = daoly.getWhichGong(upBian, downBian);

      int upGong = whichGongZhu;
      int downGong = whichGongZhu;

      //3. ���������˹��Ե�����
      int whichWh = daoly.getWhichWH(whichGongZhu);

      //4. �õ���/��/��������
      int[] upGuaXiang = daoly.getGuaXiang(up);
      int[] downGuaXiang = daoly.getGuaXiang(down);
      int[] guaXiang = daoly.mergeIntArray(upGuaXiang, downGuaXiang);

      int[] upGuaXiangHu = daoly.getGuaXiang(upHu);
      int[] downGuaXiangHu = daoly.getGuaXiang(downHu);
      int[] guaXiangHu = daoly.mergeIntArray(upGuaXiangHu, downGuaXiangHu);

      int[] upGuaXiangBian = daoly.getGuaXiang(upBian);
      int[] downGuaXiangBian = daoly.getGuaXiang(downBian);
      int[] guaXiangBian = daoly.mergeIntArray(upGuaXiangBian, downGuaXiangBian);

      //5. �õ���/��/������س��Ӧس��λ��
      int shiYao = daoly.getShiYao(up, down);
      int yingYao = daoly.getYingYao(shiYao);

      int shiYaoHu = daoly.getShiYao(upHu, downHu);
      int yingYaoHu = daoly.getYingYao(shiYaoHu);

      int shiYaoBian = daoly.getShiYao(upBian, downBian);
      int yingYaoBian = daoly.getYingYao(shiYaoBian);

      //6. �õ���/��/��/�����Եĵ�֧
      int[] upDizi = daoly.getGuaDiZi(up, 1);
      int[] downDizi = daoly.getGuaDiZi(down, 0);
      int[] diZi = daoly.mergeIntArray(upDizi, downDizi);

      int[] upDiziHu = daoly.getGuaDiZi(upHu, 1);
      int[] downDiziHu = daoly.getGuaDiZi(downHu, 0);
      int[] diZiHu = daoly.mergeIntArray(upDiziHu, downDiziHu);

      int[] upDiziBian = daoly.getGuaDiZi(upBian, 1);
      int[] downDiziBian = daoly.getGuaDiZi(downBian, 0);
      int[] diZiBian = daoly.mergeIntArray(upDiziBian, downDiziBian);

      int[] upDiziGong = daoly.getGuaDiZi(upGong, 1);
      int[] downDiziGong = daoly.getGuaDiZi(downGong, 0);
      int[] diZiGong = daoly.mergeIntArray(upDiziGong, downDiziGong);

      //7. �õ���/��/��/���Ե�����
      int[] liuQin = daoly.getLiuQin(diZi, whichWh);
      int[] liuQinHu = daoly.getLiuQin(diZiHu, whichWh);
      int[] liuQinBian = daoly.getLiuQin(diZiBian, whichWh);
      int[] liuQinGong = daoly.getLiuQin(diZiGong, whichWh);

      //8. �õ�����
      int[] liuShen = daoly.getLiuShen(rigan);

      //9. ��ʼȡ������֧����֧����֧��ʱ֧

      //10. �õ������������
      int yueGuaShen = daoly.getGuaShen(up, down, shiYao);
      boolean isHasGuaShen = daoly.isGuaShen(up, down, yueGuaShen);
      int shiShen = daoly.getShiShen(diZi, shiYao);

      //11. ���񡢷���
      int[] ff = daoly.howManyFeiFu(liuQin, liuQinGong);

      //12. �Է��ʡ�س����
      boolean isGuaFanLing = daoly.isGuaXIANGKE(whichGongZhu, whichGongBian);
      boolean isYaoFanLing = false;
      for (int fl = 0; fl < changes.length; fl++) {
        if (daoly.isYaoFanLing(diZi[changes[fl]], diZiBian[changes[fl]])) {
          isYaoFanLing = true;
          break;
        }
      }
      //20. �õ������Դ���س��
      String[] guaCi = daoly.getGuaCiAndYaoCi(up, down);

      /**
       * 21. ��ʼ��ӡ���
       */

      //0). �õ�������ʱ
      //str.append("\r\n    "+SiZhu.yinli);
      //str.append("\r\n    "+SiZhu.yangli);
      daoly.getNYRDOut(str, shijian);

      //1). �õ�����
      daoly.getGuaXiangOut(
          str,
          up, down, changes,
          upBian, downBian,
          upHu, downHu,
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
      daoly.getGuaYaoCiOut(str, guaCi, guaXiang, changes, up, down);
      // Ѯ��
      daoly.getXunKongOut(str, rigan, shijian[6]);
      //3). ������
      daoly.getYeuGuaShenOut(str, yueGuaShen, isHasGuaShen);
      //4). ����
      daoly.getShiShenOut(str, shiShen);
      //5). ����
      daoly.getFanLingOut(str, isGuaFanLing, isYaoFanLing);
      //6). ����
      daoly.getFuLingOut(str, diZi, diZiBian, up, down, upBian, downBian);
      //7). ��������
      daoly.getJinTuiShenOut(str, changes, diZi, diZiBian);
      //8). ���ϡ�������
      daoly.getLiuHeChongOut(str, up, down);
      //9). �ɷ�
      daoly.getFuFeiOut(str, ff, diZi, diZiGong);
      //10). ����ռ��
      daoly.getLiuShenOut(str, liuShen, changes, diZi, liuQin, shiYao, yueZi);
      //11). ʮ������
      daoly.getTwenteenOut(str, up, down);
      //12). ����ʮ������
      daoly.getJFSixteenOut(str, whichGongZhu, upBian, downBian, changes);
      //13). ����
      daoly.getLiuYaoZhanOut(str, up, down, shijian,
                           upGong, downGong, shiYao, yingYao,
                           yongshen, diZi, liuQin, changes,
                           diZiBian, diZiGong, liuQinGong,
                           whichGongZhu, whichGongBian
                           );
      str.append("\r\n\n");
      //Debug.out(str.toString());
    }catch(Exception ex) {
      ex.printStackTrace();
      Messages.error("DelYiJingMain("+ex+")");
    }

    return str.toString();
  }

  public void finalize() {
    str = null;
    pw = null;
    pub = null;
    daocal = null;
    SiZhu.yinli = null;
    SiZhu.yangli = null;
  }

}
