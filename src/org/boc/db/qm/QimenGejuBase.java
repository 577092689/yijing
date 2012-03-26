package org.boc.db.qm;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.PrintWriter;

public class QimenGejuBase {
	DaoQiMen daoqm;
	QimenPublic pub; 
	String[] liunian ; //�������˹����е��������
	int index = 0; //��������ΪdescMY����
	
	final int UP = 1;
	final int MID1 = 2;
	final int MID2 = 3;
	final int DOWN = 4;
	final int START = 5;
	final int END = 6;
	
	//���ߡ�����һ���ߡ��м�һ���ߡ���һ���ߡ�Ҫ�ظ����ɼ��Ŀհ��ַ�
	public static final String FILL1 = "��";   //ռ�����ַ�
	public static final String FILL2 = " ";   //ռһ���ַ�������΢��
	final String BORDER1  =  "��";  // �� ���� ������������  ��  ��  ������  �ȩ� ��	  �������� 
	final String BORDER2  =  "��";
//	private final String UPLINE   = "���������������������������������Щ������������������������������Щ�������������������������������";
//	private final String MIDLINE =  "���������������������������������੬�����������������������������੬������������������������������";
//	private final String DOWNLINE = "���������������������������������ة������������������������������ة�������������������������������";	
	static final int COL = 15;  //һ���Ź��������
	
	//��Щ������������������ַ��ĸ�����
	final int[] LINE4 = {2,2,4,2,3};  
	final int[] LINE5 = {0,0,0,0,4,3};
	//private final int[] LINE6 = LINE5; //{2,2,4,3};
	final int[] LINE7 = {1,3,1,1,1,1};		
	boolean iszf ; 
	
	/**
	 * �����ͷ5,12  5,6    5,6
	 ������������������������������������������������������������������������������
    ����  �֩� �������                               ��  ֵ���� ���Ĺ�                                     ��  ֵʹ�� ���Ź�       ��
      ������������������������������������������������������������������������������
    ����  ֧�� ����  ����  ����  ����     �� ��  ͷ�� ����                                       ��Ѯ�ש�����(��)   ��  
      ������������������������������������������������������������������������������
    �����֩��岻��ʱ���츨��ʱ�����ŷ��ʡ����Ƿ��ʡ����ŷ��������Ƿ���                     ��
      ������������������������������������������������������������������������������
	
	������֣� ���ݶ��֣�ֵ������һ������ֵʹ������������
��������֧�� ��î ���� ���� ��δ����ͷ�����ӣ���Ѯ�ף���������
���������񣺡���������������������������������������������������
	 */
	void printHead() throws BadLocationException {
		if(!QiMen2.HEAD && !QiMen2.ALL) return;
		
		String pre2 = speat(3, FILL1);
		int ju = daoqm.getJu();
		int zsgong = getZSgong();//ת���мĹ�
		
		// 2. ������
		whead(pre2 + daoqm.getYangOrYinDesc(false)+FILL1);
		whead(QiMen.yydunju[ju > 0 ? ju : 9 - ju]);
		whead("��ֵ��");
		whead2(QiMen.jx1[daoqm.gInt[0][0][12]]+ QiMen.BIGNUM[zhifugong]+"��");
		whead("��ֵʹ");
		whead2(QiMen.bm1[daoqm.gInt[3][1][zsgong]] + QiMen.BIGNUM[zsgong] + "��" );
		pw.newLine();
		// 3.�������
		String sizhu = YiJing.TIANGANNAME[SiZhu.ng] + YiJing.DIZINAME[SiZhu.nz]
				+ FILL2 + YiJing.TIANGANNAME[SiZhu.yg] + YiJing.DIZINAME[SiZhu.yz]
				+ FILL2 + YiJing.TIANGANNAME[SiZhu.rg] + YiJing.DIZINAME[SiZhu.rz]
				+ FILL2 + YiJing.TIANGANNAME[SiZhu.sg] + YiJing.DIZINAME[SiZhu.sz];
		int[] futou = daoqm.getFutou(SiZhu.rg, SiZhu.rz);
		//String sfutou = YiJing.TIANGANNAME[futou[1]] + YiJing.DIZINAME[futou[2]];
		whead(pre2 + "��֧��"+FILL1 );
		whead2(sizhu);
		whead("����ͷ");
		whead(QiMen.xunname[daoqm.getXunShu(futou[1], futou[2])]);
		whead("��Ѯ��");
		whead(QiMen.xunname[daoqm.getXunShu(SiZhu.sg, SiZhu.sz)]);
		pw.newLine();
		// 4. �������
		StringBuilder sb = new StringBuilder();
		int wu = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]; // �岻��ʱ���츨��ʱ
		if (wu != 0)
			sb.append(QiMen.gGejuDesc[wu][0] + "��");
		if (pub.isMenFu())
			sb.append("���ŷ��ʡ�");
		if (pub.isMenFan())
			sb.append("���ŷ��ʡ�");
		if (pub.isXingFu())
			sb.append("���Ƿ��ʡ�");
		if (pub.isXingFan())
			sb.append("���Ƿ��ʡ�");
		if (sb.length() > 0)
			sb = sb.delete(sb.length() - 1, sb.length());
		whead(pre2 + "����" + FILL1 );
		whead(sb.toString() + speat(30-sb.length(),FILL1)); //�̶����30���ո�ʡ�ò�����tip�ж�λ
	}
