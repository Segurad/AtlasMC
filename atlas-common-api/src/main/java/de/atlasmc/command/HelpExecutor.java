package de.atlasmc.command;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.registry.RegistryValue;

/**
 * Executor sending command info to the sender.
 * <br>
 * Parameters
 * <ul>
 * <li>page: optional integer parameter for multiple pages</li>
 * <li>command: optional string to the command if not set the executed command will be used</li>
 * </ul>
 */
@RegistryValue(registry="atlas:command/executor", key="atlas:help")
public class HelpExecutor implements CommandExecutor {

	private static final int ITEMS_PER_PAGE = 10;
	
	@Override
	public boolean execute(CommandContext context) {
		Integer page = context.getArgument("page", Integer.class, false);
		if (page == null)
			page = 1;
		String keyCommand = context.getArgument("command", String.class, false);
		if (keyCommand == null) {
			final String name = context.getCommand().getName();
			if (!name.equals("help"))
				keyCommand = name;
		}
		List<Chat> help = null;
		if (keyCommand == null) {
			help = Commands.buildHelp(page, ITEMS_PER_PAGE);
		} else {
			Command cmd = Commands.getCommand(keyCommand);
			if (cmd == null) {
				cmd = Commands.getByAlias(keyCommand);
			}
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
