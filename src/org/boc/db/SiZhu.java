package org.boc.db;

public class SiZhu {
  /**
   * ������֧
   * �������·�
   * ������ʱ��
   */
  public static int ng = 0;
  public static int nz = 0;
  public static int yg = 0;
  public static int yz = 0;
  public static int rg = 0;
  public static int rz = 0;
  public static int sg = 0;
  public static int sz = 0;
  public static int tyg = 0;
  public static int tyz = 0;
  public static int mgg = 0;
  public static int mgz = 0;

  public final static int LNBEGIN=20;  //��7�����������С����
  public final static int LNEND=50;  //��7����������������

  public static final int[] yueByNian = {0,3,5,7,9,1,3,5,7,9,1};
  public static final int[] shiByRi =   {0,1,3,5,7,9,1,3,5,7,9};

  public static String yinli = "";
  public static String yangli = "";
  public static String sex = "";
  public static boolean ISMAN ;
  public static int NAYININT = 0;
  public static String SPLIT = "";
  public static final String SZNAME = "������";
  public static final String DYNAME = "���ˣ�";
  public static final String XYNAME = "С�ˣ�";
  public static final String LLNAME = "���꣺";
  //���˵���ɵ�֧��9Ϊ8�����ˣ�3Ϊ1���2��֧��
  public static int[][] DAYUN = new int[9][3];
  public static int[][] XIAOYUN = new int[LNEND][3];
  public static int[][][] QIZHU = new int[LNEND][8][3];
  public static int QIYUNSUI = 0;   //������������������ȥ
  public static final String NUM2 = "xh";

  //��ɻ���Ҫ��:
  //���ȣ���֧���ϣ��кϻ���ֻ�ϲ���֮����ɺϻ���������ո�Ϊ���������¸ɻ�ʱ��Ϊ�ϣ�����֧��Ϊ�ϻ�֮ͬһ���з��ۺϻ�������뼺���������ո�Ϊ�ף��¸ɻ�ʱ��Ϊ��;�ո�Ϊ�����¸ɻ�ʱ��Ϊ�ף�������֧Ϊ�����δ�������ˣ���ϻ�֮��������ͬ�����ۻ��������������Ҳ�ɺϻ�:һ�����������ϣ���֧Ϊ�ϻ�֮�����и��û�����������ҺϽ���֧Ϊ���
  //�����ո����¸ɻ��ո���ʱ�ɺϣ���֧����������������������֧Ϊ���Ͼֻ������Ҳ���ۻ�������������ºϽ���֧��������ϣ�������ʱ֧�ϳ������ӳ��������磬�仯�ɹ�����֧�ϻ����Ҫ��֧�����������������͸����֧�ϻ�֮���з����ۻ�����î����ϻ����͸������򶡻����֧�ϻ�֮��ͬΪһ���ж��ۻ�������֮�ϲ������Ժ϶������ۡ���Σ�����ϣ��غϣ��ϻ�֮���Ժϻ���������ۣ�ԭ����ʧȴ������;�϶�������Ϊ�������У���������������֧�������̳塣
  //�������Ҫ��:
  //�������:�ٸ�֮�������������ڸ���;ͬ��֮������������������;���߼����������ߵ��档������:�������Ϊ�ף��������Ϊ��;������ˣ��ٸ����󣬸�����Σ�Զ������;����ͬ�����֮�������������;������˾������ܿ����˴�;����֮�ˣ��и�֮�ɻ������Կ��ۡ��������ɿ˸����и���������й�������������������������������۲��Կ��ۡ������кϣ���ȥ���������ۡ������˸��𣬵�����������������ˮ��ˮ�˻𣬱���˲��˸��𣬹ʲ��Կ��ۡ��ոɱ����ɿˣ����п����ɵ��ƣ��������ۡ�����ոɱ����¸ɿˣ����¸���������Ʒ����ʲ��Ա��������ɱ����ۡ�
  public static final String[][] GANHEZHU= new String[11][11];
  static
  {
    GANHEZHU[0][0]="�ɺ��ߣ������֮�ס�";
    GANHEZHU[1][6]="�׼��ϻ�����Ϊ����֮�ϡ��������ؼ������Ž��塣";
    //���������������ִ���ɱ����ȱ�����壬��ƶ�ˣ��Ըա����ոɺϼ�������ľ���޲ư��𡣶�����»�ɿա����𣬹��Ը��š�����������󸻡���ˮ��ƽ�����������𣬼�ͽ�ıڡ�����»��ǧ�ӡ����ոɺϼף��������������衣��ľ���Լ����������𣬼���󸻡����𣬹º����ݡ���ˮ����ְǨ�١�
    GANHEZHU[2][7]="�Ҹ��ϻ���������֮�ϡ�����汸���������塣";
    //����ƫ�ٻ��������������ߣ�����ִ���������ʹ��塣���ոɺϸ�����������ѡ���ˮ���ٻ��������ƴ���֮Ц�ա����������ý�����������ݷ�˪����ľ������ӯ�֡����ոɺ��ң������𣬰��𡣱�����塣���������������ꡣ��ˮ����԰Ư������ˮ����»���������������ɾ޸�������ˮ�����������ꡣ
    GANHEZHU[3][8]="�����ϻ�ˮ��������֮�ϡ��Ǳ����ϣ��������㡣";
    //������ɱ���������ߣ����Կ����飬��Ƨ�Ѻ͡�Ů����֧�壬�ϻ�֮ˮ�����Ը����������ոɺ���������������������ľ���پ�Ǩ�١���ˮ�����������Ժա���ˮ���������ܡ����ոɺϱ������������𣬹�����
    GANHEZHU[4][9]="���ɺϻ�ľ��������֮�ϡ��ĵ��ʴȣ��������١�";
    //����������ˮ����йľ����Ϊ����֮�ϡ����������ߣ���ɫ�Ƽҡ����ոɺ��ɣ����������갲�ݡ�����һ�����Σ�����˫ȫ�������������ǲ����ˮ�����ļ�į����ľ���أ���»�޳ɡ��������������Ī����ϲ���ٳ���»��˫����ϲ�����ϣ���»��˫�������ոɺ϶�������ľ�������������𣬹�����ׯ������Ӣ�ۺ��ܡ���ˮ�����ྭ�̡���������ӡ������������Ư�����ǡ���������޳ɡ���ľ�����겻����
    GANHEZHU[5][10]="���ϻ���������֮�ϡ���ò���㣬���鷦�塣�ж౧����֮�ģ�Ů���޿���";
    //���ոɺϹ����ľ�������Դ��ˮ�����Է�¡��������Ѱ��»�������׼���ͨ��������������������ı��Ϊ׾�����ոɺ��죬��������һ����ɰܡ��׼����������ġ����𣬲ֿ��ʡ����������ʵ����ľ���پ�½�١���ˮ����»��ȫ�����𣬲�Ե��ʧ����������;��š�
  }

