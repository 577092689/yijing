package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.boc.ui.qm.AboutFrame;
import org.boc.util.Exec;
import org.boc.util.Helper;
import org.boc.util.Messages;
import org.boc.util.Public;

public class Main1
    extends JFrame {
  private static final int WIDTH = 650;
  private static final int HIGHT = 650;
  private static final int TOOLBARW = 18;
  private static final int TOOLBARH = 24;
  private static final int STATUSTIPW = 800;
  private static final int STATUSTIPH = 14;
  private static final int STATUSTIMEW = 100;
  private static final int STATUSTIMEH = 14;
  private static final String SYS_SIZHU = "����"; //������е�����TAPҳ
  public static final boolean zc = true; //falseΪ��

  private Collection popups;  //��ǰ�Ĵ���
  private JToolBar jToolBar; //������
  private JMenu fileMenu; //һ���˵��ļ�
  private JMenu jMenuSansi; //��ʽ
  private JMenu jMenuShenshu; //�������
  private JMenu jMenuSiliu; //������س
  private JMenu jMenuFengshui; //��ˮ
  private JMenu jMenuQita; //����
  private JMenu jMenuPreference; //һ���˵�����
  private JMenu jMenuOther; //һ���˵�����

  private CommandAction cmdOpen; //�����˵�����
  private CommandAction cmdClose; //�����˵��ָ�
  private CommandAction cmdQuit; //�����˵��˳�

  private CommandAction cmdYijing; //�����˵��׾�
  private CommandAction cmdSizhu; //�����˵�����
  private CommandAction cmdFengshuiBz; //�����˵���լ�ɷ�ˮ
  private CommandAction cmdFengshuiXk; //�����˵������ɷ�ˮ
  private CommandAction cmdFengshuiSh; //�����˵������ɷ�ˮ
  private CommandAction cmdFengshuiSy; //�����˵������ɷ�ˮ
  private CommandAction cmdQimen; //�����˵����Ŷݼ�
  private CommandAction cmdLiuren; //�����˵�������
  private CommandAction cmdTaiyi; //�����˵�̫������
  private CommandAction cmdZiwei; //�����˵���΢����
  private CommandAction cmdTieban; //�����˵���������
  private CommandAction cmdNanji; //�����˵��ϼ�����
  private CommandAction cmdBeiji; //�����˵���������
  private CommandAction cmdMeihua; //�����˵���������
  private CommandAction cmdZhuge; //�����˵��������
  private CommandAction cmdXingming; //�����˵�����Ԥ��
  private CommandAction cmdCezi; //�����˵�����
  private CommandAction cmdChouqian; //�����˵���ǩ
  private CommandAction cmdMianxiang; //�����˵�����
  private CommandAction cmdShouxiang; //�����˵�����
  private CommandAction cmdGuxiang; //�����˵�����
  private CommandAction cmdTuibei; //�����˵��Ʊ�ͼ
  private CommandAction cmdShudou; //�����˵�����
  private CommandAction cmdJiemeng; //�����˵��ܹ�����
  private CommandAction cmdZhengzhao; //�����˵�����
  private CommandAction cmdFofa; //�����˵�������
  private CommandAction cmdXuexing; //�����˵�Ѫ��
  private CommandAction cmdXingzuo; //�����˵�����
  private CommandAction cmdShuzi; //�����˵�����
  private CommandAction cmdChenggu; //�����˵��ƹ�

  private CommandAction cmdHelp; //�����˵�����
  private CommandAction cmdAbout; //�����˵�����

  private JMenuBar jMenuBar; //�˵���
  private JPanel jContentPane; //���������
  private javax.swing.Timer timer;
  private JPanel jPanelStatus; //״̬��
  private JLabel jLabelStatusTime; //״̬����ʱ���ǩ
  private JPanel jPanelStatusTime; //״̬����ʱ������
  private JPanel jPanelStatusTip; //״̬����ʾ����
  private JLabel jLabelStatusTip; //״̬����ʾ��ǩ
  private JLayeredPane jPanelMain; //�����
  private JPanel siZhuPanel; //�������
  private CardLayout layout;

  private void initialize() {
    popups = new ArrayList();
    //getSystemSetting().loadSetting();
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //����ȱʡ�رհ�ť
    setSize();
    //0. ����������
    this.setTitle("�й��Ŵ�Ԥ��ѧ");
    //1. ����ĸ���
    this.setContentPane(getJContentPane());
    //2. ���ò˵���,����Ҫ���������
    this.setJMenuBar(getJMenuBars());
    //3. ���ù���������������
    jContentPane.add(getJToolBar(), BorderLayout.NORTH);
    //4. �����м�������
    jContentPane.add(getJPanelMain(), BorderLayout.CENTER);
    //5. �����������״̬����壬������Ϣ��ʾ��ʱ����ʾ
    jContentPane.add(getJPanelStatus(), BorderLayout.SOUTH);

    refreshStatus();
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if(!zc)
//        JOptionPane.showMessageDialog(getThis(),
//                                        Public.info,Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);
//        systemExit();
        	AboutFrame.show(0);
      }
    });

    timer = new javax.swing.Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doTimer();
      }
    });
    timer.start();
  }

  private class ChmThread extends Thread {
    private final String cmd ;
    ChmThread(String cmd) {
      this.cmd = cmd;
      start();
    }

    public void run() {
      try {
        sleep(100);
      }
      catch (InterruptedException ex) {
      }
      Exec.exec(cmd);
    }
  }
  public void openHelpIE() {
    try {
      new ChmThread("C:/Program Files/Internet Explorer/IEXPLORE.EXE blog.sina.com.cn/u/2479027277");
    } catch(Throwable t) {  }
  }

  public void openHelpChm() {
    String filename = Public.helpdoc;    
    String hhExe = null;
    String osName = null;
    try{
      osName = System.getProperty("os.name");
      if (osName.equals("Windows NT")) {
        hhExe = "C:/WINNT/system32/cmd.exe /c ";
      }else if(osName.equals("Windows 2000")) {
        hhExe = "C:/WINNT/system32/cmd.exe /c ";
      }else{
        hhExe = "C:/WINDOWS/system32/cmd.exe /c ";
      }
    }catch(Exception e) {
      Messages.error("Main1:openHelpChm() 189�У��򿪰����ļ���ִ������hh.exe��"+osName+"��û���ҵ�");
    }

    try {
      //filename = Public.HOME+"help/"+Properties.helpdoc;
      //filename = getClass().getResource("/help/"+Properties.helpdoc).getPath();
      //filename = filename.substring(1);
      String cmd = hhExe+filename;
      //Messages.info(cmd);
      //Exec.exec(cmd);
      new ChmThread(cmd);
    } catch(Throwable t) {
      try{
        Exec.exec("C:/windows/hh.exe "+filename);
      }catch(Throwable t1) {
        Messages.error("�򿪰����ļ�ʱִ������[" + filename + "]ʧ�ܣ�" + t1);
      }
    }
  }

  public static FileManagerFrame fManager;
  public void openFileManager() {
    if(fManager==null)
      fManager = new FileManagerFrame();
    fManager.setVisible(true);
  }

  	private static Helper parser ;
	private static JyjJTree tree ;
	private static DefaultMutableTreeNode rootNode;
	private static DefaultTreeModel model;
	public JyjJTree getMyTree() {
		return tree;
	}
	/**
	 * �·������ã�10000�β�����
	 */
	public void OpenIntoTree(String name) {
		if(parser==null) parser = new Helper();
		if(tree==null) tree = TreePanel.getTree();  //�õ����������һ��ֻ�ǿյģ����ڻ��qm.xml��ty.xml�м���
		DefaultMutableTreeNode leadSelection = TreePanel.getLeadSelection();
		if (leadSelection == null) {//���û��ѡ����ѡ����ڵ�<ʼ���ֵ�></ʼ���ֵ�>
			leadSelection = (DefaultMutableTreeNode) tree.getModel().getRoot();
			TreePanel.setLeadSelection(leadSelection);
		}
		if (rootNode == null) //��<ʼ���ֵ�></ʼ���ֵ�>��ֵ��rootNode
			rootNode = (DefaultMutableTreeNode) leadSelection.getRoot();
		//��name=qm)���õ�qm.xml�ļ�����һ�β����ڣ�ֱ��newһ��"���š��ڵ㷵����
		model = parser.parse((String) Public.mapFile.get(name));  

		//Ԥ�⼼���Ƿ��Ѿ�װ�ص����������û��װ��
		if (!((Boolean) Public.mapKeyIsLoaded.get(name)).booleanValue()) {
			//������ģ������ӵ�ʼ���ֵ���ڵ���
			rootNode.add((DefaultMutableTreeNode) model.getRoot());
			tree.expandRow(0);
			//tree.expandPath(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot()));
			((DefaultTreeModel) tree.getModel()).reload(rootNode);
			//����qm״̬Ϊ�Ѽ���
			Public.mapKeyIsLoaded.remove(name);
			Public.mapKeyIsLoaded.put(name, new Boolean(true));
			//System.out.println("add " + name);
		} else {  //����Ѿ�װ��
			Enumeration e = rootNode.children();
			DefaultMutableTreeNode theNode = null;
			while (e.hasMoreElements()) {
				theNode = (DefaultMutableTreeNode) e.nextElement();
				if (theNode.toString().indexOf(Public.getRootKey(name)) != -1) {
					rootNode.remove(theNode);
					((DefaultTreeModel) tree.getModel()).reload(rootNode);
					Public.mapKeyIsLoaded.remove(name);
					Public.mapKeyIsLoaded.put(name, new Boolean(false));
					//System.out.println("remove " + name);
					break;
				}
			}
		}
	}

  //ԭ����ע����
  public void OpenIntoTree2(String name) {
    Helper parser = new Helper();
    JyjJTree tree = TreePanel.getTree();
    //��ֱ��new һ��rootNdoe
    DefaultMutableTreeNode leadSelection = TreePanel.getLeadSelection();
    if(leadSelection==null){
      //JOptionPane.showMessageDialog(tree, "��ѡ���б�ĸ��ڵ㣡");
      //return;
      leadSelection = (DefaultMutableTreeNode)tree.getModel().getRoot();
      TreePanel.setLeadSelection(leadSelection);
    }
    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)leadSelection.getRoot();
    DefaultTreeModel model = parser.parse( (String) Public.mapFile.get(name));

    if (!((Boolean)Public.mapKeyIsLoaded.get(name)).booleanValue()) {
      rootNode.add( (DefaultMutableTreeNode) model.getRoot());
      ( (DefaultTreeModel) tree.getModel()).reload(rootNode);
      Public.mapKeyIsLoaded.put(name,new Boolean(true));
    }
    else {
      Enumeration e = rootNode.children();
      DefaultMutableTreeNode theNode = null;
      while(e.hasMoreElements()) {
        theNode = (DefaultMutableTreeNode)e.nextElement();
        if(theNode.toString().indexOf(Public.getRootKey(name))!=-1){
          rootNode.remove(theNode);
          ( (DefaultTreeModel) tree.getModel()).reload(rootNode);
          Public.mapKeyIsLoaded.put(name,new Boolean(false));
          break;
        }
      }
    }
  }

  public JInternalFrame getCurrentFrame() {
    for (Iterator it = popups.iterator(); it.hasNext(); ) {
      JInternalFrame currentFrame = (JInternalFrame) it.next();
      if (currentFrame.isSelected()) {
        return currentFrame;
      }
    }
    return null;
  }

  private void setSize() {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(WIDTH, HIGHT);
    int width = this.getWidth();
    int height = this.getHeight();

    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;
    this.setBounds(x, y, width, height);

  }

  public void refreshStatus() {
    this.getBackupAction().setEnabled(true);
    this.getRecoveryAction().setEnabled(true);
    this.setStatusText("�ɹ�������");
  }

  public void systemExit() {
  	if(Main.fout!=null)
  		Main.fout.close();
  	if(Main.ferr!=null)
  		Main.ferr.close();
  	
    //if(!Properties.zc)
//        JOptionPane.showMessageDialog(getThis(),
//                                       Public.info,Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);
  	AboutFrame.show(0);
    //System.exit(0);
  }

  public void doTimer() {
    java.util.Date now = new java.util.Date();
    int h = now.getHours();
    int mi = now.getMinutes();
    int ss = now.getSeconds();
    String d = " " +
        (now.getHours() < 10 ? "0" + h : "" + now.getHours());
    d += ":" +
        (now.getMinutes() < 10 ? "0" + mi : "" + now.getMinutes());
    d += ":" +
        (now.getSeconds() < 10 ? "0" + ss : "" + now.getSeconds());
    jLabelStatusTime.setText(d);

    if(mi%3==0 && ss==30)
      if(!zc)
//        JOptionPane.showMessageDialog(getThis(),
//                                        Public.info, Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);

    if(mi%15==0  && ss == 25)
      if(!zc)
        this.systemExit();
  }

  /**
   * ���ñ��������
   * @return
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = (JPanel)this.getContentPane();
      jContentPane.setLayout(new BorderLayout());
    }
    return jContentPane;
  }

  /**
   * ��������Ƕ�뵽�����ı���
   * @return
   */
  public JToolBar getJToolBar() {
    if (jToolBar == null) {
      jToolBar = new JToolBar();
      jToolBar.setPreferredSize(new Dimension(TOOLBARW, TOOLBARH));
      jToolBar.add(this.getBackupAction());
      jToolBar.add(this.getRecoveryAction());
      jToolBar.addSeparator();
      //jToolBar.add(getTaiyiAction());
      jToolBar.add(getQimenAction());
      jToolBar.add(getLiurenAction());
      jToolBar.addSeparator();
      jToolBar.add(getZiweiAction());
      jToolBar.add(getTiebanAction());
      jToolBar.addSeparator();
      jToolBar.add(getSizhuAction());
      jToolBar.add(getYijingAction());
      jToolBar.addSeparator();
      jToolBar.add(getFengshuiBzAction());
      jToolBar.add(getFengshuiXkAction());
      //jToolBar.add(getFengshuiShAction());
      //jToolBar.add(getFengshuiSyAction());
      jToolBar.addSeparator();
      jToolBar.add(getXingmingAction());
      //jToolBar.add(getJiemengAction());
      jToolBar.add(getZhugeAction());
      jToolBar.add(getChengguAction());
      jToolBar.addSeparator();
      jToolBar.add(getAboutAction());
      jToolBar.add(getHelpAction());
      jToolBar.addSeparator();
      jToolBar.add(getQuitAction());

    }
    return jToolBar;
  }

  /**
   * �õ������
   * @return
   */
  private JLayeredPane getJPanelMain() {
    if (jPanelMain == null) {
      jPanelMain = new JDesktopPane();
    }
    return jPanelMain;
  }

  /**
   * �õ�״̬������壬������ʾ��Ϣ��ʱ����Ϣ
   * @return
   */
  private JPanel getJPanelStatus() {
    if (jPanelStatus == null) {
      jPanelStatus = new JPanel();
      //jPanelStatus.setLayout(new BorderLayout());
      jPanelStatus.setLayout(new BoxLayout(jPanelStatus, BoxLayout.X_AXIS));
      jPanelStatus.setPreferredSize(new Dimension(500, 18));
      jPanelStatus.add(getJPanelStatusTip(), null);
      jPanelStatus.add(getJPanelStatusTime(), null);
    }
    return jPanelStatus;
  }

  /**
   * �õ�״̬����ʾ��Ϣ���
   * @return
   */
  private JPanel getJPanelStatusTip() {
    if (jPanelStatusTip == null) {
      jLabelStatusTip = new JLabel();
      jPanelStatusTip = new JPanel();
      jPanelStatusTip.setLayout(new BorderLayout());
      jPanelStatusTip.setBorder(BorderFactory.createBevelBorder(BevelBorder.
          LOWERED));
      jPanelStatusTip.setPreferredSize(new Dimension(STATUSTIPW, STATUSTIPH));
      jLabelStatusTip.setText("");
      jLabelStatusTip.setFont(new Font("����", Font.PLAIN, 12));
      jPanelStatusTip.add(jLabelStatusTip, BorderLayout.NORTH);
    }
    return jPanelStatusTip;
  }

  /**
   * �õ�״̬����ʱ�����
   * @return
   */
  private JPanel getJPanelStatusTime() {
    if (jPanelStatusTime == null) {
      jLabelStatusTime = new JLabel();
      jPanelStatusTime = new JPanel();
      jPanelStatusTime.setLayout(new BorderLayout());
      jPanelStatusTime.setBorder(BorderFactory.createBevelBorder(BevelBorder.
          LOWERED));
      jPanelStatusTime.setPreferredSize(new Dimension(STATUSTIMEW, STATUSTIMEH));
      jLabelStatusTime.setText("");
      jPanelStatusTime.add(jLabelStatusTime, BorderLayout.NORTH);
    }
    return jPanelStatusTime;
  }

  /**
   * �õ�����ҳ��
   * @return
   */
  public JPanel getSiZhu() {
    if (siZhuPanel == null) {
      siZhuPanel = new JPanel();
    }
    return siZhuPanel;
  }

  /**
   * �˵���
   * @return
   */
  private JMenuBar getJMenuBars() {
    if (jMenuBar == null) {
      jMenuBar = new JMenuBar();
      jMenuBar.add(getJMenuFile());
      jMenuBar.add(getJMenuSansi());
      jMenuBar.add(getJMenuShenshu());
      jMenuBar.add(getJMenuSiliu());
      jMenuBar.add(getJMenuFengshui());
      jMenuBar.add(getJMenuQita());
      jMenuBar.add(getJMenuPreference());
      jMenuBar.add(getJMenuOther());
    }
    return jMenuBar;
  }

  /**
   * �ļ��˵�
   * @return
   */
  private JMenu getJMenuFile() {
    if (fileMenu == null) {
      fileMenu = new JMenu();
      fileMenu.setText("W�ļ�");
      fileMenu.setFont(new Font("����", Font.PLAIN, 12));
      fileMenu.setMnemonic(KeyEvent.VK_S);
      fileMenu.add(this.getBackupAction());
      fileMenu.add(this.getRecoveryAction());
      fileMenu.addSeparator();
      fileMenu.add(getQuitAction());
    }
    return fileMenu;
  }

  /**
   * Ԥ�ⷽ���˵�
   * @return
   */
  private JMenu getJMenuSansi() {
    if (jMenuSansi == null) {
      jMenuSansi = new JMenu();
      jMenuSansi.setText("S��ʽ");
      jMenuSansi.setFont(new Font("����", Font.PLAIN, 12));
      jMenuSansi.setMnemonic(KeyEvent.VK_S);
      jMenuSansi.add(getQimenAction());
      jMenuSansi.add(getLiurenAction());
      jMenuSansi.add(getTaiyiAction());
    }
    return jMenuSansi;
  }

  private JMenu getJMenuShenshu() {
    if (jMenuShenshu == null) {
      jMenuShenshu = new JMenu();
      jMenuShenshu.setText("W����");
      jMenuShenshu.setFont(new Font("����", Font.PLAIN, 12));
      jMenuShenshu.setMnemonic(KeyEvent.VK_S);
      jMenuShenshu.add(getZiweiAction());
      jMenuShenshu.add(getTiebanAction());
      jMenuShenshu.add(getNanjiAction());
      jMenuShenshu.add(getBeijiAction());
      jMenuShenshu.add(getMeihuaAction());
    }
    return jMenuShenshu;
  }

  private JMenu getJMenuFengshui() {
    if (jMenuFengshui == null) {
      jMenuFengshui = new JMenu();
      jMenuFengshui.setText("F��ˮ");
      jMenuFengshui.setFont(new Font("����", Font.PLAIN, 12));
      jMenuFengshui.setMnemonic(KeyEvent.VK_S);
      jMenuFengshui.add(getFengshuiBzAction());
      jMenuFengshui.add(getFengshuiXkAction());
      jMenuFengshui.add(getFengshuiShAction());
      jMenuFengshui.add(getFengshuiSyAction());
    }
    return jMenuFengshui;
  }

  private JMenu getJMenuSiliu() {
    if (jMenuSiliu == null) {
      jMenuSiliu = new JMenu();
      jMenuSiliu.setText("L����");
      jMenuSiliu.setFont(new Font("����", Font.PLAIN, 12));
      jMenuSiliu.setMnemonic(KeyEvent.VK_S);
      jMenuSiliu.add(getSizhuAction());
      jMenuSiliu.add(getYijingAction());
    }
    return jMenuSiliu;
  }

  private JMenu getJMenuQita() {
    if (jMenuQita == null) {
      jMenuQita = new JMenu();
      jMenuQita.setText("Z��ռ");
      jMenuQita.setFont(new Font("����", Font.PLAIN, 12));
      jMenuQita.setMnemonic(KeyEvent.VK_S);
      jMenuQita.add(getXingmingAction());
      jMenuQita.add(getJiemengAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getZhugeAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getChengguAction());
      jMenuQita.add(getShuziAction());
      jMenuQita.add(getCeziAction());
      jMenuQita.add(getChouqianAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getMianxiangAction());
      jMenuQita.add(getShouxiangAction());
      jMenuQita.add(getGuxiangAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getXuexingAction());
      jMenuQita.add(getXingzuoAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getTuibeiAction());
      jMenuQita.add(getShudouAction());
      jMenuQita.add(getZhengzhaoAction());
      jMenuQita.add(getFofaAction());
    }
    return jMenuQita;
  }


  /**
   * �õ���ѡ��˵�
   * @return
   */
  private JMenu getJMenuPreference() {
    if (jMenuPreference == null) {
      jMenuPreference = new JMenu();
      jMenuPreference.setText("P����");
      jMenuPreference.setFont(new Font("����", Font.PLAIN, 12));
      jMenuPreference.setMnemonic(KeyEvent.VK_S);
      jMenuPreference.addSeparator();
    }
    return jMenuPreference;
  }

  /**
   * �õ������˵�
   * @return
   */
  private JMenu getJMenuOther() {
    if (jMenuOther == null) {
      jMenuOther = new JMenu();
      jMenuOther.setText("O����");
      jMenuOther.setFont(new Font("����", Font.PLAIN, 12));
      jMenuOther.setMnemonic(KeyEvent.VK_S);
      jMenuOther.add(getHelpAction());
      jMenuOther.addSeparator();
      jMenuOther.add(getAboutAction());
    }
    return jMenuOther;
  }

  private CommandAction getRecoveryAction() {
    if (cmdOpen == null) {
      cmdOpen = new CommandAction("�ָ�",
                                  new ImageIcon(getClass().getResource("/images/open.gif")),
                                  "�ָ�ԭ�����ݵ�Ԥ������", 'O',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          openFileManager();
        }
      });
    }
    return cmdOpen;
  }

  private CommandAction getBackupAction() {
    if (cmdClose == null) {
      cmdClose = new CommandAction("����", new ImageIcon(getClass().getResource("/images/close.gif")),
                                  "������ʷԤ������", 'C',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          openFileManager();
        }
      });
    }
    return cmdClose;
  }

  private CommandAction getQuitAction() {
    if (cmdQuit == null) {
      cmdQuit = new CommandAction("�˳�", new ImageIcon(getClass().getResource("/images/exit.gif")),
                                  "�˳�������", 'Q',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          systemExit();
        }
      });
    }
    return cmdQuit;
  }

  private CommandAction getHelpAction() {
    if (cmdHelp == null) {
      cmdHelp = new CommandAction("����", new ImageIcon(getClass().getResource("/images/help.gif")),
                                  "�����ļ�", 'H',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //openHelpChm();
        	openHelpIE();
        }
      });
    }
    return cmdHelp;
  }

  private CommandAction getAboutAction() {
    if (cmdAbout == null) {
      cmdAbout = new CommandAction("����", new ImageIcon(getClass().getResource("/images/about.gif")),
                                  "���ڰ汾������", 'A',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
//          JOptionPane.showMessageDialog(getThis(),
//                                        Public.info,Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);
        	AboutFrame.show(1);
        }
      });
    }
    return cmdAbout;
  }

  protected Component getThis() {
    return this;
  }


  private CommandAction getYijingAction() {
    if (cmdYijing == null) {
      cmdYijing = new CommandAction("��س", new ImageIcon(getClass().getResource("/images/m10.gif")),
                                  "��س", 'B',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("ly");
        }
      });
    }
    return cmdYijing;
  }

  private CommandAction getSizhuAction() {
    if (cmdSizhu == null) {
      cmdSizhu = new CommandAction("����", new ImageIcon(getClass().getResource("/images/m9.gif")),
                                  "����", 'D',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("sz");
        }
      });
    }
    return cmdSizhu;
  }

  private CommandAction getFengshuiBzAction() {
    if (cmdFengshuiBz == null) {
      cmdFengshuiBz = new CommandAction("��լ����", new ImageIcon(getClass().getResource("/images/m11.gif")),
                                  "��լ�ɷ�ˮ", 'E',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("bz");
        }
      });
    }
    return cmdFengshuiBz;
  }

  private CommandAction getFengshuiXkAction() {
    if (cmdFengshuiXk == null) {
      cmdFengshuiXk = new CommandAction("���շ���", new ImageIcon(getClass().getResource("/images/m12.gif")),
                                  "���շ����ɷ�ˮ", 'E',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("xk");
        }
      });
    }
    return cmdFengshuiXk;
  }

  private CommandAction getFengshuiShAction() {
    if (cmdFengshuiSh == null) {
      cmdFengshuiSh = new CommandAction("������", new ImageIcon(getClass().getResource("/images/m13.gif")),
                                  "�����ɷ�ˮ", 'E',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("sh");
        }
      });
    }
    return cmdFengshuiSh;
  }

  private CommandAction getFengshuiSyAction() {
      if (cmdFengshuiSy == null) {
        cmdFengshuiSy = new CommandAction("��Ԫ��", new ImageIcon(getClass().getResource("/images/m14.gif")),
                                    "��Ԫ�ɷ�ˮ", 'E',
                                    new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            OpenIntoTree("sy");
          }
        });
      }
      return cmdFengshuiSy;
    }

  CommandAction getQimenAction() {
    if (cmdQimen == null) {
      cmdQimen = new CommandAction("����", new ImageIcon(getClass().getResource("/images/m1.gif")),
                                  "���Ŷݼ�", 'F',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("qm");
        }
      });
    }
    return cmdQimen;
  }

  private CommandAction getLiurenAction() {
    if (cmdLiuren == null) {
      cmdLiuren = new CommandAction("����", new ImageIcon(getClass().getResource("/images/m2.gif")),
                                  "����", 'G',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("lr");
        }
      });
    }
    return cmdLiuren;
  }

  private CommandAction getTaiyiAction() {
    if (cmdTaiyi == null) {
      cmdTaiyi = new CommandAction("̫��", new ImageIcon(getClass().getResource("/images/m3.gif")),
                                  "̫������", 'I',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("ty");
        }
      });
    }
    return cmdTaiyi;
  }

  private CommandAction getZiweiAction() {
    if (cmdZiwei == null) {
      cmdZiwei = new CommandAction("��΢����", new ImageIcon(getClass().getResource("/images/m4.gif")),
                                  "��΢����", 'J',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("zw");
        }
      });
    }
    return cmdZiwei;
  }

  private CommandAction getTiebanAction() {
    if (cmdTieban == null) {
      cmdTieban = new CommandAction("��������", new ImageIcon(getClass().getResource("/images/m5.gif")),
                                  "��������", 'K',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("tb");
        }
      });
    }
    return cmdTieban;
  }

  private CommandAction getNanjiAction() {
    if (cmdNanji == null) {
      cmdNanji = new CommandAction("�ϼ�����", new ImageIcon(getClass().getResource("/images/m6.gif")),
                                  "�ϼ�����", 'K',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("nj");
        }
      });
    }
    return cmdNanji;
  }

  private CommandAction getBeijiAction() {
    if (cmdBeiji == null) {
      cmdBeiji = new CommandAction("��������", new ImageIcon(getClass().getResource("/images/m7.gif")),
                                  "��������", 'K',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("bj");
        }
      });
    }
    return cmdBeiji;
  }

  private CommandAction getMeihuaAction() {
    if (cmdMeihua == null) {
      cmdMeihua = new CommandAction("��������", new ImageIcon(getClass().getResource("/images/m8.gif")),
                                  "��������", 'S',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //OpenIntoTree("sz");
        }
      });
    }
    return cmdMeihua;
  }

  private CommandAction getZhugeAction() {
    if (cmdZhuge == null) {
      cmdZhuge = new CommandAction("��ȷֶ�", new ImageIcon(getClass().getResource("/images/o3.gif")),
                                  "����ӷֶ���", 'L',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("gg");
        }
      });
    }
    return cmdZhuge;
  }

  private CommandAction getXingmingAction() {
    if (cmdXingming == null) {
      cmdXingming = new CommandAction("����Ԥ��", new ImageIcon(getClass().getResource("/images/o1.gif")),
                                  "����Ԥ��", 'M',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("xm");
        }
      });
    }
    return cmdXingming;
  }

  private CommandAction getCeziAction() {
    if (cmdCezi == null) {
      cmdCezi = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o6.gif")),
                                  "����", 'N',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdCezi;
  }

  private CommandAction getChouqianAction() {
    if (cmdChouqian == null) {
      cmdChouqian = new CommandAction("��ǩ", new ImageIcon(getClass().getResource("/images/o7.gif")),
                                  "��ǩ", 'P',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdChouqian;
  }

  private CommandAction getMianxiangAction() {
    if (cmdMianxiang == null) {
      cmdMianxiang = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o8.gif")),
                                  "����", 'R',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdMianxiang;
  }

  private CommandAction getShouxiangAction() {
    if (cmdShouxiang == null) {
      cmdShouxiang = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o9.gif")),
                                  "����", 'S',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdShouxiang;
  }

  private CommandAction getGuxiangAction() {
    if (cmdGuxiang == null) {
      cmdGuxiang = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o10.gif")),
                                  "����", 'T',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdGuxiang;
  }

  private CommandAction getTuibeiAction() {
    if (cmdTuibei == null) {
      cmdTuibei = new CommandAction("�Ʊ�ͼ", new ImageIcon(getClass().getResource("/images/o13.gif")),
                                  "�Ʊ�ͼ", 'U',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdTuibei;
  }

  private CommandAction getShudouAction() {
    if (cmdShudou == null) {
      cmdShudou = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o14.gif")),
                                  "����", 'V',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdShudou;
  }

  private CommandAction getJiemengAction() {
    if (cmdJiemeng == null) {
      cmdJiemeng = new CommandAction("�ܹ�����", new ImageIcon(getClass().getResource("/images/o2.gif")),
                                  "�ܹ�����", 'W',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdJiemeng;
  }

  private CommandAction getZhengzhaoAction() {
    if (cmdZhengzhao == null) {
      cmdZhengzhao = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o15.gif")),
                                  "����", 'X',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdZhengzhao;
  }

  private CommandAction getFofaAction() {
    if (cmdFofa == null) {
      cmdFofa = new CommandAction("������", new ImageIcon(getClass().getResource("/images/o16.gif")),
                                  "������", 'Y',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdFofa;
  }

  private CommandAction getXuexingAction() {
    if (cmdXuexing == null) {
      cmdXuexing = new CommandAction("Ѫ��", new ImageIcon(getClass().getResource("/images/o11.gif")),
                                  "Ѫ��", 'Z',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdXuexing;
  }

  private CommandAction getXingzuoAction() {
    if (cmdXingzuo == null) {
      cmdXingzuo = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o12.gif")),
                                  "����", '1',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdXingzuo;
  }

  private CommandAction getShuziAction() {
    if (cmdShuzi == null) {
      cmdShuzi = new CommandAction("����", new ImageIcon(getClass().getResource("/images/o5.gif")),
                                  "����", '2',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdShuzi;
  }

  private CommandAction getChengguAction() {
    if (cmdChenggu == null) {
      cmdChenggu = new CommandAction("�ƹ�", new ImageIcon(getClass().getResource("/images/o4.gif")),
                                  "Ԭ��ƹ�", '3',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("cg");
        }
      });
    }
    return cmdChenggu;
  }


  public static void initGlobalFontSetting(Font fnt) {
    FontUIResource fontRes = new FontUIResource(fnt);
    for (Enumeration keys = UIManager.getDefaults().keys();
         keys.hasMoreElements(); ) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value instanceof FontUIResource)
        UIManager.put(key, fontRes);
    }
  }

  public void setStatusText(String text) {
    jLabelStatusTip.setText(" " + text);
  }

//  public void showSplash() {
//    SplashWindow splash = null;
//    splash = new SplashWindow(500);
//    //int rand = (int)(Math.random()*10);
//    //if(rand >5) rand = rand/2;
//    //splash.showSplash("/images/logo"+rand+".gif");
//    splash.showSplash("/images/logo.gif");
//  }

  public Main1() {
    super();
    initialize();
  }

  public static void main(String[] args) {
    initGlobalFontSetting(new java.awt.Font("����", java.awt.Font.PLAIN, 12));
    Main1 application = new Main1();
    application.setStatusText("������....");
    //application.showSplash();
    application.show();
    application.setStatusText(Public.status);

    //int rand = (int)(Math.random()*10);
    //if(rand >5) rand = rand/2;
    //System.err.println(rand);
  }

}
