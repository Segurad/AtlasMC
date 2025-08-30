package de.atlasmc.io;

import java.io.IOException;

import de.atlasmc.util.annotation.NotNull;
import io.netty.buffer.ByteBuf;

/**
 * Used for reading writing and creating of packets
 */
public interface PacketIO<P extends Packet> {
	
	/**
	 * Reads the packet from the given input.
	 * @param packet to read to
	 * @param in to read from
	 * @param con the connection this operation is performed for
	 * @throws IOException
	 */
	void read(@NotNull P packet, @NotNull ByteBuf in, @NotNull ConnectionHandler con) throws IOException;
	
	/**
	 * Writes the packet to the given output.
	 * @param packet to write
	 * @param out to write to
	 * @param con the connection this operation is performed for
	 * @throws IOException
	 */
	void write(@NotNull P packet, @NotNull ByteBuf out, @NotNull ConnectionHandler con) throws IOException;
	
	/**
	 * Creates a new packet
	 * @return packet
	 */
	@NotNull
	P createPacketData();
	
	/**
	 * The packet id this handler accepts
	 * @return id
	 */
	int getPacketID();
	
}
