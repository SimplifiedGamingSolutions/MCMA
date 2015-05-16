package com.sgs.mcma.model;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Achievements {
	Map<String, String> achievements = new HashMap<String, String>();
	
	public Achievements() {
		achievements.put("fullBeacon", "Resources\\Grid_Beacon.png");
		achievements.put("blazeRod", "Resources\\Grid_Blaze_rod.png");
		achievements.put("killEnemy", "Resources\\Grid_Bone.png");
		achievements.put("openInventory", "Resources\\Grid_Book.png");
		achievements.put("bookcase", "Resources\\Grid_Bookshelf.png");
		achievements.put("snipeSkeleton", "Resources\\Grid_Bow.png");
		achievements.put("makeBread", "Resources\\Grid_Bread.png");
		achievements.put("bakeCake", "Resources\\Grid_Cake.png");
		achievements.put("cookFish", "Resources\\Grid_Cooked_Fish.png");
		achievements.put("buildWorkBench", "Resources\\Grid_Crafting_Table.png");
		achievements.put("exploreAllBiomes", "Resources\\Grid_Diamond_Boots.png");
		achievements.put("diamonds", "Resources\\Grid_Diamond_Ore.png");
		achievements.put("diamondsToYou", "Resources\\Grid_Diamond.png");
		achievements.put("overkill", "Resources\\Grid_Diamond_Sword.png");
		achievements.put("theEnd2", "Resources\\Grid_Dragon_Egg.png");
		achievements.put("enchantments", "Resources\\Grid_Enchantment_Table.png");
		achievements.put("theEnd", "Resources\\Grid_Eye_of_Ender.png");
		achievements.put("buildFurnace", "Resources\\Grid_Furnace.png");
		achievements.put("ghast", "Resources\\Grid_Ghast_Tear.png");
		achievements.put("overpowered", "Resources\\Grid_Golden_Apple.png");
		achievements.put("acquireIron", "Resources\\Grid_Iron_Ingot.png");
		achievements.put("killCow", "Resources\\Grid_Leather.png");
		achievements.put("potion", "Resources\\Grid_Mundane_Potion.png");
		achievements.put("killWither", "Resources\\Grid_Nether_Star.png");
		achievements.put("mineWood", "Resources\\Grid_Oak_Wood.png");
		achievements.put("portal", "Resources\\Grid_Obsidian.png");
		achievements.put("onARail", "Resources\\Grid_Rail.png");
		achievements.put("flyPig", "Resources\\Grid_Saddle.png");
		achievements.put("buildBetterPickaxe", "Resources\\Grid_Stone_Pickaxe.png");
		achievements.put("breedCow","Resources\\Grid_Wheat.png");
		achievements.put("spawnWither", "Resources\\Grid_Wither_Skeleton_Skull.png");
		achievements.put("buildHoe", "Resources\\Grid_Wooden_Hoe.png");
		achievements.put("buildPickaxe", "Resources\\Grid_Wooden_Pickaxe.png");
		achievements.put("buildSword", "Resources\\Grid_Wooden_Sword.png");
	}

	public ImageIcon getIcon(String achievement){
		return new ImageIcon(achievements.get(achievement));
	}
}
