package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import com.sgs.mcma.gui.view.console.ConsolePane;

@SuppressWarnings("serial")
public class PlayerListPanel extends JPanel{
	private JList<String> playerList = new JList<String>();
	final JPopupMenu popup = new PlayerCommandMenu();
	ConsolePane console;
	DefaultListModel<String> playerListModel;
		
	public PlayerListPanel(ConsolePane c, DefaultListModel<String> plm) {
		console = c;
		playerListModel = plm;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200,0));
		
		JLabel label = new JLabel("Players Online");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label, BorderLayout.NORTH);
		
		playerList.setModel(playerListModel);
		playerList.setFont(new Font("Arial",Font.BOLD,14));
		playerList.addMouseListener(new PlayerListMouseListener());
		add(new JScrollPane(playerList), BorderLayout.CENTER);
	}

	private class PlayerListMouseListener extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			super.mouseClicked(e);
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				int playerIndex = playerList.locationToIndex(e.getPoint());
				if(playerIndex != -1 && playerList.getCellBounds(playerIndex, playerIndex).contains(e.getPoint())){
					playerList.setSelectedIndex(playerIndex);
					popup.setLocation(e.getLocationOnScreen());
					popup.setVisible(true);
				}
				else{
					popup.setVisible(false);
					playerList.clearSelection();
				}
			}else{
				popup.setVisible(false);
				playerList.clearSelection();
			}
		}
	}
	
	private class PlayerCommandMenu extends JPopupMenu{
		PlayerCommandMenu menu = this;
		
		public PlayerCommandMenu()
		{
			addCommand("kill", "kill player");
			addCommand("ban", "ban player");
			addCommand("ban-ip", "ban-ip player");
		}
		public void addCommand(String title, String command){
			JMenuItem temp = new JMenuItem(title);
			temp.addActionListener(new commandActionListener(command));
			this.add(temp);
		}
		
		private class commandActionListener implements ActionListener{
			private String command;
			public commandActionListener(String command) {
				this.command = command;
			}
			public void actionPerformed(ActionEvent e) {
				String player = playerList.getSelectedValue();
				console.sendCommand(command.replace("player", player));
				menu.setVisible(false);
			}
		}
	}
	
}