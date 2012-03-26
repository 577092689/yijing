package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.boc.util.Helper;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * һ�����������ı༭����/������ť��panel ��Ƕ�ڷ��ڿ�����
 */
public class TreePanel extends JPanel implements TreeSelectionListener {

	private JButton addB, deleteB;  //, saveB;
	private static JyjJTree tree;
	private static DefaultMutableTreeNode leadSelection;
	private BorderLayout borderLayout;
	public static Map<String,BasicJTabbedPane> mapBaseTabPane;

	public TreePanel() {
		mapBaseTabPane = new HashMap<String,BasicJTabbedPane>();
		Public.setMapFile();
		borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		// setSize(160, 1000);
//		Helper parser = new Helper();
//		tree = new JyjJTree(parser.parse((String) Public.mapFile.get("root")));
		//��һ�ξ���һ�ÿ�����ֻ��<ʼ���ֵ�></ʼ���ֵ�>
		tree = new JyjJTree(new DefaultTreeModel(new DefaultMutableTreeNode(Public.TREEROOT)));
		tree.setExpandsSelectedPaths(true);
		tree.setEditable(true);
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		// p1.setSize(160,1000);
		p1.add(new JScrollPane(tree), BorderLayout.CENTER);
		// ����ѡ��ֵ�ı��¼�
		tree.addTreeSelectionListener(this);
		// ��������¼�������������˫��
		tree.addMouseListener(ml);

		addB = new JButton("����");
		deleteB = new JButton("ɾ��");
		//saveB = new JButton("����");
		Box box = new Box(BoxLayout.X_AXIS);
		box.setSize(160, 100);
		box.add(addB);
		box.add(deleteB);
		//box.add(saveB);

		this.add(p1, BorderLayout.CENTER);
		this.add(box, BorderLayout.SOUTH);

		addB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) leadSelection
						.getParent();
				if (parent == null) {
					JOptionPane.showMessageDialog(TreePanel.this, "���ڵ��²���ֱ�����ӽڵ㣡");
					return;
				}

				String nodeName = JOptionPane.showInputDialog("�½ڵ���:");
				if (nodeName == null || "".equals(nodeName.trim())) {
					JOptionPane.showMessageDialog(TreePanel.this, "���ֲ���Ϊ��!");
					return;
				}
				if (nodeName != null && nodeName.length() > 0) {
					char c = nodeName.charAt(0);
					if (Character.isDigit(c)) {
						JOptionPane.showMessageDialog(TreePanel.this, "�벻Ҫ�����ֿ�ͷ!");
						return;
					}
				}
				if (leadSelection != null) {
					// nodeName += Helper.SIGN+getCurDate();
					leadSelection.add(new DefaultMutableTreeNode(nodeName));
					((DefaultTreeModel) tree.getModel()).reload(leadSelection);
					saveTree();   //Ȼ�󱣴���
				} else {
					JOptionPane.showMessageDialog(TreePanel.this, "û��ѡ�񸸽ڵ�!");
					return;
				}
			}
		});

		deleteB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (leadSelection != null) {
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) leadSelection
							.getParent();
					if (parent == null || isRoot(leadSelection)) {
						JOptionPane.showMessageDialog(TreePanel.this, "���ڵ㲻��ɾ����");
						return;
					} else {
						// ��ʾ��Ҫ����
						if (Messages.question("����������ͬԤ������һ��ɾ����ȷ����") == 1)
							return;
						// ͬʱɾ���ļ��еĶ����Ա���ͬ��
						TreeNode[] paths = leadSelection.getPath();
						String path = "";
						String fileId = null;
						for (int k = 0; k < paths.length; k++) {
							path += paths[k].toString();
						}
						Collection collIds = new ArrayList();
						collIds.add(leadSelection.toString());
						doWhile3(leadSelection, collIds);
						String[] id = (String[]) collIds.toArray(new String[collIds.size()]);
						for (int i = 0; i < Public.keyRoot.length; i++) {
							if (path.indexOf(Public.keyRoot[i]) != -1) {
								fileId = Public.valueRoot[i];
								Public.delObjsFromFile(fileId, id);
							}
						}
						// ˢ���б�,����б�û�д򿪣�����ˢ����
						BasicJTabbedPane panel = (BasicJTabbedPane) TreePanel.mapBaseTabPane
								.get(fileId);
						if (Public.getKeyValue(Public.mapKeyIsOpen, fileId)) {
							Collection coll = Public.getObjectFromFile(fileId);
							panel.updTableInfo(coll);
						}
						// ɾ���б�
						parent.remove(leadSelection);
						leadSelection = parent;
						((DefaultTreeModel) tree.getModel()).reload(parent);						
						saveTree();  //Ȼ�󱣴���
					}
				} else {
					JOptionPane.showMessageDialog(TreePanel.this, "��ѡ��Ҫɾ���Ľڵ㣡");
					return;
				}
			}
		});

