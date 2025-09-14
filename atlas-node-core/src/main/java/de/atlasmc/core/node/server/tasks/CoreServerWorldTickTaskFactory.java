package de.atlasmc.core.node.server.tasks;

import de.atlasmc.node.server.LocalServer;
import de.atlasmc.node.world.World;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/worlds")
public class CoreServerWorldTickTaskFactory implements AtlasThreadTaskFactory {

	@SuppressWarnings("unchecked")
	@Override
	public AtlasThreadTask<? extends LocalServer> createTask(ConfigurationSection config) {
		return (server, tick) -> {
			for (World world : server.getWorlds()) {
				world.tick();
			}
		};
	}

}
