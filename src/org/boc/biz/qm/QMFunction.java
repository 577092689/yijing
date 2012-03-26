package org.boc.biz.qm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.YiJing;
import org.boc.db.qm.QimenPublic;

public class QMFunction {
	private QimenPublic pub;
	private DaoQiMen daoqm;
	private static Map<String, Method> methodMap = new HashMap<String, Method>();;
	
	public QMFunction(QimenPublic pub) {
		this.pub = pub;
		if(pub!=null) daoqm = pub.getDaoQiMen();		
		setMethod();
	}
	/**
	 * �����з������ڻ����������
	 */
	private void setMethod() {
		try {
			Method[] methods = getClass().getMethods();
			for(Method method : methods) {
				methodMap.put(method.getName(), method);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<String, Method> getMethods() {
		return this.methodMap;
	}
	/**
	 * �ɷ��������ط���
	 */
	public Method getMethod(String name) {
		return methodMap.get(name);
	}
	
	//1. g1��g2�������Ƿ�Ⱥ�
	public boolean �����Ⱥ�(int g1, int g2) {
		return pub.isBihe(g1, g2);
	}
	//1. g1�������Ƿ���g2��
	public boolean ��������(int g1, int g2) {
		return pub.isGongSheng(g1, g2);
	}
	//2. g1��g2��g2��g1
	public boolean ��������(int g1, int g2) {
		return pub.isSheng2(g1, g2);
	}
	//3. g1�������Ƿ��g2��
	public boolean �������(int g1, int g2) {
		return pub.isGongke(g1, g2);
	}
	//4. g1��g2��g2��g1
	public boolean ��������(int g1, int g2) {
		return pub.isGongke2(g1, g2);
	}
	//5. g1��g2�Ƿ����
	public boolean �������(int g1, int g2) {
		return pub.isGongChong(g1, g2);
	}	
	//6. �ж�g1���Ƿ���g2��
	public boolean �������(int g1, int g2) {
		return pub.isChongke(g1, g2);
	}	
	//������֧�Ƿ����
	public boolean ������֧���(int g1, int g2) {
		return pub.isGongLiuhe(g1, g2);
	}	
	
	/////////////////////////////////////////////////////////////
	//7. �Ƿ񱾹�������������������ж���
	public boolean ��������(int g1) {
		return pub.isKong(g1);
	}
	//8. �Ƿ񱾹����ǣ����������ж���
	public boolean ��������(int g1) {
		return pub.isYima(g1);
	}
	//�ж����̸�����̸ɡ����̸����乬�����̸����乬�Ƿ����
	public boolean ��������(int gong){
		return pub.isJixing(gong);
	}
	//9. �Ƿ񱾹������Գ�֮��
	public boolean �����Գ屾��(int g1) {
		return pub.isChKong(g1);
	}
	//10. �Ƿ񱾹����ǣ����������ж���
	public boolean ���ǶԳ屾��(int g1) {
		return pub.isChMa(g1);
	}
	//15. �жϱ����Ƿ�������
	public boolean ��������(int gong){
		return pub.isNenpan(gong);
	}
	//15. �жϱ����Ƿ���˵�
	public boolean ������˵�(int gong){
		return pub.isTianKeDi(gong);
	}
	//15. �жϱ����Ƿ�ؿ���
	public boolean �����ؿ���(int gong){
		return pub.isDiKeTian(gong);
	}
	//15. �жϱ����Ƿ�������
	public boolean ����������(int gong){
		return pub.isTianShengDi(gong);
	}
	//15. �жϱ����Ƿ������
	public boolean ����������(int gong){
		return pub.isDiShengTian(gong);
	}
	//15. �жϱ����Ƿ�����
	public boolean ������(int gong){
		return pub.ismenzhi(gong);
	}
	//15. �жϱ�������
	public boolean �ſ˹�(int gong){
		return pub.ismenpo(gong);
	}
	//15. �жϱ���������
	public boolean ��������(int gong){
		return pub.ismensheng(gong);
	}
	public boolean ���빬Ĺ(int men){
		return pub.ismenmu(men);
	}
	//15. �жϱ���
	public boolean ������(int gong){
		return YiJing.WXDANKE[daoqm.gInt[4][2][gong]][daoqm.gInt[2][2][gong]]==1;  
	}
	//15. �жϱ���
	public boolean �ǿ˹�(int gong){
		return YiJing.WXDANKE[daoqm.gInt[2][2][gong]][daoqm.gInt[4][2][gong]]==1; 
	}
	//15. �жϱ���
	public boolean ��������(int gong){
		return YiJing.WXXIANGSHENG[daoqm.gInt[4][2][gong]][daoqm.gInt[2][2][gong]]==1; 
	}
//15. �жϱ���
	public boolean ���˸�(int gong){
		return YiJing.WXDANKE[daoqm.gInt[4][2][gong]][daoqm.gInt[2][4][gong]]==1;  
	}
	//15. �жϱ���
	public boolean �ɿ˹�(int gong){
		return YiJing.WXDANKE[daoqm.gInt[2][4][gong]][daoqm.gInt[4][2][gong]]==1; 
	}
	//15. �жϱ���
	public boolean ��������(int gong){
		return YiJing.WXXIANGSHENG[daoqm.gInt[4][2][gong]][daoqm.gInt[2][4][gong]]==1; 
	}
	//����ʮ�ɿ�Ӧ����
	public boolean ��������(int gong){
		return pub.getJixiongge(gong)[0].equals("1");
	}
	//��������
	public boolean ��������(int g1) {
		return pub.getmenJX(g1)[0].equals("1");
	}
	public boolean ��������(int g1) {
		return pub.getmenJX(g1)[0].equals("-1");
	}
	//��������
	public boolean ��������(int g1) {
		return pub.getshenJX(g1)[0].equals("1");
	}
	public boolean ��������(int g1) {
		return pub.getshenJX(g1)[0].equals("-1");
	}
	//��������
	public boolean ��������(int g1) {
		return pub.getxingJX(g1)[0].equals("1");
	}	
	public boolean ��������(int g1) {
		return pub.getxingJX(g1)[0].equals("-1");
	}
	//������������˥
	public boolean ��������(int gong){
		return pub.getGongWS(gong)[0].equals("1");
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////���Թ����Ǹ�����Ϊ���������պ�Ӧ�ã��������أ�Ҫд�������///////////////////
	//11. �Ƿ����̸��乬��Ĺ��
	public boolean ���̸���Ĺ��(int gong) {
		return pub.isTGanSMJ(gong);
	}
	//12. �Ƿ���̸��乬��Ĺ��
	public boolean ���̸���Ĺ��(int gong) {
		return pub.isDGanSMJ(gong);
	}
	//13. �Ƿ����̸���Ĺ
	public boolean ���̸���Ĺ(int gong) {
		return pub.isTGanMu(gong);
	}
	//14. �Ƿ���̸���Ĺ
	public boolean ���̸���Ĺ(int gong) {
		return pub.isDGanMu(gong);
	}
	//�жϱ������̸��乬Ϊ�һ�
	public boolean ���̸��һ�(int gong){
		return pub.isTpTaohua(gong);
	}
	//�жϱ������̸��乬Ϊ�һ�
	public boolean ���̸��һ�(int gong){
		return pub.isDpTaohua(gong);
	}
	//�жϱ������̸��빬�����̸��Ƿ����ǻ���
	public boolean ���̸ɻ���(int gong){
		return pub.isTpJixing(gong);
	}	
	//�жϱ������̸��빬�����̸��Ƿ����ǻ���
	public boolean ���̸ɻ���(int gong){
		return pub.isDpJixing(gong);
	}
	//�жϱ������̸��빬�Ƿ����
	public boolean ���̸ɷ��(int gong){
		return pub.isTChong(gong);
	}	
	//�жϱ������̸��빬�Ƿ����
	public boolean ���̸ɷ��(int gong){
		return pub.isDGChong(gong);
	}
	//�жϱ������̸��빬�����̸��Ƿ��ϡ����ϡ�����
	public boolean ���̸ɷ��(int gong){
		return pub.isTDGanHe(gong) || pub.isTDG3He(gong) || pub.isTDZi3He(gong) ||
					 pub.isTG3He(gong) || pub.isTG6He(gong);
	}	
	//�жϱ������̸��빬�����̸��Ƿ��ϡ����ϡ�����
	public boolean ���̸ɷ��(int gong){
		return pub.isTDGanHe(gong) || pub.isTDZi3He(gong) ||
					 pub.isDG3He(gong) || pub.isDG6He(gong);
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////
	//�Ƿ�������
	public boolean ����(int gan) {
		return pub.isYangGan(gan);
	}
	//�Ƿ�������
	public boolean ������(int gan) {
		return pub.is5YangGan(gan);
	}
	//11. �Ƿ����̸�����
	public boolean �������(int gan) {
		return pub.gettgWS(gan)[0].equals("1");
	}
	//�жϾ�����˥
	public boolean ��������(int xing) {
		return pub.getxingWS(pub.getXingGong(xing))[0].equals("1");
	}
	//�жϰ�����˥
	public boolean ��������(int men) {
		return pub.getmenWS(pub.getMenGong(men))[0].equals("1");
	}
	
	
	
	
	
	public static void main(String[] args) {
		QMFunction function = new QMFunction(null);
		Method method = function.getMethod("������������");
		System.out.println(method.getName());
	}
}
