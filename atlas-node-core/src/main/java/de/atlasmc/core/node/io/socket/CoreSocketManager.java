package de.atlasmc.core.node.io.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.util.configuration.ConfigurationException;

public class CoreSocketManager implements SocketManager {
	
	private static final int MAX_RANDOM_PORT_TRIES = 10;
	private static final int RANDOM_PORT_RANGE_START = 25000;
	private static final int RANDOM_PORT_RANGE_END = 35000;
	
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
	public NodeSocket createSocket(SocketConfig config) {
		int port = config.getPort();
		if (port == -1)
			port = findRandomPort();
		UUID socketUUID = UUID.randomUUID();
		NodeSocket socket = new CoreNodeSocket(socketUUID, AtlasNode.getAtlas(), port, config);
		socket.open();
		registerSocket(socket);
		return socket;
	}
	
	private static int findRandomPort() {
		final int portRange = RANDOM_PORT_RANGE_END - RANDOM_PORT_RANGE_START;
		int maxTries = MAX_RANDOM_PORT_TRIES;
		Random rand = new Random();
		while (maxTries > 0) {
			maxTries--;
			int port = RANDOM_PORT_RANGE_START + rand.nextInt(portRange);
			if (isPortAvailable(port)) {
				return port;
			}
		}
		throw new ConfigurationException("Unable to fetch random open port! (" + MAX_RANDOM_PORT_TRIES + " tries)");
	}
	
	private static boolean isPortAvailable(int port) {
	    try (Socket s = new Socket("localhost", port)) {
	        return false; 
	    } catch (IOException e) {
	        return true;
	    }
	}

}
