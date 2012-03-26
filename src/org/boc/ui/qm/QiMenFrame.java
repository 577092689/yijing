package org.boc.ui.qm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.boc.calendar.ui.CalendarForm;
import org.boc.dao.DaoPublic;
import org.boc.db.Calendar;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;
import org.boc.db.qm.QimenFloatToolbar;
import org.boc.delegate.DelQiMenMain;
import org.boc.event.qm.QmClickListener;
import org.boc.event.qm.TipMouseMotionAdaption;
import org.boc.ui.BasicJPanel;
import org.boc.ui.Main;
import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.Debug;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.util.VoQiMen;

public class QiMenFrame extends BasicJPanel {
	private boolean isYun = true; // �Ƿ������£���Ϊ�������룬�Ƿ����������������ã���Զ������
	private boolean isYin = false; // �Ƿ��������������������룬��Զ������
	private boolean isBoy = true; // �Ƿ����к���Ĭ����
	private int iZf = QiMen2.ZF ? 1 : 2; // ת�̻���̣�Ĭ��ת��Ϊ1����Ϊ2
	private int iZy = QiMen2.RB ? 1 : 2; // �����𲹣�Ĭ�ϲ�Ϊ2������Ϊ1
	private int iZfs = QiMen2.TD ? 1 : 2; // Сֵ��ʹ��Ĭ��������
	private int iJigong = QiMen2.KG ? 2 : 8; // �Ĺ���, ȱʡֵΪ2����������Ϊ2�����ް˹�Ϊ8

	private int yydunNum; // ���ݻ����ݵľ���
	private int ysNum = 3; // �����Ӧ���������ֱ�����1��2��3ʱ4
  private int isheng=-1;                //ʡ�����
  private int ishi=-1;                  //�е����  
  
	private int ng, nz, yg, yz, rg, rz, sg, sz;
	private int year, month, day, hour, minute, second;
	private String mzText; // �����ĸ�֧��������ʽ��Ϊ1993;1992; |1,1; 2,2;����ʽ������ת������ʱ�ٴ���	

	private DaoPublic pub;
	private DelQiMenMain qm;
	private QimenInputPanel inputPanel;  //���Ÿ߼���������
	private JToolBar toolbar; 	// ���Ź�����
	private JToolBar inputToolBar; 	//�߼��������
	private java.util.Calendar clr;
	
	private String fileId;
	private String rowId;
	private String parentNode;
	private String memo;
	private VoQiMen vo;

	public QiMenFrame() {
		qm = new DelQiMenMain();
		this.setLayout(new BorderLayout());
		pub = new DaoPublic();		
		qm.setJigong(iJigong); // �������幬�ĺι�		
		
		inputPanel = new QimenInputPanel(this);
		toolbar = new QimenFloatToolbar(this).getFloatToolbar();	
		inputToolBar = inputPanel.getInputPanel();
		ResultPanel rp = new ResultPanel();		// ����һ�������ʾ���				
		this.setResultPane(rp); 
		if(QiMen2.TOOL) addTool(toolbar);
		if(QiMen2.INPUT) addInput(inputToolBar);
		this.setListner(rp);  //ע������¼�
		
		Box box = new Box(BoxLayout.Y_AXIS);		
		box.add(rp);
		this.add(box, BorderLayout.CENTER);
	}
	
	public QimenInputPanel getQimenInputPanel() {
		return this.inputPanel;
	}
	public DelQiMenMain getDelQiMenMain() {
		return this.qm;
	}
	public JToolBar getToolbar() {
		return this.toolbar;
	}
	public JToolBar getInputpanel() {
		return this.inputToolBar;
	}

