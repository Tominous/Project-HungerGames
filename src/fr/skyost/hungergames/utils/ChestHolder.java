package fr.skyost.hungergames.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

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
		int x, z;
		for (x = minX; x <= maxX; x+=16)
		for (z = -minZ; z <= maxZ; z+=16){
			for (BlockState block : world.getChunkAt(x,z).getTileEntities()) {
				if (block instanceof Chest) {
					Chest chest = (Chest) block;
					Inventory inv = chest.getInventory();
					inv.clear();
					chests.add(chest);
				}
			}
		}
	}
	
	public List<Chest> getChests() {
		return this.chests;
	}
	
	public Chest getRandomChest() {
		int i = chests.size();
		return chests.get(rand.nextInt(i));
	}
	
	
}
