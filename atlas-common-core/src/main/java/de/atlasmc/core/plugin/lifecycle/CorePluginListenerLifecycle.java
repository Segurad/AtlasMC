package de.atlasmc.core.plugin.lifecycle;

import de.atlasmc.event.HandlerList;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginLifecycleHandler;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry = "atlas:plugin/lifecycle", key = "atlas-core:listeners")
public class CorePluginListenerLifecycle implements PluginLifecycleHandler {
	
	@Override
	public void onEnable(Plugin plugin) {
		HandlerList.loadAutoregisterListeners(plugin);
	}
	
	@Override
	public void onDisable(Plugin plugin) {
		HandlerList.unregisterAllListenerGlobal(plugin);
	}
	
	

}
