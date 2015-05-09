package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.sgs.mcma.gui.view.console.ConsolePane;
import com.sgs.mcma.webservice.Server;

@SuppressWarnings("serial")
public class BaseFrame extends JFrame 
{
	
	private static BaseFrame instance;
	private static ConsolePane console;
	public static DefaultListModel<String> playerListModel = new DefaultListModel<String>();
	
	public static BaseFrame Instance()
	{
		return instance;
	}

	public BaseFrame(String title, int width, int height)
	{
		instance = this;
		Initialize(title, width, height);
		this.pack();
		
	}

	private void Initialize(String title, int width, int height) 
	{
		this.setTitle(title);
		ImageIcon img = new ImageIcon("Resources\\SGSLogo.png");
		this.setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new BaseFrameWindowListener());
		setSize(new Dimension(width,height));
		populateContentPane();
	}

	private void populateContentPane() 
	{
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createTabs(), BorderLayout.CENTER);
	}

//
//			TabbedPane Contents
//
	private JTabbedPane createTabs() 
	{
		MinecraftTabbedPane tabs = new MinecraftTabbedPane();
		tabs.addTab("Summary", createTab1());
		tabs.addTab("Configuration", createTab2());
		tabs.addTab("Logs", new JPanel());
		
		return tabs;
	}
//
//			TAB1 Contents
//
	private JPanel createTab1() 
	{
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		console = new ConsolePane();
		tab1.add(new PlayerListPanel(console,playerListModel), BorderLayout.WEST);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(console, BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		tab1.add(panel, BorderLayout.CENTER);
		return tab1;
	}

	private JPanel createButtonPanel() 
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(createButtonBox());
		return buttonPanel;
	}

	private Box createButtonBox() 
	{
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(createStartServerButton());
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(createStopServerButton());
		buttonBox.add(Box.createHorizontalGlue());
		return buttonBox;
	}

	private JButton createStartServerButton()
	{
		ImageIcon startBtn = new ImageIcon("Resources\\StartBtn.png");
		JButton startButton = new JButton(startBtn);
		startButton.setContentAreaFilled(false);
		startButton.setMargin(new Insets(-2, -2, -2, -2));
		startButton.setFocusPainted(false);
		startButton.setOpaque(false);
		startButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				startServer();
			}

			private void startServer() 
			{
				console.startServer();
			}
		});
		return startButton;
	}
	private JButton createStopServerButton()
	{
		ImageIcon stopBtn = new ImageIcon("Resources\\StopBtn.png");
		JButton stopButton = new JButton(stopBtn);
		stopButton.setContentAreaFilled(false);
		stopButton.setMargin(new Insets(-2, -2, -2, -2));
		stopButton.setFocusPainted(false);
		stopButton.setOpaque(false);
		stopButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				stopServer();
			}

			private void stopServer() 
			{
				console.stopServer();
				playerListModel.clear();
			}
		});
		return stopButton;
	}
//
//	TAB2 Contents
//
	private JTabbedPane createTab2() 
	{
		JTabbedPane config = new JTabbedPane();
		config.setTabPlacement(SwingConstants.LEFT);
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    DirectoryTreeView dt = new DirectoryTreeView(new File("Server"));
	    JScrollPane treeView = new JScrollPane(dt);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(CreateSyntaxTextArea());
		splitPane.setPreferredSize(new Dimension(1024, 700)); //size needed in order to set divider location
		splitPane.setDividerLocation(.5);
		
		tab1.add(splitPane, BorderLayout.CENTER);

		config.addTab("Server Config", tab1);
		config.addTab("Mod Config", new JPanel());
		return config;
	}
	private Component CreateSyntaxTextArea() 
	{
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
		Thread server = new Thread(new Runnable(){
			public void run() {
				new Server("localhost", 39640).run();
			}
		});
		server.start();
	}
	//
	//Window Listener for BaseFrame
	//
	public class BaseFrameWindowListener extends WindowAdapter 
	{			
		public void windowClosing(WindowEvent e) 
		{
			if(console.isRunning())
				console.stopServer();
		}
		
		public void windowClosed(WindowEvent e) 
		{
			if(console.isRunning())
				console.stopServer();
		}
	}

	
	

}
