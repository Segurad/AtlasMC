package de.atlascore.io;

import de.atlasmc.io.Packet;

/**
 * Used when large amounts of data should be transfered.
 * The {@link ConnectionHandler} will request the packet when the last one was written.
 * This should avoid buffering a lot of not send packets.
 * @param <P>
 */
public interface PacketChunker<P extends Packet> {
	
	/**
	 * Returns the next chunk of data or null of no chunk is available.
	 * If null is returned and the chunker is not at it's end.
	 * The chunker must be re send to the connection
	 * @return chunk or null
	 */
	public Packet nextChunk();

}