//		saveB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				DefaultMutableTreeNode parent = null;
//				if (leadSelection == null) {
//					JOptionPane.showMessageDialog(TreePanel.this, "��ѡ��Ҫ������б�");
//					return;
//				}
//				try {
//					write2XmlAll(leadSelection);
//				} catch (Exception ex) {
//					Messages.error(ex.getMessage());
//				}
//			}
//		});
	}
	/**
	 * �������Ľڵ�
	 */
	public void saveTree() {
		if (leadSelection == null) {
			JOptionPane.showMessageDialog(TreePanel.this, "��ѡ��Ҫ���������ڵ㣡");
			return;
		}
		try {
			write2XmlAll(leadSelection);
		} catch (Exception ex) {
			Messages.error("����������"+ex.getMessage());
		}
	}

	private boolean isRoot(DefaultMutableTreeNode node) {
		for (int i = 0; i < Public.keyRoot.length; i++) {
			if (node.toString().indexOf(Public.keyRoot[i]) != -1) {
				return true;
			}
		}
		return false;
	}

	public static DefaultMutableTreeNode getLeadSelection() {
		return leadSelection;
	}

	public static void setLeadSelection(DefaultMutableTreeNode node) {
		leadSelection = node;
	}

	public static JyjJTree getTree() {
		if (tree == null) {
			TreePanel p = new TreePanel();
			tree = p.tree;
		}
		return tree;
	}

	public String getCurDate() {
		Timestamp columnValue = new Timestamp(System.currentTimeMillis());
		return String.valueOf(columnValue.getTime());
	}

	public void write2XmlAll(DefaultMutableTreeNode node) {
		TreeNode rootNode = node.getRoot();
		Enumeration e = rootNode.children();
		TreeNode theNode = null;
		while (e.hasMoreElements()) {
			theNode = (TreeNode) e.nextElement();
			//System.out.println(theNode.toString());
			for (int i = 0; i < Public.keyRoot.length; i++) {
				if (theNode.toString().trim().indexOf(Public.keyRoot[i]) != -1) {
					write2Xml(theNode, Public.valueRoot[i]);
				}
			}
		}
		// ��Զ���ñ���root write2Xml(rootNode, "root");
	}
	
	/**
	 * ���û������ϵ�node�����浽xml�ļ���ȥ
	 */
	public void write2Xml(TreeNode node, String type) {
		Document doc;
		DocumentBuilderFactory factory;				
		try {
			factory = DocumentBuilderFactory.newInstance();
			doc = factory.newDocumentBuilder().newDocument();
			Element root = doc.createElement(node.toString());
			doWhile(node, root, doc);
			doc.appendChild(root);  //�˾����Ҫ
			outputDoc(doc,type);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messages.error("TreePanel(" + ex + ")");
		}
	}
	// ���Document��xml�ļ�
	public void outputDoc(Document doc, String type) {
		DOMSource doms = new DOMSource(doc);
		File f = new File((String) Public.mapFile.get(type));
		StreamResult sr = new StreamResult(f);
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			java.util.Properties properties = t.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "GBK");
			t.setOutputProperties(properties);
			t.transform(doms, sr);
		} catch (TransformerConfigurationException tce) {
			Messages.error("TreePanel(" + tce.getMessage() + ")");
		} catch (TransformerException te) {
			Messages.error("TreePanel(" + te.getMessage() + ")");
		}
	}
