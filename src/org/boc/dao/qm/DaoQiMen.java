package org.boc.dao.qm;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;

public class DaoQiMen {
  private StringBuffer str;
  private DaoYiJingMain daoyj;
  private DaoPublic pub;
  private DaoCalendar daoc;
  private int whichJie; //�������ν�
  private int whichYuan; //��������Ԫ
  private int whichJu;   //�������ξ�
	private int igJigong=2;  //���幬�ĺι���ȱʡ��������
  /**
   * gInt[0]=������֧4 �� ����8 ��Ԫ11 ����12 ֵ��ʹ13 ֵ���乬14 ֵʹ�乬 Сֵ��15
   * gInt[1][1][1-9]=����,gInt[1][2][1-9]=��������
   * gInt[2][1][1-9]=����˳������2��������,3�������ǣ�������У��������ǲ�֧
   * gInt[3][1][1-9]=��������,gInt[3][2]=������
   * gInt[4][1][1-9]=�Ź������������2�������У�3���̾������ڵĹ���4���̰��ţ�5�������ǣ�6�����������С�7�������׵�֧
   */
  public int[][][] gInt;

  public DaoQiMen() {
      str = new StringBuffer();
      daoyj = new DaoYiJingMain();
      pub = new DaoPublic();
      daoc = new DaoCalendar();
      gInt = new int[5][10][16];
  }
  
