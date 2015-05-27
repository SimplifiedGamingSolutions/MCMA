package com.sgs.mcma.controller.summary;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import com.sgs.mcma.model.Achievements;
import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.view.console.ConsolePane;
import com.sgs.mcma.view.summary.ItemSelectionDialog;
import com.sgs.mcma.view.summary.PlayerCommandMenu;

public class PlayerCommandMenuController
{	
	static Achievements achievements = new Achievements();

	public static void commandPressed(JList<String> playerList, ConsolePane console, String command, String title, PlayerCommandMenu menu)
	{
		if(!console.isRunning())
			return;
		String result;
		String player = playerList.getSelectedValue();
		menu.setVisible(false);
		if (command.equals("achievement"))
		{
			//give or take
			String[] options ={	"Give Player", "Take Player"};
			ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/give.png"));
			int n = JOptionPane.showOptionDialog(BaseFrame.instance, "Give or Take Achievement from " + playerList.getSelectedValue(), title.toUpperCase(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, img, options, options[0]);
			
			if(n != -1)
			{
				//if they say give
				if(n==JOptionPane.YES_NO_OPTION)
				{	
					String instructions = "Choose an Achievement below to give to "+playerList.getSelectedValue();
					
					ItemSelectionDialog dialog = new ItemSelectionDialog("Achievements",instructions);
					dialog.addAchievements();
					dialog.setLocationRelativeTo(BaseFrame.instance);
					dialog.setModal(true);
					dialog.setVisible(true);
					
					result = dialog.getResult();
					console.sendCommand("achievement give achievement."+result+" "+player);
				}
				//if they say take
				else{
					String instructions = "Choose an Achievement below to take from "+playerList.getSelectedValue();
					
					ItemSelectionDialog dialog = new ItemSelectionDialog("Achievements",instructions);
					dialog.addAchievements();
					dialog.setLocationRelativeTo(BaseFrame.instance);
					dialog.setModal(true);
					dialog.setVisible(true);
					
					result = dialog.getResult();
					console.sendCommand("achievement take achievement."+result+" "+player);
				}
			}
			
		} else if(command.equals("difficulty"))
		{
			String[] options ={	"Peaceful", "Easy", "Normal", "Hard"};
			//ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/give.png")); This is if you wan to add an image					 goes here
			int n = JOptionPane.showOptionDialog(BaseFrame.instance, "Select Difficulty", title.toUpperCase(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(n != -1)
				console.sendCommand("difficulty " + n);			
		}else if(command.equals("gamemode"))
		{
			String[] options ={	"Survival", "Creative", "Adventure", "Spectator"};
			//ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/give.png")); This is if you wan to add an image					 goes here
			int n = JOptionPane.showOptionDialog(BaseFrame.instance, "Select Gamemode for " + playerList.getSelectedValue(), title.toUpperCase(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(n != -1)
				console.sendCommand("gamemode " + n + " " + player);			
		}
		else//another player command
		{
			
			console.sendCommand(command.replace("[player]", player));
			menu.setVisible(false);
		}
		//after all the commands
	}

}
