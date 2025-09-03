package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;

/**
 * Represents a AtlasNode
 *
 */
public interface AtlasNode {
	
	@NotNull
	NodeStatus getStatus();
	
	@NotNull
	PublicKey getPublicKey();

	@NotNull
	UUID getUUID();
	
	public static enum NodeStatus {
		OFFLINE,
		STARTING,
		ONLINE,
		SHUTING_DOWN;
	}
	
}
