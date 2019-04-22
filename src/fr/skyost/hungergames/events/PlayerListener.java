package fr.skyost.hungergames.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.skyost.hungergames.HungerGames;
import fr.skyost.hungergames.HungerGames.Step;
import fr.skyost.hungergames.HungerGamesAPI;
import fr.skyost.hungergames.HungerGamesProfile;

public class PlayerListener implements Listener {
	
	@EventHandler
	private final void onPlayerQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		if(HungerGames.players.get(player) != null) {
			HungerGamesAPI.removePlayer(player, true);
		}
	}
	
	@EventHandler
	private final void onPlayerRespawn(final PlayerRespawnEvent event) {
		final HungerGamesProfile profile = HungerGames.players.get(event.getPlayer());
		if(profile != null) {
			event.setRespawnLocation(profile.getGeneratedLocation());
		}
	}
	
	@EventHandler
	private final void onPlayerTeleport(final PlayerTeleportEvent event) {
		final Player player = event.getPlayer();
		final Location to = event.getTo();
		if(HungerGames.players.get(player) != null && ((HungerGames.currentStep == Step.GAME || HungerGames.currentStep == Step.SECOND_COUNTDOWN) && !to.getWorld().equals(HungerGames.currentMap) && !player.hasMetadata("Reverted"))) {
			HungerGamesAPI.removePlayer(player, true);
			player.teleport(to);
		}
	}
	
	@EventHandler
	private final void onPlayerInteract(final PlayerInteractEvent event) {
		final ItemStack item = event.getItem();
		if(item != null && item.equals(HungerGames.kitSelector)) {
			final Player player = event.getPlayer();
			if(player.hasPermission("hungergames.kits.use")) {
				player.openInventory(HungerGames.kitsMenu);
			}
			else {
				player.sendMessage(HungerGames.messages.messagePermission);
			}
			event.setCancelled(true);
		}

		if (event.getPlayer().isOp()) {
			return;
		}
		
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) {
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Material block = event.getClickedBlock().getType();
			if (block != null) {
				if (block == Material.TRAP_DOOR || block == Material.STONE_BUTTON || block == Material.WOOD_BUTTON
				    || block == Material.LEVER || block == Material.CHEST || block == Material.WORKBENCH 
				    || block == Material.FURNACE || block == Material.WOODEN_DOOR || block == Material.BURNING_FURNACE) {
					return;
				}
					if (event.isBlockInHand()) {
						ItemStack item = event.getItem();
						if (!HungerGames.playerMaterials.contains(item.getType())) {
							event.setCancelled(true);
							return;
						}
					}
			}
		}
		
		HungerGamesProfile playerData = HungerGames.players.get(event.getPlayer());
		if (playerData == null) {
			//player is a spectator or not part of hunger games 
			event.setCancelled(true);
			return;
		}
		if (!event.getPlayer().getWorld().getName().equals(HungerGames.currentMap.getName())) {
			event.setCancelled(true);
			return;
		}
		if (event.getClickedBlock() == null) {
			return;
		}
		Material block = event.getClickedBlock().getType();
		
	
		
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
		if (block != null)
		if (!HungerGames.playerMaterials.contains(block)) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	private final void onInventoryClick(final InventoryClickEvent event) {
		if(event.getInventory().getName().equals(HungerGames.kitsMenu.getName())) {
			final HumanEntity human = event.getWhoClicked();
			if(human != null && human instanceof Player) {
				final Player player = (Player)human;
				final ItemStack itemSelected = event.getCurrentItem();
				if(itemSelected != null) {
					final String kitName = itemSelected.getItemMeta().getDisplayName();
					if(HungerGames.config.kitsPermissions && !player.hasPermission("hungergames.kits." + ChatColor.stripColor(kitName).toLowerCase())) {
						player.sendMessage(HungerGames.messages.messagePermission);
						return;
					}
					final PlayerInventory inventory = player.getInventory();
					inventory.clear();
					inventory.setArmorContents(new ItemStack[]{null, null, null, null});
					inventory.addItem(HungerGames.kitSelector);
					final ItemStack[] items = HungerGames.kits.get(kitName);
					if(items != null) {
						inventory.addItem(items);
					}
					event.setCancelled(true);
					player.closeInventory();
				}
			}
		}
	}
	
}
