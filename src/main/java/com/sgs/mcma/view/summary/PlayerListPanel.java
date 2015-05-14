package com.sgs.mcma.view.summary;

import java.awt.BorderLayout;
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

import com.sgs.mcma.view.console.ConsolePane;

@SuppressWarnings("serial")
public class PlayerListPanel extends JPanel{
	public JList<String> playerList = new JList<String>();
	public ConsolePane console;
	DefaultListModel<String> playerListModel;
	public static PlayerListPanel instance;
	public PlayerCommandMenu popup;

	public PlayerListPanel(ConsolePane c, DefaultListModel<String> plm) {
		instance = this;
		console = c;
		popup = new PlayerCommandMenu();
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
		//this.addFocusListener(new myFocusListener());
		playerList.addFocusListener(new myFocusListener());
		//popup.addFocusListener(new myFocusListener());
		
	}

	private class PlayerListMouseListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			super.mouseReleased(e);
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				int playerIndex = playerList.locationToIndex(e.getPoint());
				if(playerIndex != -1 && playerList.getCellBounds(playerIndex, playerIndex).contains(e.getPoint())){
					playerList.setSelectedIndex(playerIndex);
					popup.setLocation(e.getLocationOnScreen());
					popup.setVisible(true);
					playerList.requestFocus();
				}
				else{
					popup.setVisible(false);
					playerList.clearSelection();
				}
			}else if(e.getButton() == MouseEvent.BUTTON1){
				int playerIndex = playerList.locationToIndex(e.getPoint());
				if(playerIndex != -1 && playerList.getCellBounds(playerIndex,  playerIndex).contains(e.getPoint())){
					playerList.setSelectedIndex(playerIndex);
				}
				else{
					playerList.clearSelection();
				}
				popup.setVisible(false);
			}
			else{
				popup.setVisible(false);
				playerList.clearSelection();
			}
		}
	}
	private class myFocusListener implements FocusListener{

		public void focusGained(FocusEvent e) {
			System.out.println("received focus"+e.getSource());
		}

		public void focusLost(FocusEvent e) {
			System.out.println("lost focus"+e.getSource());
			popup.setVisible(false);
		}
		
	}
	
}