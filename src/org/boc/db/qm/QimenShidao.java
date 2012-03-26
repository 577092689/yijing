package org.boc.db.qm;

import org.boc.db.SiZhu;

public class QimenShidao extends QimenBase{
	private String[] jibing ; //�������˹����е��������
	int index = 0; //��������ΪdescMY����

	public QimenShidao(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
  
	public String getThing(StringBuffer str,String mzText, int ysNum,boolean boy) {
		init(mzText, ysNum, boy, 2000);	//2000Ϊ���鳤��	
		
		w(p.NOKG+"һ�����ﶪʧ�ϣ�");
		w("1. ȡ����");
		getThing1();
		w(p.NEWLINE);
		w("2. ���ֶϣ�");
		getThing2();
		w(p.NEWLINE);
		w("3. ��ϣ�");
		getThing3();
		w(p.NEWLINE);
		w("4. ������Ӧ�ڣ�");
		getThing4();
		w(p.NEWLINE);
		
		w(p.NOKG+"�������ﱻ���ϣ�");
		w("1. ȡ����");
		getThing11();
		w(p.NEWLINE);
		w("2. ���ֶϣ�");
		getThing12();
		w(p.NEWLINE);
		w("3. ��ϣ�");
		getThing13();
		w(p.NEWLINE);
		w("4. ������Ӧ�ڣ�");
		getThing14();
		w(p.NEWLINE);
		
		return p.format(str, my);
	}
	
	public void getThing1() {
		w("�ո�Ϊʧ������ĳ��ʧ�����Ϊ���¾����ո�Ϊ����");
		w("ʱ��Ϊ��ʧ֮Ǯ������������Ϊ��ʧ��֮����");
		w("��Ϊ�ļ������֤�����п����ֻ�������Ϊ���顢��Ʊ�����֤���ֻ�����Ϊ��ʧ֮Ǯ����п���");			
	}
	public void getThing2() {
		if(p.isMenFan() || p.isXingFan()) w("�����ٶȿ죬Ҳ�����һأ�");
		if(p.isMenFu() || p.isXingFu()) w("��������ԭ�ز�������Ϊ������ڲ��ˣ�Ҳ���Ʋƣ���ʧ���أ�");
	}
	public void getThing3() {
		if(ritpgong==shitpgong ) w("��ʱͬ����û�ж�ʧ�������ҵ���");
		else if(p.isBihe(ritpgong, shitpgong)) {
			if((ritpgong==2 || ritpgong==8) && p.isYima(ritpgong) || p.isYima(shitpgong))
				w("��ʱ�Ⱥ͵���28���Գ壬��������Ϊ��ʧ֮�������һأ�");
			else
				w("��ʱ�Ⱥ��ﲻʧ��");
		}
		else if(p.isSheng(ritpgong, shitpgong)) w("����ʱ��˵��Ҫ���������ȥ�ң���Żأ�");
		else if(p.isSheng(shitpgong, ritpgong)) w("ʱ���乬(������֮��)�����ո��乬��Ҳ���һء�");
		else if(p.isGongke(shitpgong, ritpgong)) w("ʱ�ɿ��ոɣ������ҡ�");
		else if(p.isGongke(ritpgong, shitpgong)) w("�ոɿ�ʱ�ɣ��Լ�����ȥ�һ�ȥȡ���ܻ�����");		
		
		if(p.isTG3He(shitpgong) || p.isTG6He(shitpgong) || p.isTDGanHe(shitpgong))
			w("ʱ�ɷ�ϸ񣬲����ҡ�");
		if(shitpgong==shendigong) w("ʱ���پŵأ������ҡ�");
		
		if(p.isKong(shitpgong) || p.isTGanSMJ(SiZhu.sg, SiZhu.sz)!=null) w("ʱ�������������Ĺ��֮����Ҳ�����һأ�");
		if(p.isTGanMu(SiZhu.sg, SiZhu.sz)) w("ʱ����ĹΪʧ�ﱻ��أ�����ʰ�����ղأ�");
		if(p.isKong(ritpgong) || 
				(ritpgong==xindpgong || ritpgong==xintpgong)) w("�ոɿ����������������Լ��Ŵ��˵ط���");
		if(shitpgong==shengmengong) w("ʱ�������ţ����ܻ��ڼ��");
		if(shitpgong==shendigong || shitpgong==shenyingong) w("ʱ���پŵء�̫���������׷��֣�");
		if(shitpgong==shenwugong) w("ʱ�������䣬����ܱ���͵�߻����Լ������ˣ�");
		if(shitpgong==shenshegong) w("ʱ�����������Լ�������");
		if(shitpgong==shenyingong || shitpgong==xingruigong || shitpgong==simengong)
			w("��̫���������ǡ����ţ��������Ե�Ь����з���֮��ĵص㣬�˴����鼮��ҩƷ��������");
		if(ystpgong==shenhugong) w("ʧ��֮����̫���׻���Ϊ��Ҫ��Ʒ��");		
		
		if(p.isGongke(shenwugong, shitpgong) || p.isGongke(xingpenggong, shitpgong))
			w("ʱ�ɹ�������"+shenwugong+"����������"+xingpenggong+"�����ˣ�������Ǳ������ˡ�");
		else
			w("���乬�������ǹ�����ʱ�ɹ�������͵��");
		if(p.isGongke(ritpgong, shenwugong)) w("�չ������䣬��ʧ����");
		if(p.isKong(shenwugong)) w("�����乬��������ʧ����");
		//if(!p.isGongke2(shenwugong, shitpgong)) w("���䲻��ʱ�ɣ���͵��");
		if(p.isSheng(shitpgong, shenwugong)) w("ʱ�������䣬��͵֮��");
				
		if(dingtpgong==xiumengong) w("��+��=���ֻ���");
		if(xingpenggong==wutpgong) w("��+��=�Ʋƣ�");
		if(shangmengong==zhifugong) w("��+ֵ��=���Ƴ�����");
		
		if(shitpgong==guitpgong && guitpgong==bingdpgong) w("ʱ�ɹ����+�����˼�ϲ�����ܱ��䳤���ҵ���");
		else if(shitpgong==wutpgong && wutpgong==gengdpgong) w("ʱ�ɹ�����+����ָ��Ų���˵ط���");
		w("����ʧ��������֮�����չ�������ʱͬ���������һأ����ոɹ��������һأ�");
		w("����ʧ�����乬��ʱ�ɹ����ոɹ��ټ��Ǽ��񼪸��ﲻʧ֮��");
		w("��������"+wutpgong+"����������棬��ȡ��֮������");
		w("��������ҹ���ȡ2��7���������ſ�ȡ3�����ٿ����̶��������3������ȡ3���Ѿ����ˣ�");
		w("����ʧ�����Ϊ�����⣬��Ϊţ����װ��һ��ţƤ���ڣ�δ��δʱ���˵��δ��δʱ�����������Ʒ��");
		w("����ʧǮ���������Ϊԭ����Ǯ�ĵط���ʧ���֤������̾���Ϊ��ԭ�����ڵط���");
		w("������������������Ӣ����Ӣ�������ʱ��乬й�����������������+��Ϊ�䶯����ʧ�����ʣ�");				
	}
	
	public void getThing4() {
		if(p.isNenpan(ritpgong) && p.isNenpan(shitpgong)) w("�ո���ʱ��ͬ�����̣���Ǯ�ﶪʧ�ڼ��л������");
		else if(!p.isNenpan(ritpgong) && !p.isNenpan(shitpgong)) w("�ո���ʱ��ͬ�����̣���Ǯ�ﶪʧ����߻�Զ����");
		else if(p.isNenpan(ritpgong) && !p.isNenpan(shitpgong)) w("�ո����ڡ�ʱ�����⣬��ʧ��Ǯ������ߣ�");
		else w("ʱ�����ڡ��ո����⣬��ʧ�ڼ��С�");
		
		w("ʱ����"+shitpgong+"����ʧ����"+QiMen.JIUGONGFXIANG[shitpgong]+"��");
		if(this.isMenfu || isXingfu) w("����˵��û���뿪����ԭ�أ�");		
		
		if(dingtpgong==8 && dingtpgong==simengong)
			w("��Ϊ���֤������Ϊ�ߴ�������Ϊ�������ﱻ���ڹ��ڣ�");
		if(shitpgong==3 && 3==jing1mengong && jing1mengong==gengtpgong) w("ʱ�ɷ���+��+��Ϊ��ľ�ṹ���������п�����Ƥ���չ�");
		if(shitpgong==3 && 3==shangmengong) w("ʱ�ɷ���+��=ľ��");
		if(shitpgong==9) w("ʱ�ɷ��빬Ϊ��ɫ���������У�");
		if(shangmengong==bingtpgong && bingtpgong==shenhugong) w("ʱ�ɷ���+��+������·���ˣ�");
		if(p.isNenpan(shitpgong) && (shitpgong==3 || shitpgong==4)) w("ʱ�ɷ�����+�����=����ľ�ƼҾߣ�");
		if(p.isNenpan(shitpgong) && shitpgong==8) w("ʱ�ɷ���+����=�ߴ�����");
		if(shitpgong==6 && 6==jing1mengong) w("ʱ�ɷ�Ǭ+��=���ӻ���");
		if(shitpgong==xingruigong) w("ʱ�ɷ�����=��ë��Ʒ�������ڴ����ŷ⣻");
		if(shitpgong==2) w("ʱ�ɷ�����Ϊ����");
		if(p.isYima(shitpgong)) w("ʱ�ɷ�����Ҳ������");
		if(shitpgong==yitpgong && shitpgong==xingpenggong) w("ʱ�ɷ���+��=��ɫľ��");
		if(shitpgong==8) w("ʱ�ɷ���=ī��ɫ��");
		if(shitpgong==wutpgong && shitpgong==gengtpgong) w("ʱ�ɷ���+��=�Ʋƣ�");
		else if(ritpgong==wutpgong && ritpgong==gengtpgong) w("�ոɷ���+��=�Ʋƣ�");
				
		int men = daoqm.gInt[3][1][shitpgong];
		w("ʱ����"+shitpgong+"��Ϊ"+QiMen.JIUGONGPEOPLE[shitpgong]+"��"+QiMen.bm1[men]+"��Ϊ������"+men+"����ʰ����"+men+"��"+QiMen.JIUGONGPEOPLE[shitpgong]+"��");
		
		w("�������乬Ϊʧ�﷽λ������Ϊ��������");
		w("��ʧ��������֮����������Ϊ���棬�������ڼ��У�");
		w("����ʧǮ���ҵ�ʱ�䣬һ����ʱ�����ո�֮��ʱΪӦ�ڣ�");
		w("���򰴸�����ϣ����ո�Ѱ���ϣ����ո�Ѱ���£�");
		w("������ߣ��Գ��֮��ʱΪӦ�ڡ�");
		w("��Ѯ��������ʵ֮��ʱΪӦ�ڡ�");
		w("��ֵʹ�乬Ҳ�ɶ�������");
		w("��ʱ���乬Ҳ�ɶ�������");
	}
	public void getThing11() {
		w("����ΪС����");
		w("�ܹ��صĶ�����������ʽ��Ᵽ�������Ϊ�����");
		w("�Թ���Ϊ����֮�ˣ�");
		w("����Ϊ������λ�����ܷ���");
		w("����Ϊ������������ҲΪ�������׻�ҲΪ������ֵʹ��ҲΪ������");
	}
	public void getThing12() {
		
	}
	public void getThing13() {
		int wx = daoqm.gInt[2][1][shenwugong];  //���乬����֮��		
		if(wx==1 || wx==2 || wx==3 || wx==4 || wx==5) w("������"+shenwugong+"�����ϳ�����Ϊ������");
		else w("������"+shenwugong+"�����ϳ�����ΪŮ����");
		if(shenwugong==yitpgong) w("�������ң�ΪŮ�ˣ�");
		if(p.getGongWS(shenwugong)[0].equals("1")) w("�������ٹ����࣬��׳������");
		else w("��������Ϊ��������");
		w("����������������Ϊ������ɫ����������Ϊȹ����ɫ��");
		
		String[] wuhj = p.isshenHeju(shenwugong);
		if(!wuhj[0].equals("1")) w("������"+shenwugong+"��ʧ�֣��ư�֮��");
		if(gengtpgong==shenwugong) w("��������ͬ����ץ��С͵�ư�֮��");
		if(zhishigong==shenwugong || p.isGongke(shenwugong, zhishigong)
				|| p.isSheng(zhishigong, shenwugong)) w("ֱʹ�����䣬�������ֱʹ����ֱʹ�����䣬���Ǳ�����");
		if(p.isGongke(shenhugong, shenwugong) || p.isGongke(shenhugong, xingpenggong))
			w("��������"+shenhugong+"����������"+xingpenggong+"������������"+shenwugong+"��������ץס��");
		else if(p.isBihe(shenhugong, shenwugong) || p.isBihe(shenhugong, xingpenggong)) 
			w("��������"+shenhugong+"��������"+xingpenggong+"������������"+shenwugong+"���Ⱥͣ��п��ܲ�����������๴�ᡣ");
		else if(shenhugong == shenwugong || shenhugong == xingpenggong) 
			w("��������"+shenhugong+"��������"+xingpenggong+"������������"+shenwugong+"��ͬ���������߾����ư��߱���");
		else w("��������"+shenhugong+"������������"+xingpenggong+"������������"+shenwugong+"������������̫�󣬲����߲������֡�");
		
		if(p.isTGanMu(SiZhu.sg, SiZhu.sz)) w("ʱ����Ĺ�����ﱻ�������ˣ�");
		if(niantpgong==gengdpgong || niandpgong==gengtpgong) w("��������ҵ�֮��");
		if(yuetpgong==gengdpgong || yuedpgong==gengtpgong) w("���¸����ҵ�֮��Ҳ�����ѡ��ֵ��ҵ���");
		if(ritpgong==gengdpgong || ridpgong==gengtpgong) w("���ո����ҵ�֮��");
		if(shitpgong==gengdpgong || shidpgong==gengtpgong) w("��ʱ�����ҵ�֮��");
		
		if(p.getSanji(shitpgong)[0].equals("1") && shitpgong==shangmengong) w("ʱ����"+shangmengong+"���������Ŵ��棬Ϊ�����Ʒ��Ϊ����");
		if(p.isGongke(shenhugong, jing3mengong)) w("�׻�"+shenhugong+"���˾���"+jing3mengong+"������ѧУ���·���ҵ���");
		if(jing3mengong==1) w("��+��=ѧУ�Ƚ��ƾɣ�");
		if(!p.isNenpan(shenwugong)) w("����+����=С͵������ˣ�");
		if(shenwugong==xingyinggong) w("����+��Ӣ�ǣ�С͵�����ڣ�");
		if(p.getSanji(shenwugong)[0].equals("1")) w("���乬�����棬С͵ΪżȻ������");
		if(p.isJixing(shangmengong)) w("������"+shangmengong+"�����ٻ��̣�Ϊ���𻵣�");
		if(xingzhugong==7) w("�������ڶҹ������п����Ƿǣ�"); 		
	}
	public void getThing14() {
		if(gengtpgong==niandpgong) w("���̸���"+gengtpgong+"�������ٵ���Ϊ��ɣ�����������ڿ����ư�������϶��ţ��ư����а��ա�");
		else if(gengtpgong==yuedpgong) w("���̸���"+gengtpgong+"�������ٵ���Ϊ�¸ɣ������¸񣬱��¿����ư�������϶��ţ��ư����а��ա�");
		else if(gengtpgong==ridpgong) w("���̸���"+gengtpgong+"�������ٵ���Ϊ�ոɣ������ո񣬱��տ����ư�������϶��ţ��ư����а��ա�");
		else if(gengtpgong==shidpgong) w("���̸���"+gengtpgong+"�������ٵ���Ϊʱ�ɣ�����ʱ�񣬼�ʱ����ư�������϶��ţ��ư����а��ա�");
		else w("���̸���"+gengtpgong+"�������������ꡢ�¡��ա�ʱ�ĸ������ư���");
		w("ֵʹ��ʱ���乬����ΪӦ�ڣ�");
	}
}