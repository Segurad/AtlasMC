package de.atlasmc.core.node.server.tasks;

import de.atlasmc.node.server.InternalServer;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/scheduler")
public class CoreServerSchedulerTickTaskFactory implements AtlasThreadTaskFactory {

	@SuppressWarnings("unchecked")
	@Override
	public AtlasThreadTask<InternalServer> createTask(ConfigurationSection config) {
		return (server, _) -> {
				server.getScheduler().runNextTasks();
		};
	}
}
