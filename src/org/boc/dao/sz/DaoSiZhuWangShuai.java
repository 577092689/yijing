package org.boc.dao.sz;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuWangShuai {
  private DaoCalendar daoC;
  private DaoYiJingMain daoY;
  private DaoSiZhuPublic pub;
  //public int jinxs = 0;
  //public int muxs = 0;
  //public int shuixs = 0;
  //public int huoxs = 0;
  //public int tuxs = 0;
  public String desc = "";
  public String _desc = "";
  private double jin, mu, shui, huo, tu;
  double _jin=0, _mu=0, _shui=0, _huo=0, _tu=0;
  private String _tttt = "";
  private String sep="��\r\n    ";

  public DaoSiZhuWangShuai() {
    daoC = new DaoCalendar();
    daoY = new DaoYiJingMain();
    pub = new DaoSiZhuPublic();
    jin = 0; mu = 0; shui=0; huo=0; tu=0;
  }

/**
 * ȡ��
 * @return
 */
public String getGeJu() {
  String s = "";

  //1. ����
  if(getBaGe() > 0) s += _tttt;

  //2. �������ϸ�
  if(getWuGe() > 0)  s += _tttt;

  //3. �Ӹ�
  if(getCongGe() > 0 ) s += _tttt;

  //4. ������
  if(getHuaQiGe() > 0 ) s += _tttt;

  //5. ��»��
  if(isJianLu()) s += _tttt;

    //6. ���и�
  if(getYueRen(YiJing.JIA, YiJing.MAO) || getYueRen(YiJing.GENG, YiJing.YOU) ||
     getYueRen(YiJing.REN, YiJing.ZI))
    s += _tttt;
  //6.1 �������и�,���޸��ȡ�������
    if(getYueRenOtherGe(YiJing.YI,YiJing.YIN) || getYueRenOtherGe(YiJing.WUG,YiJing.WUZ) ||
       getYueRenOtherGe(YiJing.BING,YiJing.WUZ) || getYueRenOtherGe(YiJing.DING,YiJing.SI) ||
       getYueRenOtherGe(YiJing.JI,YiJing.SI) || getYueRenOtherGe(YiJing.XIN,YiJing.SHEN) ||
       getYueRenOtherGe(YiJing.GUI,YiJing.HAI))
      s += "�������У��������ױ�֮��˴�֧�����عʲ������и��ۣ���Ϊ"+_tttt+sep;


  //7. �Ӹ�
    if(_getOneOrTwo()!=null) s += _tttt;
    if(_getDaoCh()!=null) s += _tttt;
    if(_getChaoYang()!=null) s += _tttt;
    if(_getHeLu()!=null) s += _tttt;
    if(_getJingLanCa()!=null) s += _tttt;
    if(_getXingHeGe()!=null) s += _tttt;
    if(_getYaoHe()!=null) s += _tttt;
    if(_getZaGe()) s += _tttt;
    if(_getFeiTianLuMa()!=null) s += _tttt;

  return s;
}

public boolean isJianLu() {
  if(_getJianLu(YiJing.JIA, YiJing.YIN) || _getJianLu(YiJing.YI, YiJing.MAO) ||
       _getJianLu(YiJing.BING, YiJing.SI) || _getJianLu(YiJing.WUG, YiJing.SI) ||
       _getJianLu(YiJing.DING, YiJing.WUZ) || _getJianLu(YiJing.JI, YiJing.WUZ) ||
       _getJianLu(YiJing.GENG, YiJing.SHEN) || _getJianLu(YiJing.XIN, YiJing.YOU) ||
       _getJianLu(YiJing.REN, YiJing.HAI) || _getJianLu(YiJing.GUI, YiJing.ZI))
      return true;
    return false;
}

/**
   * ֻ�����ո�Ϊ�У����ոɽ����½���
   * �˷�����Ϊȡ��Ϊ��������
   * @return
   */
  public boolean isYueRen() {
    if((SiZhu.rg==YiJing.JIA && SiZhu.yz==YiJing.MAO) ||
       (SiZhu.rg==YiJing.BING && SiZhu.yz==YiJing.WUZ) ||
       (SiZhu.rg==YiJing.WUG && SiZhu.yz==YiJing.WUZ) ||
       (SiZhu.rg==YiJing.GENG && SiZhu.yz==YiJing.YOU) ||
       (SiZhu.rg==YiJing.REN && SiZhu.yz==YiJing.ZI))
      return true;
    return false;
  }

  /**
   * ���ո�����֧ͬ���� ���ոɵĽ�Ϊ�� ���ոɵı�Ϊ»
   * �˷�����Ϊȡ��Ϊ��������
   * @return
   */
  public boolean isYueJie() {
    if((SiZhu.rg%2==0) && YiJing.DIZIWH[SiZhu.yz]==YiJing.TIANGANWH[SiZhu.rg])
      return true;
    return false;
  }


public int getHuaQiGe() {
  if (_getHuaQiGe(YiJing.JIA, YiJing.JI,
                  new int[] {YiJing.CHEN, YiJing.XU, YiJing.CHOU, YiJing.WEI}
                  ,
                  new int[] {YiJing.JIA, YiJing.YI, YiJing.YIN, YiJing.MAO}
                  ,
                  "����ľ��Ϊ������" + sep))
    return YiJing.TU;
  if (_getHuaQiGe(YiJing.YI, YiJing.GENG,
                  new int[] {YiJing.SI, YiJing.YOU, YiJing.CHOU, YiJing.SHEN}
                  ,
                  new int[] {YiJing.BING, YiJing.DING, YiJing.WUZ, YiJing.WUZ}
                  ,
                  "������Ϊ�����" + sep))
    return YiJing.JIN;
  if (_getHuaQiGe(YiJing.BING, YiJing.XIN,
                  new int[] {YiJing.SHEN, YiJing.ZI, YiJing.CHEN, YiJing.HAI}
                  ,
                  new int[] {YiJing.WUG, YiJing.JI, YiJing.WEI, YiJing.XU}
                  ,
                  "��������Ϊ��ˮ��" + sep))
    return YiJing.SHUI;
  if (_getHuaQiGe(YiJing.DING, YiJing.REN,
                  new int[] {YiJing.HAI, YiJing.MAO, YiJing.WEI, YiJing.YIN}
                  ,
                  new int[] {YiJing.GENG, YiJing.XIN, YiJing.SHEN, YiJing.YOU}
                  ,
                  "������Ϊ��ľ��" + sep))
    return YiJing.MU;
  if (_getHuaQiGe(YiJing.WUG, YiJing.GUI,
                  new int[] {YiJing.YIN, YiJing.WUZ, YiJing.XU, YiJing.SI}
                  ,
                  new int[] {YiJing.REN, YiJing.GUI, YiJing.HAI, YiJing.ZI}
                  ,
                  "����ˮ��Ϊ�����" + sep))
    return YiJing.SHUI;
  return 0;
}

public int getWuGe() {
  _tttt = null;
  String _temp = "";
  int gejuNum = getFangOrHui();

  if(SiZhu.rg==1 || SiZhu.rg==2) {
    if(SiZhu.yz==3 || SiZhu.yz==4 || SiZhu.yz==5) {
      if(gejuNum==10 || gejuNum==11 ||gejuNum==50 ||gejuNum==51 )
        if(pub.isNo(YiJing.GENG,1) && pub.isNo(YiJing.XIN,1) &&
           pub.isNo(YiJing.SHEN,2) && pub.isNo(YiJing.YOU,2)) {
          if(gejuNum==1) _temp="��֧������î������ľ��";
          if(gejuNum==5) _temp="��֧���Ϻ�îδ����ľ��";
          if(_temp.length()>6) {
            _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ���춴��£�" + _temp +
                "�����޸������ϣ�Ϊ��ֱ���ٸ�" + sep;
            return YiJing.MU;
          }
        }
    }
  }
  if(SiZhu.rg==3 || SiZhu.rg==4) {
    if(SiZhu.yz==6 || SiZhu.yz==7 || SiZhu.yz==8) {
      if(gejuNum==21 || gejuNum==20 ||gejuNum==60 ||gejuNum==61)
        if(pub.isNo(YiJing.REN,1) && pub.isNo(YiJing.GUI,1) &&
           pub.isNo(YiJing.HAI,2) && pub.isNo(YiJing.ZI,2)) {
          if(gejuNum==2) _temp="��֧��������δ�Ϸ����";
          if(gejuNum==6) _temp="��֧�����������Ϸ����";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ��������£�" + _temp +
                  "�������ɹﺥ�ӣ�Ϊ���ϸ�" + sep;
              return YiJing.HUO;
            }
        }
    }
  }
  if(SiZhu.rg==5 || SiZhu.rg==6) {
    if(SiZhu.yz==5 || SiZhu.yz==8 || SiZhu.yz==11 || SiZhu.yz==2) {
      //if(gejuNum==1 || gejuNum==5)
        if(pub.isNo(YiJing.JIA,1) && pub.isNo(YiJing.YI,1) &&
           pub.isNo(YiJing.YIN,2) && pub.isNo(YiJing.MAO,2)) {
          if(SiZhu.nz+SiZhu.yz+SiZhu.rz+SiZhu.sz==2+5+8+11 &&
             SiZhu.nz*SiZhu.yz*SiZhu.rz*SiZhu.sz==2*5*8*11 &&
             pub.getMin(SiZhu.nz,SiZhu.yz,SiZhu.rz,SiZhu.sz)==2 &&
             pub.getMax(SiZhu.nz,SiZhu.yz,SiZhu.rz,SiZhu.sz)==11) _temp="��֧ȫ�����δ";
          if((SiZhu.nz==2 || SiZhu.nz==5 || SiZhu.nz==8 || SiZhu.nz==11) &&
             (SiZhu.yz==2 || SiZhu.yz==5 || SiZhu.yz==8 || SiZhu.yz==11) &&
             (SiZhu.rz==2 || SiZhu.rz==5 || SiZhu.rz==8 || SiZhu.rz==11) &&
             (SiZhu.sz==2 || SiZhu.sz==5 || SiZhu.sz==8 || SiZhu.sz==11)) _temp="��֧����";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ�����ļ��£�" + _temp +
                  "�����޼�����î��Ϊ����" + sep;
              return YiJing.TU;
            }
        }
    }
  }
  if(SiZhu.rg==7 || SiZhu.rg==8) {
    if(SiZhu.yz==9 || SiZhu.yz==10 || SiZhu.yz==11) {
      if(gejuNum==31 || gejuNum==30 ||gejuNum==71 ||gejuNum==70)
        if(pub.isNo(YiJing.BING,1) && pub.isNo(YiJing.DING,1) &&
           pub.isNo(YiJing.SI,2) && pub.isNo(YiJing.WUZ,2)) {
          if(gejuNum==3) _temp="��֧�����������������";
          if(gejuNum==7) _temp="��֧�������ϳ��������";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ�������£�" + _temp +
                  "�����ޱ������磬Ϊ�Ӹ��" + sep;
              return YiJing.JIN;
            }
        }
    }
  }
  if(SiZhu.rg==9 || SiZhu.rg==10) {
    if(SiZhu.yz==12 || SiZhu.yz==1 || SiZhu.yz==2) {
      if(gejuNum==41 ||gejuNum==40 ||gejuNum==81 ||gejuNum==80)
        if(pub.isNo(YiJing.WUG,1) && pub.isNo(YiJing.JI,1) &&
           pub.isNo(YiJing.WEI,2) && pub.isNo(YiJing.XU,2)) {
          if(gejuNum==4) _temp="��֧���ấ�ӳ󱱷�ˮ��";
          if(gejuNum==8) _temp="��֧�������ӳ�����ˮ��";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ���춶��£�" + _temp +
                  "�������켺δ�磬Ϊ���¸�" + sep;
              return YiJing.SHUI;
            }
        }
    }
  }

  return 0;
}

