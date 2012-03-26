package org.boc.ui.qm;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import org.boc.calendar.ui.CalendarForm;
import org.boc.calendar.ui.TimesLabel;
import org.boc.calendar.util.Lunar;
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
public class QmCalendarForm extends CalendarForm {
	private QiMenFrame frame;   //������

	public QmCalendarForm() {		
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
				frame.pan(false);
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
		frame.pan(false);
	}
		
	public void setParent(JPanel frame) {
		this.frame = (QiMenFrame)frame;
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
		new QmCalendarForm().setVisible(true);
	}
}