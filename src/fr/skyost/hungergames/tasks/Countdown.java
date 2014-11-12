package fr.skyost.hungergames.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.skyost.hungergames.HungerGames;
import fr.skyost.hungergames.utils.ErrorReport;
import fr.skyost.hungergames.utils.MobBarAPI;

public class Countdown extends BukkitRunnable {
	
	private final int originalTime;
	private int time;
	private final boolean expBarLevel;
	private final boolean mobBar;
	private final BukkitRunnable postExecute;
	
	private final Set<Player> players;
	private final MobBarAPI mobBarApi = MobBarAPI.getInstance();
	
	/**
	 * Whether or not the players will be able to move
	 */
	public boolean stale;
	private Map<UUID, Location> startLocations;
	
	public Countdown(final int time, final boolean expBarLevel, final boolean mobBar, final BukkitRunnable postExecute, boolean stale) {
		this(time, expBarLevel, mobBar, postExecute);
		this.stale = stale;
		startLocations = new HashMap<UUID, Location>();
		for (Player player : players) {
			startLocations.put(player.getUniqueId(), player.getLocation());
		}
	}

	
	public Countdown(final int time, final boolean expBarLevel, final boolean mobBar, final BukkitRunnable postExecute) {
		this.originalTime = time;
		stale = false; //default
		this.time = time;
		this.expBarLevel = expBarLevel;
		this.mobBar = mobBar;
		this.postExecute = postExecute;
		players = HungerGames.players.keySet();
		for(final Player player : players) {
			if(expBarLevel) {
				player.setLevel(time);
			}
			if(mobBar) {
				mobBarApi.setStatus(player, String.valueOf(time), 100, true);
			}
		}
	}

	@Override
	public void run() {
		time--;
		for(final Player player : players) {
			if(expBarLevel) {
				player.setLevel(time);
			}
			if(mobBar) {
				mobBarApi.setStatus(player, String.valueOf(time), (100 * time) / originalTime, false);
			}
			if (stale) {
				player.teleport(startLocations.get(player.getUniqueId()));
			}
		}
		if(time == 0) {
			if(mobBar) {
				try {
					for(final Player player : players) {
						mobBarApi.removeStatus(player);
					}
				}
				catch(final Exception ex) {
					ex.printStackTrace();
					ErrorReport.createReport(ex).report();
				}
			}
			HungerGames.tasks[0] = -1;
			HungerGames.tasks[1] = Bukkit.getScheduler().scheduleSyncDelayedTask(HungerGames.instance, postExecute);
			this.cancel();
		}
	}
	
}