private boolean _getZaGe() {
  _tttt = "";
  if(SiZhu.TGSWSJ[SiZhu.rg][SiZhu.sz]==4 && pub.getShiShenCent(3)==0)
    _tttt += "��»��ʱû���ǣ��š����Ƶ�·��������ν��»��\r\n";
  if(SiZhu.rg==YiJing.REN && (SiZhu.sz==YiJing.CHEN || SiZhu.rz==YiJing.CHEN))
    _tttt += "��ˮ���곽λ���ǡ�����������֮�磬����ν����������ϲ�����ֶ࣬��������฻�����������ƣ���ɹٶ�"+sep;
  if(SiZhu.rg==YiJing.YI && SiZhu.sz==YiJing.ZI)
    _tttt += "��ľ������ʱ��Ϊ���������֮�أ���Ϊ�۹�Ϊ�棬����ν�������������ں�îδ�¼����£���ϲ����ʱ������壬���˼��������������ϳ�"+sep;

  String _gong = "����ʱͬ�ɣ�»������ͨ����������»���ش�ã�ӡ緡��˹١�ʳ�񡢲����༪������ʵ���̳��ƺ������С���ɱ��������������ʱ���򹰲�ס"+sep;
  if((SiZhu.rg==YiJing.GUI) && SiZhu.rz==YiJing.HAI &&
     SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.CHOU )
    _tttt += "�ﺥ�չ��ʱ������»������ν��»��" + _gong;
  if(SiZhu.rg==YiJing.GUI && SiZhu.rz==YiJing.CHOU &&
     SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.HAI )
    _tttt += "����չﺥʱ������»������ν��»��" + _gong;
  if(SiZhu.rg==YiJing.DING && SiZhu.rz==YiJing.SI &&
     SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.WEI )
    _tttt += "�����ն�δʱ������»������ν��»��" + _gong;
  if(SiZhu.rg==YiJing.JI && SiZhu.rz==YiJing.WEI &&
     SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.SI )
    _tttt += "��δ�ռ���ʱ������»������ν��»��" + _gong;
  if(SiZhu.rg==YiJing.WUG && SiZhu.rz==YiJing.CHEN &&
     SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.WUZ )
    _tttt += "�쳽������ʱ������»������ν��»��" + _gong;

  if(SiZhu.rg==YiJing.JIA && SiZhu.rz==YiJing.SHEN &&
     SiZhu.sg==YiJing.JIA && SiZhu.sz==YiJing.XU )
    _tttt += "�����ռ���ʱ���Ϲ󣬼����٣��츣���ˣ�������ν�����" + _gong;
  if(SiZhu.rg==YiJing.YI && SiZhu.rz==YiJing.WEI &&
     SiZhu.sg==YiJing.YI && SiZhu.sz==YiJing.YOU )
    _tttt += "��δ������ʱ����󣬼����٣��츣���ˣ�������ν�����" + _gong;
  if(SiZhu.rg==YiJing.JIA && SiZhu.rz==YiJing.YIN &&
     SiZhu.sg==YiJing.JIA && SiZhu.sz==YiJing.ZI)
    _tttt += "�����ռ���ʱ����󣬼������ù��ˣ�����ҹ�������ҹ��ˣ�������󣩣�����ν�����" + _gong;
  if(SiZhu.rg==YiJing.WUG && SiZhu.rz==YiJing.SHEN &&
     SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU )
    _tttt += "����������ʱ��δ�󣬼������ù��ˣ�����ҹ�������ҹ��ˣ�������󣩣�����ν�����" + _gong;
  if(SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.CHOU &&
     SiZhu.sg==YiJing.XIN && SiZhu.sz==YiJing.MAO )
    _tttt += "��������îʱ�����󣬼������ù��ˣ�����ҹ�������ҹ��ˣ�������󣩣�����ν�����" + _gong;
  if(SiZhu.rg==YiJing.REN && SiZhu.rz==YiJing.CHEN &&
     SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.YIN )
    _tttt += "�ɳ�������ʱ��î�󣬼������ù��ˣ�����ҹ�������ҹ��ˣ�������󣩣�����ν�����" + _gong;

  if(SiZhu.rg==YiJing.JIA && SiZhu.sg==YiJing.YI && SiZhu.sz==YiJing.HAI) {
    _tttt += "�����Һ�ʱ������Ǭ�ԣ�����ν������Ǭ�񣻺�Ϊ���š���΢ԫ����Ϊʮ��֮�ף����Ҳ����Ϊ��ľ����֮�����Ұ�����Ϊ��»�����ֳ�Ϊ����»�񡱡�������ϲ�����ǣ��ּ�����ʵ���ȳ��̣�����ͬ" +sep;
  }

  if(SiZhu.rg==YiJing.REN && SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.YIN) {
    _tttt += "��������ʱ����������Ϊ���ţ��ϳ���Ϊ��»������ν�������޸��ּ�����»�񡱡��ɺ�����ʵ���꺥����ƶ�����̳���ơ������ա��ɳ���Ϊ���������ֶ��ߴ󸻣������м�ľ��������֮�ƣ��������٣��������ɹ�ɱ��������������˼ס������" +sep;
  }

  if(pub.getGzNum(pub.getShenShaName(7,1),1)==1 &&
     pub.getGzLocation(pub.getShenShaName(7,1)[0],1)[0]==4)
    _tttt += "ʱ����ɱһλ����������νʱ��һλ�����������ƫ�����ƣ����" + sep;

  if(SiZhu.rg==YiJing.WUG &&
     (SiZhu.rz==YiJing.SHEN || SiZhu.rz==YiJing.ZI || SiZhu.rz==YiJing.CHEN )) {
   _tttt += "�����������ӳ��գ�����ν���µ�λ�񣻼��̡��塢ɱ��"+sep;
  }
  if(SiZhu.rg==YiJing.WUG &&
     getWuGe()==YiJing.SHUI && pub.getGzNum(YiJing.SHEN,2)>0 && pub.getGzNum(YiJing.CHEN,2)>0) {
   _tttt += "����������֧���ӳ����ϣ�����ν���µ�λ�񣻼��̡��塢ɱ��"+sep;
  }
  if(SiZhu.rg==YiJing.JI &&
     (SiZhu.rz==YiJing.HAI || SiZhu.rz==YiJing.MAO || SiZhu.rz==YiJing.WEI )) {
   _tttt += "���������ں�îδ�գ�����ν���µ�λ�񣻼��̡��塢ɱ��"+sep;
  }
  if(SiZhu.rg==YiJing.JI &&
     getWuGe()==YiJing.MU && pub.getGzNum(YiJing.HAI,2)>0 && pub.getGzNum(YiJing.WEI,2)>0) {
   _tttt += "����������֧��îδ���ϣ�����ν���µ�λ�񣻼��̡��塢ɱ��"+sep;
  }

  if(SiZhu.rg==YiJing.REN &&
     (SiZhu.rz==YiJing.YIN || SiZhu.rz==YiJing.WUZ || SiZhu.rz==YiJing.XU )) {
   _tttt += "�����������������գ�����ν���䵱Ȩ�񣻼�ˮ���̳�"+sep;
  }
  if(SiZhu.rg==YiJing.REN &&
     getWuGe()==YiJing.HUO) {
   _tttt += "����������֧�ϻ��֣�����ν���µ�λ�񣻼�ˮ���̳�"+sep;
  }
  if(SiZhu.rg==YiJing.GUI &&
     (SiZhu.rz==YiJing.SI || SiZhu.rz==YiJing.YOU || SiZhu.rz==YiJing.CHOU )) {
   _tttt += "���������ڳ������գ�����ν���䵱Ȩ�񣻼�ˮ���̳�"+sep;
  }
  if(SiZhu.rg==YiJing.GUI &&
     getWuGe()==YiJing.JIN) {
   _tttt += "����������֧�ϻ��֣�����ν���䵱Ȩ�񣻼�ˮ���̳�"+sep;
  }

  if(((SiZhu.rg==YiJing.BING && SiZhu.rz==YiJing.ZI) ||
     (SiZhu.rg==YiJing.REN && SiZhu.rz==YiJing.ZI) ||
     (SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.MAO) ||
     (SiZhu.rg==YiJing.DING && SiZhu.rz==YiJing.YOU)) &&
      pub.getGzNum(YiJing.YI,1)>0) {
   _tttt += YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+"�����ҳ��ɣ�����ν�յ������񣻼��̳��"+sep;
  }
  if(pub.getGzNum(YiJing.YI,1)>=3 && getFangOrHui()==YiJing.JIN &&
     !pub.isNo(YiJing.SI,2) && !pub.isNo(YiJing.CHOU,2)) {
   _tttt += "������һ����ϳ�ȫ�ߣ�����ν�յ������񣻼��̳��"+sep;
  }

  if(SiZhu.rg==YiJing.JI &&
    (SiZhu.rz==YiJing.SI || SiZhu.rz==YiJing.YOU || SiZhu.rz==YiJing.CHOU) &&
     getFangOrHui()==YiJing.JIN) {
   _tttt += "���ո��������ϳ��գ��ϻ��֣�����ν���¸񣻼��̳弰����"+sep;
  }

  if(SiZhu.rg==YiJing.JIA && SiZhu.ng==YiJing.GENG) {
   _tttt += "���ռ����꣬����ν��·�ɱ��ɱ������������Ҫְ"+sep;
  }

  if(SiZhu.rg==YiJing.JIA && (SiZhu.ng==YiJing.WUG || SiZhu.ng==YiJing.JI)) {
   _tttt += "���ռ��켺�꣬����ν��·��Ƹ񣻵����ϲƲ�"+sep;
  }

  if(SiZhu.ng==SiZhu.yg && SiZhu.rg==SiZhu.sg && SiZhu.rg!=SiZhu.ng) {
   _tttt += "���¡���ʱ��ռ���ɣ�ͳһ�����ӣ�����ν���ɲ��Ӹ�����"+sep;
  }

  String nayin = SiZhu.NAYIN[SiZhu.ng][SiZhu.nz]+
      SiZhu.NAYIN[SiZhu.yg][SiZhu.yz]+
      SiZhu.NAYIN[SiZhu.rg][SiZhu.rz]+
      SiZhu.NAYIN[SiZhu.sg][SiZhu.sz]+
      SiZhu.NAYIN[SiZhu.tyg][SiZhu.tyz]+
      SiZhu.NAYIN[SiZhu.mgg][SiZhu.mgz];
  if(nayin.indexOf("��")!=-1 && nayin.indexOf("ľ")!=-1 &&
     nayin.indexOf("ˮ")!=-1 && nayin.indexOf("��")!=-1 &&
     nayin.indexOf("��")!=-1) {
   _tttt += "��������ʱ̥֮�����պ����ж������룬����ν���о����"+sep;
  }


  if(_tttt==null || "".equals(_tttt))
    return false;
  return true;
}

public int getCongGe() {
  //3. �Ӹ�
  _tttt = null;

  if(SiZhu.muCent == 0 && SiZhu.huoCent == 0 && SiZhu.tuCent == 0 &&
     SiZhu.jinCent == 0 && SiZhu.shuiCent == 0) {
    getWuXingCent();
  }
  //�������£���������<80, ������>530
  if(YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.rg]][YiJing.DIZIWH[SiZhu.yz]]>0) {
    if(pub.getShiShenCent(0)<SiZhu.baifen[0] && pub.getShiShenCent(2)>=SiZhu.baifen[3]) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] +
          "�ոɣ���춲��£�����������������ίʵ�����βƣ�ֻ�ô�֮��Ϊ�ӲƸ�" + sep;
      return 2;
    }
  }
  //����ɱ�£���������<80, ��ɱ����>530
  if(YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.TIANGANWH[SiZhu.rg]]>0) {
    if(pub.getShiShenCent(0)<SiZhu.baifen[0] && pub.getShiShenCent(3)>=SiZhu.baifen[3]) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ���ɱ��������������ίʵ������ɱ��ֻ�ô�֮��Ϊ��ɱ��" +
          sep;
      return 3;
    }
  }
  //����ʳ���£���������<80, ʳ������>530
  if(YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.rg]][YiJing.DIZIWH[SiZhu.yz]]>0) {
    if(pub.getShiShenCent(0)<SiZhu.baifen[0] && pub.getShiShenCent(1)>=SiZhu.baifen[3]) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ�ʳ����������������ίʵ���������й��ֻ�ô�֮��Ϊ�Ӷ���" +
          sep;
      return 1;
    }
  }
  //������ӡ����>
    if(pub.getShiShenCent(0)>=SiZhu.baifen[2] && pub.getShiShenCent(4)>=SiZhu.baifen[3] &&
       pub.getShiShenCent(2)==0 && pub.getShiShenCent(3)==0) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ���ӡ������������һ�����ǹ�ɱ֮����ν֮����ͬ�ġ���˳�������棬Ϊ��ǿ��"+sep;
      return 4;
    }
    //��������>530
    if(pub.getShiShenCent(0)>=SiZhu.baifen[3] && pub.getShiShenCent(3)==0) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "�ոɣ��������޹�ɱ�ƣ�����ӡ�֮����ֻ�ô�������Ϊ������" + sep;
      return 5;
    }


    return 0;
}

