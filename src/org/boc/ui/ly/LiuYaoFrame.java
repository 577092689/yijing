package org.boc.ui.ly;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JToolBar;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.Calendar;
import org.boc.db.ly.Liuyao;
import org.boc.db.qm.QiMen2;
import org.boc.delegate.DelYiJingMain;
import org.boc.event.ly.LyClickListener;
import org.boc.event.ly.LyTipMouseMotion;
import org.boc.ui.BasicJPanel;
import org.boc.ui.Main;
import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.util.VoLiuYao;

public class LiuYaoFrame extends BasicJPanel{
	private String mzhu;			//�����ĸ�֧��������ʽ��Ϊ1993|1992; ���� 1,1|2,2;����ʽ������ת������ʱ�ٴ���
	private int ng,nz,yg,yz,rg,rz,sg,sz;
  public int year,month,day,hour,minute,second;
  
  public boolean isYun = true;         //�Ƿ�������
  public boolean isYin= false;         //�Ƿ�������
  public boolean isBoy = true;         //�Ƿ����к�
 
  private int up;      					//��������
  private int down;      				//��������
  private int yshen;   					//����
  private int mode=Liuyao.SJDZ;	//���Է�ʽ��ȱʡ����ǰʱ��+��ݵ�֧����
  private int isheng=-1;           //ʡ�����
  private int ishi=-1;             //�е����  
  private String acts;  				//��س������,�ָ����ַ���
  
  private DaoYiJingMain daoly;  
  private DelYiJingMain delly;
  private DaoCalendar daocal;
  private LiuyaoPublic pub;
  private LiuyaoInputPanel inputPanel;
  private JToolBar toolbar; 			// ��س������
	private JToolBar inputToolBar; 	//�߼��������
  public VoLiuYao vo;

  private String fileId;
  private String rowId;
  private String parentNode;
  private String memo;  

  public LiuYaoFrame() {
    this.setLayout(new BorderLayout());
    delly = new DelYiJingMain();
    pub = delly.getLiuyaoPublic();
    pub.setBoy(isBoy);
    daocal = delly.getDaoCalendar();
    daoly = delly.getDaoYiJingMain();           
    
    /**
     * ��ʼ˳����Ҫ��1�����������ʾ��� 2��ע�����ʱ����˫���¼����ƶ���ʾ�����˵���3�������߼���塢������
     */
    ResultPanel rp = new ResultPanel();		// ����һ�������ʾ���				
		this.setResultPane(rp); 
		
		this.setListner(rp);  //ע������¼�
		
    inputPanel = new LiuyaoInputPanel(this);
		toolbar = new LiuyaoFloatToolbar(this).getFloatToolbar();	
		inputToolBar = inputPanel.getInputPanel();
		
		if(Liuyao.TOOL) addTool(toolbar);
		if(Liuyao.INPUT) addInput(inputToolBar);		
		
		Box box = new Box(BoxLayout.Y_AXIS);		
		box.add(rp);
		this.add(box, BorderLayout.CENTER);
  }

  public void init(String fileId, String rowId, String parentNode1) {
  	this.fileId = fileId;
    this.rowId = rowId;
    vo = (VoLiuYao) Public.getObjectFromFile(fileId, rowId);
    if(vo!=null) {
    	mode = vo.getQgfs();	//���Է�ʽ 
    	mzhu = vo.getMzhu();
			yshen = vo.getYs();

			isYun = vo.isIsYun();
			isBoy = vo.isIsBoy();
			isYin = vo.isIsYin();

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
			second = vo.getSecond();

			isheng = vo.getIsheng();
			ishi = vo.getIshi();			
			memo = vo.getMemo();		
     }     
     if(parentNode1==null) {
       if(vo!=null)
         parentNode1=vo.getParent();
     }
     this.parentNode = parentNode1;
     
     // ��һ�δ�ʱ����Ĭ�ϱ��浱ǰ��ʱ�䣬����ʼ���̣�������ʾ���������
 		 if (vo == null) {
 			initData();
 			initDate();
 		 }
 			
 		pan();
 		this.getResultPane().getTextPane().roll20();
 		
 		update1();
 		update2();
 		update3();
  }

