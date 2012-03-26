package org.boc.db.qm;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Messages;

public class QimenMingyun extends QimenBase{	
	private final String JIINDEX = "��";
	private final String XIINDEX = "��";
	private final String PIINDEX = "��";  //���������
	private final int INDEXLINE = 0;  //�����ָ��ָ���ڴ���������ʾ����ݣ�������ʾ
	String t = null;
	int year;   		//�������
	
	public QimenMingyun(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
	public String getLife(StringBuffer str,String mzText, int ysNum,boolean boy){
		return this.getLife(str, mzText, ysNum, boy, 0);
	}
	public String getLife(StringBuffer str,String mzText, int ysNum,boolean boy, int year) {
		this.year = year;
		//process(year);
		init(mzText, ysNum, boy, 2000);		
		
		w(p.NOKG+"һ�����ֶϣ�");
		getLife1();
		w(p.NEWLINE);
		
		w(p.NOKG+"�������˶ϣ�");
		getLife2();
		w(p.NEWLINE);
		
		w(p.NOKG+"�������˶ϣ�"); //����ְҵ
		getLife3();
		w(p.NEWLINE);
		
		w(p.NOKG+"�ġ�������ͥ�ϣ�");
		getLife4();
		w(p.NEWLINE);
		
		w(p.NOKG+"�塢ѧ���Ļ��ϣ�");
		getLife5();
		w(p.NEWLINE);
		
		w(p.NOKG+"���������ϣ�");
		getLife6();
		w(p.NEWLINE);
		
		w(p.NOKG+"�ߡ����׶ϣ�");
		getLife7();
		w(p.NEWLINE);
		
		w(p.NOKG+"�ˡ����˶ϣ�");
		getLife8();
		w(p.NEWLINE);
		
		w(p.NOKG+"�š�����ϣ�");
		getLife9();
		w(p.NEWLINE);

		return p.format(str, my);
	}
	
	/**
	 * һ�����ֶ�
	 */
	public void getLife1() {				
		int rgzt = Integer.valueOf(rgws[0]);
		int jxzt = Integer.valueOf(rgjxws[0]);
		int bmzt = Integer.valueOf(rgbmws[0]);
		int bszt = Integer.valueOf(rgbsjx[0]);
		
		w("1. ��ʱ�����˺������");
		w("�ո�"+rgws[1]+"Ϊ"+YOUQI[rgzt+2]+"��"+(rgzt>0?TDRH1[1]:TDRH2[1]),true);
		w("����"+rgjxws[1]+"Ϊ"+YOUQI[jxzt+1]+"��"+(jxzt>0?TDRH1[0]:TDRH2[0]),true);
		w(ritpgong==xingzhugong,"�ո��������ǣ��ʺϴ����ƾ����µ���ҵ���������������������Ʊ�����ԣ�",true);		
		w(ritpgong==xingqingong,"�ո��������ǣ�Ϊ�ٹ�֮�ף�",true);
		w(ritpgong==xingpenggong,"�ո��������ǣ������ǻۣ�Ҳ�е�����Ϊ��һ�棻",true);
		w("����"+rgbmws[1]+"Ϊ"+YOUQI[bmzt+1]+"��"+(bmzt>0?TDRH1[2]:TDRH2[2]),true);
		w(ritpgong==jing1mengong,"�ո��پ��ţ���˵����ʦ���ݽ�����ǿ��",true);
		w(ritpgong==kaimengong,"�ոɷ꿪�ţ�Ϊ�����󽫣�",true);
		w(ritpgong==xiumengong,"�ո�������Ϊ����Ϊ�ڲ����罻�ֶθߣ����ڴ����������ҹ�ϵ��",true);
		w("����"+rgbsjx[1]+(bszt>0?TDRH1[3]:TDRH2[3]),true);
		w(ritpgong==shenwugong,"�ո������䣬Ϊ��״Ԫ�������ǻۣ��������죻",true);
		w(ritpgong==shenhugong,"�ո��ٰ׻��������ף��������б�����ȴ�����ɵ���������Ϊ�׻�Ϊ��״Ԫ��",true);
		w(ritpgong==shendigong,"�ո��پŵأ���ƽ�ȡ���ʵ��ʵ�ڡ�Ҳ����ʵʵ�ڣ�ƽ�ȣ�һ�������ţ�",true);
		
		w("2. ֵ����ֵʹ��");
		w(boy && yang,"����������Ϊ˳������",true);
		w(boy && !yang,"���������ݲ�˳������������Ϣ�࣬Ϊ�����᣻",true);
		w(!boy && yang,"Ů�������ݲ�˳��������",true);
		w(!boy && !yang,"Ů��������Ϊ˳������",true);
		int zfxing = daoqm.gInt[2][1][p.getJiGong528(zhifugong)];  //ֵ���ǣ����Ϊ5��ת�ɶ�Ӧ�ļĹ�
		int zsmen = daoqm.gInt[3][1][p.getJiGong528(zhishigong)];  //ֵʹ��
		String zfname = "ֵ��[��"+QiMen.jx1[zfxing]+"]";
		String zsname = "ֵʹ["+QiMen.bm1[zsmen]+"��]";
		int rgxing = daoqm.gInt[2][1][ritpgong];  //�ո�����
		int rgmen = daoqm.gInt[3][1][ritpgong];  //�ո�����
		String rxname = "�ո�����[��"+QiMen.jx1[rgxing]+"]";
		String rmname = "�ո�����["+QiMen.bm1[rgmen]+"��]";
		
		w(QiMen.jxjx[zfxing]==1,zfname+"�����������󼪣�",true);
		w(QiMen.jxjx[zfxing]==-1,zfname+"����������������",true);
		w(QiMen.jxjx[zfxing]==0,zfname+"Ϊ����������ƽ��",true);
		
		w(QiMen.bmjx[zsmen]==1,zsname+"����ֵ�࣬�󼪣�",true);
		w(QiMen.bmjx[zsmen]==-1,zsname+"����ֵ�࣬������",true);
		w(QiMen.bmjx[zsmen]==0,zsname+"ƽ��ֵ�࣬ƽ��",true);
		
		w(getXingRel(zfname,rxname,zfxing,rgxing,new String[]{"�ɹ������϶�","��ʱ�������ѵ����գ����ո�����֮���������Է��ӣ���ʹΪ�ٲ���ܴ�","һ�������϶�","����","��"}),true);
		w(getXingRel(zsname,rmname,zsmen,rgmen,new String[]{"�ɹ������϶�","���²��������ո�����֮���������Է��ӣ���ʹΪ�ٲ���ܴ�","һ�������϶�","����","��"}),true);
		
		w("3. �ո���ʱ�ɣ�");
		if(rgheju[0].equals("1")) w("�乬�Ͼ֣������˺ã�"+rgheju[1],true);
		else if(rgws[0].equals("-1") && rgsanji[0].equals("-1") && rgbmjx[0].equals("-1") && rgjxjx[0].equals("-1") && rgbsjx[0].equals("-1"))
			w("�乬�������棬��������������ƶ�����˲��ѣ�"+rgws[1]+rgsanji[1]+rgbmjx[1]+rgjxjx[1]+rgbsjx[1],true);
		else w("�乬ʧ����������ƽ�����죻"+rgheju[1],true);
		
		boolean isRiKong = p.isKong(ritpgong, p.SHIKONGWANG); //�չ��Ƿ�Ѯ�գ���ʱ��Ϊ���жϣ��������ж���
		boolean isRiChKong = p.isChKong(ritpgong, p.SHIKONGWANG); //�չ��Ƿ��ǿ����Գ�֮��
		String rgSMJ = p.isTGanSMJ(SiZhu.rg, SiZhu.rz);  //�ж��ո��Ƿ�����Ĺ��
		boolean isshiKong = p.isKong(shitpgong, p.SHIKONGWANG); //ʱ�ɹ��Ƿ�Ѯ�գ���ʱ��Ϊ���жϣ��������ж���
		boolean isshiChKong = p.isChKong(shitpgong, p.SHIKONGWANG); //ʱ�ɹ��Ƿ��ǿ����Գ�֮��
		w(isRiKong,"�ո������֮������������Ϊ�£������乬�������߼��ٶ����߸��ࡣ",true);
		w(isRiChKong,"�ո�������Գ�֮����������Գ�֮��Ϊ�飻�����乬�������߼��ٶ����߸��ࡣ",true);		
		w(isRiKong && isshiChKong,"�ո����������ʱ��������Գ�֮����������ʱ�ֱ�Ϊ���ɻ�����������ʱ�����޿���",true);
		w(isshiKong && isRiChKong,"ʱ�ɿ������ո�Ϊ�����Գ�֮����������ʱ�ֱ�Ϊ���ɻ������������µ���������",true);
		w(rgSMJ!=null,"�ո��乬��"+rgSMJ+"�أ��ո���Ĺ��֮����һ����ü��չ��",true);
		String[] shiheju = p.isganHeju(SiZhu.sg, SiZhu.sz);
		w(shiheju[0].equals("1"), "ʱ�ɺϾ֣��������������ʱ��Ϊ���ս�֣������ǡ��š���͸�ֿɶ����ս����"+shiheju[1],true);
		w(!shiheju[0].equals("1"), "ʱ��ʧ�֣�������ֱ��롣ʱ��Ϊ���ս�֣������ǡ��š���͸�ֿɶ����ս����"+shiheju[1],true);
		if(shitpgong==xingpenggong && shitpgong==dumengong) {
			t = "ʱ���ٶ��ţ���������װ������ΪԪ˧�������������³��٣�";
			if(shitpgong==zhifugong) t+="����ֵ����ְλ�ɵ���߾��³��٣�";
			w(t,true);
		}else if(shitpgong==xingpenggong) {
			t = "����������ǻۣ�Ҳ�е�����Ϊ��һ�棬�õ�Ϊ�Ѿ����죻";
			if(rgjxjx[0].equals("-1")) t+="����"+zfname+"��������ֲ���̫�ã�";
			w(t,true);
		}
		
		w("4. �չ���ֶϣ�");
		w(isMenfan,"���ŷ��ʣ����Ŵ������£����·��ʣ���һ��������˳��",true);
		w(isMenfu,"���ŷ��ʣ����Ŵ������£�������һ�����������д�ķ�չ��",true);
		w(isXingfan,"���Ƿ��ʣ����Ǵ�����ʱ��������һ��������˳��",true);
		w(isXingfu,"���Ƿ��ʣ����Ǵ�����ʱ��������һ�����������д�ķ�չ��",true);
		
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==1,"�������ʱ��һ��ƽ�ȡ�˳����",true);
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==2,"����岻��ʱ�����½��ף�",true);
		
