package org.boc.ui;

import javax.swing.*;

import java.awt.*;
import java.util.*;

import org.boc.event.*;
import org.boc.util.*;
import org.boc.model.DataTableModel;

/**
 * ������ÿ��˫�����ж����ں��ּ��������û�л�����һ�����еĻ����updPageInfo����ҳ������
 * ������Main2.jTabbedPane1��������Ƕ����BasicJTabbedPane��壬һ�����һ�����Tab�����š����� 1.
 * BasicJTabbedPane��庬һ��tab����jTabbedPane2
 * �������������(��Ϣ�б�/��ϸ��Ϣ/����/...),��������Сtabҳ,������ѭ�������tabҳ 1.1 JPanel
 * jPanel2:��Ϣ�б�pane���б�ֻnewһ�����иı�ֻ���±���model�� 1.2 BasicJPanel
 * bPane:��ϸҳ��ı�ʱ����̬��ɾ������newһ����SiZhuFrame�������
 * ����屣��ʱ���������ص�do1()�ȷ�����ȡnewʱ��fileId,rowId,parent����Ϊһ���һ���˶��󣬹ʲ���Ҫ������
 * ��������ݸ���ʱ������init()���������parentΪnull����ȡ���ļ�id��rowid�õ������parent�� 1.3
 * ResultPanel:���������Ϣ�������ѭ��new ResultPanel���ı�ʱֻ�����ı���
 */
public class BasicJTabbedPane extends JPanel {
	private BorderLayout borderLayout1;
	private BorderLayout borderLayout3;
	public static final String NUM4=".";

	private JTabbedPane jTabbedPane2; // һ��tab������������Ϣ�б�jPanel2����ϸ��ϢbPane�����̡����˵ȸ���Сpane
	private JPanel jPanel2; // ��Ϣ�б�����
	private JyjJTable myTable; // jPanel2�е��б�
	private BasicJPanel bPane; // ����Ԥ����ϸ���ڵ����,SiZhuFrame��QiMenFrame���̳�����
	private Map<String,ResultPanel> mapRsPane; // ���̼���ʾ�����������

	private MyJtabedChangedLisener myTabedChange;
	private MyJtabedMouseLisener myTabedMouse;

	private String fileId;
	private String rowId;
	private String parentNode;

	public BasicJTabbedPane(String fileId, String rowId, String parentNode) {
		try {
			this.fileId = fileId;
			this.rowId = rowId;
			this.parentNode = parentNode;

			/**
			 * ���ʱ�ɴ����ֵõ��ļ�id
			 */
			jTabbedPane2 = new JTabbedPane();
			jTabbedPane2.setName(fileId);
			borderLayout1 = new BorderLayout();
			borderLayout3 = new BorderLayout();

			jPanel2 = new JPanel();
			myTable = new JyjJTable(fileId);
			// myTable = new QueryTable(null);
			myTabedChange = new MyJtabedChangedLisener();
			myTabedMouse = new MyJtabedMouseLisener();

			bPane = (BasicJPanel) Class.forName((String) Public.mapClass.get(fileId)).newInstance();
			bPane.init(fileId, rowId, parentNode);
			
			// ����ߵ����������һ��map��
			mapRsPane = new HashMap<String,ResultPanel>(); 
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
			Messages.error("BasicJTabbedPane(" + ex + ")");
		}
	}

	/**
	 * ��ʼ����壬������Ԥ�����Ļ���tab��壬��ǶnСtabҳ
	 */
	void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		jPanel2.setLayout(borderLayout3);
		JScrollPane jsp = new JScrollPane(myTable);

		jPanel2.add(jsp, BorderLayout.CENTER);

		this.add(jTabbedPane2, BorderLayout.CENTER);
		jTabbedPane2.addChangeListener(myTabedChange);
		jTabbedPane2.addMouseListener(myTabedMouse);
		jTabbedPane2.add(jPanel2, Public.tabTitle[Public.getValueIndex(fileId)][1]);
		jTabbedPane2.add(bPane, Public.tabTitle[Public.getValueIndex(fileId)][2]);
		// ������ѭ�����ӵ�������ʼ����������1��ʼ��Ӧ����map��
		int valIdx = Public.getValueIndex(fileId);
		for (int i = 3; i < Public.tabTitle[valIdx].length; i++) {
			ResultPanel rsPane = new ResultPanel();
			mapRsPane.put(String.valueOf(i - 2), rsPane);
			jTabbedPane2
					.add(rsPane, Public.tabTitle[Public.getValueIndex(fileId)][i]);
		}
		jTabbedPane2.setSelectedIndex(1);
		bPane.setFather(this); // ���õ�ǰ���Ϊ�ڶ������ĸ����
	}

	public String getFieId() {
		return fileId;
	}
	public String getRowId() {
		return rowId;
	}
	public String getParentNode() {
		return parentNode;
	}

	/**
	 * �õ��ڶ�����弴��ϸ��Ϣ
	 */
	public BasicJPanel getXxxxPanel() {
		return bPane;
	}

	/**
	 * �õ���index�������弴����/��Ϣ/��������Ϣ
	 */
	public ResultPanel getResultPanel(int index) {
		return (ResultPanel) mapRsPane.get(String.valueOf(index));
	}

	/**
	 * ������index����壬���������Ϣ
	 * 
	 * @param str
	 *          String
	 */
	public void updResultPanel(int index, String str) {
		getResultPanel(index).updResult(str);
	}

	/**
	 * ˫���б�ʱ�����µڶ���ҳ�����ϸ��Ϣ
	 */
	public void updTableInfo(Collection<VO> coll) {
		myTable.updateInfo(fileId, coll);
	}

	/**
	 * ˫��������Ҷ���¼�ʱ�����µڶ���ҳ�����ϸ��Ϣ
	 * ԭ����ȥ���ڶ����������ӣ���Ϊֱ�Ӹ��µڶ�����������
	 */
	public void updPageInfo(String fileId, String rowId, String parentNode) {
		this.fileId = fileId;
		this.rowId = rowId;
		this.parentNode = parentNode;
		jTabbedPane2.setSelectedIndex(1);
		BasicJPanel theframe = (BasicJPanel)(jTabbedPane2.getSelectedComponent());
		theframe.init(fileId, rowId, parentNode);
	}

}
