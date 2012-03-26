package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.YiJing;
import org.boc.db.qm.QimenCaiyun;
import org.boc.db.qm.QimenGeju1;
import org.boc.db.qm.QimenGongzuo;
import org.boc.db.qm.QimenHunyin;
import org.boc.db.qm.QimenJibing;
import org.boc.db.qm.QimenMingyun;
import org.boc.db.qm.QimenPublic;
import org.boc.db.qm.QimenXuexi;
import org.boc.ui.ResultPanel;

public class DelQiMenMain {
  private StringBuffer str;
  private PrintWriter pw;
  
  private DaoSiZhuMain dao;
  private DaoYiJingMain daoY;
  private DaoQiMen daoq;
  
  private QimenPublic pub;
  private QimenMingyun minyun;
  private QimenCaiyun caiyun;
  private QimenHunyin hunyin;
  private QimenJibing jibing;
  private QimenXuexi xuexi;
  private QimenGeju1 buju;  
  private QimenGongzuo work;
  
  private int jigong ;  //�������幬�ĺι� ,��QiMenFrame�и�ֵ,qm.setJigong(iJigong); 

  public DelQiMenMain() {
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    dao = new DaoSiZhuMain();
    daoq = new DaoQiMen();
    daoq.setJigong(jigong);
    daoY = new DaoYiJingMain();
    
    pub = new QimenPublic(daoq,dao);
    minyun = new QimenMingyun(pub);
    caiyun = new QimenCaiyun(daoq, pub);
    hunyin = new QimenHunyin(daoq, pub);
    jibing = new QimenJibing(pub);
    xuexi = new QimenXuexi(daoq, pub,dao);
    buju = new QimenGeju1(daoq, pub);
    work = new QimenGongzuo(daoq, pub);
  }
  
  public DaoQiMen getDaoQiMen() {
  	return this.daoq;
  }
  public QimenPublic getQimenPublic() {
  	return this.pub;
  }
  public DaoSiZhuMain getDaoSiZhuMain() {
  	return this.dao;
  }
  
  /**
   * �õ�������ҵ����Ϣ����ʱ������
   */
  public String getWork(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
    //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //���������ҵ��Ϣ
    work.getWork(str, mzText, ysNum, isMan);
  	
  	return str.toString();
  }
  
  /**
   * �õ�������ҵ��Ϣ���ɸ�֧����
   * @return
   */
  public String getWork(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
  //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //���������ҵ��Ϣ
  	work.getWork(str, mzText, ysNum, isMan);
  	
  	
  	return str.toString();
  }
  
  /**
   * �õ�ѧϰ���Ե���Ϣ����ʱ������
   * @param mzText : Ϊ������Ϣ����ʽ��1997;1998;����1,1;2,2;
   * @param ysNum: ������ţ����1���¸�2���ո�3��ʱ��4
   */
  public String getXuexi(String mzText, int ysNum, int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
    //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //���ѧϰ������Ϣ
    xuexi.getXuexi(str,mzText, ysNum);
  	
  	return str.toString();
  }
  
  /**
   * �õ�ѧϰ������Ϣ���ɸ�֧����
   * @param mzText : Ϊ������Ϣ����ʽ��1997;1998;����1,1;2,2;
   * @param ysNum: ������ţ����1���¸�2���ո�3��ʱ��4
   * @return
   */
  public String getXuexi(String mzText, int ysNum, int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
  //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //���ѧϰ������Ϣ
  	xuexi.getXuexi(str,mzText, ysNum);
  	
  	
  	return str.toString();
  }
  
  /**
   * �õ����1����Ϣ
   * iZfs�� Сֵ����1Ϊ���̣�2Ϊ����
   */
  public String getGeju1(ResultPanel rp, int y, int m, int d, int h,int mi,
      int ysnum, boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju, String mzText) {
  	str.delete(0,str.length());
  	pub.setBoy(isMan);
  	pub.setZf(iszf==1);  //ת��Ϊ1Ϊtrue
  	pub.setTd(iZfs==1);  //Сֵ��ʹ��Ĭ��������1Ϊtrue
  	pub.setCy(iscy==2);  //�����𲹣�Ĭ�ϲ�2Ϊtrue
  	pub.setJigong(jigong);  	
  	pub.setYongshen(ysnum);
  	pub.setNianming(mzText);
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
    //����ɾ��ľ�
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");

    //�������������Ϣ���˴��ã�����Ϊ5���Ƚ����⣬Ҫ�������
    buju.getGeju(rp, iszf, str);
  	
  	return null;
  }
  
  /**
   * �õ������Ϣ���ɸ�֧����
   * @return
   */
  public String getGeju1(ResultPanel rp, int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
  		int ysnum, boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju, String mzText) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
  //����ɾ��ľ�
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");
    
  //�������������Ϣ
  	buju.getGeju(rp, iszf, str);
  	
  	
  	//return str.toString();
  	return null;
  }
  
