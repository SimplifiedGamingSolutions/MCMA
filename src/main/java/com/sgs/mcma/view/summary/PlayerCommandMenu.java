package com.sgs.mcma.view.summary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import com.sgs.mcma.controller.summary.PlayerCommandMenuController;

@SuppressWarnings("serial")
public class PlayerCommandMenu extends JPopupMenu
{
	static PlayerCommandMenu instance;

	public PlayerCommandMenu()
	{
		PlayerCommandMenu.instance = this;
		addCommand("achievement", "achievement");
		addCommand("ban", "ban player");
		addCommand("ban-ip", "ban-ip player");
		addCommand("kill", "kill player");
		addCommand("clear", "todo");
		addCommand("deop", "todo");
		addCommand("difficulty", "todo");
		addCommand("effect", "todo");
		addCommand("enchant", "todo");
		addCommand("gamemode", "todo");
		addCommand("give", "todo");
		addCommand("kick", "todo");
		addCommand("kill", "todo");
		addCommand("op", "todo");
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
		jmi.setBackground(UIManager.getColor("MenuItem.selectionBackground"));
		jmi.setForeground(UIManager.getColor("MenuItem.selectionForeground"));
	}

	public void addCommand(String title, String command)
	{
		JMenuItem temp = new JMenuItem(title);
		this.add(temp);
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
			PlayerCommandMenuController.commandPressed(
					PlayerListPanel.instance.playerList,
					PlayerListPanel.instance.console, command, title,
					PlayerCommandMenu.instance);
		}
	}
}
