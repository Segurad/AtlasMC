package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketPluginMessage;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketPluginMessage<T extends AbstractPacketPluginMessage> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.channel = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		NamespacedKey.STREAM_CODEC.serialize(packet.channel, out, null);
		out.writeBytes(packet.data);
	}
	
}
