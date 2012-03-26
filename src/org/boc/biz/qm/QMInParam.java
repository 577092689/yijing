package org.boc.biz.qm;

import java.util.HashMap;
import java.util.Map;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QimenPublic;

	/**
	 * ����������������ʼ��
	 * 1. ʵ����ʱ�ͳ�ʼ��������ֻ����һ��
	 * 2. ÿ�λ��֣��������¼��ر���
	 */
public class QMInParam {
	private QimenPublic pub;
	private DaoQiMen daoqm;
	private QMFunction function;
	private static Map<String,Object> params = new HashMap<String,Object>();
	public QMInParam(QimenPublic p, QMFunction function) {
		if(p==null) return;
		pub = p;
		daoqm = pub.getDaoQiMen();
		this.function = function;
		initConstant();
	}
	
	public QMFunction getFunction() {
		return function;
	}
	
	/**
	 * һ��������ֻ��Ҫ��ʼ��һ�Σ�int
	 * 1. ����=-1������=1��������=0|-2
	 * 2. �Ͼ�=1��ʧ��=-1
	 * 3. ������=1����������=-1
	 * 4. ��=-1����=0����=1
	 * 5. �ס��ҡ����������졢�������������ɡ���
	 * 6. �ӡ�������î�������ȡ��硢δ���ꡢ�ϡ��硢��
	 * 7. ����,����,��,�㹬,�й�,Ǭ��,�ҹ�,�޹�,�빬 
	 * 8. ���š����š����š����š����š����š����š����š�����
	 * 9. ֵ�������ߡ�̫�������ϡ��׻������䡢�ŵء�����
	 * 10. �����ǡ������ǡ�����ǡ��츨�ǡ������ǡ������ǡ������ǡ������ǡ���Ӣ��
	 */
	private void initConstant() {
		params.put("this", function);
		
		params.put("����",-1);
		params.put("����",1);
		params.put("������",0);
		params.put("�Ͼ�",1);
		params.put("ʧ��",-1);
		params.put("������",1);
		params.put("��������",-1);
		params.put("��",-1);
		params.put("��",0);  //�е���0�ף���������жϼ�����Ҫ�ж���
		params.put("��",1);		
		
		params.put("��", YiJing.JIA);
		params.put("��", YiJing.YI);
		params.put("��", YiJing.BING);
		params.put("��", YiJing.DING);
		params.put("��", YiJing.WUG);
		params.put("��", YiJing.JI);
		params.put("��", YiJing.GENG);
		params.put("��", YiJing.XIN);
		params.put("��", YiJing.REN);
		params.put("��", YiJing.GUI);
		
		params.put("��", YiJing.ZI);
		params.put("��", YiJing.CHOU);
		params.put("��", YiJing.YIN);
		params.put("î", YiJing.MAO);
		params.put("��", YiJing.CHEN);
		params.put("��", YiJing.SI);
		params.put("��", YiJing.WUZ);
		params.put("δ", YiJing.WEI);
		params.put("��", YiJing.SHEN);
		params.put("��", YiJing.YOU);
		params.put("��", YiJing.XU);
		params.put("��", YiJing.HAI);
		
		params.put("����", 1);
		params.put("����", 2);
		params.put("��", 3);
		params.put("�㹬", 4);
		params.put("�й�", 5);
		params.put("Ǭ��", 6);
		params.put("�ҹ�", 7);
		params.put("�޹�", 8);
		params.put("�빬", 9);
		
		params.put("����", QiMen.MENXIU);
		params.put("����", QiMen.MENSI);
		params.put("����", QiMen.MENSHANG);
		params.put("����", QiMen.MENDU);
		params.put("����", QiMen.MENZHONG);
		params.put("����", QiMen.MENKAI);
		params.put("����", QiMen.MENJING1);
		params.put("����", QiMen.MENSHENG);
		params.put("����", QiMen.MENJING3);
		
		params.put("ֵ��", QiMen.SHENFU);
		params.put("����", QiMen.SHENSHE);
		params.put("̫��", QiMen.SHENYIN);
		params.put("����", QiMen.SHENHE);
		params.put("�׻�", QiMen.SHENHU);
		params.put("����", QiMen.SHENWU);
		params.put("�ŵ�", QiMen.SHENDI);
		params.put("����", QiMen.SHENTIAN);
		
		params.put("������", QiMen.XINGPENG);
		params.put("������", QiMen.XINGRUI);
		params.put("�����", QiMen.XINGCHONG);
		params.put("�츨��", QiMen.XINGFU);
		params.put("������", QiMen.XINGQIN);
		params.put("������", QiMen.XINGXIN);
		params.put("������", QiMen.XINGZHU);
		params.put("������", QiMen.XINGREN);
		params.put("��Ӣ��", QiMen.XINGYING);
	}
	
