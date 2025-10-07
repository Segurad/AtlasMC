package de.atlasmc.io;

import java.io.IOException;

import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.NotNull;

/**
 * Handles packets of a {@link ConnectionHandler}
 */
public interface PacketListener {
	
	/**
	 * Handle a {@link Packet}
	 * @param packet
	 * @throws IOException
	 */
	void handlePacket(@NotNull Packet packet) throws IOException;
	
	/**
	 * Handle queued packets sync if needed
	 * @throws IOException
	 */
	void handleSyncPackets(@NotNull Log logger);
	
	/**
	 * Called when this listener is unregistered
	 */
	void handleUnregister();

	/**
	 * Returns whether or not this lister has packets stored for sync handling
	 * @return true if has
	 */
	boolean hasSyncPackets();

}
