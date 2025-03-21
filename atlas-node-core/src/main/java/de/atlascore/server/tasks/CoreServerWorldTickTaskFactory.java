package de.atlascore.server.tasks;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.server.LocalServer;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.world.World;

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
