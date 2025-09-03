package de.atlasmc.socket;

import java.util.UUID;

import de.atlasmc.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.SocketConfig;
import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/socket")
public interface NodeSocketFactory {
	
	NodeSocket createProxy(UUID uuid, LocalAtlasNode node, int port, SocketConfig config);

}
