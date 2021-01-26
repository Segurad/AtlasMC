package de.atlasmc.io.atlasnetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Atlas;
import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;

public class LocalAtlasProxy implements Proxy {
	
	private final int port;
	private final List<AtlasPlayer> connections;
	private ServerSocket socket;
	
	public LocalAtlasProxy(int port) {
		this.port = port;
		this.connections = new ArrayList<AtlasPlayer>();
	}
	
	public boolean start() {
		if (socket != null) {
			if (!socket.isClosed()) return false;
		}
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public AtlasNode getNode() {
		return Atlas.getInstance();
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
