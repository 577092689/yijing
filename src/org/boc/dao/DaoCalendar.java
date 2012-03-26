package org.boc.dao;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Public;

public class DaoCalendar {

  /**
   * ��������ʱ�õ�����
   * 0 �Ƿ�����1��0��
   * 1-8 ����
   * 9-11 ����������
   * 12-14ȡ��ũ�������գ����ǲ�����������
   * 15-17 ��������
   * 18 ���ڼ�
   * @param yyyyy
   * @param mm
   * @param dd
   * @param hh
   * @param type ����Ϊ�棬����Ϊ��
   * @param yun ֻ��Ϊ����ʱ�˲�������Ч
   * @return
   */
  public int[] getSiZhu(int yyyy, int mm, int dd, int hh, int mi,
                        boolean isyun, boolean type) {
    int gYearn = 0, gMonthn = 0, gDayn = 0, gHour = hh*100+mi;  //�����۽�����
    int gYearp = 0, gMonthp = 0, gDayp = 0;                     //����
    int gYearn1 = 0, gMonthn1 = 0, gDayn1 = 0;                  //�������۽�����
    int gYun = 0, gDays = 0;
    int gYearg = 0, gYearz = 0, gMonthg = 0, gMonthz = 0, gDayg = 0, gDayz = 0, gHourg = 0, gHourz = 0;

    int days = 0;
    int _m = mm;
    int initDN = 0;
    int initMN = 0;
    int initYN = 0;
    int daysPerMN = 0;

    int initDP = 0;
    int initMP = 0;
    int initYP = 0;
    int daysPerMP = 0;

    //���������
    //    �������=����&&yue==true || ����>���´��¶�Ҫ��1
    int yunYue = getYunYue(yyyy);
    if(mm != yunYue)
      isyun = false;
    if(type)
      if((isyun && mm==yunYue) || (mm > yunYue && yunYue!=0))
        _m++;

    initDN = Calendar.IDAYN;
    initMN = Calendar.IMONTH;
    initYN = Calendar.IYEAR;
    initDP = Calendar.IDAYP;
    initMP = Calendar.IMONTH;
    initYP = Calendar.IYEAR;

    while (yyyy != (type ? initYN : initYP) ||
           _m != (type ? initMN : initMP) ||
           dd != (type ? initDN : initDP)) {
      initDN++;
      initDP++;
      days++;

      //��˳��ȡ���������ĵ�13���¿���Ϊ0
      //�������ȡ�������£���ȡ����λ��
      daysPerMN = getYinDays(initYN, initMN);
      if(daysPerMN > 32)
        daysPerMN -= daysPerMN/100 * 100;
      daysPerMP = getYangDays(initYP, initMP);

      if (initDN > daysPerMN ) {
         initDN = 1;
         initMN++;
      }
      //��ֹ������������ͬʱ����
      if(initMN == 13) {
        daysPerMN = getYinDays(initYN, initMN);
        if (daysPerMN > 32)
          daysPerMN -= daysPerMN / 100 * 100;
        if (daysPerMN == 0) {
          initMN++;
        }
      }

      //ֻ��12���£��������13��������һ��ѭ����һ�꣬�������ּ���1
      if (initMN > 13) {
        initMN = 1;
        initYN++;
      }

      if (initDP > daysPerMP ) {
         initDP = 1;
         initMP++;
      }

      //ֻ��12���£��������13��������һ��ѭ����һ�꣬�������ּ���1
      if (initMP > 12) {
        initMP = 1;
        initYP++;
      }
    }

    if(type) {
      gYearn = yyyy; //������initYN
      gMonthn = mm; //������initMN����Ϊ��������»�������¿���Ҫ��1������
      gDayn = dd; //������initDN
      gYun = isyun?1:0;
    }else {
      gYearn = initYN;
      gMonthn = initMN;
      gDayn = initDN;

      //��������»��ߴ������£���Ҫ�·ݼ�һ
      gYun = Calendar.yinli[initYN - Calendar.IYEAR][initMN] > 32 ? 1:0;
      if(gYun == 1) {
        gMonthn--;
      } else if(getYunYue(initYN) != 0 && initMN > getYunYue(initYN)) {
        gMonthn--;
      }
    }
    gYearp = initYP;
    gMonthp = initMP;
    gDayp = initDP;
    gDays = days;

    //�������ȷ�����յ�ũ����
    gYearn1 = gYearn;
    //�������ȷ�����յ�ũ����
    gMonthn1 = gMonthn;
    gDayn1 = gDayn;

    int y = gYearn;
    int m = gMonthn;  //��������ٶ����
    int actTime = gMonthn*1000000+gDayn*10000+gHour;
    int yueOfJieqi = Calendar.jieqi[gYearn - Calendar.IYEAR][m]/1000000;
    int jie = Calendar.jieqi[gYearn - Calendar.IYEAR][m];
    int breaker1 = 0;
    int breaker2 = 0;
    boolean bYun = (gYun == 1 ? true:false);

    while(true) {
      if ( (actTime >= jie && !bYun && !isYunYue(y, m)) ||
          (actTime >= jie && !bYun && gMonthn != yueOfJieqi && isYunYue(y, m)) ||
          (actTime >= jie && bYun) ||
          (actTime < jie && bYun && gMonthn == yueOfJieqi && !isYunYue(y, m)) ||
          (actTime < jie && Math.abs(gMonthn - yueOfJieqi) > 5)) {
       if(breaker2 > 0) {
         gMonthn = m;
         gYearn = y;
         break;
       }
       //����ýڵ��·�С�������£�����ֹ����������һ����
       if(yueOfJieqi < gMonthn && breaker1 != 0) {
         if(--m == 0) {
           gMonthn = 12;
           gYearn = --y;
         }else{
           gMonthn = m;
           gYearn = y;
         }
         break;
       }
        m++;
        if (m == 13) {
          m = 1;
          y++;
        }
        breaker1++;
      }else{
        if(breaker1 > 0) {
          if(--m == 0) {
            gMonthn = 12;
            gYearn = --y;
          }else{
            gMonthn = m;
            gYearn = y;
          }
          break;
        }
        if((yueOfJieqi > gMonthn && breaker2!=0) ||
            yueOfJieqi > gMonthn && breaker2==0 && yueOfJieqi - gMonthn>=6) {
          gMonthn = m;
          gYearn = y;
          break;
        }
        gMonthn = --m;
        if (m == 0) {
          m = 12;
          y--;
        }
        breaker2++;
      }
      yueOfJieqi = Calendar.jieqi[y - Calendar.IYEAR][m] / 1000000;
      jie = Calendar.jieqi[y - Calendar.IYEAR][m];
    }

    //�˴��������
    int year = gYearn - Calendar.IYEAR;
    gYearg = (year+7) % 10 == 0 ? 10 : (year+7) % 10;
    gYearz = (year+7) % 12 == 0 ? 12 : (year+7) % 12;

    //���¸ɣ���ʼ��ɣ�����������������
    int yg = SiZhu.yueByNian[gYearg];
    gMonthz = (gMonthn + 2) % 12 == 0 ? 12 : (gMonthn + 2) % 12;
    int yuezhu = gMonthz;
    if (yuezhu < YiJing.YIN)
      yuezhu += 12;
    gMonthg = (yg + yuezhu - 3) % 10 == 0 ? 10 : (yg + yuezhu - 3) % 10;

    //����
    gDayg = (gDays+7)%10==0 ? 10: (gDays+7)%10;
    gDayz = (gDays+5)%12==0 ? 12: (gDays+5)%12;

    //ʱ��
    int sg = SiZhu.shiByRi[gDayg];
    int i=1;
    for(; i<=12; i++) {
      if(SiZhu.HOURNUM[i] > gHour && i==1) {
        break;  //������ʱ
      }else if(SiZhu.HOURNUM[i] <= gHour && i==12) {
        i=1;    //�ڶ�����ʱ
        gDayg = (gDayg+1)%10==0?10:(gDayg+1)%10;
        gDayz = (gDayz+1)%12==0?12:(gDayz+1)%12;
        sg = SiZhu.shiByRi[gDayg];
        break;
      }else if(SiZhu.HOURNUM[i] <= gHour && SiZhu.HOURNUM[i+1] > gHour) {
        i++;   //����ʱ��
        break;
      }
    }
    gHourz = i;
    gHourg = (gHourz+sg-1)%10==0 ? 10 : (gHourz+sg-1)%10;

    int week = 0;
    if(days > 0 )
      week = (days+Calendar.IWEEK)%7==0 ? 7:(days+Calendar.IWEEK)%7;

    return new int[]{gYun,
        gYearg, gYearz, gMonthg, gMonthz, gDayg, gDayz, gHourg, gHourz,
        gYearp, gMonthp, gDayp,
        gYearn1, gMonthn1, gDayn1,
        gYearn, gMonthn, gDayn, week};
  }

