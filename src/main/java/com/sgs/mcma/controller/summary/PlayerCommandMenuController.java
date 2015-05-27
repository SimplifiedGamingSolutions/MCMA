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
		String result;
		String player = playerList.getSelectedValue();
		menu.setVisible(false);
		if (command.equals("achievement"))
		{
			//give or take
			String[] options ={	"Give Player", "Take Player"};
			ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/give.png"));
			int n = JOptionPane.showOptionDialog(BaseFrame.instance, "Give or Take Achievement from " + playerList.getSelectedValue(), title.toUpperCase(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, img, options, options[0]);
			
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
			
		} else//another player command
		{
			
			console.sendCommand(command.replace("[player]", player));
			menu.setVisible(false);
		}
		//after all the commands
		
		
		
		
	}

}