//	public void write2Xml(TreeNode node, String type) {
//		Element root = new Element(node.toString());
//		doWhile(node, root);
//
//		Document doc = new Document(root);
//		XMLOutputter outputter = new XMLOutputter();
//		try {
//			outputter.output(doc, new FileOutputStream((String) Public.mapFile.get(type)));
//		} catch (Exception ex) {
//			Messages.error("TreePanel(" + ex + ")");
//		}
//	}

	/**
	 * ���ýڵ������еĽڵ�����ĵ�
	 */
	private void doWhile(TreeNode node, Element root, Document doc) {
		Enumeration e = node.children();
		TreeNode theNode = null;
		while (e.hasMoreElements()) {
			theNode = (TreeNode) e.nextElement();
			Element element = doc.createElement(theNode.toString());
			root.appendChild(element);
			doWhile(theNode, element, doc);
		}
	}

	/**
	 * ���ýڵ������еĽڵ����Collection
	 */
	private void doWhile3(TreeNode node, Collection coll) {
		Enumeration e = node.children();
		TreeNode theNode = null;
		while (e.hasMoreElements()) {
			theNode = (TreeNode) e.nextElement();
			coll.add(theNode.toString());
			doWhile3(theNode, coll);
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath leadPath = e.getNewLeadSelectionPath();
		if (leadPath != null) {
			leadSelection = (DefaultMutableTreeNode) leadPath.getLastPathComponent();
			// System.out.println(leadSelection.toString());
		}
	}

	MouseListener ml = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			int selRow = tree.getRowForLocation(e.getX(), e.getY());
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			BasicJTabbedPane basePane = null;
			if (selRow != -1) {
				// if (e.getClickCount() == 1) {
				// System.out.println("����"+leadSelection.toString());
				// }else
				/**
				 * ÿ��˫��ʱ���õ��ұߵĴ�tab����(�ڼ�n��panel����n����tabҳ)
				 * ���ĳ��Ԥ����û�򿪣����½�һ��BasicJTabbedPane���ӽ�ȥ
				 */
				if (e.getClickCount() == 2 && leadSelection.isLeaf()) {
					String path1 = selPath.toString();
					String rootKey = null;
					for (int i = 0; i < Public.keyRoot.length; i++) {
						if (path1.indexOf(Public.keyRoot[i]) != -1) {
							rootKey = Public.keyRoot[i];
							JTabbedPane rightJTabbedPane = Main2.getRightTabbedPane();
							if (Public.getKeyValue(Public.mapKeyIsOpen, Public.valueRoot[i]) == false) {
								// �˴���̬ӳ����
								basePane = new BasicJTabbedPane(Public.valueRoot[i],
										leadSelection.toString(), leadSelection.getParent()
												.toString());
								mapBaseTabPane.put(Public.valueRoot[i], basePane);
								rightJTabbedPane.add(basePane, rootKey);
								// ����Ϊ˫��ʱ�Ĵ�������ͼ
								setCurView(rightJTabbedPane, rootKey);
								Public.setKeyValue(Public.mapKeyIsOpen, Public.valueRoot[i],
										true);
								break;
							} else {
								basePane = (BasicJTabbedPane) mapBaseTabPane
										.get(Public.valueRoot[i]);
								basePane.updPageInfo(Public.valueRoot[i], leadSelection
										.toString(), leadSelection.getParent().toString());
								setCurView(rightJTabbedPane, rootKey);
								mapBaseTabPane.put(Public.valueRoot[i], basePane);
							}
						}
					}
				}
			}
		}
	};

	public static void setCurView(JTabbedPane rightJTabbedPane, String rootKey) {
		int count = rightJTabbedPane.getTabCount();
		int index = 0;
		for (; index < count; index++) {
			if (rightJTabbedPane.getTitleAt(index).equals(rootKey)) {
				rightJTabbedPane.setSelectedIndex(index);
				break;
			}
		}

		return;
	}

	/**
	 * �õ�һ����Ҷ�ĸ�Ŀ¼��
	 */
	private static TreeNode _nd = null;

	public static String getParent(String leaf) {
		// ���ɾ���˽ڵ㣬��ʱleadSelectionΪnull�����丸��ȱʡ��Ԥ����֮��Ŀ¼
		if (leadSelection == null)
			return null;
		TreeNode root = (TreeNode) leadSelection.getRoot();

		doWhile2(root, leaf);
		if (_nd == null)
			return leaf;
		if (!_nd.isLeaf())
			return leaf;
		return _nd.getParent().toString();
	}

	private static void doWhile2(TreeNode node, String leaf) {
		Enumeration e = node.children();
		TreeNode theNode = null;
		while (e.hasMoreElements()) {
			theNode = (TreeNode) e.nextElement();
			if (!theNode.isLeaf())
				doWhile2(theNode, leaf);
			if (leaf.equals(theNode.toString())) {
				_nd = theNode;
				return;
			}
		}
	}

	public static void main(String[] args) {
		// TreePanel treePanel = new TreePanel();
		// treePanel.setVisible(true);
		System.out.println(new TreePanel().getCurDate());
	}
}