  public DaoCalendar getDaoCalendar() {
  	return this.daoc;
  }
  //�������幬�ĺι�����delQimenMain����
  public void setJigong(int jigong) {
  	this.igJigong = jigong; 
  }
  public int getTheJigong() {
  	return this.igJigong ; 
  }
  public DaoPublic getPublic() {
  	return pub;
  }
  /**
   * �õ��������ף�ÿ�������ľ����ʽΪ��
   *   ��X��A��X��X��B��X��X��C��X��X��D��X��Y��Z��
   *   A+B:L;gan+gong; men+men; men+gan; men+xing; gan+xing; men+gan+shen; gan+men+gan; gan+men+gon; 
   * X=����������,A=���̸ɣ�B=���̸ɣ�C=��������D=��������Y=��/��/��/��/Ĺ


   * gInt[0]=������֧ ���� ��Ԫ ���� ֵ��ʹ ֵ���乬 ֵʹ�乬 Сֵ��
   * gInt[1][1][1-9]=����,gInt[1][2][1-9]=��������
   * gInt[2][1][1-9]=����˳������2��������,3�������ǣ�������У��������ǲ�֧
   * gInt[3][1][1-9]=��������,gInt[3][2]=������
   * gInt[4][1][1-9]=�Ź������������2�������У�3���̾��ǣ�4���̰��ţ�5�������ǣ�6�����������С�7�������׵�֧

	������ʾÿһ������Ϣ
  1. �����������˥��gong_yue[][]
  2. �����ڹ�����˥���������˥��gan_gong[][],gan_yue[][]
  3. �����ڹ����������˥: xing_gong[][], xing_yue[][]
  4. �������������˥�͹��Ĺ�ϵ���ơ��ȡ��͡��塢Ĺ : men_yue[][],men_gong[][]
  
  6. ��+��:gan_gan[][]
  7. ���̸�+��:gan_gong[][],gan_gong_t[][], ���̸�+����gan_gong[][]
  8. ��+��:men_men[][]
  9. ��+��:men_gan[][]
  10. ��+��:men_xing[][]
  11. ��+��+��men_gan_shen[][][]
  12. ��+��+��: gan_men_gan[][][]
  13. ��+��+��: gan_men_gong[][][]
  14. ��+�� ��gan_xing[][]
   */
  public String[] getJiXiongOfGong() {
    String[] s = new String[1000];
    String[] geHelp = new String[500];  //�Ը�ֵİ������������õ�
    int index = 0;  //Ϊs�����Զ��ۼ���
    int ihelp = 0;  //ΪgeHelp�����Զ��ۼ���
    StringBuffer sb = new StringBuffer();
    
    geHelp[0]="====================================������Ϣ====================================";
    geHelp[++ihelp]="|    ������ʱ���ֱ�Ϊ��ɡ��¸ɡ��ոɡ�ʱ���乬��";
    geHelp[++ihelp]="|    ��    �i����ָ�ù���ʱ��Ѯ�գ��iָ�ù�������Ѯ�գ�";
    geHelp[++ihelp]="|    Ĺ �� ڣ��Ĺָ�����̸��乬��Ĺ�أ���ָ���̸����������൫�乬��Ĺ��ڣָ���̸��乬��Ĺ";
    geHelp[++ihelp]="|    ��    �R����ָ�ù�Ϊʱ֧�����ǣ��Rָ�ù�Ϊ��֧�����ǣ�";
    geHelp[++ihelp]="|    ��    ����ָ���̸��ڸù�����ԡ֮�أ�";
    geHelp[++ihelp]="|    �������Σ���Ϊ���̸����乬���ǻ��̣���Ϊ���̸����乬���ǻ��̣���Ϊ����̵�֧���̣���Ϊ������ʱ��֧������乬��֧���̣�";
    geHelp[++ihelp]="|    ��    �䣺��ָ���̸����乬���壬��ָ���̸�����̸����壻";
    geHelp[++ihelp]="|    �Q�\��Ǣ����ָ����̸����ϣ��Qָ�����֧���ϣ��\ָ������빬֧���ϣ���Ϊ�����빬֧���ϣ�ǢΪ�����빬֧���ϣ�";
    geHelp[++ihelp]="--------------------------------------------------------------------------------";
    
    
    for(int i=1; i<=9; i++) {
    	if(i==5) continue;    	
	     sb.append("  "+i+"����-��"+QiMen.gong_yue[i][SiZhu.yz]+"��");  //1��--������
	     sb.append(YiJing.TIANGANNAME[gInt[2][3][i]]);
	     sb.append("��"+QiMen.gan_gong_wx[gInt[2][3][i]][i]);
	     sb.append("��"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[2][3][i]][SiZhu.yz]]+"��"); //�������+��������
	     if(isTpJigong(i)) {//������̾���˳��Ϊ2��������Ҫͬʱ��ʾ��5������������
	    	 sb.append(YiJing.TIANGANNAME[gInt[4][5][5]]);
	    	 sb.append("��"+QiMen.gan_gong_wx[gInt[4][5][5]][i]);
      	 sb.append("��"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][5]][SiZhu.yz]]+"��");//��������5��
       }
	     
	     if(gInt[2][3][i]!=gInt[4][5][i]) {  //ֻ�����̸ɲ����ڵ��̸ɲ���Ҫ�ж�
		     sb.append(YiJing.TIANGANNAME[gInt[4][5][i]]);
		     sb.append("��"+QiMen.gan_gong_wx[gInt[4][5][i]][i]);
		     sb.append("��"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][i]][SiZhu.yz]]+"��"); //�������������	     
		     if(this.isJiGong(i)) {
		    	 sb.append(YiJing.TIANGANNAME[gInt[4][5][5]]);
	      	 sb.append("��"+QiMen.gan_gong_wx[gInt[4][5][5]][i]);
	      	 sb.append("��"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][5]][SiZhu.yz]]+"��");//��������5��
	       }
	     }
	     
	     sb.append(QiMen.jx1[gInt[2][1][i]]);
	     sb.append("��"+QiMen.xing_gong[gInt[2][1][i]][i]);
	     sb.append("��"+QiMen.xing_yue[gInt[2][1][i]][SiZhu.yz]+"��"); //�������ڹ����µ���˥
       
	     sb.append(QiMen.bm1[gInt[3][1][i]]);
	     sb.append("��"+QiMen.men_yue[gInt[3][1][i]][SiZhu.yz]);
	     sb.append("��"+QiMen.men_gong[gInt[3][1][i]][i]+"��"); //�������º͹�����˥
	     
	     s[++index] = sb.toString();
       sb.delete(0, sb.length());
              
	     int ganganNum = QiMen.gan_gan[gInt[2][3][i]][gInt[4][5][i]];
	     sb.append("    ����"+QiMen.gGejuDesc[ganganNum][0]+"��");  //�����+��
	     int ganganNum2 = 0; 
	     if(this.isJiGong(i)) {//��Ҫ���ϵ����幬�ĸ�
	    	 ganganNum2 = QiMen.gan_gan[gInt[2][3][i]][gInt[4][5][5]];
		     sb.append(QiMen.gGejuDesc[ganganNum2][0]+"��");
	     }
	     int ganganNum5 = 0;
	     if(isTpJigong(i)) {//������̾���˳��Ϊ2������Ҫ���������幬�ĸ�
	    	 ganganNum5 = QiMen.gan_gan[gInt[4][5][5]][gInt[4][5][i]];
	    	 if(ganganNum5!=0) sb.append(QiMen.gGejuDesc[ganganNum5][0]+"��"); 
	     }
	     
	     int gangongNum_t = QiMen.gan_gong[gInt[2][3][i]][i];  //���̸�+��	    
	     if(gangongNum_t!=0) sb.append(QiMen.gGejuDesc[gangongNum_t][0]+"��");  //������̸�+��	     
	     if(isTpJigong(i)) {//������̾���˳��Ϊ2��
	    	 gangongNum_t = QiMen.gan_gong[gInt[4][5][5]][i];
	    	 if(gangongNum_t!=0) sb.append(QiMen.gGejuDesc[gangongNum_t][0]+"��");  //����幬�����̸�+��
	     }
	     
	     int gangongNum_t1 = QiMen.gan_gong_t[gInt[2][3][i]][i];  //���̸�+����ֻ���������̸ɣ��������ǻ���
	     if(gangongNum_t1!=0) sb.append(QiMen.gGejuDesc[gangongNum_t1][0]+"��");  //������ǻ���
	     
	     int gangongNum_t2 = QiMen.gan_gong_ch[gInt[2][3][i]][i];
	     if(gangongNum_t2!=0) sb.append(QiMen.gGejuDesc[gangongNum_t2][0]+"��");  //���֧������Ϣ
	     
	     int gangongNum_d = 0;
	     if(gInt[2][3][i]!=gInt[4][5][i])  //ֻ�����̸ɲ����ڵ��̸ɲ���Ҫ�ж�
	    	 gangongNum_d = QiMen.gan_gong[gInt[4][5][i]][i];	//���̸�+��
	     if(gangongNum_d!=0) sb.append(QiMen.gGejuDesc[gangongNum_d][0]+"��");  //������̸�+��
	     
	     //������+������
	     int menmenNum = QiMen.men_men[gInt[3][1][i]][QiMen.dp_mengong_mc[i]];  
	     if(menmenNum!=0) sb.append(QiMen.gGejuDesc[menmenNum][0]+"��");
	     
	     //��+��,������+���̸ɣ���+���̸�
	     int menganNum = QiMen.men_gan[gInt[3][1][i]][gInt[2][3][i]];  
	     if(menganNum!=0) sb.append(QiMen.gGejuDesc[menganNum][0]+"��");	     
	     int menganNum2 = 0;
	     if(this.isJiGong(i)) {//������̾���˳��Ϊ2��
	    	 menganNum2 = QiMen.men_gan[gInt[3][1][i]][gInt[4][5][5]];  
		     if(menganNum2!=0) sb.append(QiMen.gGejuDesc[menganNum2][0]+"��");
	     }
	     int menganNum3 = 0;   
	     if(gInt[2][3][i]!=gInt[4][5][i] && menganNum3!=0) {
	    	 menganNum3 = QiMen.men_gan[gInt[3][1][i]][gInt[4][5][i]];
	    	 sb.append(QiMen.gGejuDesc[menganNum3][0]+"��");
	     }
	     int menganNum4 = 0;
	     if(menganNum3!=0 && isTpJigong(i)) {//������̾���˳��Ϊ2��,ֻ��menganNum3��=0����Ҫ�����ж�
	    	 menganNum4 = QiMen.men_gan[gInt[3][1][i]][gInt[4][5][5]];  
		     if(menganNum4!=0) sb.append(QiMen.gGejuDesc[menganNum4][0]+"��");
	     }
	     
	     //��+��
	     int xingmenNum = QiMen.xing_men[gInt[2][1][i]][QiMen.dp_menxing_mc[gInt[3][1][i]]];  
	     if(xingmenNum!=0) sb.append(QiMen.gGejuDesc[xingmenNum][0]+"��");
	     int xingmenNum2 = 0;
	     if(isTpJigong(i)) {//������̾���˳��Ϊ2��
	    	 xingmenNum2 = QiMen.xing_men[5][QiMen.dp_menxing_mc[gInt[3][1][i]]];  
		     if(xingmenNum2!=0) sb.append(QiMen.gGejuDesc[xingmenNum2][0]+"��");
	     }
	     
	     //��+��+��
	     int mganshenNum = QiMen.men_gan_shen[gInt[3][1][i]][gInt[2][3][i]][gInt[1][1][i]];  
	     if(mganshenNum==0) mganshenNum = QiMen.men_gan_shen[gInt[3][1][i]][gInt[4][5][i]][gInt[1][1][i]];
	     if(mganshenNum!=0) sb.append(QiMen.gGejuDesc[mganshenNum][0]+"��");
	     //��+���̸�+��
	     int mtganshenNum = QiMen.men_tgan_shen[gInt[3][1][i]][gInt[2][3][i]][gInt[1][1][i]];  
	     if(mtganshenNum!=0) sb.append(QiMen.gGejuDesc[mtganshenNum][0]+"��");
	     
	     //��+��+��
	     int ganmenganNum = QiMen.gan_men_gan[gInt[2][3][i]][gInt[3][1][i]][gInt[4][5][i]];  
	     if(ganmenganNum!=0) sb.append(QiMen.gGejuDesc[ganmenganNum][0]+"��");
	     int ganmenganNum2 = 0;
	     if(isTpJigong(i)) {//������̾���˳��Ϊ2��
	    	 ganmenganNum2 = QiMen.gan_men_gan[gInt[4][5][5]][gInt[3][1][i]][gInt[4][5][i]];  
		     if(ganmenganNum2!=0) sb.append(QiMen.gGejuDesc[ganmenganNum2][0]+"��");
	     }
	     
	     //��+��+��
	     int ganmengongNum = QiMen.gan_men_gong[gInt[2][3][i]][gInt[3][1][i]][i];  
	     if(ganmengongNum!=0) sb.append(QiMen.gGejuDesc[ganmengongNum][0]+"��");
	     int ganmengongNum2 = 0;
	     if(isTpJigong(i)) {//������̾���˳��Ϊ2��
	    	 ganmengongNum2 = QiMen.gan_men_gong[gInt[4][5][5]][gInt[3][1][i]][i];  
		     if(ganmengongNum2!=0) sb.append(QiMen.gGejuDesc[ganmengongNum2][0]+"��");
	     }
	     
	     //��+��
	     int ganxingNum = QiMen.gan_xing[gInt[2][3][i]][gInt[2][1][i]];  
	     if(ganxingNum!=0) sb.append(QiMen.gGejuDesc[ganxingNum][0]+"��");
	     int ganxingNum2 = 0;
	     if(ganxingNum==0 && isTpJigong(i)) {//������̾���˳��Ϊ2����������̸�����һ���ݼ׿�������Ҫ���ж���һ���Ĺ����Ƿ�����
	    	 ganxingNum2 = QiMen.gan_xing[gInt[4][5][5]][gInt[2][1][i]];  
		     if(ganxingNum2!=0) sb.append(QiMen.gGejuDesc[ganxingNum2][0]+"��");
	     }
	     
	     s[++index] = sb.toString();
       sb.delete(0, sb.length());
	     
	     ///////////////////////������еİ���������Ϣ/////////////////////////////	           
	     geHelp[++ihelp]=i+"����";
	     geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganganNum][0]+"��"+QiMen.gGejuDesc[ganganNum][1];	 
	     if(ganganNum5!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganganNum5][0]+"��"+QiMen.gGejuDesc[ganganNum5][1];	 
	     if(ganganNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganganNum2][0]+"��"+QiMen.gGejuDesc[ganganNum2][1];
	     
	     if(gangongNum_t!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_t][0]+"��"+QiMen.gGejuDesc[gangongNum_t][1];
	     if(gangongNum_t1!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_t1][0]+"��"+QiMen.gGejuDesc[gangongNum_t1][1];
	     if(gangongNum_t2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_t2][0]+"��"+QiMen.gGejuDesc[gangongNum_t2][1];
	     
	     if(gangongNum_d!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_d][0]+"��"+QiMen.gGejuDesc[gangongNum_d][1];
	     if(menmenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menmenNum][0]+"��"+QiMen.gGejuDesc[menmenNum][1];
	     if(menganNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum][0]+"��"+QiMen.gGejuDesc[menganNum][1];
	     if(menganNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum2][0]+"��"+QiMen.gGejuDesc[menganNum2][1];
	     if(menganNum3!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum3][0]+"��"+QiMen.gGejuDesc[menganNum3][1];
	     if(menganNum4!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum4][0]+"��"+QiMen.gGejuDesc[menganNum4][1];
	     
	     if(xingmenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[xingmenNum][0]+"��"+QiMen.gGejuDesc[xingmenNum][1];
	     if(xingmenNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[xingmenNum2][0]+"��"+QiMen.gGejuDesc[xingmenNum2][1];
	     
	     if(mganshenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[mganshenNum][0]+"��"+QiMen.gGejuDesc[mganshenNum][1];
	     if(mtganshenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[mtganshenNum][0]+"��"+QiMen.gGejuDesc[mtganshenNum][1];
	     
	     if(ganmenganNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmenganNum][0]+"��"+QiMen.gGejuDesc[ganmenganNum][1];
	     if(ganmenganNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmenganNum2][0]+"��"+QiMen.gGejuDesc[ganmenganNum2][1];
	     if(ganmengongNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmengongNum][0]+"��"+QiMen.gGejuDesc[ganmengongNum][1];
	     if(ganmengongNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmengongNum2][0]+"��"+QiMen.gGejuDesc[ganmengongNum2][1];
	     if(ganxingNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganxingNum][0]+"��"+QiMen.gGejuDesc[ganxingNum][1];
	     if(ganxingNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganxingNum2][0]+"��"+QiMen.gGejuDesc[ganxingNum2][1];
    }
    
    //��������Ϣ����ȫ�������з��أ�����22���ط���ʼ����
    index=22;
    for(int i=0; i<geHelp.length; i++) {
    	if(geHelp[i]==null) break;
    	s[index++] = geHelp[i];
    }
    
    return s;
  }
  /**
   * �õ����ֵļ��׸�	
   * @return
   */
  public String[] getDageJixiong() {
  	String[] s = new String[20];
  	int gejuNum; 
  	int index = 0;
  	
  	if(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]!=0) {
  		gejuNum=QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];  //ֻҪ���츨��ʱ���Ͳ��������岻��ʱ
  		if(gejuNum!=0)
  			s[index++] = ("  "+QiMen.gGejuDesc[gejuNum][0]+"��"+QiMen.gGejuDesc[gejuNum][1]);
  	}
  	if(QiMen.da_men_gong[gInt[3][1][1]][1]!=0) { //ֻҪ1��������1����Ϊ0�����ɷ��������
  		gejuNum=QiMen.da_men_gong[gInt[3][1][1]][1];
  		if(gejuNum!=0)
  			s[index++] = ("  "+QiMen.gGejuDesc[gejuNum][0]+"��"+QiMen.gGejuDesc[gejuNum][1]);
  	}
  	if(QiMen.da_xing_gong[gInt[2][1][1]][1]!=0) { //ֻҪ1��������1����Ϊ0������ɷ��ʻ����
  		gejuNum=QiMen.da_xing_gong[gInt[2][1][1]][1];
  		if(gejuNum!=0)
  			s[index++] = ("  "+QiMen.gGejuDesc[gejuNum][0]+"��"+QiMen.gGejuDesc[gejuNum][1]);
  	}
  	
  	//7/8ʱ����Ĺ,ʱ�ɱ���ʱ�����̱���Ǭ��������������Ǭ���������̶����ޣ��������̼����ޡ��ɳ����������㣻��δ���̹����
  	if((SiZhu.sg==YiJing.BING && SiZhu.sz==YiJing.XU && gInt[2][3][6]==YiJing.BING) ||
  			(SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU && gInt[2][3][6]==YiJing.WUG) ||
  			(SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.CHOU && gInt[2][3][8]==YiJing.DING) ||
  			(SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.CHOU && gInt[2][3][8]==YiJing.JI) ||
  			(SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.CHEN && gInt[2][3][4]==YiJing.REN) ||
  			(SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.WEI && gInt[2][3][2]==YiJing.GUI)) {
  		if(SiZhu.TGSWSJ[SiZhu.sg][SiZhu.yz]>5)
  			s[index++] = ("  "+QiMen.gGejuDesc[7][0]+"��"+QiMen.gGejuDesc[7][1]);
  		else
  			s[index++] = ("  "+QiMen.gGejuDesc[8][0]+"��"+QiMen.gGejuDesc[8][1]);
  	}
  	//9-12����¸��ո�ʱ�����������ʱ�м׿�ͷ����Ҫת��
  	int suige = 9, yuege=10, rige=11, shige=12;
  	int newNg = 0, newYg=0, newRg=0, newSg=0; 
  	//��������ת���ɶ�Ӧ�����
  	if(SiZhu.ng==1) newNg=getXunShu(SiZhu.ng, SiZhu.nz)+4;
  	if(SiZhu.yg==1) newYg=getXunShu(SiZhu.yg, SiZhu.yz)+4;
  	if(SiZhu.rg==1) newRg=getXunShu(SiZhu.rg, SiZhu.rz)+4;
  	if(SiZhu.sg==1) newSg=getXunShu(SiZhu.sg, SiZhu.sz)+4;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		//������̸�Ϊ�������̸�Ϊ��ɻ�ת�������ɻ�����ǵڶ���������幬�ĵ��̸��Ƿ���newNg��SiZhu.Ng��
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.ng || gInt[4][5][i]==newNg || (isJiGong(i) && (gInt[4][5][5]==newNg || gInt[4][5][5]==SiZhu.ng))))
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"��"+QiMen.gGejuDesc[suige][1]);
  		//��������ǵ������������2����֤���ж������̸ɣ���һ�����̸ɾ��ǵ�5���ĵ��̸ɼ�gInt[4][5][5]������Ǹ������ҵ��̸������
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.ng  || gInt[4][5][i]==newNg))
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"��"+QiMen.gGejuDesc[suige][1]);
  		
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.yg  || gInt[4][5][i]==newYg || (isJiGong(i) && (gInt[4][5][5]==newYg  || gInt[4][5][5]==SiZhu.yg))))
  			s[index++] = ("  "+QiMen.gGejuDesc[yuege][0]+"��"+QiMen.gGejuDesc[yuege][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.yg  || gInt[4][5][i]==newYg))
  			s[index++] = ("  "+QiMen.gGejuDesc[yuege][0]+"��"+QiMen.gGejuDesc[yuege][1]);
  		
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.rg  || gInt[4][5][i]==newRg || (isJiGong(i) && (gInt[4][5][5]==newRg  || gInt[4][5][5]==SiZhu.rg))))
  			s[index++] = ("  "+QiMen.gGejuDesc[rige][0]+"��"+QiMen.gGejuDesc[rige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.rg  || gInt[4][5][i]==newRg))
  			s[index++] = ("  "+QiMen.gGejuDesc[rige][0]+"��"+QiMen.gGejuDesc[rige][1]);
  		
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.sg  || gInt[4][5][i]==newSg || (isJiGong(i) && (gInt[4][5][5]==newSg  || gInt[4][5][5]==SiZhu.sg))))
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.sg  || gInt[4][5][i]==newSg))
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  	}
  		
  	//13�ɸɸ������ոɣ�����������
  	suige = 13;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==SiZhu.rg && gInt[4][5][i]==YiJing.GENG )
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"��"+QiMen.gGejuDesc[suige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==SiZhu.rg && gInt[4][5][i]==SiZhu.rg)
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"��"+QiMen.gGejuDesc[suige][1]);
  	}
  	//14�������������������̱�ʱѮͷ��Ѯ����+4��ΪѮͷ����Ӧ������
  	shige = 14;
  	int xuntou_liuyi = getXunShu(SiZhu.sg, SiZhu.sz)+4;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.GENG && gInt[4][5][i]==YiJing.WUG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && gInt[4][5][i]==YiJing.WUG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  	}

  	//15�ɹ�������ֱ������������  
  	shige = 15;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.WUG && gInt[4][5][i]==YiJing.GENG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[2][3][i]==YiJing.WUG && gInt[4][5][i]==YiJing.GENG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  	}
  	//16㣸����̱��ӵ���ֵ����������ֵ���ӵ��̱��������������ʱ��֮�ϡ�
  	shige = 16;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.BING && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.BING && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  		
  		if(gInt[2][3][i]==xuntou_liuyi && gInt[4][5][i]==YiJing.BING) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  		if(isTpJigong(i) && gInt[2][3][i]==xuntou_liuyi && gInt[4][5][i]==YiJing.BING){
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  		
  		if(gInt[2][3][i]==YiJing.BING && (gInt[4][5][i]==SiZhu.ng || gInt[4][5][i]==SiZhu.yg || gInt[4][5][i]==SiZhu.rg || gInt[4][5][i]==SiZhu.sg)){
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	
  	//17�������ţ������������ʱ�ɡ�
  	shige = 17;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.GUI && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GUI && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  	}
		//18�����ڱΣ��������ɣ�����ʱ�ɡ�
  	shige = 18;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.REN && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.REN && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);
  	}
  	//19ֵ������: ����ֵ���ڱ�����������+��Ϊֵ������
  	shige = 19;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==xuntou_liuyi && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  		if(isTpJigong(i) && gInt[4][5][5]==xuntou_liuyi && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	//20ֱ������Ϊ�����̼��ӣ����̼��磻���̼��������̼׳������̼��꣬���̼��������ֻ��������������޷���
  	shige = 20;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==xuntou_liuyi && ((gInt[2][3][i]==YiJing.WUG && gInt[4][5][i]==YiJing.XIN) ||
  				(gInt[2][3][i]==YiJing.JI && gInt[4][5][i]==YiJing.REN) ||
  				(gInt[2][3][i]==YiJing.GENG && gInt[4][5][i]==YiJing.GUI) ||
  				(gInt[2][3][i]==YiJing.XIN && gInt[4][5][i]==YiJing.WUG) ||
  				(gInt[2][3][i]==YiJing.REN && gInt[4][5][i]==YiJing.JI) ||
  				(gInt[2][3][i]==YiJing.GUI && gInt[4][5][i]==YiJing.GENG))) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	//21�����ʹ������������ٵ��̼�������磻���̱�����ٵ��̼��ӻ���ꣻ���̶�����ٵ��̼׳����������ʹ�������¡����޼�������С����
  	shige = 21;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if((gInt[2][3][i]==YiJing.YI && (gInt[4][5][i]==YiJing.JI ||gInt[4][5][i]==YiJing.XIN))||
  				(gInt[2][3][i]==YiJing.BING && (gInt[4][5][i]==YiJing.WUG ||gInt[4][5][i]==YiJing.GENG))||
  				(gInt[2][3][i]==YiJing.DING && (gInt[4][5][i]==YiJing.REN ||gInt[4][5][i]==YiJing.GUI))) {
  			if(i==getZhishiGong(SiZhu.sg,SiZhu.sz)) {
  				s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  			}
  		}
  	}
  	//22��Ů���ţ�����ֱʹ���ٵ��̶��档����������ᣬ��ϲ����֮�¡�
  	shige = 22;
  	int zhishiGong = getZhishiGong(SiZhu.sg,SiZhu.sz);
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(i==zhishiGong && gInt[4][5][i]==YiJing.DING) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	//23������ ����������ֵ��֮��Ϊ����������ıΪ��������������ʿ�������÷���
  	shige = 23;
  	int zhifuGong = getZhifuGong(SiZhu.sg,SiZhu.sz);
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(i==zhifuGong && (gInt[4][5][i]==YiJing.DING || gInt[4][5][i]==YiJing.YI ||
  				gInt[4][5][i]==YiJing.BING || gInt[2][3][i]==YiJing.DING ||
  				gInt[2][3][i]==YiJing.YI || gInt[2][3][i]==YiJing.BING)) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"��"+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}  	
  	
  	return s; 
  }

  /**
   * ��=8�ַ�=16�ո�
   * ��=6�ַ�=6��
   * _6=  _5=���  ��, _4=��  ��,_3= �� ,_2=����  _1=
   */
  public String getBody1(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String kg = "    ";
    String _str;
    int index = 0;


    
    //1. �õ�ȫ�ֵĴ�񣬲���ʾ�ڴ�
    String[] dgeJx = getDageJixiong();
    out1(" ");
    out1("��Ҫ��֣�");
    for(int i=0; i<dgeJx.length; i++) {
      if(dgeJx[i]==null || dgeJx[i].trim().equals("")) {
        break;
      }else{
        out1(dgeJx[i]);
      }
    }
    
    //2. �õ������ļ��׸�
    String _s[] = getJiXiongOfGong();
    for(int i=1; i<_s.length; i++) {
      if(_s[i]==null) _s[i]="";
    }

    out1("�������������������ש����������������ש�����������������");
    out1("��41��91��21��"+kg+_s[index+1]);
    out1("��42��92��22��"+kg+_s[index+2]);
    out1("��43��93��23��"+kg+_s[index+3]);
    out1("��44��94��24��"+kg+_s[index+4]);
    out1("��45��95��25��"+kg+_s[index+5]);
    out1("��46��96��26��"+kg+_s[index+6]);
    out1("�ǩ����������������贈���������������贈����������������"+kg+_s[index+7]);
    out1("��31��51��71��"+kg+_s[index+8]);
    out1("��32��52��72��"+kg+_s[index+9]);
    out1("��33��53��73��"+kg+_s[index+10]);
    out1("��34��54��74��"+kg+_s[index+11]);
    out1("��35��55��75��"+kg+_s[index+12]);
    out1("��36��56��76��"+kg+_s[index+13]);
    out1("�ǩ����������������贈���������������贈����������������"+kg+_s[index+14]);
    out1("��81��11��61��"+kg+_s[index+15]);
    out1("��82��12��62��"+kg+_s[index+16]);
    out1("��83��13��63��"+kg+_s[index+17]);
    out1("��84��14��64��"+kg+_s[index+18]);
    out1("��85��15��65��"+kg+_s[index+19]);
    out1("��86��16��66��"+kg+_s[index+20]);
    out1("�������������������ߩ����������������ߩ�����������������"+kg+_s[index+21]);
    //������ļ��׸���ʾ�ڴ�
    for(int i=22; i<500; i++) {
      if(_s[i]==null || _s[i].trim().equals("")) {
        break;
      }else{
        out1(_s[i]);
      }
    }

    _str = str.toString();

    //�ž�,result�еĶ������滻�����21/22/23/92....
    String[][] result = makeJiuGong(iZfs);
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=6; j++) {
        _str = _str.replaceAll(""+i+""+j, result[i][j]);
      }
    }

    return _str;
  }
  /**
   * ֻ�о֣�û��������Ϣ
   * simon(2011-11-28)
   */
  public String getBody0(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String kg = "    ";
    String _str;
    int index = 0;

    out1("�������������������ש����������������ש�����������������");
    out1("��41��91��21��");
    out1("��42��92��22��");
    out1("��43��93��23��");
    out1("��44��94��24��");
    out1("��45��95��25��");
    out1("��46��96��26��");
    out1("�ǩ����������������贈���������������贈����������������");
    out1("��31��51��71��");
    out1("��32��52��72��");
    out1("��33��53��73��");
    out1("��34��54��74��");
    out1("��35��55��75��");
    out1("��36��56��76��");
    out1("�ǩ����������������贈���������������贈����������������");
    out1("��81��11��61��");
    out1("��82��12��62��");
    out1("��83��13��63��");
    out1("��84��14��64��");
    out1("��85��15��65��");
    out1("��86��16��66��");
    out1("�������������������ߩ����������������ߩ�����������������");

    _str = str.toString();

    //�ž�,result�еĶ������滻�����21/22/23/92....
    String[][] result = makeJiuGong(iZfs);
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=6; j++) {
        _str = _str.replaceAll(""+i+""+j, result[i][j]);
      }
    }

    return _str;
  }
  /**
   * ԭ���� simon(2011-11-14)
   */
  public String getBody2(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String kg = "    ";
    String _str;
    int index = 0;

    //���׸�
    String _s[] = getJiXiongOfGong();
    for(int i=1; i<_s.length; i++) {
      if(_s[i]==null) _s[i]="";
    }

    out1("�������������������ש����������������ש�����������������");
    out1("��41��91��21��"+kg+_s[index+1]);
    out1("��42��92��22��"+kg+_s[index+2]);
    out1("��43��93��23��"+kg+_s[index+3]);
    out1("��44��94��24��"+kg+_s[index+4]);
    out1("��45��95��25��"+kg+_s[index+5]);
    out1("��46��96��26��"+kg+_s[index+6]);
    out1("�ǩ����������������贈���������������贈����������������"+kg+_s[index+7]);
    out1("��31��51��71��"+kg+_s[index+8]);
    out1("��32��52��72��"+kg+_s[index+9]);
    out1("��33��53��73��"+kg+_s[index+10]);
    out1("��34��54��74��"+kg+_s[index+11]);
    out1("��35��55��75��"+kg+_s[index+12]);
    out1("��36��56��76��"+kg+_s[index+13]);
    out1("�ǩ����������������贈���������������贈����������������"+kg+_s[index+14]);
    out1("��81��11��61��"+kg+_s[index+15]);
    out1("��82��12��62��"+kg+_s[index+16]);
    out1("��83��13��63��"+kg+_s[index+17]);
    out1("��84��14��64��"+kg+_s[index+18]);
    out1("��85��15��65��"+kg+_s[index+19]);
    out1("��86��16��66��"+kg+_s[index+20]);
    out1("�������������������ߩ����������������ߩ�����������������"+kg+_s[index+21]);
    //������ļ��׸���ʾ�ڴ�
    for(int i=index+22; i<100; i++) {
      if(_s[i]==null || _s[i].trim().equals("")) {
        break;
      }else{
        out1(daoyj.getRepeats(" ",56)+kg+_s[i]);
      }
    }

    _str = str.toString();

    //�ž�,result�еĶ������滻�����21/22/23/92....
    String[][] result = makeJiuGong(iZfs);
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=6; j++) {
        _str = _str.replaceAll(""+i+""+j, result[i][j]);
      }
    }

    return _str;
  }

  /**
   * ��ʱ��õ�����ͷ����Ϣ
   * @param iszf
   * @param iscy
   * @param iZfs
   * @param ju
   * @return
   */
  public String getHead1(int iszf, int iscy, int iZfs, int ju) {
    int h = Calendar.HOUR/100;
    int mi = Calendar.HOUR - Calendar.HOUR/100*100;
    int h2 = Calendar.HOUR2/100;
    int mi2 = Calendar.HOUR2 - Calendar.HOUR2/100*100;
    str.delete(0,str.length());
    String zf_, zy_, zfs_,jg_, ju_;

    zf_ = (iszf==1) ? "ת��-":"����-";
    zy_ = (iscy==1) ? "����-":"�𲹷�-";
    zfs_ = (iZfs==1) ? "Сֵ��������-":"Сֵ�������-";
    jg_ = (getJiGong()==8) ? "��������":"��������";
    
    str.append(getYangOrYinDesc(true)+zf_+zy_+zfs_+jg_);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+
               "��  ���� "+Calendar.YEARP+"-"+Calendar.MONTHP+"-"+Calendar.DAYP+
               " "+h+":"+mi);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+
               "ũ  ���� "+Calendar.YEARN1+"��"+(Calendar.YUN ? "��":"") +
               Calendar.MONTHN1+"�³�"+Calendar.DAYN+" "+h+":"+mi+" "+
               Calendar.WEEKNAME[Calendar.WEEK]);
    if(Calendar.isSunTrue) {
      str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+
          "��  ���� " + Calendar.YEARN1 + "��" +
          (Calendar.YUN ? "��" : "") +
          Calendar.MONTHN + "�³�" + Calendar.DAYN + " " + h2 + ":" + mi2 + " " +
          Calendar.WEEKNAME[Calendar.WEEK]);
    }
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  ֧�� "+
               YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "Ѯ  �գ� "+
               pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[0]+pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[0]+pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[0]+pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[0]+pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[1]);


    int lastjie = 0; int nextjie = 0, lastjiename=0, nextjiename=0;
    int lastjieyear=0, nextjieyear=0;
    //ȡ���۽�����������
    int z = Calendar.MONTHN1;
    //ȡ���ֽ�����������
    int year = Calendar.YEARN1-Calendar.IYEAR;
    //��2004.2.1����Ϊ2004.1.11��������������12��
    int _date2 = Calendar.jieqi2[year][z*2-1 + 1];
    if(!isMoreBig(Calendar.YEARN1, z*2-1+1)) { //���µڶ�����
      if(!isMoreBig(Calendar.YEARN1, z*2-1)) { //���µ�һ����
        if(z==1) {  //�����С�ڵ�һ������ֱ��ȡ��һ�µڶ������������ж���
          lastjie = Calendar.jieqi2[year-1][24];
          nextjie = Calendar.jieqi2[year][1];;
          lastjiename = 24;
          nextjiename = 1;
          lastjieyear = year-1 + Calendar.IYEAR;
          nextjieyear = year + Calendar.IYEAR;
        }else{
          lastjie = Calendar.jieqi2[year][z * 2 - 2];
          nextjie = Calendar.jieqi2[year][z * 2 - 1];;
          lastjiename = z * 2 - 2;
          nextjiename = z * 2 - 1;
          lastjieyear = year + Calendar.IYEAR;
          nextjieyear = year + Calendar.IYEAR;
        }
      }else {
        lastjie = Calendar.jieqi2[year][z * 2 - 1];
        nextjie = _date2;
        lastjiename = z * 2 - 1;
        nextjiename = z * 2 - 1 + 1;
        //���������ȥ��12��
        lastjieyear = year + Calendar.IYEAR;
        nextjieyear = year + Calendar.IYEAR;
      }

    }else{
      int z2 = 0;
        int y2 = 0;
        if(z==12) {
          z2 = 1;
          y2 = Calendar.YEARN1+1;
        }else{
          z2 = z*2-1+2;
          y2 = Calendar.YEARN1;
        }
        if(isMoreBig(y2, z2)) { //������ڸ��µڶ����������������µ�һ������
          if(z==12) {
            lastjie = Calendar.jieqi2[year+1][1];
            nextjie = Calendar.jieqi2[year+1][2];
            lastjiename = 1;
            nextjiename = 2;
            lastjieyear = year +1+ Calendar.IYEAR;
            nextjieyear = year +1+ Calendar.IYEAR;
          }else{
            lastjie = Calendar.jieqi2[year][z*2+1];
            nextjie = Calendar.jieqi2[year][z*2+2];
            lastjiename = z*2+1;
            nextjiename = z*2+2;
            lastjieyear = year + Calendar.IYEAR;
            nextjieyear = year + Calendar.IYEAR;
          }
        }else{
          lastjie = _date2;
          if (z == 12) {
            nextjie = Calendar.jieqi2[year + 1][1];
            lastjiename = 24; //����_maxMonth-1
            nextjiename = 1;
            lastjieyear = year + Calendar.IYEAR;
            nextjieyear = year + Calendar.IYEAR + 1;
          }
          else {
            nextjie = Calendar.jieqi2[year][z * 2 - 1 + 2];
            lastjiename = z * 2 - 1 + 1;
            nextjiename = z * 2 - 1 + 2;
            lastjieyear = year + Calendar.IYEAR;
            nextjieyear = year + Calendar.IYEAR;
          }
        }
    }
    int ly = lastjie/1000000;
    int lr = lastjie/10000 - ly*100;
    int ls = lastjie/100 - ly*10000 - lr*100;
    int lf = lastjie - ly*1000000 - lr*10000 - ls*100;
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  �ڣ� "+
               QiMen.JIEQI24[lastjiename]+" "+lastjieyear+"��"+
               (isYunJieqi(lastjieyear, lastjiename) ? "��":"") +
               ly+"�³�"+ lr+"��"+ls+"ʱ"+lf+"��");
    String jieqi1 = null;
    String jieqi2 = null;
    if(iscy==2) {   //1Ϊ����2Ϊ��
      jieqi1 = getJieqi1(lastjiename);  //�𲹷�Ϊ�Ͻڽ����������������𲹿϶������ܵ�ǰ��ȥ
    }else {
      jieqi2 = getJieqi2(iscy, lastjiename, lastjieyear, ly, lr, ls, lf);
    }

    ly = nextjie/1000000;
    lr = nextjie/10000 - ly*100;
    ls = nextjie/100 - ly*10000 - lr*100;
    lf = nextjie - ly*1000000 - lr*10000 - ls*100;
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  �ڣ� "+
               QiMen.JIEQI24[nextjiename]+" "+nextjieyear+"��"+
               (isYunJieqi(nextjieyear, nextjiename) ? "��":"") +
               ly+"�³�"+lr+"��"+ls+"ʱ"+lf+"��");

    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  ���� "+
               ((iscy==1)?jieqi2:jieqi1));

    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  �֣� "+
               getGeju(ju,iszf, iZfs));

    return str.toString();
  }

  /**
   * �ɸ�֧�õ�������Ϣ
   * @param iszf
   * @param iscy
   * @param iZfs
   * @param ju
   * @return
   */
  public String getHead2(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String zf_, zy_, ju_, zfs_, jg_;

    zf_ = (iszf==1) ? "ת�� -":"������-";
    zy_ = (iscy==1) ? "����-":"�𲹷�-";
    zfs_ = (iZfs==1) ? "Сֵ��������-":"Сֵ�������-";
    jg_ = (getJiGong()==8) ? "��������":"��������";    
    str.append(getYangOrYinDesc(true)+zf_+zy_+zfs_+jg_);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  ֧�� "+
               YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "Ѯ  �գ� "+
               pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[0]+pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[0]+pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[0]+pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[0]+pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[1]);

    String jieqi = getJieqi3(ju);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  ���� "+ jieqi);

    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "��  �֣� "+
               getGeju(ju,iszf, iZfs));

    return str.toString();
  }

  public String getYangOrYinDesc(boolean isKongge) {
  	String desc = null;
  	if(isKongge)
  		desc = QiMen2.YANG?"��  �̣� ":"��  �̣� ";
  	else
  		desc = QiMen2.YANG?"���̣�":"���̣�";
  	return desc;
  }

  /**
   * �õ��Ǻξ֣�ֵ����ֵʹ
   * @return
   */
  private String getGeju(int ju, int iszf, int iZfs) {
    //Debug.out("��="+this.whichJie+" ; Ԫ="+this.whichYuan);
    String str = "";
    int _ret = 0;

    if(ju>0) {
      str += "��ѡ"+QiMen.yydunju[ju];
      this.whichJu = ju>9?9-ju:ju;
    }else{
    	//int _ret = QiMen.yydun[this.whichJie][this.whichYuan];
    	//this.whichJu = _ret;
      _ret = getYangOrYinJu();      
      str += _ret>0?QiMen.yydunju[_ret]:QiMen.yydunju[9-_ret];
    }

    //�õ�ȫ��Ԫ��
    getGlobleInfo(iszf, iZfs);

    str += "  ";
    
    /**
     * (һ)���幬�ĺι������ݼ�igJigongָ���Ĺ�����������2�������ǵ�һ���Ķ�
     */
    int zfmen = gInt[0][0][12];  //ֵ����5��ʱֱ��ѡ5������Ϊ������
    int zsmen = gInt[0][0][12]!=5?gInt[0][0][12]: (this.getJu()>0)? (igJigong==0?2:igJigong) : 2;  
    if(gInt[0][0][13]==5)
    	gInt[0][0][13]=(this.getJu()>0)? (igJigong==0?2:igJigong) : 2;
    if(gInt[0][0][14]==5)
    	gInt[0][0][14] = (this.getJu()>0)? (igJigong==0?2:igJigong) : 2;
    
    str +="ֵ��["+QiMen.jx1[zfmen]+"]��"+gInt[0][0][13]+"��";
    str += "  ";
    str +="ֵʹ["+QiMen.bm1[zsmen]+"]��"+gInt[0][0][14]+"��";
    return str;
  }
  
  //���������̵ľ���
  private int getYangOrYinJu() {
  	int _ret = QiMen.yydun[this.whichJie][this.whichYuan];
    if(QiMen2.YANG){
    	this.whichJu = _ret;
    } else { //�����̾����������ţ�����������ʱ�����̾���
    	//System.out.println(SiZhu.nz+"-"+Calendar.MONTHN1+"�³�"+Calendar.DAYN+"-"+SiZhu.sz);
    	int ju = SiZhu.nz+Calendar.MONTHN1+Calendar.DAYN+SiZhu.sz;
    	ju = ju%9==0 ? 9 :ju%9;
    	this.whichJu = _ret > 0 ? ju : 0 - ju;
    }
    
    return this.whichJu;
  }
  
  
  

