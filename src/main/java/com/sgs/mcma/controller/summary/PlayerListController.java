package com.sgs.mcma.controller.summary;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.sgs.mcma.view.summary.PlayerCommandMenu;
import com.sgs.mcma.view.summary.PlayerListPanel;

public class PlayerListController
{
	public static void leftClick(JList<String> playerList,
			PlayerCommandMenu popup, MouseEvent e)
	{
		int playerIndex = playerList.locationToIndex(e.getPoint());
		if (playerIndex != -1
				&& playerList.getCellBounds(playerIndex, playerIndex).contains(
						e.getPoint()))
		{
			playerList.setSelectedIndex(playerIndex);
		} else
		{
			playerList.clearSelection();
		}
		popup.setVisible(false);
	}

	public static void rightClick(PlayerListPanel panel, MouseEvent e)
	{
		panel.showCommandMenu(e);
	}
	

	public static void playerListLostFocus(PlayerListPanel panel)
	{
		panel.hideCommandMenu();
	}
}
