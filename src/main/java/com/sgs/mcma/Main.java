package com.sgs.mcma;

import java.awt.EventQueue;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.webservice.Server;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

public class Main
{

	public static void main(String[] args)
	{
		try {
            // setup the look and feel properties
            Properties props = new Properties();
            
            props.put("logoString", "my company"); 
            props.put("licenseKey", "INSERT YOUR LICENSE KEY HERE");
            
            props.put("selectionBackgroundColor", "180 240 197"); 
            props.put("menuSelectionBackgroundColor", "180 240 197"); 
            
            props.put("controlColor", "218 254 230");
            props.put("controlColorLight", "218 254 230");
            props.put("controlColorDark", "180 240 197"); 

            props.put("buttonColor", "218 230 254");
            props.put("buttonColorLight", "255 255 255");
            props.put("buttonColorDark", "244 242 232");

            props.put("rolloverColor", "218 254 230"); 
            props.put("rolloverColorLight", "218 254 230"); 
            props.put("rolloverColorDark", "180 240 197"); 

            props.put("windowTitleForegroundColor", "0 0 0");
            props.put("windowTitleBackgroundColor", "180 240 197"); 
            props.put("windowTitleColorLight", "218 254 230"); 
            props.put("windowTitleColorDark", "180 240 197"); 
            props.put("windowBorderColor", "218 254 230");
            
            // set your theme
            SmartLookAndFeel.setCurrentTheme(props);
            // select the Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
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

}
