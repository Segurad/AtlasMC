package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketPing;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketPing<T extends AbstractPacketPing> implements PacketCodec<T> {

	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.id = in.readInt();
	}

	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.id);
	}
	
}
