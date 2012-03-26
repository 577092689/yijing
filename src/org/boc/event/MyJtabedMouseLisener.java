package org.boc.event;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

import java.util.Collection;

import org.boc.util.Public;
import org.boc.util.VO;
import org.boc.ui.*;

public class MyJtabedMouseLisener implements MouseListener{
      public void mouseClicked(MouseEvent e) {
        JTabbedPane pane = (JTabbedPane) e.getSource();
        if (e.getClickCount() == 1) {
          int index2 = pane.getSelectedIndex();
          //���ּ��ļ�id��
          String fileId = pane.getName();
          if(Public.getValueIndex(fileId)<0)
            return;
          //��һ��tabҳ������
          String title1 = Public.tabTitle[Public.getValueIndex(fileId)][1];
          String tag = pane.getTitleAt(index2);
          /**
           * ������б�ҳ��
           * ��fileId��ɵõ��˶���BasicJTabbedPane
           * �ɶ���õ�rowId
           * ��rowId�õ��丸Ŀ¼
           * ���ļ�fileId�еõ����и�Ŀ¼�µ�ֵ
           */
          if(tag.equals(title1)) {
            BasicJTabbedPane panel = (BasicJTabbedPane)TreePanel.mapBaseTabPane.get(fileId);
            String rowId = panel.getRowId();
            //System.out.println(fileId+":"+rowId);
            String parent = TreePanel.getParent(rowId);
            if(parent==null)
              parent = Public.getRootKey(fileId);
            Collection<VO> coll = Public.getObjectFromFile(fileId);
            panel.updTableInfo(coll);
            return;
          }
          return;
        }
//        	else if (e.getClickCount() == 2) { 	//˫������Ҫ�ر�
//          int index2 = pane.getSelectedIndex(); //�õ���ǰ��ǩҳ���±�
//          String tag = pane.getTitleAt(index2);
//          pane.removeTabAt(index2);
//          //�������û���
//          Public.setKeyValue(Public.mapKeyIsOpen, Public.getRootValue(tag), false);
//          return;
//        }
      }

    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
  }
