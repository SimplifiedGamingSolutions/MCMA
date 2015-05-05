package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.sgs.mcma.gui.view.console.ConsolePane;

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
		this.pack();
	}

	private void Initialize(String title, int width, int height) {
		this.setTitle(title);
		ImageIcon img = new ImageIcon("Resources\\SGSLogo.png");
		this.setIconImage(img.getImage());
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
		MinecraftTabbedPane tabs = new MinecraftTabbedPane();
		tabs.addTab("Summary", createTab1());
		tabs.addTab("Configuration", createTab2());
		tabs.addTab("Logs", new JPanel());
		
		return tabs;
	}
//
//			TAB1 Contents
//
	private JPanel createTab1() {
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		console = new ConsolePane();
		JPanel jp = new JPanel(new BorderLayout());
		jp.setPreferredSize(new Dimension(200,0));
		jp.add(new JLabel("Players Online"), BorderLayout.NORTH);
		jp.setAlignmentX(CENTER_ALIGNMENT);
		DefaultListModel<String> playerListModel = new DefaultListModel<String>();
		JList<String> playerList = new JList<String>();
		playerList.setModel(playerListModel);
		playerList.setFont(new Font("Arial",Font.BOLD,14));
		final JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItem("click me"));
		playerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(e.getButton() == MouseEvent.BUTTON3){
					popup.setLocation(e.getLocationOnScreen());
					popup.setVisible(true);
				}
			}
		});
		playerListModel.addElement("longlostbro");
		jp.add(new JScrollPane(playerList), BorderLayout.CENTER);
		tab1.add(jp, BorderLayout.WEST);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(console, BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		tab1.add(panel, BorderLayout.CENTER);
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
		ImageIcon startBtn = new ImageIcon("Resources\\StartBtn.png");
		JButton startButton = new JButton(startBtn);
		//startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setMargin(new Insets(-2, -2, -2, -2));
		startButton.setFocusPainted(false);
		startButton.setOpaque(false);
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
		ImageIcon stopBtn = new ImageIcon("Resources\\StopBtn.png");
		JButton stopButton = new JButton(stopBtn);
		//stopButton.setBorderPainted(false);
		stopButton.setContentAreaFilled(false);
		stopButton.setMargin(new Insets(-2, -2, -2, -2));
		stopButton.setFocusPainted(false);
		stopButton.setOpaque(false);
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
		splitPane.setPreferredSize(new Dimension(1024, 700)); //size needed in order to set divider location
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