  //Ϊh*100+m����ʱ���Ϊ1�����Ϊ59�֣���ʱ���Ϊ2�����259��
  public static final int[] HOURNUM = {0,60,300,500,700,900,1100,1300,1500,1700,1900,2100,2300};
  //��ʱ������12ʱ������0��Ϊ���1����ʱ��1��Ϊ���2����ʱ
  public static final int[] RHOURNUM = {1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12,0};
  //��֧����
  public static final int[] GANYINYANG = {0,1,0,1,0,1,0,1,0,1,0};
  public static final int[] ZIYINYANG =  {0,1,0,1,0,1,0,1,0,1,0,1,0};
  //��ʮ��������
  public static final String[][] NAYIN = new String[11][13];
  static
  {
  NAYIN[1] = new String[]{null,"���н�","","��Ϫˮ","","���ƻ�","","ɰ�н�","","Ȫ��ˮ","","ɽͷ��",""};
  NAYIN[2] = new String[]{null,"","���н�","","��Ϫˮ","","���ƻ�","","ɰ�н�","","Ȫ��ˮ","","ɽͷ��"};
  NAYIN[3] = new String[]{null,"����ˮ","","¯�л�","","ɳ����","","���ˮ","","ɽ�»�","","������",""};
  NAYIN[4] = new String[]{null,"","����ˮ","","¯�л�","","ɳ����","","���ˮ","","ɽ�»�","","������"};
  NAYIN[5] = new String[]{null,"������","","��ͷ��","","����ľ","","���ϻ�","","������","","ƽ��ľ",""};
  NAYIN[6] = new String[]{null,"","������","","��ͷ��","","����ľ","","���ϻ�","","������","","ƽ��ľ"};
  NAYIN[7] = new String[]{null,"������","","�ɰ�ľ","","������","","·����","","ʯ��ľ","","���˽�",""};
  NAYIN[8] = new String[]{null,"","������","","�ɰ�ľ","","������","","·����","","ʯ��ľ","","���˽�"};
  NAYIN[9] = new String[]{null,"ɣ��ľ","","������","","����ˮ","","����ľ","","�����","","��ˮ",""};
  NAYIN[10] =new String[]{null,"","ɣ��ľ","","������","","����ˮ","","����ľ","","�����","","��ˮ"};
}

  //����֮�ԡ�ʢ��˥����֮�¡�������
  public static final String[][] wxxing = new String[6][4];
  static
  {
    wxxing[1] = new String[]{"�����壬���Ըգ������ң���ζ������ɫ�ס�",
        "��ʢ�����˹�����ƣ��淽�׾���ü������彡���塣Ϊ�˸�����ϣ�������壬��֪���ܡ�̫����������ı��̰�����ʡ�",
        "��˥�����������С��Ϊ�˿̱��ڶ���ϲ����ɱ������̰����",
        "�˽�ϲ�������ɴ��¾��˲Ļ�������߲��ϣ���Ӳ�����ϣ��������������ܹܣ���������ͨ�����ڣ����̣����ӣ��������������ľ����е�ȷ���ľ�Ӫ�͹�����",
        "��:����󳦻�Ϊ�อ����������ܼ���������ϵͳ���������˥�����˻��󳦣��Σ��꣬��̵���Σ�Ƥ�����̴��������ܵȷ���ļ�����",
        "",
        ""};
    wxxing[2] = new String[] {"ľ���ʣ�����ֱ������ͣ���ζ�ᣬ��ɫ�ࡣ",
        "ľʢ�����˳��÷��������������޳�������ϸ�壬�ڼⷢ������ɫ��ס�Ϊ���в�������֮�ģ��������֮�⣬��߿�����������α��",
        "ľ˥�����˸����ݳ���ͷ��ϡ�٣��Ը�ƫ�������ʲ��ʡ�ľ������������ü�۲��������ᣬ������Ϊ�˱������ġ�",
        "��ľ��ϲ�������ɴ���ľ�ģ�ľ�����Ҿߣ�װ�꣬ľ��Ʒ��ֽҵ����ֲ�������������磬������Ʒ�����ϣ�ֲ������ʳƷ�Ⱦ�Ӫ����ҵ��",
        "ľ:���뵨��Ϊ�อ���������Ǻ���֫���������˥�����˻��Σ�����ͷ��������֫���ؽڣ��������ۣ��񾭵ȷ���ļ�����",
        "",
        ""};
    wxxing[3] = new String[]{"ˮ���ǣ����Դϣ������ƣ���ζ�̣���ɫ�ڡ�",
        "ˮ������������вɣ�������ͣ�Ϊ����˼���ǣ����Ƕ�ı��ѧʶ���ˡ�̫�����˵�Ƿǣ�Ʈ��̰����",
        "ˮ˥�����˶�С�������޳�����С���ԣ����·�����",
        "��ˮ��ϲ�������ɴ��º��������²�ȼҺ�壬��ˮ�����࣬ˮ����ˮ������أ��䶳�����̣�ϴ�࣬ɨ������ˮ���ۿڣ�Ӿ�أ���������ԡ�أ���ʳ��������Ʈ�Σ������������������ԣ��ױ仯����ˮ���ʣ��������ʣ�������ʣ�������ҵ��Ǩ�ã��ؼ����ݣ��˶������Σ����У���ߣ�ħ�������ߣ���̽�����磬������ߣ��������ߣ�ҽ��ҵ��ҩ�ﾭӪ��ҽ������ʿ��ռ���ȷ���ľ�Ӫ�͹�����",
        "ˮ:������׻�Ϊ�อ���������������ϵͳ���������˥�����˻��������ף��֣��㣬ͷ���Σ����������������������ӹ��������ȷ���ļ�����",
        "",
        ""};
    wxxing[4] = new String[]{"���������Լ������鹧����ζ�࣬��ɫ�ࡣ",
        "��ʢ������ͷС�ų����ϼ�������ŨüС����������˸��Ϊ��ǫ�͹��������Ӽ��ꡣ",
        "��˥����������ݼ��㣬������������թ�ʶ���������ʼ���ա�",
        "�˻�ϲ�Ϸ����ɴ��·Ź⣬��������ѧ�����ȣ���ȼ�����࣬�ƾ��࣬����ʳ��ʳƷ��������ױƷ������װ��Ʒ�����գ���ѧ���ľߣ��Ļ�ѧ�������ˣ����ң�д����׫�ģ���Ա��У�������飬���棬��������ȷ���ľ�Ӫ����ҵ��",
        "��:������С����Ϊ�อ�������Ѫ��������ѭ��ϵͳ���������˥�����˻�С�������࣬�磬ѪҺ����Ѫ�����������ݣ��������ಿ�ȷ���ļ�����",
        "",
        ""};
    wxxing[5] = new String[]{"�����ţ������أ��������ζ�ʣ���ɫ�ơ�",
        "��ʢ������Բ�����ǣ�ü��ľ�㣬�ڲ����ء�Ϊ����Т���ϣ���������Ա��У��бع�������̫����ͷ�Խ�������׾����������þ���",
        "��˥��������ɫ���ͣ����ǵͣ�Ϊ�˺ݶ����壬�������ã���ͨ����",
        "������ϲ����֮�أ����ء��ɴ����������ز���ũ�壬��������ƥ����װ����֯��ʯ�ϣ�ʯ�ң�ɽ�أ�ˮ�࣬�������������������£���ɡ�����̣���ˮ��Ʒ�����̣��Ŷ����м��ˣ���ʦ��������������ƣ����ʣ�ɥҵ����Ĺ��Ĺ�ع���ɮ��ȷ���ľ�Ӫ����ҵ��",
        "��:Ƣ��θ��Ϊ�อ�������������������ϵͳ���������˥�����˻�Ƣ��θ���ߣ������أ��Σ��ǵȷ���ļ�����",
        "",
        ""};
  }