//	void printHead() throws BadLocationException {
//		int lenth = 41;
//		String H = "��", V = "��"; //"��";
//		String line = speat(lenth,H);
//		String pre = speat(4,FILL1);
//		String pre2 = speat(3,FILL1);		
//		int ju = daoqm.getJu();
//		
//		//1. ����ϱ���
//		wline(pre+line);  
//		pw.newLine();
//		//2. ������
//		whead(pre2+V+"��"+FILL1+"��"+V+QiMen.yydunju[ju>0?ju:9-ju]+speat(8,FILL1));
//		whead(V+"ֵ"+FILL1+"��"+V+QiMen.jx1[daoqm.gInt[2][1][zhifugong]]+QiMen.BIGNUM[zhifugong]+"��"+speat(1,FILL1));
//		whead(V+"ֵ"+FILL1+"ʹ"+V+QiMen.bm1[daoqm.gInt[3][1][zhishigong]]+QiMen.BIGNUM[zhishigong]+"��"+speat(1,FILL1)+V);
//		pw.newLine();
//		//pw.sblack(pre+line);  
//		//pw.newLine();
//		//3.�������
//		String sizhu = YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+FILL1+
//			YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+FILL1+
//			YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+FILL1+
//			YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+FILL1;
//		whead(pre2+V+"��"+FILL1+"֧"+V+sizhu);
//		int[] futou = daoqm.getFutou(SiZhu.rg, SiZhu.rz);
//		String sfutou = YiJing.TIANGANNAME[futou[1]]+YiJing.DIZINAME[futou[2]];
//		whead(V+"��"+FILL1+"ͷ"+V+sfutou+speat(2,FILL1));
//		whead(V+"Ѯ"+FILL1+"��"+V+QiMen.xunname[daoqm.getXunShu(SiZhu.sg, SiZhu.sz)]+V);
//		pw.newLine();
//		//pw.sblack(pre+line);  
//		//pw.newLine();
//		//4. �������
//		StringBuilder sb = new StringBuilder();
//		int wu = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];  //�岻��ʱ���츨��ʱ
//		if(wu!=0) sb.append(QiMen.gGejuDesc[wu][0]+"��");
//		if(pub.isMenFu()) sb.append("���ŷ��ʡ�");
//		if(pub.isMenFan()) sb.append("���ŷ��ʡ�");
//		if(pub.isXingFu()) sb.append("���Ƿ��ʡ�");
//		if(pub.isXingFan()) sb.append("���Ƿ��ʡ�");
//		if(sb.length()>0) sb = sb.delete(sb.length()-1, sb.length());
//		whead(pre2+V+"���׸�"+V+sb.toString()+speat(lenth-sb.length()-11, FILL1)+V);
//		pw.newLine();
//		wline(pre+line);  
//	}
//	
	private void whead(String s) throws BadLocationException {
		pw.print(s,PrintWriter.SBLACK,false);
	}
	private void whead2(String s) throws BadLocationException {
		pw.print(s,PrintWriter.SBLUE,false);
	}
//	private void wline(String s) throws BadLocationException {
//		SimpleAttributeSet smallcray = new SimpleAttributeSet();
//		StyleConstants.setForeground(smallcray, Color.GRAY);
//		StyleConstants.setFontSize(smallcray, SMALL);
//		doc.insertString(doc.getLength(), s, smallcray);
//	}
	
	/**
	 * ���������
	 * @param gong: ��һ��
	 * @param type���ǿ�ʼ���ǽ�������
	 * @throws BadLocationException
	 */
	void println(int gong, int type) throws BadLocationException {
		if(isYang) {
			if((gong==2 || gong==7) && type==START) pw.sblack(BORDER1);
			else if((gong==2 || gong==7 || gong==6) && type==END) pw.sblack(BORDER1);
			else pw.sred(BORDER2);
		}else {
			if((gong==9 || gong==2 || gong==7 || gong==6) && type==START) pw.sred(BORDER2);
			else if((gong==2 || gong==7 || gong==6) && type==END) pw.sred(BORDER2);
			else pw.sblack(BORDER1);
		}
	}
	
	/**
	 * ����Ź�����ߣ�����Ϊ���ߣ�����Ϊ����
	 * @param type: �ϡ��С�������
	 * @throws BadLocationException 
	 */
	void print(int type) throws BadLocationException {		
		if(type==UP && isYang) {  //���ֵ�һ��
			pw.sred("����������������������������������");
			pw.sblack("�������������������������������Щ�������������������������������");
		}else if(type==UP) {  //���ֵ�һ��
			pw.sblack("����������������������������������");
			pw.sred("�������������������������������Щ�������������������������������");
		}
		
		if(type==MID1 && isYang) {  //���ֵ�һ��
			pw.sred("����������������������������������");
			pw.sblack("�������������������������������੬������������������������������");
		}else if(type==MID1) {  //���ֵ�һ��
			pw.sblack("����������������������������������");
			pw.sred("�������������������������������੬������������������������������");
		}
		
		if(type==MID2 && isYang) {  //���ֵ�һ��
			pw.sred("���������������������������������੬������������������������������");
			pw.sblack("��������������������������������");
		}else if(type==MID2) {  //���ֵ�һ��
			pw.sblack("���������������������������������੬������������������������������");
			pw.sred("��������������������������������");
		}
		
		if(type==DOWN && isYang) {  //���ֵ�һ��
			pw.sred("���������������������������������ة�������������������������������");
			pw.sblack("��������������������������������");
		}else if(type==DOWN) {  //���ֵ�һ��
			pw.sblack("���������������������������������ة�������������������������������");
			pw.sred("��������������������������������");
		}
	}
	
	/**
	 * �����һ�У�����˥smallpink��������ʱsmallblue��smallblack
	 */
	public void pLine1(int gong) throws BadLocationException{	
		int len = COL; 
		if(gong==5 && iszf) {
			pw.swhite(speat(len,FILL1));
			return;
		}
		
		//1. ������˥		
		if(QiMen2.MA || QiMen2.ALL) {
			String gongWS = QiMen.gong_yue[gong][SiZhu.yz];
			pw.spink(gongWS);
			len--;
		}
		
		//2. ʮ����	
		if(gong!=5 && (QiMen2.SJ || QiMen2.ALL)) {
			int[] sj = pub.getSJ12(gong);
			pw.sblack("��"+QiMen2.SHENJ[sj[0]][2]); 
			len=len-2;
			if(sj[1]!=0) {
				pw.sblack("��"+QiMen2.SHENJ[sj[1]][2]);
				len=len-2;
			}
		}
		
		//3. �Ƿ�������ʱ��
		if(QiMen2.MA || QiMen2.ALL) {			
			if(pub.isYimaOfRi(gong)) {pw.sblack("��"+QiMen.gGejuDesc[36][2]); len=len-2;}
			if(pub.isYima(gong)) {pw.sblack("��");pw.sred(QiMen.gGejuDesc[37][2]); len=len-2;}
			
			//4. �Ƿ����տա�ʱ��			
			if(pub.isKong(gong, pub.RIKONGWANG)) {pw.sblack("��"+QiMen.gGejuDesc[38][2]); len=len-2;}
			if(pub.isKong(gong, pub.SHIKONGWANG)) {pw.sblack("��");pw.sred(QiMen.gGejuDesc[39][2]); len=len-2;}
		}
		//5. ʣ�ಹ�ո�
		pw.swhite(speat(len,FILL1));
	}
	/**
	 * ����ڶ��У���Ҫ���׸�smallblack-19
	 * �ڶ��С���������ϲ��������ֹ��������
	 */
	//���ÿ�������е�����
	//private Map<Integer, String> lineMap = new HashMap<Integer, String>();
	public void pLine2(int gong) throws BadLocationException{
		int len = COL;
		StringBuilder sb  = new StringBuilder();
		if((gong==5 && iszf) || (!QiMen2.JXGE && !QiMen2.ALL)) {
			pw.swhite(speat(len,FILL1));
			return;
		}		
		
		//1. �Ƿ�������ʱ�񡢷ɸɷ���
		String ge = "";
		int[] tgan = pub.getTpjy(gong);
		int[] dgan = pub.getDpjy(gong);
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==ng || dgan[1]==ng)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(9); //"�����";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==yg || dgan[1]==yg)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(10); //"���¸�";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==rg || dgan[1]==rg)) {
			len-=6; 
			ge += QiMen.DUN+getGjname(11)+QiMen.DUN+getGjname(24);  //"���ո񡢷���";
		}
		if((tgan[0]==rg || tgan[1]==rg) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(13); //"���ɸ�";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==sg || dgan[1]==sg)) {
			len-=3;
			ge += QiMen.DUN+getGjname(12); //"��ʱ��";
		}
		sb.append(ge);
		
		//2. �ɹ���������ʮ�ɿ�Ӧ�����ˣ���ȥʡ��
