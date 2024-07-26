package de.atlascore.server.tasks;

import de.atlasmc.NodePlayer;
import de.atlasmc.log.Log;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.server.LocalServer;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/connection")
public class CoreServerConnectionTickTaskFactory implements AtlasThreadTaskFactory {

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
				final Log logger = server.getLogger();
				for (NodePlayer player : server.getPlayers()) {
					player.getConnection().handleSyncPackets(logger);
				}
			}
			
			@Override
			public String name() {
				return name;
			}
		};
	}

}
