package de.atlasmc;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;

public class Atlas implements AtlasNode {

	private static Atlas instance;
	private AtlasNetwork network;
	private List<LocalServer> localServers;
	
	public static Atlas getInstance() {
		return instance;
	}
	
	public List<LocalServer> getServers() {
		return new ArrayList<LocalServer>(localServers);
	}

	@Override
	public List<Proxy> getProxys() {
		return null;
	}

	@Override
	public List<ServerGroup> getAvailableGroups() {
		// TODO Auto-generated method stub
		return null;
	}

}
