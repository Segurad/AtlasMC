package de.atlascore.socket;

import java.util.UUID;

import de.atlasmc.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.SocketConfig;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.socket.NodeSocket;
import de.atlasmc.socket.NodeSocketFactory;

@RegistryValue(registry="atlas:factory/socket", key="atlas-core:factory/node_socket_factory", isDefault = true)
public class CoreNodeSocketFactory implements NodeSocketFactory {

	@Override
	public NodeSocket createProxy(UUID uuid, LocalAtlasNode node, int port, SocketConfig config) {
		return new CoreNodeSocket(uuid, node, port, config);
	}

}
