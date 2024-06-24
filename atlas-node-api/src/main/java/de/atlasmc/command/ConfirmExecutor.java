package de.atlasmc.command;

import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas:confirm")
public final class ConfirmExecutor implements CommandExecutor {
	
	@Override
	public boolean execute(CommandContext context) {
		Commands.finishConfirm(context);
		return true;
	}

}
