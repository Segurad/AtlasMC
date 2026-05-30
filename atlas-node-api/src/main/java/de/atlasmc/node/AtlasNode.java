package de.atlasmc.node;

import java.util.UUID;

import de.atlasmc.node.io.protocol.ProtocolAdapterManager;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.node.server.NodeServerManager;

public class AtlasNode {
	
	private static LocalAtlasNode INSTANCE;
	
	private AtlasNode() {}
	
	static void init(LocalAtlasNode node) {
		if (node == null) 
			throw new IllegalArgumentException("Node can not be null!");
		if (INSTANCE != null) 
			throw new IllegalStateException("Atlas already initialized!");
		synchronized (AtlasNode.class) {
			if (INSTANCE != null)
				throw new IllegalStateException("Atlas already initialized!");
			AtlasNode.INSTANCE = node;
		}
	}
	
	public static NodePlayer getPlayer(UUID uuid) {
		return INSTANCE.getPlayer(uuid);
	}
	
	public static NodePlayer getLocalPlayer(String name) {
		return INSTANCE.getPlayer(name);
	}
	
	public static NodeServerManager getServerManager() {
		return INSTANCE.getServerManager();
	}
	
	public static LocalAtlasNode getAtlas() {
		return INSTANCE;
	}
	
	public static ProtocolAdapterManager getProtocolAdapterManager() {
		return INSTANCE.getProtocolAdapterManager();
	}

	public static SocketManager getSocketManager() {
		return INSTANCE.getSocketManager();
	}

}
