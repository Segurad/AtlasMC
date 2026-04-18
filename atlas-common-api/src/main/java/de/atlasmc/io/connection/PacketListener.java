package de.atlasmc.io.connection;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.util.annotation.NotNull;

/**
 * Handles packets of a {@link ConnectionHandler}
 */
public interface PacketListener {
	
	/**
	 * Handle a {@link Packet} this packet may or may not be sync
	 * @param packet
	 * @throws IOException
	 */
	void handlePacket(ConnectionHandler handler, @NotNull Packet packet) throws IOException;

}