  /**
   * �����
   */
  public static final int[][] wangbanhe = new int[13][13];
  static{
    wangbanhe[12][4]=wangbanhe[4][8]=YiJing.MU;
    wangbanhe[3][7]=wangbanhe[7][11]=YiJing.MU;
    wangbanhe[6][10]=wangbanhe[10][2]=YiJing.MU;
    wangbanhe[9][1]=wangbanhe[1][5]=YiJing.MU;
  }

  /**
   * �õ�ʮ��
   * ���ʮ���ƶ�
   * �õ���֧ѭ��
   */
  public static final String[] SHISHEN = new String[]{"��","��","��","ʳ","��","��","��","ɱ","��","��","ӡ"};
  public static final String[] SHISHEN2 = new String[]{"��","�ȼ�","�ٲ�","ʳ��","�˹�","ƫ��","����","ƫ��","����","ƫӡ","��ӡ"};
  public static final int[][] TGSHISHEN = new int[11][11];
  static
  {
    TGSHISHEN[1] = new int[]{0, 1,2, 3,4, 5,6, 7,8, 9,10};
    TGSHISHEN[2] = new int[]{0, 2,1, 4,3, 6,5, 8,7, 10,9};
    TGSHISHEN[3] = new int[]{0, 9,10,1,2, 3,4, 5,6, 7,8 };
    TGSHISHEN[4] = new int[]{0, 10,9,2,1, 4,3, 6,5, 8,7 };
    TGSHISHEN[5] = new int[]{0, 7,8, 9,10,1,2, 3,4, 5,6 };
    TGSHISHEN[6] = new int[]{0, 8,7, 10,9,2,1, 4,3, 6,5 };
    TGSHISHEN[7] = new int[]{0, 5,6, 7,8, 9,10, 1,2, 3,4};
    TGSHISHEN[8] = new int[]{0, 6,5, 8,7, 10,9, 2,1, 4,3};
    TGSHISHEN[9] = new int[]{0, 3,4, 5,6, 7,8, 9,10, 1,2};
    TGSHISHEN[10] = new int[]{0,4,3, 6,5, 8,7, 10,9, 2,1};
  }
  public static final int[][] DZXUNCANG = new int[13][3];
  static
  {
    DZXUNCANG[1] = new int[]{10};
    DZXUNCANG[2] = new int[]{6,10,8};
    DZXUNCANG[3] = new int[]{1,3,5};
    DZXUNCANG[4] = new int[]{2};
    DZXUNCANG[5] = new int[]{5,2,10};
    DZXUNCANG[6] = new int[]{3,5,7};
    DZXUNCANG[7] = new int[]{4,6};
    DZXUNCANG[8] = new int[]{6,4,2};
    DZXUNCANG[9] = new int[]{7,9,5};
    DZXUNCANG[10] = new int[]{8};
    DZXUNCANG[11] = new int[]{5,8,4};
    DZXUNCANG[12] = new int[]{9,1};
  }
  /**
   * ʮ�������������
   */
  public static final String[] TGSWSJNAME = new String[]{null,"��","��","��","»","��","˥","��","��","Ĺ","��","̥","��"};
  public static final int[][] TGSWSJ = new int[11][13];
  static
  {
    TGSWSJ[1] = new int[]{0,2,3,4,5,6,7,8,9,10,11,12,1};
    TGSWSJ[2] = new int[]{0,7,6,5,4,3,2,1,12,11,10,9,8};
    TGSWSJ[3] = new int[]{0,11,12,1,2,3,4,5,6,7,8,9,10};
    TGSWSJ[4] = new int[]{0,10,9,8,7,6,5,4,3,2,1,12,11};
    TGSWSJ[5] = new int[]{0,11,12,1,2,3,4,5,6,7,8,9,10};
    TGSWSJ[6] = new int[]{0,10,9,8,7,6,5,4,3,2,1,12,11};
    TGSWSJ[7] = new int[]{0,8,9,10,11,12,1,2,3,4,5,6,7};
    TGSWSJ[8] = new int[]{0,1,12,11,10,9,8,7,6,5,4,3,2};
    TGSWSJ[9] = new int[]{0,5,6,7,8,9,10,11,12,1,2,3,4};
    TGSWSJ[10] = new int[]{0,4,3,2,1,12,11,10,9,8,7,6,5};
  }

