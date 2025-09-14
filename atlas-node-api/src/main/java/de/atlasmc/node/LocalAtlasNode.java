package de.atlasmc.node;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.network.AtlasNode;
import de.atlasmc.node.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.node.server.NodeServer;
import de.atlasmc.node.server.NodeServerManager;
import de.atlasmc.util.annotation.NotNull;

public interface LocalAtlasNode extends AtlasNode {
	
	@NotNull
	NodeServerManager getServerManager();
	
	@NotNull
	SocketManager getSocketManager();

	@NotNull
	ProtocolAdapterHandler getProtocolAdapterHandler();
	
	@NotNull
	NodeSocket getSocket(UUID uuid);

	@NotNull
	NodeServer getServer(UUID uuid);
	
	@NotNull
	NodePlayer getPlayer(String name);
	
	@NotNull
	NodePlayer getPlayer(UUID name);
	
	@NotNull
	Collection<NodePlayer> getPlayers();
	
	int getPlayerCount();
	
	void setStatus(NodeStatus status);

}
