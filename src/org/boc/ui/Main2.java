package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.boc.db.qm.QiMen2;
import org.boc.event.MyJtabedMouseLisener;
import org.boc.event.qm.TreeShowHideListener;
import org.boc.util.Messages;

/**
 * ���˲˵������������⣬�������������
 * ���������������
* ��һ�������
* �ڶ������һ����� jTabbedPane1��������Ƕ����BasicJTabbedPane��壬һ�����һ�����Tab
 */
public class Main2
    extends JPanel {
  private BorderLayout borderLayout1;
  private TreePanel treePanel;
  private JSplitPane jSplitPane1;  //һ���ָ������ɽ������������÷ָ��������һ����¶���
  private static JTabbedPane jTabbedPane1;
  private MyJtabedMouseLisener myTabedMouse;

  public Main2() {
    try {
      //����Ŀ¼
      myTabedMouse = new MyJtabedMouseLisener();
      treePanel = new TreePanel();
      borderLayout1 = new BorderLayout();
      jSplitPane1 = new JSplitPane();
      jTabbedPane1 = new JTabbedPane();
      jTabbedPane1.addMouseListener(myTabedMouse);

      jbInit();
    }
    catch (Exception ex) {
      Messages.error("Main2("+ex+")");
    }
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.setLastDividerLocation(175);
    jSplitPane1.setOneTouchExpandable(true);
    jSplitPane1.add(treePanel, JSplitPane.LEFT);
    jSplitPane1.add(jTabbedPane1, JSplitPane.RIGHT);
    //jSplitPane1.setDividerSize(20);
     
    //������չ��Ĭ������/չ��
		jSplitPane1.getLeftComponent().setMinimumSize(new Dimension()); 
    jSplitPane1.setDividerLocation(QiMen2.LEFT);
 
    jSplitPane1.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new TreeShowHideListener()); 	
    
		jSplitPane1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
  }

  public static JTabbedPane getRightTabbedPane() {
    return jTabbedPane1;
  }
}
