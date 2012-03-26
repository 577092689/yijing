package org.boc.db.qm;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.Messages;

public class GejuPanel extends ResultPanel{
  private MyTextPane textPane;
  private JScrollPane jScrollPane;

  public GejuPanel() {
    super();
    this.setLayout(new BorderLayout());
    this.add(getJScrollTextArea(), BorderLayout.CENTER);
  }

  public MyTextPane getTextPane() {
  	return textPane;
  }
  
  /**
   * ������ʾ����
   * @param str String
   */
  public void updResult(String str) {
  	if(str==null) {
  	//���·ֱ�����ʾ��һ�У�����ÿ�ζ����������һ����ʾ
      textPane.setCaretPosition(0);
      textPane.setSelectionStart(0);
      textPane.setSelectionEnd(0);
      return;
  	}
  	
  	Document doc = textPane.getDocument();
  	SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, Color.BLACK);
		
  	try {
  		doc.remove(0, doc.getLength());
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			Messages.error(e.getMessage());
			e.printStackTrace();
		}
		//���·ֱ�����ʾ��һ�У�����ÿ�ζ����������һ����ʾ
    textPane.setCaretPosition(0);
    textPane.setSelectionStart(0);
    textPane.setSelectionEnd(0);
  }

  /**
     * �õ��������ı���ʾ��
     * @return
     */
    private JScrollPane getJScrollTextArea() {
    	textPane = new MyTextPane();      
      jScrollPane = new JScrollPane(textPane);
      return jScrollPane;
    }

}
