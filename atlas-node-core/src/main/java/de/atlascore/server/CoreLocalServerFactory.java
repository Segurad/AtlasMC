package de.atlascore.server;

import java.io.File;
import java.util.UUID;

import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.factory.ServerFactory;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.server.LocalServer;

@RegistryValue(registry="atlas:factory/server", key="atlas-core:factory/localserver", isDefault = true)
public class CoreLocalServerFactory implements ServerFactory {

	@Override
	public LocalServer createServer(UUID uuid, File workdir, ServerConfig config) {
		return new CoreLocalServer(uuid, workdir, config);
	}

	@Override
	public LocalServer createServer(UUID uuid, File workdir, ServerGroup group) {
		return new CoreLocalServer(uuid, workdir, group);
	}

}
