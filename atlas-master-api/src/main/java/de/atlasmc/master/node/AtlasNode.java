package de.atlasmc.master.node;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.master.proxy.Proxy;
import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerGroup;

public interface AtlasNode extends de.atlasmc.network.AtlasNode {

	long getMaxHeapSize();

	long getUsedHeap();

	void deployServer(ServerGroup group);
	
	Server getServer(UUID uuid);
	
	Collection<Server> getServers();
	
	Proxy getProxy(UUID uuid);
	
	Collection<Proxy> getProxies();
	
	Collection<ServerGroup> getServerGroups(); 

}