//		ge = "";
//		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) {
//			len-=3; ge += "������";
//		}
//		else if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
//			len-=3; ge += "���ɹ�";
//		}
//		sb.append(ge);
		
		//3. 㣸� ��+�����+�����+������ʱ
		ge="";
		if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.BING || dgan[1]==YiJing.BING)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(16); //"��㣸�";
		}
		else if((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && 
				(dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[1]==ng ||dgan[1]==yg ||dgan[1]==rg ||dgan[1]==sg)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(16); //"��㣸�";
		}
		sb.append(ge);
		
		//4. �������ţ������������ʱ�� �������ڱΣ��������ɣ�����ʱ�ɡ�
//		ge = "";
//		if((tgan[0]==YiJing.GUI || tgan[1]==YiJing.GUI) && (dgan[0]==sg || dgan[1]==sg)) {
//			len-=3; 
//			ge += QiMen.DUN+getGjname(17); //"������";
//		}
//		else if((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==sg || dgan[1]==sg)) {
//			len-=3; 
//			ge += QiMen.DUN+getGjname(18); //"���ر�";
//		}
//		sb.append(ge);
		
		//5. ֱ������Ϊ�����̼��ӣ����̼��磻���̼��������̼׳������̼��꣬���̼��������ֻ��������������޷���
//		boolean iszhifan = ((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
//			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) ||
//			((tgan[0]==YiJing.JI || tgan[1]==YiJing.JI) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN)) ||
//			((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
//			((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI)) ||
//			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG));
//		if(iszhifan) {
//			sb.append(QiMen.DUN+getGjname(20)); //��ֵ������
//			len-=3;
//		}
		
		//6. �����ʹ������������ٵ��̼�������磻���̱�����ٵ��̼��ӻ���ꣻ���̶�����ٵ��̼׳������
		boolean is3 = (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI || dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN);
		is3 = is3 || ((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG));
		is3 = is3 || ((tgan[0]==YiJing.DING || tgan[1]==YiJing.DING) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN || dgan[0]==YiJing.GUI || dgan[1]==YiJing.GUI));
		if(is3) {
			sb.append(QiMen.DUN+getGjname(21) ); //"����ʹ"
			len-=3;
		}
		
		//7. ��Ů���ţ�����ֱʹ���ٵ��̶���
		if(zhishigong==gong && (dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			sb.append(QiMen.DUN+getGjname(22)); //"������"
			len-=3;
		}
		
		//8. ������ ����������ֵ��֮��Ϊ����
//		if(gong==zhifugong && (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI || dgan[0]==YiJing.YI || dgan[1]==YiJing.YI ||
//				tgan[0]==YiJing.BING || tgan[1]==YiJing.BING || dgan[0]==YiJing.BING || dgan[1]==YiJing.BING ||
//				tgan[0]==YiJing.DING || tgan[1]==YiJing.DING || dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
//			sb.append(QiMen.DUN+getGjname(23));  //"������"
//			len-=3;
//		}
		
		int kaimen = pub.getMenGong(QiMen.MENKAI);
		int xiumen = pub.getMenGong(QiMen.MENXIU);
		int shmen = pub.getMenGong(QiMen.MENSHENG);
		int jing3 = pub.getMenGong(QiMen.MENJING3);
		int du = pub.getMenGong(QiMen.MENDU);
		int jing1 = pub.getMenGong(QiMen.MENJING1);
		int shang = pub.getMenGong(QiMen.MENSHANG);
		int si = pub.getMenGong(QiMen.MENSI);
		int shenyin = pub.getShenGong(QiMen.SHENYIN);
		int shenhe = pub.getShenGong(QiMen.SHENHE);
		int shendi = pub.getShenGong(QiMen.SHENDI);
		int shentian = pub.getShenGong(QiMen.SHENTIAN);
		boolean isyi = tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI ||dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI; 
		boolean isbing = tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING ||dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING;
		boolean isding = tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING ||dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING;
		boolean isren = tgan[0]==YiJing.REN ||tgan[1]==YiJing.REN ||dgan[0]==YiJing.REN ||dgan[1]==YiJing.REN;
		boolean isji = tgan[0]==YiJing.JI ||tgan[1]==YiJing.JI ||dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI;
		boolean isgui = tgan[0]==YiJing.GUI ||tgan[1]==YiJing.GUI ||dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI;
		//9. ��թ
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenyin==gong)) {
			sb.append(QiMen.DUN+getGjname(420));//"����թ"
					len-=3;
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenhe==gong)) {
			sb.append(QiMen.DUN+getGjname(421));//"����թ"
			len-=3;
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shendi==gong)) {
			sb.append(QiMen.DUN+getGjname(422));//"����թ"
					len-=3;
		}
		
		//10. ���
		if(jing3==gong && (isyi || isbing || isding) && shentian==gong) {
			sb.append(QiMen.DUN+getGjname(423));//"�����"
					len-=3;
		}
		if(du==gong && (isgui || isji || isding) && (shenyin==gong || shendi==gong || shenhe==gong)) {
			sb.append(QiMen.DUN+getGjname(424));//"���ؼ�"
					len-=3;
		}
		if(jing1==gong && isren && shentian==gong) {
			sb.append(QiMen.DUN+getGjname(425));//"���˼�"
			len-=3;
		}
		if(shang==gong && (isgui || isji || isding) && (shendi==gong || shenhe==gong)) {
			sb.append(QiMen.DUN+getGjname(426));//"�����"
			len-=3;
		}
		if(si==gong && (isgui || isji || isding) && shendi==gong ) {
			sb.append(QiMen.DUN+getGjname(427));//"�����"
			len-=3;
		}
		
		//11. �Ŷ�
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && xiumen==gong && shenyin==gong) {
			sb.append(QiMen.DUN+getGjname(428));//"���˶�"
			len-=3;
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && shentian==gong) {
			sb.append(QiMen.DUN+getGjname(429));//"�����"
			len-=3;
		}
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && du==gong && shendi==gong) {
			sb.append(QiMen.DUN+getGjname(430));//"�����"
			len-=3;
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && (dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)) {
			sb.append(QiMen.DUN+getGjname(431));//"�����"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && kaimen==gong && (dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI)) {
			sb.append(QiMen.DUN+getGjname(432));//"���ض�"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN)) {
			sb.append(QiMen.DUN+getGjname(433));//"���ƶ�"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI || gong==1)) {
			sb.append(QiMen.DUN+getGjname(434));//"������"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && gong==4) {
			sb.append(QiMen.DUN+getGjname(436));//"�����"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && xiumen==gong && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN || gong==8)) {
			sb.append(QiMen.DUN+getGjname(435));//"������"
			len-=3;
		}		

		//12. ������������������ң�����������
		if((tgan[0]==YiJing.GENG ||tgan[1]==YiJing.GENG) && (
				dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI || 
				dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING ||
				dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)){
			sb.append(QiMen.DUN+getGjname(25));//"�����"
			len-=3;
		}
		
		//13 ʱ����Ĺ�����Ǹ���Ĺ
