package org.boc.ui.zw;

import org.boc.delegate.DelZiWeiMain;
import org.boc.ui.sz.SiZhuFrame;
import org.boc.util.Public;

public class ZiWeiFrame
    extends SiZhuFrame {
	private int isheng=0, ishi=0;
  private DelZiWeiMain zw;
  public ZiWeiFrame() {
    super();
    //�������ø�Ŀ¼ ��΢��3
    zw = new DelZiWeiMain();
    this.setRoot(Public.valueRoot[3]);
  }

  /**
   * ���ظ����������������Ϣ
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return zw.getMingYun(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return zw.getMingYun(year,month,day,hour,minute,isYin, isYun, isheng, ishi, isBoy);
  }

  public String do2() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do3() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do4() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do5() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do6() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do7() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}

}
