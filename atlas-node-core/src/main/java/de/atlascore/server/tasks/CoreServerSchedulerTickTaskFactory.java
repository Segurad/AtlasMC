package de.atlascore.server.tasks;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.server.LocalServer;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/scheduler")
public class CoreServerSchedulerTickTaskFactory implements AtlasThreadTaskFactory {

	@Override
	public AtlasThreadTask createTask(String name, Object... context) {
		if (context == null)
			throw new IllegalArgumentException("Context can not be null!");
		Object obj = context[0];
		if (!(obj instanceof LocalServer server))
			throw new IllegalArgumentException("Expected LocalServer as first argument: " + obj.getClass());
		return new AtlasThreadTask() {
			
			@Override
			public void tick(int tick) throws Exception {
				server.getScheduler().runNextTasks();
			}
			
			@Override
			public String name() {
				return name;
			}
		};
	}

}
