package org.boc.delegate;

import org.boc.dao.DaoBh;
import org.boc.db.Xingming;
import org.boc.db.YiJing;
import java.util.*;
import java.io.PrintWriter;
import org.boc.util.Messages;

public class DelXmMain {
  private DaoBh bh ;
  private int[] wuxing;
  private String[][] wggx;
  private String kg;
  private int[] bhArray;
  private int[] wugeNum;
  private String pingfen;
  private PrintWriter pw;

  public DelXmMain() {
    bh = new DaoBh();
    wuxing = new int[]{YiJing.SHUI, YiJing.MU, YiJing.MU, YiJing.HUO, YiJing.HUO
        , YiJing.TU, YiJing.TU, YiJing.JIN, YiJing.JIN, YiJing.SHUI};
    wggx = new String[5][11];
    wugeNum = new int[5];
    kg = "\r\n    ";
    getWugeGxDesc();
    pw = DelLog.getLogObject();
  }

  public String fx(String xm, boolean isDx) {
    if(xm==null || "".equals(xm.trim())) {
      return "���µ��������������鰡��";
    }
    StringBuffer buff = new StringBuffer();

    try{
      bhArray = new int[xm.length()];
      String tJudge = "";
      int num = 0;
      char zang;
      for (int i = 0; i < xm.length(); i++) {
        zang = xm.charAt(i);
        for (int j = 0; j < Xingming.filter.length; j++) {
          for (int k = 0; k < Xingming.filter[j].length; k++) {
            if (Xingming.filter[j][k] == 0)
              break;
            if (zang == Xingming.filter[j][k])
              num++;
          }
        }
      }
      if (num >= 2)
        return "���µ��������в��ţ��������Ϊ�ϣ�����Ҳ�ա�";
      if (num == 1)
        tJudge = "�в���֮�֣�������һ����";

      String kg = "    ";
      String xmjt = "", xmft = "", xmbh = "", xmwh = "";
      int x1 = 0, x2 = 0, m1 = 0, m2 = 0;
      //String[] wx = {"ˮ","ľ","ľ","��","��","��","��","��","��","ˮ"};
      //�õ�����
      String xm2 = bh.gb2big5(xm);
      //�õ��ո�����������塢�ʻ�������
      int _bh = 0;
      for (int i = 0; i < xm.length(); i++) {
        xmjt += xm.charAt(i) + "  ";
        xmft += xm2.charAt(i) + "  ";
        _bh = bh.getBh(xm2.charAt(i));
        bhArray[i] = _bh;
        xmbh += _bh + "   ";
        xmwh += YiJing.WUXINGNAME[wuxing[ (_bh % 10)]] + "  ";
      }
      //�ж��Ƿ��ǵ���
      boolean isDm = false;
      int len = xm.length();
      if ( (isDx && len >= 3) || (!isDx && len >= 4))
        isDm = false;
      else
        isDm = true;
        //�õ��պ����ıʻ�
      x1 = bh.getBh(xm2.charAt(0));
      if (isDx && isDm) {
        x2 = 0;
        m1 = bh.getBh(xm2.charAt(1));
        m2 = 0;
      }
      else if (!isDx && isDm) {
        x2 = bh.getBh(xm2.charAt(1));
        m1 = bh.getBh(xm2.charAt(2));
        m2 = 0;
      }
      else if (isDx && !isDm) {
        x2 = 0;
        m1 = bh.getBh(xm2.charAt(1));
        m2 = bh.getBh(xm2.charAt(2));
      }
      else {
        x2 = bh.getBh(xm2.charAt(1));
        m1 = bh.getBh(xm2.charAt(2));
        m2 = bh.getBh(xm2.charAt(3));
      }
      //�õ����֮��
      int tg = x2 == 0 ? x1 + 1 : x1 + x2;
      int dg = m2 == 0 ? m1 + 1 : m1 + m2;
      int rg = isDx ? x1 + m1 : x2 + m1;
      int zg = x1 + x2 + m1 + m2;
      int wg = 0;
      if (len == 2) wg = zg - rg + 2;
      else if (len == 3) wg = zg - rg + 1;
      else wg = zg - rg;

      wugeNum = new int[] {
          tg, rg, dg, wg, zg};

      //���
      buff.append(kg + "������ ");
      buff.append(kg + xmjt + "\r\n");
      buff.append(kg + "���壺 ");
      buff.append(kg + xmft + "\r\n");
      buff.append(kg + "�ʻ�:  ");
      buff.append(kg + xmbh + "\r\n");
      buff.append(kg + "���У� ");
      buff.append(kg + xmwh + "\r\n");
      buff.append(kg + "��� ");
      buff.append(kg + "���-" + tg + "-" + YiJing.WUXINGNAME[wuxing[tg % 10]] +
                  "-" + getJxStr(getJx(tg)) +
                  "   �˸�-" + rg + "-" + YiJing.WUXINGNAME[wuxing[rg % 10]] +
                  "-" + getJxStr(getJx(rg)) +
                  "   �ظ�-" + dg + "-" + YiJing.WUXINGNAME[wuxing[dg % 10]] +
                  "-" + getJxStr(getJx(dg)) +
                  "   ���-" + wg + "-" + YiJing.WUXINGNAME[wuxing[wg % 10]] +
                  "-" + getJxStr(getJx(wg)) +
                  "   �ܸ�-" + zg + "-" + YiJing.WUXINGNAME[wuxing[zg % 10]] +
                  "-" + getJxStr(getJx(zg)));
      buff.append("\r\n");
      buff.append("\r\n");
      buff.append("����ϣ�" + tJudge);
      getXingYi(buff, xm2, xm);
      buff.append("\r\n");
      buff.append("\r\n");
      buff.append("�����" + tg + "��" + Xingming.wuge[0] + "\r\n");
      buff.append(kg + getDesc(tg));
      buff.append("\r\n");
      buff.append("�˸���" + rg + "��" + Xingming.wuge[1] + "\r\n");
      buff.append(kg + getDesc(rg));
      buff.append("\r\n");
      buff.append("�ظ���" + dg + "��" + Xingming.wuge[2] + "\r\n");
      buff.append(kg + getDesc(dg));
      buff.append("\r\n");
      buff.append("�����" + wg + "��" + Xingming.wuge[3] + "\r\n");
      buff.append(kg + getDesc(wg));
      buff.append("\r\n");
      buff.append("�ܸ���" + zg + "��" + Xingming.wuge[4] + "\r\n");
      buff.append(kg + getDesc(zg));
      buff.append(kg + "\r\n");
      buff.append("��֮���ף�" + "\r\n");
      buff.append(kg + "�����"+tg+":"+(Xingming.sljx[tg]==null?"":Xingming.sljx[tg])+"\r\n");
      buff.append(kg + "�˸���"+rg+":"+(Xingming.sljx[rg]==null?"":Xingming.sljx[rg])+"\r\n");
      buff.append(kg + "�ظ���"+dg+":"+(Xingming.sljx[dg]==null?"":Xingming.sljx[dg])+"\r\n");
      buff.append(kg + "�����"+wg+":"+(Xingming.sljx[wg]==null?"":Xingming.sljx[wg])+"\r\n");
      buff.append(kg + "�ܸ���"+zg+":"+(Xingming.sljx[zg]==null?"":Xingming.sljx[zg])+"\r\n");
      buff.append(kg + "\r\n");
      buff.append("���ף�\r\n");
      buff.append(kg + getLiuqin(tg, rg, dg, wg, zg));
      buff.append("\r\n");
      buff.append("\r\n");
      buff.append("���֣�");
      buff.append(getTotalCent(xm) + "��");
      buff.append(pingfen);
      buff.append("\r\n");
    }catch(Exception ex) {
    	ex.printStackTrace();
      Messages.error("DelXmMain("+ex+")");
    }

    //System.out.println("x1="+x1+";x2="+x2+";m1="+m1+";m2="+m2);
    //System.out.println(buff.toString());
    return buff.toString();
  }

