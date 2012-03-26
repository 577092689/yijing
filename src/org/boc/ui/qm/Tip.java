package org.boc.ui.qm;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;
import org.boc.db.qm.QimenGeju1;
import org.boc.db.qm.QimenPublic;
import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.PrintWriter;

public class Tip {
	private MyTextPane textPane;
	private StyledDocument styledDoc; // �ı�����ĵ�
	private QiMenFrame frame;
	private QimenPublic pub;
	private DaoQiMen daoqm;
	private final String sReturn = System.getProperty("line.separator"); // ���з�
	private HtmlMultiLineControl html;

	public Tip(MyTextPane textPane, BasicJPanel frame) {
		this.textPane = textPane;
		this.frame = (QiMenFrame) frame;
		html = new HtmlMultiLineControl();
	}

	/**
	 * �õ���ʾ��Ϣ������ָ�ŵ�һ���ַ���һ��ȡ4���ַ�����Ϊ��Ĳ�4���ְ�
	 * 
	 * @param e
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String getToolTip(MouseEvent e) {
		if (pub == null) {
			pub = this.frame.getDelQiMenMain().getQimenPublic();
			daoqm = pub.getDaoQiMen();
		}

		Point p = e.getPoint();
		int pos = textPane.viewToModel(p);
		String rs = null;
		if (styledDoc == null)
			styledDoc = (StyledDocument) textPane.getDocument();
		((AbstractDocument) styledDoc).readLock();
		AttributeSet as = null;
		try {
			as = styledDoc.getCharacterElement(pos).getAttributes();
		} finally {
			((AbstractDocument) styledDoc).readUnlock();
		}

		Enumeration en = as.getAttributeNames();
		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (key.toString().equals("foreground"))
				if (as.getAttribute(key).equals(Color.WHITE))
					return null;
				else
					break;
		}

		try {
			rs = getTipinfo(pos, as);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
//		if(rs!=null && rs.trim().equals("null")) rs = null;
//		System.out.println(rs);
		return rs;
	}

	/**
	 * �㷨��Ҫ�Զ��ϴʾ� 305-502 710-905 1114-1310֮�������ֻȡһ���־���
	 * �����ױ�ͷ�������й�ϵ�����Ա�ͷ����Ҫ�̶�
	 */
	private String geyKey(int pos, AttributeSet as) throws BadLocationException {
		if (pos < 10)
			return null; // С��4����ȡ���ᱨ��
		//System.out.println(pos);
		
		//����Ǵ����壬ֻ���ص�ǰ��һ���ַ�
		Enumeration en = as.getAttributeNames();	
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					return styledDoc.getText(pos, 1);
				}
				break;
			}
		}
		//���������ˣ���ʱֻȡһ���ַ�
		if(QiMen2.XMHW==100) {
			return styledDoc.getText(pos, 1);
		}
		if ((pos > 305 && pos < 502) || (pos > 710 && pos < 905) || (pos > 1114 && pos < 1310)) {
			return styledDoc.getText(pos, 1);
		}

		StringBuilder sb = new StringBuilder();
		String[] splits = { "��", "��", "��", QimenGeju1.FILL1, QimenGeju1.FILL2 };
		for (int i = 0; i < 4; i++) {// ��ǰȡ4���ַ�
			String tmp = null;
			try {
				tmp = styledDoc.getText(pos + i, 1);
			} catch (Exception e) {
				return null;
			}
			boolean isSame = false;
			for (String split : splits) {
				if (tmp.equals(split)) {
					isSame = true;
					break;
				}
			}
			if (!isSame)
				sb.append(tmp);
			else
				break;
		}

		for (int i = 1; i <= 4; i++) {// ���ȡ4���ַ�
			String tmp = styledDoc.getText(pos - i, 1);
			boolean isSame = false;
			for (String split : splits) {
				if (tmp.equals(split)) {
					isSame = true;
					break;
				}
			}
			if (!isSame)
				sb.insert(0, tmp);
			else
				break;
		}

		return sb.toString();
	}
	
	/**
	 * ���еĴ�����ֱ�������ġ� 9+8+8+9=34
	 */
	public static final String[] BIGWORD = {"��","��","��","��","��","��","��","��",
			"��", "��", "��", "��", "��", "��", "��", "��", "Ӣ",
			"��", "��", "��", "��","��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��"};
	/**
	 * ����ˮ��ɫ�����Ӧ��pos��ֵ���ֱ����ڵڼ���
	 */
	public static final int[] REDWORD = {0,979,184,557,152,0,995,589,963,168};
	@SuppressWarnings("unchecked")
	/**
	 * ��ʾ���Ⱥ�˳��Ϊ��
	 * 1. ��ʾ��ֵ�ʱ��ͷ����Ϣ
	 * 2. ���ȿ��������壬���Ӧ����ʾ��Ϣ
	 * 3. �ж��Ƿ���ˮ��ɫ���壬��ʾ�˹�����ϸ��Ϣ
	 * 4. �ж��Ƿ��Ǽ��׸�ʮ�ɿ�Ӧ
	 * 5. �ж��Ƿ������ǽ�������
	 * 6. �ж��Ƿ���ʮ����
	 * 7. ���֧������
	 * 8. �̳��Ĺ��������R�i
	 */
	private String getTipinfo(int pos, AttributeSet as)
			throws BadLocationException {
		//1. ����Ǳ�ͷ���֣�����ʾ��ϸ�ľ���Ϣ
		//System.out.println(pos);
		if(pos>=3 && pos<=6) {
			String t = null;
			try{t = styledDoc.getText(3, 3);}catch(Exception e) {
				e.printStackTrace();
			}
			//if(t!=null && t.indexOf("")!=-1)
				return html.CovertDestionString(getDateHead());  //��ʱ������֣�Ȼ�������ͷ��
		}
			
		String key = geyKey(pos, as);
		if (key == null || key.trim().length() == 0)
			return null;
		
		//System.out.println("-- pos="+pos+";key="+key);

		// 2. �������壬�ж��Ƿ������ҵ�Ǭ���ż��š��ż����̸�/���̸ɡ��Ǽ��š������
		Enumeration en = as.getAttributeNames();
		boolean isBigFont = false;		
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					isBigFont = true;
				}
				break;
			}
		}
		if (isBigFont) {  //ֻ�д�������ǵģ���ֻ�а��š����ǡ�����ʮ��ɲ��Ǵ�����
			//System.out.println(pos+";key="+key);
			for (int i = 0; i <BIGWORD.length; i++) {
				if (BIGWORD[i].equals(key)){
					//System.out.println("isbigfont pos="+pos+";key="+key);
					return html.CovertDestionString(getDesc(pos, as, key));
				}										
			}
		}
		
		//3. �ж��Ƿ���ˮ��ɫ���壬��ʾ�˹�����ϸ��Ϣ
		int gong = 0; //��ǰ�ڼ���
