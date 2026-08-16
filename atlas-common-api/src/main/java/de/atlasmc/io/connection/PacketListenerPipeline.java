package de.atlasmc.io.connection;

import de.atlasmc.io.Packet;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.pipeline.Pipeline;

@ThreadSafe
public interface PacketListenerPipeline extends Pipeline<PacketListener> {
	
	/**
	 * Handles a packet
	 * @param handler
	 * @param packet
	 * @return if the packet was handled or not
	 */
	boolean handlePacket(ConnectionHandler handler, Packet packet);
	
	boolean handlePacketSync(ConnectionHandler handler, Packet packet);
	
}
