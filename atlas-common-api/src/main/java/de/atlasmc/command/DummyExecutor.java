package de.atlasmc.command;

import de.atlasmc.registry.RegistryValue;

/**
 * Represents a dummy executor it will send the CommandSender the issued command and returns true.
 * This executor can be used as placeholder to define command with executors without needing to implement a executor jet.
 */
@RegistryValue(registry="atlas:command/executor", key="atlas:dummy_executor")
public class DummyExecutor implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		context.getSender().sendMessage(context.getRawCommand());
		return true;
	}

}
