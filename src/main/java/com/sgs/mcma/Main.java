package com.sgs.mcma;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.webservice.Server;

public class Main
{

	public static void main(String[] args)
	{
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{

		    	Toolkit.getDefaultToolkit().setDynamicLayout(true);
				  System.setProperty("sun.awt.noerasebackground", "true");
				  JFrame.setDefaultLookAndFeelDecorated(true);
				  JDialog.setDefaultLookAndFeelDecorated(true);
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

}
