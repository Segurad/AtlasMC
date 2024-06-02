package de.atlascore.command.executor;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.command.Command;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.command.Commands;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:help")
public class CoreHelpCommand implements CommandExecutor {

	private static final int ITEMS_PER_PAGE = 10;
	
	@Override
	public boolean execute(CommandContext context) {
		Integer page = context.getArgument("page", Integer.class, false);
		if (page == null)
			page = 1;
		String keyCommand = context.getArgument("command", String.class, false);
		List<Chat> help = null;
		if (keyCommand == null) {
			help = Commands.buildHelp(page, ITEMS_PER_PAGE);
		} else {
			Command cmd = Commands.getCommand(keyCommand);
			if (cmd == null) {
				context.getSender().sendMessage("No command found with name: " + keyCommand);
			} else {
				help = Commands.buildHelp(page, ITEMS_PER_PAGE, cmd);
			}
		}
		if (help != null) {
			CommandSender sender = context.getSender();
			for (Chat msg : help) {
				sender.sendMessage(msg);
			}
		}
		return true;
	}

}
