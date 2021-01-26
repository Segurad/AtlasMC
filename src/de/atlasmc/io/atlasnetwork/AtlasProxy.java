package de.atlasmc.io.atlasnetwork;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;

public class AtlasProxy implements Proxy {

	private final AtlasNode node;
	private final int port;
	private List<AtlasPlayer> connections;
	
	public AtlasProxy(AtlasNode node, int port) {
		this.node = node;
		this.port = port;
		this.connections = new ArrayList<AtlasPlayer>();
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
	public List<AtlasPlayer> getConnections() {
		return connections;
	}

}
