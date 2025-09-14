package de.atlasmc.core.node;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
	private final SocketManager proxyManager;
	private final Map<UUID, NodeSocket> proxies;
	private final NodeServerManager smanager;
	private final UUID uuid;
	private NodeStatus status;
	
	public CoreLocalAtlasNode(AtlasNodeBuilder builder) {
		this.uuid = builder.getUUID();
		this.adapterHandler = new ProtocolAdapterHandler();
		this.proxies = new ConcurrentHashMap<>();
		this.smanager = builder.getServerManager();
		this.proxyManager = builder.getProxyManager();
		this.status = NodeStatus.STARTING;
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (smanager == null)
			throw new IllegalArgumentException("ServerManager can not be null!");
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
		return proxies.get(uuid);
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
		return proxyManager;
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