//  /**
//   * �õ��Ǻξ֣�ֵ����ֵʹ
//   * @return
//   */
//  private String getGeju1(int ju, int iZfs) {
//    //Debug.out("��="+this.whichJie+" ; Ԫ="+this.whichYuan);
//    String str = "";
//    int _ret = 0;
//
//    if(ju>0) {
//      str += "��ѡ"+QiMen.yydunju[ju];
//      this.whichJu = ju>9?9-ju:ju;
//    }else{
//      _ret = QiMen.yydun[this.whichJie][this.whichYuan];
//      this.whichJu = _ret;
//      str += _ret>0?QiMen.yydunju[_ret]:QiMen.yydunju[9-_ret];
//    }
//
//    str += "  ";
//    int zfs = getZhiFuShi(SiZhu.sg, SiZhu.sz);
//    int zflg = getZhifuGong(SiZhu.sg, SiZhu.sz);
//    int zslg = getZhishiGong(SiZhu.sg, SiZhu.sz);
//    str +="ֵ��["+QiMen.jx1[zfs]+"]��"+zflg+"��";
//    str += "  ";
//    str +="ֵʹ["+QiMen.bm1[zfs]+"]��"+zslg+"��";
//    str += iZfs==1? "  Сֵ��������ֵ��":"  Сֵ�������ֵ��";
//    return str;
//  }


  /**
   * �õ�����Ԫ
   * @param i
   * @return
   */
  private String getWhichYuan(int i) {
    String str = "";
    if(i==1)
      str = "��Ԫ";
    else if(i==2)
      str = "��Ԫ";
    else if(i==3)
      str = "��Ԫ";

    return str;
  }

  /**
   * �𲹷�
   * ��������������ϵ��Ƕ��һ�죬��1�յ�3��Ϊ3���3-1=2�졣
   * @param zy
   * @return
   */
  private String getJieqi1(int lastjiename) {
    String str = "";
    int[] ret ;
    ret = getFutou(SiZhu.rg, SiZhu.rz);
    str = QiMen.JIEQI24[lastjiename];
    str += getWhichYuan(ret[3]);
    str += "��"+(ret[0]+1)+"��";
    str += "  ��ͷ:" + YiJing.TIANGANNAME[ret[1]]+YiJing.DIZINAME[ret[2]];
    str += "  Ѯ��:"+QiMen.xunname[getXunShu(SiZhu.sg, SiZhu.sz)];

    whichJie = lastjiename;
    whichYuan = ret[3];

    return str;
  }

  /**
   * ��������
   * ��������������ϵ��Ƕ��һ�죬��1�յ�3��Ϊ3���3-1=2�졣
   * @param zy��jieyear,ly,lr,ls,lf  �Ƿ�����/�������ꡢ�����¡������ա�ʱ����
   * @return
   */
  private String getJieqi2(int zy, int lastjiename, int jieyear,
                           int ly,int lr,int ls,int lf) {
    int[] ret ;
    int x;
    String str = "";

    //1. �����
    int yn = Calendar.YEARP;   //��ǰ��ֵ��ꡢ�¡��ա�ʱ�֡��ոɡ���֧
    int mn = Calendar.MONTHP;
    int dn = Calendar.DAYP;
    int hn = Calendar.HOUR;
    int szrg = SiZhu.rg;
    int szrz = SiZhu.rz;
    boolean yunp = Calendar.YUN;
    daoc.calculate(jieyear,ly,lr,ls,lf,true,isYunJieqi(jieyear, lastjiename),-1, -1);
    //2. �������֧
    int g = SiZhu.rg;
    int z = SiZhu.rz;
    //����������ٻָ���ǰ��ʱ����Ϣ������ָ��ʡ����������̫��ʱ�� simon(2011-12-14)
    daoc.calculate(yn,mn,dn,hn/100,hn-hn/100*100,false,yunp,Calendar.PROVINCE, Calendar.CITY);
    //3. ���������Ԫ��ȷ���ǳ����ǽ���
    //   ���۳��񡢽��������ȡ����֮��ͷ���ʽ�������
    ret = getFutouOfUp(g, z);
    str = QiMen.JIEQI24[lastjiename];
    str += "��Ԫ  ��ͷ" + YiJing.TIANGANNAME[ret[1]]+YiJing.DIZINAME[ret[2]];
    str += "  ";
    if(ret[0]==0)
      str += "����";
    else if(ret[0]>0)
      str += "����"+Math.abs(ret[0]+1)+"��";
    else if(ret[0]<0)
      str += "����"+Math.abs(ret[0]-1)+"��";
    str += "\r    ";
    //4. ȡ��������ǰ����ʮ�տ��ӵڼ���
    //   ȷ���Ƿ������������Ԫ���������桱��ֻ��â�ֺʹ�ѩ�������������ʹ����ʮ��Ҳ��������
    //   ����
    boolean makeyun = false;
    if(lastjiename==9 || lastjiename==21) {
      if(ret[0]>9)
        makeyun=true;
    }


    int[][] jq = new int[61][5]; //��30�飬��1֧2�ν���3 ��Ԫ4
    int _i = 0;
    if(makeyun) {
      //����������ǰ��30�գ�ֻ���30�գ��ٺ�15��
      for (int ii = 1; ii <= 30; ii++) {
        jq[ii][1] = (ret[1] + ii - 1) > 10 ? (ret[1] + ii - 1) % 10 :(ret[1] + ii - 1);
        if(jq[ii][1]==0) jq[ii][1]=10;   //��ii>10ʱ�ͻ�Ϊ0����ʵӦΪ10
        jq[ii][2] = (ret[2] + ii - 1) > 12 ? (ret[2] + ii - 1) % 12 :(ret[2] + ii - 1);
        if(jq[ii][2]==0) jq[ii][2]=12;  //��ii>12ʱ�ͻ�Ϊ0����ʵӦΪ12
        jq[ii][3] = lastjiename;
        _i = (ii - 1) / 5 + 1;
        if (_i > 3)
          jq[ii][4] = _i - 3;
        else
          jq[ii][4] = _i;
      }

      for (int ii = 31; ii <= 45; ii++) {
        jq[ii][1] = (ret[1] + ii - 1) > 10 ? (ret[1] + ii - 1) % 10 : (ret[1] + ii - 1);     
        if(jq[ii][1]==0) jq[ii][1]=10;   //��ii>10ʱ�ͻ�Ϊ0����ʵӦΪ10
        jq[ii][2] = (ret[2] + ii - 1) > 12 ? (ret[2] + ii - 1) % 12 : (ret[2] + ii - 1);
        if(jq[ii][2]==0) jq[ii][2]=12;  //��ii>12ʱ�ͻ�Ϊ0����ʵӦΪ12
        jq[ii][3] = lastjiename + 1 > 24 ? (lastjiename + 1) % 24 : lastjiename + 1;
        _i = (ii - 1) / 5 + 1;
        jq[ii][4] = _i - 6;
      }
    }else{
    	/**
    	 * simon ������һ��bug����0��ʵΪ10��12 2011-12-22
    	 */
      for (int ii = 1; ii <= 30; ii++) {
        jq[ii][1] = (ret[1] + ii - 1) > 10 ? (ret[1] + ii - 1) % 10 : (ret[1] + ii - 1);
        if(jq[ii][1]==0) jq[ii][1]=10;  //
        jq[ii][2] = (ret[2] + ii - 1) > 12 ? (ret[2] + ii - 1) % 12 : (ret[2] + ii - 1);
        if(jq[ii][2]==0) jq[ii][2]=12;  //
        if (ii >= 16) {
          jq[ii][3] = lastjiename + 1 > 24 ? (lastjiename + 1) % 24 : lastjiename + 1;
        }
        else {
          jq[ii][3] = lastjiename;
        }
        _i = (ii - 1) / 5 + 1;
        if (_i > 3)
          jq[ii][4] = _i - 3;
        else
          jq[ii][4] = _i;
      }

      for (int ii = 31; ii <= 60; ii++) {
        jq[ii][1] = (ret[1] - ii - 30) <= 0 ? (ret[1] - ii - 30 + 100) % 10 : (ret[1] - ii - 30);
        if(jq[ii][1]==0) jq[ii][1]=10;   //��ii>10ʱ�ͻ�Ϊ0����ʵӦΪ10
        jq[ii][2] = (ret[2] - ii - 30) <= 0 ? (ret[2] - ii - 30 + 120) % 12 : (ret[2] - ii - 30);
        if(jq[ii][2]==0) jq[ii][2]=12;  //��ii>12ʱ�ͻ�Ϊ0����ʵӦΪ12
        if (ii >= 45) {
          jq[ii][3] = lastjiename - 2 <= 0 ? lastjiename - 2 + 24 : lastjiename - 2;
        }
        else {
          jq[ii][3] = lastjiename - 1 <= 0 ? lastjiename - 1 + 24 : lastjiename - 1;
        }
        _i = (ii - 1) / 5 + 1;
        if (_i == 7 || _i == 10)
          jq[ii][4] = 3;
        else if (_i == 8 || _i == 11)
          jq[ii][4] = 2;
        else if (_i == 9 || _i == 12)
          jq[ii][4] = 1;
      }
    }


    //7. ȷ�������Ǻ�Ԫ �ڼ��գ�
    str += "��  �գ� ";
    int jj = 0;
    for(jj=1; jj<=30; jj++) {
      if(jq[jj][1]==szrg && jq[jj][2]==szrz) {
        break;
      }
    }

    int[] _futou = getFutou(szrg,szrz);
    str += QiMen.JIEQI24[jq[jj][3]];
    str += QiMen.SANYUANNAME[jq[jj][4]];
    str += "  ";
    str += "��ͷ:"+YiJing.TIANGANNAME[_futou[1]]+YiJing.DIZINAME[_futou[2]];
    str += "  ��"+(_futou[0]+1)+"��";
    str += "  Ѯ��:"+QiMen.xunname[getXunShu(SiZhu.sg, SiZhu.sz)];

    whichJie = jq[jj][3];
    whichYuan = jq[jj][4];

    if(makeyun) {
      str += "\r\n    ";
      str += "��  �� ";
      str += QiMen.JIEQI24[jq[jj][3]];
      str += QiMen.SANYUANNAME[jq[jj][4]];
      str += "  ";
      str += "��ͷ:"+YiJing.TIANGANNAME[_futou[1]]+YiJing.DIZINAME[_futou[2]];
      str += "  ��"+(_futou[0]+1)+"��";
      str += "  Ѯ��:"+QiMen.xunname[getXunShu(SiZhu.sg, SiZhu.sz)];
    }

    return str;
  }

  /**
   * �õ��ν��� ��Ԫ �ڼ���  ��ͷxx
   * @param ju
   * @return
   */
  private String getJieqi3(int ju) {
    int _ju = ju>9?9-ju:ju;
    int mz = SiZhu.yz ;
    int mg = SiZhu.yg ;
    int dg = SiZhu.rg;
    int dz =  SiZhu.rz;
    int yz = mz;
    int _g=0, _z = 0;
    boolean b = false;

    yz = yz<3 ? yz+10 : yz-2;
    int[] jies = { yz*2-1, yz*2-2==0?24:yz*2-2, yz*2};
    int i=0;
    int m=0;
    String str = "";

    if(ju>0) { // ��ѡ��
      for (i = 0; i < jies.length; i++) {
        int[] ju_ = QiMen.yydun[jies[i]];
        for (m = 1; m < ju_.length; m++) {
          if (ju_[m] == _ju) {
            b = true;
            break;
          }
        }
        if (b)
          break;
      }
      str = QiMen.JIEQI24[jies[i]];
      str += getWhichYuan(m);
      this.whichJie = jies[i];
      this.whichYuan = m;
      this.whichJu = QiMen.yydun[jies[i]][m];

    }else{ //�Զ�ȷ����
      b = false;
      int _j = (mz < 3 ? mz + 10 : mz - 2) * 2 - 1;
      str = QiMen.JIEQI24[_j];
      for (i = 0; i < 10; i++) {
        _g = (dg - i + 10) % 10 == 0 ? 10 : (dg - i + 10) % 10;
        _z = (dz - i + 12) % 12 == 0 ? 12 : (dz - i + 12) % 12;
        for (m=1; m < 4; m++) {
          if (QiMen.sanyuan[m][_g][_z] == 1) {
            b = true;
            break;
          }
        }
        if(b) break;
      }
      this.whichJie = _j;
      this.whichYuan = m;
      //�˴������� ���̶�����
//      if(QiMen2.YANG) {
//      	this.whichJu = QiMen.yydun[_j][m];
//      } else {
//      	this.whichJu = getYinPanJu(QiMen.yydun[_j][m]);
//      }
      getYangOrYinJu();
      
      str += getWhichYuan(m);
    }



    int days = 0;
    int fug = 0;
    int fuz = 0;
    b = false;
    //������ѡ�֣�����ǰ���15���ж����޳��񣬽���
    //�����Զ����֣����ǳ������ɣ�����for�ɲ�����
    for(int j=0; j<15; j++) {
      _g = (dg - j + 10) % 10 == 0 ? 10 : (dg - j + 10) % 10;
      _z = (dz - j + 12) % 12 == 0 ? 12 : (dz - j + 12) % 12;
      if(QiMen.sanyuan[m][_g][_z] == 1) {
        b = true;
        if(j ==0) {
          days = 0;
          str += "  ��ͷ" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] + "  ����";
        }else {
          days = j + 1;
          str += "  ��ͷ" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] + "  ����" +
              days + "��";
        }
        break;
      }
    }
    if(!b)
      for(int j=0; j<15; j++) {
        _g = (dg + j + 10) % 10 == 0 ? 10 : (dg + j + 10) % 10;
        _z = (dz + j + 12) % 12 == 0 ? 12 : (dz + j + 12) % 12;
      if(QiMen.sanyuan[m][_g][_z] == 1) {
        if(j ==0) {
          days = 0;
          str += "  ��ͷ" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] + "  ����";
        }else {
          days = j + 1;
          str += "  ��ͷ" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] +
              "  ����" + days + "��";
        }
          break;
        }
      }

    return str;
  }

  /**
   * ���� �Ӳ��^�壬�����^��
   * ĳ���ù��� ���������з�ӳ�����鷳
   * @param zy
   * @return
   */
  private String makeYun(int zy, int lastjie) {
    String str = "";
    int[] ret ;
    if(zy==1) {  //����
      ret = getFutouOfUp(SiZhu.rg, SiZhu.rz);
    }else{  //��
      ret = getFutou(SiZhu.rg, SiZhu.rz);
    }
    if(ret[0]<-5 || ret[0]>9) {
      if(lastjie==9 || lastjie==21) {

      }
    }

    return str;
  }


  /**
   * �õ���Ԫ��ͷ��ר��������, �����ȡЩ
   * �綡î ���ȵõ� ���� ���� ��δ ���� ���� ���� ��î������������֮��
   * ��ȡ���ף���ȡ����
   * @param dg,dz Ϊ�����ĸɡ�֧
   * @return
   */
  public int[] getFutouOfUp(int dg, int dz) {
    int day=0,g=0,z=0;
    int[][] futou = new int[2][4];
    int index = 0;

    //�������ϸ��׿�ͷ
    g = YiJing.JIA;
    day = 10+dg-YiJing.JIA;
    z = (dz - day)<=0 ? dz - day + 12 : dz - day;
    z = z<=0 ? z + 12 : z;
    //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
    if(QiMen.sanyuan[1][g][z]==1) {
      futou[index++] = new int[]{day,g,z,0};
    }

    //���Ƶ��ϸ��׿�ͷ
    g = YiJing.JIA;
    day = dg - g;
    z = (dz - day)<=0 ? dz - day + 12 : dz - day;
    //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
    if(QiMen.sanyuan[1][g][z]==1) {
      futou[index++] = new int[]{day,g,z,0};
    }
    //˳�Ƶ��¸��׿�ͷ
    g = YiJing.JIA;
    day = 10 + g - dg;
    z = (dz+day>12) ? dz+day-12:dz+day;
    //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
    if(QiMen.sanyuan[1][g][z]==1) {
      futou[index++] = new int[]{0-day,g,z,0};
    }

    // ����ɴ��ڼ��ģ������Ƶ��ϸ�����ͷ����˳�Ƶ��¸�����ͷ����˳�Ƶ����¸�����ͷ
    if(dg>YiJing.JI) {
      g = YiJing.JI;
      day = dg - g;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
      g = YiJing.JI;
      day = 10 + g - dg;
      z = (dz+day>12) ? dz+day-12:dz+day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
         futou[index++] = new int[]{0-day,g,z,0};
      }
      g = YiJing.JI;
      day = 10+dg-YiJing.JI;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
    }
    //�����С�ڼ��ģ���˳�Ƶ��¸�����ͷ�������Ƶ��ϸ�����ͷ�������Ƶ����ϸ�����ͷ
    if(dg<=YiJing.JI) {
      g = YiJing.JI;
      day = g - dg;
      z = (dz+day>12) ? dz+day-12:dz+day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
         futou[index++] = new int[]{0-day,g,z,0};
      }
      g = YiJing.JI;
      day = 10+dg-YiJing.JI;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
      g = YiJing.JI;
      day = 20+dg-YiJing.JI;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      z = z<=0 ? z + 12 : z;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
    }
    /**
     * ����Ӛ��Еr�g������--���Ӳ��^�壬�����^�š������񳬹��������ϣ����������������ϣ���Ҫ����
     * �����9�գ�������С��5�գ���������
     * ����<9�գ�������>5�գ���������
     * ����9��5���������㡣
     * �˴��ټ���һ�죬����x>-6 && x<10
     */
    //ȡС��9��>-5�ģ�
    int[] ret = new int[4];
    if(ret[1]<1) {
    for(int i=0; i<futou.length; i++) {
      if(futou[i][1]>0  && futou[i][0] >0 && futou[i][0]<9) {
        ret = futou[i];
      }
    }
    }
    if(ret[1]<1) {
    for(int i=0; i<futou.length; i++) {
     if(futou[i][1]>0 && futou[i][0] >-5 && futou[i][0]<0) {
        ret = futou[i];
      }
    }
    }
    if(ret[1]<1) {
    for(int i=0; i<futou.length; i++) {
     if(futou[i][1]>0 && futou[i][0] >=9) {
        ret = futou[i];
      }
    }
    }

    return ret;
  }
  /**
   * �õ���ͷ��ר���ڲ𲹷�
   * @return
   */
  public int[] getFutou(int dg, int dz) {
    int day=0,g=0,z=0;
    int[] futou = new int[4];

    if(dg==YiJing.JI || dg==YiJing.JIA) {
      g = dg;
      z = dz;
      futou = new int[]{0,dg,dz,0};
    }else if(futou[1]==0 && dg>YiJing.JI) {
      g = YiJing.JI;
      day = dg - g;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
          futou = new int[]{day,g,z,0};
    }else if(futou[1]==0 && dg>YiJing.JIA) {
      g = YiJing.JIA;
      day = dg - g;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
          futou = new int[]{day,g,z,0};
    }

    for(int i=1; i<4; i++) {
      if (QiMen.sanyuan[i][g][z] == 1) {
        futou[3] = i;
        break;
      }
    }

    return futou;
  }

  /**
   * ���µ��ж�
   * ����ԭ���ڽ������У���С��ǰһ�ڵľ������£���Ϊֻ��15��������
   *           ���½ں�һ�������ͬ����Ҳ������
   *           ���²���1��12��
   */
  private boolean isMoreBig(int y, int jieNo) {
    int yun = daoc.getYunYue(y);
    int _date2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    int _m2 = _date2/1000000;
    if((Calendar.MONTHN1*1000000+Calendar.DAYN1*10000+Calendar.HOUR) < _date2 &&
       Math.abs(_m2 - Calendar.MONTHN1)<=2) {
      if(Calendar.YUN && !isYunJieqi(y,jieNo)) {
        return true;
      }else{
        return false;
      }
    }else{
      if(!Calendar.YUN && isYunJieqi(y,jieNo)) {
        return false;
      }else{
        return true;
      }
    }
  }

  /**
   * ���������к�1����.2��ˮ.3...
   * ����û��1��2��
   * @param y
   * @param jieNo
   * @return
   */
  private boolean isYunJieqi(int y, int jieNo) {
    if(jieNo<=2)
      return false;
    int yun = daoc.getYunYue(y);
    int jieqi0 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-2];
    int jieqi1 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-1];
    int jieqi2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    if(jieqi2<jieqi1 && jieqi2/1000000==yun)
      return true;
    if(jieqi2>jieqi1 && jieqi1<jieqi0 && jieqi2/1000000==yun)
      return true;

    return false;
  }

  public static void main(String[] args) {
    DaoQiMen qm = new DaoQiMen();
    int[] ret = qm.getFutou(6,8);
    //Debug.out("ʵ���ǣ�g = "+ret[1]+"; z="+ret[2]+ "; day = "+ret[0]+"; ��"+ret[3]+"Ԫ��");
  }

  /**
   * �õ�������������
   * ���ݣ�1+�ι�-���ݼ��� ����С��9��0Ҫ�Ӽ�9����9
   * ���ݣ�1-�ι�+���ݼ���
   * @param i ���̺ι�
   */
  public int getDipanJiyi(int i) {
    int ju = Math.abs(whichJu);
    if(whichJu > 0) {
      return (10+i-ju)%9==0 ? 9 : (10+i-ju)%9;
    }else{
      return (10-i+ju)%9==0 ? 9 : (10-i+ju)%9;
    }
  }

  /**
   * �õ�Ѯ���� Ϊ Ѯ����Ӧ��=��֧�� �� ����� �ٶ�Ӧһ��
   * int[] xunshu = {1, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2};
   * xunname = {"","����(��)","����(��)","����(��)","����(��)","�׳�(��)","����(��)"};
   * �����=0,��xunshu[0]=xunname[1]Ϊ�����죻
   *   ����=10����Ϊxunshu[10]=xunname[2]Ϊ����(��)��
   *   Ѯ��+4���ɵõ�����Ӧ�����˳����ˣ�
   */
  public int getXunShu(int sg, int sz) {
    return QiMen.xunshu[(sz-sg+120)%12];
  }

  /**
   * �õ�ֱ��ֱʹ����
   * ���ݣ�ֱ��ֱʹ����=���þ���+ʱ������Ѯ���� �C 1
   * ���ݣ�ֱ��ֱʹ����=�������þ�����ʱ������Ѯ����
   */
  public int getZhiFuShi(int sg, int sz) {
    int xs = getXunShu(sg, sz);
    int zfs = 0;
    if(this.whichJu>0) {
      zfs = (whichJu+xs-1+90)%9;
    }else{
      zfs = ((0-whichJu)-xs+1+90)%9;
    }
    return zfs==0?9:zfs;
  }

  /**
   * ��ֱ���乬
   * ����ʱ���󷨣�ֱ���乬��=���� �� 1��ʱ������������������Ӧ�Ĵ�����
   * ����ʱ���󷨣�ֱ���乬��=����������ʱ������������������Ӧ�Ĵ�����
   * ���ϲ��������ݣ��������Ϊֱ����ô�����ݽ��������߹���������Ϊֱʹ��
   * ixzfΪ2Ϊ����̣�1Ϊ������
   */
  private int ixzf = 1;  //Ĭ��������ֵ
  public void setXzf(int xzf) {
  	this.ixzf = xzf;
  }
  public int getZhifuGong(int sg, int sz) {  //Сֵ������̲�������ģ�һ���������Ƕ����ˣ���Ϊ�Ǵ�ֵ�����Ƴ�����
//  	if(ixzf==2){  //Сֵ�������ֵ���������ֵ���乬�������Ѯ�׶�Ӧ�Ĳظ�����������
//    	int xungan = QiMen.xungan[(sz-sg+12)%12];
//    	int i=1;
//    	for(; i<=9; i++) {
//    	  if(QiMen.sjly5[getDipanJiyi(i)]==xungan) break;
//    	}
//    	return i;
//    }
  	
  	//Ϊ������ֵ��
    if(sg == 1) {
      int g2 = getZhiFuShi(sg, sz);
//      /**
//       * (1)���幬�ĺι������ݼ�igJigongָ���Ĺ�����������2�������ǵ�һ���Ķ�
//       */
//      g2 = g2!=5 ? g2 : (this.getJu()>0)? (this.igJigong==0?2:igJigong) : 2;
      return g2;
    }

    int g = 0;
    if(this.whichJu>0) {
      g = (whichJu-1+QiMen.sjly2[sg]+90)%9 ;
    }else{
      g = ((0-whichJu)+1-QiMen.sjly2[sg]+90)%9;
    }

//    /**
//     * (1)���幬�ĺι������ݼ�igJigongָ���Ĺ�����������2�������ǵ�һ���Ķ�
//     */
//    g = g!=5 ? g : (this.getJu()>0)? (this.igJigong==0?2:igJigong) : 2;

    return g==0?9:g;
  }
  
  public int getZhifuGong() {
  	return this.getZhifuGong(SiZhu.sg, SiZhu.sz);
  }

  /**
   * ��ֱʹ�乬
   * ����ʱ���󷨣�ʱ�ɼף����ң���������ֱʹ�乬��ֱʹ������ʱ����ʮ�������������
   * ����ʱ���󷨣�ʱ�ɼף�0���ң���������ֱʹ�乬��ֱʹ������ʱ����ʮ�������������
   * ���ϲ��������ݣ����ֱʹ���У����Լ���������
   */
	public int getZhishiGong(int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz);
    int g = 0;
    if(this.whichJu>0) {
      g =  (zfsxs + sg -1 +90)%9;
    }else{
      g =  (zfsxs + (11 - sg) -1 +90)%9;
      
    }

