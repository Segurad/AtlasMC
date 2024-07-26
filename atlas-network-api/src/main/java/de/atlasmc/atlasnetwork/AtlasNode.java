package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

/**
 * Represents a AtlasNode
 *
 */
public interface AtlasNode {
	
	NodeStatus getStatus();
	
	PublicKey getPublicKey();

	UUID getUUID();
	
	public static enum NodeStatus {
		OFFLINE,
		STARTING,
		ONLINE
	}
	
}
