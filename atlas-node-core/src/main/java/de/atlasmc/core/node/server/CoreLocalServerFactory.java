package de.atlasmc.core.node.server;

import java.io.File;
import java.util.UUID;

import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.node.server.ServerFactory;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:factory/server", key="atlas-core:localserver", isDefault = true)
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
