package de.atlasmc;

import java.util.UUID;

import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.proxy.ProxyManager;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.util.Builder;

public class AtlasNodeBuilder implements Builder<LocalAtlasNode> {
	
	private NodeServerManager serverManager;
	private ProxyManager proxyManager;
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
	
	public AtlasNodeBuilder setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
		return this;
	}
	
	public NodeServerManager getServerManager() {
		return serverManager;
	}
	
	public ProtocolAdapterHandler getProtocolAdapterHandler() {
		return protocolAdapterHandler;
	}
	
	public ProxyManager getProxyManager() {
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
		return factory.createNode(this);
	}

	@Override
	public void clear() {
		serverManager = null;
		proxyManager = null;
		protocolAdapterHandler = null;
	}

}
