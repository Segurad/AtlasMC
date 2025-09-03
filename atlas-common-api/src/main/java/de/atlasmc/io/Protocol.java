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
	PacketIO<? extends Packet> getHandlerIn(int id);
	
	/**
	 * Returns the packet handler for the given id.
	 * @param id
	 * @return handler or null
	 */
	@Nullable
	PacketIO<? extends Packet> getHandlerOut(int id);
	
	/**
	 * Returns a new packet for the given id.
	 * @param id
	 * @return packet or null
	 */
	@Nullable
	Packet createPacketIn(int id);
	
	/**
	 * Returns a new packet for the given id.
	 * @param id
	 * @return packet or null
	 */
	@Nullable
	Packet createPacketOut(int id);
	
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
	PacketListener createDefaultPacketListenerIn(Object o);
	
	/**
	 * Creates the default packet listener for this protocol
	 * @param o
	 * @return listener
	 */
	PacketListener createDefaultPacketListenerOut(Object o);

}