//  	if((SiZhu.sg==YiJing.BING && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.BING) ||
//  			(SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.WUG) ||
//  			(SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.DING) ||
//  			(SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.JI) ||
//  			(SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.CHEN && daoqm.gInt[2][3][4]==YiJing.REN) ||
//  			(SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.WEI && daoqm.gInt[2][3][2]==YiJing.GUI)) {
//  		if(pub.gettgWS(SiZhu.sg, SiZhu.sz)[0].equals("1"))
//  			sb.append(QiMen.DUN+getGjname(8));//"��ʱ�����"
//  		else
//  			sb.append(QiMen.DUN+getGjname(7));//"��ʱ����Ĺ"
//  		len-=3;
//  	}
			
		//14. �ݼ׿������׼ӻ�����
//		if(QiMen.gan_xing[tgan[0]][daoqm.gInt[2][1][gong]]!=0 || QiMen.gan_xing[tgan[1]][daoqm.gInt[2][1][gong]]!=0) {
//			sb.append(QiMen.DUN+getGjname(437));//"���ݿ�"
//			len-=3;
//		}
		String strLine3 = this.pLine3(gong,false);
		if(strLine3!=null) {
			sb.append(QiMen.DUN+strLine3);
			sb.delete(sb.length()-1, sb.length());
			len -= strLine3.length();
		}
		if(sb.length()>0) {
			sb.delete(0, 1);
			len++;
			pw.sblack(sb.toString());			
		}
		pw.swhite(speat(len,FILL1));
	}
	/**
	 * ��������У��ո�smallblack-19
	 * �ܳ���������һ��Ϊ��������ڶ���Ϊ�����������������ڶ���Ϊnull������null����ʾΪ��5��
	 * �ڶ���ѭ��ʱ�������3�г���������2�У������2�г���������3��
	 * ���2�в��ᳬ��������3ֱ����������е�����
	 */
	private void proc(StringBuilder sb1, StringBuilder sb2, String s) {
		if(sb1.length()+s.length()-1 > COL) sb2.append(s);
		else sb1.append(s);
	}
	public String pLine3(int gong) throws BadLocationException{
		return pLine3(gong, true);
	}
	private String pLine3(int gong,boolean w) throws BadLocationException{
		if((gong==5 && iszf) || (!QiMen2.SGKY && !QiMen2.ALL)) {
			if(w) pw.swhite(speat(COL,FILL1));
			return null;
		}
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		int[] t = pub.getTpjy(gong);
		int[] d = pub.getDpjy(gong);
		
		//1. ���ʮ�ɿ�Ӧ
		Integer[] ge = pub.getJixiongge2(gong,iszf);
		for(Integer g : ge) {
			//sb1.append(QiMen.gGejuDesc[g][0]+QiMen.DUN);
			proc(sb1,sb2,getGjname(g)+QiMen.DUN);
		}
		
		//2. ������Ĺ������������
		
		//3. �������̣��ֽ��������ƣ�
		if(((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING ) && gong==1 ) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING || t[0]==YiJing.DING || t[1]==YiJing.DING) && 
				 (d[0]==YiJing.REN || d[1]==YiJing.REN || d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
				 ((d[0]==YiJing.BING || d[1]==YiJing.BING || d[0]==YiJing.DING || d[1]==YiJing.DING) && 
						 (t[0]==YiJing.REN || t[1]==YiJing.REN || t[0]==YiJing.GUI || t[1]==YiJing.GUI)) ||
						 ((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==6) ||
						 ((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG || d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
						 ((d[0]==YiJing.YI || d[1]==YiJing.YI) && (t[0]==YiJing.GENG || t[1]==YiJing.GENG || t[0]==YiJing.XIN || t[1]==YiJing.XIN)))
			//sb.append(getGjname(26)+QiMen.DUN  );//"���ơ�"
			proc(sb1,sb2,getGjname(26)+QiMen.DUN);
		
		//4. �����������������𹬣�Ϊ�ճ���ɣ�����浽�빬��Ϊ���ն��ţ����浽�ҹ���Ϊ�Ǽ�������
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3)
			proc(sb1,sb2,getGjname(27)+QiMen.DUN); //sb.append(getGjname(27)+QiMen.DUN  );//"�ճ���ɣ��"
		if((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==9)
			proc(sb1,sb2,getGjname(28)+QiMen.DUN); //sb.append(getGjname(28)+QiMen.DUN  );//"���ն��š�"
		if((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==7)
			proc(sb1,sb2,getGjname(29)+QiMen.DUN); //sb.append(getGjname(29)+QiMen.DUN  );//"�Ǽ�������"
		
		//5. ����֮�飺������ļ�������ϵ���ݻ���������ߣ�Ϊ�������顢���¾㼪��
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI ||
				t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) &&
				(daoqm.gInt[1][1][gong]==QiMen.SHENYIN || daoqm.gInt[1][1][gong]==QiMen.SHENDI || 
						daoqm.gInt[1][1][gong]==QiMen.SHENHE || daoqm.gInt[1][1][gong]==QiMen.SHENTIAN ) && 
						(daoqm.gInt[3][1][gong]==QiMen.MENKAI || daoqm.gInt[3][1][gong]==QiMen.MENXIU || daoqm.gInt[3][1][gong]==QiMen.MENSHENG)) 
			proc(sb1,sb2,getGjname(30)+QiMen.DUN); //sb.append(getGjname(30)+QiMen.DUN  );//"���顢"
		
		//6. ����»λ���ҵ��𡢱����㡢������Ϊ��»֮λ
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==4) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==9))
			proc(sb1,sb2,getGjname(31)+QiMen.DUN); //sb.append(getGjname(31)+QiMen.DUN ); //"��»��"
		
		//7. ������ϣ��Ҹ�������������Ϊ��ϣ����׼�Ϊ�Ǻϣ��ü��ţ������к�֮�����ͽ⡢�˽ᡢƽ�֡�ƽ�֡�
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG)) ||
				((t[0]==YiJing.GENG || t[1]==YiJing.GENG) && (d[0]==YiJing.YI || d[1]==YiJing.YI)) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING) && (d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
				((t[0]==YiJing.XIN || t[1]==YiJing.XIN) && (d[0]==YiJing.BING || d[1]==YiJing.BING)) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING) && (d[0]==YiJing.REN || d[1]==YiJing.REN)) ||
				((t[0]==YiJing.REN || t[1]==YiJing.REN) && (d[0]==YiJing.DING || d[1]==YiJing.DING)))
			proc(sb1,sb2,getGjname(32)+QiMen.DUN); //sb.append(getGjname(32)+QiMen.DUN  );//"��ϡ�"
		else if(((t[0]==YiJing.WUG || t[1]==YiJing.WUG) && (d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
				((t[0]==YiJing.GUI || t[1]==YiJing.GUI) && (d[0]==YiJing.WUG || d[1]==YiJing.WUG)) ||
				((t[0]==YiJing.JI || t[1]==YiJing.JI) && (gong==zhifugong)) ||
				((gong==zhifugong) && (d[0]==YiJing.JI || d[1]==YiJing.JI)))
			proc(sb1,sb2,getGjname(33)+QiMen.DUN); //sb.append(getGjname(33)+QiMen.DUN  );//"�Ǻϡ�"
		
		if(w) {
			sb1 = sb1.delete(sb1.length()-1, sb1.length());
			pw.sblack(sb1.toString());
			pw.swhite(speat(COL-sb1.length(),FILL1));
		}
		
		return sb2.toString();
	}
	/**
	 * ��������У�����
	 * �ո�smallblack-2,����bigblack(bigred)-1���ո�smallblack-4������̳����smallblack-10
	 */
	public void pLine4(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {
			pw.swhite(speat(COL,FILL1));  //�����幬��������¸ɡ������̳����Ĺ���ѷ�Ӧ���������ڹ���������ڹ���
			return ;
		}
		pw.swhite(speat(LINE4[0],FILL1));
		
		int shen = daoqm.gInt[1][1][gong]; //��������
		if(shen == QiMen.SHENFU) {
			if(iszf) pw.bred(QiMen.bs1[shen]);
			else {
				if(daoqm.getJu()>0) pw.bred(QiMen.fpjs1[shen]);
				else pw.bred(QiMen.fpjs2[shen]);
			}
		}	else {
			if(iszf) pw.bblack(QiMen.bs1[shen]);
			else {
				if(daoqm.getJu()>0) pw.bblack(QiMen.fpjs1[shen]);
				else pw.bblack(QiMen.fpjs2[shen]);
			}
		}
		  
		pw.swhite(speat(LINE4[1],FILL1));
		
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(pub.isTDXing(gong)) { //������������
				str += this.getGjname(40); //"��";
				i++;
			}
			if(pub.isTDChong(gong)) {  //�������̳�
				str += this.getGjname(41); //"��";
				i++;
			}
			if(pub.isTDGanHe(gong)) {//�����ظ�����
				str += this.getGjname(42); //"��";
				i++;
			}
			if(pub.isTDG3He(gong)) {//�����ع�����
				str += this.getGjname(43); //"�\";
				i++;
			}else if(pub.isTDZi3He(gong)) {//�����ذ�ϣ��������������
				str += this.getGjname(44); //"�Q";
				i++;
			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE4[2]-i,FILL1));	
		pw.bwhite(speat(LINE4[3],FILL1));		
		pw.swhite(speat(LINE4[4],FILL2));
	}

	/**
	 * ��������У�
	 * LINE7 = {1,3,1,1,3,1}��FILL1���ַ��ո�FILL2һ�ַ��ո�COL=15
	 * �ո����ǽ��ǣ��ո񣬵��̰��񣬿ո񣬰���12���ո�8��
	 */
	public void pLine7(int gong) throws BadLocationException{
		if(gong==5 && iszf) {
			pw.swhite(speat(2,FILL2));
			pw.swhite(speat(10,FILL1));
			
			if(QiMen2.HUO || QiMen2.ALL) {
				//���ɣ�����ʦ��
				int angan = pub.getAngan(gong);
				pw.sblack(YiJing.TIANGANNAME[angan]);  
				//���֧����ɼ����ɣ�ֻ�Ƕ��һ������˴�ֻҪ��֧
				if(angan==YiJing.DING) angan = YiJing.YI;
				else if(angan==YiJing.YI) angan = YiJing.DING;
				pw.sblack(YiJing.DIZINAME[pub.getHuozi(angan)]);
			}else
				pw.swhite(speat(2,FILL1));
			
			pw.swhite(speat(1,FILL1));
			pw.swhite(speat(2,FILL2));
			return ;
		}
		//pw.swhite(speat(LINE7[0],FILL1));
		pw.swhite(speat(2,FILL2));
		
		if(QiMen2.JTXMS || QiMen2.ALL) {
			//ز�������ǽ���
			pw.sblack(QiMen.jx1[pub.getTuiXing(gong)]);	 
			pw.sblack(QiMen.jx1[pub.getJinXing(gong)]);
			pw.swhite(speat(LINE7[2],FILL1));
			
			//���̰��񣬼����̸��������乬�İ���		
			int dpbs = daoqm.gInt[1][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			if(dpbs==QiMen.SHENFU) {
				if(iszf)
					pw.sred(QiMen.bs1[dpbs]);  
				else {
					if(daoqm.getJu()>0)
						pw.sred(QiMen.fpjs1[dpbs]);
					else
						pw.sred(QiMen.fpjs2[dpbs]);
				}
			}	else {			
				if(iszf)
					pw.sblack(QiMen.bs1[dpbs]);	  
				else {
					if(daoqm.getJu()>0)
						pw.sblack(QiMen.fpjs1[dpbs]);
					else
						pw.sblack(QiMen.fpjs2[dpbs]);
				}
			}
			pw.swhite(speat(LINE7[2],FILL1));
			
			//���̰���
			int dpbm = daoqm.gInt[3][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			int zhishimen = daoqm.gInt[3][1][zhishigong];
			if(zhishimen==dpbm) pw.sred(QiMen.bm1[dpbm]);
			else pw.sblack(QiMen.bm1[dpbm]);		
			pw.sblack(QiMen.bm1[gong]);  //���š�����ȥ��Ҳ����֮��
			pw.sblack(QiMen.bm1[pub.getYinmen(gong)]);	//���ţ���δ��
			pw.swhite(speat(LINE7[3],FILL1));
		}else{
			pw.swhite(speat(9,FILL1));
		}
		
		if(QiMen2.HUO || QiMen2.ALL) {
			//����1������ʦ�ã����幬û�а��ɣ���û����; 
			String angan_z = YiJing.TIANGANNAME[daoqm.gInt[4][5][daoqm.gInt[3][1][gong]]];
			pw.sblack(angan_z==null || angan_z.equals("")?FILL1:angan_z);  
			//����2������ʦ��
			int angan = pub.getYaoAngan(gong);
			pw.sblack(YiJing.TIANGANNAME[angan]);  
			//���֧����ɼ����ɣ�ֻ�Ƕ��һ������˴�ֻҪ��֧
			if(angan==YiJing.DING) angan = YiJing.YI;
			else if(angan==YiJing.YI) angan = YiJing.DING;
			pw.sblack(YiJing.DIZINAME[pub.getHuozi(angan)]);
			//����3������ʦ����������
			int wangan = pub.getWangAngan(gong);
			pw.sblack(YiJing.TIANGANNAME[wangan]);
		}else
			pw.swhite(speat(4,FILL1));
		
		pw.swhite(speat(0,FILL1));
		pw.swhite(speat(2,FILL2));
	}
	
	/**
	 * ��������У���+���̸�
	 * ���Ź�����˥smallblack-3������bigblack(bigred)-1�����ǹ�����˥smallblack-4������bigblack-2���ͳ����Ĺsmallblack-6
	 */
	public void pLine52(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {    //�������幬��������̸��������乬����˥����							
			pw.swhite(speat(2,FILL1));
			pw.bwhite(FILL1);
			
			if(QiMen2.WSXQ || QiMen2.ALL) {							
				int tpjgong = daoqm.getTpJigong();	 //���̸��ڹ���������˥
				String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][tpjgong];
				int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
				String tganyws = YiJing.WXSQXNAME[itganyws];
				pw.sblack(tgangws+tganyws);  //�����˥
			}else{
				pw.swhite(speat(2,FILL1));
			}
			
			pw.bwhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[4], FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}		
  	/////////////////1. ���ڹ���������˥/////////////////////
  	if(QiMen2.WSXQ || QiMen2.ALL) {
  		String mengws =  QiMen.men_gong2[daoqm.gInt[3][1][gong]][gong];
    	String menyws =  QiMen.men_yue[daoqm.gInt[3][1][gong]][SiZhu.yz];
  		pw.sblack(mengws+menyws);
  	}
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. ��������,bm1��fpjm������һ�µģ�����ת��
		int zsgong = getZSgong();		
		if(gong==zhishigong || gong==zsgong) pw.bred(QiMen.bm1[daoqm.gInt[3][1][gong]]);  
		else pw.bblack(QiMen.bm1[daoqm.gInt[3][1][gong]]);
		pw.swhite(speat(LINE5[1],FILL1));
		///////////////////////////////////////////////////
				
		//3. ���ڹ�������˥				
		int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[2][3][gong]]];
		if(QiMen2.WSXQ || QiMen2.ALL) {
			String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[2][3][gong]][gong];			
			String tganyws = YiJing.WXSQXNAME[itganyws];
			pw.sblack(tgangws+tganyws);
		}	else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));	
		
		//4. ���̸�
		if(daoqm.gInt[2][3][gong]==rg || daoqm.gInt[2][3][gong]==sg) {
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}else{
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}		
		
		//5. ���̸ɵ�֧
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[2][3][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}		
		//5. ���̸ɼĹ���
		if(daoqm.isTpJigong(gong) && iszf) { //ֻ��ת�̲���Ҫ�Ĺ�
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//6. �̳��Һ�
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//��ع����ϲ����ж��ˣ���ǰ�������֧ʱ���ж�
				if(pub.isTG6He(gong)) {//�Ƿ������빬����
					i++;
					str += this.getGjname(45); //"��";
				}else if(pub.isTG3He(gong)) {//�Ƿ���
					i++;
					str += getGjname(46); //"Ǣ";
				}
			}
			if(pub.isJixing(gong)) {//�Ƿ������ǻ���
				i++;
				str += getGjname(40); //"��";
			}
			if(pub.isTGChong(gong)) {
				i++;
				str += getGjname(41); //"��";
			}
			if(pub.isTpTaohua(gong)) {
				i++;
				str += getGjname(47); //"��";
			}
