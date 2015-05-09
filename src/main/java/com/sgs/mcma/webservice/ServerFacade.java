package com.sgs.mcma.webservice;

import com.sgs.mcma.client.PlayerJoined_Params;
import com.sgs.mcma.client.PlayerLeft_Params;
import com.sgs.mcma.gui.view.Tab1;

public class ServerFacade {

	public static boolean updatePlayerList(PlayerJoined_Params playerContainer){
		Tab1.Instance();
		Tab1.playerListModel.addElement(playerContainer.getName());
		return true;
	}
	
	public static boolean updatePlayerList(PlayerLeft_Params playerContainer){
		Tab1.Instance();
		Tab1.playerListModel.removeElement(playerContainer.getName());
		return true;
	}
}
