package org.boc.biz.ly;

import java.util.HashMap;
import java.util.Map;

import org.boc.biz.qm.QMFunction;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;

	/**
	 * ����������������ʼ��
	 * 1. ʵ����ʱ�ͳ�ʼ��������ֻ����һ��
	 * 2. ÿ�λ��֣��������¼��ر���
	 */
public class LYInParam {
	private LiuyaoPublic pub;
	private DaoYiJingMain daoly;
	private LYFunction function;
	private static Map<String,Object> params = new HashMap<String,Object>();
	public LYInParam(LiuyaoPublic p, LYFunction function) {
		if(p==null) return;
		pub = p;
		daoly = pub.getDaoYiJingMain();
		this.function = function;
		initConstant();
	}
	
	public LYFunction getFunction() {
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
	}
	
	/** 
	 * �ġ�����������int
	 * 1. ������ɡ������֧��������ɡ�������֧
	 * 2. ��ɡ���֧���¸ɡ���֧���ոɡ���֧��ʱ�ɡ�ʱ֧
	 */	
	private void initVar3(Map<String,Object> params) {		
//		params.put("�������", pub.getYSTiangan());
//		params.put("�����֧", pub.getYSDizi());
//		params.put("�������", pub.getMingtg());
//		params.put("������֧", pub.getMingdz());
//		
		params.put("���", SiZhu.ng);
		params.put("��֧", SiZhu.nz);
		params.put("�¸�", SiZhu.yg);
		params.put("��֧", SiZhu.yz);
		params.put("�ո�", SiZhu.rg);
		params.put("��֧", SiZhu.rz);
		params.put("ʱ��", SiZhu.sg);
		params.put("ʱ֧", SiZhu.sz);
	}
	
	/**
	 * ������������ֱ����
	 * @param params
	 */
	public Map<String,Object> getInputParam() {
		Map<String,Object> customParams = new HashMap<String,Object>();
		customParams.putAll(params);
		initVar1(customParams);
		initVar3(customParams);
		
		return customParams;
	}
}
