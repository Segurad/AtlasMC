package de.atlasmc.atlasnetwork.proxy;

import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.util.annotation.NotNull;

/**
 * A Socket in this Network
 */
public interface AtlasSocket {
	
	@NotNull
	AtlasNode getNode();
	
	int getPort();
	
	@NotNull
	UUID getUUID();
	
	@NotNull
	String getHost();

}
