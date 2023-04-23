package de.atlascore.atlasnetwork;

import java.util.List;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;

public class CoreRemoteAtlasNode implements AtlasNode {
	
	private boolean online;
	
	@Override
	public List<? extends Server> getServers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Proxy> getProxies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnline() {
		return online;
	}

}
