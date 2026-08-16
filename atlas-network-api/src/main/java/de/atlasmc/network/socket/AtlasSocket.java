package de.atlasmc.network.socket;

import java.net.InetSocketAddress;
import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;

/**
 * A Socket in this Network
 */
public interface AtlasSocket {
	
	@NotNull
	UUID getNodeID();
	
	@NotNull
	UUID getUUID();
	
	@NotNull
	InetSocketAddress getHost();
	
	@NotNull
	InetSocketAddress getExternalHost();

}
