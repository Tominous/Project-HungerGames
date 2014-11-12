package fr.skyost.hungergames.utils;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import fr.skyost.hungergames.HungerGames;

public class YamlPotion {
	
	private PotionType type;
	private String name;
	private List<String> lore;
	private int quantity;
	private boolean splash;
	private int level;
	private int chance;
	
	public YamlPotion(YamlConfiguration potionConfig) {
		if (potionConfig == null) {
			defaults();
			return;
		}
		
		String typeName;
		typeName = potionConfig.getString("type", "WEAKNESS");
		try {
			type = PotionType.valueOf(typeName);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			HungerGames.logsManager.log("Invalid potion type name: " + typeName+"\nDefaulting to WEAKNESS!", Level.WARNING);
			type = PotionType.WEAKNESS;
		}
		name = potionConfig.getString("name", "Dud");
		lore = potionConfig.getStringList("lore");
		quantity = potionConfig.getInt("quantity", 1);
		splash = potionConfig.getBoolean("splash", false);
		level = potionConfig.getInt("level", 1);
		chance = potionConfig.getInt("chance", 10);
		
	}
	
	private void defaults() {
		type = PotionType.WEAKNESS;
		name = "Dud";
		lore = null;
		quantity = 1;
		splash = false;
		level = 1;
		chance = 10;
	}
	
	public ItemStack getItemStack() {
		Potion potion;
		potion = new Potion(type, level);
		potion.setSplash(splash);
		ItemStack item = potion.toItemStack(quantity);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(name);
		
		return item;
	}

	public PotionType getType() {
		return type;
	}

	public int getChance() {
		return chance;
	}
	
	
}
