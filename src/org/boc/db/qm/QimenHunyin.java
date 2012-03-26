package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

/**
 * 1. ȡ����
 * 2. �����ҡ��������������Ը������ְҵ�ж�
 * 3. ���ֶϣ�ΪPRIVATE + PUBLIC
 * 4. ����ƪ��ΪPRIVATE+LOVE �� PUBLIC+LOVE
 * 5. ����ƪ��ΪPRIVATE+MARRY �� PUBLIC+MARRY
 * 6. �����ο���Ϊ3��4��5
 * @author Administrator
 *
 */
public class QimenHunyin {
	/**
	 * PRIVATE: �����۶ϣ���ֻ�����������ص��������������������������ϣ����������ǵȣ�
	 * PUBLIC������������޹ص��������������������������ϡ�����������붡�ȣ�
	 * LOVE: �����۶�
	 * MARRY: �����۶�
	 * ʵ���������������ϣ���LOVE�������LOVE��BASIC��PUBLIC����LOVE+BASICֻ���ָ����
	 */
	private static final int PRIVATE = 1,PUBLIC=2, LOVE=3, MARRY=4;  
	private DaoQiMen daoqm;
	private QimenPublic my; 
	private String mingzhu ;  // ��������1997|2001��1,1|3,5
	private int yongshen;  //���񣬴ӽ��洫���ģ����ꡢ�¡��ա�ʱ��
	private boolean boy;   //���л���Ů
	private String[] hunyin ; //�������˹����е��������
	int index = 0; //��������Ϊhunyin����

  public QimenHunyin(DaoQiMen daoqm, QimenPublic my) {
  	this.daoqm = daoqm;
  	this.my = my;
  }
  
	public String getHunyin(StringBuffer str,String mingzhu, int yongshen,boolean boy) {
		hunyin = new String[2000];
		index = 0;
		this.mingzhu = mingzhu;
		this.yongshen = yongshen;
		this.boy = boy;		
		
		hunyin[index++]=my.NOKG+"һ��ȡ����";
		getHunyin1();
		hunyin[index++]=my.NEWLINE;
		
		hunyin[index++]=my.NOKG+"�����Ը���ġ����ࡢְҵ��";
		getHunyin2(yongshen, 0, 0);
		hunyin[index++]=my.NEWLINE;	
		
		String[] ysinfo = my.getYShenInfo(yongshen, 0, 0);		
		String yshen = ysinfo[0];               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
		hunyin[index++]=my.NOKG+"�������ֶ�["+yshen+"]��";
		getHunyin3(yongshen, 0, 0,PUBLIC);
		getHunyin3(yongshen, 0, 0,PRIVATE);
		hunyin[index++]=my.NEWLINE;
		
		hunyin[index++]=my.NOKG+"�ġ�����ƪ["+yshen+"]��";
		getLoveAndMarry(PRIVATE, LOVE,yongshen,0,0);  //�����йص�
		getLoveAndMarry(PUBLIC, LOVE,yongshen,0,0);   //�������Ұ��������
		hunyin[index++]=my.NEWLINE;
		
		hunyin[index++]=my.NOKG+"�塢����ƪ["+yshen+"]��";
		getLoveAndMarry(PRIVATE, MARRY,yongshen,0,0);
		getLoveAndMarry(PUBLIC, MARRY,yongshen,0,0);
		hunyin[index++]=my.NEWLINE;
		
		//�����ο���ֻ���������������������������жϣ���ֻ��Ҫ��������ص���Ϣ��PRIVATE����
		int[] mzhu = my.getMZhu(mingzhu);
		if(mzhu.length>1 && mzhu[0] * mzhu[1]!=0) {
			hunyin[index++]=my.NOKG+"���������ο�["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]";
			
			hunyin[index++]="------------------------------����ƪ------------------------------";
			getHunyin3(0, mzhu[0], mzhu[1],PRIVATE);
			hunyin[index++]=my.NEWLINE;
			
			hunyin[index++]="------------------------------����ƪ------------------------------";
			getLoveAndMarry(PRIVATE, LOVE, 0, mzhu[0],mzhu[1]);
			hunyin[index++]=my.NEWLINE;
			
			hunyin[index++]="------------------------------����ƪ------------------------------";
			getLoveAndMarry(PRIVATE, MARRY, 0, mzhu[0],mzhu[1]);
			hunyin[index++]=my.NEWLINE;
		}
		