//		boolean isRed = false;
//		en = as.getAttributeNames();
//		while (en.hasMoreElements()) {
//			Object k1 = en.nextElement();
//			if (k1.toString().equals("foreground")) {
//				if (as.getAttribute(k1) == Color.MAGENTA) {					
//					isRed = true;
//				}
//				break;
//			}
//		}
//		if(isRed) {
			for(int i = 1; i<REDWORD.length; i++) {
				if(REDWORD[i]==pos) {
					gong = i; 
					return html.CovertDestionString(QiMen.JIUGONGINFO[gong][0]+":"+QiMen.JIUGONGINFO[gong][1]);
				}
			}
//		}
		
		
		// 4. �ж��Ƿ��Ǽ��׸�ʮ�ɿ�Ӧ����Щ�ؼ��ֳ��ȶ�����1
		String name;
		if(key.length()>1) {
			for (int i = 0; i < QiMen.gGejuDesc.length; i++) {
				if (i >= 140 && i <= 417)
					continue; // ���ڵ�һ���ֵ����ݣ���������������
				if (QiMen.gGejuDesc[i] == null || QiMen.gGejuDesc[i][0] == null)
					continue;
				name = QiMen.gGejuDesc[i].length == 3 ? QiMen.gGejuDesc[i][2]
						: QiMen.gGejuDesc[i][0];
				if (name.equals(key))
					return html.CovertDestionString(QiMen.gGejuDesc[i][0] + ": " + QiMen.gGejuDesc[i][1]);
			}
		}
		
		//5. �Ƿ������ǽ��ǡ��ذ��񡢵ذ����������š�����12;  ��һ��448-504; �ڶ���854-910; ������1259-1315;
		String t = getLine7Desc(pos);
		if(t!=null) {
			return html.CovertDestionString(t);
		}
		
		//System.out.println(key);		
		//6. ʮ���� 
		for(int i=1; i<=12; i++) {			
			if(QiMen2.SHENJ[i][2].equals(key)) {
				return html.CovertDestionString(QiMen2.SHENJ[i][0] + ": " + QiMen2.SHENJ[i][1]);
			}
		}
		
		//7. ���֧������
		for(int i=1; i<YiJing.DIZINAME.length; i++) {
			if(key.equals(YiJing.DIZINAME[i])) {
				//i = i==2?4 : (i==4? 2 : i);  
				int hg = pub.getHuogan(i);  //i����֧�������ɵ�֧�õ����
				
				String rs = null;
				if(hg==YiJing.YI) {//�����ң��Ҽ������˴��õ��Ļ�ɣ������ٻ��ˡ�
					//hg = YiJing.DING;
					rs="����Ϊ�������Ϊ�ң�<BR>";
				} else if(hg==YiJing.DING) {
					//hg = YiJing.YI;
					rs="����Ϊ�ң����Ϊ����<BR>";
				}else rs = "";
				rs += YiJing.TIANGANNAME[hg]+YiJing.DIZINAME[i]+
				":"+SiZhu.NAYIN[hg][i]+"";
				return html.CovertDestionString(rs);
			}
		}
		
		//8. �Ƿ������Ⱥ���Ĺ���̳��Ĺ���ҡ�����R�i
		for (int i = 0; i < QiMen.gGejuDesc.length; i++) {
			if ((i >= 450 && i <= 466) ||
					(i >= 36 && i <= 49)) {
				if (QiMen.gGejuDesc[i] == null || QiMen.gGejuDesc[i][0] == null)
					continue;
				name = QiMen.gGejuDesc[i].length == 3 ? QiMen.gGejuDesc[i][2]
						: QiMen.gGejuDesc[i][0];
				if (name.equals(key))
					return html.CovertDestionString(QiMen.gGejuDesc[i][0] + ": " + QiMen.gGejuDesc[i][1]);
			}
		}
		return null;
	}
	
	//�����ϸ��ʱ��ͷ����Ϣ
	private String getDateHead() {
    //���ʱ����Ϣ
		if(frame.getYear()==0) {//Ϊ��֧��ʽ
			return daoqm.getHead2(frame.getZhuanFei(), frame.getCaiYun(), 
	    		frame.getXiaozhifu(), frame.getJu());
		}
		daoqm.getDaoCalendar().calculate(frame.getYear(), 
				frame.getMonth(), frame.getDay(), frame.getHour(), frame.getMinute(), 
				frame.isYinli(), frame.isYun(), frame.getProvince(), frame.getCity());
    SiZhu.ISMAN = frame.isBoy();
    return daoqm.getHead1(frame.getZhuanFei(), frame.getCaiYun(), 
    		frame.getXiaozhifu(), frame.getJu());
	}

	/**
	 * ����gong���ǣ��ţ����̸ɣ����̸���š�����
	 * 
	 * @param pos
	 * @param as
	 * @param s
	 * @throws BadLocationException
	 */
	private String getDesc(int pos, AttributeSet as, String s)
			throws BadLocationException {
		int xmg = 0; // �ǣ��ţ����̸ɣ����̸ɣ��������
		int gong = 0; // ��һ��
		int type = 0; // �������ͣ�1�� 2�� 3���̸� 4���̸�5��
		String rs = null;

		// 1. �Ƿ������̸ɻ���̸�
		for (int i = 1; i <= 10; i++) {
			if (s.equals(YiJing.TIANGANNAME[i])) {
				xmg = i;
				boolean isMen = false;
				String men = null;
				//1. ��ȡ�ڶ�λ���һλ����ɣ����̸ɶ�����������ȡ��3λ���������������̸ɣ�Ҫ���Ƿ���˻�֧									
				int loc1 = QiMen2.HUO || QiMen2.ALL ? 2 : 1;
				int loc2 = QiMen2.HUO || QiMen2.ALL ? 5 : 4;
				boolean isgan = false;
				String gan = styledDoc.getText(pos - loc1, 1);
				for (int j = 1; j <= 10; j++) {
					if (gan.equals(YiJing.TIANGANNAME[j])) {
						isgan = true;
						break;
					}
				}
				if(isgan) {
					men = styledDoc.getText(pos - loc2, 1);
					for (int j = 0; j < QiMen.bm1.length; j++) {
						if (men.equals(QiMen.bm1[j])) {
							isMen = true;
							break;
						}
					}
				}
				
				//1.1 ��ȡ��3λ���������������̸�,�����ж��乬�á���������λ����ɣ��Ͳ����ж�������λ�Ƿ�������
				if (!isgan && !isMen) {	
					men = styledDoc.getText(pos - 3, 1);
					// System.out.println("men="+men);
					for (int j = 0; j < QiMen.bm1.length; j++) {
						if (men.equals(QiMen.bm1[j])) {						
							isMen = true ;
							break;
						}
					}
				}
				
				//1/2/3��ֵʱ��52/62��ϣ�����+���̸ɣ��������ŵ�Ϊ054��ֵҲ�����̸�
				type = ((isMen && QiMen2.XMHW>0 && QiMen2.XMHW<4) ||
								(!isMen && (QiMen2.XMHW<1 || QiMen2.XMHW>3))) ? 3 : 4;
				//System.out.println(s+"; isMen="+isMen+"\tQiMen2.XMHW="+QiMen2.XMHW+"; type="+type);
				break;
			}
		}
		// 2.�Ƿ��Ǿ���
		if (type == 0) {
			// �Ƚ������Ʒ�����ǵ����
			for (int i = 0; i < QiMen.jx1.length; i++) {
				if (s.equals(QiMen.jx1[i])) {
					xmg = i;
					type = 1;
					break;
				}
			}
		}
		// 3. �Ƿ��ǰ���
		if (type == 0) {
			for (int i = 0; i < QiMen.bm1.length; i++) {
				if (s.equals(QiMen.bm1[i])) {
					xmg = i;
					type = 2;
					break;
				}
			}
		}
		//4. �Ƿ��ǰ���
		if (type == 0) {
			// �Ƚ������Ʒ����������
			for (int i = 0; i < QiMen.bs1.length; i++) {
				if (s.equals(QiMen.bs1[i])) {
					xmg = i;
					type = 5;
					break;
				}
			}
		}
		if (type == 0)
			return null;		

		// 4. �õ���
		if (type == 1)
			gong = pub.getXingGong(xmg);
		else if (type == 2)
			gong = pub.getMenGong(xmg);
		else if (type == 3)
			gong = pub.getTianGong(xmg, 0);
		else if (type == 4)
			gong = pub.getDiGong(xmg, 0);
		else if (type == 5)
			gong = pub.getShenGong(xmg);
		
		//System.out.println("text="+s+";xmg="+xmg+";gong="+gong+";type="+type);
		
		if (gong == 0)
			return null;

		// 5. ��ָ��������ʱ��������+������
		//System.out.println(pos+";key="+s+";type="+type+";gong="+gong);
		int ge, ge1;
		if (type == 1 && gong != 0) {
			ge = QiMen.xing_men[xmg][QiMen.dp_menxing_mc[daoqm.gInt[3][1][gong]]];
			if(ge!=0) {
				rs = QiMen.gGejuDesc[ge][0] + ":" + QiMen.gGejuDesc[ge][1];
				rs += "\r\n";
			}else 
				rs = "";
			rs += "��"+QiMen.jx1[xmg]+"ֵ"+YiJing.DIZINAME[SiZhu.sz]+"ʱ��"+QiMen2.JXZS[xmg][SiZhu.sz];
			return html.CovertDestionString(rs);
		}
		// 6. ��ָ��������ʱ�� ������+������
		if (type == 2 && gong != 0) {
			ge = QiMen.men_men[xmg][QiMen.dp_mengong_mc[gong]]; // daoqm.gInt[4][4][gong]
			if(ge==0) return null;
			rs = QiMen.gGejuDesc[ge][0] + ":" + QiMen.gGejuDesc[ge][1];
			return html.CovertDestionString(rs);
		}
		// 7. ��ָ����/���̸�ʱ�� ������+���̸�/���̸� �� ��������+��
		if ((type == 3 || type == 4) && gong != 0) {
			ge = QiMen.men_gan[daoqm.gInt[3][1][gong]][xmg];			
			ge1 = QiMen.gan_gong[xmg][gong];
			if(ge==0) return null;
			rs = QiMen.gGejuDesc[ge][0]+ ":" + QiMen.gGejuDesc[ge][1]
					+ (ge1 == 0 ? "" : "<BR>"+QiMen.gGejuDesc[ge1][0] + ":" + QiMen.gGejuDesc[ge1][1]);
			
			return html.CovertDestionString(rs);
		}
		// 8. ��ָ�����ʱ�� ����+������ ��
		if (type == 5 && gong != 0) {
			ge = QiMen.shen_men[xmg][QiMen.dp_menxing_mc[daoqm.gInt[3][1][gong]]]; 
			if(ge==0) return null;
			rs = QiMen.gGejuDesc[ge][0] + ":" + QiMen.gGejuDesc[ge][1];
			return html.CovertDestionString(rs);
		}
		
		return rs;
	}
	
	private int[] tuixing = {0,1280,487,857,451,0,1298,893,1262,469};  //����,һ����������...��
	private final int[] steps = {0,1,3,5,6,7,9,10,12};  //��֧�����������ó�����,���������жϣ���270��
