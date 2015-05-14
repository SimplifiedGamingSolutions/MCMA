package com.sgs.mcma.controller.settings;

import java.io.File;

import com.sgs.mcma.view.settings.ServerSettingsTab;

public class ServerSettingsController 
{
	public static void saveButtonPressed(ServerSettingsTab tab)
	{
		tab.writeTextAreaToFile();
	}
	
	public static void selectedNodeChanged(File file, boolean isDirectory)
	{
        if(!isDirectory)
        {
        	ServerSettingsTab.Instance().writeFileToTextArea(file);
        }
    }
}