  //���ҹ��ˣ��ոɼ���֧
  public static final boolean[][] TIANYI = new boolean[11][13];
  static
  {
    TIANYI[1][2]=true;TIANYI[1][8]=true;TIANYI[5][2]=true;TIANYI[5][8]=true;
    TIANYI[2][1]=true;TIANYI[2][9]=true;TIANYI[6][1]=true;TIANYI[6][9]=true;
    TIANYI[3][10]=true;TIANYI[3][12]=true;TIANYI[4][10]=true;TIANYI[4][12]=true;
    TIANYI[7][3]=true;TIANYI[7][7]=true;TIANYI[8][3]=true;TIANYI[8][7]=true;
    TIANYI[9][4]=true;TIANYI[9][6]=true;TIANYI[10][4]=true;TIANYI[10][6]=true;
  }
  //̫�����ˣ��ոɼ���֧
  public static final boolean[][] TAIJI = new boolean[11][13];
  static
  {
    TAIJI[1][1]=true;  TAIJI[1][7]=true;  TAIJI[2][1]=true;  TAIJI[2][7]=true;
    TAIJI[3][10]=true; TAIJI[3][4]=true;  TAIJI[4][10]=true; TAIJI[4][4]=true;
    TAIJI[5][5]=true;  TAIJI[5][8]=true;  TAIJI[5][11]=true; TAIJI[5][2]=true;
    TAIJI[6][5]=true;  TAIJI[6][8]=true;  TAIJI[6][11]=true; TAIJI[6][2]=true;
    TAIJI[7][3]=true;  TAIJI[7][12]=true;  TAIJI[8][3]=true;  TAIJI[8][12]=true;
    TAIJI[9][6]=true;  TAIJI[9][9]=true;  TAIJI[10][9]=true; TAIJI[10][6]=true;
  }
  //��¹���,���Ϊ1����֧Ϊ2�����¼���ɻ��֧
  public static final boolean[][][] TIANDE = new boolean[3][13][13];
  static
  {
    TIANDE[1][3][4] =true;  TIANDE[2][4][9] =true;  TIANDE[1][5][9] =true;  TIANDE[1][6][8] =true;
    TIANDE[2][7][12] =true; TIANDE[1][8][1] =true;  TIANDE[1][9][10] =true; TIANDE[2][10][3] =true;
    TIANDE[1][11][3] =true; TIANDE[1][12][2] =true; TIANDE[2][1][6] =true;  TIANDE[1][2][7] =true;
  }
  //�µ¹��ˣ����¼����
  public static final boolean[][] YUEDE = new boolean[13][11];
  static
  {
    YUEDE[3][3] =true;  YUEDE[7][3] =true;  YUEDE[11][3] =true;
    YUEDE[9][9] =true;  YUEDE[1][9] =true;  YUEDE[5][9] =true;
    YUEDE[4][1] =true;  YUEDE[8][1] =true;  YUEDE[12][1] =true;
    YUEDE[6][7] =true;  YUEDE[2][7] =true;  YUEDE[10][7] =true;
  }
  //���������ɻ��֧
  public static final boolean[][][] SHANJI = new boolean[13][13][13];
  static
  {
    SHANJI[2][3][4] = true;
    SHANJI[4][6][7] = true;
  }
  //���ǹ��ˣ���ɻ��ոɼ���֧
  public static final boolean[][] FUXING = new boolean[11][13];
  static
  {
    FUXING[1][3]=true;  FUXING[3][3]=true;  FUXING[1][1]=true;  FUXING[3][1]=true;
    FUXING[5][9]=true;  FUXING[6][8]=true;  FUXING[4][12]=true; FUXING[2][4]=true;
    FUXING[2][2]=true;  FUXING[10][2]=true; FUXING[10][4]=true; FUXING[7][7]=true;
    FUXING[8][6]=true;  FUXING[9][5]=true;
  }
  //�Ĳ����ˣ�����ɻ��ոɼ���֧
  public static final boolean[][] WENCANG = new boolean[11][13];
  static
  {
    FUXING[1][6]=true;  FUXING[1][7]=true;  FUXING[2][6]=true;  FUXING[2][7]=true;
    FUXING[3][9]=true;  FUXING[5][9]=true;  FUXING[4][10]=true; FUXING[6][10]=true;
    FUXING[7][12]=true; FUXING[8][1]=true;  FUXING[9][3]=true;  FUXING[10][4]=true;
  }
  //����ˣ�����
  public static final boolean[][] KUIGANG = new boolean[11][13];
  static
  {
    KUIGANG[9][5]=true;  KUIGANG[7][11]=true;  KUIGANG[7][5]=true;  KUIGANG[5][11]=true;
  }
  //��ӡ���ˣ�����ɻ��ոɼ���֧
  public static final boolean[][] GUOYIN = new boolean[11][13];
  static
  {
    GUOYIN[1][11]=true;  GUOYIN[2][12]=true;  GUOYIN[3][2]=true;  GUOYIN[4][3]=true;
    GUOYIN[5][2]=true;  GUOYIN[6][3]=true;  GUOYIN[7][5]=true;
    GUOYIN[8][6]=true;  GUOYIN[9][8]=true;  GUOYIN[10][9]=true;
  }
  //ѧ�ã�����(��1ľ2ˮ3��4��5)����
  public static final boolean[][][] XUETANG = new boolean[6][11][13];
  static
  {
    XUETANG[1][8][6]=true;  XUETANG[2][6][12]=true;  XUETANG[3][1][9]=true;
    XUETANG[4][3][3]=true;  XUETANG[5][5][9]=true;
  }
  //�ʹݣ�����ɻ��ո�Ϊ��������
  public static final boolean[][][] CIGUAN = new boolean[11][11][13];
  static
  {
    CIGUAN[1][7][3]=true;  CIGUAN[2][8][4]=true;  CIGUAN[3][2][6]=true;
    CIGUAN[4][5][7]=true;  CIGUAN[5][4][6]=true;  CIGUAN[6][7][7]=true;
    CIGUAN[7][9][9]=true;  CIGUAN[8][10][10]=true;  CIGUAN[9][10][12]=true;
    CIGUAN[10][9][11]=true;
  }
  //������ˣ�������Ϊ����������������з�����1Ϊ�¹��ˡ���Ϊ�����
  public static final boolean[][][] DEXIU = new boolean[13][3][11];
  static
  {
    DEXIU[3][1][3]=true;  DEXIU[3][1][4]=true;  DEXIU[3][2][5]=true;  DEXIU[3][2][10]=true;
    DEXIU[7][1][3]=true;  DEXIU[7][1][4]=true;  DEXIU[7][2][5]=true;  DEXIU[7][2][10]=true;
    DEXIU[11][1][3]=true; DEXIU[11][1][4]=true;  DEXIU[11][2][5]=true;  DEXIU[11][2][10]=true;

    DEXIU[9][1][9]=true; DEXIU[9][1][10]=true; DEXIU[9][1][5]=true; DEXIU[9][1][6]=true;
    DEXIU[9][2][3]=true; DEXIU[9][2][8]=true; DEXIU[9][2][1]=true; DEXIU[9][2][6]=true;
    DEXIU[1][1][9]=true; DEXIU[1][1][10]=true; DEXIU[1][1][5]=true; DEXIU[1][1][6]=true;
    DEXIU[1][2][3]=true; DEXIU[1][2][8]=true; DEXIU[1][2][1]=true; DEXIU[1][2][6]=true;
    DEXIU[5][1][9]=true; DEXIU[5][1][10]=true; DEXIU[5][1][5]=true; DEXIU[5][1][6]=true;
    DEXIU[5][2][3]=true; DEXIU[5][2][8]=true; DEXIU[5][2][1]=true; DEXIU[5][2][6]=true;

    DEXIU[6][1][7]=true;  DEXIU[6][1][8]=true;  DEXIU[6][2][2]=true;  DEXIU[6][2][7]=true;
    DEXIU[10][1][7]=true;  DEXIU[10][1][8]=true;  DEXIU[10][2][2]=true;  DEXIU[10][2][7]=true;
    DEXIU[2][1][7]=true;  DEXIU[2][1][8]=true;  DEXIU[2][2][2]=true;  DEXIU[2][2][7]=true;

    DEXIU[12][1][1]=true;  DEXIU[12][1][2]=true;  DEXIU[12][2][4]=true;  DEXIU[12][2][9]=true;
    DEXIU[4][1][1]=true;  DEXIU[4][1][2]=true;  DEXIU[4][2][4]=true;  DEXIU[4][2][9]=true;
    DEXIU[8][1][1]=true;  DEXIU[8][1][2]=true;  DEXIU[8][2][4]=true;  DEXIU[8][2][9]=true;
  }
  //���ǣ�����֧����֧Ϊ�����������кε�֧��֮
  public static final boolean[][] HUAGAI = new boolean[13][13];
  static
  {
    HUAGAI[9][5]=true; HUAGAI[1][5]=true; HUAGAI[5][5]=true;
    HUAGAI[3][11]=true; HUAGAI[7][11]=true; HUAGAI[11][11]=true;
    HUAGAI[6][2]=true;HUAGAI[10][2]=true; HUAGAI[2][2]=true;
    HUAGAI[12][8]=true; HUAGAI[4][8]=true; HUAGAI[8][8]=true;
  }
  //��������֧����֧Ϊ�����������кε�֧��֮
  public static final boolean[][] YIMA = new boolean[13][13];
  static
  {
    YIMA[9][3]=true; YIMA[1][3]=true; YIMA[5][3]=true;
    YIMA[3][9]=true; YIMA[7][9]=true; YIMA[11][9]=true;
    YIMA[6][12]=true;YIMA[10][12]=true; YIMA[2][12]=true;
    YIMA[12][6]=true; YIMA[4][6]=true; YIMA[8][6]=true;
  }
  //���ǣ�����֧����֧Ϊ�����������кε�֧��֮
  public static final boolean[][] JIANG = new boolean[13][13];
  static
  {
    JIANG[9][1]=true; JIANG[1][1]=true; JIANG[5][1]=true;
    JIANG[3][7]=true; JIANG[7][7]=true; JIANG[11][7]=true;
    JIANG[6][10]=true;JIANG[10][10]=true; JIANG[2][10]=true;
    JIANG[12][4]=true; JIANG[4][4]=true; JIANG[8][4]=true;
  }
  //���ߣ����ո�Ϊ������֧����Ϊ��
  public static final boolean[][] JINYU = new boolean[11][13];
  static
  {
    FUXING[1][5]=true;  FUXING[2][6]=true;  FUXING[3][8]=true;  FUXING[4][9]=true;
    FUXING[5][8]=true;  FUXING[6][9]=true;  FUXING[7][11]=true; FUXING[8][12]=true;
    FUXING[9][2]=true;  FUXING[10][3]=true;
  }
  //����������ʱ������Ϊ��
  public static final boolean[][] JINSHEN = new boolean[11][13];
  static
  {
    FUXING[2][2]=true;  FUXING[6][6]=true;  FUXING[10][10]=true;
  }
  //��ҽ������֧��������֧������Ϊ��
  public static final boolean[][] TIANYI1 = new boolean[13][13];
  static
  {
    TIANYI1[1][12]=true;  TIANYI1[2][1]=true;  TIANYI1[3][2]=true;
    TIANYI1[4][3]=true;  TIANYI1[5][4]=true;  TIANYI1[6][5]=true;
    TIANYI1[7][6]=true;  TIANYI1[8][7]=true;  TIANYI1[9][8]=true;
    TIANYI1[10][9]=true;  TIANYI1[11][10]=true;  TIANYI1[12][11]=true;
  }
  //»�����ոɲ���֧����֮��Ϊ��
  public static final boolean[][] LUSHEN = new boolean[11][13];
  static
  {
    TIANYI1[1][3]=true;  TIANYI1[2][4]=true;  TIANYI1[3][6]=true;
    TIANYI1[4][7]=true;  TIANYI1[5][6]=true;  TIANYI1[6][7]=true;
    TIANYI1[7][9]=true;  TIANYI1[8][10]=true;  TIANYI1[9][12]=true;
    TIANYI1[10][1]=true;
  }
  //��»��������ʱ
  public static final boolean[][][][] GONGLU = new boolean[11][13][11][13];
  static
  {
    GONGLU[10][12][10][2]=true;
    GONGLU[10][2][10][12]=true;
    GONGLU[4][6][4][8]=true;
    GONGLU[6][8][6][6]=true;
    GONGLU[5][5][5][7]=true;
  }
  //���⣬��֧������
  public static final boolean[][][] TIANSE = new boolean[13][11][13];
  static
  {
    TIANSE[3][5][3]=true; TIANSE[4][5][3]=true; TIANSE[5][5][3]=true;
    TIANSE[6][1][7]=true; TIANSE[7][1][7]=true; TIANSE[8][1][7]=true;
    TIANSE[9][5][9]=true;TIANSE[10][5][9]=true; TIANSE[11][5][9]=true;
    TIANSE[12][1][1]=true; TIANSE[1][1][1]=true; TIANSE[2][1][1]=true;
  }
  //���޵���������֧����֧Ϊ����������֧��֮��Ϊ�ǣ�������������֮
  //0��֧1-5��������3ˮ5������1������2�ǵ�����֧��֧��
  public static final boolean[][][][] TIANLUO = new boolean[6][3][13][13];
  static
  {
    TIANLUO[0][1][11][12]=true; TIANLUO[0][1][12][11]=true;
    TIANLUO[0][2][5][6]=true;   TIANLUO[0][2][6][5]=true;
    TIANLUO[4][1][11] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[4][1][12] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[3][2][5] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[3][2][6] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[5][2][5] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[5][2][6] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
  }
  //���У����ոɲ���֧����֮��Ϊ��
  public static final boolean[][] YANGREN = new boolean[11][13];
  static
  {
    YANGREN[1][4]=true;  YANGREN[2][3]=true;  YANGREN[3][7]=true;
    YANGREN[4][6]=true;  YANGREN[5][7]=true;  YANGREN[6][6]=true;
    YANGREN[7][10]=true;  YANGREN[8][9]=true;  YANGREN[9][1]=true;
    YANGREN[10][12]=true;
  }
  //�õ�ʮ�ɵ�����
  public static final int[] YANGREN2 = {0,4,3,7,6,7,6,10,9,1,12};

