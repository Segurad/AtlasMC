package de.atlasmc.atlasnetwork.proxy;

import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;

/**
 * A Proxy in this Network
 */
public interface Proxy {
	
	AtlasNode getNode();
	
	int getPort();
	
	UUID getUUID();
	
	String getHost();

}
