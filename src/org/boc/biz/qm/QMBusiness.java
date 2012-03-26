package org.boc.biz.qm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;

import org.boc.biz.Business;
import org.boc.db.qm.QimenPublic;
import org.boc.rule.ELException;
import org.boc.rule.ExpressionEvaluatorImpl;

public class QMBusiness extends Business {
	private QMInParam param;
	private QMFunction function;
	private Map<String,Object> inParams ;   //�����������
	private ExpressionEvaluatorImpl expr = new ExpressionEvaluatorImpl();
	private boolean isSaveResult;  //�Ƿ񱣴洦����
	public QMBusiness(QimenPublic pub) {
		function = new QMFunction(pub);
		param = new QMInParam(pub, function);		
	}
	
	/**
	 * ִ��һ������
	 */
	public String evaluate(String rule) {		
		try {
			expr.evaluate(rule, String.class, this, this);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.toString();
		}
		return null;
	}
	
	/**
	 * ��������������Լ��Զ��庯���Ķ���thisָ��
	 */
	public Object resolveVariable(String param) throws ELException {
		Context ctx;
		Object obj = inParams.get(param);
		//System.out.println("�������------"+param+"="+obj);
		return obj == null ? param : obj;
	}
	/**
	 * �����������
	 */
	public void setVariable(String name, Object value) throws ELException {
		if(!isSaveResult) return;
		if(name==null || value==null) return; 
		if(name.equals(QMOutParam.OUTPARAM1))
			QMOutParam.append(value.toString());
	}
	/**
	 * ������
	 */
	public Method resolveFunction(String prefix, String localName) {
		//System.out.println(localName+":"+function);
		return function.getMethod(localName);
	}
	/**
	 * ����������ı��������뵽List�з��أ��������
	 */
	private List<String> procRule(String text) {
		List<String> result=new ArrayList<String>();
		
		Pattern p = Pattern.compile("(.*?(\r\n|\n)).*");
		Matcher m = p.matcher(text);	
		while(m.find()){
			String s = m.group();
			//System.out.println("1: "+s);
			if("".equals(s.trim())) continue;
			//1. �滻����...�������еİ��˫���ţ����ž�Ÿ�̾�ŵȾ������滻��
			s = s.replaceAll("\"", "");
			//2. ��...����...�������ȫ�ǣ������ո�  �滻�ɰ��(,)�ո�     ��������������һ���������޲���			
			s = s.replaceAll("(.*?)(��)(.*?)(��)(.*?)(��)(.*?)(\\{|��)(.*?)(\\}|��)","$1($3,$5)$7$8$9$10");
			//3. ȥ����β�������� �ո�;������.�������з���
			s = s.replaceAll("(}|��)(.*)", "$1");
			s = s.replaceAll("(.*?)(��)(.*?)(��)(.*?)(\\{|��)(.*?)(\\}|��)","$1($3)$5$6$7$8");
			//3. ��  ��|{...}|��  �滻��       ��� ��ô ��� ���� "...";
			s = s.replaceAll("(\\{|��)(.*?)(\\}|��)", " ��� ����  \"$2\";");
			//System.out.println("2: "+s);
			result.add(s);
		}
		return result;
	}
	
	/**
	 * ���������е����й��� 
	 */
	public String checkRules(String text) {
		inParams = param.getInputParam();		//ÿ�ζ�����ȡһ�Σ������Ч�����⣬�����ڱ��ʱ����Ҳ����
//		QMOutParam.clean();  //��ջ��棬��ֹԽ��Խ��
		//System.out.println(text);
		isSaveResult = false;   //����Ҫ���洦���������Ӽ���Ч��
		List<String> list = procRule("\r\n"+text);  //�����һ�����з��������һ�ж�ʧ��
		String rs = null;
		String ruleText = null;
		for(int line = 0; line<list.size(); line++) {
			ruleText = list.get(line).trim().replaceAll("\r\n", "");
			//System.out.println(line+" : "+ruleText);
			//System.out.println(line+":"+list.get(line));
			if((rs = this.evaluate(ruleText))!=null) {
				//System.out.println((line+1) + " Ϊ��   "+ruleText);
				return "��"+(line+1)+"�У������������ϢΪ��                                             "+rs;
			}
		}
		printFunction();
		printVariable();
		return "д�ò������й����﷨�����ѳɹ��������ִ�У�";
	}
	public void printVariable() {		
		for(Iterator<String> it = inParams.keySet().iterator(); it.hasNext();) {
			String name = it.next();
			Object o = inParams.get(name);
			System.out.println(name);
		}
	}
	public void printFunction() {
		Map<String, Method> map = function.getMethods();
		String pname="";
		for(Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String name = it.next();
			Method method = map.get(name);
			Class[] paras = method.getParameterTypes();
			if(paras.length==1) pname = "�乬";
			else if(paras.length==2) pname="�乬1, �乬2";
			System.out.println(name+"("+pname+")");
		}
	}
	
	/**
	 * ִ�����еĹ��� 
	 */
	public String runRules(String text) {
		inParams = param.getInputParam();		//ÿ�ζ�����ȡһ�Σ������Ч�����⣬�����ڱ��ʱ����Ҳ����
		//System.out.println("----------------------text--------------------\r\n"+text);
		QMOutParam.clean();
		isSaveResult = true;   //��Ҫ���洦���������Ӽ���Ч��
		List<String> list = procRule("\r\n"+text);  //��Ȼ����һ�ж�ʧ��
		String err = null;
		for(int line = 0; line<list.size(); line++) {
			//System.out.println(list.get(line));
			if((err = this.evaluate(list.get(line)))!=null) 
				return "��"+(line+1)+"�У������������ϢΪ��                                            "+err;
		}
		
		//�����������
		String rs = getResult();
		//ȡ�������ŵı��� ��var1��
		Pattern p = Pattern.compile("((\\(|��)[^��|^\\)]*(\\)|��))");
		Matcher m = p.matcher(rs);	
		while(m.find()){
			String s1 = m.group();
			if("".equals(s1.trim())) continue;
			String s2 = s1.replaceAll("\\(|��|\\)|��", "");
			//System.out.println("s1="+s1+";s2="+s2+";value="+inParams.get(s2));
			rs = rs.replaceAll("((\\(|��)"+s2+"(\\)|��))", String.valueOf(inParams.get(s2)));
		}
		
//		String k;
//		Object v;
//		for(Iterator<String> it=inParams.keySet().iterator(); it.hasNext();) {
//			k = it.next();
//			v = inParams.get(k);
//			if(v==null) v="";
//			rs = rs.replaceAll(k, String.valueOf(v));
//		}
		return rs;
	}
	
	public String getResult() {
		return QMOutParam.getInfo();
	}
	
	public static void main(String[] args) {
		String text = "��� ��ã������� �ҹ���  ��aaaa��111��222��\n��� ��������{bbbbbbbbbbb}\r\n��� �㣨����ccccccccccccc��\n";
		text = "{���ֵܣ���ã�ʦ��)�� �� �����ˣ��ֵ�(ũ��}";
		text = "{a}aa}   ����;;��    ,��  a   ; .";
		//Pattern p = Pattern.compile("((\\(|��)[^��|^\\)]*(\\)|��))");
		System.out.println(text.replaceAll("(}|��)(.*)", "$1"));
//		Matcher m = p.matcher(text);	
//		while(m.find()){
//			String s = m.group();
//			s = s.replaceAll("\\(|��|\\)|��", "");
//			System.out.println("1: "+s);
//		}
	}
}
