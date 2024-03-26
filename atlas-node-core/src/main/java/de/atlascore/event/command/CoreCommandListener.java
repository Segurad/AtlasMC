package de.atlascore.event.command;

import de.atlasmc.Atlas;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.Commands;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.event.command.CommandEvent;

public class CoreCommandListener implements Listener {
	
	@EventHandler
	public void onCommand(CommandEvent event) {
		if (event.isCancelled())
			return;
		String command = event.getCommand();
		if (command == null || command.length() == 0)
			return;
		CommandContext context = Commands.parseCommand(event.getSender(), command);
		if (!context.execute()) {
			Atlas.getLogger().debug("Failed to execute command: {}", command);
		}
	}

}
