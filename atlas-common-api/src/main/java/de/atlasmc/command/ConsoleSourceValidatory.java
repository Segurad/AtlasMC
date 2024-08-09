package de.atlasmc.command;

import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/source_validator", key="atlas:console")
public class ConsoleSourceValidatory implements CommandSourceValidator {

	@Override
	public boolean isValid(CommandSender sender) {
		return sender instanceof ConsoleCommandSender;
	}

}
