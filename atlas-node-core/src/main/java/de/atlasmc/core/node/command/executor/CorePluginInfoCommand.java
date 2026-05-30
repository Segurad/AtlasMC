package de.atlasmc.core.node.command.executor;

import de.atlasmc.Atlas;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.plugin.Dependency;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:command/info")
public class CorePluginInfoCommand implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		String name = context.getArgument("plugin", String.class);
		var manager = Atlas.getPluginManager();
		Plugin plugin = manager.getPlugin(name);
		var sender = context.getSender();
		if (plugin == null) {
			sender.sendMessage("No plugin found with name: " + name);
			return true;
		}
		sender.sendMessage("=== " + plugin.getName() + " " + plugin.getVersion() + " =====");
		sender.sendMessage(plugin.getDescription());
		sender.sendMessage("---");
		sender.sendMessage("Authors:");
		for (String author : plugin.getAuthor())
			sender.sendMessage("- " + author);
		var proto = plugin.getPrototype();
		var locks = manager.lockCount(plugin);
		sender.sendMessage("Status: " + (plugin.isLoaded() ? plugin.isEnabled() ? "enabled" : "loaded" : "unloaded"));
		sender.sendMessage("Locks: " + locks);
		sender.sendMessage("Keep-Loaded: " + manager.isKeepLoaded(plugin));
		sender.sendMessage("Required Features: " + String.join(", ", proto.getRequiredFeatures()));
		sender.sendMessage("Soft required Features: " + String.join(", ", proto.getSoftRequiredFeatures()));
		sender.sendMessage("Dependencies: " + String.join(", ", proto.getDependencies().stream().map(Dependency::toString).toList()));
		sender.sendMessage("Configurations:");
		for (var cfg : plugin.getConfigurations())
			sender.sendMessage("- " + cfg.getNamespacedKey());
		return true;
	}

}
