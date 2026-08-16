package de.atlasmc.io.connection;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.util.annotation.NotNull;

/**
 * Handles packets of a {@link ConnectionHandler}
 */
public interface PacketListener {
	
	/**
	 * Handle a {@link Packet} this packet may or may not be sync.
	 * @param handler
	 * @param packet
	 * @param handled whether or not the packed was handled by a previous handler
	 * @throws IOException
	 * @return whether or not this packet was handled. If the handler does not change the handled state it should return the given state.
	 */
	boolean handlePacket(ConnectionHandler handler, @NotNull Packet packet, boolean handled) throws IOException;
	
	/**
	 * Handle a {@link Packet} this packet sync
	 * @param handler
	 * @param packet
	 * @param handled whether or not the packed was handled by a previous handler
	 * @throws IOException
	 * @return whether or not this packet was handled. If the handler does not change the handled state it should return the given state.
	 */
	boolean handlePacketSync(ConnectionHandler handler, @NotNull Packet packet, boolean handled) throws IOException;

}
