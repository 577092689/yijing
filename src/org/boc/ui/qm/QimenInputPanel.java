package org.boc.ui.qm;

import static org.boc.ui.UIPublic.getInputWindow;
import static org.boc.ui.UIPublic.getLinkText;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.boc.biz.qm.QMBusiness;
import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.Calendar;
import org.boc.db.qm.QiMen;
import org.boc.ui.MyTextPane;
import org.boc.ui.UIPublic;
import org.boc.ui.UIPublic.NormalInputWindow;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.Messages;
import org.boc.util.PrintWriter;
import org.boc.util.Public;

public class QimenInputPanel extends JPanel{
	private HtmlMultiLineControl html;
	private QiMenFrame frame;
	private DaoQiMen daoqm;
	private DaoSiZhuMain daosz;
	private PrintWriter pw = new PrintWriter();
	private NormalInputWindow memoWin;			//��ע����
	private JDialog ruleWin;								//����¼�봰��
	private MyTextPane ruleTextPane;				//���������
	private InputFocusListener inputFocus;
	private QMBusiness qmbiz;  							//���Ź�������
	
	private boolean isBoy;  
  private JTextField textMZhu;    //������Ϊ1977;1988;�ȶ����ݻ�1,1;2,2;�ȶ����֧
  private JTextField textNum;			//�������
  private JTextField textHour;		//��ʱ�����
  private JComboBox comboyyDunJu;    //�����ݾ�
  private JComboBox comboYShen;    //�����ꡢ�¡��ա�ʱ
  private JComboBox comboRule;    	//�������涨��
  private ButtonGroup groupNanLv;    //�л�Ů
  private JComboBox comboSheng;      //ʡ
  private JComboBox comboShi;        //��
  private JRadioButton radioBoy;      //��
  private JRadioButton radioGirl;     //Ů
  private String mzText;  //�����ĸ�֧��������ʽ��Ϊ1993;1992; |1,1; 2,2;����ʽ������ת������ʱ�ٴ���
  private int iprovince = -1;  
  private int icity = -1;
  private int ruleIndex = 0;  			//��������ѡ���¼
  private int yydunNum;              //���ݻ����ݵľ���
  private int ysNum; 								//�����Ӧ���������ֱ�����1��2��3ʱ4  
  private String[] sheng;
  private String[] shi;
  
  public static final int Height = 25;
  public static final int Width = 140;
  public static final int Width2 = 80;
  
