package org.boc.util;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

public class Public {
	public static final int QM = 1;
	public static final int LY = 2;
	public static final int SZ = 3;
	
  public static final String DATA = "dat";
  public static final String LOG = "log";
  public static final String CONF = "conf";
  public static final String QMXXDZ = "database/����/��Ϣ����";
  public static final String QMGZDZ = "database/����/������";
  public static final String QMGJDZ = "database/����/��ֶ���";
  public static final String QMQDSZ = "database/����/��������/qdsz.ini";
  public static final String LYQDSZ = "database/��س/��������/qdsz.ini";
  public static final String NUM1 = "wang";
  
  public static final boolean DEBUGSWITCH = false;  //true��System.outת���������־�ļ���
  public static final String LOGFILE = "log.txt";
  public static final boolean zc = true; //�Ƿ�ע�ᣬfalseΪ��
  public static final String helpdoc = "/conf"+File.separator+"help.pdf";  //�����ĵ�����
  public static final String NOW = "����ʱ��"; //��һ��ʹ��ʱ��qm.xml��sz.xmlû�����ɣ�ȱʡ�����´������ӽڵ㣬��<����><����ʱ��></></>
  
	public static final int SPLASH = 0;
  public final static String[] DAXIAO = new String[]{" ","һ","��","��",
      "��","��","��","��","��","��","ʮ","ʮһ","ʮ��","ʮ��",
      "ʮ��","ʮ��","ʮ��","ʮ��","ʮ��","ʮ��","��ʮ","��ʮһ",
      "��ʮ��","��ʮ��","��ʮ��","��ʮ��","��ʮ��","��ʮ��","��ʮ��",
      "","","",};
  
  /**
   * �޸��û�Ŀ¼Ϊ��ǰĿ¼��simon 2011-10-10
   */
  public static String HOME = "";
  //public static String HOME = System.getProperty("user.home") + File.separator + ".jyijing" + File.separator;
  /**
   * ���⣬�Ի�������
   */

  public static final String infoTitle = "��ܰ��ʾ";
  public static final String  status = "�ﶡ�����ˡ��Ȩ���У��Ͻ�������ҵ��;��Υ�߱ؾ���";
  public static final String  title = "ʼ��Ԥ����ŵ�����Ԥ���ȫ";
  public static final String TREEROOT = "ʼ���ֵ�";

