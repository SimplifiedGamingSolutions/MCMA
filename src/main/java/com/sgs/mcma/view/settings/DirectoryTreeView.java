package com.sgs.mcma.view.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.sgs.mcma.controller.settings.ServerSettingsController;

@SuppressWarnings("serial")
public class DirectoryTreeView extends JPanel
{
	public static DirectoryTreeView instance;
	public static ArrayList<String> exclusions = new ArrayList<String>(Arrays.asList("crash-reports,logs,world,libraries,eula.txt,.*\\.json,.*\\.jar,.*\\.log.,.*\\..gz,.*\\.dat.*,.*\\.lock,.*\\.mca".split(",")));
	private JTree tree;

	public DirectoryTreeView(File dir)
	{
		DirectoryTreeView.instance = this;
		setLayout(new BorderLayout());

		// Make a tree list with all the nodes, and make it a JTree
		tree = new JTree(addNodes(null, dir));
		tree.setCellRenderer(new MyTreeCellRenderer());
		// Add a listener
		tree.addTreeSelectionListener(new TreeSelectionEditor());

		// Lastly, put the JTree into a JScrollPane.
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.getViewport().add(tree);
		this.add(BorderLayout.CENTER, scrollpane);
	}

	public String getNodeLocalPath(DefaultMutableTreeNode node)
	{
		String jTreeVarSelectedPath = "";
		Object[] paths = node.getPath();
		for (int i = 0; i < paths.length; i++)
		{
			jTreeVarSelectedPath += paths[i];
			if (i + 1 < paths.length)
			{
				jTreeVarSelectedPath += File.separator;
			}
		}
		return jTreeVarSelectedPath;
	}

	/** Add nodes from under "dir" into curTop. Highly recursive. */
	DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir)
	{
		String curPath = dir.getPath();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(dir.getName());
		if (curTop != null)
		{ // should only be null at root
			curTop.add(curDir);
		}
		Vector<String> ol = new Vector<String>();
		String[] tmp = dir.list(new FilenameFilter()
		{

			public boolean accept(File dir, String name)
			{
				for (String ex : DirectoryTreeView.exclusions)
				{
					if (name.matches(ex))
					{
						return false;
					}
				}
				return true;
			}

		});
		for (String element : tmp)
		{
			ol.addElement(element);
		}
		Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
		File f;
		Vector<String> files = new Vector<String>();
		// Make two passes, one for Dirs and one for Files. This is #1.
		for (int i = 0; i < ol.size(); i++)
		{
			String thisObject = ol.elementAt(i);
			String newPath;
			if (curPath.equals("."))
			{
				newPath = thisObject;
			} else
			{
				newPath = curPath + File.separator + thisObject;
			}
			if ((f = new File(newPath)).isDirectory())
			{
				addNodes(curDir, f);
			} else
			{
				files.addElement(thisObject);
			}
		}
		// Pass two: for files.
		for (int fnum = 0; fnum < files.size(); fnum++)
		{
			curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
		}
		return curDir;
	}

	public File getFileForSelectedNode()
	{
		return new File(getNodeLocalPath((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent()));
	}

	@Override
	public Dimension getMinimumSize()
	{
		return new Dimension(200, 400);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200, 400);
	}

	public static void main(String[] av)
	{

		JFrame frame = new JFrame("FileTree");
		frame.setForeground(Color.black);
		frame.setBackground(Color.lightGray);
		Container cp = frame.getContentPane();

		if (av.length == 0)
		{
			cp.add(new DirectoryTreeView(new File("Server")));
		} else
		{
			cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
			for (String element : av)
			{
				cp.add(new DirectoryTreeView(new File(element)));
			}
		}

		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static class MyTreeCellRenderer extends DefaultTreeCellRenderer
	{
		private static ImageIcon closed = MyTreeCellRenderer.scale(new ImageIcon("Resources\\Chest-Closed.png"), 1 / 2, DirectoryTreeView.instance.tree);
		private static ImageIcon open = MyTreeCellRenderer.scale(new ImageIcon("Resources\\Chest-Open.png"), 1 / 2, DirectoryTreeView.instance.tree);

		static ImageIcon scale(ImageIcon icon, double scaleFactor, JTree tree)
		{
			double width = icon.getIconWidth();
			double height = icon.getIconHeight();
			int iconwidth = UIManager.getIcon("FileView.fileIcon").getIconWidth();
			int iconheight = UIManager.getIcon("FileView.fileIcon").getIconHeight();
			BufferedImage image = new BufferedImage(iconwidth, iconheight, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = image.createGraphics();
			g.scale(iconwidth / width, iconheight / height);
			icon.paintIcon(tree, g, 0, 0);
			g.dispose();

			return new ImageIcon(image);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
		{
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			// decide what icons you want by examining the node
			if (value instanceof DefaultMutableTreeNode)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				String path = DirectoryTreeView.instance.getNodeLocalPath(node);
				boolean isDirectory = new File(path).isDirectory();
				if (isDirectory)
				{
					setClosedIcon(MyTreeCellRenderer.closed);
					setOpenIcon(MyTreeCellRenderer.open);
					// setIcon(closed);
					setLeafIcon(MyTreeCellRenderer.closed);
					// setIcon(new ImageIcon("Resources\\Chest-Open.png"));
					// setIcon(UIManager.getIcon("FileView.directoryIcon"));
				} else
				{
					setIcon(UIManager.getIcon("FileView.fileIcon"));
				}
			}

			return this;
		}

	}

	public class TreeSelectionEditor implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent e)
		{
			String path = getNodeLocalPath((DefaultMutableTreeNode) e.getPath().getLastPathComponent());
			File file = new File(path);
			ServerSettingsController.selectedNodeChanged(file, file.isDirectory());
		}
	}
}