	public void init(String fileId, String rowId, String parentNode1) {
		this.fileId = fileId;
		this.rowId = rowId;
		vo = (VoQiMen) Public.getObjectFromFile(fileId, rowId);
		if (vo != null) {
			mzText = vo.getMingzhu();
			ysNum = vo.getYongshen();

			isYun = vo.isIsYun();
			isBoy = vo.isIsBoy();
			isYin = vo.isIsYin();

			iZf = vo.getIZf();
			iZy = vo.getIZy();
			iZfs = vo.getIZfs();
			iJigong = vo.getIqt1(); // ���幬�ĺι�

			ng = vo.getNg();
			nz = vo.getNz();
			yg = vo.getYg();
			yz = vo.getYz();
			rg = vo.getRg();
			rz = vo.getRz();
			sg = vo.getSg();
			sz = vo.getSz();

			year = vo.getYear();
			month = vo.getMonth();
			day = vo.getDay();
			hour = vo.getHour();
			minute = vo.getMinute();

			isheng = vo.getIsheng();
			ishi = vo.getIshi();
			yydunNum = vo.getYydunNum();
			memo = vo.getMemo();		
			
			//System.out.println("���� �� "+vo.getFileId()+":"+vo.getRowId()+"::vo!=null ::::��ng="+ng+";nz="+nz+";year="+year);
		}		
		if (parentNode1 == null) {
			if (vo != null)
				parentNode1 = vo.getParent();
		}
		this.parentNode = parentNode1;		

		// ��һ�δ�ʱ����Ĭ�ϱ��浱ǰ��ʱ�䣬����ʼ���̣�������ʾ���������
		if (vo == null) {			
			initData();
			initDate();
		}
			
		pan(false);
		this.getResultPane().getTextPane().roll20();
		//System.out.println(memo);
		update1();
		update2(QiMen2.CALENDAR ? 1 : 2);
		update3();
	}
	
	//����ʱ�������¼����壬��ע��Ϣ���
	public void update1() {
		//1. ͬ�������������		
		if(inputPanel.isVisible()) 
			inputPanel.update(isBoy,mzText,isheng,ishi,ysNum,yydunNum);		
		//2. ���±�ע��Ϣ���
		if(inputPanel.getMemoWin()!=null && inputPanel.getMemoWin().isVisible())
			inputPanel.showMemo();
	}
	/**
	 * 
	 * @param isShow: 0: ������������1��Ϊ��ʾ��2��Ϊ����
	 */
	public void update2(int isShow) {		
		//����ʱ�����
		CalendarForm cal = ((QmClickListener)clickListner).getCalendarForm();
		if(isShow!=0) {
			cal.setVisible(isShow==1);
		}
		if(clickListner!=null && cal.isVisible()) {		
			if (ng!=0) {//2.1 ����Ǹ�֧����Ҫ������嵽��֧��ʽ
				cal.updateGanzi(ng,nz,yg,yz,rg,rz,sg,sz);
				return;
			}
			if(isYin) {  //2.2 �����ũ����Ҫ�ȼ��㣬���ʱ��û�е�����ְ�ť��ʱ��û�л��㡣������������֣����Զ�������ֵ�
				//Messages.info("ũ���� "+Calendar.YEARP+"-"+Calendar.MONTHP+"-"+Calendar.DAYP+" "+hour+":"+minute);
				pan(false);
				cal.updateDate(Calendar.YEARP,Calendar.MONTHP,Calendar.DAYP,hour,minute);
			}else{  //2.3 �ǹ�������ֱ�Ӹ���
				//Messages.info("������"+year+"-"+month+"-"+day+" "+hour+":"+minute);
				cal.updateDate(year,month,day,hour,minute);  //�������ʾΪ��ǰ���ڵ�˫����ʱ��
			}
		}
	}
	
	//����Сֵ�����𲹵�
	public void update3() {
		QiMen2.ZF = this.iZf==1;
		QiMen2.RB = this.iZy==1;
		QiMen2.TD = this.iZfs==1;
		QiMen2.KG = this.iJigong==2;
		Main.setStatusInfo(Public.QM);
	}

	/**
	 * ���������Ϣ do1:"���һ�������ڵĸ�����" do2:"��ֶ������µ�ȫ��ɾ����" do3:"��������" do4: "��ѧ" do5: "����"
	 * do6: "����" do7: "����" do8: "����"
	 * 
	 * @return JPanel
	 */
	// �����а�ť���ã�����Ҫ����Ϊfalse������һ��˫������ʱ����Ĭ��Ϊ��ǰʱ�䣬��Ҫ����һ��
	public String pan(boolean isSave) {
//	Messages.info("year="+year+"; month="+month+"; day="+day+"; hour="+hour+"; minute="+minute+
//	";\r\n isYin="+ isYin+"; isYun="+isYun+"; isheng="+isheng+"; ishi="+ishi+"; isBoy="+isBoy+
//	"\r\nng="+ng+"; nz="+nz+"; yg="+yg+"; yz="+yz+"; rg="+rg+"; rz="+rz+"; sg="+sg+"; sz="+sz+
//	"\r\n vo.getNg="+(vo!=null ? vo.getNg():vo));
		// �ȱ��棬������
		if (!saving(isSave))
			return null;
		qm.setJigong(iJigong); // �������幬�ĺι�
		if (ng==0)
			return qm.getGeju1(getResultPane(), year, month, day, hour, minute,
					ysNum, isYin, isYun, isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum,mzText);
		return qm.getGeju1(getResultPane(), ng, nz, yg, yz, rg, rz, sg, sz, ysNum, isYun,
				isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum,mzText);
	}

