package com.sgs.mcma.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

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
public class BaseFrame extends JFrame{

	public static BaseFrame instance;
	private static ConsolePane console;
	TrayIcon trayIcon;
    SystemTray tray;
    
    public BaseFrame(String title, int width, int height)
    {
	    systemTrayInitialization();
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
				BaseFrame frame = new BaseFrame("Minecraft Mod Admin", 1024, 700);
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
    private void systemTrayInitialization() {
	    if(SystemTray.isSupported()){
	        tray=SystemTray.getSystemTray();
	        Image image=Toolkit.getDefaultToolkit().getImage("Resources\\Images\\SGSLogo.png");
	        ActionListener exitListener=new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.out.println("Exiting....");
	                System.exit(0);
	            }
	        };
	        PopupMenu popup=new PopupMenu();
	        MenuItem defaultItem=new MenuItem("Exit");
	        defaultItem.addActionListener(exitListener);
	        popup.add(defaultItem);
	        defaultItem=new MenuItem("Open");
	        defaultItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                setVisible(true);
	                setExtendedState(JFrame.NORMAL);
	            }
	        });
	        popup.add(defaultItem);
	        trayIcon=new TrayIcon(image, "SystemTray Demo", popup);
	        trayIcon.setImageAutoSize(true);
	        trayIcon.addMouseListener(new MouseAdapter() {
	        	public void mousePressed(java.awt.event.MouseEvent e) 
	        	{
	        		if(e.getClickCount() >= 2){
	        			setVisible(true);
		                setExtendedState(JFrame.NORMAL);
	                }
	        	};
			});
	    }else{
	        System.out.println("system tray not supported");
	    }
	    addWindowStateListener(new WindowStateListener() {
	        public void windowStateChanged(WindowEvent e) {
	            if(e.getNewState()==ICONIFIED){
	                try {
	                    tray.add(trayIcon);
	                    setVisible(false);
	                } catch (AWTException ex) {
	                    System.out.println("unable to add to tray");
	                }
	            }
	    if(e.getNewState()==7){
	                try{
	        tray.add(trayIcon);
	        setVisible(false);
	        System.out.println("added to SystemTray");
	        }catch(AWTException ex){
	        System.out.println("unable to add to system tray");
	    }
	        }
	    if(e.getNewState()==MAXIMIZED_BOTH){
	                tray.remove(trayIcon);
	                setVisible(true);
	            }
	            if(e.getNewState()==NORMAL){
	                tray.remove(trayIcon);
	                setVisible(true);
	            }
	        }
	    });
	
	    setVisible(true);
	    setSize(300, 200);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
