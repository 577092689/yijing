package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenXuexi {
	private DaoSiZhuMain daosz;  //������dao
	private DaoQiMen daoqm;
	private String mingzhu;  //Ϊ������Ϣ����ʽ��1997;1998;����1,1;2,2;
	private int yongshen;    //������ţ����1���¸�2���ո�3��ʱ��4
	private QimenPublic my; 
	private String[] xuexi ; //����ѧϰ�����е��������
	int index = 0; //��������Ϊxuexi����

	public QimenXuexi(DaoQiMen daoqm, QimenPublic my, DaoSiZhuMain daosz) {
  	this.daoqm = daoqm;
  	this.my = my;
  	this.daosz = daosz; 
  }
  
	public String getXuexi(StringBuffer str,String mingzhu, int yongshen) {
		this.mingzhu = mingzhu;
		this.yongshen = yongshen; 		
		//Messages.info(mingzhu+"; ����Ϊ��"+yongshen);
		xuexi = new String[100];
		index = 0;
		
		getXuexi1();
		
		//��ǰ̨���ݵ���������
		getXuexi2(yongshen,0,0);
		getXuexi3(yongshen,0,0);
		getXuexi4(yongshen,0,0);
		
		//�濴����		
		if(mingzhu!=null && !"".equals(mingzhu.trim())) {
			xuexi[index++]=my.NOKG+"****************************�濴����****************************";
			int[] mzGanzi = my.getMZhu(mingzhu);
			for(int j = 0; j<mzGanzi.length; j=j+2){
				xuexi[index++]=my.NOKG+"----------------------------����["+YiJing.TIANGANNAME[mzGanzi[j]]+YiJing.DIZINAME[mzGanzi[j+1]]+"]--------------------------";
				getXuexi2(0,mzGanzi[j],mzGanzi[j+1]);
				getXuexi3(0,mzGanzi[j],mzGanzi[j+1]);
				getXuexi4(0,mzGanzi[j],mzGanzi[j+1]);
			}
		}
		
		return my.format(str,xuexi);
	}
	
	/**
	 * һ��ȡ����
	 */
	public void getXuexi1() {
		xuexi[index++]=my.NOKG+"һ��ȡ����";
		xuexi[index++]="����������⣬�����ո�Ϊ�������ˣ�";
		xuexi[index++]="��ĸ�����Ů�Ŀ����������ʱ��Ϊ������";
		xuexi[index++]="�濴������������Ҳ��������Ϊ����";
		xuexi[index++]="�츨��Ϊ����Ժ��";
		xuexi[index++]="ֵ��Ϊ������࿼�٣�ֵʹΪ�������ٻ򸱼࿼�٣�";
		xuexi[index++]="����������£��Ŀƣ�";
		xuexi[index++]="���Ŵ����Ծ�ѧУ��¼ȡ֪ͨ�顢��ƣ�";
		xuexi[index++]="��ɴ���¼ȡ��ѧУ��";
		xuexi[index++]="ʱ�������壻";
	}
	
	/**
	 * �������ֶϣ�
	 * @param type��Ҫ�жϴ��ֵ�����, 4Ϊ��������3Ϊ������2Ϊ������1Ϊʱ��,0��ȡgan��zi��ֵ
	 * @param gan,zi: ��Щ����Ҫȡ��������ֱ��ָ���ɵģ���չ֮�ã�
	 * �����ո�Ϊ�������ƶϣ����������¸�Ϊ���񣬴�������ʱ��Ϊ����ʱ����
	 */
	public void getXuexi2(int type, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(type, gan, zi);		
		String yshen = ysinfo[0];               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
		int gong = Integer.valueOf(ysinfo[1]);  //�����乬
		gan = Integer.valueOf(ysinfo[2]);       //��������
		zi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧
		
		String sige = my.getSige(1)!=0?"���":my.getSige(2)!=0?"�¸�":my.getSige(3)!=0?"�ո�":my.getSige(4)!=0?"ʱ��":null; //�õ�������ʱ���乬
		int rgGong = my.getTianGong(SiZhu.rg, SiZhu.rz); //�õ��ոɹ�
		int nianGong = my.getTianGong(SiZhu.ng, SiZhu.nz); //��ɹ�		
		int jing3Gong = my.getMenGong(QiMen.MENJING3);   //�õ������乬
		int[] jing3Dpjy = my.getDpjy(jing3Gong);  //�õ������乬�ĵ�������
		int fuGong = my.getXingGong(QiMen.XINGFU); //�õ��츨���乬				
		
		String[] jxge = my.getJixiongge(gong);  //�õ��ù��ļ��׸�
		String[] ganwx = my.gettgWS(gan, zi);   //�õ��ø��乬��˥���
		int[] dpjy = my.getDpjy(gong);   	//�õ����񹬵ĵ�������
		int dingGong = my.getTianGong(YiJing.DING, 0); //�õ������乬
		int[] dingDpjy = my.getDpjy(dingGong);  //�õ������乬�ĵ�������
		String dingSMJ = my.isTGanSMJ(YiJing.DING, 0); //�����Ƿ��乬��Ĺ��
		String[] dingJX = my.getJixiongge(dingGong); //�ж϶����乬���׸�
		 
		String[] jing3WS = my.getmenWS(jing3Gong); //�����乬��˥
		String[] jing3JX = my.getJixiongge(jing3Gong); //�жϾ����乬���׸�
		
		xuexi[index++]=my.NOKG+"�������ֶϣ�"+yshen+"����";
		if(my.isMenFan()) xuexi[index++]="���ŷ��������Կ϶���˳��";
		if(my.isXingFan()) xuexi[index++]="���Ƿ��������Կ϶���˳��";
		if(jxge[0].equals("1")) xuexi[index++]="�����乬�ü��񣬴�������¼ȡ��"+jxge[1];
		if(my.isKong(gong, my.SHIKONGWANG)) xuexi[index++]="�����乬�������ض������ϣ�";
		
		if(ganwx[0].equals("1")) xuexi[index++]="�����乬���࣬������״̬�ȽϺã�"+ganwx[1];
		String smj = my.isTGanSMJ(gan, zi); //�����Ƿ���Ĺ��
		if(smj!=null) xuexi[index++]="�����乬��"+smj+"�أ�����״̬���ã�";
		
		if(my.isSheng(nianGong, jing3Gong)) xuexi[index++]="�����"+nianGong+"��������"+jing3Gong+"����Ϊ̫�������±��ǵ�һ����";
		if(my.isKong(fuGong, my.SHIKONGWANG)) xuexi[index++]="�츨�ǿ�������û����ƾ��";
		if(my.isChongke(gong, fuGong)) xuexi[index++]="������"+gong+"������츨��"+fuGong+"����֤������ѧϰ��";
		if(my.getSige(1)!=0)
		if(sige!=null) xuexi[index++]=sige+"����������������ʱ��������ͳ�ƻ�¼ȡ��Ա���磻��";
		if(dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG) xuexi[index++]="����ӵ��̸����������̣���ѧ������רҵ��";		
		
		int duGong = my.getMenGong(QiMen.MENDU); //�����乬
		int tyGong = my.getXingGong(QiMen.SHENYIN); //̫���乬
		if(duGong==tyGong) xuexi[index++]="���ż�̫�������˰������ӣ�";
		
		if(dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG)	
			xuexi[index++]="�������ٸ�����������������Ϊ������ʱ�е���ɣ����������ף�";
		
		if(my.isChongke(gong, duGong)) xuexi[index++]="����˶��Ź�����ϲ�������ԵĹ�����";
		if(my.isChongke(gong, jing3Gong)) xuexi[index++]="����˾��Ź�����ϲ�����Ƶȼ�����ǿ�Ĺ�����";
		if(gong==duGong) xuexi[index++]="�����ٶ��ţ�ϲ�������ԵĹ�����";
		if(gong==jing3Gong) xuexi[index++]="�����پ��ţ�ϲ�����Ƶȼ�����ǿ�Ĺ�����";
		
		int shengGong = my.getMenGong(QiMen.MENSHENG); //�����Ź�
		if(gong==shengGong) xuexi[index++]="���������ţ�ϲ�������⣻";
		int xiuGong = my.getMenGong(QiMen.MENXIU);  //������
		if(gong==xiuGong) xuexi[index++]="���������ţ�ϲ���罻����������������Ĺ�����";
		int siGong = my.getMenGong(QiMen.MENSI); //������
		if(gong==siGong) xuexi[index++]="���������ţ�����״̬���ã�";		
		
		int heGong = my.getShenGong(QiMen.SHENHE); //�����乬
		int sheGong = my.getShenGong(QiMen.SHENSHE); //�����乬
		if(gong == heGong) xuexi[index++]="���������ϣ���Ƣ����ͣ�";
		if(gong == sheGong) xuexi[index++]="���������ߣ�������ѹ����";
	}

	
	/**
	 * �����ɼ����ӷ�����
	 * @param type��Ҫ�жϴ��ֵ�����, 4Ϊ��������3Ϊ������2Ϊ������1Ϊʱ��,0��ȡgan��zi��ֵ
	 * @param gan,zi: ��Щ����Ҫȡ��������ֱ��ָ���ɵģ���չ֮�ã�
	 * �����ո�Ϊ�������ƶϣ����������¸�Ϊ���񣬴�������ʱ��Ϊ����ʱ����
	 */
	public void getXuexi3(int type, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(type, gan, zi);		
		String yshen = ysinfo[0];               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
		int gong = Integer.valueOf(ysinfo[1]);  //�����乬
		gan = Integer.valueOf(ysinfo[2]);       //��������
		zi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧

		xuexi[index++]=my.NOKG+"�����ɼ����ӷ�����"+yshen+"����";
		String[] ysGanhj = my.isganHeju(gan,zi);
		if(ysGanhj[0].equals("1")) xuexi[index++]="�����乬�Ͼ֣��ɼ��Ϻã�"+ysGanhj[1];
		else if(ysGanhj[0].equals("0")) xuexi[index++]="�����乬ʧ�����������ɼ�һ�㣻"+ysGanhj[1];
		else xuexi[index++]="�����乬��ȫʧ���������ɼ���⣻"+ysGanhj[1];
		
		String[] dingHj = my.isganHeju(YiJing.DING,0);
		int dingGong = my.getTianGong(YiJing.DING, 0); //�õ������乬
		int[] dingDpjy = my.getDpjy(dingGong);  //�õ������乬�ĵ�������
		if(dingHj[0].equals("1")) xuexi[index++]="����Ϊ���£��乬�Ͼ֣��ɼ��Ϻã�"+dingHj[1];
		else if(dingHj[0].equals("0")) xuexi[index++]="����Ϊ���£��乬ʧ�����������ɼ�һ�㣻"+dingHj[1];
		else xuexi[index++]="����Ϊ���£��乬��ȫʧ���������ɼ���⣻"+dingHj[1];
		if(dingDpjy[0]==YiJing.GUI || dingDpjy[1]==YiJing.GUI ||
				dingDpjy[0]==YiJing.GENG || dingDpjy[1]==YiJing.GENG) xuexi[index++]="��Ϊ�Ŀƣ����ӹ���Ӹ�Ϊ����࣬�Ҿ��治���ࣻ";
//		if(my.isKong(dingGong, my.SHIKONGWANG)) xuexi[index++]="��Ϊ�Ŀƣ��乬�������ɼ����ã�";
//		if(dingSMJ!=null) xuexi[index++]="��Ϊ�Ŀƣ��乬��"+dingSMJ+"�أ��ɼ������룻";
//		if(dingJX[0].equals("0")) xuexi[index++]="��Ϊ�Ŀƣ��乬�׸񣬳ɼ������룻"+dingJX[1];
		
		int jing3Gong = my.getMenGong(QiMen.MENJING3);
		String[] jing3Hj = my.ismenHeju(jing3Gong);
		if(jing3Hj[0].equals("1")) xuexi[index++]="����Ϊ�����乬�Ͼ֣��ɼ��Ϻã�"+jing3Hj[1];
		else if(jing3Hj[0].equals("0")) xuexi[index++]="����Ϊ�����乬ʧ�����������ɼ�һ�㣻"+jing3Hj[1];
		else xuexi[index++]="����Ϊ�����乬��ȫʧ���������ɼ���⣻"+jing3Hj[1];
//		if(my.isKong(jing3Gong, my.SHIKONGWANG)) xuexi[index++]="����Ϊ��ƣ��乬�������ɼ����ã�";
//		if(jing3WS[0].equals("-1")) xuexi[index++]="����Ϊ��ƣ��乬�����������ɼ������룻"+jing3WS[1];
//		if(jing3JX[0].equals("0")) xuexi[index++]="����Ϊ��ƣ��乬�׸񣬳ɼ������룻"+jing3JX[1];
		
		if(dingGong==gong) xuexi[index++]="����������ͬ�����ĿƳɼ��ã�";
		else if(my.isSheng(dingGong, gong)) xuexi[index++]="�����乬�����񹬣��ĿƳɼ��ã�";
		else if(my.isSheng(gong,dingGong)) xuexi[index++]="�����������乬���ĿƳɼ��ã�";
		else if(my.isBihe(gong,dingGong)) xuexi[index++]="�����붡���乬��Ⱥͣ��ĿƳɼ������ԣ�";
		else if(my.isChongke(gong,dingGong)||my.isChongke(dingGong,gong)) xuexi[index++]="�����붡���乬���ˣ��ĿƳɼ������룻";
		
		if(jing3Gong==gong) xuexi[index++]="����������ͬ������Ƴɼ��ã�";
		else if(my.isSheng(jing3Gong, gong)) xuexi[index++]="�����乬�����񹬣���Ƴɼ��ã�";
		else if(my.isSheng(gong,jing3Gong)) xuexi[index++]="�����������乬����Ƴɼ��ã�";
		else if(my.isBihe(gong,jing3Gong)) xuexi[index++]="�����뾰���乬��Ⱥͣ���Ƴɼ������ԣ�";
		else if(my.isChongke(gong,jing3Gong)||my.isChongke(jing3Gong,gong)) xuexi[index++]="�����뾰���乬���ˣ���Ƴɼ������룻";
		
		String gongshu = my.getGongshu(gong);
		if(ysGanhj[0].equals("1")) xuexi[index++]="�����乬�Ͼ֣�"+gongshu+"�ɶ�90%���ϣ�";
		else if(ysGanhj[0].equals("0")) xuexi[index++]="�����乬ʧ����������"+gongshu+"�ɶ�60%���ң�";
		else xuexi[index++]="�����乬��ȫʧ��������"+my.getGongshu(gong)+"�ɶ�40%���£�";
		if(my.isKong(gong, my.SHIKONGWANG)) xuexi[index++]="�������乬������"+gongshu+"����Ӧ���룻";
		
		int wuGong = my.getTianGong(YiJing.WUG, 0);
		String wugongshu = my.getGongshu(wuGong);
		xuexi[index++]="��ѧ���ÿ��������乬��"+wugongshu;
	}
	
	/**
	 * �ġ�¼ȡѧУ��רҵ������
	 * @param type��Ҫ�жϴ��ֵ�����, 4Ϊ��������3Ϊ������2Ϊ������1Ϊʱ��,0��ȡgan��zi��ֵ
	 * @param gan,zi: ��Щ����Ҫȡ��������ֱ��ָ���ɵģ���չ֮�ã�
	 * �����ո�Ϊ�������ƶϣ����������¸�Ϊ���񣬴�������ʱ��Ϊ����ʱ����
	 */
	public void getXuexi4(int type, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(type, gan, zi);		
		String yshen = ysinfo[0];               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
		int gong = Integer.valueOf(ysinfo[1]);  //�����乬
		gan = Integer.valueOf(ysinfo[2]);       //��������
		zi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧
		
		int yearGong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //�õ�����乬
		int fuGong = my.getXingGong(QiMen.XINGFU);  //�õ��츨���乬
		int ruiGong = my.getXingGong(QiMen.XINGRUI);  //�õ��������乬
		int zfGong = daoqm.getZhifuGong(SiZhu.sg, SiZhu.sz); //�õ�ֵ���乬
    int zsGong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);  //�õ�ֵʹ�乬
    
    xuexi[index++]=my.NOKG+"�ġ�¼ȡѧУ��רҵ������"+yshen+"����";
    
    if(my.isSheng(yearGong, gong)) xuexi[index++]="����乬�����񹬣�һ����ѧУ¼ȡ��";
    else if(my.isBihe(yearGong, gong)) xuexi[index++]="����乬�����񹬱Ⱥͣ��б�ѧУ¼ȡ֮��";
    else if(yearGong==gong) xuexi[index++]="����乬������ͬ������ѧУ¼ȡ֮��";
    else if(my.isSheng(gong,yearGong)) xuexi[index++]="����������乬����ʾϣ����ѧУ¼ȡ������Ը����";
    else xuexi[index++]="����������乬���ˣ����ᱻѧУ¼ȡ��";
    
    if(my.isSheng(fuGong, gong)) xuexi[index++]="�츨���乬�����񹬣�һ����ѧУ¼ȡ��";
    else if(my.isBihe(fuGong, gong)) xuexi[index++]="�츨���乬�����񹬱Ⱥͣ��б�ѧУ¼ȡ֮��";
    else if(fuGong==gong) xuexi[index++]="�츨���乬������ͬ������ѧУ¼ȡ֮��";
    else if(my.isSheng(gong,fuGong)) xuexi[index++]="�������츨���乬����ʾϣ����ѧУ¼ȡ������Ը����";
    else xuexi[index++]="�������츨���乬���ˣ����ᱻѧУ¼ȡ��";
    
    if(my.isSheng(zfGong, gong)) xuexi[index++]="ֵ���乬�����񹬣�һ����ѧУ¼ȡ��";
    else if(my.isBihe(zfGong, gong)) xuexi[index++]="ֵ���乬�����񹬱Ⱥͣ��б�ѧУ¼ȡ֮��";
    else if(zfGong==gong) xuexi[index++]="ֵ���乬������ͬ������ѧУ¼ȡ֮��";
    else if(my.isSheng(gong,zfGong)) xuexi[index++]="������ֵ���乬����ʾϣ����ѧУ¼ȡ������Ը����";
    else xuexi[index++]="������ֵ���乬���ˣ����ᱻѧУ¼ȡ��";
    
    if(my.isSheng(fuGong, ruiGong)) xuexi[index++]="�츨���乬���������乬���ֱ�ʾ���Ա�ѧУ¼ȡ��";
    else if(my.isBihe(fuGong, ruiGong)) xuexi[index++]="�츨���乬���������乬�Ⱥͣ��ֱ�ʾ���Ա�ѧУ¼ȡ֮��";
    else if(fuGong==ruiGong) xuexi[index++]="�츨���乬��������ͬ�����ֱ�ʾ���Ա�ѧУ¼ȡ֮��";
    else if(my.isSheng(ruiGong,fuGong)) xuexi[index++]="�������乬���츨���乬���ֱ�ʾϣ����ѧУ¼ȡ������Ը����";
    else xuexi[index++]="�������乬���츨���乬���ˣ��ֱ�ʾ���ᱻѧУ¼ȡ��";
    
    String fxiang = QiMen.JIUGONGFXIANG[yearGong]; //�õ���ɹ��ķ�λ
    boolean isnei = my.isNenpan(yearGong);
    String neiwai = my.isNenpan(yearGong)?"ʡ��":"ʡ��";   //�ж���ɹ������̻�������
    xuexi[index++]="�����"+yearGong+"��������"+(isnei?"����":"����")+"��¼ȡѧУ�ھ�ס��"+neiwai+fxiang+"��";
    
    int yearGongMen = daoqm.gInt[3][1][yearGong]; //�õ�����乬����
    String[] BAMENJUANYE = {"","Ϊ������ͥ�����רҵ","Ϊ������������ҽѧ��רҵ",
    		"Ϊ����������רҵ","Ϊ������רҵ",
    		"",
    		"Ϊ���ι�����רҵ","Ϊ������רҵ","Ϊũҵ������רҵ","Ϊ����������רҵ"};
    xuexi[index++]="�����"+yearGong+"����"+QiMen.bm1[yearGongMen]+"�ţ�"+BAMENJUANYE[yearGongMen]+"��";
	}
	


}