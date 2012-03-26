package org.boc.dao;

import java.io.*;
import java.util.Stack;
import org.boc.delegate.DelLog;
import org.boc.util.Messages;
import org.boc.util.Public;

public class DaoBh {
  private DaoPublic pub ;
  private String[][] shou;
  private char[] num;
  private PrintWriter pw;

  public DaoBh() {
    pw = DelLog.getLogObject();
    pub = new DaoPublic();
    num = new char[]{' ','һ','��','��','��','��','��','��','��','��','ʮ'};
  }

  /**
   * �õ�ĳ���׵ĺ�����ʻ�����������
   * @return int
   */
  public int getBsbh(char c) {
    String s = "";   //32Ϊ�ո�
    String s1 = String.valueOf(c);
    int bh = 0;
    try {
    	
    	InputStreamReader f = new InputStreamReader(Public.getFileFromJar("/conf/bs.simon"));
      BufferedReader r = new BufferedReader(f);
      while(s != null) {
        if(s.indexOf(s1)!=-1) {
          if(s.charAt(0)>'0' && s.charAt(0)<'9')
            bh = Integer.valueOf(String.valueOf(s.charAt(0))).intValue();
        }
        s = r.readLine();
      }
    }catch (FileNotFoundException ex) {
      Messages.error("DaoBh("+ex+")");
    }catch(IOException ex) {
      Messages.error("DaoBh("+ex+")");
    }
    return bh;
  }

  /**
   * �õ�����ַ��ıʻ����죬������������Ϊ�����ܱȷ���ʻ���
   * @param s String
   * @return int
   */
  public int getBsbh(String s) {
    int total = 0;
    for(int i=0; i<s.length(); i++) {
      total += getBsbh(s.charAt(i));
    }
    return total;
  }

  /**
   *�õ���ǰclass��·��
   * @return
   */
  private String getCurrentClassPath() {
    String str = DaoPublic.getClassRootPath();
    if(str.indexOf("%")!=-1)
      str = pub.unicode2String(str);

    return str;
  }

  /**
   * �õ�һ�������ıʻ���
   * @param str String
   * @return int
   */
  public int getBhOfFt(String str) {
    try {
      return getBh(new String(gb2big5(str)));
    }
    catch (Exception ex) {
      return 0;
    }
  }

  /**
   * �õ�һ�������ıʻ���
   * @param str String
   * @return int
   */
  public int getBh(String str) {
    if(str==null || str.trim().equals(""))
      return 0;

    int tNum = getBsbh(str);
    char c = 0;
    for(int i=0; i<str.length(); i++) {
      c = str.charAt(i);
      tNum += getBh(c);
    }
    return tNum;
  }

  /**
   * �õ���ǰ���ֵıʻ�
   * @param c char
   * @return int
   */
  public int getBh(char c) {
    //�������С�һ�����������ġ��塢�����ߡ��ˡ��š�ʮ������Ҫ�ֱ�1��2��3��4��5��6��7��8��9��10����
    for(int i=0; i<num.length; i++) {
      if(c==num[i])
        return i;
    }

    int c1=32;  //32Ϊ�ո�
    int num = -1;
    Stack stack = new Stack();
    try {
      InputStreamReader f = new InputStreamReader(Public.getFileFromJar("/conf/bh.simon"));
      BufferedReader r = new BufferedReader(f);
      while(c1!=-1) {
        c1 = r.read();
        if(c1==32)
          continue;

        if(c1==c) {
          int num1 = ((Integer)stack.pop()).intValue();
          int num2 = ((Integer)stack.pop()).intValue();
          if(num2>'9')
            num2 = 32;
          //System.out.println("j="+j+"\tc1="+String.valueOf((char)c1)+"c2="+String.valueOf((char)c2)+"c3="+String.valueOf((char)c3)+";");
          num = Integer.valueOf(String.valueOf(new char[]{(char)num2,(char)num1}).trim()).intValue();
          r.close();
          stack = null;
          break;
        }
        //������
        stack.push(new Integer(c1));
      }
    }catch (FileNotFoundException ex) {
      Messages.error("DaoBh("+ex+")");
    }catch(IOException e) {
      Messages.error("DaoBh("+e+")");
    }
    return num+getBsbh(c);
  }

  public char gb2big5(char c) {
    int c1=32, c2; //32Ϊ�ո�
    BufferedReader rj = null;
    try {
    	InputStreamReader f = new InputStreamReader(Public.getFileFromJar("/conf/jf.simon"));
    	rj = new BufferedReader(f);
      while(c1!=-1) {
        c1 = rj.read();
        if(c1==32)
          continue;

        if(c1==c) {
          c2 = rj.read();
          rj.close();
          if(c2==32)
            c = (char)c1;
          else
            c = (char)c2;
          break;
        }
      }
    }catch (FileNotFoundException ex) {
      Messages.error("DaoBh("+ex+")");
    }catch(IOException e) {
      Messages.error("DaoBh("+e+")");
    }

    return c;
  }

  public String gb2big5(String s) {
    int c1=32;  //32Ϊ�ո�
    if(s==null || "".equals(s.trim()))
      return s;

    String str = "";
    char c ;
    for(int i=0; i<s.length(); i++) {
      c = s.charAt(i);
      str += gb2big5(c);
    }
    return str;
  }


  /**
   * ��һ�������ֿ���һ�������ֿ�ϲ���һһ��Ӧ����ʽ
   * �÷����ֿ�����word

  private void getJtAndFt() {
    int c1=32, c2=32;  //32Ϊ�ո�
    StringBuffer buff = new StringBuffer();
    try {
      BufferedReader rj = new BufferedReader(new FileReader(getCurrentClassPath() + "/bh_j.simon"));
      BufferedReader rf = new BufferedReader(new FileReader(getCurrentClassPath() + "/bh_f.simon"));
      BufferedWriter jf = new BufferedWriter(new FileWriter(getCurrentClassPath() + "/bh_jf.simon"));
      while(c1!=-1) {
        c1 = rj.read();
        c2 = rf.read();
        if(c1==32 || c2 == 32 || c1==c2)
          continue;
        buff.append((char)c1);
        buff.append((char)c2);
        buff.append(" ");
      }
      jf.write(buff.toString());
      jf.close();
      rj.close();
      rf.close();
    }catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  */

  /**
   * �õ�һ������ķ�����
   * @param c char
   * @return char
   */
  public char getFanTi(char c) {
    return c;
  }

  /**
   * �õ�һ���ַ�������ķ���
   * @param s String
   * @return String
   */
  public String getFanTi(String s) {
    return s;
  }

  public static void main(String[] args) throws Exception {
//    DaoBh bh = new DaoBh();
//    String s = "���л�";
//    String s1 = bh.gb2big5(s);
//    System.out.print(s+"-"+bh.getBh(s)+"��"+s1+"-"+bh.getBh(s1)+"\t");
//
//    char c ;
//    for(int i=0; i<s.length(); i++) {
//      c = s1.charAt(i);
//      System.out.print(c+"-"+bh.getBh(c)+"��\t");
//    }
  	String path = DaoBh.class.getResource("bh.simon").getPath();
  	System.out.println(path);
  }
}