  /**
   * ��ʱ��õ�ͷ��
   * @param sj 0�Ƿ����� 1-8���� 9-11���������� 12-14ȡ��ũ�������գ����ǲ�����������
   * 15-17 �������� 18 ���ڼ�
   * @param sheng,shi -1Ϊ������̫��ʱ
   */
  public String getShiJian(int[] sj,int h, int mi,int sheng,int shi, String born, boolean sex) {
    StringBuilder sb = new StringBuilder();
    int sunTrue = 0;
    boolean isSunTrue = false;
    if(sheng>=0 && shi>=0) {
      isSunTrue = true;
    }else{
      isSunTrue = false;
    }

    if(isSunTrue) {
      sunTrue = (Calendar.jingdu[sheng][shi] - 120 * 60) * 4;
      sunTrue += Calendar.zpsc[sj[10]][sj[11]];
    }

    sb.append("������"+sj[9]+"��"+sj[10]+"��"+sj[11]+"�� "+Calendar.WEEKNAME[sj[18]]);
    sb.append("\n    ũ����"+sj[12]+"��");
    sb.append(sj[0]==1 ? "��":"");
    sb.append(Public.DAXIAO[sj[13]]+"�³�"+Public.DAXIAO[sj[14]]+" "+h+":"+mi);
    int h1 = (h*60*60+mi*60+sunTrue)/60/60;
    int mi1 = (h*60*60+mi*60+sunTrue)/60 - h1*60;
    int hour2 = h1*100+mi1;
    if(isSunTrue) {
    	sb.append("\n"+new DaoYiJingMain().getRepeats(" ", YiJing.INTER[0]));
    	sb.append("������̫��ʱ��" + sj[12] + "��" );
    	sb.append(sj[0]==1 ? "��" : "");
    	sb.append(sj[13] + "�³�" + sj[14] + " " + h1 + ":" + mi1) ;
    }
    
//    int iborn = 0;
//    try{
//    	iborn = Integer.valueOf(born);
//    }catch(Exception e) {}
//    if(iborn!=0 && iborn > Calendar.IYEAR && iborn <Calendar.MAXYEAR) {
//      int bg = (iborn - Calendar.IYEAR + Calendar.IYEARG) % 10 == 0 ? 10 :
//          (iborn - Calendar.IYEAR + Calendar.IYEARG) % 10;
//      int bz = (iborn - Calendar.IYEAR + Calendar.IYEARZ) % 12 == 0 ? 12 :
//          (iborn - Calendar.IYEAR + Calendar.IYEARZ) % 12;
//      sb.append("\n    ������");
//      sb.append(born + "������" + (sex ? "Ǭ��" : "����"));
//      sb.append("��" + YiJing.TIANGANNAME[bg] + YiJing.DIZINAME[bz]);
//    }else if(born.split(",").length>1) { //����Ǹ�֧��ʽ
//    	int bg = Integer.valueOf(born.split(",")[0]);
//    	int bz = Integer.valueOf(born.split(",")[1]);
//    	sb.append("\n    ������");
//      sb.append(sex ? "Ǭ��" : "����");
//      sb.append("��" + YiJing.TIANGANNAME[bg] + YiJing.DIZINAME[bz]);
//    }

    return sb.toString();
  }

