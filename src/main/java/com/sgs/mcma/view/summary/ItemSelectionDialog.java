package com.sgs.mcma.view.summary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sgs.mcma.model.Achievements;

@SuppressWarnings("serial")
public class ItemSelectionDialog extends JDialog {
	
	private static ItemSelectionDialog instance;
	private String result;
	private JPanel panel;
	private FlowLayout layout = new FlowLayout();
	private String instructions;
	
	//construct jdialog
	public ItemSelectionDialog(String title, String instructions) {
		//super(BaseFrame.instance,true);
		instance = this;		
		this.instructions=instructions;
		setTitle(title);
		this.setPreferredSize(new Dimension(512, 512));//this is a hardcoded number we can change this to be dynamic.
		this.setSize(new Dimension(512, 512));//this is a hardcoded number we can change this to be dynamic.
		
		run();
		this.pack();
	}

	//add components to jdialog (instruction label and item holder panel)
	private void run() {
		panel = new JPanel();
		panel.setLayout(layout);
		JLabel label = new JLabel(instructions, SwingConstants.CENTER);
		label.setFont(new Font("Serif", Font.BOLD, 15));
		layout.setAlignment(FlowLayout.CENTER);
		this.add(label, BorderLayout.NORTH);
		this.add(panel,BorderLayout.CENTER);
		
	}

	//add a item(item is a jdialog with icon and description) to panel inside of jdialog
	public void addItem(String command, ImageIcon image, String description){
		panel.add(new Item(command, image, description));
	}
	
	//add button and description to panel inside panel inside jdialog
	private class Item extends JPanel{		
		public Item(final String command, ImageIcon image, String description) {
			this.setLayout(new BorderLayout());
			JLabel label = new JLabel(description, SwingConstants.CENTER);
			JButton button = new JButton(image);
			button.setSize(new Dimension(image.getIconWidth(),image.getIconHeight()));
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					instance.setResult(command);
					instance.dispose();

				}
			});
			add(button, BorderLayout.NORTH);
			add(label,BorderLayout.SOUTH);

		}
	}

	void setResult(String result) {
		this.result = result;
	}
	
	public String getResult(){
		return result;
	}
	
	public void addAchievements(){
/*		achievements.getIcon("killWither"),achievements.getIcon("fullBeacon"),achievements.getIcon("breedCow"),achievements.getIcon("diamondsToYou"),
		achievements.getIcon("overpowered") };*/
		Achievements achievements = new Achievements();
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Taking Inventory");
		this.addItem("mineWood", achievements.getIcon("mineWood"), "Getting Wood");
		this.addItem("buildWorkBench", achievements.getIcon("buildWorkBench"), "Benchmarking");
		this.addItem("buildPickaxe", achievements.getIcon("buildPickaxe"), "Time to Mine!");
		this.addItem("buildFurnace", achievements.getIcon("buildFurnace"), "Hot Topic");
		this.addItem("acquireIron", achievements.getIcon("acquireIron"), "Acquire Hardware");
		this.addItem("buildHoe", achievements.getIcon("buildHoe"), "Time to Farm!");
		this.addItem("makeBread", achievements.getIcon("makeBread"), "Bake Bread");
		this.addItem("bakeCake", achievements.getIcon("bakeCake"), "The Lie");
		this.addItem("buildBetterPickaxe", achievements.getIcon("buildBetterPickaxe"), "Getting an Upgrade");
		this.addItem("cookFish", achievements.getIcon("cookFish"), "Delicious Fish");
		this.addItem("onARail", achievements.getIcon("onARail"), "On A Rail");
		this.addItem("buildSword", achievements.getIcon("buildSword"), "Time to Strike!");
		this.addItem("killEnemy", achievements.getIcon("killEnemy"), "Monster Hunter");
		this.addItem("killCow", achievements.getIcon("killCow"), "Cow Tipper");
		this.addItem("flyPig", achievements.getIcon("flyPig"), "When Pigs Fly");
		this.addItem("snipeSkeleton", achievements.getIcon("snipeSkeleton"), "Sniper Duel");
		this.addItem("diamonds", achievements.getIcon("diamonds"), "DIAMONDS!");
		this.addItem("portal", achievements.getIcon("portal"), "We Need to Go Deeper");
		this.addItem("ghast", achievements.getIcon("ghast"), "Return to Sender");
		this.addItem("blazeRod", achievements.getIcon("blazeRod"), "Into Fire");
		this.addItem("potion", achievements.getIcon("potion"), "Local Brewery");
		this.addItem("theEnd", achievements.getIcon("theEnd"), "The End?");
		this.addItem("theEnd2", achievements.getIcon("theEnd2"), "The End.");
		this.addItem("enchantments", achievements.getIcon("enchantments"), "Enchanter");
		this.addItem("overkill", achievements.getIcon("overkill"), "Overkill");
		this.addItem("bookcase", achievements.getIcon("bookcase"), "Librarian");
		this.addItem("exploreAllBiomes", achievements.getIcon("exploreAllBiomes"), "Adventuring Time");
		this.addItem("spawnWither", achievements.getIcon("spawnWither"), "The Beginning?");
		this.addItem("killWither", achievements.getIcon("killWither"), "The Beginning.");
		this.addItem("fullBeacon", achievements.getIcon("fullBeacon"), "Beaconator");
		this.addItem("breedCow", achievements.getIcon("breedCow"), "Repopulation");
		this.addItem("diamondsToYou", achievements.getIcon("diamondsToYou"), "Diamonds to you!");
		this.addItem("overpowered", achievements.getIcon("overpowered"), "Overpowered");
		
	}
}
