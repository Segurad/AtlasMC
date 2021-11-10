package de.atlasmc.atlasnetwork.proxy;

import de.atlasmc.atlasnetwork.AtlasNode;

/**
 * A Proxy in this Network
 */
public interface Proxy {
	
	public AtlasNode getNode();
	
	public int getPort();
	
	public String getHost();

}