private String _getYaoHe() {
  _tttt = null;
  if(((SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.CHOU) ||
     (SiZhu.rg==YiJing.GUI && SiZhu.rz==YiJing.CHOU)) &&
     pub.getGzNum(2, YiJing.CHOU)>1) {
     _tttt="�������ң�����б��������Ϊ���ǣ�����ν��ң�ȸ�"+sep+
         "����֧��ʱ֧���ӻ��ȣ������ʵ������ң�����������������������������֮һ�ֺ�ס��Ϊ���ɱ��������磬���й�����ּ��硣"+sep;
  }
  if((SiZhu.rg==YiJing.JIA && SiZhu.rz==YiJing.ZI)) {
    if(SiZhu.jinCent == 0 && SiZhu.tuCent == 0)
     _tttt="�����ռ���ʱ�������й�ˮң���������������������𣬱�������𣬴���ν��ң�ȸ�"+sep+
         "�������������ӡ�����ӣ�����ң�ϣ���ɼ��������������ꡢ�ϡ�����Ϊ�Ƹ�ϲ����ɡ��������ӡ��������î����֮�¡���ǿ�й���֮�˷�����Ϸ��ˡ�"+sep;
  }

  return _tttt;
}

private String _getXingHeGe() {
  _tttt = null;
  if(SiZhu.rg==YiJing.GUI && SiZhu.sg==YiJing.JIA && SiZhu.sz==YiJing.YIN) {
     _tttt="������ʱ����λ������ν�̺ϸ���������ƹ�ȫ���˼���ʵ���������磬ϲ���м��ϳ��޹�ɱ�����ټ��׸������꼺�֣�Ϊ���Ժ�"+sep;
  }

  return _tttt;
}

private String _getJingLanCa() {
  _tttt = null;
  if(SiZhu.rg==YiJing.GENG && pub.getGzNum(YiJing.GENG, 1)>=3 &&
     (SiZhu.rz==YiJing.CHEN || SiZhu.rz==YiJing.SHEN || SiZhu.rz==YiJing.ZI) &&
     (getWuGe()==YiJing.SHUI && pub.getGzNum(YiJing.SHEN,2)>0 && pub.getGzNum(YiJing.CHEN,2)>0)) {
      _tttt = "����ȫ�����£������ӳ������������֣����յù���Ϊ�󣬴���ν������񣻲�����ʵ����ϲ�ж����Ƶأ����߱�����������ɹ�ӡ����ɷ����ӡ���ʳ�����������ƣ�ʱ�����꣬�丣����"+sep;
  }
  return _tttt;
}

private String _getHeLu() {
  _tttt = null;
  if(SiZhu.rg==YiJing.WUG && SiZhu.sg==YiJing.GENG && SiZhu.sz==YiJing.SHEN &&
    (SiZhu.rz==YiJing.SHEN || SiZhu.rz==YiJing.ZI || SiZhu.rz==YiJing.CHEN || SiZhu.rz==YiJing.XU)) {
   _tttt="����ʱ�����գ�ʳ����»������������î���ǣ�������������ż������νרʳ��»���������ʵ����ɷΪ��������ӡ�Ǹ���ʳ��������î����ʵ���ǡ����ֳ��꣨���������ȼɣ���ϲ�ﶬ����ʳ���������ǡ�ӡ緣����̳��ƺ�"+sep;
 }
 if(SiZhu.rg==YiJing.GUI && SiZhu.sg==YiJing.GENG && SiZhu.sz==YiJing.SHEN) {
   _tttt="���ո���ʱ���Ը��갵����îʳ»�����ǡ����»���������갵��������١���ӡ�����������ȫ�������������������󣬴���νרӡ��»��ϲ����ﶬ������춴��ļ����졢�����ȡ��硢���˸��������ꡣϲ��������ӡ������ˮ֮�˴󷢣��������"+sep;
  }
  return _tttt;
}

private String _getChaoYang() {
  _tttt = null;
  if(SiZhu.rg == YiJing.XIN && SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.ZI)
    _tttt = "������������ʱ������ν������������ϲ����ˮ��ľ��ƽƽ���ɱ��������ɱ���ɳ�ϣ����²Ƹ��ԲƸ��ۣ��ɳ���"+sep;

  return _tttt;
}

private String _getDaoCh() {
  _tttt = null;
  int j=0;

  if (((SiZhu.rg==YiJing.BING && SiZhu.rz==YiJing.WUZ) ||
      (SiZhu.rg==YiJing.DING && SiZhu.rz==YiJing.SI)) &&
      (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
      (pub.getGzNum(YiJing.SI,2)>=3 || pub.getGzNum(YiJing.WUZ,2)>=3)){
    _tttt = "�ֵ�֧�����س壬���Թ�ˮΪ���٣�»�������������������������й�ˮ��������ͬ�ˣ�������ν����񣻸��ó��ꡢ��֮һ�ְ�����Ϊ������򲻿ɣ�����δ��ȼ�������ʵ" + sep;
  }

  return _tttt;
}

private String _getFeiTianLuMa() {
  _tttt = null;
  int j=0;

  if (((SiZhu.rg==YiJing.GENG && SiZhu.rz==YiJing.ZI) &&
       (SiZhu.rg==YiJing.REN && SiZhu.rz==YiJing.ZI) &&
        SiZhu.yz==YiJing.ZI && pub.getGzNum(YiJing.ZI,2)>=3)){
    _tttt = "�Ӷಢ��»������ν����»��񣻼ɹ�����¶��»�ѷɳ壻��������ܷɳ壨���곽���ӣ���Ҫ������һ�ֺ�ס���������˹�����Ҫ������δ����������κ�һ�ְ����磩��ϲ�˹١�ʳ�񼰸�֧����" + sep;
  }
  if (((SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.HAI) &&
       (SiZhu.rg==YiJing.GUI && SiZhu.rz==YiJing.HAI) &&
       SiZhu.yz==YiJing.HAI && pub.getGzNum(YiJing.HAI,2)>=3)){
    _tttt = "���ಢ��»������ν����»��񣻼ɹ�����¶��»�ѷɳ壻��������ܷɳ壨��������Ҫ������һ�ֺ�ס���������˹�����Ҫ�������ꡢ�ϡ�����κ�һ�ְ����ȣ���ϲ�˹١�ʳ�񼰸�֧���ˣ�" + sep;
  }

  return _tttt;
}


private String _getOneOrTwo() {
  _tttt = null;
  int j=0;

  if(SiZhu.muCent == 0) j++;
  if(SiZhu.huoCent == 0) j++;
  if(SiZhu.tuCent == 0) j++;
  if(SiZhu.jinCent == 0) j++;
  if(SiZhu.shuiCent == 0) j++;
  if(j==4) _tttt = "�ָ�֧�崿����������νһ�е�����"+sep;
  if(j==3) _tttt = "�ָ�֧�崿����������ν��������"+sep;

  return _tttt;
}

/**
 * �˸�
 * �Ƚٲ��ڰ˸�֮�ڣ��������»�и���
 */
public int getBaGe() {
  //��Ƚٲ���ȡ�񣬵�Ҳ���ܾʹ˷��أ���Ϊ���������£���͸ˮ����ȡ�Ƹ�
  //if(YiJing.DIZIWH[SiZhu.yz] == YiJing.TIANGANWH[SiZhu.rg])
  //  return 0;

  int[] xc = SiZhu.DZXUNCANG[SiZhu.yz];
  int ngwx = YiJing.TIANGANWH[SiZhu.ng];
  int ygwx = YiJing.TIANGANWH[SiZhu.yg];
  int sgwx = YiJing.TIANGANWH[SiZhu.sg];
  int yzwx = 0;
  String _t = "";
  int whichxc = 0;

  for(int i=0; i<xc.length; i++) {
    yzwx = YiJing.TIANGANWH[xc[i]];
    whichxc = xc[i];
    _tttt = null;
    if(i==0) _t="�����";
    if(i>=1) _t="�������";

    if (yzwx == ygwx && yzwx!=YiJing.TIANGANWH[SiZhu.rg]) {
      _tttt = _t + YiJing.TIANGANNAME[SiZhu.yg] +
          YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.yg]] +  "���¸�͸����Ϊ" +
          SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][SiZhu.yg]] + "��"+sep;
      break;
    }
    else if (yzwx == ngwx && yzwx!=YiJing.TIANGANWH[SiZhu.rg]) {
      _tttt = _t + YiJing.TIANGANNAME[SiZhu.ng] + YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.ng]] +
          "�����͸����Ϊ" + SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][SiZhu.ng]] + "��"+sep;
      break;
    }
    else if (yzwx == sgwx && yzwx!=YiJing.TIANGANWH[SiZhu.rg]) {
      _tttt = _t + YiJing.TIANGANNAME[SiZhu.sg] + YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.sg]] +
          "��ʱ��͸����Ϊ" + SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][SiZhu.sg]] + "��"+sep;
      break;
    }
  }
  if(_tttt!=null)
    return SiZhu.TGSHISHEN[SiZhu.rg][whichxc];

  if(_tttt == null) {
    if(YiJing.DIZIWH[SiZhu.yz] == YiJing.TIANGANWH[SiZhu.rg]) {
      if(xc.length<=1)
        whichxc = xc[0];
      else
        whichxc = xc[1];
      _t = "�������������͸��ȡ�����";
    }else{
      whichxc = xc[0];
      _t = "�������������͸��ȡ�䱾��";
    }
    _tttt = _t + YiJing.TIANGANNAME[whichxc] +
        YiJing.WUXINGNAME[YiJing.TIANGANWH[whichxc]] + SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][whichxc]] + "��"+sep;
    return SiZhu.TGSHISHEN[SiZhu.rg][whichxc];
  }

  return 0;
}

/**
 * ���޸��ȡ����£�ȡ������
 */
private boolean getYueRenOtherGe(int x, int y) {
  _tttt = "";
  if(SiZhu.rg==x && SiZhu.yz==y) {
    //_tttt = YiJing.TIANGANNAME[x]+"��"+YiJing.DIZINAME[y]+"�£�Ϊ";
    if(y==YiJing.YIN) {
      if(SiZhu.huoCent>=SiZhu.tuCent) _tttt += "�����˹ٸ�";
      else _tttt += "����ƫ�Ƹ�";
    }else if(y==YiJing.WUZ) {
      if(x==YiJing.BING) _tttt += "�����˹ٸ�";
      if(x==YiJing.WUG)  _tttt += "������ӡ��";
    }else if(y==YiJing.SI) {
      if(SiZhu.tuCent>=SiZhu.jinCent) {
        if (x == YiJing.DING) {
          _tttt += "�����˹ٸ�";
        }else{
          _tttt += "������ӡ��";
        }
      } else {
        if (x == YiJing.DING) {
          _tttt += "�������Ƹ�";
        }else{
         _tttt += "������ӡ��";
        }
      }
    }else if(y==YiJing.SHEN) {
      if(SiZhu.shuiCent>=SiZhu.tuCent) _tttt += "��ˮ�˹ٸ�";
      else _tttt += "������ӡ��";
    }else if(y==YiJing.HAI) {
      _tttt += "��ľ�˹ٸ�";
    }

    return true;
  }
  return false;
}

/**
 * ���и�
 */
private boolean getYueRen(int x, int y) {
  _tttt = null;
  if(SiZhu.rg==x && SiZhu.yz==y) {
    _tttt = YiJing.TIANGANNAME[x]+"��"+YiJing.DIZINAME[y]+"�£�Ϊ���и�"+sep;
    return true;
  }
  return false;
}

/**
 * ��»��
 */
private boolean _getJianLu(int x, int y) {
  _tttt = null;
  if(SiZhu.rg==x && SiZhu.yz==y) {
    _tttt = YiJing.TIANGANNAME[x]+"��"+YiJing.DIZINAME[y]+"�£�Ϊ��»��"+sep;
    return true;
  }
  return false;
}

/**
 * //������
 * @return
 */
