package fr.skyost.hungergames.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import fr.skyost.hungergames.HungerGames;
import fr.skyost.hungergames.tasks.RandomItems;

public class ChestHolder {
	
	private List<Chest> chests;
	private Random rand;
	
	/**
	 * Genereates a new Chest Holder, which goes through the described region and
	 * detects all pre-existing chests and stores them.
	 * @param world
	 * @param minX
	 * @param minZ
	 * @param maxX
	 * @param maxZ
	 */
	public ChestHolder(World world, int minX, int minZ, int maxX, int maxZ) {
		chests = new LinkedList<Chest>();
		rand = new Random();
		int x, y, z;

		for (x = minX; x <= maxX; x++)
		for (z = minZ; z <= maxZ; z++)
		for (y = 0; y <= world.getHighestBlockYAt(x, z) ; y++) {
			Block block = world.getBlockAt(x, y, z);
			Material blockType = block.getType();
			if (block.getState() instanceof Chest) {
				Chest chest = (Chest) block.getState();
				Inventory inv = chest.getInventory();
				inv.clear();
				
				Random random = new Random();
				int max, i;
				ItemStack item;
				max = random.nextInt(3) + 1;
				for (i = 0; i < max; i++) {
					item = RandomItems.pickRandomItem();
					chest.getInventory().addItem(item);
				}
				
				chests.add(chest);	
			}
			else if (blockType == Material.CROPS || blockType == Material.CARROT || blockType == Material.POTATO) {
				block.setType(Material.AIR); //remove all crops
			}
		}
		
		HungerGames.logsManager.log("Loaded " + chests.size() + " chests!");

		HungerGames.logsManager.log("Generating wall. This may take a while...");
		
		for (y = 0; y <= 255; y++) {
			for (x = minX; x <= maxX; x++) {
				world.getBlockAt(x, y, minZ).setType(Material.GLASS);
				world.getBlockAt(x, y, maxZ).setType(Material.GLASS);
			}
			for (z = minZ; z <= maxZ; z++) {
				world.getBlockAt(minX, y, z).setType(Material.GLASS);
				world.getBlockAt(maxX, y, z).setType(Material.GLASS);
			}
		}

		HungerGames.logsManager.log("Wall generated!");
	}
	
	public List<Chest> getChests() {
		return this.chests;
	}
	
	public Chest getRandomChest() {
		int i = chests.size();
		return chests.get(rand.nextInt(i));
	}
	
	
}