  /**
   * �õ�ָ���������������������ʼ����������������������ڼ�, ��ʱ������1����
   * @param type ����Ϊ�棬����Ϊ��
   * @param yuen ֻ��Ϊ����ʱ�˲�������Ч
   */
  public void calculate(int y, int m, int d, int h,int mi, boolean type, boolean yun,int sheng ,int shi) {
    transYinYangDate(y,m,d,type,yun);
    getWeek();
    //Calendar.HOUR = h*100+mi;
    //�õ���̫��ʱ��,��; �õ������µ�����ʱ��
    int sunTrue = 0;
    Calendar.HOUR = h*100+mi;
    //���������̫��ʱ�����뽫���Ϊȫ�ֱ������Ա�ָ�ʱʹ�ã�simon(2011-12-14)
    if(sheng>=0 && shi>=0) {
      Calendar.isSunTrue = true;
      Calendar.PROVINCE = sheng;
      Calendar.CITY = shi;
    }else {
    	Calendar.isSunTrue = false;
    }
//    if(sheng<=0 && shi<=0 && Calendar.isSunTrue && Calendar.PROVINCE!=-1 && Calendar.CITY!=-1){
//      sheng = Calendar.PROVINCE;
//      shi = Calendar.CITY;
//    }

    if(Calendar.isSunTrue) {
      sunTrue = (Calendar.jingdu[sheng][shi] - 120 * 60) * 4;
      sunTrue += Calendar.zpsc[Calendar.MONTHP][Calendar.DAYP];
    }

    SiZhu.yinli = "��������ʱ�䣺"+Calendar.YEARN+"��"+(Calendar.YUN ? "��":"") +
                  Calendar.MONTHN+"�³�"+Calendar.DAYN+" "+h+":"+mi+" "+
                  Calendar.WEEKNAME[Calendar.WEEK];
    int h1 = (h*60*60+mi*60+sunTrue)/60/60;
    int mi1 = (h*60*60+mi*60+sunTrue)/60 - h1*60;
    Calendar.HOUR2 = h1*100+mi1;
    if(Calendar.isSunTrue) {
      SiZhu.yinli += "\n"+new DaoYiJingMain().getRepeats(" ", YiJing.INTER[0])+
          "������̫��ʱ��" + Calendar.YEARN + "��" +
          (Calendar.YUN ? "��" : "") +
          Calendar.MONTHN + "�³�" + Calendar.DAYN + " " + h1 + ":" + mi1 + " " +
          Calendar.WEEKNAME[Calendar.WEEK];
    }

    Calendar.YEARN1 = Calendar.YEARN;
    Calendar.MONTHN1 = Calendar.MONTHN;
    Calendar.DAYN1 = Calendar.DAYN;
    SiZhu.yangli = "����ʱ�䣺"+Calendar.YEARP+"--"+Calendar.MONTHP+"--"+Calendar.DAYP;
    //�����µĽڣ��������֧�������¸�֧,���֧��������
    getMonthGanZi();

    getRiZhu();
    //�����>23:00���ǵڶ����ˣ����ո�֧��ʱ�����ţ�����������ʱ+1��������û��Ҫ
    getShiZhu();
  }


