package de.atlasmc.node.server;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import de.atlasmc.network.server.ServerGroup;

public interface NodeServerManager {
	
	Collection<NodeServer> getServers();
	
	NodeServer getServer(UUID uuid);
	
	<T extends NodeServer> T getServer(UUID uuid, Class<T> clazz);
	
	Collection<NodeServer> getServers(ServerGroup group);

	NodeServer deployServer(ServerGroup group);
	
	Set<String> getPreferedServerGroups();
	
	boolean addPreferedServerGroup(String group);
	
	boolean removePreferedServerGroup(String group);
	
	boolean hasPreferedServerGroup(String group);

}
