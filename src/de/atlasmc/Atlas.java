package de.atlasmc;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.server.AtlasNetwork;
import de.atlasmc.server.AtlasNode;
import de.atlasmc.server.AtlasServer;

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

}
