package de.atlasmc.command;

import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:command/source_validator")
public interface CommandSourceValidator {
	
	boolean isValid(CommandSender sender);

}
