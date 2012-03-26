package org.boc.ui;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.boc.db.qm.QiMen2;
import org.boc.event.qm.QmClickListener;
import org.boc.event.qm.TipMouseMotionAdaption;
import org.boc.ui.qm.QimenPopupMenu;
import org.boc.util.HtmlMultiLineControl;

/**
 * �ڶ��������QimenFrame���������Ժ�������һ��JPanel������BasicJTabbedPane��ѭ��new�ģ�������õڶ�������do1������
     * ���3��ֱ�Ӹ���ResultPane����4��5��6���Ƿ���ֵ�Ժ�������ģ��������3��ͬ
 */
public abstract class BasicJPanel extends JPanel{
	private BasicJTabbedPane father; //�������ĸ����,�Ա�ȡ�����еĵ����鼰�Ժ�����
  public JTextArea jTextArea;
  public JScrollPane jScrollPane;  
  protected HtmlMultiLineControl html = new HtmlMultiLineControl();
  public MouseListener clickListner;
  public MouseMotionListener mouseMotionListener;
  /**
   * ���󱣴����ļ�ʱ�������ĸ��ڵ㣬�Է�ֹ���ļ�����ͬʱ�Ĵ���
   * Ŀǰ���ļ�idһ��
   */
  private String root;
  public String getRoot() {
    return root;
  }
  public void setRoot(String root) {
    this.root = root;
  }
  public void setFather(BasicJTabbedPane pane) {
  	this.father = pane;
  }
  public BasicJTabbedPane getFather() {
  	return this.father;
  }
  /**
   * ��ʼ����������������
   * @param fileId String  �ļ�id
   * @param rowId String   ���б�ʶ�������Ľڵ�����
   * @param parentNode String  �ýڵ�ĸ��ڵ�
   */
  public abstract void init(String fileId, String rowId, String parentNode);

  /**
   * ���������ť�ķ��������ʵ���˱�������
   * �����̡������ԡ����������ס��Ը񡢲��׵�
   * @return String
   */
  //public String do0(ResultPanel pane) {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do1() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do2() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do3() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do4() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do5() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do6() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do7() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do8() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do9() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}
  public String do10() {return "\r\n    ������......����ȴ���һ�汾���ƣ�";}

  /**
   * �õ���Ա��������Ϣ
   * �總ע����λ�� ְ�� ������ַ����ס��ַ
   * �������룬 �绰�� �ֻ��� email, QQ�� MSN
   * @return
   */
  public Box getOtherInfoPane(JButton saveB, String memo) {
    Box box = new Box(BoxLayout.Y_AXIS);

    box.add(new JLabel("  "));
    box.add(new JLabel("  "));
    box.add(new JLabel("  "));

    Box p1 = new Box(BoxLayout.X_AXIS);
    p1.add(new JLabel("��ע��Ϣ��                                           "));
    p1.add(saveB);
    box.add(p1);
    jTextArea = new JTextArea();
    jTextArea.setLineWrap(false);
    jTextArea.setText(memo);
  //���·ֱ�����ʾ��һ�У�����ÿ�ζ����������һ����ʾ
    jTextArea.setCaretPosition(0);
    jTextArea.setSelectionStart(0);
    jTextArea.setSelectionEnd(0);
    
    //jTextArea.setForeground(java.awt.Color.white);
    //jTextArea.setRows(20);
    //jTextArea.setColumns(100);
    jScrollPane = new JScrollPane(jTextArea);
    box.add(jScrollPane);

    return box;
  }

  public JTextArea getJTextArea() {
    return this.jTextArea;
  }

  //����Ҫ���µ����
  private ResultPanel resultPane ; 
  public void setResultPane(ResultPanel rp) {
  	this.resultPane = rp;
  }
  //�õ����ʱ��˳���������ƶ��¼�
  public ResultPanel getResultPane() {   
  	return resultPane;
  }
  
  public MouseMotionListener getMouseMotionListener() {
  	return mouseMotionListener;
  }
  
	//����/ɾ��������
	public void addTool(JToolBar toolbar) {
		this.getResultPane().addFloatToolbar(toolbar);  //�ڽ������������һ������������
	}
	public void delTool(JToolBar toolbar) {
		this.getResultPane().delFloatToolbar(toolbar);  //�ڽ���������ɾ��һ������������
	}
	//����/ɾ���߼����
	public void addInput(JToolBar inputToolBar) {
		this.getResultPane().addInputPanel(inputToolBar);  //�ڽ������������һ������������
	}
	public void delInput(JToolBar inputToolBar) {
		this.getResultPane().delInputPanel(inputToolBar);  //�ڽ���������ɾ��һ������������
	}
}
