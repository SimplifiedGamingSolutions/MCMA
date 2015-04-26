package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javafx.scene.control.Alert;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.sgs.mcma.gui.view.console.ConsolePane;
import com.sgs.mcma.gui.view.console.ControllableProcess;

public class BaseFrame extends JFrame {
	private static BaseFrame instance;
	private static ConsolePane console;
	public static BaseFrame Instance(){
		return instance;
	}

	protected Process serverProcess;
	public BaseFrame(String title, int width, int height){
		super();
		instance = this;
		Initialize(title, width, height);
	}

	private void Initialize(String title, int width, int height) {
		this.setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(width,height));
		populateContentPane();
	}

	private void populateContentPane() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createTabs(), BorderLayout.CENTER);
	}

//
//			TabbedPane Contents
//
	private JTabbedPane createTabs() {
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=50 marginheight=10>Summary</body></html>", createTab1());
		tabs.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=50 marginheight=10>Config</body></html>", createTab2());
		tabs.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=50 marginheight=10>Configuration</body></html>", new JPanel());
		tabs.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=50 marginheight=10>Logs</body></html>", new JPanel());
		return tabs;
	}
//
//			TAB1 Contents
//
	private JPanel createTab1() {
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		console = new ConsolePane();
		tab1.add(console, BorderLayout.CENTER);
		tab1.add(createButtonPanel(), BorderLayout.SOUTH);
		return tab1;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(createButtonBox());
		return buttonPanel;
	}

	private Box createButtonBox() {
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(createStartServerButton());
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(createStopServerButton());
		buttonBox.add(Box.createHorizontalGlue());
		return buttonBox;
	}

	private JButton createStartServerButton(){
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer();
			}

			private void startServer() {
				console.startServer();
			}
		});
		return startButton;
	}
	private JButton createStopServerButton(){
		JButton stopButton = new JButton("STOP");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}

			private void stopServer() {
				console.stopServer();
			}
		});
		return stopButton;
	}
//
//	TAB2 Contents
//
	private JTabbedPane createTab2() {
		JTabbedPane config = new JTabbedPane();
		config.setTabPlacement(JTabbedPane.LEFT);
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    DirectoryTreeView dt = new DirectoryTreeView(new File("Server"));
	    JScrollPane treeView = new JScrollPane(dt);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(CreateSyntaxTextArea());
		splitPane.setSize(1024, 700); //size needed in order to set divider location
		splitPane.setDividerLocation(.5);
		
		tab1.add(splitPane, BorderLayout.CENTER);

		config.addTab("Server Config", tab1);
		config.addTab("Mod Config", new JPanel());
		return config;
	}
	private Component CreateSyntaxTextArea() {
	      JPanel cp = new JPanel(new BorderLayout());
	      RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
	      textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
	      textArea.setCodeFoldingEnabled(true);
	      textArea.setAntiAliasingEnabled(true);
	      RTextScrollPane sp = new RTextScrollPane(textArea);
	      sp.setFoldIndicatorEnabled(true);
	      cp.add(sp);
	      return cp;
}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				BaseFrame frame = new BaseFrame("Minecraft Mod Admin", 1024, 700);
				frame.setVisible(true);
			}
		});
	}
}
