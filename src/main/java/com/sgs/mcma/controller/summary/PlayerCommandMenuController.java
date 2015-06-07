package com.sgs.mcma.controller.summary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.sgs.mcma.model.Achievements;
import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.view.console.ConsolePane;
import com.sgs.mcma.view.summary.ItemSelectionDialog;
import com.sgs.mcma.view.summary.PlayerCommandMenu;
import com.sun.prism.Graphics;

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
					if(result != "null")
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
					if(result != "null")
						console.sendCommand("achievement take achievement."+result+" "+player);
				}
			}
			
		}else if(command.equals("ban [player]")){
			ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/banned.jpg"));
			Object[] options1 = { "Ban Player", "Cancel"};

			  
			     JPanel panel = new JPanel();
			     panel.setLocation(BaseFrame.instance.getX(), BaseFrame.instance.getY());
			     
			     panel.setBorder(BorderFactory.createTitledBorder("Ban player "+playerList.getSelectedValue()));
			     panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			     panel.add(Box.createVerticalStrut(10));
			     panel.add(new JLabel("Optional: Enter reason for ban"));
			     panel.add(Box.createVerticalStrut(2));
			     JTextField textField = new JTextField(10);
			     panel.add(textField);
			     panel.add(Box.createVerticalStrut(10));
			
			     int response = JOptionPane.showOptionDialog(BaseFrame.instance, panel, "Ban player "+playerList.getSelectedValue(),
			             JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			             img, options1, null);
			     
			     if (response == JOptionPane.YES_OPTION){
			         JOptionPane.showMessageDialog(null, "Player "+playerList.getSelectedValue()+ " was banned.");
			         console.sendCommand("ban "+playerList.getSelectedValue()+ " "+textField.getText());
			     }
			 

		}else if(command.equals("clear [player]")){
			ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/clear.png"));
			Object[] options1 = { "Clear Items", "Cancel"};

			  
			     JPanel panel = new JPanel();
			     panel.setLocation(BaseFrame.instance.getX(), BaseFrame.instance.getY());
			     
			     panel.setBorder(BorderFactory.createTitledBorder("Clear player "+playerList.getSelectedValue() +" items."));
			     panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			     panel.add(Box.createVerticalStrut(10));
			     panel.add(new JLabel("Enter item to clear or leave blank to clear ALL items"));
			     panel.add(Box.createVerticalStrut(2));
			     panel.add(new JLabel("e.g diamond, stone, goldlen_axe, etc"));
			     panel.add(Box.createVerticalStrut(2));
			     JTextField textField = new JTextField(4);
			     panel.add(textField);
			     panel.add(Box.createVerticalStrut(10));
			
			     int response = JOptionPane.showOptionDialog(BaseFrame.instance, panel, "Clear player "+playerList.getSelectedValue() +" items.",
			             JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			             img, options1, null);
			     
			     if (response == JOptionPane.YES_OPTION){
			         //JOptionPane.showMessageDialog(null, "Player "+playerList.getSelectedValue()+ " items.");
			         console.sendCommand("clear "+playerList.getSelectedValue()+ " "+textField.getText());
			     }

		}else if(command.equals("difficulty"))
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
		}else if(command.equals("effect")){
			ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/effect.png"));
			Object[] options1 = { "Set Effect"," Clear All Effects", "Cancel"};

			  
			     JPanel panel = new JPanel();
			     
			     
			     panel.setBorder(BorderFactory.createTitledBorder("Set Effects for player "+playerList.getSelectedValue()));
			     panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			     panel.add(Box.createVerticalStrut(10));
			     panel.add(new JLabel("Select Effect"));
			     panel.add(Box.createVerticalStrut(2));
			     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
	                model.addElement("Speed");model.addElement("Slowness");model.addElement("Haste");model.addElement("Mining Fatigue");model.addElement("Strength");model.addElement("Instant Health");model.addElement("Instant Damage");model.addElement("Jump Boost");model.addElement("Nausea");model.addElement("Regeneration");model.addElement("Resistance");model.addElement("Fire Resistance");model.addElement("Water Breathing");model.addElement("Invisibility");model.addElement("Blindness");model.addElement("Night vision");model.addElement("Hunger");model.addElement("Weakness");model.addElement("Poison");model.addElement("Health Boost");model.addElement("Absorption");model.addElement("Saturation");
	                JComboBox<String> comboBox = new JComboBox<String>(model);
			     panel.add(comboBox);
			     panel.add(Box.createVerticalStrut(2));
			     panel.add(new JLabel("Enter duration in seconds"));
			     panel.add(Box.createVerticalStrut(2));
			     JTextField textField = new JTextField(4);
			     panel.add(textField);
			     panel.add(Box.createVerticalStrut(2));
			     panel.add(new JLabel("Enter Amplifier up to 255"));
			     panel.add(Box.createVerticalStrut(2));
			     JTextField textField1 = new JTextField(4);
			     panel.add(textField1);
			     panel.add(Box.createVerticalStrut(10));
			
			     int response = JOptionPane.showOptionDialog(BaseFrame.instance, panel, "Set Effects for player "+playerList.getSelectedValue(),
			             JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			             img, options1, null);
			     
			     if (response == JOptionPane.YES_OPTION){
			    	 int selection = comboBox.getSelectedIndex()+1;
			    	 console.sendCommand("effect "+playerList.getSelectedValue()+ " "+selection+ " "+textField.getText()+ " "+textField1.getText());
			     }else if(response == JOptionPane.NO_OPTION){
			    	 console.sendCommand("effect "+playerList.getSelectedValue()+" clear");
			     }
	
		}else if(command.equals("enchant [player]")){
			
			ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/Achievements/effect.png"));
			Object[] options1 = { "Enchant item held", "Cancel"};

			  
			     JPanel panel = new JPanel();
			     
			     
			     panel.setBorder(BorderFactory.createTitledBorder("Enchant player "+playerList.getSelectedValue()+" current item held"));
			     panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			     panel.add(Box.createVerticalStrut(10));
			     panel.add(new JLabel("Select Enchantment"));
			     panel.add(Box.createVerticalStrut(2));
			     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
	                model.addElement("Protection");model.addElement("Fire Protection");model.addElement("Feather Falling");model.addElement("Blast Protection");model.addElement("Projectile Protection");model.addElement("Respiration");model.addElement("Aqua Affinity");model.addElement("Thorns");model.addElement("Depth Strider");model.addElement("Sharpness");model.addElement("Smite");model.addElement("Bane of Arthropods");model.addElement("Knockback");model.addElement("Fire Aspect");model.addElement("Looting");model.addElement("Efficiency");model.addElement("Silk Touch");model.addElement("Unbreaking");model.addElement("Fortune");model.addElement("Power");model.addElement("Punch");model.addElement("Flame");model.addElement("Infinity");model.addElement("Luck of the Sea");model.addElement("Lure");
	                JComboBox<String> comboBox = new JComboBox<String>(model);
			     panel.add(comboBox);
			     panel.add(Box.createVerticalStrut(2));
			     panel.add(new JLabel("Enter Enchantment Level (optional)"));
			     panel.add(Box.createVerticalStrut(2));
			     JTextField textField = new JTextField(4);
			     panel.add(textField);
			     panel.add(Box.createVerticalStrut(10));
			
			     int response = JOptionPane.showOptionDialog(BaseFrame.instance, panel, "Enchant player "+playerList.getSelectedValue()+" current item held",
			             JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
			             img, options1, null);
			     
			     if (response == JOptionPane.YES_OPTION){
			    	 String selection =comboBox.getSelectedItem().toString().toLowerCase();			    	 
			    	 console.sendCommand("enchant "+playerList.getSelectedValue()+ " "+selection.replace(' ', '_')+" "+textField.getText());
			     }
			
		}else if(command.equals("give")){
			//left of here, trying to get a html page to load inside a panel using jeditorpane, could help us style easier espeically with so many items and blocks to give
			
			
			
			
			
			
			
			 JFrame f = new JFrame();
			 
			 
			 JPanel panel = new JPanel();

			
			JEditorPane editorPane = new JEditorPane();
			editorPane.setEditable(false);
			java.net.URL helpURL = ClassLoader.getSystemResource("Resources/html/temp.html");
			
			if (helpURL != null) {
			    try {
			        editorPane.setPage("http://hydra-media.cursecdn.com/minecraft.gamepedia.com/d/d1/DataValues.svg");
			    } catch (IOException e) {
			        System.out.println("Attempted to read a bad URL: " + helpURL);
			    }
			} else {
			    System.out.println("Couldn't find file: TextSamplerDemoHelp.html");
			}

			panel.add(editorPane);
			f.add(panel);
			f.pack();
			f.setVisible(true);
			
			
			
		}
		else//another player command
		{
			
			console.sendCommand(command.replace("[player]", player));
			menu.setVisible(false);
		}
		//after all the commands
	}

}
