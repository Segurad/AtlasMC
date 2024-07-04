package de.atlasmc.server;

import java.io.File;
import java.util.UUID;

import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/server")
public interface ServerFactory {
	
	LocalServer createServer(UUID uuid, File workdir, ServerConfig config);
	
	LocalServer createServer(UUID uuid, File workdir, ServerGroup group);

}
