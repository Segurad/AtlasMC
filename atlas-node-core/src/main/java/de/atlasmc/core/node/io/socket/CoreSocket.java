package de.atlasmc.core.node.io.socket;

import java.util.UUID;

import de.atlasmc.network.AtlasNode;
import de.atlasmc.network.socket.AtlasSocket;

public class CoreSocket implements AtlasSocket {
	
	protected final int port;
	protected final String host;
	protected final AtlasNode node;
	protected final UUID uuid;
	
	public CoreSocket(UUID uuid, AtlasNode node, String host, int port) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (node == null)
			throw new IllegalArgumentException("Node can not be null!");
		if (host == null)
			throw new IllegalArgumentException("Host can not be null!");
		if (port < 0 || port > 0xFFFF)
			throw new IllegalArgumentException("Port must be between 0 and 65535: " + port);
		this.host = host;
		this.port = port;
		this.node = node;
		this.uuid = uuid;
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

	@Override
	public UUID getUUID() {
		return uuid;
	}

}
