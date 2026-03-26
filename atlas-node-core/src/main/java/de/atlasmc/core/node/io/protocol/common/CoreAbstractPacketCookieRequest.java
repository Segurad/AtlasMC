package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieRequest;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketCookieRequest<T extends AbstractPacketCookieRequest> implements PacketCodec<T> {
	
	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		NamespacedKey.STREAM_CODEC.serialize(packet.key, out, null);
	}
	
	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.key = NamespacedKey.STREAM_CODEC.deserialize(in);
	}

}
