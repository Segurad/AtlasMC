package de.atlascore.command.factory;

import de.atlascore.command.CoreCommand;
import de.atlascore.command.CoreCommandArg;
import de.atlasmc.Gamemode;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.entity.Player;

public class CoreCmdFactoryGamemode {
	
	public static CoreCommand create() {
		CommandExecutor survivalExecutor = (sender, cmd, label, args, depth) -> {
			if (args.length - depth > 1) {
				// TODO other
			} else {
				if (!(sender instanceof Player)) return false;
				Player p = (Player) sender;
				p.setGamemode(Gamemode.SURVIVAL);
			}
			return false;
		};
		
		CoreCommand cmd = new CoreCommand("gamemode", null, null, "gm");
		
		
		CoreCommandArg arg0 = new CoreCommandArg("survial", null, null);
	}

}
