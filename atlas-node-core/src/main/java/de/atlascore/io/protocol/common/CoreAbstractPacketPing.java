package de.atlascore.io.protocol.common;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.common.AbstractPacketPing;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketPing<T extends AbstractPacketPing> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.id = in.readInt();
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.id);
	}
	
}
