package fr.skyost.hungergames.tasks;

import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.skyost.hungergames.HungerGames;
import fr.skyost.hungergames.HungerGames.Step;
import fr.skyost.hungergames.HungerGamesProfile;

public class DeathMatch extends BukkitRunnable {
	
	@Override
	public void run() {

		Player player;
		
		//generate small dome
		int x, y, z;
		Location center = HungerGames.currentMap.getSpawnLocation();
		int minX = center.getBlockX() - 50;
		int maxX = minX + 100;
		int minZ = center.getBlockZ() - 50;
		int maxZ = minZ + 100;
		for (y = 0; y <= 255; y++) {
			for (x = minX; x <= maxX; x++) {
				HungerGames.currentMap.getBlockAt(x, y, minZ).setType(Material.GLASS);
				HungerGames.currentMap.getBlockAt(x, y, maxZ).setType(Material.GLASS);
			}
			for (z = minZ; z <= maxZ; z++) {
				HungerGames.currentMap.getBlockAt(minX, y, z).setType(Material.GLASS);
				HungerGames.currentMap.getBlockAt(maxX, y, z).setType(Material.GLASS);
			}
		}
		
		for(final Entry<Player, HungerGamesProfile> entry : HungerGames.players.entrySet()) {
			player = entry.getKey();
			player.teleport(entry.getValue().getGeneratedLocation());
			player.sendMessage("The deathmatch has started!");
		}
		HungerGames.tasks[0] = -1;
		HungerGames.tasks[1] = -1;
	}
	
}