//			if(pub.isTGanMu(gong)) {
//				i++;  //��1��2  ��3��4��5
//				str += itganyws<=2 ? getGjname(49):getGjname(48); //"��":"Ĺ";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
		pw.swhite(speat(LINE5[5],FILL2));
	}
	
	//��+���̸�
	public void pLine5(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {    //�������幬��������̸��������乬����˥����							
			/////////////1. ������˥///////////////////////////
			int jigong2 = daoqm.getJiGong();
	  	String xinggws =  QiMen.xing_gong[5][jigong2];
	  	String xingyws =  QiMen.xing_yue[5][SiZhu.yz];
	  	if(QiMen2.WSXQ || QiMen2.ALL) 
	  		pw.sblack(xinggws+xingyws);  //�����������˥
	  	else
	  		pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[0],FILL1));  
			//2. ����
			pw.bblack(QiMen.jx1[5]);  //������
			//////////////////////////////////////

			if(QiMen2.WSXQ || QiMen2.ALL) {							
				int tpjgong = daoqm.getTpJigong();	 //���̸��ڹ���������˥
				String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][tpjgong];
				int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
				String tganyws = YiJing.WXSQXNAME[itganyws];
				pw.sblack(tgangws+tganyws);  //�����˥
			}else{
				pw.swhite(speat(2,FILL1));
			}
			
			pw.bwhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[4], FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}		
		///////////////1. ������˥///////////////////////////////
  	String xinggws =  QiMen.xing_gong[daoqm.gInt[2][1][gong]][gong];
  	String xingyws =  QiMen.xing_yue[daoqm.gInt[2][1][gong]][SiZhu.yz];  	
  	if(QiMen2.WSXQ || QiMen2.ALL)
  		pw.sblack(xinggws+xingyws);  //���ڹ���������˥
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. ����
		pw.bblack(QiMen.jx1[daoqm.gInt[2][1][gong]]);  //����
		pw.swhite(speat(LINE5[1],FILL1));
		//////////////////////////////////////////////////////
				
		//3. ���ڹ�������˥				
		int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[2][3][gong]]];
		if(QiMen2.WSXQ || QiMen2.ALL) {
			String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[2][3][gong]][gong];			
			String tganyws = YiJing.WXSQXNAME[itganyws];
			pw.sblack(tgangws+tganyws);
		}	else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));	
		
		//4. ���̸�
		if(daoqm.gInt[2][3][gong]==rg || daoqm.gInt[2][3][gong]==sg) {
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}else{
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}		
		
		//5. ���̸ɵ�֧
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[2][3][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}		
		//5. ���̸ɼĹ���
		if(daoqm.isTpJigong(gong) && iszf) { //ֻ��ת�̲���Ҫ�Ĺ�
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//6. �̳��Һ�
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//��ع����ϲ����ж��ˣ���ǰ�������֧ʱ���ж�
				if(pub.isTG6He(gong)) {//�Ƿ������빬����
					i++;
					str += this.getGjname(45); //"��";
				}else if(pub.isTG3He(gong)) {//�Ƿ���
					i++;
					str += getGjname(46); //"Ǣ";
				}
			}
			if(pub.isJixing(gong)) {//�Ƿ������ǻ���
				i++;
				str += getGjname(40); //"��";
			}
			if(pub.isTGChong(gong)) {
				i++;
				str += getGjname(41); //"��";
			}
			if(pub.isTpTaohua(gong)) {
				i++;
				str += getGjname(47); //"��";
			}