	public QimenInputPanel(QiMenFrame frame){
		this.frame = frame;
		daoqm = frame.getDelQiMenMain().getDaoQiMen();
		daosz = frame.getDelQiMenMain().getDaoSiZhuMain();
		
		isBoy = frame.isBoy();
		iprovince = frame.getProvince();
		icity = frame.getCity();
		yydunNum = frame.getJu();
		ysNum = frame.getYsnum();
		html = new HtmlMultiLineControl();
		inputFocus = new InputFocusListener();
		//����ע��Ϣ
		memoWin = (NormalInputWindow)getInputWindow("��ע��Ϣ",
				QimenPopupMenu.WIDTH1,QimenPopupMenu.HEIGHT1 ,true, false, false);
		//�رձ�ע����ʱ�����ñ��水ť
		try {
			memoWin.setCloseCallback(this, this.getClass().getMethod("saveMemo"));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public NormalInputWindow getMemoWin() {
		return this.memoWin;
	}
	public JToolBar getInputPanel() {	
		MouseAdapter genderAdpater = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
					frame.setBoy(e.getSource()==radioBoy);
			}
		};
				
		Box box9 = new Box(BoxLayout.X_AXIS);
		JLabel linkTime = null;
		try {
			linkTime = getLinkText("������壺",this,this.getClass().getMethod("showCalendar"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		box9.add(linkTime);
		box9.add(new JLabel("ѡ��������֧..."));
		
		Box box1 = new Box(BoxLayout.X_AXIS);
		if(this.isBoy) {
      radioBoy = new JRadioButton("��", true);
      radioGirl = new JRadioButton("Ů");
    }else{
      radioBoy = new JRadioButton("��");
      radioGirl = new JRadioButton("Ů", true);
    }
    radioBoy.addMouseListener(genderAdpater);
    radioGirl.addMouseListener(genderAdpater);
    box1.add(new JLabel("�Ա�"));
    groupNanLv = new ButtonGroup();
    groupNanLv.add(radioBoy);
    groupNanLv.add(radioGirl);  
    box1.add(radioBoy);
    box1.add(radioGirl);
    
    FocusAdapter focusAdapter = new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				((JTextField)e.getComponent()).selectAll();
			}
			public void focusLost(FocusEvent e) {
				String mz = ((JTextField)e.getComponent()).getText();
				checkMZ(mz);
				frame.setMztext(mz);
			}
		};
    Box box2 = new Box(BoxLayout.X_AXIS);
		JLabel labelMing = new JLabel("������");
		box2.add(labelMing);
    textMZhu = new JTextField(4);
    Dimension d = textMZhu.getPreferredSize(); 
		d.width = Width;
		d.height = Height;
		textMZhu.setMaximumSize(d);
    textMZhu.setText(mzText);
    textMZhu.addFocusListener(focusAdapter);
    box2.add(textMZhu);
    textMZhu.setToolTipText(html.CovertDestionString("��Ԥ�����������ѧʱ����Ҫͬʱָ��������"+
    		"<BR>����ͬʱָ�����������ʱ����֧���ɣ�<BR>��ʱ����ʽ����1977|1986������֧��ʽ�綡����ͱ�������Ϊ��4,6|3,5����"));
    textMZhu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
    Box box3 = new Box(BoxLayout.X_AXIS);
    box3.add(new JLabel("����"));
    box3.add(this.getComboYShen());
    
    Box box4 = new Box(BoxLayout.X_AXIS);
    box4.add(new JLabel("���᣺"));
    box4.add(this.getComboShengShi());
    
    Box box5 = new Box(BoxLayout.X_AXIS);
    box5.add(new JLabel("�þ֣�"));
    box5.add(this.getComboYYDunJu());
    
    Box box6 = new Box(BoxLayout.X_AXIS);
		JLabel labelNum = new JLabel("������֣�");
		box6.add(labelNum);
    textNum = new JTextField(4);
    d = textNum.getPreferredSize(); 
		d.width = Width-20;
		d.height = Height;
		textNum.setMaximumSize(d);
		textNum.addFocusListener(inputFocus);
		box6.add(textNum);
		textNum.setToolTipText(html.CovertDestionString("������������ǰʱ����֣�"));
		textNum.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		Box box7 = new Box(BoxLayout.X_AXIS);
		JLabel labelHour = new JLabel("ʱ����֣�");
		box7.add(labelHour);
    textHour = new JTextField(4);
    d = textHour.getPreferredSize(); 
		d.width = Width-20;
		d.height = Height;
		textHour.setMaximumSize(d);
		textHour.addFocusListener(inputFocus);
		box7.add(textHour);
		textHour.setToolTipText(html.CovertDestionString("������������ǰ�����ռ���������Ӧ��ʱ������֣�"));
		textHour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		Box box8 = new Box(BoxLayout.X_AXIS);
		JLabel linkLable = null;
		try {
			linkLable = getLinkText("��ע��Ϣ��",this,this.getClass().getMethod("showMemo"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		box8.add(linkLable);
		box8.add(new JLabel("����������Ϣ..."));
		
		Box box10 = new Box(BoxLayout.X_AXIS);
		box10.add(new JLabel("�������涨�ƣ�"));
		box10.add(this.getComboRule());

    JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    box1.setAlignmentX(LEFT_ALIGNMENT);
    box2.setAlignmentX(LEFT_ALIGNMENT);
    box3.setAlignmentX(LEFT_ALIGNMENT);
    box4.setAlignmentX(LEFT_ALIGNMENT);
    box5.setAlignmentX(LEFT_ALIGNMENT);
    box6.setAlignmentX(LEFT_ALIGNMENT);
    box7.setAlignmentX(LEFT_ALIGNMENT);
    box8.setAlignmentX(LEFT_ALIGNMENT);
    box9.setAlignmentX(LEFT_ALIGNMENT);
    box10.setAlignmentX(LEFT_ALIGNMENT);
    
    toolBar.add(box9);
    toolBar.addSeparator();
    toolBar.add(box1);  //�Ա�
    toolBar.addSeparator();
    toolBar.add(box2);	//����
    toolBar.addSeparator();
    toolBar.add(box4);	//������
    toolBar.addSeparator();
    toolBar.add(box3);	//����
    toolBar.addSeparator();
    toolBar.add(box5);	//ѡ��
    toolBar.addSeparator();
    toolBar.add(box6);	//�������
    toolBar.addSeparator();
    toolBar.add(box7);	//��ʱ�����
    toolBar.addSeparator();
    toolBar.add(box8);	//��ע��Ϣ
    toolBar.addSeparator();
    toolBar.add(box10);	//��������Ϣ

    toolBar.setFloatable(true);
    toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return toolBar;
	}
	//��ʾ�������
	public void showCalendar() {
		frame.update2(1);
	}
	
	/**
	 * ��ʾ��ע��Ϣ
	 * �����ı�ע��Ϣ���ڹر�ʱ����ʵ��setVisible(false)
	 */	
	public void showMemo() {		
		MyTextPane text = memoWin.getTextpane();
		Document doc = text.getDocument();
		try {
			doc.remove(0, doc.getLength());
			pw.setDocument(doc);			
			pw.sblue(frame.getMemo());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		text.roll20();  //������һ��
		memoWin.setVisible(true);
	}
	//һ���ص��������رձ�ע����ʱ�����ñ��水ť
	public void saveMemo() {
		frame.setMemo(memoWin.getTextpane().getText());
		frame.pan(true);
	}
	
	/**
	 * ��QimenFrame������
	 */
	public void update(boolean isBoy,String mzText, 
			int isheng, int ishi, int ysNum, int yydunNum){
		radioBoy.setSelected(isBoy);
		radioGirl.setSelected(!isBoy);
		textMZhu.setText(mzText);
		comboSheng.setSelectedIndex(isheng+1);
		comboShi.setSelectedIndex(ishi+1);
		comboYShen.setSelectedIndex(ysNum);
		comboyyDunJu.setSelectedIndex(yydunNum);
	}
	
	private Box getComboYYDunJu() {
    Box box = new Box(BoxLayout.X_AXIS);
    int i = 0;

    comboyyDunJu = new JComboBox(QiMen.yydunju);
    Dimension d = comboyyDunJu.getPreferredSize(); 
		d.width = Width; 
		d.height = Height;
		comboyyDunJu.setMaximumSize(d);
    comboyyDunJu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    comboyyDunJu.setToolTipText(html.CovertDestionString("Ĭ�ϲ���ѡ���ɳ����Զ��жϵ�ǰ������һ�֡�<BR>"+
		"����֧¼��ʱ�����жϲ��ô���ʱ�������ֶ�ָ����һ�֣�"));
    comboyyDunJu.setSelectedIndex(yydunNum);
    comboyyDunJu.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        int index = ((JComboBox) e.getSource()).getSelectedIndex();
        frame.setJu(index);// 0-18
        frame.pan(false);
        //JOptionPane.showMessageDialog(getThis(),String.valueOf(yydunNum), "����",JOptionPane.INFORMATION_MESSAGE);
      }
    });
    box.add(comboyyDunJu);

    return box;
  }
	
  /**
   * ѡ�������޷��ꡢ�¡��ա�ʱ
   * ����
   * ����
   * ����
   * ��������
   * �Ź�
   * @return
   */
  private Box getComboYShen() {
    Box box = new Box(BoxLayout.X_AXIS);
    int i = 0;

    comboYShen = new JComboBox(QiMen.yongshen);
    Dimension d = comboYShen.getPreferredSize(); 
    d.width = Width;
		d.height = Height;
		comboYShen.setMaximumSize(d);
    comboYShen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
    comboYShen.setToolTipText(html.CovertDestionString("Ĭ���ո�Ϊ���񣬵����Ǳ��ˣ���λΪ����ʱ����ʱ���ѡ�������ˡ�"+
		"<BR>���ѡ����ɡ��¸ɡ��ոɡ�ʱ�ɡ�һ�������������š����ŵȣ�������������ģ�顣"));
    comboYShen.setSelectedIndex(ysNum);
    comboYShen.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        int index = ((JComboBox) e.getSource()).getSelectedIndex();
        frame.setYsnum(index);// 4~1
        //JOptionPane.showMessageDialog(getThis(),String.valueOf(ysNum), "����",JOptionPane.INFORMATION_MESSAGE);
      }
    });
    box.add(comboYShen);