	// ������������ʱ��ImageMouseListener�е��ô˷���
//	public void pan(int y1, int m1, int d1, int h1) {
//		if (y1 == -1)
//			y1 = year;
//		if (m1 == -1)
//			m1 = month;
//		if (d1 == -1)
//			d1 = day;
//		if (h1 == -1)
//			h1 = hour;
//		// �ȱ��棬������
//		if (!saving(false))
//			return;
//		if (vo == null)
//			return;
//		qm.setJigong(iJigong); // �������幬�ĺι�
//		if (vo.getNg() == 0)
//			qm.getGeju1(getResultPane(), y1, m1, d1, h1, minute, isYin, isYun,
//					isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum);
//		else
//			qm.getGeju1(getResultPane(), ng, nz, yg, yz, rg, rz, sg, sz, isYun,
//					isheng, ishi, isBoy, iZf, iZy, iZfs, yydunNum);
//	}

	private void getParameterAndCheck() {
		Debug.out("month=" + month + "day=" + day + "hour=" + hour + "minute="
				+ minute + "isYin=" + "\r\n" + isYin + "isYun=" + isYun + "isheng="
				+ isheng + "ishi=" + ishi + "isBoy=" + isBoy + "\r\n" + "ng=" + ng
				+ "nz=" + nz + "yg=" + yg + "yz=" + yz + "rg=" + rg + "rz=" + rz
				+ "sg=" + sg + "sz=" + sz + "isYun=" + isYun + "isBoy=" + isBoy);

	}

	private void clear() {
		Calendar.YEARN = 0;
		Calendar.YEARP = 0;
		Calendar.MONTHN = 0;
		Calendar.MONTHP = 0;
		Calendar.DAYN = 0;
		Calendar.DAYP = 0;
		Calendar.HOUR = 0;
	}

	protected Component getThis() {
		return this;
	}