//			if(pub.isTGanMu(gong)) {
//				i++;  //��1��2  ��3��4��5
//				str += itganyws<=2 ? getGjname(49):getGjname(48); //"��":"Ĺ";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
		pw.swhite(speat(LINE5[5],FILL2));
	}
	/**
	 * ��������У���+���̸�
	 */
	public void pLine6(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {  //�������幬��Ҫ���⴦��					
			pw.swhite(speat(2,FILL1));
			pw.bwhite(FILL1);
			
			//3. ���̸��ڼĹ���������˥
			int jigong = daoqm.getJiGong();
	  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][jigong];
	  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
			String dganyws = YiJing.WXSQXNAME[idganyws];
			if(QiMen2.WSXQ || QiMen2.ALL)
				pw.sblack(dgangws+dganyws);   //������幬���̸���˥
			else
				pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[2],FILL1));

			//4. ���̸�
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			
			//5. ���̸ɵ�֧
			if(QiMen2.HUO || QiMen2.ALL) {
				int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
				pw.sblack(YiJing.DIZINAME[huozi]);
			}else
				pw.swhite(speat(1,FILL1));
				
			pw.bwhite(speat(1,FILL1));  //���һ����ո�
			pw.swhite(speat(LINE5[3]+LINE5[4]-1,FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}
		  	
		/////////////////1. ���ڹ���������˥/////////////////////
  	if(QiMen2.WSXQ || QiMen2.ALL) {
  		String mengws =  QiMen.men_gong2[daoqm.gInt[3][1][gong]][gong];
    	String menyws =  QiMen.men_yue[daoqm.gInt[3][1][gong]][SiZhu.yz];
  		pw.sblack(mengws+menyws);
  	}
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. ��������,bm1��fpjm������һ�µģ�����ת��
		int zsgong = getZSgong();		
		if(gong==zhishigong || gong==zsgong) pw.bred(QiMen.bm1[daoqm.gInt[3][1][gong]]);  
		else pw.bblack(QiMen.bm1[daoqm.gInt[3][1][gong]]);
		//System.out.println("QimenGejuBase: gong="+gong+"men="+daoqm.gInt[3][1][gong]);
		pw.swhite(speat(LINE5[1],FILL1));
		///////////////////////////////////////////////////
		
  	//3. ���̸��ڹ���������˥
  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][gong]][gong];
  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][gong]]];
		String dganyws = YiJing.WXSQXNAME[idganyws];
		if(QiMen2.WSXQ || QiMen2.ALL)
			pw.sblack(dgangws+dganyws);			//���̸��ڹ���������˥
		else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));
		
		//4. ���̸�
		if(daoqm.gInt[4][5][gong]==rg || daoqm.gInt[4][5][gong]==sg)
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		else
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		
		//5. ���̸ɵ�֧
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}
		
		//6. �Ƿ��мĹ����̸�	
		if(daoqm.isJiGong(gong) && iszf) {  //ֻ��ת�̲żĹ�
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//7. �̳��
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//��ع����ϲ����ж��ˣ���ǰ�������֧ʱ���ж�
				if(pub.isDG6He(gong)) {//�Ƿ�����빬����
					i++;
					str += getGjname(45); //"��";
				}else if(pub.isDG3He(gong)) {//�Ƿ���
					i++;
					str += getGjname(46); //"Ǣ";
				}
			}
			if(pub.isDpJixing(gong)) {//�Ƿ������ǻ���
				i++;
				str += getGjname(40); //"��";
			}
			if(pub.isDGChong(gong)) {
				i++;
				str += getGjname(41); //"��";
			}
			if(pub.isDpTaohua(gong)) {
				i++;
				str += getGjname(47); //"��";
			}
