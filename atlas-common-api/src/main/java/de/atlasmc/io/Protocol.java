package de.atlasmc.io;

import de.atlasmc.util.annotation.Nullable;

/**
 * A network protocol
 */
public interface Protocol {
	
	/**
	 * Returns a packet codec for the given id.
	 * @param id
	 * @return codec or null
	 */
	@Nullable
	PacketCodec<? extends Packet> getCodecServerbound(int id);
	
	/**
	 * Returns the packet codec for the given id.
	 * @param id
	 * @return codec or null
	 */
	@Nullable
	PacketCodec<? extends Packet> getCodecClientbound(int id);
	
	/**
	 * Returns a packet codec for the given default id.
	 * @param id
	 * @return codec or null
	 */
	@Nullable
	PacketCodec<? extends Packet> getCodecServerboundByDefault(int id);
	
	/**
	 * Returns the packet codec for the given default id.
	 * @param id
	 * @return codec or null
	 */
	@Nullable
	PacketCodec<? extends Packet> getCodecClientboundByDefault(int id);
	
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
