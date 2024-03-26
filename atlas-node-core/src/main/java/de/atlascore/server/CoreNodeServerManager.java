package de.atlascore.server;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.util.map.CopyOnWriteArrayListMultimap;
import de.atlasmc.util.map.Multimap;

public class CoreNodeServerManager implements NodeServerManager {
	
	private final Map<UUID, NodeServer> servers;
	private final Multimap<ServerGroup, NodeServer> serverByGroup;
	
	public CoreNodeServerManager() {
		this.servers = new ConcurrentHashMap<>();
		this.serverByGroup = new CopyOnWriteArrayListMultimap<>();
	}
	
	@Override
	public Collection<NodeServer> getServers() {
		return servers.values();
	}

	@Override
	public NodeServer getServer(UUID uuid) {
		return servers.get(uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends NodeServer> T getServer(UUID uuid, Class<T> clazz) {
		NodeServer server = servers.get(uuid);
		if (clazz.isInstance(server))
			return (T) server;
		return null;
	}

	@Override
	public Collection<NodeServer> getServers(ServerGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeServer deployServer(UUID uuid, ServerGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

}