//    /**
//     * (2)���幬�ĺι������ݼ�igJigongָ���Ĺ�����������2�������ǵڶ����Ķ�
//     */
//    g = g!=5 ? g : (this.getJu()>0)? (this.igJigong==0?2:igJigong) : 2;

    return g==0?9:g;
  }
  public int getZhishiGong() {
  	return this.getZhishiGong(SiZhu.sg, SiZhu.sz);
  }

  /**
   * �õ�ָ������������
   * ���ǲ��������ݾ֣���Զ˳ʱ�����λ��Ű˹�
   * ��������幬�Ĺ�û���κ�Ӱ�죬��Ϊ��������ֱ��ȡ��5����Ӧ����
   */
  public int getGongXingOfZhuan(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //ֵ��Ϊĳ�ǵ����
    int zflg = getZhifuGong(sg,sz); //ֵ���乬��
    if(zflg == 5)  //����5������2��
      zflg = 2;  //2ԭֵΪ2
    if(zfsxs == 5) //Ϊ5��Ϊ2�����������������5�й�����һ����һ����?
      zfsxs = 2;  //2ԭֵΪ2
    int i=1;
    int j=0;
    int k=0;

    for(; i<QiMen.jx2.length; i++) {
      if(zfsxs == QiMen.jx2[i])  break;
    }
    for(; j<QiMen.jgxh.length; j++) {
      if(zflg==QiMen.jgxh[j])  break;
    }
    for(; k<QiMen.jgxh.length; k++) {
      if(gong==QiMen.jgxh[k])  break;
    }
    if(gong==5)
      return 0;

    int x = (i + k - j + 8)%8==0?8:(i + k - j + 8)%8;
    //x = x == 5? 2:x;
    return QiMen.jx2[x];  ////����ʲ�������5����Ϊֻ��8����������
  }
  //�ɹ�����ֵ�����幬���Ĺ�
  public int getGongXingOfFei(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //ֵ��Ϊĳ�ǵ���ţ���5Ϊ������
    int zflg = getZhifuGong(sg,sz); //ֵ���乬������5Ϊ�й�

    int x = 0;
    if(whichJu>0) //zflg=5,1 gong==5,9 zfsxs=1,9;
    	x = (gong - zflg + zfsxs + 9)%9 == 0 ? 9 : (gong - zflg + zfsxs + 9)%9;
    else
    	x = (zflg - gong + zfsxs + 9)%9==0 ? 9 : (zflg - gong + zfsxs + 9)%9;

    return x;  
  }

  /**
   * �õ�ָ������������
   * ���ǲ��������ݾ֣���Զ˳ʱ�����λ��Ű˹�
   * ��������幬�Ĺ�û���κ�Ӱ�죬��Ϊ��������ֱ��ȡ��5����Ӧ����
   * simon 2011-12-23
   */
  public int getGongMenOfZhuan(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //ֵʹΪĳ�ŵ����
    int zslg = getZhishiGong(sg,sz); //ֵʹ�乬��
    if(zslg == 5)  //����5������2��
      zslg = 2;
    if(zfsxs == 5) //Ϊ5��Ϊ2�����������������5���ź���һ����һ����?
      zfsxs = 2;
    int i=1;
    int j=0;
    int k=0;

    for(; i<QiMen.bm2.length; i++) {
      if(zfsxs == QiMen.bm2[i])  break;  //zfsxs=2,8--> i=6,2
    }
    for(; j<QiMen.jgxh.length; j++) {
      if(zslg==QiMen.jgxh[j])  break;  //zslg=2,8--> j=5,1
    }
    for(; k<QiMen.jgxh.length; k++) {
      if(gong==QiMen.jgxh[k])  break;  //gong=5, k=0
    }
    if(gong==5)
      return 0;
    return QiMen.bm2[(i + k - j + 8)%8==0?8:(i + k - j + 8)%8];  //����ʲ�������5����Ϊֻ��8����������
  }
  public int getGongMenOfFei(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //ֵʹΪĳ�ŵ����
    int zslg = getZhishiGong(sg,sz); //ֵʹ�乬��
    
    int x = 0;
    if(whichJu>0) //zflg=5,1 gong==5,9 zfsxs=1,9;
    	x = (gong - zslg + zfsxs + 9)%9 == 0 ? 9 : (gong - zslg + zfsxs + 9)%9;
    else
    	x = (zslg - gong + zfsxs + 9)%9==0 ? 9 : (zslg - gong + zfsxs + 9)%9;

    return x;    
  }

  /**
   * ����ֱ����ʱ���ߣ���˳���棩
   * ������ֵ��������ֵ����ת�̷�
   */
  public int getGongShenOfZhuan(int gong, int sg, int sz) {
    //int zflg =  getZhifuGong(sg,sz); //ֵ���乬��
  	int zflg = 0;
    if(ixzf==2) zflg = this.getDipanzhifu(sg, sz);
    else zflg = getZhifuGong(sg,sz); //ֵ���乬��
    
    //(��)���幬�ĺι������ݼ�igJigongָ���Ĺ�����������2�������ǵڶ����Ķ�
    if(zflg == 5) {
    //zflg = 2;
    	zflg = (this.getJu()>0)? (igJigong==0?2:igJigong) : 2;      
    }
    int j=0;
    int k=0;

    for(; j<QiMen.jgxh.length; j++) {
      if(zflg==QiMen.jgxh[j])  break;
    }
    for(; k<QiMen.jgxh.length; k++) {
      if(gong==QiMen.jgxh[k])  break;
    }

    if(gong==5)
      return 0;
    if(whichJu>0)
      return (1 + k - j + 8)%8==0?8:(1 + k - j + 8)%8;
    else
      return (1 - k + j + 8)%8==0?8:(1 - k + j + 8)%8;
  }
  //���̷���ֵ���乬��5�������Ĺ�
  public int getGongShenOfFei(int gong, int sg, int sz) {
    int zflg = 0;
    if(ixzf==2) zflg = this.getDipanzhifu(sg, sz);
    else zflg = getZhifuGong(sg,sz); //ֵ���乬��

		if(whichJu>0)
			return QiMen.fpjg[(gong-zflg+9+1)%9==0 ? 9 : (gong-zflg+9+1)%9];
		else
			return QiMen.fpjg[(zflg-gong+9+1)%9==0 ? 9 : (zflg-gong+9+1)%9];
  }
  /**
   * ת�̷��������ǹ�ʽ��
   * ���ݣ��������Ƕ�Ӧ���֣������Ƕ�Ӧ���֣�����+1��������С��1��ô���������9��Ϊ���Ƕ�Ӧ�����֣���
   * ���ݣ��������Ƕ�Ӧ���֣������������Ƕ�Ӧ����+1��������С��1��ô���������9��Ϊ���Ƕ�Ӧ�����֣���
   */
  public int getTianpanJiyiOfZhuan(int gong, int sg, int sz) {
    if(gong==5)
      return 0;
    int x = getGongXingOfZhuan(gong, sg, sz);
    int g = 0;

    if(this.whichJu>0) {
      g =  (x - whichJu + 1 + 90)%9;
    }else{
      g =  ((0 - whichJu) - x + 1 + 90)%9;
    }

    return g==0?9:g;
  }
  public int getTianpanJiyiOfFei(int gong, int sg, int sz) {
    int x = getGongXingOfFei(gong, sg, sz);  //�õ��ù��������ǣ�������������Ϊ�����
    //return QiMen.sjly5[getDipanJiyi(x)]; //�ù��ĵ���������Ŷ�Ӧ�����
    return getDipanJiyi(x); //�ù��ĵ���������ż�Ϊ���̵��������
  }

  /**
   * �ڰ˹�ͼ����������������
   * @param o
   * @return
   */
  private String format(String o) {
    if(o==null)
      o="";
    if(o.trim().equals("null"))
       o="";
    if(o.getBytes().length<8)
      o=daoyj.getRepeats(" ", (8-o.getBytes().length))+o;
    else if(o.getBytes().length==12){
    	return (o+daoyj.getRepeats(" ",16-o.getBytes().length));
    }else if(o.getBytes().length==10){
    	return (daoyj.getRepeats(" ",2)+o+daoyj.getRepeats(" ",16-2-o.getBytes().length));
    }
    
    int len = o.getBytes().length;
    int hlen = (16-len)/2;
    return (daoyj.getRepeats(" ",hlen)+o+daoyj.getRepeats(" ",hlen));
  }
  /**
   * �ؾŹ�ͼ���Կ������
   * @param o
   * @return
   */
  private String formatLeft(String o) {
    if(o==null)
      o="";
    o = o.trim();
    o = o.replaceAll("null", "");

    int len = o.getBytes().length;
    int hlen = 16-len;
    return (o+daoyj.getRepeats(" ",hlen));
  }

  /**
   * ����Ź�ͼ
   * @param o
   */
  private void out1(Object o) {
    str.append("    "+o.toString()+"\r\n");
  }

  /**
   * �ž�
   * ���õ���ÿ����ʾ����Ϣ
   * _6��Ϊ�գ�_5��ʾ����+������ɡ�_4��ʾ����+������ɡ�_3��ʾ����_2��ʾ�����������ꡢ�¡��ա�ʱ�ɱ�ʶ
   */
  private String[][] makeJiuGong(int iZfs) {
    String[][] rs = new String[10][7];
    String[] bs = whichJu>0?QiMen.bs1:QiMen.bs1;
    
    /**
     * ����X0Ϊ���̸ɹ�����˥�����̸ɹ�����˥�����Ź�����˥�����ǹ�����˥
     */
    for(int i=1; i<=9; i++) {
    	if(i==5) continue;
    	//���̸�
    	rs[i][6] = QiMen.gan_gong_wx2[gInt[2][3][i]][i];
    	rs[i][6] += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[2][3][i]][SiZhu.yz]];
    	//���̸�
    	rs[i][6] += QiMen.gan_gong_wx2[gInt[4][5][i]][i];
    	rs[i][6] += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][i]][SiZhu.yz]];
    	//����
    	rs[i][6] +=  QiMen.men_gong2[gInt[3][1][i]][i];
    	rs[i][6] +=  QiMen.men_yue[gInt[3][1][i]][SiZhu.yz];
    	//����
    	rs[i][6] +=  QiMen.xing_gong[gInt[2][1][i]][i];
    	rs[i][6] +=  QiMen.xing_yue[gInt[2][1][i]][SiZhu.yz];
    }
