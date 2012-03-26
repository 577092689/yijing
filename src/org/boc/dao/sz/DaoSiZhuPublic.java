package org.boc.dao.sz;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuPublic {

  public String sep = "\r\n    ";
  public String kg = "    ";

  /**
   * �ж�ĳ�����д�����ж�
   * 1.����>=4
   * 2.����+�Լ�>4 && �Լ�>=1
   * 3.��ֻ򷽾�
   * 4.�ɺϾֻ�֧���ϻ���ڰ�� && �Լ�>=2
   * @param type
   * @return
   */
  public boolean isTooManyWX(int type) {
    int j = 0;
    j = getHowWx(type);
    if(j>=4) return true;

    if(j<1)
      return false;

    if(type==YiJing.SHUI) j+=getHowWx(YiJing.JIN);
    if(type==YiJing.MU) j+=getHowWx(YiJing.SHUI);
    if(type==YiJing.HUO) j+=getHowWx(YiJing.MU);
    if(type==YiJing.TU) j+=getHowWx(YiJing.HUO);
    if(type==YiJing.JIN) j+=getHowWx(YiJing.TU);
    if(j>4) return true;

    if(j>1 &&
       ((isTF(6,10,2,18,2,10) && type==YiJing.JIN) ||
       (isTF(9,10,11,30,9,11) && type==YiJing.JIN) ||
       (isTF(12,4,8,24,4,12) && type==YiJing.MU) ||
       (isTF(3,4,5,12,3,5) && type==YiJing.MU) ||
       (isTF(9,1,5,15,1,9) && type==YiJing.SHUI) ||
       (isTF(12,1,2,15,1,12) && type==YiJing.SHUI) ||
       (isTF(3,7,11,21,3,11) && type==YiJing.HUO) ||
       (isTF(6,7,8,21,6,8) && type==YiJing.HUO)))
      return true;

    return false;
  }

  /**
   * �ж��������Ƿ�����ż�����
   */
  public int[] isHasPOXOfMing(int ss) {
    int[] pos = new int[4];
    int i = 0;

    int sswx = YiJing.TIANGANWH[getShenShaName(ss,1)[0]];
    if(YiJing.TIANGANWH[SiZhu.ng] == sswx)
      pos[i++] = SiZhu.ng;
    if(YiJing.TIANGANWH[SiZhu.yg] == sswx)
      pos[i++] = SiZhu.yg;
    if(YiJing.TIANGANWH[SiZhu.rg] == sswx)
      pos[i++] = SiZhu.rg;
    if(YiJing.TIANGANWH[SiZhu.sg] == sswx)
      pos[i++] = SiZhu.sg;

    return pos;
  }

  /**
   * �жϴ��˻���������Ƿ�����ż��
   */
  public boolean isPOX(int ss, int g) {
    int sswx = YiJing.TIANGANWH[getShenShaName(ss,1)[0]];
    return sswx==YiJing.TIANGANWH[g];
  }


  /**
   * �Ƿ�����
   */
  public int isLiuHe(int z1, int z2) {
    return (YiJing.DZLIUHE[z1][z2]);
  }

  /**
   * ��֧�Ƿ������������ϲ�����֧
   */
  public int isLiuHe(int z) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz, z};
    int wx = YiJing.DZLIUHE[z][SiZhu.nz];
    if(wx > 0) return wx;
    wx = YiJing.DZLIUHE[z][SiZhu.yz];
    if(wx > 0) return wx;
    wx = YiJing.DZLIUHE[z][SiZhu.sz];
    if(wx > 0) return wx;

    return 0;
  }


  /**
   * �Ƿ����
   */
  public int isWuHe(int z1, int z2) {
    return (YiJing.TGWUHE[z1][z2]);
  }

  /**
   * �Ƿ��������������֧
   */
  public int isSanHuiOfRizhu(int z1, int z2, int z3) {
    return (YiJing.DZSHANHUI[z1][z2][z3]);
  }

  /**
   * �Ƿ�����������겻����֧������
   */
  public int isSanHui(int z1, int z2) {
    int wx = (YiJing.DZSHANHUI[z1][z2][SiZhu.nz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][z2][SiZhu.yz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][z2][SiZhu.sz]);
    if(wx>0) return wx;
    return 0;
  }

  /**
   * �Ƿ��������꺬��֧������
   */
  public int isSanHuiOfRizhu(int z1) {
    int wx = (YiJing.DZSHANHUI[z1][SiZhu.rz][SiZhu.nz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][SiZhu.rz][SiZhu.yz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][SiZhu.rz][SiZhu.sz]);
    if(wx>0) return wx;
    return 0;
  }

  /**
   * �Ƿ��������겻����֧������
   */
  public int isSanHui(int z1) {
        int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
        for(int i=0; i<zs.length; i++) {
          for(int j=0; j<zs.length; j++) {
            if(zs[j]==zs[i]) continue;
            if(YiJing.DZSHANHUI[zs[i]][zs[j]][z1] > 0)
              return YiJing.DZSHANHE[zs[i]][zs[j]][z1];
          }
        }
        return 0;
  }

  /**
   * �����������Ƿ����Ͼ֣�������֧
   */
  public int isSanHe(int z) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
    for(int i=0; i<zs.length; i++) {
      for(int j=0; j<zs.length; j++) {
        if(zs[j]==zs[i]) continue;
        if(YiJing.DZSHANHE[zs[i]][zs[j]][z] > 0)
          return YiJing.DZSHANHE[zs[i]][zs[j]][z];
      }
    }
    return 0;
  }

  /**
   * ���������ְ�����֧�Ƿ����Ͼ�
   */
  public int isSanHeOfRizhu(int z) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
    for(int i=0; i<zs.length; i++) {
      if(YiJing.DZSHANHE[zs[i]][SiZhu.rz][z] > 0)
        return YiJing.DZSHANHE[zs[i]][SiZhu.rz][z];
    }
    return 0;
  }


  /**
   * ����������������Ƿ����Ͼֲ�����֧
   */
  public int isSanHe(int z1, int z2) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
    for (int i = 0; i < zs.length; i++) {
      if (YiJing.DZSHANHE[zs[i]][z1][z2] > 0)
        return YiJing.DZSHANHE[zs[i]][z1][z2];
    }
    return 0;
  }

  /**
   * �������������֧�Ƿ����Ͼ�
   */
  public int isSanHeOfRizhu(int z1, int z2) {
    return YiJing.DZSHANHE[z1][z2][SiZhu.rz];
  }


  /**
   * ��������֧�Ƿ������е������
   */
  public int isWangBanHe(int z1, int z2) {
    return (SiZhu.wangbanhe[z1][z2]);
  }

  /**
   * ��ͷ
   * �ؽţ����� ��֧ ��֧(ľ������� ���ˮ��� ����ľ��ˮ ����ľˮ ˮ��ľ����δ)
   * ��1 ��2
   * type 1�ϸ�  2��֧
   * @return
   */
  public int getGaiTouJieJiao(int g, int z, int type) {
    if(type == 2) {
      if (YiJing.WXDANKE[YiJing.TIANGANWH[g]][YiJing.DIZIWH[z]] > 0) {
        return 1;
      }
    }else{
      if ( (YiJing.WXDANSHENG[YiJing.TIANGANWH[g]][YiJing.DIZIWH[z]] > 0 ||
            YiJing.WXDANKE[YiJing.DIZIWH[z]][YiJing.TIANGANWH[g]] > 0 ||
            YiJing.WXDANKE[YiJing.TIANGANWH[g]][YiJing.DIZIWH[z]] > 0) &&
          !isHasMu(g, z) && !isHasYuqi(g, z) &&
          YiJing.TIANGANWH[g] != YiJing.DIZIWH[z] &&
          YiJing.WXDANSHENG[YiJing.DIZIWH[z]][YiJing.TIANGANWH[g]] <= 0) {
        return 2;
      }
    }

    return 0;
  }

  /**
   * ��ָ�����еõ���Ϊ��������ɷ
   */
  public int getShenShaByWX(int wx) {
    if(YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.rg]][wx]>0)
      return 2;
    else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.rg]][wx]>0)
      return 1;
    else if(YiJing.WXDANKE[wx][YiJing.TIANGANWH[SiZhu.rg]]>0)
      return 3;
    else if(YiJing.WXDANSHENG[wx][YiJing.TIANGANWH[SiZhu.rg]]>0)
      return 4;
    return 0;
  }

  /**
   * �ж�ָ���ĸ��Ƿ��и�
   * @param g
   * @return
   */
  public boolean isYouGen(int g) {
    return isHasRen(g) || isHasLu(g) || isHasMu(g) || isHasYuqi(g) || isHasChangS(g);
  }

  /**
   * ָ���ĸ��Ƿ�������
   * @return
   */
  public boolean isHasRen(int g) {
    boolean b = false;

    b = (SiZhu.YANGREN[g][SiZhu.nz] ||
         SiZhu.YANGREN[g][SiZhu.yz] ||
         SiZhu.YANGREN[g][SiZhu.rz] ||
         SiZhu.YANGREN[g][SiZhu.sz]);
    if(b) return b;

    if(g==YiJing.WUG) {
      b = (SiZhu.nz == YiJing.CHEN || SiZhu.nz == YiJing.XU ||
           SiZhu.yz == YiJing.CHEN || SiZhu.yz == YiJing.XU ||
           SiZhu.rz == YiJing.CHEN || SiZhu.rz == YiJing.XU ||
           SiZhu.sz == YiJing.CHEN || SiZhu.sz == YiJing.XU);
      if (b)  return b;
    }
    if(g==YiJing.JI) {
      b = (SiZhu.nz == YiJing.CHOU || SiZhu.nz == YiJing.WEI ||
           SiZhu.yz == YiJing.CHOU || SiZhu.yz == YiJing.WEI ||
           SiZhu.rz == YiJing.CHOU || SiZhu.rz == YiJing.WEI ||
           SiZhu.sz == YiJing.CHOU || SiZhu.sz == YiJing.WEI);
      if (b)  return b;
    }
    return false;
  }

  /**
   * ָ���ĸ��Ƿ���»
   * @return
   */
  public boolean isHasLu(int g) {
    boolean b = false;

    b =  (SiZhu.LUSHEN[g][SiZhu.nz] ||
          SiZhu.LUSHEN[g][SiZhu.yz] ||
          SiZhu.LUSHEN[g][SiZhu.rz] ||
          SiZhu.LUSHEN[g][SiZhu.sz]);
    if(b) return b;

    if(g==YiJing.JI) {
      b = (SiZhu.nz == YiJing.CHEN || SiZhu.nz == YiJing.XU ||
           SiZhu.yz == YiJing.CHEN || SiZhu.yz == YiJing.XU ||
           SiZhu.rz == YiJing.CHEN || SiZhu.rz == YiJing.XU ||
           SiZhu.sz == YiJing.CHEN || SiZhu.sz == YiJing.XU);
      if (b)  return b;
    }
    if(g==YiJing.WUG) {
      b = (SiZhu.nz == YiJing.CHOU || SiZhu.nz == YiJing.WEI ||
           SiZhu.yz == YiJing.CHOU || SiZhu.yz == YiJing.WEI ||
           SiZhu.rz == YiJing.CHOU || SiZhu.rz == YiJing.WEI ||
           SiZhu.sz == YiJing.CHOU || SiZhu.sz == YiJing.WEI);
      if (b)  return b;
    }
    return false;

  }

  /**
   * ָ���ĸ��Ƿ���Ĺ
   * @return
   */
  public boolean isHasMu(int g) {
    return (SiZhu.TGSWSJ[g][SiZhu.nz]==9 ||
       SiZhu.TGSWSJ[g][SiZhu.yz]==9  ||
       SiZhu.TGSWSJ[g][SiZhu.rz]==9  ||
       SiZhu.TGSWSJ[g][SiZhu.sz]==9 );
  }

  /**
   * ָ���ĸ��Ƿ�������
   * @return
   */
  public boolean isHasYuqi(int g) {
    if(YiJing.TIANGANWH[g]==YiJing.MU) {
      return getGzNum(YiJing.WEI,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.HUO) {
      return getGzNum(YiJing.XU,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.JIN) {
      return getGzNum(YiJing.CHOU,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.SHUI) {
      return getGzNum(YiJing.CHEN,2)>0;
    }

    return false;
  }

  /**
   * ָ���ĸ��Ƿ���ָ����֧��Ĺ
   * @return
   */
  public boolean isHasMu(int g, int z) {
    return (SiZhu.TGSWSJ[g][z]==9);
  }

  /**
   * ָ���ĸ��Ƿ���ָ����֧������
   * @return
   */
  public boolean isHasYuqi(int g, int z) {
    if(YiJing.TIANGANWH[g]==YiJing.MU) {
      return (z == YiJing.WEI);
    }else if(YiJing.TIANGANWH[g]==YiJing.HUO) {
      return (z == YiJing.XU);
    }else if(YiJing.TIANGANWH[g]==YiJing.JIN) {
      return (z == YiJing.CHOU);
    }else if(YiJing.TIANGANWH[g]==YiJing.SHUI) {
      return (z == YiJing.CHEN);
    }

    return false;
  }


  /**
   * ָ���ĸ��Ƿ��г���
   * @return
   */
  public boolean isHasChangS(int g) {
    if(YiJing.TIANGANWH[g]==YiJing.MU) {
      return getGzNum(YiJing.HAI,2)+getGzNum(YiJing.ZI,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.HUO) {
      return getGzNum(YiJing.YIN,2)+getGzNum(YiJing.MAO,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.JIN) {
      return getGzNum(YiJing.CHEN,2)+getGzNum(YiJing.XU,2) +
          getGzNum(YiJing.CHOU,2)+getGzNum(YiJing.WEI,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.SHUI) {
      return getGzNum(YiJing.SHEN,2)+getGzNum(YiJing.YOU,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.TU) {
      return getGzNum(YiJing.SI,2)+getGzNum(YiJing.WUZ,2)>0;
    }

    return false;
  }


  /**
   * ָ����֧�Ƿ���ָ���ĸɵ�»
   * @return
   */
  public boolean isLu(int g ,int z) {
    return (SiZhu.LUSHEN[g][z]);

  }

  /**
   * ָ����֧�Ƿ���ָ���ĸɵ���
   * @return
   */
  public boolean isRen(int g ,int z) {
    return (SiZhu.YANGREN[g][z]);

  }

  /**
   * �õ�ָ����֧��ɷ�Ƿ��ܳ� 0���� 10���� 11��֧�� 12 ң�� 99�ʶ�֧��
   * @param ss
   * @return
   */
  public int getShenShaIsChong(int ss) {
    int[] iret = new int[3];
    int j=0;
    int gz = getShenShaName(ss,2)[0];

    if(gz==SiZhu.nz) {
      if (YiJing.DZCHONG[SiZhu.yz][gz] > 0)  iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.rz][gz] > 0) iret[j++] = 11;
      else if (YiJing.DZCHONG[SiZhu.sz][gz] > 0) iret[j++] = 12;
    }
    if(gz==SiZhu.yz) {
      if (YiJing.DZCHONG[SiZhu.nz][gz] > 0)  iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.rz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.sz][gz] > 0) iret[j++] = 11;
    }
    if(gz==SiZhu.rz) {
      if (YiJing.DZCHONG[SiZhu.yz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.sz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.nz][gz] > 0)  iret[j++] = 11;
    }
    if(gz==SiZhu.sz) {
      if (YiJing.DZCHONG[SiZhu.rz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.yz][gz] > 0) iret[j++] = 11;
      else if (YiJing.DZCHONG[SiZhu.nz][gz] > 0)  iret[j++] = 12;
    }

    if(j==2)
      return 99;

    //�˴�ֻ�ڴ������ж��Ƿ���������ʱ֧��˴���ɷ
    if(j==0){
      if(YiJing.DZCHONG[SiZhu.nz][gz] > 0 ||
         YiJing.DZCHONG[SiZhu.nz][gz] > 0 ||
         YiJing.DZCHONG[SiZhu.nz][gz] > 0 ||
         YiJing.DZCHONG[SiZhu.nz][gz] > 0)
        return -1;
    }
    return iret[0];
  }


  /**
   * �õ�ָ���ĸ�֧��ɷ�Ƿ��ܿ� 0���� 10���� 11���ɿ� 12 ң�� //99�ʿ�
   * @param ss
   * @return
   */
  public int getShenShaIsKe(int ss, int gztype) {
    int iret = 0;
    int gz = getShenShaName(ss,gztype)[0];

    if(gztype==1) {
      if(gz==SiZhu.ng) {
        if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yg) {
        if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sg) {
        if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
    }else{
      if(gz==SiZhu.nz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.rz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
    }

    return iret;
  }

  /**
   * �õ�ָ���ĸ�֧��ɷ�Ƿ����� 0���� 10���� 11������ 12 ң��
   * @param ss
   * @return
   */
  public int getShenShaIsSheng(int ss, int gztype) {
    int iret = 0;
    int gz = getShenShaName(ss,gztype)[0];

    if(gztype==1) {
      if(gz==SiZhu.ng) {
        if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yg) {
        if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sg) {
        if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
    }else{
      if(gz==SiZhu.nz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.rz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
    }

    return iret;
  }

  /**
   * �õ�ָ���ĸ�֧��ɷ�Ƿ���� 0���� 10���� 11���ɺ� 12 ң�� 99�ʺ�
   * @param ss
   * @return
   */
  public int getShenShaIsHe(int ss, int gztype) {
    int[] iret = new int[3];
    int j = 0;
    int gz = getShenShaName(ss,gztype)[0];

    if(gztype==1) {
      if(gz==SiZhu.ng) {
        if (YiJing.TGHE[SiZhu.yg][gz] > 0)
          iret[j++] = 10;
        else if (YiJing.TGHE[SiZhu.sg][gz] > 0)
          iret[j++] = 12;
      }
      if (gz == SiZhu.yg) {
        if (YiJing.TGHE[SiZhu.ng][gz] > 0)
          iret[j++] = 10;
        else if (YiJing.TGHE[SiZhu.sg][gz] > 0)
          iret[j++] = 11;
      }
      if (gz == SiZhu.sg) {
        if (YiJing.TGHE[SiZhu.yg][gz] > 0)
          iret[j++] = 11;
        else if (YiJing.TGHE[SiZhu.ng][gz] > 0)
          iret[j++] = 12;
      }

      //�˴�ֻ�ڴ������ж��Ƿ���������ʱ֧��˴���ɷ
      if(j==0){
        if(YiJing.TGHE[SiZhu.ng][gz] > 0 ||
           YiJing.TGHE[SiZhu.yg][gz] > 0 ||
           YiJing.TGHE[SiZhu.rg][gz] > 0 ||
           YiJing.TGHE[SiZhu.sg][gz] > 0)
          return -1;
      }
    }else{
      if(gz==SiZhu.nz) {
        if (YiJing.DZLIUHE[SiZhu.yz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.rz][gz] > 0) iret[j++] = 11;
        else if (YiJing.DZLIUHE[SiZhu.sz][gz] > 0) iret[j++] = 12;
      }
      if(gz==SiZhu.yz) {
        if (YiJing.DZLIUHE[SiZhu.nz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.rz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.sz][gz] > 0) iret[j++] = 11;
      }
      if(gz==SiZhu.rz) {
        if (YiJing.DZLIUHE[SiZhu.yz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.sz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.nz][gz] > 0) iret[j++] = 11;
      }
      if(gz==SiZhu.sz) {
        if (YiJing.DZLIUHE[SiZhu.rz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.yz][gz] > 0) iret[j++] = 11;
        else if (YiJing.DZLIUHE[SiZhu.nz][gz] > 0) iret[j++] = 12;
      }

      //�˴�ֻ�ڴ������ж��Ƿ���������ʱ֧��˴���ɷ
      if(j==0){
        if(YiJing.DZLIUHE[SiZhu.nz][gz] > 0 ||
           YiJing.DZLIUHE[SiZhu.yz][gz] > 0 ||
           YiJing.DZLIUHE[SiZhu.rz][gz] > 0 ||
           YiJing.DZLIUHE[SiZhu.sz][gz] > 0)
          return -1;
      }
    }
    if(iret[1]>0)
      return 99;
    return iret[0];
  }

  /**
   * �ж������λ��
   * 0 ���� 1 ��֧ 2 ң��
   */
  public int getTwoShenLocation(int[] loc1, int[] loc2) {
    if(loc1[0]+loc2[0]==3 || loc1[0]+loc2[1]==3 || loc1[1]+loc2[0]==3 || loc1[1]+loc2[1]==3)
      return 0;
    else if(loc1[0]+loc2[0]==5 || loc1[0]+loc2[1]==5 || loc1[1]+loc2[0]==5 || loc1[1]+loc2[1]==5)
      return 2;
    else if(loc1[0]+loc2[0]==6 || loc1[0]+loc2[1]==6 || loc1[1]+loc2[0]==6 || loc1[1]+loc2[1]==6)
      return 1;
    return -1;
  }

  /**
   * �õ�ָ���ĸɻ�֧��λ��
   * @param gz
   * @param gztype
   * @return
   */
  public int[] getGzLocation(int gz, int gztype){
    int j=0;
    int[] loc = new int[4];
    int[] gzs = new int[4];

    if(gztype==1) {
      gzs = new int[]{SiZhu.ng, SiZhu.yg, 0, SiZhu.sg};
    }else {
      gzs = new int[]{SiZhu.nz, SiZhu.yz, SiZhu.rz, SiZhu.sz};
    }

    for(int i=0; i<gzs.length; i++) {
      if(gzs[i] == gz)
        loc[j++] = i+1;
    }
    return loc;
  }

  /**
   * ��ָ������ɷ��������ɻ��֧��λ�õõ�������,�ո�Ϊȫ�ֱ���
   * @param ss 0��,1�ȼ� 2�ٲ�3 ʳ�� 4�˹� 5ƫ�� 6���� 7ƫ�� 8���� 9ƫӡ 10��ӡ
   * @param gztype 1Ϊ�� 2Ϊ֧
   * @return
   */
  public int[] getShenShaName(int ss, int gztype) {
    int i = 0;
    for(i=1; i<=10; i++) {
      if(SiZhu.TGSHISHEN[SiZhu.rg][i] == ss)
        break;
    }

    if(gztype == 1) {
      return new int[]{i,0};
    }
    if(i==5)
      return new int[]{YiJing.CHEN,YiJing.XU};
    if(i==6)
      return new int[]{YiJing.CHOU,YiJing.WEI};
    if(i==1 || i==2 || i==7 || i==8)
      return new int[]{i+2,0};
    if(i==3 || i==4 || i==9)
      return new int[]{i+3,0};
    if(i==10)
      return new int[]{YiJing.ZI,0};
    return new int[]{SiZhu.rg,0};
  }

  /**
   * ��ָ������ɷ��������ɻ��֧��λ�õõ�������,�ո�Ϊȫ�ֱ���
   * @param ss 0�Ƚ� 1ʳ�� 2�� 3��ɱ 4ӡ
   * @param gztype 1Ϊ�� 2Ϊ֧
   * @return
   */
  public int[] getShenShaName2(int ss, int gztype) {
    int[] name = new int[4];
    if(gztype==1) {
      name[0] = getShenShaName(ss * 2 + 1, gztype)[0];
      name[1] = getShenShaName(ss * 2 + 2, gztype)[0];
    }else {
      name[0] = getShenShaName(ss * 2 + 1, gztype)[0];
      name[1] = getShenShaName(ss * 2 + 2, gztype)[0];
      name[2] = getShenShaName(ss * 2 + 1, gztype)[1];
      name[3] = getShenShaName(ss * 2 + 2, gztype)[1];
    }

    return name;
  }

  /**
   * ��ָ���ɻ�֧�����жϾ����Ƿ��д˸�֧
   * @param gz ��֧
   * @param gztype 1Ϊ�� 2Ϊ֧
   * @return ��Ϊû�д˸ɻ�֧
   */
  public boolean isNo(int gz, int gztype) {
    if(gztype==1){
      if(SiZhu.ng==gz || SiZhu.yg==gz || SiZhu.rg==gz || SiZhu.sg==gz)
        return false;
    }else{
      if(SiZhu.nz==gz || SiZhu.yz==gz || SiZhu.rz==gz || SiZhu.sz==gz)
        return false;
    }
    return true;
  }

  /**
   * �õ���ɵ�֧��ɷ����
   * @return
   */
  public String getGanTouZiCangDesc() {
    String s = "";

    int g0 = getShenShaNum(1, 0);
    int g1 = getShenShaNum(1, 1);
    int g2 = getShenShaNum(1, 2);
    int g3 = getShenShaNum(1, 3);
    int g4 = getShenShaNum(1, 4);

    int z0 = getShenShaNum(2, 0);
    int z1 = getShenShaNum(2, 1);
    int z2 = getShenShaNum(2, 2);
    int z3 = getShenShaNum(2, 3);
    int z4 = getShenShaNum(2, 4);

    if (g0 > 1)
      s += "�Ƚ�����";
    else if (g1 > 1)
      s += "ʳ�˵���";
    else if (g2 > 1)
      s += "�������";
    else if (g3 > 1)
      s += "��ɱ����";
    else if (g4 > 1)
      s += "ӡ�ɵ���";
    else if (g2 >= 1 && g3 >= 1 && g4 >= 1)
      s += "�ƹ�ӡ��ȫ";
    else if (g2 >= 1 && g3 >= 1)
      s += "�ƹٲ�͸";
    else if (g3 >= 1 && g4 >= 1)
      s += "��ӡ����";
    else if (g1 >= 1 && g3 >= 1)
      s += "ʳ�˹�ɱ����";
    else if (g1 >= 1 && g4 >= 1)
      s += "ӡ��ʳ�˲���";
    else if (g2 >= 1 && g4 >= 1)
      s += "��ӡ��͸";
    else if (g2 >= 1 && g1 >= 1)
      s += "����ʳ������";
    else
      s += "";

    return s;
  }

  /**
   * �õ�ʮ�����˥����
   * @param type 0Ϊ�Ƚ� 1Ϊʳ�� 2�Ʋ� 3��ɱ 4ӡ��
   * @return
   */
  public String getShiShenWSDesc(int type) {
    int x = getShiShenCent(type);

    for(int i=0; i<SiZhu.baifen.length; i++) {
      if(x<SiZhu.baifen[i] && i==0) {
        return SiZhu.judgeWSName[i];
      }
      else if(x>=SiZhu.baifen[i] && i==3) {
        return SiZhu.judgeWSName[i + 1];
      }
      else if(x>=SiZhu.baifen[i] && x<SiZhu.baifen[i+1]) {
        return SiZhu.judgeWSName[i + 1];
      }
    }
    return null;
  }

  /**
   * �õ�ʮ�����˥
   * @param type 0Ϊ�Ƚ� 1Ϊʳ�� 2�Ʋ� 3��ɱ 4ӡ��
   * @return 1��֮���� 2ƫ�� 3���� 4ǿ�� 5��֮����
   */
  public int getShiShenWS(int type) {
    int x = getShiShenCent(type);

    for(int i=0; i<SiZhu.baifen.length; i++) {
      if(x<SiZhu.baifen[i] && i==0) {
        return i+1;
      }
      else if(x>=SiZhu.baifen[i] && i==3) {
        return i+2;
      }
      else if(x>=SiZhu.baifen[i] && x<SiZhu.baifen[i+1]) {
        return i+2;
      }
    }
    return 0;
  }


  /**
   * �õ�ʮ�����˥����
   * @param type 0Ϊ�Ƚ� 1Ϊʳ�� 2�Ʋ� 3��ɱ 4ӡ��
   * @return
   */
  public int getShiShenCent(int type) {

    if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.MU) {
      if(type == 0) {
        return SiZhu.muCent;
      }else if(type == 1) {
        return SiZhu.huoCent;
      }else if(type ==2) {
        return SiZhu.tuCent;
      }else if(type == 3) {
        return SiZhu.jinCent;
      }else if(type == 4) {
        return SiZhu.shuiCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.HUO) {
      if(type == 0) {
        return SiZhu.huoCent;
      }else if(type == 1) {
        return SiZhu.tuCent;
      }else if(type ==2) {
        return SiZhu.jinCent;
      }else if(type == 3) {
        return SiZhu.shuiCent;
      }else if(type == 4) {
        return SiZhu.muCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.TU) {
      if(type == 0) {
        return SiZhu.tuCent;
      }else if(type == 1) {
        return SiZhu.jinCent;
      }else if(type ==2) {
        return SiZhu.shuiCent;
      }else if(type == 3) {
        return SiZhu.muCent;
      }else if(type == 4) {
        return SiZhu.huoCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.JIN) {
      if(type == 0) {
        return SiZhu.jinCent;
      }else if(type == 1) {
        return SiZhu.shuiCent;
      }else if(type ==2) {
        return SiZhu.muCent;
      }else if(type == 3) {
        return SiZhu.huoCent;
      }else if(type == 4) {
        return SiZhu.tuCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.SHUI) {
      if(type == 0) {
        return SiZhu.shuiCent;
      }else if(type == 1) {
        return SiZhu.muCent;
      }else if(type ==2) {
        return SiZhu.huoCent;
      }else if(type == 3) {
        return SiZhu.tuCent;
      }else if(type == 4) {
        return SiZhu.jinCent;
      }
    }
    return 0;
  }

  /**
   * �õ�ָ���ĸɻ�֧��ĳ����ɷ����
   * @param gz 1�� 2֧
   * @param type 0Ϊ�Ƚ� 1Ϊʳ�� 2�Ʋ� 3��ɱ 4ӡ��
   * @return
   */
  public int getShenShaNum(int gz, int type) {
    int c1 = 0;
    int[] wxs = {
        YiJing.TIANGANWH[SiZhu.ng], YiJing.TIANGANWH[SiZhu.yg],
        YiJing.TIANGANWH[SiZhu.sg]};
    int rgwx = YiJing.TIANGANWH[SiZhu.rg];
    if (gz == 2)
      wxs = new int[] {
          YiJing.DIZIWH[SiZhu.nz], YiJing.DIZIWH[SiZhu.yz],
          YiJing.DIZIWH[SiZhu.sz]};

    if (type == 0) {
      for (int i = 0; i < wxs.length; i++) {
        if (wxs[i] == rgwx)
          c1++;
      }
    }
    if (type == 1) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANSHENG[rgwx][wxs[i]] > 0)
          c1++;
      }
    }
    if (type == 2) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANKE[rgwx][wxs[i]] > 0)
          c1++;
      }
    }
    if (type == 3) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANKE[wxs[i]][rgwx] > 0)
          c1++;
      }
    }
    if (type == 4) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANSHENG[wxs[i]][rgwx] > 0)
          c1++;
      }
    }

    return c1;
  }

  /**
   * �õ�ָ����֧����
   * @param x
   * @return
   */
  public int getGzNum(int x, int type) {
    int j = 0;
    if (type == 1) {
      if (SiZhu.ng == x)
        j++;
      if (SiZhu.yg == x)
        j++;
      if (SiZhu.rg == x)
        j++;
      if (SiZhu.sg == x)
        j++;
    }
    else {
      if (SiZhu.nz == x)
        j++;
      if (SiZhu.yz == x)
        j++;
      if (SiZhu.rz == x)
        j++;
      if (SiZhu.sz == x)
        j++;
    }

    return j;
  }

  /**
   * �õ�ָ����֧����
   * @param x
   * @return
   */
  public int getGzNum(int[] x, int type) {
    int j = 0;
    for(int i=0; i<x.length; i++) {
      if (type == 1) {
        if (SiZhu.ng == x[i])
          j++;
        if (SiZhu.yg == x[i])
          j++;
        if (SiZhu.rg == x[i])
          j++;
        if (SiZhu.sg == x[i])
          j++;
      }
      else {
        if (SiZhu.nz == x[i])
          j++;
        if (SiZhu.yz == x[i])
          j++;
        if (SiZhu.rz == x[i])
          j++;
        if (SiZhu.sz == x[i])
          j++;
      }
    }

    return j;
  }


  /**
   * �õ�ָ���������м���
   */
  public int getHowWx(int wx) {
    int j = 0;
    if (YiJing.DIZIWH[SiZhu.nz] == wx)
      j++;
    if (YiJing.DIZIWH[SiZhu.yz] == wx)
      j++;
    if (YiJing.DIZIWH[SiZhu.rz] == wx)
      j++;
    if (YiJing.DIZIWH[SiZhu.sz] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.ng] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.yg] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.rg] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.sg] == wx)
      j++;

    return j;
  }

  /**
   * �õ�ָ���������м���
   * gz 1Ϊ��ɵĸ���������2Ϊ��֧�ĸ�������
   */
  public int getHowWx(int wx, int gz) {
    int j = 0;

    if(gz==2) {
      if (YiJing.DIZIWH[SiZhu.nz] == wx)
        j++;
      if (YiJing.DIZIWH[SiZhu.yz] == wx)
        j++;
      if (YiJing.DIZIWH[SiZhu.rz] == wx)
        j++;
      if (YiJing.DIZIWH[SiZhu.sz] == wx)
        j++;
    }else {
      if (YiJing.TIANGANWH[SiZhu.ng] == wx)
        j++;
      if (YiJing.TIANGANWH[SiZhu.yg] == wx)
        j++;
      if (YiJing.TIANGANWH[SiZhu.rg] == wx)
        j++;
      if (YiJing.TIANGANWH[SiZhu.sg] == wx)
        j++;
    }

    return j;
  }


  /**
   * �ж�xyz�����Ƿ���ָ���Ľ�
   */
  public boolean isTF(int x,int y,int z,int min,int max,int sum) {
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
   * ����֮�˼�
   */
  public String getWuXingYiJi(int type) {
    switch(type) {
      case YiJing.MU:
        return kg +"��ľ��ϲ�������ɴ���ľ�ģ�ľ�����Ҿߣ�װ�꣬ľ��Ʒ��ֽҵ����ֲ�������������磬"+sep+
            "������Ʒ�����ϣ�ֲ������ʳƷ�Ⱦ�Ӫ����ҵ��";
      case YiJing.HUO:
        return kg +"�˻�ϲ�Ϸ����ɴ��·Ź⣬��������ѧ�����ȣ���ȼ�����࣬�ƾ��࣬����ʳ��ʳƷ��������ױƷ������װ��Ʒ�����գ�"+sep+
            "��ѧ���ľߣ��Ļ�ѧ�������ˣ����ң�д����׫�ģ���Ա��У�������飬���棬��������ȷ���ľ�Ӫ����ҵ��";
      case YiJing.TU:
        return kg +"������ϲ����֮�أ����ء��ɴ����������ز���ũ�壬��������ƥ����װ����֯��ʯ�ϣ�ʯ�ң�ɽ�أ�ˮ�࣬����������������"+sep+
            "���£���ɡ�����̣���ˮ��Ʒ�����̣��Ŷ����м��ˣ���ʦ��������������ƣ����ʣ�ɥҵ����Ĺ��Ĺ�ع���ɮ��ȷ���ľ�Ӫ����ҵ��";
      case YiJing.JIN:
        return kg +"�˽�ϲ�������ɴ��¾��˲Ļ�������߲��ϣ���Ӳ�����ϣ��������������ܹܣ���������ͨ�����ڣ����̣����ӣ�"+
            "�������������ľ����е�ȷ���ľ�Ӫ�͹�����";
      case YiJing.SHUI:
        return kg +"��ˮ��ϲ�������ɴ��º��������²�ȼҺ�壬��ˮ�����࣬ˮ����ˮ������أ��䶳�����̣�ϴ�࣬ɨ������ˮ���ۿڣ�Ӿ�أ���������ԡ�أ�"+sep+
            "��ʳ��������Ʈ�Σ������������������ԣ��ױ仯����ˮ���ʣ��������ʣ�������ʣ�������ҵ��Ǩ�ã��ؼ����ݣ��˶������Σ����У���ߣ�"+sep+
            "ħ�������ߣ���̽�����磬������ߣ��������ߣ�ҽ��ҵ��ҩ�ﾭӪ��ҽ������ʿ��ռ���ȷ���ľ�Ӫ�͹�����";
    }
    return null;
  }

  /**
   * ʮ������
   * 0��,1�ȼ� 2�ٲ� 3ʳ�� 4�˹� 5ƫ�� 6���� 7ƫ�� 8���� 9ƫӡ 10��ӡ
   */
  public String getShiShenXing(int type) {
    switch(type) {
      case 1:
        return kg +"�ȼ����ԣ��Ƚ����㣬�¸�ð�գ�������ȡ���������ڹ�Ƨ��ȱ����Ⱥ����Ϊ�����Ѻϣ�";
      case 2:
        return kg +"�ٲ����ԣ��ȳ�ֱ̹������־�����ܶ���������������äĿ��ȱ�����ǣ���Ϊ����嶯��";
      case 3:
        return kg +"ʳ�����ԣ�������ͣ����˿����������������������α��ȱ���Ƿǣ���Ϊ�ظ�ų����";
      case 4:
        return kg +"�˹����ԣ�������Ծ���Ż����磬��ǿ��ʤ�������������ԣ�ȱ��Լ������Ϊ�����ѱ��";
      case 5:
        return kg +"ƫ�����ԣ��������飬�������飬�ֹۿ��ʣ����������鸡��ȱ�����ƣ���Ϊ����������";
      case 6:
        return kg +"�������ԣ����ͽڼ�̤ʵ���أ�������Թ���������ڹ��ң�ȱ����ȡ����Ϊų�����ܣ�";
      case 7:
        return kg +"ƫ�����ԣ���ˬ���壬������ȡ�����ϻ�������������ƫ��������Ե�����Ϊ���伫�ˣ�";
      case 8:
        return kg +"�������ԣ���ֱ���𣬶�ׯ���࣬ѭ�浸�أ��������ڿ̰壬ī�سɹ棬��Ϊ��־���᣻";
      case 9:
        return kg +"ƫӡ���ԣ�������������Ӧ��������Ŷ��գ��������ڹ¶���ȱ�����飬��Ϊ��˽��Į��";
      case 10:
        return kg +"��ӡ���ԣ���ӱ�ʴȣ���������������˳�ܣ���������ӹµ��ȱ����ȡ����Ϊ�ٶ�������"+sep;
    }
    return null;
  }

  /**
   * ��������
   */
  public String[] getWuXingXing(int type) {
    switch(type) {
      case YiJing.MU:
        return new String[]{kg +"ľ���ʣ�����ֱ������ͣ���ζ�ᣬ��ɫ�ࡣ",
            "����ľʢ�����˳��÷��������������޳�������ϸ�壬�ڼⷢ������ɫ��ס�Ϊ���в�������֮�ģ��������֮�⣬��߿�����������α��",
            "����ľ��̫�������˸����ݳ���ͷ��ϡ�٣��Ը�ƫ�������ʲ��ʡ�",
            "����ľ������������ü�۲��������ᣬ������Ϊ�˱������ġ�"};
      case YiJing.HUO:
        return new String[]{kg +"���������Լ������鹧����ζ�࣬��ɫ�ࡣ",
            "������ʢ������ͷС�ų����ϼ�������ŨüС����������˸��Ϊ��ǫ�͹��������Ӽ��ꡣ",
            "��������̫������������ݼ��㣬������������թ�ʶ���������ʼ���ա�",
            "������˥����������ݼ��㣬������������թ�ʶ���������ʼ���ա�"};
      case YiJing.TU:
        return new String[]{kg +"�����ţ������أ��������ζ�ʣ���ɫ�ơ�",
             "������ʢ������Բ�����ǣ�ü��ľ�㣬�ڲ����ء�Ϊ����Т���ϣ���������Ա��У��бع���",
             "��������̫��������ͷ�Խ�������׾����������þ���",
             "��������������������ɫ���ͣ����ǵͣ�Ϊ�˺ݶ����壬�������ã���ͨ����"};
      case YiJing.JIN:
        return new String[]{kg +"�����壬���Ըգ������ң���ζ������ɫ�ס�",
            "������ʢ�����˹�����ƣ��淽�׾���ü������彡���塣Ϊ�˸�����ϣ�������壬��֪���ܡ�",
            "��������̫��������������ı��̰�����ʡ�",
            "�����������������������С��Ϊ�˿̱��ڶ���ϲ����ɱ������̰����"};
      case YiJing.SHUI:
        return new String[]{kg +"ˮ���ǣ����Դϣ������ƣ���ζ�̣���ɫ�ڡ�",
            "����ˮ������������вɣ�������ͣ�Ϊ����˼���ǣ����Ƕ�ı��ѧʶ���ˡ�",
            "����ˮ��̫�������˺�˵�Ƿǣ�Ʈ��̰����",
            "����ˮ���������������С�������޳�����С���ԣ����·�����"};
    }
    return null;

    }

  /**
   * ��������
   */
  public String getMGXinXing(int type) {
    switch (type) {
      case YiJing.ZI:
        return kg +"�ӹ�������ǣ�־����������ԣ�弪��" + sep +
            "�ӹ�֮�˼���������������������򸾳��棬������ʵ����ʱ�������죬����δ���ǿ���������Ƨ��Ȼ����־��ǿ��������Թ���п�������" + sep +
            "������˲��ѣ������棬����Ӱ�����࣬��ѪҺѭ��֮������˼��룬���粻����֢״��" ;
      case YiJing.CHOU:
        return kg +"�󹬣�����ǣ����ѵü����������ģ����꼪��" + sep +
            "��֮�ˣ�־������ð�������ѵ̬֮�ȶ��ˣ����������ˣ���һʱ֮���������Է�â��¶������Թ�̣������谭����ҵ֮��չ��" + sep +
            "������˲��ѣ������棬�򻼷�ʪ��ϥ��������������Ų�����Ƥ��ʪ����֢��" ;
      case YiJing.YIN:
        return kg +"��������Ȩ�ǣ�����������������Ȩ����" + sep +
            "����֮�ˣ����Ը��㣬���������ϲ�ٽݾ�������������ã�̸�¾������������֮ӡ�󣬽������㣬��Ե���ѣ���ı������Ҳ���޷����ѡ�" + sep +
            "�����˲��ѣ������棬����Ӱ����֫���β����簩��Ӹ�ң�̵ˮ��ۣ���ʪ��Ӹ����ѪҺ�����֢��";
      case YiJing.MAO:
        return kg +"î���������ǣ�������ƣ���Ȩʱ��ǫ��Ϊ�ϡ�" + sep +
            "î��֮�ˣ���ʱ������׳��ǿ�������׶�ŭ�����˾������ۡ��Գ�����Ը��Ͻ����۲�����ǿ���������������͸�ӣ��ƶ������" + sep +
            "������˲��ѣ������棬����Ӱ��������������ʯ���̴����Ѹأ�Ѫ����֢��" ;
      case YiJing.CHEN:
        return kg +"�����������ǣ��¶෴������ı���ܡ�" + sep +
            "����֮�ˣ��Ը��ºͣ������������Ϊ���Ž����ˣ����������ǷǾ�����������ͣ�ȱ�����ϣ���ʵ����ʹȻ��" + sep +
            "������˲��ѣ������棬����Ӱ��������ˮ�𲻼ã���������������Ƣ��������֢��" ;
      case YiJing.SI:
        return kg +"�ȹ��������ǣ������񷢣�Ů���к÷�" + sep +
            "�ȹ�֮�ˣ�̬�ȳ�����ϲ��ë��ã��˶����ӽ�������¼ţ�������ϸ�ڷ����ֶξ��������羭�����֣����ܻ��ٳɴ�ǰ;������" + sep +
            "ƽʱ����˼���ǣ�����մ��΢�Բ���������˲��ѣ���Ӱ�츹��������θ�����أ�йк��������֢��" ;
      case YiJing.WUZ:
        return kg +"�繬���츣�ǣ��ٻ�������" + sep +
            "�繬֮�ˣ������߹���Ұ�ġ���ǿ����־���������裬����ʱ������ʢ�����˺Ͱ��޷������ֶΡ������̬�����гɹ�֮һ�ա�" + sep +
            "������˲��ѣ������棬����Ӱ������������������ʹ����ʪ�������֢��";
      case YiJing.WEI:
        return kg +"δ���������ǣ�һ����µ������ʼ����" + sep +
            "δ��֮�ˣ����Ը������ж��׷�ŭ;òΪ��˳�������򼫹�ִ�������Ͻ����кܺõ���������۲�������������Ծ�����׵�����ɫ֮�á�" + sep +
            "������˲��ѣ������棬���ܻ�θ�������������������ȼ���" ;
      case YiJing.SHEN:
        return kg +"�깬������ǣ�������飬Ů������" + sep +
            "�깬֮�ˣ�����˫���Ը���ʱ�����ֹ���ʱ���ǡ��ļ�����ÿ�������ӱ�����������̬����¶�ȼѡ�������ҵ��Ӧ��ʼ���գ����ɰ�;���ϡ�" + sep +
            "������˲��ѣ������棬�˷������������������ԣ���������������������֮������" ;
      case YiJing.YOU:
        return kg +"�Ϲ��������ǣ������ֱ��ʱ���Ƿǡ�" + sep +
            "�Ϲ�֮�ˣ���Ĭ������˼����Զ���ĵ���������ʵ�ɿ��������������������ǿ����һں�ѣ�����Ϊ������á�һ��������Ȼ���ã�ʧ��ʱ������ʯ���������ҽ�Ʃ���ӵ�����" + sep +
            "�����棬�������˲��ѣ�����Ӱ��������������ף����಻����֢��" ;
      case YiJing.XU:
        return kg +"�繬�������ǣ�����ƽ�ͣ��յ�������" + sep +
            "�繬֮�ˣ���������һ���ƻ���ȫ���Ը�������������ǡ���ȱ���������������Ӧ���侲����˼��ΪҪ��" + sep +
            "������˲��ѣ������棬����Ӱ��ͷ������ѣ�Σ��з磬˼����ң�������ʹ�ȼ�����" ;
      case YiJing.HAI:
        return kg +"�����������ǣ��Ĵ����򣬿˼����ˡ�" + sep +
            "����֮�ˣ���м�ΪŨ��������������������ͨ�ﴦ�¹���������ƽ���Ӹ��˲Ʋ���Ը������ͬ��������֮��������������" + sep +
            "������˲��ѣ������棬����Ӱ������ȫ���������ð����ʹ��֢������ѪҺ�ж������״����Լ��ؽ��ף�֫����Ե�֢��" + sep +
            "�������л��ǺͿ����������й³����ޣ����ʺ�ѧ��֮�о����ɾ�Ҳ��Ϊ���Ž���£���ҵ��΢����Ӧ������������" + sep +
            "�˸����¶���������ӣ������������飬���ܽ���ɳ�����ϡ�" + sep +
            "���������У�������������������ң����ڳ嶯��Ӧע������������֧���ˣ��Է������ֻ���" + sep +
            "������崿���ӣ���ɱ��ӡ�����Ҽ��У����ԲŸɣ������������Σ������ķ������۴�������ҵ�������ش�ְ֮�����ܾ������ء�" + sep +
            "������������Զ�����緢չ;��֧�����ǣ������ƿ��٣���ҵ�гɣ�ÿ�ɾۻ��ƶ�������֧����ɱ�����ܳ�Ϊ�⽻�ٻ������ս�Ľ��졣";
    }
    return null;
  }





}
