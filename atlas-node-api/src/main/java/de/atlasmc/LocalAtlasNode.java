package de.atlasmc;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;

public interface LocalAtlasNode extends AtlasNode {
	
	NodeServerManager getServerManager();
	
	Collection<NodeServer> getServers();

	Collection<LocalProxy> getProxies();

	ProtocolAdapterHandler getProtocolAdapterHandler();

	void registerProxy(LocalProxy proxy);
	
	NodeServer getServer(UUID uuid);
	
	LocalProxy getProxy(UUID uuid);

	NodePlayer getPlayer(String name);
	
	NodePlayer getPlayer(UUID name);
	
	void setStatus(NodeStatus status);

}
