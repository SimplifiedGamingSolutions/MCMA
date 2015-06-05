package com.sgs.mcma.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sgs.mcma.view.console.ConsolePane;
import com.sgs.mcma.view.logs.ServerLogTab;
import com.sgs.mcma.view.settings.ServerSettingsTab;
import com.sgs.mcma.view.summary.PlayerCommandMenu;
import com.sgs.mcma.view.summary.SummaryTab;
import com.sgs.mcma.webservice.Server;

import de.muntjak.tinylookandfeel.Theme;
import de.muntjak.tinylookandfeel.TinyLookAndFeel;

@SuppressWarnings("serial")
public class BaseFrame extends JFrame{

	public static BaseFrame instance;
	private static ConsolePane console;
	TrayIcon trayIcon;
    SystemTray tray;
    
    public BaseFrame(String title, int width, int height)
    {
    	populateJMenuBar();
		  try {
		      UIManager.setLookAndFeel(new TinyLookAndFeel());
		      SwingUtilities.updateComponentTreeUI(this);
		      if(PlayerCommandMenu.instance != null)
		      {
			      for(JMenuItem item : PlayerCommandMenu.menuItems)
			      {
			    	  item.setForeground(UIManager.getColor("List.foreground"));
			      }
		      }
		  } catch(Exception ex) {
		      ex.printStackTrace();
		  }
	    systemTrayInitialization();
	    BaseFrame.instance = this;
		BaseFrame.console = new ConsolePane();
		Initialize(title, width, height);
		pack();
    }
    private void populateJMenuBar()
	{
    	//Where the GUI is created:
    	JMenuBar menuBar;
    	JMenu menu, submenu;
    	JMenuItem menuItem;
    	JRadioButtonMenuItem rbMenuItem;
    	JCheckBoxMenuItem cbMenuItem;

    	//Create the menu bar.
    	menuBar = new JMenuBar();

    	//Build the first menu.
    	menu = new JMenu("UI Manager");
    	menu.setMnemonic(KeyEvent.VK_A);
    	menu.getAccessibleContext().setAccessibleDescription(
    	        "The only menu in this program that has menu items");
    	menuBar.add(menu);

    	//a group of JMenuItems
    	menuItem = new JMenuItem("load theme",
    	                         KeyEvent.VK_T);
    	menuItem.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_1, ActionEvent.ALT_MASK));
    	menuItem.getAccessibleContext().setAccessibleDescription(
    	        "This doesn't really do anything");
    	menuItem.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
			    JFileChooser ch = new JFileChooser();
			    ch.setCurrentDirectory(new File("src\\main\\java\\Resources\\Themes"));
			    ch.showOpenDialog(BaseFrame.instance);
				try
				{
				    Theme.loadTheme(ch.getSelectedFile());
				    UIManager.setLookAndFeel(UIManager.getLookAndFeel());
				    SwingUtilities.updateComponentTreeUI(BaseFrame.instance);

				      if(PlayerCommandMenu.instance != null)
				      {
					      for(JMenuItem item : PlayerCommandMenu.menuItems)
					      {
					    	  item.setForeground(UIManager.getColor("List.foreground"));
					      }
				      }
				} catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
    	menu.add(menuItem);

    	menuItem = new JMenuItem("Both text and icon",
    	                         new ImageIcon("images/middle.gif"));
    	menuItem.setMnemonic(KeyEvent.VK_B);
    	menu.add(menuItem);

    	menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
    	menuItem.setMnemonic(KeyEvent.VK_D);
    	menu.add(menuItem);

    	//a group of radio button menu items
    	menu.addSeparator();
    	ButtonGroup group = new ButtonGroup();
    	rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
    	rbMenuItem.setSelected(true);
    	rbMenuItem.setMnemonic(KeyEvent.VK_R);
    	group.add(rbMenuItem);
    	menu.add(rbMenuItem);

    	rbMenuItem = new JRadioButtonMenuItem("Another one");
    	rbMenuItem.setMnemonic(KeyEvent.VK_O);
    	group.add(rbMenuItem);
    	menu.add(rbMenuItem);

    	//a group of check box menu items
    	menu.addSeparator();
    	cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
    	cbMenuItem.setMnemonic(KeyEvent.VK_C);
    	menu.add(cbMenuItem);

    	cbMenuItem = new JCheckBoxMenuItem("Another one");
    	cbMenuItem.setMnemonic(KeyEvent.VK_H);
    	menu.add(cbMenuItem);

    	//a submenu
    	menu.addSeparator();
    	submenu = new JMenu("A submenu");
    	submenu.setMnemonic(KeyEvent.VK_S);

    	menuItem = new JMenuItem("An item in the submenu");
    	menuItem.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_2, ActionEvent.ALT_MASK));
    	submenu.add(menuItem);

    	menuItem = new JMenuItem("Another item");
    	submenu.add(menuItem);
    	menu.add(submenu);

    	//Build second menu in the menu bar.
    	menu = new JMenu("Another Menu");
    	menu.setMnemonic(KeyEvent.VK_N);
    	menu.getAccessibleContext().setAccessibleDescription(
    	        "This menu does nothing");
    	menuBar.add(menu);
    	this.setJMenuBar(menuBar);
	}
	private void Initialize(String title, int width, int height)
	{
		setTitle(title);
		ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/SGSLogo.png"));
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new BaseFrameWindowListener());
		this.setSize(new Dimension(width, height));
		populateContentPane();
	}
    public static ConsolePane getConsole(){
    	return console;
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

		tabs.addTab("Summary"/*,new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("Resources/Images/StartBtn.png")))*/, new SummaryTab(BaseFrame.console));
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
	        Image image=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/Images/SGSLogo.png"));
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
	        trayIcon=new TrayIcon(image, "MCMA", popup);
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
