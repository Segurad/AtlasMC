package de.atlasmc;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.proxy.ProxyManager;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;

public interface LocalAtlasNode extends AtlasNode {
	
	NodeServerManager getServerManager();
	
	ProxyManager getProxyManager();

	ProtocolAdapterHandler getProtocolAdapterHandler();
	
	NodeServer getServer(UUID uuid);
	
	LocalProxy getProxy(UUID uuid);

	NodePlayer getPlayer(String name);
	
	NodePlayer getPlayer(UUID name);
	
	Collection<NodePlayer> getPlayers();
	
	int getPlayerCount();
	
	void setStatus(NodeStatus status);

}
