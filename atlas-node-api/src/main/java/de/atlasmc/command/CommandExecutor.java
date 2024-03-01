package de.atlasmc.command;

import java.util.Collection;
import java.util.List;

import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:command_executor")
public interface CommandExecutor {
	
	/**
	 * 
	 * @param context the commands context
	 * @return true if the Command has been executed successfully
	 */
	boolean execute(CommandContext context);
	
	default Collection<String> getKeys() {
		return List.of();
	}

}
