package de.atlasmc.core.node;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import de.atlasmc.Atlas;
import de.atlasmc.node.AtlasNodeBuilder;
import de.atlasmc.node.LocalAtlasNode;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.node.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.node.server.NodeServer;
import de.atlasmc.node.server.NodeServerManager;

public class CoreLocalAtlasNode implements LocalAtlasNode {
	
	private final ProtocolAdapterHandler adapterHandler;
	private final SocketManager socketManager;
	private final NodeServerManager smanager;
	private final UUID uuid;
	private volatile NodeStatus status;
	
	public CoreLocalAtlasNode(AtlasNodeBuilder builder) {
		this.uuid = Objects.requireNonNull(builder.getUUID());
		this.adapterHandler = Objects.requireNonNull(builder.getProtocolAdapterHandler());
		this.smanager = Objects.requireNonNull(builder.getServerManager());
		this.socketManager = Objects.requireNonNull(builder.getProxyManager());
		this.status = NodeStatus.STARTING;
	}
	
	@Override
	public NodeServerManager getServerManager() {
		return smanager;
	}

	@Override
	public ProtocolAdapterHandler getProtocolAdapterHandler() {
		return adapterHandler;
	}

	@Override
	public NodeStatus getStatus() {
		return status;
	}
	
	@Override
	public NodeServer getServer(UUID uuid) {
		return smanager.getServer(uuid);
	}

	@Override
	public NodeSocket getSocket(UUID uuid) {
		return socketManager.getSocket(uuid);
	}
	
	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public NodePlayer getPlayer(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public NodePlayer getPlayer(UUID name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setStatus(NodeStatus status) {
		this.status = status;
	}

	@Override
	public PublicKey getPublicKey() {
		return Atlas.getKeyPair().getPublic();
	}

	@Override
	public SocketManager getSocketManager() {
		return socketManager;
	}

	@Override
	public Collection<NodePlayer> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPlayerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