  //��������֧����֧Ϊ�����������кε�֧��֮
  public static final boolean[][] WANGSHEN = new boolean[13][13];
  static
  {
    WANGSHEN[9][12]=true; WANGSHEN[1][12]=true; WANGSHEN[5][12]=true;
    WANGSHEN[3][6]=true; WANGSHEN[7][6]=true; WANGSHEN[11][6]=true;
    WANGSHEN[6][9]=true;WANGSHEN[10][9]=true; WANGSHEN[2][9]=true;
    WANGSHEN[12][3]=true; WANGSHEN[4][3]=true; WANGSHEN[8][3]=true;
  }
  //��ɷ������֧����֧Ϊ�����������кε�֧��֮
  public static final boolean[][] JIESHA = new boolean[13][13];
  static
  {
    JIESHA[9][6]=true; JIESHA[1][6]=true; JIESHA[5][6]=true;
    JIESHA[3][12]=true; JIESHA[7][12]=true; JIESHA[11][12]=true;
    JIESHA[6][3]=true;JIESHA[10][3]=true; JIESHA[2][3]=true;
    JIESHA[12][9]=true; JIESHA[4][9]=true; JIESHA[8][9]=true;
  }
  //��ɷ������֧����֧Ϊ�����������кε�֧��֮
  public static final boolean[][] ZAISHA = new boolean[13][13];
  static
  {
    ZAISHA[9][7]=true; ZAISHA[1][7]=true; ZAISHA[5][7]=true;
    ZAISHA[3][1]=true; ZAISHA[7][1]=true; ZAISHA[11][1]=true;
    ZAISHA[6][4]=true;ZAISHA[10][4]=true; ZAISHA[2][4]=true;
    ZAISHA[12][10]=true; ZAISHA[4][10]=true; ZAISHA[8][10]=true;
  }
  //����ɷ������֧Ϊ���������������֧������1Ϊ������Ů��2��֮
  public static final boolean[][][] GOUSHA = new boolean[3][13][13];
  public static final boolean[][][] JIAOSHA = new boolean[3][13][13];
  static
  {
    GOUSHA[1][1][4]=true;GOUSHA[2][1][10]=true;
    JIAOSHA[1][1][10]=true;JIAOSHA[2][1][4]=true;
    GOUSHA[1][2][5]=true;GOUSHA[2][2][11]=true;
    JIAOSHA[1][2][11]=true;JIAOSHA[2][2][5]=true;
    GOUSHA[1][3][6]=true;GOUSHA[2][3][12]=true;
    JIAOSHA[1][3][12]=true;JIAOSHA[2][3][6]=true;
    GOUSHA[1][4][7]=true;GOUSHA[2][4][1]=true;
    JIAOSHA[1][4][1]=true;JIAOSHA[2][4][7]=true;
    GOUSHA[1][5][8]=true;GOUSHA[2][5][2]=true;
    JIAOSHA[1][5][2]=true;JIAOSHA[2][5][8]=true;
    GOUSHA[1][6][9]=true;GOUSHA[2][6][3]=true;
    JIAOSHA[1][6][3]=true;JIAOSHA[2][6][9]=true;
    GOUSHA[1][7][10]=true;GOUSHA[2][7][4]=true;
    JIAOSHA[1][7][4]=true;JIAOSHA[2][7][10]=true;
    GOUSHA[1][8][11]=true;GOUSHA[2][8][5]=true;
    JIAOSHA[1][8][5]=true;JIAOSHA[2][8][11]=true;
    GOUSHA[1][9][12]=true;GOUSHA[2][9][6]=true;
    JIAOSHA[1][9][6]=true;JIAOSHA[2][9][12]=true;
    GOUSHA[1][10][1]=true;GOUSHA[2][10][7]=true;
    JIAOSHA[1][10][7]=true;JIAOSHA[2][10][1]=true;
    GOUSHA[1][11][2]=true;GOUSHA[2][11][8]=true;
    JIAOSHA[1][11][8]=true;JIAOSHA[2][11][2]=true;
    GOUSHA[1][12][3]=true;GOUSHA[2][12][9]=true;
    JIAOSHA[1][12][9]=true;JIAOSHA[2][12][3]=true;
  }
  //�³�����,����֧Ϊ׼������������֧����Ϊ��,1�ǹ³�2�ǹ���
  public static final boolean[][][] GCGX = new boolean[3][13][13];
  static
  {
    GCGX[1][12][3]=true;  GCGX[2][12][11]=true;
    GCGX[1][1][3]=true;  GCGX[2][1][11]=true;
    GCGX[1][2][3]=true;  GCGX[2][2][11]=true;

    GCGX[1][3][6]=true;  GCGX[2][3][2]=true;
    GCGX[1][4][6]=true;  GCGX[2][4][2]=true;
    GCGX[1][5][6]=true;  GCGX[2][5][2]=true;

    GCGX[1][6][9]=true;  GCGX[2][6][5]=true;
    GCGX[1][7][9]=true;  GCGX[2][7][5]=true;
    GCGX[1][8][9]=true;  GCGX[2][8][5]=true;

    GCGX[1][9][12]=true;  GCGX[2][9][8]=true;
    GCGX[1][10][12]=true;  GCGX[2][10][8]=true;
    GCGX[1][11][12]=true;  GCGX[2][11][8]=true;
  }
  //Ԫ��������֧Ϊ׼�������������֧������1Ϊ������Ů��2��֮
  public static final boolean[][][] YUANCHEN = new boolean[13][13][13];
  static
  {
    YUANCHEN[1][1][8]=true;  YUANCHEN[2][1][6]=true;
    YUANCHEN[1][2][9]=true;  YUANCHEN[2][2][7]=true;
    YUANCHEN[1][3][10]=true;  YUANCHEN[2][3][8]=true;
    YUANCHEN[1][4][11]=true;  YUANCHEN[2][4][9]=true;
    YUANCHEN[1][5][12]=true;  YUANCHEN[2][5][10]=true;
    YUANCHEN[1][6][1]=true;  YUANCHEN[2][6][11]=true;
    YUANCHEN[1][7][2]=true;  YUANCHEN[2][7][12]=true;
    YUANCHEN[1][8][3]=true;  YUANCHEN[2][8][1]=true;
    YUANCHEN[1][9][4]=true;  YUANCHEN[2][9][2]=true;
    YUANCHEN[1][10][5]=true;  YUANCHEN[2][10][3]=true;
    YUANCHEN[1][11][6]=true;  YUANCHEN[2][11][4]=true;
    YUANCHEN[1][12][7]=true;  YUANCHEN[2][12][5]=true;
  }
  //������������Ϊ���������ꡢ�¡�ʱ֧����Ϊ����
  //��YiJing.KONGWANG();
  //ʮ���ܣ������ո�֧��֮����
  public static final boolean[][] DABAI = new boolean[11][13];
  static
  {
    DABAI[1][5]=true;  DABAI[2][6]=true;  DABAI[9][9]=true;
    DABAI[3][9]=true;  DABAI[4][12]=true; DABAI[7][5]=true;
    DABAI[5][11]=true; DABAI[10][12]=true;DABAI[8][6]=true;
    DABAI[6][2]=true;
  }
  //�̳أ�����֧����֧������������֧
  public static final boolean[][] XIANCI = new boolean[13][13];
  static
  {
    XIANCI[9][10]=true; XIANCI[1][10]=true; XIANCI[5][10]=true;
    XIANCI[3][4]=true; XIANCI[7][4]=true; XIANCI[11][4]=true;
    XIANCI[6][7]=true;XIANCI[10][7]=true; XIANCI[2][7]=true;
    XIANCI[12][1]=true; XIANCI[4][1]=true; XIANCI[8][1]=true;
  }
  //���ɷ��������ʱͬʱ���������κ�������Ϊ��
  public static final boolean[][] GULUAN = new boolean[11][13];
  static
  {
    GULUAN[2][6]=true;  GULUAN[4][6]=true;  GULUAN[8][12]=true;
    GULUAN[5][9]=true;  GULUAN[9][3]=true;  GULUAN[5][7]=true;
    GULUAN[9][1]=true;  GULUAN[3][7]=true;
  }
  //���������������Ϊ��
  public static final boolean[][] YYCACUO = new boolean[11][13];
  static
  {
    YYCACUO[3][1]=true;  YYCACUO[4][2]=true;  YYCACUO[5][3]=true;
    YYCACUO[8][4]=true;  YYCACUO[9][5]=true;  YYCACUO[10][6]=true;
    YYCACUO[3][7]=true;  YYCACUO[4][8]=true;  YYCACUO[5][9]=true;
    YYCACUO[8][10]=true; YYCACUO[9][11]=true; YYCACUO[10][12]=true;
  }
  //�ķϣ��������ո�֧���ڸ���Ϊ��
  public static final boolean[][][] SIFEI = new boolean[13][11][13];
  static
  {
    SIFEI[12][3][7]=true;  SIFEI[1][3][7]=true;  SIFEI[2][3][7]=true;
    SIFEI[12][4][6]=true;  SIFEI[1][4][6]=true;  SIFEI[2][4][6]=true;
    SIFEI[3][7][9]=true;    SIFEI[4][7][9]=true;   SIFEI[5][7][9]=true;
    SIFEI[3][8][10]=true;    SIFEI[4][8][10]=true;   SIFEI[5][8][10]=true;
    SIFEI[6][9][1]=true;    SIFEI[7][9][1]=true;   SIFEI[8][9][1]=true;
    SIFEI[6][10][12]=true;    SIFEI[7][10][12]=true;   SIFEI[8][10][12]=true;
    SIFEI[9][1][3]=true;   SIFEI[10][1][3]=true; SIFEI[11][1][3]=true;
    SIFEI[9][2][4]=true;   SIFEI[10][2][4]=true; SIFEI[11][2][4]=true;
  }

