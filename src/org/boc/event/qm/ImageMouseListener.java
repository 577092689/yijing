package org.boc.event.qm;


public class ImageMouseListener {// implements MouseListener{
//	private QiMenFrame pane; 
//	private JTextField textField;
//	private int type;  //-1Ϊ��С��1Ϊ����
//	private int sizhu;  //1Ϊ�ꡢ2Ϊ�£�3Ϊ�գ�4Ϊʱ
//	//private int hour,minute,second;  //ʱ����
//	
////	public void setHour(int hour, int minute, int second) {
////		this.hour = hour;
////		this.minute = minute; 
////		this.second = second;
////	}
//	public ImageMouseListener(QiMenFrame pane, int type, int sizhu, JTextField textField) {
//		this.textField = textField;
//		this.type = type;
//		this.sizhu = sizhu;
//		this.pane = pane;
//	}
//	
//	public void mouseClicked(MouseEvent me) {
//		if(textField.getText().equals("")) {
//  		Messages.info("����������ʱ����򣬲���Ϊ�գ�");
//  		return;
//		}    		
//		
//    if (me.getClickCount() == 1) {
//    	int num = 0; 
//    	int hour = pane.getHour();
//    		if(sizhu==4) {
//    				num = type<0 ? hour-2 : hour+2; //�õ�Сʱ����2
//    		} else {
//    				num = type<0 ? Integer.valueOf(textField.getText())-1 : Integer.valueOf(textField.getText())+1;	    
//    		}    		
//    	
//    	if(sizhu==1) {
//    		String s = pane.checkInputs(num+"", "1", "1", "1");
//    		if(s!=null) {
//	    		Messages.info(s);
//	    		return;
//    		}    		
//    		textField.setText(num+"");
//    		pane.pan(num, -1 ,-1 ,-1);
//    	}
//    	if(sizhu==2) {
//    		num = num<=0 ? 12 : num;
//    		num = num>12 ? 1 : num; 
//    		textField.setText(num+"");
//    		pane.pan( -1, num ,-1 ,-1);
//    	}
//    	if(sizhu==3) {
//    		num = num<=0 ? 31 : num;
//    		num = num>31 ? 1 : num; 
//    		textField.setText(num+"");
//    		pane.pan( -1, -1 ,num ,-1);
//    	}
//    	if(sizhu==4) {
//    		num = num<=0 ? 23 : num;
//    		num = num>23 ? 0 : num; 
//    		textField.setText(num+":"+pane.getMinute()+":00");
//    		pane.pan(-1, -1, -1, num);
//    	}        	
//    	pane.getResultPane().getTextPane().roll20();
//    }
//  }
//  public void mouseEntered(MouseEvent e) {}
//  public void mouseExited(MouseEvent e) {}
//  public void mousePressed(MouseEvent e) {}
//  public void mouseReleased(MouseEvent e) {}
}
