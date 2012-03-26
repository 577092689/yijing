package org.boc.dao.ly;

import org.boc.dao.DaoCalendar;
import org.boc.db.Calendar;
import org.boc.db.YiJing;

public class LiuyaoPublic {
	private DaoYiJingMain daoly;
  private DaoCalendar daocal;
  
	public LiuyaoPublic(DaoYiJingMain daoly, DaoCalendar daocal) {
		this.daoly = daoly;
		this.daocal = daocal;
	}
	public DaoYiJingMain getDaoYiJingMain() {
		return daoly;
	}
	public DaoCalendar getDaoCalendar() {
		return daocal;
	}
	
	//���ַ���ת���ɶ�س����
	public int[] getActs(String sact) {
		if(sact==null || sact.trim().equals("")) return null;
		
		int[] chgs;
		String[] s = sact.split(",");
    int j=0;
    
    for(int i=0; i<s.length; i++) {
      if(s[i]==null || s[i].trim().equals("0") || "".equals(s[i].trim()))
        continue;
      j++;
    }    
    chgs = new int[j];
    j = 0;
    for(int i=0; i<s.length; i++) {
      if(s[i]==null || s[i].trim().equals("0") || "".equals(s[i].trim()))
        continue;
      chgs[j++] = Integer.valueOf(s[i]).intValue();
    }
    
    return chgs;
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
	
	/**
   * ����ݵõ����֧
   */
  public int[] getYearGanzi(int year) {
  	int bg = (year - Calendar.IYEAR + Calendar.IYEARG) % 10 == 0 ? 10 :
      (year - Calendar.IYEAR + Calendar.IYEARG) % 10;
  	int bz = (year - Calendar.IYEAR + Calendar.IYEARZ) % 12 == 0 ? 12 :
      (year - Calendar.IYEAR + Calendar.IYEARZ) % 12;
  	return new int[]{bg, bz};
  }
  
  /**
   * �õ�Ѯ��
   */
  public String[] getXunKongs(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    int zi1 = xk/100;
    int zi2 = xk - zi1*100;

    String[] str = new String[2];
    str[0] = YiJing.DIZINAME[zi1];
    str[1] = YiJing.DIZINAME[zi2];
    return str;
  }
  public int[] getXunKongi(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    return new int[]{xk/100, xk%100};
  }
  
  private boolean isboy;
  public boolean isBoy() {
  	return isboy;
  }
  public void setBoy(boolean isboy){
  	this.isboy = isboy;
  }
}
