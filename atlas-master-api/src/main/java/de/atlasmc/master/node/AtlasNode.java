package de.atlasmc.master.node;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.master.socket.NodeSocket;

public interface AtlasNode extends de.atlasmc.network.AtlasNode {

	long getMaxHeapSize();

	long getUsedHeap();

	void deployServer(ServerGroup group);
	
	Server getServer(UUID uuid);
	
	Collection<Server> getServers();
	
	NodeSocket getProxy(UUID uuid);
	
	Collection<NodeSocket> getProxies();
	
	Collection<ServerGroup> getServerGroups(); 

}
