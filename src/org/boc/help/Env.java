package org.boc.help;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import sun.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class Env {
	public String getJavaSystem() {
		Properties props = System.getProperties();
		StringBuilder sb = new StringBuilder();
		String[] keys = { "os.arch", "os.name", "os.version", "sun.os.patch.level",
				"user.country", "user.dir", "user.home", "user.name", "user.language" };
		for (String key : keys)
			sb.append(key).append("=").append(props.getProperty(key)).append("\r\n");
		return sb.toString();
	}

	/**
	 * ��ȡwidnows������mac��ַ.
	 */
	public String getWindowsMACAddress() {
		StringBuilder mac = new StringBuilder();
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ipconfig /all");// windows�µ������ʾ��Ϣ�а�����mac��ַ��Ϣ
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			int how = 0;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("physical address");// Ѱ�ұ�ʾ�ַ���[physical address]
				if (index >= 0) {// �ҵ���
					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index >= 0) {
						mac.append("����"+(++how)+":"+line.substring(index + 1).trim()+"\r\n");// ȡ��mac��ַ��ȥ��2�߿ո�
					}
				}
				index = line.toLowerCase().indexOf("ip address");// Ѱ�ұ�ʾ�ַ���[physical address]
				if (index >= 0) {// �ҵ���
					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index >= 0) {
						mac.append("--IP��ַ:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
				index = line.toLowerCase().indexOf("subnet mask");// Ѱ�ұ�ʾ�ַ���[physical address]
				if (index >= 0) {// �ҵ���
					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index >= 0) {
						mac.append("--��������:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
				index = line.toLowerCase().indexOf("default gateway");// Ѱ�ұ�ʾ�ַ���[physical address]
				if (index >= 0) {// �ҵ���
					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index >= 0) {
						mac.append("--ȱʡ����:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
				index = line.toLowerCase().indexOf("dns servers");// Ѱ�ұ�ʾ�ַ���[physical address]
				if (index >= 0) {// �ҵ���
					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index >= 0) {
						mac.append("--DNS����:"+line.substring(index + 1).trim()+"\r\n");
					}
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {}
			bufferedReader = null;
			process = null;
		}

		return mac.toString();
	}
	
	public String getComputerName()  {
		InetAddress ind;
		String name = "";
		try {
			ind = InetAddress.getLocalHost();
			name = "��������ƣ�"+ind.getHostName()+"��IP��ַ��"+ind.getHostAddress();
		} catch (UnknownHostException e) {
		}
		return name;
	}
	
	public String getMemoryInfo(){
		StringBuilder sb = new StringBuilder();
    int mb = 1024*1024;
//    long totalMemory = Runtime.getRuntime().totalMemory() / mb;//��ʹ���ڴ�
//    System.out.println("��ʹ���ڴ�:"+totalMemory);
//    long freeMemory = Runtime.getRuntime().freeMemory() / mb;//ʣ���ڴ�
//    System.out.println("ʣ���ڴ�:"+freeMemory);
//    long maxMemory = Runtime.getRuntime().maxMemory() / mb;//����ʹ���ڴ�
//    System.out.println("����ʹ���ڴ�:"+maxMemory);
    OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / mb;//�ܵ������ڴ�
    sb.append("�ܵ������ڴ�:"+totalMemorySize+"M\r\n");
    long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / mb;//ʣ��������ڴ�
    sb.append("ʣ��������ڴ�:"+freePhysicalMemorySize+"M\r\n");
    long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize())/mb;//��ʹ�õ������ڴ�
    sb.append("��ʹ�õ������ڴ�:"+usedMemory+"M\r\n");
    return sb.toString();
	}
	
	public String getPhisicalInfo(){
		StringBuilder sb = new StringBuilder();
    File[] roots = File.listRoots();//��ȡ���̷����б�
    String p1="", p2="";
    for (int i = 0; i < roots.length; i++) {
    	if(roots[i].canRead()) p1 += roots[i]+"��";
    	else p2 += roots[i]+"��";    		      
    }
    sb.append("���̷�����"+p1+"���̷���:"+p2);
    
    return sb.toString();
	}



	public static void main(String[] args) {
		Env env = new Env();
		System.out.println(env.getJavaSystem());
		 String mac = env.getWindowsMACAddress();   
     System.out.println(mac);   
     System.out.println(env.getComputerName());
     System.out.println(env.getMemoryInfo());
     System.out.println(env.getPhisicalInfo());
	}
}
