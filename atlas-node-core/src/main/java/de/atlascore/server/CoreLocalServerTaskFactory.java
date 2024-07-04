package de.atlascore.server;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key="atlas-core:factory/localserver/task")
public interface CoreLocalServerTaskFactory {
	
	CoreLocalServerTask create(CoreLocalServer server, ConfigurationSection config);

}
