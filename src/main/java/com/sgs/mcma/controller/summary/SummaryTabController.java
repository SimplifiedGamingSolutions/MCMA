package com.sgs.mcma.controller.summary;

import com.sgs.mcma.view.summary.SummaryTab;

public class SummaryTabController
{
	public static boolean startButtonPressed(SummaryTab tab)
	{
		return tab.startServer();
	}

	public static boolean stopButtonPressed(SummaryTab tab)
	{
		if(tab.stopServer()){
			tab.clearPlayerList();
			return true;
		}
		return false;
	}
}
