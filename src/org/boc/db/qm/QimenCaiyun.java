package org.boc.db.qm;

import java.util.ArrayList;
import java.util.List;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenCaiyun {
	private DaoQiMen daoqm;
	private QimenPublic my; 
	private String mingzhu;
	private int yongshen;  //�������ͣ����1���¸�2���ո�3��ʱ��4
	private boolean boy;		
	private String[] m ; //�������˹����е��������
	int n = 0; //��������ΪdescMY����
	String t=null;	
	boolean iszf ;  //�Ƿ�ת������
	
	int ystpgong;  		//���������乬
	int ysdpgong ;    //������̹�
	String yshenname;               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
	int ysgan ,yszi;  //����ɡ�����֧	
	String[] ysheju;  //�����Ƿ�Ͼ�
	String[] ysjxge;  //�����еļ��׸��
	
	int[] mzhu ;      	//�����ĸ���֧����
	int mtpgong ;  			//����
	int zhishigong ;   	//ֵʹ��
	int zhifugong;      //ֵ����
	
	int kaimengong ;  //�������ڵĹ�
	int jing1mengong ;
	int jing3mengong ;
	int shengmengong; 
	int simengong;
	int shangmengong;
	int dumengong;
	int xiumengong;
	
	int niantpgong ;  //��������乬
	int niandpgong ;	//��ɵ����乬
	int nianzigong ;  //�õ���֧�乬
	int yuetpgong ;   //�¸����̹�
	int yuedpgong ;   //�¸ɵ��̹�
	int ritpgong ;   //�ո����̹�
	int ridpgong ;   //�ոɵ��̹�
	int shitpgong ;   //ʱ�����̹�
	int shidpgong ;		//ʱ�ɵ��̹�
	
	int wutpgong ;	   //�������乬
	int wudpgong;
	int jitpgong;
	int jidpgong;
	int dingtpgong ;		//�������乬
	int dingdpgong ;		//�������乬
	int bingtpgong ;		//�������乬
	int bingdpgong ;		//�������乬
	int xintpgong ;		//�������乬
	int xindpgong ;		//�������乬
	int gengtpgong;
	int gengdpgong;
	int yitpgong;
	int yidpgong;
	int guitpgong;
	int guidpgong;
	int rentpgong;
	int rendpgong;
	
	int shenwugong ;		//�����乬
	int shenhegong; 		//�����乬 
	int shenhugong;
	int shenshegong;
	int shenyingong;
	int shendigong;
	int shentiangong;
	
	int xingpenggong ;	//�������乬
	int xingruigong ;	//�������乬
	int xingrengong;
	int xingchonggong;
	int xingfugong;
	int xingyinggong;
	int xingzhugong;
	
	int ty ; //�����ǵ����
	int tygong ;//�õ�ֵ�����̾������ڵĹ�

	public QimenCaiyun(DaoQiMen daoqm, QimenPublic qmmy) {
  	this.daoqm = daoqm;
  	this.my = qmmy;
  }
	
	void init(String mingzhu, int yongshen,boolean boy) {
		m = new String[2500];
		n = 0;
		this.mingzhu = mingzhu;
		this.boy = boy;
		this.yongshen = yongshen;
		
  	String[] ysinfo = my.getYShenInfo(yongshen, 0, 0);		
  	yshenname = ysinfo[0];
  	ystpgong = Integer.valueOf(ysinfo[1]);  //���������乬
  	ysgan = Integer.valueOf(ysinfo[2]);
  	yszi= Integer.valueOf(ysinfo[3]);
  	ysdpgong = my.getDiGong(ysgan,yszi);
  	ysheju=my.isganHeju(ystpgong);       //�����Ƿ�Ͼ�
  	ysjxge = my.getJixiongge(ystpgong);  //�����еļ��׸��
  	
  	mzhu = my.getMZhu(mingzhu);
  	mtpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)?my.getTianGong(mzhu[0], mzhu[1]):0;  //����
  	
  	zhishigong = daoqm.getZhishiGong();   //ֵʹ��
  	zhifugong = daoqm.getZhifuGong();  	
  	zhifugong = my.getJiGong528(zhifugong);  //�˾�ǳ���Ҫ����������
  	
  	niantpgong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //��ɹ�
  	niandpgong = my.getDiGong(SiZhu.ng, SiZhu.nz);
  	nianzigong = my.getDiziGong(SiZhu.nz);  //�õ���֧�乬
  	ritpgong = my.getTianGong(SiZhu.rg, SiZhu.rz);  //�ո����̹�
  	ridpgong = my.getDiGong(SiZhu.rg, SiZhu.rz);  //�ոɵ��̹�
  	yuetpgong = my.getTianGong(SiZhu.yg, SiZhu.yz);  //�¸����̹�
  	yuedpgong = my.getDiGong(SiZhu.yg, SiZhu.yz);  //�¸ɵ��̹�
  	shitpgong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //ʱ�����̹�
  	shidpgong = my.getDiGong(SiZhu.sg, SiZhu.sz);
  	
  	wutpgong = my.getTianGong(YiJing.WUG, 0);	
  	wudpgong = my.getDiGong(YiJing.WUG, 0);
  	jitpgong = my.getTianGong(YiJing.JI, 0);
  	jidpgong = my.getDiGong(YiJing.JI, 0);
  	dingtpgong = my.getTianGong(YiJing.DING, 0);
  	dingdpgong = my.getDiGong(YiJing.DING, 0);
  	bingtpgong = my.getTianGong(YiJing.BING, 0);
  	bingdpgong = my.getDiGong(YiJing.BING, 0);
  	xintpgong = my.getTianGong(YiJing.XIN, 0);
  	xindpgong = my.getDiGong(YiJing.XIN, 0);
  	gengtpgong = my.getTianGong(YiJing.GENG, 0);
  	gengdpgong = my.getDiGong(YiJing.GENG, 0);
  	yitpgong = my.getTianGong(YiJing.YI, 0);
  	yidpgong = my.getDiGong(YiJing.YI, 0);
  	rentpgong = my.getTianGong(YiJing.REN, 0);
  	rendpgong = my.getDiGong(YiJing.REN, 0);
  	guitpgong = my.getTianGong(YiJing.GUI, 0);
  	guidpgong = my.getDiGong(YiJing.GUI, 0);
  	
  	kaimengong = my.getMenGong(QiMen.MENKAI);
  	shengmengong = my.getMenGong(QiMen.MENSHENG);
  	jing1mengong = my.getMenGong(QiMen.MENJING1);
  	jing3mengong = my.getMenGong(QiMen.MENJING3);
  	simengong = my.getMenGong(QiMen.MENSI);
  	shangmengong = my.getMenGong(QiMen.MENSHANG);
  	dumengong = my.getMenGong(QiMen.MENDU);
  	xiumengong = my.getMenGong(QiMen.MENXIU);
  	
  	shenwugong = my.getShenGong(QiMen.SHENWU);
  	shenhegong = my.getShenGong(QiMen.SHENHE);
  	shenhugong = my.getShenGong(QiMen.SHENHU);
  	shenshegong = my.getShenGong(QiMen.SHENSHE);
  	shenyingong = my.getShenGong(QiMen.SHENYIN);
  	shendigong = my.getShenGong(QiMen.SHENDI);
  	shentiangong = my.getShenGong(QiMen.SHENTIAN);
  	
  	xingpenggong = my.getXingGong(QiMen.XINGPENG);
  	xingrengong = my.getXingGong(QiMen.XINGREN);
  	xingchonggong = my.getXingGong(QiMen.XINGCHONG);
  	xingfugong = my.getXingGong(QiMen.XINGFU);
  	xingruigong = my.getXingGong(QiMen.XINGRUI);
  	xingyinggong = my.getXingGong(QiMen.XINGYING);
  	xingzhugong = my.getXingGong(QiMen.XINGZHU);
  	
  	ty = zhifugong; //daoqm.gInt[4][3][zhifugong];
		tygong = my.getXingGong(my.getJiGong528(ty));  //�õ�ֵ�����̾������ڵĹ�
	}
  
	public String getCaiyun(StringBuffer str,String mingzhu, int yongshen,boolean boy, int iszf) {
		init(mingzhu, yongshen, boy);
		this.iszf = iszf!=2;
		m[n++]=my.NOKG+"һ��ȡ����["+yshenname+"]��";
		getMoney1();
		m[n++]=my.NEWLINE;
		
		
		m[n++]=my.NOKG+"�������ֶϣ�";
		getMoney2();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"����������ϣ�";
		List<Integer> ysgong = new ArrayList<Integer>(); //�����������ڵĹ�
		List<String> ysname = new ArrayList<String>(); //������������
		getYShen(ysgong, ysname);
		for(int i=0; i<ysgong.size(); i++) {
			int thegong = ysgong.get(i) == 5 ? 2 : ysgong.get(i);  //�����5��������Ǽ�2��
			m[n++]="------------------------------"+ysname.get(i)+"("+QiMen.BIGNUM[thegong]+"��)------------------------------";
			getMoney31(thegong);
			if(ystpgong==thegong || thegong==shitpgong)  //���Ϊ���񹬺�ʱ�ɹ��������һЩ������ж���Ϣ
				getMoney32(thegong);
			if(ystpgong==thegong) getMoney33(thegong);
		}
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"�ġ���ҵ��Ӫ�ϣ�";
		getMoney40();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"�塢��ս�����ϣ�";
		getMoney50();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"�����������ڶϣ�";
		getMoney4();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"�ߡ�Ͷ�������ϣ�";
		m[n++]="------------------------------����������------------------------------";
		getMoney501();
		m[n++]="------------------------------���------------------------------";
		getMoney502();
		m[n++]="------------------------------����------------------------------";
		getMoney503();
		m[n++]="------------------------------�������޷��ز�------------------------------";
		getMoney504();
		m[n++]="------------------------------��Ʊ��齱------------------------------";
		getMoney505();
		m[n++]="------------------------------Ͷ���뿪��------------------------------";
		getMoney506();
		m[n++]="------------------------------�ϻ�Ͷ��------------------------------";
		getMoney507();
		m[n++]="------------------------------���------------------------------";
		getMoney508();
		m[n++]="------------------------------�Ŵ�------------------------------";
		getMoney509();
		m[n++]="------------------------------��ծ------------------------------";
		getMoney510();
		m[n++]=my.NEWLINE;
		m[n++]="------------------------------Ӧ�ڲο�------------------------------";
		getMoney500();
		m[n++]=my.NEWLINE;
		
		return my.format(str, m);
	}
	
	/**
	 * һ���ж�
	 */
	public void getMoney1() {
		m[n++]="�¸�Ϊͬ�о����ߣ�";
		m[n++]="�ո�Ϊ��ⷽ�����򷽡����ʷ������ⷽ��";
		m[n++]="ʱ��Ϊ����ⷽ��������һ������������Ͷ�ʷ������ⷽ���ӹ�����Ϊ���壬�����ָ�ꡢ���ӡ��ơ������Ӫ��Ŀ��";
		m[n++]="��Ϊ�ʱ���";
		m[n++]="����Ϊ���̡����棻";
		m[n++]="����Ϊ���ݡ���Ϣ����������֮��Ϊ���ǣ�";
		m[n++]="����Ϊ��Ƥ��";
		m[n++]="����Ϊ��ծ�ˡ����䳵Ƥ��";
		m[n++]="����Ϊ���顢�߻�����ͬ��������Ʊ��";
		m[n++]="ֵ��Ϊ��ⷽ���������Ŵ��ˡ����С����顢������լ��";
		m[n++]="ֵʹΪ���塢�����ˡ�����ˡ���Ӫ��Ŀ��ԭ�о�լ��";
		m[n++]="������Ϊ�����ˡ�����ˣ�";
		m[n++]="����Ϊ�����ˡ������̣�";
		m[n++]="�����ݡ����乬Ϊ�òƷ����������乬Ϊ�ò�������";		
	}
	/**
	 * ���ֶ�
	 */
	public void getMoney2() {
		if(my.isMenFu()) m[n++]="���ŷ��ʣ��������չ�������˾����˶�, ����׷��Ͷ�ʣ����������Ʋơ������ѹ���������ɣ���������Ǯ�ơ��������";
		if(my.isMenFan()) m[n++]="���ŷ����������鷴����˳������Զ�У���Ʋ�����ʴ������ʹ׬ǮҲ�б����������˻�Ҳ���˻�֮��Ҳ���죬������������";
		if(my.isXingFu()) m[n++]="���Ƿ��ʣ���ʱ�������������չ�������˾����˶�, ����׷��Ͷ�ʣ����������Ʋơ������ѹ���������ɣ���������Ǯ�ơ��������";
		if(my.isXingFan()) m[n++]="���Ƿ�������ʱ�����������鷴����˳������Զ�У���Ʋ�����ʴ������ʹ׬ǮҲ�б����������˻�Ҳ���˻�֮��Ҳ���죬������������";
		//�岻��ʱ2���츨��ʱ2
		int gejuNum = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];
  	if(gejuNum!=0)	m[n++] = QiMen.gGejuDesc[gejuNum][0]+"��"+QiMen.gGejuDesc[gejuNum][1];

		//ֵʹ�乬��
		int zsgong = daoqm.getZhishiGong();
		String[] zsmen = my.getmenJX(zsgong);
		String t = "";
		if(zsmen[0].equals("-1")) t="��"+zsmen[1]+"����֮��";
		if(my.isKong(zsgong)) t+="��գ����С�ֵʹ��գ���ʼ���ա������³ɿ�֮��"; 
		if(!"".equals(t)) m[n++] = "ֵʹ��"+zsgong+"��"+t;
	}
	/**
	 * �������Զ�
	 */
	private void getMoney31(int gong) {
		int men = daoqm.gInt[3][1][gong];  //������
		int shen = daoqm.gInt[1][1][gong]; //���е���		
		int[] tg = my.getTpjy(gong);  //���е���������		
		String[] menjx = my.getmenJX(gong); 		
		boolean iskong = my.isKong(gong);
		
		//�Ͼ�ʧ�ֶ�
		String[] heju = my.isganHeju(gong);
		if(heju[0].equals("1")) m[n++]="�Ͼ֣��¿��԰�ɣ��꼪�ţ����ż��¼���"+heju[1];
		else m[n++]="ʧ�֣�˵��״̬���ѣ����Գɹ�������֮��"+heju[1];
		
		//����������
		boolean ismenpo = QiMen.men_gong[men][gong].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[men][gong].equals(QiMen.zphym1);
		boolean ismenshe = QiMen.men_gong[men][gong].equals(QiMen.zphym4);
		if(!menjx[0].equals("1") && ismenpo)
			m[n++]=menjx[1]+"�ڹ��б��ȣ����ű��Ȼ����أ��²���֮��";
		else if(menjx[0].equals("1") && ismenpo)
			m[n++]=menjx[1]+"�ڹ��б��ȣ����ű��ȼ����ͣ��²���֮��";
		else if(!menjx[0].equals("1") && ismenzhi)
			m[n++]=menjx[1]+"�ڹ��б��ƣ����ű����ײ������н��֮��";
		else if(menjx[0].equals("1") && ismenzhi)
			m[n++]=menjx[1]+"�ڹ��б��ƣ����ű��˼����ͣ��²���֮��";
		else if(menjx[0].equals("1") && ismenshe)
			m[n++]=menjx[1]+"�ڹ��б��������ű����д�������֮��";
		else if(!menjx[0].equals("1") && ismenshe)
			m[n++]=menjx[1]+"�ڹ��б��������ŵ������ѱܣ�����֮��";
		
		//���ŷ��ȷ��Ʋ�����		
		if(gong==kaimengong && (ismenpo || ismenzhi)) {
			t = "������"+kaimengong+"����"+(ismenpo?"����":"����")+"�����׸���׵���Ϣ����ʱ���������֮�����鷳�£�����";
			//�ҵ�������Ĺ�
			for(int i=1; i<=9; i++) {
				if(i==5) continue;
				if(my.isChongke(i, ystpgong)) {
					if(my.getMenGong(QiMen.MENJING1)==i) t+="������"+i+"��������"+ystpgong+"��������˾���ࣻ";
					else if(my.getMenGong(QiMen.MENSHANG)==i) t+="������"+i+"��������"+ystpgong+"���������֣�";
					else if(my.getMenGong(QiMen.MENSI)==i) t+="������"+i+"��������"+ystpgong+"��������̯λ��Ƥ֮�೶Ƥ�£�";
					else if(yuetpgong==i) t+="�¸���"+i+"�����ˣ���ͬ��ͬ�¿Ӻ�Ǯ�ƣ�";
					else if(niantpgong==i) t+="�����"+i+"�����ˣ����ϼ����ܲ������鷳��";
				}				
			}
			if(my.isBihe(yuetpgong, ystpgong) && my.getShenGong(QiMen.SHENWU)==yuetpgong) 
				t+="�¸���"+yuetpgong+"����������"+ystpgong+"���Ⱥͣ����������䣬������̰�ƣ�";
			if(ysjxge[0].equals("1")) t+="�������д��ּ�����������鷳��"+ysjxge[1];
			else t+="�����д����ֲ�����С�ĳ������ӣ�"+ysjxge[1];
			m[n++]=t;
		}
		
		//��Ҫ���׸��
		if(my.isJixing(gong)) m[n++]= "�����ǻ��̣����������ꡢ���ף���Ͷ��Ϊ��ģ����ƷΪ���������⣻";
		if(my.isTaohua(gong)) m[n++]= "���һ���Ҳ�����������£�����ƣ�";
		for(int za : new int[]{420,421,422}) {
			if(QiMen.men_gan_shen[men][tg[0]][shen]==za || QiMen.men_gan_shen[men][tg[1]][shen]==za)
				m[n++]= "��"+QiMen.gGejuDesc[za][0]+"��"+QiMen.gGejuDesc[za][1];
		}
		int[] geju = my.getShiganKeying(gong);
		for(int ge : geju) {
			if(ge==0) continue;
			m[n++]= QiMen.gGejuDesc[ge][0]+"��"+QiMen.gGejuDesc[ge][1];
		}
		
		//Ѯ�ն�
		int wutggong = my.getTianGong(YiJing.WUG, 0);  //�������̵Ĺ�
		int wudggong = my.getDiGong(YiJing.WUG, 0);  //���ڵ��̵Ĺ�
		if(iskong) {
			t = "�乬������Ҳ�����³ɿ�֮��";
			if(kaimengong==gong) t += "����쳧���¿��ſ�����δ���߿����ɣ��ѿ��߱ض�����ͣ����";
			if(wutggong==gong) t+="�칬����Ϊ�ʱ�������Ҳ����Ǯ�����˻���"; 
			m[n++] = t;
		}
		
		//��Ĺ��
		boolean ismu = my.isTGanMu(YiJing.WUG, 0);		
		if(ismu) {
			t = "���̸���Ĺ���������������鷳��";
			if(kaimengong==gong) t += "����쳧���¿�����Ĺ��δ���߿����ɣ��ѿ��߱ض�����ͣ����";
			if(shitpgong==gong) t+="ʱ��"+YiJing.TIANGANNAME[SiZhu.sg]+"�ڹ�����⣬��ʾ��Ʒ���ػ�ѹ��"; 
			m[n++] = t;
		}
		
		//���
		t = "";
		if(my.isTGChong(gong)) t+="���̸��빬��壻";
		if(my.isTDChong(gong)) t+="���̸�����̸���壻";
		if(!"".equals(t)) t+="���������١������ĺ����鼱�ꣻ";
		if(!"".equals(t) && (wutggong==gong || wudggong==gong)) t+="�����죬�ʱ����Ϊɢ��������Ǯ�ٶȿ죻";
		if(!"".equals(t)) m[n++] = t;
		
		//�϶�
		t = "";
		if(my.isTDG3He(gong)) t+="������빬��֧���ϣ�";
		else {
			if(my.isTG6He(gong)) t+="�����빬��֧���ϣ�";
			else if(my.isTG3He(gong)) t+="�����빬��֧��ϣ�";
			if(my.isTDZi3He(gong)) t+="����̵�֧��ϣ�";
		}
		if(my.isTDGanHe(gong)) t+="����̸���ϣ�";
		if(!"".equals(t)) m[n++] = t+"��������������ȥ��";
		
		//�ٰ����
		if(gong==my.getShenGong(QiMen.SHENTIAN)) m[n++] = "���پ�������չ��ͼ���������Ҳ���ֵúܴ���ף�";
		else if(gong==my.getShenGong(QiMen.SHENDI)) m[n++] = "���پŵ������ؾ��̣����÷�չ��";
		else if(gong==my.getShenGong(QiMen.SHENYIN)) m[n++] = "����̫�������в߻������й���������";
		else if(gong==my.getShenGong(QiMen.SHENHE)) m[n++] = "�����������кϻ��ˣ�Ҳ�Ǵ�֮�ǣ�";
		else if(gong==my.getShenGong(QiMen.SHENWU)) m[n++] = "����������Ͷ�������︯�ñ��ʣ�";
		else if(gong==my.getShenGong(QiMen.SHENFU)) m[n++] = "����ֵ�������л�ͷ��˾֧�֣�";
		else if(gong==my.getShenGong(QiMen.SHENSHE)) m[n++] = "��������Ϊ�ٻ����ñ��ʣ�Ҳ���ʽ��޷��������������б仯��";
		
		//�پ��Ƕ�
		if(gong==my.getXingGong(QiMen.XINGPENG)) m[n++] = "��������������ƴ�ƻ�Ӯ������Ҳ��Ұ�Ĵ�";
		else if(gong==my.getXingGong(QiMen.XINGCHONG)) m[n++] = "��������������Ǯ�ٶȿ죻";
		else if(gong==my.getXingGong(QiMen.XINGFU)) m[n++] = "���츨������ʵ�ڣ�";
		else if(gong==my.getXingGong(QiMen.XINGRUI)) m[n++] = "�������������в���֮����Ҳ˵�����͹�������ƣ����";
		else if(gong==my.getXingGong(QiMen.XINGZHU)) m[n++] = "�������������Ƿǣ�";

		//�ٰ��Ŷ�
		int dumengong = my.getMenGong(QiMen.MENDU);
		boolean isdunei = my.isNenpan(dumengong);
		if(gong==dumengong) m[n++] = "�ٶ����������������أ�"+(isdunei?"�������̣����ڽ���":"�������̣��˶㵽���ȥ�ˣ�");
		else if(gong==my.getMenGong(QiMen.MENSI)) {
			t = "�����ţ�����֮��";
			if(zhishigong==gong) t+="������Ϊֵʹ���⿪����ף�";
			m[n++] = t;
		}else if (gong==my.getMenGong(QiMen.MENJING1)) m[n++] = "�꾪�ţ������ģ�";
		
		//���������Ƕ�
		int gengtpgong = my.getTianGong(YiJing.GENG, 0);
		int gengdpgong = my.getDiGong(YiJing.GENG, 0);
		if(gong==gengtpgong || gong==gengdpgong) {
			t = "���ͬ������ʾ����������Ϊ���͡��ص㵥λ����ҵ��";
			if(niantpgong==gong || niandpgong==gong) t+="����ɣ���ʾ�ϼ��쵼���ԣ�";
			if(yuetpgong==gong || yuedpgong==gong) t+="���¸ɣ���ʾ������鵷��";
			if(shitpgong==gong || shidpgong==gong) t+="��ʱ�ɣ���ֹ�����й�";
			m[n++] = t;
		}
		int dingtpgong = my.getTianGong(YiJing.DING, 0);
		int dingdpgong = my.getDiGong(YiJing.DING, 0);
		if(dingtpgong==gong || dingdpgong==gong) {
			t = "�ٶ���˵��������������Լ�������У�";
			if(my.isTDGanHe(gong)) t+="ǡ�궡��������֮�ϣ�����̰�����Ʋƣ�";
			m[n++] = t;
		}
		if(niantpgong==gong || niandpgong==gong) m[n++]="�����ͬ����������������ϼ��쵼֧�֣�";
		else if(my.isSheng(niantpgong, gong)) m[n++]="�����"+niantpgong+"���������ϼ��쵼֧�֣�";
		else if(my.isChongke(niantpgong, gong)) m[n++]="�����"+niantpgong+"����ˣ�Ҳ��һ�������²�����";
		if(my.isChongke(nianzigong, gong)) m[n++]="����֧����"+nianzigong+"����ˣ�Ҳ��һ�������²�����";
		//�Ƿ����
		int niangan = my.getTiangan(SiZhu.ng, SiZhu.nz);
		int yuegan = my.getTiangan(SiZhu.yg, SiZhu.yz);
		int shigan = my.getTiangan(SiZhu.sg, SiZhu.sz);
		boolean isnianji = niangan==YiJing.YI || niangan==YiJing.BING || niangan==YiJing.DING;
		boolean isyueji = yuegan==YiJing.YI || yuegan==YiJing.BING || yuegan==YiJing.DING;
		boolean isshiji = shigan==YiJing.YI || shigan==YiJing.BING || shigan==YiJing.DING;
		if(isnianji && (niantpgong==gong || niandpgong==gong)) m[n++]="���з��棬��Ϊ���["+YiJing.TIANGANNAME[niangan]+"]���ص��ϼ��쵼֧�֣�";
		if(isyueji && (yuetpgong==gong || yuedpgong==gong)) m[n++]="���з��棬Ϊ�¸�["+YiJing.TIANGANNAME[yuegan]+"]���ص�ͬ������������";
		if(isshiji && (shitpgong==gong || shidpgong==gong)) m[n++]="���з��棬Ϊʱ��["+YiJing.TIANGANNAME[shigan]+"]���ص���������������";
		//�����빬�϶�
		int guitpgong = my.getTianGong(YiJing.GUI, 0);
		int guidpgong = my.getDiGong(YiJing.GUI, 0);
		if(gong==1 && (gong==guitpgong || gong==guidpgong)) m[n++]="�ٹ��俲�������������̾�����ģ�";
		
		//�������������Ϣ������ʱ�ɶ�����Ϣ
		//if(gong==ysgong || gong==shitpgong) getMoney32(gong);
	}
	/**
	 * ������ʱ�ɹ� ���Զ�
	 * ������Щ�������Ϣ��ʱ�ɱ�ʾ��һ��������Ҳ��Щ������Ϣ
	 */
	private void getMoney32(int gong) {		
		String[] ysdpheju=my.isganHeju(ysdpgong);       //��������Ƿ�Ͼ�
		int men = daoqm.gInt[3][1][gong];  //������

		//����Ͼ���Ĺ
		if(gong==ystpgong) {
			if(!ysdpheju[0].equals("1")) m[n++]="������̸���"+ysdpgong+"��ʧ�֣�����֮��"+ysdpheju[1];
			if(my.isDGanMu(ysgan,yszi)) m[n++]="������̸���"+ysdpgong+"����Ĺ����ʾ��ԥ������";
		}
		
		//��˥����ģ
		int gan = 0; //�õ��ù������̸ɣ���ʱΪ������ֻȡһ��
		gan = ystpgong==gong?ysgan:shitpgong==gong?my.getTiangan(SiZhu.sg, SiZhu.sz):my.getTpjy(gong)[0];
		String ysws = QiMen.gan_gong_wx[gan][gong];
		int ysws3 = QiMen.gan_gong_wx3[gan][gong];
		if(ysws3<=0) m[n++]="�����乬��["+ysws+"]�أ�Ϊ�����滮�׶Ρ������Ƚ�����";
		else if(ysws3==-2) m[n++]="�����乬��["+ysws+"]�أ�˵����������������ı�׶Σ�";
		else if(ysws3==1) m[n++]="�����乬��["+ysws+"]�أ�Ϊ��չ���õĴ�˾";
		               
		//���������
		if(gong==my.getXingGong(QiMen.XINGRUI)) m[n++]="�����ǣ�˵��ԭ����ҵ��Ӫ���ƣ�";
		if(gong==my.getMenGong(QiMen.MENJING1)) m[n++]="�꾪�ţ����ڳԹ�˾��";
		else if(gong==my.getMenGong(QiMen.MENSHANG)) m[n++]="�����ţ���Ҫ�۸ߣ�";
		else if(gong==my.getMenGong(QiMen.MENDU)) m[n++]="�ٶ��ţ�������������ȥ��";
		else if(gong==my.getMenGong(QiMen.MENSI)) m[n++]="�����ţ�����ǰ״�����ã�";
		else if(gong==my.getMenGong(QiMen.MENKAI)) m[n++]="�ٿ��ţ����������ã�Ҳ��֪�飬�������Ѿ�������";
		
		if(my.ismenzhi(gong)) m[n++]=QiMen.bm1[men]+"�����ƣ���ʾ����в��";
		if(my.isGeju(gong,70)) m[n++]="���˶ݼ���˵����ͨ�����˺͹�ϵ����Ŀǰ������ı���µ�����֮����";
		
		if(gong==my.getShenGong(QiMen.SHENSHE)) m[n++]="�����ߣ����䶯����ı�Ŀǰ״����";
		else if(gong==my.getShenGong(QiMen.SHENWU)) m[n++]="�����䣬����١����󻰡����°ܻ���";
		else if(gong==zhifugong) {
			if(gong==1 || gong==3 || gong==7 || gong==9)
				t = "����ֵ����Ϊ����ְ���쵼��˾�����ˣ�";
			else if(gong==6)
				t = "����ֵ����Ϊĳ��λ�쵼�����ˣ�";
			else
				t = "����ֵ��������Ϊ��ְ���쵼��˾�����ˣ�";
			String[] zhifuhj = my.isganHeju(gong);
			if(zhifuhj[0].equals("1")) t +="���кϾ֣�ӦΪ��Ȩ����֮�ˣ�";
			else t +="����ʧ�֣��ٽײ���ӦΪ�Ƴ�����֮�ࣻ";
			if(t!=null) m[n++] = t;
		}
		
		//�����̹�ϵ
		int hegong = my.getShenGong(QiMen.SHENHE);
		if(gong==hegong) m[n++] ="������ͬ����˵����Ʒ����������Ҫ�����ڴ����̣�";
		else if(my.isSheng(gong, hegong)) m[n++] ="����������"+hegong+"����˵����Ʒ����������Ҫ�����ڴ����̣�";
		else if(my.isBihe(gong, hegong)) m[n++] ="����������"+hegong+"���Ⱥͣ�˵����Ʒ����������Ҫ�����ڴ����̣�";
		else if(my.isSheng(hegong, gong)) m[n++] ="����������"+hegong+"��������˵�������������ܴ�";
		else if(my.isChongke(hegong, gong) || my.isChongke(gong, hegong)) m[n++] ="����������"+hegong+"���໥��ˣ�˵��������̹�ϵû�д���ã�";
		
		//���
		int jinggong = my.getMenGong(QiMen.MENJING3);
		int dinggong = my.getTianGong(YiJing.DING, 0);
		if(my.isChongke(jinggong, gong)) m[n++] ="������"+jinggong+"���������֤���������Ȳ�����";
		else if(my.isChongke(gong, jinggong)) m[n++] ="�����˾�������"+jinggong+"������ʾ��Ը������棻";
		else m[n++] ="�����뾰������"+jinggong+"��������Ⱥͻ�ͬ������ʾ���ЧӦ���ã�";
		if(my.isChongke(dinggong, gong)) m[n++] ="������"+dinggong+"���������֤���������Ȳ�����";
		else if(my.isChongke(gong, dinggong)) m[n++] ="�����˶�������"+dinggong+"������ʾ��Ը������棻";
		else m[n++] ="�����붡������"+dinggong+"��������Ⱥͻ�ͬ������ʾ���ЧӦ���ã�";
	}
	private void getMoney33(int gong) {
		//�Գ�
		if(mtpgong!=0 && my.isChongke(ystpgong, wutpgong) && my.isChongke(mtpgong, wutpgong)) {
			 t = "������"+ystpgong+"����������"+mtpgong+"������������������"+wutpgong+"�������Ʋ�֮�������׸����";
			 if(ystpgong==dingtpgong || ystpgong==dingdpgong) t+="�����ٶ�������Ů�ˣ�";
			 else if(mtpgong==dingtpgong || mtpgong==dingdpgong) t+="�����ٶ�������Ů�ˣ�";
			 if(ystpgong==shenwugong || ystpgong==xingpenggong) t+="������������������ʧ����";
			 else if(mtpgong==shenwugong || mtpgong==xingpenggong) t+="������������������ʧ����";
			 if(ystpgong==jing1mengong) t+="�����پ��ţ������ٷǣ�";
			 else if(mtpgong==jing1mengong) t+="�����پ��ţ������ٷǣ�";
			 if(my.isChongke(niantpgong, ystpgong)) t+="�������������"+niantpgong+"���ˣ��������������ţ������������ģ�����˰�񷣿";
			 else if(my.isChongke(niantpgong, mtpgong)) t+="�������������"+niantpgong+"���ˣ��������������ţ������������ģ�����˰�񷣿";
			 m[n++] = t;
		}
		//�ø���Ĺ
		if(my.isTGanMu(SiZhu.yg, SiZhu.yz)) {
			t = "�¸���Ĺ��˵�����������ѵ�Ȧ�ף�";
			if(my.isKong(yuetpgong)) t+="�ַ���������ף�";
			m[n++] = t;
		}
	}
	/**
	 * �������ڶ�
	 */
	private void getMoney4() {
		//�����������ϵ��
		m[n++] = "1) �����������ϵ���Ƿ������";
		if(my.isSheng( shengmengong, ystpgong)) m[n++] = "������"+shengmengong+"������������"+ystpgong+"������������";
		else if(my.isSheng(ystpgong,shengmengong)) m[n++] = "������"+ystpgong+"����������"+shengmengong+"�������������е�����Ǯ��";
		else if(my.isBihe(ystpgong,shengmengong)) m[n++] = "������"+ystpgong+"������������"+shengmengong+"���Ⱥͣ���Ч�治��";
		else if(ystpgong==shengmengong) m[n++] = "����������ͬ��"+shengmengong+"��������֮�Ϻ���ƣ��ػ�����";
		//���������
		String[] smheju = my.ismenHeju(shengmengong);
		if(my.isSheng( shengmengong, wutpgong)) m[n++] = "������"+shengmengong+"����������"+wutpgong+"�����ػ�����"+(smheju[0].equals("1")?"�ֺϾ֣�������":"��ʧ�֣��������᣻");
		else if(my.isBihe(wutpgong,shengmengong)) m[n++] = "����"+wutpgong+"������������"+shengmengong+"���Ⱥͣ���������";
		else if(ystpgong==shengmengong) m[n++] = "��������ͬ��"+shengmengong+"��������֮�Ϻ���ƣ��ػ�����";
		else if(my.isSheng(wutpgong,shengmengong)) m[n++] = "����"+wutpgong+"����������"+shengmengong+"������Ҫ���������ʱ����ɻ�����";
		else if(my.isChongke(shengmengong, wutpgong)) m[n++] = "������"+shengmengong+"����������"+wutpgong+"�������Ȿ��"+(smheju[0].equals("1")?"":"��ʧ�֣���òҲ��̶ã�");
		
		//�ò�ʱ�� 
		m[n++] = "2) ����ʱ�䣺";
		boolean iswunei = my.isNenpan(wutpgong);
		boolean isshnei = my.isNenpan(shengmengong);
		t=null;
		if(iswunei && isshnei) 
			t="������"+shengmengong+"��������"+wutpgong+"������Ϊ���̣��ò��ٶȿ죻";
		else if((!iswunei && isshnei) || (iswunei && !isshnei)) 
			t="������"+shengmengong+"��������"+wutpgong+"����һ��һ�⣬�ò��ٶ�����";
		else t="������"+shengmengong+"��������"+wutpgong+"�����������̣��ò��ٶ�����";
		if(my.isTDG3He(wutpgong) ||	my.isTG6He(wutpgong) || my.isTG3He(wutpgong) ||	my.isTDZi3He(wutpgong) || my.isTDGanHe(wutpgong)) 
			t+="�칬�з�ϣ������ٶ�����";
		else if(my.isTGChong(wutpgong) || my.isTDChong(wutpgong)) 
			t+="�칬�з�壬�����ٶȿ죻";
		if(t!=null) m[n++] = t; 
		
		//������Ƿ���Ĺ��Ѯ��
		int[] gongs = {ystpgong, shengmengong, wutpgong};
		String[] gongname = {"������"+ystpgong+"��","������"+shengmengong+"��","����"+wutpgong+"��"};
		for(int i=0; i<gongs.length; i++) {
			if(my.isTGanMu(gongs[i])) m[n++] = gongname[i]+"��Ĺ������֮�ڿɶϣ�";
		}
		for(int i=0; i<gongs.length; i++) {
			if(my.isKong(gongs[i])) m[n++] = gongname[i]+"�������������ʵ֮�ڿɶϣ�";
		}
		int wudpgong = my.getDiGong(YiJing.WUG, 0);
		int[] wutpjy = my.getTpjy(wudpgong);
		String wudpname = YiJing.TIANGANNAME[wutpjy[0]]+(wutpjy[1]==0?"":YiJing.TIANGANNAME[wutpjy[1]]);
		m[n++] = "����������"+wudpgong+"������֮��["+wudpname+"]Ҳ��ΪӦ�ڣ�";
		
		//������
		m[n++] = "3) ���졢�����乬��������٣�";
		String[] wuheju = my.isganHeju(wutpgong);
		String[] wujxge = my.getJixiongge(wutpgong);
		t=null;
		if(wuheju[0].equals("1")) t = "����"+wutpgong+"���Ͼ֣���ƿɵã����乬֮���ϣ�"+my.getGongshu(wutpgong);
		else t = "����"+wutpgong+"��ʧ�֣���Ƽ��ѣ�";
		if(wujxge[0].equals("0")) t+="�ַ�"+wujxge[1]+"��ֹ������ǣ�"; 
		if(t!=null) m[n++] = t; 

		m[n++]="������"+shengmengong+"�������乬�����Ƿ�Ͼ֡�����˥��������"+my.getGongshu(shengmengong);
		String[] shheju = my.ismenHeju(shengmengong);
		String[] shxge = my.getJixiongge(shengmengong);
		if(shheju[0].equals("1")) m[n++]="���źϾ֣��ֹ���"+shxge[1]+"����󣬿ɶ������乬֮������";
		else if(shheju[0].equals("0") && !shxge[0].equals("0")) m[n++]="����ʧ��������������"+shxge[1]+"˵����һ�������󣬿ɶ������乬֮������";
		else if(shheju[0].equals("-1") && !shxge[0].equals("0")) m[n++]="����ʧ��������������"+shxge[1]+"����΢�����ɶ������乬֮С����";
		else if(!shheju[0].equals("1") && shxge[0].equals("0")) m[n++]="����ʧ�֣�����"+shxge[1]+"�����Ȿ��������";
		if(shengmengong==8) {
			m[n++]="�������޹�Ϊ��������ʾ���˻�û�з�����";
		}
		if(my.isKong(shengmengong)) m[n++]="������������ֻ�ܻ�һ������";
		String[] cxws = my.getxingWS(shengmengong);
		String[] ysws = my.gettgWS(ysgan, yszi);
		if(cxws.equals("1") && ysws[0].equals("1")) m[n++]="����"+cxws[1]+"����"+ysws[1]+"������ͼ���ɶ������乬֮������";
		else if(cxws.equals("-1") && ysws[0].equals("-1")) m[n++]="����"+cxws[1]+"����"+ysws[1]+"����΢�����������乬֮С���ϣ�";
		else m[n++]="����"+cxws[1]+"����"+ysws[1]+"����һ�㣬�������乬֮�����ϣ�";
		
		//�ϲ�Ʒ����
		m[n++] = "4) ��ʱ���乬�ϲ�Ʒ��������";
		m[n++] = "���۲�Ʒ���й��˵ȣ���ʱ���乬֮���ϣ�"+my.getGongshu(shitpgong);
		String[] shiws = my.gettgWS(SiZhu.sg, SiZhu.sz);
		String[] shijx = my.getJixiongge(shitpgong);
		if(shiws.equals("1") && shijx[0].equals("1")) m[n++]="ʱ��"+shiws[1]+shijx[1]+"�ɶ�ʱ�ɹ�֮������";
		if(shiws.equals("-1") && shijx[0].equals("-1")) m[n++]="ʱ��"+shiws[1]+shijx[1]+"��ʱ���乬֮С����֮��";
		else m[n++]="ʱ��"+shiws[1]+shijx[1]+"��ʱ���乬֮������֮��";
		
	}
	private void getMoney501() {
		m[n++]="����ⷿ�ݡ��Ƶꡢ����Ƚ��ף����ո���ⷽ����ⷽ��ʱ��Ϊ��һ������ⷽ������Ϊ�н��ˣ�";
		m[n++]="�������Ƚ��ף����ո�Ϊ������ʱ��Ϊ����������Ϊ�����ˡ��н��ˣ�";
		if(my.isMenFu()) m[n++]="������������Ʒ������������Ϊ�����Զ�ľɻ����ɻ�����";
		
		if(my.isSheng(ritpgong, shitpgong)) m[n++]="�ո���"+ritpgong+"����ʱ������"+shitpgong+"������������";
		else if(my.isSheng(shitpgong,ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�����"+ritpgong+"�������򷽣�";
		else if(my.isChongke(ritpgong, shitpgong)) m[n++]="�ո���"+ritpgong+"�����ʱ������"+shitpgong+"����������Ҫ��";
		else if(my.isChongke(shitpgong,ritpgong)) m[n++]="ʱ����"+shitpgong+"������ո�����"+ritpgong+"��������������";
		else if(shitpgong==ritpgong) m[n++]="��ʱͬ��"+ritpgong+"�������ױسɣ��������������ȥ��";
		else if(my.isBihe(shitpgong,ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�����"+ritpgong+"���Ⱥͣ���ƽ���ף�Ҳ���ɽ���";
		
		if(my.isKong(ritpgong) && my.isKong(shitpgong)) m[n++]="���ո���ʱ�ɾ��乬���������ױز��ɹ���";
		else if(my.isKong(ritpgong)) m[n++]="���ո��乬���������ױز��ɹ���";
		else if(my.isKong(shitpgong)) m[n++]="��ʱ���乬���������ױز��ɹ���";
		
		if(my.isSheng(shenhegong, ritpgong)) m[n++]="������"+shenhegong+"�����ո�����"+ritpgong+"�����н�������������";
		else if(my.isBihe(shenhegong, ritpgong)) m[n++]="������"+shenhegong+"�����ո�����"+ritpgong+"���Ⱥͣ��н�������������";
		else if(shenhegong==ritpgong) m[n++]="�������ո�ͬ��"+ritpgong+"�����н�������������";
		else if(my.isChongke(shenhegong, ritpgong)||my.isChongke(ritpgong,shenhegong)) m[n++]="������"+shenhegong+"������ո�����"+ritpgong+"�����н������������ͣ�������թ��";
		
		if(my.isSheng(shenhegong, shitpgong)) m[n++]="������"+shenhegong+"����ʱ������"+shitpgong+"�����н�������������";
		else if(my.isBihe(shenhegong, shitpgong)) m[n++]="������"+shenhegong+"����ʱ������"+shitpgong+"���Ⱥͣ��н�������������";
		else if(shenhegong==shitpgong) m[n++]="������ʱ��ͬ��"+shitpgong+"�����н�������������";
		else if(my.isChongke(shenhegong, shitpgong) || my.isChongke(shitpgong,shenhegong)) m[n++]="������"+shenhegong+"�����ʱ������"+shitpgong+"�����н������������ͣ�������թ��";
		
		if(my.isKong(shenhegong)) m[n++]="������"+shenhegong+"�����������м�թ��ƭ֮�£�";
		if(my.isTGanMu(shenhegong)) m[n++]="������"+shenhegong+"����Ĺ�����м�թ��ƭ֮�£�";		
		//getMoney500();
	}	
	private void getMoney502() {
		m[n++]="�ո�Ϊ������ʱ��Ϊ���";
		
		String[] shiheju = my.isganHeju(SiZhu.sg, SiZhu.sz);
		String[] shimen = my.getmenJX(shitpgong);
		String[] shigeju = my.getJixiongge(shitpgong);
		if(shiheju[0].equals("1")) m[n++]="ʱ����"+shitpgong+"���Ͼ֣����������ã�"+shiheju[1];
		else if(!shiheju[0].equals("1") && shenwugong==shitpgong)
			m[n++]="ʱ����"+shitpgong+"��ʧ�֣����ϳ����䣬���Ǽ�ðα����Ʒ���Ǹ��ñ��ʻ��"+shiheju[1];
		else if(!shiheju[0].equals("1") && shimen[0].equals("-1") && shigeju[0].equals("0")) 
			m[n++]="ʱ����"+shitpgong+"��ʧ�֣�����"+shimen[1]+shigeju[1]+"���Ǽ�ðα����Ʒ���Ǹ��ñ��ʻ��"+shiheju[1];
		else m[n++]="ʱ����"+shitpgong+"��ʧ��������������һ��Ĵ�Ʒ��"+shiheju[1];
		if(shitpgong==7 && xingpenggong==7) m[n++]="ʱ����7����������ˮ�񣬷�ֹ��Ʒ��ˮ�֣�";
		
		String shismj = my.isTGanSMJ(SiZhu.sg,SiZhu.sz);
		if(shismj!=null) m[n++]="ʱ����"+shitpgong+"����"+shismj+"�أ�������ͼ��";
		if(my.isKong(shitpgong)) 		m[n++]="ʱ����"+shitpgong+"�����������޻�������";
		
		if(my.isSheng(shitpgong, ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�"+ritpgong+"�������ܺû�ث����������Ҳ����ȱ������";
		else if(my.isChongke(shitpgong, ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�"+ritpgong+"��������������";
		else if(my.isSheng(ritpgong,shitpgong)) m[n++]="�ո���"+ritpgong+"����ʱ��"+shitpgong+"����Ϊ����ȥ���, ��Ҫ�ⷿΪ�ر�����������";
		else if(my.isChongke(ritpgong,shitpgong)) m[n++]="�ո���"+ritpgong+"����ʱ��"+shitpgong+"��������ɣ����ɽ��ٻ���";
		else if(shitpgong==ritpgong) m[n++]="��ʱͬ��"+ritpgong+"�������ױسɣ�";
		else if(my.isBihe(shitpgong,ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�����"+ritpgong+"���Ⱥͣ���ƽ���ף�Ҳ���ɽ���";
		
		if(my.isChongke(shenhegong, ritpgong)) m[n++]="������"+shenhegong+"������ո�����"+ritpgong+"�������б�����թ��";	
		
		getMoney600();
		//getMoney500();
	}
	private void getMoney503() {
		m[n++]="ֵ��Ϊ������ֵʹΪ�򷽣�";
		m[n++]="�ո�Ϊ������ʱ��Ϊ���";
		
		if(my.isSheng(zhishigong, zhifugong)) m[n++]="ֵʹ��"+zhishigong+"����ֵ��"+zhifugong+"��������������ȥ��";
		else if(my.isChongke(zhishigong, zhifugong) ||
				my.isChongke(zhishigong, zhifugong)) 
			m[n++]="ֵʹ��"+zhishigong+"����ֵ������"+zhifugong+"�����ˣ���������ȥ��";
		else if(my.isSheng(zhifugong,zhishigong)) m[n++]="ֵ����"+zhifugong+"����ֵʹ"+zhishigong+"������������ȥ��";
		else if(zhishigong==zhifugong) m[n++]="ֵ����ֵʹͬ��"+zhishigong+"��������������ȥ��";
		else if(my.isBihe(zhishigong,zhifugong)) m[n++]="ֵʹ��"+zhishigong+"����ֵ������"+zhifugong+"���Ⱥͣ���ƽ���ף�����������ȥ��";
		
		if(my.isSheng(shitpgong, ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�"+ritpgong+"���������ˣ�������ȥ��";
		else if(my.isChongke(shitpgong, ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�"+ritpgong+"�������ÿ죻";
		else if(my.isSheng(ritpgong,shitpgong)) m[n++]="�ո���"+ritpgong+"����ʱ��"+shitpgong+"��������������۸�ͻ�����Ը������";
		else if(my.isChongke(ritpgong,shitpgong)) m[n++]="�ո���"+ritpgong+"����ʱ��"+shitpgong+"�����������ų��������ɽ��ٻ���";
		else if(shitpgong==ritpgong) m[n++]="��ʱͬ��"+ritpgong+"�������ױسɣ�";
		else if(my.isBihe(shitpgong,ritpgong)) m[n++]="ʱ����"+shitpgong+"�����ո�����"+ritpgong+"���Ⱥͣ���ƽ���ף�Ҳ���ɽ���";
		
		
		if(my.isChongke(shenhegong, ritpgong)) m[n++]="������"+shenhegong+"������ո�����"+ritpgong+"�������б�����թ��";
		if(my.isChongke(shenhegong, zhifugong)) m[n++]="������"+shenhegong+"�����ֵ������"+zhifugong+"�������б�����թ��";
		
		getMoney600();
		//getMoney500();
	}
	private void getMoney504() {
		m[n++]="�������������ޣ����ز�һ����ֵ�����ո�Ϊ�򷿣���������֮�ˣ�";
		m[n++]="������Ϊ���ݣ�������Ϊ��Ƥ��������ʱ��Ϊ���ݻ��Ƥ��";
		
		if(ystpgong==shengmengong) m[n++]="����������ͬ��"+shengmengong+"����˵��һ��������λ��";
		else if(ystpgong==kaimengong) m[n++]="�����뿪��ͬ��"+kaimengong+"����˵��һ��������λ��";
		if(shengmengong==1) m[n++]="������1������������⵽һ¥�����̣�";		
		
		int[] a = (zhifugong==ritpgong) ? new int[]{zhifugong} : new int[]{zhifugong, ritpgong};
		String[] aname = (zhifugong==ritpgong) ? new String[]{"ֵ�����ո�"} : new String[]{"ֵ��", "�ո�"};
		int[] b = {shengmengong,simengong,shitpgong==shengmengong || shitpgong==simengong?0:shitpgong};
		String[] bname = {"����","����",shitpgong==shengmengong || shitpgong==simengong?null:"ʱ��"};
		if(shitpgong==shengmengong) bname[0]+="��ʱ��";
		if(shitpgong==shitpgong) bname[1]+="��ʱ��";
		String[] bname2 = {"����","��Ƥ",shitpgong==shengmengong || shitpgong==simengong?null:"���ݺ͵�Ƥ"};
		int[] good = {0,0,0};
		
		//�����м���ã����������׸��ף������������޼�����������м���һ�㣬
		for(int wu=0; wu<b.length; wu++) {				
			if(b[wu]==0) continue;			
			String[] ws = my.getmenWS(b[wu]);        //�ŵ���˥
			String[] jige = my.getJixiongge(b[wu]);  //���ļ��׸�
			if(ws[0].equals("1") && jige[0].equals("1")) {
				m[n++]=bname[wu]+"��"+b[wu]+"����"+ws[1]+jige[1]+"˵��"+bname2[wu]+"�ã�";
				good[wu] = 1;
			}
			else if(!ws[0].equals("1") && !jige[0].equals("1")) {
				m[n++]=bname[wu]+"��"+b[wu]+"����"+ws[1]+jige[1]+"˵��"+bname2[wu]+"�ܲ";
				good[wu] = 2;
			}
			else {
				m[n++]=bname[wu]+"��"+b[wu]+"����"+ws[1]+jige[1]+"˵��"+bname2[wu]+"һ�㣻";
				good[wu] = 3;
			}
		}
		
		//ֵ���������š��ո��������š��ո���ʱ�ɣ�û��ֵ����ʱ�ɵĹ�ϵ
		for(int ren=0; ren<a.length; ren++) {
			for(int wu=0; wu<b.length; wu++) {	
				if(b[wu]==0) continue;			
				if(a[ren]==zhifugong && b[wu]==shitpgong && a.length==2 && b.length==3) continue;
				String[] rs = new String[]{good[wu]==1?"���������������ɷ���":"���������������󷢴�",
								good[wu]==2?"����ƼҰܲ�":"����֮��",
								good[wu]==2?"�����ưܲ���":"����֮��",
								"����֮��","��ƽ��","��ƽ��"};
				m[n++]=getMoney700(b[wu],bname[wu],a[ren],aname[ren],rs);
			}
		}		
		//getMoney500();
	}
	private void getMoney505() {	
		//����
		if(my.isWuyangshi()) {
			m[n++]= YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"Ϊ����ʱ�����ͣ�";
		}else
			m[n++]= YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"Ϊ����ʱ��������";
		if(my.isMenFan()) m[n++]= "���ŷ��ʣ����ͣ�";
		else if(my.isMenFu()) m[n++]= "���ŷ��ʣ�������";
		if(my.isXingFan()) m[n++]= "���Ƿ��ʣ����ͣ�";
		else if(my.isXingFu()) m[n++]= "���Ƿ��ʣ�������";
		
		String[] kaimenhj = my.ismenHeju(kaimengong);
		if(!kaimenhj[0].equals("1")) m[n++]= "������"+kaimengong+"��ʧ�֣����Ʋƣ����ͣ�"+kaimenhj[1];
		else m[n++]= "������"+kaimengong+"���Ͼ֣�������"+kaimenhj[1];
		String[] shiheju = my.ismenHeju(shitpgong);
		if(!shiheju[0].equals("1")) m[n++]= "ʱ����"+kaimengong+"��ʧ�֣������ͣ�"+shiheju[1];
		else m[n++]= "ʱ����"+kaimengong+"���Ͼ֣����ͣ�"+shiheju[1];
		
		//��齱
		m[n++]="1����齱�Ƿ���󽱣�������"+ystpgong+"����";
		m[n++]="    ����Ʒ������������=���ţ����������Ϊ��Ϊ�轱�ˣ��������Ϊ��Ϊ�齱�ˣ�";
		m[n++]="    ʱ��Ϊ�齱�ˣ�����Ϊ���쵥λ��";
		int[] tpjy = my.getTpjy(ystpgong);
		int[] dpjy = my.getDpjy(ystpgong);
		for(int t1 : tpjy) 
			for(int d1 : dpjy) {
				if(t1==0 || d1==0) continue;
				String tname = YiJing.TIANGANNAME[t1];
				String dname = YiJing.TIANGANNAME[d1];
				m[n++]="    "+my.gettgWS(t1, 0)[1]+my.gettgWS(d1, 0)[1];
				if(YiJing.WXDANKE[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[d1]]==1) {
					m[n++]="    ���̸�["+tname+"]�˵��̸�["+dname+"]�����ͣ�";
				}else if(YiJing.WXDANKE[YiJing.TIANGANWH[d1]][YiJing.TIANGANWH[t1]]==1) {
					m[n++]="    ���̸�["+dname+"]�����̸�["+tname+"]��������";
				}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[d1]]==1) {
					m[n++]="    ���̸�["+tname+"]�����̸�["+dname+"]��������";
				}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[d1]][YiJing.TIANGANWH[t1]]==1) {
					m[n++]="    ���̸�["+dname+"]�����̸�["+tname+"]�����ͣ�";
				}else {
					m[n++]="    ���̸�["+dname+"]�����̸�["+tname+"]�Ⱥͣ���������";
				}
			}

		
		
		m[n++]= "    "+getMoney700(shitpgong,"ʱ��",ystpgong,"��Ʒ",new String[]{"����","����","����","����","����","����"});
		m[n++]= "    "+getMoney700(shitpgong,"ʱ��",kaimengong,"����",new String[]{"����","����","����","����","����","����"});
		
		//��������Ʊ
		m[n++]="2���ʲ��Ʊ��������"+ystpgong+"����";
		m[n++]= "    ����Ϊ��Ʊ�����ո�Ϊ�齱�ˣ�����Ϊ���쵥λ��ʱ�ɻ�ֵʹ�����壻";
		String[] riheju = my.ismenHeju(ritpgong);
		if(!riheju[0].equals("1")) m[n++]= "    �ո���"+kaimengong+"��ʧ�֣�������"+riheju[1];
		else m[n++]= "    �ո���"+kaimengong+"���Ͼ֣��󼪣�"+riheju[1];
		
		m[n++]= "    "+getMoney700(dingtpgong,"����",ritpgong,"�ո�",new String[]{"��","����","��","����","��","��"});
		m[n++]= "    "+getMoney700(ritpgong,"�ո�",kaimengong,"����",new String[]{"����֮��","�н�֮��","�н�֮��","����֮��","����֮��","����֮��"});
		m[n++]= "    "+getMoney700(shengmengong,"����",ritpgong,"�ո�",new String[]{"�в�֮��","����֮��","����֮��","����֮��","�в�֮��","�в�֮��"});
		m[n++]= "    "+getMoney700(shengmengong,"����",wutpgong,"���",new String[]{"�ػ���","���Ȿ","�Ȿ֮��","�Ȿ֮��","�ػ���","�ػ���"});
		
		//getMoney500();
	}
	private void getMoney506() {
		m[n++]= "�����桢��λ�����칤�������ؾ��̣��ո�Ϊ����ˣ�ʱ��Ϊ�˿ͣ�";
		m[n++]= "��Ͷ�ʣ��ո�Ϊ���ʷ���ʱ��ΪͶ�ʷ���";
		m[n++]= "���š�����Ϊ�̳����������Ƶꣻ�ɲο���������һ�ڣ�  ";
		m[n++]= "ֵʹ��ʱ��Ϊ���壻 ";
		m[n++]= "����Ϊ��ͬ��֤��,����Ϊ����֤��";
		
		int[] tpjy = my.getTpjy(ystpgong);
		int[] dpjy=my.getDpjy(ystpgong);
		if(my.isChongke(ystpgong, kaimengong)) {
			t=null;
			if(my.isGeju(ystpgong, 107)) t="���з�������Ƶ���";			
			else if(tpjy[0]==YiJing.REN ||tpjy[1]==YiJing.REN ||dpjy[0]==YiJing.REN ||dpjy[1]==YiJing.REN)
				t="����������������";
			else if(ysjxge[0].equals("-1")) t="���з�"+ysjxge[1];
			if(t!=null) m[n++]= "������"+ystpgong+"����˿�������"+kaimengong+"��������Ҳ�ܰ�ɣ���"+t+"������ʱ�䲻��̫����";
		}
		
		String kaimenname = zhishigong==kaimengong && shitpgong==kaimengong ?"���š�ֵʹ��ʱ��":zhishigong==kaimengong?"���š�ֵʹ":shitpgong==kaimengong?"���š�ʱ��":"����";
		String shengmenname = zhishigong==shengmengong && shitpgong==shengmengong ?"���š�ֵʹ��ʱ��":zhishigong==shengmengong?"���š�ֵʹ":shitpgong==shengmengong?"���š�ʱ��":"����";
		m[n++]= getMoney700(kaimengong,kaimenname,ystpgong,"����",new String[]{"�³�֮��","��Ȼ�ƺ��۱����������׸��߸���","������ƣ���","������ƣ���","־�ڱص�֮��","�μ�"});
		m[n++]= getMoney700(shengmengong,shengmenname,ystpgong,"����",new String[]{"�³�֮��","��Ȼ�ƺ��۱����������׸��߸���",	"������ƣ���","������ƣ���","־�ڱص�֮��","�μ�"});
		if(zhishigong!=kaimengong && zhishigong!=shengmengong)
			m[n++]= getMoney700(zhishigong,"ֵʹ",ystpgong,"����",new String[]{"�³�֮��","��Ȼ�ƺ��۱����������׸��߸���","������ƣ���","������ƣ���","־�ڱص�֮��","�μ�"});
		if(shitpgong!=kaimengong && shitpgong!=shengmengong && shitpgong!=zhishigong)
			m[n++]= getMoney700(shitpgong,"ʱ��",ystpgong,"����",new String[]{"�³�֮��","��Ȼ�ƺ��۱����������׸��߸���",	"������ƣ���","������ƣ���","־�ڱص�֮��","�μ�"});
		
		m[n++]= getMoney700(shitpgong,"ʱ��",kaimengong,"����",new String[]{"�˿Ͷ�","�˿���",	"�˿Ͷ�","�˿���","�˿Ͷ�","�˿Ͷ�"});
		m[n++]= getMoney700(shitpgong,"ʱ��",ritpgong,"�ո�",new String[]{"����Ͷ��","����Ͷ��",	"����Ͷ��","����Ͷ��","����Ͷ��","����Ͷ��"});
		m[n++]= getMoney700(shitpgong,"ʱ��",wutpgong,"���",new String[]{"Ͷ�ʷ��п�","Ͷ�ʷ���ʱ�޿�",	"Ͷ�ʷ��п�","Ͷ�ʷ���ʱ�޿�","Ͷ�ʷ��п�","Ͷ�ʷ��п�"});
		m[n++]= getMoney700(ritpgong,"�ո�",wutpgong,"���",new String[]{"���ʷ������ʽ�","���ʷ��ʽ�����",	"���ʷ��ܵõ�Ͷ��","���ʷ��ò���Ͷ��","���ʷ��ܵõ�Ͷ��","���ʷ��ܵõ�Ͷ��"});
		
		int jing3mengong = my.getMenGong(QiMen.MENJING3);
		if(my.isKong(jing3mengong)) m[n++]= "������"+jing3mengong+"���������������û��ǩԼ������ʵ֮�տ���ǩ����";
		if(my.isKong(dingtpgong)) m[n++]= "������"+dingtpgong+"���������������û��ǩԼ������ʵ֮�տ���ǩ����";
		if(my.isTGanMu(YiJing.DING, 0)) m[n++]= "������"+dingtpgong+"����Ĺ��������֮�շ�����ǩ����ͬ����¿���֤��֤����";
		String kaiws = QiMen.gong_yue[kaimengong][SiZhu.yz];
		if(kaiws.equals(QiMen.wxxqs3) || kaiws.equals(QiMen.wxxqs4) || kaiws.equals(QiMen.wxxqs5)) 
			m[n++]="������"+kaimengong+"����������Ϊ"+kaiws+"�أ�������֮�¿ɳɣ�";
		
		
		//getMoney500();
	}
	private void getMoney507() {
		m[n++]= "�ո�Ϊ�ҷ���ʱ��Ϊ�ϻ�֮�ˣ�";
		m[n++]= "�����ո�Ϊ�ҷ������ϳ�����֮��Ϊ�ϻ�֮��";
		
		m[n++]= "1) �ո���ʱ�����˶ϣ�";
		m[n++]= getMoney700(shitpgong,"ʱ��",ystpgong,"�ո�",new String[]{"��������","���Ҳ���",	"��������","��������","��ӯ","��ƽ"});
		m[n++]= getMoney700(shengmengong,"����",ystpgong,"�ո�",new String[]{"��","��","ƽ","ƽ","��","ƽ"});
		
		m[n++]= "2) �����ոɹ��ϣ�";
		int[] tpjy = my.getTpjy(ysdpgong); //�õ�������̹��ϵ���������
		for(int t1 : tpjy) {
			if(t1==0) continue;
			String tname = YiJing.TIANGANNAME[t1];
			String dname = YiJing.TIANGANNAME[ysgan];
			m[n++]=my.gettgWS(t1, 0)[1]+my.gettgWS(ysgan, yszi)[1];
			if(YiJing.WXDANKE[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[ysgan]]==1) {
				m[n++]="���̸�["+tname+"]�˵��̸�["+dname+"]���²��ɣ�����˳����";
			}else if(YiJing.WXDANKE[YiJing.TIANGANWH[ysgan]][YiJing.TIANGANWH[t1]]==1) {
				m[n++]="���̸�["+dname+"]�����̸�["+tname+"]���²��ɣ�����˳����";
			}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[ysgan]]==1) {
				m[n++]="���̸�["+tname+"]�����̸�["+dname+"]�����ҷ�������";
			}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[ysgan]][YiJing.TIANGANWH[t1]]==1) {
				m[n++]="���̸�["+dname+"]�����̸�["+tname+"]��������������";
			}else 
				m[n++]="���̸�["+dname+"]�����̸�["+tname+"]�Ⱥͣ���ƽ��";
		}
		int gong2 = my.getTianGong(tpjy[0], 0);  //�����ո��ϳ�֮���乬
		String gname = YiJing.TIANGANNAME[tpjy[0]];  //����
		m[n++]= "3) �ϻ��˶ϣ�";
		m[n++]= gname+"�������̸���"+gong2+"��Ϊ�ϻ��˵ķ�λ������Ҳ�ɿ��ϻ��˵Ľ�����";
		getMoney31(gong2);
		m[n++]= "4) Ͷ�����ϣ�";
		m[n++]= "�������"+wutpgong+"�����ɶ�Ͷ����";
		
		//getMoney500();
	}
	private void getMoney508() {
		m[n++]="ֵ��Ϊ���������У���ֵʹΪ���֮�ˣ�";
		m[n++]="�ֿ�������֮��������Ϊ���֮�ˣ�����Ϊ���������У�";
		
		if(my.isMenFan() || my.isXingFan()) m[n++]="������������;���ϣ�";
		if(simengong==zhishigong) m[n++]="����Ϊֵʹ������֮��";
		
		m[n++] = "1) ��ֵ����ֵʹ��";
		m[n++]= getMoney700(zhifugong,"ֵ��",zhishigong,"ֵʹ",new String[]{"�ɴ�����","�������","�������","�ɴ�����","ƽ","ƽ"});
		if(my.isKong(zhifugong) && my.isKong(zhishigong))
			m[n++]= getMoney800(new int[]{zhifugong,zhishigong},new String[]{"ֵ��","ֵʹ"},"�������������ز���");
		else if(my.isKong(zhishigong))
			m[n++]= getMoney800(new int[]{zhishigong},new String[]{"ֵʹ"},"�����������ز���");
		else if(my.isKong(zhifugong))
			m[n++]= getMoney800(new int[]{zhifugong},new String[]{"ֵ��"},"�����������ز���");
		
		m[n++] = "2) ������֮����"+yshenname+"��";
		if(my.isKong(ystpgong)) m[n++] ="�÷���"+ystpgong+"������֮�أ�˵��������Ǯ���˲��ڼң�ȥ��Ҳ������ɣ�";
		int txing = daoqm.gInt[2][1][ystpgong]; //����֮��������
		int dxing = ystpgong; //����֮��������
		m[n++] = "������Ϊ["+QiMen.jx1[txing]+"]��������Ϊ["+QiMen.jx1[dxing]+"]��";
		m[n++] = getXingRel(dxing,txing,new String[]{"����س�","���跴����","ƽ","���跴����","������سٻ�"});
		if(my.isTGanMu(ysgan,yszi)) m[n++] = "���������ٸ�["+YiJing.TIANGANNAME[ysgan]+"]��Ĺ�⣬˵���������ģ����Ͻ�";
		if(my.getChongGong(zhishigong)==shengmengong) m[n++] = "ֵʹ��"+zhishigong+"�Գ���������"+shengmengong+"�������������Ǯ�����⣻";
		
		//getMoney500();
	}
	private void getMoney509() {
		m[n++]="ֵ��Ϊ����Ϊ�Ŵ��ˣ�������Ϊ����ˣ�����Ϊ��Ϣ��";		
		String tyname = "["+QiMen.jx1[ty]+"]";
		m[n++]="1��ֵ�������Ҷϣ�";
		m[n++]= "    ֵ����"+QiMen.dpjg[zhifugong]+"��������["+QiMen.jx1[ty]+"]��"+tygong+"����";
		m[n++] = "    "+getGongGongRel(zhifugong,tygong,new String[]{"��","��","��","��","ƽ"});
		
		m[n++]="2��ֵ�������ҡ����Ŷϣ�";
		if(my.isChongke(shengmengong,zhifugong) && YiJing.WXDANKE[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    ������"+shengmengong+"����"+tyname+"��"+tygong+"����ͬ��ֵ��"+zhifugong+"�����ų�֮����ʧ������";
		else if(my.isSheng(shengmengong,zhifugong)	&& YiJing.WXDANSHENG[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    ������"+shengmengong+"����"+tyname+"��"+tygong+"����ͬ��ֵ��"+zhifugong+"������Ϣȫ����";
		else if(my.isBihe(shengmengong,zhifugong)	&& YiJing.WXDANSHENG[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    ������"+shengmengong+"����ֵ���Ⱥͣ�"+tyname+"��"+tygong+"����ֵ��"+zhifugong+"������Ϣȫ����";
		else if(shengmengong==zhifugong	&& YiJing.WXDANSHENG[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    ������ֵ��ͬ����"+tyname+"��"+tygong+"����ֵ��"+zhifugong+"������Ϣȫ����";
		else 
			m[n++] = "    ������"+shengmengong+"����"+tyname+"��"+tygong+"����һ��һ��ֵ��"+zhifugong+"�������֮���ȫ����ٻ���";
		
		String tyws = QiMen.xing_gong[ty][zhifugong];
		if(!tyws.equals(QiMen.wxxqs1) && !tyws.equals(QiMen.wxxqs2))
			m[n++] = "����"+tyname+"��"+zhifugong+"����"+tyws+"�أ�����������������ǲ�ȫ����ٻ���";
		//getMoney500();
	}
	private void getMoney510() {
		m[n++]="ֵ��Ϊ��ծ֮�ˣ������ǡ�������ΪǷծ�ˣ�����Ϊ��ծ�ˣ��׻�Ϊί����ծ֮�ˣ�����Ϊ�����ˣ�";
		
		if(my.isMenFu()) m[n++] = "���ŷ��ʣ�����أ�";
		if(my.isXingFu()) m[n++] = "�����Ƿ��ʣ�����أ�";
		
		String[] tyws = my.getGongWS(tygong);
		int[] tytpjy = my.getTpjy(tygong);
		m[n++] = "1) ���������Ҷϣ�";
		m[n++] = "    ������"+shangmengong+"��������["+QiMen.jx1[ty]+"]��"+tygong+"����";
		if(my.isChongke(shangmengong, tygong)) m[n++] = "    ���ſ����ң���ծ��ʵ��ʵ��ȥ�֣�";
		else if(my.isChongke(tygong,shangmengong)) {
			t= "    ���ҿ����ţ��˴�����������";
			if(tyws[0].equals("1")) t+="���乬"+tyws[1]+"��˵����������ȴ���Ĳ�����";			
			m[n++]  = t;
		}else if(my.isSheng(tygong, shangmengong)) {
			t = "���������ţ������뻹��";
			if(!tyws[0].equals("1")) t+="���乬"+tyws[1]+"��������������ʹ���Ҳ���ܻ�ȫ��";
			m[n++]  = t;
		}else if(my.isSheng(shangmengong, tygong)) m[n++] = "    ���������ң���ծ�˲����֣�";
		else if(shangmengong==tygong) m[n++] = "    ����������ͬ������ծ����Ƿծ�˴�ͨһ����";
		else if(my.isBihe(shangmengong, tygong)) m[n++] = "    ���������ұȺͣ���ծ����Ƿծ�˴�ͨһ����";
		
		m[n++] = "2) ֵ�������Ҷϣ�";
		m[n++] = "    ֵ����"+zhifugong+"��������["+QiMen.jx1[ty]+"]��"+tygong+"����";
		if(my.isChongke(zhifugong, tygong)) {
			t = "    ֵ�������ң���ծ��ִ����ծ��";
			if(zhifugong==dingtpgong && zhifugong==4) t+= "ֵ�������ҳ˶������Ĺ������Ĺ�������ִ�����أ����о���֮�£�";
			if(zhifugong==jing3mengong && zhifugong==4) t+= "ֵ���������پ������Ĺ������Ĺ�������ִ�����أ����о���֮�£�";
			m[n++] = t;
		}
		else if(my.isChongke(tygong,zhifugong)) {
			t= "    ���ҿ�ֵ��������֮��";
			if(tygong==shenwugong) t+="���ϳ����䣬���Ĳ�����";
			if(tygong==xingpenggong) t+="���������ǣ����Ĳ�����";
			if(tytpjy[0]==YiJing.GENG || tytpjy[1]==YiJing.GENG) t+="���ϳ˸�����ֵ�������о���֮�£�";
			if(tytpjy[0]==YiJing.XIN || tytpjy[1]==YiJing.XIN) t+="���ϳ�������ֵ�������о���֮�£�";
			m[n++]  = t;
		}else if(my.isSheng(tygong, shangmengong)) {
			t = "���������ţ������뻹��";
			if(!tyws[0].equals("1")) t+="���乬"+tyws[1]+"��������������ʹ���Ҳ���ܻ�ȫ��";
			m[n++]  = t;
		}else if(my.isSheng(zhifugong, tygong)) m[n++] = "    ֵ�������ң����ڲ����֣�";
		else if(zhifugong==tygong) m[n++] = "    ֵ��������ͬ����ծ����Ƿծ����Э���׵���";
		else if(my.isBihe(zhifugong, tygong)) m[n++] = "    ֵ�������ұȺͣ�ծ����Ƿծ����Э���׵���";
		
		m[n++] = "3) ���š�������ֵ����";
		m[n++] = "    ֵ����"+zhifugong+"��������["+QiMen.jx1[ty]+"]��"+tygong+"����������"+shangmengong+"����";
		if(my.isSheng(tygong, zhifugong) && my.isSheng(shangmengong, zhifugong))
			m[n++] = "    ����������ͬ��ֵ������Ϣȫ��׷�أ�";
		else if(my.isChongke(tygong, zhifugong) && my.isChongke(shangmengong, zhifugong))
			m[n++] = "    ����������ͬ��ֵ������Ϣ������";
		else if(my.isSheng(shangmengong, zhifugong) && my.isChongke(shangmengong, tygong))
			m[n++] = "    ������ֵ���������ң� ���ֻأ�";
		else if(my.isSheng(shangmengong, tygong) && my.isChongke(shangmengong, zhifugong))
			m[n++] = "    ���������ң���ֵ�����ֲ��أ�";
		else 
			m[n++] = "    ���š����ҡ�ֵ�����߹�ϵ������ֻأ�";
		
		if(wutpgong==kaimengong && my.isNenpan(wutpgong)) {
			m[n++] = "��������"+wutpgong+"�����Ὺ�ţ��������̣���ծ�ٻ���";
		}			
		if(my.isNenpan(shangmengong) && my.isNenpan(zhifugong) && my.isNenpan(tygong)) 
			m[n++] = "ֵ����"+zhifugong+"��������["+QiMen.jx1[ty]+"]��"+tygong+"����������"+shangmengong+"�����������̣��ٶȿ죻";
		else
			m[n++] = "ֵ����"+zhifugong+"��������["+QiMen.jx1[ty]+"]��"+tygong+"����������"+shangmengong+"�����������⣬�ٶ�����";
		
		//m[n++] = "��";
		//getMoney500();
	}
	
	private String getGongGongRel(int g1, int g2, String[] rs) {
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
	
	private String getXingGongRel(int g, int x, String[] rs) {
		String res = null;
		String name1 = "["+QiMen.dpjg[g]+"]";
		String name2 = "["+QiMen.jx1[x]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jgwh[g]][QiMen.jx3[x]]!=0) res=name1+"��"+name2+"��"+rs[0]+"��";
		else if(YiJing.WXDANKE[QiMen.jgwh[g]][QiMen.jx3[x]]!=0) t=name1+"��"+name2+"��"+rs[1]+"��";
		else if(YiJing.WXDANSHENG[QiMen.jx3[x]][QiMen.jgwh[g]]!=0) res=name2+"��"+name1+"��"+rs[2]+"��";
		else if(YiJing.WXDANKE[QiMen.jx3[x]][QiMen.jgwh[g]]!=0) t=name2+"��"+name1+"��"+rs[3]+"��";
		else  res=name1+"��"+name2+"�Ⱥͣ�"+rs[4]+"��";
		
		return res;
	}
	
	private String getXingRel(int x1, int x2, String[] rs) {
		String res = null;
		String name1 = "["+QiMen.jx1[x1]+"]";
		String name2 = "["+QiMen.jx1[x2]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"��"+name2+"��"+rs[0]+"��";
		else if(YiJing.WXDANKE[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"��"+name2+"��"+rs[1]+"��";
		else if(YiJing.WXDANSHENG[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"��"+name1+"��"+rs[2]+"��";
		else if(YiJing.WXDANKE[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"��"+name1+"��"+rs[3]+"��";
		else  res=name1+"��"+name2+"�Ⱥͣ�"+rs[4]+"��";
		
		return res;
	}
	
	private String getMoney800(int[] g1, String[] gname, String desc) {
		StringBuilder sb = new StringBuilder(gname[0]+"��"+g1[0]+"��");
		for(int i=1; i<g1.length; i++) {
			sb.append("��"+gname[i]+"��"+g1[i]+"��");
		}
		return sb.append("��"+desc).toString();
	}
	private String getMoney700(int g1, String name1, int g2, String name2, String[] rs) {
		t = null;
		//if(g1==5) g1 = my.getJiGong528(gong)
		if(my.isSheng(g1, g2)) t=name1+"��"+g1+"����"+name2+"����"+g2+"����"+rs[0]+"��";
		else if(my.isChongke(g1, g2)) t=name1+"��"+g1+"�����"+name2+"����"+g2+"����"+rs[1]+"��";
		else if(my.isSheng(g2,g1)) t=name2+"��"+g2+"����"+name1+"����"+g1+"����"+rs[2]+"��";
		else if(my.isChongke(g2,g1)) t=name2+"��"+g2+"�����"+name1+"����"+g1+"����"+rs[3]+"��";
		else if(g1==g2) t=name1+"��"+name2+"ͬ��"+g2+"����"+rs[4]+"��";
		else if(my.isBihe(g1,g2)) t=name1+"��"+g1+"����"+name2+"����"+g2+"���Ⱥͣ�"+rs[5]+"��";
		return t;
	}
	
	private void getMoney600() {
		//ʱ�������š����
		String[] shiheju = my.isganHeju(SiZhu.sg, SiZhu.sz);
		if(my.isSheng(shitpgong, shengmengong) || my.isSheng(shengmengong,shitpgong)) m[n++] = "ʱ����"+shitpgong+"������������"+shengmengong+"��������������ͼ��";
		else if(my.isChongke(shitpgong, shengmengong) || my.isChongke(shengmengong, shitpgong))
			m[n++] = "ʱ����"+shitpgong+"������������"+shengmengong+"�����ˣ�������ͼ��"+(shiheju[0].equals("1")?"":"��ʱ��ʧ�֣����Ȿ��");
		else if(shitpgong==shengmengong) m[n++]="ʱ��������ͬ��"+shengmengong+"����������ͼ��";
		else if(my.isBihe(shitpgong,shengmengong)) m[n++]="ʱ����"+shitpgong+"������������"+shengmengong+"���Ⱥͣ��ɻ�����";
		
		if(my.isSheng(shitpgong, wutpgong) || my.isSheng(wutpgong,shitpgong)) m[n++] = "ʱ����"+shitpgong+"����������"+wutpgong+"��������������ͼ��";
		else if(my.isChongke(shitpgong, wutpgong) || my.isChongke(wutpgong, shitpgong))
			m[n++] = "ʱ����"+shitpgong+"����������"+wutpgong+"�����ˣ�������ͼ��"+(shiheju[0].equals("1")?"":"��ʱ��ʧ�֣����Ȿ��");
		else if(shitpgong==wutpgong) m[n++]="ʱ������ͬ��"+wutpgong+"����������ͼ��";
		else if(my.isBihe(shitpgong,wutpgong)) m[n++]="ʱ����"+shitpgong+"����������"+wutpgong+"���Ⱥͣ��ɻ�����";
	}
	
	private void getMoney500() {
		boolean isrinei = my.isNenpan(ritpgong);
		boolean isshinei = my.isNenpan(shitpgong);
		if(isrinei && isshinei) 
			m[n++]="�ո���"+ritpgong+"����ʱ����"+shitpgong+"������Ϊ���̣������ٶȿ죻";
		else if((!isrinei && isshinei) || (isrinei && !isshinei)) 
			m[n++]="�ո���"+ritpgong+"����ʱ����"+shitpgong+"����һ��һ�⣬�����ٶ�����";
		else m[n++]="�ո���"+ritpgong+"����ʱ����"+shitpgong+"�����������̣������ٶ�����";
		
		if(my.isKong(shitpgong)) m[n++]="ʱ����"+shitpgong+"������������ʵ���ʵ֮�տɳɣ�";
		if(my.isTGanMu(shitpgong)) m[n++]="ʱ����"+shitpgong+"����Ĺ�������֮�տɳɣ�";
		int shiws = SiZhu.TGSWSJ[SiZhu.sg][SiZhu.yz];
		if(shiws>5) m[n++]="ʱ��["+YiJing.TIANGANNAME[SiZhu.sg]+"]��"+shitpgong+"����������Ϊ"+SiZhu.TGSWSJNAME[shiws]+"�أ�����֮�¿ɳɣ�";
		int[] shidpjy = my.getDpjy(shitpgong);
		String shidpjyname = YiJing.TIANGANNAME[shidpjy[0]]+(shidpjy[1]==0?"":YiJing.TIANGANNAME[shidpjy[1]]);
		m[n++] = "ʱ�ɹ�����֮��["+shidpjyname+"]Ҳ��ΪӦ�ڣ�";
		int[] zstpjy = my.getTpjy(zhishigong==5?2:zhishigong);
		String zstpjyname = YiJing.TIANGANNAME[zstpjy[0]]+(zstpjy[1]==0?"":YiJing.TIANGANNAME[zstpjy[1]]);
		m[n++] = "ֵʹ"+zhishigong+"������֮��["+zstpjyname+"]Ҳ��ΪӦ�ڣ�";
	}
	
	/**
	 * �������������š����š�ʱ����ֵ����ֵʹ���칬
	 * ȥ���ظ��ģ�����Map<gong,����>�з���
	 * @param mingzhu
	 * @param yongshen
	 * @return
	 */
	private void getYShen(List<Integer> ysgong, List<String> ysname) {			
		String mname = mtpgong!=0 ? "����["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]" : null;
		String shiname = "ʱ�ɹ�["+YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"]"; 
		
		int[] gongs = {ystpgong,mtpgong,kaimengong,shengmengong,shitpgong,zhifugong,zhishigong,wutpgong};
		String theyshen = yshenname.substring(0,yshenname.length()/2)+"��";
		String[] ysdesc = {theyshen,mname,"���Ź�","���Ź�",shiname,"ֵ����","ֵʹ��","��ɹ�"};
		for(int g=0; g<gongs.length; g++) {
			if(gongs[g]==5) gongs[g]=2;  //�幬��2��
			if(gongs[g]==0) continue;
			int index = -1;
			if((index=ysgong.indexOf(gongs[g]))!=-1) {
				ysname.set(index, ysname.get(index) + "," + ysdesc[g]);
			}	else {
				ysgong.add(gongs[g]);
				ysname.add(ysdesc[g]);
			}
		}
	}
	private void w(String text) {
		m[n++] = text;
	}
	private void getMoney40() {
		w("1) �����״���ϣ�");
		if(ystpgong==dumengong) w("������"+ystpgong+"������ţ����˱Ƚ��ܸɣ�����Ը��������ԭ��������ȥ���ղ����ʽ�");
		if(my.isTaohua(ystpgong)) w("�乬Ϊ��ԡ֮�أ���ԡΪ�һ��ܵأ���ʾ��������������£����������һ���");
		if(ystpgong==kaimengong && ystpgong==shenhegong) w("���������ų����ϣ�Ϊ���������ϵĹ�����");
		if(ystpgong==jing1mengong) w("�꾪�������ģ�");
		if(my.isKong(ystpgong)) w("���������û�еף�");
		if(ystpgong==bingtpgong) w("�ٱ���Ȩ�����Լ�˵���㡣");
		if(ystpgong==xintpgong) w("����������Υ����");
		if(ystpgong==xingruigong) w("�������ǣ���̰�ģ�");
		if(ystpgong==shangmengong) w("�����ţ���̬��ǿ�᣻");
		if(ystpgong==shenwugong) w("�ո��ϳ����䣬�м�˰��˰�������");
		if(ystpgong==xintpgong && ystpgong==shenwugong) w("���������һ�᲻������");
		if(ystpgong==shenwugong && ystpgong==dumengong) w("����Ͷ���ͬ����һ����˵�ٻ���");
		if(ystpgong==niantpgong || ystpgong==niandpgong) {
			if(ystpgong==xiumengong) w("�ո���̫�꣬���к�̨��������Ϊ�������ŵĸ߹٣�");
			else w("�ո���̫�꣬���к�̨��");
		}
		if(ystpgong==shenwugong && 
				(my.isSheng(ystpgong, zhifugong) || my.isSheng(ystpgong, zhishigong)))
			w("�����䣬��ֵ����ֵʹ�����ߺ��ţ�����");
		if(ystpgong==xingyinggong) w("��Ӣ�Ǵ������Ӽ���");
		if(ystpgong==jing3mengong) w("�پ���Ư�����Ļ����ʸߣ������Ծ�ǿ��");
		if(ystpgong==shenhugong) w("�˰׻�Ƣ�����á�");
		m[n++]=my.NEWLINE;
		
		w("2) ��ҵ״���ϣ�");
		if(my.isMenFu() || my.isXingFu()) w("��ַ��������Ʋ����ˣ���ǰ��׬Ǯ��");
		if(my.isMenFu() || my.isXingFu() || my.isMenFan() || my.isXingFan())
			w("������������ˮ�ܲ��飬˵�������ķ�ˮ���á�");
		String[] kmhj = my.ismenHeju(kaimengong);
		if(kmhj[0].equals("1")) {
			w("������"+kaimengong+"���Ͼ֣�Ϊ����ҵ��Ч��á�"+kmhj[1]);
		}else{
			w("������"+kaimengong+"��ʧ�֣�ΪС��ҵ����Ч��һ�㡣"+kmhj[1]);
		}
		if(my.isKong(kaimengong)) {
			if(kmhj[0].equals("1")) w("����������������ҵͣ��������ֺÿ����ǰ�ͣ����");
			else if(kmhj[0].equals("-1"))w("����������������ҵͣ��������ȫʧ�֣�Ϊ���Ʋ���");
			else w("��������������� һ�㣬����ҵͣ����");
		}
		if(my.isYima(kaimengong) || my.isChMa(kaimengong, my.SHIKONGWANG)) w("���ų����ǻ��������乬�壬��ҵǨַ��");
		if(kaimengong==shenshegong) w("����Ϊ�����ϳ����ߣ�˵��������һ�������Ƚ϶�ĵط�������");
		if(kaimengong==shenhugong) w("�����ϳ˰׻���������");
		if(QiMen.men_gong[QiMen.MENKAI][kaimengong].equals(QiMen.zphym5)) w("������Ĺ��������");
					
		if(my.isSheng(kaimengong, ystpgong)) w("�������ոɣ���λ�벻�����ˣ���λ�벻������");
		else if(my.isChongke(kaimengong, ystpgong)) w("���ų���ոɹ�������λ��Ҫ�����ˣ��������׸���");
		else w("���������ţ��Ⱥ͡�ͬ����Ҫ�ȿ�����������ʾ�ǵ�λ�Ǹɣ����ǵ�λ�벻�����ˣ�һ��Ա�����ӣ�");
		
		w("ʱ����"+shitpgong+"����Ϊ���������ˡ�Ա����");
		if(my.isKong(shitpgong)) w("ʱ�ɷ��������ҵ��Ա�١�");
		if(shitpgong==shendigong) w("ʱ�ɳ˾ŵأ���ҵ��Աӷ�ף�����Ч��̫�͡�");
		if(QiMen.gan_gong_wx[my.getTiangan(SiZhu.sg,SiZhu.sz)][shitpgong].indexOf("��")!=-1) w("ʱ�ɳ�»λ��Ա������ߡ�");
		if(shitpgong==xingruigong || shitpgong==jitpgong || shitpgong==xintpgong)
			w("ʱ�ɷ꼺�������ǣ�������Ա���в������Ҫ��");
		if(shitpgong==shenwugong) w("ʱ�ɳ����䣬��͵����̰��ռ���ˡ�������Ϊ��");
		if(shitpgong==xiumengong) w("ʱ�ɷ����ţ�������ף�Ա������������������ɢ�����衣");
		if(shitpgong==xingchonggong) w("ʱ�ɷ�����ǣ�����Ч�ʸߡ�");
		if(shitpgong==xingzhugong) w("ʱ�ɷ������ǣ�Ա���׷��������¹ʡ�");
		if(shitpgong==zhifugong) w("ʱ�ɳ�ֵ����Ϊ�����ɣ���֯��ǿ���ɾ��㡣");

		m[n++]=my.NEWLINE;
		
		w("3) �ƻ�״���ϣ�");
		w("������"+jing3mengong+"����Ϊ�ƻ�����ս�ԡ��滮��������ս�������ߡ���桢�����ƶȡ���Ŀ�����⡢���ӡ�");
		String[] jing3hj = my.ismenHeju(jing3mengong);
		if(jing3hj[0].equals("1")) w("���źϾ֣����ƻ����ơ����ܡ�");
		if(my.isKong(jing3mengong)) w("���ſ�����û�мƻ���û���ƶȡ�");
		if(jing3mengong==xintpgong) w("���ŷ��������ƻ��д�����ȱ�ݡ�");
		if(jing3mengong==gengtpgong) w("���ŷ����������������ʵ�֡�");
		Integer[] jing3ge = my.getJixiongge2(jing3mengong, iszf);
		for(int j3ge : jing3ge) {
			if(j3ge==16) w("���ŷ�㣸����ƻ����ҡ�");
		}
		if(jing3mengong==jitpgong) w("���ŷ꼺���ƻ���̫��������");
		if(my.isTDG3He(jing3mengong) || my.isTDGanHe(jing3mengong)
				|| my.isTDZi3He(jing3mengong) || my.isTG3He(jing3mengong) 
				|| my.isTG6He(jing3mengong)) w("���������Ƿ�ϣ����ƻ��޷���ʵ��");
		if(my.isYima(jing3mengong) || my.isChMa(jing3mengong, my.SHIKONGWANG)) 
			w("���Ź��з����������ǣ����ƻ��ܿ����ʵ�֡�");
		if(my.isSheng(jing3mengong, ystpgong) || my.isSheng(jing3mengong, ystpgong))
			w("���Ź����ոɻ������ոɱȺͣ��ƻ���ʵʩ������ʵ��");
		else if(my.isChongke(ystpgong, jing3mengong))
			w("�ոɿ˾��Ź����ƻ�Ҳ������ʵ��");
		else if(my.isChongke(jing3mengong, ystpgong) || !jing3hj[0].equals("1"))
			w("���Ź����ոɻ����乬ʧ�֣��ƻ���ʵ���ˡ�");
		if(jing3mengong==shenshegong) {
			int[] tpjy = my.getTpjy(jing3mengong);
			int[] dpjy = my.getDpjy(jing3mengong);
			if((tpjy[0]==YiJing.DING || tpjy[1]==YiJing.DING) && 
					(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN))
				w("����Ϊ��Ӫ���ԣ����ŷ궡������֮�ϣ��������ߣ����Բ�����");
			else
				w("���Ź������ߣ����仯�޳���");
		}
		if(jing3mengong==shenwugong) w("���Ź������䣬���ƻ�����ٲ�ʵ�ģ���������ġ�");
		if(jing3mengong==shenhugong) w("���ų˰׻������ƻ�©���ࡣ");
		if(jing3mengong==shendigong) w("���ų˾ŵأ����ƻ�ʵʩ������ƻ��¾ɡ�");
		
		if(my.isSheng(ystpgong, shenhegong)) w("�ո������ϣ��Լ��뷢չ�����̣�");
		if(my.isChongke(shenhegong, shitpgong)) w("���Ͽ�ʱ�ɣ������̲���ɴ�����£�");
		if(my.isTaohua(shenhegong)){
			if(shenhegong==xingchonggong) w("�������һ����������������뷢��ƣ����ҳ�����ǣ��뾡�췢��ơ�");
			else w("�������һ����������������뷢��ơ�");				
		}
		if(dingtpgong==gengtpgong || dingtpgong==gengdpgong) w("�����ٸ������������ð졣");
		if(my.isTGanMu(YiJing.DING, 0)) w("������Ĺ������������ܳ�ʱ�䡣");
		if(zhishigong==xingpenggong || zhishigong==shenwugong) w("ֵʹΪ����İ��²��ţ����������Ϊ̰�ģ�Ҫ�ô���Ҫ��Ǯ��");
		if(my.isChongke(zhishigong, dingtpgong)) w("ֵʹ��������������Ϊ�ѣ���Ҫ����");
		
		m[n++]=my.NEWLINE;
		
		w("4) �ʽ�״���ϣ�");
		if(my.gettgWS(YiJing.WUG, 0)[0].equals("1"))
			w("��������"+wutpgong+"����Ϊ��ҵ�ʱ���������Ϊ�ʽ�ࡣ");
		else w("��������"+wutpgong+"����Ϊ��ҵ�ʱ����ִ�˥��Ϊ�ʽ��١�");
		if(my.isTDGanHe(wutpgong)) w("�����ϣ��ʽ�ռѹ��");
		if(wutpgong==xindpgong) w("��������ʽ�ɢ��̫�죬֧����̫�졢����ס�ʽ�");
		if(my.isJixing(wutpgong)) w("�����ǻ��̣����ʽ�����");
		if(wutpgong==gengtpgong) w("���������ʽ�ȱ��������λ��");
		if(my.isKong(wutpgong)) {
			if(my.getGongWS(wutpgong).equals("1")	
					&& my.getxingWS(shengmengong)[0].equals("1")) w("���գ����乬�������أ��������࣬���ʽ�ֻ����ʱ��ȱ���ʽ�ȱ����ԭ��ɲο�ʱ�ɲ�Ʒ��Ϣ��");
			else w("���գ����乬�����˥�ػ����Ź����ǲ�������ʾȷʵû���ʽ��ʽ�ȱ����ԭ��ɲο�ʱ�ɲ�Ʒ��Ϣ��");
		}
		if(my.isTGanMu(YiJing.WUG, 0)) {
			int yuews = SiZhu.TGSWSJ[YiJing.WUG][SiZhu.yz];
			if(yuews<=5) w("�����������أ��乬Ϊ��⣬�ʽ���ʱ��ȱ��");
			else w("���������������乬��Ĺ���ʽ�����");			
		}
		if(my.isTGanMu(SiZhu.sg, SiZhu.sz)) w("ʱ����Ĺ����Ʒ���������������ʱ������������ȥ�������޷�������");
		if(my.isTDG3He(shitpgong) || my.isTDGanHe(shitpgong)
				|| my.isTDZi3He(shitpgong) || my.isTG3He(shitpgong) 
				|| my.isTG6He(shitpgong)) w("ʱ�ɷ�ϣ���Ʒ������ȥ�������޷�������");
		if(shitpgong==shenshegong) w("ʱ�������߲��ƣ���Ʒ������ȥ��");
		if(my.isChongke(shitpgong, ritpgong)) w("ʱ�ɿ��ոɣ�Ӧ�жϲ�Ʒ������");
		if(wutpgong==shengmengong && wutpgong==shenhegong) w("��������ͬ�����ϳ����ϣ�˵��������׬Ǯ");
		if(wutpgong==xingpenggong && my.isTDChong(wutpgong)) w("��������ǣ��������壬�ʽ��ߵÿ죬����û���ʽ�");
		else if(wudpgong==xingpenggong && my.isTDChong(wudpgong)) 
			w("�������"+wudpgong+"���������ǣ��������壬�ʽ��ߵÿ죬����û���ʽ�");
		
		m[n++]=my.NEWLINE;
		
		w("5) ��Ʒ״���ϣ�");
		w("ʱ����"+shitpgong+"����Ϊ��Ʒ��������Ʒ��");
		String[] shihj = my.isganHeju(SiZhu.sg, SiZhu.sz);
		if(shihj[0].equals("1")) w("ʱ�ɺϾ֣���Ʒ�á�"+shihj[1]);
		else w("ʱ��ʧ�֣���Ʒ�����"+shihj[1]);
		if(shitpgong==zhifugong) w("ʱ�ɷ�ֵ������Ʒ�߼���������");
		if(shitpgong==shenshegong || my.isTGanMu(SiZhu.sg, SiZhu.sz))
			w("ʱ�ɷ����߻���Ĺ����Ʒ���ʣ�����������");
		if(shitpgong==shenyingong) w("ʱ�ɷ�̫���������á�");
		if(shitpgong==shenhegong) w("ʱ�ɷ����ϣ�Ʒ�ֶࡣ");
		if(shitpgong==shenwugong) w("ʱ�ɷ����䣬�ٻ���ʱ��Ϊ��ƷҲΪ�Է��������䣬����Ū���ˣ�����Ǯ������������");
		if(shitpgong==shenhugong) w("ʱ�ɷ�׻����д�Ʒ����Ĵ�"); 
		if(shitpgong==shendigong) w("ʱ�ɷ�ŵأ�����࣬���ʱ�䳤�������ŵأ������ڴ����");
		if(shitpgong==shentiangong) w("ʱ�ɷ���죬��������Զ����������");			
		
		m[n++]=my.NEWLINE;
		
		w("6) ����״���ϣ�");
		w("������"+shengmengong+"�����������󣬲ο������졣�乬������׬����������");
		if(QiMen.gan_gong_wx[my.getTiangan(ysgan,yszi)][ystpgong].indexOf("��")!=-1) w("����λ��»λ��Ҳ������");
		if(my.isSheng(shengmengong, ystpgong) || my.isBihe(shengmengong, ystpgong)) {
			if(my.isTDG3He(shengmengong) || my.isTDGanHe(shengmengong)
					|| my.isTDZi3He(shengmengong) || my.isTG3He(shengmengong) 
					|| my.isTG6He(shengmengong)) w("�������ոɻ�Ⱥͣ�������׬Ǯ���������乬��ϣ���ʱû��������ȵ����������ߺϱ��忪��ʱ���вơ��û���Ŭ�����ı侭Ӫ�ƻ����ı��������ԡ���չ�����̡�");
			else
				w("���Ź����ոɹ���Ⱥͣ���׬Ǯ��");		
		}
		else if(my.isSheng(ystpgong, shengmengong)) w("���������ţ�����Ŭ�����ܻ�����");
		if(my.isKong(shengmengong)) w("���Ź�������ûǮ��׬��û���������������롣");
		if(my.getxingWS(shengmengong)[0].equals("1"))
			w("���Ź�����֮����Ϊ���ǣ��òƶ�����Ҫ�����ǵ�״����������Ϊ�ƺ�");
		else 
			w("���Ź�����֮����Ϊ���ǣ��òƶ�����Ҫ�����ǵ�״����������Ϊ�Ʊ���");
		if(wutpgong==shengmengong) w("��������ͬ����Ͷ�ʱ�Ӯ����");
		if(shengmengong==shenshegong) w("�����ϳ����ߣ�Ǯ�����á�");
		m[n++]=my.NEWLINE;
	}
	private void getMoney50() {
		w("(һ)ʱ����"+shitpgong+"����Ϊ�������֣�Ϊ̸�ж��֡�Ϊ�з����¸���"+yuetpgong+"����Ϊͬ�У���ʱҲΪ�������֡�ʱ������Ҫ���¸ɴ�֮��");
		if(shitpgong==shenshegong) w("ʱ�ɳ����ߣ��Է���թ���˲��ɽ���");
		if(shitpgong==shenyingong) w("��̫�������������£�ҲΪ��թ������ơ�");
		if(shitpgong==shenhugong) w("�˰׻�����ֺ�Ϊֱˬ����ֲ��ã�Ϊ����");
		if(shitpgong==shenwugong) w("�����䣬��������Ͷ������С������Υ������ƭ��������");
		if(shitpgong==shendigong) w("�˾ŵأ������ٻ���������������ִ��");
		if(shitpgong==shentiangong || (SiZhu.sg==YiJing.GENG && shitpgong==bingdpgong))
			w("�˾��죬���߸��ӱ��������򣬻��ߴ�����ĵĿ����г���");
		if(shitpgong==shenhegong) w("ʱ�ɷ����ϣ��Է���̸�и��֡�");
		
		if(shitpgong==shangmengong) w("ʱ�ɷ����ţ�����ǿ���������������ˡ�");
		if(shitpgong==dumengong) w("����ţ����ĵİ��£����ĵľ�����");
		if(shitpgong==jing3mengong) w("�꾰�ţ������Ͷ��֣�ս���Ͷ��֣���������֡�");
		if(shitpgong==kaimengong) w("�꿪�ţ���ͬ�⣬�ð��¡�");
		if(shitpgong==xiumengong || (SiZhu.sg==YiJing.BING && gengdpgong==shitpgong)) w("�����Ż��߱��Ӹ��������ã�Ը�⽲�͡�");
		if(shitpgong==shengmengong) w("�����ţ���Ӯ����");
		if(shitpgong==xingfugong) w("���츨���Ƚ����ţ��в�Σ���˵����");
		if(shitpgong==xingruigong) w("�����ǣ����ð졣");
		
		if(shitpgong==gengtpgong || shitpgong==gengdpgong)
			w("ʱ�ɷ������Ϊ�������׺ݡ�");
		if(shitpgong==xintpgong || shitpgong==xindpgong)
			w("ʱ�ɷ�������Υ�����᲻���ֶΡ�");
		if(shitpgong==yitpgong || shitpgong==yidpgong ||
				shitpgong==bingtpgong || shitpgong==bingdpgong ||
				shitpgong==dingtpgong || shitpgong==dingdpgong)
			w("ʱ�ɷ����棬����ҷ�������");
		
		w("(��)�������ͣ���ȷ�����͵Ĺ����У����ش�֣����ذ��ţ��ٿ����ǣ���󿴰���");
		if(my.isMenFu() || my.isXingFu()) w("��ַ������������");
		if(my.isMenFan() || my.isXingFan()) w("��ַ�������������");
		if(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==2) w("����岻��ʱ���׷�����");
		
		if(ritpgong==gengtpgong && gengtpgong==bingdpgong) w("�չ�����ӱ����������������");
		if(ritpgong==bingtpgong && bingtpgong==gengdpgong) w("�չ�����Ӹ���Ҫ�������أ�");
		if(ritpgong==shangmengong || ritpgong==xiumengong) w("�չ����ٰ��ŵ��У����������ͣ�������������");
		if(ritpgong==shendigong || ritpgong==shentiangong) w("�չ����ٰ����У��������ͣ��ŵ�������");
		if(ritpgong==xingchonggong || ritpgong==xingfugong) w("�չ����پ��ǵ��У���������ͣ��츨��������");
		if((ritpgong==wutpgong && wutpgong==bingdpgong) ||
				(ritpgong==bingtpgong && bingtpgong==wudpgong)) w("�չ�����ӱ��������죬����ƣ���ս��ʤ��");
		if(SiZhu.rg==YiJing.XIN && xintpgong==dingdpgong) w("�չ����Ӷ������̻�����");
		if(zhishigong==ritpgong) w("�ոɳ�ֵʹ�ţ��н���Ч���������Լ�����ذ��»������������ţ�֧�֡�");
		if(ritpgong==dingtpgong && ritpgong==zhishigong) w("�չ�����Ů���ţ����ؾ��̻�����������ؾ�Ӫ��");
		if((ritpgong==xintpgong && xintpgong==yitpgong) 
				||(ritpgong==yitpgong && yitpgong==xindpgong)) w("�չ��������ң��Ҽ������׸񣬺���˫���������Ρ�");
		if(ritpgong==guitpgong && guitpgong==dingtpgong) w("�չ����Ӷ��������޳��п��ࡣ");
		if(ritpgong==dingtpgong && dingtpgong==guidpgong) w("�չ��궡�ӹ�л����������п��࣬���Ƿǡ�");
		if(ritpgong==gengtpgong && 
				(gengtpgong==guidpgong || gengtpgong==rendpgong)) w("�չ�����ӹ��񣩣������ɣ���������");
		if((ritpgong==gengtpgong && gengtpgong==jidpgong) ||
				(ritpgong==bingtpgong)) w("�չ�����Ӽ����̸񣩣��������ǣ�㣸񣩣���֮��������ˣ������ܿ���Ҳָ��ҵ�ڲ����Ҳ��Žᡣ");
		if(my.isTGanMu(SiZhu.rg, SiZhu.rz) || 
				(ritpgong==rendpgong || ritpgong==guidpgong)) w("�ո���Ĺ�����߼��ɡ���ݼ��������ܵ��֣����ɲ�׬ǮҲ����ȥͶ�ʡ�");
		if((ritpgong==gengtpgong && ritpgong==wudpgong) ||
				(ritpgong==wutpgong && ritpgong==gengdpgong)) w("�չ�������죨�����񣩣���Ӹ����ɹ��񣩣��˵ز������أ����ط�����������ã����ٿ��ſ����̳����������ţ���");
		if(gengtpgong==ridpgong || ritpgong==gengdpgong) w("�����ոɣ����ɸ񣩣��ոɼӸ����ɸɸ񣩣����˲������ˣ��������˰��㣨�׻����񣬷��׻�ҧ�ˣ���");
		
		if(my.isTGanMu(SiZhu.rg, SiZhu.rz) || my.isTpJixing(SiZhu.rg, SiZhu.rz)){
			if((SiZhu.rg==YiJing.YI && (ritpgong==6 || ritpgong==7)) ||
					(SiZhu.rg==YiJing.BING && (ritpgong==1)) ||
					(SiZhu.rg==YiJing.DING && (ritpgong==1))) 
				w("�չ�������Ĺ�������ơ����̣���ҵ�ڲ���Э�������Žᡣ");
		}
		if(my.isTDChong(ritpgong) || my.isTGChong(ritpgong)) w("�ոɷ�壬Ӧ�������ж���Ҫ�����������й��ɡ�");
		if(my.isTDChong(shitpgong) || my.isTGChong(shitpgong)) w("ʱ�ɷ�壬Ӧ�������ж���Ҫ�����������й��ɡ�");
		if(my.isTDG3He(ritpgong) || my.isTDGanHe(ritpgong)
				|| my.isTDZi3He(ritpgong) || my.isTG3He(ritpgong) 
				|| my.isTG6He(ritpgong)) w("�ոɷ�ϣ��������ס�ˡ�");
		if(my.isTDG3He(shitpgong) || my.isTDGanHe(shitpgong)
				|| my.isTDZi3He(shitpgong) || my.isTG3He(shitpgong) 
				|| my.isTG6He(shitpgong)) w("ʱ�ɷ�ϣ��������ס�ˡ�");
		if(my.isTpJixing(SiZhu.rg, SiZhu.rz)) w("�ոɷ����ǻ��̣���Ʋ��á�");
		if(ritpgong==gengtpgong && gengtpgong==bingdpgong) w("�ոɸ��ӱ��������������������");
		if(yuetpgong==gengtpgong && gengtpgong==bingdpgong) w("�¸ɸ��ӱ������ҷ�������");
		w("����չ���Ŷݸ�����ս������������ȡʤ����թ����ƶ�����Ҫ���ü�ıȡ�óɹ���");
		
		if((my.isNenpan(ritpgong) && my.isNenpan(yuetpgong)) ||
				(!my.isNenpan(ritpgong) && !my.isNenpan(yuetpgong))) w("��ϻ����Ƿ��ܳ��ã����ոɺ��¸ɣ��ֶ������̻��߶������̣�����ֿ���");
		else
			w("(��)��ϻ����Ƿ��ܳ��ã����ոɺ��¸ɣ���һ��������һ�������̣��طּҡ�");
		w("����ʱѡ����ѡ�����ſ�������֮���������ǵ���ȥ���£����׳ɹ���");
		
		w("(��)���顢���Է�ѡ����ѡ����ֵ��Ϊ�ҷ���ҲΪ�ɳ����أ�ֵʹΪ�ɳ����ˣ��������ˣ���Ϊ�������ˣ�Ϊ������Ϊ�������֡�");
		if(my.isChongke(zhifugong, gengtpgong) ||
				my.isChongke(zhishigong, gengtpgong) ||
				my.isSheng(gengtpgong, zhifugong) ||
				my.isSheng(gengtpgong, zhishigong)) w("ֵ������ֵʹ���˸��������ֵ������ֵʹ�������ܳɹ����ܵ��֣�");
		else
			w("ֵ������ֵʹ�������˸������Ҹ���Ҳ����ֵ����ֵʹ�������鲻�ܳɹ���");
		
		w("(��)ɢ����Ϣ�����̱�"+bingtpgong+"��Ϊ�ң����̸�"+gengtpgong+"��Ϊ�У���������"+shenwugong+"��Ϊ��Ϣ����Ϣ����Ծ�������"+jing3mengong+"��Ϊ����");
		if(my.isChongke(bingtpgong, gengtpgong) ||
				my.isSheng(gengtpgong, bingtpgong)) w("���˸�����������������С�");
		else w("�����˸�����Ҳ�����������鲻���С�");
		if(my.getGongWS(shenwugong)[0].equals("1")) w("������"+shenwugong+"�����࣬ɢ������ȽϹ㣻");
		else w("������"+shenwugong+"��������ɢ������Ƚ�խ��");
		
		if(jing3mengong==shenshegong || jing3mengong==shenwugong) 
			w("�����ϳ����߻����䣬Ϊ����Ϣ��");
		if(my.isKong(jing3mengong)) w("��������������ʾû����Ϣ��");
		if(my.getmenWS(jing3mengong)[0].equals("1")) w("�����乬���࣬Ϊ����Ϣ��");
		else w("�����乬������Ϊ����Ϣ��");
	}
}













