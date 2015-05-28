package com.sgs.mcma.controller.settings;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;

import com.google.common.io.Files;
import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.view.settings.ServerSettingsTab;
import com.sgs.mcma.view.summary.SummaryTab;

public class ServerSettingsController
{
	public static void saveButtonPressed(ServerSettingsTab tab)
	{
		tab.writeTextAreaToFile();
	}

	public static void selectedNodeChanged(File file, boolean isDirectory)
	{
		if (!isDirectory)
		{
			ServerSettingsTab.Instance().writeFileToTextArea(file);
		}
	}

	public static void activateMod(String selectedValue, DefaultListModel<String> inactive, DefaultListModel<String> active)
	{
		if(!BaseFrame.getConsole().isRunning())
			return;
		try
		{
			Files.move(new File("Server\\mods\\"+selectedValue+".disabled"), new File("Server\\mods\\"+selectedValue));
			populateInactiveModsList(inactive);
			populateActiveModsList(active);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void deactivateMod(String selectedValue, DefaultListModel<String> inactive, DefaultListModel<String> active)
	{
		if(!BaseFrame.getConsole().isRunning())
			return;
		try
		{
			Files.move(new File("Server\\mods\\"+selectedValue), new File("Server\\mods\\"+selectedValue+".disabled"));
			populateInactiveModsList(inactive);
			populateActiveModsList(active);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addExternalMod(DefaultListModel<String> inactive, DefaultListModel<String> active)
	{
		File mods = new File("Server\\mods\\");
		if(BaseFrame.getConsole().isRunning() || !mods.exists())
			return;
		JFileChooser chooser = new JFileChooser(mods);
		chooser.showOpenDialog(ServerSettingsTab.Instance());
		try
		{
			File file = chooser.getSelectedFile();
			Files.copy(file, new File("Server\\mods\\"+file.getName()));
			populateInactiveModsList(inactive);
			populateActiveModsList(active);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void populateInactiveModsList(DefaultListModel<String> inactiveModsList)
	{
		File mods = new File("Server\\mods\\");
		if(!mods.exists())
			return;
		File[] files = mods.listFiles(new FilenameFilter()
		{
			
			public boolean accept(File dir, String name)
			{
				if(name.endsWith(".disabled"))
					return true;
				return false;
			}
		});
		inactiveModsList.clear();
		for(File file : files){
			inactiveModsList.addElement(file.getName().replace(".disabled", ""));
		}
	}

	public static void populateActiveModsList(DefaultListModel<String> activeModsList)
	{
		File mods = new File("Server\\mods\\");
		if(!mods.exists())
			return;
		File[] files = mods.listFiles(new FilenameFilter()
		{
			
			public boolean accept(File dir, String name)
			{
				if(!name.endsWith(".disabled"))
					return true;
				return false;
			}
		});
		activeModsList.clear();
		for(File file : files){
			activeModsList.addElement(file.getName());
		}
	}
}