		return my.format(str, hunyin);
	}
	
	/**
	 * һ��ȡ����
	 * Ϊ��Լƪ�����˴��ɲ����
	 */
	private void getHunyin1() {		
		hunyin[index++]="�Լ���⣬��ѡ�ո�Ϊ����ʱ��Ϊ�������ż����ĸ����Ů����ʱ��Ϊ����";
		hunyin[index++]="�������̸������ڣ����̸�����ȥ��";
		hunyin[index++]="�����̵����桢Ů���������з����֮�ɴ���Ů����";
		hunyin[index++]="�����̸����з�������Ů�����֮�ɴ����з���";
		hunyin[index++]="������Ϊ������ý�ˣ�";
		hunyin[index++]="�Զ���Ϊ�鸾�����֤�顢����Ů�ѣ���Ϊǰ�ޣ�";
		hunyin[index++]="�Ա���Ϊ����������ѣ���Ϊǰ��";
		hunyin[index++]="ʱ��Ҳ�����壻";
		hunyin[index++]="��������ٷ�λҲ��Ϊ����";
	}
	/**
	 * �����Ը���ġ����ࡢְҵ��
	 * �����ҡ�����������������
	 * @param 
	 */
	private void getHunyin2(int ystype, int gan, int zi) {		
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		String yshen = ysinfo[0];               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
		int ysgan = Integer.valueOf(ysinfo[2]);       //��������
		int yszi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		//Ϊ��Լƪ������ֻ����������������´����ǽ�ysgan������ǰ��
		int[] ganzi = {ysgan, ysgan==YiJing.YI?0:YiJing.YI, 
				ysgan==YiJing.GENG?0:YiJing.GENG, ysgan==YiJing.DING?0:YiJing.DING, ysgan==YiJing.BING?0:YiJing.BING};; 
		
		for(int j = 0; j<ganzi.length; j++){
			if(ganzi[j]==0) continue; 
			if(ganzi[j]==ysgan)
				hunyin[index++]="----------------------------"+yshen+"["+YiJing.TIANGANNAME[ganzi[j]]+"]--------------------------";
			else
				hunyin[index++]="----------------------------["+YiJing.TIANGANNAME[ganzi[j]]+"]--------------------------";
			this.getXingeOfShen(ganzi[j],0);
			this.getXingeOfMen(ganzi[j],0);
			this.getZhangxiang(ganzi[j],0);
			this.getZhiye(ganzi[j],0);
		}		
	}
	/**
	 * �������ֶϣ�
	 * @param ystype : �������ͣ�0��ָ����gan��ziΪ����1Ϊ������2Ϊ������3Ϊ������4Ϊʱ����
	 * @param gan,zi: Ϊָ������֧�����񣬲���ȱʡ���ꡢ�¡��ա�ʱ��������ȡ����Ϊ����������
	 * @param all�� �����PUBLIC����PRIVATE
	 */
	private void getHunyin3(int ystype, int gan ,int zi, int all) {		
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		String yshen = ysinfo[0];               //��������ƣ����ո�Ϊ�ã����Ϊ�õ�
		int gong = Integer.valueOf(ysinfo[1]);  //�����乬
		int ysgan = Integer.valueOf(ysinfo[2]);       //��������
		int yszi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		boolean iskong = my.isKong(gong, my.SHIKONGWANG);
		int[] tpjy = my.getTpjy(gong);
		int[] dpjy = my.getDpjy(gong);
		int nianganGong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //��������乬
		int nianziGong = my.getDiziGong(SiZhu.nz);  //������֧����Ӧ���乬
		int gengGong = my.getTianGong(YiJing.GENG, 0); //���乬
		int yiGong = my.getTianGong(YiJing.YI, 0); //���乬
		
		if(all==PUBLIC) {			 
			if(my.isMenFu()) hunyin[index++]="���ŷ��ʣ�����Ҷ���̸���ɣ�������벻�ˣ�";
			if(my.isMenFan()) hunyin[index++]="���ŷ��ʣ�̸���󷴸���˳�����Ҳ��һ����";
			if(my.isXingFu()) hunyin[index++]="���Ƿ��ʣ�����Ҷ���̸���ɣ�������벻�ˣ�";
			if(my.isXingFan()) hunyin[index++]="���Ƿ��ʣ�̸���󷴸���˳�����Ҳ��һ����";
			
			//ֻ���ո�Ϊ����ʱ�ɲ�����
			if(ystype==my.YSHENDAY) {
				int shigong = my.getTianGong(SiZhu.sg, SiZhu.sz);
				boolean shikong = my.isKong(shigong, my.SHIKONGWANG); //�õ�ʱ�����ڵĹ��Ƿ�Ѯ��
				int shimen = daoqm.gInt[3][1][shigong]; //�õ�ʱ�����ڹ����� 
				int shishen = daoqm.gInt[1][1][shigong]; //�õ�ʱ�����ڹ�����
				if(shikong) hunyin[index++]="ʱ�ɷ������ҲΪ������գ��²���֮��";
				if(shimen==QiMen.MENKAI) hunyin[index++]="ʱ���ٿ��ţ����������Ѿ�������";
				if(my.isTaohua(ysgan, 0)) hunyin[index++]="ʱ�����һ�����������֮��������йأ�";
				else if(shishen==QiMen.SHENHE) hunyin[index++]="ʱ�������ϣ���������֮��������йأ�";
				else if(shishen==QiMen.SHENHE && my.isTaohua(ysgan, 0)) hunyin[index++]="ʱ�������������һ�����������֮��������йأ�";
			}
			
			int heGong= my.getShenGong(QiMen.SHENHE);
			int[] hetpjy=my.getTpjy(heGong);
			if(hetpjy[0]==YiJing.GENG || hetpjy[1]==YiJing.GENG) {
				hunyin[index++]="������"+heGong+"�������Ϊ����������и������׵ķ��ţ�����������������Ȼ��;ز�ۣ�";
			}
			
			int[] mzhu = my.getMZhu(mingzhu);  //������֧��һ��Ϊ���˵ģ�Ĭ��0,1Ϊ�Լ��ģ�2��3Ϊ�����
			if(mzhu.length==4 && mzhu[0]*mzhu[1]*mzhu[2]*mzhu[3]!=0) {
				String boyname = null;
				String girlname = null;
				if(boy) {
					boyname = "�з�����["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
					girlname = "Ů������["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
				}else{
					boyname = "Ů������["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
					girlname = "�з�����["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
				}
				String ysname = boy ? boyname:girlname;  //��������
				String dxname = boy ? girlname:boyname;   //��������
				
				int selfmzGong = my.getTianGong(mzhu[0], mzhu[1]);
				int whomzGong = my.getTianGong(mzhu[2], mzhu[3]);
	
					String[] ysnmWS = my.gettgWS(mzhu[0], mzhu[1]);
					String[] dxnmWS = my.gettgWS(mzhu[2], mzhu[3]);
					if(!ysnmWS[0].equals("1") && selfmzGong==my.getXingGong(QiMen.XINGRUI))
						hunyin[index++] =ysname+"�乬�������ַ����ǲ��ǣ�˵���������ʲ����������";
					if(!dxnmWS[0].equals("1") && whomzGong==my.getXingGong(QiMen.XINGRUI))
						hunyin[index++] =dxname+"�乬�������ַ����ǲ��ǣ�˵���������ʲ����������";
			}
			
			if(boy && my.isChongke(nianganGong, yiGong))
				hunyin[index++] ="�����"+nianganGong+"����[��]����"+yiGong+"����Ϊ��λ���쵼���䲻����Ŀǰ״�����ѣ����׸���ף���ĸ���޳���׮���£�";
			else if(!boy && my.isChongke(nianganGong, gengGong))
				hunyin[index++] ="�����"+nianganGong+"����[��]����"+gengGong+"����Ϊ��λ���쵼���䲻����Ŀǰ״�����ѣ����׸���ף���ĸ���޳���׮���£�";
			if(my.isChongke(nianganGong, heGong))
				hunyin[index++] ="�����"+nianganGong+"������������"+heGong+"����Ҳ����ĸ���޳���׮���£�˭���������࣬˭���ֵù�������������";
		}
		//////////////////////////////////////////////////////////////////////
		///   �������������
		//////////////////////////////////////////////////////////////////////
		if(all==PRIVATE) {
			int menGong = daoqm.gInt[3][1][gong];  //��Ϊ���е���������Ϊ�����ڵ��̵ĵڼ���
			int[] angan = my.getDpjy(menGong);
			if(boy && (angan[0]==YiJing.YI || angan[0]==YiJing.YI)) hunyin[index++]="�������ٰ���[��]����ʾ��Ϊ��������";
			if(boy && (angan[0]==YiJing.DING || angan[0]==YiJing.DING)) hunyin[index++]="�������ٰ���[��]����ʾ��Ϊ��������";
			if(!boy && (angan[0]==YiJing.GENG || angan[0]==YiJing.GENG)) hunyin[index++]="�������ٰ���[��]����ʾ��Ϊ��������";
			if(!boy && (angan[0]==YiJing.BING || angan[0]==YiJing.BING)) hunyin[index++]="�������ٰ���[��]����ʾ��Ϊ��������";
			
			String isTsmj = my.isTGanSMJ(ysgan, 0);  //�ж��������̸��Ƿ�����Ĺ��
			boolean isDsmj = my.isDGanMu(ysgan, 0);  //�ж�������̸��Ƿ���Ĺ
			if(isTsmj!=null) hunyin[index++]="�������̸�"+YiJing.TIANGANNAME[ysgan]+"��"+gong+"����"+isTsmj+"�أ��������������鷳��";
			else if(isDsmj) hunyin[index++]="������̸�"+YiJing.TIANGANNAME[ysgan]+"��"+my.getDiGong(ysgan, 0)+"����Ĺ�أ��������������鷳��";		
			
			if(iskong) hunyin[index++]="����Ѯ�գ���������ա��²���֮��Ҳ��ʾ�����鷳�ò����������̸������";
			if(boy && iskong && (tpjy[0]==YiJing.YI || tpjy[1]==YiJing.YI || dpjy[0]==YiJing.YI || dpjy[1]==YiJing.YI))
				hunyin[index++]="������[��]ͬ�������������δ�ɼ�֮��";
			if(!boy && iskong && (tpjy[0]==YiJing.GENG || tpjy[1]==YiJing.GENG || dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG))
				hunyin[index++]="������[��]ͬ�������������δ�ɼ�֮��";		
			
			if(my.isChongke(nianganGong, gong))
				hunyin[index++] ="�����"+nianganGong+"������������"+gong+"����Ϊ��λ���쵼���䲻����Ŀǰ״�����ѣ����׸���ף�";
			
			String[] jxge = my.getJixiongge(gong);
			if(my.isYima(gong)) {
				if(jxge[0].equals("1")) hunyin[index++]="���������ǣ�������飬�����ʾ�Լ����������飻"+jxge[1];
				else hunyin[index++]="���������ǣ������ַ��׸�Ϊ���ϣ�������飻"+jxge[1];
			}
			
			if(daoqm.gInt[3][1][gong]==QiMen.MENDU) {
				String t="�����ٶ��ţ�����Ը��̸����Ҳ��ʾҪ���ܣ�";			
				if(my.isKong(gong)) t += "�����֮������ʾΪ�뱣�ܣ�";
				hunyin[index++] = t; 
			}
			if(daoqm.gInt[1][1][gong]==QiMen.SHENWU) hunyin[index++]="���������䣬��ʾ��������";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENSHE) hunyin[index++]="���������ߣ���ʾ�������";			
			if(my.isTaohua(ysgan, 0)) hunyin[index++]="�������һ�����"+(my.isNenpan(gong)?"����Ϊǽ���һ�":"����Ϊǽ���һ�")+"��Ӧ������֮��";
			if((gong==2 || gong==6) && iskong && daoqm.gInt[3][1][gong]==QiMen.MENSI) {
				hunyin[index++]="������"+gong+"�������ţ��ַ����������Ҳ����ȥδ��֮�£���ʾ��ȥ��ĸ���Զ�û�гɻ飻";
			}else if((gong==2 || gong==6) && daoqm.gInt[3][1][gong]==QiMen.MENSI) {
				hunyin[index++]="������"+gong+"�������ţ���ʾ��ĸ���Ի��£�";
			}
			
			if(daoqm.gInt[1][1][gong]==QiMen.SHENHE) {
				hunyin[index++] = "�����������ϣ�Ҳ���ж��λ�����";
			}
		}		
	}
	/**
	 * �������������
	 * @param all: ��PUBLIC����PRIVATE
	 * @param love: LOVEֻ�ʺ������׶Σ�MARRY���ʺϻ����׶�
	 * @param ystype
	 * @param gan
	 * @param zi
	 */
	private void getLoveAndMarry(int all, int love, int ystype,int gan, int zi) {	
		int[] mzhu = my.getMZhu(mingzhu);  //������֧��һ��Ϊ���˵ģ�Ĭ��0,1Ϊ�Լ��ģ�2��3Ϊ�����
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		int gong = Integer.valueOf(ysinfo[1]);  			//�����乬
		int ysgan = Integer.valueOf(ysinfo[2]);       //��������
		int yszi = Integer.valueOf(ysinfo[3]);        //����ĵ�֧
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		boolean iskong = my.isKong(gong);  //�����乬�Ƿ����
		int shiGong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //ʱ���乬
		int heGong = my.getShenGong(QiMen.SHENHE); //�����乬
		int nianganGong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //��������乬
		int nianziGong = my.getDiziGong(SiZhu.nz);  //������֧����Ӧ���乬
		int gengGong = my.getTianGong(YiJing.GENG, 0); //���乬
		int yiGong = my.getTianGong(YiJing.YI, 0); //���乬
		int bingGong = my.getTianGong(YiJing.BING, 0); //���乬
		int dingGong = my.getTianGong(YiJing.DING, 0); //���乬
		int[] ystpjy = my.getTpjy(gong);  //�õ��������������
		int[] ysdpjy = my.getDpjy(gong);

		/**  ����ר�������йص���������� */
		if(all==PRIVATE) {
			//����Ͼֻ�ʧ��
			String[] isheju = my.isganHeju(ysgan, 0); 
			if(!isheju[0].equals("1")) 
				hunyin[index++]="�����乬ʧ�֣���"+(love==LOVE?"�����ѳɣ�":"������˳��")+isheju[1];
			else if(isheju[0].equals("1")) 
				hunyin[index++]="�����乬�Ͼ֣���"+(love==LOVE?"�����׳ɣ�":"������˳��")+isheju[1];
			
			//�����������Ϲ��������̫�깬
			String t=null;
			if(my.isSheng(gong, heGong) || my.isSheng(heGong, gong)) {
				if(love==LOVE) t= "������"+gong+"������������"+heGong+"���������������׳ɣ�";
				else if(love==MARRY) t= "������"+gong+"������������"+heGong+"��������û�������뷨���벻�˻飻";
				if(iskong) t += "���������������������";
				else if(my.isTpJixing(ysgan, yszi)) t+= "�������ǻ��̣�������������";
				else if(iskong && my.isTpJixing(ysgan, yszi)) t+= "����Ѯ�գ������ǻ��̣�������������";
				if(t!=null) hunyin[index++] = t; 
			}else if(love==LOVE && my.isChongke(gong, heGong)) {
				hunyin[index++] ="������"+gong+"���������"+heGong+"����������̸������";
			}		
			
			if(my.isChongke(gong, nianganGong)) {
				if(love==LOVE) hunyin[index++] ="������"+gong+"�����̫���������"+nianganGong+"����Ҳ��һ�����̸���ɣ�";
				if(love==MARRY) hunyin[index++] ="������"+gong+"�����̫���������"+nianganGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
			}else if(my.isChongke(gong, nianziGong)) {
				if(love==LOVE) hunyin[index++] ="������"+gong+"�����̫���֧����"+nianziGong+"����Ҳ��һ�����̸���ɣ�";
				if(love==MARRY) hunyin[index++] ="������"+gong+"�����̫���֧����"+nianziGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
			}
			
			if(love==MARRY) {
				if(daoqm.gInt[3][1][gong]==QiMen.MENJING1 && !my.getshenJX(gong)[0].equals("1") && 
						(ysdpjy[0]==YiJing.WUG || ysdpjy[1]==YiJing.WUG))
					hunyin[index++]="�����پ��ţ����������ڽǣ����������������죬˵������ΪǮ���ֹ�˾��";
				else if(daoqm.gInt[3][1][gong]==QiMen.MENJING1)
					hunyin[index++]="�����پ��ţ����������ڽǣ�";
				
				
				if(my.isYima(gong)) hunyin[index++] = "����飬���������ǣ���ʾ�Լ�ȥ���Ѿ����������飻";
				if(daoqm.gInt[1][1][gong]==QiMen.SHENHU) hunyin[index++] = "����飬�����ٰ׻�Ҳ��ʾƢ�����ꡢ�����Ĵ�";
				
				//�������乬�Ƿ��У�56�Ҽ�����110������
				t = null;
				for(int thegeju : my.getShiganKeying(gong)){
					if(thegeju==110) t = "������"+gong+"������+�ң�������飬�з����������ʱ��ο����е�֧����������Ƕ���ݣ�";
					if(thegeju==56)  t = "������"+gong+"������+����˵����������ҳ��ߣ�";
				}
				//����������֮�ɹ��Ĺ�ϵ
				t=null;
				int hegan = my.getGanHe(ysgan);
				int ganheGong = 0;
				if(hegan==YiJing.JIA) 
					ganheGong=daoqm.getZhifuGong();
				else
					ganheGong = my.getTianGong(hegan, 0);
				if(my.isSheng(gong, ganheGong) || my.isSheng(ganheGong, gong)) {
					t = "������"+gong+"��������֮��["+YiJing.TIANGANNAME[hegan]+"]����"+ganheGong+"��������֤��˫������ܺã����Ҳ���벻�ˣ�";
					if(my.isYima(ganheGong))
						t+="��"+ganheGong+"�������ǣ�˵����������֮�����ˣ�";
					if(my.isKong(ganheGong))
						t += "��"+ganheGong+"���������Ҳ�������������ˣ�";
					int[] tpjy = my.getTpjy(ganheGong);
					int[] dpjy = my.getDpjy(ganheGong);
					if(daoqm.gInt[1][1][ganheGong]==QiMen.SHENWU && 
							(tpjy[0]==YiJing.XIN || tpjy[1]==YiJing.XIN || dpjy[0]==YiJing.XIN || dpjy[1]==YiJing.XIN))
						t+="�乬���������ϳ����䣬��һ��˵����������֮�¶����´��";
				}else if(my.isChongke(gong, ganheGong) || my.isChongke(ganheGong, gong))
					t = "������"+gong+"��������֮��["+YiJing.TIANGANNAME[hegan]+"]����"+ganheGong+"�����ˣ�֤��˫����ϵ���ͣ�������ϵ���ȶ���";
				if(t!=null) hunyin[index++] = t;
				
				//���������Ҷ���������
				t=null;
				int dingdpGong = my.getDiGong(YiJing.DING, 0);  //�����ڵ��̹�
				int bingdpGong = my.getDiGong(YiJing.BING, 0);
				int gengdpGong = my.getDiGong(YiJing.GENG, 0);
				int yidpGong = my.getDiGong(YiJing.YI, 0);  //�õ��������乬
				int _gan1=0, gong1 = 0;
				if(gong==yidpGong) {_gan1=YiJing.YI; gong1=yiGong; }
				else if(gong==gengdpGong) {_gan1=YiJing.GENG; gong1=gengGong;}
				else if(gong==bingdpGong) {_gan1=YiJing.BING; gong1=bingGong;}
				else if(gong==dingdpGong) {_gan1=YiJing.DING; gong1=dingGong;}
				if(gong1!=0) {
					t="��������["+YiJing.TIANGANNAME[_gan1]+"]�������������ѣ����������δ��ͬ�ӣ�";
					if(ystpjy[0]==YiJing.XIN || ystpjy[1]==YiJing.XIN || ysdpjy[0]==YiJing.XIN || ysdpjy[1]==YiJing.XIN)
						t += "������ͬ��������������Υ��������ͬ�ӣ�";
					if(daoqm.gInt[2][1][gong]==QiMen.XINGRUI)
						t += "��������ͬ����Ϊ����Ҳ�������߲��㣻";
					if(gong1==7)
						t+=YiJing.TIANGANNAME[_gan1]+"��7������������һ�������С����";
					if(my.isBihe(gong1, gong))
						t+="�������乬�Ⱥͣ��������嶤����";
					if(my.isSheng(gong, gong1) || my.isSheng(gong1, gong))
						t+="�������乬�������������嶤����";
					if(t!=null)
						t+="��ü������"+YiJing.TIANGANNAME[_gan1]+"����"+gong1+"��֮���ϣ�����ʱ�䳤���پŵ�Ҳ��ʱ�䳤��";
				}
				if(t!=null) hunyin[index++]=t;
			}
		}
		
		//�����乬��ͬ������֧�ϡ���������ȡ����ˣ����Ͽ˶���������������̫�깬��		
		if(mzhu.length==4 && mzhu[0]*mzhu[1]*mzhu[2]*mzhu[3]!=0 &&(gan!=0 && zi!=0)) { //gan,zi!=0��ʾҪ��������
			String boyname = null;
			String girlname = null;
			if(boy) {
				boyname = "�з�����["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
				girlname = "Ů������["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
			}else{
				boyname = "Ů������["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
				girlname = "�з�����["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
			}
			String ysname = boy ? boyname:girlname;  //��������
			String dxname = boy ? girlname:boyname;   //��������
			
			int selfmzGong = my.getTianGong(mzhu[0], mzhu[1]);
			int whomzGong = my.getTianGong(mzhu[2], mzhu[3]);
			if(selfmzGong==whomzGong ) {				
				String t = null;
				if(love==LOVE) t = boyname+"��"+girlname+"ͬ��"+selfmzGong+"�������±س�֮��";
				else if(love==MARRY) t =  boyname+"��"+girlname+"ͬ��"+selfmzGong+"���������ȹ�֮��";
				if(!my.getJixiongge(selfmzGong)[0].equals("1")) t += "��ϧ���׸��������¶�ĥ������֮��";
				int[] dpjy = my.getDpjy(selfmzGong);
				if(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN) t+= "���ɣ�������ɢ����֮��";
				if(t!=null) hunyin[index++] = t;
			}
			
			if(my.isGongLiuhe(selfmzGong, whomzGong) && love==LOVE)
				hunyin[index++]= boyname+"��"+girlname+"�乬���֧���ϣ����±س�֮���ټ��Ż�����ǻ򼪸񣬰��ո�����ˣ��׸��򲻳ɣ�";	
			
			if(my.isSheng(selfmzGong, whomzGong) || my.isSheng(whomzGong, selfmzGong)) {
				if(love==LOVE) hunyin[index++]= boyname+"��"+girlname+"�乬������Ҳ�������ܳɣ��ټ��Ż�����ǻ򼪸񣬿����Ը���";
			}	else if(my.isBihe(selfmzGong, whomzGong)) { 
				if(love==LOVE) hunyin[index++]= boyname+"��"+girlname+"�乬��Ⱥͣ�Ҳ�������ܳɣ��ټ��Ż�����ǻ򼪸񣬿����Ը���";
			}	else if(my.isChongke(selfmzGong, whomzGong) || my.isChongke(whomzGong, selfmzGong)) { 
				if(love==LOVE) hunyin[index++]= boyname+"��"+girlname+"�乬���ˣ����л��²���֮��";			
			}
			
			if(love==LOVE && my.isChongke(heGong, whomzGong)) {
				hunyin[index++] ="������"+heGong+"����˶�������"+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"����"+whomzGong+"�������������ѳɣ�";
			}
			
			if(my.isChongke(selfmzGong, nianganGong)) {
				if(love==LOVE) hunyin[index++] =ysname+"��"+selfmzGong+"�����̫���������"+nianganGong+"����Ҳ��һ�����̸���ɣ�";
				if(love==MARRY) hunyin[index++] =ysname+"��"+selfmzGong+"�����̫���������"+nianganGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
			}else if(my.isChongke(selfmzGong, nianziGong)) {
				if(love==LOVE) hunyin[index++] =ysname+"��"+selfmzGong+"�����̫���֧����"+nianziGong+"����Ҳ��һ�����̸���ɣ�";
				if(love==MARRY) hunyin[index++] =ysname+"��"+selfmzGong+"�����̫���֧����"+nianziGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
			}
		}
		
	//////////////����ȫ�ǹ�������PUBLIC,���������޹ص�///////////////////////////////
	//////
	///////////////////////////////////////////////////////////////////
		if(all!=PUBLIC) return;
	//�����乬�Ƿ�Ͼ֡�ʱ���乬�Ƿ�Ͼ�
		String[] isShenheju = my.isshenHeju(heGong);  
		if(!isShenheju[0].equals("1")) {
			if(love==LOVE) 
				hunyin[index++]="������"+heGong+"��ʧ�֣�Ҳ�������ѳɣ�"+isShenheju[1];
			else if(love==MARRY)
				hunyin[index++]="������"+heGong+"��ʧ�֣�Ҳ������û�и��飬�����������ã����е�֧Ϊ"+my.getDznameOfGong(heGong)+"����ֹ"+my.getDzyueOfGong(heGong)+"���֣�"+isShenheju[1];
		}else{
			if(love==LOVE) 
				hunyin[index++]="������"+heGong+"���Ͼ֣�Ҳ�������׳ɣ�"+isShenheju[1];
			else if(love==MARRY)
				hunyin[index++]="������"+heGong+"���Ͼ֣�Ҳ����ͥ������"+isShenheju[1];
		}	
			
		String[] isshiHeju = my.isganHeju(SiZhu.sg, SiZhu.sz);
		if(!isshiHeju[0].equals("1"))
			hunyin[index++]="ʱ����"+shiGong+"��ʧ�֣�"+(love==LOVE?"Ҳ�������ѳɣ�":"����������˳��")+isshiHeju[1];
		else 
			hunyin[index++]="ʱ����"+shiGong+"���Ͼ֣�"+(love==LOVE?"Ҳ�������׳ɣ�":"������ͥ������")+isshiHeju[1];
		
		//�������Ϊ�ոɣ����ж�ʱ�չ���������Ⱥ�
		if(ystype==my.YSHENDAY && (my.isSheng(gong, shiGong) || my.isSheng(shiGong, gong))) {
			if(love==LOVE) hunyin[index++]="ʱ�ն���������Ҳ�������ܳɣ��ټ��Ż�����ǻ򼪸񣬿����Ը���";
			else if(love==MARRY) hunyin[index++]="ʱ�ն�������������벻��֮���ټ��Ż�����ǻ򼪸񣬿����Ը���";
		}else if(ystype==my.YSHENDAY && my.isBihe(gong, shiGong)) {
			if(love==LOVE) hunyin[index++]="ʱ�ն�����Ⱥͣ�Ҳ�������ܳɣ��ټ��Ż�����ǻ򼪸񣬿����Ը���";
			else if(love==MARRY) hunyin[index++]="ʱ�ն�����Ⱥͣ�����벻��֮���ټ��Ż�����ǻ򼪸񣬿����Ը���";
		}else if(ystype==my.YSHENDAY && (my.isChongke(gong, shiGong) || my.isChongke(shiGong, gong))) {
			if(love==LOVE) hunyin[index++]="ʱ�ն������ˣ�Ҳ�������ѳɣ�";
			else if(love==MARRY) hunyin[index++]="ʱ�ն������ˣ�����飬����ɢ֮��";
		}
		
		//���Ϲ��˶�����������Ů���������ҹ������Ͽ�̫�깬��
		if(love==LOVE && boy && my.isChongke(heGong, yiGong)) {
			hunyin[index++] ="������"+heGong+"���������"+heGong+"����Ҳ�������ѳɣ�";
		}
		if(love==LOVE && !boy && my.isChongke(heGong, gengGong)) {
			hunyin[index++] ="������"+heGong+"����˸�����"+gengGong+"����Ҳ�������ѳɣ�";
		}
		if(my.isChongke(heGong, nianganGong)) {
			if(love==LOVE) hunyin[index++] ="������"+heGong+"�����̫���������"+nianganGong+"����Ҳ�������ѳɣ�";
			if(love==MARRY) hunyin[index++] ="������"+heGong+"�����̫���������"+nianganGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
		}else if(my.isChongke(heGong, nianziGong)) {
			if(love==LOVE) hunyin[index++] ="������"+heGong+"�����̫���֧����"+nianziGong+"����Ҳ�������ѳɣ�";
			if(love==MARRY) hunyin[index++] ="������"+heGong+"�����̫���֧����"+nianziGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
		}
		if(my.isChongke(nianganGong, heGong)) {
			if(love==LOVE) hunyin[index++] ="̫�������"+nianganGong+"�������������"+heGong+"����Ҳ�������ѳɣ�";
			if(love==MARRY) hunyin[index++] ="̫�������"+nianganGong+"�������������"+heGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
		}else if(my.isChongke(nianziGong,heGong)) {
			if(love==LOVE) hunyin[index++] ="̫���֧��"+nianziGong+"�������������"+heGong+"����Ҳ�������ѳɣ�";
			if(love==MARRY) hunyin[index++] ="̫���֧��"+nianziGong+"�������������"+heGong+"���������ף����л�䣬̫���֧��ΪӦ�ڣ�";
		}
		
		//��������󹬻����Ϲ�������������������ϣ�
		if(boy && love==LOVE && my.isSheng(nianganGong, yiGong))
			hunyin[index++] ="�����"+nianganGong+"��������[��]����"+yiGong+"������ĸ������׮���£�";
		else if(!boy && love==LOVE && my.isSheng(nianganGong, gengGong))
			hunyin[index++] ="�����"+nianganGong+"��������[��]����"+gengGong+"������ĸ������׮���£�";
		if(love==LOVE && my.isSheng(nianganGong, heGong))
			hunyin[index++] ="�����"+nianganGong+"������������"+heGong+"������ĸ������׮���£�";		
		
		//������������Ⱥ͡�ͬ����
		boolean isboy = my.isSheng(gengGong,yiGong);
		boolean isgirl = my.isSheng(yiGong, gengGong);
		if(isboy || isgirl){
			String t = null;
			if(love==LOVE) {
				t = "������乬�����������ɳɣ��꼪�ż����Ǽ�������Ը���"; 
				if(!isboy) t+="Ů��׷���з���";
				else t+="�з�׷��Ů����";
				if(isboy && my.isKong(gengGong)) t+="���з��乬����������ɱ�У��������죬����׷��Է���";
				else if(isboy && !my.isganHeju(YiJing.GENG, 0)[0].equals("1")) t+="���з��乬����������ɱ�У��������죬����׷��Է���";
				
				if(isgirl && my.isKong(yiGong)) t+="��Ů���乬��������������������׷��Է���";
				else if(isgirl && !my.isganHeju(YiJing.YI, 0)[0].equals("1")) t+="��Ů���乬��������������������׷��Է���";
			}	else if(love==MARRY) {
				t = "������乬��������������������벻�ˣ��꼪�ż����Ǽ�������Ը���";
			}			
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENDI) t+="�ҹ���ŵأ������ã�";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENDI) t+="������ŵأ������ã�";
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENHE) t+="�ҹ������ϣ�����Ͷ��ϣ�";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENHE) t+="���������ϣ�����Ͷ��ϣ�";
			if(t!=null) hunyin[index++] = t; 
		}
		else if( my.isBihe(gengGong,yiGong)){  //�ȺͲ���������
			String t = null;
			if(love==LOVE) t = "������乬�Ⱥͣ������ɳɣ��꼪�š����񡢼��ǡ���������Ը���"; 
			else if(love==MARRY) t = "������乬�Ⱥͣ���������������벻�ˣ��꼪�š����񡢼��ǡ���������Ը���";
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENDI) t+="�ҹ���ŵأ������ã�";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENDI) t+="������ŵأ������ã�";
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENHE) t+="�ҹ������ϣ�����Ͷ��ϣ�";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENHE) t+="���������ϣ�����Ͷ��ϣ�";
			if(t!=null) hunyin[index++] = t; 
		}else if(gengGong == yiGong){
			String t = null;
			if(love==LOVE) t = "�����ͬ��"+gengGong+"�������±س�֮��";
			else if(love==MARRY) t="�����ͬ��"+gengGong+"���������ȶ�֮��";
			if(!my.getJixiongge(gengGong)[0].equals("1")) t += "��ϧ���׸��������¶�ĥ������֮��";
			int[] dpjy = my.getDpjy(gengGong);
			if(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN) t+= "���ɣ�������ɢ����֮��";
			if(t!=null) hunyin[index++] = t;
		}
			
		//��������
		boolean isyi = my.isChongke(yiGong, gengGong);
		boolean isgeng = my.isChongke(gengGong, yiGong);
		if(isyi || isgeng) {
			String t = null;
			if(love==LOVE) {
				t = "������乬���ˣ������ɢ�����������ѳɣ�";
				if(my.isSheng(gengGong, dingGong)) 
					t+="�ָ��붡�乬������˵���з������������� ��";
				if(my.isSheng(yiGong, bingGong)) 
					t+="��������乬������˵��Ů����������������";
				int[] yidpjy = my.getDpjy(yiGong);
				int[] gengdpjy = my.getDpjy(gengGong);
				if(yiGong==bingGong || yidpjy[0]==YiJing.BING || yidpjy[1]==YiJing.BING )
					t += "�ұ�ͬ���������Ը���";
				if(gengGong==dingGong || gengdpjy[0]==YiJing.DING || gengdpjy[1]==YiJing.DING )
					t += "����ͬ���������Ը���";
			} else if(love==MARRY) {
				t = "������乬���ˣ������ɢ���������޹�ϵ���������⣻";
				if(isyi && !my.getJixiongge(gengGong)[0].equals("1")) t += "�ַ��׸���鼸�ɶ��֣�";
				else if(isgeng && !my.getJixiongge(yiGong)[0].equals("1")) t += "�ַ��׸���鼸�ɶ��֣�";
				int yiws = SiZhu.TGSWSJ[YiJing.YI][SiZhu.yz];
				int gengws = SiZhu.TGSWSJ[YiJing.GENG][SiZhu.yz];				
				if(gengws>yiws) t+="�������"+SiZhu.TGSWSJNAME[gengws]+"�أ��������"+SiZhu.TGSWSJNAME[yiws]+"�أ��з���ǿӲ���������飻";
				else t+="�������"+SiZhu.TGSWSJNAME[gengws]+"�أ��������"+SiZhu.TGSWSJNAME[yiws]+"�أ�Ů����ǿӲ���������飻";
				if((yiGong==2 && gengGong==8) || (yiGong==8 && gengGong==2)) t+="�Һ�Ϊ2��8����壬�Ͼ�Ϊ�ͣ����з���ì�ܣ�";
				if(my.isSheng(gengGong, dingGong) || my.isSheng(dingGong, gengGong)) 
					t+="���붡�乬������˵���з������� ���������˶�����������飻";
				if(my.isSheng(yiGong, bingGong) || my.isSheng(bingGong, yiGong)) 
					t+="������乬������˵��Ů�����ӳ�ǽ ���������˶����ɷ�����飻";
				int[] yidpjy = my.getDpjy(yiGong);
				int[] gengdpjy = my.getDpjy(gengGong);
				if(yiGong==bingGong || yidpjy[0]==YiJing.BING || yidpjy[1]==YiJing.BING )
					t += "�ұ�ͬ���������Ը���";
				if(gengGong==dingGong || gengdpjy[0]==YiJing.DING || gengdpjy[1]==YiJing.DING )
					t += "����ͬ���������Ը���";
			}
			if(t!=null) hunyin[index++] = t;
		}
			
		//���֮��
		if(ystype==my.YSHENDAY && love==LOVE){
			hunyin[index++] = "����֮Ӧ��";
			boolean rinei = my.isNenpan(gong);
			boolean shinei = my.isNenpan(shiGong); 
			String t = null;
			if(rinei && shinei) t = "��ʱ�������̣����죻";
			else if(!rinei && !shinei) t = "��ʱ�������̣�ʱ�䲻��̫�죻";
			else t = "��ʱһ��һ�⣬������";				
			if(t!=null) hunyin[index++] = "    "+t;
		}
		if(love==LOVE) {
			String t = null;
			if(my.getSige(4)!=0) t="��ʱ�񣬸��죻";
			else if(my.getSige(3)!=0) t="���ո񣬸��죻";
			else if(my.getSige(2)!=0) t="���¸����ڳɻ�֮��";
			else if(my.getSige(1)!=0) t="��������ڳɻ�֮��";
			if(t!=null) hunyin[index++] = "    "+t;
			
			t = "";
			if(YiJing.DZLIUHE[SiZhu.sz][SiZhu.nz]!=0)
				t += "ʱ֧����֧�ϣ�";
			if(YiJing.DZLIUHE[SiZhu.rz][SiZhu.nz]!=0)
				t += "��֧����֧�ϣ�";
			if(YiJing.DZLIUHE[SiZhu.sz][SiZhu.yz]!=0)
				t += "ʱ֧����֧�ϣ�";
			if(YiJing.DZLIUHE[SiZhu.rz][SiZhu.yz]!=0)
				t += "��֧����֧�ϣ�";
			if(!"".equals(t)) hunyin[index++] = "    "+t+"�������ڳɻ�֮��";
			
			if(heGong == nianganGong) hunyin[index++] = "    ������̫��ͬ��"+heGong+"�������ڳɻ�֮��";
			if(my.isSheng(nianganGong, heGong)) hunyin[index++] = "    ���̫����"+nianganGong+"��������"+heGong+"����Ҳ�����ڳɻ�֮��";
			if(my.isSheng(nianziGong, heGong)) hunyin[index++] = "    ��֧̫����"+nianziGong+"��������"+heGong+"����Ҳ�����ڳɻ�֮��";
			if(my.isKong(heGong)) hunyin[index++] = "    ������"+heGong+"���������Գ�ʵ����ʵ֮��ΪӦ�ڣ�";
		}
		
		/////////////////////////////////////////////////////////////////////////////
		//����ר���ѻ��//////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////		
		if(love!=MARRY) return;	
		int shigangong = my.getTianGong(SiZhu.sg, SiZhu.sz);
		int xingruigong = my.getXingGong(QiMen.XINGRUI);
		if(shigangong==xingruigong) {
			hunyin[index++] = "ʱ��ΪС������С�����������ǣ���ʾ������ѧ����";
		}
		if(dingGong==my.getMenGong(QiMen.MENKAI)) {
			String t="��Ϊ���֤�飬�ٿ���Ϊ��Ժ���٣��ɶ�����׼��飻";
			if(dingGong == my.getShenGong(QiMen.SHENWU))
				t+= "�������䣬Ϊ˽��Э����飬��Ը����֪����";
			hunyin[index++] = t;
		}
		if(ystype==3)
			hunyin[index++] ="ʱ��Ϊ��Ů��"+shiGong+"������Ӧ��֧Ϊ"+my.getDznameOfGong(shiGong)+"��"+my.getGongshu(shiGong)+"���ɵ�֧��š��Ⱥ�����������֮���ۺ���˥����Ů������"+my.gettgWS(SiZhu.sg, SiZhu.sz)[1];		
				
		if(yiGong==3) hunyin[index++] = "��������3����Ϊ�ع���î��λ��������飻";
		//������乬���Ƿ����
		for(int _g_ : new int[]{yiGong,gengGong})
			if((daoqm.gInt[3][1][_g_]==QiMen.MENSI || daoqm.gInt[3][1][_g_]==QiMen.MENSHANG) &&
					(daoqm.gInt[2][1][_g_]==QiMen.XINGRUI || daoqm.gInt[2][1][_g_]==QiMen.XINGZHU) &&
					(my.getJixiongge(_g_)[0].equals("-1")) &&
					my.isYima(_g_))
				hunyin[index++] = (_g_==yiGong?"[��]":"[��]")+"��"+_g_+"�������ǣ���"+QiMen.bm1[daoqm.gInt[3][1][_g_]]+"�š���"+QiMen.jx1[daoqm.gInt[2][1][_g_]]+"�ǡ��׸�"+my.getJixiongge(_g_)[1]+"������飬���֮�ڿ����������֮����";
		
		//���������
		String t=null;
		int dingdpGong = my.getDiGong(YiJing.DING, 0);  //�����ڵ��̹�
		int bingdpGong = my.getDiGong(YiJing.BING, 0);
		int gengdpGong = my.getDiGong(YiJing.GENG, 0);
		int yidpGong = my.getDiGong(YiJing.YI, 0);  //�õ��������乬
		t = null;
		if(yidpGong==gengGong || yidpGong==gengdpGong ) {
			t = "���������Ϊ��ǰ�Ļ���״����ͬ��"+yidpGong+"���������������ֿ���������飻���֮�ڿ���֮��֧������֧��";
			if(my.isYima(yidpGong)) t += "�������ǣ�Ҳ����飻";
		}	else if(gengdpGong==yiGong) {
			t = "���������Ϊ��ǰ�Ļ���״����ͬ��"+gengdpGong+"���������������ֿ���������飻���֮�ڿ���֮��֧������֧��";
			if(my.isYima(gengdpGong)) t += "�������ǣ�Ҳ����飻";
		}
		if(t!=null) hunyin[index++] = t; 
		
		t=null;
		if(my.isChongke(yiGong, gengGong)) {
			t = "[��]������ż[��]�����ˣ���ʾҪ���ľ��Ľϴ�";
			if(daoqm.gInt[3][1][yiGong]==QiMen.MENSI) t+="�������ţ������������أ�";
			else if(daoqm.gInt[3][1][gengGong]==QiMen.MENSI) t+="�������ţ������������أ�";
		}
		if(t!=null) hunyin[index++] = t;
		
		//��(��)���˶�(��)
		if(my.isChongke(yiGong, dingGong)) hunyin[index++]="����"+yiGong+"���˶�����"+dingGong+"������ʾ���Ӳ�ͬ����飻";
		if(my.isChongke(gengGong, bingGong)) hunyin[index++]="����"+gengGong+"���˱�����"+bingGong+"������ʾ�ɷ�ͬ����飻";
		
		
		//�����ٶ�,�����ٱ�
		t = "";
		if(gengGong==dingdpGong) {
			t += "�����ٶ���Ҳ�������������";
			if(my.isChongke(gengGong, dingGong) || my.isChongke(dingGong, gengGong))
				t+="�������乬���ˣ������᳤�ã����˷�����ʱ�ض����֣�";
		}
		if(yiGong==bingdpGong) {
			t += "�����ٱ������Ӻ��ӳ�ǽ��";
			if(my.isChongke(yiGong, bingGong) || my.isChongke(bingGong, yiGong))
				t+="�������乬���ˣ������᳤�ã����˷�����ʱ�ض����֣�";
		}
		if(dingGong==bingdpGong) {
			t += "�����ٱ���Ҳ�������飻";
			if(my.isSheng(bingGong, dingGong)) t+="����������������������࣬��׷�����鸾���˺ܶࣻ";
		}else if(bingGong==dingdpGong) {
			t += "�����ٶ���Ҳ�������飻";
			if(my.isSheng(bingGong, dingGong)) t+="����������������������࣬��׷�����鸾���˺ܶࣻ";
		}
		if(!"".equals(t)) hunyin[index++]= t; 
		int renGong = my.getTianGong(YiJing.REN, 0);
		if(daoqm.gInt[1][1][dingGong]==QiMen.SHENHU && 
				daoqm.gInt[3][1][dingGong]==QiMen.MENSHANG && my.isKong(renGong)) {
			hunyin[index++]="���ٰ׻������ţ������֮��Ϊ�ɣ�����"+renGong+"���������������ɷ�Ϊ�Ѹ���";
		}
		//ʱ���ȶ�
		t = null;
		int shigan = my.getTiangan(SiZhu.sg, SiZhu.sz);
		if(my.isSheng(shiGong, dingGong)) {
			t = "ʱ����"+shiGong+"������������"+dingGong+"����Ҳ����������";
			if(shigan==YiJing.REN) t+="��ʱ��Ϊ�ɣ����ɺϸ�������֮�ϣ���������Ϣ�������ˣ�";
		}else if(my.isBihe(shiGong, dingGong)) {
			t = "ʱ����"+shiGong+"���붡������"+dingGong+"���Ⱥͣ�Ҳ����������";
			if(shigan==YiJing.REN) t+="��ʱ��Ϊ�ɣ����ɺϸ�������֮�ϣ���������Ϣ�������ˣ�";
		}
		if(t!=null) hunyin[index++]=t;
		
		//Ů��ǰ���ַ�
		t="";
		int[] dingdpjy = my.getDpjy(dingGong);  //�õ������ĵ�������
		int[] bingdpjy = my.getDpjy(bingGong);  		
		if(!boy && (my.isSheng(gengGong, dingGong) || my.isSheng(dingGong, gengGong))) {
			t+="Ů������ģ���Ϊǰ�򣬶�Ϊǰ�����֮�ޣ������乬��������ʾǰ����̸һ����Ҫ�ٻ��ˣ�";
			if(my.isKong(dingGong)) t+="�������������ʵ֮��Ϊ���ڣ�";
			if(daoqm.gInt[1][1][dingGong]==QiMen.SHENWU && (dingdpjy[0]==YiJing.REN || dingdpjy[1]==YiJing.REN))
				t+="�����������ϳ����䣬��ʾ��Ů�ѽ�Ϲ���������飻";
			if(my.isSheng(bingGong, gong) || my.isSheng(gong, bingGong))
				t+="��Ϊ�ַ���������������Ϊ��һ����׷������ʱ��Ϊ���е�֧�����·ݣ�";
			if(bingdpjy[0]==YiJing.XIN || bingdpjy[1]==YiJing.XIN)
				t+="��������Ϊ�ϣ���ʾ�������ѽ���飻";
		}
		if(!"".equals(t)) hunyin[index++]=t;
		
	//�е�ǰ������
		t="";
		if(boy && (my.isSheng(yiGong, bingGong) || my.isSheng(bingGong, yiGong))) {
			t+="��������ģ���Ϊǰ�ޣ���Ϊǰ�޺���֮�򣬶����乬��������ʾǰ����̸һ����Ҫ�ٻ��ˣ�";
			if(my.isKong(bingGong)) t+="�������������ʵ֮��Ϊ���ڣ�";
			if(daoqm.gInt[1][1][bingGong]==QiMen.SHENWU && (bingdpjy[0]==YiJing.XIN || bingdpjy[1]==YiJing.XIN))
				t+="�����������ϳ����䣬��ʾ�����ѽ�Ϲ���������飻";
			if(my.isSheng(dingGong, gong) || my.isSheng(gong, dingGong))
				t+="��Ϊ���ޣ�������������Ϊ��׷��һŮ�ӣ�����ʱ��Ϊ���е�֧�����·ݣ�";
			if(dingdpjy[0]==YiJing.REN || dingdpjy[1]==YiJing.REN)
				t+="��������Ϊ�ϣ���ʾ�������ѽ���飻";
		}
		if(!"".equals(t)) hunyin[index++]=t;
		
		//������Ǯ��
		int wuTGong = my.getTianGong(YiJing.WUG, 0);
		int wuDGong = my.getDiGong(YiJing.WUG, 0);
		if(my.gettgWS(YiJing.WUG, 0)[0].equals("1")) {
			hunyin[index++]="��������"+wuTGong+"�������Ǯ������Ŀ�����乬���࣬��ȡ��ֵ��������ҲΪ��Ϊ���޷ֵõĲ����������"+wuDGong+"��Ϊ�Լ��ֵõĲ��";
		}else {
			hunyin[index++]="��������"+wuTGong+"�������Ǯ������Ŀ�����乬��������ȡСֵ��������ҲΪ��Ϊ���޷ֵõĲ����������"+wuDGong+"��Ϊ�Լ��ֵõĲ��";
		}
		if(ystype==my.YSHENDAY && shiGong==gong){
			hunyin[index++]="ʱ��ͬ��������С���и��Լ���";
		}
		int jing1Gong = my.getMenGong(QiMen.MENJING1);
		hunyin[index++]="���Ҫ���˾���־�����"+jing1Gong+"����Ӧ��"+QiMen.JIUGONGFXIANG[jing1Gong]+"����ʦ��";
		
	//���֮��
		if(ystype==my.YSHENDAY ){
			hunyin[index++] = "���֮�ڣ�";
			boolean rinei = my.isNenpan(gong);
			boolean shinei = my.isNenpan(shiGong); 
			t = null;
			if(rinei && shinei) t = "��ʱ�������̣����죻";
			else if(!rinei && !shinei) t = "��ʱ�������̣�ʱ�䲻��̫�죻";
			else t = "��ʱһ��һ�⣬������";				
			if(t!=null) hunyin[index++] = "    "+t;
		}
		t = null;
		if(my.getSige(4)!=0) t="��ʱ�񣬸��죻";
		else if(my.getSige(3)!=0) t="���ո񣬸��죻";
		else if(my.getSige(2)!=0) t="���¸����ڳɻ�֮��";
		else if(my.getSige(1)!=0) t="��������ڳɻ�֮��";
		if(t!=null) hunyin[index++] = "    "+t;
		
		t = "";
		if(YiJing.DZCHONG[SiZhu.sz][SiZhu.nz]!=0)
			t += "ʱ֧����֧�壬";
		if(YiJing.DZCHONG[SiZhu.rz][SiZhu.nz]!=0)
			t += "��֧����֧�壬";
		if(YiJing.DZCHONG[SiZhu.sz][SiZhu.yz]!=0)
			t += "ʱ֧����֧�壬";
		if(YiJing.DZCHONG[SiZhu.rz][SiZhu.yz]!=0)
			t += "��֧����֧�壬";
		if(!"".equals(t)) hunyin[index++] = "    "+t+"�����������֮��";
		
		if(my.isChongke(nianganGong, heGong)) hunyin[index++] = "    ���̫����"+nianganGong+"���������"+heGong+"����Ҳ���������֮��";
		if(my.isChongke(nianziGong, heGong)) hunyin[index++] = "    ��֧̫����"+nianziGong+"���������"+heGong+"����Ҳ���������֮��";
		if(my.isYima(dingGong)) hunyin[index++] = "    �������ǣ�����Զ�������֮��Ϊ���֮�ڣ�Ϊ����Ϊ���գ�";
		int zsGong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);  //�õ�ֵʹ�乬
		hunyin[index++] = "    ֵʹ����"+zsGong+"����������֮�ɿɶ�Ӧ�ڣ������乬֮����������������";
	}
	
	//�ɰ����ж������Ը�
	private void getXingeOfShen(int gan, int zi) {
		int gong = my.getTianGong(gan, zi);   //�õ���һ��
		
		//hunyin[index++]="----���������Ը�----";
		String[] heju = my.isganHeju(gan, zi); //�����乬�Ƿ�Ͼ�
		if(heju[0].equals("1")) hunyin[index++] = "�����乬�Ͼ֣���ʾ���úܲ������������ã�����������"+heju[1];
		else hunyin[index++] = "�����乬ʧ�֣���ʾ���ࡢ��������������һ�㣻"+heju[1];
		
		if(daoqm.gInt[1][1][gong]==QiMen.SHENFU) hunyin[index++]="�乬��ֵ��������Ƚ�����ֱ�����쵼���ܣ��ؼ�ʱ���ܴ����Σ������Ƚ����ڰ������ˣ�"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENSHE) hunyin[index++]="�乬�����ߣ��������Ӧ�����������������Բ��������թ��ʵ�������������ѣ�"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENYIN) hunyin[index++]="�乬��̫�������кܺõĲ߻�ͷ�ԣ����ڳ�ı���ߣ���������Ƚ�ϸ���ȫ�棻"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENHE) hunyin[index++]="�乬�����ϣ���������������֯��������Ե�ȽϺã��Ը���ͣ��������Ⱥ���ܺ����ദ��"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENHU) hunyin[index++]="�乬�ٰ׻�������Ƣ����ǿ�����ԣ����ң��Ǹ����͵�ֱƢ�������������ڳ�����"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENWU) hunyin[index++]="�乬�����䣬����ϻۣ��������������������������������λ������Ϊ��թ��С��������Ϣ���֣�"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENDI) hunyin[index++]="�乬�پŵأ��������ڲأ����������������ģ�";
		else hunyin[index++]="�乬�پ��죬־��������׳־���ƣ��������ء��������ֵ���Ϣ������"; 		
	}
	
	//�ɰ��ſ������Ը�
	private void getXingeOfMen(int gan, int zi) {
		int gong = my.getTianGong(gan, zi);   //�õ���һ��		 
		
		//hunyin[index++]="----���ſ������Ը�----";
		if(my.isTpJixing(gan, zi)) hunyin[index++]="�乬�����ǻ��̣��Ա��ң�";
		if(daoqm.gInt[3][1][gong]==QiMen.MENXIU) hunyin[index++]="�乬�����ţ�����Ƚ���ɢ������������Ƚ����е��ˣ������ڴ������"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG) hunyin[index++]="�乬�����ţ��Ƚ��о���ͷ�ԣ�������ʶǿ�У������澳�д���һ����·����"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENSHANG) hunyin[index++]="�乬�����ţ���ǿ��ʤ�����ڳ���ͷ����ɱ�򶷣��������µ���Ϣ������"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENDU) hunyin[index++]="�乬�ٶ��ţ������Ը�ƫ��ͳ�ͱ��أ������ţ������˽������������̲����µ��ˣ��Ǵ����������Լ��������ʵ��ˣ�"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENJING3) hunyin[index++]="�乬�پ��ţ����ڱ����ڹ�ͨ��Ҳ������ҥ���ڣ�������"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENSI) hunyin[index++]="�乬�����ţ����������������ڹ�ͨ���������ţ��Ը���죻"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENJING1) hunyin[index++]="�乬�پ��ţ�������һ������ֱ�ڿ죬���Կ��������һ���ǵ�С���£��龪���죬�л��Ƿǣ�";
		else hunyin[index++]="�乬���乬�ٿ��ţ��ȽϿ��ţ��ܰ��ݣ��������飬�������Ը���˲����ױ������ܣ�������ʲô���������Ǿ�˵ʲô��Ů�������ţ��ټ��ң�����Ǵ��±�¶���ԸУ�"; 		
	}
	//�ɾ��ǿ�����
	private void getZhangxiang(int gan, int zi) {
		String _t_ = null;
		int gong = my.getTianGong(gan, zi);   //�õ���һ��
		String[] ganWS = my.gettgWS(gan, zi);  //�ж�����Ƿ�����
		String[] xingWS = my.getxingWS(gong);  //�жϸù����Ƿ���˥
		String[] shenJX = my.getshenJX(gong);  //�õ��ù���ļ���
		
		//hunyin[index++]="----���ǿ�����----";
		if(gong==3 || gong==4){ 
			if(ganWS[0].equals("1")) hunyin[index++]="��"+(gong==3?"����":"����")+"�����࣬��ĸ�����";
			else hunyin[index++]="����"+(gong==3?"����":"����")+"������������Ĳ��ߣ�";
		}
		if(daoqm.gInt[2][1][gong]==QiMen.XINGPENG) {
			_t_ ="�������ǣ���ʿ����Ƚϴ�Ӳ��Ũ�ڣ������������ԣ�Ů�ԱȽϰ�С��Ƥ���ڣ������������ţ�";
			if(xingWS[0].equals("1")) _t_+="�乬�����࣬��ķʴ󣬽Ų����ȣ�����Ƚ��ף�"+xingWS[1];
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGREN) {
			_t_="�������ǣ���İ�С���ĳ����������׽ӽ��ڽ̣�";
			if(xingWS[0].equals("1")) _t_+="�乬�����࣬��ķ��֣�"+xingWS[1]; 
			if(shenJX[0].equals("-1")) _t_+=shenJX[1]+"�������ձ����ֲ��������ˡ�ȳ�ȵȣ�";		
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGCHONG) {
			_t_="������ǣ�Ů���������������ƫ�ݣ�";
			if(xingWS[0].equals("1")) _t_+="�乬�����࣬Ů�������������������ΰ����"+xingWS[1]; 
			else _t_+="�乬������Ů�Ծ���³Ѹ����Բ��ʽ�����ɩ�������������磻"+xingWS[1];
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGFU) {
			_t_="���츨�ǣ�Ϊ�˺������ܹ�Ӯ�ýϺõ���Ե��ü��Ŀ�㣻";
			if(xingWS[0].equals("1")) _t_+="�乬�����࣬��Ů��Ϊ��Ů��������Ϊ˧�磻"+xingWS[1]; 
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGQIN) hunyin[index++]="�������ǣ��������㣬���Ȳ����츨����������Ʒ����Ϊ��������ֱ��";
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGYING) hunyin[index++]="����Ӣ�ǣ��沿����ߣ����Ժ���ϡ�裬Ů��ͷ��ȱ��Ӫ�����׿ݻƣ�";
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGRUI) {
			_t_="�������ǣ���ıȽ��֣���ͷ���ߣ�Ƥ��ƫ��ɫ�����������ػ��ȽϹ㣬�������ͣ�";
			if(xingWS[0].equals("1")) _t_+="�乬�����࣬�����Ƚ����ԣ�"+xingWS[1];
			else _t_+="�乬�������������Ǻ����ԣ�"+xingWS[1];
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGZHU) {
			_t_="�������ǣ���ĸߴ󣬸��ң�ԭ���ԱȽ�ǿ�����׸��˲����׽ӽ��ĸо���";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENHU && xingWS[0].equals("1"))
				_t_+="�乬�����࣬���ٰ׻���Ƣ������֮�ˣ�";
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGXIN) hunyin[index++]="�������ǣ��沿���������������ԣ�������������¸ң�";
	}
	
	//�ɰ��Ÿ�ֿ�ְҵ
	private void getZhiye(int gan, int zi) {
		String _t_ = null;
		int gong = my.getTianGong(gan, zi);   //�õ���һ��
		int[] tpjy = my.getTpjy(gong); 
		int[] dpjy = my.getDpjy(gong);
		int yearGong = my.getTianGong(SiZhu.ng, SiZhu.nz);
		int kaiGong = my.getMenGong(QiMen.MENKAI); //�õ��������ڵĹ�
		int duGong = my.getMenGong(QiMen.MENDU); //�õ����Ź�
		int huGong = my.getShenGong(QiMen.SHENHU); //�õ��׻���
		int pengGong = my.getXingGong(QiMen.XINGPENG); //���������ڵĹ�
		int shengGong = my.getMenGong(QiMen.MENSHANG);  //�õ��������ڵĹ�
		int sheGong = my.getShenGong(QiMen.SHENSHE);  //�õ��������ڵĹ�
		int fuGong = my.getShenGong(QiMen.SHENFU);  //�õ�ֵ�����ڵĹ�
		boolean isKong = my.isKong(gong, my.SHIKONGWANG);  //�ж������Ƿ�ΪѮ��
		boolean isNei = my.isNenpan(gong); //�ж��Ƿ�Ϊ����
		String[] heju = my.isganHeju(gan, zi); //�����乬�Ƿ�Ͼ�
		String[] jxge = my.getJixiongge(gong);  //�õ����ļ��׸�
		
		//hunyin[index++]="----���ſ�ְҵ----";
		if(my.isChongke(gong, kaiGong)) hunyin[index++]="������"+gong+"���˿�������"+kaiGong+"������ʾ����������"; 
		if(my.isSheng(gong, shengGong)) hunyin[index++]="������"+gong+"������������"+shengGong+"������ʾ��Ǯ�з���"; 
		if(gong==sheGong) {
			_t_="�����乬�������ߣ���ʾ�����ڽ̣�";
			if(isKong) _t_ += "�����������ʾ����̵��Ĳ�ʵ��";
			hunyin[index++] = _t_;
		}
		if(my.isChongke(gong, yearGong)) hunyin[index++]="������"+gong+"�����������"+yearGong+"������ʾ�븸ĸ��ϵ���ã�";
		if(gong==fuGong) {
			if(gong==1 || gong==3 || gong==7 || gong==9)
				_t_ = "������"+gong+"��������ֵ����Ϊ����ְ���쵼��˾�����ˣ�";
			else if(gong==6)
				_t_ = "������"+gong+"��������ֵ����Ϊĳ��λ�쵼�����ˣ�";
			else
				_t_ = "������"+gong+"��������ֵ��������Ϊ��ְ���쵼��˾�����ˣ�";
			String[] zhifuhj = my.isganHeju(gong);
			if(zhifuhj[0].equals("1")) _t_ +="���кϾ֣�ӦΪ��Ȩ����֮�ˣ�"+zhifuhj[1];
			else _t_ +="����ʧ�֣��ٽײ���ӦΪ�Ƴ�����֮�ࣻ"+zhifuhj[1];
			if(_t_!=null) hunyin[index++] = _t_;
		}
		if(gong==duGong && gong==huGong) {
			 _t_ = null;
			if(tpjy[0]==YiJing.REN || tpjy[1]==YiJing.REN || dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN)
				_t_ = "������"+gong+"�������ٰ׻��������ַ���Ϊ���ޣ�������Ѻ����";
			else if(tpjy[0]==YiJing.XIN || tpjy[1]==YiJing.XIN || dpjy[0]==YiJing.XIN || dpjy[1]==YiJing.XIN)
				_t_ = "������"+gong+"�������ٰ׻��������ַ���Ϊ���ˣ�������Ѻ����";
			else if(tpjy[0]==YiJing.GUI || tpjy[1]==YiJing.GUI || dpjy[0]==YiJing.GUI || dpjy[1]==YiJing.GUI)
				_t_ = "������"+gong+"�������ٰ׻��������ַ��Ϊ������������Ѻ����";
			if( _t_!=null && gong==pengGong) _t_+="���ұ����˿";
			if(_t_!=null) hunyin[index++] = _t_;
		}		
		if(isNei) hunyin[index++] = "������"+gong+"����Ϊ���̣������ص㲻Զ��";
		else hunyin[index++] = "������"+gong+"����Ϊ���̣�����ع�����";
		
		if(daoqm.gInt[3][1][gong]==QiMen.MENKAI){
			_t_ = "�乬�ٿ��ţ�Ϊ������������ϰ壬�����й�ְ�Ĺ����ߣ�";
			if(isKong) _t_ += "�����������ʾĿǰ��û�й�����";
			else if(heju.equals("1")) _t_+="�ֺϾ֣�Ŀǰ�����Ƚ�˳�ģ�";
			else _t_+="��ʧ�֣�Ŀǰ������˳�ģ�";
			hunyin[index++] = _t_;
		}
		if(daoqm.gInt[3][1][gong]==QiMen.MENXIU){
			_t_ = "�乬�����ţ�Ϊˮ�������Ρ���µ���ʹ������ˣ�";
			if(gong==6 || gong==7) _t_ += "��"+gong+"��������Ϊ������Ա������ɡ���ƣ�";
			hunyin[index++] = _t_;
		}
		
		String[] xingWS = my.getxingWS(gong);
		if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG){
			_t_ = "�乬�����ţ�Ϊ����������Ӫ��ó��֮�ˣ�";
			if(heju.equals("1")) {
				_t_+="�ֺϾ֣���ʾ���������ʻ����Ϻã�";				
				if(xingWS[0].equals("1")) _t_+="�ֲ��Ǵ����أ����������ۺ�";
				if(jxge[0].equals("1")) _t_+= "�ٷ꼪��Ϊ���̴�֣�"+jxge[1]+xingWS[1];
			}	else {
				_t_+="��ʧ�֣���ʾ���벻�ȶ����������ѣ�";
				if(!xingWS[0].equals("1")) _t_+="�ַ���ǲ������������Ƽ��ѣ�"+xingWS[1];	
			}
			hunyin[index++] = _t_;			
			
			if(QiMen.men_gong[QiMen.MENSHENG][gong]==QiMen.zphym1) {
				if((tpjy[0]==YiJing.WUG ||tpjy[1]==YiJing.WUG ) && (dpjy[0]==YiJing.GENG || dpjy[0]==YiJing.GENG)) {
					if(daoqm.gInt[1][1][gong]==QiMen.SHENWU ){
						hunyin[index++] = "�乬�����ţ��������ƣ����������䣬����������+���ɹ��񣬱���ʧ���ƣ�";
					}
					if(daoqm.gInt[2][1][gong]==QiMen.XINGPENG)  {
						hunyin[index++] = "�乬�����ţ��������ƣ������������ǣ�����������+���ɹ��񣬱���ʧ���ƣ�";
					}
				}
			}
					
		}
		
		if(daoqm.gInt[3][1][gong]==QiMen.MENSHANG) hunyin[index++] = "�乬�����ţ�Ϊ�˶�Ա�����ˡ����������ԡ���е�豸����������������������ʹ���йأ�";
		if(daoqm.gInt[3][1][gong]==QiMen.MENDU) hunyin[index++] = "�乬�ٶ��ţ�Ϊ�����뼼���йأ����ڲ��ӹ������ˣ�";
		if(daoqm.gInt[3][1][gong]==QiMen.MENJING3) hunyin[index++] = "�乬�پ��ţ�ΪӰ�ӡ���桢ͼ�顢���ӡ��Ļ����������������ְҵ��";
		if(daoqm.gInt[3][1][gong]==QiMen.MENSI) hunyin[index++] = "�乬�����ţ�Ϊ��ͽ���ڽ̹������ڽ������������ְҵ��ز������������ס�ҽԺ���йأ�";
		if(daoqm.gInt[3][1][gong]==QiMen.MENJING1) hunyin[index++] = "�乬�پ��ţ�Ϊ��ʦ����ʦ�����֡����������졢����Ա�����Ρ�Ӫ����Ա�����ֳ���Ա�ȣ�";		
	}
	
}




