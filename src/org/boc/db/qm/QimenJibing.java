package org.boc.db.qm;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenJibing extends QimenBase{
	private String[] jibing ; //�������˹����е��������
	int index = 0; //��������ΪdescMY����

	public QimenJibing(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
  
	public String getBing(StringBuffer str,String mzText, int ysNum,boolean boy) {
		init(mzText, ysNum, boy, 2000);	//2000Ϊ���鳤��	
		
		w(p.NOKG+"һ��������");
		getBing1();
		w(p.NEWLINE);
		
		w(p.NOKG+"������ϣ�");
		w("1) �����в���");
		getBing21();
		w(p.NEWLINE);
		w("2) �кβ���");
		getBing22();
		w(p.NEWLINE);		
		w("3) ҽҩ��");
		getBing23();
		w(p.NEWLINE);
		w("4) ���飺");
		getBing24();
		w(p.NEWLINE);
		w("5) Ӧ�ڣ�");
		getBing25();
		w(p.NEWLINE);
		w("6) ������");
		getBing26();
		w(p.NEWLINE);
		w("7) ������");
		getBing27();
		w(p.NEWLINE);
		
		return p.format(str, my);
	}
	
	/**
	 * һ�����ֶ�
	 */
	public void getBing1() {
		w("����Ϊ���������ϣ�����Ϊ��ҩ��");
		w("��Ϊ��ҩ��ҽ������Ϊ�׻�����������");
		w("����Ϊ������");
	}
	//�����в�
	public void getBing21() {
		w("����"+xingruigong+"����"+QiMen.JIUGONGPEOPLE2[xingruigong]+"�в���");		
		if(p.isGongke(yitpgong, xingruigong)) w("�ҹ������ǲ��������޲���");
		boolean isnei = p.isNenpan(xingruigong);
		
		if(xingruigong==niantpgong || xingruigong==niandpgong) {
			if(isnei) w("��������"+xingruigong+"���������̣�����ɣ������ﳤ����ĸ�м�����");
			else w("��������"+xingruigong+"��������ɣ������̣�����������ĸ�򳤱��м�����");
		}
		if(xingruigong==yuetpgong || xingruigong==yuedpgong) {
			if(isnei) w("��������"+xingruigong+"���������̣����¸ɣ��������ֵܽ����м�����");
			else w("��������"+xingruigong+"�������¸ɣ������̣����÷򡢽���������м�����");
		}
		if(xingruigong==shitpgong || xingruigong==shidpgong) {
			if(isnei) w("��������"+xingruigong+"���������̣���ʱ�ɣ�������С���м�����");
			else w("��������"+xingruigong+"������ʱ�ɣ������̣������ݼ�С���м�����");
		}
	}
	//�кβ�
	public void getBing22() {
		boolean isnei = p.isNenpan(xingruigong);
		String bwyi = (yitpgong==1 || yitpgong==8 || yitpgong==3 || yitpgong==4) && !p.isNenpan(1) ? 	"���" : "�Ҳ�";
		String bwxin = (xintpgong==1 || xintpgong==8 || xintpgong==3 || xintpgong==4) && !p.isNenpan(1) ? 	"���" : "�Ҳ�";
		String bwren = (rentpgong==1 || rentpgong==8 || rentpgong==3 || rentpgong==4) && !p.isNenpan(1) ? 	"���" : "�Ҳ�";
		String bwgui = (guitpgong==1 || guitpgong==8 || guitpgong==3 || guitpgong==4) && !p.isNenpan(1) ? 	"���" : "�Ҳ�";
		
		if(isnei) w("��������"+xingruigong+"���������̣���"+QiMen.JGBUWEINEI[xingruigong]+"������");
		else w("��������"+xingruigong+"���������̣���"+QiMen.JGBUWEIWAI[xingruigong]+"������");
		w("ʱ��Ҳ�������ʼ��������Ŵ�����������Ϊ�˰̡������׻��乬��Ҫ�����ɺ��ǹ�ͬ�ϣ�");
		w("�������乬֮�ϳ˸��ܿˣ��������ƣ��˸ɡ���������λ���м�����");
		
		if(xingruigong==shenhegong) w("�����������ϣ�����ֹһ�ֲ���");
		if(p.isKong(shenhegong)) w("��������˽�Ϻ�֮�£�����"+shenhegong+"����������ʾ�������");
		if(xingruigong==shenwugong) w("���������䣬�����а�����������״̬�ļ�����ѪҺ������Һ�������˻��ԣ�");
		if(xingruigong==shenshegong) w("���������ߣ��������ƣ�Ҳ����ת�Ƴɱ�Ĳ���");
		
		if(xingruigong==zhifugong) w("������ֵ������"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[1]:"���̣�"+QiMen.TIANGANWAI[1])+"������");
		if(xingruigong==yitpgong) w("�������ң���"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[2]:"���̣�"+bwyi+QiMen.TIANGANWAI[2])+"������");
		if(xingruigong==bingtpgong) w("�����ٱ�����"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[3]:"���̣�"+QiMen.TIANGANWAI[3])+"������");
		if(xingruigong==dingtpgong) w("�����ٶ�����"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[4]:"���̣�"+QiMen.TIANGANWAI[4])+"������");
		if(xingruigong==wutpgong) w("�������죬��"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[5]:"���̣�"+QiMen.TIANGANWAI[5])+"������");
		if(xingruigong==jitpgong) w("�����ټ�����"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[6]:"���̣�"+QiMen.TIANGANWAI[6])+"������");
		if(xingruigong==gengtpgong) w("�����ٸ���"+(isnei? "���̣�"+QiMen.TIANGANNEI[7]:"���̣�"+QiMen.TIANGANWAI[7])+"��������꼪������Ϊ���ԣ�");
		if(xingruigong==xintpgong) w("������������"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[8]:"���̣�"+bwxin+QiMen.TIANGANWAI[8])+"������");
		if(xingruigong==rentpgong) w("�������ɣ���"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[9]:"���̣�"+bwren+QiMen.TIANGANWAI[9])+"������");
		if(xingruigong==guitpgong) w("�����ٹ��"+ (isnei ? "���̣�"+QiMen.TIANGANNEI[10]:"���̣�"+bwgui+QiMen.TIANGANWAI[10])+"������");
		
		if(xingruigong==shenhugong) w("�����ٰ׻������˲���");
		if(xingruigong==jing3mengong) w("�����پ��ţ�����Ѫ����Ѫ��Ѫ�ܼ�����Ҳ������������Ӫ�������ϣ�");
		if(shangmengong==shentiangong) w("���������ͬ��"+shangmengong+"�����ߴ�����ˤ��֮��");
		
		if(xingruigong==6 && gengtpgong==6) w("ǬҲ������ֳ�������������跿�²��٣�");
		if(xingruigong==2 && shenhugong==2 && kaimengong==2) w("��+�׻�+����=������������");
		if(xingruigong==9 && shenwugong==9 && dingtpgong==9) w("��+����+��=����Ѫ��");
		if(xingruigong==xintpgong && xintpgong==guitpgong) w("��+��=���಻�ã����ʲ��룬�����죻");
		if(xingruigong==3 && simengong==3) w("��+��=�¾���֬���Σ�");
		if(shangmengong==6) w("��+Ǭ+�׸�=ͷ���ܹ��ˣ�");
		if(xingruigong==dingtpgong && dingtpgong==xintpgong) w("��+��=���ಡ��");
		if(xingruigong==dingtpgong && dingtpgong==gengtpgong) w("��+��=���ಡ��");
		if(shangmengong==dingtpgong && p.isTGanMu(YiJing.DING, 0)) w("��+��+��Ĺ=���ಡ��");
		if(shangmengong==4 && shenhugong==4) w("��+�׻�+��[+����]=��Ѫ�ܼ���������Ѫ�ܣ�");
		if(simengong==7 && xingxingong==7) w("��+�ҹ�+����=ҽ����������7���׳��Ị̇�");
		if(xingruigong==dingtpgong && !p.isNenpan(xingruigong)) w("��+����=��ǻ����");
		if(xingruigong==jitpgong && jitpgong==guidpgong) w("��+��̴���");
		if((gengtpgong==6 || gengtpgong==9) && gengtpgong==guidpgong) w("���ٹ��Ǭ�����빬����Ѫ˨���Թ����ȡ�");
		if(xingruigong==7 && xintpgong==7 &&  xintpgong==wudpgong) w("�����쵽һ�飬��ҹ���������������");
		if(xingruigong==2 && xintpgong==2 &&  xintpgong==wudpgong) w("�����쵽һ�飬��������һ���Ǿ�׵������");
		if(xingruigong==4 && xintpgong==4 &&  xintpgong==wudpgong) w("�����쵽һ�飬���㹬��һ���Ǿ�׵������");		
		if(xingruigong==guitpgong && guitpgong==dingdpgong && !boy) w("�����Ϊ������֢��");
		if(xingruigong==rentpgong && rentpgong==dingdpgong && !boy) w("�ɣ�����Ϊ������֢��");
		if(xingruigong==shenshegong && shenshegong==guitpgong) w("��ˮ�����ߣ���ϵͳ���������⡣");
		
		w("���������š����š������׻�ͬ����Ϊ��֢��������֢���Ҳ������ء�ֻҪ����������һ��Ҳ�ǰ�֢���֢������һ�𵫷��׸������̸��̣�Ҳ�ɶϼ������֣����׹����֮���ɶ���Դ��");
	}
	//ҽҩ
	public void getBing23() {
		String[] xinw = p.isxingHeju(xingxingong);
		String[] yiw = p.isganHeju(yitpgong);
		if(xinw[0].equals("1")) w("��������"+xingxingong+"���Ͼ֣�Ϊ��ҽ��"+xinw[1]);
		else w("��������"+xingxingong+"��ʧ�֣�Ϊӹҽ��"+xinw[1]);
		if(yiw[0].equals("1")) w("������"+yitpgong+"���Ͼ֣�Ϊ��ҽ��"+yiw[1]);
		else w("������"+yitpgong+"��ʧ�֣�Ϊӹҽ��"+yiw[1]);
		
		if(p.isGongke(xingxingong, xingruigong)) w("������ҽ��ӹҽ������������"+xingxingong+"���ܿ����ǲ���֮"+xingruigong+"���ߣ�ҽ���й���");
		if(p.isGongke(yitpgong, xingruigong)) w("������ҽ��ӹҽ����������"+yitpgong+"���ܿ����ǲ���֮"+xingruigong+"���ߣ�ҽ���й���");
		if(p.isGongke(xingruigong,xingxingong)) w("���ǲ���֮"+xingruigong+"��������������"+xingxingong+"��������ҽ�಻����Ҳ��");
		if(p.isGongke(xingruigong,yitpgong)) w("���ǲ���֮"+xingruigong+"������������"+yitpgong+"��������ҽ�಻����Ҳ��");
		
		if(p.isTaohua(yitpgong)) w("��������"+yitpgong+"������ԡ�ܵ�Ҳ�������ò���");
		if(p.isTGanMu(YiJing.YI,0)) w("����Ĺ��ҽ��ҽ�����У�");
		if(yitpgong==shendigong) w("���پŵ�����ҽ�����伸��������ꣻ");
		if(yitpgong==xingfugong) w("��Ϊ����ҽ�����츨������Ϊ���ڣ�");
		if(yitpgong==shentiangong) w("���پ���Ҳ������������");
		if(yitpgong==shenshegong || yitpgong==simengong) w("����Ο������Ҳ�����š��񺺡��ɹ�֮�ࡣ");
		if(yitpgong==dumengong || yitpgong==shenhugong) w("�����ٶ��š��׻�����ҽ��һ����������Ӳ��");
		if(yitpgong==shenyingong) w("����̫������ҽ��ϸ�壬��ϸ���档");
		if(yitpgong==dingtpgong || yitpgong==kaimengong) w("���ٿ��š��������������������ơ�");
		
		w("������Ϊ��ҩ������Ϊ��ҩ��");
		if(xingxingong==kaimengong || xingxingong==xiumengong || xingxingong==shengmengong) w("�����ٿ����ݡ�����������ҩ������֮��");
		else w("���������������ˡ���������ҩ�����룻");
		if(yitpgong==kaimengong || yitpgong==xiumengong || yitpgong==shengmengong) w("���ٿ����ݡ�����������ҩ������֮��");
		else w("�������������ˡ�������ҩ�����룻");
		if(p.isKong(yitpgong)) w("�ҿ�������ҽ������ҩ�������ã�");
		if(p.isKong(xingxingong)) w("���Ŀ���������ҩ�������ã�");
		if(yitpgong==zhifugong || xingxingong==zhifugong) w("���Ļ�����ֵ������Ϊ�߼�ҩƷ����ҽ��Ժ��");
		if(yitpgong==zhishigong || xingxingong==zhishigong) w("���Ļ�����ֵʹ����Ϊ�߼�ҩƷ����ҽ��Ժ��");
		if(yitpgong==shenshegong || xingxingong==shenshegong || yitpgong==shenwugong || xingxingong==shenwugong)
			w("���Ļ�����Ο�߻������Ƕ���ٲ�ʵ����ҽ��ҩ����թ�ϵ���ƭ��");
	}
	//����
	public void getBing24() {
		if(p.isMenFu() || p.isXingFu()) w("������Ϊ�ò������Բ����ò�ʱ��ϳ�����ʱ������������");
		if(p.isMenFan() || p.isXingFan()) w("���������鷴����Ҳ��̥��ʵ�����������������ò����ף��ִ����Բ���");
		
		if(p.isGongke(ystpgong, xingruigong)) w("������"+ystpgong+"�����ǹ���Ϊ�˿˲������鷢չ��������������乬���࣬���м�����Ǽ�������ѡ�");
		else if(p.isChongke(xingruigong, ystpgong)) w("�����ǳ����������"+ystpgong+"�������ס�");
		if(p.isganHeju(SiZhu.sg,SiZhu.sz)[0].equals("1")) w("ʱ����"+shitpgong+"���Ͼ֣�����");
		else w("ʱ����"+shitpgong+"��ʧ�֣��ף�");
		if(p.isganHeju(ysgan,yszi)[0].equals("1")) w("������"+ystpgong+"���Ͼ֣�����");
		else w("������"+ystpgong+"��ʧ�֣��ף�");
		if(p.getmenWS(simengong)[0].equals("-1") || (simengong==3 || simengong==4)) w("������"+simengong+"�����������ƣ�����");
		
		if(p.isTGanMu(ysgan,yszi)) w("�����乬��Ĺ���ף�Ҳ��ʾסԺ��������ΪҽԺ���ո��󣩣�Ҳ��ʾ�ڼ���Ϣ�����ݣ�");
		if(p.isDGanMu(ysgan,yszi)) w("������̸���"+ysdpgong+"����Ĺ���ף�Ҳ��ʾסԺ��������ΪҽԺ���ո��󣩣�Ҳ��ʾ�ڼ���Ϣ�����ݣ�");		
		if(p.isKong(ysgan,yszi)) w("�������������²�������������ò���������������Ҳ��û�м�����");
		
		if(p.getGongWS(xingruigong)[0].equals("1")) w("�����乬���������࣬���أ�");
		else w("�����乬�������������������ƣ����鲻�Ǻ����ء�");
		
		if(p.isGongke2(ystpgong, niantpgong) || p.isGongke2(ystpgong, QiMen.ziOfGua[SiZhu.nz])) w("����̫����ɻ��֧�����ˣ����ֿ�̫�꣬�����֣�");
		if(ystpgong==kaimengong) w("�����ٿ�����������Ҳ��������ȷ�����س�����");
		if(ystpgong==simengong || ysdpgong==simengong) w("���������̻���̷����ţ����ף����������");
		
		if(xingruigong==8 || xingruigong==4 || xingruigong==2 || xingruigong==6) w("��������ά������ʱ�䳤");
		if(xingruigong==zhifugong) w("��������ֵ��������̫������");
		
		if(xingruigong==xingxingong || xingruigong==yitpgong) w("�����ǡ�����������ͬ����˵������ҩ��ά�֣�Ҳ��ʾ���Ƽ�ʱ��");
		if(xingruigong==yitpgong && yitpgong==gengdpgong) w("�����ǹ����Ҹ��ϣ������ɹ���");
		if(ystpgong==xingxingong || ystpgong==yitpgong) w("���������Ļ����棬�в�Ҳ���Ƽ�ʱ��ͬʱ�������ҩ��");
		if(xingruigong==shengmengong) w("���ǵ�����������");
		if(xingruigong==simengong) w("���ǵ�����������");
		
		if(xingruigong==6 || xingruigong==7) w("��������������Ǭ���Ҷ����������𣬲����Ρ�");
		if(xingruigong==9 || xingruigong==5 || xingruigong==2 || xingruigong==8) w("���������������빬����������������塢�����޹�ҲΪ�����䲡����������");
		if(xingruigong==1) w("�������������俲�����������䲻�ܺܿ�ͺã����չ����Ȭ����");
		if(xingruigong==3 || xingruigong==4) w("��������������ܹ��ˣ���ҩ������");
		
		if(xingruigong==yitpgong && yitpgong==xindpgong) w("���ǲ�������+���������ߣ�˵�����񼴽���ȥ�����鲻��Ѹ�ٷ�չ��");
		if(xingruigong==bingtpgong && bingtpgong==gengdpgong) w("���ǲ����ٱ�+������ȥ��˵�����񼴽���ȥ�����鲻��Ѹ�ٷ�չ��");
		if(xingruigong==xintpgong && xintpgong==yidpgong) w("���ǲ�������+�һ��������ټ��񣬵��в�����֮��");
		if(xingruigong==bingtpgong && bingtpgong==wudpgong) w("���ǲ����ٱ�+�����Ѩ�����ټ��񣬵��в�����֮��");
		if(xingruigong==wutpgong && wutpgong==bingdpgong) w("���ǲ�������+�������ף����ټ��񣬵��в�����֮��");
		if(xingruigong==xintpgong && xintpgong==yidpgong) w("���ǲ�������+�һ�������˵�����齫���ء�");
		if(xingruigong==guitpgong && guitpgong==dingdpgong) w("���ǲ����ٹ�+����ز�ã���˵�����齫���ء�");
		if(xingruigong==xintpgong && xintpgong==gengdpgong) w("���ǲ�������+���׻���������˵�����齫���ء�");
		if(xingruigong==bingtpgong && bingtpgong==guidpgong) w("���ǲ����ٱ�+�����˺��£���˵�����齫���ء�");
		if(xingruigong==gengtpgong && gengtpgong==bingdpgong) w("���ǲ����ٸ�+������������˵�����齫���ء�");
		if(xingruigong==wutpgong && wutpgong==dingdpgong) w("���ǲ�������+������ת�⣬��Ԥʾ���鼴����ת��");
		if(xingruigong==dingtpgong && dingtpgong==bingdpgong) w("���ǲ����ٶ�+��������ת����Ԥʾ���鼴����ת��");
		if(xingruigong==xintpgong && xintpgong==dingdpgong) w("���ǲ�������+��������棬��Ԥʾ���鼴����ת��");
		if(xingruigong==wutpgong && wutpgong==gengdpgong) w("���ǲ�������+�����򼲲�Ҫ��ɢ��Ҫת��������ļ��������軻ҽԺҽ�Σ�");
		if(xingruigong==gengtpgong && gengtpgong==wudpgong) w("���ǲ����ٸ�+�죬�򼲲�Ҫ��ɢ��Ҫת��������ļ��������軻ҽԺҽ�Σ�");
		if(xingruigong==gengtpgong && gengtpgong==jidpgong) w("���ǲ����ٸ�+���̸�Ҳ����������");
		if(xingruigong==wutpgong && wutpgong==rendpgong) w("���ǲ�������+�����������Σ������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==yitpgong && yitpgong==jidpgong) w("���ǲ�������+��������Ĺ�������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==yitpgong && yitpgong==rendpgong) w("���ǲ�������+��������أ������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==dingtpgong && dingtpgong==xindpgong) w("���ǲ����ٶ�+����ȸ�����������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==jitpgong && jitpgong==jidpgong) w("���ǲ����ټ�+���ػ���������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==jitpgong && jitpgong==xindpgong) w("���ǲ����ټ�+���λ���Ĺ�������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==jitpgong && jitpgong==rendpgong) w("���ǲ����ټ�+�ɵ������ţ������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==jitpgong && jitpgong==guidpgong) w("���ǲ����ټ�+��������䣬�����������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==xintpgong && xintpgong==wudpgong) w("���ǲ�������+���������ˣ������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==xintpgong && xintpgong==guidpgong) w("���ǲ�������+�����λ��ǣ������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==rentpgong && rentpgong==xindpgong) w("���ǲ�������+��Ο������������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==rentpgong && rentpgong==rendpgong) w("���ǲ�������+��������ޣ������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==guitpgong && guitpgong==gengdpgong) w("���ǲ����ٹ�+��̫�������������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==guitpgong && guitpgong==xindpgong) w("���ǲ����ٹ�+���������Σ������������಻ȥ��������أ��ò�����֮��");
		if(xingruigong==jitpgong && jitpgong==xindpgong) w("���ǲ����ټ�+������Ԥʾ�ɲ���ȥ���²�������");
		if(xingruigong==jitpgong && jitpgong==jidpgong) w("���ǲ����ټ�+������Ԥʾ�ɲ���ȥ���²�������");
		if(xingruigong==yitpgong && yitpgong==bingdpgong) w("���ǲ�������+������˳�죬��˵�����������÷���չ��");
		if(xingruigong==yitpgong && yitpgong==dingdpgong) w("���ǲ�������+��������������˵�����������÷���չ��");
		if(xingruigong==bingtpgong && bingtpgong==yidpgong) w("���ǲ����ٱ�+�����²��У���˵�����������÷���չ��");
		if(xingruigong==dingtpgong && dingtpgong==dingdpgong) w("���ǲ����ٶ�+��������̫������˵�����������÷���չ��");
		if(xingruigong==gengtpgong && gengtpgong==yidpgong) w("���ǲ����ٸ�+��̫�׷��ǣ���˵�����������÷���չ��");
		if(xingruigong==rentpgong && rentpgong==yidpgong) w("���ǲ�������+��С�ߵ��ƣ���˵�����������÷���չ��");
		if(xingruigong==jitpgong && jitpgong==wudpgong) w("���ǲ����ټ�+��Ȯ�������������˰�����������ʦ��ҩ֮��");
		if(xingruigong==rentpgong && rentpgong==wudpgong) w("���ǲ�������+��С�߻����������˰�����������ʦ��ҩ֮��");
		if(xingruigong==xintpgong && xintpgong==dingdpgong) w("���ǲ�������+��������棬�����˰�����������ʦ��ҩ֮��");
		if(xingruigong==dingtpgong && dingtpgong==guidpgong) w("���ǲ����ٶ�+����ȸͶ��������");
		if(xingruigong==guitpgong && guitpgong==dingdpgong) w("���ǲ����ٹ�+����ز�ã��׼����֮��");
		if(xingruigong==guitpgong && guitpgong==guidpgong) w("���ǲ����ٹ�+���������ţ����������ߣ�����Σ��");
		if(xingruigong==guitpgong && guitpgong==xindpgong) w("���ǲ����ٹ�+���������Σ����������ߣ�����Σ��");
		
		if(xingruigong==gengtpgong && gengtpgong==rendpgong) w("���ǲ����ٸ�+��С��Ϊ���鲻�ȶ������ƶ���С�㲻����");
		if(xingruigong==gengtpgong && gengtpgong==guidpgong) w("���ǲ����ٸ�+����Ϊ���鲻�ȶ������ƶ���С�㲻����");
		
		int gejuNum = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];
		if(gejuNum==2) w("�岻��ʱ�˽��š�");
				
		if(p.isJixing(xingruigong)) {
			if(xingruigong==4 && (rentpgong==4 || guitpgong==4)) w("�����乬�����ǻ��������ס��ر��Ǽ׳��������Ĺ��������������Ĺ������˲�֫�塢�Ƚ�֮�֡�");
			else if(xingruigong==8 && gengtpgong==8) w("�����乬�����ǻ��������ס���������ް˹���������������֡�");
			else if((xingruigong==3 && wutpgong==3) || 
					(xingruigong==9 && xintpgong==9) || 
					(xingruigong==2 && jitpgong==2)) w("�����乬�����ǻ��������ס������𹬣������빬����������һ������ʹ֮�֡�");
			else if(xingruigong==gengtpgong && gengtpgong==guidpgong) w("�����乬�����ǻ��������ס���+�����㹬��һ��������ֲ�ʹ��");
			else w("�����乬���ǻ��̣������ס�");
		}

		int[] tgan = p.getTpjy(xingruigong);
		int[] dgan = p.getDpjy(xingruigong);
		boolean isyi = tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI ||dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI; 
		boolean isbing = tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING ||dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING;
		boolean isding = tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING ||dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING;
		boolean isren = tgan[0]==YiJing.REN ||tgan[1]==YiJing.REN ||dgan[0]==YiJing.REN ||dgan[1]==YiJing.REN;
		boolean isji = tgan[0]==YiJing.JI ||tgan[1]==YiJing.JI ||dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI;
		boolean isgui = tgan[0]==YiJing.GUI ||tgan[1]==YiJing.GUI ||dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI;
		//��թ
		boolean is3za = false;
		if((kaimengong==xingruigong || xiumengong==xingruigong || shengmengong==xingruigong) && 
				(isyi || isbing || isding) && (shenyingong==xingruigong)) {
			is3za = true;
		}else 
		if((kaimengong==xingruigong || xiumengong==xingruigong || shengmengong==xingruigong) && 
				(isyi || isbing || isding) && (shenhegong==xingruigong)) {
			is3za = true;
		}else
		if((kaimengong==xingruigong || xiumengong==xingruigong || shengmengong==xingruigong) && 
				(isyi || isbing || isding) && (shendigong==xingruigong)) {
			is3za = true;
		}
		if(is3za) w("�����乬����թ�����鷴��������");
		
		//���
		boolean is5jia = false;
		if(jing3mengong==xingruigong && (isyi || isbing || isding) && shentiangong==xingruigong) {
			is5jia = true;
		}else 
		if(dumengong==xingruigong && (isgui || isji || isding) && (shenyingong==xingruigong || shendigong==xingruigong || shenhegong==xingruigong)) {
			is5jia = true;
		}else 
		if(jing1mengong==xingruigong && isren && shentiangong==xingruigong) {
			is5jia = true;
		}else 
		if(shangmengong==xingruigong && (isgui || isji || isding) && (shendigong==xingruigong || shenhegong==xingruigong)) {
			is5jia = true;
		}else 
		if(simengong==xingruigong && (isgui || isji || isding) && shendigong==xingruigong ) {
			is5jia = true;
		}
		if(is5jia) w("���Ƿ����Ҳ�Ǽ��ס�");
		
		//�Ŷ�
		boolean is9dun = false;
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && xiumengong==xingruigong && shenyingong==xingruigong) {
			is9dun = true;
		}else 
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && dumengong==xingruigong && shendigong==xingruigong) {
			is9dun = true;
		}else 
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && kaimengong==xingruigong && (dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI)) {
			is9dun = true;
		}
		if(is9dun) w("���Ƿ�ض����˱����������,�˶�Ҳ�Ǽ��ס�");
		
		boolean is3 = (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI || dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN);
		is3 = is3 || ((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG));
		is3 = is3 || ((tgan[0]==YiJing.DING || tgan[1]==YiJing.DING) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN || dgan[0]==YiJing.GUI || dgan[1]==YiJing.GUI));
		if(is3) {
			w("���Ƿ������ʹ���Բ����������ر��Ƕ���Ϊ���֮��");
		}
		
		if(zhishigong==xingruigong && (dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			w("���Ƿ���Ů���ţ��ò�����������");
		}
		
		if(((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.BING || dgan[1]==YiJing.BING)) ||
		 	((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && 
				(dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || 
						dgan[1]==SiZhu.ng ||dgan[1]==SiZhu.yg ||dgan[1]==SiZhu.rg ||dgan[1]==SiZhu.sg))) {
			w("���Ƿ�㣸�֫�����˺�����������ʱ��㣸����¾ɲ����Ρ�");
		}
		
		if((tgan[0]==SiZhu.rg || tgan[1]==SiZhu.rg) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			w("�����ٷɸɸ���ϲ�����ҩ����֢��");
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.rg || dgan[1]==SiZhu.rg)) {
			w("�����ٷ��ɸ���ϲ�����ҩ����֢��");
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) {
			w("�����ٷ����񣬲��˲��������˾��֡�");
		}
		if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			w("�����ٷɹ��񣬲��˲��������˾��֡�");
		}
		
	}
	//Ӧ��
	public void getBing25() {
		if(p.isNenpan(xingruigong)) w("���������̿죬��������������������");
		w("���ҪȥҽԺ���������̶�Զ������λ�����Ǹ����ǹ��ķ���");
		if(p.isTGanMu(SiZhu.ng) && ( xingruigong==niantpgong || p.isGongChong(xingruigong, niantpgong)))
			w("̫����Ĺ֮�꣬�ֳ嶯�ǹ������ǹ�������������");
		if(p.isGongChong(niantpgong, ritpgong) && p.isGongke2(QiMen.ziOfGua[SiZhu.nz], QiMen.ziOfGua[SiZhu.rz]))
			w("���������ؿ�֮�꣬����������");
		w("�������乬��ʮ����ʲô���˴˸�֮����Ϊ����֮�ڡ�");
		w("������Ĺ֮���£��󲡱�����");
		w("ֵʹ�乬�ɶϲ����������������ٸ�Ҳ�ɶϾ������ڣ�");
		if(p.isNenpan(shitpgong) && p.isNenpan(ritpgong)) w("����������ʱͬΪ���̣����졣��Ϊҽ�������乬֮�ն�������");
		else w("����������ʱͬΪ���̻�һ��һ�⣬��������Ϊҽ�������乬֮�ն�������");
		w("��ΪסԺ�ѣ��伸��������������ȡ����");
	}	
	//����
	public void getBing26() {
		if(mzhu[0]==0) return;
		
		w("������"+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"��");
		if(p.isTGanMu(mzhu[0],mzhu[1])) w("��������乬��Ĺ���ף�Ҳ��ʾסԺ��������ΪҽԺ���ո��󣩣�Ҳ��ʾ�ڼ���Ϣ�����ݣ�");
		if(p.isKong(mzhu[0],mzhu[1])) w("�����乬�������²�������������ò���������������Ҳ��û�м�����");
		
		if(p.isganHeju(mzhu[0],mzhu[1])[0].equals("1")) w("������"+mtpgong+"���Ͼ֣�����");
		else w("������"+mtpgong+"��ʧ�֣��ף�");		
		if(p.isGongke(mtpgong, xingruigong)) w("������"+mtpgong+"�����ǹ���Ϊ�˿˲������鷢չ���������������������ã�û�м���������乬���࣬���м�����Ǽ�������ѡ�");
		else if(p.isChongke(xingruigong, mtpgong) && mtpgong!=0) w("�����ǳ����������"+mtpgong+"�������ס�");
		
		w("�����乬�������������µ�������˥��");
		if(mtpgong==zhifugong && p.isKong(mtpgong)) w("������ֵ����գ�����������");
		if(mtpgong==kaimengong) w("�����ٿ�����������Ҳ��������ȷ�����س�����");
		if(mtpgong==simengong || mdpgong==simengong) w("���������̻���̷����ţ����ף����������");
		
		//������������֧��Ĺ��֮�·ݴ��ף�
		String smj = "";
		for(int i=1; i<13; i++) {
			if(SiZhu.TGSWSJ[mzhu[0]][i]==8 || SiZhu.TGSWSJ[mzhu[0]][i]==9 || SiZhu.TGSWSJ[mzhu[0]][i]==10)
				smj += YiJing.DIZINAME[i];
		}
		w("������ɣ�"+YiJing.TIANGANNAME[mzhu[0]]+"���ڣ�"+smj+"��Ϊ��Ĺ��֮���£����ף�");
	}
	//����
	public void getBing27() {
		if(p.isTGanMu(SiZhu.sg,SiZhu.sz)) w("ռ��Ů��ʱ����Ĺ������");
		if(!boy && p.isKong(gengtpgong)) w("Ů�⣬��Ϊ�򣬿�����ʾ���ڼң�");
		if(!boy && (gengtpgong==wudpgong || gengdpgong==wutpgong)) w("Ů�⣬��Ϊ������+�����+����ʾ�����̣�����ʾ���ɷ���ҳ����ˣ�");
	}
}