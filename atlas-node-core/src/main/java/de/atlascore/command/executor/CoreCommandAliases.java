package de.atlascore.command.executor;

import de.atlasmc.command.Command;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.command.Commands;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:aliases")
public class CoreCommandAliases implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		String command = context.getArgument("command", String.class);
		CommandSender sender = context.getSender();
		Command cmd = Commands.getCommand(command);
		if (cmd == null) {
			context.getSender().sendMessage("No command found with name: " + command);
		} else {
			sender.sendMessage("--- Aliases: " + command + " ---");
			if (cmd.hasAliases()) {
				for (String alias : cmd.getAliases())
					sender.sendMessage(alias);
			}
		}
		return true;
	}

}
