package org.boc.help;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.ui.BasicJTabbedPane;
import org.boc.util.Public;

public class MailUtil {
	private BasicJTabbedPane bpane;
	
	public MailUtil(BasicJTabbedPane bpane) {
		this.bpane = bpane;
	}
	/**
	 * �����ʼ�
	 * @param mailServer:�ʼ�������
	 * @param user�� �û���
	 * @param pass��������
	 * @param subject�����ʼ�����
	 * @param content�� �ʼ�����
	 * @param address��������
	 * @throws Exception
	 */
	public void send(String mailServer, final String user, final String pass,
			String subject, String content, String address) throws Exception {
		if(bpane==null || bpane.getXxxxPanel()==null) return;
		// ��һ��,����ϵͳ��һϵ������		
		String pass2;
		Properties props = System.getProperties();
		if(pass==null || pass.trim().equals("")) pass2="223789";
		// ָ�������ʼ�������
		props.put("mail.smtp.host", mailServer);
		props.put("mail.smtp.auth", "true");
		// ȡ��һ��Session���󣬴���һ���ʼ�����
		Session sendmailsession = Session.getDefaultInstance(props, 
				new Authenticator() {			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String pass="" ;
				if(pass.length()<=6) pass="@";
				if(pass.length()==1) pass=Public.NUM1+pass;
				if(pass.length()==5) pass+=SiZhu.NUM2;
				pass+=bpane.NUM4+YiJing.NUM3;
				return new PasswordAuthentication(user, pass);
			}
			public String getPass() {
				return pass;
			}
			public String getUser() {
				return user;
			}
		}
		);
//		Session sendmailsession = Session.getDefaultInstance(props, null);
		// �õ�һ�����ڷ����ʼ�����,ָ�����͵�Э��
		Transport transport = sendmailsession.getTransport("smtp");
		// �õ�һ��ʵ�ʷ����ʼ�����Ϣ����
		MimeMessage message = new MimeMessage(sendmailsession);
		// ָ���ʼ��ķ�������
		message.setSubject(subject);
		// ָ���ʼ��ķ�������
		message.setText(content);
		// ָ���ʼ��ķ�������
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(address));
		// ȷ����session��message֮��,��һ���ʼ����շ����ʼ���ַ
		Address toAddress = new InternetAddress(address);
		// ���ʼ���ַ��ӵ�message��ȥ��
		message.addRecipient(Message.RecipientType.TO, toAddress);
		/*
		 * ����������Ҫ��֤ʱ��ֱ��ʹ�õ���� transport.send(message);
		 */
		/* �����Ƿ�������Ҫ��֤ʱ��Ҫ�ķ��� */
		transport.connect();
		//System.out.println("�ʼ����������ӳɹ�");
		//transport.send(message);
		transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
		//System.out.println("�����ʼ��ɹ�");
	}

	public static void main(String[] args) throws Exception {
		//MailUtil.send("smtp.163.com", "vandh@163.com", "223789", "ʼ��Ԥ��", "�ʼ�����","vandh@163.com");
	}
}
