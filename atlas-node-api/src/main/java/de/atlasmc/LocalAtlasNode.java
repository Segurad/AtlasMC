package de.atlasmc;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.NodePlayer;
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
	
	NodeServer getLocalServer(UUID uuid);
	
	LocalProxy getLocalProxy(UUID uuid);

	NodePlayer getLocalPlayer(String name);
	
	NodePlayer getLocalPlayer(UUID name);
	
	void setStatus(NodeStatus status);

}
