package com.sgs.mcma.controller.summary;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.sgs.mcma.view.summary.PlayerCommandMenu;

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

	public static void rightClick(JList<String> playerList,
			PlayerCommandMenu popup, MouseEvent e)
	{
		int playerIndex = playerList.locationToIndex(e.getPoint());
		if (playerIndex != -1
				&& playerList.getCellBounds(playerIndex, playerIndex).contains(
						e.getPoint()))
		{
			playerList.setSelectedIndex(playerIndex);
			popup.setLocation(e.getLocationOnScreen());
			popup.setVisible(true);
			playerList.requestFocus();
		} else
		{
			popup.setVisible(false);
			playerList.clearSelection();
		}
	}

	public static void playerListLostFocus(PlayerCommandMenu popup)
	{
		popup.setVisible(false);
	}
}
