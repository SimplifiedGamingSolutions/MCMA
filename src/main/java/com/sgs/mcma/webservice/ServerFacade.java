package com.sgs.mcma.webservice;

import com.sgs.mcma.client.PlayerJoined_Params;
import com.sgs.mcma.client.PlayerLeft_Params;
import com.sgs.mcma.view.summary.SummaryTab;

public class ServerFacade
{

	public static boolean updatePlayerList(PlayerJoined_Params playerContainer)
	{
		SummaryTab.Instance();
		SummaryTab.playerListModel.addElement(playerContainer.getName());
		return true;
	}

	public static boolean updatePlayerList(PlayerLeft_Params playerContainer)
	{
		SummaryTab.Instance();
		SummaryTab.playerListModel.removeElement(playerContainer.getName());
		return true;
	}
}
