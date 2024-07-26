package de.atlasmc;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;

public class AtlasNode {
	
	private static LocalAtlasNode INSTANCE;
	private static AtlasNetwork NETWORK;
	
	private AtlasNode() {}
	
	public static void init(LocalAtlasNode node, AtlasNetwork network) {
		if (node == null) 
			throw new IllegalArgumentException("Node can not be null!");
		if (INSTANCE != null) 
			throw new IllegalStateException("Atlas already initialized!");
		synchronized (Atlas.class) {
			if (INSTANCE != null)
				throw new IllegalStateException("Atlas already initialized!");
			AtlasNode.INSTANCE = node;
			AtlasNode.NETWORK = network;
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
	
	public Collection<NodeServer> getServers() {
		return INSTANCE.getServers();
	}

	public static ProtocolAdapter getProtocolAdapter(int version) {
		return INSTANCE.getProtocolAdapterHandler().getProtocol(version);
	}
	
	public static ProtocolAdapterHandler getProtocolAdapterHandler() {
		return INSTANCE.getProtocolAdapterHandler();
	}

	public static AtlasNetwork getNetwork() {
		return NETWORK;
	}

}
