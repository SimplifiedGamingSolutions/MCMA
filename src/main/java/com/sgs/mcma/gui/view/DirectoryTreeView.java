package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

@SuppressWarnings("serial")
public class DirectoryTreeView extends JPanel {
	public static DirectoryTreeView instance;
	private JTree tree;
	public File getFileForSelectedNode(){
		return new File(getNodeLocalPath((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent()));
	}
public DirectoryTreeView(File dir) {
	instance = this;
    setLayout(new BorderLayout());

    // Make a tree list with all the nodes, and make it a JTree
    tree = new JTree(addNodes(null, dir));

    // Add a listener
    tree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
        RSyntaxTextArea textArea = ServerSettingsTab.getTextArea();
        if(textArea != null && !new File(getNodeLocalPath(node)).isDirectory()){
        	String text = "";
			try {
				text = new String(Files.readAllBytes(Paths.get(getNodeLocalPath(node))), Charset.defaultCharset());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	textArea.setText(text);
        }
      }
    });
    
    // Lastly, put the JTree into a JScrollPane.
    JScrollPane scrollpane = new JScrollPane();
    scrollpane.getViewport().add(tree);
    add(BorderLayout.CENTER, scrollpane);
  }

  public String getNodeLocalPath(DefaultMutableTreeNode node) {
	  String jTreeVarSelectedPath = "";
      Object[] paths = node.getPath();
      for (int i=0; i<paths.length; i++) {
          jTreeVarSelectedPath += paths[i];
          if (i+1 <paths.length ) {
              jTreeVarSelectedPath += File.separator;
          }
      }
      return jTreeVarSelectedPath;
}

/** Add nodes from under "dir" into curTop. Highly recursive. */
  DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
    String curPath = dir.getPath();
    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(dir.getName());
    if (curTop != null) { // should only be null at root
      curTop.add(curDir);
    }
    Vector<String> ol = new Vector<String>();
    String[] tmp = dir.list();
    for (int i = 0; i < tmp.length; i++)
      ol.addElement(tmp[i]);
    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
    File f;
    Vector<String> files = new Vector<String>();
    // Make two passes, one for Dirs and one for Files. This is #1.
    for (int i = 0; i < ol.size(); i++) {
      String thisObject = ol.elementAt(i);
      String newPath;
      if (curPath.equals("."))
        newPath = thisObject;
      else
        newPath = curPath + File.separator + thisObject;
      if ((f = new File(newPath)).isDirectory())
        addNodes(curDir, f);
      else
        files.addElement(thisObject);
    }
    // Pass two: for files.
    for (int fnum = 0; fnum < files.size(); fnum++)
      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
    return curDir;
  }

  @Override
public Dimension getMinimumSize() {
    return new Dimension(200, 400);
  }

  @Override
public Dimension getPreferredSize() {
    return new Dimension(200, 400);
  }

  public static void main(String[] av) {

    JFrame frame = new JFrame("FileTree");
    frame.setForeground(Color.black);
    frame.setBackground(Color.lightGray);
    Container cp = frame.getContentPane();

    if (av.length == 0) {
      cp.add(new DirectoryTreeView(new File("Server")));
    } else {
      cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
      for (int i = 0; i < av.length; i++)
        cp.add(new DirectoryTreeView(new File(av[i])));
    }

    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}