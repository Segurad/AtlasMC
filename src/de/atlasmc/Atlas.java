package de.atlasmc;

import java.util.List;

import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.plugin.messenger.Messenger;

public class Atlas {

	private static LocalAtlasNode instance;
	private static AtlasNetwork network;
	
	public Atlas(LocalAtlasNode node, AtlasNetwork network) {
		if (instance != null) throw new RuntimeException("Atlas already initiated!");
		instance = node;
		Atlas.network = network;
	}
	
	public static LocalAtlasNode getAtlas() {
		return instance;
	}
	
	public List<LocalServer> getServers() {
		return instance.getServers();
	}

	public static ProtocolAdapter getProtocolAdapter(int version) {
		return instance.getProtocolAdapterHandler().getProtocol(version);
	}
	
	public static ProtocolAdapterHandler getProtocolAdapterHandler() {
		return instance.getProtocolAdapterHandler();
	}

	public static Messenger getMessenger() {
		return instance.getMessenger();
	}

	public static AtlasNetwork getNetwork() {
		return network;
	}

}