  public static String[] keyRoot = {"̫��","����","����",
      "��΢","����","����","��س","����","��լ","����","�ƹ�","���"};
  public static String[] valueRoot = {"ty","qm","lr",
      "zw","tb","sz","ly","xk","bz","xm","cg","gg"};
  public static final String[][] tabTitle = new String[valueRoot.length][10];
  static {
    //tabTitle[1] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "��һ","�ֶ�","��������","������ѧ","������ҵ","����","����","����"};
  	tabTitle[1] = new String[] {"","������Ϣ�б�", "�������"};
    tabTitle[2] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "���","����"};
    tabTitle[3] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "����","����"};
    tabTitle[4] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "����","����"};
    tabTitle[5] = new String[]{"","��Ϣ�б�", "��ϸ��Ϣ", "����","������","���","��ҵ","����","����","�Ը�","����"};
    tabTitle[6] = new String[] {"","��س��Ϣ�б�", "��س����"};
    tabTitle[7] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "����","����","��ʱ"};
    tabTitle[8] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "������","����"};
    tabTitle[9] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "����"};
    tabTitle[10] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "Ԭ��ƹ�"};
    tabTitle[11] = new String[] {"","��Ϣ�б�", "��ϸ��Ϣ", "��ȷֶ���"};
  }
  /**
   * ������������BasicJPanel::XxxxFrame����壬����ÿ��˫����ʱ�����´�����Frame�ˣ�  
   * ��BasicJTabbedPane�Ĺ��췽���е���
   */
  //public static HashMap<String,BasicJPanel> frameCache = new HashMap<String,BasicJPanel>(10);
  
  public static final String[] strClass = {
      "org.boc.ui.ty.TaiYiFrame","org.boc.ui.qm.QiMenFrame","org.boc.ui.lr.LiuRenFrame",
      "org.boc.ui.zw.ZiWeiFrame","org.boc.ui.tb.TiebanFrame",
      "org.boc.ui.sz.SiZhuFrame","org.boc.ui.ly.LiuYaoFrame",
      "org.boc.ui.xk.XuanKongFrame","org.boc.ui.bz.BaZhaiFrame",
      "org.boc.ui.xm.XingMingFrame","org.boc.ui.cg.ChengGuFrame","org.boc.ui.gg.GuiGuFrame"};
  /**
   * ���װ���ж�
   * �ж���ʽ���������������������ƹǡ�����Ƿ��
   */
  public static Map<String,String> mapClass = new HashMap<String,String>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapClass.put(valueRoot[i], strClass[i]);
    }
  }

  /**
   * ��value��ֵ��qm�õ��������еĵڼ���
   */
  public static int getValueIndex(String value) {
    for(int i=0; i<valueRoot.length; i++) {
      if(valueRoot[i].equals(value))
        return i;
    }
    return -1;
  }

  /**
   * �ж�Ԥ����������Ϣ����Ƿ��Ѵ�
   * �ж���ʽ���������������������ƹǡ�����Ƿ��
   */
  public static Map<String,Boolean> mapKeyIsOpen = new HashMap<String,Boolean>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapKeyIsOpen.put(valueRoot[i], new Boolean(false));
    }
  }

  /**
   * �ж�Ԥ�⼼���Ƿ���װ�ص�����
   * �ж���ʽ���������������������ƹǡ�����Ƿ��
   */
  public static Map<String,Boolean> mapKeyIsLoaded = new HashMap<String,Boolean>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapKeyIsLoaded.put(valueRoot[i], new Boolean(false));
    }
  }
  /**
   * ��ֵ��
   */
  public static Map<String,String> mapRootKeyValue = new HashMap<String,String>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapRootKeyValue.put(keyRoot[i],valueRoot[i]);
    }
  }
  public static Map<String,String> mapRootValueKey = new HashMap<String,String>();
  static {
    for(int i=0; i<keyRoot.length; i++) {
      mapRootValueKey.put(valueRoot[i],keyRoot[i]);
    }
  }


  /**
   * ����bool��map��key��ֵΪbooleanֵ
   */
  public static void setKeyValue(Map<String,Boolean> map, String value, boolean b) {
    map.put(value, new Boolean(b));
  }
  public static boolean getKeyValue(Map<String,Boolean> map, String value) {
    return ((Boolean)map.get(value)).booleanValue();
  }

  /**
   * ��vlaueֵȡ����keyֵ������ĸȡ����
   */
  public static String getRootKey(String value) {
    return (String)mapRootValueKey.get(value);
  }

  /**
   * ��keyֵȡ����valueֵ���ɺ���ȡ��ĸ
   */
  public static String getRootValue(String key) {
    return (String)mapRootKeyValue.get(key);
  }


  //����дxml�ļ�����
  public static Map<String,String> mapFile ;
  public static void setMapFile() {
    mapFile = new HashMap<String,String>();
//    mapFile.put("root",HOME+DATA+"/tree.xml");
    mapFile.put("ty",HOME+DATA+"/ty.xml");
    mapFile.put("qm",HOME+DATA+"/qm.xml");
    mapFile.put("lr",HOME+DATA+"/lr.xml");

    mapFile.put("zw",HOME+DATA+"/zw.xml");
    mapFile.put("tb",HOME+DATA+"/tb.xml");

    mapFile.put("xk",HOME+DATA+"/xk.xml");
    mapFile.put("bz",HOME+DATA+"/bz.xml");

    mapFile.put("sz",HOME+DATA+"/sz.xml");
    mapFile.put("ly",HOME+DATA+"/ly.xml");

    mapFile.put("xm",HOME+DATA+"/xm.xml");
    mapFile.put("cg",HOME+DATA+"/cg.xml");
    mapFile.put("gg",HOME+DATA+"/gg.xml");
  }

  public static Font getFont() {
    return new Font("����", Font.PLAIN, 12);
  }

  /**
   * ��������Ϊһ��Collectionд���ļ�����������ˣ�Ҫɾ�������
   * @param vo Serializable
   */
  public static void writeObject2File(VO vo) {
    ObjectOutputStream out = null;
    String fileId = vo.getFileId();
    String rowId = vo.getRowId();
    String pathName = HOME + DATA +"/"+fileId+".dat";
    try {
      Collection<VO> coll = getObjectFromFile(fileId);
      out = new ObjectOutputStream(new FileOutputStream(pathName));
      if(coll!=null) {
        Iterator<VO> it = coll.iterator();
        while(it.hasNext()) {
          VO vo_ = it.next();
          if(vo_.getRowId().equals(rowId)) {
            coll.remove(vo_);
            break;
          }
        }
      } else{
        coll = new ArrayList<VO>();
      }
      coll.add(vo);
      out.writeObject(coll);
      out.close();
    }
    catch (IOException ex) {
      Messages.error("Public::writeObject2File(��������Ϊһ��VOд���ļ�����"+ex+")");
      //return ;
    }
  }
  public static void writeObject2File(String fileId, Collection c) {
    ObjectOutputStream out = null;
    String pathName = HOME + DATA + "/"+fileId+".dat";
    try {
      out = new ObjectOutputStream(new FileOutputStream(pathName));
      out.writeObject(c);
      out.close();
    }
    catch (IOException ex) {
      Messages.error("Public::writeObject2File(��������Ϊһ��Collectionд���ļ�����"+ex+")");
      //return ;
    }
  }


  /**
   * ���ļ��������Collection,��ָ����rowId�õ�������ֵ
   * @param pathName
   * @return
   */
  @SuppressWarnings("unchecked")
	public static Collection<VO> getObjectFromFile(String fileId) {
    String pathName = HOME + DATA+"/"+fileId+".dat";
    Collection<VO> coll = null;
    ObjectInputStream in = null;
    try {
    	if(!new File(pathName).exists()) return null;  //��������ڣ�ֱ�ӷ��أ���Ҫ����
      in = new ObjectInputStream(new FileInputStream(pathName));
      coll = (Collection<VO>)in.readObject();
      return coll;
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("Public::getObjectFromFile2(��"+fileId+"�ļ�����������"+ex+")");
      //return null;
    }finally{
      try {
        if(in!=null) in.close();
      } catch (IOException ex) { }
    }
    return null;
  }

  public static Object getObjectFromFile(String fileId, String rowId) {
  	Collection<VO> coll = getObjectFromFile(fileId); 
  	if(coll==null) return null;
  	for(VO vo : coll) {
  		if(vo.getRowId().equals(rowId)) {
        return vo;
      }
  	}
    return null;
  }

   @SuppressWarnings("unchecked")
	public static void delObjsFromFile(String fileId, String[] id) {
    String pathName = HOME + DATA+"/"+fileId+".dat";
     Collection<VO> coll = null;
     Collection<VO> c = new ArrayList<VO>();
     ObjectInputStream in = null;
     try {
       in = new ObjectInputStream(new FileInputStream(pathName));
       coll = (Collection<VO>)in.readObject();

       //����һ��
       if(coll!=null && coll.size()>0) {
         for(Iterator<VO> it = coll.iterator(); it.hasNext();) {
           c.add(it.next());
         }
       }

       if(coll!=null && coll.size()>0) {
         for(Iterator<VO> it = coll.iterator(); it.hasNext();) {
           VO vo = (VO)it.next();
           for(int j=0; j<id.length; j++) {
             if (vo.getRowId().equals(id[j])) {
               c.remove(vo);
               break;
             }
           }
         }
       }
       writeObject2File(fileId, c);
     }
     catch (Exception ex) {
       ex.printStackTrace();
       //Messages.error("Public::delObjsFromFile(���ļ�ɾ������������"+ex+")");
       //return ;
     }finally{
       try {
         if(in!=null)
           in.close();
       }
       catch (IOException ex) {
       }
     }
   }

   public static java.sql.Date getDateValue() {
     java.sql.Date columnValue = new java.sql.Date(System.currentTimeMillis());
     return columnValue;
   }

   public static Time getTimeValue() {
     Time columnValue = new Time(System.currentTimeMillis());
     return columnValue;
   }

   public static Timestamp getTimestampValue() {
     Timestamp columnValue = new Timestamp(System.currentTimeMillis());
     return columnValue;
   }

   /**
    * �����������������֧����
    * G = 4C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d - 3
    * Z = 8C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d + 7 + i
    * @param yy int
    * @param m int
    * @param d int
    */
  public static void getDayGZ(int yy, int m, int d) {
    int i = m/2==0 ? 6 : 0;
    int c = yy/100;
    int y = yy%100;
    int g = 4*c+c/4+5*y+y/4+(3*(m+1)/5)+d-3;
    int z = 8*c+c/4+5*y+y/4+(3*(m+1)/5)+d+7-i;
    String[] gan = new String[]{"��","��","��","��","��","��","��","��","��","��","��"};
    String[] zi  = new String[]{"��","��","��","��","î","��","��","��","δ","��","��","��","��"};
    System.out.println("����"+yy+"-"+m+"-"+d+" ��֧��"+gan[g%10]+zi[z%12]);
  }

  /**
   * �õ�����1��x��y�յ�����
   */
  public static int getTotal(int x, int y) {
    int[] arr = new int[]{0,31,28,31,30,31,30,31,31,30,31,30,31};
    int t = 0;
    for(int i=1; i<x; i++) {
      t += arr[i];
    }
    return t+y;
  }
  
  public static InputStream getFileFromJar(String dir) {
  	if(dir==null) return null;
//  	Messages.info("0"+Public.class.getResource("."));
//  	Messages.info("0"+Public.class.getResource(""));
//  	Messages.info("0"+Public.class.getResource("/"));
//  	Messages.info(""+Public.class.getResource(dir));  jar:file:/E:/..../jyijing.jar!/conf/a.txt
  	
  	if(!dir.startsWith("/")) dir = "/"+dir;  	
//  	dir = Public.class.getResource(dir).getPath();  	
//  	if(dir.startsWith("file:")) dir = dir.substring(6);
//  	else if(dir.startsWith("jar:file:")) dir = dir.substring(10);
  	
  	InputStream is=Public.class.getResourceAsStream(dir);    

  	return is;
  }

  /**
   * ����ת����������ʽ
   * ��Ԫ������1977����1901�������ѣ���
   * ��������=14Q+10.6(R+1)+������������-29.5n
   * @param yy int
   * @param m int
   * @param d int
   */
  public static void yang2Yin(int yy, int m, int d) {
    int q = (yy-1977)/4;
    int r = (yy-1977)%4;
    double t = 14*q+10.6*(r+1)+getTotal(m,d);
    int mm = (int)(t/29.5);
    int dd = (int)(t - mm*29.5);
    System.out.println("����"+yy+"-"+m+"-"+d+" ũ���ǣ�"+yy+":"+mm+":"+dd);
  }
  /**
   * �õ�����ʱ��
   */
  public static Calendar getCalendarForBeijing() {
  	//TimeZone tz   =   TimeZone.getTimeZone( "Asia/Shanghai ");//����ʱ��
  	//Calendar c ���ɵ�ʱ���� GMT ʱ�� �ȱ���ʱ���� 8 Сʱ c.getHour()Ҳ���� 8 Сʱ �õ�������Ҳ����˲������� ������������ GMTʱ����22�� ���� Сһ�죩����Ҫ����
  	TimeZone tz = TimeZone.getTimeZone("GMT+8");
    GregorianCalendar clr = new GregorianCalendar(tz);
  	//Calendar clr = Calendar.getInstance();
  	//clr.setTimeZone(tz);
  	return clr;
  }
  
  public static int getCurrentTime(int i) {
  	Calendar clr = getCalendarForBeijing();
  	return i==1 ? clr.get(Calendar.YEAR) : 
  				 i==2 ? clr.get(Calendar.MONTH):
  				 i==3 ? clr.get(Calendar.DAY_OF_MONTH):
  				 i==4 ? clr.get(Calendar.HOUR_OF_DAY) :
  				 i==5 ? clr.get(Calendar.MINUTE): clr.get(Calendar.SECOND);
  }
  
  public static void main(String[] args) {
    Public.yang2Yin(1977,5,10);
    Public.yang2Yin(1994,5,7);
  }
}