//	private final int jxstep = 1;  		//���ǣ����������1��
//	private final int dpbsstep = 3; 	//���̰��������Ǿ�3
//	private final int dpbmstep = 5;		//���̰���
//	private final int tmstep = 6; 		//���ţ���ԭ��
//	private final int ymstep = 7; 		//����
//	private final int agzstep = 9;		//���ϰ���
//	private final int agystep = 10;		//�۷�����
//	private final int aghstep = 11;		//�������ţ����֧
//  private final int agwystep = 12;		//�������Ű���		
	public String getLine7Desc(int pos) {
//		��452/453,455,457/458/459,461/462  || ��470,��488
//		��858,��894,��1263��һ1281����1299/ ��886��		
		int gong = 0;  //�õ���ǰָ����һ��
		int type = -1;  //�õ���ǰҪ���ʵ�����0���ǣ�1���ǣ�3������5�����ţ�6���ţ�7���ţ�9���ϰ��ɣ�10�۷�����
		for(int i=1; i<tuixing.length; i++) {
			if(i==5) continue;  //���幬���˴����ܰ��Ӷ����жϣ�����
			for(int j=0; j<steps.length; j++)
			if(pos==tuixing[i]+steps[j]) {
				gong = i;
				type = steps[j];
				break;
			}
		}
		//System.out.println("pos="+pos+";gong="+gong+";type="+type);
		if(pos==885) {type=10;gong=5;}  //���幬�۷�����
		if(gong==0 || type==-1) return null;
		String rs = null;
		int x,xg,ge;
		switch(type){
		case 0: //����
			x = pub.getTuiXing(gong);
			xg = pub.getXingGong(x);
			rs = "���ǣ�"+QiMen.jx1[x]+"������"+xg+"��������ȥ֮�£�";
			break;
		case 1: //����
			x = pub.getJinXing(gong);
			xg = pub.getXingGong(x);
			rs = "���ǣ�"+QiMen.jx1[x]+"������"+xg+"������δ��֮�£�";
			break;
		case 3: //������
			x = daoqm.gInt[1][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			xg = pub.getShenGong(x);
			rs = "�������"+QiMen.bs1[x]+"������"+xg+"��������ȥ֮�£�";
			break;
		case 5: //������
			x = daoqm.gInt[3][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			xg = pub.getMenGong(x);
			rs = "�����ţ�"+QiMen.bm1[x]+"������"+xg+"��������ȥ֮�£�";
			break;
		case 6: //����
			x = gong;
			xg = pub.getMenGong(x);
			rs = "���ţ�"+QiMen.bm1[x]+"������"+xg+"��������ȥ֮�£�";
			break;
		case 7: //����
			x = pub.getYinmen(gong);
			xg = pub.getMenGong(x);
			rs = "���ţ�"+QiMen.bm1[x]+"������"+xg+"������δ��֮�£�";
			break;
		case 9: //���ϰ���
			x = daoqm.gInt[4][5][daoqm.gInt[3][1][gong]];
			ge = QiMen.gan_gan[x][daoqm.gInt[4][5][gong]];
			xg = pub.getTianGong(x, 0);
			rs = "���ϰ��ɣ�"+YiJing.TIANGANNAME[x]+"����"+xg+"����<BR>"+
				QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1];
			break;
		case 10: //Ҫ������
			x = pub.getAngan(gong);
			//int jg = gong==5?daoqm.getTpJigong() : gong;  //���幬����������֮��
			int jg = gong;  //���幬Ҳ�����幬�ĵ��и�
			//System.out.println("jg="+jg+";gong="+gong);
			ge = QiMen.gan_gan[x][daoqm.gInt[4][5][jg]];
			xg = pub.getTianGong(x, 0);
			rs = "�۷����ɣ�"+YiJing.TIANGANNAME[x]+"����"+xg+"����<BR>"+
			QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1];
			break;
//		case 11: //��֧
//			x = pub.getAngan(gong);
//			x = x==2?4 : (x==4? 2 : x);  //�����ң��Ҽ���
//			xg = pub.getHuozi(x);
//			rs = YiJing.TIANGANNAME[x]+YiJing.DIZINAME[xg];
//			break;
		case 12: //���̰���
			x = pub.getWangAngan(gong);
			ge = QiMen.gan_gan[x][daoqm.gInt[4][5][gong]];
			xg = pub.getTianGong(x, 0);
			rs = "���̰��ɣ�"+YiJing.TIANGANNAME[x]+"����"+xg+"����<BR>"+
			QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1];
			break;
		default:
			break;
		}
		return rs;
	}
}
