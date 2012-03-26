package org.boc.db.qm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.BadLocationException;

import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Messages;

public class QimenPublic {
	private DaoQiMen daoqm;
	private DaoSiZhuMain daosz;  //������dao
	public final String NOKG = "<no>";  //�����ַ���ǰ�棬��ʾ����Ҫ�ո�
	public final String NEWLINE = "<newline>";  //��ʾ����
	public final int RIKONGWANG = 3; //�տ���
	public final int SHIKONGWANG = 4; //ʱ����
	public final int YSHENYEAR=1,YSHENMONTH=2,YSHENDAY=3,YSHENHOUR=4; 
	
  public QimenPublic(DaoQiMen daoqm, DaoSiZhuMain daosz) {
  	this.daoqm = daoqm;
  	this.daosz = daosz;
  }
  
  public DaoQiMen getDaoQiMen() {
  	return this.daoqm;
  }
  public DaoSiZhuMain getDaoSiZhuMain() {
  	return this.daosz;
  }
  
	//�Ƿ�ɸ�
	public boolean isFeigan() {
		int riganGong = getTianGong(SiZhu.rg, SiZhu.rz); //�õ��ո��乬��ֻҪ�жϵ��̸���geng�Ϳ�����
		int[] dpjy = this.getDpjy(riganGong);
		return (dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG);  	
	}
	//�Ƿ����
	public boolean isFugan() {
		int riganGong = getTianGong(SiZhu.rg, SiZhu.rz); //�õ��ո��乬��ֻҪ�ж����̸���geng�Ϳ�����
		int[] tpjy = this.getTpjy(riganGong);
		return (tpjy[0]==YiJing.GENG || tpjy[1]==YiJing.GENG);  	
	}
	
	/**
	 * �õ��۷�����
	 * ʱ����ֵʹ�����ְ����������켺�����ɹ˳���Ź������ְ����������켺�����ɹ�沼�Ź���
	 * �����֣���ʱֵʹ�ŵ��������ʱ����ͬ����Ϊ�˶࿴��Ϣ����ʱ�����й����ţ���˳���棻
	 */
	private final int[] ANGAN = {YiJing.DING,YiJing.BING,YiJing.YI,YiJing.WUG,YiJing.JI,YiJing.GENG,YiJing.XIN,YiJing.REN,YiJing.GUI}; 
	public int getYaoAngan(int gong) {		
		return getAngan(gong);
	}
	public int getAngan(int gong) {		
		return gYaoangan[gong];
	}
	private int _getYaoAngan(int gong) {
		int zsgong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);
		int zstgan = daoqm.gInt[2][3][zsgong];
		int zsdgan = daoqm.gInt[4][5][zsgong];
		int sg = this.getTiangan(SiZhu.sg, SiZhu.sz);
		boolean isyang = daoqm.getJu()>0;
		
		int location = 0;  //ʱ���乬��ANGONG��Ԫ�ص�λ��
		int how = zstgan==zsdgan ? 5 : zsgong; //���ʸ�ʱ����5����������ֵʹ��
		//���ʱ�����幬���幬���̸���ͬ��������zsgong��Ϊ�˿�������Ϣ��Ҫ��������һ�µģ���
		if(how==5 && daoqm.gInt[4][5][5]==sg) {
			how = zsgong;
		}
		for(int i=0; i<9; i++) {
			if(ANGAN[i]==sg) {
				location = i; 
				break;
			}
		}		
			
