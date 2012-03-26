package org.boc.db.ly;

import javax.swing.text.BadLocationException;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.Calendar;
import org.boc.db.YiJing;
import org.boc.ui.ly.GuaBase;
import org.boc.util.Messages;
import org.boc.util.Public;

public class Guaing extends GuaBase{
	private DaoYiJingMain daoly;
  private LiuyaoPublic pub;
  private DaoCalendar daocal;
  /**
   *������֧ 
   * 0 �Ƿ�����1��0��
   * 1-8 ����
   * 9-11 ����������
   * 12-14ȡ��ũ�������գ����ǲ�����������
   * 15-17 ��������
   * 18 ���ڼ�
   */
  private int[] sz;  
  String pre1 = speat(2, Liuyao.FILL2);
	String pre2 = speat(30,Liuyao.FILL1);
	String pre3 = speat(2, Liuyao.FILL1);
  
	public Guaing(LiuyaoPublic pub){
		this.pub = pub;
		this.daoly = pub.getDaoYiJingMain();
		this.daocal = pub.getDaoCalendar();
	}
	public String getGua() {		
		try {
			//1. ��ʼ��
			init();
			//2. �õ�����
			sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
			//3. �õ��ԵĻ�����Ϣ
			getGua1();
			getGua2();
			//4. ��ӡ��ͷ
			printHead();
			//5. �������
			getGua3();
			pw.newLine();
		} catch (BadLocationException e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	void printHead() throws BadLocationException {
		if(!Liuyao.HEAD && !Liuyao.ALL) return;		

		pw.print(pre1+pre1 + (Liuyao.go?"��س":"÷��")+"��"+pre1,pw.SBLACK,false);
		pw.println(Liuyao.QGFS[mode]+pre2, pw.SBLACK,false);

		pw.print(pre1+pre1 + "������"+pre1,pw.SBLACK,false);
		pw.println(sz[9]+"��"+sz[10]+"��"+sz[11]+"�� "+Calendar.WEEKNAME[sz[18]]+pre2, pw.SBLACK,false);

		pw.print(pre1+pre1 + "ũ����"+pre1,pw.SBLACK,false);
		pw.print(sz[12]+"��"+(sz[0]==1 ? "��":""),pw.SBLACK,false);
		pw.println(Public.DAXIAO[sz[13]]+"�³�"+Public.DAXIAO[sz[14]]+" "+h+":"+mi+pre2, pw.SBLACK,false);

		int sunTrue = 0;
    if(sheng>=0 && shi>=0) {
      sunTrue = (Calendar.jingdu[sheng][shi] - 120 * 60) * 4;
      sunTrue += Calendar.zpsc[sz[10]][sz[11]];
      int h1 = (h*60*60+mi*60+sunTrue)/60/60;
      int mi1 = (h*60*60+mi*60+sunTrue)/60 - h1*60;
      int hour2 = h1*100+mi1;
      pw.print(pre1+pre1 + "������"+pre1,pw.SBLACK,false);
      pw.println(sz[12]+"��"+(sz[0]==1?"��":"")+sz[13]+"�³�"+sz[14]+" "+h1+":"+mi1+pre2, pw.SBLACK,false);
    }
		// 3.�������
    pw.print(pre1+pre1 + "��֧��"+pre1,pw.SBLACK,false);
    pw.print(YiJing.TIANGANNAME[sz[1]]+YiJing.DIZINAME[sz[2]]+Liuyao.FILL1,pw.SBLUE,true);
    pw.print(YiJing.TIANGANNAME[sz[3]]+YiJing.DIZINAME[sz[4]],pw.SBLUE,true);
    pw.print("(��"+YiJing.DIZINAME[YiJing.DZCHONG2[sz[4]]]+")"+Liuyao.FILL1,pw.SBLACK,false);
    pw.print(YiJing.TIANGANNAME[sz[5]]+YiJing.DIZINAME[sz[6]],pw.SBLUE,true);
    pw.print("(��"+pub.getXunKongs(sz[5],sz[6])[0]+pub.getXunKongs(sz[5],sz[6])[1]+")"+Liuyao.FILL1,pw.SBLACK,false);
    pw.println(YiJing.TIANGANNAME[sz[7]]+YiJing.DIZINAME[sz[8]]+pre2,pw.SBLUE,true);
    
		// 4. �������		
    pw.print(pre1+pre1 + "��֣�"+pre1,pw.SBLACK,false);
    pw.print(isGuaFanLing?"�Է���"+Liuyao.FILL1:"",pw.SBLACK,false);
    pw.print(isYaoFanLing?"س����"+Liuyao.FILL1:"",pw.SBLACK,false);
    pw.print(isLiuChongGua?"������"+Liuyao.FILL1:"",pw.SBLACK,false);
    pw.print(isLiuHeGua?"������"+Liuyao.FILL1:"",pw.SBLACK,false);
    pw.print(iHouGua!=0?YiJing.HOUGUAZHAN2[iHouGua]+Liuyao.FILL1:"",pw.SBLACK,false);
    pw.print(iBianGua!=0?YiJing.JINGFANGZHAN2[iBianGua]+Liuyao.FILL1:"",pw.SBLACK,false);
    pw.print(pre2,pw.SBLACK,false);
    pw.newLine();
    pw.newLine();
	}
	
	public void getGua1() {
		//if(mode==Liuyao.YAO || mode==Liuyao.SXD) 
  		
  	if(mode==Liuyao.SJTG || mode==Liuyao.SJDZ){
  		int nian = mode==Liuyao.SJTG ? sz[1] : mode==Liuyao.SJDZ ? sz[2] : sz[2];
      up = (nian+sz[13]+sz[14])%8 == 0 ? 8 : (nian+sz[13]+sz[14])%8;
      down = (up+sz[8])%8 == 0 ? 8 : (up+sz[8])%8;
      acts = new int[]{down%6 == 0 ? 6 : down%6};      
  	}
	}
	
	//1. ��/��/��/�˹����Ե��ϡ����Լ���س
	int upHu ,downHu ,upBian ,downBian ;	
	//2. �������Ե����������˹����Ե��ϡ�����
	int whichGongZhu,whichGongHu ,whichGongBian ;	
	int upGong ,downGong ;
	//3. ���������˹��Ե�����
	int whichWh ;
	//4. �õ���/��/��������
	int[] upGuaXiang ,downGuaXiang ,guaXiang ;
	int[] upGuaXiangHu ,downGuaXiangHu ,guaXiangHu ;
	int[] upGuaXiangBian ,downGuaXiangBian ,guaXiangBian;
	//5. �õ���/��/������س��Ӧس��λ��
	int shiYao ,yingYao ,shiYaoHu ,yingYaoHu ,shiYaoBian ,yingYaoBian ;
	//6. �õ���/��/��/�����Եĵ�֧
	int[] upDizi ,downDizi,diZi ,upDiziHu ,downDiziHu ,diZiHu ;
	int[] upDiziBian ,downDiziBian ,diZiBian ;
	int[] upDiziGong ,downDiziGong ,diZiGong;

	//7. �õ���/��/��/���Ե�����
	int[] liuQin ,liuQinHu ,liuQinBian ,liuQinGong;
	//8. �õ�����
	int[] liuShen ;
	//10. �õ������������
	int yueGuaShen,shiShen ;
	boolean isHasGuaShen ;
	//11. ���񡢷���
	int[] ff ;
	//12. �Է��ʡ�س����
	boolean isGuaFanLing ,isYaoFanLing ;
	//13. �õ������Դ���س��
	String[] guaCi ;
	//13. �����Ƿ�Ϊ����������
	boolean isLiuChongGua ,isLiuHeGua;
	//14. ʮ�����ԡ�ʮ������
	int iHouGua, iBianGua;  
	public void getGua2() {
		try{
			//1. ��/��/��/�˹����Ե��ϡ����Լ���س
			up = up % 8 == 0 ? 8 : up % 8;
			down = down % 8 == 0 ? 8 : down % 8;
		
			upHu = daoly.getHuGuaUpOrDown(up, down, "UP");
			downHu = daoly.getHuGuaUpOrDown(up, down, "DOWN");		
			upBian = daoly.getBianGuaUpOrDown(up, down, acts, "UP");
			downBian = daoly.getBianGuaUpOrDown(up, down, acts, "DOWN");
			
			//2. �������Ե����������˹����Ե��ϡ�����
			whichGongZhu = daoly.getWhichGong(up, down);
			whichGongHu = daoly.getWhichGong(upHu, downHu);
			whichGongBian = daoly.getWhichGong(upBian, downBian);
			
			upGong = whichGongZhu;
			downGong = whichGongZhu;
		
			//3. ���������˹��Ե�����
			whichWh = daoly.getWhichWH(whichGongZhu);
		
			//4. �õ���/��/��������
			upGuaXiang = daoly.getGuaXiang(up);
			downGuaXiang = daoly.getGuaXiang(down);
			guaXiang = daoly.mergeIntArray(upGuaXiang, downGuaXiang);
		
			upGuaXiangHu = daoly.getGuaXiang(upHu);
			downGuaXiangHu = daoly.getGuaXiang(downHu);
			guaXiangHu = daoly.mergeIntArray(upGuaXiangHu, downGuaXiangHu);
		
			upGuaXiangBian = daoly.getGuaXiang(upBian);
			downGuaXiangBian = daoly.getGuaXiang(downBian);
			guaXiangBian = daoly.mergeIntArray(upGuaXiangBian, downGuaXiangBian);
		
			//5. �õ���/��/������س��Ӧس��λ��
			shiYao = daoly.getShiYao(up, down);
			yingYao = daoly.getYingYao(shiYao);
		
			shiYaoHu = daoly.getShiYao(upHu, downHu);
			yingYaoHu = daoly.getYingYao(shiYaoHu);
		
			shiYaoBian = daoly.getShiYao(upBian, downBian);
			yingYaoBian = daoly.getYingYao(shiYaoBian);
		
			//6. �õ���/��/��/�����Եĵ�֧
			upDizi = daoly.getGuaDiZi(up, 1);
			downDizi = daoly.getGuaDiZi(down, 0);
			diZi = daoly.mergeIntArray(upDizi, downDizi);
		
			upDiziHu = daoly.getGuaDiZi(upHu, 1);
			downDiziHu = daoly.getGuaDiZi(downHu, 0);
			diZiHu = daoly.mergeIntArray(upDiziHu, downDiziHu);
			
			upDiziBian = daoly.getGuaDiZi(upBian, 1);
			downDiziBian = daoly.getGuaDiZi(downBian, 0);
			diZiBian = daoly.mergeIntArray(upDiziBian, downDiziBian);
			
			upDiziGong = daoly.getGuaDiZi(upGong, 1);
			downDiziGong = daoly.getGuaDiZi(downGong, 0);
			diZiGong = daoly.mergeIntArray(upDiziGong, downDiziGong);
		
			//7. �õ���/��/��/���Ե�����
			liuQin = daoly.getLiuQin(diZi, whichWh);
			liuQinHu = daoly.getLiuQin(diZiHu, whichWh);
			liuQinBian = daoly.getLiuQin(diZiBian, whichWh);
			liuQinGong = daoly.getLiuQin(diZiGong, whichWh);
		
			//8. �õ�����
			liuShen = daoly.getLiuShen(sz[5]);
			
			//9. ��ʼȡ������֧����֧����֧��ʱ֧
			
			//10. �õ������������
			yueGuaShen = daoly.getGuaShen(up, down, shiYao);
			isHasGuaShen = daoly.isGuaShen(up, down, yueGuaShen);
			shiShen = daoly.getShiShen(diZi, shiYao);
		
			//11. ���񡢷���
			ff = daoly.howManyFeiFu(liuQin, liuQinGong);
			
			//12. �Է��ʡ�س����
			isGuaFanLing = daoly.isGuaXIANGKE(whichGongZhu, whichGongBian);
			isYaoFanLing = false;
			for (int fl = 0; fl < acts.length; fl++) {
				if (daoly.isYaoFanLing(diZi[acts[fl]], diZiBian[acts[fl]])) {
					isYaoFanLing = true;
					break;
				}
			}
			//13. �����Ƿ�Ϊ����������
			isLiuChongGua = YiJing.LIUCHONGGUA[up][down] != 0;
	    isLiuHeGua = YiJing.LIUHEGUA[up][down] != 0;
	    
	    //14. ʮ�����ԡ�ʮ������
	  	iHouGua = YiJing.HOUGUA[up][down] ;
	  	iBianGua = YiJing.SIXTEENGUA[whichGongZhu][upBian][downBian] ;
	  	
			//20. �õ������Դ���س��
			guaCi = daoly.getGuaCiAndYaoCi(up, down);
		}catch(Exception ex) {
			ex.printStackTrace();
			Messages.error("DelYiJingMain("+ex+")");
		}
	}
	
	private void getGua3() {
		try{
			StringBuffer str = new StringBuffer();
	    //1). �õ�����
	    getGua31();
	    //2). �Դ���س��
	    daoly.getGuaYaoCiOut(str, guaCi, guaXiang, acts, up, down);
	    // Ѯ��
	    daoly.getXunKongOut(str, sz[5], sz[6]);
	    //3). ������
	    daoly.getYeuGuaShenOut(str, yueGuaShen, isHasGuaShen);
	    //4). ����
	    daoly.getShiShenOut(str, shiShen);
	    //5). ����
	    daoly.getFanLingOut(str, isGuaFanLing, isYaoFanLing);
	    //6). ����
	    daoly.getFuLingOut(str, diZi, diZiBian, up, down, upBian, downBian);
	    //7). ��������
	    daoly.getJinTuiShenOut(str, acts, diZi, diZiBian);
	    //8). ���ϡ�������
	    daoly.getLiuHeChongOut(str, up, down);
	    //9). �ɷ�
	    daoly.getFuFeiOut(str, ff, diZi, diZiGong);
	    //10). ����ռ��
	    daoly.getLiuShenOut(str, liuShen, acts, diZi, liuQin, shiYao, sz[4]);
	    //11). ʮ������
	    daoly.getTwenteenOut(str, up, down);
	    //12). ����ʮ������
	    daoly.getJFSixteenOut(str, whichGongZhu, upBian, downBian, acts);
	    //13). ����
	    daoly.getLiuYaoZhanOut(str, up, down, sz,
	                         upGong, downGong, shiYao, yingYao,
	                         yshen, diZi, liuQin, acts,
	                         diZiBian, diZiGong, liuQinGong,
	                         whichGongZhu, whichGongBian
	                         );
	    str.append("\r\n\n");
	    //Debug.out(str.toString());
	  }catch(Exception ex) {
	    ex.printStackTrace();
	    Messages.error("DelYiJingMain("+ex+")");
	  }
	}
	
	private final static int K1 = 16;
	private void getGua31() throws Exception{
	    //Ϊ��ʽ����
	    String s = null;   //���Դ�/���Դ�/���Դ�
	    /**
	     * 1. ����ι����ԣ��ȿ�2���ټ���X�����ԣ��ٿ�12��X�����ԣ�12�ո�X������
	     * 2. س��+��س���ж�س��ǰ��һ�ո�û�е��пո���
	     * 3. ���ԣ��ȿ�10���ټ�����
	     * 4. ���ԣ��ȿ�10���ټӱ���
	     * 5. �������� + �����֧ + �������� + �����س + ��
	     * 6. ���������Դǣ�س��
	     */
	    p12(pre1+pre1);
	    s = YiJing.JINGGUANAME[whichGongZhu]+YiJing.GONGZHUGUASELF+YiJing.GUA64NAME[up][down]+"��";	    
	    p121(s);
	    p12(speat(16, Liuyao.FILL2));
	    s = YiJing.JINGGUANAME[whichGongBian]+YiJing.GONGBIANGUASELF+YiJing.GUA64NAME[upBian][downBian]+"��";
	    p121(s);
	    p12(speat(10, Liuyao.FILL2));
	    s = YiJing.JINGGUANAME[whichGongHu]+YiJing.GONGHUGUASELF+YiJing.GUA64NAME[upHu][downHu]+"��";
	    p121(s);

	    pw.newLine();
	    pw.newLine();
	    pw.newLine();
	    for(int i=6; i>0; i--) {
	    	
	      //����/��������/��������/��֧/����/��س/��س��Ӧس/����
	      if(ff[i] == i) {
	      	p11(YiJing.FEIFUNAME[1]);
	      }else{
	        //��Ҫ��ȫ���У��ʼӼ����ո�
	      	p11(speat(YiJing.FEIFUNAME[1].getBytes().length, Liuyao.FILL2));
	      }
	      if(liuQin[i]==yshen)
	      	p31(YiJing.LIUQINNAME[liuQin[i]]);
	      else
	      	p11(YiJing.LIUQINNAME[liuQin[i]]);
	      if(isDongYao(acts ,i)) 
	      	p22(Liuyao.FILL1+YiJing.YAONAME[guaXiang[i]]+Liuyao.FILL1);
	      else
	      	p12(Liuyao.FILL1+YiJing.YAONAME[guaXiang[i]]+Liuyao.FILL1);
	      p11(YiJing.DIZINAME[diZi[i]]);
	      p11(YiJing.WUXINGNAME[YiJing.DIZIWH[diZi[i]]]);
	      
	      if(isDongYao(acts ,i)) {
	      	p21(YiJing.YAODONG[guaXiang[i]]);
	      }else{
	      	p11(speat(YiJing.YAODONG[1].getBytes().length,Liuyao.FILL2));
	      }

	      if(shiYao == i) {
	      	p21(Liuyao.FILL1+YiJing.SHIYINGNAME[YiJing.SHI]);
	      }else if(yingYao == i) {
	      	p21(Liuyao.FILL1+YiJing.SHIYINGNAME[YiJing.YING]);
	      }else{
	      	p11(Liuyao.FILL1+
	      			speat(YiJing.SHIYINGNAME[YiJing.YING].getBytes().length,Liuyao.FILL2));
	      }

	      //����
	      if(ff[i] == i && liuQinGong[i]==yshen) {
	      	p11(Liuyao.FILL1);
	      	p31(YiJing.FEIFUNAME[2]);
	      	p31(YiJing.LIUQINNAME[liuQinGong[i]].substring(0,1));
	      	p31(YiJing.DIZINAME[diZiGong[i]]);
	      	p11(Liuyao.FILL1);
	      }else if(ff[i] == i) {
	      	p11(Liuyao.FILL1);
	      	p11(YiJing.FEIFUNAME[2]);
	      	p11(YiJing.LIUQINNAME[liuQinGong[i]].substring(0,1));
	      	p11(YiJing.DIZINAME[diZiGong[i]]);
	      	p11(Liuyao.FILL1);
	      }else{
	      	p11(speat(6,Liuyao.FILL1));
	      }

	      //��������/��֧/����/��Ӧس
	      p11(pre1);
	      p11(YiJing.LIUQINNAME[liuQinBian[i]]);
	      p12(Liuyao.FILL1+YiJing.YAONAME[guaXiangBian[i]]+Liuyao.FILL1);
	      p11(YiJing.DIZINAME[diZiBian[i]]);
	      p11(YiJing.WUXINGNAME[YiJing.DIZIWH[diZiBian[i]]]);
	      //bianLen += YiJing.YAONAME2[guaXiangBian[i]];
	      if(shiYaoBian == i) {
	      	p21(Liuyao.FILL1+YiJing.SHIYINGNAME[YiJing.SHI]);
	      }else if(yingYaoBian == i) {
	      	p21(Liuyao.FILL1+YiJing.SHIYINGNAME[YiJing.YING]);
	      }else{
	      	p11(Liuyao.FILL1+
	      			speat(YiJing.SHIYINGNAME[YiJing.YING].getBytes().length,Liuyao.FILL2));
	      }
	      
	      //��������/��֧/����/��Ӧس
	      p11(pre1+pre1);
	      p11(YiJing.LIUQINNAME[liuQinHu[i]]);
	      p12(Liuyao.FILL1+YiJing.YAONAME[guaXiangHu[i]]+Liuyao.FILL1);
	      p11(YiJing.DIZINAME[diZiHu[i]]);
	      p11(YiJing.WUXINGNAME[YiJing.DIZIWH[diZiHu[i]]]);
	      //huLen += YiJing.YAONAME2[guaXiangHu[i]];
	      if(shiYaoHu == i) {
	      	p21(Liuyao.FILL1+YiJing.SHIYINGNAME[YiJing.SHI]);
	      }else if(yingYaoHu == i) {
	      	p21(Liuyao.FILL1+YiJing.SHIYINGNAME[YiJing.YING]);
	      }else{
	      	p11(Liuyao.FILL1+
	      			speat(YiJing.SHIYINGNAME[YiJing.YING].getBytes().length,Liuyao.FILL2));
	      }
	      
	      //����
	      p41(speat(2,Liuyao.FILL1));
	      p41(YiJing.LIUSHENNAME[liuShen[i]]);
	      pw.newLine();
	      pw.newLine();
	    }
	}
}
