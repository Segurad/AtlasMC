package de.atlasmc.node.server;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.network.server.ServerGroup;

public interface NodeServerManager {
	
	Collection<NodeServer> getServers();
	
	NodeServer getServer(UUID uuid);
	
	<T extends NodeServer> T getServer(UUID uuid, Class<T> clazz);
	
	Collection<NodeServer> getServers(ServerGroup group);

	NodeServer deployServer(ServerGroup group);

	boolean registerServerGroup(ServerGroup group);
	
	boolean unregisterServerGroup(ServerGroup group);
	
	Collection<ServerGroup> getServerGroups();
	
	ServerGroup getServerGroup(String name);

}
