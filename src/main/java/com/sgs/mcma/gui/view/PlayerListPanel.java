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
	private JList<String> playerList = new JList<String>();
	public static JPopupMenu popup;
	ConsolePane console;
	DefaultListModel<String> playerListModel;
	private static PlayerListPanel instance;

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
	
	private class PlayerCommandMenu extends JPopupMenu{
		PlayerCommandMenu menu = this;
		
		public PlayerCommandMenu()
		{
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
		public void mouseEntered(MouseEvent event){
			JMenuItem jmi = (JMenuItem) event.getSource();
			jmi.setBackground(UIManager.getColor    ("MenuItem.selectionBackground"));
		    jmi.setForeground(UIManager.getColor("MenuItem.selectionForeground"));
		}
		
		public void addCommand(String title, String command){
			JMenuItem temp = new JMenuItem(title);
			this.add(temp);
			temp.addActionListener(new commandActionListener(command, title));
		
		}
		
		private class commandActionListener implements ActionListener{
			private String command;
			private String title;
			
			ImageIcon img = new ImageIcon("Resources\\give.png");

			public commandActionListener(String command, String title){
				this.command = command;
				this.title = title;
				menu.setVisible(false);
				
			}
			public void actionPerformed(ActionEvent e) {
				if(command.equals("achievement")){
					String [] options = {"Give Player","Take Player"};
					JOptionPane optionPane = new JOptionPane();
					//optionPane.setLocation(x,y);
					//BaseFrame.instance.getLocation().getX() + BaseFrame.instance.getWidth()/2;
					
					menu.setVisible(false);
					optionPane.showOptionDialog(BaseFrame.instance, "Give or Take Achievement from "+playerList.getSelectedValue(), title.toUpperCase(), JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,img, options,options[0]);
					
					
				}else{			
					String player = playerList.getSelectedValue();
					console.sendCommand(command.replace("player", player));
					menu.setVisible(false);
				}
				
			}
		}
		

		
	}
	
}