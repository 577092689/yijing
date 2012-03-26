package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.boc.ui.qm.Tip;
import org.boc.util.Messages;

public class ResultPanel extends JPanel{
  private MyTextPane textPane;  //��ʾ��Ϣ���ı���
  private JScrollPane jScrollPane;  //������  
  private BasicJPanel frame;  //��QimenFrame/SizhuFrame��
  private Tip tip; 
  private BorderLayout layout;

  public ResultPanel() {
    super();
    layout = new BorderLayout();
    textPane = new MyTextPane();    
    this.setLayout(layout);
    this.add(getJScrollTextArea(), layout.CENTER);
  }
  
  public void addFloatToolbar(JToolBar toolBar) {
    this.add(toolBar, layout.WEST);
    this.updateUI();
  }
  public void delFloatToolbar(JToolBar toolBar) {
  	this.remove(toolBar);
  	this.validate();
  	this.updateUI();
  }
  public void addInputPanel(JToolBar inanel) {
    this.add(inanel, layout.EAST);
    this.updateUI();
  }
  public void delInputPanel(JToolBar inanel) {
  	this.remove(inanel);
  	this.validate();
  	this.updateUI();
  }
  
  public MyTextPane getTextPane() {
  	return textPane;
  }
  public void setTextPane(MyTextPane textPane) {
  	this.textPane = textPane;
  }
  
  /**
   * ������ʾ����
   * @param str String
   */
  public void updResult(String str) {
  	if(str==null) {
  	//���·ֱ�����ʾ��һ�У�����ÿ�ζ����������һ����ʾ
      textPane.roll20();
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
    textPane.roll20();
  }

  /**
     * �õ��������ı���ʾ��
     * @return
     */
    private JScrollPane getJScrollTextArea() {    	    	    
      jScrollPane = new JScrollPane(textPane);
      return jScrollPane;
    }
//    /**
//     * �ꡢ�¡��ա�ʱ����ɫ���壬ֵ����ֵʹ���ú�ɫ����
//     * @param doc
//     * @throws BadLocationException
//     */
//    private void updateString(Document doc) throws BadLocationException {    	  	
//    	String FLAG = "��";
//    	String[] blueStr = {"��","��","��","ʱ"};
//    	String[] redStr = {"��",""};
//    	String[] yellowString = {"��","��","��","��","��","��"};
//    	
//    	SimpleAttributeSet redStyle = new SimpleAttributeSet();
//  		StyleConstants.setForeground(redStyle, Color.RED);  	
//  		//StyleConstants.setBold(redStyle, true);
//  		//StyleConstants.setFontSize(redStyle, 18);
//  		SimpleAttributeSet blueStyle = new SimpleAttributeSet();
//  		StyleConstants.setForeground(blueStyle, Color.BLUE);
//  		SimpleAttributeSet yelStyle = new SimpleAttributeSet();
//  		StyleConstants.setForeground(yelStyle, Color.PINK);  //MAGENTA ORANGE
//  		//StyleConstants.setBackground(yelStyle, Color.lightGray);
//  		
//  		String str = doc.getText(0, doc.getLength());
//  		if(str==null || str.trim().length()==0) return;
//  		int fromIndex = str.indexOf(FLAG);  //��|��ʼ����Ҫ�滻���ַ���
//  		//�õ�ֵʹ��
//  		int izhishi = str.indexOf("ֵʹ",0);
//  		redStr[1] = str.substring(izhishi+3,izhishi+4);
//  		//��������ʱ�����ɫ
//  		for(int i=0; i<blueStr.length; i++) {
//  			int start = str.indexOf(blueStr[i]+" ", fromIndex);
//  			if(start<0) break;
//  			doc.remove(start, 2);
//  			doc.insertString(start, blueStr[i]+" ", blueStyle);
//  		}
//  		//��ֱ��ֵʹ��ɺ�ɫ
//  		for(int i=0; i<redStr.length; i++) {
//  			int start = str.indexOf(" "+redStr[i]+" ", fromIndex);
//  			if(start<0) break;
//  			doc.remove(start, 3);
//  			doc.insertString(start, " "+redStr[i]+" ", redStyle);
//  		}
//  		//��������˥��ʶ����ɫ
//  		for(int i=0; i<yellowString.length; i++) {
//  			int start = str.indexOf(FLAG+yellowString[i]+" ", 0); //
//  			if(start<0) break;
//  			doc.remove(start+1, 2);
//  			doc.insertString(start+1, yellowString[i]+" ", yelStyle);
//  			while((start = str.indexOf(FLAG+yellowString[i]+" ", start+5))!=-1) {
//  				doc.remove(start+1, 2);
//    			doc.insertString(start+1, yellowString[i]+" ", yelStyle);
//  			}
//  		}
//    }


}
