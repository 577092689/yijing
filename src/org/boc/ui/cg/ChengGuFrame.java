package org.boc.ui.cg;

import org.boc.delegate.DelSiZhuMain;
import org.boc.ui.sz.SiZhuFrame;
import org.boc.util.Public;

public class ChengGuFrame
    extends SiZhuFrame {
  private DelSiZhuMain dels ;
  private int isheng=0, ishi=0;
  public ChengGuFrame() {
    super();
    //�������ø�Ŀ¼
    dels = new DelSiZhuMain();
    this.setRoot(Public.valueRoot[10]);
  }

  /**
   * ���ظ����������������Ϣ
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getChengGuInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getChengGuInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  public String do2() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do3() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do4() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do5() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do6() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do7() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}

}
