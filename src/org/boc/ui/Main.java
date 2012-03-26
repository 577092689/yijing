package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;

import org.boc.db.qm.QiMen2;
import org.boc.help.HelpThread;
import org.boc.ui.qm.AboutFrame;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.xml.XmlProc;

public final class Main extends JFrame {
  private Main1 main ;
  private Main2 main2;  
  private JPanel jContentPane; //���������
  private JPanel jPanelStatus; //״̬��
  private JPanel jPanelStatusTip; //״̬����ʾ����
  private JLabel jLabelStatusTip; //״̬����ʾ��ǩ
  private static JLabel jLabelInfo; //״̬���������Ϣ��ǩ
  private JPanel jPanelInfo; //״̬����ʱ������
  private javax.swing.Timer timer;
  private static final int WIDTH = 800;
  private static final int HIGHT = 500;
  private static Main jyjframe ;
  public static PrintStream fout; 
  public static PrintStream ferr;

  private Main() {
    main = new Main1();
    main2 = new Main2();
  }

  public static Main getInstance() {
    if(jyjframe==null)
      jyjframe = new Main();
    return jyjframe;
  }

  public void init() {
  	JFrame.setDefaultLookAndFeelDecorated(true);
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //����ȱʡ�رհ�ť
    setSize();

    this.setContentPane(getJContentPane());
    this.setJMenuBar(main.getJMenuBar());
    this.getContentPane().add(main.getJToolBar(),BorderLayout.NORTH);
    this.getContentPane().add(main2, BorderLayout.CENTER);
    this.getContentPane().add(getJPanelStatus(), BorderLayout.SOUTH);

    this.setTitle(Public.title);
    this.setIconImage((new ImageIcon(getClass().getResource("/images/title.gif"))).getImage());
    //refreshStatus();

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        systemExit();
      }
    });
  }

  public static void setStatusInfo(int type) {
  	if(type==Public.QM)
  	jLabelInfo.setText("                "+
    		(QiMen2.ZF?"ת��":"����")+"      "+
    		(QiMen2.RB?"����":"�𲹷�")+"     "+
    		(QiMen2.TD?"Сֵ��������":"Сֵ�������")+"     "+
    		(QiMen2.KG?"��������":"��������"));      
  	else if(type==Public.LY)
  		jLabelInfo.setText("                ");
  	else
  		jLabelInfo.setText("                ");
  }

  private void setSize() {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(WIDTH, HIGHT);
    int width = this.getWidth();
    int height = this.getHeight();
    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;
    this.setBounds(x, y, width, height);
    //this.setBounds(0, 0, screen.width, screen.height);
  }

  private JPanel getJPanelStatus() {
    if (jPanelStatus == null) {
      jPanelStatus = new JPanel();
      jPanelStatus.setLayout(new BoxLayout(jPanelStatus, BoxLayout.X_AXIS));
      jPanelStatus.setPreferredSize(new Dimension(900, 30));
      jPanelStatus.add(getStatusInfo(), null);  //һ����ʾ��ǰ��ֵ�״��
      jPanelStatus.add(getJPanelStatusTip(), null); //һ����ʾ��Ȩ�����
    }
    return jPanelStatus;
  }
  
  /**
   * �õ�״̬������Ϣ���
   * @return
   */
  private JPanel getStatusInfo() {
    if (jPanelInfo == null) {
      jLabelInfo = new JLabel();
      jPanelInfo = new JPanel();
      jPanelInfo.setLayout(new BorderLayout());
      jPanelInfo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      jPanelInfo.setPreferredSize(new Dimension(800, 30));
      jLabelInfo.setFont(new Font("TimesRoman", Font.PLAIN, 12));
      //jLabelInfo.setForeground(Color.BLUE);  
      jPanelInfo.add(jLabelInfo, BorderLayout.CENTER);
    }
    return jPanelInfo;
  }
  
  /**
   * �õ�״̬����Ȩ���
   * @return
   */
  private JPanel getJPanelStatusTip() {
    if (jPanelStatusTip == null) {
      jLabelStatusTip = new JLabel();  //��ʾ��Ȩ��Ϣ
      jPanelStatusTip = new JPanel();	 //��������壬����jLabelStatusTip
      jPanelStatusTip.setLayout(new BorderLayout());
      jPanelStatusTip.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      jPanelStatusTip.setPreferredSize(new Dimension(100, 30));
      jLabelStatusTip.setFont(new Font("TimesRoman", Font.PLAIN, 12));
      //jLabelStatusTip.setForeground(Color.BLUE);
      //jLabelStatusTip.setAlignmentY(CENTER_ALIGNMENT);
      jPanelStatusTip.add(jLabelStatusTip, BorderLayout.CENTER);
    }
    return jPanelStatusTip;
  }

  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = (JPanel)this.getContentPane();
      jContentPane.setLayout(new BorderLayout());
    }
    return jContentPane;
  }

  protected Component getThis() {
    return this;
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

  public void setStatusText() {
    jLabelStatusTip.setText("  " + Public.status);
  }

  public void systemExit() {
  	if(fout!=null)
  		fout.close();
  	if(ferr!=null)
  		ferr.close();
  	
//  JOptionPane.showMessageDialog(getThis(),Public.info,Public.infoTitle,JOptionPane.INFORMATION_MESSAGE);
//  System.exit(0);
  	AboutFrame.show(0);

  }

  public void showSplash() {
    SplashWindow splash = null;
    splash = new SplashWindow(Public.SPLASH);
    splash.showSplash("/images/logo.gif");
  }

  /** Memory managment */
  protected void finalize() throws Throwable {
    super.finalize();
    main = null;
    main2 = null;
  }
  //����ҪԤ�Ƚ���û��ֱ��ȱʡ����󱣴�ʱ������
  private static void writeDefaultData() {
  	File f = new File(Public.HOME + Public.DATA);
  	if(!f.exists()) f.mkdirs();
  	f = new File(Public.HOME + Public.LOG);
  	if(!f.exists()) f.mkdirs();
//    File test ;
//    for (int i = 0; i < Public.valueRoot.length; i++) {
//    	File datafile = new File(Public.HOME + Public.DATA);
//    	if(!datafile.exists()) 
//    		datafile.mkdir();
//    	
//      test = new File(Public.HOME + Public.DATA + File.separator + Public.valueRoot[i]+".dat");
//      if (!test.exists()) {
//        Public.writeObject2File(Public.valueRoot[i], null);
//      }
//    }
  }

  //��jar��Ŀ¼�µ��ļ��������û�Ŀ¼��
  private static void copy2Home(String dir, String fname)  throws Exception{
    InputStream in;
    FileOutputStream out;
    File test ;

    String filepath = Public.HOME + dir + File.separator+fname;
    test = new File(filepath);
    if (!test.exists()) {
      in = test.getClass().getResourceAsStream("/" + dir + "/" + fname);
      out = new FileOutputStream(filepath);
      int bRead = -1;
      while ( (bRead = in.read()) != -1)
        out.write(bRead);
      out.close();
    }
  }
  
  /**
   * ȱʡ���������ϵĵ�ǰʱ��ڵ�
   * @param type ȱʡ�򿪵�Public.valueRoot[] qm/ly��ȱʡ˫����һ��
   * @param leafNode  ��ǰʱ��
   * @param parentNodeName  ���Ŷݼ�
   */
  public void openDefault(String[] types) {
  	for(String type : types) 
  		main.OpenIntoTree(type);  //�����ϴ򿪼���ȱʡ��Ԥ�⼼��   
  	String type = types[0];
  	
  	String typename = Public.mapRootValueKey.get(type);
    //ģ��˫���¼������ұߴ�һ�����
    JTabbedPane rightJTabbedPane = Main2.getRightTabbedPane();
    //��һ�δ�xx.xmlʱ��ȱʡѡ������ʱ�䡱����ڵ�
    BasicJTabbedPane basePane = new BasicJTabbedPane(type,Public.NOW,null);
    new HelpThread(basePane).start();  //����һ�����߳�
    TreePanel.mapBaseTabPane.put(type, basePane);
    rightJTabbedPane.add(basePane, typename);
    TreePanel.setCurView(rightJTabbedPane,typename);  //ѡ��˫��ʱ��Ӧ��Ԥ�����
    Public.setKeyValue(Public.mapKeyIsOpen, type, true);
  }

  /**
   * ��д���û�Ŀ¼�����������������ļ�
   * ȥ���û�Ŀ¼ֱ�ӱ����ڵ�ǰĿ¼�£�simon 2011-10-10
   * @param args
   */
  public static void main(String[] args) {
  	Long t1 = System.currentTimeMillis();
  	
  	XmlProc.loadFromOnFile();  //���������ļ�����ѡ��
    try {
      //����û�Ŀ¼
      //checkUserDirectory();
      //д��ȱʡ�������ļ�
      writeDefaultData();
    }
    catch (Exception e) {
      e.printStackTrace();
      //System.err.println("JyiFrame.main() : Error : " + e);
      System.exit(0);
    }
    try {
      try {
      	if(Public.DEBUGSWITCH) {
      		ferr = new PrintStream(new FileOutputStream(Public.HOME + "log/jyijing.err"), true); 
      		System.setErr(ferr);
      	}
      }
      catch (Exception e) {
      	if(!Public.DEBUGSWITCH) 
      		System.err.println("Jxtray.main() : Output Error redirection : " + e);
      }
      try {
      	if(Public.DEBUGSWITCH) {
      		fout = new PrintStream(new FileOutputStream(Public.HOME + "log/jyijing.out"), true);
      		System.setOut(fout);
      	}
      }
      catch (Exception e) {
        System.err.println("Jxtray.main() : Output redirection : " + e);
      }
    }catch (Exception e) {
      Messages.error("��������û��װ�������ļ�(" + e + ")");
      System.err.println("��������û��װ�������ļ�(" + e + ")");
      System.exit(0);
    }

    initGlobalFontSetting(Public.getFont());
    Main frame = Main.getInstance();
    frame.init();
    //frame.showSplash();  //������������    
    //frame.setSize();
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setStatusText();
    frame.setStatusInfo(Public.QM);
    frame.setVisible(true);
    long t2 = System.currentTimeMillis();
    //System.out.println("1. ���������ֿ��ҳ��ʱ�䣺"+(t2-t1)+"ms");    
    frame.openDefault(new String[]{"ly","qm"});    
    //frame.openDefault(new String[]{"qm","ly"});
    //System.out.println("2. ˫����Ҷ�����־�ʱ�䣺"+(System.currentTimeMillis()-t2)+"ms");    
    if(!QiMen2.IO)
    	XmlProc.loadFromXmlFile();    
  }

}
