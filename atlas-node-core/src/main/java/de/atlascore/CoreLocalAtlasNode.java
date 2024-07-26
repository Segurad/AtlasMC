package de.atlascore;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.plugin.CoreNodeBuilder;
import de.atlasmc.Atlas;
import de.atlasmc.LocalAtlasNode;
import de.atlasmc.NodePlayer;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;

public class CoreLocalAtlasNode implements LocalAtlasNode {
	
	private final ProtocolAdapterHandler adapterHandler;
	private final Map<UUID, LocalProxy> proxies;
	private final NodeServerManager smanager;
	private final UUID uuid;
	private NodeStatus status;
	
	public CoreLocalAtlasNode(CoreNodeBuilder builder) {
		this.uuid = builder.getUUID();
		this.adapterHandler = new ProtocolAdapterHandler();
		this.proxies = new ConcurrentHashMap<>();
		this.smanager = builder.getServerManager();
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
	public Collection<LocalProxy> getProxies() {
		return proxies.values();
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
	public void registerProxy(LocalProxy proxy) {
		proxies.put(proxy.getUUID(), proxy);
	}
	
	@Override
	public NodeServer getServer(UUID uuid) {
		return smanager.getServer(uuid);
	}

	@Override
	public LocalProxy getProxy(UUID uuid) {
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
	public Collection<NodeServer> getServers() {
		return smanager.getServers();
	}

	@Override
	public void setStatus(NodeStatus status) {
		this.status = status;
	}

	@Override
	public PublicKey getPublicKey() {
		return Atlas.getKeyPair().getPublic();
	}

}
