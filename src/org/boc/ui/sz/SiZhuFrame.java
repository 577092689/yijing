package org.boc.ui.sz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.delegate.DelSiZhuMain;
import org.boc.delegate.DelYiJingMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VO;

public class SiZhuFrame
    extends BasicJPanel {
  private JCheckBox checkboxYun; //�Ƿ�����
  private JTextField textYear;   //��
  private JTextField textMonth;  //��
  private JTextField textDay;    //��
  private JTextField textHour;   //ʱ����
  public boolean isYun;         //�Ƿ�������
  public boolean isYin;         //�Ƿ�������
  public boolean isBoy;         //�Ƿ����к�

  private ButtonGroup groupYYli;     //������
  private ButtonGroup groupNanLv;    //�л�Ů
  private ButtonGroup groupType;    //���ڻ����

  private JRadioButton radioBazi;     //�������
  private JRadioButton radioShijian;  //����ʱ��
  private JRadioButton radioYin;      //����
  private JRadioButton radioYang;     //����
  private JRadioButton radioBoy;      //��
  private JRadioButton radioGirl;     //Ů
  private int isheng=0, ishi=0;

  public int ng,nz,yg,yz,rg,rz,sg,sz;
  public int year,month,day,hour,minute,second;

  private DelSiZhuMain dels;
  private DelYiJingMain dely ;
  private DaoPublic pub ;
  private DaoCalendar c;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VO vo;
  private String yText, mText, dText, hText;

  public SiZhuFrame() {
    this.setLayout(new BorderLayout());
    pub = new DaoPublic();
    c = new DaoCalendar();
    dels = new DelSiZhuMain();
    dely = new DelYiJingMain();
    isheng = -1;
    ishi = -1;
    this.setRoot(Public.valueRoot[5]);
  }

  public void finalize() {
    pub = null;
    c = null;
    dels = null;
    dely = null;
  }

  public void init(String fileId, String rowId, String parentNode1) {
    vo = (VO)Public.getObjectFromFile(fileId, rowId);
    //���ö���������
    if (vo != null) {
      isYun = vo.isIsYun();
      isBoy = vo.isIsBoy();
      isYin = vo.isIsYin();

      isYun = vo.isIsYin();
      isBoy = vo.isIsBoy();
      isYin = vo.isIsYin();

      ng = vo.getNg();
      nz = vo.getNz();
      yg = vo.getYg();
      yz = vo.getYz();
      rg = vo.getRg();
      rz = vo.getRz();
      sg = vo.getSg();
      sz = vo.getSz();

      year = vo.getYear();
      month = vo.getMonth();
      day = vo.getDay();
      hour = vo.getHour() ;
      minute = vo.getMinute();

      if(year==0) {
        yText = ng+","+nz;
        mText = yg + "," + yz;
        dText = rg + "," + rz;
        hText = sg + "," + sz;
      }else {
        yText = ""+year;
        mText = ""+ month;
        dText = ""+ day;
        hText = hour+":"+minute+":00";
      }
      isheng = vo.getIsheng();
      ishi = vo.getIshi();
      memo = vo.getMemo();
    }
    this.fileId = fileId;
    this.rowId = rowId;
    if(parentNode1==null) {
      if(vo!=null)
        parentNode1=vo.getParent();
    }
    this.parentNode = parentNode1;

    //1. ¼���Ϊ3����壬������
    this.add(getInputPanel(), BorderLayout.NORTH);
    //2. �ı���
    this.add(getOtherInfoPane(getSaveJButton(), memo), BorderLayout.CENTER);
  }

  public JButton getSaveJButton() {
    CommandAction actionXX = new CommandAction("����", null, "", ' ',
                                               new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String type = _check();
        if(type==null)
          return;
        if(!type.equals("shijian"))
          vo = new VO(ng,nz,yg,yz,rg,rz,sg,sz,isBoy,true, isYun, isheng, ishi);
        else
          vo = new VO(year,month,day,hour,minute,isBoy,isYin, isYun, isheng, ishi);

        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //��ȡ��Ŀ¼�� һ��ȡԤ�����ĸ�Ŀ¼����ȡroot��ֵ
        vo.setRoot(getRoot());  //�˴���������ֵ
        vo.setParent(parentNode);
        vo.setYcsj(Public.getTimestampValue().toString());
        vo.setMemo(memo);
        Public.writeObject2File(vo);
        JOptionPane.showMessageDialog(getThis(), "����ɹ���", "��ʾ��Ϣ",
                                      JOptionPane.INFORMATION_MESSAGE);
        clear();
      }
    });
    JButton buttonXX = new JButton(actionXX);
    return buttonXX;
  }

  /**
   * ���������Ϣ
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getMingYun(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getMingYun(year,month,day,hour,minute,isYin, isYun, isheng, ishi, isBoy);
  }

  /**
   * �����������Ϣ
   */
  public String do2() {
    if (vo == null)return "";
    String str = "";

    str += "\r\n    ������������ԣ�\r\n";
    int upGua = (SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1)%8==0?8:(SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1)%8;
    int downGua = (upGua + SiZhu.sz)%8==0?8:(upGua + SiZhu.sz)%8;
    int change = (SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6==0?6:(SiZhu.ng+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6;
    str += dely.getGuaXiang( upGua, downGua, new int[]{change},
                             new int[]{0,SiZhu.ng,SiZhu.nz,SiZhu.yg,SiZhu.yz,SiZhu.rg,SiZhu.rz,SiZhu.sg,SiZhu.sz},0);

    str += "\r\n    ����֧�������ԣ�\r\n";
    upGua = (SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1)%8==0?8:(SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1)%8;
    downGua = (upGua + SiZhu.sz)%8==0?8:(upGua + SiZhu.sz)%8;
    change = (SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6==0?6:(SiZhu.nz+Calendar.MONTHN1 + Calendar.DAYN1+SiZhu.sz)%6;
    str += dely.getGuaXiang( upGua, downGua, new int[]{change},
                             new int[]{0,SiZhu.ng,SiZhu.nz,SiZhu.yg,SiZhu.yz,SiZhu.rg,SiZhu.rz,SiZhu.sg,SiZhu.sz},0);

    return str;
  }

  /**
   * ��������Ϣ
   */
  public String do3() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getGlobleInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getGlobleInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  /**
   * �����ҵ��Ϣ
   */

  /**
   * ���������Ϣ
   */
  public String do5() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getHunYinInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getHunYinInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  /**
   * ���������Ϣ
   */

  /**
   * ����Ը���Ϣ
   */
  public String do7() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getXingGeInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getXingGeInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  /**
   * ���������Ϣ
   */

  /**
   * �ɶ���������¼�����
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(getUpPanel());
    inputPanel.add(getCenterPanel());

    return inputPanel;
  }

  /**
   * �õ���һ�ŵ����
   * @return
   */
  private Box getUpPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    radioShijian = new JRadioButton("ʱ��",true);
    radioShijian.setActionCommand("shijian");
    radioBazi = new JRadioButton("����");
    radioBazi.setActionCommand("bazi");

    if (this.isYin) {
      radioYin = new JRadioButton("����", true);
      radioYang = new JRadioButton("����");
    }
    else {
      radioYin = new JRadioButton("����");
      radioYang = new JRadioButton("����", true);
    }
    radioYin.setActionCommand("yinli");
    radioYang.setActionCommand("yangli");
    if (this.isBoy) {
      radioBoy = new JRadioButton("��", true);
      radioGirl = new JRadioButton("Ů");
    }
    else {
      radioBoy = new JRadioButton("��");
      radioGirl = new JRadioButton("Ů", true);
    }
    radioBoy.setActionCommand("nan");
    radioGirl.setActionCommand("lv");

    box.add(new JLabel("���ͣ�"));
    groupType = new ButtonGroup();
    groupType.add(radioShijian);
    groupType.add(radioBazi);
    box.add(radioShijian);
    box.add(radioBazi);
    box.add(new JLabel("    "));
    box.add(new JLabel("������"));
    groupYYli = new ButtonGroup();
    groupYYli.add(radioYin);
    groupYYli.add(radioYang);
    box.add(radioYin);
    box.add(radioYang);
    box.add(new JLabel("     "));
    box.add(new JLabel("�Ա�"));
    groupNanLv = new ButtonGroup();
    groupNanLv.add(radioBoy);
    groupNanLv.add(radioGirl);
    box.add(radioBoy);
    box.add(radioGirl);
    box.add(new JLabel("    "));
    box.add(new JLabel("�����أ�"));
    //box.add(this.getComboShengShi());

    return box;
  }

  /**
   * �õ��ڶ��ŵ����
   * @return
   */
  private Box getCenterPanel() {
    Box box = new Box(BoxLayout.X_AXIS);


    JLabel labelYear = new JLabel("��");
    box.add(labelYear);
    textYear = new JTextField(4);
    textYear.setText(yText);
    box.add(textYear);
    box.add(new JLabel("    "));

    checkboxYun = new JCheckBox("��", isYun);
    box.add(checkboxYun);

    JLabel labelMonth = new JLabel("��");
    box.add(labelMonth);
    textMonth = new JTextField(2);
    textMonth.setText(mText);
    box.add(textMonth);
    box.add(new JLabel("    "));

    JLabel labelDay = new JLabel("��");
    box.add(labelDay);
    textDay = new JTextField(2);
    textDay.setText(dText);
    box.add(textDay);
    box.add(new JLabel("    "));

    JLabel labelHour = new JLabel("ʱ");
    box.add(labelHour);
    textHour = new JTextField(8);
    textHour.setText(hText);
    box.add(textHour);
    box.add(new JLabel("    "));

    return box;
  }

  private String _check() {
    String type = groupType.getSelection().getActionCommand();
    String rili = groupYYli.getSelection().getActionCommand();
    String xingbie = groupNanLv.getSelection().getActionCommand();
    String y = textYear.getText();
    String m = textMonth.getText();
    String d = textDay.getText();
    String h = textHour.getText();

    isYun = checkboxYun.isSelected();
    isYin = rili.equals("yinli")?true:false;
    isBoy = xingbie.equals("nan")?true:false;

    memo = getJTextArea().getText();

    String checks = checkInputs(y,m,d,h,type.equals("shijian"));
    if(checks != null) {
      JOptionPane.showMessageDialog(getThis(), checks, "��ʾ��Ϣ",JOptionPane.INFORMATION_MESSAGE);
      return null;
    }
    getParameters(type, y, m, d, h);

    return type;
  }

  private void clear() {
    Calendar.YEARN = 0;
    Calendar.YEARP = 0;
    Calendar.MONTHN = 0;
    Calendar.MONTHP = 0;
    Calendar.DAYN = 0;
    Calendar.DAYP = 0;
    Calendar.HOUR = 0;
  }

  protected Component getThis() {
    return this;
  }

  /**
   * �õ��������
   */
  private void getParameters(String type, String _y, String _m,
                             String _d, String hms) {
    if (type.equals("shijian")) {
      year = _y.equals("") ? 0 : Integer.valueOf(_y).intValue();
    }
    else {
      year = 0;
      String _y1 = _y.substring(0, _y.indexOf(","));
      String _y2 = _y.substring(_y.indexOf(",") + 1);
      ng = Integer.valueOf(_y1).intValue();
      nz = Integer.valueOf(_y2).intValue();
    }

    if (type.equals("shijian")) {
      month = _m.equals("") ? 0 : Integer.valueOf(_m).intValue();
    }
    else {
      month = 0;
      String _y1 = _m.substring(0, _m.indexOf(","));
      String _y2 = _m.substring(_m.indexOf(",") + 1);
      yg = Integer.valueOf(_y1).intValue();
      yz = Integer.valueOf(_y2).intValue();
    }

    if (type.equals("shijian")) {
      day = _d.equals("") ? 0 : Integer.valueOf(_d).intValue();
    }
    else {
      day = 0;
      String _y1 = _d.substring(0, _d.indexOf(","));
      String _y2 = _d.substring(_d.indexOf(",") + 1);
      rg = Integer.valueOf(_y1).intValue();
      rz = Integer.valueOf(_y2).intValue();
    }

    if (type.equals("shijian")) {
      String[] h = hms.split(":");
      if (h.length == 1) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = 0;
        second = 0;
      }
      if (h.length == 2) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        second = 0;
      }
      if (h.length == 3) {
        hour = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
        minute = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
        second = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
      }
    }
    else {
      hour = 0;
      minute = 0;
      second = 0;
      String _y1 = hms.substring(0, hms.indexOf(","));
      String _y2 = hms.substring(hms.indexOf(",") + 1);
      sg = Integer.valueOf(_y1).intValue();
      sz = Integer.valueOf(_y2).intValue();
    }

    if (!type.equals("shijian")) {
      SiZhu.yinli = "";
      SiZhu.yangli = "";
    }
  }

  /**
   * ������������ĸ�ʽ�Ƿ���ȷ
   * @return
   */
  private String checkInputs(String y, String m,String d, String h, boolean is) {
    return pub.checks(y,m,d,h,isYin,isYun, is);
  }
}
