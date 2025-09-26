package de.atlascore.master.proxy;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.master.socket.NodeSocket;
import de.atlasmc.master.socket.SocketManager;

public class CoreSocketManager implements SocketManager {
	
	private Map<UUID, NodeSocket> sockets;
	private Map<String, SocketConfig> configs;

	public CoreSocketManager() {
		this.sockets = new ConcurrentHashMap<>();
		this.configs = new ConcurrentHashMap<>();
	}
	
	@Override
	public NodeSocket getSocket(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		return sockets.get(uuid);
	}

	@Override
	public SocketConfig getSocketConfig(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		return configs.get(name);
	}

	@Override
	public boolean addSocketConfig(SocketConfig config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		return configs.putIfAbsent(config.getName(), config) == null;
	}

	@Override
	public boolean removeSocketConfig(SocketConfig config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		return configs.remove(config.getName(), config);
	}

}
