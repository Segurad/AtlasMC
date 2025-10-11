package de.atlasmc.core.node.io.socket;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.io.socket.SocketManager;

public class CoreSocketManager implements SocketManager {
	
	private Map<UUID, NodeSocket> sockets;
	
	public CoreSocketManager() {
		sockets = new ConcurrentHashMap<>();
	}
	
	@Override
	public NodeSocket getSocket(UUID uuid) {
		return sockets.get(uuid);
	}

	@Override
	public boolean registerSocket(NodeSocket socket) {
		return sockets.put(socket.getUUID(), socket) != socket;
	}

	@Override
	public boolean unregisterSocket(NodeSocket socket) {
		return sockets.remove(socket.getUUID(), socket);
	}

	@Override
	public Collection<NodeSocket> getSockets() {
		return sockets.values();
	}

	@Override
	public NodeSocket createSocket(SocketConfig config, InetSocketAddress host, InetSocketAddress externalHost) {
		UUID socketUUID = UUID.randomUUID();
		NodeSocket socket = new CoreNodeSocket(socketUUID, AtlasNode.getAtlas(), config, host, externalHost);
		socket.open();
		registerSocket(socket);
		return socket;
	}

}
