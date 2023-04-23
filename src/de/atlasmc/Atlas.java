package de.atlasmc;

import java.io.File;
import java.security.KeyPair;
import java.util.List;

import org.slf4j.Logger;

import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.scheduler.Scheduler;

public class Atlas {

	private static LocalAtlasNode INSTANCE;
	private static AtlasNetwork NETWORK;
	
	private Atlas() {}
	
	public static void init(LocalAtlasNode node, AtlasNetwork network) {
		if (node == null) 
			throw new IllegalArgumentException("Node can not be null!");
		if (network == null)
			throw new IllegalArgumentException("Network can not be null!");
		if (INSTANCE != null) 
			throw new IllegalStateException("Atlas already initialized!");
		synchronized (Atlas.class) {
			if (INSTANCE != null)
				throw new IllegalStateException("Atlas already initialized!");
			Atlas.INSTANCE = node;
			Atlas.NETWORK = network;
		}
	}
	
	public static LocalAtlasNode getAtlas() {
		return INSTANCE;
	}
	
	public List<LocalServer> getServers() {
		return INSTANCE.getServers();
	}

	public static ProtocolAdapter getProtocolAdapter(int version) {
		return INSTANCE.getProtocolAdapterHandler().getProtocol(version);
	}
	
	public static ProtocolAdapterHandler getProtocolAdapterHandler() {
		return INSTANCE.getProtocolAdapterHandler();
	}

	public static Scheduler getScheduler() {
		return INSTANCE.getScheduler();
	}

	public static Logger getLogger() {
		return INSTANCE.getLogger();
	}

	public static AtlasNetwork getNetwork() {
		return NETWORK;
	}

	public static File getWorkDir() {
		return INSTANCE.getWorkDir();
	}
	
	public static KeyPair getKeyPair() {
		return INSTANCE.getKeyPair();
	}
	
}
