package de.atlasmc.io;

import java.io.IOException;

import de.atlasmc.log.Log;

public interface PacketListener {
	
	/**
	 * Handle a {@link Packet}
	 * @param packet
	 * @throws IOException
	 */
	void handlePacket(Packet packet) throws IOException;
	
	/**
	 * Handle queued packets sync if needed
	 * @throws IOException
	 */
	void handleSyncPackets(Log logger);
	
	void handleUnregister();

	boolean hasSyncPackets();

}
