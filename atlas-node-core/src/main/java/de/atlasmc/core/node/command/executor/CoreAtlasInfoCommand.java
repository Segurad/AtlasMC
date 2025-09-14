package de.atlasmc.core.node.command.executor;

import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas:info")
public class CoreAtlasInfoCommand implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		CommandSender sender = context.getSender();
		Runtime rt = Runtime.getRuntime();
		sender.sendMessage(
				"VM-Name: " + System.getProperty("java.vm.name"),
				"VM-Verions: " + System.getProperty("java.vm.version"),
				"OS-Name: " + System.getProperty("os.name"),
				"Processors: " + rt.availableProcessors(),
				"Memory: " + ((rt.totalMemory() - rt.freeMemory()) / 1000 / 1000) + "mb /" + rt.totalMemory() / 1000 / 1000 + "mb (max: " + rt.maxMemory() / 1000 / 1000 + "mb)"
				);
		return true;
	}

}
