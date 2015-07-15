package com.sgs.mcma.webservice;

import com.sgs.mcma.view.summary.SummaryTab;

public class ServerFacade
{

	public static boolean addPlayer(String player)
	{
		SummaryTab.Instance();
		SummaryTab.playerListModel.addElement(player);
		return true;
	}

	public static boolean removePlayer(String player)
	{
		SummaryTab.Instance();
		SummaryTab.playerListModel.removeElement(player);
		return true;
	}
}
