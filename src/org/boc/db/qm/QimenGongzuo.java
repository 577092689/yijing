package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenGongzuo {
	/**
	 * PRIVATE: �����۶ϣ���ֻ�����������صĹ���������������˿��ţ����������ǵȣ�
	 * PUBLIC������������޹ص�������������������꿪�ſ���ɡ�ʱ�ɿ��ոɵȣ�
	 */
	private static final int PRIVATE = 1,PUBLIC=2; 
	private DaoQiMen daoqm;
	private QimenPublic my; 
	private String mingzhu;
	private int yongshen; 
	private boolean boy; 
	private String[] swork ; //�������˹����е��������
	int index = 0; //��������ΪdescMY����

	public QimenGongzuo(DaoQiMen daoqm, QimenPublic my) {
  	this.daoqm = daoqm;
  	this.my = my;
  }
  
	public String getWork(StringBuffer str,String mingzhu, int yongshen,boolean boy) {
		swork = new String[500];
		index = 0;
		this.mingzhu = mingzhu;
		this.yongshen = yongshen;
		this.boy = boy;		
		
		swork[index++]=my.NOKG+"һ��ȡ����";
		getWork1();
		swork[index++]=my.NEWLINE;
		
		String[] ysinfo = my.getYShenInfo(yongshen, 0, 0);		
		String yshen = ysinfo[0];               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
		swork[index++]=my.NOKG+"�������ֶ�["+yshen+"]��";
		getWork2();
		swork[index++]=my.NEWLINE;
		
		swork[index++]=my.NOKG+"������١���ְ����ҵ�����顢�������["+yshen+"]��";
		getWork3(PRIVATE, yongshen,0,0);  //�����йص�
		getWork3(PUBLIC, yongshen,0,0);   //�����Ĳ����������
		swork[index++]=my.NEWLINE;
		
		swork[index++]=my.NOKG+"�ġ��������ڶ�["+yshen+"]��";
		getWork4(PRIVATE, yongshen,0,0);
		getWork4(PUBLIC, yongshen,0,0);
		swork[index++]=my.NEWLINE;
		
		//����������ο�����ֻ��Ҫ��������ص���Ϣ��PRIVATE����
		int[] mzhu = my.getMZhu(mingzhu);
		if(mzhu.length>1 && mzhu[0] * mzhu[1]!=0) {
			swork[index++]=my.NOKG+"�塢�����ο�["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]";
			swork[index++]="------------------------------��ϸ��ϲο�------------------------------";
			getWork3(PRIVATE, 0, mzhu[0],mzhu[1]);
			swork[index++]=my.NEWLINE;
			
			swork[index++]="------------------------------��������ο�------------------------------";
			getWork4(PRIVATE, 0, mzhu[0],mzhu[1]);
			swork[index++]=my.NEWLINE;
		}
		
		return my.format(str, swork);
	}
	
	/**
	 * һ��ȡ����
	 */
	public void getWork1() {
		swork[index++]="���Ϊ�ϼ����߼���ί���ϼ���׼���أ�";
		swork[index++]="�¸�Ϊͬ�£�";
		swork[index++]="�ո�Ϊ����ߣ�";
		swork[index++]="ʱ��Ϊ�¼���Ⱥ�ڡ����塢�������ż����Ů������������";
		swork[index++]="����Ϊ��������λ�����ǡ���Ժ�����١����᣻";
		swork[index++]="����Ϊ��ְ��������λ�����Ӹɲ���ʿ��תҵ����Ĺٳ���";
		swork[index++]="ֵ��Ϊ��ͷ��˾���࿼�����С���ί��";
		swork[index++]="ֵʹ��Ҳ�����塢���٣�";
		swork[index++]="��Ϊ�������������";
		swork[index++]="�濴������"; 
	}
	/**
	 * �������ֶ�
	 */
	public void getWork2() {		
		if(my.isMenFu()) swork[index++]="���ŷ��������������ͣ���ʹ�ࡢ���ˡ�ʱ�䳤������������ά��ԭ״���⹤����ְ�ǲ��ˣ�";
		if(my.isMenFan()) swork[index++]="���ŷ��ʣ����Ͳ������������鷴����������ɰ��׷֣��ٶȿ죻�����Ρ������䶯���⹤�������سɣ�";
		if(my.isXingFu()) swork[index++]="���Ƿ��ʣ����������ͣ���ʹ�ࡢ���ˡ�ʱ�䳤������������ά��ԭ״���⹤����ְ�ǲ��ˣ�";
		if(my.isXingFan()) swork[index++]="���Ƿ��ʣ����Ͳ������������鷴����������ɰ��׷֣��ٶȿ죻�����Ρ������䶯���⹤�������سɣ�";
	}
	/**
	 * ������١���ְ����ҵ�����顢�������
	 * gInt[1][1][1-9]=����,gInt[1][2][1-9]=��������
   * gInt[2][1][1-9]=����˳������2��������,3�������ǣ�������У��������ǲ�֧
   * gInt[3][1][1-9]=��������,gInt[3][2]=������
   * gInt[4][1][1-9]=�Ź������������2�������У�3���̾��ǣ�4���̰��ţ�5�������ǣ�6�����������С�7�������׵�֧
	 */
	public void getWork3(int all, int ystype, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		int ysgan = Integer.valueOf(ysinfo[2]);       //��������
		int yszi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		int gong = Integer.valueOf(ysinfo[1]);  //�����乬
		int[] tpjy = my.getTpjy(gong);
		int[] dpjy = my.getDpjy(gong);
		int ngtpgong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //������������乬
		int ngdpgong = my.getDiGong(SiZhu.ng, SiZhu.nz);  //������ɵ����乬
		int kaimengong = my.getMenGong(QiMen.MENKAI);  //�����乬			
		int shigangong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //ʱ���乬
		int zhifugong = daoqm.getZhifuGong();
		int yuegong = my.getTianGong(SiZhu.yg, SiZhu.yz);
		
		/** 1. �������ٶ� */
		String[] ysheju = my.isganHeju(ysgan, 0);
		String[] ysjxge = my.getJixiongge(gong); //���׸�
		String[] ysxing = my.getxingJX(gong);  //�ǵļ���
		String[] ysshen = my.getshenJX(gong);  //��ļ���		
		String t = null;
		if(all==PRIVATE) {
			if(ysheju[0].equals("1")) {
				t="������"+gong+"���Ͼ֣�����״̬�ȽϺã�";
				if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG && ysjxge[0].equals("1"))
					t+="�������ţ�����Ϊ��Ȩ����֮�ˣ�"+ysjxge[1];
				swork[index++] = t+ysheju[1];
			}else{
				swork[index++] = "������"+gong+"��ʧ�֣�����״̬����ѵõ���ְ�͹�����"+ysheju[1];
			}
			if(ysxing[0].equals("1") && ysshen[0].equals("1")) swork[index++] = "�����ټ��Ǽ������������������й���������";
			
			if(gong==zhifugong) {
				if(gong==1 || gong==3 || gong==7 || gong==9)
					t = "������"+gong+"��������ֵ����Ϊ����ְ���쵼��˾�����ˣ�";
				else if(gong==6)
					t = "������"+gong+"��������ֵ����Ϊĳ��λ�쵼�����ˣ�";
				else
					t = "������"+gong+"��������ֵ��������Ϊ��ְ���쵼��˾�����ˣ�";
				String[] zhifuhj = my.isganHeju(gong);
				if(zhifuhj[0].equals("1")) t +="���кϾ֣�ӦΪ��Ȩ����֮�ˣ�"+zhifuhj[1];
				else t +="����ʧ�֣��ٽײ���ӦΪ�Ƴ�����֮�ࣻ"+zhifuhj[1];
				if(t!=null) swork[index++] = t;
			}
			
			if(gong==my.getMenGong(QiMen.MENXIU)) {
				t = "���������ţ��������ˡ��������ڼң�";
				if(my.getMenGong(QiMen.MENKAI)==6) t+="������Ǭ����ӦΪ��ְ��";
				if(my.isKong(6)) t+="������Ѯ�գ�˵���Ѵ�ְ��";
				swork[index++] = t;
			}
			
			if(isThisGeju(gong,56))	swork[index++] = "������������ߣ�����������ݣ���Ҫ��ְ��"+QiMen.gGejuDesc[56][1];
			if(isThisGeju(gong,65))	swork[index++] = "�����ӫ��̫�ף�����������ݣ���Ҫ��ְ��"+QiMen.gGejuDesc[65][1];
			if(isThisGeju(gong,101))	swork[index++] = "�����̫����ӫ����׼��������ݡ���ְ��"+QiMen.gGejuDesc[101][1];
			if(isThisGeju(gong,110))	swork[index++] = "�����׻����񣬲�׼��������ݡ���ְ��"+QiMen.gGejuDesc[110][1];
			if(isThisGeju(gong,132))	swork[index++] = "�����Ο��ز�ã������й�˾���������������ְ�����˲��ܣ�"+QiMen.gGejuDesc[132][1];
			if(isThisGeju(gong,108))	swork[index++] = "������񣬿��ܻᱻ��ְ���򱻹�˾�����㣻"+QiMen.gGejuDesc[108][1];
			if(isThisGeju(gong,78))	swork[index++] = "������ȸͶ�������ܻᱻ��ְ���򱻹�˾�����㣻"+QiMen.gGejuDesc[142][1];
			
			if(daoqm.gInt[3][1][gong]==QiMen.MENJING1) swork[index++] = "�����پ��ţ�����˾���ࣻ";
			if(daoqm.gInt[3][1][gong]==QiMen.MENSHANG) swork[index++] = "���������ţ�˵���о���������";
			if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG) swork[index++] = "���������ţ�˵��������йأ�";
			if(daoqm.gInt[3][1][gong]==QiMen.MENDU) swork[index++] = "�����ٶ�����������";
			if(daoqm.gInt[3][1][gong]==QiMen.MENSI) swork[index++] = "������������������";
			if(daoqm.gInt[3][1][gong]==QiMen.MENJING3 && daoqm.gInt[1][1][gong]==QiMen.SHENHU) {
				t = "�������д�ѧ��ƾ���׻������Ļ����־��żӰ׻���ӦΪ��������";
				if(QiMen.men_gong[QiMen.MENJING3][gong].equals(QiMen.zphym1)) t+="�������乬���ƣ�����Ƴɼ����ã�";
				if(my.isSheng(gong, my.getTianGong(YiJing.DING, 0))) t+="�������棬������ѧ�ĿƵģ�";
				swork[index++] = t;
			}

			if(daoqm.gInt[2][1][gong]==QiMen.XINGFU) swork[index++] = "�������츨�ǣ�˵�������ܸɣ����Ļ���";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGCHONG) swork[index++] = "��������壬����ˬ�����䣻";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGREN) swork[index++] = "�����������ǣ����飻";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGXIN) swork[index++] = "�����������ǣ���ֱ��";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGQIN) swork[index++] = "�����������ǣ��Һ�";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGYING) swork[index++] = "��������Ӣ�ǣ����ң�";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGRUI) swork[index++] = "�����������ǣ�̰����";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGZHU) swork[index++] = "�����������ǣ���թ��";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGPENG) swork[index++] = "�����������ǣ������֮�ˣ�";
			
			if(daoqm.gInt[1][1][gong]==QiMen.SHENTIAN) swork[index++] = "�����ϳ˾��죬���ø���Զ��Ҳ���䶯֮��";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENHU) swork[index++] = "�����ϳ˰׻���������ǣ����";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENSHE) swork[index++] = "�����ϳ����ߣ�������ݳ������²��ƣ�Ҳ������֮��";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENDI) swork[index++] = "�����ϳ˾ŵأ�������֮��";
			
			int sigong = my.getMenGong(QiMen.MENSI);
			String[] simenheju = my.ismenHeju(sigong);
			if(my.isChongke(sigong, gong)) {
				t = "������"+sigong+"�����������"+gong+"�������������������Ѻ�������"; 
				if(!simenheju[0].equals("1")) t+="�������乬ʧ�֣���������Խ����"+simenheju[1];
				swork[index++] = t;
			}
			
			int shengmengong = my.getMenGong(QiMen.MENSHENG);		
			int wutgong = my.getTianGong(YiJing.WUG, 0);
			int wudgong = my.getDiGong(YiJing.WUG, 0);
			String[] shengmenwx = my.getmenWS(shengmengong);
			String[] caixingwx = my.getxingWS(shengmengong);
			if(wutgong==gong || wudgong==gong) {
				if(!shengmenwx[0].equals("1") || !caixingwx[0].equals("1")) 
					swork[index++] ="��������ͬ��"+gong+"���������Ż���ǲ��������ڴ���Ǯ���ࣻ";
			}			
			if(shengmengong==gong && (wutgong==gong || wudgong==gong)) {
				swork[index++] = "������"+gong+"���������ţ������죬��������ģ�";
			}
		}else{			
			int[] ktpjy = my.getTpjy(kaimengong);
			int[] kdpjy = my.getDpjy(kaimengong);
			t=null;
			String[] kheju = my.isganHeju(ktpjy[0], 0);
			if(ktpjy[0]==YiJing.GENG || ktpjy[1]==YiJing.GENG) t="������"+kaimengong+"�������̸���֤���Ǵ�λ��Ҳ��������˳����";
			else if(kdpjy[0]==YiJing.GENG || kdpjy[1]==YiJing.GENG) t="������"+kaimengong+"���ٵ��̸���֤���Ǵ�λ��Ҳ��������˳����";
			if(t!=null && kheju[0].equals("1")) t+="�ֱ����Ͼ֣�������λЧ��ã�"+kheju[1];
			else if(t!=null) t+="������ʧ�֣�������λЧ�治�ã�"+kheju[1];				
			if(t!=null) swork[index++] = t;
		}
		
		/** 2. �Ƿ����䶯 */
		if(all==PRIVATE) {
			if(my.isTChong(gong)) swork[index++] = "������"+gong+"�����̸��빬��壬�������䶯֮��";
			if(my.isTDChong(gong)) swork[index++] = "������"+gong+"�����������壬�������䶯֮��";
			if(my.isChongke(kaimengong, gong)) swork[index++] = "������"+kaimengong+"���������"+gong+"��������������������ƸҲ��������λ¼ȡ���������������˳�";
			if(my.isTDGanHe(gong)) swork[index++] = "����"+gong+"������������̸���ϣ�Ҳ����������֮��";
			if(my.isKong(gong)) swork[index++] = "������"+gong+"�����������и�ְ֮�ǣ�Ҳ�������������²���֮��"; 
			boolean tgmu = my.isTGanMu(ysgan,0);
			boolean dgmu = my.isDGanMu(ysgan,0);
			if(tgmu) swork[index++]="������"+gong+"�����̸ɴ�Ĺ�أ�����������������������";
			if(dgmu) swork[index++]="������̸���"+my.getDiGong(ysgan, 0)+"����Ĺ�أ�����������������������";
			if(my.getDiGong(ysgan, 0)==daoqm.getZhifuGong()) {
				t = "������̸�����ȥ״̬������"+my.getDiGong(ysgan, 0)+"���ϳ�ֵ�����������쵼֧�֣�";
				if(my.isTDGanHe(my.getDiGong(ysgan, 0))) t+="�������������ϣ���˵�����쵼��ϵ����";
				swork[index++] = t; 
			}
			if(tpjy[0]==YiJing.GENG || tpjy[1]==YiJing.GENG ){
				swork[index++] = "������"+gong+"�����ٸ���Ϊ�������������֮��";
			}else if( dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG) {
				swork[index++] = "������"+gong+"�����ٸ���Ϊ�������������֮��";
			}
		}else{
			if(my.isKong(shigangong)) swork[index++]="ʱ����"+shigangong+"��������Ҳ��������ղ���֮��";
			if(my.isKong(kaimengong)) swork[index++] = "������"+kaimengong+"��������Ҳ��Ҫ��ְ���⿪�ꡢ�칫˾�꿪��Ϊ�մ��ף���ʾ����Ҫ���ţ�";
			if(my.isTChong(kaimengong)) swork[index++] = "������"+kaimengong+"�����̸��빬��壬Ҳ������������"; 
			if(my.isTDChong(kaimengong)) swork[index++] = "������"+kaimengong+"�����̸�����̸���壬Ҳ������������";
			if(my.isYima(kaimengong)) swork[index++] = "������"+kaimengong+"��������Ҳ������������";
			if(my.isYima(my.getChongGong(kaimengong))) swork[index++] = "������"+kaimengong+"����������"+my.getChongGong(kaimengong)+"����壬Ҳ������������";
			
			int[] kmtpjy = my.getTpjy(kaimengong);
			int[] kmdpjy = my.getDpjy(kaimengong);			
			t=null;
			if(my.isGeju(kaimengong, 125)) t = "������"+kaimengong+"�����ɼӸ���Ҳ��Ϊ�Ƶ����������䶯��"; 
			else if(my.isGeju(kaimengong, 107)) t = "������"+kaimengong+"���ٸ����ɣ��ֳ�Ϊ�Ƶ����������䶯��";
			else if(kmtpjy[0]==YiJing.REN || kmtpjy[1]==YiJing.REN) t = "������"+kaimengong+"�������ɣ�Ҳ������������";
			else if(kmdpjy[0]==YiJing.REN || kmdpjy[1]==YiJing.REN) t = "������"+kaimengong+"�������ɣ�Ҳ������������";
			if(t!=null) if(kaimengong == my.getShenGong(QiMen.SHENHE)) t+="�������ϣ�������ͬʱ�䶯��";
			if(t!=null) swork[index++] = t;
			
			if(my.isJixing(kaimengong)) swork[index++] ="������"+kaimengong+"�������ǻ��̣�Ҳ����ְ��";
			if(ystype==my.YSHENDAY || ystype==my.YSHENMONTH) {				
				if(my.isTChong(yuegong)) swork[index++] ="�¸���"+yuegong+"�������̸��빬��壬Ҳ��ͬ��Ҫ�䶯��";
				if(my.isTDChong(yuegong)) swork[index++] ="�¸���"+yuegong+"�������̸�����̸���壬Ҳ��ͬ��Ҫ�䶯��";
			}
		}
		
		/** 3. ������������� */
		if(all==PRIVATE) {
			if(my.isGongke(gong, kaimengong)) t ="����˿���"+kaimengong+"��������Ŭ��Ҳ�ܵù������ְ��Ҳ�����˲����ڵ�λ���ˣ��뻻������";
			else if(my.isGongSheng(kaimengong, gong)) t ="������"+kaimengong+"������������ְ�ù�˳��������֮�󣻲����������λ���õ��ߣ��й����Ĵ�����ҵ��ǿ������˳����";
			else if(my.isGongSheng(gong,kaimengong)) t ="����������"+kaimengong+"����˵������õ���ݹ���������Ը�����й����Ĵ�����ҵ��ǿ������˳����";
			else if(my.isChongke(kaimengong, gong)) t ="������"+kaimengong+"���������Ҳ����ְ�������ɴ˹�����ְλ����λ����Ҫ�����ˡ�";
			else if(gong==kaimengong) t = "�����뿪��ͬ��"+kaimengong+"����־�ڱصã��й����Ĵ�����ҵ��ǿ������˳����";
			else if(my.isBihe(kaimengong, gong)) t = "������"+gong+"���뿪����"+kaimengong+"���Ⱥͣ��й����Ĵ�����ҵ��ǿ������˳����";
			if(my.isKong(kaimengong)) t+="������Ѯ�գ�ʧȥ���ã�";
			swork[index++] = t; 
			
			int dumengong = my.getMenGong(QiMen.MENDU);
			if(my.isGongke(gong, dumengong)) t ="����˶�����"+dumengong+"��������Ŭ��Ҳ�ܵù������ְ��";
			else if(my.isGongSheng(dumengong, gong)) t ="������"+dumengong+"������������ְ�ù�˳��������֮�󣻶�תҵ���飬��������Ҫ�쵼������������׼�ˣ�";
			else if(my.isGongSheng(gong,dumengong)) t ="������������"+dumengong+"����˵������õ���ݹ���������Ը����";
			else if(my.isChongke(dumengong, gong)) t ="������"+dumengong+"���������Ҳ����ְ����תҵ������ˣ�";
			else if(gong==dumengong) t = "���������ͬ��"+dumengong+"��������ְ�ص�֮��";
			else if(my.isBihe(dumengong, gong)) t = "�����������"+dumengong+"���Ⱥͣ���תҵ������ˣ�";
			if(my.isKong(dumengong)) t+="������Ѯ�գ�ʧȥ���ã�";
			swork[index++] = t; 
			
			if(my.isSheng(zhifugong, gong)) swork[index++] = "ֵ����"+zhifugong+"��������Ҳ���ܽ���֮��";
			else if(my.isChongke(zhifugong, gong)) swork[index++] = "ֵ����"+zhifugong+"�������񣬶�ͷ��˾�����⣬�н�ְ֮Σ��";
			
			if(ngtpgong==gong) {
				swork[index++] = "������"+gong+"��������ɣ�Ϊ��̫�꣬������������֮��Ҳ���õ��쵼֧�֣�";
			}else if(ngdpgong==gong) {
				swork[index++] = "������"+gong+"��������ɣ�Ϊ��̫�꣬������������֮��Ҳ���õ��쵼֧�֣�";
			}else if(my.isSheng(ngtpgong, gong)) {
				swork[index++] = "̫����"+ngtpgong+"�������񣬱�ʾ�ܵõ��ϼ��쵼��֧�֣�Ҳ˵�����쵼��ϵ��Ǣ�����ΪѧУ�����ҵ����Ҳ��˳����";
			}else if(my.isBihe(ngtpgong, gong)) {
				swork[index++] = "̫����"+ngtpgong+"��������Ⱥͣ�˵�����쵼��ϵ��Ǣ�����ҵ����Ҳ��ʾ˳����";
			}else if(my.isGongke(ngtpgong, gong)) {
				swork[index++] = "̫����"+ngtpgong+"�����������ϼ��쵼�����ˣ�����������쵼Ҫ������Լ���Ը��";
			}
			
			if(my.isChongke(yuegong, gong)) {
				t = "�¸���"+yuegong+"����������ͬ�»������ֵ���";
				if(yuegong == my.getShenGong(QiMen.SHENWU)) t+= "���ϳ����䣬�����д�С���棻";
				swork[index++] = t;
			}
			if(my.isChongke(shigangong, gong)) {
				t = "ʱ����"+shigangong+"����������������Ⱥ������״��";
				if(shigangong == my.getShenGong(QiMen.SHENWU)) t+= "���ϳ����䣬�����д�С���棻";
				swork[index++] = t;
			}
		}else{
			if(my.isChongke(kaimengong, ngtpgong)) swork[index++] = "������"+kaimengong+"�������"+ngtpgong+"���������쵼�Ĺ������øɣ�����ѹ����";
			if(my.isChongke(shigangong, ngtpgong)) swork[index++] = "ʱ����"+shigangong+"�������"+ngtpgong+"���������쵼�ĵ��Ӻ��أ������Ѷȴ�";
			
			t = "ʱ����"+shigangong+"��";
			boolean isshigan = false;
			if(shigangong==my.getMenGong(QiMen.MENSI)) {
				t+="�������ţ����²���֮��";isshigan=true;
			}
			if(shigangong==my.getMenGong(QiMen.SHENDI)) {
				t+="���پŵأ����²���֮��";isshigan=true;
			}
			String[] shihj = my.isganHeju(SiZhu.sg, SiZhu.sz);
			if(!shihj[0].equals("1")) {
				t+="��ʧ�֣����²���֮��"+shihj[1];isshigan=true;
			}
			String shismj = my.isTGanSMJ(SiZhu.sg, SiZhu.sz); {
				if(shismj!=null) t+="ʱ��������Ĺ��Ҳ����ʦ���������²�˳����";isshigan=true;
			}
			if(isshigan) swork[index++] = t;
			
			if(my.isChongke(shigangong, gong)) swork[index++] = "ʱ��"+shigangong+"�����ո�"+gong+"����Ҳ�����������أ�"; 
			int dinggong = my.getTianGong(YiJing.DING, 0);
			int[] dingdpjy = my.getDpjy(dinggong);
			if(dingdpjy[0]==YiJing.GENG || dingdpjy[1]==YiJing.GENG) 
				swork[index++] = "����"+dinggong+"�����ٸ���˵���������裬һʱ�첻������";
		}
	}
	/**
	 * �ġ��������ڶ�
	 */
	public void getWork4(int all, int ystype, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		int ysgan = Integer.valueOf(ysinfo[2]);       //��������
		int yszi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		int gong = Integer.valueOf(ysinfo[1]);  //�����乬
		int kaimengong = my.getMenGong(QiMen.MENKAI);  //�����乬			
		int shigangong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //ʱ���乬
		int rigangong = my.getTianGong(SiZhu.rg, SiZhu.rz);
		String t = null;
		
		if(all==PRIVATE) {
			if(my.isTianKeDi(gong)) swork[index++] = "����"+gong+"�������̸ɿ˵��̸ɣ�˵�����Ͳ����������ڵ�����";
			else if(my.isDiShengTian(gong)) swork[index++] = "����"+gong+"���е��̸������̸ɣ�˵�����Ͳ����������ڵ�����";
			else if(my.isDiKeTian(gong)) swork[index++] = "����"+gong+"���е��̸ɿ����̸ɣ�˵�����������ͣ������ڵ�����";
			else if(my.isTianShengDi(gong)) swork[index++] = "����"+gong+"�������̸������̸ɣ�˵�����������ͣ������ڵ�����";
			if(my.isKong(gong)) swork[index++] = "�����乬Ѯ�գ���ʵ���ʵ֮�գ�����ض���üĿ��";
		}else{ 
			if(my.isTianKeDi(shigangong)) swork[index++] = "ʱ��"+shigangong+"�������̸ɿ˵��̸ɣ�˵�����Ͳ����������ڵ�����";
			else if(my.isDiShengTian(shigangong)) swork[index++] = "ʱ��"+shigangong+"���е��̸������̸ɣ�˵�����Ͳ����������ڵ�����";
			else if(my.isDiKeTian(shigangong)) swork[index++] = "ʱ��"+shigangong+"���е��̸ɿ����̸ɣ�˵�����������ͣ������ڵ�����";
			else if(my.isTianShengDi(shigangong)) swork[index++] = "ʱ��"+shigangong+"�������̸������̸ɣ�˵�����������ͣ������ڵ�����";
									
			boolean rinei = my.isNenpan(rigangong);
			boolean shinei = my.isNenpan(shigangong); 
			t = null;
			if(rinei && shinei) t = "����"+rigangong+"����ʱ��"+shigangong+"�����������̹������죻";
			else if(!rinei && !shinei) t = "����"+rigangong+"����ʱ��"+shigangong+"�����������̣�ʱ�䲻��̫�죻";
			else t = "����"+rigangong+"����ʱ��"+shigangong+"����һ��һ�⣬������";				
			if(t!=null) swork[index++] = t;
			
			if(my.isKong(kaimengong)) swork[index++] = "������"+kaimengong+"��Ѯ�գ���ʵ���ʵ֮�գ���λ�ض���ʵ��";
			if(my.isKong(my.getMenGong(QiMen.MENJING3))) swork[index++] = "��������Ϣ������"+my.getMenGong(QiMen.MENJING3)+"������������ʵ���ʵ֮�ձ�����Ϣ��";
			if(my.isKong(my.getTianGong(YiJing.DING,0))) {
				t = "�����������Ҳ���������"+my.getTianGong(YiJing.DING,0)+"������������ʵ���ʵ������Ϣ���ο���������֮�ɣ�";
				swork[index++] = t;
			}else{
				swork[index++] = "�����������Ҳ������ɲο���������֮�ɶ�Ӧ�ڣ�";
			}
			
			String fxiang = QiMen.JIUGONGFXIANG[kaimengong]; //�õ����Ź��ķ�λ
	    boolean isnei = my.isNenpan(kaimengong);
	    String neiwai = my.isNenpan(kaimengong)?"ʡ��":"ʡ��";   //�ж������̻�������
	    swork[index++]="������"+kaimengong+"��������"+(isnei?"����":"����")+"�������������λ�ھ�ס��"+neiwai+fxiang+"��";
	    swork[index++] = "���������������֮�ɡ���������"+my.getTianGong(YiJing.DING,0)+"����Ҳ�ɶϷ���";
		}
	}
	
	/**
	 * �Ƿ�����geju��ָ����gong�������ʮ�ɿ�Ӧ
	 * @param geju
	 * @return
	 */
	private boolean isThisGeju(int gong, int geju) {
		int[] ysky = my.getShiganKeying(gong);  //���񹬵�ʮ�ɿ�Ӧ
		for(int ge : ysky)
			if(ge==geju) return true;
		return false;
	}
}