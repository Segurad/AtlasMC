package de.atlasmc;

import java.util.List;

import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.plugin.messenger.Messenger;

public class Atlas {

	private static LocalAtlasNode instance;
	
	public Atlas(LocalAtlasNode node) {
		if (instance != null) throw new RuntimeException("Atlas already started!");
		instance = node;
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

}
