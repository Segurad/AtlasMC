package de.atlasmc.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 * Abstract base for packet handling
 * @param <P> the packet type
 */
public interface PacketIO<P extends Packet> {
	
	void read(P packet, ByteBuf in, ConnectionHandler con) throws IOException;
	
	void write(P packet, ByteBuf out, ConnectionHandler con) throws IOException;
	
	P createPacketData();
	
	int getPacketID();
	
}