		w(rgtpdpjy[0]==YiJing.REN || rgtpdpjy[1]==YiJing.REN,"�ո�+�ɣ���Ϊ���Σ������������������������׷������ܴ��֣����߷������Σ�һ���������죬������ü����֮�գ�",true);
		w(rgtpdpjy[0]==YiJing.XIN || rgtpdpjy[1]==YiJing.XIN,"�ո�+������Ϊ�����������������������������׷������ܴ��֣����߷������Σ�һ���������죬������ü����֮�գ�",true);
		w(rgdptpjy[0]==YiJing.REN || rgdptpjy[1]==YiJing.REN,"��+�ոɣ���Ϊ���Σ������������������������׷������ܴ��֣����߷������Σ�һ���������죬������ü����֮�գ�",true);
		w(rgdptpjy[0]==YiJing.XIN || rgdptpjy[1]==YiJing.XIN,"��+�ոɣ���Ϊ�����������������������������׷������ܴ��֣����߷������Σ�һ���������죬������ü����֮�գ�",true);
		if((ritpgong==6 || ritpgong==7 || ritpgong==8 || ritpgong==9) && (rgtpdpjy[0]==YiJing.GUI || rgtpdpjy[1]==YiJing.GUI))
			w("�ո�+���Ϊ����������"+ritpgong+"��Ϊ���ߣ��Ͼ��߻�Ϊ���ǣ���Ϊ������ḻ�����",true);
		else if(rgtpdpjy[0]==YiJing.GUI || rgtpdpjy[1]==YiJing.GUI)
			w("�ո�+���Ϊ����������"+ritpgong+"��Ϊ���ͣ���ʧ�������������żŹ�ƶ���µ�ƶ��",true);
		if((ridpgong==6 || ridpgong==7 || ridpgong==8 || ridpgong==9) && (rgdptpjy[0]==YiJing.GUI || rgdptpjy[1]==YiJing.GUI))
			w("��+�ոɣ���Ϊ����������"+ridpgong+"��Ϊ���ߣ��Ͼ��߻�Ϊ���ǣ���Ϊ������ḻ�����",true);
		else if(rgdptpjy[0]==YiJing.GUI || rgdptpjy[1]==YiJing.GUI)
			w("��+�ոɣ���Ϊ����������"+ridpgong+"��Ϊ���ͣ���ʧ�������������żŹ�ƶ���µ�ƶ��",true);
		
		if(ritpgong==kaimengong) {
			t = "�ո��뿪��ͬ����һ�����ְ����";
			if(ritpgong==shenhugong) t+="ͬʱ�׻��ӿ��� Ϊ��·����Ϊ���ʹ󽫣�";
			w(t,true);			
		}
		w(p.isYima(shitpgong),"ʱ�������ǣ�һ���������ߣ�",true);
		w(p.isYima(zhishigong),"ֵʹ�����ǣ�һ���������ߣ�",true);
		w(p.isYima(ritpgong),"�ո��乬�����ǣ�һ���������ߣ�",true);
		w(niantpgong==xingqingong,"���������ݣ���Ϊ�ٹ�֮�ף�",true); 
		String[] niantgws = p.gettgWS(SiZhu.ng, SiZhu.nz);
		w(niantgws[0].equals("1"),"����Ϊ�����乬���࣬������������������ǿ��",true);
		String[] niansanji = p.getSanji(niantpgong);
		String[] nianbmjx = p.getmenJX(niantpgong);
		if(ritpgong==xingruigong && rgsanji[0].equals("1") && rgbmjx[0].equals("1")) 
			w("�ո��乬�����ǣ������������ţ�Ϊ�ܲٶ�׿֮����",true);		
		else if(niantpgong==xingruigong && niansanji[0].equals("1") && nianbmjx[0].equals("1")) 
			w("����乬�����ǣ������������ţ�Ϊ�ܲٶ�׿֮����",true);
		w(p.isGongSheng(niantpgong, ritpgong),"������ոɣ�һ���ܵõ��쵼���Ρ����ã�",true);
		w(p.isChongke(zhifugong, ritpgong),"ֵ�����ոɣ�һ���ò����쵼�����κ����ã�",true);
		
		w(boy && (rgtpdpjy[0]==YiJing.YI || rgtpdpjy[1]==YiJing.YI),"�ո��������棬Ϊһ����Ů�˰�����",true);
		
		t=null;
		if(ritpgong==shangmengong && ritpgong==xingchonggong) {
			t = "�ո�����"+ritpgong+"�������ź�����ǣ��洢��������Ϣ��";
		} else if(ridpgong==shangmengong && ridpgong==xingchonggong) {
			t = "�ոɵ�������"+ridpgong+"�������ź�����ǣ��洢��������Ϣ��";
		}
		if(t!=null) {
			//����Ϊ���̸���֮�ɣ�����Ϊ���̸���֮��
			int yqgan = p.is5YangGan(SiZhu.rg) ? p.getDpjy(gengtpgong)[0] : p.getTpjy(gengdpgong)[0];
			String yqnian = p.getYearString(1, year, 1, 50, yqgan);  //�����Ժ���Ҫ������
			if(p.is5YangGan(SiZhu.rg))
				t += "����Ѱ"+gengtpgong+"������֮�ɣ�"+yqnian+"��������";
			else
				t += "����Ѱ"+gengdpgong+"������֮�ɣ�"+yqnian+"��������";
			w(t,true);
		}
		getShare3(true,ritpgong,true,false);  //����ոɹ���һЩ������Ϣ
		w("�����ո��乬ҲҪ�ο���",true);
		
		w("5. �ո��乬���׸�ֶϣ�");
		boolean hasgeju = false;
		int ge1 = QiMen.gan_gan[rgtptpjy[0]][rgtpdpjy[0]];
		int ge2= QiMen.gan_gan[rgtptpjy[1]][rgtpdpjy[0]] ;
		int ge3= QiMen.gan_gan[rgtptpjy[0]][rgtpdpjy[1]] ;
		int ge4= QiMen.gan_gan[rgtptpjy[1]][rgtpdpjy[1]] ;
		int[] gejunum = {81,63,73,82,110,56,78,132,101,65,103,85,105,100,55,75,102,108,107};
		String[] gejudesc = {"�������ף���ӱ�����ͷ��ռ","�����Ѩ�������죬�������","����ת�⣺�����죬�س�����",
				"����ҫ������Ӷ���������ҫ","�׻����������ң�����֮��","�������ߣ��Ҽ�����ų��֮�ˣ��������޳ɰܣ������ձ�����",
				"��ȸͶ�������ӹ��������","Ο��ز�ã���Ӷ�������С�ˣ�ʧʱ��Ŀä�����������߻������",
				"̫����ӫ�����ӱ���������ƶ��","ӫ��̫�ף����Ӹ���������ҵ����","�����񣺸����죬��ɶ�ܣ��˵ز�������",
				"�ɹ�����Ӹ�����ɶ�ܣ��˵ز�������","̫��ͬ�������Ӹ�������ս���ֵ��׹�","̫�׷��ǣ������ң����ҳ���",
				"���汻�̣��ҼӸ������ҳ���","�Ƿ�̫�ף����Ӹ���Ω�����������л������ȳ���","���ݲؽ������Ӷ���Ω�����������л������ȳ���",
				"��񣺸��ӹƼ���ĺ�","С�񣺸����ɣ��ֳ��Ƶ�����ʱ��ƶ"};
		for(int i=0; i<gejunum.length; i++) {
			if(ge1==gejunum[i] || ge2==gejunum[i] || ge3==gejunum[i] || ge4==gejunum[i]) {
				hasgeju = true;
				w(gejudesc[i],true);
			}				
		}
		
