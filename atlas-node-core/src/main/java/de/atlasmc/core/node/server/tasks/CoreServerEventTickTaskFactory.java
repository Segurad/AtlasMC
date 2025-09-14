package de.atlasmc.core.node.server.tasks;

import java.util.Queue;

import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/events")
public class CoreServerEventTickTaskFactory implements AtlasThreadTaskFactory {

	@SuppressWarnings("unchecked")
	@Override
	public AtlasThreadTask<LocalServer> createTask(ConfigurationSection config) {
		return (server, tick) -> {
			Queue<Event> events = server.getEventQueue();
			Event event = null;
			while ((event = events.poll()) != null) {
				HandlerList.callEvent(event);
			}
		};
	}

}
