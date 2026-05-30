package de.atlasmc.core.node.command.executor;

import de.atlasmc.Atlas;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:command/list")
public class CorePluginListCommand implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		var plugins = Atlas.getPluginManager().getPlugins();
		var sender = context.getSender();
		sender.sendMessage("=== Plugins: " + plugins.size() + " =====");
		sender.sendMessage(String.join(", ", plugins.stream().map(Plugin::getName).toList()));
		return true;
	}

}
