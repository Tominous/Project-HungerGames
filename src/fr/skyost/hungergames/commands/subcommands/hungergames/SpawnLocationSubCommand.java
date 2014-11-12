package fr.skyost.hungergames.commands.subcommands.hungergames;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import fr.skyost.hungergames.SpawnLocationManager;
import fr.skyost.hungergames.commands.SubCommandsExecutor.CommandInterface;

public class SpawnLocationSubCommand implements CommandInterface {

	@Override
	public final String[] names() {
		return new String[]{"spawn-modify", "spawn"};
	}
	
	@Override
	public final boolean forcePlayer() {
		return true;
	}
	
	@Override
	public final String getPermission() {
		return "hungergames.lobby.set";
	}
	
	@Override
	public final int getMinArgsLength() {
		return 1;
	}
	
	@Override
	public final String getUsage() {
		return "spawn [add/remove]";
	}
	
	@Override
	public final boolean onCommand(final CommandSender sender, final String[] args) throws InvalidConfigurationException {
		final Location location = ((Player)sender).getLocation();
		
		if (args.length != 1) {
			return false;
		}
		
		String arg = args[0];
		if (arg.equalsIgnoreCase("add")) {
			SpawnLocationManager.addSpawn(location);
			sender.sendMessage("Spawn location " + location + " added!");
			return true;
		} else if (arg.equalsIgnoreCase("remove")) {
			SpawnLocationManager.removeSpawn(location);
			return true;
		} else {
			return false;
		}
	}

}
