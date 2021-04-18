package de.atlasmc.atlasnetwork;

import java.util.List;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;

public class LocalAtlasNode implements AtlasNode {
	
	private final ProtocolAdapterHandler adapterHandler;
	
	public LocalAtlasNode() {
		this.adapterHandler = new ProtocolAdapterHandler();
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

}
