package org.boc.ui.bz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.boc.db.BaZhai;
import org.boc.db.Calendar;
import org.boc.delegate.DelFs_BaZhai;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VoBaZhai;

public class BaZhaiFrame
    extends BasicJPanel {
  private DelFs_BaZhai del;
  private int yearBoy; //����
  private int yearGirl; //Ů��
  private JButton butYxp; //������
  private JButton butFx; //����

  private JComboBox comboWx; //����
  private JTextField textBoy; //����
  private JTextField textGirl; //Ů��
  private JComboBox comboDmw; //����λ
  private JComboBox comboDmx; //������
  private JComboBox comboWf; //��λ
  private JComboBox comboFmw; //����λ
  private JComboBox comboCw; //��λ
  private JComboBox comboGxw; //��ϴλ
  private JComboBox comboJzw; //��լλ
  private JComboBox comboCfw; //����λ
  private JComboBox comboZw; //��λ
  private JComboBox comboZx; //����
  private JComboBox comboFz; //����
  private JList jList;

  private int iWx; //����
  private int iDmw; //����λ
  private int iDmx; //������
  private int iWf; //��λ
  private int iFmw; //����λ
  private int iCw; //��λ
  private int iGxw; //��ϴλ
  private int iJzw; //��լλ
  private int iCfw; //����λ
  private int iZw; //��λ
  private int iZx; //����
  private int iFz; //����
  private int[] iHjs;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VoBaZhai vo;

  public BaZhaiFrame() {
    this.setLayout(new BorderLayout());
    del = new DelFs_BaZhai();
    iFz = 1;
    jList = new JList(BaZhai.hj);
  }

  public void init(String fileId, String rowId, String parentNode1) {
    vo = (VoBaZhai) Public.getObjectFromFile(fileId, rowId);
    if (vo != null) {
      yearBoy = vo.getYear();
      yearGirl = vo.getYearGirl();
      iFz = vo.isIsBoy() ? 0 : 1;
      iHjs = vo.getIHjs();
      iWx = vo.getIWx();
      iDmw = vo.getIDmw();
      iDmx = vo.getIDmx();
      iGxw = vo.getIGxw();
      iJzw = vo.getIJzw();
      iWf = vo.getIWf();
      iFmw = vo.getIFmw();
      iCw = vo.getICw();
      iCfw = vo.getICfw();
      iZw = vo.getIZw();
      iZx = vo.getIZx();
      memo = vo.getMemo();
    }

    this.fileId = fileId;
    this.rowId = rowId;
    if (parentNode1 == null) {
      if (vo != null)
        parentNode1 = vo.getParent();
    }
    this.parentNode = parentNode1;
    //1. ¼���Ϊ3����壬������
    this.add(getInputPanel(), BorderLayout.NORTH);
    //2. �ı���
    this.add(getOtherInfoPane(this.getSaveJButton(), memo), BorderLayout.CENTER);
  }

  private JButton getSaveJButton() {
    CommandAction actionXX = new CommandAction("����", null, "", ' ', new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String rs = check1();
        if (rs != null) {
          JOptionPane.showMessageDialog(getThis(), rs, "��ʾ��Ϣ",
                                        JOptionPane.INFORMATION_MESSAGE);
          return;
        }
        getInputs();
        vo = new VoBaZhai(yearBoy, yearGirl, iFz, iHjs, iWx,
                                     iDmw, iDmx, iGxw, iJzw, iWf, iFmw,
                                     iCw, iCfw, iZw, iZx);
        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //��ȡ��Ŀ¼�� һ��ȡԤ�����ĸ�Ŀ¼����ȡroot��ֵ
        vo.setRoot(Public.valueRoot[8]); //�˴��ǰ�լ��ֵ
        vo.setParent(parentNode);
        vo.setYcsj(Public.getTimestampValue().toString());
        vo.setMemo(memo);
        Public.writeObject2File(vo);
        JOptionPane.showMessageDialog(getThis(), "����ɹ���", "��ʾ��Ϣ",
                                      JOptionPane.INFORMATION_MESSAGE);
        clear();
      }
    });
    JButton buttonQK = new JButton(actionXX);
    return buttonQK;
  }

  public String do1() {
    if (vo == null)return "";
    return del.getBaZhaYxp(yearBoy, yearGirl, iFz, iHjs, iWx,
                             iDmw, iDmx, iGxw, iJzw, iWf, iFmw,
                             iCw, iCfw, iZw, iZx);
  }

  public String do2() {
    if (vo == null)return "";
    return del.getBaZhaiInfo(yearBoy, yearGirl, iFz, iHjs, iWx,
                             iDmw, iDmx, iGxw, iJzw, iWf, iFmw,
                             iCw, iCfw, iZw, iZx);
  }


  /**
   * ������������¼�����
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
    inputPanel.add(getOnePanel());
    inputPanel.add(getTwoPanel());
    inputPanel.add(getThreePanel());
    inputPanel.add(getFourPanel());
    inputPanel.add(getFivePanel());
    inputPanel.add(getSixPanel());

    return inputPanel;
  }

  /**
   * �õ���һ�е����
   * @return
   */
  private Box getOnePanel() {
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("��ͷ��"));
    jList.setVisibleRowCount(3);
    if(iHjs != null)
      jList.setSelectedIndices(iHjs);

    JScrollPane pane = new JScrollPane(jList);
    box.add(pane);
    box.add(new JLabel("  "));

    return box;
  }

  private Box getTwoPanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("����"));
    box.add(this.getWuXiang());
    box.add(new JLabel("  "));
    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("���ţ�"));
    box2.add(this.getFangMenWei());
    box2.add(new JLabel("  "));
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("������"));
    textBoy = new JTextField(4);
    textBoy.setText(""+this.yearBoy);
    box3.add(textBoy);
    box3.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);
    box1.add(box3);

    return box1;
  }

  private Box getThreePanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("���ţ�"));
    box.add(this.getDaMenWei());
    box.add(new JLabel("  "));
    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("��λ��"));
    box2.add(this.getChuangWei());
    box2.add(new JLabel("  "));
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("Ů����"));
    textGirl = new JTextField(4);
    textGirl.setText(""+this.yearGirl);
    box3.add(textGirl);
    box3.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);
    box1.add(box3);

    return box1;
  }

  private Box getFourPanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("����"));
    box.add(this.getDaMenXiang());
    box.add(new JLabel("  "));
    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("������"));
    box2.add(this.getChuFang());
    box2.add(new JLabel("  "));
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("������"));
    box3.add(this.getFangZhu());
    box3.add(new JLabel("  "));

    box1.add(box);
    box1.add(box2);
    box1.add(box3);

    return box1;
  }

  /**
   * �õ������е����
   * @return
   */
  private Box getFivePanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("�Է���"));
    box.add(this.getWoFang());
    box.add(new JLabel("  "));

    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("��λ��"));
    box2.add(this.getZhaoWei());
    box2.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);

    return box1;
  }

  /**
   * �õ������е����
   * @return
   */
  private Box getSixPanel() {
    Box box1 = new Box(BoxLayout.Y_AXIS);
    Box box = new Box(BoxLayout.X_AXIS);
    box.add(new JLabel("��լ��"));
    box.add(this.getJiuZhai());
    box.add(new JLabel("  "));

    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("����"));
    box2.add(this.getZhaoXiang());
    box2.add(new JLabel("  "));
    box1.add(box);
    box1.add(box2);

    return box1;
  }

  private String checkBoy() {
    if (textBoy.getText() == null || textBoy.getText().equals("")) {
      return "����������ݱ�����" + Calendar.MAXYEAR + "��" +
          Calendar.IYEAR + "֮��";
    }
    try {
      Integer.valueOf(textBoy.getText().trim()).intValue();
    }
    catch (Exception e) {
      return "����������ݱ��������֣�������" + Calendar.MAXYEAR + "��" +
          Calendar.IYEAR + "֮��";
    }
    String _y = textBoy.getText();
    yearBoy = _y.equals("") ? 0 : Integer.valueOf(_y).intValue();
    if (yearBoy > Calendar.MAXYEAR || yearBoy < Calendar.IYEAR)
      return "������ݱ�����" + Calendar.MAXYEAR + "��" + Calendar.IYEAR + "֮��";
    _y = textGirl.getText();

    return null;
  }

  private String checkGirl() {
    String _y = textGirl.getText();
    try {
      Integer.valueOf(textGirl.getText().trim()).intValue();
    }
    catch (Exception e) {
      return "Ů��������ݱ��������֣�������" + Calendar.MAXYEAR + "��" +
          Calendar.IYEAR + "֮��";
    }
    if (textGirl.getText() == null || textGirl.getText().equals("")) {
      return "Ů��������ݱ�����" + Calendar.MAXYEAR + "��" +
          Calendar.IYEAR + "֮��";
    }
    yearGirl = _y.equals("") ? 0 : Integer.valueOf(_y).intValue();
    if (yearGirl > Calendar.MAXYEAR || yearGirl < Calendar.IYEAR)
      return "Ů����ݱ�����" + Calendar.MAXYEAR + "��" + Calendar.IYEAR + "֮��";

    return null;
  }

  private String check1() {
    String boy = checkBoy();
    String girl = checkGirl();

    if ( (iFz == 0 || iFz == 1) && boy != null) {
      return boy;
    }
    else if (iFz == 2 && girl != null) {
      return girl;
    }
    if (iWx == 0)
      return "�����������ѡ������Ϊ��������������ˡ�ֹ���ء��顢��";
    return null;
  }

  private String check3() {
    String boy = checkBoy();
    if (boy != null)return boy;
    String girl = checkGirl();
    if (girl != null)return girl;
    if (iWx == 0)
      return "�����������ѡ������Ϊ��������������ˡ�ֹ���ء��顢��";
    return null;
  }

  private String check2() {
    String str = check3();
    if (str != null) {
      return str;
    }

    if (this.iWx == 0)
      return "�����������ѡ����������ˡ�ֹ���ء��顢��";
    if (this.iDmw == 0)
      return "����λ�ñ���ѡ����סլ��̫���������";
    if (this.iDmx == 0)
      return "���ſ��ŷ������ѡ����סլ��̫���������";
    if (this.iWf == 0)
      return "�Է�λ�ñ���ѡ����סլ��̫���������";
    if (this.iFmw == 0)
      return "�Է����ŷ������ѡ��������С̫���������";
    if (this.iCw == 0)
      return "��ͷλ�ñ���ѡ��������С��̫���������";
    if (this.iCfw == 0)
      return "��������ѡ����סլ��̫���������";
    if (this.iZw == 0)
      return "��λ����ѡ����סլ��̫���������";
    if (this.iZx == 0)
      return "��ĳ���(�Կ��ƿ���Ϊ׼)����ѡ����סլ��̫���������";
    //if(this.iGxw==0)
    //  return "����λ�ñ���ѡ����סլ��̫���������";
    //if(this.iJzw==0)
    //  return "�������ѡ������̫���������";

    return null;
  }

  private void getInputs() {
    memo = getJTextArea().getText();
    String _boy = textBoy.getText().trim();
    String _girl = textGirl.getText().trim();
    yearBoy = _boy.equals("") ? 0 : Integer.valueOf(_boy).intValue();
    yearGirl = _girl.equals("") ? 0 : Integer.valueOf(_girl).intValue();
    iHjs = jList.getSelectedIndices();
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
   * �õ������������
   * @return
   */
  private Box getWuXiang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboWx = new JComboBox(BaZhai.fx);
    comboWx.setSelectedIndex(this.iWx);
    comboWx.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iWx = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboWx);

    return box;
  }

  /**
   * �õ�����λ��������
   * @return
   */
  private Box getDaMenWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboDmw = new JComboBox(BaZhai.fx);
    comboDmw.setSelectedIndex(this.iDmw);
    comboDmw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iDmw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboDmw);

    return box;
  }

  /**
   * �õ��������������
   * @return
   */
  private Box getDaMenXiang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboDmx = new JComboBox(BaZhai.fx);
    comboDmx.setSelectedIndex(this.iDmx);
    comboDmx.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iDmx = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboDmx);

    return box;
  }

  /**
   * �õ��Է���������
   * @return
   */
  private Box getWoFang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboWf = new JComboBox(BaZhai.fx);
    comboWf.setSelectedIndex(this.iWf);
    comboWf.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iWf = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboWf);

    return box;
  }

  /**
   * �õ����ŵ�������
   * @return
   */
  private Box getFangMenWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboFmw = new JComboBox(BaZhai.fx);
    comboFmw.setSelectedIndex(this.iFmw);
    comboFmw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iFmw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboFmw);

    return box;
  }

  /**
   * �õ���λ��������
   * @return
   */
  private Box getChuangWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboCw = new JComboBox(BaZhai.fx);
    comboCw.setSelectedIndex(this.iCw);
    comboCw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iCw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboCw);

    return box;
  }

  /**
   * �õ���ϴλ��������
   * @return
   */
  private Box getGuanXiWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboGxw = new JComboBox(BaZhai.fx);
    comboGxw.setSelectedIndex(this.iGxw);
    comboGxw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iGxw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboGxw);

    return box;
  }

  /**
   * �õ���լ��������
   * @return
   */
  private Box getJiuZhai() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboJzw = new JComboBox(BaZhai.fx);
    comboJzw.setSelectedIndex(this.iJzw);
    comboJzw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iJzw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboJzw);

    return box;
  }

  /**
   * �õ�������������
   * @return
   */
  private Box getChuFang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboCfw = new JComboBox(BaZhai.fx);
    comboCfw.setSelectedIndex(this.iCfw);
    comboCfw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iCfw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboCfw);

    return box;
  }

  /**
   * �õ���λ��������
   * @return
   */
  private Box getZhaoWei() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboZw = new JComboBox(BaZhai.fx);
    comboZw.setSelectedIndex(this.iZw);
    comboZw.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iZw = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboZw);

    return box;
  }

  /**
   * �õ������������
   * @return
   */
  private Box getZhaoXiang() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboZx = new JComboBox(BaZhai.fx);
    comboZx.setSelectedIndex(this.iZx);
    comboZx.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iZx = ( (JComboBox) e.getSource()).getSelectedIndex();
      }
    });
    box.add(comboZx);

    return box;
  }

  /**
   * �õ�����������
   * @return
   */
  private Box getFangZhu() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboFz = new JComboBox(new String[] {"��", "Ů"});
    comboFz.setSelectedIndex(this.iFz);
    comboFz.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iFz = ( (JComboBox) e.getSource()).getSelectedIndex() + 1;
      }
    });
    box.add(comboFz);

    return box;
  }

  class PrintListener
      implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int selected[] = jList.getSelectedIndices();
      //System.out.println("Selected Elements:  ");
      //for (int i = 0; i < selected.length; i++) {
      //  String element = (String) jList.getModel().getElementAt(selected[i]);
      //  System.out.println("  " + element);
      //}
    }
  }

}
