package com.sgs.mcma.controller.summary;

import javax.swing.DefaultListModel;

import com.sgs.mcma.view.console.ConsolePane;
import com.sgs.mcma.view.summary.SummaryTab;

public class SummaryTabController 
{
	public static void startButtonPressed(SummaryTab tab)
	{
		tab.startServer();
	}
	public static void stopButtonPressed(SummaryTab tab)
	{
		tab.stopServer();
		tab.clearPlayerList();
	}
}