  /**
   * �õ�ʱ��
   */
  public void getShiZhu() {
    SiZhu.sg = SiZhu.shiByRi[SiZhu.rg];
    getShiZi();
    SiZhu.sg = (SiZhu.sz+SiZhu.sg-1)%10==0 ? 10 : (SiZhu.sz+SiZhu.sg-1)%10;
  }


  /**
   * �õ�����
   */
  public void getRiZhu() {
    //if(Calendar.HOUR >= 2300)
    //  Calendar.DAYS++;
    SiZhu.rg = (Calendar.DAYS+7)%10==0 ? 10: (Calendar.DAYS+7)%10;
    SiZhu.rz = (Calendar.DAYS+5)%12==0 ? 12: (Calendar.DAYS+5)%12;
  }

  /**
   * �õ�ʱ���ĵ�֧
   * Calendar.HOURΪԭ����ʱ�䣬Calendar.HOUR2Ϊ��̫��ʱ
   */
  public void getShiZi() {
    int i=1;
    for(; i<=12; i++) {
      if(SiZhu.HOURNUM[i] > Calendar.HOUR2 && i==1) {
        break;
      }else if(SiZhu.HOURNUM[i] <= Calendar.HOUR2 && i==12) {
        i=1;
        SiZhu.rg = (SiZhu.rg+1)%10==0?10:(SiZhu.rg+1)%10;
        SiZhu.rz = (SiZhu.rz+1)%12==0?12:(SiZhu.rz+1)%12;
        SiZhu.sg = SiZhu.shiByRi[SiZhu.rg];
        break;
      }else if(SiZhu.HOURNUM[i] <= Calendar.HOUR2 && SiZhu.HOURNUM[i+1] > Calendar.HOUR2) {
        i++;
        break;
      }
    }
    SiZhu.sz = i;
  }

