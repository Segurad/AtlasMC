package de.atlasmc.core.node.server;

import java.io.File;
import java.util.UUID;

import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.node.server.InternalServer;
import de.atlasmc.node.server.ServerFactory;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:factory/server", key="atlas-core:internalserver", isDefault = true)
public class CoreInternalServerFactory implements ServerFactory {

	@Override
	public InternalServer createServer(UUID uuid, File workdir, ServerConfig config) {
		return new CoreInternalServer(uuid, workdir, config);
	}

	@Override
	public InternalServer createServer(UUID uuid, File workdir, ServerGroup group) {
		return new CoreInternalServer(uuid, workdir, group);
	}

}
