package com.sgs.mcma.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sgs.mcma.view.console.ConsolePane;
import com.sgs.mcma.view.logs.ServerLogTab;
import com.sgs.mcma.view.settings.ServerSettingsTab;
import com.sgs.mcma.view.summary.SummaryTab;
import com.sgs.mcma.webservice.Server;

@SuppressWarnings("serial")
public class BaseFrame extends JFrame
{
	public static BaseFrame instance;
	private static ConsolePane console;

	public BaseFrame(String title, int width, int height)
	{
		BaseFrame.instance = this;
		BaseFrame.console = new ConsolePane();
		Initialize(title, width, height);
		pack();

	}

	private void Initialize(String title, int width, int height)
	{
		setTitle(title);
		ImageIcon img = new ImageIcon("Resources\\SGSLogo.png");
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new BaseFrameWindowListener());
		this.setSize(new Dimension(width, height));
		populateContentPane();
	}

	private void populateContentPane()
	{
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createTabs(), BorderLayout.CENTER);
	}

	//
	// TabbedPane Contents
	//
	private JTabbedPane createTabs()
	{
		final MinecraftTabbedPane tabs = new MinecraftTabbedPane();

		tabs.addTab("Summary", new SummaryTab(BaseFrame.console));
		tabs.addTab("Configuration", new ServerSettingsTab());
		tabs.addTab("Logs", new ServerLogTab());

		tabs.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				if (tabs.getSelectedIndex() == 2)
				{
					ServerLogTab.instance.setSelectedIndex(0);
					ServerLogTab.instance.updateAllMessages();
				}
			}
		});
		return tabs;
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				BaseFrame frame = new BaseFrame("Minecraft Mod Admin", 1024,
						700);
				frame.setVisible(true);
			}
		});
		Thread server = new Thread(new Runnable()
		{
			public void run()
			{
				new Server("localhost", 39640).run();
			}
		});
		server.start();
	}

	//
	// Window Listener for BaseFrame
	//
	public class BaseFrameWindowListener extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			if (BaseFrame.console.isRunning())
			{
				BaseFrame.console.stopServer();
			}
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			if (BaseFrame.console.isRunning())
			{
				BaseFrame.console.stopServer();
			}
		}
	}

}
