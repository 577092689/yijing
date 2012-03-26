package org.boc.ui.lr;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.db.Calendar;
import org.boc.delegate.DelLiurenMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VoLiuRen;

public class LiuRenFrame
    extends BasicJPanel {

  private DelLiurenMain del;
  private DaoPublic pub;
  private DaoCalendar c;
  private String str;

  private JCheckBox checkboxYun; //�Ƿ�����
  private JCheckBox checkDang; //�Ƿ�ǰʱ�����
  private JTextField textBorn; //������
  private JTextField textYear; //��
  private JTextField textMonth; //��
  private JTextField textDay; //��
  private JTextField textHour; //ʱ����
  private boolean isYun; //�Ƿ�������
  private boolean isYin; //�Ƿ�������
  private boolean isBoy; //�Ƿ����к�

  private ButtonGroup groupYYli; //������
  private ButtonGroup groupNanLv; //�л�Ů
  private JRadioButton radioYin; //����
  private JRadioButton radioYang; //����
  private JRadioButton radioBoy; //��
  private JRadioButton radioGirl; //Ů

  private JComboBox comboGr; //����
  private JComboBox comboZy; //��ҹ
  private JComboBox comboDg; //�ݸ�
  private JComboBox comboYj; //�½�

  private int iGr; //����
  private int iZy; //��ҹ
  private int iDg; //�ݸ�
  private int iYj; //�½�
  private int iyb; //������
  private int iyy; //�����
  private int imm; //��
  private int idd; //��
  private int ihh; //ʱ
  private int imi; //��
  private int iss; //��

  private int bg;
  private int bz;
  private int yg;
  private int yz;
  private int mg;
  private int mz;
  private int dg;
  private int dz;
  private int hg;
  private int hz;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  private VoLiuRen vo;
  private String bText, yText, mText, dText, hText;

  public LiuRenFrame() {
    this.setLayout(new BorderLayout());
    pub = new DaoPublic();
    c = new DaoCalendar();
    del = new DelLiurenMain();
  }

  public void init(String fileId, String rowId, String parentNode1) {
    vo = (VoLiuRen) Public.getObjectFromFile(fileId, rowId);
    if (vo != null) {
      isYun = vo.isIsYun();
      isBoy = vo.isIsBoy();
      isYin = vo.isIsYin();

      iGr = vo.getIGr();
      iZy = vo.getIZy();
      iDg = vo.getIDg();
      iYj = vo.getIYj();

      bg = vo.getBg();
      bz = vo.getBz();
      yg = vo.getNg();
      yz = vo.getNz();
      mg = vo.getYg();
      mz = vo.getYz();
      dg = vo.getRg();
      dz = vo.getRz();
      hg = vo.getSg();
      hz = vo.getSz();

      iyb = vo.getIyb();
      iyy = vo.getYear();
      imm = vo.getMonth();
      idd = vo.getDay();
      ihh = vo.getHour();
      imi = vo.getMinute();
      iss = vo.getSecond();

      if (iyb == 0) {
        bText = bg + "," + bz;
      }
      else {
        bText = "" + iyb;
      }
      if (iyy == 0) {
        yText = yg + "," + yz;
        mText = mg + "," + mz;
        dText = dg + "," + dz;
        hText = hg + "," + hz;
      }
      else {
        yText = "" + iyy;
        mText = "" + imm;
        dText = "" + idd;
        hText = ihh + ":" + imi + ":00";
      }
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
    this.add(getOtherInfoPane(getSaveJButton(), memo),BorderLayout.CENTER);
    //setEnable(false);
  }

  private JButton getSaveJButton() {
    CommandAction actionQk = new CommandAction("����", null, "", ' ',
                                               new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String rs = check();
        if (rs != null) {
          JOptionPane.showMessageDialog(getThis(), rs, "��ʾ��Ϣ",
                                        JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        getInputs();
        if (checkDang.isSelected()) {
          java.util.Date d = new java.util.Date();
          iyy = d.getYear() + 1900;
          imm = d.getMonth() + 1;
          idd = d.getDate();
          ihh = d.getHours();
          imi = d.getMinutes();
          iss = d.getSeconds();
          if (iyb == 0)
            iyb = pub.getYear(bg, bz);
          vo = new VoLiuRen(iyb, iyy, imm, idd, ihh, imi, iss, false, isBoy,
                            false, iGr, iZy, iDg, iYj);
        }
        else if (textYear.getText().indexOf(",") > -1) {
          if (textBorn.getText().indexOf(",") == -1) {
            bg = (iyb - Calendar.IYEAR + Calendar.IYEARG) % 10 == 0 ? 10 :
                (iyb - Calendar.IYEAR + Calendar.IYEARG) % 10;
            bz = (iyb - Calendar.IYEAR + Calendar.IYEARZ) % 12 == 0 ? 12 :
                (iyb - Calendar.IYEAR + Calendar.IYEARZ) % 12;
          }
          vo = new VoLiuRen(bg, bz, yg, yz, mg, mz, dg, dz, hg, hz, isYin,
                            isBoy, isYun, iGr, iZy, iDg, iYj);
        }
        else {
          vo = new VoLiuRen(iyb, iyy, imm, idd, ihh, imi, iss, isYin, isBoy,
                            isYun, iGr, iZy, iDg, iYj);
        }
        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //��ȡ��Ŀ¼�� һ��ȡԤ�����ĸ�Ŀ¼����ȡroot��ֵ
        vo.setRoot(Public.valueRoot[2]);  //�˴������ɵ�ֵ
        vo.setParent(parentNode);
        vo.setYcsj(Public.getTimestampValue().toString());
        vo.setMemo(memo);
        Public.writeObject2File(vo);
        JOptionPane.showMessageDialog(getThis(), "����ɹ���", "��ʾ��Ϣ",
                                      JOptionPane.INFORMATION_MESSAGE);
        clear();
      }
    });
    JButton buttonQK = new JButton(actionQk);
    return buttonQK;
  }

  /**
   * ���������Ϣ
   * @return JPanel
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getNg() == 0)
      return del.getInfo(iyb, iyy, imm, idd, ihh, imi, iss, true, isBoy,
                         false, iGr, iZy, iDg, iYj);
    return del.getInfo(bg, bz, yg, yz, mg, mz, dg, dz, hg, hz, isYin,
                            isBoy, isYun, iGr,iZy,iDg,iYj);
  }

  /**
   * ������������¼�����
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(getUpPanel());
    inputPanel.add(getDownPanel());

    return inputPanel;
  }

  private Box getUpPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    box.add(new JLabel(" "));
    box.add(new JLabel("���� "));
    textBorn = new JTextField(4);
    textBorn.setText(bText);
    box.add(textBorn);
    box.add(new JLabel("  "));

    if (this.isYin) {
      radioYin = new JRadioButton("����", true);
      radioYang = new JRadioButton("����");
    }
    else {
      radioYin = new JRadioButton("����");
      radioYang = new JRadioButton("����", true);
    }
    radioYin.setActionCommand("yin");
    radioYang.setActionCommand("yang");
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

    box.add(new JLabel("�Ա� "));
    groupNanLv = new ButtonGroup();
    groupNanLv.add(radioBoy);
    groupNanLv.add(radioGirl);
    box.add(radioBoy);
    box.add(radioGirl);
    box.add(new JLabel("      "));

    box.add(new JLabel("��Σ�"));
    checkDang = new JCheckBox("��ǰ", false);
    class TimeActionListener
        implements ActionListener {
      public void actionPerformed(ActionEvent ev) {
        if (checkDang.isSelected()) {
          setEnable(false);
        }
        else {
          setEnable(true);
        }
      }
    }

    ActionListener tlisten = new TimeActionListener();
    checkDang.addActionListener(tlisten);
    box.add(checkDang);
    box.add(new JLabel("  �� "));
    textYear = new JTextField(4);
    textYear.setText(yText);
    box.add(textYear);
    box.add(new JLabel("��"));
    checkboxYun = new JCheckBox("��", isYun);
    box.add(checkboxYun);
    textMonth = new JTextField(3);
    textMonth.setText(mText);
    box.add(textMonth);
    box.add(new JLabel("��"));
    textDay = new JTextField(3);
    textDay.setText(dText);
    box.add(textDay);
    box.add(new JLabel("��"));
    textHour = new JTextField(8);
    textHour.setText(hText);
    box.add(textHour);
    box.add(new JLabel("ʱ  "));
    groupYYli = new ButtonGroup();
    groupYYli.add(radioYin);
    groupYYli.add(radioYang);
    box.add(radioYin);
    box.add(radioYang);
    box.add(new JLabel(" "));

    return box;
  }

  private void setEnable(boolean bl) {
    textYear.setEnabled(bl);
    textMonth.setEnabled(bl);
    textDay.setEnabled(bl);
    textHour.setEnabled(bl);
    checkboxYun.setEnabled(bl);
    radioYin.setEnabled(bl);
    radioYang.setEnabled(bl);
  }

  private Box getDownPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    box.add(new JLabel(" "));
    box.add(new JLabel("���� "));
    box.add(this.getGuiren());
    box.add(new JLabel("  "));
    box.add(new JLabel("��ҹ "));
    box.add(this.getZouye());
    box.add(new JLabel("  "));
    box.add(new JLabel("�ݸ� "));
    box.add(this.getDungan());
    box.add(new JLabel("  "));
    box.add(new JLabel("�½� "));
    box.add(this.getYueJiang());
    box.add(new JLabel(" "));

    return box;
  }

  private String check() {
    if (!checkDang.isSelected()) {
      isYun = checkboxYun.isSelected();
      String rili = groupYYli.getSelection().getActionCommand();
      isYin = rili.equals("yin");
      String y = textYear.getText();
      String m = textMonth.getText();
      String d = textDay.getText();
      String h = textHour.getText();
      String rs = pub.checks(y, m, d, h, isYin, isYun);
      if (rs != null)
        return rs;
    }

    String txt = textBorn.getText().trim();
    if (txt.equals("")) {
      return "��Ԥ���˳�����ݲ���Ϊ�գ�";
    }
    if (txt.indexOf(",") == -1) {
      try {
        int i = Integer.valueOf(txt).intValue();
      }
      catch (Exception e) {
        return "��Ԥ���˳������ֻ��������";
      }
      if (Integer.valueOf(txt).intValue() > Calendar.MAXYEAR ||
          Integer.valueOf(txt).intValue() < Calendar.IYEAR) {
        return "��Ԥ���˳�����ݱ�����" + Calendar.IYEAR + "��" + Calendar.MAXYEAR + "֮��";
      }
    }

    return null;
  }

  private void getInputs() {
    isYun = checkboxYun.isSelected();
    String rili = groupYYli.getSelection().getActionCommand();
    isYin = rili.equals("yin");
    String nanlv = groupNanLv.getSelection().getActionCommand();
    isBoy = nanlv.equals("nan");
    memo = getJTextArea().getText();

    if (textBorn.getText().indexOf(",") > -1)
      getBornGz();
    else
      getBornSj();
    if (!checkDang.isSelected())
      if (textYear.getText().indexOf(",") > -1)
        getGanzi();
      else
        getShijian();

  }

  private void getBornGz() {
    String[] s = textBorn.getText().split(",");
    bg = Integer.valueOf(s[0]).intValue();
    bz = Integer.valueOf(s[1]).intValue();
  }

  private void getBornSj() {
    iyb = textBorn.getText().equals("") ? 0 :
        Integer.valueOf(textBorn.getText()).intValue();
  }

  private void getGanzi() {
    String[] s = textYear.getText().split(",");
    yg = Integer.valueOf(s[0]).intValue();
    yz = Integer.valueOf(s[1]).intValue();

    s = textMonth.getText().split(",");
    mg = Integer.valueOf(s[0]).intValue();
    mz = Integer.valueOf(s[1]).intValue();

    s = textDay.getText().split(",");
    dg = Integer.valueOf(s[0]).intValue();
    dz = Integer.valueOf(s[1]).intValue();

    s = textHour.getText().split(",");
    hg = Integer.valueOf(s[0]).intValue();
    hz = Integer.valueOf(s[1]).intValue();
  }

  private void getShijian() {
    iyy = textYear.getText().equals("") ? 0 :
        Integer.valueOf(textYear.getText()).intValue();
    imm = textMonth.getText().equals("") ? 0 :
        Integer.valueOf(textMonth.getText()).intValue();
    idd = textDay.getText().equals("") ? 0 :
        Integer.valueOf(textDay.getText()).intValue();
    String[] h = (textHour.getText()).split(":");
    if (h.length == 1) {
      ihh = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      imi = 0;
      iss = 0;
    }
    if (h.length == 2) {
      ihh = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      imi = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
      iss = 0;
    }
    if (h.length == 3) {
      ihh = h[0] == null ? 0 : Integer.valueOf(h[0]).intValue();
      imi = h[1] == null ? 0 : Integer.valueOf(h[1]).intValue();
      iss = h[2] == null ? 0 : Integer.valueOf(h[2]).intValue();
    }
  }

  private void clear() {
    str = null;
  }

  protected Component getThis() {
    return this;
  }

  private Box getGuiren() {
    Box box = new Box(BoxLayout.X_AXIS);
    comboGr = new JComboBox(new String[] {"�������ţ", "�����ţ��", "������ճ�"});
    comboGr.setSelectedIndex(iGr);
    comboGr.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iGr = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboGr);

    return box;
  }

  private Box getZouye() {
    Box box = new Box(BoxLayout.X_AXIS);
    comboZy = new JComboBox(new String[] {"����", "����"});
    comboZy.setSelectedIndex(iZy);
    comboZy.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iZy = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboZy);

    return box;
  }

  private Box getDungan() {
    Box box = new Box(BoxLayout.X_AXIS);
    comboDg = new JComboBox(new String[] {"��Ѯ", "ʱѮ"});
    comboDg.setSelectedIndex(iDg);
    comboDg.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iDg = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboDg);

    return box;
  }

  private Box getYueJiang() {
    Box box = new Box(BoxLayout.X_AXIS);
    comboYj = new JComboBox(new String[] {"����", "����",
                            "�ӽ�", "��", "����", "î��", "����", "�Ƚ�"
                            , "�罫", "δ��", "�꽫", "�Ͻ�", "�罫", "����"});
    comboYj.setSelectedIndex(iYj);
    comboYj.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iYj = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboYj);

    return box;
  }

}
