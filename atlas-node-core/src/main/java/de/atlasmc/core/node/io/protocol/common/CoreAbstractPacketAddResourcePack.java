package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.codec.UUIDCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketAddResourcePack;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketAddResourcePack<T extends AbstractPacketAddResourcePack> implements PacketCodec<T> {

	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.uuid = UUIDCodec.readUUID(in);
		packet.url = StringCodec.readString(in);
		packet.hash = StringCodec.readString(in, 40);
		packet.force = in.readBoolean();
		if (in.readBoolean()) {
			packet.promptMessage = Chat.STREAM_CODEC.deserialize(in, con.getCodecContext());
		}
	}

	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		UUIDCodec.writeUUID(packet.uuid, out);
		StringCodec.writeString(packet.url, out);
		StringCodec.writeString(packet.hash, out, 40);
		out.writeBoolean(packet.force);
		if (packet.promptMessage != null) {
			out.writeBoolean(true);
			packet.promptMessage.writeToStream(out, con.getCodecContext());
		} else {
			out.writeBoolean(false);
		}
	}

}
