package de.atlasmc.core.plugin.lifecycle;

import de.atlasmc.Atlas;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginLifecycleHandler;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry = "atlas:plugin/lifecycle", key = "atlas-core:unregister-scheduled-tasks")
public class CorePluginScheduledTasksLifecycle implements PluginLifecycleHandler {

	@Override
	public void onDisable(Plugin plugin) {
		Atlas.getScheduler().removeAllTasks(plugin);
	}
	
}
