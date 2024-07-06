package de.atlasmc.tick;

import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/atlas_thread_task")
public interface AtlasThreadTaskFactory {
	
	/**
	 * Creates a new {@link AtlasThreadTask}.
	 * The context is defined by the implementation.
	 * @param name the tasks name
	 * @param context
	 * @return
	 */
	AtlasThreadTask createTask(String name, Object... context);

}
