package fr.skyost.hungergames;

import fr.skyost.hungergames.utils.CircularQueue;
import org.bukkit.Location;


/**
 * Manages all the locations a player can spawn. <br />
 * This Class will also host the mechanisms that try to make sure that the players are spawned in unique areas using a circular queue.
 * @author Skyler
 *
 */
public final class SpawnLocationManager {
	
	private static CircularQueue<Location> list = new CircularQueue<Location>();
	
	/**
	 * Gets the next spawn location from the manager.<br />
	 * The spawn location obtained will be the next in the list. It will only be the same as previously obtained if the
	 * Circle has been fully transposed.
	 * @return
	 */
	public static Location next() {
		return list.next();
	}
	
	/**
	 * Adds the spawn location at the end of the stored list.
	 * @param loc
	 */
	public static void addSpawn(Location loc) {
		list.add(loc);
	}
	
	/**
	 * Removes the passed location from the list, if it exists within it.<br />
	 * TODO: RELIES ON PITCH AND YAW TO FIND IT
	 * @param loc
	 */
	public static void removeSpawn(Location loc) {
		list.remove(loc);
	}
}
