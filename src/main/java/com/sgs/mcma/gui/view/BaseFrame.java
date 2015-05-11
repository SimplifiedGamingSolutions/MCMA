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
		MinecraftTabbedPane tabs = new MinecraftTabbedPane();
		Tab1 tab1 = new Tab1(console);
		ServerSettingsTab tab2 = new ServerSettingsTab();
		
		tabs.addTab("Summary", tab1.createTab1());
		tabs.addTab("Configuration", tab2.createTab2());
		tabs.addTab("Logs", new JPanel());
		
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
