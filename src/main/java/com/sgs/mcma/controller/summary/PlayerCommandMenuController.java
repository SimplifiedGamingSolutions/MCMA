package com.sgs.mcma.controller.summary;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.view.console.ConsolePane;
import com.sgs.mcma.view.summary.PlayerCommandMenu;

public class PlayerCommandMenuController
{

	public static void commandPressed(JList<String> playerList, ConsolePane console, String command, String title, PlayerCommandMenu menu)
	{
		menu.setVisible(false);
		if (command.equals("achievement"))
		{
			String[] options =
			{ "Give Player", "Take Player" };
			ImageIcon img = new ImageIcon("Resources\\give.png");
			JOptionPane.showOptionDialog(
					BaseFrame.instance,
					"Give or Take Achievement from "
							+ playerList.getSelectedValue(),
					title.toUpperCase(), JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, img, options, options[0]);
		} else
		{
			String player = playerList.getSelectedValue();
			console.sendCommand(command.replace("player", player));
			menu.setVisible(false);
		}
	}

}
