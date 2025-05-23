package de.atlasmc.tick;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key="atlas:factory/atlas_thread_task")
public interface AtlasThreadTaskFactory {
	
	/**
	 * Creates a new {@link AtlasThreadTask}.
	 * The context is defined by the implementation.
	 * @param name the tasks name
	 * @param context
	 * @return
	 */
	<T> AtlasThreadTask<T> createTask(ConfigurationSection config);

}