  private void getXingYi(StringBuffer buff, String xm2,String xm) {
    String zi = null;
    String s1, s2 ,hz;

    for(int i=0; i<xm2.length(); i++) {
      hz = String.valueOf(xm.charAt(i));
      zi = (String)Xingming.map.get(hz);
      if(zi==null) {
        Set set1 = Xingming.mapBshz.keySet();
        for(Iterator it1=set1.iterator(); it1.hasNext();) {
          s1 = (String)it1.next();
          s2 = (String)Xingming.mapBshz.get(s1);
          if(s2.indexOf(hz)!=-1) {
            zi = (String) Xingming.mapBsms.get(s1);
            break;
          }
        }
      }
      if(zi==null){
        zi = "��������ֿ�ȱ��";
      }
      buff.append(kg+xm2.charAt(i)+"��"+zi);
    }
  }

  //1�� 0�� 2��
  private int getJx(int bh) {
    for(int j=0; j<=2; j++) {
      for (int i = 0; i < Xingming.szjx[j].length; i++) {
        if (bh == Xingming.szjx[j][i])
          return j;
      }
    }

    return 0;
  }

  private String getDesc(int bh) {
    if(bh > Xingming.szhy.length)
      return "����ʻ���������������о�֮�С�";

    String[] desc = Xingming.szhy[bh];
    String rs = "";
    for(int i=0; i<desc.length; i++) {
      rs += desc[i]+"\r\n    ";
    }
    return rs;
  }

