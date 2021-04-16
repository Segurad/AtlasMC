package de.atlasmc.atlasnetwork;

import java.util.List;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;

/**
 * Represents a AtlasNode
 * @author Segurad
 *
 */
public interface AtlasNode {

	public List<? extends Server> getServers();
	public List<Proxy> getProxys();
	public List<ServerGroup> getAvailableGroups();
	
}
