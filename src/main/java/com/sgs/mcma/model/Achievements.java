package com.sgs.mcma.model;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Achievements {
	Map<String, String> achievements = new HashMap<String, String>();
	
	public Achievements() {
		achievements.put("fullBeacon", "Resources\\Images\\AchievementsGrid_Beacon.png");
		achievements.put("blazeRod", "Resources\\Images\\AchievementsGrid_Blaze_rod.png");
		achievements.put("killEnemy", "Resources\\Images\\AchievementsGrid_Bone.png");
		achievements.put("openInventory", "Resources\\Images\\AchievementsGrid_Book.png");
		achievements.put("bookcase", "Resources\\Images\\AchievementsGrid_Bookshelf.png");
		achievements.put("snipeSkeleton", "Resources\\Images\\AchievementsGrid_Bow.png");
		achievements.put("makeBread", "Resources\\Images\\AchievementsGrid_Bread.png");
		achievements.put("bakeCake", "Resources\\Images\\AchievementsGrid_Cake.png");
		achievements.put("cookFish", "Resources\\Images\\AchievementsGrid_Cooked_Fish.png");
		achievements.put("buildWorkBench", "Resources\\Images\\AchievementsGrid_Crafting_Table.png");
		achievements.put("exploreAllBiomes", "Resources\\Images\\AchievementsGrid_Diamond_Boots.png");
		achievements.put("diamonds", "Resources\\Images\\AchievementsGrid_Diamond_Ore.png");
		achievements.put("diamondsToYou", "Resources\\Images\\AchievementsGrid_Diamond.png");
		achievements.put("overkill", "Resources\\Images\\AchievementsGrid_Diamond_Sword.png");
		achievements.put("theEnd2", "Resources\\Images\\AchievementsGrid_Dragon_Egg.png");
		achievements.put("enchantments", "Resources\\Images\\AchievementsGrid_Enchantment_Table.png");
		achievements.put("theEnd", "Resources\\Images\\AchievementsGrid_Eye_of_Ender.png");
		achievements.put("buildFurnace", "Resources\\Images\\AchievementsGrid_Furnace.png");
		achievements.put("ghast", "Resources\\Images\\AchievementsGrid_Ghast_Tear.png");
		achievements.put("overpowered", "Resources\\Images\\AchievementsGrid_Golden_Apple.png");
		achievements.put("acquireIron", "Resources\\Images\\AchievementsGrid_Iron_Ingot.png");
		achievements.put("killCow", "Resources\\Images\\AchievementsGrid_Leather.png");
		achievements.put("potion", "Resources\\Images\\AchievementsGrid_Mundane_Potion.png");
		achievements.put("killWither", "Resources\\Images\\AchievementsGrid_Nether_Star.png");
		achievements.put("mineWood", "Resources\\Images\\AchievementsGrid_Oak_Wood.png");
		achievements.put("portal", "Resources\\Images\\AchievementsGrid_Obsidian.png");
		achievements.put("onARail", "Resources\\Images\\AchievementsGrid_Rail.png");
		achievements.put("flyPig", "Resources\\Images\\AchievementsGrid_Saddle.png");
		achievements.put("buildBetterPickaxe", "Resources\\Images\\AchievementsGrid_Stone_Pickaxe.png");
		achievements.put("breedCow","Resources\\Images\\AchievementsGrid_Wheat.png");
		achievements.put("spawnWither", "Resources\\Images\\AchievementsGrid_Wither_Skeleton_Skull.png");
		achievements.put("buildHoe", "Resources\\Images\\AchievementsGrid_Wooden_Hoe.png");
		achievements.put("buildPickaxe", "Resources\\Images\\AchievementsGrid_Wooden_Pickaxe.png");
		achievements.put("buildSword", "Resources\\Images\\AchievementsGrid_Wooden_Sword.png");
	}

	public ImageIcon getIcon(String achievement){
		return new ImageIcon(achievements.get(achievement));
	}
}