  /**
   * �õ��µĸ�֧
   * ��ȡ���µĽڴ��ڵ������С�����෴
   *    1. �������Ҵ��ڸ÷�����
   *    2. �����´��ڸ��������·ݲ����
   *    3. ���´��ڴ���
   *    4. ����С�ڷ��������·���ͬ
   *    5. С�ڴ������·����6������
   */
  public void getMonthGanZi() {
    int yun = 0;
    int y = Calendar.YEARN;
    int m = Calendar.MONTHN;  //��������ٶ����
    int actTime = Calendar.MONTHN*1000000+Calendar.DAYN*10000+Calendar.HOUR;
    int yueOfJieqi = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR][m]/1000000;
    int jie = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR][m];
    int breaker1 = 0;
    int breaker2 = 0;

    while(true) {
      if ( (actTime >= jie && !Calendar.YUN && !isYunYue(y, m)) ||
          (actTime >= jie && !Calendar.YUN && Calendar.MONTHN != yueOfJieqi && isYunYue(y, m)) ||
          (actTime >= jie && Calendar.YUN) ||
          (actTime < jie && Calendar.YUN && Calendar.MONTHN == yueOfJieqi && !isYunYue(y, m)) ||
          (actTime < jie && Math.abs(Calendar.MONTHN - yueOfJieqi) > 5)) {
       if(breaker2 > 0) {
         Calendar.MONTHN = m;
         Calendar.YEARN = y;
         break;
       }
       //����ýڵ��·�С�������£�����ֹ����������һ����
       if(yueOfJieqi < Calendar.MONTHN && breaker1 != 0) {
         if(--m == 0) {
           Calendar.MONTHN = 12;
           Calendar.YEARN = --y;
         }else{
           Calendar.MONTHN = m;
           Calendar.YEARN = y;
         }
         break;
       }
        m++;
        if (m == 13) {
          m = 1;
          y++;
        }
        breaker1++;
      }else{
        if(breaker1 > 0) {
          if(--m == 0) {
            Calendar.MONTHN = 12;
            Calendar.YEARN = --y;
          }else{
            Calendar.MONTHN = m;
            Calendar.YEARN = y;
          }
          break;
        }
        if((yueOfJieqi > Calendar.MONTHN && breaker2!=0) ||
            yueOfJieqi > Calendar.MONTHN && breaker2==0 && yueOfJieqi-Calendar.MONTHN>=6) {
          Calendar.MONTHN = m;
          Calendar.YEARN = y;
          break;
        }
        Calendar.MONTHN = --m;
        if (m == 0) {
          m = 12;
          y--;
        }
        breaker2++;
      }
      yueOfJieqi = Calendar.jieqi[y - Calendar.IYEAR][m] / 1000000;
      jie = Calendar.jieqi[y - Calendar.IYEAR][m];
    }

    /**
     * �õ�������������
     * ���98.1ũ�������������С��һ����98��1�µĽ��������������ܵ�97.12��ȥ
     * ���Ƶ�99.12�������ˣ��϶���һ������2000���1��
     * @since simon 2011-10-11 ���������������
     * ���2007.1.14����������ӽ���С�����Ľ�����2007.1.2����ˮ�������·ݲ�����Calendar.jieqi[77][1]����[77][2]
     * ���������Ҳ��һ������2007�꣬���Ծ�����bug
     */
    int jie1 = 0;
    /////////////////////////////////////////////////////////////
    Calendar.MINJIEN = Calendar.YEARN1;
    Calendar.MINJIEY = Calendar.jieqi[y - Calendar.IYEAR][Calendar.MONTHN]/1000000;    
    Calendar.MINJIER = (Calendar.jieqi[y - Calendar.IYEAR][Calendar.MONTHN] - Calendar.MINJIEY*1000000)/10000;
    //���㷨���ӵ����һ��������ʼ��飬�·����>=10�£��϶���ǰһ��ģ�һֱ�ҵ�һ������ǰ�����֮��ģ�ȡС�Ľ���
    //�������������Ҫ��������
    //����ȵ�ǰ��ͷ�Ľ���С��ȵ�ǰ��β�Ľ����󣬻���ȡ��һ�����һ�̫꣬���ӣ�û��Ҫʵ����
