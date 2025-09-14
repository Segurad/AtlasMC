package de.atlasmc.core.node.io.socket;

import java.util.UUID;

import de.atlasmc.network.socket.SocketConfig;
import de.atlasmc.node.LocalAtlasNode;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.io.socket.NodeSocketFactory;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:factory/socket", key="atlas-core:factory/node_socket_factory", isDefault = true)
public class CoreNodeSocketFactory implements NodeSocketFactory {

	@Override
	public NodeSocket createProxy(UUID uuid, LocalAtlasNode node, int port, SocketConfig config) {
		return new CoreNodeSocket(uuid, node, port, config);
	}

}
