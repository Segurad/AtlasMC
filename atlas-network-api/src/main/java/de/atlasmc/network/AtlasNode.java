package de.atlasmc.network;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Represents a AtlasNode
 *
 */
public interface AtlasNode {
	
	/**
	 * Returns the id of the main socket of this node.
	 * May be null if there is no active socket.
	 * @return id or null
	 */
	@Nullable
	UUID getSocketID();
	
	@NotNull
	NodeStatus getStatus();
	
	@NotNull
	PublicKey getPublicKey();

	@NotNull
	UUID getID();
	
	public static enum NodeStatus {
		OFFLINE,
		STARTING,
		ONLINE,
		SHUTING_DOWN;
	}
	
}
