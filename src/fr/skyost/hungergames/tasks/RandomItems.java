package fr.skyost.hungergames.tasks;

import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import fr.skyost.hungergames.HungerGames;
import fr.skyost.hungergames.HungerGamesAPI;

public class RandomItems extends BukkitRunnable {
	
	private final BukkitScheduler scheduler = Bukkit.getScheduler();
	private static final Random random = new Random();
	private final int doubledRandomDistance = HungerGames.config.gameRandomItemsDistance * 2;
	
	@Override
	public void run() {
//		final Location location = HungerGames.currentMap.getSpawnLocation();
//		location.add(random.nextInt(doubledRandomDistance) - HungerGames.config.gameRandomItemsDistance + 1, 0, random.nextInt(doubledRandomDistance) - HungerGames.config.gameRandomItemsDistance + 1);
//		final int y = HungerGames.currentMap.getHighestBlockYAt(location);
//		location.setY(y);
		ItemStack item;
		int i, max;
		if(HungerGames.config.gameRandomItemsChests) {
//			final Block chestBlock = location.getBlock();
//			chestBlock.setType(Material.CHEST);
//			final Chest chest = (Chest)chestBlock.getState();
//			chest.getInventory().addItem(item);
//			final Block under = location.clone().subtract(0, 1, 0).getBlock();
//			if(!under.getType().isSolid()) {
//				under.setType(Material.GRASS);
//			}
			for (Chest chest : HungerGames.chestHolder.getChests()) {
				max = random.nextInt(3) + 1;
				for (i = 0; i < max; i++) {
					item = pickRandomItem();
					chest.getInventory().addItem(item);
				}
			}
			
		}
		else {
			HungerGames.logsManager.log("Unable to spawn the specified item. One of your items is invalid.", Level.WARNING);
		}
		scheduler.scheduleSyncDelayedTask(HungerGames.instance, this, random.nextInt(HungerGames.config.gameRandomItemsDelay * 20));
	}
	
	public static final ItemStack pickRandomItem() {
		int probability = 0;
		for(final int key : HungerGames.randomItems.keySet()) {
			probability += Integer.valueOf(key);
		}
		probability = random.nextInt(probability);
		int cumulativeProbability = 0;
		for(final Entry<Integer, ItemStack> entry : HungerGames.randomItems.entrySet()) {
			cumulativeProbability += entry.getKey();
			if(probability <= cumulativeProbability) {
				return entry.getValue();
			}
		}
		return null;
	}
	
}
