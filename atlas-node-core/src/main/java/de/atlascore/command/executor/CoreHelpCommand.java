package de.atlascore.command.executor;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.command.Command;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.command.Commands;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.MathUtil;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:help")
public class CoreHelpCommand implements CommandExecutor {

	private static final int ITEMS_PER_PAGE = 10;
	
	@Override
	public boolean execute(CommandContext context) {
		Integer page = context.getArgument("page", Integer.class, false);
		if (page == null)
			page = 1;
		String keyCommand = context.getArgument("command", String.class, false);
		if (keyCommand == null) {
			List<Command> commands = new ArrayList<>(Commands.getCommands());
			commands.sort((a, b) -> {
				return a.getName().compareTo(b.getName());
			});
			int first = (page - 1) * ITEMS_PER_PAGE;
			int maxPages = MathUtil.upper(((double) commands.size()) / ITEMS_PER_PAGE);
			first = MathUtil.getInRange(first, 0, commands.size()-1);
			String[] lines = new String[((commands.size()-first) % ITEMS_PER_PAGE) + 1];
			lines[0] = "--- Help - Commands - Page [" + page + "/" + maxPages + "] ---";
			for (int i = 1; i < lines.length; i++) {
				Command cmd = commands.get(first++);
				String description = cmd.getCommandDescription();
				if (description == null)
					description = "-";
				lines[i] = "- " + cmd.getName() + ": " + description;
			}
			context.getSender().sendMessage(lines);
		} else {
			Command cmd = Commands.getCommand(keyCommand);
			if (cmd == null) {
				context.getSender().sendMessage("No command found with name: " + keyCommand);
			} else {
				List<Chat> help = Commands.buildHelp(cmd);
				CommandSender sender = context.getSender();
				for (Chat msg : help) {
					sender.sendMessage(msg);
				}
			}
		}
		return true;
	}

}
