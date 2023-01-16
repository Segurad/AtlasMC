package de.atlasmc.io;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import io.netty.buffer.ByteBuf;

/**
 * Abstract base for packet handling
 * @param <P> the packet type
 */
public abstract class PacketIO<P extends Packet> {

	public abstract void read(P packet, ByteBuf in, ConnectionHandler con) throws IOException;
	
	public abstract void write(P packet, ByteBuf out, ConnectionHandler con) throws IOException;
	
	public abstract P createPacketData();
	
}
