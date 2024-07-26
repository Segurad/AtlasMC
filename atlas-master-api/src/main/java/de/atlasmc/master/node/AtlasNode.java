package de.atlasmc.master.node;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.master.proxy.Proxy;
import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerGroup;

public interface AtlasNode extends de.atlasmc.atlasnetwork.AtlasNode {

	long getMaxHeapSize();

	long getUsedHeap();

	void deployServer(ServerGroup group);
	
	Server getServer(UUID uuid);
	
	Proxy getProxy(UUID uuid);
	
	Collection<ServerGroup> getServerGroups(); 

}
