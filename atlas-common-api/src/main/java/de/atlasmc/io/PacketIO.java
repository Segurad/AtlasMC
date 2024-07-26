package de.atlasmc.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 * Used for reading writing and creating of packets
 */
public interface PacketIO<P extends Packet> {
	
	void read(P packet, ByteBuf in, ConnectionHandler con) throws IOException;
	
	void write(P packet, ByteBuf out, ConnectionHandler con) throws IOException;
	
	P createPacketData();
	
	int getPacketID();
	
}
