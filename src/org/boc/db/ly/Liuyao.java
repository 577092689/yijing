package org.boc.db.ly;

import org.boc.db.YiJing;

public class Liuyao {
	public static final String HH="<BR>&nbsp;&nbsp;&nbsp;&nbsp;";
	public static final String XMLHH="<BR>";
	
	public static boolean go = true;		//��سΪtrue������Ϊ÷��
	public static boolean ALL = true;  //ȫ����ʾ����
	public static boolean JXGE = false;
	public static boolean WSXQ = false;
	public static boolean MA = true;	
	public static boolean TIP = true;
	public static boolean NOW = true;
	public static boolean HEAD = true;  //�Ƿ���ʾ����ͷ��������Ϣ
	public static final int LEFTMIN = 1;  //��ߵ�������С���
	public static final int LEFTMAX = 175;  //��ߵ���������ʵĿ��
	public static int LEFT = LEFTMIN;  //��ߵ����Ŀ�ȣ�Ĭ��Ϊ������ߵģ�����Ϊ1
	
	public static final String FUHAOYI="\\|";   //������
	public static final String FUHAODOT=",";   //,����
	public static final String DUN="��";   //���ٺ�
	public static final String FILL1 = "��";   //ռ�����ַ�
	public static final String FILL2 = " ";   //ռһ���ַ�������΢��
	
	public static boolean TOOL = true;  //�Ƿ���ʾ��������Ĭ����ʾ
	public static boolean INPUT = false;  //�Ƿ���ʾ�߼���壬Ĭ����ʾ
	public static boolean IO = true;  //��ʾ���û����Զ������ʾ��Ϣ���������桢��ֶ��ơ�Ĭ��Ϊ����
		
	public final static int YAO = 1;			//ҡ��
	public final static int SJDZ = 2;			//ʱ��+��֧
	public final static int SJTG = 3;			//ʱ��+���
	public final static int SXD = 4;      //����+����+��س
	public final static int Ming = 5;			//����+��س
	public final static int SJHZ = 6;
	public final static int SJSZ = 7;
	public final static int SJFM = 8;
	public final static int SHUZI = 9;
	public final static int HANZI = 10;
	public final static int SUIJI = 11;
	public final static String[] QGFS = {"","ҡ��","ʱ��+��֧","ʱ��+���",
		"����+����+��س","����+��س","ʱ��+����","ʱ��+����","����","����","����","���"};
	
  //������
  public static final int ZSMY = 1;
  public static final int JDYQ = 2;
  public static final int SWBD = 3;
  public static final int LAHY = 4;
  public static final int QXKS = 5;
  public static final int GZJY = 6;
  public static final int JYQC = 7;
  public static final int CXCG = 8;
  public static final int RTJB = 9;
  public static final int TSQX = 10;
  public static final int DLFS = 11;
  public static final int XRZS = 12;
  public static final int HYFW = 13;
  public static final int TYJS = 14;
  public static final String[] rules = {"  ", "��������","��������","ʧ�ﱻ��","��������",
  	"��ѧ����","������ҵ","��Ӫ���","���г���","���弲��","��ʱ����","�����ˮ","������ʧ","���з���","��������"};
  
//����ѡ��
  public static final String[] yongshen = {"  ", YiJing.LIUQINNAME[1],YiJing.LIUQINNAME[2],
  	YiJing.LIUQINNAME[3],YiJing.LIUQINNAME[4],YiJing.LIUQINNAME[5]};
}
