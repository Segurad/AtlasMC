package de.atlasmc;

import de.atlasmc.server.AtlasNode;

public class Atlas implements AtlasNode {

	private static Atlas instance;
	private AtlasNode master;
	
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

}