		if(isyang)
			return ANGAN[(gong-how+location+9)%9];
		else
			return ANGAN[(location+9-gong+how)%9];
	}
	
	/**
	 * ��ʦ���ɣ�ʱ�ɼ���ֵʹ���ϣ�Ȼ�������̵�˳����ߵ��̵���������˳������һȦ
	 * @param gong
	 * @return
	 */
	public int getWangAngan(int gong) {		
		return gWangangan[gong];
	}
	private int _getWangAngan(int g) {
		int zsgong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);
		int sg = this.getTiangan(SiZhu.sg, SiZhu.sz);
	//��18349276����
		int[] tpjys = {daoqm.gInt[2][3][1],daoqm.gInt[2][3][8],
				daoqm.gInt[2][3][3],daoqm.gInt[2][3][4],daoqm.gInt[2][3][9],
				daoqm.gInt[2][3][2],daoqm.gInt[2][3][7],daoqm.gInt[2][3][6]};  

  	int i=0,j = 0, x=0;;
  	for(; i<QiMen.jgxh.length; i++) {  //ת��
  		if(QiMen.jgxh[i]==zsgong) break;
  	}
  	for(; j<tpjys.length; j++) {
  		if(tpjys[j]==sg) break;
  	}

  	for(int k=0; k<QiMen.jgxh.length; k++) {
  		if(QiMen.jgxh[k]==g) {
  			//x = (k - i + j + 8)%7==0?7:(k - i + j + 8)%7;
  			x = (k - i + j + 8)%8;
  	    break;
  		}
  	}

  	return tpjys[x]; 
	}
	
	/**
	 * �ж�����乬�Ƿ�Ͼ֣������жϳɼ����ӣ��Ͼ�����Ϊ1234
	 * 1������ڹ�Ϊ���࣬�濴����״̬�����乬���������ࣻ
	 * 2���ü���
	 * 3�������࣬����֤���Ʋ��ȣ��ż��ײ��ۣ�
	 * 4����Ѯ��
	 * 5�������桢�ǵļ��ס���ļ��ײ���
	 * @param gong
	 * @return ��Ĺ�������=-1Ϊʧ��������˥��̥��=0Ϊ����������������Ӽ���=1Ϊ�Ͼ�
	 */
	public String[] isganHeju(int gong) {
		String[] desc = new String[2];
		int gan = this.getTpjy(gong)[0];
		int zi = 0;
		boolean isKong = isKong(gong, SHIKONGWANG);		//�жϸù��Ƿ���ʱ��Ѯ��		
		String[] ganws = gettgWS(gan, zi);  //�õ��ո���˥���
		String[] jige = getJixiongge(gong);  //�Ƿ�ü���
		String[] sanji = getSanji(gong); 	//�Ƿ������		
		String[] bmws = getmenWS(gong);    //�ŵ���˥
		String[] xingjx = getxingJX(gong);    //�ǵļ���
		String[] shenjx = getshenJX(gong);    //��ĵļ���

		//1. �ж��Ƿ�Ͼ�
		if(ganws[0].equals("-1") || isKong) desc[0]="-1";
		else if(ganws[0].equals("1") && jige[0].equals("1") && !isKong && !bmws[0].equals("-1")) desc[0]="1";
		else desc[0]="0";
		//2. ������Ϣ
		desc[1] = (isKong?"����������":"")+ganws[1]+jige[1]+sanji[1]+bmws[1]+xingjx[1]+shenjx[1];
		
		return desc;		
	}
	public String[] isganHeju(int gan, int zi) {		
		int gong = getTianGong(gan, zi);
		return isganHeju(gong);
	}
	
	/**
	 * �жϰ����乬�Ƿ�Ͼ֣��Ͼ�����Ϊ1234
	 * 1����Ϊ����
	 * 2���ü���
	 * 3�������࣬����֤���Ʋ��ȣ��ż��ײ��ۣ�
	 * 4����Ѯ��
	 * 5�������棬�ǵļ��ס��ɵ���˥�Ȳ���
	 * @param gong
	 * @return ����123=1Ϊ�Ͼ֣�����Ϊʧ��Ϊ-1
	 */
	public String[] isshenHeju(int gong) {
		if(gong==5) gong=this.getJigong();
		String[] desc = new String[2];
		//int gong = this.getShenGong(shen);
		
		boolean isKong = isKong(gong, SHIKONGWANG);		//�жϸù��Ƿ���ʱ��Ѯ��
		String[] jige = getJixiongge(gong);  //�Ƿ�ü���
		String[] sanji = getSanji(gong); 	//�Ƿ������		
		String[] bmws = getmenWS(gong);    //�ŵ���˥
		String[] xingjx = getxingJX(gong);    //�ǵļ���
		String[] shenjx = getshenJX(gong);    //��ĵļ���

		//1. �ж��Ƿ�Ͼ�
		if(!shenjx[0].equals("-1") && jige[0].equals("1") && !isKong && !bmws[0].equals("-1")) 
			desc[0]="1";
		else 
			desc[0]="-1";
		
		//2. ������Ϣ
		desc[1] = (isKong?"����������":"")+shenjx[1]+jige[1]+bmws[1]+sanji[1]+xingjx[1];
		
		return desc;		
	}
	
	/**
	 * �жϰ��źϾ����
	 * 	�Ͼ�����Ϊ123��124��125��
	 * 1�����ڹ�Ϊ���࣬�濴����״̬�����乬���������ࣻ
	 * 2���ü��񣻲�Ѯ�գ�
	 * 3�������棻
	 * 4���ü���
	 * 5���ü��ǣ�
	 *  ʧ������Ϊ�����ڹ��д���������
	 *  ʧ��������Ϊ�������������������������ǣ������ڹ����ݵء��м������棻
	 * @return 
	 *   [0]= -1Ϊ������0Ϊʧ����������1Ϊ�Ͼ�
	 *   [1]=������[X]��Y�������������������乬������أ��ü���A+B=C��[��]�����棬�ãۼ����ף���X1���ãۼ����ף���X2��
	 * @param rigan=�ոɣ�gong=����
	 */
	public String[] ismenHeju(int gong) {
		if(gong==5) gong=this.getJigong();
		String[] desc = new String[2];
		
		boolean isKong = isKong(gong);		//�жϸù��Ƿ���ʱ��Ѯ��
		String[] jxws = this.getmenWS(gong);  //�ŵ���˥
		String[] jige = this.getJixiongge(gong);  //�Ƿ�ü���
		String[] sanji = this.getSanji(gong); 	//�Ƿ������
		String[] shenjx = this.getshenJX(gong); //��ļ���
		String[] xingjx = this.getxingJX(gong);  //�Ƿ�ü���
		//1. �ж��Ƿ�Ͼ�
		if(jxws[0].equals("-1")) desc[0]="-1";
		else if(jxws[0].equals("1") && jige[0].equals("1") && !isKong && 
				(sanji[0].equals("1") || shenjx[0].equals("1") || xingjx.equals("1"))) desc[0]="1";
		else desc[0]="0";
		//2. ������Ϣ
		desc[1] = (isKong?"����������":"")+jxws[1]+jige[1]+sanji[1]+shenjx[1]+xingjx[1];
		
		return desc;		
	}
	
	/**
	 * �ж�ĳһ�����ǵĺϾ����
	 * 	�Ͼ���������1234��
	 * 1�����乬���࣬�濴������乬��
	 * 2���ü���
	 * 3����Ѯ�ա�
	 * 4�������࣬�������ƺ��ȣ�
	 * 5��������
	 *  ����ʧ������Ϊ�����ڹ��д����طϵأ��濴���
	 *  ����ʧ��������Ϊ������������������ǣ�
	 * @return 
	 *   [0]= -1Ϊʧ��������0Ϊʧ����������1Ϊ�Ͼ�
	 *   [1]=������[X]��Y�������������������乬������أ��ü���A+BΪC��[��]�����棬�ãۼ����ף���X1��
	 * @param gong=����
	 */
	public String[] isxingHeju(int gong) {
		if(gong==5) gong=this.getJigong();
		String[] desc = new String[2];
		
		boolean isKong = isKong(gong);		//�жϸù��Ƿ���ʱ��Ѯ��
		String[] jxws = this.getxingWS(gong);  //�ǵ���˥
		String[] jige = this.getJixiongge(gong);  //�Ƿ�ü���
		String[] sanji = this.getSanji(gong); 	//�Ƿ������
		String[] menjx = this.getmenJX(gong);   //�ŵļ���
		//1. �ж��Ƿ�Ͼ�
		if(jxws[0].equals("-1")) desc[0]="-1";
		else if(jxws[0].equals("1") && jige[0].equals("1") && !isKong && !menjx[0].equals("-1")) desc[0]="1";
		else desc[0]="0";
		//2. ������Ϣ
		desc[1] = jxws[1]+jige[1]+sanji[1]+menjx[1];
		
		return desc;
	}
	
	/**
	 * �ж�ĳһ���İ����ڸù��Ƿ����࣬�濴������乬��
	 * @param gong
	 * @return 
	 * 	 [0]= -1Ϊ����=����Ĺ��0Ϊ������=�ͼ��ݵأ�1Ϊ����=����
	 *   [1]=������[X]��Y�������������������乬������ء�
	 */
	public String[] getmenWS(int gong) {
		if(gong==5) gong=this.getJigong();
		int men = daoqm.gInt[3][1][gong];
		
		String[] desc = new String[2];
		StringBuilder sb = new StringBuilder();
		
		String wxsqf = QiMen.men_gong[men][gong]; 
		if(wxsqf.equals(QiMen.zphym6) || wxsqf.equals(QiMen.zphym4)) desc[0]="1";
		else if(wxsqf.equals(QiMen.zphym3)) desc[0]="0";
		else desc[0]="-1";
		
		sb.append("[").append(QiMen.bm1[men]).append("��]�乬");
		sb.append(wxsqf).append("������").append(QiMen.gong_yue[men][SiZhu.yz]);
		sb.append("����������").append(QiMen.gong_yue[gong][SiZhu.yz]).append("��");
		
		desc[1] = sb.toString();		
		return desc;		
	}
	/**
	 * �ж�ĳһ���ľ����ڸù��Ƿ����࣬�濴������乬��
	 * @param gong
	 * @return 
	 * 	 [0]= -1Ϊ����=���ϣ�0Ϊ������=�ݣ�1Ϊ����=����
	 *   [1]=������[X]��Y�������������������乬������ء�
	 */
	public String[] getxingWS(int gong) {
		if(gong==5) gong=this.getJigong();
		int xing = daoqm.gInt[2][1][gong];
		if(xing==0) xing=5;
		String[] desc = new String[2];
		StringBuilder sb = new StringBuilder();
		
		String wxsqf = QiMen.xing_gong[xing][gong]; 
//		if(wxsqf==null) {
//			System.err.println("��="+xing+";��="+gong+"����QiMen.xing_gong���ǣݣ۹����Ҳ�����Ӧ������������");
//			return null;
//		}
		if(wxsqf.equals(QiMen.wxxqs1) || wxsqf.equals(QiMen.wxxqs2)) desc[0]="1";
		else if(wxsqf.equals(QiMen.wxxqs3)) desc[0]="0";
		else desc[0]="-1";
		
		sb.append("[��").append(QiMen.jx1[xing]).append("]�乬");
		sb.append(wxsqf).append("������").append(QiMen.xing_yue[xing][SiZhu.yz]);
		sb.append("����������").append(QiMen.gong_yue[gong][SiZhu.yz]).append("��");
		
		desc[1] = sb.toString();		
		return desc;		
	}
	/**
	 * �ж�������乬�������״̬�Ƿ����࣬���乬Ϊ�����濴��������乬������״̬��
	 * @return 
	 *   [0]= ��Ĺ��=-1Ϊ������˥��̥��=0|-2Ϊ������������=1Ϊ����
	 *   [1]=������[X]��Y�������������������乬������ء�
	 * @param gan=��ɣ�zi=��֧�����gan=�ף��������gan+zi�����жϣ�
	 */
	public String[] gettgWS(int gong) {
		if(gong==5) gong=this.getJigong();
		int gan = this.getTpjy(gong)[0];
		return gettgWS(gan,0);
	}
	public String[] getdgWS(int gong) {
		int gan = this.getDpjy(gong)[0];
		return gettgWS(gan,0);
	}
	public String[] gettgWS(int gan, int zi) {
		int gong = this.getTianGong(gan, zi);
		if(gan==YiJing.JIA) gan = daoqm.getXunShu(gan, zi)+4;
		
		String[] desc = new String[2];
		StringBuilder sb = new StringBuilder();
		
		desc[0] = QiMen.gan_gong_wx3[gan][gong]+""; //
		sb.append("[").append(YiJing.TIANGANNAME[gan]).append("]��").append(gong).append("��");
		sb.append(QiMen.gan_gong_wx[gan][gong]).append("������").append(SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gan][SiZhu.yz]]);
		sb.append("����������").append(QiMen.gong_yue[gong][SiZhu.yz]).append("��");
		
		desc[1] = sb.toString();		
		return desc;
	}
	/**
	 * �жϸø���֧�����������ι�
	 * @param gan�����
	 * @param zi����֧
	 * @return
	 */
	public int getTianGong(int gan, int zi) {
		if(gan==0) return 0;
		if(tpgGong==null)
			return _getTianGong(gan,zi);  //��ʼʱ��Щ������ܻ�û������
		return tpgGong.get(this.getTiangan(gan, zi));
	}
	public int _getTianGong(int gan, int zi) {
    int jiakaitou = -1; 
    for(int i=1; i<=9; i++) {
    	if(daoqm.iszf() && i==5) continue;
    	if(gan==YiJing.JIA) jiakaitou = daoqm.getXunShu(gan, zi)+4;
    		
    	if(daoqm.gInt[2][3][i]==gan || jiakaitou==daoqm.gInt[2][3][i]) return i;
    	//if(daoqm.gInt[2][1][i]==2) {
    	if(daoqm.isTpJigong(i)) {
    		if(daoqm.gInt[4][5][5]==gan || jiakaitou==daoqm.gInt[4][5][5]) return i;
    	}
    	//System.out.println("daoqm.gInt[2][3]["+i+"]="+daoqm.gInt[2][3][i]);
    }
    Messages.error("QimenPublic:getTianGong() 322�У���������û���ҵ��ø�֧��"+YiJing.TIANGANNAME[gan]+YiJing.DIZINAME[zi]+"]���ڵĹ���");
    return 0;
	}
	/**
	 * �жϸø���֧����ڵ�����ι�
	 * @param gan�����
	 * @param zi����֧
	 * @return
	 */
	public int getDiGong(int gan, int zi) {
		if(gan==0) return 0;
		if(dpgGong==null) return _getDiGong(gan,zi);
		return dpgGong.get(gan==YiJing.JIA ? daoqm.getXunShu(gan, zi)+4 : gan);
	}
	public int _getDiGong(int gan, int zi) {
    int jiakaitou = -1; 
    for(int i=1; i<=9; i++) {
    	//�����ת�̣�����ֱ�ӷ��ص�5����ת���мĹ�������û��
    	if(daoqm.iszf() && i==5) continue;
    	if(gan==YiJing.JIA) jiakaitou = daoqm.getXunShu(gan, zi)+4;
    		   
    	if(daoqm.gInt[4][5][i]==gan || jiakaitou==daoqm.gInt[4][5][i]) return i;
    	
    	if(daoqm.isJiGong(i)) {  
    		if(daoqm.gInt[4][5][5]==gan || jiakaitou==daoqm.gInt[4][5][5]) return i;
    	}
    	
    }
    Messages.error("QimenPublic:getDiGong() 343�У���������û���ҵ��ø�֧��"+YiJing.TIANGANNAME[gan]+YiJing.DIZINAME[zi]+"]���ڵĹ���");
    return 0;
	}
	//�õ�ĳ����+��֧��Ӧ����ɣ�������Ǽ׿�ͷ�ģ���ֱ�ӷ���
	public int getTiangan(int gan ,int zi) {
		return (gan==YiJing.JIA)?daoqm.getXunShu(gan, zi)+4:gan;
	}
	/**
	 * �����Ƿ�����棬������������Ǿ���
	 * �����ĹҲ�㣬��Ҫע��
	 * @return [0]=-1��1�У�[1]=
	 */
	public String[] getSanji(int gong) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //���̸�1��2�����̸�1��2
		String[] desc = {"-1","������["};//�������棻
		
		tpg1 = daoqm.gInt[2][3][gong];
		//if(daoqm.gInt[2][1][gong]==2) tpg2 = daoqm.gInt[4][5][5];
		if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
		dpg1 = daoqm.gInt[4][5][gong];
		//if(gong==2) 
		if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];
		
		int[] gans = {tpg1,tpg2,dpg1,dpg2};
		for(int gan : gans) {		
			if(gan==YiJing.YI || gan==YiJing.BING || gan==YiJing.DING) {
				desc[0]="1";
				desc[1] +=(QiMen.gan_gong_mu[tpg1][gong]!=null ? YiJing.TIANGANNAME[gan]+"�빬Ĺ" : YiJing.TIANGANNAME[gan]);
			}
		}
			
		if(desc[0].equals("-1")) desc[1] = "�������棻"; 
		else desc[1] += "]��"; 
		return desc; 
	}
	/**
	 * �õ��ù��и������ϵļ��׸�,ֻҪ��һ����������Ǽ�
	 * @return [0] 0��1����[1]������������ϵļ��׸�����
	 */
	public String[] getJixiongge(int gong) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //���̸�1��2�����̸�1��2
		String[] rs = new String[2]; 
		
		tpg1 = daoqm.gInt[2][3][gong];
		//if(daoqm.gInt[2][1][gong]==2) tpg2 = daoqm.gInt[4][5][5];
		if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
		dpg1 = daoqm.gInt[4][5][gong];
		//if(gong==2) dpg2 = daoqm.gInt[4][5][5];
		if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];
		
		int ge1 = QiMen.gan_gan[tpg1][dpg1];
		boolean isJige1 = QiMen.isJige(ge1) , isJige2=true, isJige3=true, isJige4=true;
		rs[0] = isJige1 ? "1" : "0";
		rs[1] = YiJing.TIANGANNAME[tpg1]+"+"+YiJing.TIANGANNAME[dpg1]+"Ϊ"+QiMen.gGejuDesc[ge1][0];		
		
		if(dpg2!=0) {
			ge1 = QiMen.gan_gan[tpg1][dpg2];
			isJige2 = QiMen.isJige(ge1);
			rs[0] = isJige1 && isJige2 ? "1" : "0";
			rs[1] += ","+YiJing.TIANGANNAME[tpg1]+"+"+YiJing.TIANGANNAME[dpg2]+"Ϊ"+QiMen.gGejuDesc[ge1][0];
		}
		
		if(tpg2!=0) {
			ge1 = QiMen.gan_gan[tpg2][dpg1];
			isJige3 = QiMen.isJige(ge1);
			rs[0] = isJige1 && isJige2 && isJige3 ? "1" : "0";
			rs[1] += ","+YiJing.TIANGANNAME[tpg2]+"+"+YiJing.TIANGANNAME[dpg1]+"Ϊ"+QiMen.gGejuDesc[ge1][0];
			if(dpg2!=0) {
				ge1 = QiMen.gan_gan[tpg2][dpg2];
				isJige4 = QiMen.isJige(ge1);
				rs[0] = isJige1 && isJige2 && isJige3 && isJige4 ? "1" : "0";
				rs[1] += ","+YiJing.TIANGANNAME[tpg2]+"+"+YiJing.TIANGANNAME[dpg2]+"Ϊ"+QiMen.gGejuDesc[ge1][0];
			}
		}
		if(rs[0].equals("1")) 
			rs[1] += "����";
		else 
			rs[1] += "�׸�";
		return rs; 
	}
	public Integer[] getJixiongge2(int gong,boolean iszf) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //���̸�1��2�����̸�1��2
		List<Integer> rs = new ArrayList<Integer>(4); 
		
		tpg1 = daoqm.gInt[2][3][gong];		
		dpg1 = daoqm.gInt[4][5][gong];
		if(iszf) {
			if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
			if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];
		}
		
		rs.add(QiMen.gan_gan[tpg1][dpg1]);
		if(dpg2!=0) 
			rs.add(QiMen.gan_gan[tpg1][dpg2]);
			
		if(tpg2!=0) {
			rs.add(QiMen.gan_gan[tpg2][dpg1]);
			if(dpg2!=0) rs.add(QiMen.gan_gan[tpg2][dpg2]);
		}

		return (Integer[])rs.toArray(new Integer[rs.size()]); 
	}
	
	/**
	 * �ж�ĳһ�����ŵļ��ף�
	 * @param gong
	 * @return 
	 * 	 [0]= -1Ϊ���ţ�0Ϊƽ�ţ�1����
	 *   [1]=������[X]�ż�|�ס�
	 */
	public String[] getmenJX(int gong) {
		int men = daoqm.gInt[3][1][gong];
		
		String[] desc = new String[2];
		desc[0]=QiMen.bmjx[men]+"";
		desc[1]="["+QiMen.bm1[men]+"��]"+(QiMen.bmjx[men]==-1?"�ף�":QiMen.bmjx[men]==0?"ƽ��":"����");

		return desc;		
	}
	/**
	 * �ж�ĳһ�����ǵļ��ף�
	 * @param gong
	 * @return 
	 * 	 [0]= -1Ϊ�ף�0Ϊ�У�1��
	 *   [1]=������[X]�ż�|�ס�
	 */
	public String[] getxingJX(int gong) {
		int xing = daoqm.gInt[2][1][gong];
		
		String[] desc = new String[2];
		desc[0]=QiMen.jxjx[xing]+"";
		desc[1]="[��"+QiMen.jx1[xing]+"]"+(QiMen.jxjx[xing]==-1?"�ף�":QiMen.jxjx[xing]==0?"ƽ��":"����");

		return desc;		
	}
	
	/**
	 * �ж�ĳһ������ļ��ף�
	 * @param gong
	 * @return 
	 * 	 [0]= -1Ϊ�ף�0Ϊ�У�1��
	 *   [1]=������[X]��|�ס�
	 */
	public String[] getshenJX(int gong) {
		int shen = daoqm.gInt[1][1][gong];
		
		String[] desc = new String[2];
		desc[0]=QiMen.bsjx[shen]+"";
		desc[1]="["+QiMen.bs2[shen]+"]"+(QiMen.bsjx[shen]==-1?"�ף�":QiMen.bsjx[shen]==0?"ƽ��":"���� ");

		return desc;		
	}
	
	//<no>��ʾ����Ҫ�ո�
  public String format(StringBuffer sb, String[] array) {  	
  	for(String s:array) {
  		if(s==null) break;
  		if(s.equals(this.NEWLINE)) {
  			sb.append("\r\n");
  			continue;
  		}
  		if(s.startsWith(NOKG))
  			sb.append(s.replaceAll(NOKG,"")+"\r\n");
  		else
  			sb.append("    "+s+"\r\n");
  	}
  	return sb.toString(); 
  }
  /**
   * �жϸù��Ƿ����ջ�ʱѮ��
   * @param gong
   * @param day 3Ϊ������4Ϊʱ��
   * @return
   */
  public boolean isKong(int gong) {
  	return isKong(gong, this.SHIKONGWANG);
  }
  public boolean isKong(int gong,int day) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
    
    int[] xk; 
    if(day==SHIKONGWANG)
    	xk = daoqm.getPublic().getXunKong(SiZhu.sg,SiZhu.sz);  //�õ�ʱ��Ѯ�յ�֧
    else if(day==RIKONGWANG)
    	xk = daoqm.getPublic().getXunKong(SiZhu.rg,SiZhu.rz);  //�õ�����Ѯ�յ�֧
    else
    	return false;
    
    return dz1==xk[0] || dz2==xk[0] || dz1==xk[1] || dz2==xk[1];    
  }
  /**
   * �жϸù��Ƿ�����Գ�֮��
   * @param day Ѯ�����ݣ�3Ϊ������4Ϊʱ��
   */
  public boolean isChKong(int gong) {
  	return isChKong(gong, SHIKONGWANG);
  }
  public boolean isChKong(int gong, int day) {
  	//�ȵõ��ù��ĶԳ�֮��: 1-9, 2-8,3-7,4-6
  	int[] ch = {0, 9, 8, 7, 6, 0, 4, 3, 2, 1};  //����ĳ����Ӧ�ĶԳ�֮��
  	int chGong = ch[gong];
  	
  	return isKong(chGong, day);
  }
  /**
   * �жϸù��Ƿ����ǶԳ�֮��
   * @param day Ѯ�����ݣ�3Ϊ������4Ϊʱ����
   */
  public boolean isChMa(int gong) {
  	return isChMa(gong, SHIKONGWANG);
  }
  public boolean isChMa(int gong, int day) {
  	//�ȵõ��ù��ĶԳ�֮��: 1-9, 2-8,3-7,4-6
  	int[] ch = {0, 9, 8, 7, 6, 0, 4, 3, 2, 1};  //����ĳ����Ӧ�ĶԳ�֮��
  	int chGong = ch[gong];
  	
  	return day==SHIKONGWANG ? isYima(chGong) : isYimaOfRi(chGong);
  }
  /**
   * �жϸ��������������֮���Ƿ�����Ĺ��
   * @param gan+zi����Ϊ����Լ׿�ͷ��Ҫת��������
   * @return
   */
  public String isTGanSMJ(int gan, int zi) {
  	int gong = this.getTianGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	String rs = QiMen.gan_gong_si[gan][gong]+
  				QiMen.gan_gong_mu[gan][gong]+
  				QiMen.gan_gong_jue[gan][gong];
  	
  	rs = rs.replaceAll("null", "").trim();
  	return rs.equals("") ? null : rs;
  }
  public boolean isTGanSMJ(int gong) {
  	int[] tpjy = this.getTpjy(gong);

    return isTGanSMJ(tpjy[0],0)!=null || isTGanSMJ(tpjy[1],0)!=null;
  }
  //�Ƿ����̸��빬Ĺ
  public boolean isTGanMu(int gan, int zi) {
  	int gong = this.getTianGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	return QiMen.gan_gong_mu[gan][gong]!=null;
  }
  public boolean isTGanMu(int gong) {
  	int[] tpjy = this.getTpjy(gong);    
  	return QiMen.gan_gong_mu[tpjy[0]][gong]!=null || QiMen.gan_gong_mu[tpjy[1]][gong]!=null;
  }
  //�Ƿ���̸���Ĺ
  public boolean isDGanMu(int gan, int zi) {
  	gan = this.getTiangan(gan, zi);
  	int gong = this.getDiGong(gan, zi);
  	return QiMen.gan_gong_mu[gan][gong]!=null ;
  }
  public boolean isDGanMu(int gong) {
  	int[] dpjy = this.getDpjy(gong);    
  	return QiMen.gan_gong_mu[dpjy[0]][gong]!=null || QiMen.gan_gong_mu[dpjy[1]][gong]!=null;
  }
  /**
   * �жϸõ������������֮���Ƿ�����Ĺ��
   * @param gan+zi����Ϊ����Լ׿�ͷ��Ҫת��������
   * @return
   */
  public String isDGanSMJ(int gan, int zi) {
  	int gong = this.getDiGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	String rs = QiMen.gan_gong_si[gan][gong]+
  				QiMen.gan_gong_mu[gan][gong]+
  				QiMen.gan_gong_jue[gan][gong];
  	
  	rs = rs.replaceAll("null", "").trim();
  	return rs.equals("") ? null : rs;
  }
  public boolean isDGanSMJ(int gong) {
  	int[] dpjy = this.getDpjy(gong);
  	return isTGanSMJ(dpjy[0],0)!=null || isTGanSMJ(dpjy[1],0)!=null;
  }
  /**
   * �����Ƿ����
   */
  public boolean isMenFu() {
  	return QiMen.da_men_gong[daoqm.gInt[3][1][1]][1]==3; 
  }
  /**
   * �����Ƿ���
   */
  public boolean isMenFan() {
  	return QiMen.da_men_gong[daoqm.gInt[3][1][1]][1]==4; 
  }
  /**
   * �����Ƿ����
   */
  public boolean isXingFu() {
  	return QiMen.da_xing_gong[daoqm.gInt[2][1][1]][1]==5;
  }
  /**
   * �����Ƿ���
   */
  public boolean isXingFan() {
  	return QiMen.da_xing_gong[daoqm.gInt[2][1][1]][1]==6; 
  }
  //�õ�ĳ������������
  public int[] getTpjy(int gong) {
  	int[] rs = {daoqm.gInt[2][3][gong],0};
  	
  	//if(daoqm.gInt[2][1][gong]==2)
  	if(daoqm.isTpJigong(gong))
  		rs[1]=daoqm.gInt[4][5][5]; 
  	
  	return rs; 
  }
  //�õ�ĳ���ĵ�������
  public int[] getDpjy(int gong) {
  	int[] rs = {daoqm.gInt[4][5][gong],0};
  	
  	//if(gong==2)
  	if(daoqm.isJiGong(gong)) 
  		rs[1]=daoqm.gInt[4][5][5]; 
  	
  	return rs; 
  }
  /**
   * �õ�ָ�����ŵ��乬
   * bm2 = {0,   1��,  8��, 3��, 4��, 9��, 2��, 7��, 6��}
   */
  public int getMenGong(int men) {
  	if(menGong==null) return _getMenGong(men);
  	return menGong.get(men);
  }
  public int _getMenGong(int men) {
  	for(int i=0; i<10; i++) {
  		if(daoqm.gInt[3][1][i]==men) return i; 
  	}
  	return 0;
  }
  
  /**
   * �õ�ָ������������乬
   * ��1����2����3����4����5����6����7����8
   */
  public int getShenGong(int shen) {
  	return shenGong.get(shen);
  }
  public int _getShenGong(int shen) {
  	for(int i=0; i<10; i++) {
  		if(daoqm.gInt[1][1][i]==shen) return i; 
  	}
  	return 0;
  }
  
  /**
   * �õ�ָ�����ǵ��乬
   * jx2 = 1�� 2�� 3�� 4�� 5�� 6�� 7�� 8�� 9Ӣ
   */
  public int getXingGong(int xing) {
  	return xingGong.get(xing);
  }
  public int _getXingGong(int xing) {
  	for(int i=0; i<10; i++) {
  		if(daoqm.gInt[2][1][i]==xing) return i; 
  	}
  	return 0;
  }
  /**
   * �жϸù������̻�������
   * ����1��8��3��4Ϊ����
   */
  public boolean isNenpan(int gong) {
  	int ju = daoqm.getJu();
  	return (ju>0 && (gong==1 || gong==8 || gong==3 || gong==4)) ||
  	(ju<0 && (gong==9 || gong==2 || gong==7 || gong==6));
  }
  /**
   * �ж�g1���Ƿ���g2��
   */
  public boolean isChongke(int g1, int g2) {
  	int[][] chke = new int[10][3];
  	chke[1]=new int[]{2,8,9};
  	chke[2] = new int[]{8,3,4};
  	chke[3] = new int[]{7,6};
  	chke[4] = new int[]{6,7};
  	chke[6] = new int[]{4,9};
  	chke[7] = new int[]{3,9};
  	chke[8] = new int[]{2,3,4};
  	chke[9] = new int[]{1};
  	
  	int[] ckgong = chke[g2];
  	for(int i=0; i<ckgong.length; i++)
  		if(ckgong[i]==g1) return true;
  	
  	return false;
  }
  /** ������ķ����ĸ��� **/
  public boolean isGongChongke(int g1, int g2) {
  	return this.isChongke(g1, g2);
  }
  /**
   * g1��g2�Ƿ����
   * @param g1
   * @param g2
   * @return
   */
  public boolean isGongke2(int g1, int g2) {
  	return isGongke(g1,g2) || isGongke(g2,g1);
  }
  //g1�Ƿ��g2
  public boolean isGongke(int g1, int g2) {
  	int[][] chke = new int[10][3];
  	chke[1]=new int[]{2,8};
  	chke[2] = new int[]{3,4};
  	chke[3] = new int[]{7,6};
  	chke[4] = new int[]{6,7};
  	chke[6] = new int[]{9};
  	chke[7] = new int[]{9};
  	chke[8] = new int[]{3,4};
  	chke[9] = new int[]{1};
  	
  	int[] ckgong = chke[g2];
  	for(int i=0; i<ckgong.length; i++)
  		if(ckgong[i]==g1) return true;
  	
  	return false;
  }
  /**
   * �ж�g1���Ƿ��g2��
   */
  public boolean isGongChong(int g1, int g2) {
  	return new int[]{0,9,8,7,6,0,4,3,2,1}[g1]==g2;
  }
  /**
   * �õ�g1���ĶԳ幬
   */
  public int getChongGong(int g1) {
  	
  	return new int[]{0,9,8,7,6,0,4,3,2,1}[g1];
  }
  
  /**
   * �ж�g1���Ƿ���g2��
   */
  public boolean isGongSheng(int g1, int g2) {
  	return this.isSheng(g1, g2);
  }
  public boolean isSheng2(int g1, int g2) {
  	return isSheng(g1,g2) || isSheng(g2,g1);
  }
  public boolean isSheng(int g1, int g2) {
  	int[][] chke = new int[10][3];
  	chke[1]=new int[]{6,7};
  	chke[2] = new int[]{9};
  	chke[3] = new int[]{1};
  	chke[4] = new int[]{1};
  	chke[6] = new int[]{2,8};
  	chke[7] = new int[]{2,8};
  	chke[8] = new int[]{9};
  	chke[9] = new int[]{3,4};
  	
  	int[] ckgong = chke[g2];
  	for(int i=0; i<ckgong.length; i++)
  		if(ckgong[i]==g1) return true;
  	
  	return false;
  }
  //�ж�g1��g2���Ƿ�Ⱥ�
  public boolean isBihe(int g1, int g2) {
  	int[] chke = new int[10];
  	chke[2] = 8;
  	chke[3] = 4;
  	chke[4] = 3;
  	chke[6] = 7;
  	chke[7] = 6;
  	chke[8] = 2;
  	
  	return chke[g2]==g1;
  }
  
  /**
   * �õ���������ʱ���乬�����û�У��򷵻�0
   * @param type: ���ͣ����1���¸�2���ո�3��ʱ��4��
   */
  public int getSige(int type){
  	int gan2 = 0, gan = 0; 
  	
  	gan = type==1?SiZhu.ng:type==2?SiZhu.yg:type==3?SiZhu.rg:type==4?SiZhu.sg:0;
  	//��������ת���ɶ�Ӧ�����
  	if(type==1 && SiZhu.ng==1) gan2=daoqm.getXunShu(SiZhu.ng, SiZhu.nz)+4;
  	else if(type==2 && SiZhu.yg==1) gan2=daoqm.getXunShu(SiZhu.yg, SiZhu.yz)+4;
  	else if(type==3 && SiZhu.rg==1) gan2=daoqm.getXunShu(SiZhu.rg, SiZhu.rz)+4;
  	else if(type==4 && SiZhu.sg==1) gan2=daoqm.getXunShu(SiZhu.sg, SiZhu.sz)+4;
  	
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		//������̸�Ϊ�������̸�Ϊ��ɻ�ת�������ɻ�����ǵڶ���������幬�ĵ��̸��Ƿ���newNg��SiZhu.Ng��
  		if(daoqm.gInt[2][3][i]==YiJing.GENG && (daoqm.gInt[4][5][i]==gan || daoqm.gInt[4][5][i]==gan2 || 
  				(daoqm.isJiGong(i) && (daoqm.gInt[4][5][5]==gan2 || daoqm.gInt[4][5][5]==gan))))
  			return i;
  		//��������ǵ������������2����֤���ж������̸ɣ���һ�����̸ɾ��ǵ�5���ĵ��̸ɼ�gInt[4][5][5]������Ǹ������ҵ��̸������
  		if(daoqm.isTpJigong(i) && daoqm.gInt[4][5][5]==YiJing.GENG && 
  				(daoqm.gInt[4][5][i]==gan  || daoqm.gInt[4][5][i]==gan2 ||
  				  (daoqm.isJiGong(i) && (daoqm.gInt[4][5][5]==gan2 || daoqm.gInt[4][5][5]==gan))))
  			return i;  		
  	}
  	return 0;
  }
  //���̸��Ƿ��乬�����ǻ���
  public boolean isTpJixing(int gan, int zi) {
  	int gong = this.getTianGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	return QiMen.gan_gong_t[gan][gong]!=0 || isTDXing(gong) ;
  }
  public boolean isTpJixing(int gong) {
  	int[] tpjy = this.getTpjy(gong);
  	return  isTpJixing(tpjy[0],0) || isTpJixing(tpjy[1],0) || 
  					isTDXing(gong);
  }
  //�����Ƿ��л���
  public boolean isJixing(int gong) {
  	int[] tpjy = this.getTpjy(gong);
  	int[] dpjy = this.getDpjy(gong);
  	return  isTpJixing(tpjy[0],0) || isTpJixing(tpjy[1],0) || 
  					isTDXing(gong) || 
  					isDpJixing(dpjy[0],0) || isDpJixing(dpjy[1],0);
  }
  //���̸��Ƿ��乬������
  public boolean isDpJixing(int gan, int zi) {
  	int gong = this.getDiGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	return QiMen.gan_gong_t[gan][gong]!=0 || isTDXing(gong);
  }
  public boolean isDpJixing(int gong) {
  	int[] dpjy = this.getDpjy(gong);
  	return  isTDXing(gong) || 
  					isDpJixing(dpjy[0],0) || isDpJixing(dpjy[1],0);
  }
  /**
   * ���������͵õ��������乬����֧��Ϣ
   * @param type Ҫ�жϴ��ֵ�����, 4Ϊ��������3Ϊ������2Ϊ������1Ϊʱ��,0��ȡgan��zi��ֵ
   * @param gan
   * @param zi
   * @return
   */
	public String[] getYShenInfo(int type, int gan ,int zi) {
		int gong = 0; 		//Ϊ����
		String yshen = type==YSHENHOUR?"ʱ��Ϊ��":type==YSHENDAY?"�ո�Ϊ��":type==YSHENMONTH?"�¸�Ϊ��":type==YSHENYEAR?"���Ϊ��":"ָ�����̸�["+YiJing.TIANGANNAME[gan]+"]Ϊ��";
		if(type==YSHENHOUR) {
			gong = getTianGong(SiZhu.sg, SiZhu.sz); //�õ�ʱ�ɹ�
			gan = SiZhu.sg; zi = SiZhu.sz;
		}
		else if(type==YSHENDAY) {
			gong = getTianGong(SiZhu.rg, SiZhu.rz); //�õ��ոɹ�
			gan = SiZhu.rg; zi = SiZhu.rz;
		}
		else if(type==YSHENMONTH) {
			gong = getTianGong(SiZhu.yg, SiZhu.yz); //�õ��¸ɹ�
			gan = SiZhu.yg; zi = SiZhu.yz;
		}
		else if(type==YSHENYEAR) {
			gong = getTianGong(SiZhu.ng, SiZhu.nz); //�õ���ɹ�
			gan = SiZhu.ng; zi = SiZhu.nz;
		}
		else if(gan!=0 && zi!=0)
			gong = getTianGong(gan,zi); //�õ�ָ���ĸ���֧֮�乬
	//���ָ���˰��ž��ǰ�����������Ϊ�������ոɹ�Ϊ����
		else {			
			int[] ys = {0,0,0,0,0,1,2,3,4,6,7,8,9};  //Ŀǰ��Ӧ��ϵ: 5��12Ϊ���ţ�13~21Ϊ�Ź�
			if(type>=5 && type<=12) {  //����Ϊ����
				yshen = QiMen.bm1[ys[type]]+"��Ϊ��";
				gong = this.getMenGong(ys[type]); 
				int[] typjy = this.getTpjy(gong);
				gan = typjy[0]; zi = 0;
			}else if(type>=13 && type<=21) {
				gong = type-12;  //�Ź�Ϊ����
				yshen = "��"+QiMen.BIGNUM[gong]+"��Ϊ��";
				int[] typjy = this.getTpjy(gong);
				gan = typjy[0]; zi = 0;
			}
		}
		
		return new String[]{yshen, gong+"", gan+"", zi+""};
	}
	/**
	 * ���������ĸ�ʽ���õ������ĸ���֧
	 * ������ʽһ��Ϊ��1977��1,1������ʽ���ڻ����д���2���ȶ�������
	 * @return
	 */
	public int[] getMZhu(String mingzhu) {
		if(mingzhu==null || "".equals(mingzhu.trim()))
			return new int[]{0,0};
		
		//ȥ���ո�ǰ��|
		String[] kg = {"\\|",";",",","\\","\\/","-","%","$","@","*"};
		mingzhu = mingzhu.trim();
		for(int i =0; i<kg.length; i++ ) {
			if(mingzhu.startsWith(kg[i])) mingzhu = mingzhu.substring(1);
			if(mingzhu.endsWith(kg[i])) mingzhu = mingzhu.substring(0, mingzhu.length()-1);
		}
		
		String[] yeararry = mingzhu.split("\\|");  //�õ������ݻ����֧		
		int year = 0;
		int ng=0, nz=0;
		int[] yearganzi = new int[2*yeararry.length];
		int j = 0;
		
		for(int i=0; i<yeararry.length; i++) {
			String[] douhao = yeararry[i].split(",");		
			if(douhao.length==1) {
				year = Integer.valueOf(yeararry[i]);
				int[] ngz = this.getYearGanzi(year); 
				yearganzi[j++] = ngz[0];
				yearganzi[j++] = ngz[1];
			}else {			
				yearganzi[j++] = Integer.valueOf(douhao[0]);
				yearganzi[j++] = Integer.valueOf(douhao[1]);
			}
		}
		return yearganzi;
	}
	
	//�жϸø�֧�乬�Ƿ����һ�
	public boolean isTaohua(int gan, int zi) {
		int gong = this.getTianGong(gan, zi);
		if(gan==YiJing.JIA) gan = daoqm.getXunShu(gan, zi)+4;
		
		return QiMen.gan_gong_hua[gan][gong]!=null;
	}
	public boolean isTaohua(int gong) {
		int[] tpjy = this.getTpjy(gong);
		return QiMen.gan_gong_hua[tpjy[0]][gong]!=null || QiMen.gan_gong_hua[tpjy[1]][gong]!=null;
	}
	//�Ǹ������������ֵ����һ�
	public boolean isTpTaohua(int gong) {
		return isTaohua(gong);
	}
	public boolean isDpTaohua(int gong) {
		int[] dpjy = this.getDpjy(gong);
		return QiMen.gan_gong_hua[dpjy[0]][gong]!=null || QiMen.gan_gong_hua[dpjy[1]][gong]!=null;
	}
	/**
	 * �ù����Ƿ���������
	 * @param gong
	 * @return
	 */
	public boolean isYima(int gong) {
    int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
    	
    return (SiZhu.YIMA[SiZhu.sz][dz1] || SiZhu.YIMA[SiZhu.sz][dz2]); 
	}
	public boolean isYimaOfRi(int gong) {
    int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    	
    	
    return (SiZhu.YIMA[SiZhu.rz][dz1] || SiZhu.YIMA[SiZhu.rz][dz2]); 
	}
	
	//�õ��Ź����ص�֧����
	public int[] getDiziOfGong(int gong) {
		int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��
    return new int[]{dz1,dz2};  //���ϴ�С�����˳��
	}
