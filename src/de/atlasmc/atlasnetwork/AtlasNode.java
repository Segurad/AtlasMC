package de.atlasmc.atlasnetwork;

import java.util.List;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;

/**
 * Represents a AtlasNode
 *
 */
public interface AtlasNode {

	public List<? extends Server> getServers();
	
	public List<? extends Proxy> getProxies();
	
	public boolean isOnline();
	
}
