package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketText;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketText<T extends AbstractPacketText> implements PacketCodec<T> {

	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.text = Chat.STREAM_CODEC.deserialize(in, con.getCodecContext());
	}

	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		Chat.STREAM_CODEC.serialize(packet.text, out, con.getCodecContext());
	}

}
