package de.atlascore.server.tasks;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.server.LocalServer;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/scheduler")
public class CoreServerSchedulerTickTaskFactory implements AtlasThreadTaskFactory {

	@SuppressWarnings("unchecked")
	@Override
	public AtlasThreadTask<LocalServer> createTask(ConfigurationSection config) {
		return (server, tick) -> {
				server.getScheduler().runNextTasks();
		};
	}
}
