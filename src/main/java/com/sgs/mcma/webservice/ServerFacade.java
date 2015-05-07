package com.sgs.mcma.webservice;

import com.sgs.mcma.client.PlayerJoined_Params;
import com.sgs.mcma.client.PlayerLeft_Params;
import com.sgs.mcma.gui.view.BaseFrame;

public class ServerFacade {

	public static boolean updatePlayerList(PlayerJoined_Params playerContainer){
		BaseFrame.Instance().playerListModel.addElement(playerContainer.getName());
		return true;
	}
	
	public static boolean updatePlayerList(PlayerLeft_Params playerContainer){
		BaseFrame.Instance().playerListModel.removeElement(playerContainer.getName());
		return true;
	}
}
