package de.atlasmc.atlasnetwork.proxy;

import de.atlasmc.atlasnetwork.AtlasNode;

public class Proxy {
	
	private final int port;
	private final String host;
	
	public Proxy(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public AtlasNode getNode() {
		return null;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getHost() {
		return host;
	}

}
