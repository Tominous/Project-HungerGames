package fr.skyost.hungergames.commands.subcommands.hungergames;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import fr.skyost.hungergames.HungerGames;
import fr.skyost.hungergames.HungerGamesAPI;
import fr.skyost.hungergames.HungerGames.Step;
import fr.skyost.hungergames.commands.SubCommandsExecutor.CommandInterface;
import fr.skyost.hungergames.tasks.Countdown;
import fr.skyost.hungergames.tasks.PostExecuteFirst;

public class ForceStartSubCommand implements CommandInterface {

	@Override
	public final String[] names() {
		return new String[]{"forcestart", "start"};
	}
	
	@Override
	public final boolean forcePlayer() {
		return false;
	}
	
	@Override
	public final String getPermission() {
		return "hungergames.start";
	}
	
	@Override
	public final int getMinArgsLength() {
		return 0;
	}
	
	@Override
	public final String getUsage() {
		return "forcestart";
	}
	
	@Override
	public final boolean onCommand(final CommandSender sender, final String[] args) throws InvalidConfigurationException {
		if (HungerGames.currentStep == HungerGames.Step.LOBBY) {
			//COPIED FROM HungerGamesAPI:AddPlayer()
			HungerGames.logsManager.log("Starting game...");
			HungerGamesAPI.broadcastMessage(HungerGames.messages.message3.replace("/n/", String.valueOf(HungerGames.config.lobbyCountdownTime)));
			HungerGames.currentStep = Step.FIRST_COUNTDOWN;
			HungerGames.tasks[0] = new Countdown(HungerGames.config.lobbyCountdownTime, HungerGames.config.lobbyCountdownExpBarLevel, HungerGames.config.lobbyCountdownMobBar, new PostExecuteFirst()).runTaskTimer(HungerGames.instance, 0, 20L).getTaskId();
		
			return true;
		}
		else {
			sender.sendMessage("Session is already underway!");
			return true;
		}
	}

}
