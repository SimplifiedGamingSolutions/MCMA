package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sgs.mcma.gui.view.console.ConsolePane;
import com.sgs.mcma.webservice.Server;

@SuppressWarnings("serial")
public class BaseFrame extends JFrame 
{
	private static ConsolePane console;

	public BaseFrame(String title, int width, int height)
	{
		console = new ConsolePane();
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
		final MinecraftTabbedPane tabs = new MinecraftTabbedPane();
		
		tabs.addTab("Summary", new SummaryTab(console));
		tabs.addTab("Configuration", new ServerSettingsTab());
		tabs.addTab("Logs", (JTabbedPane) new ServerLogTab());
		
		tabs.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				if(tabs.getSelectedIndex() == 2){
					ServerLogTab.instance.setSelectedIndex(0);
					ServerLogTab.instance.updateAllMessages();
				}
			}
		});
		return tabs;
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
