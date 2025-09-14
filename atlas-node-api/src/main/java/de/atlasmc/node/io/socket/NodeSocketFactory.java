package de.atlasmc.node.io.socket;

import java.util.UUID;

import de.atlasmc.network.socket.SocketConfig;
import de.atlasmc.node.LocalAtlasNode;
import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/socket")
public interface NodeSocketFactory {
	
	NodeSocket createProxy(UUID uuid, LocalAtlasNode node, int port, SocketConfig config);

}
