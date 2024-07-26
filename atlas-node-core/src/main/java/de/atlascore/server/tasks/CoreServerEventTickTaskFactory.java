package de.atlascore.server.tasks;

import java.util.Queue;

import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.server.LocalServer;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key = "atlas-core:server/tick/events")
public class CoreServerEventTickTaskFactory implements AtlasThreadTaskFactory {

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
				Queue<Event> events = server.getEventQueue();
				Event event = null;
				while ((event = events.poll()) != null) {
					HandlerList.callEvent(event);
				}
			}
			
			@Override
			public String name() {
				return name;
			}
		};
	}

}
