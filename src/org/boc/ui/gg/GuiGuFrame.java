package org.boc.ui.gg;

import org.boc.delegate.DelSiZhuMain;
import org.boc.ui.sz.SiZhuFrame;
import org.boc.util.Public;

public class GuiGuFrame
    extends SiZhuFrame {
  private DelSiZhuMain dels ;
  private int isheng=0, ishi=0;
  public GuiGuFrame() {
  	
    super();
    //�������ø�Ŀ¼
    dels = new DelSiZhuMain();
    this.setRoot(Public.valueRoot[11]);
  }

  /**
   * ���ظ����������������Ϣ
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getGuiGuInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getGuiGuInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  public String do2() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do3() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do4() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do5() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do6() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do7() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}

}