    return box;
  }
  /**
   * �������涨��
   * @return
   */
  private Box getComboRule() {
    Box box = new Box(BoxLayout.X_AXIS);

    comboRule = new JComboBox(QiMen.rules);
    Dimension d = comboRule.getPreferredSize(); 
    d.width = Width;
		d.height = Height;
		comboRule.setMaximumSize(d);
		comboRule.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		comboRule.setToolTipText(html.CovertDestionString("�����Լ��Ĺ����������������㣡"));
		comboRule.setSelectedIndex(0);
		comboRule.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
      	ruleIndex = ((JComboBox) e.getSource()).getSelectedIndex();
      	//Messages.question("ruleIndex="+ruleIndex+";"+QiMen.rules[ruleIndex]);
        showRuleWindow();
      }
    });
    box.add(comboRule);

    return box;
  }
  
  private boolean checkMZ(String s) {
  	String info1 = "��������ݻ��֧��ʾ�������|�ָ����磺��1977�ݻ��1977|1989�ݻ��4,6�ݻ��4,6|9,9�ݣ�";
  	String info2 = "��ݱ�����"+Calendar.IYEAR+"��"+Calendar.MAXYEAR+"֮�䣡";
  	String info3 = "��ɱ�����1��10֮�䣬��֧������1��12֮�䣬�Լס���Ϊ1�������ƣ�";
  	
  	if(s==null || "".equals(s.trim())) return true;
  	if(s.split(",").length>2 && s.split(QiMen.FUHAOYI).length<2) {  //"2,2|1,1".split(",")=3
  		Messages.question(info1);
  		textMZhu.setFocusable(true);
  		textMZhu.setText("");
			return false;
  	}
  	
  	String[] mz = s.split(QiMen.FUHAOYI);
  	for(String text : mz) {
  		String[] gz = text.split(QiMen.FUHAODOT);
  		if(gz.length==1) {
  			try{
  				int year = Integer.valueOf(text);
  				if(year>Calendar.MAXYEAR || year<Calendar.IYEAR) {
  					Messages.question(info2);
  					textMZhu.setFocusable(true);
  					textMZhu.setText("");
  					return false;
  				}
  			}catch(Exception e) {
  				Messages.question(info1);
  				textMZhu.setFocusable(true);
  				textMZhu.setText("");
  				return false;
  			}
  		}else{
				try{
  				int gan = Integer.valueOf(gz[0]);
  				int zi = Integer.valueOf(gz[1]);
  				if(gan>10 || gan<1) {
  					Messages.question(info3);
  					textMZhu.setFocusable(true);
  					textMZhu.setText("");
  					return false;
  				}
  				if(zi>12 || zi<1) {
  					Messages.question(info3);
  					textMZhu.setFocusable(true);
  					textMZhu.setText("");
  					return false;
  				}
				}catch(Exception e) {					
  				Messages.question(info3);
  				textMZhu.setFocusable(true);
  				textMZhu.setText("");
  				return false;
  			}				
			}
  	}
  	
  	return true;
  }
  
  /**
   * �õ�ʡ�е�������
   * @return
   */
  public Box getComboShengShi() {
    Box box = new Box(BoxLayout.X_AXIS);
    int i = 0;
    sheng = null;
    shi = null;
    //���ж���ʡ����Ϊ��������ǿյ�
    for (i = 0; i < Calendar.cityname.length; i++) {
      if (Calendar.cityname[i][0] == null)break;
    }
    sheng = new String[i + 1];
    for (i = 0; i < sheng.length; i++) {
      sheng[i] = Calendar.cityname[i][0];
    }

    comboSheng = new JComboBox(sheng);
    comboSheng.setToolTipText(html.CovertDestionString("������̫��ʱ��Ĭ��Ϊ����ʱ�䣬���õ�����<BR>��������ѡ��ʡ�ݡ�"));
    comboSheng.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    comboSheng.insertItemAt("    ", 0);
    Dimension d = comboSheng.getPreferredSize(); 
		d.width = QimenInputPanel.Width2;
		d.height = QimenInputPanel.Height;
		comboSheng.setMaximumSize(d);
    if (iprovince >= 0) {
      comboSheng.setSelectedIndex(iprovince + 1);
      shi = Calendar.cityname[iprovince];
      comboShi = new JComboBox(shi);
      comboShi.insertItemAt("    ", 0);
      comboShi.setSelectedIndex(iprovince + 1);
    }
    else {
      comboSheng.setSelectedIndex(0);
      shi = new String[] {
          "    "};
      comboShi = new JComboBox(shi);
      comboShi.setSelectedItem("");
      d = comboShi.getPreferredSize(); 
      d.width = QimenInputPanel.Width2;
  		d.height = QimenInputPanel.Height; 
  		comboShi.setMaximumSize(d);
    }

    comboSheng.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int index = ( (JComboBox) e.getSource()).getSelectedIndex();
        if (index > 0) {
          comboShi.removeAllItems();
          comboShi.addItem("    ");
          shi = Calendar.cityname[index - 1];
          for (int i = 0; i < shi.length; i++) {
            comboShi.addItem(shi[i]);
          }
        }
        else {
          comboShi.removeAllItems();
          comboShi.addItem("    ");
        }
        frame.setProvince(index - 1);

      }
    });

    comboShi.setToolTipText(html.CovertDestionString("������̫��ʱ��Ĭ��Ϊ����ʱ�䣬���õ�����<BR>��������ѡ����У���������ѡ��ʡ�ݡ�"));
    comboShi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    comboShi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setCity(( (JComboBox) e.getSource()).getSelectedIndex() - 1);
      }
    });

    box.add(comboSheng);
    box.add(comboShi);

    return box;
  }
  
  class InputFocusListener implements FocusListener{
  	
		public void focusGained(FocusEvent e) {
			JTextField text = (JTextField)e.getSource();
			text.selectAll();
		}
		public void focusLost(FocusEvent e) {
			//1. �õ���������
			JTextField text = (JTextField)e.getSource();
			text.setForeground(Color.BLUE);
			text.setFont(new Font("����",Font.BOLD,16));
			String snum = text.getText();
			if(snum.trim().equals("")) return;
			int num = 0;
			try{
				num = Integer.valueOf(snum);
			}catch(Exception exp) {
				text.setText("���ֱ����");
				text.setForeground(Color.RED);
			}
			if(num<0) {
				text.setText("����0�����");
				text.setForeground(Color.RED);
			}			
			
			//2. ��������ʱ��
			if(text.equals(textNum)) {
				//��ʱĬ��ʱ��Ϊ��ǰʱ��������ʧȥ���㽫���±�����ֳ���
				runNumber(text,num);
			} else if(text.equals(textHour)) {
				runHour(text,num);
			}
			
	    frame.pan(false);
		}
		
		/**
		 * ��һ���ı�����ת������
		 * @param snum
		 * @return
		 */
		private int procTextnumber(String snum) {
			if(snum.trim().equals("")) return 0;
			int num = 0;
			try{
				num = Integer.valueOf(snum);
			}catch(Exception exp) {
				return 0;
			}
			if(num<0) {
				return 0;
			}		
			return num;
		}
		
		//�ɵ�ǰʱ�����������Ϊʱ����֣�1������ʱ��2���ǳ�ʱ�� 1=0 2=2 3=4 4=6
		private void runHour(JTextField text, int num) {
			num = num%12==0 ? 12:num%12;
//			java.util.Calendar clr = Public.getCalendarForBeijing();
//	  	int y = clr.get(java.util.Calendar.YEAR) ; 
//	  	int m = clr.get(java.util.Calendar.MONTH)+1;
//	  	int d = clr.get(java.util.Calendar.DAY_OF_MONTH);
//	  	int h = num*2 - 2;
			int y = frame.getYear();
			int m = frame.getMonth();
			int d = frame.getDay();
			int h = num*2 - 2;
	  	
	    frame.setInputParameter(y, m, d, h, 1, true, false); //trueΪ��������������ֻ������������壬falseΪ����
	    	  	
	  	//����б������Ա���Ϊ����
	  	int junum = procTextnumber(textNum.getText());
	  	if(junum!=0) {
	  		junum = junum%9==0 ? 9:junum%9;
	  	//2. ����Ҳ�Ե�ǰ�ֵ�������Ϊ׼���������Ե�ǰʱ��Ϊ׼�ˡ����ûѡ�֣���ǰ����ȻΪ��ǰʱ��
	  		int ju = daoqm.getJu() > 0 ? junum : 9+junum;
		    frame.setJu(ju);
	  	}
		}
		//�ɵ�ǰʱ��õ���ǰ�ξ֣����ɱ�����Ϊ�������
		private void runNumber(JTextField text, int num) {							
			//1. ����ǰ���ڣ���ʱ�����������ѡ���ʱ�䣬����Ӧ����frame��Ϊ׼ 
//			java.util.Calendar clr = Public.getCalendarForBeijing();
//	  	int y = clr.get(java.util.Calendar.YEAR) ; 
//	  	int m = clr.get(java.util.Calendar.MONTH)+1;
//	  	int d = clr.get(java.util.Calendar.DAY_OF_MONTH);
//	  	int h = clr.get(java.util.Calendar.HOUR_OF_DAY) ;	  	
//	  	int mi = clr.get(java.util.Calendar.MINUTE);
			int y = frame.getYear();
			int m = frame.getMonth();
			int d = frame.getDay();
			int h = frame.getHour();
			int mi = frame.getMinute();
	  	
	  	//2. �������ʱ����Ϊ�գ��Ա���ʱ������ΪСʱ���ӣ���=0ʱ0�֣���ʱΪ2ʱ0�֣���Ϊ4ʱ0��
	  	int junum = procTextnumber(textHour.getText());
	  	if(junum!=0) {
	  		junum = junum%12==0 ? 12:junum%12;
	  		h = (junum-1)*2;
	  		mi = 0;
	  	}
	  	
	  	//2. ����Ҳ�Ե�ǰ�ֵ�������Ϊ׼���������Ե�ǰʱ��Ϊ׼�ˡ����ûѡ�֣���ǰ����ȻΪ��ǰʱ��
//	  	daosz.getDateOut(y, m, d, h, mi, false, true, true, 0, 0);
//	    daoqm.getHead1(QiMen2.ZF?1:2, QiMen2.RB?1:2, QiMen2.TD?1:2, 0); //1ת 2�� 1������ 0��
	  	//3. ��������������
			num = num%9==0 ? 9:num%9;
			int ju = daoqm.getJu() > 0 ? num : 9+num;			
			//trueΪ��������������ֻ������������壬falseΪ����
	    frame.setInputParameter(y, m, d, h, mi, true, false); 
	    //������setInputParameter֮������ju�����򲻻���Ч
	    frame.setJu(ju);
		}
	}
  /**
   * ��ʾ����¼�봰��ʱ
   * 1. ��ʼ������
   * 2. ��ʼ������
   */
  private void showRuleWindow() {
  	if(ruleWin==null) {
  		ruleWin = new RuleWindow();  		
  	}
  	ruleWin.setTitle(QiMen.rules[ruleIndex]+"�������涨��");
		Document doc = ruleTextPane.getDocument();
		try {
			doc.remove(0, doc.getLength());
			pw.setDocument(doc);		
			if(ruleIndex>0) {
				String rs = readRuleFromFile(ruleIndex);
				if(rs==null) return;
				pw.sblack(rs);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		ruleTextPane.roll20();  
		if(!ruleWin.isVisible()) ruleWin.setVisible(true);
  }
  
  /**
   * �ر�ʱ�����������
   * �ļ��еĻ��з���\n������Ҫ��\r\n�滻�������������
   */
  private void saveRule2File(int fileIndex) {
  	BufferedWriter fw = null;	
  	String sText = ruleTextPane.getText().replaceAll("\r\n", "\n");
  	try {
  		fw = new BufferedWriter(new FileWriter(getRuleFile(fileIndex)));
  		//System.out.println(sText);
			fw.write(sText);
			//System.out.println(sText.replaceAll("\r\n", ""));
			fw.flush();
		} catch (IOException e) {			
			e.printStackTrace();
			try { fw.close(); } catch (IOException e1) {}
		}
  }
  /**
   * �ӹ����ļ���ȡ
   * ��Ϊ��readLine�������Բ���������з���Ҫ�Լ�����
   */
  private String readRuleFromFile(int fileIndex) {
  	BufferedReader fr = null;
  	StringBuilder sb = new StringBuilder();
  	String sLine = null;  	
  	File file = getRuleFile(fileIndex);
  	if(!file.exists()) {
  		Messages.info(file.getPath()+"�����ڣ��������µĹ����ļ���");
  		return "";
  	}
  	try {
  		fr = new BufferedReader(new FileReader(file));
			while((sLine = fr.readLine())!=null) {
				//if(sLine.trim().equals("")) continue;
				//System.out.println(sLine);
				sb.append(sLine+"\r\n");
				//sb.append(sLine.replaceAll("\n", "\r\n"));
			}
		} catch (IOException e) {			
			e.printStackTrace();
			try { fr.close(); } catch (IOException e1) {}
		}
  		
  	return sb.toString();
  }
  /**
   * ���ö�ȡ���ļ����
   * ��QiMen.rules��˳��
   */
  private File getRuleFile(int fileIndex) {
  	return new File(Public.QMGZDZ+"/"+QiMen.rules[fileIndex]+".txt");
  }
  private File getRuleFile() {
  	return getRuleFile(ruleIndex);
  }
  private void initQMBusiness(){
  	if(qmbiz==null)
  		qmbiz = new QMBusiness(frame.getDelQiMenMain().getQimenPublic());  //���Ź�������
  }
  
  /**
   * ���Ź�����
   */
  private void checking() {
  	initQMBusiness();
  	String checkMsg = qmbiz.checkRules(ruleTextPane.getText());
  	ruleWin.setAlwaysOnTop(false);
  	Messages.error(checkMsg);
  	ruleWin.setAlwaysOnTop(true);
  }
  /**
   * ���Ź���������
   */
  NormalInputWindow runRs = (NormalInputWindow)UIPublic.getInputWindow("���н��", 600, 300, true, false, false);
  MyTextPane runText = runRs.getTextpane();
  private void running() {
  	initQMBusiness();
  	String rsMsg = qmbiz.runRules(ruleTextPane.getText());
  	ruleWin.setAlwaysOnTop(false);
  	try {
			runRs.setCloseCallback(this, getClass().getMethod("showRuleWin"),null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}  	
		Document runDoc = runText.getDocument();
		try {
			runDoc.remove(0, runDoc.getLength());
			pw.setDocument(runDoc);			
			pw.sblue(rsMsg);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		runText.roll20();  //������һ��
		runRs.setVisible(true);		 
  }
  /**
   * ���й��򣬷��ؽ������Ҫ���ⲿ��������
   */
  public String runRule(int fileIndex) {
  	initQMBusiness();
  	String rs = readRuleFromFile(fileIndex);
  	if(rs==null) return null;
  	return qmbiz.runRules(rs);  	
  }
  
  public void showRuleWin() {
  	ruleWin.setAlwaysOnTop(true);
  }
  
  /**
   * �����ƴ���
   */
  class RuleWindow extends JDialog {
  	public RuleWindow() {
	  	Box box = new Box(BoxLayout.Y_AXIS);
	  	ruleTextPane = new MyTextPane();        
	    JScrollPane jScrollPane = new JScrollPane(ruleTextPane);        
	    box.add(jScrollPane, BorderLayout.CENTER);   
	    
	    Box box2 = new Box(BoxLayout.X_AXIS);
	    JButton check = new JButton("�﷨���");
	    check.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					checking();
				}
			});
	    JButton run = new JButton("������");
	    run.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					running();
				}
			});
	    JButton save = new JButton("����");
	    save.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					ruleWin.setAlwaysOnTop(false);
					int rs = Messages.question("������ԭ�ļ�"+QiMen.rules[ruleIndex]+".txt����ȷ�ϣ�");
					ruleWin.setAlwaysOnTop(true);
					if (rs == 1) {						
						return;
					}
					saveRule2File(ruleIndex);
				}
			});
	    JButton close = new JButton("�ر�");
	    close.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					close();
				}
			});
	    box2.add(check);
	    box2.add(run);
	    box2.add(save);
	    box2.add(close);
	    box.add(box2);
	    
	    this.getContentPane().add(box,BorderLayout.CENTER);
	    this.setUndecorated(false);  
	    this.setSize(600, 500);  	    
	    this.setLocationRelativeTo(null);  
	    this.setAlwaysOnTop(true);	  	   
  	}
  	
  	public void close() {
  		this.setAlwaysOnTop(false);
  		int rs = Messages.question("���ر�"+QiMen.rules[ruleIndex]+"�����ƴ��ڣ���ȷ�ϣ�");
  		this.setAlwaysOnTop(true);
			if (rs == 1) return;	
			this.setVisible(false);
  	}
    
  	protected void processWindowEvent(WindowEvent e) {
			if(e.getID()==WindowEvent.WINDOW_CLOSING){
				close();
			}
  	}
  }
}
