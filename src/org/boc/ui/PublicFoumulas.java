package org.boc.ui;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.util.PrintWriter;

public class PublicFoumulas {
	public static void print(PrintWriter pw) throws Exception {

	}

	public static void printYima(PrintWriter pw) throws Exception {
		pw.println("      ����",PrintWriter.BRED);
		pw.newLine();
		pw.println("���ӳ���������",PrintWriter.BBLUE);
		pw.println("�����������ꣻ",PrintWriter.BBLUE);
		pw.println("���ϳ����ں���",PrintWriter.BBLUE);
		pw.println("��îδ�����ȡ�",PrintWriter.BBLUE);
	}

	public static void printRsqsf(PrintWriter pw) throws Exception {
		pw.println("       ������ʱ��",PrintWriter.BRED);
		pw.newLine();
	  pw.println("�׼����Ӽף��Ҹ���������",PrintWriter.BBLUE);
	  pw.println("���������𣬶��ɸ��Ӿӡ�",PrintWriter.BBLUE);
	  pw.println("���η�������������;��",PrintWriter.BBLUE);
	}

	public static void printNayin(PrintWriter pw) throws Exception {
		pw.bblack("                ��ʮ����������");
		pw.newLine();
		pw.newLine();
		int index = 0,i=0,j=0;
		while(++index <= 60) {
			i = (++i)%11==0 ? 1 : i;
			j = (++j)%13==0 ? 1 : j;			
			
			for(int k=0; k<4; k++) {
				pw.mpink((1863+k*60+index)+"  ");
			}
			pw.mblue("     ");
			pw.mblue(YiJing.TIANGANNAME[i]+YiJing.DIZINAME[j]);
			pw.mred("          "+SiZhu.NAYIN[i][j]);
			pw.newLine();		
		}
	}

	public static void printSgkyb(PrintWriter pw) throws Exception {
		pw.bblack("                  ʮ�ɿ�Ӧ��");
		pw.newLine();
		pw.newLine();
		int ge = 0;
		for(int i=2; i<=10; i++) {
			pw.bblack("                      ");
			pw.bred(YiJing.TIANGANNAME[i]); //+"��"+YiJing.TIANGANNAME[j]);
			pw.newLine();
			for(int j=2; j<=10; j++) {
				ge = QiMen.gan_gan[i][j];					
				pw.sblue(QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1].replace(QiMen.HH, ""));
				pw.newLine();
			}
			pw.newLine();
		}
	}

	public static void printNsqyf(PrintWriter pw) throws Exception {
		pw.println("          �������·�", PrintWriter.BRED);
		pw.newLine();
		pw.println("�׼�֮������ף��Ҹ�֮����Ϊͷ��", PrintWriter.BBLUE);
		pw.println("����֮��Ѱ���ϣ���������˳ˮ����", PrintWriter.BBLUE);
		pw.println("�������δ��𣬼���֮�Ϻ�׷��", PrintWriter.BBLUE);
	}

	public static void printShiTianganSWSJB(PrintWriter pw) throws Exception {
		pw.bblack("        ʮ�������������");
		pw.newLine();
		pw.newLine();
		pw.println("�������������ө��ө��ө��ө��ө��ө��ө��ө��ө��ө��ө��ө���", PrintWriter.SBLUE, false);
		pw.println("������  ״̬�������婦�ک��٩��۩�˥����������Ĺ������̥������", PrintWriter.SRED, false);
		pw.println("�ĩ����Щ����੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("��    �� �� �������ө�������î�������ȩ��穦δ���ꩦ�ϩ��穧", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("�� �� �� �� ������î�������ȩ��穦δ���ꩦ�ϩ��穦�����ө���", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("�� �� �� �� ������î�������ȩ��穦δ���ꩦ�ϩ��穦�����ө���", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("�� �� �� �� ���ȩ��穦δ���ꩦ�ϩ��穦�����ө�������î������", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("��    �� �� ���ꩦ�ϩ��穦�����ө�������î�������ȩ��穦δ��", PrintWriter.SBLACK, false);
		pw.println("�ĩ����੤���੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("��    �� �� ���穦�ȩ�����î���������ө������穦�ϩ��ꩦδ��", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("�� �� �� �� ���ϩ��ꩦδ���穦�ȩ�����î���������ө������穧", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("�� �� �� �� ���ϩ��ꩦδ���穦�ȩ�����î���������ө������穧", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("�� �� �� �� ���ө������穦�ϩ��ꩦδ���穦�ȩ�����î��������", PrintWriter.SBLACK, false);
		pw.println("��    �������੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤�੤��", PrintWriter.SBLUE, false);
		pw.println("��    �� �� ��î���������ө������穦�ϩ��ꩦδ���穦�ȩ�����", PrintWriter.SBLACK, false);
		pw.println("�������۩����۩��۩��۩��۩��۩��۩��۩��۩��۩��۩��۩��۩���", PrintWriter.SBLUE, false);
		pw.newLine();
	}
}
