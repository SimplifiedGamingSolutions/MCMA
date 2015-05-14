package com.sgs.mcma.controller.logs;

import com.sgs.mcma.view.logs.ServerLogTab;

public class LogTabController {
	public static void logTabChanged(ServerLogTab tabbedPane){
		if(tabbedPane.getSelectedIndex()==0)
			tabbedPane.updateAllMessages();
		else if(tabbedPane.getSelectedIndex()==1)
			tabbedPane.updateErrorMessages();
		else
			tabbedPane.updateChatMessages();
	}
}