		w(p.isFeigan(),QiMen.gGejuDesc[13][0]+"�������ոɣ��������������˲�������",true);
		w(p.isFugan(),QiMen.gGejuDesc[24][0]+"�����������������ոɣ����˲�������",true);
		w(!hasgeju,QiMen.gGejuDesc[ge1][0]+"��"+QiMen.gGejuDesc[ge1][1],true);
	}
	
	/**
	 * ����
	 */
	public void getLife2() {
		String[] menHeju = p.ismenHeju(shengmengong);  //�ж������Ƿ�Ͼ�
		String[] sanji = p.getSanji(shengmengong);  //�ж����Ź��Ƿ�������		
		String[] ws = p.getmenWS(shengmengong);
		String[] xingjx = p.getxingJX(shengmengong);
		
		w("1. ���Ų�Ϊ�ϣ�");
		w(menHeju[0].equals("1"),"���źϾ֣������ҵΪ�ƣ��Ͼ����ҵ���㣻"+menHeju[1],true);
		w(sanji[0].equals("1"),sanji[1]+"���Ų�ҵ��Ҫ�����棻",true);
		
		boolean kong = p.isKong(shengmengong);
		boolean ismenpo = QiMen.men_gong[QiMen.MENSHENG][shengmengong].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[QiMen.MENSHENG][shengmengong].equals(QiMen.zphym1);
		w(ismenpo,"�����乬���ȣ����ſ˹������ͣ����ԴﵽĿ�ģ�",true);
		w(ismenzhi,"�����乬���ƣ������ܿ˼����ͣ����ԴﵽĿ�ģ�",true);
		w(kong,"�����乬���������߼��������߸��࣬�������ࣻ",true);
		
		w("2. �������չ��ϣ�");
		//�õ������乬�ĵ����������������ǣ��ж��Ƿ��и�
		int[] tpjy = p.getTpjy(shengmengong);
		int[] dpjy = p.getDpjy(shengmengong);
		//if(tpjy[0]==YiJing.GENG ||tpjy[1]==YiJing.GENG ||dpjy[0]==YiJing.GENG ||dpjy[1]==YiJing.GENG) {
		w(p.isChongke(shengmengong, ritpgong),"�����ִ������֮�ء��漮����ҵ���乬(�ٸ�����)����ո��乬���������磻",true);
		w(p.isChongke(ritpgong, shengmengong),"�����ִ������֮�ء��漮����ҵ���ոɳ�����ţ����ǰܼ��ӣ�",true);
		
		w(getGongRel(shengmengong, "����", ritpgong, "�ո�", 
				new String[]{"Ϊ�������ң��Դ����ó�"+(sanji[0].equals("1")?sanji[1]+"�����׵ò�":""),
				"�в�����","�������","�������","��","��"}),true);
		
		w("3. Ǯ�ƶ��ٶϣ�");
		if(ws[0].equals("1") && xingjx[0].equals("1")) {
			t = "�����乬�����ټ��ǲƴ�";
			if(gengtpgong==shengmengong && shengmengong==shenyingong) t+="���ٸ�����̫��Ϊ�Ӷ�֮�ƣ�";
			else if(gengtpgong==shengmengong) t+="���ٸ���Ϊ����֮�ƣ�";
			else if(shengmengong==shenyingong) t+="���ϳ�̫����Ϊ�Ӷ�֮�ƣ�";
			w(t,true);
		}
		
		String gongws = QiMen.xing_gong[caixing][shengmengong]; 
		String yuews = QiMen.xing_yue[caixing][SiZhu.yz];		
		if(gongws.equals(QiMen.wxxqs1) && yuews.equals(QiMen.wxxqs1)){
			t = "����["+QiMen.jx1[caixing]+"]��"+shengmengong+"���������Ϊ���أ�";
			if(kong) t+= "���乬�������Ƹ��׾���ɢ��";
			else t+="���������̼����";
			w(t,true);
		}else if(gongws.equals(QiMen.wxxqs1) && yuews.equals(QiMen.wxxqs2)){
			t = "����["+QiMen.jx1[caixing]+"]��"+shengmengong+"��Ϊ���أ�����Ϊ��أ�";
			if(kong) t+= "���乬�������Ƹ��׾���ɢ��";
			else t+="����ǧ���̣�";
			w(t,true);
		}else 
		if( gongws.equals(QiMen.wxxqs2) && (yuews.equals(QiMen.wxxqs1) || yuews.equals(QiMen.wxxqs2))){
			t = "����["+QiMen.jx1[caixing]+"]��"+shengmengong+"��Ϊ��أ�"+"���������ࣻ";
			if(kong) t+= "���乬�������Ƹ��׾���ɢ��";
			else t+="���ǰ����̣�";
			w(t,true);
		}else if((gongws.equals(QiMen.wxxqs5) || gongws.equals(QiMen.wxxqs6) )&& (yuews.equals(QiMen.wxxqs5) || yuews.equals(QiMen.wxxqs6))){
			w("����["+QiMen.jx1[caixing]+"]��"+shengmengong+"��"+gongws+"��������"+yuews+"���޲ƿ������������ʵ���",true);
		}else if((gongws.equals(QiMen.wxxqs3) || gongws.equals(QiMen.wxxqs4) )&& (yuews.equals(QiMen.wxxqs3) || yuews.equals(QiMen.wxxqs4) || yuews.equals(QiMen.wxxqs5) || yuews.equals(QiMen.wxxqs6))){
			w("����["+QiMen.jx1[caixing]+"]��"+shengmengong+"��"+gongws+"��������"+yuews+"�����˼�������ѵã�",true);
		}else{
			w("����["+QiMen.jx1[caixing]+"]��"+shengmengong+"��"+gongws+"��������"+yuews+"������һ�㣬�����¸���",true);
		}
			
		w("4. Ǯ�Ʒ�λ�ϣ�");
		boolean rinei = p.isNenpan(ritpgong);
		boolean shnei = p.isNenpan(shengmengong);
		boolean isyang = daoqm.getJu()>0;
		w(rinei && !shnei,(daoqm.getJu()>0?"����":"����")+"���ո�������"+ritpgong+"��������������"+shengmengong+"��������Ǩ�Ӳ��ܸ�������",true);
		w(!rinei && shnei,(isyang?"����":"����")+"���ո�������"+	ritpgong+"��������������"+shengmengong+"������ҵ��ʹ�ḻ�Լ�Ҳ�����ã�",true);
		w(rinei && shnei,(isyang?"����":"����")+"���ո�������"+	ritpgong+"��������������"+shengmengong+"���������������̣�һ��������",true);
		w(!rinei && !shnei,(isyang?"����":"����")+"���ո�������"+	ritpgong+"��������������"+shengmengong+"���������������̣����뵽���ȥ���ܴ����þ��棻",true);
		
		t = null;
		if(shnei) {
			t = "����������"+shengmengong+"�������ڼ���"+QiMen.JIUGONGFXIANG[shengmengong]+"��";
		}else{
			t = "����������"+shengmengong+"�����������"+QiMen.JIUGONGFXIANG[shengmengong]+"��";
		}
		if(p.isKong(shengmengong) && (p.isYima(shengmengong) || p.getGongWS(shengmengong)[0].equals("1"))) {
			t+="�������乬������"+(p.isYima(shengmengong)?"�������ǲ�Ϊ�գ�":"������Ϊ�գ�");
		}else if(p.isKong(shengmengong)) {
			int chgong = p.getChongGong(shengmengong);
			boolean isnei = p.isNenpan(chgong);
			t+="�������乬���������Գ�֮"+chgong+"��������"+(isnei?"����":"����")+"�������"+(isnei?"����":"���")+QiMen.JIUGONGFXIANG[shengmengong]+"��";
		}
		w(t,true);		
	}
	/**
	 * ����
	 */
	public void getLife3() {
		String xing[] = p.isxingHeju(ritpgong);
		String men[] = p.ismenHeju(ritpgong);
		String[] jxdesc1 = {"","�ո��������ǺϾ֣��߹ش󽫣�",
				"�ո��������ǺϾ֣���λ��Ȩ�أ���Ϊ�ܲٶ�׿֮����",				
				"�ո�������ǺϾ֣�����߽��������Ȩ����֮�ˣ�",
				"�ո����츨�ǺϾ֣��Ļ��������гɾ��������Ĺ�Ա��",
				 "�ո����������ݺϾ֣��ٹ�Ԫ�ף�",				 
				 "�ո������ĺϾ֣�Ϊҽ���������Ź�Ա����Ż����磻",				 
				 "�ո��������Ͼ֣����취��Ա�����⽻����˵�ҡ���������ʦ���質�ң�",
				 "�ո������κϾ֣�ũ��ˮ����Ա���������ׯ��",
				 "�ո�����Ӣ�ǺϾ֣��Ľ̿Ƽ���ҵ����֮�ģ�����������",				 
				 };		
		String[] jxdesc0 = {"","�ո���������ʧ���������������ǹ���Ա��Ƴ�����ӵ��ų��೤��",
				"�ո���������ʧ����������Ϊ���ռ��˸�����ׯ��",
				"�ո��������ʧ����������Ϊ�����е�Сͷͷ����ࣻ",
				"�ո����츨��ʧ����������Ϊ��ƶ��֪ʶ���ӣ�",
				"",
				"�ո���������ʧ�����������ɴ���ҽ������������Ͷ��ߣ�",
				"�ո���������ʧ����������ΪĻ�ű��ͻ����������",
				"�ո���������ʧ�������������������Ը����㣻",	 
				"�ո�����Ӣ��ʧ�������������Ľ���ҵ��һ���ɼ���"};
		String[] jxdesc2 = {"","�ո���������ʧ��������Ϊʿ����С͵С���",
				"�ո���������ʧ���������¿������������գ�",
				"�ո��������ʧ��������Ϊ��ͨʿ����˾������",
				"�ո����츨��ʧ�����������е�ʿ�ȹ���֮�˻�����Ϊ����",
				"",
				"�ո���������ʧ��������Ϊ�����������Ͷ��ߣ�",
				"�ո���������ʧ��������Ϊ˵�����ջ������񺺡�",
				"�ո���������ʧ��������Ϊ����������",	 
				"�ո�����Ӣ��ʧ��������Ϊұ��������йصĿ������ӹ֮�ˣ�"};
	 //"��", "��", "��", "��","��", "��", "��", "��", "��"
		String[] bmdesc1 = {"","�ո������źϾ֣�Ϊ�칫�����Σ������鳤��������������ֳ���Ϊ�׳�������Ա��",
				"�ո������źϾ֣�Ϊ˾�������Ժ����Ժ�������͸ľֹ�Ա��",
				"�ո������źϾ֣���٣�",
				"�ո��ٶ��źϾ֣�Ϊ���ܻ��ء��������Ա��",
				"",
				"�ո��ٿ��źϾ֣��Ĺ٣�",
				"�ո��پ��źϾ֣������Ʊ棬Ϊ��ʦ����ʦ���⽻�ҡ��質�ҡ�������й�ְҵ��",
				"�ո������źϾ֣���ʯ��һ�����㣻",
				"�ո��پ��źϾ֣��Ļ���ҵ�гɣ������ҡ������ҡ���ѧ�ҡ��鷨�ң�"		
		}; 
		
		String[] bmdesc0 = {"","�ո�������ʧ���������������ǹ���Ա��Ƴ�����ӵ��ų��೤��",
				"�ո�������ʧ����������Ϊ���ռ��˸�����ׯ��",
				"�ո�������ʧ����������Ϊ�����е�Сͷͷ����ࣻ",
				"�ո��ٶ���ʧ����������Ϊ��ƶ��֪ʶ���ӣ�",
				"", 
				"�ո��ٿ���ʧ�����������ɴ���ҽ������������Ͷ��ߣ�",
				"�ո��پ���ʧ����������ΪĻ�ű��ͻ����������", 
			 "�ո�������ʧ�������������������Ը����㣻",				 
			 "�ո��پ���ʧ�������������Ľ���ҵ��һ���ɼ���"};
				 
		String[] bmdesc2 = {"","�ո�������ʧ��������Ϊʿ����С͵С���",
				"�ո�������ʧ���������¿������������գ�",
				"�ո�������ʧ��������Ϊ��ͨʿ����˾������",
				"�ո��ٶ���ʧ�����������е�ʿ�ȹ���֮�˻�����Ϊ����",
				"", 
				"�ո��ٿ���ʧ��������Ϊ�����������Ͷ��ߣ�",
				"�ո��پ���ʧ��������Ϊ˵�����ջ������񺺡�", 
			 "�ո�������ʧ��������Ϊ����������",				 
			 "�ո��پ���ʧ��������Ϊұ��������йصĿ������ӹ֮�ˣ�"};
				 
		//w(rgheju[0].equals("1"),"���ž��Ǿ���ְҵ����˥������λ���չ��Ͼ֣�Ϊ����ϵ����ʧ֮һ�����е������ȫʧ��Ϊƽ��");		
		w("1. ��������ְҵ��");
		w(xing[0].equals("1"), jxdesc1[daoqm.gInt[2][1][ritpgong]]+xing[1],true);
		w(xing[0].equals("0"),jxdesc0[daoqm.gInt[2][1][ritpgong]]+xing[1],true);
		w(xing[0].equals("-1"),jxdesc2[daoqm.gInt[2][1][ritpgong]]+xing[1],true);
		
		w("2. ��������ְҵ��");
		w(men[0].equals("1"), bmdesc1[daoqm.gInt[3][1][ritpgong]]+men[1],true);
		w(men[0].equals("0"), bmdesc0[daoqm.gInt[3][1][ritpgong]]+men[1],true);
		w(men[0].equals("-1"),bmdesc2[daoqm.gInt[3][1][ritpgong]]+men[1],true);
		
		w("3. ����������ҵ���ˣ�");
		boolean iskong = p.isKong(kaimengong);
		int[] tpjy = p.getTpjy(kaimengong);
		int[] dpjy = p.getDpjy(kaimengong);
		w(getGongRel(kaimengong, "����", ritpgong, "�ո�", 
				new String[]{"һ����ҵ������˺�ͨ","����","����ٵ���Ը",
				"��ҵ����ӹٽ���"+(iskong?"�����������������ʵ֮��ΪӦ":""),	"��","��"}),true);
		if(tpjy[0]==SiZhu.rg || tpjy[0]==SiZhu.ng ||
				tpjy[1]==SiZhu.rg || tpjy[1]==SiZhu.ng ||
				dpjy[0]==SiZhu.rg || dpjy[0]==SiZhu.ng ||
				dpjy[1]==SiZhu.rg || dpjy[1]==SiZhu.ng ) {
			t = "�����乬����ɻ��ոɣ�������ְ����һ��";
			if(kaimengong==shenhugong) {
				t+="���ٰ׻���Ϊ��٣���Ϊ����ָ�ӹ٣�";
				if(kaimengong==xingruigong) t+="���������ǣ�Ϊ����ԺУ���٣�";
			}
			else if(kaimengong==shenwugong) t+="�������䣬Ϊ̰�٣�";
			else if(kaimengong==shenshegong) t+="�������ߣ�Ϊ�鳼���ӣ�";
			w(t,true);
		}
		
		String[] sanji = p.getSanji(kaimengong);
		if(sanji[0].equals("1")) w("����"+sanji[1]+"ӦΪ�Ĺ٣�",true);
		
		boolean ismenpo = QiMen.men_gong[QiMen.MENKAI][kaimengong].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[QiMen.MENKAI][kaimengong].equals(QiMen.zphym1);
		w(ismenpo,"�����乬���ȣ����ſ˹������ͣ����ԴﵽĿ�ģ�",true);
		w(ismenzhi,"�����乬���ƣ������ܿ˼����ͣ����ԴﵽĿ�ģ�",true);
		
		w(iskong,"�����乬���������߼��������߸��࣬�������ࣻ");
		int[] ggong = p.getTpjy(kaimengong);
		w(kaimengong==shenhugong && (YiJing.GENG==ggong[0] || YiJing.GENG==ggong[1]),"�׻��Ӹ���Ȩ��֮��",true);
		
		boolean kainei = p.isNenpan(kaimengong);
		if(kainei) {
			w("����������"+kaimengong+"������ҵ�ڱ��أ���"+QiMen.JIUGONGFXIANG[kaimengong]+"��",true);
		}else{
			w("����������"+kaimengong+"������ҵΪ��أ���"+QiMen.JIUGONGFXIANG[kaimengong]+"��",true);
		}
	}
	
	/**
	 * �ġ�
	 */
	public void getLife4() {
		int marry = 0;
		
		w("1. �����������ϣ�");
		w(getGongRel(xiumengong, "����", ritpgong, "�ո�", new String[]{"��","����","��","����",	"��","��"}),true);
		w(getGongRel(shenhegong, "����", ritpgong, "�ո�", new String[]{"��","����","��","����",	"��","��"}),true);
		if(p.isChongke(shenhegong, ritpgong)) marry++;
		if(p.isChongke(xiumengong, ritpgong)) marry++;
		if(p.isKong(xiumengong)) { w("�����������������˳�ı�־��",true); marry++;}
		if(xiumengong==gengtpgong) {  w("���ŷ����������˳�ı�־��",true);marry++;}
		if(p.isKong(shenhegong))  { w("�����������������˳�ı�־��",true);marry++;}
		if(shenhegong==gengtpgong)  { w("���Ϸ����������˳�ı�־��",true);marry++;}
		
		w("2. ����������ո�������֮�ɣ�");
		String rgname = YiJing.TIANGANNAME[SiZhu.rg];
		int hegan = p.getHegan(SiZhu.rg);  //�����ո�����֮��
		if(hegan==YiJing.JIA) hegan = YiJing.WUG;  //���Ϊ�ף���ֱ�ӷ���ֵ�����ڹ�����
		int hegangong = p.getTianGong(hegan,0);
		String heganname = YiJing.TIANGANNAME[hegan];
		w(getGongRel(yitpgong, "[��]", gengtpgong, "[��]", new String[]{"��������","���޸��鲻�ã�������˳��Ҳ������������","��������","���޸��鲻�ã�������˳��Ҳ������������",	"��","��"}),true);
		if(SiZhu.rg!=YiJing.YI && SiZhu.rg!=YiJing.GENG) //�����������������֮��
			w(getGongRel(ritpgong, "�ո�["+rgname+"]", hegangong, "���֮��["+heganname+"]", new String[]{"��","�Լ��ڼ���һ����","��","��ż�ڼ���һ����",	"��","��"}),true);
		if(p.isChongke(ritpgong, hegangong) || p.isChongke(hegangong,ritpgong)) marry++;
		if(p.isKong(yitpgong))  { w("[��]�������������ֹһ�εı�־��",true);marry++;}
		if(yitpgong==simengong) {  w("[��]�����ţ�������������֮�£�",true);marry++;}
		if(p.isKong(gengtpgong))  { w("[��]�������������ֹһ�εı�־��",true);marry++;}
		if(gengtpgong==simengong) {  w("[��]�����ţ�������������֮�£�",true);marry++;}
		if(p.isChongke(yitpgong, gengtpgong) || p.isChongke(gengtpgong, yitpgong)) {marry++; };
			
		w("3. ��"+(boy?"����":"�ɷ�")+"��");
		int qzTgong = boy?p.getTianGong(YiJing.YI, 0):p.getTianGong(YiJing.GENG, 0); //�õ� ����|�ɷ�����̹�
		int qzDgong = boy?p.getDiGong(YiJing.YI, 0):p.getDiGong(YiJing.GENG, 0);   //�õ�����|�ɷ����ڵĵ��̹�
		int qzDjy[] = p.getDpjy(qzTgong);
		int qzTjy[] = p.getTpjy(qzDgong);		
		int qieTgong = boy?p.getTianGong(YiJing.DING, 0):p.getTianGong(YiJing.BING, 0); //�õ� С�������׵����̹�
		int qieDgong = boy?p.getDiGong(YiJing.DING, 0):p.getDiGong(YiJing.BING, 0);   //�õ�С�����������ڵĵ��̹�		
		if(qzTjy[0]==YiJing.GENG || qzTjy[1]==YiJing.GENG) {
			w("����������"+(boy?"��":"��")+"����Ϊ�̸񣬶�"+(boy?"��":"��")+"��������������������",true);	
			marry++;
		}
		else if(qzDjy[0]==YiJing.GENG || qzDjy[1]==YiJing.GENG) {
			w("����������"+(boy?"��":"��")+"����Ϊ�̸񣬶�"+(boy?"��":"��")+"��������������������",true);
			marry++;
		}
		
		int zhishiGong = daoqm.getZhishiGong(SiZhu.sg,SiZhu.sz);
  	if(boy) {
			for(int i=1; i<=9; i++) {
	  		if(i==5) continue;
	  		if((i==zhishiGong && daoqm.gInt[4][5][i]==YiJing.DING) ||
	  				(i==2 && i==zhishiGong && daoqm.gInt[4][5][5]==YiJing.DING)){
	  			marry++;
	  			w("��Ů���ţ�������ֱʹ���ٵ��̶��棬���������У�Ҳ���޺��ӳ�ǽ��",true);
	  			break;
	  		}
	  	}
  	
	  	w(QiMen.gan_gong_mu[YiJing.YI][qzTgong]!=null, "����������Ĺ���޲����ӣ�",true);
	  	w(QiMen.gan_gong_mu[YiJing.YI][qzDgong]!=null,"����������Ĺ���޲����ӣ�",true);
	  	
	  	//w(p.isSheng(ritpgong,qzTgong),"���޶�ΪС����˭��˭�迴�乬���ˣ������޹������޺�����",true);
	  	w(p.isSheng(ritpgong,qieTgong),"���޶�ΪС����˭��˭�迴�乬���ˣ�����С��������ϲ����ߵ�Ұ����",true);
	  	//w(p.isChongke(ritpgong,qzTgong) || p.isChongke(qzTgong,ritpgong),"���޹����ˣ������޸��鲻����",true);
	  	//w(p.isSheng(ritpgong,qzTgong) || p.isSheng(qzTgong,ritpgong),"���޹������������޸�������ι̣�����������",true);
	  	
	  	w(qzDjy[0]==YiJing.DING || qzDjy[1]==YiJing.DING,"����ͬ��������ȢС���š��������ϣ��������£���檺�г��",true);
	  	w(qzTjy[0]==YiJing.DING || qzTjy[1]==YiJing.DING,"����ͬ��������ȢС���š��������ϣ��������£���ҷ��ģ�",true);
  	}
		
  	w("4. ��Ӧ�ڣ�");
		boolean yinei = p.isNenpan(yitpgong);
		boolean gengnei = p.isNenpan(gengtpgong);
		if(yinei && gengnei) {
			w("[��]��"+yitpgong+"����[��]��"+gengtpgong+"��ͬ�����̣����֮��",true);
		}else if(!yinei && !gengnei) {
			w("[��]��"+yitpgong+"����[��]��"+gengtpgong+"��ͬ�����̣����֮��",true);
		}else {
			w("[��]��"+yitpgong+"����[��]��"+gengtpgong+"��һ��һ�⣬���֮��",true);
		}
		if(marry>=2) {
			w("������˳��־���ԣ����������������乬��˥ȡ������������"+shenhegong+"��������"+p.getGongWS(shenhegong)[1]+"�أ�"+p.getGongshu(shenhegong),true);
			if(p.isKong(shenhegong)) w("������"+shenhegong+"����������ֹ������ݻ�䣺"+p.getYearString(5, year, 18, 40, shenhegong),true); //18��30�������10���ڷ�
			else if(p.isKong(xiumengong)) w("������"+xiumengong+"����������ֹ������ݻ�䣺"+p.getYearString(5, year, 18, 40, xiumengong),true); //18��30�������10���ڷ�
			if(p.isKong(yitpgong)) w("[��]��"+yitpgong+"����������ֹ������ݻ�䣺"+p.getYearString(5, year, 18, 40, yitpgong),true); //18��30�������10���ڷ�
			else if(p.isKong(gengtpgong)) w("[��]��"+gengtpgong+"����������ֹ������ݻ�䣺"+p.getYearString(5, year, 18, 40, gengtpgong),true); //18��30�������10���ڷ�
			
			if(p.isChongke(yitpgong, gengtpgong) || p.isChongke(gengtpgong,yitpgong)) {
				w("��������乬���ˣ������������֮����ܿ�����֮���䣻",true);
			}
		}	
	}

	/**
	 * �塢
	 */
	public void getLife5() {
		t="";
		int times = 0;
		if(ritpgong==dingdpgong) {
			t += "���ٶ��棬�ĿƳɼ��ã�";times++;
		}else if(p.isSheng(dingdpgong, ritpgong)) {
			t += "��"+dingtpgong+"�������������ĿƳɼ��ã�";times++;
		}
		if(ritpgong==jing3mengong) {
			t += "�پ��ţ���Ƴɼ��ã�";times++;
		}else if(p.isSheng(jing3mengong, ritpgong)) {
			t += "��"+jing3mengong+"��������������Ƴɼ��ã�";times++;
		}
		if(ritpgong==xingfugong) {
			t += "���츨�ǣ������Ļ���";times++;
		}else if(p.isSheng(xingfugong, ritpgong)) {
			t += "��"+jing3mengong+"���츨�������������Ļ���";times++;
		}
		if(ritpgong==xingpenggong) {
			t += "�������ǣ����̸ߣ�";times++;
		}else if(p.isSheng(xingpenggong, ritpgong)) {
			t += "��"+xingpenggong+"�����������������̸ߣ�";times++;
		}
		
		t = "�ո���"+ritpgong+"����"+t;
		if(times>2) t+="�о���������ѧ����";
		else if(times>1) t+="���ƻ�����ѧ����";
		else if(times==1) t+="ѧ�����Ǻܸߣ���ר����ר���ְһ��ѧ����";
		else if(times==0) t+="����ɼ����ã���С�Ļ���";
		w(t);
	}
	
	/**
	 * ����
	 */
	public void getLife6() {
		int ngws = QiMen.gan_gong_wx3[SiZhu.ng][niantpgong];
		int rgws = QiMen.gan_gong_wx3[SiZhu.rg][ritpgong];
		boolean ruinei = p.isNenpan(xingruigong);
		
		w("1. ������");
		if((ngws==1 || rgws==1) && !p.isChongke(xingruigong, ritpgong) && !p.isChongke(simengong, ritpgong) &&
				!p.isChongke(jing1mengong, ritpgong) && !p.isChongke(shangmengong, ritpgong)) {
			w("��ɻ��ո��乬���࣬���ǲ��ǡ����š����š����ž������ոɣ����岻��һ���޴��֣�",true);
		}
		
		//1. �ȶ�������
		int howmanyyears = 60;  //ע�������
		t = "��������"+xingruigong+"��";
		t += ruinei?"��������":"��������";
		if(p.isGongke(xingruigong, ritpgong)) t+="���ֳ���ո�";
		t += "��Ҫ��ֹ��"+(ruinei?QiMen.JGBUWEINEI[xingruigong]:QiMen.JGBUWEIWAI[xingruigong])+"��������";
		w(t,true);
		if(xingruigong==simengong || xingruigong==shenhugong) w("�������������š��׻�ͬ����һ���ò��ͻ�Ƚ����أ�",true);		
		if(p.isKong(xingruigong))  //���Ѯ�գ����ʵ/��ʵ֮��ҲҪ��
			w("�������乬Ѯ�գ���������ʵ���ʵ֮����Ҫע���ֹ������"+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, xingruigong),true);
		else
			w("�������ע�⼲����"+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, xingruigong)),true);  //������ʵ֮�������

		w("2. �����˻���");
		if(p.isGongke(shangmengong, ritpgong)) {
			w("������"+shangmengong+"������ոɹ������ף�һ��Ҫ�����֣�",true);
			if(p.isKong(xingruigong))  //���Ѯ�գ����ʵ/��ʵ֮��ҲҪ��
				w("������������������ʵ���ʵ֮����Ҫע�����֣�"+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, shangmengong),true);
			else
				w("�������ע�����֣�"+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, shangmengong)),true);  //������ʵ֮�������
		}
		if(p.isGongke(simengong, ritpgong)) {
			w("������"+simengong+"������ոɹ������ף�һ��Ҫ�������ˣ�",true);
			if(p.isKong(simengong))  //���Ѯ�գ����ʵ/��ʵ֮��ҲҪ��
				w("������������������ʵ���ʵ֮����Ҫע���������֣�"+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, simengong),true);
			else
				w("�������ע���������֣�"+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, simengong)),true);  //������ʵ֮�������
		}
		if(p.isGongke(jing1mengong, ritpgong)) {
			w("������"+jing1mengong+"������ոɹ������ף�һ��Ҫ������ٷǣ�",true);
			if(p.isKong(simengong))  //���Ѯ�գ����ʵ/��ʵ֮��ҲҪ��
				w("������������������ʵ���ʵ֮����Ҫע�����ٷǣ�"+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, jing1mengong),true);
			else
				w("�������ע�����ٷǣ�"+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, jing1mengong)),true);  //������ʵ֮�������
		}
		w("3. ������");
		w("ֵʹ["+QiMen.bm1[zhishigong]+"]����"+zhishigong+"��������˥���ж����޵���Ҫ���ݣ�",true);
		int maxnum = p.getGongshu(zhishigong, 2); 
		w("��ֵʹ��"+p.getGongshu(zhishigong)+"ȡ�����Ⱥ������е��������"+maxnum+"��������Ӧ��"+maxnum+"0���ϣ�",true);
	}
	/**
	 * �ߡ�
	 */
	public void getLife7() {
		int[] ngTpjy = p.getTpjy(p.getDiGong(SiZhu.ng, SiZhu.nz));  //�õ�����������ڹ�����������
		int[] ngDpjy = p.getDpjy(p.getTianGong(SiZhu.ng, SiZhu.nz));//�õ�����������ڹ��ĵ�������
		int tpjy6[] = p.getTpjy(6); //�õ�Ǭ����������
		int dpjy6[] = p.getDpjy(6); //�õ�Ǭ����������
		int tpjy2[] = p.getTpjy(2); //�õ�������������
		int dpjy2[] = p.getDpjy(2); //�õ�������������
		int xdDjy[] = p.getDpjy(yuetpgong);
		int xdTjy[] = p.getTpjy(yuedpgong);
		int zyDjy[] = p.getDpjy(shitpgong);
		int zyTjy[] = p.getTpjy(shidpgong);
		
		w("1. �ϸ�ĸ��");
		if(tpjy6[0]==YiJing.GENG || tpjy6[1]==YiJing.GENG)	w("����������Ǭ������������",true);	
		else if(dpjy6[0]==YiJing.GENG || dpjy6[1]==YiJing.GENG) w("����������Ǭ������������",true);	
		if(tpjy2[0]==YiJing.GENG || tpjy2[1]==YiJing.GENG) w("����������������ĸ������",true);	
		else if(dpjy2[0]==YiJing.GENG || dpjy2[1]==YiJing.GENG)	w("����������������ĸ������",true);			
		if(ngTpjy[0]==YiJing.XIN || ngTpjy[1]==YiJing.XIN) w("���������ٸ�ĸ������ĸһ���������죻",true);	
		else if(ngDpjy[0]==YiJing.XIN || ngDpjy[1]==YiJing.XIN) w("���������ٸ�ĸ������ĸһ���������죻",true);
		if(ngTpjy[0]==YiJing.REN || ngTpjy[1]==YiJing.REN) w("���������ٸ�ĸ������ĸһ���������죻",true);	
		else if(ngDpjy[0]==YiJing.REN || ngDpjy[1]==YiJing.REN) w("���������ٸ�ĸ������ĸһ���������죻",true);
		
		w("2. ���ֵܽ��ã�");
		if(xdTjy[0]==YiJing.GENG || xdTjy[1]==YiJing.GENG) w("�����������ֵܹ����ֵܽ��ù�ϵ���ã�",true);	
		else if(xdDjy[0]==YiJing.GENG || xdDjy[1]==YiJing.GENG) w("�����������ֵܹ����ֵܽ��ù�ϵ���ã�",true);	
		if(xdTjy[0]==YiJing.XIN || xdTjy[1]==YiJing.XIN) w("�����������ֵܹ����ֵܽ���һ���������죻",true);	
		else if(xdDjy[0]==YiJing.XIN || xdDjy[1]==YiJing.XIN) w("�����������ֵܹ����ֵܽ���һ���������죻",true);
		if(xdTjy[0]==YiJing.REN || xdTjy[1]==YiJing.REN) w("�����������ֵܹ����ֵܽ���һ���������죻",true);	
		else if(xdDjy[0]==YiJing.REN || xdDjy[1]==YiJing.REN) w("�����������ֵܹ����ֵܽ���һ���������죻",true);
		String xdws = QiMen.gong_yue[yuetpgong][SiZhu.yz];
		if(xdws.equals(QiMen.wxxqs1) || xdws.equals(QiMen.wxxqs2))
			w("�¸�["+YiJing.TIANGANNAME[SiZhu.yg]+"]��"+yuetpgong+"�����࣬"+p.getGongshu(yuetpgong)+"ȡ���еĴ��������ֵܽ��ù���"+p.getGongshu(yuetpgong,1)+"��",true);
		else if(xdws.equals(QiMen.wxxqs5))
			w("�¸�["+YiJing.TIANGANNAME[SiZhu.yg]+"]��"+yuetpgong+"�������أ�"+p.getGongshu(yuetpgong)+"ȡ���е�С�������ֵܽ��ù���"+p.getGongshu(yuetpgong,-1)+"��",true);
		else 
			w("�¸�["+YiJing.TIANGANNAME[SiZhu.yg]+"]��"+yuetpgong+"��������"+p.getGongshu(yuetpgong)+"ȡ���е����������ֵܽ��ù���"+p.getGongshu(yuetpgong,-2)+"��",true);
		
		
		w("3. �����");
		if(zyTjy[0]==YiJing.GENG || zyTjy[1]==YiJing.GENG) w("�������������﹬������Ů�ٶ�������",true);	
		else if(zyDjy[0]==YiJing.GENG || zyDjy[1]==YiJing.GENG) w("�������������﹬������Ů�ٶ�������",true);	
		if(zyTjy[0]==YiJing.XIN || zyTjy[1]==YiJing.XIN) w("�������������﹬��������һ���������죻",true);	
		else if(zyDjy[0]==YiJing.XIN || zyDjy[1]==YiJing.XIN) w("�������������﹬��������һ���������죻",true);
		if(zyTjy[0]==YiJing.REN || zyTjy[1]==YiJing.REN) w("�������������﹬��������һ���������죻",true);	
		else if(zyDjy[0]==YiJing.REN || zyDjy[1]==YiJing.REN) w("�������������﹬��������һ���������죻",true);
		String childws = QiMen.gong_yue[shitpgong][SiZhu.yz];
		if(childws.equals(QiMen.wxxqs1) || childws.equals(QiMen.wxxqs2))
			w("ʱ��["+YiJing.TIANGANNAME[SiZhu.sg]+"]��"+shitpgong+"�����࣬"+p.getGongshu(shitpgong)+"ȡ���еĴ�������Ů����"+p.getGongshu(shitpgong,1)+"��",true);
		else if(childws.equals(QiMen.wxxqs5))
			w("ʱ��["+YiJing.TIANGANNAME[SiZhu.sg]+"]��"+shitpgong+"�������أ�"+p.getGongshu(shitpgong)+"ȡ���е�С������Ů����"+p.getGongshu(shitpgong,-1)+"��",true);
		else 
			w("ʱ��["+YiJing.TIANGANNAME[SiZhu.sg]+"]��"+shitpgong+"��������"+p.getGongshu(shitpgong)+"ȡ���е���������Ů����"+p.getGongshu(shitpgong,-2)+"��",true);
		t = "";
		if(p.isKong(shitpgong)) t += "��������";
		if(p.isYima(shitpgong))  t += "�����ǣ�";
		if(!t.equals("")) w("��ʱ��"+t+"��ʾ��Ů�е��ߣ��е�����",true);
		
		t="";
		if(shitpgong==simengong) t+="�����ţ�";
		if(p.isTGanMu(SiZhu.sg, SiZhu.sz)) t+="��Ĺ��";
		if(!t.equals("")) w("��ʱ��"+t+"��ʾ��Ů��������ز����Ϣ��",true);
		
	}
	
	public void getLife8() {
		int gong = getFirstGong();		
		for(int i=0; i<8; i++) {
			gong = (i==0) ? gong : getNextGong(gong);			
//			getShare3(true,gong,true,false);
//			getShare21(true,gong,true);
//			getShare22(true,gong,true,false);
			int[] jxindex = getDayun(false, gong, false,false);
			w((i+1)+". "+(i*15+1)+"��"+((i+1)*15)+"��----"+QiMen.dpjg[gong]+"����"+getJXindex(jxindex[0], jxindex[1])+"����");
			getDayun(true, gong, true,false);
		}
	}
	
	public void getLife9() {
		//ֻ����0��60�������
		for(int i=1; i<=60; i++) {			
			int[] gz = getNextSuiGanzi(i);
			String gan = YiJing.TIANGANNAME[gz[0]];
			String zi = YiJing.DIZINAME[gz[1]];
			int niangangong = p.getTianGong(gz[0], gz[1]);
			int nianzigong = p.getDiziGong(gz[1]);
			
			//������ָ��			
			int[] jxindex = getYearInfo(niangangong, nianzigong, i, gz[1], false, false,false);
			if(jxindex[0]>=INDEXLINE || jxindex[1]>=INDEXLINE) {
				w(i+"��----"+(year==0?"":(year+i))+"("+getNextSui(i)+"��"+gan+niangangong+"����"+zi+nianzigong+"�� "+getJXindex(jxindex[0],jxindex[1])+" )��");
				getYearInfo(niangangong, nianzigong, i, gz[1], true,true,true);
				w(p.NEWLINE);
			}			
		}
	}
	
	private int[] getDayun(boolean isw,int g,boolean iskong,boolean ispre) {
		int[] jxtg3 = getShare3(isw,g,iskong,ispre);
		int[] jxtg21 = getShare21(isw,g,iskong);
		int[] jxtg22 = getShare22(isw,g,iskong,ispre);
		
		return new int[]{jxtg3[0]+jxtg21[0]+jxtg22[0],jxtg3[1]+jxtg21[1]+jxtg22[1]};
	}
	
	private int[] getYearInfo(int niangangong, int nianzigong, int sui, int nianzi, boolean isw, boolean iskong,boolean ispre) {
		//int ji = 0, xi=0; //��һ��������ָ��
		
		int[] jxtg3 = getShare3(isw,niangangong,iskong,ispre);
		//getShare21(isw,niangangong,iskong,ispre);
		int[] jxtg22 = getShare22(isw,niangangong,iskong,ispre);
		int[] jxtg1 = {0,0};
		if(niangangong!=nianzigong) {
			jxtg1 = getShare1(isw,niangangong,iskong,ispre); //����ȣ��������ɹ�������Ϣ					
		}
		int[] jxtg = getNiangan(isw,niangangong, iskong,ispre);		
		
		int[] jxzi3 = getShare3(isw,nianzigong,iskong,ispre);
		//getShare21(isw,nianzigong,iskong,ispre);
		int[] jxzi22 = getShare22(isw,nianzigong,iskong,ispre);
		int[] jxzi1 = getShare1(isw,nianzigong,iskong,ispre); //�����Ƿ���ȣ��������֧��������Ϣ�����ר����Ϣ����֧ר����Ϣ
		int[] jxzi = getNianzi(isw,nianzigong, iskong, sui, nianzi,ispre);		
		 
		//System.out.println(sui+":"+(jxtg3[0]+jxtg22[0]+jxtg1[0]+jxtg[0]+jxzi3[0]+jxzi22[0]+jxzi1[0]+jxzi[0]));
		//System.out.println(sui+":"+(jxtg3[1]+jxtg22[1]+jxtg1[1]+jxtg[1]+jxzi3[1]+jxzi22[1]+jxzi1[1]+jxzi[1]));
		return new int[]{jxtg3[0]+jxtg22[0]+jxtg1[0]+jxtg[0]+jxzi3[0]+jxzi22[0]+jxzi1[0]+jxzi[0],
				jxtg3[1]+jxtg22[1]+jxtg1[1]+jxtg[1]+jxzi3[1]+jxzi22[1]+jxzi1[1]+jxzi[1]};
	}
	
	/**
	 * ��֧ר��,yearΪ������yearziΪ�������֧
	 * @param w�Ƿ������ispre�Ƿ���ʾǰ׺��b�Ƿ�ո�
	 * @param year �����������
	 * @param yearzi ������֧
	 * @return
	 */
	private int[] getNianzi(boolean w, int g,boolean b,int year, int yearzi,boolean ispre) {
		int ji = 0, xi=0; //��һ��������ָ��
		int down = 18, up = 45;
		int dz = QiMen.jgdz[g];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
    String rgname = YiJing.TIANGANNAME[SiZhu.rg];
    String nzname = YiJing.DIZINAME[yearzi];
    String pre = g+"����";
    String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		String pipre = ispre ? PIINDEX+" "+pre : "";
		
		if(year>up) {//���µ���5
			if(SiZhu.TGSWSJ[SiZhu.rg][yearzi]==5) {
				w(w,xipre+"�ո�["+rgname+"]����֧["+nzname+"]��{"+SiZhu.TGSWSJNAME[5]+"}�أ����µ�������˥�����ף�",b); 
				xi++; 
			}else{
				w(w,jipre+" "+pre+"�ո�["+rgname+"]����֧["+nzname+"]��{"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.rg][yearzi]]+"}�أ�����",b); 
				ji++; 
			}
		}	else if(year<down) {//����˥��6,7,
			boolean istrue = false;
			for(int i=6; i<=7; i++) {
				if(SiZhu.TGSWSJ[SiZhu.rg][yearzi]==i) {
					w(w,xipre+"�ո�["+rgname+"]����֧["+nzname+"]��{"+SiZhu.TGSWSJNAME[i]+"}�أ����µ�������˥�����ף�",b); 
					xi++; 
					istrue = true;
					break;
				}
			}
			if(!istrue) {
					w(w,jipre+"�ո�["+rgname+"]����֧["+nzname+"]��{"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.rg][yearzi]]+"}�أ�����",b); 
					ji++; 
			}
		} else if(year>=down && year<=up) {  //����������Ĺ��̥��8��9��10��11��12
			boolean istrue = false;
			for(int i=8; i<=12; i++){
				if(SiZhu.TGSWSJ[SiZhu.rg][yearzi]==i) {
					w(w,xipre+"�ո�["+rgname+"]����֧["+nzname+"]��{"+SiZhu.TGSWSJNAME[i]+"}�أ�������������̥�����ף�",b); 
					xi++; 
					istrue = true;
					break;
				}
			}
			if(!istrue) {
				w(w,jipre+"�ո�["+rgname+"]����֧["+nzname+"]��{"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.rg][yearzi]]+"}�أ�����",b); 
				ji++; 
			}
		}
		
		if(p.isYima(p.getChongGong(p.getDiziGong(yearzi)))) w(w,pipre+"��֧�嶯�����乬��֧֮�꣬�ж���",b);
		if(p.isKong(g) && (dz1==yearzi || dz2==yearzi)) w(w,pipre+"��������������֧��ʵ�����б仯��",b);
		if(p.isKong(g) && (p.getChongzi(dz1)==yearzi || p.getChongzi(dz2)==yearzi)) w(w,pipre+"��������������֧��ʵ�����б仯��",b);
		if(p.isYima(g) || p.isYimaOfRi(g)) w(w,pipre+"��֧Ϊ���ǣ����ڱ��б仯��",b);
		
		return new int[]{ji,xi};
	}
	
	/**
	 * ���ר��
	 * @param w�Ƿ������ispre�Ƿ���ʾǰ׺��b�Ƿ�ո�
	 * @return
	 */
	
	private int[] getNiangan(boolean w, int g,boolean b,boolean ispre) {
		int ji = 0, xi=0; //��һ��������ָ��
		String pre = g+"����";  
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		int[] dpjy = p.getTpjy(g);
		int rigan = p.getTiangan(SiZhu.rg, SiZhu.rz);
		if(dpjy[0]== rigan || dpjy[1]==rigan) {
			w(w,jipre+"�ո��������ͬ��Ϊ���͸����������Ϊ��",b);
			ji++;
		}
		
		return new int[]{ji,xi};
	}
	
	/**
	 * �õ������������֧������Ϣ
	 * @param g
	 * @param isw�Ƿ������ispre�Ƿ���ʾǰ׺��b�Ƿ�ո�
	 * @int index������ָ�������غ��жϸ����Ƿ�Ҫ��ʾ����
	 */
	private int[] getShare1(boolean w, int g,boolean b,boolean ispre) {
		int ji = 0, xi=0; //��һ��������ָ��
		String pre = g+"����";
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		//��share3��share22��������
//		String[] bmjx = p.getmenJX(g);
//		String[] bsjx = p.getshenJX(g);
//		String[] jxjx = p.getxingJX(g);
//		String[] gjjx = p.getJixiongge(g);
//		if(gjjx[0].equals("0")) {  //���׸��ٵ�һ����������ָ����1
//			//t = "��"+gjjx[1]+"����";
//			if(bmjx[0].equals("-1") && bsjx[0].equals("-1") && jxjx[0].equals("-1")) {t+="����"+bmjx[1]+jxjx[1]+bsjx[1]; index++;}
//			else if(bmjx[0].equals("-1") && bsjx[0].equals("-1")) { t+="����"+bmjx[1]+bsjx[1];index++;} 
//			else if(bmjx[0].equals("-1") && jxjx[0].equals("-1")) { t+="����"+bmjx[1]+jxjx[1];index++;}
//			else if(jxjx[0].equals("-1") && bsjx[0].equals("-1")) { t+="����"+bmjx[1]+bsjx[1];index++;}
//			
//			if(p.isKong(g)) { t+="���׸��������������¸����أ�"; index++;}			
//			w(w,pre+t,b);
//		}else{	//������������ָ����1
//			w(w,pre+"��"+gjjx[1]+"����",b);
//			index--;
//		}
		
		if(p.isChongke(g, ritpgong)) {
			w(w,xipre+"����ոɹ������д��ִ��ѣ�",b);
			xi++;
		}

		boolean ismenpo = QiMen.men_gong[daoqm.gInt[3][1][g]][g].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[daoqm.gInt[3][1][g]][g].equals(QiMen.zphym1);
		if(ismenpo) {w(w,xipre+"�����ȣ����²�����",b); xi++;}
		if(ismenzhi) {w(w,xipre+"�����ƣ����²�����",b); xi++;}
		
		if(p.isTGanMu(g)) {w(w,xipre+"���̸��ڱ�����Ĺ��һ������֮��",b);xi++;}
		
		if(zhifugong==g) {w(w,jipre+"��ֵ�����������ˣ�",b);ji++;}
		
		int[] dpjy = p.getDpjy(g);
		if(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN) {w(w,xipre+"�����ɣ����ڵ������룻",b);xi++;}
		if(dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG) {w(w,xipre+"���ٸ������ף��������裬�����Ʋƣ�",b);xi++;}
		
		if(g==shangmengong && g==xingchonggong) {w(w,xipre+"���ż���壬��������֮��",b);xi++;}
		
		return new int[]{ji,xi};
	}
	/**
	 * �õ����������Ϣ�����ˡ����깲��
	 * @param isw�Ƿ������ispre�Ƿ���ʾǰ׺��b�Ƿ�ո�
	 */
	private int[] getShare22(boolean isw, int g,boolean b,boolean ispre) {
		int rigan = p.getTiangan(SiZhu.rg, SiZhu.rz);
		String riganname = YiJing.TIANGANNAME[rigan];
		int xi = 0;  //��ָ
		int ji = 0;  //��ָ
		String pre = g+"����";
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		if(QiMen.gan_gong_mu[rigan][g]!=null) {
			t = ("����Ϊ�ո�["+riganname+"]֮Ĺ�⣬ΪǱ�����ã���������״̬��");
			if(p.isYima(g) || p.isYimaOfRi(g)) t += "�������ǣ����ʾû����ȫ��ס��";
			else if(p.isYima(p.getChongGong(g)) ||
					p.isYimaOfRi(p.getChongGong(g))) t+= "�����Թ�������壬���ʾû����ȫ��ס��";
			w(isw ,xipre+t,b);
			xi++;
		}
		
		String[] bmjx = p.getmenJX(g);
		String[] bsjx = p.getshenJX(g);
		String[] jxjx = p.getxingJX(g);
		String[] gjjx = p.getJixiongge(g);
		if(gjjx[0].equals("0")) {  //���׸���Ϊ��
			t = "��"+gjjx[1];
			if(bmjx[0].equals("-1")) t+="��"+bmjx[1];
			if(jxjx[0].equals("-1")) t+="��"+jxjx[1];
			if(bsjx[0].equals("-1")) t+="��"+bsjx[1];
			w(isw ,xipre+t+"����������ʮ֮�˾ţ�",b);  //����������һ�μ��ѵ�ʱ�⣬ʱ�˲���
			xi++;
		}
		else if(gjjx[0].equals("1")) {  //������Žм�
			String ma = "";
			if(p.isYima(g) || p.isYimaOfRi(g)) ma = "�ַ����ǣ��ض��������죻";
			else if(p.isYima(p.getChongGong(g)) ||
					p.isYimaOfRi(p.getChongGong(g))) t+= "�ַ�Թ�������壬�ض��������죻";
			
			t = "��"+gjjx[1];
			if(!bmjx[0].equals("-1")) t+="��"+bmjx[1];
			if(!jxjx[0].equals("-1")) t+="��"+jxjx[1];
			if(!bsjx[0].equals("-1")) t+="��"+bsjx[1];
			w(isw , jipre+t+"�����������㼱��"+ma,b);  //����������һ�γɹ��������ʱ��
			ji++;
		}
		
		if(g==zhifugong && p.isSheng(g, ritpgong)) {
			w(isw, jipre+"��ֵ�������չ����ӹٽ���֮��",b);
			ji++;
		}
		
		return new int[]{ji,xi};
	}
	
	/**
	 * �õ�������Ϣ�����ˡ����깲��
	 * @param g
	 */
	private int[] getShare21(boolean isw, int g,boolean b) {
		int ji=0, xi=0; 
		
		if(p.getshenJX(g)[0].equals("1"))  ji++ ;
		else if(p.getshenJX(g)[0].equals("-1")) xi++;	//daoqm.gInt[1][1][g];		
		if(p.getmenJX(g)[0].equals("1"))  ji++ ;
		else if(p.getmenJX(g)[0].equals("-1")) xi++;	//daoqm.gInt[3][1][g];
		if(p.getxingJX(g)[0].equals("1"))  ji++ ;
		else if(p.getxingJX(g)[0].equals("-1")) xi++;	 // daoqm.gInt[2][1][g];
		
		w(isw && g==dumengong,"�����ţ����ڼ��磬��չ�����˶�����Ҳ����װ��",b);
		w(isw && g==jing3mengong,"�����ţ����Ļ��������������������飻",b);
		w(isw && g==kaimengong,"�����ţ�������һ����ҵ���������¿�ʼ�µ����������ְ����Ϊ��˽֮�±ر�й©��",b);
		w(isw && g==shangmengong,"�����ţ����˲���",b);
		w(isw && g==shengmengong,"�����ţ�����ƣ�",b);
		w(isw && g==xiumengong,"�����ţ��������ͼ�ͥ���棻",b);
		w(isw && g==simengong,"�����ţ�����ɥТ��������һ������������ɱ��Ȩ�������̴��ף�",b);
		w(isw && g==jing1mengong,"�����ţ�������ٷǣ�",b);
		
		w(isw && g==xingfugong,"���츨�ǣ�������,�����Ļ���",b);
		w(isw && g==xingyinggong,"����Ӣ�ǣ����Ļ�������ȵ��",b);
		w(isw && g==xingchonggong,"������ǣ�Ϊ���£�",b);
		w(isw && g==xingruigong,"�������ǣ���������",b);
		w(isw && g==xingrengong,"�������ǣ��󼪣�",b);
		w(isw && g==xingzhugong,"���������ǣ�ϲɱ��ս�������Ʊ棬������������һ�棻",b);
		w(isw && g==xingxingong,"�������ǣ�Ϊҽҩ��",b);
		w(isw && g==xingpenggong,"���������ǣ�����������Ϊ���ǻ۵��ˡ������ˣ�Ҳ����ͣ�����ˣ�",b);
		w(isw && g==xingqingong,"�������ǣ�Ϊ�ٹ�֮�ף�",b);
		
		w(isw && g==shenhegong,"�ϳ����ϣ���������",b);
		w(isw && g==shenyingong,"�ϳ�̫����Ϊ����֮�񣬹�������֮��",b);
		w(isw && g==shenshegong,"�ϳ����ߣ�Ϊ�龪��",b);
		w(isw && g==shendigong,"�ϳ˾ŵأ�����֮��",b);
		w(isw && g==shentiangong,"�ϳ˾��죬ΪԤıǰ;֮�£�����֮�󣬴���־��ߴ�Ҳ����������������죻",b);
		w(isw && g==zhifugong,"�ϳ�ֵ�����������ˣ�",b);
		w(isw && g==shenhugong,"�ϳ˰׻�������ɥТ����",b);
		w(isw && g==shenwugong,"�ϳ����䣬���˵���",b);
		
		return new int[]{ji,xi};
	}
	
	/**
	 * �õ�������Ϣ��Ϊ�չ������ˡ����깲��
	 * @param isw��ʾ�Ƿ������b��ʾ�Ƿ�ո�ispre��ʾ�Ƿ�Ҫ��ʾǰ׺
	 * @return int[] {ji, xi}
	 */
	private int[] getShare3(boolean isw, int g,boolean b, boolean ispre) {
		String s = null;
		String[] shenjx = p.getshenJX(g);
		int xi = 0;  //��ָ
		int ji = 0;  //��ָ
		String pre = g+"����";
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		if(g==shangmengong && g==shenshegong) {
			s = "���ż����ߣ����˱���ҧ֮��";
			if(SiZhu.nz==YiJing.SI) s+="�������ߣ������������׷�����";	
			w(isw,xipre+s,b);
			xi++;
		}
		
		int[] ggong = p.getTpjy(g);
		w(isw && g==shenhugong && (YiJing.GENG==ggong[0] || YiJing.GENG==ggong[1]),"�׻��Ӹ���Ȩ��֮��",b);
		if(p.isJixing(g)) {
			w(isw, xipre+"�����˲��ٷǣ������̶����䶯��С�ڳ壻",b);
			xi++;
		}
		if(p.isKong(g)) {
			w(isw, xipre+"�������߼��������߸��࣬�������ࣻ",b);
			xi++;
		}
		if(p.isTDGanHe(g)) {
			ji++;
			if(shenjx[0].equals("-1")) 
				w(isw, jipre+"�乬�����ظ���ϣ���ͬ���˺���֮�󣬱�Ϊ���񣬵�����"+shenjx[1]+"����Ϊ����֮�ϻ�����֮�ϣ�",b);
			else
				w(isw, jipre+"�乬�����ظ���ϣ���ͬ���˺���֮��",b);
		}
		
		return new int[]{ji,xi};
	}
	
	private int getFirstGong() {
		return QiMen.ziOfGua[SiZhu.nz];
	}
	private int getNextGong(int gong) {
		if(gong==5) {
			Messages.error("ȡ��5������һ������������5����");
			return -1;
		}
		int[] gongs = {1,8,3,4,9,2,7,6};
		for(int i=0; i<gongs.length; i++) {
			if(gongs[i]==gong) {
				return i<gongs.length-1 ? gongs[i+1] : gongs[0];
			}
		}
		Messages.error("ȡ��"+gong+"������һ������");
		return -1;
	}
	/**
	 * �ڳ�������ϼ���ָ����add��󣬷��ظ�֧
	 * @param add
	 * @return
	 */
	private String getNextSui(int add) {
		int gan = (SiZhu.ng+add)%10==0 ? 10 : (SiZhu.ng+add)%10; 
		int zi = (SiZhu.nz+add)%12==0 ? 12 : (SiZhu.nz+add)%12; 
		return YiJing.TIANGANNAME[gan]+YiJing.DIZINAME[zi];
	}
	private int[] getNextSuiGanzi(int add) {
		int gan = (SiZhu.ng+add)%10==0 ? 10 : (SiZhu.ng+add)%10; 
		int zi = (SiZhu.nz+add)%12==0 ? 12 : (SiZhu.nz+add)%12; 
		return new int[]{gan,zi};
	}
	
	/**
	 * �õ�����ָ����������
	 * @param ji: Ϊ����ָ�����ʾ
	 * @param xi��Ϊ�׵�ָ�����ʾ
	 * @return
	 */
	private String getJXindex(int ji, int xi) {
		String s = "";
		for(int i=0; i<ji; i++) {
			s+=JIINDEX;
		}
		for(int i=0; i<xi; i++) {
			s+=XIINDEX;
		}
		return s;
	}
}