//			if(pub.isDGanMu(gong)) {
//				i++;
//				str += idganyws<=2 ? getGjname(49):getGjname(48); //"��":"Ĺ";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
//		if(i<LINE5[4]) {//̫���ˣ�����4���ַ�ʱ��FILL2��Ҫ��С��
//			pw.swhite(speat(LINE5[5],FILL2));
//		}else{
//			pw.swhite(speat(2,FILL2));
//		}
		pw.swhite(speat(LINE5[5],FILL2));
		//System.out.println("gong="+gong+";i="+i);
	}
	
	//��+���̸�
	public void pLine62(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {  //�������幬��Ҫ���⴦��					
			
	  	/////////////1. ������˥///////////////////////////
			int jigong2 = daoqm.getJiGong();
	  	String xinggws =  QiMen.xing_gong[5][jigong2];
	  	String xingyws =  QiMen.xing_yue[5][SiZhu.yz];
	  	if(QiMen2.WSXQ || QiMen2.ALL) 
	  		pw.sblack(xinggws+xingyws);  //�����������˥
	  	else
	  		pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[0],FILL1));  
			//2. ����
			pw.bblack(QiMen.jx1[5]);  //������
			//////////////////////////////////////
			
			//3. ���̸��ڼĹ���������˥
			int jigong = daoqm.getJiGong();
	  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][jigong];
	  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
			String dganyws = YiJing.WXSQXNAME[idganyws];
			if(QiMen2.WSXQ || QiMen2.ALL)
				pw.sblack(dgangws+dganyws);   //������幬���̸���˥
			else
				pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[2],FILL1));

			//4. ���̸�
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			
			//5. ���̸ɵ�֧
			if(QiMen2.HUO || QiMen2.ALL) {
				int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
				pw.sblack(YiJing.DIZINAME[huozi]);
			}else
				pw.swhite(speat(1,FILL1));
				
			pw.bwhite(speat(1,FILL1));  //���һ����ո�
			pw.swhite(speat(LINE5[3]+LINE5[4]-1,FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}
		
  	///////////////1. ������˥///////////////////////////////
  	String xinggws =  QiMen.xing_gong[daoqm.gInt[2][1][gong]][gong];
  	String xingyws =  QiMen.xing_yue[daoqm.gInt[2][1][gong]][SiZhu.yz];  	
  	if(QiMen2.WSXQ || QiMen2.ALL)
  		pw.sblack(xinggws+xingyws);  //���ڹ���������˥
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. ����
		pw.bblack(QiMen.jx1[daoqm.gInt[2][1][gong]]);  //����
		pw.swhite(speat(LINE5[1],FILL1));
		//////////////////////////////////////////////////////
		
  	//3. ���̸��ڹ���������˥
  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][gong]][gong];
  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][gong]]];
		String dganyws = YiJing.WXSQXNAME[idganyws];
		if(QiMen2.WSXQ || QiMen2.ALL)
			pw.sblack(dgangws+dganyws);			//���̸��ڹ���������˥
		else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));
		
		//4. ���̸�
		if(daoqm.gInt[4][5][gong]==rg || daoqm.gInt[4][5][gong]==sg)
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		else
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		
		//5. ���̸ɵ�֧
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}
		
		//6. �Ƿ��мĹ����̸�	
		if(daoqm.isJiGong(gong) && iszf) {  //ֻ��ת�̲żĹ�
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//7. �̳��
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//��ع����ϲ����ж��ˣ���ǰ�������֧ʱ���ж�
				if(pub.isDG6He(gong)) {//�Ƿ�����빬����
					i++;
					str += getGjname(45); //"��";
				}else if(pub.isDG3He(gong)) {//�Ƿ���
					i++;
					str += getGjname(46); //"Ǣ";
				}
			}
			if(pub.isDpJixing(gong)) {//�Ƿ������ǻ���
				i++;
				str += getGjname(40); //"��";
			}
			if(pub.isDGChong(gong)) {
				i++;
				str += getGjname(41); //"��";
			}
			if(pub.isDpTaohua(gong)) {
				i++;
				str += getGjname(47); //"��";
			}