	/**
	 * �����жϣ�boolean
	 * 1. Ǭ�죬����
	 * 2. ���ݣ�����
	 * 3. ���ŷ��ʡ����ŷ��ʡ����Ƿ��ʡ����Ƿ���
	 */
	private void initVar1(Map<String,Object> params) {
		params.put("Ǭ��", pub.isBoy());
		params.put("����", !pub.isBoy());
		params.put("����", daoqm.getJu()>0);
		params.put("����", daoqm.getJu()<0);
		params.put("���ŷ���",pub.isMenFu());
		params.put("���ŷ���",pub.isMenFan());
		params.put("���Ƿ���",pub.isXingFu());
		params.put("���Ƿ���",pub.isXingFan());
		params.put("���ŷ���",pub.isMenFu());
		params.put("���ŷ���",pub.isMenFan());
		params.put("���Ƿ���",pub.isXingFu());
		params.put("���Ƿ���",pub.isXingFan());
	}
	
	/**
	 * �����乬��int
	 * 1. ��ɡ��¸ɡ��ոɡ�ʱ������|�����乬
	 * 2. ֵʹ�乬
	 * 3. ���������乬�������칬������������乬������ع��������������乬�����������乬
	 * 4. �������乬|����������乬|���乬��...  === ���̸��乬�������������乬�������������ء�
	 * 5. �������乬|����������乬��...  			=== ���̸��乬�����������������乬��
	 * 6. �����乬|���Ź��������乬��...   		=== �����乬
	 * 7. �����乬|�������乬��...  				=== �����乬�����������֡����乬������java������ʽ
	 * 9. �����乬���ŵ��乬��...						=== �����乬
	 */ 
	private void initVar2(Map<String,Object> params) {
		params.put("����乬", pub.getTianGong(SiZhu.ng, SiZhu.nz));
		params.put("��ɵ����乬", pub.getDiGong(SiZhu.ng, SiZhu.nz));
		params.put("�¸��乬", pub.getTianGong(SiZhu.yg, SiZhu.yz));
		params.put("�¸ɵ����乬", pub.getDiGong(SiZhu.yg, SiZhu.yz));
		params.put("�ո��乬", pub.getTianGong(SiZhu.rg, SiZhu.rz));
		params.put("�ոɵ����乬", pub.getDiGong(SiZhu.rg, SiZhu.rz));
		params.put("ʱ���乬", pub.getTianGong(SiZhu.sg, SiZhu.sz));
		params.put("ʱ�ɵ����乬", pub.getDiGong(SiZhu.sg, SiZhu.sz));		
		
		params.put("ֵʹ�乬", daoqm.getZhishiGong());
		
		params.put("�������̸��乬", pub.getYSTgong());
		params.put("������̸��乬", pub.getYSDgong());
		params.put("�������̸��乬", pub.getMingTgong());
		params.put("�������̸��乬", pub.getMingDgong());		
		
		params.put("���乬", pub.getTianGong(YiJing.YI, 0));
		params.put("���乬", pub.getTianGong(YiJing.BING, 0));
		params.put("���乬", pub.getTianGong(YiJing.DING, 0));
		params.put("���乬", pub.getTianGong(YiJing.WUG, 0));
		params.put("���乬", pub.getTianGong(YiJing.JI, 0));
		params.put("���乬", pub.getTianGong(YiJing.GENG, 0));
		params.put("���乬", pub.getTianGong(YiJing.XIN, 0));
		params.put("���乬", pub.getTianGong(YiJing.REN, 0));
		params.put("���乬", pub.getTianGong(YiJing.GUI, 0));
		
		
		params.put("�������乬", pub.getDiGong(YiJing.YI, 0));
		params.put("���̱��乬", pub.getDiGong(YiJing.BING, 0));
		params.put("���̶��乬", pub.getDiGong(YiJing.DING, 0));
		params.put("�������乬", pub.getDiGong(YiJing.WUG, 0));
		params.put("���̼��乬", pub.getDiGong(YiJing.JI, 0));
		params.put("���̸��乬", pub.getDiGong(YiJing.GENG, 0));
		params.put("�������乬", pub.getDiGong(YiJing.XIN, 0));
		params.put("�������乬", pub.getDiGong(YiJing.REN, 0));
		params.put("���̹��乬", pub.getDiGong(YiJing.GUI, 0));
		
		params.put("�����乬", pub.getMenGong(QiMen.MENXIU));
		params.put("�����乬", pub.getMenGong(QiMen.MENSHENG));
		params.put("�����乬", pub.getMenGong(QiMen.MENSHANG));
		params.put("�����乬", pub.getMenGong(QiMen.MENDU));
		params.put("�����乬", pub.getMenGong(QiMen.MENJING3));
		params.put("�����乬", pub.getMenGong(QiMen.MENSI));
		params.put("�����乬", pub.getMenGong(QiMen.MENJING1));
		params.put("�����乬", pub.getMenGong(QiMen.MENKAI));
		
		params.put("�������乬", pub.getXingGong(QiMen.XINGPENG));
		params.put("�������乬", pub.getXingGong(QiMen.XINGRUI));
		params.put("������乬", pub.getXingGong(QiMen.XINGCHONG));
		params.put("�츨���乬", pub.getXingGong(QiMen.XINGFU));
		params.put("�������乬", pub.getXingGong(QiMen.XINGQIN));
		params.put("�������乬", pub.getXingGong(QiMen.XINGXIN));
		params.put("�������乬", pub.getXingGong(QiMen.XINGZHU));
		params.put("�������乬", pub.getXingGong(QiMen.XINGREN));
		params.put("��Ӣ���乬", pub.getXingGong(QiMen.XINGYING));
		
		params.put("ֵ���乬", pub.getShenGong(QiMen.SHENFU));
		params.put("�����乬", pub.getShenGong(QiMen.SHENSHE));
		params.put("̫���乬", pub.getShenGong(QiMen.SHENYIN));
		params.put("�����乬", pub.getShenGong(QiMen.SHENHE));
		params.put("�׻��乬", pub.getShenGong(QiMen.SHENHU));
		params.put("�����乬", pub.getShenGong(QiMen.SHENWU));
		params.put("�ŵ��乬", pub.getShenGong(QiMen.SHENDI));
		params.put("�����乬", pub.getShenGong(QiMen.SHENTIAN));
	}
	