  /**
   * ���д��
   */
  public static int muCent = 0;
  public static int huoCent = 0;
  public static int tuCent = 0;
  public static int jinCent = 0;
  public static int shuiCent = 0;
  //������Ϊ100��ͬ��Ϊ100����������80����������60����������40���ҿ�����20���Դ�Ϊ���������ʼ��
  public static final int wangCent = 100;
  public static final int xiangCent = 80;
  public static final int xiuCent = 60;
  public static final int keCent = 40;
  public static final int siCent = 20;
  // ����(������ϵ��-�����3.0��֧  �����2.5����֧,��ȥ����������)
  public static final double[] sanhuiXS = {0.0, 1.0, 0.8};
  //����-����֧��Ϊ����������������֧������î��(����壺����һ֧������-0.5��֧,-0.2��֧��-0.6��֧,-0.3��֧������һ֧��֧�������:-0.5��֧,-0.2��֧,0.1��֧��)
  //       -�����Ⱥ��壺����һ֧������-0.5��֧,-0.2��֧����-0.6��֧,-0.3��֧������һ֧��֧����֧-0.5��֧,-0.2��֧,0.1��֧
  //      -������壺����һ֧������0.5��֧,0.3��֧,��0.3��֧,0.2��֧������һ֧��֧����֧0.3��֧,0.2��֧,0.1��֧
  public static final double[] lcXS1 = {0.0,-0.5,-0.2,-0.6,-0.3,-0.5,-0.2,0.1};
  public static final double[] lcXS2 = {0.0,0.5,0.3,0.3,0.2,0.3,0.2,0.1};
  //����(������ϵ��-�����2.5��֧ �����2.0����֧,Ҫ��ȥ��֧��)
  public static final double[] sanheXS = {0.0, 1.0, 0.8};
  //��������� ������ϵ��������һ֧����֧1.3��֧��1.0��֧������һ֧��֧����֧1.0����֧��0.6����֧��0.2����֧
  public static final double[] wangBHXS = {0.0, 0.5, 0.3, 0.3, 0.2, 0.1};
  //���� ������ϵ�����϶�����(����һ֧����֧1.2��֧��1.0��֧������һ֧��֧����֧1.0����֧��0.6����֧��0.2����֧)
  //                 �ϻ�(����֧2.0��֧ ����֧1.5����֧)
  public static final double[] lhXS = {0.0,0.3,0.2,0.3,0.2,0.1,2.0,1.5};
  //���Ϸ������ ������ϵ��������һ֧����֧0.5��֧��0.3��֧������һ֧��֧����֧0.3����֧��0.2����֧��0.1����֧
  public static final double[] feiBHXS = {0.0,0.3,0.1,0.2,0.1,0.05};
  //���� ������ϵ��,���߲�����,ֻ�����ƣ���ʵû����������һ֧��֧0.3��֧��0.2��֧��0.1��֧
  public static final double[] shengXS = {0.0,0.15,0.1,0.05};
  //��� ������ϵ��,���߲�������ֻ��һ����в(��δ���ˮ����-0.3,������0.05)������һ֧��֧-0.3��֧��-0.2��֧��-0.1��֧
  public static final double[] keXS = {0.0,-0.15,-0.1,-0.05,0.1};
  //���� ��î�갵������٣�������ϵ����0.5��֧,�⼴��������
  public static final double anheXS = 0.1;
  //�츲����(����֧����): 0.8 �����ˣ�-0.8 ��ϵأ�ֻ�����ӡ����ȡ��������������գ���0.5*��֧����
  public static final double[] ganziXS = {0.0,0.1,-0.1,0.1};
  //���е�һ�ȼ� ������3֧��һĹ�� 0.35��֧������  ��һ����  0.7 »�� 1.0��֧������ �������籾��0.7
  public static final double[] sanziXS = {0.3,0.35,0.7,1.0,0.7};
  //�຦ -0.1��֧
  public static final double haiXS = -0.05;
  //���� -0.1��֧
  public static final double xingXS = -0.05;
  public static final double tdhXS = 0.05;


