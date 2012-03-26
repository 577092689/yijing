package org.boc.ui.ly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.boc.calendar.ui.MiniCalendarForm;
import org.boc.ui.UIPublic;
import org.boc.util.Messages;

/**
 * ���������������
 * �Ľ���
 * 1�������ǰʱ���л������ڣ�
 * 2�������֧������ʾ��֧¼�뷽ʽ��
 * 3��˫��ĳ�գ����ضԻ���; 
 * 4)���Сʱ�ͷ���
 */
public class LyCalendarForm extends MiniCalendarForm {
	private LiuYaoFrame frame;   //������

	public LyCalendarForm() {		
		super();
	}
	
	/**
	 * ��ʼ��ʱ��Ϊȫ�ɡ�ȫ֧����ֹ˫����Ҷ��ʱ�ҵ�֧������
	 */
	public void initGanzi() {			
		this.buttonGanzi = UIPublic.getInitImageButton("yes","yes","��֧����",new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				//����ŵ�������£���ֹ��һ�β�ѡ��ֱ�����.�˴�����λ��ֵ����ʱҲ������ã�����������ʱ�Ͳ�����ֵ.
	      updateFrameganzi();
				frame.pan();
			}
		});
		super.initGanzi();
	}

	/**
	 * �����������
	 * Ϊ��ֹ�ظ���������¼�����Ҫ�ж�frame��������ʱ���������ѡ��ֵ��һ������Ҫ����
	 * 0������frame�����е�������ʱ��ֵ��Ȼ��������֣�
	 * 1��2��3��4���ֱ�Ϊ�ꡢ�¡��ա�ʱ�������ͬ���ø��º���֣�����Ҫ����frame��ֵ��Ҫ������֣�
	 * 5��Ϊ�֣���Ҫ����frame��ֵ������Ҫ��֣�
	 * ��˫��Ҷ��ȫ������������ʱ����Ӹ�֧�л���������ʱ��Ҳ��Ҫ�������е�������ʱ�������鷳
	 */
	public void reDo(int type) {
		if(frame == null) return;
		
		int iyear = model.getSelectYear(); 
		int ihour = Integer.valueOf(this.hf.getText());
		int iminute = Integer.valueOf(this.mf.getText());
		int iday = this.model.getSelectDay();
		int imonth = this.model.getSelectMonth()+1; 
		
		//System.out.println("type="+type+";iyear="+iyear+";imonth="+imonth+";iday="+iday+";ihour="+ihour+";frame.year="+frame.getYear()+";frame.month="+frame.getMonth()+";frame.day="+frame.getDay()+";frame.hour="+frame.getHour());
		//����������������Ƿ��ǣ�һ�ɰ�����¼��
		if(type==5) {  //���Ϊ���ӣ���ֱ�Ӹ���ֵ�󷵻�
			frame.setMinute(iminute);
			return;
		}else if(type==4 && frame.getHour()==ihour) { //Сʱ��ͬ���ø��º���֣�����Ҫ
			return;
		}else if(type==3 && frame.getDay()==iday) { //������ͬ���ø��º���֣�����Ҫ
			return;
		}else if(type==2 && frame.getMonth()==imonth) { //�·���ͬ���ø��º���֣�����Ҫ
			//if(frame.getYear()*frame.getMonth()*frame.getDay()==0) return;
			return;
		}else if(type==1 && frame.getYear()==iyear) { //�����ͬ���ø��º���֣�����Ҫ
			return;
		}
		if(iyear<org.boc.db.Calendar.IYEAR || iyear >org.boc.db.Calendar.MAXYEAR) {
			Messages.info("Ŀǰ֧�������"+org.boc.db.Calendar.IYEAR+"~"+org.boc.db.Calendar.MAXYEAR+"֮�䣡");
			return;
		}
		frame.setInputParameter(iyear, imonth, iday, ihour, iminute, true, false);
		frame.setInputGanzi(0, 0, 0, 0, 0, 0, 0, 0);
		frame.pan();
	}
		
	public void setParent(JPanel frame) {
		this.frame = (LiuYaoFrame)frame;
	}
	
	public void updateFrameganzi() {
		int ing = ng.getSelectedIndex();
    int iyg = yg.getSelectedIndex();
    int irg = rg.getSelectedIndex();
    int isg = sg.getSelectedIndex();
    int inz = ing%2==1 ? (nz.getSelectedIndex()-1)*2+1 : nz.getSelectedIndex()*2;
    int iyz = iyg%2==1 ? (yz.getSelectedIndex()-1)*2+1 : (yz.getSelectedIndex()*2);
    int irz = irg%2==1 ? (rz.getSelectedIndex()-1)*2+1 : (rz.getSelectedIndex()*2);
    int isz = isg%2==1 ? (sz.getSelectedIndex()-1)*2+1 : (sz.getSelectedIndex()*2);
    if(frame==null) return;
    frame.setInputGanzi(ing, inz,	iyg, iyz,	irg, irz,	isg, isz);
    frame.setInputParameter(0, 0, 0, 0, 0, false, false);
	}
	
	public static void main(String[] args) {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		LyCalendarForm form = new LyCalendarForm();
		JFrame frame = new JFrame();
		frame.getContentPane().add(form);
		frame.setSize(500,500);
		frame.setVisible(true);
	}
}