//    //1. ����x5=����+ ��������
//    for(int i=1; i<=9; i++) {
//      if(i==2) {//���Ϊ2��������Ҫͬʱ��ʾ��5���ĵ�������
//        rs[i][5] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
//      } else {
//        rs[i][5] = QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
//      }
//    }
//
//    //2. ����x4=����+�������ǣ�Ϊ������ȡ����5������   
//    for(int i=1; i<=9; i++) {
//      if(gInt[2][1][i]==2) {//������̾���˳��Ϊ2��������Ҫͬʱ��ʾ��5�����������Ǻ���
//        rs[i][4] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.jx1[5] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
//      } else {
//        rs[i][4] = QiMen.jx1[gInt[2][1][i]] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
//      }
//    }
    //1. ����x5=����+ �����������ǣ��������Ķ������幬֮�Ĺ�����
    for(int i=1; i<=9; i++) {
      if(this.isJiGong(i)) {//���igJigongΪ0��2�����ڵ�2��Ҫͬʱ��ʾ��5���ĵ������ǣ����igJigongΪ8�����8��Ҫͬʱ��ʾ��5���ĵ�������
        rs[i][5] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.jx1[gInt[2][1][i]]  + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
      } else {
        rs[i][5] = QiMen.jx1[gInt[2][1][i]] + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
      }
    }

    //2. ����x4=����+�������ǣ�Ϊ������ȡ����5������   
    for(int i=1; i<=9; i++) {
      if(this.isTpJigong(i)) {//���igJigongΪ0��2�����ڵ�2��Ҫͬʱ��ʾ��5���ĵ������ǣ����igJigongΪ8�����8��Ҫͬʱ��ʾ��5���ĵ�������
        rs[i][4] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
      }else {
        rs[i][4] = QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
      }
    }
    
    //3. ����x3=����
    if(iZfs==1) {
      for(int i=1; i<=9; i++) {
        rs[i][3] = bs[gInt[1][1][i]] + "    " ;
      }
    }else{
    }
    
    /**
     * 4. ����x2=һЩ��Ҫ�ģ����ǡ��һ������ǻ��̡����̸���Ź����
     * ���ǻ��̶Ե��̸�ҲҪ����
     */
    for(int i=1; i<=9; i++) {
    	int dz = QiMen.jgdz[i];  //�õ��Ź����صĵ�֧
    	int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
    	
    	if(SiZhu.YIMA[SiZhu.sz][dz1] || SiZhu.YIMA[SiZhu.sz][dz2]) {
				rs[i][2] += "�� ";  //�Ƿ���ʱ֧����
			}
    	if(SiZhu.YIMA[SiZhu.rz][dz1] || SiZhu.YIMA[SiZhu.rz][dz2]) {
				rs[i][2] += "�R ";  //�Ƿ�����֧����
			}
    	
    	String gangongHua = QiMen.gan_gong_hua[gInt[2][3][i]][i];
    	if(gangongHua!=null) 
    		rs[i][2] += "�� ";  //ʱ������    			
    	if(isTpJigong(i) && gangongHua==null && QiMen.gan_gong_hua[gInt[4][5][5]][i]!=null) 
    		rs[i][2] += "�� ";
    	
    	int gangongNum_t1 = QiMen.gan_gong_t[gInt[2][3][i]][i];  //���̸�+�����������ǻ���
	    if(gangongNum_t1!=0) rs[i][2] += "�� ";
	    if(isTpJigong(i) && gangongNum_t1==0 && QiMen.gan_gong_t[gInt[4][5][5]][i]!=0) 
    		rs[i][2] += "�� "; //�������̸ɵ�
	    
	    int gangongNum_d1 = QiMen.gan_gong_t[gInt[4][5][i]][i];  //���̸�+�����������ǻ���
	    if(gangongNum_d1!=0) rs[i][2] += "�� ";
	    if(isJiGong(i) && gangongNum_d1==0 && QiMen.gan_gong_t[gInt[4][5][5]][i]!=0) 
    		rs[i][2] += "�� "; //���ǵ��̸ɵ�
	    
	    //���̸�����̸ɵ�֧����
	    if(this.isTDZiXing(i)) rs[i][2] += "�� "; 
	    if(this.isNyrsXing(i)) rs[i][2] += "�� ";
	    
	    int gangongNum_t2 = QiMen.gan_gong_ch[gInt[2][3][i]][i];  //���̸�+����ֻ���������̸ɣ��������ǻ���
	    if(gangongNum_t2!=0) rs[i][2] += "�� ";
	    if(isTpJigong(i) && gangongNum_t2==0 && QiMen.gan_gong_ch[gInt[4][5][5]][i]!=0) 
    		rs[i][2] += "�� ";
	    
	    String ganganCh = QiMen.gan_gan_ch[gInt[2][3][i]][gInt[4][5][i]];  //���̸�+���̸��Ƿ����
	    if(ganganCh!=null) rs[i][2] += "�� ";
	    if(isTpJigong(i) && ganganCh==null && QiMen.gan_gan_ch[gInt[4][5][5]][gInt[4][5][i]]!=null) 
    		rs[i][2] += "�� ";
	    
	    //���̸ɡ����̸��Ƿ�����
	    boolean isTD6he = YiJing.TGWUHE[gInt[2][3][i]][gInt[4][5][i]]!=0;
	    if(isTD6he) rs[i][2] += "�� ";
	    else if(isTpJigong(i) && YiJing.TGWUHE[gInt[4][5][5]][gInt[4][5][i]]!=0) //����2��
	    	rs[i][2] += "�� ";
	    else if(isJiGong(i) && YiJing.TGWUHE[gInt[2][3][i]][gInt[4][5][5]]!=0)  //����2��
    		rs[i][2] += "�� ";
	    //else if(gInt[2][1][i]==2 && i==2 && YiJing.TGWUHE[gInt[4][5][5]][gInt[4][5][5]]!=0)  //����̸�2��
    	//	rs[i][2] += "�� ";
	    
	    //�����֧�빬֧�Ƿ�����
	    if(isTDG3he(i)) rs[i][2] += "�\ ";
	    else {//�������ع�֧���ϣ���������֧��ϡ��칬��ϡ��칬���ϾͲ��ж���
	    	if(this.isTDZi3he(i)) rs[i][2] += "�Q ";
	    	if(this.isTG6he(i)) rs[i][2] += "�� ";  //���칬���ϣ��Ͳ��ж��칬�����
	    	else if(this.isTG3he(i)) rs[i][2] += "Ǣ ";
	    }
    }
    
    /**
     * 5. ����x1=�ꡢ�¡��ա�ʱ����������Ĺ
     * ���������ʱ��5�����2��
     */
  //���Ϲ�����˥
    for(int i=1; i<=9; i++) {
    	rs[i][1] += QiMen.gong_yue[i][SiZhu.yz]+" ";
    }
    
    int[] xksz = pub.getXunKong(SiZhu.sg,SiZhu.sz);  //�õ�ʱ��Ѯ�յ�֧    	
    int[] xkrz = pub.getXunKong(SiZhu.rg,SiZhu.rz);  //�õ�����Ѯ�յ�֧    	
    //���ڼ׿�ͷ�ģ���Ҫ���⻻��
    int jiakaitou1 = -1,jiakaitou2 = -1,jiakaitou3 = -1,jiakaitou4 = -1 ; 
    for(int i=1; i<=9; i++) {
    	if(SiZhu.ng==1) jiakaitou1 = this.getXunShu(SiZhu.ng, SiZhu.nz)+4;
    	if(SiZhu.yg==1) jiakaitou2 = this.getXunShu(SiZhu.yg, SiZhu.yz)+4;
    	if(SiZhu.rg==1) jiakaitou3 = this.getXunShu(SiZhu.rg, SiZhu.rz)+4;
    	if(SiZhu.sg==1) jiakaitou4 = this.getXunShu(SiZhu.sg, SiZhu.sz)+4;
    		
    	if(gInt[2][3][i]==SiZhu.ng || jiakaitou1==gInt[2][3][i]) rs[i][1] += "�� ";
    	if(gInt[2][3][i]==SiZhu.yg || jiakaitou2==gInt[2][3][i]) rs[i][1] += "�� ";
    	if(gInt[2][3][i]==SiZhu.rg || jiakaitou3==gInt[2][3][i]) rs[i][1] += "�� ";
    	if(gInt[2][3][i]==SiZhu.sg || jiakaitou4==gInt[2][3][i]) rs[i][1] += "ʱ ";
    	if(isTpJigong(i)) {
    		if(gInt[4][5][5]==SiZhu.ng || jiakaitou1==gInt[4][5][5]) rs[i][1] += "�� ";
      	if(gInt[4][5][5]==SiZhu.yg || jiakaitou2==gInt[4][5][5]) rs[i][1] += "�� ";
      	if(gInt[4][5][5]==SiZhu.rg || jiakaitou3==gInt[4][5][5]) rs[i][1] += "�� ";
      	if(gInt[4][5][5]==SiZhu.sg || jiakaitou4==gInt[4][5][5]) rs[i][1] += "ʱ ";
    	}
    }
    
    
    for(int i=1; i<=9; i++) {
    	int dz = QiMen.jgdz[i];  //�õ��Ź����صĵ�֧
    	int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	        	 
    	
    	if(dz1==xksz[0] || dz2==xksz[0] || dz1==xksz[1] || dz2==xksz[1]) {
    		rs[i][1] += "�� ";  //ʱ������    			
    	}
    	if(dz1==xkrz[0] || dz2==xkrz[0] || dz1==xkrz[1] || dz2==xkrz[1]) {
    		rs[i][1] += "�i ";  //��������    			
    	}
    	
    	String gangongMu = QiMen.gan_gong_mu[gInt[2][3][i]][i];
    	if(gangongMu!=null) {
    		if(SiZhu.TGSWSJ[gInt[2][3][i]][SiZhu.yz]>5) rs[i][1] += "Ĺ ";  //���̸���Ĺ			
    		else rs[i][1] += "�� ";  //���̸��������������
    	}else if(isTpJigong(i) && QiMen.gan_gong_mu[gInt[4][5][5]][i]!=null) {
    		if(SiZhu.TGSWSJ[gInt[4][5][5]][SiZhu.yz]>5) rs[i][1] += "Ĺ ";  //���̸���Ĺ			
    		else rs[i][1] += "�� ";  //���̸��������������
    	}  
    	
    	//���̸���Ĺ�����жϿ���
    	gangongMu = QiMen.gan_gong_mu[gInt[4][5][i]][i];
    	if(gangongMu!=null) {
    		rs[i][1] += "ڣ ";  
    	}else if(isJiGong(i) && QiMen.gan_gong_mu[gInt[4][5][5]][i]!=null) {
    		rs[i][1] += "ڣ ";  
    	}  
    } 
        
    //��ʽ��
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=2; j++) {
        rs[i][j] = formatLeft(rs[i][j]);
      }
    }
    for(int i=1; i<=9; i++) {
      for(int j=3; j<=6; j++) {
        rs[i][j] = format(rs[i][j]);
      }
    }

    return rs;
  }
  
  /**
   * ����̸�����Ӧ�ĵ�֧�Ƿ�����
   * @param gong
   * @return
   */
  private boolean isTDZiXing(int gong) {
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	int dpdz1 = getZiOfgan(gInt[4][5][gong]), dpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZXING[tpdz1][dpdz1]!=0) return true;
  	else if(isTpJigong(gong) && YiJing.DZXING[tpdz2][dpdz1]!=0 ) //����2��
  		return true;
  	else if(this.isJiGong(gong) && YiJing.DZXING[tpdz1][dpdz2]!=0)  //����2��
  		return true;
  	else if(this.isJiGong(gong) && isTpJigong(gong) && YiJing.DZXING[tpdz2][dpdz2]!=0)
  		return true;
  	//tpdz2,dpdz2����Ҫ���
  	
  	return false;
  }
  
  /**
   * �жϸù��Ƿ����ꡢ�¡��ա�ʱ���乬������ǣ����ж��乬��֧��������ʱ��֧�Ƿ�����
   * @param gong
   * @return
   */
  private boolean isNyrsXing(int gong) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
  	int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
  	int[] tg2 = {gInt[2][3][gong],0}; //�õ��ù������
  	
  	if(isTpJigong(gong)) tg2[1]=gInt[4][5][5];
  	
  	int[] zi4 = {SiZhu.nz, SiZhu.yz, SiZhu.rz, SiZhu.sz};
  	int[] gan4 = {SiZhu.ng, SiZhu.yg, SiZhu.rg, SiZhu.sz};
  	for(int i = 0; i<gan4.length; i++) {  //���׿�ͷ��ת�ɶ�Ӧ�����
  		if(gan4[i]==YiJing.JIA) gan4[i] = getXunShu(gan4[i], zi4[i])+4;
  	}  	
  	
  	for(int i=0; i<zi4.length; i++) {
  		if((tg2[0]==gan4[i] || tg2[1]==gan4[i]) && (dz1==zi4[i] || dz2==zi4[i])) {
  			if(YiJing.DZXING[zi4[i]][dz1]!=0 || YiJing.DZXING[zi4[i]][dz2]!=0) return true;
  		}
  	}
  	
  	return false;
  }
  
  /**
   * �ж�����֧������֧����֧�Ƿ�����
   * @param gong
   * @return
   */
  private boolean isTDG3he(int gong) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
  	int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	int dpdz1 = getZiOfgan(gInt[4][5][gong]), dpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	boolean isTDG3he = YiJing.DZSHANHE[tpdz1][dpdz1][dz1]!=0 ||
		 									 YiJing.DZSHANHE[tpdz1][dpdz1][dz2]!=0;
  	if(isTDG3he) return true;
  	else if(isTpJigong(gong) && (YiJing.DZSHANHE[tpdz2][dpdz1][dz1]!=0 ||
  										YiJing.DZSHANHE[tpdz2][dpdz1][dz2]!=0)) //����2��
  		return true;
  	else if(isJiGong(gong) && (YiJing.DZSHANHE[tpdz1][dpdz2][dz1]!=0 ||
  										YiJing.DZSHANHE[tpdz1][dpdz2][dz2]!=0))  //����2��
  		return true;
  	//tpdz2,dpdz2����Ҫ���
  	
  	return false;
  }
  private boolean isTG6he(int gong) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
  	int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZLIUHE[tpdz1][dz1]!=0 || YiJing.DZLIUHE[tpdz1][dz2]!=0)
  		return true;
  	else if(isTpJigong(gong) && (YiJing.DZLIUHE[tpdz2][dz1]!=0 || 
  			YiJing.DZLIUHE[tpdz2][dz2]!=0)) return true;

  	return false;
  }
  private boolean isTG3he(int gong) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
  	int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZBANHE[tpdz1][dz1]!=0 || YiJing.DZBANHE[tpdz1][dz2]!=0)
  		return true;
  	else if(isTpJigong(gong) && (YiJing.DZBANHE[tpdz2][dz1]!=0 || 
  			YiJing.DZBANHE[tpdz2][dz2]!=0)) return true;

  	return false;
  }
  private boolean isTDZi3he(int gong) {
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	int dpdz1 = getZiOfgan(gInt[4][5][gong]), dpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZBANHE[tpdz1][dpdz1]!=0) return true;
  	else if(isTpJigong(gong) && YiJing.DZBANHE[tpdz2][dpdz1]!=0 ) //����2��
  		return true;
  	else if(isJiGong(gong) && YiJing.DZBANHE[tpdz1][dpdz2]!=0)  //����2��
  		return true;
  	//tpdz2,dpdz2����Ҫ���
  	
  	return false;
  }
  //�õ�����ֵ������Сֵ�������ʱ��Ѯ���ڵ���֧�乬
  public int getDipanzhifu(int sg, int sz) {
		int xungan = QiMen.xungan[(sz-sg+12)%12];
		int i=1;
		for(; i<=9; i++) {
		  if(QiMen.sjly5[getDipanJiyi(i)]==xungan) break;
		}
		return i;
  }
  
  /** �����ǵõ���Ӧ�ĵ�֧ */
  private int getZiOfgan(int gan) {
  	return new int[]{0,0,0,0,0,YiJing.ZI,YiJing.XU,YiJing.SHEN,YiJing.WUZ,YiJing.CHEN,YiJing.YIN}[gan];
  }

  /**
   * �õ�������Ҫ�أ����������֧��������س��������Ϊ׼
   * gInt[0][0]=������֧ ���� ��Ԫ ���� ֵ��ʹ12  ֵ���乬13  ֵʹ�乬14 Сֵ��
   * gInt[1][1][1-9]=����,gInt[1][2][1-9]=��������
   * gInt[2][1][1-9]=����˳��������������,�������ǣ�������У��������ǲ�֧
   * gInt[3][1][1-9]=��������,gInt[3][2]=������
   * gInt[4][1][1-9]=�Ź�������������������У����̾��ǣ����̰��ţ��������ǣ������������С� �������׵�֧
   */
  public void getGlobleInfo(int iszf, int iZfs) {
  	this.setXzf(iZfs);
    gInt[0][0] = new int[]{0, SiZhu.ng, SiZhu.nz, SiZhu.yg, SiZhu.yz, SiZhu.rg,
        SiZhu.rz, SiZhu.sg,SiZhu.sz, 
        whichJie, whichYuan, whichJu,
        getZhiFuShi(SiZhu.sg, SiZhu.sz), getZhifuGong(SiZhu.sg, SiZhu.sz),
        getZhishiGong(SiZhu.sg, SiZhu.sz),1};
    /**
     * ���̣�Сֵ�����ֵ�����
     */
    if(iZfs==1) {
      for(int i=1; i<=9; i++) {
        gInt[1][1][i] = iszf==2 ? getGongShenOfFei(i,SiZhu.sg,SiZhu.sz) : getGongShenOfZhuan(i,SiZhu.sg,SiZhu.sz);
      }
    }else{  //�����ֵ��    	
    	for(int i=1; i<=9; i++) {
        gInt[1][1][i] = iszf==2 ? getGongShenOfFei(i,SiZhu.sg,SiZhu.sz) : getGongShenOfZhuan(i,SiZhu.sg,SiZhu.sz);
      }
    }
    for(int i=1; i<=9; i++) {
    	try{
    		gInt[1][2][i] = iszf==2 ? QiMen.fpjswx[gInt[1][1][i]] : QiMen.bs3[gInt[1][1][i]];
    	}catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("gInt[1][1]["+i+"]="+gInt[1][1][i]);
    	}
    }

    /**
     * ����
     */
    for(int i=1; i<=9; i++) {
      gInt[2][1][i] = iszf==2 ? getGongXingOfFei(i,SiZhu.sg,SiZhu.sz) : 
      	getGongXingOfZhuan(i,SiZhu.sg,SiZhu.sz);  
    }
    for(int i=1; i<=9; i++) {
      gInt[2][2][i] = iszf==2 ? QiMen.fpjxwx[gInt[2][1][i]] : QiMen.jx3[gInt[2][1][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[2][3][i] = iszf==2 ? getTianpanJiyiOfFei(i,SiZhu.sg,SiZhu.sz) :
      	getTianpanJiyiOfZhuan(i,SiZhu.sg,SiZhu.sz);
    }
    for(int i=1; i<=9; i++) {
      gInt[2][5][i] = QiMen.sjly4[gInt[2][3][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[2][3][i] = QiMen.sjly5[gInt[2][3][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[2][4][i] = YiJing.TIANGANWH[gInt[2][3][i]];
    }


    /**
     * ����
     */
    for(int i=1; i<=9; i++) {
      gInt[3][1][i] = iszf==2 ? getGongMenOfFei(i,SiZhu.sg,SiZhu.sz) :
      	getGongMenOfZhuan(i,SiZhu.sg,SiZhu.sz);  
    }
    for(int i=1; i<=9; i++) {
      gInt[3][2][i] = iszf==2 ? QiMen.fpjmwx[gInt[3][1][i]]:QiMen.bm3[gInt[3][1][i]];
    }

    /**
     * ����
     */
    for(int i=1; i<=9; i++) {
      gInt[4][1][i] = QiMen.jgbg[i];  //YiJing.JingGuaName[]
    }
    for(int i=1; i<=9; i++) {
      gInt[4][2][i] = QiMen.jgwh[i];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][3][i] = iszf==2 ? QiMen.fpjg[i]:QiMen.dpjx4[i];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][4][i] = iszf==2 ? QiMen.fpjg[i]:QiMen.dpbm4[i];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][5][i] = getDipanJiyi(i);
    }
    for(int i=1; i<=9; i++) {
      gInt[4][7][i] = QiMen.sjly4[gInt[4][5][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][5][i] = QiMen.sjly5[gInt[4][5][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][6][i] = YiJing.TIANGANWH[gInt[4][5][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][8][i] = QiMen.jgdz[i];
    }
  }

  /**
   * �õ���ǰ����
   * ����Ϊ���֣�����Ϊ���ݾ֣�
   */
  public int getJu() {
  	return whichJu;
  }
  /**
   * �Ƿ������幬����֮��
   * @param gong
   * @return
   */
  public boolean isJiGong(int gong) {
  	if(!iszf) return false;
  	//return gong== (igJigong==8?8:2);
  	return gong== getJiGong();
  }
  /**
   * �õ����幬�ڵ�������֮��
   * @param gong
   * @return
   */
  private boolean iszf = false;  //�����Ƿ���ת������
  public void setZhuanpan(boolean iszf) {
  	this.iszf = iszf;
  }
  public boolean iszf() {
  	return iszf;
  }
  public int getJiGong() {
  	//return gong== (igJigong==8?8:2);
  	return whichJu>0 ? (igJigong==8?8:2) : 2;
  }
  /**
   * �жϵ�ǰ�����������Ƿ�Ϊ���幬����֮���ĵ�����
   * ��Ϊ������ǣ����ʾ�ù�������������2����Ҫ�����⴦��
   * @param gong
   * @return
   */
  public boolean isTpJigong(int gong) {
  	//return gInt[2][1][gong]==(igJigong==8?8:2);
  	if(!iszf) return false;  //�������ţ�û�мĹ�
  	return gInt[2][1][gong]==getJiGong();
  }
  /**
   * �õ����幬����������֮����������������2��
   * @param gong
   * @return
   */
  public int getTpJigong() {
  	for(int i=1; i<=9; i++) {
  		if(isTpJigong(i)) return i;
  	}
  	return 0;
  }
}
