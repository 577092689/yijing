package org.boc.db;

public class TieBan {
  /**
   * ����������Ժ�������ɼ�
   * ��֧�������Ժ�ͼ������
   * ʱ������
   * ��֧����
   * �����������Ӧ�������������
   * ̫��������
   */
  public static final int[] tgs = {0,6,2,8,7,1,9,3,4,6,2};
  public static final int[][] dzs = new int[13][2];
  public static final boolean[] szyy = {false,
      true,true,true,true,true,true,
      false,false,false,false,false,false};
  public static final boolean[] yzyy = {false,
      true,false,true,false,true,false,
      true,false,true,false,true,false};
  public static final int[] htxt = {0,6,8,4,5,0,1,2,7,3};
  public static final int[] thtgs = {0,9,8,7,6,5,9,8,7,6,5};
  public static final int[] thdzs = {0,9,8,7,6,5,4,9,8,7,6,5,4};

  static {
    dzs[1]=new int[]{1,6};dzs[12]=new int[]{1,6};
    dzs[2]=new int[]{5,10};dzs[5]=new int[] {5,10};
    dzs[8]=new int[]{5,10};dzs[11]=new int[]{5,10};
    dzs[3]=new int[]{3,8};dzs[4]=new int[]{3,8};
    dzs[6]=new int[]{2,7};dzs[7]=new int[]{2,7};
    dzs[9]=new int[]{4,9};dzs[10]=new int[]{4,9};
  }
}
