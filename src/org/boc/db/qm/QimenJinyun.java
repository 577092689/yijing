package org.boc.db.qm;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;


public class QimenJinyun extends QimenBase{
	private String[] jibing ; //�������˹����е��������
	int index = 0; //��������ΪdescMY����

	public QimenJinyun(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
  
	public String getNow(StringBuffer str,String mzText, int ysNum,boolean boy, int iszf) {
		init(mzText, ysNum, boy, 2000);	//2000Ϊ���鳤��	
		this.iszf = iszf!=2;
		w(p.NOKG+"һ�����ֶϣ�");
		getNow1();
		w(p.NEWLINE);
		
		w(p.NOKG+"��������״̬��");
		getNow2();
		w(p.NEWLINE);
		
		w(p.NOKG+"�������ף�");
		getNow3();
		w(p.NEWLINE);
				
		w(p.NOKG+"�ġ�������");
		getNow4();
		w(p.NEWLINE);
		
		w(p.NOKG+"�塢������");
		getNow5();
		w(p.NEWLINE);
		
		w(p.NOKG+"����������");
		getNow6();
		w(p.NEWLINE);
		
		w(p.NOKG+"�ߡ����ˣ�");
		getNow7();
		w(p.NEWLINE);
		
		return p.format(str, my);
	}
	
	/**
	 * һ�����ֶ�
	 */
	public void getNow1() {
		w(isMenfan,"���ŷ��ʣ����Ŵ������£����·��ʣ������ڲ�˳����");
		w(isMenfu,"���ŷ��ʣ����Ŵ������£����������ڲ�˳�������¿�����");
		w(isXingfan,"���Ƿ��ʣ����Ǵ�����ʱ�����������ڷ�������˳������");
		w(isXingfu,"���Ƿ��ʣ����Ǵ�����ʱ�����������ڲ�˳�������¿�����");
		
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==1,"�������ʱ�񣬽��ڷ�չƽ�ȡ�����˳����");
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==2,"����岻��ʱ�����½��ף������ڲ�˳������");
	}
	public void getNow2() {
		String[] riheju = p.isganHeju(ritpgong);
		if(riheju[0].equals("1")) {
			w("�ոɺϾ֣�����������ҵ˳������������ʱ�ڣ�"+riheju[1]);
		}else{
			w("�ո�ʧ�֣��������˽��������ϲ"+riheju[1]);
		}
		w("�����ո���"+ridpgong+"�����ϼһ�����λ��"+QiMen.JIUGONGFXIANG[ridpgong]);
		
		if(ritpgong==gengdpgong || ridpgong==gengtpgong)
			w("�ո��ٸ���С�˶࣬�򷴶Ե��˶࣬ì�ܵ�࣬�����ɶࡣ");
		if(mtpgong!=0 && (mtpgong==gengdpgong || mdpgong==gengtpgong))
			w("�����ٸ���С�˶࣬�򷴶Ե��˶࣬ì�ܵ�࣬�����ɶࡣ");
		if(boy && (ritpgong==dingtpgong || ritpgong==dingdpgong))
			w("�ո��ٶ����ͱ���������֮�⻹��С�ۣ�������Ҳ�С��ر����ո����ٶ��������׼�����ھ������ˡ������ٶ��ţ����䣬˵������Ҳ�����ϡ�");
		if(boy && (mtpgong==dingtpgong || mtpgong==dingdpgong))
			w("�����ٶ����ͱ���������֮�⻹��С�ۣ�������Ҳ�С�Ҳ�����ڻ�û�г��֣��������ͻ���֡������ٶ��ţ����䣬��˵������Ҳ�����ϡ�");
		if(!boy && (ritpgong==bingtpgong || ritpgong==bingdpgong))
			w("�ո��ٱ����ͱ������ɷ�֮�⻹�����ˣ�������Ҳ�С��ر����ո����ٱ��������׼�����ھ������ˡ������ٶ��ţ����䣬˵������Ҳ�����ϡ�");
		if(!boy && (mtpgong==bingtpgong || mtpgong==bingdpgong))
			w("�����ٱ����ͱ������ɷ�֮�⻹�����ˣ�������Ҳ�С�Ҳ�����ڻ�û�г��֣��������ͻ���֡������ٶ��ţ����䣬��˵������Ҳ�����ϡ�");
		
		String bwei = !p.isNenpan(wutpgong) && 
			(wutpgong==1 || wutpgong==8 || wutpgong==3 || wutpgong==4) ? "���":"�Ҳ�";
		w("����"+wutpgong+"����Ǯ������"+bwei+QiMen.JIUGONGBUWEI[wutpgong]+"�з��ţ�");
		
		String bw1834 = !p.isNenpan(3) ? "���":"�Ҳ�" ;		 //�ж�1834Ϊ���������Ҳ�
		String bw9276 = !p.isNenpan(7) ? "���":"�Ҳ�" ;		 //�ж�9276Ϊ���������Ҳ�
		if(rentpgong==3 || guitpgong==3) w("�ɻ����3��������"+bw1834+"�������룻");
		if(rentpgong==7 || guitpgong==7) w("�ɻ����7��������"+bw9276+"�������룻");
		if(rentpgong==5 || guitpgong==5) w("�ɻ������5������������룻");
		if(rentpgong==8 || guitpgong==8) w("�ɻ����8��������"+bw1834+"�Ȳ����룻");
		if(rentpgong==6 || guitpgong==6) w("�ɻ����6��������"+bw9276+"�Ȳ����룻");
		if(rentpgong==1 || guitpgong==1) w("�ɻ����1���������������β����룻");
		if(rentpgong==9 || guitpgong==9) w("�ɻ����9��������ͷ���۲����룻");
		
		if(zhifugong==ritpgong) w("�ϳ�ֵ��������֯���������ڵ����������������ܷ����Ŷ����á�");
		else if(shenshegong==ritpgong) w("�ϳ����ߣ�������ơ�");
		else if(shenyingong==ritpgong) w("�ϳ�̫�����˲߻����⣬�׷�С�ˡ�");
		else if(shenhegong==ritpgong) w("�ϳ����ϣ����ڴ��˽������̸�С�");
		else if(shenhugong==ritpgong) w("�ϳ˰׻���Ƣ��ֱˬ���꣬����������������¡�");
		else if(shenwugong==ritpgong) w("�ϳ����䣬�˸�Ͷ�������������£�͵˰©˰�����ɴ�ƭ���������������鲻�������䡣");
		else if(shentiangong==ritpgong) w("�ϳ˾��죬�Ը��������������������˥��ø���Զ����;���ϡ�");
		else if(shendigong==ritpgong) w("�ϳ˾ŵأ����أ�������ţ�Ǽ⡣");
		
		if(ritpgong==xiumengong) w("�����ż��ţ�����");
		else if(ritpgong==shengmengong) w("�����ţ�����");
		else if(ritpgong==kaimengong) w("�����ţ�����");
		else if(ritpgong==dumengong) w("�����ţ���ƽ��");
		else if(ritpgong==jing3mengong) w("�����ţ�С�ף�");
		else if(ritpgong==shangmengong) w("�����ţ��ף�");
		else if(ritpgong==simengong) w("�����ţ��������ﲻ�죬���˱����Ĺ��ɷ��������ԭ��ռ������֮����Ҫ��Т����");
		else if(ritpgong==jing1mengong) w("�����ţ��ף�");
		
		if(ritpgong==xingzhugong) w("�������ǣ��ϲ����಻�����أ�");
		else if(ritpgong==xingruigong) w("�������ǣ����ڽύ���ѣ�����ѧϰ����̰�ƣ�����಻�����أ�");
		else if(ritpgong==xingpenggong) w("����������ʱ�ã���ʱ�����������Ӽ��ƴ�ƣ���֮����಻�����أ�");
		else if(ritpgong==xingxingong) w("�������ǣ�Ϊ���쵼���ܣ�");
		else if(ritpgong==xingfugong) w("���츨�ǣ�Ϊ����ֱ�����Ļ���");
		else if(ritpgong==xingyinggong) w("����Ӣ�ǣ���ƽ��");
		else if(ritpgong==xingchonggong) w("������ǣ��μ���");
		
		if(ritpgong==bingtpgong && bingtpgong==guidpgong) w("�չ��ٱ��ӹ��Ǿƹ�");
		if(ritpgong==dingtpgong && dingtpgong==guidpgong) w("�չ��ٹ�Ӷ���������Ϊ��");
		if(ritpgong==guitpgong && guitpgong==jidpgong) w("�չ��ٹ�����Ǿƹ�");
		if(ritpgong==dingtpgong && dingtpgong==jidpgong) w("�չ��ٶ��Ӽ����̹�");
		if(!boy && ritpgong==dingtpgong && dingtpgong==1) w("�ո��ڿ������ٶ����������ˡ�");
		if(ritpgong==zhifugong && zhifugong==yitpgong) w("�ո���ֵ�����ң��и߹��ϣ����");
		if(!boy && ritpgong==zhifugong && zhifugong==wutpgong) w("�ո���ֵ�����죬�з��Σ�");
		if(!boy && ritpgong==zhifugong && zhifugong==jitpgong) w("�ո���ֵ�����ѣ���һ���߹�����֣�");
		if(ritpgong==shenshegong && ritpgong==wutpgong) w("�չ������ߣ�����ô�硣");
		
		if(ritpgong==dingtpgong) w("�ٶ�����������������");
		else if(ritpgong==bingtpgong) {			
			if(ritpgong==xingpenggong) w("�ٱ��������Ȩ�����Լ�˵���㣬��Ƣ�����ꡣ");
			else w("�ٱ�����Ȩ�����Լ�˵���㡣");
		}
		else if(ritpgong==gengtpgong) w("�ٸ��������������ס�");
		else if(ritpgong==p.getTianGong(YiJing.JI, 0)) w("�ټ������ޣ���˽���������ֶβ�������");
		else if(ritpgong==p.getTianGong(YiJing.XIN, 0)) w("���������벻��ȷ�������鲻��ȷ��");
		else if(ritpgong==p.getTianGong(YiJing.REN, 0) ||
				ritpgong==p.getTianGong(YiJing.GUI, 0)) w("���ɡ�����Է�չ�����ס�");
		if(ritpgong==p.getDiGong(YiJing.REN, 0)) w("�ӵ����ɣ����߶������С�");
		
		Integer[] rigeju = p.getJixiongge2(ritpgong,iszf);
		for(int rge : rigeju) {
			if(rge==16) w("�ո���㣸�����û��˳�򣬰��»��ң�������");			
		}
		
		String t = "";
		if(p.isJixing(ritpgong)) 	t += "�ո������乬��֧���̣�";
		if(p.isTDXing(ritpgong))	t += "�չ����̸�����̸����̣�";
		int[] gzi = p.getDiziOfGong(ritpgong);  //�������ص�֧
		if(YiJing.DZXING[gzi[0]][SiZhu.rz]==1 || YiJing.DZXING[gzi[1]][SiZhu.rz]==1) t += "�չ���֧��������֧���̣�";
		if(!t.equals("")) w(t+"�ո��������ǻ��̣����ܡ�ѹ����ƣ�͡���ʧ�����ˡ�����������������ְ��");
		
		if(p.isTGanMu(SiZhu.rg,SiZhu.rz)) w("�ո�������Ĺ������Ϊ�����������޲ߣ��������ܷ��ӻ���������֮�¡�");
		if(p.isDGanMu(SiZhu.rg,SiZhu.rz)) w("�ո��ڵ���"+ridpgong+"����Ĺ����ԥ��������־���졣");
		if(p.isTianKeDi(ridpgong)) w("�ո��ڵ���"+ridpgong+"�������̸ɿˣ����ڲ�˳");
		if(p.isKong(ritpgong)) w("�ոɷ�գ�Ϊ��ǰ�ݼٻ���У�");
		
		if(zhifugong==ritpgong) {
			if(ritpgong==8 || ritpgong==2 ||ritpgong==4 ||ritpgong==6) w("�ո���ֵ��������ά��ӦΪ��ְ������Ǭ���п������⣻");
			else w("�ո���ֵ��������������ӦΪ��ְ��");
		}
			
		if(p.isBihe(shitpgong, ritpgong)) {
			if(riheju[0].equals("1")) w("ʱ�ձȺͣ��չ��Ͼ֣��������ƽϺã�");
			else w("ʱ�ձȺͣ��չ��м����ף�ƽ�������Щ��˳��");
		}
		
		if(mzhu.length>1 && mzhu[0]!=0) {
			int mzgong = p.getTianGong(mzhu[0], mzhu[1]);
			int nzgong = QiMen.ziOfGua[SiZhu.nz];
			if(p.isChongke(mzgong, nzgong) || p.isChongke(nzgong,mzgong)) w("������"+mzgong+"����������֧̫��"+nzgong+"�����϶���˳��");
		}
	}
	public void getNow3() {
		int fathergong,mothergong;  //�õ���ĸ���ڵĹ�
		if(p.isYangGan(SiZhu.ng)) {
			fathergong = p.getTianGong(SiZhu.ng, SiZhu.nz);
			int mothergan = p.getGanHe(SiZhu.ng);			
			mothergong = mothergan==YiJing.JIA ? zhifugong : p.getTianGong(mothergan, 0);
			w("��ɣ�"+YiJing.TIANGANNAME[SiZhu.ng]+"��Ϊ��Ϊ������"+fathergong+
					"�������֮�ɣ�"+YiJing.TIANGANNAME[mothergan]+"��Ϊ��ĸ����"+mothergong+"����");
		}else{
			mothergong = p.getTianGong(SiZhu.ng, SiZhu.nz);
			int fathergan = p.getGanHe(SiZhu.ng);			
			fathergong = fathergan==YiJing.JIA ? zhifugong : p.getTianGong(fathergan, 0);
			w("��ɣ�"+YiJing.TIANGANNAME[SiZhu.ng]+"��Ϊ��Ϊĸ����"+mothergong+
					"�������֮�ɣ�"+YiJing.TIANGANNAME[fathergan]+"��Ϊ�丸����"+fathergong+"����");
		}				
		
		Integer[] ge = p.getJixiongge2(6,iszf);  //�õ�������ʮ�ɿ�Ӧ
		for(int g : ge) {
			if(g==67 && xingzhugong==6) w("��Ǭ����Ϊ����+�ɻ������ޡ��׸����������ƾ����������ˣ�");
		}
		ge = p.getJixiongge2(2,iszf);  //�õ�ĸ����ʮ�ɿ�Ӧ
		for(int g : ge) {
			if(g==67 && xingzhugong==2) w("ĸ������Ϊ����+�ɻ������ޡ��׸����������ƾ���ĸ�����ˣ�");
		}
		
		ge = p.getJixiongge2(yuetpgong,iszf);  //�õ��¸��乬
		for(int g : ge) {
			if(g==80) w("�¸�Ϊ������"+yuetpgong+"��������+�ң��з��˲Ƶģ�");
		}
		if(fathergong==yuetpgong) w("�¸��븸��ͬ��"+fathergong+"���������������ֵܽ���ס��һ��");
		if(mothergong==yuetpgong) w("�¸���ĸ��ͬ��"+mothergong+"��������ĸ�����ֵܽ���ס��һ��");
		
		int peiou = p.getHegan(SiZhu.rg);
		int peiougong = peiou==YiJing.JIA ? zhifugong : p.getTianGong(peiou, 0);
		w("���ո����֮��ɣ�"+YiJing.TIANGANNAME[peiou]+"��Ϊ��ż����"+peiougong+"����");
		if(p.isSheng(peiougong, zhifugong)) w("��ż����ֵ������"+zhifugong+"����Ϊ�쵼���������ģ�");
	}
	public void getNow4() {
		w("��ο����弲����");
	}
	public void getNow5() {
		w("��ο�����������");
	}
	public void getNow6() {
		w("��ο�������ҵ��");
	}
	public void getNow7() {
		w("��ο���Ӫ��ƣ�");
	}
}