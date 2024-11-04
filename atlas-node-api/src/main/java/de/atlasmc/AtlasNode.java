package de.atlasmc;

import java.util.UUID;

import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.proxy.ProxyManager;
import de.atlasmc.server.NodeServerManager;

public class AtlasNode {
	
	private static LocalAtlasNode INSTANCE;
	
	private AtlasNode() {}
	
	static void init(LocalAtlasNode node) {
		if (node == null) 
			throw new IllegalArgumentException("Node can not be null!");
		if (INSTANCE != null) 
			throw new IllegalStateException("Atlas already initialized!");
		synchronized (Atlas.class) {
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

	public static ProtocolAdapter getProtocolAdapter(int version) {
		return INSTANCE.getProtocolAdapterHandler().getProtocol(version);
	}
	
	public static ProtocolAdapterHandler getProtocolAdapterHandler() {
		return INSTANCE.getProtocolAdapterHandler();
	}

	public static ProxyManager getProxyManager() {
		return INSTANCE.getProxyManager();
	}

}
