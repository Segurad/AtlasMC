package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieData;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketCookieResponse<T extends AbstractPacketCookieData> implements PacketCodec<T> {

	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		NamespacedKey.STREAM_CODEC.serialize(packet.key, out, null);
		if (packet.payload == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			writeVarInt(packet.payload.readableBytes(), out);
			out.writeBytes(packet.payload);
		}
	}
	
	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.key = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
		if (in.readBoolean()) {
			int size = readArrayLength(in, 5120);
			packet.payload = in.readBytes(size);
		}
	}
	
}
