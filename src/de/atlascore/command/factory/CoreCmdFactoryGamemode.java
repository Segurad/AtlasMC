package de.atlascore.command.factory;

import de.atlascore.command.CoreCommand;
import de.atlasmc.Gamemode;
import de.atlasmc.command.Command;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.entity.Player;

public class CoreCmdFactoryGamemode {
	
	public static CoreCommand create() {
		CommandExecutor survivalExe = (sender, cmd, label, args, depth) -> {
			return executeGameMode(Gamemode.SURVIVAL, sender, cmd, args, depth);
		};
		
		CommandExecutor creativeExe = (sender, cmd, label, args, depth) -> {
			return executeGameMode(Gamemode.CREATIVE, sender, cmd, args, depth);
		};
		
		CommandExecutor spectatorExe = (sender, cmd, label, args, depth) -> {
			return executeGameMode(Gamemode.SPECTATOR, sender, cmd, args, depth);
		};
		
		CommandExecutor adventureExe = (sender, cmd, label, args, depth) -> {
			return executeGameMode(Gamemode.ADVENTURE, sender, cmd, args, depth);
		};
		
		CoreCommand cmd = new CoreCommand("gamemode", null, null, "gm");
		cmd.createArg("survival", survivalExe);
		cmd.createArg("0", survivalExe);
		cmd.createArg("creative", creativeExe);
		cmd.createArg("1", creativeExe);
		cmd.createArg("spectator", spectatorExe);
		cmd.createArg("3", spectatorExe);
		cmd.createArg("adventure", adventureExe);
		cmd.createArg("2", adventureExe);
		return cmd;
	}
	
	private static boolean executeGameMode(Gamemode gamemode, CommandSender sender, Command cmd, String[] args, int depth) {
		if (args.length - depth > 1) {
			return false;
			// TODO other gamemode
		} else {
			if (!(sender instanceof Player)) return false;
			Player p = (Player) sender;
			p.setGamemode(gamemode);
			return true;
		}
	}

}