private boolean _getHuaQiGe(int x, int y, int[] yz, int[] nozi, String name) {
  String _temp = null;
  _tttt = null;

  if(SiZhu.rg==x && SiZhu.yg==y) _temp = YiJing.TIANGANNAME[x] +"��"+YiJing.TIANGANNAME[y]+"��";
  else if(SiZhu.rg==x && SiZhu.sg==y) _temp = YiJing.TIANGANNAME[x] +"��"+YiJing.TIANGANNAME[y]+"ʱ";
  else if(SiZhu.rg==y && SiZhu.yg==x) _temp = YiJing.TIANGANNAME[y]+"��"+YiJing.TIANGANNAME[x] +"��";
  else if(SiZhu.rg==y && SiZhu.sg==x) _temp = YiJing.TIANGANNAME[y]+"��"+YiJing.TIANGANNAME[x] +"ʱ";
  if(_temp != null) {
    if((SiZhu.yz == yz[0] || SiZhu.yz == yz[1] ||
        SiZhu.yz == yz[2] || SiZhu.yz == yz[3] ) &&
       (pub.isNo(nozi[0], 1) && pub.isNo(nozi[1], 1) &&
        pub.isNo(nozi[2],2) && pub.isNo(nozi[3],2))) {
      _tttt = _temp + "������" + YiJing.DIZINAME[SiZhu.yz] + "�£�" + name;
      return true;
    }
  }
  return false;
}

  /**
   * ��������֧�ͳ����·ݼ�����
   * @return
   */
  public String sijiDesc() {
    String s = null;

    switch(SiZhu.yz){
      case 3:
        if(SiZhu.rg==1 || SiZhu.rg==2){
          s = "����ľ�ۣ��ຮ�̴档";
        }else if(SiZhu.rg==3 || SiZhu.rg==4){
          s = "�����ڴ���������ľ�㣬ν������棬�������ۡ�";
        }else if(SiZhu.rg==5 || SiZhu.rg==6){
          s = "�ຮ�̴棬���ƹ��顣";
        }else if(SiZhu.rg==7 || SiZhu.rg==8){
          s = "ľ�۽𺮡�";
        }else{
          s = "�����Ժ���";
        }
        break;
      case 4:
      if(SiZhu.rg==1 || SiZhu.rg==2){
        s = "ľ������ຮ�̴档";
      }else if(SiZhu.rg==3 || SiZhu.rg==4){
        s = "ľ��ͨ����";
      }else if(SiZhu.rg==5 || SiZhu.rg==6){
        s = "���ƹ��顣";
      }else if(SiZhu.rg==7 || SiZhu.rg==8){
        s = "ľ����飬�ຮδ����";
      }else{
        s = "����������";
      }
      break;
    case 5:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "����������";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "ʪ���޹⡣";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "���Ȩ��";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "ĸ�����ࡣ";
      }
      else {
        s = "�ܹ���ˮ��";
      }
      break;
    case 6:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "�������ľ�ӻ��ƣ�����Ҷ�ݡ�";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "������Ȩ��";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "��������������ҡ�";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "�����������Ϊ����������δ��������������";
      }
      else {
        s = "ִ�Թ�Դ��ʱ���Լʣ����ñȼ硣";
      }
      break;
    case 7:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "�����������Ҷ�ݡ�";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "���Ȩ����֮���ӡ�";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "�������";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "�һ��۽���Ϊ����������δ��������������";
      }
      else {
        s = "ִ�Թ�Դ��ʱ���Լʣ����ñȼ硣";
      }
      break;
    case 8:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "�������ң�����Ҷ�ݡ�";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "�����̴档";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "�������";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "������������Ϊ����������δ��������������";
      }
      else {
        s = "������֮������������";
      }
      break;
    case 9:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "��ľ���㡣";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "��Ϣ���ݡ�";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "����ĸ˥��";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "���Ȩ��";
      }
      else {
        s = "��ˮͨԴ��";
      }
      break;
    case 10:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "�����Ȩ����ľ������";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "��Ϣ���ݡ�";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "����ĸ˥��";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "�����Ȩ��";
      }
      else {
        s = "��ˮͨԴ��";
      }
      break;
    case 11:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "����������";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "����������";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "���Ȩ��";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "�����д档";
      }
      else {
        s = "������֮������������";
      }
      break;
    case 12:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "ľ��ˮ����";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "���������";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "ˮ��������";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "������ˮ��";
      }
      else {
        s = "������ˮ��";
      }
      break;
    case 1:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "ľ˥ˮ������ˮ������";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "���������";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "��ʪ������";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "������ˮ��";
      }
      else {
        s = "������ˮ��";
      }
      break;
    case 2:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "ľ��ˮ������ˮ������";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "ʪ���޹⡣";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "�캮�ض���";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "��������";
      }
      else {
        s = "����ˮ����";
      }
      break;
    }
    return s;
  }



  /**
   * ȡ��ֻ򷽾�
   * ��λ������1λ�����У��ڶ�λ��������֧��0Ϊ����֧
   * 1:��ľ��=3+4+5=12 min(x,y,z)=3 max(x,y,z)=5
   * 2:����=6+7+8=21
   * 3:����=9+10+11=30
   * 4:��ˮ��=12+1+2=15
   * 5:��ľ��=12+4+8=24
   * 6:�ϻ��=11+3+7=21
   * 7:�Ͻ��=10+2+6=18
   * 8:��ˮ��=9+1+5=15
   */
  private int getFangOrHui() {
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 3, 5, 12) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 3, 5, 12) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 3, 5, 12))
      return 11;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 3, 5, 12))
      return 10;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 6, 8, 21) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 6, 8, 21) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 6, 8, 21))
      return 21;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 6, 8, 21))
      return 20;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 9, 11, 30) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 9, 11, 30) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 9, 11, 30))
      return 31;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 9, 11, 30))
      return 30;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 1, 12, 15) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 1, 12, 15) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 1, 12, 15))
      return 41;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 1, 12, 15))
      return 40;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 4, 12, 24) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 4, 12, 24) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 4, 12, 24))
      return 51;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 4, 12, 24))
      return 50;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 3, 11, 21) ||
       pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 3, 11, 21) ||
       pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 3, 11, 21))
     return 61;
     if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 3, 11, 21))
       return 60;
   if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 2, 10, 18) ||
       pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 2, 10, 18) ||
       pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 2, 10, 18))
     return 71;
   if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 2, 10, 18))
     return 70;
   if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 1, 9, 15) ||
       pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 1, 9, 15) ||
       pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 1, 9, 15))
     return 81;
   if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 1, 9, 15))
     return 80;

   return 0;
  }

  /**
   * ���ظ������Ƿ���������ϻ��ϼ���λ��
   * 0 ���� 1��һ֧ 2����֧ >=100 Ϊ����֧ ��100Ϊ����֧���� 101Ϊ����֧��֧
   * gz ��ɻ��֧
   * type ������
   */
  private int isHasRelate(int gz, int type, int sum ,int power) {
    int iret = 0;
    int year,month,day,hour;
    if(gz==1) {
      year = SiZhu.ng;
      month = SiZhu.yg;
      day = SiZhu.rg;
      hour = SiZhu.sg;
    }else{
      year = SiZhu.nz;
      month = SiZhu.yz;
      day = SiZhu.rz;
      hour = SiZhu.sz;
    }

    if(year+month==sum && year*month==power) {
      return type*100;
    }
    if(year+day==sum && year*day==power) {
      return type*10+1;
    }
    if(year+hour==sum && year*hour==power) {
      return type*10+2;
    }
    if(day+month==sum && day*month==power) {
      return type*100;
    }
    if(hour+month==sum && hour*month==power) {
      return type*100+1;
    }
    if(day+hour==sum && day*hour==power) {
      return type*10;
    }

    return iret;
  }

  /**
   * �õ��������ͼ�λ��
   * ��λ���֣���һλ���ͣ��ڶ�λλ��
   * 1���� 4î�� 3���� 6�Ⱥ� 5���� 2��δ
   * 0���� 1��һ֧ 2����֧
   * type Ϊ����֧��
   * @return
   */
  private int getLiuChong(int type, int sum, int power) {
    int[] irets = new int[2];
    int j = 0;

    int i = isHasRelate(2,type,sum,power);
    if(i>0) irets[j++] = i;

    return irets[0];
  }

  /**
   * �õ���������ϣ����������޳��ƣ��˷����ݲ���
   * 1:��ľ 2:��� 3:��� 4:��ˮ
   * 5:��ľ 6:�ϻ� 7:�Ͻ� 8:��ˮ
   * �����������=i*10����Ϊ����
   */
  private int getSanHuiOrSanHe() {
    int i = getFangOrHui();
    int c = 0;
    // �ж��Ƿ�Ͼ��е�����î�ϱ�����֧֮����
    if(i>4) {
      if(i==50 || i==51) {
        c = getLiuChong(4, 14, 40);
        if(c==40)
          return i * 10;
      }
      if(i==60 || i==61) {
        c = getLiuChong(1, 8, 7);
        if(c==10)
          return i * 10;
      }
      if(i==70 || i==71) {
        c = getLiuChong(4, 14, 40);
        if(c==40)
          return i * 10;
      }
      if(i==80 || i==81) {
        c = getLiuChong(1, 8, 7);
        if(c==10)
          return i * 10;
      }
    }

    return i;
  }

  /**
   *��ϣ���λ���֣���һλ���ͣ��ڶ�λλ��
   * �����ϲ����������
   * 5:��ľ 6:�ϻ� 7:�Ͻ� 8:��ˮ
   * type 1ǰ����� 2������� 3�������
   */
  private int getBanHe(int type, int wx) {
    int i = getFangOrHui();
    int result = 0;
    int[] irets = new int[2];
    int j = 0;

    if(wx == 5) {
      if (i != 50 && i != 51) {
        if (type == 1) {
          result = isHasRelate(2, 5, 16, 48);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 5, 12, 32);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 5, 20, 96);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }
    if(wx == 6) {
      if (i != 60 && i != 61) {
        if (type == 1) {
          result = isHasRelate(2, 6, 10, 21);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 6, 18, 77);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 6, 14, 33);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }
    if(wx == 7) {
      if (i != 70 && i != 71) {
        if (type == 1) {
          result = isHasRelate(2, 7, 16, 60);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 7, 12, 20);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 7, 8, 12);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }
    if(wx == 8) {
      if (i != 80 && i != 81) {
        if (type == 1) {
          result = isHasRelate(2, 8, 10, 9);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 8, 6, 5);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 8, 14, 45);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }

    return irets[0];
  }

  /**
   * �����Ƿ�û�
   a) ��ɺϻ���������ո�Ϊ���������¸ɻ�ʱ��Ϊ�ϣ�����֧��Ϊ�ϻ�֮ͬһ���з��ۺϻ���
   b) ���������ϣ���֧Ϊ�ϻ�֮�����и��û���
   c) �ո����¸ɻ��ո���ʱ�ɺϣ���֧����������������������֧Ϊ���Ͼֻ������Ҳ���ۻ���

   ��֧�ϻ����Ҫ��֧�����������������͸����֧�ϻ�֮���з����ۻ���
   ����֮�ϲ������Ժ϶������ۡ�
   ����ϣ��غϣ��ϻ�֮���Ժϻ���������ۣ�ԭ����ʧȴ������;
   �϶�������Ϊ�������У���������������֧�������̳塣
   */
  private int getHeHua(int type, int x1, int x2) {
    int i = getFangOrHui();
    if(type==1) {
      if(SiZhu.yg+SiZhu.rg==x1+x2 && SiZhu.yg*SiZhu.rg==x1*x2) {
        if(YiJing.DIZIWH[SiZhu.yz] == YiJing.TGHE[x1][x2]) {
           return 10001;
        }
        if((YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)) ||
           (YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)) ||
           (YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)) ||
           (YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)))
          return 10003;
        if((YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)) ||
           (YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)) ||
           (YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)) ||
           (YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)))
          return 10003;
        if((YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)) ||
           (YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)) ||
           (YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)) ||
           (YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)))
          return 10003;
        if((YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)) ||
           (YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)) ||
           (YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)) ||
           (YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)))
          return 10003;

      }
      if(SiZhu.yg+SiZhu.ng==x1+x2 && SiZhu.yg*SiZhu.ng==x1*x2) {
        int j = YiJing.DZLIUHE[x1][x2];
        if(j==YiJing.JIN && (YiJing.DIZIWH[SiZhu.nz]==YiJing.JIN ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.TU))
          return 10002;
        if(j==YiJing.MU && (YiJing.DIZIWH[SiZhu.nz]==YiJing.SHUI ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.MU) ||
            SiZhu.nz==YiJing.WEI || SiZhu.nz==YiJing.CHEN)
          return 10002;
        if(j==YiJing.SHUI && (YiJing.DIZIWH[SiZhu.nz]==YiJing.JIN ||
                              YiJing.DIZIWH[SiZhu.nz]==YiJing.SHUI)||
            SiZhu.nz==YiJing.CHOU || SiZhu.nz==YiJing.CHEN)
          return 10002;
        if(j==YiJing.HUO && (YiJing.DIZIWH[SiZhu.nz]==YiJing.MU ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.HUO)||
            SiZhu.nz==YiJing.XU || SiZhu.nz==YiJing.WEI)
          return 10002;
        if(j==YiJing.TU && (YiJing.DIZIWH[SiZhu.nz]==YiJing.HUO ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.TU))
          return 10002;
      }
    }else{
      if((SiZhu.nz+SiZhu.yz==x1+x2 && SiZhu.nz*SiZhu.yz==x1*x2) ||
         (SiZhu.yz+SiZhu.rz==x1+x2 && SiZhu.yz*SiZhu.rz==x1*x2) ||
         (SiZhu.rz+SiZhu.sz==x1+x2 && SiZhu.rz*SiZhu.sz==x1*x2)){
        if(YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.ng] ||
           YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.yg] ||
           YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.rg] ||
           YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.sg])
          return 10000;
      }
    }

    return 0;
  }

  /**
   * �õ�����,������ɻ��֧
   * �����������δ�ϣ�����ˮ�����ӳ�� >100Ϊ����֧��100����101��֧��,<100������֧
   * >10000Ϊ�϶�����10001Ϊ��֧�û� 10002Ϊ��֧�и� 10003Ϊ�ϻ����������ϻ������
   * 1:��ľ 2:��� 3:��� 4:��ˮ
   * 5:��ľ 6:�ϻ� 7:�Ͻ� 8:��ˮ 9:�ϼ׼����ӳ��� 0����δ��
   * type 1Ϊ��� 2Ϊ��֧
   * wx Ϊ����
   */
  private int _getLiuHe(int type, int wx) {
    int i = getFangOrHui();
    int result = 0;
    int[] irets = new int[2];
    int j=0;
    int _i = 0;

    if(type==1) {
      if(wx==5) {
        result = isHasRelate(1, 5, 13, 36);
        if (result > 0) {
          _i = getHeHua(type,4,9);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==6) {
        result = isHasRelate(1, 6, 15, 50);
        if (result > 0) {
          _i = getHeHua(type,5,10);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==7) {
        result = isHasRelate(1, 7, 9, 14);
        if (result > 0) {
          _i = getHeHua(type,2,7);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==8) {
        result = isHasRelate(1, 8, 11, 24);
        if (result > 0) {
          _i = getHeHua(type,3,8);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==9) {
        result = isHasRelate(1, 9, 7, 6);
        if (result > 0) {
          _i = getHeHua(type,1,6);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
    }else{
      if(wx==5) {
        result = isHasRelate(2, 5, 15, 36);
        if (result > 0) {
          _i = getHeHua(type,3,12);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==6) {
        result = isHasRelate(2, 6, 15, 44);
        if (result > 0) {
          _i = getHeHua(type,4,11);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==7) {
        result = isHasRelate(2, 7, 15, 50);
        if (result > 0) {
          _i = getHeHua(type,5,10);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==8) {
        if(i!=40 || i!=41) {
          result = isHasRelate(2, 8, 15, 54);
          if (result > 0) {
          _i = getHeHua(type,6,9);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
        }
      }
      if(wx==9) {
        result = isHasRelate(2, 9, 3, 2);
        if (result > 0) {
          _i = getHeHua(type,1,2);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==10) {
        if(i!=20 || i!=21) {
          result = isHasRelate(2, 10, 15, 56);
          if (result > 0) {
          _i = getHeHua(type,7,8);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
        }
      }
    }

    return irets[0];
  }

  /**
   * �õ�����
   * �������9 ����ľ����4
   * ��δ 2���� 3���� 4î�� 9���� 10�Ϻ��຦
   * @return
   */
  private double getLiuHai(int x, int y, String name) {
    int i = getFangOrHui();
    int result = 0;

    if (i == 30 || i == 31) {
      if(x+y==20 && x*y==99)
        return 0;
    }
    if (i == 10 || i == 11) {
      if(x+y==9 && x*y==20)
        return 0;
    }
    result = isHasRelate(2, 1, x+y, x*y);
    if (result > 0) {
      desc += name + "��";
      return SiZhu.haiXS;
    }
    return 0;
  }

  /**
   * �õ�����,��һλ��wx���ڶ�λ��λ��
   * 1:��î 2:������ 201���� 202���� 203���� 3:����δ
   * wx ��������
   */
  private void getSanXing() {
    int result = 0;
    int[] irets = new int[2];
    int j = 0;

    result = isHasRelate(2, 1, 5, 4);
    if (result > 0) {
      _shui += SiZhu.xingXS * shui;
      _mu += SiZhu.xingXS * mu;
      desc += "��î���̣�";
      _p("��î���̣�ˮ�� ",SiZhu.xingXS * shui,"ľ�� ",SiZhu.xingXS * mu);
    }

    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 3, 9, 18) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 3, 9, 18) ||
        pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 3, 9, 18) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 3, 9, 18))
      return;
    else {
      result = isHasRelate(2, 2, 9, 18);
      if (result > 0) {
        _mu += SiZhu.xingXS * mu;
        _huo += SiZhu.xingXS * huo;
        desc += "�������̣�";
        _p("�������̣�ľ�� ",SiZhu.xingXS * mu,"��� ",SiZhu.xingXS * huo);
      }
      else {
        result = isHasRelate(2, 2, 15, 54);
        if (result > 0) {
          _huo += SiZhu.xingXS * huo;
          _jin += SiZhu.xingXS * jin;
          desc += "�������̣�";
          _p("�������̣���� ",SiZhu.xingXS * huo,"��� ",SiZhu.xingXS * jin);
        }
        else {
          result = isHasRelate(2, 2, 12, 27);
          if (result > 0) {
            _mu += SiZhu.xingXS * mu;
            _jin += SiZhu.xingXS * jin;
            desc += "�������̣�";
            _p("�������̣�ľ�� ",SiZhu.xingXS * mu,"��� ",SiZhu.xingXS * jin);
          }
        }
      }

      if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 2, 11, 21) ||
          pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 2, 11, 21) ||
          pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 2, 11, 21) ||
          pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 2, 11, 21))
        return;
      else {
        result = isHasRelate(2, 3, 13, 22);
        if (result > 0) {
          _tu += SiZhu.xingXS * 2 * tu;
          _p("�������̣����� ",SiZhu.xingXS * 2 * tu);
          desc += "�������̣�";
        }
        else {
          result = isHasRelate(2, 3, 19, 88);
          if (result > 0) {
            _tu += SiZhu.xingXS * 2 * tu;
            _p("��δ���̣����� ",SiZhu.xingXS * 2 * tu);
            desc += "��δ���̣�";
          }
          else {
            result = isHasRelate(2, 3, 10, 16);
            if (result > 0) {
              _tu += SiZhu.xingXS * 2 * tu;
              _p("��δ���̣����� ",SiZhu.xingXS * 2 * tu);
              desc += "��δ���̣�";
            }
          }
        }
      }
    }
  }

  /**
   * //2����֧��Ը�֧���
    //���������(������ϵ��-�����3.0��֧  �����2.5����֧)
   */
  private double getFangOrHuiXS(int wx, String name1, String name2) {
    double xs = 0;
    double xs1 = 0;
    String _temp = "��";
    double xs2=SiZhu.sanheXS[2],xs3=SiZhu.sanheXS[1];

    if(wx<5) {
      _temp = "��";
      xs2 = SiZhu.sanhuiXS[2];
      xs3 = SiZhu.sanhuiXS[1];
    }

    xs = getFangOrHui();
    if(xs == wx*10) {
      xs1 = xs2;
      desc += name1+"����������"+_temp+name2+"��";
    }
    if(xs == wx*10+1) {
      xs1 = xs3;
      desc += name1+"��������"+_temp+name2+"��";
    }
    return xs1;
  }

  /**
   * ��֧����
   * wx:�������͵�����
   */
  private double[] getDZLiuChong(int wx,int sum, int power, String name1, String name2, String name3, String name4) {
    double xs = 0;
    double[] _xs = new double[2];

    xs = getLiuChong(wx, sum, power);
    double xs1=0, xs2=0;

    if(wx==5 || wx==2) {
      if(xs == wx * 100) {
        xs1 = SiZhu.lcXS1[1] ;
        xs2 = SiZhu.lcXS1[3];
        desc += name1 + "������壻";
      }
      if(xs == wx * 100+1) {
        xs1 = SiZhu.lcXS1[2] ;
        xs2 = SiZhu.lcXS1[4];
        desc += name1 + "��֧��壻";
      }
    }else{
      if (xs == wx * 100 && SiZhu.yz == wx) {
        xs2 = SiZhu.lcXS1[3];
        xs1 = SiZhu.lcXS1[1];
        desc += name1 + "����������壻";
      }
      if (xs == wx * 100 && SiZhu.yz == wx+6) {
        xs2 = SiZhu.lcXS1[1];
        xs1 = SiZhu.lcXS1[3];
        desc += name1 + "����������壻";
      }
      if (xs == wx * 100 + 1 && SiZhu.yz == wx) {
        xs2 = SiZhu.lcXS1[4];
        xs1 = SiZhu.lcXS1[2];
        desc += name1 + "��֧��������壻";
      }
      if (xs == wx * 100 + 1 && SiZhu.yz == wx+6) {
        xs2 = SiZhu.lcXS1[2];
        xs1 = SiZhu.lcXS1[4];
        desc += name1 + "��֧��������壻";
      }
    }
    if(xs == wx*10) {
      xs2 = SiZhu.lcXS1[5];
      xs1 = SiZhu.lcXS1[5];
      desc += name1+"������壻";
    }
    if(xs == wx*10+1) {
      xs2 = SiZhu.lcXS1[6];
      xs1 = SiZhu.lcXS1[6];
      desc += name1+"��֧��壻";
    }
    if(xs == wx*10+2) {
      xs2 = SiZhu.lcXS1[7];
      xs1 = SiZhu.lcXS1[7];
      desc += name1+"ң������������";
    }

    _xs[0]= xs1;
    _xs[1] = xs2;
    return _xs;
  }

  /**
   * �õ����ϵ��
   * qh: ���ǰ��
   * wx: ��������
   */
  private double getBanHeXS(int qh, int wx, String name1, String name2) {
    double xs1 = 0;
    double xs = 0;

    xs =  getBanHe(qh, wx);
   if(xs == wx*100) {
     xs1 = SiZhu.wangBHXS[1];
     desc += name1+"���������ϣ�";
   }
   if(xs == wx*100+1) {
     xs1 = SiZhu.wangBHXS[2];
     desc += name1+"�������֧��ϣ�";
   }
   if(xs == wx*10) {
     xs1 = SiZhu.wangBHXS[3];
     desc += name1+"������ϣ�";
   }
   if(xs == wx*10+1) {
     xs1 = SiZhu.wangBHXS[4];
     desc += name1+"��֧��ϣ�";
   }
   if(xs == wx*10+2) {
     xs1 = SiZhu.wangBHXS[5];
     desc += name1+"����֧��ϣ�";
   }
   return xs1;
  }

  /**
   * �õ��������
   * �Ҹ� �׼��ϻ�ʱ���ȥԭ��
   * ���� ���� �ӳ� ��δӦ��ȥԭ��
   * gz: ��ɻ��֧
   * wx: ��������
   */
  private double getLiuHe(int gz, int wx, String name1, String name2) {
    double xs1 = 0;
    double xs = 0;

    xs = _getLiuHe(gz,wx);
    if(xs == wx*100) {
      xs1 = SiZhu.lhXS[1];
      desc += name1 + "����������ϣ�";
    }
    if(xs == wx*100+1) {
      xs1 = SiZhu.lhXS[2];
      desc += name1 + "��֧������ϣ�";
    }
    if(xs == wx*10) {
      xs1 = SiZhu.lhXS[3];
      desc += name1 + "�����ϣ�";
    }
    if(xs == wx*10+1) {
      xs1 = SiZhu.lhXS[4];
      desc += name1 + "��֧�ϣ�";
    }
    if(xs == wx*10+2) {
      xs1 = SiZhu.lhXS[5];
      desc += name1 + "ң�ϣ�";
    }
    if(gz==1) {
      if (xs == 10001) {
        xs1 = SiZhu.lhXS[6];
        desc += name1 + "����֧ͬ���ж��ϻ���";
      }
      if (xs == 10002) {
        xs1 = SiZhu.lhXS[6];
        desc += name1 + "����֧�и��ϻ���";
      }
      if (xs == 10003) {
        xs1 = SiZhu.lhXS[6];
        desc += name1 + "���֧��������϶������棻";
      }
    }else{
      if(xs == 10000) {
         xs1 = SiZhu.lhXS[6];
         desc += name1 + "�����͸�����ϻ���";
       }
    }

    if(gz == 1 && (wx == 7 || wx == 9)) {
      if(xs>=10000)
        xs1 = xs1/2;
    }
    if(gz == 2 && (wx==5 || wx == 7 || wx == 9 || wx == 10)) {
      if(xs>=10000)
        xs1 = xs1/2;
    }

    return xs1;
  }

  private double getSK(int sk, int gz, int loc, int x, int y) {
    double xs1 = 0;
    String name1=null, name2=null;
    int xwx=0, ywx=0;

    if (gz == 1) {
      name1 = YiJing.TIANGANNAME[x];
      name2 = YiJing.TIANGANNAME[y];
      xwx = YiJing.TIANGANWH[x];
      ywx = YiJing.TIANGANWH[y];
    }
    else {
      name1 = YiJing.DIZINAME[x];
      name2 = YiJing.DIZINAME[y];
      xwx = YiJing.DIZIWH[x];
      ywx = YiJing.DIZIWH[y];
    }
    if (sk == 1) {
      if (YiJing.WXDANSHENG[xwx][ywx] > 0) {
        if (loc == 0) {
          //desc += name1 + "��������" + name2 + "��";
          xs1 += SiZhu.shengXS[1];
        }
        else if (loc == 1) {
          //desc += name1 + "��������" + name2 + "��";
          xs1 += SiZhu.shengXS[2];
        }
        else {
          //desc += name1 + "ң������" + name2 + "��";
          xs1 += SiZhu.shengXS[3];
        }
      }
    }
    else {
      if (YiJing.WXDANKE[xwx][ywx] > 0
          && ywx == YiJing.SHUI &&
          (x == YiJing.JI || x == YiJing.CHEN || x == YiJing.CHOU)) {
        desc += "ʪ��"+name1+"��ˮ��";
        xs1 += SiZhu.keXS[4];
      }
      else if (YiJing.WXDANKE[xwx][ywx] > 0) {
        if (loc == 0) {
          if((x==YiJing.MAO && y==YiJing.SHEN) || (x==YiJing.SHEN && y==YiJing.MAO)) {
            desc += name1 + "�������" + name2 + "����î�갵�ϣ�";
            xs1 += SiZhu.keXS[1]+SiZhu.anheXS;
          }else{
            //desc += name1 + "�������" + name2 + "��";
            xs1 += SiZhu.keXS[1];
          }
        }
        else if (loc == 1) {
          //desc += name1 + "�������" + name2 + "��";
          xs1 += SiZhu.keXS[2];
        }
        else {
          //desc += name1 + "ң�����" + name2 + "��";
          xs1 += SiZhu.keXS[3];
        }
      }
    }
    return xs1;
  }

  /**
   * �ж�ָ���ĸɻ�֧�Ƿ������
   * @param sk 1��2��
   * @param wx ��������
   */
  private double getShengKeXS(int wx, int sk) {
    double xs1 = 0;

    if(YiJing.TIANGANWH[SiZhu.ng] == wx) {
      xs1 += getSK(sk, 1, 0, SiZhu.yg, SiZhu.ng);
      xs1 += getSK(sk, 1, 1, SiZhu.rg, SiZhu.ng);
      xs1 += getSK(sk, 1, 2, SiZhu.sg, SiZhu.ng);
    }else if(YiJing.TIANGANWH[SiZhu.yg] == wx) {
      xs1 += getSK(sk, 1, 0, SiZhu.ng, SiZhu.yg);
      xs1 += getSK(sk, 1, 0, SiZhu.rg, SiZhu.yg);
      xs1 += getSK(sk, 1, 1, SiZhu.sg, SiZhu.yg);
    }else if(YiJing.TIANGANWH[SiZhu.rg] == wx) {
      xs1 += getSK(sk, 1, 1, SiZhu.ng, SiZhu.rg);
      xs1 += getSK(sk, 1, 0, SiZhu.yg, SiZhu.rg);
      xs1 += getSK(sk, 1, 0, SiZhu.sg, SiZhu.rg);
    }else if(YiJing.TIANGANWH[SiZhu.sg] == wx) {
      xs1 += getSK(sk, 1, 2, SiZhu.ng, SiZhu.sg);
      xs1 += getSK(sk, 1, 1, SiZhu.yg, SiZhu.sg);
      xs1 += getSK(sk, 1, 0, SiZhu.rg, SiZhu.sg);
    }

    if(YiJing.DIZIWH[SiZhu.nz] == wx) {
      xs1 += getSK(sk, 2, 0, SiZhu.yz, SiZhu.nz);
      xs1 += getSK(sk, 2, 1, SiZhu.rz, SiZhu.nz);
      xs1 += getSK(sk, 2, 2, SiZhu.sz, SiZhu.nz);
    }else if(YiJing.DIZIWH[SiZhu.yz] == wx) {
      xs1 += getSK(sk, 2, 0, SiZhu.nz, SiZhu.yz);
      xs1 += getSK(sk, 2, 0, SiZhu.rz, SiZhu.yz);
      xs1 += getSK(sk, 2, 1, SiZhu.sz, SiZhu.yz);
    }else if(YiJing.DIZIWH[SiZhu.rz] == wx) {
      xs1 += getSK(sk, 2, 1, SiZhu.nz, SiZhu.rz);
      xs1 += getSK(sk, 2, 0, SiZhu.yz, SiZhu.rz);
      xs1 += getSK(sk, 2, 0, SiZhu.sz, SiZhu.rz);
    }else if(YiJing.DIZIWH[SiZhu.sz] == wx) {
      xs1 += getSK(sk, 2, 2, SiZhu.nz, SiZhu.sz);
      xs1 += getSK(sk, 2, 1, SiZhu.yz, SiZhu.sz);
      xs1 += getSK(sk, 2, 0, SiZhu.rz, SiZhu.sz);
    }

    return xs1;
  }

  private void judgeWX(int gz, int x, double xs) {
    if(gz==1) {
      if (YiJing.TIANGANWH[x] == YiJing.MU) {
        _mu += xs * mu;
        _p("ľ��ظ��ؿ˵÷�", xs * mu);
      }
      if (YiJing.TIANGANWH[x] == YiJing.HUO){
        _huo += xs * huo;
        _p("����ظ��ؿ˵÷�", xs * huo);
      }
      if (YiJing.TIANGANWH[x] == YiJing.TU) {
        _tu += xs * tu;
        _p("����ظ��ؿ˵÷�", xs * tu);
      }
      if (YiJing.TIANGANWH[x] == YiJing.JIN){
        _jin += xs * jin;
        _p("����ظ��ؿ˵÷�", xs * jin);
      }
      if (YiJing.TIANGANWH[x] == YiJing.SHUI){
        _shui += xs * shui;
        _p("ˮ��ظ��ؿ˵÷�", xs * shui);
      }
    }else
    if(gz==2) {
      if (YiJing.DIZIWH[x] == YiJing.MU){
        _mu += xs * mu;
        _p("ľ��ظ��ؿ˵÷�", xs * mu);
      }
      if (YiJing.DIZIWH[x] == YiJing.HUO){
        _huo += xs * huo;
        _p("����ظ��ؿ˵÷�", xs * huo);
      }
      if (YiJing.DIZIWH[x] == YiJing.TU){
        _tu += xs * tu;
        _p("����ظ��ؿ˵÷�", xs * tu);
      }
      if (YiJing.DIZIWH[x] == YiJing.JIN){
        _jin += xs * jin;
        _p("����ظ��ؿ˵÷�", xs * jin);
      }
      if (YiJing.DIZIWH[x] == YiJing.SHUI){
        _shui += xs * shui;
        _p("ˮ��ظ��ؿ˵÷�", xs * shui);
      }
    }
  }

  private void getTFDZK(int x, int y) {
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[x]][YiJing.DIZIWH[y]] > 0) {
      desc += YiJing.TIANGANNAME[x]+"��"+YiJing.DIZINAME[y]+"ν֮�츲��";
      judgeWX(2,y,SiZhu.ganziXS[1]);
    }
    if(YiJing.WXDANSHENG[YiJing.DIZIWH[y]][YiJing.TIANGANWH[x]] > 0) {
      desc += YiJing.DIZINAME[y]+"��"+YiJing.TIANGANNAME[x]+"ν֮���أ�";
      judgeWX(1,x,SiZhu.ganziXS[1]);
    }
    if(YiJing.WXDANKE[YiJing.TIANGANWH[x]][YiJing.DIZIWH[y]] > 0) {
      desc += YiJing.TIANGANNAME[x]+"��"+YiJing.DIZINAME[y]+"ν֮��ˣ�";
      judgeWX(2,y,SiZhu.ganziXS[2]);
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[y]][YiJing.TIANGANWH[x]] > 0) {
      desc += YiJing.DIZINAME[y]+"��"+YiJing.TIANGANNAME[x]+"ν֮�ؿˣ�";
      judgeWX(1,x,SiZhu.ganziXS[2]);
    }
  }

  private void _thdh(int x, int y) {
    if(x==YiJing.WUG && y==YiJing.ZI) {
      desc += "������ϵغϣ�";
      _tu += SiZhu.tdhXS * tu;
      _shui += SiZhu.tdhXS * shui;
      _p("������ϵغ�,���� ",SiZhu.tdhXS * tu,"ˮ�� ",SiZhu.tdhXS * shui);
    }
    if(x==YiJing.XIN && y==YiJing.SI) {
      _jin += SiZhu.tdhXS * jin;
      _huo += SiZhu.tdhXS * huo;
      _p("������ϵغ�,��� ",SiZhu.tdhXS * jin,"��� ",SiZhu.tdhXS * huo);
      desc += "������ϵغϣ�";
    }
    if(x==YiJing.DING && y==YiJing.HAI) {
      _huo += SiZhu.tdhXS * huo;
      _shui += SiZhu.tdhXS * shui;
      _p("������ϵغ�,��� ",SiZhu.tdhXS * huo,"ˮ�� ",SiZhu.tdhXS * shui);
      desc += "������ϵغϣ�";
    }
    if(x==YiJing.REN && y==YiJing.WUZ) {
      _huo += SiZhu.tdhXS * huo;
      _shui += SiZhu.tdhXS * shui;
      _p("������ϵغ�,��� ",SiZhu.tdhXS * huo,"ˮ�� ",SiZhu.tdhXS * shui);
      desc += "������ϵغϣ�";
    }
  }

  /**
   * //�츲(�������֮): 0.1*�˸ɷ��� ����: 0.1��֧������
    //��ˣ�-0.1*�˸ɷ��� �ؿˣ�-0.1��֧������
    //��ϵغϣ�ֻ�����ӡ����ȡ��������������գ���0.1*��֧����
   */
  private void getGZRelate() {
    getTFDZK(SiZhu.ng,SiZhu.nz);
    getTFDZK(SiZhu.yg,SiZhu.yz);
    getTFDZK(SiZhu.rg,SiZhu.rz);
    getTFDZK(SiZhu.sg,SiZhu.sz);
    _thdh(SiZhu.ng,SiZhu.nz);
    _thdh(SiZhu.yg,SiZhu.yz);
    _thdh(SiZhu.rg,SiZhu.rz);
    _thdh(SiZhu.sg,SiZhu.sz);
  }

  /**
   * //����������3֧��һĹ����ľĹ��δ������ 0.35��֧������
    //����������3֧��һ������ľ�����ڳ����� 0.7��֧������
    //����������3֧������»��              1.0��֧������
   ��õز�Ϊ���ۣ�
   */
  private void getMuYuWang() {
    double _temp = 0;

    if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.MU ||
       YiJing.TIANGANWH[SiZhu.yg]==YiJing.MU ||
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU ||
       YiJing.TIANGANWH[SiZhu.sg]==YiJing.MU) {
      _temp =
          _getMuYuWang("ľ", YiJing.WEI, YiJing.CHEN, new int[] {0, YiJing.HAI,
                       YiJing.ZI, YiJing.YIN, YiJing.MAO});
      _mu += _temp * 100;
      _p(_tttt, _temp * 100);
    }

  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.HUO ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.HUO ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.HUO ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.HUO) {
    _temp =
        _getMuYuWang("��", YiJing.XU, YiJing.WEI, new int[] {0, YiJing.YIN,
                     YiJing.MAO, YiJing.SI, YiJing.WUZ});
    _huo += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.TU ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.TU ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.TU ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.TU) {
    _temp =
        _getMuYuWang("��", YiJing.WEI, YiJing.CHEN, new int[] {0, YiJing.SI,
                     YiJing.WUZ, YiJing.XU, YiJing.CHOU});
    _tu += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.JIN ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.JIN ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.JIN) {
    _temp =
        _getMuYuWang("��", YiJing.CHOU, YiJing.XU, new int[] {0, YiJing.CHOU,
                     YiJing.CHEN, YiJing.SHEN, YiJing.YOU});
    _jin += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.SHUI ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.SHUI ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.SHUI ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.SHUI) {
    _temp =
        _getMuYuWang("ˮ", YiJing.CHEN, YiJing.CHOU, new int[] {0,
                     YiJing.SHEN, YiJing.YOU, YiJing.HAI, YiJing.ZI});
    _shui += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  }

  private double _getMuYuWang(String name, int mu, int yu, int[] wang) {
    double xs = 0;
    String _t = "";

    if(SiZhu.nz == mu) {
      _t += "Ĺ������֧"+YiJing.DIZINAME[mu]+"��";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.yz == mu) {
      _t += "Ĺ������֧"+YiJing.DIZINAME[mu]+"��";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.rz == mu) {
      _t += "Ĺ������֧"+YiJing.DIZINAME[mu]+"��";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.sz == mu){
      _t += "Ĺ����ʱ֧"+YiJing.DIZINAME[mu]+"��";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.nz == yu) {
      _t += "����֧"+YiJing.DIZINAME[yu]+"��������";
      xs += SiZhu.sanziXS[2];
    }
    if(SiZhu.yz == yu){
      _t += "����֧"+YiJing.DIZINAME[yu]+"��������";
      xs += SiZhu.sanziXS[2];
    }
    if(SiZhu.rz == yu){
      _t += "����֧"+YiJing.DIZINAME[yu]+"��������";
      xs += SiZhu.sanziXS[2];
    }
    if(SiZhu.sz == yu) {
      _t += "��ʱ֧"+YiJing.DIZINAME[yu]+"��������";
      xs += SiZhu.sanziXS[2];
    }

    if(SiZhu.nz == wang[1] || SiZhu.nz == wang[2]) {
      _t += "����֧������";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.nz == wang[3] || SiZhu.nz == wang[4]){
      _t += "ͨ����֧��";
      xs += SiZhu.sanziXS[3];
    }
    if(SiZhu.yz == wang[1] || SiZhu.yz == wang[2]) {
      _t += "����֧������";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.yz == wang[3] || SiZhu.yz == wang[4]) {
      _t += "ͨ����֧��";
      xs += SiZhu.sanziXS[3];
    }
    if(SiZhu.rz == wang[1] || SiZhu.rz == wang[2]) {
      _t += "����֧������";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.rz == wang[3] || SiZhu.rz == wang[4]) {
      _t += "ͨ����֧��";
      xs += SiZhu.sanziXS[3];
    }
    if(SiZhu.sz == wang[1] || SiZhu.sz == wang[2]) {
      _t += "��ʱ֧������";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.sz == wang[3] || SiZhu.sz == wang[4]){
      _t += "ͨ��ʱ֧��";
      xs += SiZhu.sanziXS[3];
    }
    if(_t.length()>1) {
      desc += name + _t;
      //1ֻΪ>0
      _tttt = name+_t;
    }

    return xs;
  }

  private void  _p(String str, double cent) {
    if(cent != 0)
      _desc += str+cent+"��";
  }
  private void  _p(String str, double cent, String str1, double cent1) {
    if(cent != 0)
      _desc += str+cent+","+str1+cent1+"��";
  }
  private void _p() {
    desc += "\r\n    ";
  }

  ////ת���ɰٷ��� x/2 40+(x-80)/2.5 60+(x-130)/4 75+(x-190)/17 90+(x-230)/5
  private void _getBaiFenZhi() {
    int[] cent = {SiZhu.muCent1,SiZhu.huoCent1,SiZhu.tuCent1,SiZhu.jinCent1,SiZhu.shuiCent1};
    for(int j=0; j<cent.length; j++) {
      for (int i = 0; i < SiZhu.baifen.length; i++) {
        if (cent[j] < SiZhu.judgeWS[i] || i==SiZhu.baifen.length-1) {
          //��x<80 x[0]=x[0]*x0/y0     ���� x = x0 + (x-y0)*(x1-x0)/(y1-y0)
          if(i==0)
            cent[j] = (cent[j] * SiZhu.baifen[0] /SiZhu.judgeWS[0]);
          else
            cent[j] = SiZhu.baifen[i-1]+(cent[j]-SiZhu.judgeWS[i-1])*(SiZhu.baifen[i]-SiZhu.baifen[i-1])/(SiZhu.judgeWS[i]-SiZhu.judgeWS[i-1]);
          break;
        }
      }
    }
    SiZhu.muCent = cent[0];
    SiZhu.huoCent = cent[1];
    SiZhu.tuCent = cent[2];
    SiZhu.jinCent = cent[3];
    SiZhu.shuiCent = cent[4];
  }

  /**
   * �õ����з���
   * ���ԭ��
  ֻ����������˥��������Ϊ100�֣�����(֧�����ȫ͸100*��4+4��=800��)����˥(20-100*0.8*7=-540)�֡�
  100������˥ƽ��㣬��Ȼ�����и���Է����۾���ǿ���������塣
  ��֧�໥�����˶Է����������档
  ͬ��Ϊ100����������80����������60����������40���ҿ�����20���Դ�Ϊ���������ʼ�֡�
  ���ᡢ���ϡ�����ϡ�������ϡ����Ͼ��Ժϻ�������������ϵ������ԭ���з������䡣
  ���ᡢ���ϡ�����ϡ�������ϡ����Ͼ��Ժϻ����������, ԭ����ʧȴ������;�϶�����, Ϊ��������, ��������������֧�������̳�.
  ���������岢�����������ۡ�
  �����ϲ����۰�ϣ����������ٳ�ɢ�⣬����������
  ��֧�����̺�
  ���������ϡ����ᡢ���ϲ�������Ҫ���ǡ�
  */
  public String getWuXingCent() {
    desc = "";
    _desc = "";
    _tttt = "";
    _huo = 0;
    _mu = 0;
    _tu = 0;
    _jin = 0 ;
    _shui = 0;

    int xs = 0;
    int len = 0;
    double _temp= 0;

    //1) ��֧���������
    //a) ����îľ��������������ˮ�ݡ��ļ���������ˮ��ľ�������ۡ�
    jin = SiZhu.jibenfen[YiJing.JIN][YiJing.DIZIWH[SiZhu.yz]];
    mu = SiZhu.jibenfen[YiJing.MU][YiJing.DIZIWH[SiZhu.yz]];
    shui = SiZhu.jibenfen[YiJing.SHUI][YiJing.DIZIWH[SiZhu.yz]];
    huo = SiZhu.jibenfen[YiJing.HUO][YiJing.DIZIWH[SiZhu.yz]];
    tu = SiZhu.jibenfen[YiJing.TU][YiJing.DIZIWH[SiZhu.yz]];

    _desc += "���л����֣�mu="+mu+";huo="+huo+";tu="+tu+";jin="+jin+";shui="+shui+";";

    //�õ�ȫ�����и���
    desc += "ȫ��"+pub.getHowWx(YiJing.MU)+YiJing.WUXINGNAME[YiJing.MU]+",";
    desc += pub.getHowWx(YiJing.HUO)+YiJing.WUXINGNAME[YiJing.HUO]+",";
    desc += pub.getHowWx(YiJing.TU)+YiJing.WUXINGNAME[YiJing.TU]+",";
    desc += pub.getHowWx(YiJing.JIN)+YiJing.WUXINGNAME[YiJing.JIN]+",";
    desc += pub.getHowWx(YiJing.SHUI)+YiJing.WUXINGNAME[YiJing.SHUI]+"��";
    _p();
    desc += "���"+pub.getGanTouZiCangDesc()+",";
    len = desc.length();

    //�õ�������и���,��һ�ȼ�Ϊ0.3��֧��
    if(pub.getHowWx(YiJing.MU, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.MU, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("��ɱȽ�ľ��", _temp);
      _mu += _temp;
    }
    if(pub.getHowWx(YiJing.HUO, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.HUO, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("��ɱȽٻ��", _temp);
      _huo += _temp;
    }
    if(pub.getHowWx(YiJing.TU, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.TU, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("��ɱȽ�����", _temp);
      _tu += _temp;
    }
    if(pub.getHowWx(YiJing.JIN, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.JIN, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("��ɱȽٽ��", _temp);
      _jin += _temp;
    }
    if(pub.getHowWx(YiJing.SHUI, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.SHUI, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("��ɱȽ�ˮ��", _temp);
      _shui += _temp;
    }


    //�õ���֧���и���,��һ�ȼ�Ϊ0.3��֧��
    //������ͬ�ĵ�֧�����Ƚټӷ֣�ֻ����͸����ɵ�ͨ�����ӷ֡�����͸���ԱȽ��ۡ�
    if(pub.getHowWx(YiJing.MU, 1) == 0 && pub.getHowWx(YiJing.MU, 2) > 1) {
        _temp = (pub.getHowWx(YiJing.MU, 2) - 1) * SiZhu.sanziXS[3] * 100;
        _p("��֧�Ƚ�ľ��", _temp);
        _mu += _temp;
    }
    if(pub.getHowWx(YiJing.HUO, 1) == 0 && pub.getHowWx(YiJing.HUO, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.HUO, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("��֧�Ƚٻ��", _temp);
      _huo += _temp;
    }
    if(pub.getHowWx(YiJing.TU, 1) == 0 && pub.getHowWx(YiJing.TU, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.TU, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("��֧�Ƚ�����", _temp);
      _tu += _temp;
    }
    if(pub.getHowWx(YiJing.JIN, 1) == 0 && pub.getHowWx(YiJing.JIN, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.JIN, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("��֧�Ƚٽ��", _temp);
      _jin += _temp;
    }
    if(pub.getHowWx(YiJing.SHUI, 1) == 0 && pub.getHowWx(YiJing.SHUI, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.SHUI, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("��֧�Ƚ�ˮ��", _temp);
      _shui += _temp;
    }


    //����ϵ����������ȽϹ�*100
    _temp = 100 * getFangOrHuiXS(1,"��î��","ľ");
    _p("������î����ľ�� ",_temp);
    _mu += _temp;
    _temp = 100 * getFangOrHuiXS(2,"����δ","��");
    _p("��������δ����� ",_temp);
    _huo += _temp;
    _temp = 100 * getFangOrHuiXS(3,"������","��");
    _p("���������磬��� ",_temp);
    _jin += _temp;
    _temp = 100 * getFangOrHuiXS(4,"���ӳ�","ˮ");
    _p("���ấ�ӳ�ˮ�� ",_temp);
    _shui += _temp;

    //b) ����-����֧��Ϊ����������������֧������î��(����壺����һ֧������-0.5��֧,-0.2��֧��-0.6��֧,-0.3��֧������һ֧��֧�������:-0.5��֧,-0.2��֧,0.1��֧��)
    //       -�����Ⱥ��壺����һ֧������-0.5��֧,-0.2��֧����-0.6��֧,-0.3��֧������һ֧��֧����֧-0.5��֧,-0.2��֧,0.1��֧
    //       -������壺����һ֧������0.5��֧,0.3��֧,��0.3��֧,0.2��֧������һ֧��֧����֧0.3��֧,0.2��֧,0.1��֧
    double[] _lc ;
    _lc = getDZLiuChong(1, 8, 7, "����","��ˮ","���","ˮ��");
    _shui += _lc[0] * Math.min(shui,huo);
    _huo += _lc[1] * Math.min(shui,huo);
    _p("������ˮ��ӷ֣�ˮ",_lc[0] * Math.min(shui,huo), "��",_lc[1] * Math.min(shui,huo));

    _lc = getDZLiuChong(4, 14, 40, "î��","îľ","�Ͻ�","��ľ");
    _mu += _lc[0] * Math.min(mu,jin);
    _jin += _lc[1] * Math.min(mu,jin);
    _p("î�ϳ��ˮ��ӷ֣�ľ",_lc[0] * Math.min(mu,jin), "��",_lc[1] * Math.min(mu,jin));

    _lc = getDZLiuChong(3,12,27,"����","��ľ","���","��ľ");
    _mu += _lc[0] * Math.min(mu,jin);
    _jin += _lc[1] * Math.min(mu,jin);
    _p("������ˮ��ӷ֣�ľ",_lc[0] * Math.min(mu,jin), "��",_lc[1] * Math.min(mu,jin));

    _lc = getDZLiuChong(6,18,72,"�Ⱥ�","�Ȼ�","��ˮ","ˮ��");
    _huo += _lc[0] * Math.min(shui,huo);
    _shui += _lc[1] * Math.min(shui,huo);
    _p("�Ⱥ�����ˮ�ӷ֣���",_lc[0] * Math.min(shui,huo), "ˮ",_lc[1] * Math.min(shui,huo));

    _lc = getDZLiuChong(5,16,55,"����","","","��");
    _tu += (_lc[0] + _lc[1]) * tu;
    _p("���������ӷ� ",(_lc[0] + _lc[1]) * tu);

    _lc = getDZLiuChong(2,10,16,"��δ","","","��");
    _tu += (_lc[0] + _lc[1]) * tu;
    _p("��δ�����ӷ� ",(_lc[0] + _lc[1]) * tu);


    //c) ����(������ϵ��-�����2.5��֧ �����2.0����֧)
    _temp = 100 * getFangOrHuiXS(5,"��îδ","ľ");
    _p("���Ϻ�îδ��ľ�� ",_temp);
    _mu += _temp;
    _temp = 100 * getFangOrHuiXS(6,"������","��");
    _p("���������磬��� ",_temp);
    _huo += _temp;
    _temp = 100 * getFangOrHuiXS(7,"���ϳ�","��");
    _p("�������ϳ󣬽�� ",_temp);
    _jin += _temp;
    _temp = 100 * getFangOrHuiXS(8,"���ӳ�","ˮ");
    _p("�������ӳ���ˮ�� ",_temp);
    _shui +=  _temp;
    if(desc.length()>len+10) {
      len = desc.length();
      _p();
    }

    // ��������� ����һ֧����֧1.3��֧��1.0��֧������һ֧��֧����֧1.0����֧��0.6����֧��0.2����֧
    // ���Ϸ������ ����һ֧����֧0.5��֧��0.3��֧������һ֧��֧����֧0.3����֧��0.2����֧��0.1����֧
   _temp = getBanHeXS(1,5,"��î","ľ") * Math.max(shui,mu);
   _p("��î���ľ���� ",_temp);
   _mu += _temp;
   _temp = getBanHeXS(2,5,"îδ","ľ") * Math.max(tu,mu);
   _p("îδ���ľ���� ",_temp);
   _mu += _temp;
   _temp = getBanHeXS(3,5,"��δ","ľ") * Math.max(shui,tu);
   _p("��δ���ľ���� ",_temp);
   _mu += _temp;
   _temp = getBanHeXS(1,6,"����","��") * Math.max(huo,mu);
   _p("�����ϻ𣬼� ",_temp);
   _huo += _temp;
   _temp = getBanHeXS(2,6,"����","��") * Math.max(huo,tu);
   _p("�����ϻ𣬼� ",_temp);
   _huo += _temp;
   _temp = getBanHeXS(3,6,"����","��") * Math.max(mu,tu);
   _p("�����ϻ𣬼� ",_temp);
   _huo += _temp;
   _temp = getBanHeXS(1,7,"����","��") * Math.max(huo,jin);
   _p("���ϰ�Ͻ𣬼� ",_temp);
   _jin += _temp;
   _temp = getBanHeXS(2,7,"�ϳ�","��") * Math.max(jin,tu);
   _p("�ϳ��Ͻ𣬼� ",_temp);
   _jin += _temp;
   _temp = getBanHeXS(3,7,"�ȳ�","��") * Math.max(huo,tu);
   _p("�ȳ��Ͻ𣬼� ",_temp);
   _jin += _temp;
   _temp = getBanHeXS(1,8,"����","ˮ") * Math.max(jin,shui);
   _p("���Ӱ��ˮ���� ",_temp);
   _shui += _temp;
   _temp = getBanHeXS(2,8,"�ӳ�","ˮ") * Math.max(shui,tu);
   _p("�ӳ����ˮ���� ",_temp);
   _shui += _temp;
   _temp = getBanHeXS(3,8,"�곽","ˮ") * Math.max(jin,tu);
   _p("�곽���ˮ���� ",_temp);
   _shui += _temp;

     //e) ���� �϶�����(����һ֧����֧1.2��֧��1.0��֧������һ֧��֧����֧1.0����֧��0.6����֧��0.2����֧)
    //         �ϻ�(����֧2.0��֧ ����֧1.5����֧)
    _temp = getLiuHe(1,5,"����","ľ") * Math.max(huo,shui);
    _p("���ɺ�ľ�� ",_temp);
    _mu += _temp;
    _temp = getLiuHe(1,6,"���","��") * Math.max(tu,shui);
    _p("���ϻ�� ",_temp);
    _huo += _temp;
    _temp = getLiuHe(1,7,"�Ҹ�","��") * Math.max(mu,jin);
    _p("�Ҹ��Ͻ�� ",_temp);
    _jin += _temp;
    _temp = getLiuHe(1,8,"����","ˮ") * Math.max(huo,jin);
    _p("������ˮ�� ",_temp);
    _shui += _temp;
    _temp = getLiuHe(1,9,"�׼�","��") * Math.max(mu,tu);
    _p("�׼������� ",_temp);
    _tu += _temp;
    _temp = getLiuHe(2,5,"����","ľ") * Math.max(mu,shui);
    _p("�������ľ�� ",_temp);
    _mu += _temp;
    _temp = getLiuHe(2,6,"î��","��") * Math.max(mu,tu);
    _p("î��ϻ�� ",_temp);
    _huo += _temp;
    _temp = getLiuHe(2,7,"����","��") * Math.max(tu,jin);
    _p("���ϺϽ�� ",_temp);
    _jin += _temp;
    _temp = getLiuHe(2,8,"����","ˮ") * Math.max(huo,jin);
    _p("�����ˮ�� ",_temp);
    _shui += _temp;
    _temp = getLiuHe(2,9,"�ӳ�","��") * Math.max(shui,tu);
    _p("�ӳ������ ",_temp);
    _tu += _temp;
    _temp = getLiuHe(2,10,"��δ","��") * Math.max(huo,tu);
    _p("��δ������ ",_temp);
    _tu += _temp;

    //l) �຦  -0.1��֧
    _temp = getLiuHai(1,8,"��δ�຦");
    _tu += _temp * tu;
    _shui += _temp * shui;
    _p("��δ�຦��ˮ�� ", _temp * shui,"���� ",_temp * tu);
    _temp = getLiuHai(2,7,"�����຦");
    _tu += _temp * tu;
    _huo += _temp * huo;
    _p("�����຦����� ", _temp * huo,"���� ",_temp * tu);
    _temp = getLiuHai(3,6,"�����຦");
    _mu += _temp * mu;
    _huo += _temp * huo;
    _p("�����຦��ľ�� ", _temp * mu,"��� ",_temp * huo);
    _temp = getLiuHai(4,5,"î���຦");
    _tu += _temp * tu;
    _mu += _temp * mu;
    _p("î���຦��ľ�� ", _temp * mu,"���� ",_temp * tu);
    _temp = getLiuHai(9,11,"�����຦");
    _tu += _temp * tu;
    _jin += _temp * jin;
    _p("�����຦����� ", _temp * jin,"���� ",_temp * tu);
    _temp = getLiuHai(10,12,"�Ϻ��຦");
    _jin += _temp * jin;
    _shui += _temp * shui;
    _p("�Ϻ��຦��ˮ�� ", _temp * shui,"��� ",_temp * jin);

    //m) ���� -0.1��֧
    getSanXing();

    if(desc.length()>len+10) {
      len = desc.length();
      _p();
    }

    //g) ���� ���߲�����,ֻ�����ƣ���ʵû����������һ֧��֧0.3��֧��0.2��֧��0.1��֧
    //i) ��� ���߲�������ֻ��һ����в(��δ���ˮ����-0.3,������0.1)������һ֧��֧-0.3��֧��-0.2��֧��-0.1��֧
    //j) ���� ��î�갵������٣�������ϵ����0.2��֧,�⼴��������
    _temp = (getShengKeXS(YiJing.MU,1) + getShengKeXS(YiJing.MU,2)) * mu;
    _mu += _temp;
    _p("ľ��֧Զ������Ӱ��ӷ� ",_temp);
    _temp = (getShengKeXS(YiJing.TU,1) + getShengKeXS(YiJing.TU,2)) * tu;
    _tu += _temp;
    _p("����֧Զ������Ӱ��ӷ� ",_temp);
    _temp = (getShengKeXS(YiJing.HUO,1) + getShengKeXS(YiJing.HUO,2)) * huo;
    _huo += _temp;
    _p("���֧Զ������Ӱ��ӷ� ",_temp);
    _temp = (getShengKeXS(YiJing.JIN,1) + getShengKeXS(YiJing.JIN,2)) * jin;
    //System.out.println(getShengKeXS(YiJing.JIN,1) +":"+ getShengKeXS(YiJing.JIN,2));
    _jin += _temp;
    _p("���֧Զ������Ӱ��ӷ� ",_temp);
    _temp = (getShengKeXS(YiJing.SHUI,1) + getShengKeXS(YiJing.SHUI,2)) * shui;
    _shui += _temp;
    _p("ˮ��֧Զ������Ӱ��ӷ� ",_temp);

    //�츲(�������֮): 0.1*�˸ɷ��� ����: 0.1��֧������
    //��ˣ�-0.1*�˸ɷ��� �ؿˣ�-0.1��֧������
    //��ϵغϻ����У�ֻ�����ӡ����ȡ��������������գ���0.1*��֧����
    getGZRelate();

    //����������3֧��һĹ����ľĹ��δ������ 0.35��֧������
    //����������3֧��һ������ľ�����ڳ����� 0.7��֧������
    //����������3֧������»��              1.0��֧������
    getMuYuWang();

    //�ܷ֣��൱������͸���߼ӷ֣���͸�߲��ӣ���ɼӵ�֧��Ϊ�ܷ֣�
    if(pub.getHowWx(YiJing.MU) == 0) {
      SiZhu.muCent1 = 0;
    }else {
      SiZhu.muCent1 = (int)(_mu + mu);
    }

    if(pub.getHowWx(YiJing.HUO) == 0) {
      SiZhu.huoCent1 = 0;
    }else {
      SiZhu.huoCent1 = (int)(_huo+huo);
    }

    if(pub.getHowWx(YiJing.TU) == 0) {
      SiZhu.tuCent1 = 0;
    }else {
      SiZhu.tuCent1 = (int)(_tu+tu);
    }

    if(pub.getHowWx(YiJing.SHUI) == 0) {
      SiZhu.shuiCent1 = 0;
    }else {
      SiZhu.shuiCent1 = (int)(_shui+shui);
    }

    if(pub.getHowWx(YiJing.JIN) == 0) {
      SiZhu.jinCent1 = 0;
    }else {
      SiZhu.jinCent1 = (int)(_jin+jin);
    }

    ////ת���ɰٷ��� x/2 40+(x-80)/2.5 60+(x-130)/4 75+(x-190)/17 90+(x-230)/5
    _getBaiFenZhi();

    _p();
    int x0 = pub.getShiShenCent(0);
    int x1 = pub.getShiShenCent(1);
    int x2 = pub.getShiShenCent(2);
    int x3 = pub.getShiShenCent(3);
    int x4 = pub.getShiShenCent(4);
    desc += "���ۺ�Զ������Ӱ�죬���϶����ո�"+x0+"��"+pub.getShiShenWSDesc(0)+",";
    if(x1 >0)
      desc += "ʳ��"+x1+"��"+pub.getShiShenWSDesc(1)+"��";
    if(x2 >0)
      desc += "����"+x2+"��"+pub.getShiShenWSDesc(2)+"��";
    if(x3 >0)
      desc += "��ɱ"+x3+"��"+pub.getShiShenWSDesc(3)+"��";
    if(x4 >0)
      desc += "ӡ��"+x4+"��"+pub.getShiShenWSDesc(4)+"��";
    //Debug.out("\r\n\r\nľ="+SiZhu.muCent1+"����="+SiZhu.huoCent1+"����="+SiZhu.tuCent1+"����="+SiZhu.jinCent1+"��ˮ="+SiZhu.shuiCent1+"��");
    //Debug.out("\r\n"+_desc);

    return desc;
  }

}
