package de.atlasmc.core.node;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.node.AtlasNodeBuilder;
import de.atlasmc.node.LocalAtlasNode;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.node.io.protocol.ProtocolAdapterManager;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.node.server.NodeServer;
import de.atlasmc.node.server.NodeServerManager;

public class CoreLocalAtlasNode implements LocalAtlasNode {
	
	private final ProtocolAdapterManager adapterManager;
	private final SocketManager socketManager;
	private final NodeServerManager smanager;
	private final KeyPair playerAuthKey;
	private volatile NodeStatus status;
	
	public CoreLocalAtlasNode(AtlasNodeBuilder builder) {
		this.adapterManager = Objects.requireNonNull(builder.getProtocolAdapterManager(), "ProtocolAdapterManager");
		this.smanager = Objects.requireNonNull(builder.getServerManager(), "ServerManager");
		this.socketManager = Objects.requireNonNull(builder.getProxyManager(), "ProxyManager");
		this.playerAuthKey = Objects.requireNonNull(builder.getPlayerAuthKey(), "PlayerAuthKey");
		this.status = NodeStatus.STARTING;
	}
	
	@Override
	public NodeServerManager getServerManager() {
		return smanager;
	}

	@Override
	public ProtocolAdapterManager getProtocolAdapterManager() {
		return adapterManager;
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
	public UUID getID() {
		return AtlasNetwork.getNodeUUID();
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
	public Certificate getCertificate() {
		return AtlasNetwork.getNodeCertificate();
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

	@Override
	public List<UUID> getSocketIDs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyPair getPlayerAuthKey() {
		return playerAuthKey;
	}

}