	/** 
	 * �ġ�����������int
	 * 1. ������ɡ������֧��������ɡ�������֧
	 * 2. ��ɡ���֧���¸ɡ���֧���ոɡ���֧��ʱ�ɡ�ʱ֧
	 */	
	private void initVar3(Map<String,Object> params) {		
		params.put("�������", pub.getYSTiangan());
		params.put("�����֧", pub.getYSDizi());
		params.put("�������", pub.getMingtg());
		params.put("������֧", pub.getMingdz());
		
		params.put("���", SiZhu.ng);
		params.put("��֧", SiZhu.nz);
		params.put("�¸�", SiZhu.yg);
		params.put("��֧", SiZhu.yz);
		params.put("�ո�", SiZhu.rg);
		params.put("��֧", SiZhu.rz);
		params.put("ʱ��", SiZhu.sg);
		params.put("ʱ֧", SiZhu.sz);
	}
	
	/*
	 * �塢״̬�жϣ�int
	 * 1. ���������˥���ո���˥�� ʱ����˥��ֵʹ�����˥   				=== ���ࡢ������������
	 * 2. ���񹬾�����˥���չ�������˥��ʱ��������˥��ֵʹ��������˥
	 * 3. ���񹬰�����˥���չ�������˥��ʱ��������˥��ֵʹ��������˥
	 * 4. �������״̬���չ����״̬��ʱ�����״̬��ֵʹ�����״̬		=== �Ͼ֡�ʧ��
	 * 5. ���񹬰���״̬���չ�����״̬��ʱ������״̬��ֵʹ������״̬
	 * 6. ���񹬾���״̬���չ�����״̬��ʱ������״̬��ֵʹ�����Ǿ���
	 * 7. ���񹬰���״̬���չ�����״̬��ʱ������״̬��ֵʹ���������  							
	 * 8. ���񹬵��桢�չ����桢ʱ�����桢ֵʹ������ 					=== �����桢��������
	 * 9. ���񹬰��ż��ס��չ����ż��ס�ʱ�����ż��ס�ֵʹ�����ż���		=== �����ס���
	 * 10. ���񹬰����ס��չ������ס�ʱ�������ס�ֵʹ��������
	 * 11. ���񹬾��Ǽ��ס��չ����Ǽ��ס�ʱ�����Ǽ��ס�ֵʹ�����Ǽ���
	 * 12. ���񹬸�ּ��ס��չ���ּ��ס�ʱ����ּ��ס�ֵʹ����ּ���
	 */
	private void initVar4(Map<String,Object> params) {
		int zhishigong = daoqm.getZhishiGong();
		zhishigong = zhishigong==5 ? pub.getJigong() : zhishigong;
		
		params.put("���������˥", Integer.valueOf(pub.gettgWS(pub.getYSTgong())[0]));
		params.put("�ո���˥", Integer.valueOf(pub.gettgWS(SiZhu.rg,SiZhu.rz)[0]));
		params.put("ʱ����˥", Integer.valueOf(pub.gettgWS(SiZhu.sg,SiZhu.sz)[0]));
		params.put("ֵʹ�����˥", Integer.valueOf(pub.gettgWS(zhishigong)[0]));
		
		params.put("���񹬾�����˥", Integer.valueOf(pub.getxingWS(pub.getYSTgong())[0]));
		params.put("�չ�������˥", Integer.valueOf(pub.getxingWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ��������˥", Integer.valueOf(pub.getxingWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ��������˥", Integer.valueOf(pub.getxingWS(zhishigong)[0]));
		
		params.put("���񹬰�����˥", Integer.valueOf(pub.getmenWS(pub.getYSTgong())[0]));
		params.put("�չ�������˥", Integer.valueOf(pub.getmenWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ��������˥", Integer.valueOf(pub.getmenWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ��������˥", Integer.valueOf(pub.getmenWS(zhishigong)[0]));
		
		params.put("�������״̬", Integer.valueOf(pub.isganHeju(pub.getYSTgong())[0]));
		params.put("�չ����״̬", Integer.valueOf(pub.isganHeju(SiZhu.rg, SiZhu.rz)[0]));
		params.put("ʱ�����״̬", Integer.valueOf(pub.isganHeju(SiZhu.sg, SiZhu.sz)[0]));
		params.put("ֵʹ�����״̬", Integer.valueOf(pub.isganHeju(zhishigong)[0]));
		
		params.put("���񹬾���״̬", Integer.valueOf(pub.isxingHeju(pub.getYSTgong())[0]));
		params.put("�չ�����״̬", Integer.valueOf(pub.isxingHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ������״̬", Integer.valueOf(pub.isxingHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ������״̬", Integer.valueOf(pub.isxingHeju(zhishigong)[0]));
		
		params.put("���񹬰���״̬", Integer.valueOf(pub.ismenHeju(pub.getYSTgong())[0]));
		params.put("�չ�����״̬", Integer.valueOf(pub.ismenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ������״̬", Integer.valueOf(pub.ismenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ������״̬", Integer.valueOf(pub.ismenHeju(zhishigong)[0]));
		
		params.put("���񹬰���״̬", Integer.valueOf(pub.isshenHeju(pub.getYSTgong())[0]));		
		params.put("�չ�����״̬", Integer.valueOf(pub.isshenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));		
		params.put("ʱ������״̬", Integer.valueOf(pub.isshenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ������״̬", Integer.valueOf(pub.isshenHeju(zhishigong)[0]));
		
		params.put("���񹬵���", Integer.valueOf(pub.getSanji(pub.getYSTgong())[0]));
		params.put("�չ�����", Integer.valueOf(pub.getSanji(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ������", Integer.valueOf(pub.getSanji(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ������", Integer.valueOf(pub.getSanji(zhishigong)[0]));
		
		params.put("���񹬰��ż���", Integer.valueOf(pub.getmenJX(pub.getYSTgong())[0]));
		params.put("�չ����ż���", Integer.valueOf(pub.getmenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ�����ż���", Integer.valueOf(pub.getmenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ�����ż���", Integer.valueOf(pub.getmenJX(zhishigong)[0]));
		
		params.put("���񹬰�����", Integer.valueOf(pub.getshenJX(pub.getYSTgong())[0]));
		params.put("�չ�������", Integer.valueOf(pub.getshenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ��������", Integer.valueOf(pub.getshenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ��������", Integer.valueOf(pub.getshenJX(zhishigong)[0]));
		
		params.put("���񹬾��Ǽ���", Integer.valueOf(pub.getxingJX(pub.getYSTgong())[0]));
		params.put("�չ����Ǽ���", Integer.valueOf(pub.getxingJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ�����Ǽ���", Integer.valueOf(pub.getxingJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("ֵʹ�����Ǽ���", Integer.valueOf(pub.getxingJX(zhishigong)[0]));
		
		params.put("���񹬸�ּ���", Integer.valueOf(pub.getJixiongge(pub.getYSTgong())[0]));
		params.put("�չ���ּ���", Integer.valueOf(pub.getJixiongge(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("ʱ����ּ���", Integer.valueOf(pub.getJixiongge(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));		
		params.put("ֵʹ����ּ���", Integer.valueOf(pub.getJixiongge(zhishigong)[0]));
	}
	
	/**
	 * �����ַ���������String
	 * 1. ���������˥�������ո���˥������ ʱ����˥������ֵʹ����˥����   					=== ���ࡢ������������
	 * 2. ���񹬾�����˥�������չ�������˥������ʱ��������˥������������
	 * 3. ���񹬰�����˥�������չ�������˥������ʱ��������˥����
	 * 4. �������״̬�������չ����״̬������ʱ�����״̬����		=== �Ͼ֡�ʧ��
	 * 5. ���񹬰���״̬�������չ�����״̬������ʱ������״̬������
	 * 6. ���񹬾���״̬�������չ�����״̬������ʱ������״̬����
	 * 7. ���񹬰���״̬�������չ�����״̬������ʱ������״̬ ���� 							
	 * 8. ���񹬵����������չ�����������ʱ���������� 						=== �����桢��������
	 * 9. ���񹬰��ż����������չ����ż���������ʱ�����ż�������		=== �����ס���
	 * 10. ���񹬰������������չ�������������ʱ������������
	 * 11. ���񹬾��Ǽ����������չ����Ǽ���������ʱ�����Ǽ�������
	 */
	private void initVar5(Map<String,Object> params) {
		int zhishigong = daoqm.getZhishiGong();
		zhishigong = zhishigong==5 ? pub.getJigong() : zhishigong;
		
		params.put("���������˥����", pub.gettgWS(pub.getYSTgong())[1]);
		params.put("�ո���˥����", pub.gettgWS(SiZhu.rg,SiZhu.rz)[1]);
		params.put("ʱ����˥����", pub.gettgWS(SiZhu.sg,SiZhu.sz)[1]);
		params.put("ֵʹ�����˥����", pub.gettgWS(zhishigong)[1]);
		
		params.put("���񹬾�����˥����", pub.getxingWS(pub.getYSTgong())[1]);
		params.put("�չ�������˥����", pub.getxingWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ��������˥����", pub.getxingWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ��������˥����", pub.getxingWS(zhishigong)[1]);
		
		params.put("���񹬰�����˥����", pub.getmenWS(pub.getYSTgong())[1]);
		params.put("�չ�������˥����", pub.getmenWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ��������˥����", pub.getmenWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ��������˥����", pub.getmenWS(zhishigong)[1]);
		
		params.put("�������״̬����", pub.isganHeju(pub.getYSTgong())[1]);
		params.put("�չ����״̬����", pub.isganHeju(SiZhu.rg, SiZhu.rz)[1]);
		params.put("ʱ�����״̬����", pub.isganHeju(SiZhu.sg, SiZhu.sz)[1]);
		params.put("ֵʹ�����״̬����", pub.isganHeju(zhishigong)[1]);
		
		params.put("���񹬾���״̬����", pub.isxingHeju(pub.getYSTgong())[1]);
		params.put("�չ�����״̬����", pub.isxingHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ������״̬����", pub.isxingHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ������״̬����", pub.isxingHeju(zhishigong)[1]);
		
		params.put("���񹬰���״̬����", pub.ismenHeju(pub.getYSTgong())[1]);
		params.put("�չ�����״̬����", pub.ismenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ������״̬����", pub.ismenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ������״̬����", pub.ismenHeju(zhishigong)[1]);
		
		params.put("���񹬰���״̬����", pub.isshenHeju(pub.getYSTgong())[1]);		
		params.put("�չ�����״̬����", pub.isshenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);		
		params.put("ʱ������״̬����", pub.isshenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ��������˥����", pub.getxingWS(zhishigong)[1]);
		
		params.put("���񹬵�������", pub.getSanji(pub.getYSTgong())[1]);
		params.put("�չ���������", pub.getSanji(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ����������", pub.getSanji(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ����������", pub.getSanji(zhishigong)[1]);
		
		params.put("���񹬰��ż�������", pub.getmenJX(pub.getYSTgong())[1]);
		params.put("�չ����ż�������", pub.getmenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ�����ż�������", pub.getmenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ�����ż�������", pub.getmenJX(zhishigong)[1]);
		
		params.put("���񹬰���������", pub.getshenJX(pub.getYSTgong())[1]);
		params.put("�չ�����������", pub.getshenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ������������", pub.getshenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ������������", pub.getshenJX(zhishigong)[1]);
		
		params.put("���񹬾��Ǽ�������", pub.getxingJX(pub.getYSTgong())[1]);
		params.put("�չ����Ǽ�������", pub.getxingJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("ʱ�����Ǽ�������", pub.getxingJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("ֵʹ�����Ǽ�������", pub.getxingJX(zhishigong)[1]);
	}
	
	/**
	 * ������������ֱ����
	 * @param params
	 */
	public Map<String,Object> getInputParam() {
		Map<String,Object> customParams = new HashMap<String,Object>();
		customParams.putAll(params);
		initVar1(customParams);
		initVar2(customParams);
		initVar3(customParams);
		initVar4(customParams);
		initVar5(customParams);
		
		return customParams;
	}
}
