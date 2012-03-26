package org.boc.dao.ly;

import org.boc.dao.DaoPublic;
import org.boc.db.YiJing;

public class DaoYiJingMain {

  /**
   * �õ�Ѯ�յ�س
   */
  public void getXunKongOut(StringBuffer str, int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    int zi1 = xk/100;
    int zi2 = xk - zi1*100;

    str.append("Ѯ�գ�");
    str.append("\n");
    str.append(getRepeats(" ", YiJing.INTER[0]));
    str.append(YiJing.DIZINAME[zi1]+", " + YiJing.DIZINAME[zi2]);
  }

  /**
   * �õ�Ӧ��
   */
  public void getYingQiOut(
      StringBuffer str, int zi, int local, int yuezi, int rigan, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int[] liuqin, int[] diziGong,
      int[] liuqinGong, boolean isFu) {
    //13����سѮ�շ������ַ��ճ壬ν֮��ʵ�����Ա��ն�֮��
    if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        if(YiJing.DZCHONG[zi][rizi] == 1) {
          str.append("\n");
          str.append(getRepeats(" ", YiJing.INTER[0]));
          str.append("Ӧ�ڣ�");
          str.append("��سѮ�շ����򰵶����ַ��ճ�ν֮��ʵ�����Ա��ն�֮��");
        }
      }
    }
    //9����سѮ�հ��������Գ�Ѯ���֮�ն�֮��
    if(!isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("Ӧ�ڣ�");
        str.append("��سѮ�հ��������Գ�Ѯ���֮�ն�֮��");
      }
    }
    //10����سѮ�շ��������Գ�Ѯ֮�ն�֮��
    if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("Ӧ�ڣ�");
        str.append("��سѮ�շ����򰵶������Գ�Ѯ֮�ն�֮��");
      }
    }
    //11����سѮ�գ��������ϣ����Գ�Ѯ�����֮�ն�֮��
    if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        if(isHe(str,rigan,rizi,yuezi,changes,dizi,diziBian,zi)) {
           str.append("\n");
           str.append(getRepeats(" ", YiJing.INTER[0]));
           str.append("Ӧ�ڣ�");
           str.append("��سѮ�շ����򰵶��ַ��س��������س���ա��ºϣ����Գ�Ѯ�����֮�ն�֮��");
        }
      }
    }
    //12����سѮ�գ��������壬���Գ�Ѯ�ϳ���֮�ն�֮��
    if(!isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        if(isJingChong(str,rigan,rizi,yuezi,changes,dizi,diziBian,zi)) {
           str.append("\n");
           str.append(getRepeats(" ", YiJing.INTER[0]));
           str.append("Ӧ�ڣ�");
           str.append("��سѮ�հ������壬���Գ�Ѯ�ϳ���֮�ն�֮��");
        }
      }
    }
    //8����س��Ĺ�����Գ�Ĺ֮���ն�֮��
    if(isRuMu(str,new int[]{local,0}, changes,rigan, liuqinGong, diziGong, liuqin, dizi,
             diziBian, isFu, rizi, yuezi, "", false)) {
     str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("Ӧ�ڣ�");
     str.append("��س��Ĺ�����Գ�Ĺ֮���ն�֮��");
   }
   //7����س��ʱ������������������Ϊ̫��������Ĺ�����ն�֮��
   if(isWxsqx(zi,yuezi)){
     if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
       if(isDongAndBianSheng(zi,changes,dizi,diziBian)) {
         str.append("\n");
         str.append(getRepeats(" ", YiJing.INTER[0]));
         str.append("Ӧ�ڣ�");
         str.append("��س��ʱ������������������Ϊ̫��������Ĺ�����ն�֮��");
       }
     }
   }
   //4�������س����ס���������֮�ն�֮�������񱻳壬���Ժ��ն�֮��
   if(isHe(str,rigan,rizi,yuezi,changes,dizi,diziBian,zi)) {
     str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("Ӧ�ڣ�");
     str.append("��س����ס�����Գ��֮�ն�֮��");
   }
   if(isDongAndBianChong(zi,changes,dizi,diziBian) || isRiYueChong(zi,rizi,yuezi)) {
     str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("Ӧ�ڣ�");
     str.append("��س����������س�壬���Ժ��ն�֮��");
   }
   //3���ճ������񣬻��ճ��ٶ�س��������س���񣬼��Ա��ն�֮��
   boolean bool1 = false;
   boolean bool2 = false;
   if(zi == rizi)
     bool1 = true;
    for(int i=0; i<changes.length; i++) {
      if(rizi == dizi[changes[i]]) {
        if(YiJing.DZLIUHE[dizi[changes[i]]][zi] == 1 ||
           YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[changes[i]]]][YiJing.DIZIWH[zi]] == 1) {
          bool2 = true;
          break;
        }
      }
    }
    if(bool1 || bool2) {
      str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("Ӧ�ڣ�");
     str.append("��س���ճ������ճ��ٶ�س��������س���񣬼��Ա��ն�֮��");
    }

    //2���������಻�������ճ��嶯֮ʱӦ�顣
    if(!isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isHasQuanForSilen(str,zi,yuezi,rigan,rizi,changes,dizi,diziBian,false)) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("Ӧ�ڣ�");
        str.append("��س���಻�������ճ��嶯֮ʱӦ�顣");

      }
    }
    //5����س��������������֮�ڷ��ܳ��¡�
    if(!isHasQuanForActive(str,zi,yuezi,rizi,changes,dizi,diziBian,local,rigan,false) ||
       !isHasQuanForSilen(str,zi,yuezi,rigan,rizi,changes,dizi,diziBian,false)){
      str.append("\n");
      str.append(getRepeats(" ", YiJing.INTER[0]));
      str.append("Ӧ�ڣ�");
      str.append("��س��������������֮�ڷ��ܳ��¡�");
    }
    //6����س�ܿˣ������ƿ�֮�ն�֮��
    if(isDongAndBianKe(zi,changes,dizi,diziBian,false)) {
      str.append("\n");
      str.append(getRepeats(" ", YiJing.INTER[0]));
      str.append("Ӧ�ڣ�");
      str.append("��س�ܿˣ������ƿ�֮�ն�֮��");
    }
  }

  /**
   * ���������ʱ
   * @param shijian
   */
  public void getNYRDOut(StringBuffer str, int[] shijian) {
    //SiZhu.yinli = null;
    str.append("\n    ��֧��");
    str.append(YiJing.TIANGANNAME[shijian[1]]);
    str.append(YiJing.DIZINAME[shijian[2]]);
    str.append("��");
    str.append("  ");
    str.append(YiJing.TIANGANNAME[shijian[3]]);
    str.append(YiJing.DIZINAME[shijian[4]]);
    str.append("�£���"+YiJing.DIZINAME[YiJing.DZCHONG2[shijian[4]]]+"��");
    str.append("  ");
    str.append(YiJing.TIANGANNAME[shijian[5]]);
    str.append(YiJing.DIZINAME[shijian[6]]);
    DaoPublic daoPub = new DaoPublic();
    str.append("�գ���"+daoPub.getXunKongOut(shijian[5],shijian[6])[0]+
               daoPub.getXunKongOut(shijian[5],shijian[6])[1]+"��");
    str.append("  ");
    str.append(YiJing.TIANGANNAME[shijian[7]]);
    str.append(YiJing.DIZINAME[shijian[8]]);
    str.append("ʱ");
    str.append("\n\n");
  }

  /**
   * �������ԭ�񡢳��񡢼���ȵ���Ҫ��Ϣ:

   һ�� ��������
   1. ����ſ�
      1.1 �м�������
      1.2 ��������
      1.3 λ���ڻ�����
      1.4 �������
   2. �½��ս�
      2.1 �½�֮����������
          a) �½��������������˼��
          b) �������ļ��е��������������԰��Թ����С���֧���о��ϡ�
      2.2 �ս�֮������Ĺ��
          a) �������Ĺ����ָ��ռ�ٹ��������ռ�ٹ�������س���ַ���������������Ĺ�У���Ϊ��Σ֮��
          b) ����Ĺ����س����س����Ϊ�ٹ�ǡ��Ĺ���ճ���������������س����������оȣ������������˳壬����Σ���ɡ�
          c) �붯Ĺ������س����س������Ϊ�ٹ����������������ж�س�ĵ�֧ǡ���Ǵ˹ٹ�س��Ĺ�⡣
          d) ��س����س����س����Ĺ����Ĺ�ķ�ʽ�ǳ�Ĺس�����ܳ��ա�
          e) ��س�붯س֮Ĺ����Ĺ����Ĺ����س�����Խ�Ĺ��
          f) ��س���س֮Ĺ��Ĺ��ʽ�����֣�һ�ǳ�س�����ǳ�Ĺ�����Ǻ�Ĺ����Ĺ֮������ҽԺ����������Ѩ���ⷿ������֮���塣
          d) ������Ĺ����س����سΪ��س������س֮��֧ǡ���Ƕ�س֮Ĺ��
      2.3 Ѯ��
          a) �����������в�ʵ����ա���α����ٵȺ��塣���飬���գ����лڣ�Ӧ�գ��Է���ʵ����Ӧ��գ�˫������ʵ���˻��ѳɡ�
          b) ������֮�£��������󡣷����ܡ�����֮�£������򼪡�
   3. ���¾�����س����Ȩ���ϻ�Ȩ����
      ��������Ȩ�����ۺϻ�Ȩ��������Ȩ�Ż��кϻ�Ȩ��û������ȨҲ��̸���Ϻϻ�Ȩ����������Ȩ����ۺϻ���
      3.1 �ա��·���
          a) ����ͬ��ͬȨ���������κ�س��Զ�����˺ϻ�Ȩ��
      3.2 ��س����Ȩ
          ����Ȩ������������س�����񡢳���ȣ�ֻ�ǲ��ܲ���ϻ���
          a) ��س�뾲س��壬���ж�س�򰵶�س�����У�һ�㲻�ۣ����ھ����л������ģ�����س��壬˫��˭Ҳ�岻��˭�����ۡ�
          b) �½���������سΪ�����ƣ���������Ȩ��
          c) �½������ྲس��������Ϊ���ƣ�������Ȩ�� �����պϡ�ֵ�ռ����±�������Ȩ��
          d) �ս�����س����س����֧֮������س���ж��䡢����֮س�ˡ�й����سʱ��Ϊ���ơ�������Ȩ��
          e) ����֮س���ճ��������Ϊ���ơ�������Ȩ��
          f) ��س���ա���������������������������Ȩ��
          g) ��س���ա���һ���ܿˣ�һ������Ҳ������Ȩ��
          h) ��س���ա���˫�����ܿˣ���������Ȩ��
          i) ��س���ա���һ������һ���ܿˣ���������Ȩ�ؼ������ж�س���򱳡������������ˣ���������Ȩ��
          j) ��سѮ�գ������������������ɷ���һ��������������ղ���������������ְ�ܡ�
          k) �����գ�����������Ϊ�ٿգ����շ�ֵ֮ʱ���á���������������ֵ���ã���ա���ɶ��ⲻ�˿գ����������ķ��������β������ġ�

      3.3 ��γ�Ϊ����س
          a) �½���ֵ��֮��س�����������Ʒ���Ϊ������
          b) �ս�����֧֮���ྲس��Ϊ��������س���������ˣ�����������������س����
          c) �ս�����֧֮������س���ж���֮س��֮Ϊ������
          d) ��س�������е����ྲس��Ϊ��������س���������ˣ�����������������س����
          e) ��س�������е�������س���ж��䡢����֮س��֮Ϊ������
          f) ��س�������е����ྲس��Ϊ��������س���������ˣ�����������������س����
          g) ��س�������еĵ�������س���ж��䡢����֮س��֮Ϊ������
          h) ����س���ա���̨���ھ�س����ʱ��ν֮�����൱�ڶ�س��
      3.4 ��س/����س֮���˺ϻ�Ȩ
          a) ԭ��3���ա��½��嶯س���³壬�պϿɽ⡣�ճ�����ʱ���պ�ס�ɽ⡣
          b) ��س�붯س��壬��(ָ�Ƿ��б�س���ա�������)��ʤ��˥�߰ܣ�����������Ȩ��˥�߳��ɢ������Ȩ��˫����������Ϊ���ܾ��ˣ���������Ȩ��
          c) �½��嶯س������˥��Ϊ���ơ�������֮��س�������������ܿ�֮��س���³壬�ڶ�سֵ�ջ���պ�֮�գ�Ҳ������Ȩ������������Ȩ���ճ�����ʱ���պ�ס�ɽ⡣
          d) �¡��ս���������سν��ɢ���˶�س��������Ȩ�����ɽ⡣
          e) ��λ��س���س������ʱ������س�����ɢ��ʧȥ����Ȩ������ʱ�������س�Զ�سֻ�岻��ʱ����λ��س����������������Ȩ�� ��س�Ա�λ��س�������ʱ�����۶�س��˥����س������Ȩ��ֻ�б�λ��س���ա��½�ʱ����λ��س��������Ȩ��
          f) ��س���ա�������ֻ�����������ܿˣ������ˡ���й������������Ȩ��С������ͷ�ˣ�������Ȩ��������ͷ����������������Ȩ��
          g) ��س���ա���һ��������һ���ܿˣ�������Ȩ��
          h) ��س���ա�������������������������Ȩ��
          i) ��س���ա���˫�����ܳ�ˣ�������Ȩ��
          j) ����س���ջ��ºϣ�������˥�����۰�ס����ʱʧȥ����Ȩ��
          k) �������³�Ϊ�ƣ���ù֮������Ϊ�ݸ���ľ���������𣬷��˸��ˡ�
          l) ��سѮ�գ���ա����վ���������������ְ�ܡ�
      3.5 ��س���˺ϻ�Ȩ
          a) ԭ��1�����б�س�����˱���Լ���س�����Զ�سû�������ˡ��ϡ���ʱ�����������������س���������ˡ��ϡ������á�
          b) ԭ��2����س���س��塣��س���س���һ�㲻����ֻ����س������֮س�����á�
          c) ԭ��3���ա��½����س���³壬�պϿɽ⡣�ճ�����ʱ���պ�ס�ɽ⡣
          d) �½����س������˥��Ϊ���ơ�������֮��س�������������ܿ�֮��س���³壬�ڱ�سֵ�ջ���պ�֮�գ�Ҳ������Ȩ������������Ȩ���ճ�����ʱ���պ�ס�ɽ⡣
          e) ��سѮ�գ���س�����գ�����سѮ�գ���سҲ����������������Ȩ������س����ʱ��������Ȩ���嶯س����Ѯ�յı�س���������á�
   4. ��������
      ���ϻ��ɹ����Ի��������С�
      4.1  ����
          a) ʵ�Ͼ֡������ж�س����س������س���ա��³ɻ�������仯�񡣻�������ͬ�ջ���֮���� && �պ������в��˻���������ɹ�������϶��������۰�ס��
          b) ���Ͼ֡�a����һس������Ѯ�գ�����ʵֵ��֮ʱ��ʱ�ɾ֣���Ϊ����һ���á���Ӧ�ھ��ڡ����á�֮س�ϡ�
      3.2 ����
          a) �ϻ��ɹ������������е�����س�����������ж�س�뱾λ��س֮�ϡ�&& ���뻯���������½������ջ�����֮һΪ�������ա����κ�һ�������ǿ˻���֮���С�
          b) ���ջ���������֮س��ϣ���Ϊ�϶������� ���ա����뾲سΪ�˺�ʱ������س��˥�����Ժ����ۡ�
   5. �������˺��̳�
          a) �ս����½���壬��س�����У��ꡢ�¡��ա�ʱ��ͬ��ͬȨ����˲����ս����½���ϻ�����塣
          b) �¡��տ�����������֮س������֮س���������¡� �ա�

          d) ���ж�س��������֮س�������ˡ��ϡ��������еľ�س��Ҳ�������ˡ��ϡ���ͬ��εĶ�س��
          e) ��������֮��س������˥��֮����س����˥��֮س������������֮س��
          f) ��������֮��س������˥��֮����س����˥��֮س������������֮س��
          g) ��
          h) ��
          i) ��
          j) ��
          k) ��
   6.
   ���� ����ԭ��
        ԭ�����������س

   ���� ���ۼ���
        �����ǿ��������س

   �ġ� ���۳���
        �������������س��

   * @param str
   * @param ysLiuqin �ĸ�����Ϊ�ã���ǰ̨����
   * @param dizi
   * @param liuqin
   * @param diziBian
   * @param diziGong
   */
  public void getLiuYaoZhanOut(StringBuffer str,int up, int down, int[] shijian,
                             int upGong, int downGong, int shiyao, int yingyao,
                             int ysLiuqin,int[] dizi, int[] liuqin, int[] changes,
                             int[] diziBian, int[] diziGong, int[] liuqinGong,
                             int whichGongZ, int whichGongB) {
  int yuezi = shijian[4];
  int rizi = shijian[6];
  int rigan = shijian[5];
  boolean isFu = false; //�ж��Ƿ��Ƿ�������
  int[] ysLocal = new int[2]; //����λ������
  int _ysLiuqin = ysLiuqin;
  int ysDizi = 0;
  int yuanshendz = 0;
  int jishendz = 0;
  int chushendz = 0;

  //�������سΪ������Ҫת��
  if (ysLiuqin == 0) {
    ysLiuqin = liuqin[shiyao];
  }
  //�õ������֧
  for (int i = 1; i <= 6; i++) {
    if (liuqin[i] == ysLiuqin) {
      ysDizi = dizi[i];
      break;
    }
  }
  //ԭ��
  int yuanshen = 0;
  for (int i = 1; i <= 6; i++) {
    if (YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[i]]][YiJing.DIZIWH[ysDizi]] == 1) {
      yuanshen = liuqin[i];
      yuanshendz = dizi[i];
      break;
    }
  }

  //����
  int jishen = 0;
  for (int i = 1; i <= 6; i++) {
    if (YiJing.WXDANKE[YiJing.DIZIWH[dizi[i]]][YiJing.DIZIWH[ysDizi]] == 1) {
      jishen = liuqin[i];
      jishendz = dizi[i];
      break;
    }
  }

  //����
  int chushen = 0;
  for (int i = 1; i <= 6; i++) {
    if (YiJing.WXDANKE[YiJing.DIZIWH[ysDizi]][YiJing.DIZIWH[dizi[i]]] == 1) {
      chushen = liuqin[i];
      chushendz = dizi[i];
      break;
    }
  }

  //��سռ��
  //��������Թ�����˥���
  str.append(YiJing.LIUYAOSELF);
  str.append(getRepeats(" ", YiJing.INTER[0]));
  str.append("����" + YiJing.JINGGUANAME[whichGongZ] + "�����½���" +
             YiJing.WXSQXNAME[YiJing.WXSQX[yuezi][YiJing.BAGONGGUAWH[
             whichGongZ]]] + "�أ�");
  str.append("����" + YiJing.JINGGUANAME[whichGongB] + "�����½���" +
             YiJing.WXSQXNAME[YiJing.WXSQX[yuezi][YiJing.BAGONGGUAWH[
             whichGongB]]] + "�ء�");
  int[] silents = getSilents(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  int[] andongs = getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  int[] actives = getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  int[] bians   = getBians(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  getWangOrNoOut(str,yuezi,rigan,rizi,dizi,diziBian,changes,silents,andongs,actives,bians);

  {
    //����ռ��
    str.append(YiJing.YONGSHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));
    str.append(YiJing.YONGSHEN[_ysLiuqin]);

    //�����λ�ü��Ƿ����������ڷ�����
    isFu = getShenLocal(ysLiuqin, liuqin, liuqinGong, ysLocal);

    //����״��
    getShenState(str, liuqin, ysLiuqin, ysLocal, isFu, up, down, upGong,
                 downGong, "����");

    //�����Ƿ����ջ򶯻򶯶����Ĺ
    isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                       liuqin, dizi, diziBian, isFu, rizi, yuezi, "����", true);

    //�������˺��̳���������˥
    for(int i=0; i<ysLocal.length; i++) {
      if(ysLocal[i] == 0) break;
      getSGFKHXCout(dizi[ysLocal[i]], silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }

    //Ӧ��
    for(int i=0; i<ysLocal.length; i++) {
      if(ysLocal[i] == 0) break;
      getYingQiOut(str, dizi[ysLocal[i]], ysLocal[i], yuezi, rigan, rizi, changes, dizi,
                   diziBian, liuqin,
                   diziGong, liuqinGong, isFu);
    }
  }
  {
    //�����س���׼������ԭ����������ף������ж���سҲ
    if (ysLiuqin != liuqin[shiyao] && chushen != liuqin[shiyao] &&
        yuanshen != liuqin[shiyao] && jishen != liuqin[shiyao]) {
      //��سռ��
      str.append(YiJing.SHIYAOSELF);
      str.append(getRepeats(" ", YiJing.INTER[0]));
      str.append(YiJing.YONGSHEN[liuqin[shiyao]]);

      //��سλ�ü��Ƿ����������ڷ�����
      isFu = getShenLocal(liuqin[shiyao], liuqin, liuqinGong, ysLocal);

      //��س״��
      getShenState(str, liuqin, liuqin[shiyao], ysLocal, isFu, up, down,
                   upGong,
                   downGong, "��س");

      //��س�Ƿ����ջ�Ĺ
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "��س",true);

      //�������˺��̳���������˥
    getSGFKHXCout(dizi[shiyao] ,silents,andongs,actives,bians,rizi,yuezi,str,rigan, changes, dizi,diziBian);
    }
  }
  {
    //ԭ��ռ��
    str.append(YiJing.YUANSHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));

    if(yuanshen == 0) {
      str.append("ԭ��û�����ԡ�");
    }else {
      str.append(YiJing.YONGSHEN[yuanshen]);
      //ԭ���λ�ü��Ƿ����������ڷ�����
      isFu = getShenLocal(yuanshen, liuqin, liuqinGong, ysLocal);

      //ԭ��״��
      getShenState(str, liuqin, yuanshen, ysLocal, isFu, up, down, upGong,
                   downGong, "ԭ��");

      //ԭ���Ƿ����ջ�Ĺ
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "ԭ��",true);

      //�������˺��̳���������˥
      getSGFKHXCout(yuanshendz, silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }
  }
  {
    //����ռ��
    str.append(YiJing.JISHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));

    if(jishen == 0) {
      str.append("����û�����ԡ�");
    }else {
      str.append(YiJing.YONGSHEN[jishen]);
      //�����λ�ü��Ƿ����������ڷ�����
      isFu = getShenLocal(jishen, liuqin, liuqinGong, ysLocal);

      //����״��
      getShenState(str, liuqin, jishen, ysLocal, isFu, up, down, upGong,
                   downGong, "����");

      //�����Ƿ����ջ�Ĺ
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "����",true);

      //�������˺��̳���������˥
      getSGFKHXCout(jishendz, silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }
  }
  {
    //����ռ��
    str.append(YiJing.CHUSHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));

    if(chushen == 0) {
      str.append("����û�����ԡ�");
    }else{
      str.append(YiJing.YONGSHEN[chushen]);
      //�����λ�ü��Ƿ����������ڷ�����
      isFu = getShenLocal(chushen, liuqin, liuqinGong, ysLocal);

      //����״��
      getShenState(str, liuqin, chushen, ysLocal, isFu, up, down, upGong,
                   downGong, "����");

      //�����Ƿ����ջ�Ĺ
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "����",true);

      //�������˺��̳���������˥
      getSGFKHXCout(chushendz, silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }
  }


}

  /**
   * ȡ�����ԭ���ɻ�����״�����缸���������ԡ�������
   * @param str
   * @param liuqin
   * @param ysLiuqin
   * @param ysNum
   * @param ysLocal
   * @param up
   * @param down
   * @param upGong
   * @param downGong
   * @param ziName
   */
  public void getShenState(
      StringBuffer str, int[] liuqin, int ysLiuqin, int[] ysLocal,
      boolean isFu,
      int up, int down, int upGong, int downGong, String ziName) {
    int num = 0;
    if (ysLocal[1] != 0)
      num = 2;
    else
      num = 1;

    str.append("\n");
    str.append(getRepeats(" ", YiJing.INTER[0]));
    if (isFu) {
      str.append(ziName + "û�����ԣ�ȡ������" + num + "��" + ziName + "��");
      if (num > 1) {
        str.append("����һ��Ϊ" +
                   YiJing.YAONAME3[getYinOrYang(upGong, downGong, ysLocal[0])] +
                   "��");
        str.append("��һ��Ϊ" +
                   YiJing.YAONAME3[getYinOrYang(upGong, downGong, ysLocal[1])] +
                   "��");
      }
      else {
        str.append("Ϊ" +
                   YiJing.YAONAME3[getYinOrYang(upGong, downGong, ysLocal[0])] +
                   "��");
      }
      if (ysLocal[0] > 3 && ysLocal[1] > 3) {
        str.append("��λ�����ԣ�");
      }
      else if (ysLocal[0] <= 3 && ysLocal[1] <= 3 && ysLocal[1] > 0) {
        str.append("��λ�����ԣ�");
      }
      else if (ysLocal[0] <= 3 && ysLocal[1] == 0) {
        str.append("λ�����ԣ�");
      }
      else if (ysLocal[0] > 3 && ysLocal[1] == 0) {
        str.append("λ�����ԣ�");
      }
      else {
        str.append("����һ��λ�����ԣ���һ��λ�����ԣ�");
      }
      str.append("��ϸռ�������ռ��");
      return;
    }
    else if (num > 1) {
      str.append("��" + num + "��" + ziName + "��");
      str.append("����һ��Ϊ" + YiJing.YAONAME3[getYinOrYang(up, down, ysLocal[0])] +
                 "��");
      str.append("��һ��Ϊ" + YiJing.YAONAME3[getYinOrYang(up, down, ysLocal[1])] +
                 "��");
      if (ysLocal[0] > 3 && ysLocal[1] > 3) {
        str.append("��λ�����ԣ�");
      }
      else if (ysLocal[0] <= 3 && ysLocal[1] <= 3) {
        str.append("��λ�����ԣ�");
      }
      else {
        str.append("����һ��λ�����ԣ���һ��λ�����ԣ�");
      }
    }
    else {
      str.append("��" + num + "��" + ziName + "��");
      str.append("Ϊ" + YiJing.YAONAME3[getYinOrYang(up, down, ysLocal[0])] +
                 "��");
      if (ysLocal[0] > 3) {
        str.append("λ�����ԣ�");
      }
      else {
        str.append("λ�����ԣ�");
      }
    }
  }


  /**
   * �õ�֧�Ƿ�Ĺ�أ���Ҫ������Ĺ���붯Ĺ�򶯶����Ĺ
   * @param str
   * @param ysLocal ����λ��������֧����
   * @param changes ��س����
   * @param liuqinGong ��������
   * @param diziGong ���Ե�֧
   * @param liuqin ��������
   * @param dizi ���Ե�֧
   * @param diziBian ���Ե�֧
   * @param isFu �Ƿ��Ƿ�س
   * @param rizi ��֧
   * @param yuezi ��֧
   * @param ziName ֧�����������������س
   */
  public boolean isRuMu(
      StringBuffer str,int[] ysLocal, int[] changes,int rigan,
      int[] liuqinGong,int[] diziGong,
      int[] liuqin, int[] dizi, int[] diziBian,
      boolean isFu,int rizi, int yuezi, String ziName,boolean bl
                     ) {
    boolean bYJWang = false;
    boolean bRJWang = false;
    boolean bRJZhi = false;

    int num = 0;
    if(ysLocal[1] != 0)
      num = 2;
    else
      num = 1;

    int _liuqin, _dizi;
    for (int i = 0; i < num; i++) {
      if (isFu) {
        _liuqin = liuqinGong[ysLocal[i]]; //�������Ե�����
        _dizi = diziGong[ysLocal[i]];     //�������Եĵ�֧
      }
      else {
        _liuqin = liuqin[ysLocal[i]];
        _dizi = dizi[ysLocal[i]];
      }
      //�½�֮����������
      if(bl) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append(ziName + YiJing.LIUQINNAME[_liuqin] + YiJing.DIZINAME[_dizi] +
                   "س��");
        str.append("���½���" + YiJing.WXSQXNAME[howWxsqx(_dizi, yuezi)] + "�أ�");
      }
      bYJWang = isWxsqx(_dizi, yuezi);

      //�ս���˥��Ĺ
      bRJWang = isSwmj(_dizi, rizi);
      bRJZhi = _dizi == rizi;
      if(bl) {
        str.append("���ս���" + YiJing.SWMJNAME[howSwmj(_dizi, rizi)] + "�أ�");
      }

      //��������Ĺ
      boolean _b = false;
      String _temp = "";
      if (liuqin[ysLocal[i]] == YiJing.GUANGUI)
        _temp = "����";
      if (howSwmj(_dizi, rizi) == YiJing.MURJVALUE) {
        if(bl) {
          str.append("\n");
          str.append(getRepeats(" ", YiJing.INTER[0]));
          str.append(ziName + _temp + "����Ĺ��");
        }
        if(isZiWang(str,_dizi,yuezi,rigan,rizi,changes,dizi,diziBian)) {
          if(bl) {
            str.append("���������������оȣ�");
          }
        }else{
          if(bl) {
            str.append("�����������������ף�");
          }
        }
        if(bl) {
          str.append("����Ĺ����Ĺ�ķ�ʽ�ǳ�Ĺس�����ܳ��ա�");
        }
        return true;
      }
      //�붯Ĺ
      for (int i1 = 0; i1 < changes.length; i1++) {
        //���ĳس���붯س���������Լ���
        if(ysLocal[i] == changes[i1])
          continue;
        if (howSwmj(_dizi, dizi[changes[i1]]) == YiJing.MURJVALUE) {
          if(bl) {
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
            str.append(ziName + _temp + "�붯Ĺ��");
          }
          if(isZiWang(str,_dizi,yuezi,rigan,rizi,changes,dizi,diziBian)) {
            if(bl) {
              str.append("���������������оȣ�");
            }
          }else{
            if(bl) {
              str.append("�����������������ף�");
            }
          }
          for (int i2 = 0; i2 < changes.length; i2++) {
            //�������Ϊ��س
            if (changes[i2] == ysLocal[i]) {
              _b = true;
              break;
            }
          }
          if (_b)
            if(bl) {
              str.append(ziName + "�Ƕ�س�붯Ĺ�������ѽ⡣");
            }
          else
            if(bl) {
              str.append(ziName + "�Ǿ�س�붯س֮Ĺ����Ĺ����Ĺ����س�����Խ�Ĺ��");
            }
          return true;
        }
      }

      if(isDongRuBianMuOut(str,changes,liuqin,dizi,diziBian,_dizi,bl))
        return true;
    }
    return false;
  }

  /**
   * �Ƿ��ж�����Ĺ
   * @param str
   * @param changes
   * @param liuqin
   * @param dizi
   * @param diziBian
   */
  public boolean isDongRuBianMuOut(
      StringBuffer str,int[] changes,
      int[] liuqin, int[] dizi, int[] diziBian, int zi, boolean bl) {
    String _temp = "";
    for (int i = 0; i < changes.length; i++) {
      if(dizi[changes[i]] != zi)
        continue;
      if (howSwmj(dizi[changes[i]], diziBian[changes[i]]) == YiJing.MURJVALUE) {
          if(bl) {
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
          }
          if (liuqin[i] == YiJing.GUANGUI)
            _temp = "����";

          if(bl) {
            str.append("��س" + YiJing.DIZINAME[dizi[changes[i]]] + _temp + "������Ĺ��");
            str.append("��Ĺ��ʽ�����֣�һ�ǳ�س�����ǳ�Ĺ�����Ǻ�Ĺ����Ĺ֮������ҽԺ����������Ѩ���ⷿ������֮���塣");
          }
          return true;
        }
      }
      return false;
    }

  /**
   * ��������������Ϣ
   * 1. �ĸ��񷢶�
   * 2. ����֮��˥���������
   * 3. ����֮�����
   * 4. �����ٺ�����
   */
  public void getLiuShenOut(StringBuffer str, int[] liushen, int[] changes,
                            int[] dizi, int[] liuqin, int shiyao, int yuez) {
    str.append(YiJing.LIUSHENSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    //�ĸ��񷢶�
    for(int i=0; i<changes.length; i++) {
      if(changes[i]==0) continue;
      str.append(YiJing.LIUSHENFADONG[liushen[changes[i]]]);
      str.append("\n");
      str.append(getRepeats(" ",YiJing.INTER[0]));
    }
    //����֮��˥���������
    if(this.isWxsqx(dizi[shiyao],yuez)) {
      str.append(YiJing.LIUSHENCISHIGOOD[liushen[shiyao]]);
    }else{
      str.append(YiJing.LIUSHENCISHIBAD[liushen[shiyao]]);
    }
    str.append("\n");
    str.append(getRepeats(" ",YiJing.INTER[0]));
    //����֮�����
    for(int i=1; i<=6; i++) {
      if(YiJing.WXDANKE[YiJing.DIZIWH[dizi[i]]][YiJing.DIZIWH[dizi[shiyao]]] == 1) {
        str.append(YiJing.LIUSHENKESHIYONG[liushen[i]]);
        str.append("\n");
        str.append(getRepeats(" ",YiJing.INTER[0]));
      }
    }
    //�����ٺ�����
    for(int i=1; i<=6; i++) {
      str.append(YiJing.LIUSHENQIN[liushen[i]][liuqin[i]]);
      str.append("\n");
      str.append(getRepeats(" ",YiJing.INTER[0]));
    }
  }

  /**
   * ����Ƿ���ʮ������֮һ
   * @param str
   * @param up
   * @param down
   */
  public void getTwenteenOut(StringBuffer str, int up, int down) {
    str.append(YiJing.HOUGUASELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(YiJing.HOUGUAZHAN[YiJing.HOUGUA[up][down]]);
  }

  /**
   * �õ����Կ�������ʮ�����Է�
   * @param str
   * @param whichGongZhu
   * @param upBian
   * @param downBian
   */
  public void getJFSixteenOut(StringBuffer str,int whichGongZhu,
                           int upBian, int downBian, int[] changes) {
    str.append(YiJing.JINGFANGGUASELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    if(changes.length == 0 || changes[0] == 0) {
      str.append(YiJing.WUBIANGUASELF);
      return;
    }

    int i = YiJing.SIXTEENGUA[whichGongZhu][upBian][downBian];
    if(i != 0) {
      str.append(YiJing.JINGFANGZHAN[i]);
    }else{
      str.append(YiJing.JINGFANGWUSELF);
    }
  }

  /**
   * �Ƿ��������Ի�������
   * @param str
   * @param up
   * @param down
   */
  public void getLiuHeChongOut(StringBuffer str, int up, int down) {
    boolean bool = false;
    str.append(YiJing.CHSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));

    if(YiJing.LIUCHONGGUA[up][down] != 0) {
      bool = true;
      str.append(YiJing.LCSELF);
    }
    if(YiJing.LIUHEGUA[up][down] != 0) {
      bool = true;
      str.append(YiJing.LHSELF);
    }
    if(bool)
      str.append(YiJing.CHZHANSELF);
    else
      str.append(YiJing.CHWUSELF);

  }

  /**
   * ����Ƿ��ǽ��������Ϣ
   * @param str
   * @param changes
   * @param diZi
   * @param diZiBian
   */
  public void getJinTuiShenOut(StringBuffer str, int[] changes, int[] diZi, int[] diZiBian) {
    boolean bool = false;
    str.append(YiJing.JTSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    int i1=0,i2=0; //Ϊ��ֹ�ظ�

    for(int i=0; i<changes.length; i++) {
      if(YiJing.JINSHEN[diZi[changes[i]]][diZiBian[changes[i]]] != 0) {
        if(i1 == 0) {
          bool = true;
          str.append(YiJing.JSSELF);
          i1++;
        }
      }
      if(YiJing.TUISHEN[diZi[changes[i]]][diZiBian[changes[i]]] != 0) {
        if(i2 == 0) {
          bool = true;
          str.append(YiJing.TSSELF);
          i2++;
        }
      }
    }
    if(bool)
      str.append(YiJing.JTZHANSELF);
    else
      str.append(YiJing.JTWUSELF);
  }

  /**
   * ����Ƿ��з���,����ָ�Ա��س�ĵ�֧���䡣
   * @param str
   * @param zhu ���Ե�֧
   * @param bian
   * @param whichZ �����ι�
   */
  public void getFuLingOut(StringBuffer str, int[] z, int[] b, int upZ, int downZ, int upB, int downB) {
    boolean bool = false;
    str.append(YiJing.FULSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    if(downZ != downB) {
      if(z[1] == b[1] && z[2] == b[2] && z[3] == b[3]) {
        bool = true;
        str.append(YiJing.GUAFULWSELF);
      }
    }

    if(upZ != upB) {
      if(z[4] == b[4] && z[5] == b[5] && z[6] == b[6]) {
        bool = true;
        str.append(YiJing.GUAFULNSELF);
      }
    }

    if(bool) {
      str.append(YiJing.FULZHANSELF);
    }else{
      str.append(YiJing.WUFULSELF);
    }

  }

  /**
   * ����Ƿ��Ƿ����Ի�س����
   * �Է���ָ��������������ˣ�������������
   * س����ָ���е�ĳ��س�����ˡ�
   * @param str
   * @param <any>
   */
  public void getFanLingOut(StringBuffer str, boolean isGuaFanLing, boolean isYaoFanLing) {
    str.append(YiJing.FLSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    if(isGuaFanLing)
      str.append(YiJing.GUAFLSELF);
    if(isYaoFanLing)
      str.append(YiJing.YAOFLSELF);
    if(isGuaFanLing || isYaoFanLing)
      str.append(YiJing.FLZHANSELF);
    else
      str.append(YiJing.WUFLSELF);
  }

  /**
   * �õ�������
   * @return
   */
  public void getYeuGuaShenOut(StringBuffer str, int yueGuaShen, boolean isHasGuaShen) {
    str.append(YiJing.YUEGUASHENSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(YiJing.DIZINAME[yueGuaShen]);
    if(isHasGuaShen)
      str.append("�� "+YiJing.YUEGUASHENNOTE[1]);
    else
      str.append("�� "+YiJing.YUEGUASHENNOTE[0]);
  }

  /**
   * �õ�����
   * @return
   */
  public void getShiShenOut(StringBuffer str, int shiShen) {
    str.append(YiJing.SHISHENSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(YiJing.SHISHEN2NAME[shiShen]);
  }

  /**
   * �õ��Դ���س��
   * @return
   */
  public void getGuaYaoCiOut(StringBuffer str, String[] guaCi, int[] guaXiang,
                             int[] changes, int up, int down ) {
    str.append(YiJing.GUACISELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(guaCi[0]);
    str.append(YiJing.YAOCISELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    //�������س�Զ�����Ǭ���������ȡ��ʡ�
    String temp_ = null;
    if(changes.length == 6 && up != 1 && up != 8 && down != 1 && down != 8)
      temp_ = YiJing.LYDONGSELF;
    else if(changes.length == 0 || changes[0] == 0)
      temp_ = YiJing.LYJINGSELF;
    else {
      int which = getDongYaoCi(guaXiang, changes);
      if(which>6) temp_="��سȫ����ȡ�Դ�";
      else temp_ = guaCi[which];
    }
    str.append(temp_);
    str.append("\n");
  }

  /**
   * �õ�����
   * @param z Ϊ������֧
   * @param jing Ϊ�˹��Ծ���֧
   * @return
   */
  public void getFuFeiOut(StringBuffer str, int[] ff,int[] z, int[] jing) {
    boolean bool = false;
    int zwh = 0;
    int bwh = 0;
    int i1=0,i2=0,i3=0,i4=0,i5=0; //��ֹ����������ʱ�����ظ�

    str.append(YiJing.FUFEISELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    for(int i=0; i<ff.length; i++) {
      if(ff[i] == 0)
        continue;
      zwh = YiJing.DIZIWH[z[ff[i]]];
      bwh = YiJing.DIZIWH[jing[ff[i]]];
      if(YiJing.WXDANSHENG[zwh][bwh] != 0) {
        if(i1 == 0) {
          i1++;
          bool = true;
          str.append(YiJing.FEISFUSELF);
        }
      }
      if(YiJing.WXDANKE[zwh][bwh] != 0) {
        if(i2 == 0) {
          i2++;
          bool = true;
          str.append(YiJing.FEIKFUSELF);
        }
      }
      if(YiJing.WXDANSHENG[bwh][zwh] != 0) {
        if(i3 == 0) {
          i3++;
          bool = true;
          str.append(YiJing.FUSFEISELF);
        }
      }
      if(YiJing.WXDANKE[bwh][zwh] != 0) {
        if(i4 == 0) {
          i4++;
          bool = true;
          str.append(YiJing.FUKFEISELF);
        }
      }
      if(YiJing.WXBIHE[bwh][zwh] != 0) {
        if(i5 == 0) {
          i5++;
          bool = true;
          str.append(YiJing.FEIFUHESELF);
        }
      }
    }
    if(!bool)
      str.append(YiJing.FEIFUWUSELF);
  }


  /**
   * �õ�����
   * @param up �������������
   * @param down �������������
   * @param changes ��س����
   * @param upBian �����������������
   * @param downBian �����������������
   * @param upHu �����������������
   * @param downHu �����������������
   * @param whichGongZhu ���Ժι�
   * @param whichGongBian ���Ժι�
   * @param whichGongHu ���Ժι�
   * @param shiYao ������سλ��
   * @param yingYao ����Ӧسλ��
   * @param shiYaoBian ������سλ��
   * @param yingYaoBian ����Ӧسλ��
   * @param shiYaoHu ������سλ��
   * @param yingYaoHu ����Ӧسλ��
   * @param diZi ���Ե�֧��س����س
   * @param diZiBian ���Ե�֧��س����س
   * @param diZiHu ���Ե�֧��س����س
   * @param diZiGong ���������˹���֮���Եĵ�֧��س����س
   * @param guaXiang ���������س����س��س��1Ϊ��س2Ϊ��س
   * @param guaXiangBian ��������
   * @param guaXiangHu ��������
   * @param liuQin ��������
   * @param liuQinBian ��������
   * @param liuQinHu ��������
   * @param liuQinGong �˹��Ծ�������
   * @param liuShen ����
   * @param yueGuaShen ������λ���õ�֧��ʾ
   * @param isHasGuaShen �Ƿ���������
   * @param shiShen ����
   * @param ff �ɷ�س��λ��
   * @param guaCi ��ʮ�����Դ�
   * @return
   */
  public String getGuaXiangOut(
      StringBuffer str,
      int up, int down, int[] changes,
      int upBian, int downBian,
      int upHu, int downHu,
      int whichGongZhu, int whichGongBian, int whichGongHu,
      int shiYao, int yingYao,
      int shiYaoBian, int yingYaoBian,
      int shiYaoHu, int yingYaoHu,
      int[] diZi, int[] diZiBian, int[] diZiHu, int[] diZiGong,
      int[] guaXiang, int[] guaXiangBian, int[] guaXiangHu,
      int[] liuQin, int[] liuQinBian, int[] liuQinHu, int[] liuQinGong,
      int[] liuShen,
      int[] ff
      ) {
    //Ϊ��ʽ����
    String zhuLen = null;   //���Դ�
    String huLen = null;    //���Դ�
    String bianLen = null;  //���Դ�
    String dyLen = null;    //��س��
    int totalLen1 = 0;  //���Ե����ĵ�
    int totalLen2 = 0;  //���Ե����ĵ�
    int totalLen3 = 0;  //���Ե����ĵ�
    String temp_ = null; //��ʱ����

    /**
     * 1. ����ι����ԣ��ȿ�2���ټ���X�����ԣ��ٿ�12��X�����ԣ�12�ո�X������
     * 2. س��+��س���ж�س��ǰ��һ�ո�û�е��пո���
     * 3. ���ԣ��ȿ�10���ټ�����
     * 4. ���ԣ��ȿ�10���ټӱ���
     * 5. �������� + �����֧ + �������� + �����س + ��
     * 6. ���������Դǣ�س��
     */
    str.append(getRepeats(" ",YiJing.INTER[0]));
    zhuLen = YiJing.JINGGUANAME[whichGongZhu]+YiJing.GONGZHUGUASELF+YiJing.GUA64NAME[up][down]+"��";
    str.append(zhuLen);
    str.append(getRepeats(" ",16));
    bianLen = YiJing.JINGGUANAME[whichGongBian]+YiJing.GONGBIANGUASELF+YiJing.GUA64NAME[upBian][downBian]+"��";
    str.append(bianLen);
    str.append(getRepeats(" ",16));
    huLen = YiJing.JINGGUANAME[whichGongHu]+YiJing.GONGHUGUASELF+YiJing.GUA64NAME[upHu][downHu]+"��";
    str.append(huLen);

    totalLen1 = YiJing.INTER[0]+zhuLen.getBytes().length/2;
    totalLen2 = YiJing.INTER[0]+zhuLen.getBytes().length+YiJing.INTER[1]+bianLen.getBytes().length/2;
    totalLen3 = YiJing.INTER[0]+zhuLen.getBytes().length+YiJing.INTER[1]+bianLen.getBytes().length+YiJing.INTER[1]+huLen.getBytes().length/2;

    str.append("\n");
    for(int i=6; i>0; i--) {
      //����/��������/��������/��֧/����/��س/��س��Ӧس/����
      zhuLen = "";
      if(ff[i] == i) {
        zhuLen += YiJing.FEIFUNAME[1];
      }else{
        //��Ҫ��ȫ���У��ʼӼ����ո�
        zhuLen += getRepeats(" ",YiJing.FEIFUNAME[1].getBytes().length);
      }
      zhuLen += YiJing.LIUQINNAME[liuQin[i]];
      zhuLen += " "+YiJing.YAONAME[guaXiang[i]]+" ";
      zhuLen += YiJing.DIZINAME[diZi[i]];
      zhuLen += YiJing.WUXINGNAME[YiJing.DIZIWH[diZi[i]]];
      //zhuLen += YiJing.YAONAME2[guaXiang[i]];

      if(isDongYao(changes ,i)) {
        zhuLen += YiJing.YAODONG[guaXiang[i]];
      }else{
        zhuLen += getRepeats(" ",YiJing.YAODONG[1].getBytes().length);
      }

      if(shiYao == i) {
        zhuLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.SHI];
      }else if(yingYao == i) {
        zhuLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.YING];
      }else{
        zhuLen += getRepeats(" ",YiJing.SHIYINGNAME[YiJing.YING].getBytes().length+1);
      }
      str.append(makeCenter(zhuLen,totalLen1));

      //�˴�Ϊ��ʽ����
      huLen = "";
      if(ff[i] == i) {
        huLen = getRepeats(" ",1);
        huLen += YiJing.FEIFUNAME[2];
        huLen += YiJing.LIUQINNAME[liuQinGong[i]].substring(0,YiJing.LIUQINNAME[liuQinGong[i]].length()/2);
        huLen += YiJing.DIZINAME[diZiGong[i]];
        str.append(huLen+"  ");
      }else{
        str.append(getRepeats(" ", 4));
      }

      //��������/��֧/����/��Ӧس
      bianLen = YiJing.LIUQINNAME[liuQinBian[i]];
      bianLen += " "+YiJing.YAONAME[guaXiangBian[i]]+" ";
      bianLen += YiJing.DIZINAME[diZiBian[i]];
      bianLen += YiJing.WUXINGNAME[YiJing.DIZIWH[diZiBian[i]]];
      //bianLen += YiJing.YAONAME2[guaXiangBian[i]];
      if(shiYaoBian == i) {
        bianLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.SHI];
      }else if(yingYaoBian == i) {
        bianLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.YING];
      }else{
        bianLen += getRepeats(" ",YiJing.SHIYINGNAME[YiJing.YING].getBytes().length+1);
      }
      str.append(makeCenter(bianLen,totalLen2-totalLen1-zhuLen.getBytes().length/2-huLen.getBytes().length));

      //��������/��֧/����/��Ӧس
      huLen = YiJing.LIUQINNAME[liuQinHu[i]];
      huLen += " "+YiJing.YAONAME[guaXiangHu[i]]+" ";
      huLen += YiJing.DIZINAME[diZiHu[i]];
      huLen += YiJing.WUXINGNAME[YiJing.DIZIWH[diZiHu[i]]];
      //huLen += YiJing.YAONAME2[guaXiangHu[i]];
      if(shiYaoHu == i) {
        huLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.SHI];
      }else if(yingYaoHu == i) {
        huLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.YING];
      }else{
        huLen += getRepeats(" ",YiJing.SHIYINGNAME[YiJing.YING].getBytes().length+1);
      }
      str.append(makeCenter(huLen,totalLen3-totalLen2-bianLen.getBytes().length/2));

      //����
      str.append(getRepeats(" ",YiJing.INTER[2]));
      str.append(YiJing.LIUSHENNAME[liuShen[i]]);

      str.append("\n");
    }

    return str.toString();
  }

  /**
   * �ж��Ƿ����Է���,������������Ƿ���ˣ����Ƿ��������
   * �����ޣ��ޱ��������������Զ�������Ϊʲô���ڷ�����?
   * ���������ڡ��⣺���������ڶ����������г����������������������ϣ�������δ���������ꡣ������ԣ��г�δ��壮��������塱��
   * @return
   */
  public boolean isGuaXIANGKE(int whichGongZhu, int whichGongBian) {
    if(whichGongZhu == YiJing.KUN && whichGongBian == YiJing.GEN)
      return true;

    if(whichGongZhu == YiJing.GEN && whichGongBian == YiJing.KUN)
      return true;

    int i1 = YiJing.BAGONGGUAWH[whichGongZhu];
    int i2 = YiJing.BAGONGGUAWH[whichGongBian];
    int i = YiJing.WXXIANGKE[i1][i2];

    return i==0 ? false : true;
  }

  /**
  * �õ������Դ���س��
  * @return
  */
 public String[] getGuaCiAndYaoCi(int up, int down) {
   return YiJing.GUA64CI[up][down];
 }

 /**
  * ����֧�Ƿ����
  * @param i1
  * @param i2
  * @return
  */
 public boolean isDiZiKe(int i1, int i2){
   int i = YiJing.WXXIANGKE[YiJing.DIZIWH[i1]][YiJing.DIZIWH[i2]];
   return i == 0 ? false : true;
 }

 /**
  * ����֧�Ƿ����
  * @param i1
  * @param i2
  * @return
  */
 public boolean isDiZiChong(int i1, int i2){
   int i = YiJing.DZCHONG[i1][i2];
   return i == 0 ? false : true;
 }

 /**
  * ����֧�Ƿ���
  * @param i1
  * @param i2
  * @return
  */
 public boolean isYaoFanLing(int i1, int i2){
   return isDiZiKe(i1,i2) || isDiZiChong(i1,i2);
 }

 /**
  * �Ƿ�ǰس�Ƕ�س
  * @param change
  * @param current
  * @return
  */
 public boolean isDongYao(int[] changes, int current) {
   for(int i=0; i<changes.length; i++) {
     if(changes[i] == current) {
       return true;
     }
   }
   return false;
 }

  /**
   * �ɶ�س�������ĸ�س��س��
    ��س�����ģ��Ա����ԴǶ�֮��
����һس�����Զ�س֮س�Ƕ�֮��
������س���ߣ���ȡ��س֮س����Ϊ�ϣ����ԡ�������ȥ������δ������Ҳ�������Ů�����ԣ�������������س�Զ������Գ���س��֮������سΪ����֮�ϣ���������ȥ������δ���������д���ѧ�ʡ�
������������س���ͬ����س����س����ȡ�϶�֮س��֮������ȼ��ԣ����š�������س�Զ������Ծ���س��س��Ϊ�ϡ�
������س���ߣ���������س���м�һس֮س��Ϊ�ϣ������ԣ��Ŷ������ġ��������س�Զ�����ȡ����س��س��Ϊ�ϡ�
������س���ߣ����¾�֮س�Ƕ�֮�������ˮδ���ԣ��Ŷ������������ġ�������س�Զ������Գ������س�Ƕ�֮��������������������ġ��������س�Զ�����ȡ�Ŷ�س��س�Ƕ�֮��
������ද�ߣ�ȡ��س��س�Ƕ�֮��
������س�Զ����ԣ������Ǭ�����ԣ��ԡ��þš�����������֮�Ƕ�֮������Ǭ����س�Զ�����ΪȺ�����ף�����
����Ǭ��������������ԣ��������س�Զ������Ա��Ե���Ƕ�֮���������Ů������س�Զ�������Ǭ�Ե���Ƕ�֮����ΪŮ��������Ǭ�Ա�����Ů�������ڰ˹��Ե�Ǭ��֮�С�
   * @param changes
   * @return
   */
  public int getDongYaoCi(int[] guaX, int[] changes) {
    int len = changes.length;
    int c1,c2;
    int who = 0;
    String str = "ERR!";

    if(len == 1)
      who = changes[0];

    if(len ==2){
      c1 = guaX[changes[0]];
      c2 = guaX[changes[1]];
      if(c1 == c2) {
        who = Math.max(changes[0], changes[1]);
      }else{
        if(c1 == YiJing.YANGYAO)
          who = changes[1];
        else
          who = changes[0];
      }
    }

    if(len ==3){
      int[] sortChanges = sort(changes);
      who = changes[1];
    }

    if(len == 4) {
      boolean b = false;
      for(int i=1; i<=6; i++) {
        b = false;
        for(int j=0; j<changes.length; j++) {
          if(i == changes[j]) {
            b = true;
            break;
          }
        }
        if(!b) {
          who = i;
          break;
        }
      }
    }

    if(len == 5) {
      who = 21 - changes[0] - changes[1] - changes[2] - changes[3] - changes[4];
    }

    if(len == 6) {
        who = 7;
    }

    return who;
  }

  /**
   * ð�ݷ�����
   * @param array
   * @return
   */
  public int[] sort(int[] array) {
    int length = array.length;
    int t;
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          t = array[j];
          array[j] = array[j + 1];
          array[j + 1] = t;
        }
      }
    }
    return array;
  }

  /**
   * �ж��Ƿ񹬾����д����ױ�����û�У�������򷵻�true
   * @param str
   * @param center
   * @return
   */
  public int[] howManyFeiFu(int[] liuQinZhu, int[] liuQinQong) {
    boolean b = false;
    int[] ret = new int[7];

    for(int i=1; i<=6; i++) {
      b = false;
      for(int j=1; j<=6; j++) {
        if(liuQinQong[i] == liuQinZhu[j]) {
          b = true;
          break;
        }
      }
      if(!b)
        ret[i] = i;
    }
/*
    int j = 0;
    for(int i = 0; i<7; i++) {
      if(ret[i] != 0) j++;
    }
    iret = new int[j];
    j = 0;
    for(int i = 0; i<7; i++) {
      if(ret[i] != 0) iret[j++] = ret[i];
    }
*/
    return ret;
  }

  /**
   * ���������ַ�ʹ��������center��
   * @param str
   * @param center
   * @return
   */
  public String makeCenter(String str, int center) {
    int len = str.getBytes().length;
    return getRepeats(" ",center+len/2 - len)+str;
  }

  /**
   * ������(������)
   * @param str
   * @param len
   * @return
   */
  public int getGuaShen(int up, int down, int shiyao) {
    int guashen = YiJing.YUEGUASHEN[getYinOrYang(up,down,shiyao)][shiyao];
    return guashen;
  }

  /**
   * �����Ƿ�����
   * @param up
   * @param down
   * @param guashen
   * @return
   */
  public boolean isGuaShen(int up, int down, int guashen) {
    int[] diZi = mergeIntArray(getGuaDiZi(up,1),getGuaDiZi(down,0));
    boolean b = false;
    for(int i=1; i<7; i++) {
      if(diZi[i] == guashen)
        b = true;
    }
    return b;
  }

  /**
   * ������
   * @param diZi
   * @param shiYao
   * @return
   */
  public int getShiShen(int[] diZi, int shiYao) {
    return YiJing.SHISHEN2[diZi[shiYao]];
  }

  /**
   * �����Լ����Է�����س��Ӧس������λ��س����������
   * @param str
   * @param len
   * @return
   */
  public int getYinOrYang(int up, int down, int where) {
    int[] upGuaXiang1 = getGuaXiang(up);
    int[] downGuaXiang1 = getGuaXiang(down);
    int[] guaXiang1 = mergeIntArray(upGuaXiang1,downGuaXiang1);

    return guaXiang1[where];
  }

  /**
   * �����ظ���Ԫ��
   * @param str
   * @param len
   * @return
   */
  public String getRepeats(String str, int len) {
    String retStr = "";
    for(int i = 0; i<len; i++) {
      retStr += str;
    }
    return retStr;
  }

  /**
   * �����Ժ����Եõ��������Թ�
   * @param up
   * @param down
   * @return
   */
  public int getWhichGong(int up ,int down) {
    return YiJing.BAGONGGUA[up][down];
  }

  /**
   * �����Ժ����Եõ��以�����Ի�����
   * @param up
   * @param down
   * @return
   */
  public int getHuGuaUpOrDown(int up ,int down, String type) {
    int upOrDown = 0;
    int[] upGuaX = YiJing.GUAXIANG[up];
    int[] downGuaX = YiJing.GUAXIANG[down];
    if("UP".equalsIgnoreCase(type))
      upOrDown = YiJing.XIANGGUA[downGuaX[3]][upGuaX[1]][upGuaX[2]];
    else
      upOrDown = YiJing.XIANGGUA[downGuaX[2]][downGuaX[3]][upGuaX[1]];

    return upOrDown;
  }

  /**
   * �����Ժ����Եõ���������Ի�����
   * @param up
   * @param down
   * @param change
   * @param type
   * @return
   */
  public int getBianGuaUpOrDown(int up, int down, int changes[],String type) {
    if(changes.length == 1 && changes[0]==0) {
      if("UP".equalsIgnoreCase(type)) {
        return up;
      }else{
        return down;
      }
    }

    int upOrDown = 0;
    int[] upGuaX = YiJing.GUAXIANG[up];
    int[] downGuaX = YiJing.GUAXIANG[down];
    int i1=0,i2=0,i3=0,i;

      if("UP".equalsIgnoreCase(type)) {
        i1 = upGuaX[1];
        i2 = upGuaX[2];
        i3 = upGuaX[3];
        for(int j=0; j<changes.length; j++) {
          if(changes[j] <= 3)
            continue;
          i = changes[j]%3==0 ? 3 : changes[j]%3;
          if (i == 1) {
            i1 = getReverseYinYang(upGuaX[1]);
          }else if (i == 2) {
            i2 = getReverseYinYang(upGuaX[2]);
          }else {
            i3 = getReverseYinYang(upGuaX[3]);
          }
        }
        upOrDown = YiJing.XIANGGUA[i1][i2][i3];
      }else{
        i1 = downGuaX[1];
        i2 = downGuaX[2];
        i3 = downGuaX[3];
        for(int j=0; j<changes.length; j++) {
          if(changes[j] >= 4)
            continue;
          i = changes[j];
          if (i == 1) {
            i1 = getReverseYinYang(downGuaX[1]);
          }else if (i == 2) {
            i2 = getReverseYinYang(downGuaX[2]);
          }else {
            i3 = getReverseYinYang(downGuaX[3]);
          }
        }
        upOrDown = YiJing.XIANGGUA[i1][i2][i3];
      }

    return upOrDown;
  }

  /**
   * �����෴����س����س
   * @param i
   * @return
   */
  public int getReverseYinYang(int i) {
    if(i==1)
      return 2;
    return 1;
  }

  /**
   * �ɰ˹��Եõ�����������
   * @param gong
   * @return
   */
  public int getWhichWH(int gong) {
    return YiJing.BAGONGGUAWH[gong];
  }

  /**
   * ���Եõ����Ե�س��ͼ
   * @param gua
   * @return
   */
  public int[] getGuaXiang(int gua) {
    return YiJing.GUAXIANG[gua];
  }

  /**
   * �õ����Ե���سλ��
   * @param up
   * @param down
   * @return
   */
  public int getShiYao(int up, int down) {
    return YiJing.BAGONGSHIYING[up][down];
  }

  /**
   * ����س�õ���Ӧس��λ��
   * @param shi
   * @return
   */
  public int getYingYao(int shi){
    int shi1 = (shi+3)%6 == 0 ? 6 : (shi+3)%6;
    return shi1;
  }

  /**
   * ���Լ���������λ�õõ����֧ 0��1��
   * @param gua
   * @param upOrDown
   * @return
   */
  public int[] getGuaDiZi(int gua, int upOrDown) {
    return YiJing.BAGUADIZI[gua][upOrDown];
  }

  /**
   * ���Լ���������λ�õõ������ 0��1��
   * @param gua
   * @param upOrDown
   * @return
   */
  public int[] getGuaTG(int gua, int upOrDown) {
    return YiJing.BAGUATG[gua][upOrDown];
  }


  /**
   * ���ҵ�������˹��Ե����еõ�������֮һ
   * @param selfWH
   * @param gongWH
   * @return
   */
  public int getLiuQin(int selfWH, int gongWH) {
    return YiJing.LIUQIN[selfWH][gongWH];
  }

  /**
   * �ɰ��Ե�֧�������������еõ�������
   * @param self
   * @param gong
   * @return
   */
  public int[] getLiuQin(int[] self, int gong) {
    int[] lq = new int[7];
    lq[1] = getLiuQin(gong, YiJing.DIZIWH[self[1]]);
    lq[2] = getLiuQin(gong, YiJing.DIZIWH[self[2]]);
    lq[3] = getLiuQin(gong, YiJing.DIZIWH[self[3]]);
    lq[4] = getLiuQin(gong, YiJing.DIZIWH[self[4]]);
    lq[5] = getLiuQin(gong, YiJing.DIZIWH[self[5]]);
    lq[6] = getLiuQin(gong, YiJing.DIZIWH[self[6]]);
    return lq;
  }

  /**
   * �ϲ�������
   * @param arr1
   * @param arr2
   * @return
   */
  public String[] mergeStrArray(String[] up,String[] down) {
    String[] newArray = new String[7];
    newArray[1] = down[1];
    newArray[2] = down[2];
    newArray[3] = down[3];
    newArray[4] = up[1];
    newArray[5] = up[2];
    newArray[6] = up[3];

    return newArray;
  }

  /**
   * �ϲ������Ե�֧
   * @param up
   * @param down
   * @return
   */
  public int[] mergeIntArray(int[] up, int[] down) {
    int[] dizi = new int[7];
    dizi[1] = down[1];
    dizi[2] = down[2];
    dizi[3] = down[3];
    dizi[4] = up[1];
    dizi[5] = up[2];
    dizi[6] = up[3];

    return dizi;
  }

  public int[] transToInt(Integer[] i) {
    int[] iret = new int[7];
    for(int len = 1; len<i.length; len++) {
      iret[len] = i[len].intValue();
    }
    return iret;
  }

  /**
   * ���ոɵõ�����
   * @param rigan
   * @return
   */
  public int[] getLiuShen(int rigan) {
    return YiJing.RIGANLIUSHEN[rigan];
  }

  /**
   * �жϸ�֧����֧�������໹��������
   * ���״��ε�
   * 1/2Ϊ���ࡢ3��4��5Ϊ������
   * @param zi Ϊ�ĸ���֧
   * @param yuez Ϊ��֧
   * @return
   */
  public boolean isWxsqx(int zi, int yuez) {
    return YiJing.WXSQX[yuez][YiJing.DIZIWH[zi]] > 2 ? false : true;
  }
  public int howWxsqx(int zi, int yuez) {
    return YiJing.WXSQX[yuez][YiJing.DIZIWH[zi]] ;
  }

  /**
   * �жϸ�֧����֧����������˥Ĺ��
   * ���״��ε�
   * 1/2Ϊ���ࡢ3��4��5Ϊ������
   * @param zi
   * @param rizi
   * @return
   */
  public boolean isSwmj(int zi, int rizi) {
    return YiJing.SWMJ[YiJing.DIZIWH[zi]][rizi] > 2 ? false:true;
  }
  public int howSwmj(int zi, int rizi) {
    return YiJing.SWMJ[YiJing.DIZIWH[zi]][rizi] ;
  }

  /**
   * ���������������еĸ�����֧����˥���
    //1. ��س����˥����������Ȩ����
    //2. �󰵶�س
    //3. �󶯡�����س����˥��������Ȩ
    //4. ���س֮��˥��������Ȩ
    //5. �������Ͽ�������˥
    //6. �������˺��̳���������˥
   * @param str
   * @param bRJWang
   * @param bRJZhi
   */
  public boolean getWangOrNoOut(
      StringBuffer str, int yuezi, int rigan, int rizi,
      int dizi[], int[] diziBian, int[] changes,
       int[] silents, int[] andongs, int[] actives, int[] bians) {

    //���Ͼ�
    //��Ҫ��س
    int[] zis1 = new int[15];
    int j = 0;
    for(int i=0; i<andongs.length; i++) {
      if(andongs[i] == 0)
        break;
      zis1[j++] = andongs[i];
    }
    for(int i=0; i<actives.length; i++) {
      if(actives[i] == 0)
        break;
      zis1[j++] = actives[i];
    }
    for(int i=0; i<bians.length; i++) {
      if(bians[i] == 0)
        break;
      zis1[j++] = bians[i];
    }
    zis1[j++] = rizi;
    zis1[j++] = yuezi;

    //Ҫ���Ͼ�س
    int[] zis2 = new int[15];
    for(int i=0; i<zis1.length; i++) {
      if(zis1[i] == 0)
        break;
      zis2[i] = zis1[i];
    }

    for(int i=0; i<silents.length; i++) {
      if(silents[i] == 0)
        break;
      zis2[j++] = silents[i];
    }
    getShanHeOut(str,zis1,zis2,silents,rigan,rizi,yuezi);

    //���Ͼ�
    this.getLiuHeOut(andongs,actives,bians,rizi,yuezi,str,dizi,changes,diziBian);

    return true;
  }

  /**
   * �������˺��̳���������˥
   */
  public void getSGFKHXCout(int zi, int[] silents, int[] andongs, int[] actives,
                            int[] bians, int rizi, int yuezi, StringBuffer str,
                            int rigan, int[] changes, int[] dizi,int[] diziBian) {
    //��
    str.append("\n");
    str.append(getRepeats(" ", YiJing.INTER[0]));
    str.append("֧"+YiJing.DIZINAME[zi]+"��������");
    for(int j=0; j<silents.length; j++) {
      if(silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[silents[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("��س"+YiJing.DIZINAME[silents[j]]+"��");
      }
    }
    for(int j=0; j<andongs.length; j++) {
      if(andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[andongs[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("����س"+YiJing.DIZINAME[andongs[j]]+"��");
      }
    }
    for(int j=0; j<actives.length; j++) {
      if(actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[actives[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("��س"+YiJing.DIZINAME[actives[j]]+"��");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.WXDANSHENG[YiJing.DIZIWH[bians[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("��س" + YiJing.DIZINAME[bians[j]] + "��");
      }
    }
    if(YiJing.WXDANSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("��֧"+YiJing.DIZINAME[rizi]+"��");
    }
    if(YiJing.WXDANSHENG[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("��֧"+YiJing.DIZINAME[yuezi]+"��");
    }

    //��
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("�ܹ���");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZGONG[silents[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[silents[j]] + "��");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZGONG[andongs[j]][zi] == 1) {
        str.append("����س" + YiJing.DIZINAME[andongs[j]] + "��");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZGONG[actives[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[actives[j]] + "��");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZGONG[bians[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[bians[j]] + "��");
      }
    }
    if (YiJing.DZGONG[rizi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[rizi] + "��");
    }
    if (YiJing.DZGONG[yuezi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[yuezi] + "��");
    }

    //��
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("�ܷ���");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZFU[silents[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[silents[j]] + "��");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZFU[andongs[j]][zi] == 1) {
        str.append("����س" + YiJing.DIZINAME[andongs[j]] + "��");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZFU[actives[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[actives[j]] + "��");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZFU[bians[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[bians[j]] + "��");
      }
    }
    if (YiJing.DZFU[rizi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[rizi] + "��");
    }
    if (YiJing.DZFU[yuezi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[yuezi] + "��");
    }

    //��
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("�ܿˣ�");
    for(int j=0; j<silents.length; j++) {
      if(silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANKE[YiJing.DIZIWH[silents[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("��س"+YiJing.DIZINAME[silents[j]]+"��");
      }
    }
    for(int j=0; j<andongs.length; j++) {
      if(andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANKE[YiJing.DIZIWH[andongs[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("����س"+YiJing.DIZINAME[andongs[j]]+"��");
      }
    }
    for(int j=0; j<actives.length; j++) {
      if(actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if(YiJing.WXDANKE[YiJing.DIZIWH[actives[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("��س"+YiJing.DIZINAME[actives[j]]+"��");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.WXDANKE[YiJing.DIZIWH[bians[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("��س" + YiJing.DIZINAME[bians[j]] + "��");
      }
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("��֧"+YiJing.DIZINAME[rizi]+"��");
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("��֧"+YiJing.DIZINAME[yuezi]+"��");
    }

    //��
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("�ܺ���");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZHAI[silents[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[silents[j]] + "��");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZHAI[andongs[j]][zi] == 1) {
        str.append("����س" + YiJing.DIZINAME[andongs[j]] + "��");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZHAI[actives[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[actives[j]] + "��");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZHAI[bians[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[bians[j]] + "��");
      }
    }
    if (YiJing.DZHAI[rizi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[rizi] + "��");
    }
    if (YiJing.DZHAI[yuezi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[yuezi] + "��");
    }

    //��
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("���̣�");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZXING[silents[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[silents[j]] + "��");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZXING[andongs[j]][zi] == 1) {
        str.append("����س" + YiJing.DIZINAME[andongs[j]] + "��");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZXING[actives[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[actives[j]] + "��");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZXING[bians[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[bians[j]] + "��");
      }
    }
    if (YiJing.DZXING[rizi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[rizi] + "��");
    }
    if (YiJing.DZXING[yuezi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[yuezi] + "��");
    }

    //��
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("�ܳ壺");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZCHONG[silents[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[silents[j]] + "��");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZCHONG[andongs[j]][zi] == 1) {
        str.append("����س" + YiJing.DIZINAME[andongs[j]] + "��");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZCHONG[actives[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[actives[j]] + "��");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZCHONG[bians[j]][zi] == 1) {
        str.append("��س" + YiJing.DIZINAME[bians[j]] + "��");
      }
    }
    if (YiJing.DZCHONG[rizi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[rizi] + "��");
    }
    if (YiJing.DZCHONG[yuezi][zi] == 1) {
      str.append("��֧" + YiJing.DIZINAME[yuezi] + "��");
    }

  }

  /**
   * �õ����Ͼ�
   * a) �ϻ��ɹ������������е�����س�����������ж�س�뱾λ��س֮�ϡ�
   *              && ���뻯���������½������ջ�����֮һΪ�������ա����κ�һ�������ǿ˻���֮���С�
   * b) ���ջ���������֮س��ϣ���Ϊ�϶������� ���ա����뾲سΪ�˺�ʱ������س��˥�����Ժ����ۡ�
   */
  public void getLiuHeOut(int[] andongs, int[] actives, int[] bians,
                          int rizi, int yuezi, StringBuffer str,
                          int[] dizi, int[] changes, int[] diziBian) {
    int[] zis1 = new int[6];
    int j = 0;
    int wx = 0;

    for(int i=0; i<andongs.length; i++) {
      if(andongs[i] == 0)
        break;
      zis1[j++] = andongs[i];
    }
    for(int i=0; i<actives.length; i++) {
      if(actives[i] == 0)
        break;
      zis1[j++] = actives[i];
    }
    for(int i=0; i<j; i++) {
      for(int k=0; k<j; k++) {
        if (YiJing.DZLIUHE[zis1[i]][zis1[k]] == 1) {
          //����һ��Ϊ�����Ҳ���
          wx = YiJing.DZLIUHE[zis1[i]][zis1[k]];
          if(wx > 0) {
            if (YiJing.DIZIWH[rizi] == wx || YiJing.DIZIWH[yuezi] == wx) {
              if (YiJing.WXDANKE[YiJing.DIZIWH[rizi]][wx] == 0 &&
                  YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][wx] == 0)  {
                str.append("\n");
                str.append(getRepeats(" ", YiJing.INTER[0]));
                str.append("���Ͼ֣�");
                str.append(YiJing.DIZINAME[zis1[i]]);
                str.append(YiJing.DIZINAME[zis1[k]]);
                str.append("�������ж�س�붯س�϶��ɻ�����������ͬ�ջ���֮�������պ������в��˻������С�");
              }
            }else{
              str.append("\n");
              str.append(getRepeats(" ", YiJing.INTER[0]));
              str.append("���Ϻ϶�������");
              str.append(YiJing.DIZINAME[zis1[i]]);
              str.append(YiJing.DIZINAME[zis1[k]]);
              str.append("�������ж�س�붯س�ϣ����������в�ͬ�ջ���֮���л��պ������п˻������С�");
            }
          }
        }          ;
      }
    }

    for(int i=0; i<changes.length; i++) {
      //����һ��Ϊ�����Ҳ���
      wx = YiJing.DZLIUHE[dizi[changes[i]]][diziBian[changes[i]]];
      if (wx > 0) {
        if (YiJing.DIZIWH[rizi] == wx || YiJing.DIZIWH[yuezi] == wx) {
          if (YiJing.WXDANKE[YiJing.DIZIWH[rizi]][wx] == 0 &&
              YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][wx] == 0) {
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
            str.append("���Ͼ֣�");
            str.append(YiJing.DIZINAME[dizi[changes[i]]]);
            str.append(YiJing.DIZINAME[diziBian[changes[i]]]);
            str.append("�������ж�س���س�϶��ɻ�����������ͬ�ջ���֮�������պ������в��˻������С�");
          }
        }else{
          str.append("\n");
          str.append(getRepeats(" ", YiJing.INTER[0]));
          str.append("���Ϻ϶�������");
          str.append(YiJing.DIZINAME[dizi[changes[i]]]);
          str.append(YiJing.DIZINAME[diziBian[changes[i]]]);
          str.append("�������ж�س���س�ϣ����������в�ͬ�ջ���֮���л��պ������п˻������С�");
        }
      }
    }

  }

  /**
   * �õ����Ͼ�
   * a) ʵ�Ͼ֡������ж�س����س������س���ա��³ɻ�������仯��
   *       ��������ͬ�ջ���֮���� && �պ������в��˻���������ɹ�������϶��������۰�ס��
   * b) ���Ͼ֡�a����һس������Ѯ�գ�����ʵֵ��֮ʱ��ʱ�ɾ֣���Ϊ����һ���á���Ӧ�ھ��ڡ����á�֮س�ϡ�
   * @param str
   * @param zi
   */
  public void getShanHeOut(StringBuffer str, int[] zi, int[] zi2, int[] silents,
                           int rigan, int rizi, int yuezi) {
    int wx = 0;
    boolean bool = false;
    String _s = "";

    for(int i=0; i<zi.length; i++){
      for(int j=0; j<zi.length; j++){
        bool = false;
        for(int k=0; k<zi2.length; k++){
          //����һ��Ϊ�����Ҳ���
          wx = YiJing.DZSHANHE[zi[i]][zi[j]][zi[k]];
          if(wx > 0 && _s.indexOf(""+wx) == -1) {
            _s += ""+wx;
            if(YiJing.DIZIWH[rizi] == wx || YiJing.DIZIWH[yuezi] == wx) {
              if(YiJing.WXDANKE[YiJing.DIZIWH[rizi]][wx] == 0 &&
                 YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][wx] == 0) {
                //�Ƿ���һس����
                for(int l=0; l<silents.length; l++) {
                  if(silents[l] == zi2[k]) {
                    bool = true;
                    break;
                  }
                }
                if(!bool) {
                  //�Ƿ���һسΪѮ��
                  if(!isXunKong(zi[i],rigan,rizi) && !isXunKong(zi[j],rigan,rizi) &&
                     !isXunKong(zi2[k],rigan,rizi)) {
                    str.append("\n");
                    str.append(getRepeats(" ", YiJing.INTER[0]));
                    str.append("���Ͼ�ʵ�Ͼ֣�");
                    str.append(YiJing.DIZINAME[zi[i]]);
                    str.append(YiJing.DIZINAME[zi[j]]);
                    str.append(YiJing.DIZINAME[zi[k]]);
                    str.append("�������ж����䡢�������ա��º϶��ɻ�����������ͬ�ջ���֮�������պ������в��˻������С�");
                }else{
                  str.append("\n");
                  str.append(getRepeats(" ", YiJing.INTER[0]));
                  str.append("���Ͼִ��Ͼ֣�");
                  str.append(YiJing.DIZINAME[zi[i]]);
                  str.append(YiJing.DIZINAME[zi[j]]);
                  str.append(YiJing.DIZINAME[zi[k]]);
                  str.append("����������س�϶��ɻ�����������ͬ�ջ���֮�������պ������в��˻������С���һسΪѮ��,������ʱ�ɾ֣���Ϊ����һ���á���Ӧ�ھ��ڴ���֮س�ϡ�");

                }
              }else{
                if(!isXunKong(zi[i],rigan,rizi) && !isXunKong(zi[j],rigan,rizi) &&
                     !isXunKong(zi2[k],rigan,rizi)) {
                    str.append("\n");
                    str.append(getRepeats(" ", YiJing.INTER[0]));
                    str.append("���Ͼִ��Ͼ֣�");
                    str.append(YiJing.DIZINAME[zi[i]]);
                    str.append(YiJing.DIZINAME[zi[j]]);
                    str.append(YiJing.DIZINAME[zi[k]]);
                    str.append(
                        "����������س�϶��ɻ�����������ͬ�ջ���֮�������պ������в��˻������С���һس����,����ʱ�ɾ֣���Ϊ����һ���á���Ӧ�ھ��ڴ���֮س�ϡ�");
                  }else if(!isXunKong(zi[i],rigan,rizi) && !isXunKong(zi[j],rigan,rizi) &&
                     isXunKong(zi2[k],rigan,rizi)){
                    str.append("\n");
                    str.append(getRepeats(" ", YiJing.INTER[0]));
                    str.append("���Ͼִ��Ͼ֣�");
                    str.append(YiJing.DIZINAME[zi[i]]);
                    str.append(YiJing.DIZINAME[zi[j]]);
                    str.append(YiJing.DIZINAME[zi[k]]);
                    str.append(
                        "����������س�϶��ɻ�����������ͬ�ջ���֮���У����������в��˻������С���һس������ǡѮ�գ���������Ҳ��");
                  }
              }
            }
          }else{
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
            str.append("���Ϻ϶������֣�");
            str.append(YiJing.DIZINAME[zi[i]]);
            str.append(YiJing.DIZINAME[zi[j]]);
            str.append(YiJing.DIZINAME[zi[k]]);
            str.append("����������س�ϣ����������в�ͬ�ջ���֮���У����պ������п˻������С�");
          }
        }
      }
    }
  }
  }

  /**
   * �ж�һ����֧�Ƿ�����
   * @return
   */
  public boolean isZiWang(
      StringBuffer str, int zi, int yuezi, int rigan ,int rizi, int[] changes,
      int[] dizi, int[] diziBian) {

    int[] silents = getSilents(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] andongs = getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] actives = getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] bians   = getBians(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);

    for(int i=0; i<silents.length; i++) {
      if(silents[i] == zi)
        return true;
    }
    for (int i = 0; i < andongs.length; i++) {
      if (andongs[i] == zi)
        return true;
    }
    for (int i = 0; i < actives.length; i++) {
      if (actives[i] == zi)
        return true;
    }
    for (int i = 0; i < bians.length; i++) {
      if (bians[i] == zi)
        return true;
    }
    return false;
  }

  /**
   * �������еİ���س
   * @return
   */
  public int[] getAnDongs(StringBuffer str, int rigan, int rizi, int yuezi,
                         int[] changes, int[] dizi, int[] diziBian,boolean bl) {
   int zi = 0;
   int[] silent = new int[6];
   int j = 0;
   String _temp = null;
   int start = 0;
   int end = 0;
   boolean bool = false;

   for (int i = 1; i <= 6; i++) {
     bool = false;
     for (int k = 0; k < changes.length; k++) {
       if (i == changes[k]) {
         bool = true;
         break;
       }
     }
     if(bool)
       continue;

      zi = dizi[i];
      _temp = "\n";
      _temp += getRepeats(" ", YiJing.INTER[0]);
      _temp += "����س" + YiJing.DIZINAME[zi] + "��";
      start = str.length();
      if(bl)
        str.append(_temp);
      end = str.length();
      if (isAndong(str, zi, yuezi, rizi, changes, dizi, diziBian,bl)) {
        if (isHasQuanForSilen(str, zi, yuezi, rigan, rizi, changes, dizi,
                              diziBian,bl)) {
          if(bl) {
            if(isXunKong(zi,rigan,rizi))
             str.append("�ð���س��Ѯ�յ�������Ȩ��");
           else
             str.append("�ð���س������Ȩ��");
          }
        }
        silent[j++] = zi;
      }else{
        str.delete(start,end);
      }
    }

    return silent;
  }

  /**
   * �������еľ�س
   * @return
   */
  public int[] getSilents(StringBuffer str, int rigan, int rizi, int yuezi,
                         int[] changes, int[] dizi, int[] diziBian,boolean bl) {
   int zi = 0;
   int[] silent = new int[6];
   int j = 0;
   boolean bool = false;

   for (int i = 1; i <= 6; i++) {
     bool = false;
     for (int k = 0; k < changes.length; k++) {
       if (i == changes[k]) {
         bool = true;
         break;
       }
     }
     if(bool)
       continue;

     zi =  dizi[i];
     if (!isAndong(str, zi, yuezi, rizi, changes, dizi, diziBian,false)) {
       if(bl) {
         str.append("\n");
         str.append(getRepeats(" ", YiJing.INTER[0]));
         str.append("��س" + YiJing.DIZINAME[zi] + "��");
       }
       if (isHasQuanForSilen(str, zi, yuezi, rigan, rizi, changes, dizi,
                             diziBian,bl)) {
         if(bl) {
           if(isXunKong(zi,rigan,rizi))
             str.append("�þ�س��Ѯ�յ�������Ȩ��");
           else
             str.append("�þ�س������Ȩ��");
         }
       }
       silent[j++] = zi;
     }
   }

   return silent;
 }


  /**
   * �������еĶ�س
   * @return
   */
  public int[] getActives(StringBuffer str, int rigan, int rizi, int yuezi,
                          int[] changes, int[] dizi, int[] diziBian,boolean bl) {
    int zi = 0;
    int[] silent = new int[6];
    int j = 0;
    boolean bool = false;

    for (int i = 1; i <= 6; i++) {
      bool = false;
      for (int k = 0; k < changes.length; k++) {
        if (i == changes[k]) {
          bool = true;
          break;
        }
      }
      if(!bool)
        continue;

      zi =  dizi[i];
      if(bl) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("��س" + YiJing.DIZINAME[zi] + "��");
      }
      if (isHasQuanForActive(str, zi, yuezi, rizi, changes, dizi,
                                  diziBian, i, rigan,bl)) {
        if(bl) {
          if(isXunKong(zi,rigan,rizi))
             str.append("�ö�س��Ѯ�յ�������Ȩ��");
           else
             str.append("�ö�س������Ȩ��");
        }
      }
      silent[j++] = zi;
    }

    return silent;
  }

  /**
   * �������еĵı�س
   * @return
   */
  public int[] getBians(StringBuffer str, int rigan, int rizi, int yuezi,
                          int[] changes, int[] dizi, int[] diziBian,boolean bl) {
    int zi = 0;
    int[] silent = new int[6];
    int j = 0;

    for (int i = 0; i < changes.length; i++) {
      if(changes[i]==0)
        continue;
      zi =  diziBian[changes[i]];
      if(bl) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("��س" + YiJing.DIZINAME[zi] + "��");
      }
      if (isHasQuanForBian(str, zi, yuezi, rizi, rigan, changes, dizi,
                                  diziBian,bl)) {
        if(bl) {
          if(isXunKong(zi,rigan,rizi))
             str.append("�ñ�س��Ѯ�յ�������Ȩ��");
           else
             str.append("�ñ�س������Ȩ��");
        }
      }
      silent[j++] = zi;
    }

    return silent;
  }


  /**
   * �õ���س������Ȩ
   * @return
   */
  public boolean isHasQuanForBian(
      StringBuffer str, int zi, int yuezi, int rizi,int rigan,
      int[] changes, int[] dizi, int[] diziBian, boolean bl) {
    //��سѮ�գ���س�����գ�����سѮ�գ���سҲ����������������Ȩ������س����ʱ��������Ȩ��
    //�嶯س����Ѯ�յı�س���������á�
    if(isXunKong(zi,rigan,rizi)) {
      if(bl)
        str.append("�ñ�س��Ѯ�գ�������Ȩ��ֻ�д���س����ʱ��������Ȩ���嶯س����Ѯ�յı�س���������á�");
      return false;
    }

    //�½����س������˥��Ϊ���ơ�������֮��س�������������ܿ�֮��س���³壬
    //�ڱ�سֵ�ջ���պ�֮�գ�Ҳ������Ȩ������������Ȩ���ճ�����ʱ���պ�ס�ɽ⡣
    if(YiJing.DZCHONG[yuezi][zi] == 1) {
      if(isWxsqx(zi,yuezi) || (!isWxsqx(zi,yuezi) && !isDongAndBianKe(zi,changes,dizi,diziBian,true)))
        if(zi != rizi && YiJing.DZLIUHE[zi][rizi] == 0) {
          if(bl)
            str.append("�ñ�س���³壬��������������ܿ˵���ֵ�ջ���պϣ�ֻ�з����ʱ���պϻ���²�������Ȩ��");
          return false;
        }
    }

    return true;
  }

  /**
   * �õ���س�밵��س����Ȩ
   * @param local ��֧��λ��
   * @return
   */
  public boolean isHasQuanForActive(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int local, int rigan, boolean bl) {
    boolean bool1 = isHasQuanForActive2(str, zi, yuezi, rizi, changes, dizi, diziBian, local, rigan, bl);
    if(!bool1) {
      return bool1;
    }
    else {
      boolean bool2 = getTwoDongYaoChong(str, zi, yuezi, rizi, changes, dizi, diziBian, local, rigan, bl);
      return bool2;
    }
  }

  public boolean isHasQuanForActive2(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int local, int rigan, boolean bl) {
    //��سѮ�գ���ա����վ���������������ְ�ܡ�
   if(isXunKong(zi,rigan,rizi)) {
     if(!isRiYueChong(zi,rizi,yuezi) && !isDongAndBianChong(zi,changes,dizi,diziBian)) {
       if(bl)
         str.append("�ö�س��Ѯ�գ������ա��¡�������֮س��֮����ʧȥ����Ȩ��ֻ�г�ա����ղ���������������ְ�ܡ�");
       return false;
     }
   }
   //��سѮ�գ���س�����գ�����سѮ�գ���سҲ����������������Ȩ������س����ʱ��������Ȩ��
    //�嶯س����Ѯ�յı�س���������á�
    if(isXunKong(diziBian[local],rigan,rizi)) {
      if(bl)
        str.append("�ö�س����֮س��Ѯ�գ���سҲʧȥ����Ȩ��ֻ�д���س����ʱ��������Ȩ���嶯س����Ѯ�յı�س���������á�");
      return false;
    }

    //�½��嶯س������˥��Ϊ���ơ�������֮��س�������������ܿ�֮��س���³壬
    //�ڶ�سֵ�ջ���պ�֮�գ�Ҳ������Ȩ������������Ȩ���ճ�����ʱ���պ�ס�ɽ⡣
    if(YiJing.DZCHONG[yuezi][zi] == 1) {
      if(isWxsqx(zi,yuezi) || (!isWxsqx(zi,yuezi) && !isDongAndBianKe(zi,changes,dizi,diziBian,true)))
        if(zi != rizi && YiJing.DZLIUHE[zi][rizi] == 0) {
          if(bl)
            str.append("�ö�س���³壬��������������ܿ˵���ֵ�ջ���պϣ�ֻ�з����ʱ���պϻ���²�������Ȩ��");
          return false;
        }
    }
    //�ս���������سν��ɢ���˶�س��������Ȩ�����ɽ⡣
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[rizi][zi] == 1) {
        if(bl)
          str.append("��������س���ճ����ɢ����������Ȩ�����ɽ⡣");
        return false;
      }
    }
    //��λ��س���س������ʱ������س�����ɢ��ʧȥ����Ȩ��
    //����ʱ�������س�Զ�سֻ�岻��ʱ����λ��س����������������Ȩ��
    // ��س�Ա�λ��س�������ʱ�����۶�س��˥����س������Ȩ��ֻ�б�λ��س���ա��½�ʱ����λ��س��������Ȩ��
    if(YiJing.DZCHONG[diziBian[local]][zi] == 1) {
      if(!isWxsqx(zi,yuezi)) {
        str.append("��������س����س�����ɢ��ʧȥ����Ȩ��");
        return false;
      }else{
        if(YiJing.WXDANKE[YiJing.DIZIWH[diziBian[local]]][YiJing.DIZIWH[zi]] == 1) {
          if(rizi != zi && YiJing.DZLIUHE[yuezi][zi] != 1 && YiJing.DZLIUHE[rizi][zi] != 1) {
            if(bl)
              str.append("�ö�س�����൫����س������ˣ��ֲ�ֵ�½���ֵ�ա����ºϣ�ʧȥ����Ȩ��");
            return false;
          }
        }
        //if(YiJing.WXDANKE[YiJing.DIZIWH[diziBian[local]]][YiJing.DIZIWH[zi]] == 0) {
        //  str.append("�ö�س�����൫����سֻ�岻�ˣ�������Ȩ������С��");
        //  return false;
        //}
      }
    }
    //��س���ա�������ֻ�����������ܿˣ������ˡ���й������������Ȩ��С��
    //����ͷ�ˣ�������Ȩ��������ͷ����������������Ȩ��
    if(!isWxsqx(zi,yuezi) && !isSwmj(zi,rizi)) {
      if(YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 0 &&
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 0 )
        if(YiJing.WXDANKE[YiJing.DIZIWH[diziBian[local]]][YiJing.DIZIWH[zi]] == 1) {
          if(bl)
            str.append("�ö�س���ա�������ֻ�����������ܿˣ�������ͷ�ˣ�������Ȩ��");
          return false;
        }
    }
    //��س���ա���˫�����ܳ�ˣ�������Ȩ��
    if((YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1 &&
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ) ||
      (YiJing.DZCHONG[rizi][zi] == 1 && YiJing.DZCHONG[yuezi][zi] == 1) ||
      (YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1 &&
       YiJing.DZCHONG[rizi][zi] == 1) ||
      (YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 &&
       YiJing.DZCHONG[yuezi][zi] == 1)) {
     if(bl)
       str.append("�ö�س���ա���˫�����ܳ�ˣ�������Ȩ��");
     return false;
   }
   //����س���ջ��ºϣ�������˥�����۰�ס����ʱʧȥ����Ȩ���ޱ�س��������
   //�����ߵ��ˣ�֤���ޱ�س��֮
   if(YiJing.DZLIUHE[zi][yuezi] == 1 || YiJing.DZLIUHE[zi][rizi] == 1) {
     if(bl)
       str.append("�ö�س���ա�����һ����ס�ư�ס�����ޱ�س���壬��ʱʧȥ����Ȩ��");
     return false;
   }

    return true;
  }

  //��س�붯س��壬��(ָ�Ƿ��б�س���ա�������)��ʤ��˥�߰ܣ�����������Ȩ��
    //˥�߳��ɢ������Ȩ��˫����������Ϊ���ܾ��ˣ���������Ȩ��
    public boolean getTwoDongYaoChong(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int local, int rigan, boolean bl) {
      for (int i = 0; i < changes.length; i++) {
        if (YiJing.DZCHONG[dizi[changes[i]]][zi] == 1) {
          boolean dong1 = isHasQuanForActive2(str, zi, yuezi, rizi, changes, dizi,
                                             diziBian, local, rigan, false);
          boolean dong2 = isHasQuanForActive2(str, dizi[changes[i]], yuezi, rizi,
                                             changes, dizi, diziBian, local,
                                             rigan, false);
          if (dong1 && dong2) {
            if (bl)
              str.append("�����දس����һ���දس��Ϊ���ܾ��ˣ�������Ȩ��");
            return false;
          }
          if (!dong1 && !dong2) {
            if (bl)
              str.append("��������س����һ������س��������ɢ��������Ȩ��");
            return false;
          }
          if (!dong1 && dong2) {
            if (bl)
              str.append("��������س����һ���දس������ɢ��������Ȩ��");
            return false;
          }
        }
      }
      return true;
    }


  /**
   * �õ���س������Ȩ
   * @return
   */
  public boolean isHasQuanForSilen(
      StringBuffer str, int zi, int yuezi, int rigan ,int rizi,
      int[] changes, int[] dizi, int[] diziBian,boolean bl) {
    //��سѮ�գ�����������������������Ȩ
    if(isXunKong(zi,rigan,rizi) && !isWxsqx(zi,yuezi) && !isSwmj(zi,rizi)) {
       if(bl)
         str.append("�þ�سѮ�գ�������������������������Ȩ��");
       return false;
    }

    //�½���������سΪ�����ƣ���������Ȩ��
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][yuezi] == 1) {
        if(bl)
          str.append("��������س���³壬Ϊ�����ƣ���������Ȩ��");
        return false;
      }
    }
    //�½������ྲس��������Ϊ���ƣ�������Ȩ�� �����պϡ�ֵ�ռ����±�������Ȩ��
    if(isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][yuezi] == 1) {
        if(zi != rizi ||
           YiJing.DZLIUHE[zi][rizi] != 1) {
          if(bl)
            str.append("�����ྲس���³壬�����պϡ�ֵ�������ȣ�Ϊ����������Ȩ�����±��С�");
          return false;
        }
      }
    }
    //�ս�����س����س����֧֮������س���ж��䡢����֮س�ˡ�й����سʱ��Ϊ���ơ�������Ȩ������֮س���ܿ˶���س
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][rizi] == 1 || this.isDongAndBianChong(zi,changes,dizi,diziBian))
         if(isDongAndBianKe(zi,changes,dizi,diziBian,false) ||
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1||
         YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]]  == 1) {
        if (bl) {
          str.append("�����ྲس���ջ򶯻����ɱ�س�壬���ж��䡢����֮س�ˡ�й����Ϊ���ƣ�������Ȩ��");
        }
        return false;
      }
    }
    //����֮س���ճ��������Ϊ���ơ�������Ȩ��
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][rizi] == 1 &&
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1){
        if(bl)
          str.append("��������س���ճ��������Ϊ���ƣ�������Ȩ��");
        return false;
      }
    }
    //��س���ա���������������������������Ȩ��
    if(!isWxsqx(zi,yuezi) && !isSwmj(zi,rizi)) {
      if(bl) {
        str.append("�þ�س���ա�������������������������Ȩ��");
      }
      return false;
    }
    //��س���ա���һ���ܿˣ�һ������Ҳ������Ȩ��
    if((!isWxsqx(zi,yuezi) && YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1) ||
      (!isSwmj(zi,rizi) && YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1)) {
      if(bl)
        str.append("�þ�س���ա���һ���ܿ�һ��������������Ȩ��");
      return false;
    }
    //��س���ա���˫�����ܿˣ���������Ȩ��
    if(YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 &&
       YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) {
      if(bl)
        str.append("�þ�س���ա���˫�����ܿˣ�������Ȩ��");
      return false;
    }
    //��س���ա���һ������һ���ܿˣ���������Ȩ�ؼ������ж�س���򱳡������������ˣ���������Ȩ��
    if((YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
       YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) &&
      (YiJing.WXXIANGSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
       YiJing.WXXIANGSHENG[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1)){
      if(isDongAndBianKe(zi,changes,dizi,diziBian,false)) {
        if(bl) {
          str.append("�þ�س���ա���һ������һ���ܿˣ����ж���س��֮��������Ȩ��");
        }
        return false;
      }
    }

    return true;
  }

  /**
   * �Ƿ��ǰ���س
   * @return
   */
  public boolean isAndong(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian,boolean bl) {

    //�½���ֵ��֮��س�����������Ʒ���Ϊ����
    if(zi == rizi && YiJing.DZCHONG[zi][yuezi] == 1) {
      if(bl)
        str.append("�þ�سֵ�շ��³壬���������Ʒ���Ϊ������");
      return true;
    }
    //�ս�����֧֮���ྲس��Ϊ��������س���������ˣ�����������������س����
    if(isWxsqx(zi, yuezi) && YiJing.DZCHONG[zi][rizi] == 1) {
      if(bl)
        str.append("�����ྲس���ճ壬Ϊ������");
      return true;
    }
    //�ս�����֧֮������س���ж���֮س��֮Ϊ������
    if(!isWxsqx(zi, yuezi) && YiJing.DZCHONG[zi][rizi] == 1 &&
       isDongAndBianSheng(zi,changes,dizi,diziBian)){
      if(bl)
        str.append("��������س���ճ壬���ж���֮س��֮Ϊ������");
      return true;
    }
    //��س�������е����ྲس��Ϊ�������˱�س�붯س����û���κι�ϵ�����Ⱥ�
    if(isWxsqx(zi,yuezi)) {
       int[] doBians = getDoBians(changes, dizi, diziBian);
       for (int i = 0; i < doBians.length; i++) {
         if(YiJing.DZCHONG[doBians[i]][zi] == 1) {
           if(bl)
             str.append("�����ྲس���붯سû�������̳��ϵ�ı�س�壬Ϊ������");
           return true;
         }
       }
     }
     //��س�������е�������س���ж��䡢����֮س��֮Ϊ������
     if(!isWxsqx(zi,yuezi)) {
       int[] doBians = getDoBians(changes, dizi, diziBian);
       for (int i = 0; i < doBians.length; i++) {
         if (YiJing.DZCHONG[doBians[i]][zi] == 1) {
           if (YiJing.WXDANSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
               isDongAndBianSheng(zi, changes, dizi, diziBian)) {
             if(bl)
               str.append("��������س����س�壬���ж��䡢����֮Ϊ������");
             return true;
           }
         }
       }
     }
     //��س�������е����ྲس��Ϊ����
     if(isWxsqx(zi,yuezi)) {
       for (int i = 0; i < changes.length; i++) {
         if(YiJing.DZCHONG[dizi[changes[i]]][zi] == 1) {
           if(bl)
             str.append("�����ྲس����س�壬Ϊ������");
           return true;
         }
       }
     }
     //��س�������еĵ�������س���ж��䡢����֮س��֮Ϊ������
     if(!isWxsqx(zi,yuezi)) {
       for (int i = 0; i < changes.length; i++) {
         if (YiJing.DZCHONG[dizi[changes[i]]][zi] == 1) {
           if (YiJing.WXDANSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
               isDongAndBianSheng(zi, changes, dizi, diziBian)) {
             if(bl)
               str.append("��������س����س�壬���ж��䡢����֮Ϊ������");
             return true;
           }
         }
       }
     }
     //����س���ա���̨���ھ�س����ʱ��ν֮�����൱�ڶ�س��
     if(isWxsqx(zi,yuezi)) {
       if(YiJing.DZLIUHE[zi][rizi] == 1 ||
          YiJing.DZLIUHE[zi][yuezi] == 1) {
         if (bl)
           str.append("�����ྲس���ա��ºϣ�ν֮�����൱�ڶ�س��");
         return true;
       }
     }

    return false;
  }

  /**
   * �õ�������������س�ı�س�ĵ�֧
   * @return
   */
  public int[] getDoBians(int[] changes, int[] dizi, int[] diziBian) {
    int[] bian = new int[6];
    int j = 0;

    for(int i=0; i<changes.length; i++) {
      if(isDoseBian(dizi[changes[i]],diziBian[changes[i]])) {
        bian[j++] = diziBian[changes[i]];
      }
    }

    int[] retBian = new int[j];
    for(int i=0; i<j; i++) {
      retBian[i] = bian[i];
    }

    return retBian;
  }

  /**
   * �˱�س�Ƿ��붯سû���κ����˳��ϵ����ȥ��������س
   * @return
   */
  public boolean isDoseBian(int dong, int bian) {
    return dong == bian;
  }

  /**
   * �Ƿ��ж���֮س��֮
   * @return
   */
  public boolean isDongAndBianSheng(int zi, int[] changes, int dizi[], int[] diziBian) {
    for(int i=0; i<changes.length; i++) {
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[changes[i]]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
    }
    int[] doBians = getDoBians(changes,dizi,diziBian);
    for(int i=0; i<doBians.length; i++) {
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[doBians[i]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
    }

    return false;
  }

  /**
   * �Ƿ��ж���֮س�˸�س
   * �����س�Ƕ�س�����࣬���ܷ�����˶���س
   * @return
   */
  public boolean isDongAndBianKe(int zi, int[] changes, int dizi[], int[] diziBian, boolean isSheng) {
    for(int i=0; i<changes.length; i++) {
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[changes[i]]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
    }
    int[] doBians = getDoBians(changes,dizi,diziBian);
    for(int i=0; i<doBians.length; i++) {
      if(YiJing.WXDANKE[YiJing.DIZIWH[doBians[i]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
      if(isSheng) {
        if(YiJing.WXDANSHENG[YiJing.DIZIWH[zi]][YiJing.DIZIWH[doBians[i]]] == 1 )
          return true;
        if(YiJing.WXDANKE[YiJing.DIZIWH[zi]][YiJing.DIZIWH[doBians[i]]] == 1 )
          return true;
      }
    }

    return false;
  }

  /**
   * �Ƿ��ж���֮س���س
   * @return
   */
  public boolean isDongAndBianChong(int zi, int[] changes, int dizi[], int[] diziBian) {
    for(int i=0; i<changes.length; i++) {
      if(YiJing.DZCHONG[dizi[changes[i]]][zi] == 1 )
        return true;
    }
    int[] doBians = getDoBians(changes,dizi,diziBian);
    for(int i=0; i<doBians.length; i++) {
      if(YiJing.DZCHONG[doBians[i]][zi] == 1 )
        return true;
    }

    return false;
  }

  /**
   * �Ƿ�������֮س���س
   * @return
   */
  public boolean isRiYueChong(int zi, int rizi, int yuezi) {
    return (YiJing.DZCHONG[rizi][zi] == 1) && (YiJing.DZCHONG[yuezi][zi] ==1) ;
  }

  /**
   * ����Ѯ�գ�Ϊ��һ֧*100+�ڶ�֧
   * �Ƿ���Ѯ��
   * @return
   */
  public int getXunKong(int rigan, int rizi) {
    return YiJing.KONGWANG[rigan][rizi];
  }
  public boolean isXunKong(int zi, int rigan ,int rizi) {
    int k = getXunKong(rigan, rizi);
    if(zi == k/100 || zi == (k - k/100 * 100))
      return true;
    return false;
  }

  /**
   * �õ����λ�ü��Ƿ��Ƿ���
   * @param ysLiuqin
   * @param liuqin
   * @param liuqinGong
   * @param ysLocal
   * @return
   */
  public boolean getShenLocal(int ysLiuqin ,int[] liuqin,
                              int[] liuqinGong, int[] ysLocal) {
    int j = 0;
    boolean isFu = true;
    //�õ������λ��
    for (int i = 1; i < liuqin.length; i++) {
      if (liuqin[i] == ysLiuqin) {
        isFu = false;
        ysLocal[j++] = i;
      }
    }
    //������֣���õ������������λ��
    if(isFu) {
      for(int i = 1; i<= 6; i++) {
        if(liuqinGong[i] == ysLiuqin) {
          ysLocal[j++] = i;
        }
      }
    }

    return isFu;
  }

  /**
   * �жϸ�֧�Ƿ��Ǿ�س���Ƕ�������س
   * @return
   */
  public boolean isDongOrJing(
      StringBuffer str, int zi, int yuezi, int rizi,int[] changes,
      int[] dizi, int[] diziBian) {
    if(isAndong(str,zi,yuezi,rizi,changes,dizi,diziBian,false))
      return true;
    for(int i=0; i<changes.length; i++) {
      if(dizi[changes[i]] == zi)
        return true;
    }

    return false;
  }

  /**
   * �ö�س����س�Ƿ���������س����س���س���º�
   * @return
   */
  public boolean isHe(
      StringBuffer str, int rigan, int rizi, int yuezi,
      int[] changes, int[] dizi, int[] diziBian, int zi) {
    int[] actives = getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] andongs = this.getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] zis1 = new int[6];
    int j = 0;
    int wx = 0;

    for(int i=0; i<andongs.length; i++) {
      if(andongs[i] == 0)
        break;
      zis1[j++] = andongs[i];
    }
    for(int i=0; i<actives.length; i++) {
      if(actives[i] == 0)
        break;
      zis1[j++] = actives[i];
    }
    for(int i=0; i<j; i++) {
      if (YiJing.DZLIUHE[zis1[i]][zi] == 1) {
        return true;
      }
      ;
    }

    for(int i=0; i<changes.length; i++) {
      if(dizi[changes[i]] != zi)
        continue;
      wx = YiJing.DZLIUHE[dizi[changes[i]]][diziBian[changes[i]]];
      if (wx > 0) {
        return true;
      }
    }

    if(YiJing.DZLIUHE[zi][rizi] == 1 || YiJing.DZLIUHE[zi][yuezi] == 1)
      return true;
    return false;
  }

  /**
   * �þ�س�Ƿ񱻳�
   * @return
   */
  public boolean isJingChong(StringBuffer str, int rigan, int rizi, int yuezi,
                             int[] changes, int[] dizi, int[] diziBian, int zi) {
    int[] actives = this.getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] andongs = this.getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] bians = this.getDoBians(changes,dizi,diziBian);
    for(int i=0; i<actives.length; i++) {
      if(YiJing.DZCHONG[zi][actives[i]] == 1)
        return true;
    }
    for(int i=0; i<andongs.length; i++) {
      if(YiJing.DZCHONG[zi][andongs[i]] == 1)
        return true;
    }
    for(int i=0; i<bians.length; i++) {
      if(YiJing.DZCHONG[zi][bians[i]] == 1)
        return true;
    }
    if(YiJing.DZCHONG[zi][rizi] == 1 || YiJing.DZCHONG[zi][yuezi] == 1)
        return true;

      return false;
  }
}
