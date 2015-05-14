package com.sgs.mcma.view.summary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.view.console.ConsolePane;

@SuppressWarnings("serial")
public class PlayerCommandMenu extends JPopupMenu{
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
		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent e) {
			JList<String> playerList = PlayerListPanel.instance.playerList;
			ConsolePane console = PlayerListPanel.instance.console;
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
