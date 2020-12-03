package de.atlasmc.server;

import java.util.List;

public interface AtlasNode {

	public AtlasNode getMaster();
	public boolean isMaster();
	public List<? extends Server> getServers();
	
}
