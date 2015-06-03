package com.sgs.mcma.controller.summary;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
	
	public static String displayIP()
	{
		String IPAddress = "";
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.getip.com/").get();
			IPAddress = doc.select("div.index").select("em").first().text();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return "Tell your friends to enter: " + IPAddress + " to join your server!";
	}
}
