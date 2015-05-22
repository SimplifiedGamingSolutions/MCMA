package com.sgs.mcma.model;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Achievements {
	Map<String, String> achievements = new HashMap<String, String>();
	
	public Achievements() {
		achievements.put("fullBeacon", "Resources/Images/Achievements/Grid_Beacon.png");
		achievements.put("blazeRod", "Resources/Images/Achievements/Grid_Blaze_rod.png");
		achievements.put("killEnemy", "Resources/Images/Achievements/Grid_Bone.png");
		achievements.put("openInventory", "Resources/Images/Achievements/Grid_Book.png");
		achievements.put("bookcase", "Resources/Images/Achievements/Grid_Bookshelf.png");
		achievements.put("snipeSkeleton", "Resources/Images/Achievements/Grid_Bow.png");
		achievements.put("makeBread", "Resources/Images/Achievements/Grid_Bread.png");
		achievements.put("bakeCake", "Resources/Images/Achievements/Grid_Cake.png");
		achievements.put("cookFish", "Resources/Images/Achievements/Grid_Cooked_Fish.png");
		achievements.put("buildWorkBench", "Resources/Images/Achievements/Grid_Crafting_Table.png");
		achievements.put("exploreAllBiomes", "Resources/Images/Achievements/Grid_Diamond_Boots.png");
		achievements.put("diamonds", "Resources/Images/Achievements/Grid_Diamond_Ore.png");
		achievements.put("diamondsToYou", "Resources/Images/Achievements/Grid_Diamond.png");
		achievements.put("overkill", "Resources/Images/Achievements/Grid_Diamond_Sword.png");
		achievements.put("theEnd2", "Resources/Images/Achievements/Grid_Dragon_Egg.png");
		achievements.put("enchantments", "Resources/Images/Achievements/Grid_Enchantment_Table.png");
		achievements.put("theEnd", "Resources/Images/Achievements/Grid_Eye_of_Ender.png");
		achievements.put("buildFurnace", "Resources/Images/Achievements/Grid_Furnace.png");
		achievements.put("ghast", "Resources/Images/Achievements/Grid_Ghast_Tear.png");
		achievements.put("overpowered", "Resources/Images/Achievements/Grid_Golden_Apple.png");
		achievements.put("acquireIron", "Resources/Images/Achievements/Grid_Iron_Ingot.png");
		achievements.put("killCow", "Resources/Images/Achievements/Grid_Leather.png");
		achievements.put("potion", "Resources/Images/Achievements/Grid_Mundane_Potion.png");
		achievements.put("killWither", "Resources/Images/Achievements/Grid_Nether_Star.png");
		achievements.put("mineWood", "Resources/Images/Achievements/Grid_Oak_Wood.png");
		achievements.put("portal", "Resources/Images/Achievements/Grid_Obsidian.png");
		achievements.put("onARail", "Resources/Images/Achievements/Grid_Rail.png");
		achievements.put("flyPig", "Resources/Images/Achievements/Grid_Saddle.png");
		achievements.put("buildBetterPickaxe", "Resources/Images/Achievements/Grid_Stone_Pickaxe.png");
		achievements.put("breedCow","Resources/Images/Achievements/Grid_Wheat.png");
		achievements.put("spawnWither", "Resources/Images/Achievements/Grid_Wither_Skeleton_Skull.png");
		achievements.put("buildHoe", "Resources/Images/Achievements/Grid_Wooden_Hoe.png");
		achievements.put("buildPickaxe", "Resources/Images/Achievements/Grid_Wooden_Pickaxe.png");
		achievements.put("buildSword", "Resources/Images/Achievements/Grid_Wooden_Sword.png");
	}

	public ImageIcon getIcon(String achievement){
		URL url = ClassLoader.getSystemResource(achievements.get(achievement));
		if(url != null)
			return new ImageIcon(url);
		return new ImageIcon();
	}
}
