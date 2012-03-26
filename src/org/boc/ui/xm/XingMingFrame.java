package org.boc.ui.xm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.boc.delegate.DelXmMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.CommandAction;
import org.boc.util.Public;
import org.boc.util.VO;

public class XingMingFrame
    extends BasicJPanel {
  private DelXmMain xm;

  private JTextField txtName ;     //����
  private ButtonGroup xing;     //������
  private JRadioButton dan;     //����
  private JRadioButton fu;      //����

  private String xingm ;
  private boolean isDan;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;
  public VO vo;

  public XingMingFrame() {
    this.setLayout(new BorderLayout());
    xm = new DelXmMain();
  }

  public void init(String fileId, String rowId, String parentNode) {
    vo = (VO)Public.getObjectFromFile(fileId, rowId);
    this.fileId = fileId;
    this.rowId = rowId;
    this.parentNode = parentNode;

    //1. ¼���Ϊ3����壬������
    this.add(getInputPanel(), BorderLayout.NORTH);
    //2. �ı���
    this.add(getOtherInfoPane(getSaveJButton(), memo),BorderLayout.CENTER);
  }

  public JButton getSaveJButton() {
    CommandAction actionXX = new CommandAction("����", null, "", ' ', new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String rs = check();
        if(rs!=null) {
          JOptionPane.showMessageDialog(getThis(), rs, "��ʾ��Ϣ",
                                        JOptionPane.INFORMATION_MESSAGE);
          return ;
        }
        getInputs();
        vo = new VO();
        vo.setName(xingm);                  //����
        vo.setBqt1(isDan);                  //�Ƿ��ǵ���
        vo.setRowId(rowId);
        vo.setFileId(fileId);
        //��ȡ��Ŀ¼�� һ��ȡԤ�����ĸ�Ŀ¼����ȡroot��ֵ
        vo.setRoot(Public.valueRoot[9]);  //�˴���������ֵ
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
    return xm.fx(vo.getName(), vo.isBqt1());
  }

  /**
   * ��һ��������¼�����
   * @return
   */
  private JPanel getInputPanel() {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(getUpPanel());

    return inputPanel;
  }

  private Box getUpPanel() {
    Box box = new Box(BoxLayout.X_AXIS);

    box.add(new JLabel(" "));
    box.add(new JLabel("������������"));
    txtName = new JTextField(4);
    if(vo==null || vo.getName()==null)
      txtName.setText(rowId);
    else
      txtName.setText(vo.getName());
    box.add(txtName);
    box.add(new JLabel("    "));

    dan = new JRadioButton("����",true);
    dan.setActionCommand("dan");
    fu = new JRadioButton("����");
    fu.setActionCommand("fu");
    xing = new ButtonGroup();
    xing.add(dan);
    xing.add(fu);
    box.add(dan);
    box.add(fu);
    box.add(new JLabel("      "));
    return box;
  }

  private void getInputs() {
    memo = getJTextArea().getText();
    xingm = txtName.getText();
    isDan = xing.getSelection().getActionCommand().equals("dan");
  }

  private String check() {
    String xm = txtName.getText();
    if(xm==null || "".equals(xm.trim()) || xm.length()<2)
      return "����("+xm+")����Ϊ�գ������پ����պ���";

    if(xm.length()>4)
      return "��������("+xm+")̫������������������Ѳ��ܴ�����������������ϵ";


    String df = xing.getSelection().getActionCommand();
    boolean b = df.equals("fu");
    if(b && xm.length()<3) {
      return "����("+xm+")����Ҫ�и�����";
    }else if(!b && xm.length()>3)
      return "����("+xm+")�����е����Ӵ��";

    return null;
  }

  protected Component getThis() {
    return this;
  }

  private void clear() {
  }
}
