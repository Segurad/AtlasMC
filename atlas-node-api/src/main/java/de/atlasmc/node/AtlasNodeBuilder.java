package de.atlasmc.node;

import java.security.KeyPair;
import java.util.UUID;

import de.atlasmc.node.io.protocol.ProtocolAdapterManager;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.node.server.NodeServerManager;
import de.atlasmc.util.Builder;

public class AtlasNodeBuilder implements Builder<LocalAtlasNode> {
	
	private NodeServerManager serverManager;
	private SocketManager proxyManager;
	private ProtocolAdapterManager protocolAdapterManager;
	private UUID uuid;
	private LocalAtlasNodeFactory factory;
	private KeyPair playerAuthKey;
	
	public AtlasNodeBuilder setServerManager(NodeServerManager serverManager) {
		this.serverManager = serverManager;
		return this;
	}
	
	public AtlasNodeBuilder setProtocolAdapterManager(ProtocolAdapterManager protocolAdapterManager) {
		this.protocolAdapterManager = protocolAdapterManager;
		return this;
	}
	
	public AtlasNodeBuilder setSocketManager(SocketManager proxyManager) {
		this.proxyManager = proxyManager;
		return this;
	}
	
	public NodeServerManager getServerManager() {
		return serverManager;
	}
	
	public ProtocolAdapterManager getProtocolAdapterManager() {
		return protocolAdapterManager;
	}
	
	public SocketManager getProxyManager() {
		return proxyManager;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public AtlasNodeBuilder setUUID(UUID uuid) {
		this.uuid = uuid;
		return this;
	}
	
	public LocalAtlasNodeFactory getFactory() {
		return factory;
	}
	
	public AtlasNodeBuilder setFactory(LocalAtlasNodeFactory factory) {
		this.factory = factory;
		return this;
	}
	
	@Override
	public LocalAtlasNode build() {
		if (factory == null)
			throw new IllegalStateException("No node factory defined!");
		return factory.createNode(this);
	}
	
	public AtlasNodeBuilder setPlayerAuthKey(KeyPair playerAuthKey) {
		this.playerAuthKey = playerAuthKey;
		return this;
	}

	public KeyPair getPlayerAuthKey() {
		return playerAuthKey;
	}

}
