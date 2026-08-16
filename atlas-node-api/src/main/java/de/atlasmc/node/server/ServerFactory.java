package de.atlasmc.node.server;

import java.io.File;
import java.util.UUID;

import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/server")
public interface ServerFactory {
	
	InternalServer createServer(UUID uuid, File workdir, ServerConfig config);
	
	InternalServer createServer(UUID uuid, File workdir, ServerGroup group);

}