//�õ��Ź����ص�֧��Ӧ���·�
	public String getDzyueOfGong(int gong) {
//		int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
//    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��
//    String[] yuefen = {"","��","��","��","��","��","��","��",
//    		"��","��","��","��","ʮ"};
//    String dzname = yuefen[dz1];
//    if(dz2>0 && (dz1==1 || dz1==2))
//      dzname = dzname + (?"��"+yuefen[dz2]:"");
		String[] yue = {"","����","ũ���������·�","ũ�����·�","ũ���������·�","ũ���������·�",
				"ũ���š�ʮ�·�","ũ�����·�","���¡�����","ũ�����·�"};
    return yue[gong];
	}
	//�õ��Ź����ص�֧����
	public String getDznameOfGong(int gong) {
		int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��
    String dzname = YiJing.DIZINAME[dz1];
    dzname = dzname + (dz2>0?"��"+YiJing.DIZINAME[dz2]:"");
    return dzname;
	}
	//�ж϶������еĵ�֧�Ƿ����
	public boolean isGongLiuhe(int g1, int g2) {
		int[] dz1 = getDiziOfGong(g1);
		int[] dz2 = getDiziOfGong(g2);
		for(int zi1 : dz1) {
			for(int zi2 : dz2) {
				if(YiJing.DZLIUHE[zi1][zi2]!=0)
					return true;
			}
		}
		return false;
	}
	//�ɵ�֧�õ�����Ӧ�Ĺ�
	public int getDiziGong(int dz) {
		return QiMen.ziOfGua[dz];
	}
	
	//�õ��ø�����֮��
	public int getGanHe(int gan) {
		return new int[]{0,6,7,8,9,10,1,2,3,4,5}[gan];
	}
	/**
	 * �õ�ĳ���������������1�����������2������֮��3,4
	 * @return
	 */
	public String getGongshu(int gong) {
		gong = getJiGong528(gong);  //��������幬����õ��Ĺ�
		
		int[] num = QiMen.JIUGONGSHU[gong];
		return gong+"��������"+num[1]+"��������"+num[2]+"������֮��Ϊ"+num[3]+","+num[4]+"��";
	}
	/**
	 * �õ���������4��ֵ�е����ֵ���ڶ���ֵ����Сֵ
	 * @param gong
	 * @param type 1=���ֵ��2=�ڶ���ֵ��3=��3��ֵ��...; -1=��Сֵ��-2=�ڶ�С��ֵ��-x=��xС��ֵ
	 * @return
	 */
	public int getGongshu(int gong, int type) {
		gong = getJiGong528(gong);  //��������幬����õ��Ĺ�
		
		int[] num = QiMen.JIUGONGSHU[gong];
		//int[] num2 = num.clone();
		int[] num3 = new int[num.length];
		System.arraycopy(num, 0, num3, 0, num.length);
		Arrays.sort(num3);   
		
	//ע�⣬��Ϊ��Сֵ����һ��Ϊ0�ģ���ȥ���һ��
		return num3[type>0?num3.length-type : Math.abs(type)];  
	}
	/**
	 * �ù����̸��빬�Ƿ����
	 * @param gong
	 * @return
	 */
	public boolean isTChong(int gong) {
		int[] tpjy = this.getTpjy(gong);
		
		return QiMen.gan_gong_ch[tpjy[0]][gong]!=0 || QiMen.gan_gong_ch[tpjy[1]][gong]!=0;
	}
	public boolean isTGChong(int gong) {
		return isTChong(gong);
	}
	//���̸��빬�Ƿ����
	public boolean isDGChong(int gong) {
		int[] dpjy = this.getDpjy(gong);
		
		return QiMen.gan_gong_ch[dpjy[0]][gong]!=0 || QiMen.gan_gong_ch[dpjy[1]][gong]!=0;
	}
	/**
	 * �ù��Ƿ���������̳�
	 * @param gong
	 * @return
	 */
	public boolean isTDChong(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(QiMen.gan_gan_ch[t][d]!=null) return true;
		
		return false;
	}
	/**
	 * �ù�������̸��Ƿ����
	 * @return
	 */
	public boolean isTDGanHe(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.TGHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * ����̵�֧�빬�е�֧�Ƿ�����
	 * @param gong
	 * @return
	 */
	public boolean isTDG3He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tpdz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] dpjy = this.getDpjy(gong);
		int[] dpdz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : tpdz)
			for(int d : dpdz)
				for(int g: gongzi)
				if(YiJing.DZSHANHE[t][d][g]!=0) return true;
		
		return false; 
	}
	/**
	 * ����̵�֧�Ƿ��ϣ��򲻴������ϣ�ֻ�а��������
	 * @param gong
	 * @return
	 */
	public boolean isTDZi3He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tpdz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] dpjy = this.getDpjy(gong);
		int[] dpdz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		
		for(int t : tpdz)
			for(int d : dpdz)
				if(YiJing.DZBANHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * ���̵�֧�빬֧�Ƿ���
	 * @param gong
	 * @return
	 */
	public boolean isTG3He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tddz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : tddz)
			for(int d : gongzi)
				if(YiJing.DZBANHE[t][d]!=0) return true;
		
		return false; 
	}
	//���̵�֧�빬�Ƿ����ϼ����
	public boolean isDG3He(int gong) {
		int[] dpjy = this.getDpjy(gong);
		int[] dddz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : dddz)
			for(int d : gongzi)
				if(YiJing.DZBANHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * ���̵�֧�빬֧�Ƿ�����
	 * @param gong
	 * @return
	 */
	public boolean isTG6He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tddz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : tddz)
			for(int d : gongzi)
				if(YiJing.DZLIUHE[t][d]!=0) return true;
		
		return false; 
	}
	//���̸��빬�Ƿ�����
	public boolean isDG6He(int gong) {
		int[] dpjy = this.getDpjy(gong);
		int[] dddz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : dddz)
			for(int d : gongzi)
				if(YiJing.DZLIUHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * ���̸��Ƿ�����̸�����
	 */
	public boolean isTDXing(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.DZXING[this.getdzOfgan(t)][this.getdzOfgan(d)]!=0) return true;
		
		return false; 
	}
	//�ж���ɵ�����
	public boolean isYangGan(int gan) {
//		int[] yang={YiJing.JIA, YiJing.BING,YiJing.WUG,YiJing.GENG,YiJing.REN};
//		for(int y :yang) 
//			if(gan==y) return true;
//		
//		return false;
		return gan%2==1;
	}
	/**
	 * �ù������̸��Ƿ�˵��̸�
	 * @return
	 */
	public boolean isTianKeDi(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANKE[YiJing.TIANGANWH[t]][YiJing.TIANGANWH[d]]==1) return true;
		
		return false; 
	}
	public boolean isDiKeTian(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANKE[YiJing.TIANGANWH[d]][YiJing.TIANGANWH[t]]==1) return true;
		
		return false; 
	}
	/**
	 * �ù������̸��Ƿ������̸�
	 * @return
	 */
	public boolean isTianShengDi(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANSHENG[YiJing.TIANGANWH[t]][YiJing.TIANGANWH[d]]==1) return true;
		
		return false; 
	}
	public boolean isDiShengTian(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANSHENG[YiJing.TIANGANWH[d]][YiJing.TIANGANWH[t]]==1) return true;
		
		return false; 
	}
	/**
	 * �õ�һ�����еĵõ�ʮ�ɿ�Ӧ
	 * @param gong
	 * @return
	 */
	public int[] getShiganKeying(int gong) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //���̸�1��2�����̸�1��2
		tpg1 = daoqm.gInt[2][3][gong];
		if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
		dpg1 = daoqm.gInt[4][5][gong];
		//if(gong==2) 
		if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];

		return new int[]{QiMen.gan_gan[tpg1][dpg1],QiMen.gan_gan[tpg1][dpg2],
				QiMen.gan_gan[tpg2][dpg1],QiMen.gan_gan[tpg2][dpg2]}; 
	}
	/**
	 * �жϸù����Ƿ��и�ʮ�ɿ�Ӧ����
	 * @param geju
	 * @return
	 */
	public boolean isGeju(int gong, int geju) {
		int[] ge = getShiganKeying(gong);
		for(int g : ge) 
			if(g==geju) return true;
		return false;
	}
	
	/** �����ǵõ���Ӧ�ĵ�֧ */
  public int getdzOfgan(int gan) {
  	return new int[]{0,0,0,0,0,YiJing.ZI,YiJing.XU,YiJing.SHEN,YiJing.WUZ,YiJing.CHEN,YiJing.YIN}[gan];
  }
  //�жϹ����Ƿ�������
  public boolean ismenpo(int gong) {
  	int men = daoqm.gInt[3][1][gong];  //������
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym2);
  }
  public boolean ismenzhi(int gong) {
  	int men = daoqm.gInt[3][1][gong];  //������
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym1);
  }
  public boolean ismenmu(int men) {  //�Ƿ����빬Ĺ
  	int gong = this.getMenGong(men);  //������
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym5);
  }
  public boolean ismensheng(int gong) {
  	int men = daoqm.gInt[3][1][gong];  //������
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym4);
  }
  public boolean isWuyangshi() {
  	return new boolean[]{false,true,true,true,true,true,false,false,false,false,false}[SiZhu.sg];
  }
  /**
   * �õ������������˥
   * @param gong
   * @return
   */
  public String[] getGongWS(int gong) {  	
  	String s = QiMen.gong_yue[gong][SiZhu.yz];
  	String[] rs = {"-1",s};
  	
  	if(s.equals(QiMen.wxxqs1) || s.equals(QiMen.wxxqs2))
  		rs[0]="1";
  	else if(s.equals(QiMen.wxxqs3) || s.equals(QiMen.wxxqs4))
  		rs[0]="0";
  	
  	return rs;
  }
  /**
   * �����幬ת���ɶ�Ӧ�ļĹ�
   * ������2�������ּİ˹������û��ָ����һ�ɼ�������
   * @param gong
   * @return
   */
  public int getJiGong528(int gong) {
  	if(gong!=5) return gong;
  	return daoqm.getJu()>0 ? (daoqm.getTheJigong()==8?8:2) : 2;
  }
  /**
   * ����ݵõ����֧
   */
  public int[] getYearGanzi(int year) {
  	int[] gz = daosz.getDaoCalendar().getSiZhu(year, 6, 6, 6, 6, false, false);
  	return new int[]{gz[1],gz[2]};
  }
  /**
   * �Ƿ�Ϊ������
   * @param gan
   * @return
   */
  public boolean is5YangGan(int gan) {
  	return gan==YiJing.JIA||gan==YiJing.YI||gan==YiJing.BING||gan==YiJing.DING||gan==YiJing.WUG;
  }
  /**
   * ����ʼ�굽��ֹ�꣬�õ��ɴ�������ɿ�ͷ����ͷ
   * @param syear,eyear,ng: ��ʼ����������������Ҫ�õ�����ɿ�ͷ�����
   * @param iyear�� ������ݣ�0��ʾǰ̨�����Ǹ�֧���Ǿ�������
   * @return Map<��ݣ����֧>
   */
  public Map<Integer, String> getYearMapFromGan(int iyear, int syear, int eyear, int ng) {//��-����  ����-���ӣ�   
  	int[] sngz = addGanzi(SiZhu.ng,SiZhu.nz,syear);  //: getYearGanzi(syear);
  	int sng = sngz[0];
  	int snz = sngz[1];
  	Map<Integer,String> map = new TreeMap<Integer,String>();
  	
  	int cha = ng>=sng?(ng-sng):(ng-sng+10);   //�õ�ng��sng����   	
  	String ngname = YiJing.TIANGANNAME[ng];
  	for(int i=0; i<(eyear-syear)/10+1; i++) {
  		int year = syear+cha+10*i;
  		int nz = (snz+cha+10*i)%12==0 ? 12 : (snz+cha+10*i)%12;  //�õ���ng��ͷ����֧
  		String nzname = YiJing.DIZINAME[nz];
  		map.put((iyear+year), ngname+nzname);
  	}
  	return map;
  }

  public Map<Integer, String> getYearMapFromZi(int iyear, int syear, int eyear, int nz) {
  	int[] sngz = addGanzi(SiZhu.ng,SiZhu.nz,syear); // : getYearGanzi(,SiZhu.ng,SiZhu.nz,syear);
  	int sng = sngz[0];
  	int snz = sngz[1];
  	Map<Integer,String> map = new TreeMap<Integer,String>();
  	
  	int cha = nz>=snz?(nz-snz):(nz-snz+12);   //�õ�nz��snz����   	
  	String nzname = YiJing.DIZINAME[nz];
  	for(int i=0; i<(eyear-syear)/12+1; i++) {
  		int year = syear+cha+12*i;
  		int ng = (sng+cha+12*i)%10==0 ? 10 : (sng+cha+12*i)%10;  //�õ���nz��ͷ�����
  		String ngname = YiJing.TIANGANNAME[ng];
  		map.put((iyear+year), ngname+nzname);
  	}
  	return map;
  }
  
  /**
   * �õ�ĳ����ʵ֮��
   */
  public Map<Integer, String> getYearTianMapFromGong(int iyear,int syear, int eyear, int gong) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��
    Map<Integer,String> m1 = this.getYearMapFromZi(iyear, syear, eyear, dz1);
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, dz2));
    return m1;
  }
  /**
   * �õ�ĳ����ʵ֮��
   */
  public Map<Integer, String> getYearChongMapFromGong(int iyear, int syear, int eyear, int gong) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    
    Map<Integer,String> m1 = this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz1));
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz2)));
    return m1;
  }
  /**
   * �õ�ĳ����ʵ���ʵ֮��
   */
  public Map<Integer, String> getYearTianChongMapFromGong(int iyear, int syear, int eyear, int gong) {
  	int dz = QiMen.jgdz[gong];  //�õ��Ź����صĵ�֧
    int dz1 = dz%100, dz2 = dz/100;  //ȡ�����е�ÿ��    
    
    Map<Integer,String> m1 = this.getYearMapFromZi(iyear, syear, eyear, dz1);
    m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz1)));    
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, dz2));
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz2)));
    
    return m1;
  }
  /**
   * ���õ���������ַ�����ʽ���أ���1995(����)��2000(����)
   * @param type : 1Ϊ�ɣ�2Ϊ֧��3Ϊ����ʵ֮�꣬4Ϊ����ʵ֮�꣬5Ϊ�������ʵ֮��
   * @param syear : ��ʼ����
   * @param eyear �� ��������
   * @param ngzgong �� �Դ˿�ͷ����ɻ���֧��
   * @param iyear �� �������
   * @return
   */
  public final static int YEARGAN = 1;
  public final static int YEARZI = 2;
  public final static int YEARGONGTIAN = 3;
  public final static int YEARGONGCHONG = 4;
  public final static int YEARGONGTIANCHONG = 5;
  public String getYearString(int type, int iyear,  int syear, int eyear, int ngzgong) {//��-����  ����-���ӣ�   
  	String s = "";  	
  	Map<Integer,String> m = null;
  	
  	switch(type) {
  	case YEARGAN:
  		m = getYearMapFromGan(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARZI:
  		m = getYearMapFromZi(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARGONGTIAN:
  		m = getYearTianMapFromGong(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARGONGCHONG:
  		m = getYearChongMapFromGong(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARGONGTIANCHONG:
  		m = getYearTianChongMapFromGong(iyear, syear,eyear,ngzgong);
  		break;
  	}
  	
		for(Iterator<Integer> it = m.keySet().iterator(); it.hasNext();) {
			Integer key = it.next();
			String value = m.get(key);
			s += key+"("+value+")��";
		}
		return s.substring(0,s.length()-1);
  }

  /**
   * �õ�����֮��
   * @param gan
   * @return
   */
  public int getHegan(int gan) {  	
  	if(gan<=5) return gan+5;
  	return gan-5;
  }
  /**
   * �õ�����ĵ�֧
   * @param zi
   * @return
   */
  public int getChongzi(int zi) {
  	int[] ch = {0,7,8,9,10,11,12,1,2,3,4,5,6};
  	return ch[zi];
  }
  
	/**
	 * �����֧������ݣ���1864=������������֮
	 * @param year
	 */
//	public int getGanziFromYear(int year) {
//		if(year != 0) return year;
//		
//		int g = YiJing.JIA;  //SiZhu.ng; 
//		int z = YiJing.ZI;   //SiZhu.nz;
//		for(int i=1; i<=60; i++) {
//			g = (g+i)%10==0 ? 10 : (g+i)%10;
//			z = (z+i)%12==0 ? 12 : (z+i)%12;
//			if(g==SiZhu.ng && z==SiZhu.nz) {
//				return 1864+i;
//			}
//		}
//		return -1;
//	}
  /**
   * ��ָ���ĸ�֧�ϼ���add���ĸ�֧
   */
	public int[] addGanzi(int g, int z, int add) {
		g = (g+add)%10==0 ? 10 : (g+add)%10;
		z = (z+add)%12==0 ? 12 : (z+add)%12;
		
		return new int[]{g,z};
	}
	
	//�õ��۷�֮���ǡ�����
	public int getTuiXing(int gong) {
		return gong;
	}
	/**
	 * ���ǣ���Զ��˳ʱ��˳�Ű˹�
	 */
	public int getJinXing(int gong) { 
  	return jxingMap.get(gong);
  }
	private void _getJinXing() { 
  	int x0 = getDiGong(SiZhu.rg, SiZhu.rz);  //�����ո����乬ԭ����
  	int zfg = daoqm.getZhifuGong();  //ֵ����
  	if(daoqm.iszf() && zfg==5) zfg = daoqm.getJiGong();
  	//��zfg��Ӧx0����˳����
  	int i=0,j = 0;
  	for(; i<QiMen.jx2.length; i++) {
  		if(QiMen.jx2[i]==zfg) break;
  	}
  	for(; j<QiMen.jx2.length; j++) {
  		if(QiMen.jx2[j]==x0) break;
  	}
  	for(int g=1; g<=9; g++) {
	  	for(int k=0; k<QiMen.jx2.length; k++) {
	  		if(QiMen.jx2[k]==g) {
	  			int x = (k - i + j + 8)%8==0?8:(k - i + j + 8)%8;
	  	    jxingMap.put(g, QiMen.jx2[x]);
	  	    break;
	  		}
	  	}
  	}
  	jxingMap.put(5, 5); //���ݷ���
  }
	
	/**
	 * �۷����ţ���Զ˳��
	 */
	public int getYinmen(int gong) {
		return ymenMap.get(gong);
	}
	private void _getYinmen() {
		int x0 = daoqm.gInt[3][1][this.getTianGong(SiZhu.rg, SiZhu.rz)]; //�����ոɵ���
  	int zsg = daoqm.getZhishiGong(); //ֵʹ��
  	if(daoqm.iszf() && zsg==5) zsg = daoqm.getJiGong();
  	int i=0,j = 0;
  	for(; i<QiMen.bm2.length; i++) {
  		if(QiMen.bm2[i]==zsg) break;
  	}
  	for(; j<QiMen.bm2.length; j++) {
  		if(QiMen.bm2[j]==x0) break;
  	}
  	for(int g=1; g<=9; g++) {
	  	for(int k=0; k<QiMen.bm2.length; k++) {
	  		if(QiMen.bm2[k]==g) {
	  			int x = (k - i + j + 8)%8==0?8:(k - i + j + 8)%8;
	  	    ymenMap.put(g, QiMen.bm2[x]);
	  	    break;
	  		}
	  	}
  	}
  	ymenMap.put(5, 5); //���ݷ���
	}
  /**
   * �õ�ĳ����ʮ����
   * @param gong
   * @return
   */
	public int[] getSJ12(int gong) {
		if(gong==5) return null;
		int[] sj = new int[2];
		int[] zi = this.getDiziOfGong(gong);
		sj[0] = (zi[0]-SiZhu.nz+1+12)%12==0?12 : (zi[0]-SiZhu.nz+1+12)%12;
		if(zi[1]!=0) {
			sj[1] = sj[0];  //������֧˳��
			sj[0] = (zi[1]-SiZhu.nz+1+12)%12==0?12 : (zi[1]-SiZhu.nz+1+12)%12;
		}
		return sj;
	}
//	/**
//	 * �õ�ĳ���Ļ�֧�������ɰ��ɵã���ʱ���Ǻ�
//	 */
//	public int getHuoziOfGong(int gong) {
//		int g1 = daoqm.gint[][][daoqm.getZhifuGong()];
//	}
	/**
	 * �õ�ĳ��ɵĻ�֧����ɼ����ɣ�ֻ�Ƕ��һ������˴�����ת�������ҵĻ���
	 * ���ֻ�ܺ��˵���X�����˾ʹ���
	 * int[] xunshu = {1, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2};
   * xunname = {"","����(��)","����(��)","����(��)","����(��)","�׳�(��)","����(��)"};
	 */
	public int getHuozi(int gan) {
		return huoganMap.get(gan);
	}
	/**
	 * �õ�ĳ��֧�Ļ��
	 * ���ֻ�ܺ��˵���X�����˾ʹ���
	 */
	public int getHuogan(int zi) { //8,2|1,11  x,1
		return huoziMap.get(zi);
	}
	public int _getHuozi(int gan) {
		int xz = QiMen.xunzi[(SiZhu.sz-SiZhu.sg+12)%12];  //�õ�ʱ���׿�ͷ��֧
		return (gan-1+xz+12)%12==0? 12 : (gan-1+xz+12)%12;
	}
	/**
	 * �ɸɻ�֧�õ�����
	 * @param gan�� ��ʡ�ԣ���֧�Ƴ�
	 * @param zi����ʡ�ԣ��ɸ��Ƴ�
	 * @return
	 */
	public int[] getNayin(int gan, int zi) {
		if(gan==0 && zi==0) return null;
		if(gan==0) gan=getHuogan(zi);
		if(zi==0) zi = getHuozi(gan);
		return new int[]{gan,zi};
	}
	private boolean isboy;  //��Ů����Ϊtrue
	private boolean iszf;		//ת���̣�תΪtrue
	private boolean istd; 	//Сֵ�����컹�ǵأ�����Ϊtrue
	private boolean iscy;   //�𲹻������򣬲�Ϊtrue
	private int jigong;	
	private int ysTgong;			//�����乬�������������乬
	private int ysDgong;			//��������乬
	private int ysGan;				//�������
	private int ysZi;					//�����֧
	private int mtpgong;			//���������乬
	private int mdpgong; 			//���������乬
	private int mingdz,mingtg;  //������ɡ���֧
	public void setBoy(boolean isboy) {
		this.isboy = isboy;
	}
	public boolean isBoy() {
		return isboy;
	}
	public void setZf(boolean iszf) {
		this.iszf = iszf;
	}
	public boolean isZf() {
		return iszf;
	}
	public void setTd(boolean istd) {
		this.istd = istd;
	}
	public boolean isTd() {
		return istd;
	}
	public void setCy(boolean iscy) {
		this.iscy = iscy;
	}
	public boolean isCy() {
		return iscy;
	}
	public void setJigong(int jigong) {
		this.jigong = jigong;
	}
	public int getJigong() {
		return jigong;
	}
	public void setYongshen(int yshen) {
		String[] ysinfo = getYShenInfo(yshen, 0, 0);		
		ysTgong = Integer.valueOf(ysinfo[1]);  //���������乬
  	ysGan = Integer.valueOf(ysinfo[2]);
  	ysZi = Integer.valueOf(ysinfo[3]);
  	ysDgong = getDiGong(ysGan, ysZi);
	}
	public int getYSTgong() {
		return ysTgong;
	}
	public int getYSDgong() {
		return ysDgong;
	}
	public int getYSTiangan() {
		return ysGan;
	}
	public int getYSDizi() {
		return ysZi;
	}
	public void setNianming(String mingzhu) {
		int[] mzhu = getMZhu(mingzhu);
  	mtpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)? getTianGong(mzhu[0], mzhu[1]):0;  //����
  	mdpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)? getDiGong(mzhu[0], mzhu[1]):0;  //����
  	mingtg = mzhu[0];
  	mingdz = mzhu[1];
	}
	public int getMingTgong() {
		return mtpgong;
	}
	public int getMingDgong() {
		return mdpgong;
	}
	public int getMingtg() {
		return mingtg;
	}
	public int getMingdz() {
		return mingdz;
	}
	
	
	public static Map<Integer,Integer> tpgGong; 	//���̸�����Ӧ�Ĺ�
	public static Map<Integer,Integer> dpgGong; 	//���̸�����Ӧ�Ĺ�
	public static Map<Integer,Integer> menGong; 	//������Ӧ�Ĺ�
	public static Map<Integer,Integer> xingGong; 	//������Ӧ�Ĺ�
	public static Map<Integer,Integer> shenGong; 	//������Ӧ�Ĺ�
	public static Map<Integer,Integer> jxingMap; 	//�۷�����
	public static Map<Integer,Integer> ymenMap;		//�۷�����
	public static Map<Integer,Integer> huoganMap;		//�ɶ�Ӧ�Ļ�֧
	public static Map<Integer,Integer> huoziMap;		//֧��Ӧ�Ļ��
	private static int[] gYaoangan;								 	//�۷�����
	private static int[] gWangangan;								 	//��ʦ����
	public void initGlobal() {
		tpgGong = new HashMap<Integer,Integer>(10);
		dpgGong = new HashMap<Integer,Integer>(10);
		menGong = new HashMap<Integer,Integer>(10);
		xingGong = new HashMap<Integer,Integer>(10);
		shenGong = new HashMap<Integer,Integer>(10);
		jxingMap = new HashMap<Integer,Integer>(10);
		ymenMap = new HashMap<Integer,Integer>(10);
		huoganMap = new HashMap<Integer,Integer>(10);
		huoziMap = new HashMap<Integer,Integer>(10);
		gYaoangan=new int[10];		
		gWangangan=new int[10];
		for(int i=2; i<=10; i++) {
			tpgGong.put(i, _getTianGong(i, 0));
			dpgGong.put(i, _getDiGong(i, 0));
		}
		for(int i=1; i<=9; i++) {
			menGong.put(i, _getMenGong(i));
			
			int xg = _getXingGong(i);
			if(i==5 && xg==0) 
				xingGong.put(5, 5); //���幬��ȱʡΪΪ����
			else
				xingGong.put(i, xg);
			
			shenGong.put(i, _getShenGong(i));
		}
		for(int i=1; i<10; i++) {
			gYaoangan[i] = _getYaoAngan(i);
		}
		for(int i=1; i<10; i++) {
			gWangangan[i] = _getWangAngan(i);
		}
		for(int i=1; i<=10; i++) {
			int hz = _getHuozi(i);
			huoganMap.put(i, hz);
			huoziMap.put(hz, i);
		}
		this._getJinXing();
		this._getYinmen();
	}
	
	/**
	 * �õ��ù����еĸ��
	 * @param gong
	 * @throws BadLocationException
	 */
	public List<Integer> getGejus(int gong) throws BadLocationException{
		List<Integer> list = new ArrayList<Integer>();
		int zfg = daoqm.getZhifuGong();
		int zsg = daoqm.getZhishiGong();
		int kaimen = getMenGong(QiMen.MENKAI);
		int xiumen = getMenGong(QiMen.MENXIU);
		int shmen = getMenGong(QiMen.MENSHENG);
		int jing3 = getMenGong(QiMen.MENJING3);
		int du = getMenGong(QiMen.MENDU);
		int jing1 = getMenGong(QiMen.MENJING1);
		int shang = getMenGong(QiMen.MENSHANG);
		int si = getMenGong(QiMen.MENSI);
		int shenyin = getShenGong(QiMen.SHENYIN);
		int shenhe = getShenGong(QiMen.SHENHE);
		int shendi = getShenGong(QiMen.SHENDI);
		int shentian = getShenGong(QiMen.SHENTIAN);
		int[] tgan = getTpjy(gong);
		int[] dgan = getDpjy(gong);
		int[] t = tgan;
		int[] d = dgan;
		boolean isyi = tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI ||dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI; 
		boolean isbing = tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING ||dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING;
		boolean isding = tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING ||dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING;
		boolean isren = tgan[0]==YiJing.REN ||tgan[1]==YiJing.REN ||dgan[0]==YiJing.REN ||dgan[1]==YiJing.REN;
		boolean isji = tgan[0]==YiJing.JI ||tgan[1]==YiJing.JI ||dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI;
		boolean isgui = tgan[0]==YiJing.GUI ||tgan[1]==YiJing.GUI ||dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI;

		//1. �Ƿ�������ʱ�񡢷ɸɷ���		
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.ng || dgan[1]==SiZhu.ng)) {
			list.add(9); //"�����";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.yg || dgan[1]==SiZhu.yg)) {
			list.add(10); //"���¸�";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.rg || dgan[1]==SiZhu.rg)) {
			list.add(24);  //"���ո񡢷���";
		}
		if((tgan[0]==SiZhu.rg || tgan[1]==SiZhu.rg) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			list.add(13); //"���ɸ�";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.sg || dgan[1]==SiZhu.sg)) {
			list.add(12); //"��ʱ��";
		}

		//3. 㣸� ��+�����+�����+������ʱ
		if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.BING || dgan[1]==YiJing.BING)) {
			list.add(16); //"��㣸�";
		}
		else if((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && 
				(dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || 
						dgan[1]==SiZhu.ng ||dgan[1]==SiZhu.yg ||dgan[1]==SiZhu.rg ||dgan[1]==SiZhu.sg)) {
			list.add(16); //"��㣸�";
		}

		//4. �������ţ������������ʱ�� �������ڱΣ��������ɣ�����ʱ�ɡ�
		if((tgan[0]==YiJing.GUI || tgan[1]==YiJing.GUI) && (dgan[0]==SiZhu.sg || dgan[1]==SiZhu.sg)) {
			list.add(17); //"������";
		}
		else if((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && 
				(dgan[0]==SiZhu.sg || dgan[1]==SiZhu.sg)) {
			list.add(18); //"���ر�";
		}
		
		//5. ֱ������Ϊ�����̼��ӣ����̼��磻���̼��������̼׳������̼��꣬���̼��������ֻ��������������޷���
		boolean iszhifan = ((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) ||
			((tgan[0]==YiJing.JI || tgan[1]==YiJing.JI) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN)) ||
			((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
			((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI)) ||
			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG));
		if(iszhifan) {
			list.add(20); //��ֵ������
		}
		
		//6. �����ʹ������������ٵ��̼�������磻���̱�����ٵ��̼��ӻ���ꣻ���̶�����ٵ��̼׳������
		boolean is3 = (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI || dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN);
		is3 = is3 || ((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG));
		is3 = is3 || ((tgan[0]==YiJing.DING || tgan[1]==YiJing.DING) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN || dgan[0]==YiJing.GUI || dgan[1]==YiJing.GUI));
		if(is3) {
			list.add(21); //"����ʹ"
		}
		
		//7. ��Ů���ţ�����ֱʹ���ٵ��̶���
		if(zsg==gong && (dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			list.add(22); //"������"
		}
		
		//8. ������ ����������ֵ��֮��Ϊ����
		if(gong==zfg && (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI || dgan[0]==YiJing.YI || dgan[1]==YiJing.YI ||
				tgan[0]==YiJing.BING || tgan[1]==YiJing.BING || dgan[0]==YiJing.BING || dgan[1]==YiJing.BING ||
				tgan[0]==YiJing.DING || tgan[1]==YiJing.DING || dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			list.add(23);  //"������"
		}
		
		//9. ��թ
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenyin==gong)) {
			list.add(420);//"����թ"
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenhe==gong)) {
			list.add(421);//"����թ"
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shendi==gong)) {
			list.add(422);//"����թ"
		}
		
		//10. ���
		if(jing3==gong && (isyi || isbing || isding) && shentian==gong) {
			list.add(423);//"�����"
		}
		if(du==gong && (isgui || isji || isding) && (shenyin==gong || shendi==gong || shenhe==gong)) {
			list.add(424);//"���ؼ�"
		}
		if(jing1==gong && isren && shentian==gong) {
			list.add(425);//"���˼�"
		}
		if(shang==gong && (isgui || isji || isding) && (shendi==gong || shenhe==gong)) {
			list.add(426);//"�����"
		}
		if(si==gong && (isgui || isji || isding) && shendi==gong ) {
			list.add(427);//"�����"
		}
		
		//11. �Ŷ�
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && xiumen==gong && shenyin==gong) {
			list.add(428);//"���˶�"
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && shentian==gong) {
			list.add(429);//"�����"
		}
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && du==gong && shendi==gong) {
			list.add(430);//"�����"
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && (dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)) {
			list.add(431);//"�����"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && kaimen==gong && (dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI)) {
			list.add(432);//"���ض�"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN)) {
			list.add(433);//"���ƶ�"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI || gong==1)) {
			list.add(434);//"������"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && gong==4) {
			list.add(436);//"�����"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && xiumen==gong && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN || gong==8)) {
			list.add(435);//"������"
		}		

		//12. ������������������ң�����������
		if((tgan[0]==YiJing.GENG ||tgan[1]==YiJing.GENG) && (
				dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI || 
				dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING ||
				dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)){
			list.add(25);//"�����"
		}
		
		//13 ʱ����Ĺ�����Ǹ���Ĺ
  	if((SiZhu.sg==YiJing.BING && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.BING) ||
  			(SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.WUG) ||
  			(SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.DING) ||
  			(SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.JI) ||
  			(SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.CHEN && daoqm.gInt[2][3][4]==YiJing.REN) ||
  			(SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.WEI && daoqm.gInt[2][3][2]==YiJing.GUI)) {
  		if(gettgWS(SiZhu.sg, SiZhu.sz)[0].equals("1"))
  			list.add(8);//"��ʱ�����"
  		else
  			list.add(7);//"��ʱ����Ĺ"
  	}
			
		//14. �ݼ׿������׼ӻ�����
		if(QiMen.gan_xing[tgan[0]][daoqm.gInt[2][1][gong]]!=0 || QiMen.gan_xing[tgan[1]][daoqm.gInt[2][1][gong]]!=0) {
			list.add(437);//"���ݿ�"
		}
		
		//2. ������Ĺ������������
		if(this.isTGanMu(gong) || this.isDGanMu(gong))
			list.add(13);
				
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
			list.add(26);//"���ơ�"
		
		//4. �����������������𹬣�Ϊ�ճ���ɣ�����浽�빬��Ϊ���ն��ţ����浽�ҹ���Ϊ�Ǽ�������
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3)
			list.add(27);//"�ճ���ɣ��"
		if((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==9)
			list.add(28);//"���ն��š�"
		if((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==7)
			list.add(29);//"�Ǽ�������"
		
		//5. ����֮�飺������ļ�������ϵ���ݻ���������ߣ�Ϊ�������顢���¾㼪��
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI ||
				t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) &&
				(daoqm.gInt[1][1][gong]==QiMen.SHENYIN || daoqm.gInt[1][1][gong]==QiMen.SHENDI || 
						daoqm.gInt[1][1][gong]==QiMen.SHENHE || daoqm.gInt[1][1][gong]==QiMen.SHENTIAN ) && 
						(daoqm.gInt[3][1][gong]==QiMen.MENKAI || daoqm.gInt[3][1][gong]==QiMen.MENXIU || daoqm.gInt[3][1][gong]==QiMen.MENSHENG)) 
			list.add(30);//"���顢"
		
		//6. ����»λ���ҵ��𡢱����㡢������Ϊ��»֮λ
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==4) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==9))
			list.add(31); //"��»��"
		
		//7. ������ϣ��Ҹ�������������Ϊ��ϣ����׼�Ϊ�Ǻϣ��ü��ţ������к�֮�����ͽ⡢�˽ᡢƽ�֡�ƽ�֡�
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG)) ||
				((t[0]==YiJing.GENG || t[1]==YiJing.GENG) && (d[0]==YiJing.YI || d[1]==YiJing.YI)) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING) && (d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
				((t[0]==YiJing.XIN || t[1]==YiJing.XIN) && (d[0]==YiJing.BING || d[1]==YiJing.BING)) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING) && (d[0]==YiJing.REN || d[1]==YiJing.REN)) ||
				((t[0]==YiJing.REN || t[1]==YiJing.REN) && (d[0]==YiJing.DING || d[1]==YiJing.DING)))
			list.add(32);//"��ϡ�"
		else if(((t[0]==YiJing.WUG || t[1]==YiJing.WUG) && (d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
				((t[0]==YiJing.GUI || t[1]==YiJing.GUI) && (d[0]==YiJing.WUG || d[1]==YiJing.WUG)) ||
				((t[0]==YiJing.JI || t[1]==YiJing.JI) && (gong==zfg)) ||
				((gong==zfg) && (d[0]==YiJing.JI || d[1]==YiJing.JI)))
			list.add(33);//"�Ǻϡ�"
		
		return list;
	}
	
  public static void main(String[] args) {
  	DaoQiMen dao = new DaoQiMen();
  	DaoSiZhuMain sizhu = new DaoSiZhuMain();
		QimenPublic p = new QimenPublic(dao,sizhu);
		//System.out.println(p.getYearString(1,1995, 2005, 3));
		//System.out.println(p.getYearString(2,1995, 2005, 6));
		//System.out.println(p.getYearString(5,1976+18, 1976+40, 8));
		SiZhu.sg=1; SiZhu.sz=11;
		for(int i=1; i<YiJing.TIANGANNAME.length; i++){
			int g = p.getHuozi(i);
			System.out.print(YiJing.TIANGANNAME[i]+YiJing.DIZINAME[g]+"\t");			
		}
		System.out.println();
		for(int i=1; i<YiJing.DIZINAME.length; i++){
			int g = p.getHuogan(i);
			System.out.print(YiJing.TIANGANNAME[g]+YiJing.DIZINAME[i]+"\t");
		}
		
	}
}