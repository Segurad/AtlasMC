package de.atlasmc.network;

import java.security.cert.Certificate;
import java.util.List;
import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;

/**
 * Represents a AtlasNode
 *
 */
public interface AtlasNode {
	
	List<UUID> getSocketIDs();
	
	@NotNull
	NodeStatus getStatus();
	
	@NotNull
	Certificate getCertificate();

	@NotNull
	UUID getID();
	
	public static enum NodeStatus {
		OFFLINE,
		STARTING,
		ONLINE,
		SHUTING_DOWN;
	}
	
}
