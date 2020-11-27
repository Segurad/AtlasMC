package de.atlasmc;

import de.atlasmc.server.AtlasNetwork;
import de.atlasmc.server.AtlasNode;
import de.atlasmc.world.World;

public class Atlas implements AtlasNode {

	private static Atlas instance;
	private AtlasNetwork network;
	
	public static AtlasNode getNode() {
		return instance;
	}

	@Override
	public boolean isThis() {
		return true;
	}

	@Override
	public AtlasNode getMaster() {
		return null;
	}

	@Override
	public boolean isMaster() {
		return false;
	}

	public static World getWorld(String world) {
		// TODO Auto-generated method stub
		return null;
	}

}
