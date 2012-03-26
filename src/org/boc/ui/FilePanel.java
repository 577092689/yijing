package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.boc.util.CustomFileFilter;
import org.boc.util.FileUtilities;
import org.boc.util.Messages;
import org.boc.util.Public;

public class FilePanel
    extends JPanel
    implements  ActionListener {
  private JyjButton bBackup;
  private JyjButton bRecovery;
  private JCheckBox delOldBack;
  private JCheckBox delOldRecover;

  public FilePanel() {
    super();
    init();
  }

  public void init() {
    bBackup = new JyjButton(
      new ImageIcon(getClass().getResource("/images/close.gif")), "����", this, "backup");
    bRecovery = new JyjButton(
      new ImageIcon(getClass().getResource("/images/open.gif")), "�ָ�", this, "recovery");
    this.setLayout(new BorderLayout());
    this.setFont(Public.getFont());

    Box box1 = new Box(BoxLayout.X_AXIS);
    box1.add(new JLabel("1. �ļ����ݣ�    "));
    box1.add(bBackup);
    delOldBack = new JCheckBox("ɾ��ԭ��ʷ����", false);
    box1.add(new JLabel("      "));
    box1.add(delOldBack);

    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("2. �ļ��ָ���    "));
    box2.add(bRecovery);
    delOldRecover = new JCheckBox("ɾ��ԭ�����ļ�", false);
    box2.add(new JLabel("      "));
    box2.add(delOldRecover);

    Box box3 = new Box(BoxLayout.Y_AXIS);
    box3.add(box1);
    box3.add(box2);
    box3.add(new JLabel(" "));

    this.add(new JLabel(" "),BorderLayout.NORTH);
    this.add(box3,BorderLayout.CENTER);
    this.add(new JLabel(" "),BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("backup")) {
      try {
          this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
          //�����ʷĿ¼Ϊ�գ���ֱ�ӷ���
          if(!jarOrZipFiles(Public.HOME+Public.DATA)) {
            Messages.info("��ʷ����Ŀ¼Ϊ�գ�����Ҫ���ݣ�");
            return;
          }
          this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
      catch (Exception ex) {
        Messages.error("FilePanel()������ʷ����ʧ��: " + ex);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }

    }
    else{
      try {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //Messages.info(name);
        readFromJarOrZip();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
      catch (Exception ex) {
        Messages.error("FilePanel()�ָ��ļ�ʧ��: " + ex);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }
  }

  /**
   * ��ָ��Ŀ¼�µ��ļ����jar��
   * ��󱣴浽ָ�����ļ�������
   */
  public boolean jarOrZipFiles(String path) {
    File[] files = getFiles(path, null);
    if(files==null || files.length<1)
      return false;

    String name = null;
    CustomFileFilter zipfileFilter[] = {
        new CustomFileFilter("�鵵�ļ�(*.jar)", FileUtilities.JAR),
        new CustomFileFilter("ѹ���ļ�(*.zip)", FileUtilities.ZIP)};
    try{
      name = Messages.chooseFile(zipfileFilter, true);
      if (name != null) {
        //true����ļ���������ʾ���ǣ�false�ļ�����Ҳ����ʾ���ǣ���Ϊ�ǻָ�
        if (name.substring(name.lastIndexOf(".")+1).equals("zip")) {
          ZipOutputStream zipOutput = new ZipOutputStream(new
              BufferedOutputStream(new FileOutputStream(name)));
          zipOutput.setMethod(ZipOutputStream.DEFLATED);
          for (int i = 0; i < files.length; i++) {
            ZipEntry entry = new ZipEntry(files[i].getName());
            entry.setMethod(ZipOutputStream.DEFLATED);
            zipOutput.putNextEntry(entry);
            zipOutput.write(getBytesFromFile(files[i]));
          }
          if (zipOutput != null) {
            zipOutput.closeEntry();
            zipOutput.close();
          }
        }else if(name.substring(name.lastIndexOf(".")+1).equals("jar")) {
          JarOutputStream jarOutput = new JarOutputStream(new
              BufferedOutputStream(new FileOutputStream(name)));
          jarOutput.setMethod(JarOutputStream.DEFLATED);
          for (int i = 0; i < files.length; i++) {
            ZipEntry entry = new ZipEntry( files[i].getName());
            entry.setMethod(JarOutputStream.DEFLATED);
            jarOutput.putNextEntry(entry);
            jarOutput.write(getBytesFromFile(files[i]));
          }
          if (jarOutput != null) {
            jarOutput.closeEntry();
            jarOutput.close();
          }
        }
        if(this.delOldBack.isSelected()) {
            int tip = Messages.question("���Ҫɾ��ԭ��ʷ������");
            //Messages.info(String.valueOf(tip)); 0Ϊȷ�� 1Ϊ����
            if(tip==0) {
              File f = new File(Public.HOME+Public.DATA);
              File[] fs = f.listFiles();
              for(int i=0; i<fs.length; i++) {
                fs[i].delete();
              }
            }
          }

        Messages.error("������ʷ���ݳɹ����� ");
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      Messages.error("JxtDumpPanelListener : erreur SQL : " + ex);
    }
    return true;
  }

  /**
   * ��ѹjar�����ļ�����
   * ��󱣴浽ָ�����ļ�������
   */
  public void UnJarOrZipFile(String path) {
    byte[] bs ;
    String name = null;
    final int BUFFER = 2048;
    CustomFileFilter zipfileFilter[] = {
        new CustomFileFilter("�鵵�ļ�(*.jar)", FileUtilities.JAR),
        new CustomFileFilter("ѹ���ļ�(*.zip)", FileUtilities.ZIP)};
    try{
      name = Messages.chooseFile(zipfileFilter, false);
      if (name != null) {
        if (name.substring(name.lastIndexOf(".") + 1).equals("zip")) {
          BufferedOutputStream dest = null;
          FileInputStream fis = new FileInputStream(name);
          ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
          ZipEntry entry;
          while ( (entry = zis.getNextEntry()) != null) {
            //System.out.println("Extracting: " +entry);
            int count;
            byte data[] = new byte[BUFFER];
            // write the files to the disk
            FileOutputStream fos = new FileOutputStream(path+File.separator+entry.getName());
            dest = new BufferedOutputStream(fos, BUFFER);
            while ( (count = zis.read(data, 0, BUFFER)) != -1) {
              dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
          }
          if (zis != null) {
            zis.closeEntry();
            zis.close();
          }
        }
        else if (name.substring(name.lastIndexOf(".") + 1).equals("jar")) {
          BufferedOutputStream dest = null;
          FileInputStream fis = new FileInputStream(name);
          JarInputStream zis = new JarInputStream(new BufferedInputStream(fis));
          JarEntry entry;
          while ( (entry = zis.getNextJarEntry()) != null) {
            //System.out.println("Extracting: " +entry);
            int count;
            byte data[] = new byte[BUFFER];
            // write the files to the disk
            FileOutputStream fos = new FileOutputStream(path+File.separator+entry.getName());
                      dest = new BufferedOutputStream(fos, BUFFER);
                      while ( (count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                      }
                      dest.flush();
                      dest.close();
                    }
                    if (zis != null) {
                      zis.closeEntry();
                      zis.close();
                    }

        }
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      Messages.error("��ѹ���ļ�ʧ��: " + ex);
    }
  }

  /**
   * ��ȡjar�����ļ����ݣ�׷�ӵ�ԭ�ļ��ĺ���
   * ��󱣴浽ָ�����ļ�������
   */
  public void readFromJarOrZip() {
    String path = Public.HOME + Public.DATA;
    File[] files = getFiles(path, null);

    final int BUFFER = 2048;
    String name = null;
    CustomFileFilter zipfileFilter[] = {
        new CustomFileFilter("�鵵�ļ�(*.jar)", FileUtilities.JAR),
        new CustomFileFilter("ѹ���ļ�(*.zip)", FileUtilities.ZIP)};
    try{
      name = Messages.chooseFile(zipfileFilter, false);
      if (name != null) {
        if (name.substring(name.lastIndexOf(".")+1).equals("zip")) {
          BufferedOutputStream dest = null;
          ZipInputStream zis =
              new ZipInputStream(new BufferedInputStream(new FileInputStream(name)));
          ZipEntry entry ;
          while ( (entry = zis.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];
            //���ԭĿ¼�����ļ�����׷�ӵ��ļ�ĩβ�������½�
            //Messages.info(path + File.separator+entry.getName());
            FileOutputStream fos = new FileOutputStream(new File(path + File.separator+entry.getName()), false);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ( (count = zis.read(data, 0, BUFFER)) != -1) {
              dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
          }
          if (zis != null) {
            zis.closeEntry();
            zis.close();
          }
        }else if(name.substring(name.lastIndexOf(".")+1).equals("jar")) {
          BufferedOutputStream dest = null;
          JarInputStream zis =
              new JarInputStream(new BufferedInputStream(new FileInputStream(name)));
          ZipEntry entry ;
          while ( (entry = zis.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];
            //���ԭĿ¼�����ļ�����׷�ӵ��ļ�ĩβ�������½�
            FileOutputStream fos = new FileOutputStream(new File(path + File.separator+entry.getName()), false);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ( (count = zis.read(data, 0, BUFFER)) != -1) {
              dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
          }
          if (zis != null) {
            zis.closeEntry();
            zis.close();
          }
        }
        if(this.delOldRecover.isSelected()) {
          File f = new File(name);
          int tip = Messages.question("���Ҫɾ�����ݵ���ʷ������");
          //Messages.info(String.valueOf(tip)); 0Ϊȷ�� 1Ϊ����
          if(tip==0) {
            f.delete();
          }
        }
        Messages.error("�ָ���ʷ���ݵ��������ݿ�ɹ����� ");
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      Messages.error("�ָ���ʷ���ݵ��������ݿ�ʧ��: " + ex);
    }
  }

  /**
   * �õ�ĳ��Ŀ¼�µ������ļ�����
   * ���Թ��˵õ�ĳ���ض��Ĵ���չ���Ļ����ĳ���ֵ��ļ�
   */
  public String[] getFilesAndDir(String path, final String sFilter) {
    if (path == null)
      return null;
    File dir = new File(path);
    String filename;
    String[] children;
    FilenameFilter filter;

    if (sFilter != null) {
      filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.indexOf(sFilter) != -1;
        }
      };
      children = dir.list(filter);
    }
    else {
      children = dir.list();
    }

    return children;
  }

  /**
   * �õ�ĳ��Ŀ¼�µ������ļ�����
   * ���Թ��˵õ�ĳ���ض��Ĵ���չ���Ļ����ĳ���ֵ��ļ�
   */
  public File[] getFiles(String path, final String sFilter) {
    if (path == null)
      return null;
    File dir = new File(path);
    String filename;
    File[] children;
    FilenameFilter filter;

    if (sFilter != null) {
      filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.indexOf(sFilter) != -1;
        }
      };
      children = dir.listFiles(filter);
    }
    else {
      children = dir.listFiles();
    }

    return children;
  }


  /**
   * ��ȡ�ļ����ֽ�������
   * @param file File
   * @throws IOException
   * @return byte[]
   */
  public byte[] getBytesFromFile(File file) throws IOException {
    InputStream is = new FileInputStream(file);
    long length = file.length();
    if (length > Integer.MAX_VALUE) {
      Messages.info("�ļ�̫������");
    }
    byte[] bytes = new byte[ (int) length];
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length &&
           (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += numRead;
    }
    if (offset < bytes.length) {
      Messages.info("��ȡ�ļ���������");
    }
    is.close();
    return bytes;
  }



  public static void main(String[] args) {
  }


}
