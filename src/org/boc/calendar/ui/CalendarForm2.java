package org.boc.calendar.ui;

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
import javax.swing.JFrame;
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

import org.boc.calendar.util.Lunar;
import org.boc.ui.UIPublic;
import org.boc.ui.qm.QiMenFrame;
import org.boc.util.Messages;


/**
 * ���������������
 * �Ľ���
 * 1�������ǰʱ���л������ڣ�
 * 2�������֧������ʾ��֧¼�뷽ʽ��
 * 3��˫��ĳ�գ����ضԻ���; 
 * 4)���Сʱ�ͷ���
 */
public class CalendarForm2 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6376610934523560443L;
	private final static Font font = new Font("����", Font.TRUETYPE_FONT, 12);
	Calendar clr = Calendar.getInstance();
	private QiMenFrame frame;   //������
	private Lunar lunar ;
	private GanziMouseAdapter ganziMouseAdapter ;
	private FocusListener focusListener;
	
	private final int MAXYEAR = 9999;
	private final int MINYEAR = 1900;
	private final String LINKTEXT = "��ǰʱ��";
	private Container main;
	private CalendarTableModel model;
	private TimesLabel time;
	private JTable table;
	private JTableHeader th ;
	private JLabel hLabel, mLabel, timeLabel;  //Сʱ�����ӡ���ǰʱ��	
	private JLabel nzhu, yzhu, rzhu, szhu;     //�ꡢ�¡��ա�ʱ��	
	private JComboBox ng,nz,yg,yz,rg,rz,sg,sz; //������ʱ��֧����ѡ���
	private JButton buttonGanzi;
	JTextField tf; //���
	JTextField hf; //Сʱ
	JTextField mf; //����
	JComboBox select = new JComboBox();
	BasicArrowButton arrowUp = new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton arrowDown = new BasicArrowButton(BasicArrowButton.SOUTH);
	BasicArrowButton arrowUph = new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton arrowDownh = new BasicArrowButton(BasicArrowButton.SOUTH);
	BasicArrowButton arrowUpm = new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton arrowDownm = new BasicArrowButton(BasicArrowButton.SOUTH);
	JEditorPane html = null;

	public CalendarForm2() {		
		this.main = this.getContentPane();
		this.main.setLayout(null);
		this.main.setFont(CalendarForm2.font);

		this.model = new CalendarTableModel();
		this.table = new JTable(model);
		focusListener = new FocusListener();
		ganziMouseAdapter = new GanziMouseAdapter();
		
		select.setFont(font);
		String[] vs = {"һ", "��", "��", "��", "��", "��", "��", "��", "��", "ʮ", "ʮһ", "ʮ��"};
		for (int i=0; i<vs.length; i++)
			select.addItem(vs[i] + "��");
		select.setBounds(5, 5, 80, 24);
		select.addActionListener(this);  //���·ݸı䣬��������¼�
		select.setSelectedIndex(this.model.getCurrentMonth());
		this.main.add(select);
		
		tf = new JTextField(String.valueOf(this.model.getCurrentYear()));
		tf.setBounds(95, 5, 70, 24);
		tf.setEditable(true);
		tf.addFocusListener(focusListener); 		
		this.main.add(tf);
		
		arrowUp.setBounds(165, 5, 20, 12);
		arrowUp.addActionListener(this);  //��һ�꣬����
		this.main.add(arrowUp);
		arrowDown.setBounds(165, 17, 20, 12);
		arrowDown.addActionListener(this); //��һ�꣬����
		this.main.add(arrowDown);
		arrowUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrowDown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		table.setBackground(this.getBackground());
		table.setFont(CalendarForm2.font);

		th = table.getTableHeader();
		th.setResizingAllowed(false);
		th.setReorderingAllowed(false);
		th.setBackground(Color.GRAY);
		th.setBounds(5, 30, 180, 20);
		main.add(th);
		
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		dtcr.setFont(font);
		dtcr.setForeground(Color.BLACK);
		table.setDefaultRenderer(Object.class, dtcr);
		dtcr = new DefaultTableCellRenderer();   
        dtcr.setForeground(Color.RED);
        dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
        
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateColumnsFromModel(false);
    table.setCellSelectionEnabled(true);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setSelectionForeground(new Color(0xFF8800));
    table.setSelectionBackground(this.main.getBackground());
    table.setBorder(LineBorder.createBlackLineBorder());
		table.setBounds(5, 50, 180, 80);   //(5, 50, 180, 96) 
		table.addMouseListener(new TableMouseAdapter());
		main.add(table);
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		
		Font labelFont = new Font("����", Font.PLAIN, 12);
		hLabel = new JLabel("Сʱ");
		hLabel.setFont(labelFont);
		hLabel.setBounds(10, 131, 30, 24);
		main.add(hLabel);
		hf = new JTextField(String.valueOf(this.model.getCurrentHour()));
		hf.setBounds(40, 131, 30, 24);
		hf.setEditable(true);
		hf.addFocusListener(focusListener); 		
		main.add(hf);
		arrowUph.setBounds(70, 131, 20, 12);
		arrowUph.addActionListener(this);  //��һСʱ������
		this.main.add(arrowUph);
		arrowDownh.setBounds(70, 142, 20, 12);
		arrowDownh.addActionListener(this); //��һ��ʱ������
		this.main.add(arrowDownh);
		arrowDownh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrowUph.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		
		mLabel = new JLabel("����");
		mLabel.setFont(labelFont);
		mLabel.setBounds(100, 131, 30, 24);
		main.add(mLabel);
		mf = new JTextField(String.valueOf(this.model.getCurrentMinute()));
		mf.setBounds(130, 131, 30, 24);
		mf.setEditable(true);
		mf.addFocusListener(focusListener); 		
		main.add(mf);
		arrowUpm.setBounds(160, 131, 20, 12);
		arrowUpm.addActionListener(this);  //��һ���ӣ�����
		this.main.add(arrowUpm);
		arrowDownm.setBounds(160, 142, 20, 12);
		arrowDownm.addActionListener(this); //��һ���ӣ�����
		this.main.add(arrowDownm);
		arrowUpm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		arrowDownm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		
		timeLabel = new JLabel("");
		timeLabel.setFont(CalendarForm2.font);
		timeLabel.setBounds(7, 157, 60, 21);  //(5, 147, 180, 14)
		LinkLabel link = new LinkLabel();
		link.initText(timeLabel);
		timeLabel.addMouseListener(link);
		timeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		main.add(timeLabel);
		time = new TimesLabel();
		time.setFont(CalendarForm2.font);
		time.setBounds(67, 160, 120, 18);  //(5, 147, 180, 14)
		main.add(time);		

    html = new JEditorPane("text/html", "<html></html>");
    html.setEditable(false);
    html.setBounds(190, 5, 112, 169);
    html.setFont(CalendarForm2.font);
    html.setBackground(main.getBackground());
    html.setBorder(LineBorder.createBlackLineBorder());
    html.addMouseListener(new GanziAdapter());
    main.add(html);
    
    initGanzi();
        
		//Image icon = this.getToolkit().getImage("res/icon.gif");
		//this.setIconImage(icon);
		//this.setTitle("Calendar - ialvin.cn");
		//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(310, 180);
		this.setResizable(false);
		//WindowView.moveToScreenCenter(this);
		this.setUndecorated(true);
		this.initLunar();
		showWhich(true);
	}
	
	/**
	 * ��ʼ��ʱ��Ϊȫ�ɡ�ȫ֧����ֹ˫����Ҷ��ʱ�ҵ�֧������
	 */
	private void initGanzi() {		
		nzhu = new JLabel("������");
		yzhu = new JLabel("������");
		rzhu = new JLabel("������");
		szhu = new JLabel("ʱ����");
		ng = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		ng.setName("niangan");
		yg = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		yg.setName("yuegan");
		rg = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		rg.setName("rigan");
		sg = this.getSelectOfGanzi(lunar.Tianan0,ganziMouseAdapter);
		sg.setName("shigan");
		nz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		nz.setName("nianzi");
		yz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		yz.setName("yuezi");
		rz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		rz.setName("rizi");
		sz = this.getSelectOfGanzi(lunar.Deqi0,ganziMouseAdapter);
		sz.setName("shizi");
		buttonGanzi = UIPublic.getInitImageButton("yes","yes","��֧����",new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				//����ŵ�������£���ֹ��һ�β�ѡ��ֱ�����.�˴�����λ��ֵ����ʱҲ������ã�����������ʱ�Ͳ�����ֵ.
	      updateFrameganzi();
				frame.pan(false);
			}
		});
		//˫���¼����л���ʱ��¼�����
		main.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					showWhich(true);
				}
			}
		});
		nzhu.setBounds(40, 13, 50, 24);		
		ng.setBounds(110,13,50,24);
		nz.setBounds(190,13,50,24);
		
		yzhu.setBounds(40, 38, 50, 24);		
		yg.setBounds(110,38,50,24);
		yz.setBounds(190,38,50,24);
		
		rzhu.setBounds(40, 63, 50, 24);		
		rg.setBounds(110,63,50,24);
		rz.setBounds(190,63,50,24);
		
		szhu.setBounds(40, 88, 50, 24);		
		sg.setBounds(110,88,50,24);
		sz.setBounds(190,88,50,24);
		
		buttonGanzi.setBounds(120, 115, 70, 60);
		
		main.add(nzhu);
		main.add(ng);
		main.add(nz);
		
		main.add(yzhu);
		main.add(yg);
		main.add(yz);
		
		main.add(rzhu);
		main.add(rg);
		main.add(rz);
		
		main.add(szhu);
		main.add(sg);
		main.add(sz);
		main.add(buttonGanzi);
	}
	
	/**
	 * ��ʾ��壬��ʾ����������
	 */
	private void initLunar() {
		if (this.html == null) return;
		clr.set(this.model.getSelectYear(), this.model.getSelectMonth(), this.model.getSelectDay());
		lunar = new Lunar(clr.getTimeInMillis());
		StringBuffer htm = new StringBuffer();
		htm.append("<html><div style='margin:5 auto;' align='center' margin='10'><font color='blue' size='4'><b>" + 
			lunar.getSolarYear() + "��" +
			lunar.getSolarMonth() + "��" + 
			lunar.getSolarDay() + "��</b></font></div>");
		htm.append("<div style='margin:1 auto;' align='center'><font size='4' color='green'><b>����" + 
				this.model.titles[lunar.getDayOfWeek()-1] + "</b></font></div>");
		htm.append("<div style='margin:5 auto;' align='center'><font size='3' color='red'><b>" + 
				lunar.getLunarYear() + "��" +				
				lunar.getLunarMonthString() + "��" +
				lunar.getLunarDayString() + "</b></font></div>");		
		
		htm.append("<div style='margin:2 auto;' align='center'>"+lunar.getCyclicaYear()+"��<br>");
		htm.append(lunar.getCyclicaMonth()+"��<br>");
		htm.append(""+lunar.getCyclicaDay()+"��<br>");
		htm.append(""+lunar.getCyclicaHour()+"ʱ<br>");
		//htm.append(""+Lunar.Animals[lunar.getDeqiD()]+"�ճ�" + Lunar.Animals[(lunar.getDeqiD()+6)%12] + "����</div>");

		String tip = "<html>" + lunar.getDescription();
		if (!"".equals(lunar.getLFestivalName()))
			tip += "<br>" + lunar.getLFestivalName();
		if (!"".equals(lunar.getSFestivalName()))
			tip += "<br>" + lunar.getSFestivalName();
		if (lunar.isHoliday())
			tip += "(�ż�)";
		if (!"".equals(lunar.getTermString()))
			tip += "<br>����" + lunar.getTermString();
		if (lunar.isBlackFriday())
			tip += "<br>��ɫ������";
		
		this.html.setText(htm.toString());
		this.html.setToolTipText(tip + "</html>");
	}

	/**
	 * ������һ�����һ���ѡ���·�ʱ��������¼�
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		int num = 0;
		if (this.arrowUp.equals(o)) {   //��һ��
			num = this.model.getSelectYear();
			if(num>=MAXYEAR) num=MAXYEAR-1;			
			this.tf.setText(String.valueOf(++num));
			this.model.setSelectYear(num);
		} else if (this.arrowDown.equals(o)) {  //��һ��
			num = this.model.getSelectYear();
			if(num<=MINYEAR) num=MINYEAR+1;
			this.tf.setText(String.valueOf(--num));
			this.model.setSelectYear(num);
		} else if (this.arrowUph.equals(o)) {   //��һСʱ
			num = Integer.valueOf(hf.getText());
			if(num>=23) num=-1;			
			this.hf.setText(String.valueOf(++num));
		} else if (this.arrowDownh.equals(o)) {  //��һСʱ
			num = Integer.valueOf(hf.getText());
			if(num<=0) num=24;
			this.hf.setText(String.valueOf(--num));
		} else if (this.arrowUpm.equals(o)) {   //��һ����
			num = Integer.valueOf(mf.getText());
			if(num>=59) num=-1;			
			this.mf.setText(String.valueOf(++num));
			reDo(5);
			return;
		} else if (this.arrowDownm.equals(o)) {  //��һ����
			num = Integer.valueOf(mf.getText());
			if(num<=0) num=60;
			this.mf.setText(String.valueOf(--num));
			reDo(5);
			return;
		} else if (this.select.equals(o)) {  //�·ݸı�
			this.model.setSelectMonth(this.select.getSelectedIndex());			
			this.model.fireTableDataChanged();
			this.initLunar();
			reDo(2);
			return;
		}
		this.model.fireTableDataChanged();
		this.initLunar();
		reDo(0);   //�������
	}
	
	/**
	 * Ϊ���/Сʱ/���Ӽ�һ��ֵ�ı�����¼�
	 */
	class FocusListener extends FocusAdapter {
		public void focusGained(final FocusEvent arg0) {
			((JTextField)arg0.getComponent()).selectAll();
		}
		public void focusLost(FocusEvent e) {
			if(e.getSource().equals(tf)) {
				String syear = tf.getText();
				int iyear = 0 ;
				try{
					iyear = Integer.valueOf(syear);
					if(iyear<1900 ) iyear = MINYEAR;														
					if(iyear>9999) iyear = MAXYEAR;
					tf.setText(iyear+"");
					CalendarForm2.this.model.setSelectYear(iyear);
				}catch(Exception exception) {
					tf.setText(CalendarForm2.this.model.getSelectYear()+"");
				}
				CalendarForm2.this.model.fireTableDataChanged();
				CalendarForm2.this.initLunar();
				reDo(1);   //�������
			}else if(e.getSource().equals(hf)) {
				String shour = hf.getText();
				int ihour = 0 ;
				try{
					ihour = Integer.valueOf(shour);
					if(ihour<0 ) ihour = 0;														
					if(ihour>23) ihour = 23;
					hf.setText(ihour+"");
				}catch(Exception exception) {
					hf.setText(CalendarForm2.this.model.getCurrentHour()+"");
				}
				reDo(4);   //�������
			}else if(e.getSource().equals(mf)) {
				String sminute = mf.getText();
				int iminute = 0 ;
				try{
					iminute = Integer.valueOf(sminute);
					if(iminute<0 ) iminute = 0;														
					if(iminute>59) iminute = 59;
					mf.setText(iminute+"");
				}catch(Exception exception) {
					mf.setText(CalendarForm2.this.model.getCurrentMinute()+"");
				}
				reDo(5);   //������֣������ܱ������
			}			
		}
	};

	/**
	 * ֧�ֳ�������ʽ
	 */
	class LinkLabel extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			setText(e,true);
		}
		public void mouseExited(MouseEvent e) {
			setText(e,false);
		}
		public void mouseClicked(MouseEvent e) {
			CalendarForm2.this.model.setSelectYear(CalendarForm2.this.model.getCurrentYear());
			CalendarForm2.this.model.setSelectMonth(CalendarForm2.this.model.getCurrentMonth());
			CalendarForm2.this.model.setSelectDay(CalendarForm2.this.model.getCurrentDay());
			CalendarForm2.this.tf.setText(CalendarForm2.this.model.getCurrentYear()+"");
			CalendarForm2.this.select.setSelectedIndex(CalendarForm2.this.model.getCurrentMonth());
			CalendarForm2.this.hf.setText(CalendarForm2.this.model.getCurrentHour()+"");
			CalendarForm2.this.mf.setText(CalendarForm2.this.model.getCurrentMinute()+"");
			
			CalendarForm2.this.model.fireTableDataChanged();
			CalendarForm2.this.initLunar();
			reDo(0);  //Ҫ�������
		}
		public void initText(JLabel o) {
			o.setText("<html><div style='vertical-align:top;' ><font color=blue><U>" + CalendarForm2.this.LINKTEXT +"</U></font></div></html>");
		}
		private void setText(MouseEvent e, boolean b) {
			JLabel o = (JLabel)e.getComponent();			
			if (!b)
				o.setText("<html><div style='vertical-align:top;'><font color=blue><U>" + CalendarForm2.this.LINKTEXT +"</U></font></div></html>");
			else
				o.setText("<html><div style='vertical-align:top;' ><font color=blue><U><b>" + CalendarForm2.this.LINKTEXT +"</b></U></font></div></html>");
		}
	}
	
	/**
	 * �������е���굥���¼���˫���¼�
	 * ����ѡ��
	 * ˫������
	 */
	Timer mouseTimer = null;
	class TableMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {			
			final int day = model.mapValue(table.getSelectedRow(), table.getSelectedColumn());
			if (day < 0) return;
			
//			if (e.getClickCount() == 1) {
//				mouseTimer = new Timer(350, new ActionListener() {
//					public void actionPerformed(ActionEvent evt) {
							model.setSelectDay(day);
							initLunar();				
							reDo(3);  //Ҫ�������
//						mouseTimer.stop();
//					}
//				});
//				mouseTimer.restart();
//			} else if (e.getClickCount() == 2 && mouseTimer.isRunning()) {
//				mouseTimer.stop();
//				CalendarForm2.this.setVisible(false);
//			}			
		}
	}
	/**
	 * �����������
	 * Ϊ��ֹ�ظ���������¼�����Ҫ�ж�frame��������ʱ���������ѡ��ֵ��һ������Ҫ����
	 * 0������frame�����е�������ʱ��ֵ��Ȼ��������֣�
	 * 1��2��3��4���ֱ�Ϊ�ꡢ�¡��ա�ʱ�������ͬ���ø��º���֣�����Ҫ����frame��ֵ��Ҫ������֣�
	 * 5��Ϊ�֣���Ҫ����frame��ֵ������Ҫ��֣�
	 * ��˫��Ҷ��ȫ������������ʱ����Ӹ�֧�л���������ʱ��Ҳ��Ҫ�������е�������ʱ�������鷳
	 */
	private void reDo(int type) {
		if(frame == null) return;
		
		int iyear = this.model.getSelectYear();
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
	
	private int forUpdateGanzi(int gan,int zi) {
		int index =  gan%2==1?(zi+1)/2:zi/2;
		//System.out.println("gan="+gan+";zi="+zi+";index="+index);
		return index;
	}
	
	/**
	 * ���µ�ǰ�ĸ�֧���
	 * ��һ���ã��ͻ��Զ�����֧�ĸı䣬���õ����Ҳ���֧������
	 */
	public void updateGanzi(int ng1,int nz1,int yg1,int yz1,int rg1,int rz1,int sg1,int sz1) {
		showWhich(false);
		//1=1,3=2,5=3;   2=1,4=2,6=3,8=4,10=5,12=6
		ng.setSelectedIndex(ng1);  
		nz.setSelectedIndex(forUpdateGanzi(ng1,nz1));
		yg.setSelectedIndex(yg1);
		yz.setSelectedIndex(forUpdateGanzi(yg1,yz1));
		rg.setSelectedIndex(rg1);
		rz.setSelectedIndex(forUpdateGanzi(rg1,rz1));
		sg.setSelectedIndex(sg1);
		sz.setSelectedIndex(forUpdateGanzi(sg1,sz1));
	}
	
	//���µ�ǰ��ʱ�����
	public void updateDate(int year,int month,int day,int hour,int minute) {
		showWhich(true);
		this.model.setSelectYear(year);
		this.model.setSelectMonth(month);
		this.model.setSelectDay(day);		
		
		//���¸����ظ���������¼����Ǹ����⣬��Ҫ�ж�frame��������ʱ���������ѡ��ֵ��һ������Ҫ����
		this.tf.setText(year+"");
		this.select.setSelectedIndex(month-1);		
		this.hf.setText(hour+"");
		this.mf.setText(minute+"");
		
		CalendarForm2.this.model.fireTableDataChanged();
		CalendarForm2.this.initLunar();  //��Ҫ�Ǹ���html��ʾ���
	}
		
	public void setParent(JPanel frame) {
		this.frame = (QiMenFrame)frame;
	}

	private void showWhich(boolean bool) {
		select.setVisible(bool);
		tf.setVisible(bool);
		arrowUp.setVisible(bool);
		arrowDown.setVisible(bool);
		th.setVisible(bool);
		table.setVisible(bool);
		hLabel.setVisible(bool);
		hf.setVisible(bool);
		arrowUph.setVisible(bool);
		arrowDownh.setVisible(bool);
		mLabel.setVisible(bool);
		mf.setVisible(bool);		
		arrowUpm.setVisible(bool);
		arrowDownm.setVisible(bool);
		timeLabel.setVisible(bool);
		time.setVisible(bool);
		html.setVisible(bool);		
		
		nzhu.setVisible(!bool);
		yzhu.setVisible(!bool);
		rzhu.setVisible(!bool);
		szhu.setVisible(!bool);
		ng.setVisible(!bool);
		nz.setVisible(!bool);
		yg.setVisible(!bool);
		yz.setVisible(!bool);
		rg.setVisible(!bool);
		rz.setVisible(!bool);
		sg.setVisible(!bool);
		sz.setVisible(!bool);
		buttonGanzi.setVisible(!bool);
		
		//ȱʡ��ȡ����
		html.grabFocus();
		html.requestFocus();
		html.requestFocusInWindow();
	}
	
	// �õ��ɻ�֧��������
	private JComboBox getSelectOfGanzi(String[] gz, ActionListener adapter) {		 
		JComboBox combo = new JComboBox(gz);		
		Dimension d = combo.getPreferredSize();
		d.width = 30;
		d.height = 25;
		combo.setMaximumSize(d);
		combo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		combo.setSelectedIndex(1);
		combo.addActionListener(adapter);
		return combo;
	}
	/**
	 * ��֧�����򵥻��¼�
	 */
	class GanziMouseAdapter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JComboBox o = (JComboBox) e.getSource();				
			int index = o.getSelectedIndex();			
			if(index<=0) return;  //Ϊ��Ч���
			//System.out.println("��ǰo="+o.getName()+";index="+index);
      if(o.getName().equals(ng.getName())) {
      	//System.out.println("ng="+ng.getName()+";index="+index);
      	nz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          nz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	nz.setSelectedIndex(1);
      }else if(o.getName().equals(yg.getName())) {
      	//System.out.println("yg="+yg.getName()+";index="+index);
      	yz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          yz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	yz.setSelectedIndex(1);
      }else if(o.getName().equals(rg.getName())) {
      	//System.out.println("rg="+rg.getName()+";index="+index);
      	rz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          rz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	rz.setSelectedIndex(1);
      }else if(o.getName().equals(sg.getName())) {
      	//System.out.println("sg="+sg.getName()+";index="+index);
      	sz.removeAllItems();
      	for (int i = 0; i < 7; i++) {
          sz.addItem(index%2==0 ? lunar.Deqi2[i] : lunar.Deqi1[i]);
        }
      	sz.setSelectedIndex(1);
      }
      //����ŵ�������£����򱣴�ʱ�Ͳ�����ֵ.�ڸ�֧����ʱҲ������ã���ֹ��һ�β�ѡ��ֱ�����
      updateFrameganzi();
		}
	}
	private void updateFrameganzi() {
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
	/**
	 * html��嵥���¼�
	 */
	class GanziAdapter extends MouseAdapter {
  	public void mouseClicked(MouseEvent e) {  		
  		if(e.getClickCount()==2) {
  			showWhich(false);
  		}
  	}
	}
	
	public static void main(String[] args) {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		new CalendarForm2().setVisible(true);
	}
}