//			if(pub.isDGanMu(gong)) {
//				i++;
//				str += idganyws<=2 ? getGjname(49):getGjname(48); //"��":"Ĺ";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
		pw.swhite(speat(LINE5[5],FILL2));
	}
	

	
	/**
	 * ѭ����������ո��ָ�����ַ�
	 */
	String speat(int len, String fillstr) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<len; i++) sb.append(fillstr);
		
		return sb.toString();
	}
	
	int zhishigong ,zhifugong;  //ֵʹ��ֵ����
	int ng ,yg, rg, sg;  //ȥ���׿�ͷ���ת�����
	boolean isYang;  //�����ֻ�������
	
	ResultPanel resultPane;  //չ�ֽ�������
	MyTextPane text;				 //��ʾ�����������Ϣ
	Document doc ;					 //��ʾ�ṹ���ĵ�
	PrintWriter pw ;
	
	void init() throws BadLocationException {
		text = resultPane.getTextPane();
		//text.setEditable(false);
		doc = text.getDocument();
		doc.remove(0, doc.getLength());
		
		zhifugong = daoqm.getZhifuGong();
		zhishigong = daoqm.getZhishiGong();  //ֵʹ��ֵ����
		ng = pub.getTiangan(SiZhu.ng, SiZhu.nz);  //ȥ���׿�ͷ���ת�����
		yg = pub.getTiangan(SiZhu.yg, SiZhu.yz);
		rg = pub.getTiangan(SiZhu.rg, SiZhu.rz);
		sg = pub.getTiangan(SiZhu.sg, SiZhu.sz);
		
		isYang = daoqm.getJu()>0;
		pw = new PrintWriter();
		pw.setDocument(doc);
		pub.initGlobal();  //�Ż�ȫ�ֱ����ķ����ٶ�
	}
	/**
	 * �ɸ�����õ���ֵļ�����ƣ�����������2�����ڣ���ֱ��ʹ��1
	 * @param ge
	 * @return
	 */
	String getGjname(int ge) {
		//���ֻ�ж�λ���ȣ�����λ���ȵ�ΪNull
		return QiMen.gGejuDesc[ge].length==2 || QiMen.gGejuDesc[ge][2]==null ? QiMen.gGejuDesc[ge][0] :QiMen.gGejuDesc[ge][2];
	}
	//�õ�ֵʹ��
	int getZSgong() {
		int zsgong = zhishigong;
		if(iszf && zhishigong==5) zsgong = daoqm.getJiGong(); //ת���мĹ�
		return zsgong;
	}
}