  private String getJxStr(int jx) {
    if(jx==0) return "ƽ";
    else if(jx==1) return "��";
    else return "��";
  }

  private String getLiuqin(int tg, int rg, int dg, int wg, int zg) {
    String s = "";

    s += getRelate(tg, rg, 1);
    s += getRelate(rg, dg, 2);
    s += getRelate(rg, wg, 3);
    s += getRelate(rg, zg, 4);
    s += getZongAndRen(zg,rg);

    return s;
  }

  /**
   * ������˸�Ĺ�ϵ�����Կ�������
   * @param type 1-������˸� 2-�˸���ظ� 3-�˸������ 4-�˸����ܸ�
   * @return String
   */
  private String getRelate(int g1, int g2, int type) {
    String rs = null;
    boolean g1bh2 = false; //�Ⱥ���
    boolean g1k2 = false;  //g1��g2
    boolean g2k1 = false;  //g2��g1
    int g2Jx = getJx(g2);

    if(YiJing.WXDANKE[wuxing[g1%10]][wuxing[g2%10]]>0) {
      g1k2 = true;
      rs =  wggx[type][0];
    }
    else if(YiJing.WXDANSHENG[wuxing[g1%10]][wuxing[g2%10]]>0)
      rs =  wggx[type][1];
    else if(YiJing.WXDANKE[wuxing[g2%10]][wuxing[g1%10]]>0) {
      g2k1 = true;
      rs =  wggx[type][2];
    }
    else if(YiJing.WXDANSHENG[wuxing[g2%10]][wuxing[g1%10]]>0)
      rs =  wggx[type][3];
    else {
      g1bh2 = true;
      rs =  wggx[type][4];
    }

    if(g2Jx==1 && g1bh2)
      rs +=  wggx[type][5];
    else if(g2Jx==1 && g2k1)
      rs +=  wggx[type][6];
    else if(g2Jx==1 && g1k2)
      rs +=  wggx[type][7];
    else if(g2Jx==2 && g1bh2)
      rs +=  wggx[type][8];
    else if(g2Jx==2 && g2k1)
      rs +=  wggx[type][9];
    else if(g2Jx==2 && g1k2)
      rs +=  wggx[type][10];

    return rs;
  }

  private String getZongAndRen(int zg,int rg) {
    String rs = "";
    int zgjx = getJx(zg);
    int rgjx = getJx(rg);
    if(zgjx == rgjx)
      rs += kg+"�ܸ����˸�������ͬ��������һ֮�ˡ�";
    if((zgjx==1 && rgjx==2) || (zgjx==2 && rgjx==1)) {
      rs += "�ܸ����˸������෴�����ﲻ��֮ͬ�ˡ�";
    }
    if(zgjx==1 && rgjx==2)
      rs += kg+"���������������������ƶ����顣";
    if(zgjx==2 && rgjx==1)
      rs += kg+"�Ƚ�ע�ؾ������������";
    if(zgjx==1 && rgjx==1)
      rs += kg+"����������Ϲ������͡�";
    if(zgjx==2 && rgjx==2)
      rs += kg+"˼��ƫ�����������������ʧ��֮�ˡ�";

      return rs;
  }

  /**
   * ��������
   * 1. ������������ 10��
   * 2. ���������   50��
   * 3. ��������ܹ�ϵ 20��
   * 4. ��������       20��
   * @return int
   */
  private int getTotalCent(String xm) {
    pingfen = "";
    int cent = getWxskCent();
    cent += getWuGeCent();
    cent += getWugeGxCent();
    cent += getZixingCent(xm);

    return 100+cent;
  }

  /**
   * ��������       20��
   * @param xm2 String
   * @return int
   */
  private int getZixingCent(String xm) {
    int cent = 0;
    String hz;

    for(int i=0; i<xm.length(); i++) {
      hz = String.valueOf(xm.charAt(i)).trim();
      if (Xingming.map.get(hz) != null)
        cent -= 5;
      else {
        Set set0 = Xingming.mapBshz.keySet();
        for (Iterator it1 = set0.iterator(); it1.hasNext(); ) {
          if (((String)Xingming.mapBshz.get((String) it1.next())).indexOf(hz) != -1) {
            cent -= 5;
          }
        }
      }
    }
    pingfen += kg+"��������ֿ۳�: "+cent+"%";
    return cent;
  }

