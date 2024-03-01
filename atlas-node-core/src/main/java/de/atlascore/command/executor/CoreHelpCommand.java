package de.atlascore.command.executor;

import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command_executor", key="atlas-core:help")
public class CoreHelpCommand implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