  //�����н�ľˮ��������֧���еķ���,���ڽ�100�֣�������80��
  public static final int[][] jibenfen = new int[6][6];
  static {
    jibenfen[YiJing.JIN][YiJing.JIN]=wangCent;jibenfen[YiJing.SHUI][YiJing.JIN]=xiangCent;
    jibenfen[YiJing.TU][YiJing.JIN]=xiuCent;jibenfen[YiJing.HUO][YiJing.JIN]=keCent;
    jibenfen[YiJing.MU][YiJing.JIN]=siCent;
    jibenfen[YiJing.MU][YiJing.MU]=wangCent;jibenfen[YiJing.HUO][YiJing.MU]=xiangCent;
    jibenfen[YiJing.SHUI][YiJing.MU]=xiuCent;jibenfen[YiJing.JIN][YiJing.MU]=keCent;
    jibenfen[YiJing.TU][YiJing.MU]=siCent;
    jibenfen[YiJing.SHUI][YiJing.SHUI]=wangCent;jibenfen[YiJing.MU][YiJing.SHUI]=xiangCent;
    jibenfen[YiJing.JIN][YiJing.SHUI]=xiuCent;jibenfen[YiJing.TU][YiJing.SHUI]=keCent;
    jibenfen[YiJing.HUO][YiJing.SHUI]=siCent;
    jibenfen[YiJing.HUO][YiJing.HUO]=wangCent;jibenfen[YiJing.TU][YiJing.HUO]=xiangCent;
    jibenfen[YiJing.MU][YiJing.HUO]=xiuCent;jibenfen[YiJing.SHUI][YiJing.HUO]=keCent;
    jibenfen[YiJing.JIN][YiJing.HUO]=siCent;
    jibenfen[YiJing.TU][YiJing.TU]=wangCent;jibenfen[YiJing.JIN][YiJing.TU]=xiangCent;
    jibenfen[YiJing.HUO][YiJing.TU]=xiuCent;jibenfen[YiJing.MU][YiJing.TU]=keCent;
    jibenfen[YiJing.SHUI][YiJing.TU]=siCent;

  }

  //public final static int wangjiCent = 96;
  public final static int[] judgeWS = {80,130,190,260,700}; //y
  public final static int[] baifen =  {40,60, 80, 90, 100}; //x     ��x<80 x=x*x0/y0     ���� x = x0 + (x-y0)*(x1-x0)/(y1-y0)
  public static int muCent1 = 0;
  public static int huoCent1 = 0;
  public static int tuCent1 = 0;
  public static int jinCent1 = 0;
  public static int shuiCent1 = 0;

  public final static String[] judgeWSName = {"��֮����","ƫ��","����","ǿ��","��֮����"};

}
