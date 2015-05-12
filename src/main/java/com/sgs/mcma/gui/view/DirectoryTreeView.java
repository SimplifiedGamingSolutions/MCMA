package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

@SuppressWarnings("serial")
public class DirectoryTreeView extends JPanel {
	public static DirectoryTreeView instance;
	private static ArrayList<String> exclusions = new ArrayList<String>();
	private JTree tree;
	public DirectoryTreeView(File dir, ArrayList<String> exclusions) {
		instance = this;
	    setLayout(new BorderLayout());
	
	    // Make a tree list with all the nodes, and make it a JTree
	    tree = new JTree(addNodes(null, dir));
	    tree.setCellRenderer(new MyTreeCellRenderer());
	    // Add a listener
	    tree.addTreeSelectionListener(new TreeSelectionEditor());
	    
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
	    String[] tmp = dir.list(new FilenameFilter(){
	
			public boolean accept(File dir, String name) {
				boolean check = !name.contains(".jar");
				return check;
			}
	    	
	    });
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

		public File getFileForSelectedNode(){
			return new File(getNodeLocalPath((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent()));
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

	ArrayList<String> exclusions = new ArrayList<String>();
	exclusions.add(".jar");
	exclusions.add(".log");
    if (av.length == 0) {
      cp.add(new DirectoryTreeView(new File("Server"), exclusions));
    } else {
      cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
      for (int i = 0; i < av.length; i++)
        cp.add(new DirectoryTreeView(new File(av[i]), exclusions));
    }

    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {

      @Override
      public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
          super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

          // decide what icons you want by examining the node
          if (value instanceof DefaultMutableTreeNode) {
              DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
              if (new File(instance.getNodeLocalPath(node)).isDirectory()) {
                  setIcon(UIManager.getIcon("FileView.directoryIcon"));
              } else {
                  setIcon(UIManager.getIcon("FileView.fileIcon"));
              }
          }

          return this;
      }

  }
  public class TreeSelectionEditor implements TreeSelectionListener {
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
	    }
}