package de.atlasmc.io;

import de.atlasmc.util.annotation.Nullable;

/**
 * A network protocol
 */
public interface Protocol {
	
	/**
	 * Returns a packet handler for the given id.
	 * @param id
	 * @return handler or null
	 */
	@Nullable
	PacketIO<? extends Packet> getHandlerServerbound(int id);
	
	/**
	 * Returns the packet handler for the given id.
	 * @param id
	 * @return handler or null
	 */
	@Nullable
	PacketIO<? extends Packet> getHandlerClientbound(int id);
	
	/**
	 * Returns a new packet for the given id.
	 * @param id
	 * @return packet or null
	 */
	@Nullable
	Packet createPacketServerbound(int id);
	
	/**
	 * Returns a new packet for the given id.
	 * @param id
	 * @return packet or null
	 */
	@Nullable
	Packet createPacketClientbound(int id);
	
	/**
	 * Returns the protocols version id
	 * @return version id
	 */
	int getVersion();
	
	/**
	 * Creates the default packet listener for this protocol
	 * @param o
	 * @return listener
	 */
	@Nullable
	PacketListener createDefaultPacketListenerServerbound(Object o);
	
	/**
	 * Creates the default packet listener for this protocol
	 * @param o
	 * @return listener
	 */
	@Nullable
	PacketListener createDefaultPacketListenerClientbound(Object o);

}
