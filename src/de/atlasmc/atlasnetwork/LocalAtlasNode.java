package de.atlasmc.atlasnetwork;

import java.util.List;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.plugin.messenger.Messenger;

public class LocalAtlasNode implements AtlasNode {
	
	private final ProtocolAdapterHandler adapterHandler;
	private final Messenger messenger;
	
	public LocalAtlasNode() {
		this.adapterHandler = new ProtocolAdapterHandler();
		this.messenger = new Messenger();
	}
	
	@Override
	public List<LocalServer> getServers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Proxy> getProxys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServerGroup> getAvailableGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	public ProtocolAdapterHandler getProtocolAdapterHandler() {
		return adapterHandler;
	}

	public Messenger getMessenger() {
		return messenger;
	}

	@Override
	public boolean isOnline() {
		return true;
	}

}
