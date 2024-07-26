package de.atlascore.command.executor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import de.atlasmc.AtlasNode;
import de.atlasmc.Gamemode;
import de.atlasmc.NodePlayer;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.entity.Player;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry = "atlas:command/executor", key="atlas-core:gamemode")
public class CoreGamemodeCommand implements CommandExecutor {

	private static final Collection<String> KEYS = List.of("gamemode", "gamemodeID", "target");
	
	@Override
	public boolean execute(CommandContext context) {
		Gamemode gamemode = null;
		Integer id = context.getArgument("gamemodeID", Integer.class, false);
		if (id != null) {
			gamemode = Gamemode.getByID(id);
		}
		if (gamemode == null) {
			context.getArgument("gamemode", Gamemode.class);
		}
		List<?> targets = context.getArgument("target", List.class, false);
		if (targets == null || targets.isEmpty()) {
			if (context.getSender() instanceof NodePlayer aplayer) {
				Player p = aplayer.getPlayer();
				if (p != null) {
					p.setGamemode(gamemode);
					return true;
				}
			}
		} else {
			boolean success = false;
			for (Object target : targets) {
				NodePlayer aplayer = null;
				if (target instanceof String targetName) {
					aplayer = AtlasNode.getLocalPlayer(targetName);
				} else if (target instanceof UUID targetUUID) {
					aplayer = AtlasNode.getPlayer(targetUUID);
				}
				if (aplayer == null)
					continue;
				Player p = aplayer.getPlayer();
				if (p != null) {
					p.setGamemode(gamemode);
					success = true;
				}
			}
			return success;
		}
		return false;
	}
	
	@Override
	public Collection<String> getKeys() {
		return KEYS;
	}

}
