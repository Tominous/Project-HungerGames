package fr.skyost.hungergames.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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
		}

		try{
			
			BlockVector min = new BlockVector(minX, 0, minZ);
			BlockVector max = new BlockVector(maxX, 0, maxZ);
			ProtectedRegion region = new ProtectedCuboidRegion("arena", min, max);
			RegionManager manager = WGBukkit.getRegionManager(world);
			if (manager.hasRegion("arena")){
				manager.removeRegion("arena");
			}
			manager.addRegion(region);
			HungerGames.logsManager.log("Created worldguard region around the arena");
		}
		catch(Exception e){
			HungerGames.logsManager.log("Unable to create worldguard region around the arena");
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
