package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.sgs.mcma.gui.view.console.ConsolePane;

@SuppressWarnings("serial")
public class PlayerListPanel extends JPanel{
	public JList<String> playerList = new JList<String>();
	public ConsolePane console;
	DefaultListModel<String> playerListModel;
	public static PlayerListPanel instance;
	private PlayerCommandMenu popup;

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
					popup.requestFocus();
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
	
}