package de.atlasmc.core.node.server.tasks;

import de.atlasmc.log.Log;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/connection")
public class CoreServerConnectionTickTaskFactory implements AtlasThreadTaskFactory {

	@SuppressWarnings("unchecked")
	@Override
	public AtlasThreadTask<LocalServer> createTask(ConfigurationSection config) {
		return (server, _) -> {
			final Log logger = server.getLogger();
			for (NodePlayer player : server.getPlayers()) {
				player.getConnection().handleSyncPackets(logger);
			}
		};
	}

}
