package org.boc.db.qm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.BadLocationException;

import org.boc.dao.qm.DaoQiMen;
import org.boc.ui.ResultPanel;

public class QimenGeju1 extends QimenGejuBase{
	/**
	 * ת��Ϊ1������Ϊ2
	 * �������С��1��2��3�в��õ���4��5��6��7Ҫ��
	 * ��1����2����1�����ģ�����9�񡱩�    ����һ�µģ�����ӡ��������з��
	 * ÿ���7�У� ÿ�������̶���
	 * 1�У�����˥smallpink-1��������ʱsmallblue-2��smallblack-16
	 * 2�У���Ҫ���׸�smallblack-19
	 * 3�У��ո�smallblack-19
	 * 4�У��ո�smallblack-3,����bigblack(bigred)-1���ո�smallblack-4������̳����smallblack-10
	 * 5�У����Ź�����˥smallblack-3������bigblack(bigred)-1�����ǹ�����˥smallblack-4������bigblack-2���ͳ����Ĺsmallblack-6
	 * 6�У����ǹ�����˥smallblack-3������bigblack-1�����ǹ�����˥smallblack-4������bigblack-2���ͳ����Ĺsmallblack-6
	 * 7�У��ո�smallblack-4�����̰���smallblack(smallred)-1���ո�smallblack-5������smallblack-9
	 */
	public String getGeju(ResultPanel rp, int izf, StringBuffer str) {
		this.resultPane = rp;  	
		this.iszf = izf!=2;
		daoqm.setZhuanpan(iszf);
		try {
			init();
			//1. ��ӡ��ͷ
			printHead();
			pw.newLine();
			//2. ��ӡ��һ����
			print(UP);
			pw.newLine();
			//
			for(int i=1; i<=7; i++) {
				pGong(4, i);
				pGong(9, i);
				pGong(2, i);
				println(2,END);
				pw.newLine();
			}
			
			print(MID1);
			pw.newLine();
			
			for(int i=1; i<=7; i++) {
				pGong(3, i);
				pGong(5, i);
				pGong(7, i);
				println(7,END);
				pw.newLine();
			}
			
			print(MID2);
			pw.newLine();
			
			for(int i=1; i<=7; i++) {
				pGong(8, i);
				pGong(1, i);
				pGong(6, i);
				println(6,END);
				pw.newLine();
			}
			
			print(DOWN);
			pw.newLine();
		} catch (BadLocationException e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}

	/**
	 * ����Ź���
	 * @param gong
	 * @throws BadLocationException
	 * 5�����̸ɡ�6�ŵ��̸ɡ�52�����̸ɡ�62�ǵ��̸�
	 * ��Զ��pLine5/pLine6�� pLine52/pLine62���Ⱥ������ϵ
	 */
	private void pGong(int gong, int line) throws BadLocationException{		
		println(gong,START);		
		
		if(QiMen2.XMHW==100) {  //72�䣬��1234567����			
				try {
					Method method = map.get(line);
					//System.out.println(line+":"+method.getName());
					method.invoke(this, gong);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
		}

		if(line==1) pLine1(gong);
		else if(line==2) pLine2(gong);
		else if(line==3) pLine3(gong);
		else if(line==7) pLine7(gong);	
		//xmhw=0/4/5������+���̸ɣ���+���̸ɣ� ����123������+���̸ɡ���+���̸ɣ����045123��ֵ���ܱ��ˣ�һ�䵽Tip:getDesc()���������ˡ�
		if(QiMen2.XMHW==0) { //�����ţ�����Ĭ�ϵģ�������ʾ���ǶԵģ���������ˣ�
			if(line==4) pLine4(gong);
			else if(line==5) pLine5(gong);
			else if(line==6) pLine6(gong);
		}else if(QiMen2.XMHW==1) {//������
			if(line==4) pLine4(gong);
			else if(line==5) pLine52(gong);
			else if(line==6) pLine62(gong);
		}else if(QiMen2.XMHW==2){ //������
			if(line==4) pLine52(gong);
			if(line==5) pLine4(gong);
			if(line==6) pLine62(gong);
		}else if(QiMen2.XMHW==3){  //������
			if(line==4) pLine52(gong);
			if(line==5) pLine62(gong);
			if(line==6) pLine4(gong);
		}else if(QiMen2.XMHW==4){  //������
			if(line==4) pLine5(gong);
			if(line==5) pLine4(gong);
			if(line==6) pLine6(gong);
		}else if(QiMen2.XMHW==5){	//������
			if(line==4) pLine5(gong);
			if(line==5) pLine6(gong);
			if(line==6) pLine4(gong);
		} 
	}
	
	private static Map<Integer, Method> map = new HashMap<Integer, Method>(7);
	public static void getRandomInvoke() {		
		int[] r = getRandom();
		for(int i=1; i<=7; i++) {
			//r[i-1]�ǵ�ǰ��Ҫ�任��λ����ţ��ɴ˵õ���Ӧ�ĵ��÷���
			Method m = null;
			try {
				m = QimenGejuBase.class.getMethod("pLine"+r[i-1], int.class);
				map.put(i, m);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}

	//�õ�һ��123456��ͬ˳��������
	private static int[] getRandom() {
		int[] arr = new int[7];
		for (int i = 0; i < 7; i++) {
			arr[i] = (int) (Math.random() * 7) + 1;
			for (int j = 0; j < i; j++) {
				if (arr[j] == arr[i]) {//���arr[i]��arr[j]��ͬ����arr[i]����ȡֵ��������
					i--;
					break;
				}
			}
		}
		return arr;
	}
	
	public QimenGeju1(DaoQiMen daoqm, QimenPublic pub) {
  	this.daoqm = daoqm;
  	this.pub = pub;  	
  }
	
	public static void main(String[] args) {
		int[] arr = new int[7];
		for (int i = 0; i < 7; i++) {
			arr[i] = (int) (Math.random() * 7) + 1;
			for (int j = 0; j < i; j++) {
				if (arr[j] == arr[i]) {//���arr[i]��arr[j]��ͬ����arr[i]����ȡֵ��������
					i--;
					break;
				}
			}
		}
		for (int i = 0; i < 7; i++)
			System.out.print(arr[i] + " ");
	}

}