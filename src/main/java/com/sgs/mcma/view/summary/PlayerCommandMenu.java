package com.sgs.mcma.view.summary;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import com.sgs.mcma.controller.summary.PlayerCommandMenuController;

@SuppressWarnings("serial")
public class PlayerCommandMenu extends JPopupMenu
{
	public static PlayerCommandMenu instance;
	public static ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();

	public PlayerCommandMenu()
	{
		PlayerCommandMenu.instance = this;
		addCommand("achievement", "achievement");
		addCommand("ban", "ban [player]");
		addCommand("ban-ip", "ban-ip [player]");
		addCommand("kill", "kill [player]");
		addCommand("clear", "clear [player]");
		addCommand("deop", "deop [player]");
		addCommand("difficulty", "difficulty");
		addCommand("effect", "todo");
		addCommand("enchant", "enchant [player]");
		addCommand("gamemode", "gamemode");
		addCommand("give", "todo");
		addCommand("kick", "kick [player]");
		addCommand("op", "op [player]");
		addCommand("pardon", "todo");
		addCommand("particle", "todo");
		addCommand("playsound", "todo");
		addCommand("replaceitem", "todo");
		addCommand("scoreboard", "todo");
		addCommand("setidletimeout", "todo");
		addCommand("spreadplayers", "todo");
		addCommand("stats", "todo");
		addCommand("tell", "todo");
		addCommand("tellraw", "todo");
		addCommand("testfor", "todo");
		addCommand("title", "todo");
		addCommand("tp", "todo");
		addCommand("trigger", "todo");
		addCommand("whitelist", "todo");
		addCommand("xp", "todo");

	}

	public void mouseEntered(MouseEvent event)
	{
		JMenuItem jmi = (JMenuItem) event.getSource();
	}

	public void addCommand(String title, String command)
	{
		JMenuItem temp = new JMenuItem(title);
		this.add(temp);
		menuItems.add(temp);
		temp.addActionListener(new commandActionListener(command, title));

	}

	private class commandActionListener implements ActionListener
	{
		private String command;
		private String title;

		public commandActionListener(String command, String title)
		{
			this.command = command;
			this.title = title;
		}

		public void actionPerformed(ActionEvent e)
		{
			PlayerCommandMenuController.commandPressed(PlayerListPanel.instance.playerList, PlayerListPanel.instance.console, command, title, PlayerCommandMenu.instance);
		}
	}
}
