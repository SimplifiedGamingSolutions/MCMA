package com.sgs.mcma.view.summary;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sgs.mcma.controller.summary.PlayerListController;
import com.sgs.mcma.view.console.ConsolePane;

@SuppressWarnings("serial")
public class PlayerListPanel extends JPanel
{
	public JList<String> playerList = new JList<String>();
	public ConsolePane console;
	DefaultListModel<String> playerListModel;
	public static PlayerListPanel instance;
	public PlayerCommandMenu popup;

	public PlayerListPanel(ConsolePane console, DefaultListModel<String> playerListModel)
	{
		PlayerListPanel.instance = this;
		this.console = console;
		popup = new PlayerCommandMenu();
		this.playerListModel = playerListModel;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 0));
		JLabel label = new JLabel("Players Online");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(label, BorderLayout.NORTH);
		playerList.setModel(playerListModel);
		playerList.setFont(new Font("Arial", Font.BOLD, 14));
		playerList.addMouseListener(new PlayerListMouseListener());
		this.add(new JScrollPane(playerList), BorderLayout.CENTER);
		playerList.addFocusListener(new myFocusListener());

	}

	private class PlayerListMouseListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e)
		{
			super.mouseReleased(e);
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				PlayerListController.rightClick(instance, e);
			} else if (e.getButton() == MouseEvent.BUTTON1)
			{
				PlayerListController.leftClick(playerList, popup, e);
			}
		}
	}

	private class myFocusListener implements FocusListener
	{

		public void focusGained(FocusEvent e)
		{

		}

		public void focusLost(FocusEvent e)
		{
			PlayerListController.playerListLostFocus(instance);
		}

	}

	public void hideCommandMenu()
	{
		popup.setVisible(false);
	}

	public void showCommandMenu(MouseEvent e)
	{
		int playerIndex = playerList.locationToIndex(e.getPoint());
		if (playerIndex != -1 && playerList.getCellBounds(playerIndex, playerIndex).contains(e.getPoint()))
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

}