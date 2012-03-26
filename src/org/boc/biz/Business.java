package org.boc.biz;

import java.lang.reflect.Method;

import org.boc.rule.ELException;
import org.boc.rule.ExpressionEvaluatorImpl;
import org.boc.rule.FunctionMapper;
import org.boc.rule.VariableResolver;
import org.boc.rule.parser.ELParserTokenManager;

public class Business implements IBiz {
	public void run() {
		ExpressionEvaluatorImpl expr = new ExpressionEvaluatorImpl();
		String s1 = "100*2 + 300 + 500/1-800";
		// ��Ҫ�ո�
		String s2 = "���((Ǭ�� ��С�� 3 ���� ��� С�� 4)����(100>200 ���� 11<10))��ô(y Ϊ  \"���\"); ���� (\"���»����ã�\")��";
		String s3 = "���    Ǭ��<=300 ��ô y Ϊ ���;";
		String s4 = "��� Ǭ��<=300 ���� ��� С�� 1 ��ô �ǶԵģ� ���� \"�Ǵ�ģ���Ϊ����˵1\"��";
		String s5 = "��� Ǭ��<=300 ��ô ���;";
		String s6 = "���  ���(8,45) ��ô ������";
		String s7 = "���     ���� \"���,������{}��������\";"; 
		s7 = "��� (����true ���� (2==2  ���� 3==3)) ��ô  ��� ����  \"  ��Ӣ�ǳ����࣬�������������Ĺ�������ա�ʱ���ɣ������졣\";";
		s7 = "��� �� ��ô y = aaaaaaaaǬ��aaaaaaaa; ";
		try {
			String rs = (String) expr.evaluate(s7, String.class, this, this);
			System.out.println("���н��Ϊ��" + rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public Object resolveVariable(String pName) throws ELException {
		// System.out.println(pName);
		if (pName.equals("Ǭ��"))
			return 100;
		else if (pName.equals("���"))
			return 2; // 200��2
		else if (pName.equals("this"))
			return this;
		return pName;
	}

	public void setVariable(String name, Object value) throws ELException {
		 System.out.println("setVariable="+name+"\t"+value);
	}

	public Method resolveFunction(String prefix, String localName) {
		//System.out.println("resolveFunction="+prefix+"\t"+localName);
		return this.getMethod(localName,int.class , int.class);
	}
	
	public Method getMethod(String mname,Class... params) {
		try {
			return this.getClass().getMethod(mname,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean ���(int i, int j) {
		return i>j;
	}

	public static void main(String[] args) {
		new Business().run();
	}
}
