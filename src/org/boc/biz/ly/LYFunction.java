package org.boc.biz.ly;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.boc.biz.qm.QMFunction;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.YiJing;

public class LYFunction {
	private LiuyaoPublic pub;
	private DaoYiJingMain daoly;
	private static Map<String, Method> methodMap = new HashMap<String, Method>();;
	
	public LYFunction(LiuyaoPublic pub) {
		this.pub = pub;
		if(pub!=null) daoly = pub.getDaoYiJingMain();
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
}
