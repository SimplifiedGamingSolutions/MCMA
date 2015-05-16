package com.sgs.mcma.view.summary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sgs.mcma.model.Achievements;
import com.sgs.mcma.view.BaseFrame;

@SuppressWarnings("serial")
public class ItemSelectionDialog extends JDialog {
	private static ItemSelectionDialog instance;
	private String result;
	private JPanel panel;
	public ItemSelectionDialog(String title) {
		instance = this;
		setTitle(title);
		this.setPreferredSize(new Dimension(BaseFrame.instance.getWidth()/4, BaseFrame.instance.getHeight()/4));
		JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel = new JPanel(new GridLayout(0, 4));
		pane.setViewportView(panel);
		add(pane);
		this.pack();
		this.setVisible(true);
	}

	public void addItem(String command, ImageIcon image, String description){
		panel.add(new Item(command, image, description));
	}
	
	private class Item extends JPanel{
		String command;
		ImageIcon image;
		String description;
		
		public Item(final String command, ImageIcon image, String description) {
			this.command = command;
			this.image = image;
			this.description = description;
			setLayout(new BorderLayout());
			JButton button = new JButton(image);
			button.setSize(new Dimension(32,32));
			button.setMargin(new Insets(-2, -2, -2, -2));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					instance.setResult(command);
					instance.dispose();
				}
			});
			add(button, BorderLayout.CENTER);
			add(new JLabel(this.description), BorderLayout.SOUTH);
		}
	}

	void setResult(String command) {
		this.result = command;
	}
	
	public String getResult(){
		return result;
	}
	
	public void addAchievements(){
/*		ImageIcon[] achievementIcons = {achievements.getIcon("openInventory"),achievements.getIcon("mineWood"),achievements.getIcon("buildWorkBench"),
		achievements.getIcon("buildPickaxe"),achievements.getIcon("buildFurnace"),achievements.getIcon("acquireIron"),achievements.getIcon("buildHoe"),
		achievements.getIcon("makeBread"),achievements.getIcon("bakeCake"),achievements.getIcon("buildBetterPickaxe"),achievements.getIcon("cookFish"),
		achievements.getIcon("onARail"),achievements.getIcon("buildSword"),achievements.getIcon("killEnemy"),achievements.getIcon("killCow"),
		achievements.getIcon("flyPig"),achievements.getIcon("snipeSkeleton"),achievements.getIcon("diamonds"),achievements.getIcon("portal"),achievements.getIcon("ghast"),
		achievements.getIcon("blazeRod"),achievements.getIcon("potion"),achievements.getIcon("theEnd"),achievements.getIcon("theEnd2"),achievements.getIcon("enchantments"),
		achievements.getIcon("overkill"),achievements.getIcon("bookcase"),achievements.getIcon("exploreAllBiomes"),achievements.getIcon("spawnWither"),
		achievements.getIcon("killWither"),achievements.getIcon("fullBeacon"),achievements.getIcon("breedCow"),achievements.getIcon("diamondsToYou"),
		achievements.getIcon("overpowered") };*/
		Achievements achievements = new Achievements();
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
		this.addItem("openInventory", achievements.getIcon("openInventory"), "Open Inventory");
	}
}