//  /**
//   * �õ���������Ϣ����ʱ������
//   */
//  public String getJibing(int y, int m, int d, int h,int mi,
//      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
//      int iszf, int iscy,  int iZfs,int ju) {
//  	str.delete(0,str.length());
//  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
//  	
//    //���ʱ����Ϣ
//    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
//    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
//    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //���ʱ��ͷ��
//    str.append("\r\n");
//    str.append("\r\n");
//    
//    //����ɾ��ľ�
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");
//
//    //���������Ϣ
//    jibing.getBing(str,mzText,ysnum,isMan);
//  	
//  	return str.toString();
//  }
//  
//  /**
//   * �õ�������Ϣ���ɸ�֧����
//   * @return
//   */
//  public String getJibing(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
//      boolean yun, int sheng ,int shi,boolean isMan,
//      int iszf, int iscy, int iZfs,int ju) {
//  	str.delete(0,str.length());
//  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
//  	
//    //���ʱ����Ϣ
//    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
//    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
//    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //���ʱ��ͷ��
//    str.append("\r\n");
//    str.append("\r\n");
//    
//  //����ɾ��ľ�
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");
//    
//  //���������Ϣ
//  	jibing.getJibing(str);
//  	
//  	
//  	return str.toString();
//  }
  
  /**
   * �õ����˵���Ϣ����ʱ������
   */
  public String getCaiyun(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
    //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //���������Ϣ
    caiyun.getCaiyun(str, mzText, ysNum, isMan, iszf);
  	
  	return str.toString();
  }
  /**
   * �õ�������Ϣ���ɸ�֧����
   * @return
   */
  public String getCaiyun(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
  //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //���������Ϣ
  	caiyun.getCaiyun(str, mzText, ysNum, isMan, iszf);
  	
  	
  	return str.toString();
  }
  
  /**
   * �õ���������Ϣ����ʱ������
   */
  public String getHunyin(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
    //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //���������Ϣ
    hunyin.getHunyin(str, mzText, ysNum, isMan);
  	
  	return str.toString();
  }
  /**
   * �õ�������Ϣ���ɸ�֧����
   * @return
   */
  public String getHunyin(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
  //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //���������Ϣ
  	hunyin.getHunyin(str, mzText, ysNum, isMan);
  	
  	
  	return str.toString();
  }
  
  /**
   * �õ����˵���Ϣ����ʱ������
   */
  public String getMingYun(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
    //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //���������Ϣ
  	minyun.getLife(str, mzText, ysNum, isMan, y);
  	
  	return str.toString();
  }
  /**
   * �õ�������Ϣ���ɸ�֧����
   * @return
   */
  public String getMingYun(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
  	
    //���ʱ����Ϣ
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //���ʱ��ͷ��
    str.append("\r\n");
    str.append("\r\n");
    
  //����ɾ��ľ�
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //���������Ϣ
  	minyun.getLife(str, mzText, ysNum, isMan);
  	
  	
  	return str.toString();
  }

  /**
   * ��ʱ������
   * @param type ������������ʱ�䣬trueΪ����
   * @param yun  �����»��Ƿ����£�trueΪ����
   * @param isMan �Ƿ���Ů
   * @param iszf ת�����Ż��Ƿ������š�
   * @param iscy �������򷨻��ǲ�����
   * @param ju ֱ�������������ݼ���
   * @return
   */
  public String getQiMenInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan,
                              int iszf, int iscy,  int iZfs,int ju) {
    str.delete(0,str.length());
    daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
    
    //���ʱ����Ϣ
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    getQiMenOut(iszf,iscy, iZfs,ju,str,true);

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * �ɰ�������
   * @return
   */
  public String getQiMenInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, int sheng ,int shi,boolean isMan,
                                int iszf, int iscy, int iZfs,int ju) {
      str.delete(0,str.length());
      daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
      
      //���ʱ����Ϣ
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);
      getQiMenOut(iszf,iscy, iZfs,ju,str,false);

      //Debug.out(str.toString());
      return str.toString();
  }

  /**
   * ���������Ϣ
   */
  private void getQiMenOut(int iszf, int iscy,  int iZfs,int ju,
                           StringBuffer str, boolean b) {
    str.append("\r\n");
    daoq.setJigong(jigong);  //�������ã����ʼ��ֻһ�Σ�һ�������˲�����Ч
    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    if(b) {
      str.append(daoq.getHead1(iszf, iscy, iZfs, ju));
      str.append("\r\n");
      str.append(daoq.getBody1(iszf, iscy, iZfs, ju));
    }else{
      str.append(daoq.getHead2(iszf, iscy, iZfs, ju));
      str.append("\r\n");
      str.append(daoq.getBody1(iszf, iscy, iZfs, ju));
    }

  }
  
  //�������幬�ĺι�
  public void setJigong(int jigong) {
  	this.jigong = jigong;
  }
  public int getJigong() {
  	return this.jigong;
  }

  public static void main(String[] args) {
    DelQiMenMain qm = new DelQiMenMain();
    //qm.getQiMenInfo(1977,3,23,6,34,true,true,-1,-1,true,1,1,0);
    //qm.getQiMenInfo(1934,1,23,6,34,false,true,-1,-1,true,1,1,0);
    //qm.getQiMenInfo(2005,11,2,20,18,false,true,-1,-1,true,1,1,1,0);
    //qm.getQiMenInfo(1933,5,23,12,18,true,true,-1,-1,true,1,1,0);
    //qm.getQiMenInfo(2005,11,17,11,28,false,true,-1,-1,true,1,2,1,0);
    qm.getQiMenInfo(2004,2,1,11,28,false,true,-1,-1,true,1,2,1,0);
    //qm.getQiMenInfo(1,1,3,3,6,6,2,2,true,-1,-1,true,1,2,1,5);
  }
}