//    int jieqiyear = Calendar.YEARN1;
//    for(int i=1; i<=24; i++) {
//    	if(i<=2 && Calendar.jieqi[y - Calendar.IYEAR][i]/1000000>=10) 
//    		jieqiyear = Calendar.YEARN1 - 1;
//    	else 
//    		jieqiyear = Calendar.YEARN1;
//    	if(jieqiyear*100000000+Calendar.jieqi[y - Calendar.IYEAR][i] < Calendar.YEARN1*1000000+Calendar.MONTHN1*1000000+Calendar.DAYN1*10000 &&
//    			jieqiyear*100000000+Calendar.jieqi[y - Calendar.IYEAR][i] < Calendar.YEARN1*1000000+Calendar.MONTHN1*1000000+Calendar.DAYN1*10000)
//    		
//    		
//    }
    ////////////////////////////////////////////////////////////
    if(Calendar.MONTHN<12) {
      jie1 = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR][Calendar.MONTHN+1];
      Calendar.MAXJIEN = Calendar.YEARN;
    }
    else {
      jie1 = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR + 1][1];
      if(jie1/1000000 > 5) {
        Calendar.MAXJIEN = Calendar.YEARN;
      }else {
        Calendar.MAXJIEN = Calendar.YEARN + 1;
      }
    }
    Calendar.MAXJIEY = jie1/1000000;
    Calendar.MAXJIER = (jie1 - Calendar.MAXJIEY * 1000000)/10000;

    //�˴��������
    getYearGanZi();
    //��ʼ��ɣ�����������������
    int yg = SiZhu.yueByNian[SiZhu.ng];
    SiZhu.yz = (Calendar.MONTHN+2)%12 == 0 ? 12 : (Calendar.MONTHN+2)%12;
    int yuezhu = SiZhu.yz;
    if(yuezhu < YiJing.YIN)
      yuezhu += 12;
    SiZhu.yg = (yg+yuezhu-3)%10 == 0 ? 10 : (yg+yuezhu-3)%10;
  }

  /**
   * �õ���ĸ�֧
   * 1930 ����
   */
  public void getYearGanZi() {
    int year = Calendar.YEARN - Calendar.IYEAR;
    SiZhu.ng = (year+7) % 10 == 0 ? 10 : (year+7) % 10;
    SiZhu.nz = (year+7) % 12 == 0 ? 12 : (year+7) % 12;
  }

  /**
   * �õ����ڼ������������
   * @param days
   * @return
   */
  public int getWeek() {
    if(Calendar.DAYS == 0 )
      Calendar.WEEK = 0;
    Calendar.WEEK = (Calendar.DAYS+Calendar.IWEEK)%7==0 ? 7:(Calendar.DAYS+Calendar.IWEEK)%7;
    return Calendar.WEEK;
  }

  /**
   * �õ�ָ���������������������ʼ�����������
   * �õ�����Ӧ������������
   * @param type ����Ϊ�棬����Ϊ��
   * @param yuen ֻ��Ϊ����ʱ�˲�������Ч
   */
  public void transYinYangDate(int y, int m, int d, boolean type, boolean yun) {
  	//System.out.println(y+"; m="+m+"; d="+d+";type="+type+"; yun="+yun);
    int days = 0;
    int _m = m;
    int initDN = 0;
    int initMN = 0;
    int initYN = 0;
    int daysPerMN = 0;

    int initDP = 0;
    int initMP = 0;
    int initYP = 0;
    int daysPerMP = 0;
    
    int yunYue = getYunYue(y);  //�õ���������������
    if(m != yunYue)
      yun = false;
    if(type)  //���������������=����&&yue==true || ����>���´��¶�Ҫ��1�� ��Ϊ�������һ��
      if((yun && m==yunYue) || (m > yunYue && yunYue!=0))
        _m++;

    initDN = Calendar.IDAYN;
    initMN = Calendar.IMONTH;
    initYN = Calendar.IYEAR;
    initDP = Calendar.IDAYP;
    initMP = Calendar.IMONTH;
    initYP = Calendar.IYEAR;

    while (y != (type ? initYN : initYP) ||
           _m != (type ? initMN : initMP) ||
           d != (type ? initDN : initDP)) {
      initDN++;		//����������1
      initDP++;		//����������1
      days++;			//��������1

      daysPerMN = getYinDays(initYN, initMN);   //����ÿ������      
      if (initDN > daysPerMN ) {  //������������ȸ����������ˣ����1�����ۼƣ������·ݼ�1
         initDN = 1;
         initMN++;
      }           
      if(initMN == 13) {  //��ֹ������������ͬʱ���㣬�����µĻ���13�����Ǵ��ڵģ��������������һ����
        daysPerMN = getYinDays(initYN, initMN);
        if (daysPerMN == 0) {
          initMN++;
        }
      }      
      if (initMN > 13) {	//���ֻ��13���£��������13������ݼ�1�����·�����1
        initMN = 1;
        initYN++;
      }
      
      daysPerMP = getYangDays(initYP, initMP);	//����ÿ������
      if (initDP > daysPerMP ) { //�ۼ���>ÿ�����������ʼ��������Ϊ1�������·ݼ�1
         initDP = 1;
         initMP++;
      }      
      if (initMP > 12) {  //����ֻ��12���£��������13��������һ��ѭ����һ�꣬������������Ϊ1
        initMP = 1;
        initYP++;
      }
    }

    if(type) {
      Calendar.YEARN = y; //������initYN
      Calendar.MONTHN = m; //������initMN����Ϊ��������»�������¿���Ҫ��1������
      Calendar.DAYN = d; //������initDN
      Calendar.YUN = yun;
    }else {
      Calendar.YEARN = initYN;
      Calendar.MONTHN = initMN;
      Calendar.DAYN = initDN;

      //��������»��ߴ������£���Ҫ�·ݼ�һ
      Calendar.YUN = Calendar.yinli[initYN - Calendar.IYEAR][initMN] > 32;
      if(Calendar.YUN) {
        Calendar.MONTHN--;
      } else if(getYunYue(initYN) != 0 && initMN > getYunYue(initYN)) {
        Calendar.MONTHN--;
      }
    }

    Calendar.YEARP = initYP;
    Calendar.MONTHP = initMP;
    Calendar.DAYP = initDP;

    Calendar.DAYS = days;
  }

  /**
   * �õ�ָ������������������������
   */
  public int getDiffDates() {
    if(Calendar.YEARN1==Calendar.IYEAR &&
       Calendar.MONTHN1 == Calendar.IMONTH &&
       Calendar.DAYN1 == Calendar.IDAYN)
      return 30;
    int days = 0;
    int _m = Calendar.MONTHN1;
    int _d = 0, _y = 0;
    int iy = 0;
    int im = 0;
    int id = 0;
    int daysPerMonth = 0;

    //���������
    //    �������=����&&yue==true || ����>���´��¶�Ҫ��1
    int yunYue = getYunYue(Calendar.YEARN1);
    if((Calendar.YUN && Calendar.MONTHN1==yunYue) || (Calendar.MONTHN1 > yunYue && yunYue!=0))
        _m++;

    //����
    DaoSiZhuMain dao = new DaoSiZhuMain();
    if(dao.getSexChar() == 1) {
      iy = Calendar.YEARN1;
      im = _m;  //��������Ҵ������£������1����Ϊȡ������ʱ�޲�����
      id = Calendar.DAYN1;

      _y = Calendar.MAXJIEN;
      _m = Calendar.MAXJIEY;
      _d = Calendar.MAXJIER;
      //�����1����Ϊȡ������ʱ�޲�����
      //_m=���� ���·�-1>�Ľ������·�=��������
      //_m>���µ�
      int yy = getYunYue(_y);
      if((_m == yy && Calendar.MINJIEY == yy) || (_m > yy && yy>0))  ++_m;
    }else{
      //����
      _y = Calendar.YEARN1;
      //_m�Ѿ���ʼ����ֵ��
      _d = Calendar.DAYN1;

      iy = Calendar.MINJIEN;
      im = Calendar.MINJIEY;
      id = Calendar.MINJIER;
      int yy = getYunYue(iy);
      if((im == yy && Calendar.MAXJIEY > yy) || (im > yy && yy>0))  ++im;
    }

    while (_y != iy || _m != im || _d != id) {
      id++;
      days++;

      //��˳��ȡ���������ĵ�13���¿���Ϊ0
      //�������ȡ�������£���ȡ����λ��
      daysPerMonth = getYinDays(iy, im);
      if(daysPerMonth > 32)
        daysPerMonth -= daysPerMonth/100 * 100;

      if (id > daysPerMonth ) {
         id = 1;
         im++;
      }
      //��ֹ������������ͬʱ����
      if(im == 13) {
        daysPerMonth = getYinDays(iy, im);
        if (daysPerMonth > 32)
          daysPerMonth -= daysPerMonth / 100 * 100;
        if (daysPerMonth == 0) {
          im++;
        }
      }

      //ֻ��12���£��������13��������һ��ѭ����һ�꣬�������ּ���1
      if (im > 13) {
        im = 1;
        iy++;
      }
    }
    //System.err.println("days = "+days);
    return days;
  }

  /**
   * �õ�ָ��������������������������Ϊĳ��ĳ��˾����
   */
  public int getDiffDatesForSiLing() {
     if(Calendar.YEARN <= Calendar.IYEAR &&
       Calendar.MONTHN <= Calendar.IMONTH &&
       Calendar.DAYN <= Calendar.IDAYN)
      return 1;
    int days = 0;
    int _m = Calendar.MONTHN1;
    int _d = 0, _y = 0;
    int iy = 0;
    int im = 0;
    int id = 0;
    int daysPerMonth = 0;

    //���������
    //    �������=����&&yue==true || ����>���´��¶�Ҫ��1
    int yunYue = getYunYue(Calendar.YEARN1);
    if((Calendar.YUN && Calendar.MONTHN1==yunYue) || (Calendar.MONTHN1 > yunYue && yunYue!=0))
        _m++;

    DaoSiZhuMain dao = new DaoSiZhuMain();

    _y = Calendar.YEARN1;
    //_m�Ѿ���ʼ����ֵ��
    _d = Calendar.DAYN1;

    iy = Calendar.MINJIEN;
    im = Calendar.MINJIEY;
    id = Calendar.MINJIER;
    int yy = getYunYue(iy);
    if ( (im == yy && Calendar.MAXJIEY > yy) || (im > yy && yy > 0))
      ++im;

    while (_y != iy || _m != im || _d != id) {
    	if(id>=30 && im>=12 && iy>=Calendar.MAXYEAR) {
    		return 10;  //�㷨̫���ӣ����ʵ��û�ҵ�������ȱʡ����10��
    	}
      id++;
      days++;

      //��˳��ȡ���������ĵ�13���¿���Ϊ0
      //�������ȡ�������£���ȡ����λ��
      daysPerMonth = getYinDays(iy, im);
      if(daysPerMonth > 32)
        daysPerMonth -= daysPerMonth/100 * 100;

      if (id > daysPerMonth ) {
         id = 1;
         im++;
      }
      //��ֹ������������ͬʱ����
      if(im == 13) {
        daysPerMonth = getYinDays(iy, im);
        if (daysPerMonth > 32)
          daysPerMonth -= daysPerMonth / 100 * 100;
        if (daysPerMonth == 0) {
          im++;
        }
      }

      //ֻ��12���£��������13��������һ��ѭ����һ�꣬�������ּ���1
      if (im > 13) {
        im = 1;
        iy++;
      }
    }
    //System.err.println("days = "+days);
    return days;
  }

  /**
   * �õ�ָ������µ������������޲������£���˳��ȡһ��13����/��
   */
  public int getYinDays(int y, int m) {
    //��Ӧ��ֱ������ֵ���������ʱ
    if(y<Calendar.IYEAR) return 29;
    //System.out.print(y);
    //simon ע�͵�������У�2011-11-16
    //if(y - Calendar.IYEAR>=81)
    //System.out.println((y - Calendar.IYEAR)+",m="+m);
    
    int days = Calendar.yinli[y - Calendar.IYEAR][m];
    if(days>32)  //�����429����Ϊ429-400
      days -= days/100 * 100;

    return days;
  }

  /**
   * �õ�ָ������µ������������в�������
   * @param yun ��Ϊ���£���
   */
  public int getYinDays(int y, int m, boolean yun) {
    int yunYue = getYunYue(y);
    int days = 0;

    if(yunYue == 0) {
      return Calendar.yinli[y - Calendar.IYEAR][m];
    }

    if(m < yunYue || (!yun && m==yunYue)) {
      days = Calendar.yinli[y - Calendar.IYEAR][m];
    }else if((yun && m==yunYue)) {
      days = Calendar.yinli[y - Calendar.IYEAR][++m];
      days -= days/100 * 100;
    } else {
      days = Calendar.yinli[y - Calendar.IYEAR][++m];
    }

    return days;
  }

  /**
   * �жϵ�ǰ��ĵ�ǰ��(����ʵ���·ݣ�ֻ�����)�Ƿ�������
   * @param <any>
   * @return
   */
  public boolean isYunYue(int y, int m) {
    int days = Calendar.yinli[y - Calendar.IYEAR][m];
    return days>32;
  }

  /**
   * �õ����������
   */
  public int getYunYue(int y){
    if(y==0) return y;
    int[] yue = Calendar.yinli[y - Calendar.IYEAR];
    int j = 0;
    for(int i = 1; i<13; i++) {
      if(yue[i] > 32) {
        j = yue[i]/100;
        break;
      }
    }
    return j;
  }

  /**
   * �õ�ָ������µ���������
   */
  public int getYangDays(int y, int m) {
    int[] mday = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 0};
    int x = y%4==0 && (y%100!=0 || y%400==0) ? 29:28;
    return m == 2 ? x : mday[m];
  }

  public static void main(String[] args) throws Exception{
    DaoCalendar d = new DaoCalendar();
    long t1 = System.currentTimeMillis();
    //d.calculate(1977,3,23,6,31,true,true,0,0);
    //Debug.out(SiZhu.yinli+"\r\n"+SiZhu.yangli);
    //int[] gz = d.getSiZhu(2001,4,25,11,30,false,false);
//    int[] gz = d.getSiZhu(2004,2,1,0,30,true,false);
    
    d.transYinYangDate(2010,12,30,false, true);
    d.transYinYangDate(2013,1,30,false, true);
//    System.out.println("���֣� "+(gz[0]==1?"��  ":"  ")+YiJing.TIANGANNAME[gz[1]] + YiJing.DIZINAME[gz[2]] +
//                       "\t"+ YiJing.TIANGANNAME[gz[3]] + YiJing.DIZINAME[gz[4]] +
//                       "\t"+ YiJing.TIANGANNAME[gz[5]] + YiJing.DIZINAME[gz[6]] +
//                       "\t"+ YiJing.TIANGANNAME[gz[7]] + YiJing.DIZINAME[gz[8]] );

    System.out.println("��ʱ��"+(System.currentTimeMillis()-t1)+"ms");
  }
}
