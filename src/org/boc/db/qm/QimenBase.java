package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public abstract class QimenBase {
	final String[] YOUQI = {"����","����","����","����"}; //�ո����⣬-2,-1Ϊ������0������1����
	final String[] TDRH1 = {"����ʱ","�õ���","���˺�","������"}; 
	final String[] TDRH2 = {"������ʱ","���õ���","�����˺�","��������"};
	
	DaoQiMen daoqm;
	DaoSiZhuMain daosz;  //������dao
	QimenPublic p;
	String[] my ; //�������˹����е��������
	int index = 0; 				//��������Ϊmy����
	boolean iszf; 	//�Ƿ���ת������

	String mingzhu;  	//����
	int yongshen;  		//�������ͣ����1���¸�2���ո�3��ʱ��4
	int ysgan,yszi;   //����ɡ�֧
	int ystpgong;  		//�������̹�
	int ysdpgong;
	boolean boy;			//�Ƿ���
	boolean yang;			//�Ƿ�������
	
	String[] rgws ,rgjxws ,rgbmws ;  //�ո��乬��ɡ����ǡ�������˥
	String[] rgsanji; 		//�չ��Ƿ������
	String[] rgheju; 			//�õ��ո��Ƿ�Ͼ�
	String[] rgbsjx; 			//�չ���ļ���
	String[] rgjxjx;  		//�չ��Ƿ�ü���
	String[] rgbmjx;   		//�չ��ŵļ���		
	
	int zhishigong ;   	//ֵʹ��
	int zhifugong;      //ֵ����
	int[] mzhu ;      	//�����ĸ���֧����
	int mtpgong ;  			//����
	int mdpgong;				//�����̹�
	int caixing; 				//���ǣ���������֮��
	
	boolean isMenfu; 		//���ŷ���
	boolean isMenfan; 	//���ŷ���
	boolean isXingfu; 	//���Ƿ���
	boolean isXingfan;  //���Ƿ���
	
	int kaimengong ;  //�������ڵĹ�
	int jing1mengong ;
	int jing3mengong ;
	int shengmengong; 
	int simengong;
	int shangmengong;
	int xiumengong;
	int dumengong;
	
	int niantpgong ;  //��������乬
	int niandpgong ;	//��ɵ����乬
	int yuetpgong ;   //�¸����̹�
	int yuedpgong ;   //�¸ɵ��̹�
	int ritpgong ;   //�ո����̹�
	int ridpgong ;   //�ոɵ��̹�
	int shitpgong ;   //ʱ�����̹�
	int shidpgong ;		//ʱ�ɵ��̹�
	int[] rgtptpjy,rgdpdpjy;   //�չ��������ǣ���������
	int[] rgtpdpjy; 	//�չ����̵�������
	int[] rgdptpjy; 	//�չ�������������
	
	int wutpgong ;	   //ʮ�������������乬
	int wudpgong ;
	int jitpgong;
	int jidpgong;
	int xintpgong;
	int xindpgong;
	int rentpgong;
	int guitpgong;
	int rendpgong;
	int guidpgong;
	int gengtpgong;
	int gengdpgong;
	int yitpgong;
	int yidpgong;
	int bingtpgong;
	int bingdpgong;
	int dingtpgong ;		//�������乬
	int dingdpgong ;		//�������乬
	
	int shenwugong ;		//�����乬 
	int shenshegong;
	int shenyingong;
	int shenhugong;
	int shendigong;
	int shentiangong;
	int shenhegong;
	
	int xingpenggong ;	//�����乬	
	int xingchonggong;
	int xingfugong;
	int xingyinggong;
	int xingruigong;
	int xingzhugong;
	int xingxingong;
	int xingqingong;	
	int xingrengong;
	
	protected void init(String mzText1, int ysNum1,boolean boy1, int len) {
		my = new String[len];
		index = 0;
		
		mingzhu = mzText1;
		boy = boy1;
		yongshen = ysNum1;
		String[] ysinfo = p.getYShenInfo(yongshen, 0, 0);		
  	//yshenname = ysinfo[0];
  	ystpgong = Integer.valueOf(ysinfo[1]);  //���������乬
  	ysgan = Integer.valueOf(ysinfo[2]);
  	yszi = Integer.valueOf(ysinfo[3]);
  	ysdpgong = p.getDiGong(ysgan, yszi);
		
		yang = daoqm.getJu()>0;
		
		mzhu = p.getMZhu(mingzhu);
  	mtpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)?p.getTianGong(mzhu[0], mzhu[1]):0;  //����
  	mdpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)?p.getDiGong(mzhu[0], mzhu[1]):0;  //����
  	
  	niantpgong = p.getTianGong(SiZhu.ng, SiZhu.nz);  //��ɹ�
  	niandpgong = p.getDiGong(SiZhu.ng, SiZhu.nz);
  	ritpgong = p.getTianGong(SiZhu.rg, SiZhu.rz);  //�ո����̹�
  	ridpgong = p.getDiGong(SiZhu.rg, SiZhu.rz);  //�ոɵ��̹�
  	yuetpgong = p.getTianGong(SiZhu.yg, SiZhu.yz);  //�¸����̹�
  	yuedpgong = p.getDiGong(SiZhu.yg, SiZhu.yz);  //�¸ɵ��̹�
  	shitpgong = p.getTianGong(SiZhu.sg, SiZhu.sz);  //ʱ�����̹�
  	shidpgong = p.getDiGong(SiZhu.sg, SiZhu.sz);
		
  	rgtpdpjy = p.getDpjy(ritpgong); 		
  	rgdptpjy = p.getTpjy(ridpgong); 
  	rgtptpjy = p.getTpjy(ritpgong);
  	rgdpdpjy = p.getDpjy(ridpgong);
  	
		rgws = p.gettgWS(SiZhu.rg, SiZhu.rz);  //�õ��ո���˥��� -1������0��-2Ϊ��������1Ϊ����
		rgjxws = p.getxingWS(ritpgong);
		rgbmws = p.getmenWS(ritpgong);
		rgbsjx = p.getshenJX(ritpgong);  //��ļ���
		rgsanji = p.getSanji(ritpgong); 	//�Ƿ������
		rgheju = p.isganHeju(SiZhu.rg, SiZhu.rz); //�õ��ո��Ƿ�Ͼ�
		rgjxjx = p.getxingJX(ritpgong);  //�Ƿ�ü���
		rgbmjx = p.getmenJX(ritpgong);   //�ŵļ���	
		
		zhishigong = daoqm.getZhishiGong();   //ֵʹ��
  	zhifugong = daoqm.getZhifuGong(); 
  	
  	kaimengong = p.getMenGong(QiMen.MENKAI);
  	shengmengong = p.getMenGong(QiMen.MENSHENG);
  	jing1mengong = p.getMenGong(QiMen.MENJING1);
  	jing3mengong = p.getMenGong(QiMen.MENJING3);
  	simengong = p.getMenGong(QiMen.MENSI);
  	shangmengong = p.getMenGong(QiMen.MENSHANG);
  	xiumengong = p.getMenGong(QiMen.MENXIU);
  	dumengong = p.getMenGong(QiMen.MENDU);

		wutpgong = p.getTianGong(YiJing.WUG, 0);	
		wudpgong = p.getDiGong(YiJing.WUG, 0);
  	dingtpgong = p.getTianGong(YiJing.DING, 0);
  	dingdpgong = p.getDiGong(YiJing.DING, 0);
  	bingtpgong = p.getTianGong(YiJing.BING, 0);
  	bingdpgong = p.getDiGong(YiJing.BING, 0);
  	yitpgong = p.getTianGong(YiJing.YI, 0);
  	yidpgong = p.getDiGong(YiJing.YI, 0);
  	gengtpgong = p.getTianGong(YiJing.GENG, 0);
  	gengdpgong = p.getDiGong(YiJing.GENG, 0);
  	jitpgong = p.getTianGong(YiJing.JI, 0);
  	jidpgong = p.getDiGong(YiJing.JI, 0);
  	xintpgong = p.getTianGong(YiJing.XIN, 0);
  	xindpgong = p.getDiGong(YiJing.XIN, 0);
  	rentpgong = p.getTianGong(YiJing.REN, 0);
  	rendpgong = p.getDiGong(YiJing.REN, 0);
  	guitpgong = p.getTianGong(YiJing.GUI, 0);
  	guidpgong = p.getDiGong(YiJing.GUI, 0);
  		
  	shenwugong = p.getShenGong(QiMen.SHENWU);
  	shenhegong = p.getShenGong(QiMen.SHENHE);
  	shenshegong = p.getShenGong(QiMen.SHENSHE);
  	shenyingong = p.getShenGong(QiMen.SHENYIN);
  	shenhugong = p.getShenGong(QiMen.SHENHU);
  	shendigong = p.getShenGong(QiMen.SHENDI);
  	shentiangong = p.getShenGong(QiMen.SHENTIAN);
  	
  	xingpenggong = p.getXingGong(QiMen.XINGPENG); 
  	xingchonggong = p.getXingGong(QiMen.XINGCHONG);
  	xingfugong = p.getXingGong(QiMen.XINGFU);
  	xingyinggong = p.getXingGong(QiMen.XINGYING);
  	xingruigong = p.getXingGong(QiMen.XINGRUI);
  	xingzhugong = p.getXingGong(QiMen.XINGZHU);
  	xingxingong = p.getXingGong(QiMen.XINGXIN);
  	xingqingong = p.getXingGong(QiMen.XINGQIN);
  	xingrengong = p.getXingGong(QiMen.XINGREN);
  	
  	caixing = daoqm.gInt[2][1][shengmengong];
  	
  	isMenfu = p.isMenFu(); //���ŷ���
		isMenfan = p.isMenFan(); //���ŷ���
		isXingfu = p.isXingFu(); //���Ƿ���
		isXingfan = p.isXingFan();  //���Ƿ���
	}
	/**
	 * д�뻺������
	 * @param istrue: ���Ϊ�棬��д���ַ���
	 * @param hasKong : �Ƿ�ӿո��������4��
	 */
	protected void w(boolean istrue, String s) {
  	w(istrue,s,false);
  }
	protected void w(boolean istrue, String s,boolean hasKong) {
  	if(istrue) w(s, hasKong);
  }
	protected void w(String s,boolean hasKong) {
  	my[index++] = (hasKong?"    ":"")+s;
  }
	protected void w(String s) {
  	w(s,false);
  }
	/**
	 * �õ���1����2֮��������ˡ����������ˡ��Ⱥ�֮��Ĺ�ϵ������
	 * @param x1
	 * @param x2
	 * @param rs
	 * @return
	 */
  String getXingRel(String name1, String name2, int x1, int x2, String[] rs) {
		String res = null;
		//String name1 = "["+QiMen.jx1[x1]+"]";
		//String name2 = "["+QiMen.jx1[x2]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"��"+name2+"��"+rs[0]+"��";
		else if(YiJing.WXDANKE[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"��"+name2+"��"+rs[1]+"��";
		else if(YiJing.WXDANSHENG[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"��"+name1+"��"+rs[2]+"��";
		else if(YiJing.WXDANKE[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"��"+name1+"��"+rs[3]+"��";
		else  res=name1+"��"+name2+"�Ⱥͣ�"+rs[4]+"��";
		
		return res;
	}
  /**
   * �õ�ĳ����g1��g2֮���������ˡ�����������ˡ�ͬ�����Ⱥ�֮��Ĺ�ϵ����
   * @param g1
   * @param name1
   * @param g2
   * @param name2
   * @param rs
   * @return
   */
  String getGongRel(int g1, String name1, int g2, String name2, String[] rs) {
		String t = null;
		if(p.isSheng(g1, g2)) t=name1+"��"+g1+"����"+name2+"����"+g2+"����"+rs[0]+"��";
		else if(p.isChongke(g1, g2)) t=name1+"��"+g1+"�����"+name2+"����"+g2+"����"+rs[1]+"��";
		else if(p.isSheng(g2,g1)) t=name2+"��"+g2+"����"+name1+"����"+g1+"����"+rs[2]+"��";
		else if(p.isChongke(g2,g1)) t=name2+"��"+g2+"�����"+name1+"����"+g1+"����"+rs[3]+"��";
		else if(g1==g2) t=name1+"��"+name2+"ͬ��"+g2+"����"+rs[4]+"��";
		else if(p.isBihe(g1,g2)) t=name1+"��"+g1+"����"+name2+"����"+g2+"���Ⱥͣ�"+rs[5]+"��";
		return t;
	}
  /**
   * �õ���֮�������ˡ����������ˡ��Ⱥ�֮��Ĺ�ϵ����
   * @param g1
   * @param g2
   * @param rs
   * @return
   */
  String getGongRel(int g1, int g2, String[] rs) {
		String res = null;
		String name1 = "["+QiMen.dpjg[g1]+"]";
		String name2 = "["+QiMen.dpjg[g2]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jgwh[g1]][QiMen.jgwh[g2]]!=0) res=name1+"��"+name2+"��"+rs[0]+"��";
		else if(YiJing.WXDANKE[QiMen.jgwh[g1]][QiMen.jgwh[g2]]!=0) res=name1+"��"+name2+"��"+rs[1]+"��";
		else if(YiJing.WXDANSHENG[QiMen.jgwh[g2]][QiMen.jgwh[g1]]!=0) res=name2+"��"+name1+"��"+rs[2]+"��";
		else if(YiJing.WXDANKE[QiMen.jgwh[g2]][QiMen.jgwh[g1]]!=0) res=name2+"��"+name1+"��"+rs[3]+"��";
		else  res=name1+"��"+name2+"�Ⱥͣ�"+rs[4]+"��";
		
		return res;
	}
}