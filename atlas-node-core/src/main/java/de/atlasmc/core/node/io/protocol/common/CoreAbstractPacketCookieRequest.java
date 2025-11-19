package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieRequest;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketCookieRequest<T extends AbstractPacketCookieRequest> implements PacketIO<T> {
	
	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		NamespacedKey.STREAM_CODEC.serialize(packet.key, out, null);
	}
	
	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.key = NamespacedKey.STREAM_CODEC.deserialize(in);
	}

}