	/**
	 * ����,������水ť�ǣ��ᱣ�浽�ļ���������̰�ťʱ����д�ļ�
	 */
	private boolean saving(boolean iswritefile) {
		if (iswritefile) {
			int rs = Messages.question("��"+fileId+"�ݽڵ�����Ϊ��"+rowId+"���������޸ģ�ȷ��Ҫ���ԣ�"+(ng!=0?"��֧":"ʱ��")+"����ʽ������");
			if (rs == 1)
				return false;
		}		
		if (ng==0) {
			// �����󣬽������vo����
			vo = new VoQiMen(mzText, ysNum, year, month, day, hour, minute, isBoy,
					isYin, isYun, isheng, ishi, iZf, iZy, iZfs, yydunNum, memo);
			//System.out.println("1:"+vo.getFileId() +":"+vo.getRowId()+"::vo.getNg="+vo.getNg()+";ng="+ng+";year="+vo.getYear());
		} else {			
			String checkgz = checkGZ(yg, yz, yydunNum);
			if (checkgz != null) {
				JOptionPane.showMessageDialog(getThis(), checkgz, "��ʾ��Ϣ",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}

			vo = new VoQiMen(mzText, ysNum, ng, nz, yg, yz, rg, rz, sg, sz, isBoy,
					isYun, isheng, ishi, iZf, iZy, iZfs, yydunNum, memo);
			//System.out.println("2:"+vo.getFileId() +":"+vo.getRowId()+"::vo.getNg="+vo.getNg()+";ng="+ng+";year="+vo.getYear());
		}		
		vo.setIqt1(iJigong); // �����ٸ�vo�Ľṹ�ˣ�ֻ����������
		vo.setRowId(rowId);
		vo.setFileId(fileId);
		// ��ȡ��Ŀ¼�� һ��ȡԤ�����ĸ�Ŀ¼����ȡroot��ֵ
		vo.setRoot(Public.valueRoot[1]);
		vo.setParent(parentNode);
		vo.setYcsj(Public.getTimestampValue().toString());

		if (!iswritefile)
			return true; // ��������棬�˴��Ϳ��Է�����		
		Public.writeObject2File(vo);		
		clear();
		//System.out.println(vo.getFileId() +":"+vo.getRowId()+"::ng="+vo.getNg()+";year="+vo.getYear());
		
		return true;
	}
	
  /**
   * Ϊ��ֹ�ظ���ӣ������ж��Ƿ��Ѿ����룬�ڹ���ʱ���벻���ظ�������ڶ�����л�ʱ���룬��϶����ظ�
   * ¼�������Ϣ�Ѿ����£���������Ѿ�����ı���ɾ�������ټ�
   * @param rs
   */
  public void setListner(ResultPanel rs) {
  	MyTextPane pane = rs.getTextPane();

  	//1. �����һ�������˵��¼�
  	MouseListener[] lis1 = pane.getMouseListeners();
  	for(int i=0; i<lis1.length; i++) {
  		if(lis1[i] instanceof QimenPopupMenu) {
  			pane.removeMouseListener(lis1[i]);
  		}
  	}  	
		QimenPopupMenu popmenu = QimenPopupMenu.getSharedInstance();
  	popmenu.setFrame(this);
  	pane.addMouseListener(popmenu);

  	//2. �����һ������ƶ��¼�
  	MouseMotionListener[] lis2 = pane.getMouseMotionListeners();
  	for(int i=0; i<lis2.length; i++) {
  		if(lis2[i] instanceof TipMouseMotionAdaption) {
  			pane.removeMouseMotionListener(lis2[i]);
  		}
  	}  	
  	mouseMotionListener = new TipMouseMotionAdaption(pane,this);
  	if(QiMen2.TIP){	 //Ĭ���ǹرգ�����ʾ��ʾ��Ϣ��
	  	pane.addMouseMotionListener(mouseMotionListener);
	  }
  	
  	//3. ���һ�����˫���¼�
  	clickListner =  new QmClickListener(this);
  	pane.addMouseListener(clickListner);
  }

	/**
	 * ������������ĸ�ʽ�Ƿ���ȷ
	 * 
	 * @return
	 */
	public String checkInputs(String y, String m, String d, String h) {
		return pub.checks(y, m, d, h, isYin, isYun);
	}

	/**
	 * ����֧��ֵĽ�����ѡ���Ƿ�һ�� ���yz=2����ֻ��������ˮ�����أ��������ڵ������ݾ���
	 */
	private String checkGZ(int yg, int yz, int yydunNum) {
		int yz_ = yz;
		yz = yz < 3 ? yz + 10 : yz - 2;
		int[] jies = { yz * 2 - 2 == 0 ? 24 : yz * 2 - 2, yz * 2 - 1, yz * 2 };
		int[] jus = new int[10];
		int k = 0;
		boolean bl = false;
		String _s = "";

		for (int i = 0; i < 3; i++) {
			int[] ju_ = QiMen.yydun[jies[i]];

			for (int m = 1; m < ju_.length; m++) {
				bl = false;
				for (int j = 0; j < jus.length; j++) {
					if (jus[j] != 0 && jus[j] == ju_[m]) {
						bl = true;
						break;
					}
				}
				if (!bl) {
					jus[k++] = ju_[m];
				}
			}
		}

		for (int i = 0; i < jus.length; i++) {
			if (jus[i] == 0)
				continue;
			int _ii = jus[i] <= 0 ? 9 - jus[i] : jus[i];
			_s += QiMen.yydunju[_ii] + ",";
		}
		if (_s.length() > 1)
			_s = _s.substring(0, _s.length() - 1);

		if (yydunNum <= 0) {
			// return "��֧��ֱ���ѡ�����ݻ����ݾ���";
			return null;
		}
		for (int i = 0; i < jus.length; i++) {
			if (jus[i] == 0)
				continue;
			if (jus[i] < 0 && 9 - jus[i] == yydunNum)
				return null;
			if (jus[i] > 0 && jus[i] == yydunNum)
				return null;
		}
		return YiJing.DIZINAME[yz_] + "�±���Ϊ����ĳ�֣�\r\n" + _s;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	private void initDate() {
		java.util.Calendar clr = Public.getCalendarForBeijing();
		year = clr.get(java.util.Calendar.YEAR);
		month = clr.get(java.util.Calendar.MONTH)+1;
		day = clr.get(java.util.Calendar.DAY_OF_MONTH);
		hour = clr.get(java.util.Calendar.HOUR_OF_DAY);
		minute = clr.get(java.util.Calendar.MINUTE);
		second = clr.get(java.util.Calendar.SECOND);
	}
	/**
	 * ��������㣬ǰ��vo��Ϊ�գ�����һ������Ϊ�գ�����Щ�ֶϲ������
	 */
	private void initData() {
		isYun = true; 
		isYin = false; 
		isBoy = true; 
		iZf = QiMen2.ZF ? 1 : 2; 
		iZy = QiMen2.RB ? 1 : 2; 
		iZfs = QiMen2.TD ? 1 : 2; 
		iJigong = QiMen2.KG ? 2 : 8; 

		yydunNum=0; 
		ysNum = 3; 
	  isheng=-1; 
	  ishi=-1; 
	  ng=nz=yg=yz=rg=rz=sg=sz=0;
		mzText="";	
	}

	/**
	 * ���������ʱ���������CalendarForm�ĵ����������ʱ��
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @param h
	 * @param mi
	 */
	public void setInputParameter(int y, int m, int d, int h, int mi,
			boolean run, boolean yin) {
		this.year = y;
		this.month = m;
		this.day = d;
		this.hour = h;
		this.minute = mi;
		this.isYun = run;
		this.isYin = yin;
		this.yydunNum = 0;
	}
	
	//����������ʱ��֧
	public void setInputGanzi(int ng,int nz, int yg,int yz,int rg,int rz,int sg,int sz) {
		this.ng = ng;
		this.yg = yg;
		this.rg = rg;
		this.sg = sg;
		this.nz = nz;
		this.rz = rz;
		this.yz = yz;
		this.sz = sz;
	}

	// type ����Ϊ�棬����Ϊ��
	public boolean isYinli() {
		return this.isYin;
	}
	public void setYinli(boolean yin) {
		this.isYin = yin;
	}
	public boolean isYun() {
		return this.isYun;
	}
	public void setYun(boolean run) {
		this.isYun = run;
	}

	public int getProvince() {
		return this.isheng;
	}
	public void setProvince(int province) {
		this.isheng = province;
	}
	public int getCity() {
		return this.ishi;
	}
	public void setCity(int city) {
		this.ishi = city;
	}
	public boolean isBoy() {
		return this.isBoy;
	}
	public void setBoy(boolean isboy) {
		this.isBoy = isboy;
	}

	public int getZhuanFei() {
		return iZf;
	}

	public void setZhuanFei(boolean iszhuan) {
		this.iZf = iszhuan ? 1 : 2; // תΪ�棬Ϊ1����Ϊ�٣�Ϊ2
	}

	public int getCaiYun() {
		return iZy;
	}

	public void setCaiYun(boolean isrun) {
		this.iZy = isrun ? 1 : 2; // ����Ϊ�棬Ϊ1�� ��Ϊ�٣�Ϊ2��
	}

	public int getXiaozhifu() {
		return iZfs;
	}

	public void setXiaozhifu(boolean istian) {
		this.iZfs = istian ? 1 : 2;
	}

	public int getJigong() {
		return this.iJigong;
	}

	public void setJigong(boolean iskun) {
		this.iJigong = iskun ? 2 : 8;
	}
	public int getJu() {
		return yydunNum;
	}
	public void setJu(int ju) {
		yydunNum = ju;
	}

	public int getYsnum() {
		return this.ysNum;
	}
	public void setYsnum(int num) {
		this.ysNum = num;
	}

	public String getMztext() {
		return mzText;
	}
	public void setMztext(String mz) {
		this.mzText = mz;
	}
	public String getMemo() {
		return this.memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getNg() {
		return ng;
	}

	public void setNg(int ng) {
		this.ng = ng;
	}

	public int getNz() {
		return nz;
	}

	public void setNz(int nz) {
		this.nz = nz;
	}

	public int getYg() {
		return yg;
	}

	public void setYg(int yg) {
		this.yg = yg;
	}

	public int getYz() {
		return yz;
	}

	public void setYz(int yz) {
		this.yz = yz;
	}

	public int getRg() {
		return rg;
	}

	public void setRg(int rg) {
		this.rg = rg;
	}

	public int getRz() {
		return rz;
	}

	public void setRz(int rz) {
		this.rz = rz;
	}

	public int getSg() {
		return sg;
	}

	public void setSg(int sg) {
		this.sg = sg;
	}

	public int getSz() {
		return sz;
	}

	public void setSz(int sz) {
		this.sz = sz;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
}