  /**
   * ��������ܹ�ϵ 20��
   * @return int
   */
  private int getWugeGxCent() {
    int cent = 0;
    //������˸�
    if (getJx(wugeNum[1])==2 &&
        YiJing.WXXIANGKE[wuxing[wugeNum[0]%10]][wuxing[wugeNum[1]%10]] > 0) {
      cent -= 5;
    }
    //�ء��⡢�����˸�
    for(int i=2; i<wugeNum.length; i++) {
      //System.out.println(getJx(wugeNum[i])+";"+wugeNum[i]);
      if(i==1)
        continue;
      if (getJx(wugeNum[i])==2 &&
          YiJing.WXXIANGKE[wuxing[wugeNum[i]%10]][wuxing[wugeNum[1]%10]] > 0) {
        cent -= 5;
      }
    }
    pingfen += kg+"����ϵ�ֿ۳�: "+cent+"%";
    return cent;
  }

  /**
   * ���������   50��
   * @return int
   */
  private int getWuGeCent() {
    int cent = 0;
    for(int i=0; i<wugeNum.length; i++) {
      if (getJx(wugeNum[i]) == 2)
        cent -= 10;
    }
    pingfen += kg+"����׷ֿ۳�: "+cent+"%";
    return cent;
  }

  /**
   *  ������������ 10��
   * @return int
   */
  private int getWxskCent() {
    int cent = 0;
    for(int i=0; i<bhArray.length - 1; i++) {
      if(YiJing.WXXIANGKE[wuxing[bhArray[i]%10]][wuxing[bhArray[i+1]%10]]>0) {
        cent -= 5;
      }
    }
    if(cent<-10)
      cent=-10;
    pingfen += kg+"�������˷ֿ۳�: "+cent+"%";
    return cent;
  }

  private void getWugeGxDesc() {
    wggx[1] = new String[]{"��ĸ�Ḻ́ܽȽ�������",
        "��ĸ�Ƚ��۰���Ů��",
        "�Ը�ĸ�������ײ�����������",
        "�Ƚ�Т˳��",
        "��ĸ�Ƚ��۰���Ů��",
        kg+"��ҵ�ɼ̳����븸ĸ�ദ������Ǣ��",
        kg+"Ϊ���ֳɼҵ����͡�",
        kg+"�͸�ĸ�׵Ĺ�ϵ���׷������硣",
        kg+"��ĸ����Ů�����н���֮�ǡ�",
        kg+"�Գ����ķ��������ر�ǿ�ҡ�",
        ""
        };
     wggx[2] = new String[]{
         kg+"��ͥ���Ϳ�֮�¡�",
         kg+"�ܵõ���ż����Ů�İ�����",
         kg+"��Ů������ϲ",
         kg+"����Ů�ȽϽ��衣",
         kg+"��ͥ�Ž�������ǿ��",
         kg+"�ܳ����ӹ�ҫ�Ż���",
         "",
         kg+"��Ů�Ķ������ԱȽ����ԡ�",
         "",
         kg+"��ż��Ƣ���Ź֣���Ů�ķ�������Ƚ�ǿ�ҡ�",
         kg+"��ͥ�ж��Ƿǡ�"
     };
     wggx[3] = new String[]{
         kg+"һ����Ϊͬ���������ۡ�",
         kg+"һ��������Ѳ���֮����",
         kg+"�ܵò���֮����",
         kg+"�Դ����ѱȽϿ�������",
         "",
         kg+"�����ù���������",
         kg+"�ܵ����õ��������ܵ����˵��𾴡�",
         kg+"�����൱�ܸɣ������ܳ���Ϊ�Լ����á�",
         "",
         kg+"���׺ͱ��˷��������ϵľ��ף������ʧ��",
         ""
     };
     wggx[4] = new String[]{
         "",
         "",
         "",
         "",
         "",
         kg+"һ�����಻�Ͷ���ĺ��ˡ�",
         kg+"�������ܶ�����̤ʵ�ع�����������ϳɹ���",
         kg+"���ܳɴ��£���һ����;����µ��",
         "",
         kg+"��;���ѣ�˼�뱯�ۡ�",
         kg+"�����������ϻ����ϵĴ������ס�"
     };
  }

  public static void main(String[] args) {
    DelXmMain main = new DelXmMain();
    System.out.println(main.fx("aaa",true));
  }
}
