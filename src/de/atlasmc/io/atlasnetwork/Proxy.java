package de.atlasmc.io.atlasnetwork;

import java.util.List;

import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;

public interface Proxy {
	
	public AtlasNode getNode();
	public int getPort();
	public List<AtlasPlayer> getConnections();

}