  /**
   * ���������Ϣ
   */
  public void pan() {  
    //1. �õ���س����
    int[] chgs = pub.getActs(acts);
    
    //2. ���ܺ������Է�ʽ�����ն��ǰ����ԡ����ԡ���س��װ��
    delly.getGuaXiang(this.getResultPane(),mode,yshen,mzhu,
    		isBoy,isheng,ishi,
    		year,month,day,hour,minute,
    		isYin,isYun,
        up,down, chgs);
//    this.getResultPane().getTextPane().setText(rs);
//    this.getResultPane().getTextPane().roll20();
//    return rs;
  }
  
  /**
	 * ����,������水ť�ǣ��ᱣ�浽�ļ���������̰�ťʱ����д�ļ�
	 */
	public boolean save() {
		int rs = Messages.question("��"+fileId+"�ݽڵ�����Ϊ��"+rowId+"���������޸ģ�ȷ��Ҫ���ԣ�"+(ng!=0?"��֧":"ʱ��")+"����ʽ������");
		if (rs == 1)
			return false;
		
		VoLiuYao vo = new VoLiuYao(mode, yshen,up, down, 
          acts, new int[]{0,ng,nz,yg,yz,rg,rz,sg,sz},
          year, month, day, hour, minute,
          isYin, isYun,mzhu,isheng,ishi);
		vo.setRowId(rowId);
		vo.setFileId(fileId);
		//��ȡ��Ŀ¼�� һ��ȡԤ�����ĸ�Ŀ¼����ȡroot��ֵ
		vo.setRoot(Public.valueRoot[6]);  //�˴�����س��ֵ
		vo.setParent(parentNode);
		vo.setYcsj(Public.getTimestampValue().toString());
		vo.setMemo(memo);
		Public.writeObject2File(vo);
		clear();
		//System.out.println("��̨͵͵�����ˡ�");
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
  	LiuyaoPopupMenu popmenu = LiuyaoPopupMenu.getSharedInstance();
  	popmenu.setFrame(this);
  	pane.addMouseListener(popmenu);

  	//2. �����һ������ƶ��¼�
  	mouseMotionListener = new LyTipMouseMotion(pane,this);
  	if(Liuyao.TIP){	 //Ĭ���ǹرգ�����ʾ��ʾ��Ϣ��
	  	pane.addMouseMotionListener(mouseMotionListener);
	  }
  	
  	//3. ���һ�����˫���¼�
  	clickListner =  new LyClickListener(this);
  	pane.addMouseListener(clickListner);
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
  
	//����ʱ�������¼����壬��ע��Ϣ���
	public void update1() {
		//1. ͬ�������������		
		if(inputPanel.isVisible()) 
			inputPanel.update(isBoy,mzhu,isheng,ishi,yshen,mode);		
		//2. ���±�ע��Ϣ���
		if(inputPanel.getMemoWin()!=null && inputPanel.getMemoWin().isVisible())
			inputPanel.showMemo();
	}
	/**
	 * 
	 * @param isShow: 0: ������������1��Ϊ��ʾ��2��Ϊ����
	 */
	public void update2() {		
		//����ʱ�����
		LyCalendarForm cal = ((LyClickListener)clickListner).getCalendarForm();
		if(clickListner!=null && cal.isVisible()) {		
			if (ng!=0) {//2.1 ����Ǹ�֧����Ҫ������嵽��֧��ʽ
				cal.updateGanzi(ng,nz,yg,yz,rg,rz,sg,sz);
				return;
			}
			if(isYin) {  //2.2 �����ũ����Ҫ�ȼ��㣬���ʱ��û�е�����ְ�ť��ʱ��û�л��㡣������������֣����Զ�������ֵ�
				//Messages.info("ũ���� "+Calendar.YEARP+"-"+Calendar.MONTHP+"-"+Calendar.DAYP+" "+hour+":"+minute);
				pan();
				cal.updateDate(Calendar.YEARP,Calendar.MONTHP,Calendar.DAYP,hour,minute);
			}else{  //2.3 �ǹ�������ֱ�Ӹ���
				//Messages.info("������"+year+"-"+month+"-"+day+" "+hour+":"+minute);
				cal.updateDate(year,month,day,hour,minute);  //�������ʾΪ��ǰ���ڵ�˫����ʱ��
			}
		}
	}
	
	//�������������ʾ
	public void update3() {
		Main.setStatusInfo(Public.LY);
	}
	
	/**
	 * ��������㣬ǰ��vo��Ϊ�գ�����һ������Ϊ�գ�����Щ�ֶϲ������
	 */
	private void initData() {
		isYun = true; 
		isYin = false; 
		isBoy = true; 
		up=0; 
	  down=0;
	  yshen=0;
	  mode=Liuyao.SJDZ;	
	  acts="";
	  isheng=-1; 
	  ishi=-1; 
	  ng=nz=yg=yz=rg=rz=sg=sz=0;
		mzhu="";	
	}
  
  /** ��ʼ��ʱ��Ϊ��ǰ������ʱ�� */
  private void initDate() {
		java.util.Calendar clr = Public.getCalendarForBeijing();
		year = clr.get(java.util.Calendar.YEAR);
		month = clr.get(java.util.Calendar.MONTH)+1;
		day = clr.get(java.util.Calendar.DAY_OF_MONTH);
		hour = clr.get(java.util.Calendar.HOUR_OF_DAY);
		minute = clr.get(java.util.Calendar.MINUTE);
		second = clr.get(java.util.Calendar.SECOND);
	}

	/** ���������ʱ���������CalendarForm�ĵ����������ʱ�� */
	public void setInputParameter(int y, int m, int d, int h, int mi, boolean run, boolean yin) {
		this.year = y;
		this.month = m;
		this.day = d;
		this.hour = h;
		this.minute = mi;
		this.isYun = run;
		this.isYin = yin;
	}
	
	/** ����������ʱ��֧ */
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
	public String getMzhu() {
		return mzhu;
	}
	public void setMzhu(String mzhu) {
		this.mzhu = mzhu;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public boolean isYun() {
		return isYun;
	}
	public void setYun(boolean isYun) {
		this.isYun = isYun;
	}
	public boolean isYin() {
		return isYin;
	}
	public void setYin(boolean isYin) {
		this.isYin = isYin;
	}
	public boolean isBoy() {
		return isBoy;
	}
	public void setBoy(boolean isBoy) {
		pub.setBoy(isBoy);
		this.isBoy = isBoy;
	}
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public int getYshen() {
		return yshen;
	}
	public void setYshen(int yshen) {
		this.yshen = yshen;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getActs() {
		return acts;
	}
	public void setActs(String acts) {
		this.acts = acts;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getSheng() {
		return isheng;
	}
	public void setSheng(int isheng) {
		this.isheng = isheng;
	}
	public int getShi() {
		return ishi;
	}
	public void setShi(int ishi) {
		this.ishi = ishi;
	}
  public LiuyaoInputPanel getLiuyaoInputPanel() {
		return inputPanel;
	}
	public DelYiJingMain getDelYiJingMain() {
		return delly;
	}
	public JToolBar getToolbar() {
		return this.toolbar;
	}
	public LiuyaoInputPanel getInputpanel() {
		return this.inputPanel;
	}
	public JToolBar getInputToolbar() {
		return this.inputToolBar;
	}

  public void finalize() {
  	delly = null;
    daocal = null;
    daoly = null;
    vo = null;
  }
}
