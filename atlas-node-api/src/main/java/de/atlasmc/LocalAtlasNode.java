package de.atlasmc;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.socket.NodeSocket;
import de.atlasmc.socket.SocketManager;
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
