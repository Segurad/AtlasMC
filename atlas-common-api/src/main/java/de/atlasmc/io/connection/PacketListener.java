package de.atlasmc.io.connection;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.log.Log;
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
	
	/**
	 * Handle a {@link Packet} this packet is sync to some context calling {@link ConnectionHandler#handleSyncPackets(Log)}
	 * @param packet
	 * @throws IOException
	 */
	void handlePacketSync(ConnectionHandler handler, @NotNull Packet packet) throws IOException;

}
