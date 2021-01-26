package de.atlasmc;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.atlasnetwork.AtlasNetwork;
import de.atlasmc.io.atlasnetwork.AtlasNode;
import de.atlasmc.io.atlasnetwork.Proxy;
import de.atlasmc.io.atlasnetwork.server.AtlasServer;

public class Atlas implements AtlasNode {

	private static Atlas instance;
	private AtlasNetwork network;
	private List<AtlasServer> localServers;
	
	public static Atlas getInstance() {
		return instance;
	}

	@Override
	public AtlasNode getMaster() {
		return network.getMaster();
	}

	@Override
	public boolean isMaster() {
		return network.getMaster() == this;
	}
	
	public List<AtlasServer> getServers() {
		return new ArrayList<AtlasServer>(localServers);
	}

	@Override
	public List<Proxy> getProxys() {
		// TODO Auto-generated method stub
		return null;
	}

}
