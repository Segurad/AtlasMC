package de.atlasmc.node;

import java.util.UUID;

import de.atlasmc.node.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.node.server.NodeServerManager;
import de.atlasmc.util.Builder;

public class AtlasNodeBuilder implements Builder<LocalAtlasNode> {
	
	private NodeServerManager serverManager;
	private SocketManager proxyManager;
	private ProtocolAdapterHandler protocolAdapterHandler;
	private UUID uuid;
	private LocalAtlasNodeFactory factory;
	
	public AtlasNodeBuilder setServerManager(NodeServerManager serverManager) {
		this.serverManager = serverManager;
		return this;
	}
	
	public AtlasNodeBuilder setProtocolAdapterHandler(ProtocolAdapterHandler protocolAdapterHandler) {
		this.protocolAdapterHandler = protocolAdapterHandler;
		return this;
	}
	
	public AtlasNodeBuilder setProxyManager(SocketManager proxyManager) {
		this.proxyManager = proxyManager;
		return this;
	}
	
	public NodeServerManager getServerManager() {
		return serverManager;
	}
	
	public ProtocolAdapterHandler getProtocolAdapterHandler() {
		return protocolAdapterHandler;
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

}
