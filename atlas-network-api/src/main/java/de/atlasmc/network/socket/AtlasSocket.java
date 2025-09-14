package de.atlasmc.network.socket;

import java.util.UUID;

import de.atlasmc.network.AtlasNode;
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
