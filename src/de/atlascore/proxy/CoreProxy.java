package de.atlascore.proxy;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.proxy.Proxy;

public class CoreProxy implements Proxy {
	
	private final int port;
	private final String host;
	private final AtlasNode node;
	
	public CoreProxy(AtlasNode node, String host, int port) {
		this.host = host;
		this.port = port;
		this.node = node;
	}
	
	@Override
	public AtlasNode getNode() {
		return node;
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
	@Override
	public String getHost() {
		return host;
	}

}
