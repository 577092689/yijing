package org.boc.model;

import javax.swing.table.*;

import java.util.Collection;
import java.util.Iterator;

import org.boc.util.*;
import org.boc.db.Calendar;
import org.boc.db.YiJing;
import org.boc.db.Xuank;
import org.boc.db.BaZhai;

public class DataTableModel
    extends AbstractTableModel {

  private String[] titles;
  private Class[] types;
  private Object data[][];
  private String fileId;

  public DataTableModel(String fileId, Collection coll) {
    this.fileId = fileId;
    setDatas(fileId, coll);
  }

  public DataTableModel(String fileId) {
    this.fileId = fileId;
    setDatas(fileId, null);
  }

  public int getRowCount() {
    return data.length;
  }

  public int getColumnCount() {
    return titles.length;
  }

  public String getColumnName(int c) {
    return titles[c];
  }

  public Class getColumnClass(int c) {
    return types[c];
  }

  public Object getValueAt(int r, int c) {
    return data[r][c];
  }

  public String getFileId() {
    return this.fileId;
  }

  /**
   * ���³�ʼ���б�����
   * @param fileId String
   * @param coll Collection
   */
  public void setDatas(String fileId, Collection coll) {
    //1. ��ʼ������
    if (fileId.equals(Public.valueRoot[1]) ||
        fileId.equals(Public.valueRoot[3]) ||
        fileId.equals(Public.valueRoot[4]) ||
        fileId.equals(Public.valueRoot[5]) ||
        fileId.equals(Public.valueRoot[10]) ||
        fileId.equals(Public.valueRoot[11])) { //���� ��΢ ���� ���� �ƹ� ���
      titles = new String[] {
          "Ԥ�����", "����ʡ��", "�����Ա�", "���ʱ��", "Ԥ��ʱ��"};
      types = new Class[] {
          String.class, String.class, String.class, String.class, String.class};
    }
    else if (fileId.equals(Public.valueRoot[2])) { //����
      titles = new String[] {
          "Ԥ�����", "����", "�Ա�", "���ʱ��", "Ԥ��ʱ��"};
      types = new Class[] {
          String.class, String.class, String.class, String.class, String.class};
    }
    else if (fileId.equals(Public.valueRoot[6])) { //��س
      titles = new String[] {
          "Ԥ�����", "���Է�ʽ", "ȡ����", "����", "����", "��س", "Ԥ��ʱ��"};
      types = new Class[] {
          String.class, String.class, String.class, String.class, String.class,
          String.class, String.class};
    }
    else if (fileId.equals(Public.valueRoot[7])) { //����
      titles = new String[] {
          "Ԥ�����", "ɽ��", "��������", "���ŷ���", "��������", "���·���", "Ԥ��ʱ��"};
      types = new Class[] {
          String.class, String.class, String.class, String.class, String.class,
          String.class, String.class};
    }
    else if (fileId.equals(Public.valueRoot[8])) { //��լ
      titles = new String[] {
          "Ԥ�����", "����ͷ", "����", "Ů��", "����", "����", "Ԥ��ʱ��"};
      types = new Class[] {
          String.class, String.class, String.class, String.class, String.class,
          String.class, String.class};
    }
    else if (fileId.equals(Public.valueRoot[9])) { //����
      titles = new String[] {
          "Ԥ��������", "Ԥ��ʱ��"};
      types = new Class[] {
          String.class, String.class};
    }
    else { //����
      titles = new String[] {
          "Ԥ�����", "Ԥ��ʱ��"};
    }
    //2. ��ʼ������
    if (coll == null || coll.size() < 1) {
      data = new Object[1][titles.length];
      return;
    }
    else {
      data = new Object[coll.size()][titles.length];
    }
    //3. ��������
    try {
      int i = 0;
      for (Iterator it = coll.iterator(); it.hasNext(); i++) {
        VO vo = (VO) it.next();
        data[i][0] = vo.getRowId();
        //-----------------------------------------------------------------------------
        if (fileId.equals(Public.valueRoot[1]) ||
            fileId.equals(Public.valueRoot[3]) ||
            fileId.equals(Public.valueRoot[4]) ||
            fileId.equals(Public.valueRoot[5]) ||
            fileId.equals(Public.valueRoot[10]) ||
            fileId.equals(Public.valueRoot[11])) { //���� ��΢ ���� ���� �ƹ� ���
          if (vo.getIsheng() >= 0 && vo.getIshi() >= 0)
            data[i][1] = Calendar.cityname[vo.getIsheng()][0] +
                Calendar.cityname[vo.getIsheng()][vo.getIshi()];
          else data[i][1] = "";
          data[i][2] = vo.isIsBoy() ? "�к�" : "Ů��";
          if (vo.getYear() > 0)
            data[i][3] = vo.getYear() + "-" + vo.getMonth() + "-" + vo.getDay() +
                " " + vo.getHour() + ":" + vo.getMinute();
          else {
            data[i][3] = YiJing.TIANGANNAME[vo.getNg()] +
                YiJing.DIZINAME[vo.getNz()] + " " +
                YiJing.TIANGANNAME[vo.getYg()] + YiJing.DIZINAME[vo.getYz()] +
                " " +
                YiJing.TIANGANNAME[vo.getRg()] + YiJing.DIZINAME[vo.getRz()] +
                " " +
                YiJing.TIANGANNAME[vo.getSg()] + YiJing.DIZINAME[vo.getSz()];
          }
          data[i][4] = vo.getYcsj();
        }
        else if (fileId.equals(Public.valueRoot[2])) { //����
          if ( ( (VoLiuRen) vo).getIyb() > 0)
            data[i][1] = String.valueOf( ( (VoLiuRen) vo).getIyb());
          else
            data[i][1] = YiJing.TIANGANNAME[ ( (VoLiuRen) vo).getBg()] +
                YiJing.DIZINAME[ ( (VoLiuRen) vo).getBz()];
          data[i][2] = vo.isIsBoy() ? "�к�" : "Ů��";
          if (vo.getYear() > 0)
            data[i][3] = vo.getYear() + "-" + vo.getMonth() + "-" + vo.getDay() +
                " " + vo.getHour() + ":" + vo.getMinute();
          else {
            data[i][3] = YiJing.TIANGANNAME[vo.getNg()] +
                YiJing.DIZINAME[vo.getNz()] + " " +
                YiJing.TIANGANNAME[vo.getYg()] + YiJing.DIZINAME[vo.getYz()] +
                " " +
                YiJing.TIANGANNAME[vo.getRg()] + YiJing.DIZINAME[vo.getRz()] +
                " " +
                YiJing.TIANGANNAME[vo.getSg()] + YiJing.DIZINAME[vo.getSz()];
          }
          data[i][4] = vo.getYcsj();
        }
        else if (fileId.equals(Public.valueRoot[6])) { //��س
          VoLiuYao ly = (VoLiuYao) vo;
          String qgfs = "";
          if (ly.getQgfs() == 1) qgfs = "����������";
          if (ly.getQgfs() == 2) qgfs = "��ʱ��";
          if (ly.getQgfs() == 3) qgfs = "����ҡ��";
          data[i][1] = qgfs;
          data[i][2] = YiJing.LIUQINNAME[ly.getYs()];
          if (ly.getYs() == 0)
            data[i][2] = "��س";
          data[i][3] = YiJing.JINGGUANAME[ly.getUp()];
          data[i][4] = YiJing.JINGGUANAME[ly.getDown()];
          String dy = "";
          if(ly.getDy()!=null && ly.getDy().trim().length()>0) {
	          String[] changs = ly.getDy().split(",");
	          for (int i2 = 0; i2 < changs.length; i2++) {
	            dy += changs[i2].equals("1") ? "��," : "";
	            dy += changs[i2].equals("2") ? "��," : "";
	            dy += changs[i2].equals("3") ? "��," : "";
	            dy += changs[i2].equals("4") ? "��," : "";
	            dy += changs[i2].equals("5") ? "��," : "";
	            dy += changs[i2].equals("6") ? "��," : "";
	          }
	          dy = dy.length() > 0 ? dy.substring(0, dy.length() - 1) : dy;
          }
          data[i][5] = dy;
          data[i][6] = ly.getYcsj();
        }
        else if (fileId.equals(Public.valueRoot[7])) { //����
          VoXuanKong xk = (VoXuanKong) vo;
          data[i][1] = Xuank.s24name[xk.getSx()];
          data[i][2] = new String[] {
              "", "һ��", "����", "����", "����", "����", "����", "����", "����", "����"}
              [xk.getSyjy()];
          data[i][3] = BaZhai.fx[xk.getDm()];
          if (vo.getYear() > 0)
            data[i][4] = vo.getYear() + "-" + vo.getMonth() + "-" + vo.getDay() +
                " " + vo.getHour() + ":" + vo.getMinute();
          else {
            data[i][4] = YiJing.TIANGANNAME[vo.getNg()] +
                YiJing.DIZINAME[vo.getNz()] + " " +
                YiJing.TIANGANNAME[vo.getYg()] + YiJing.DIZINAME[vo.getYz()] +
                " " +
                YiJing.TIANGANNAME[vo.getRg()] + YiJing.DIZINAME[vo.getRz()] +
                " " +
                YiJing.TIANGANNAME[vo.getSg()] + YiJing.DIZINAME[vo.getSz()];
          }
          if (xk.getFxyear() > 0)
            data[i][5] = xk.getFxyear() + "-" + xk.getFxmonth() + "-" +
                xk.getFxday() +
                " " + xk.getFxhour() + ":" + xk.getFxminute();
          else {
            data[i][5] = YiJing.TIANGANNAME[xk.getFxng()] +
                YiJing.DIZINAME[xk.getFxnz()] + " " +
                YiJing.TIANGANNAME[xk.getFxyg()] + YiJing.DIZINAME[xk.getFxyz()] +
                " " +
                YiJing.TIANGANNAME[xk.getFxrg()] + YiJing.DIZINAME[xk.getFxrz()] +
                " " +
                YiJing.TIANGANNAME[xk.getFxsg()] + YiJing.DIZINAME[xk.getFxsz()];
          }
          data[i][6] = xk.getYcsj();
        }
        else if (fileId.equals(Public.valueRoot[8])) { //��լ
          VoBaZhai bz = (VoBaZhai) vo;
          //"����ͷ","����", "Ů��", "����","����", "Ԥ��ʱ��"
          String hjs = "";
          int[] ihjs = bz.getIHjs();
          for (int j = 0; j < ihjs.length; j++) {
            hjs += BaZhai.hj[ihjs[j]];
          }
          data[i][1] = hjs;
          data[i][2] = String.valueOf(bz.getYear());
          data[i][3] = String.valueOf(bz.getYearGirl());
          data[i][4] = BaZhai.fx[bz.getIWx()];
          data[i][5] = BaZhai.fx[bz.getIDmx()];
          data[i][6] = bz.getYcsj();
        }
        else if (fileId.equals(Public.valueRoot[9])) { //����
          data[i][0] = vo.getName();
          data[i][1] = vo.getYcsj();
        }
        else { //����
          data[i][1] = vo.getYcsj();
        }
        //---------------------------------------------------------------------------------
      }

      fireTableDataChanged();
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      Messages.error("DataTableModel("+ex+")");
    }
  }
}
