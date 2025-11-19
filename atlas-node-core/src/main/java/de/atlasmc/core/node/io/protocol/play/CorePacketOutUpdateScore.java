package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateScore;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateScore.NumberFormatType;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateScore implements PacketIO<PacketOutUpdateScore> {

	@Override
	public void read(PacketOutUpdateScore packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entry = StringCodec.readString(in);
		packet.objective = StringCodec.readString(in);
		packet.value = readVarInt(in);
		final CodecContext context = handler.getCodecContext();
		if (in.readBoolean())
			packet.displayName = Chat.STREAM_CODEC.deserialize(in, context);
		if (in.readBoolean()) {
			packet.formatType = NumberFormatType.getByID(readVarInt(in));
			if (packet.formatType != NumberFormatType.BLANK)
				packet.numberFormat = Chat.STREAM_CODEC.deserialize(in, context).toComponent();
		}
	}

	@Override
	public void write(PacketOutUpdateScore packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		StringCodec.writeString(packet.entry, out);
		StringCodec.writeString(packet.objective, out);
		writeVarInt(packet.value, out);
		final CodecContext context = handler.getCodecContext();
		if (packet.displayName != null) {
			out.writeBoolean(true);
			Chat.STREAM_CODEC.serialize(packet.displayName, out, context);
		} else {
			out.writeBoolean(false);
		}
		if (packet.formatType != null) {
			out.writeBoolean(true);
			if (packet.formatType != NumberFormatType.BLANK);
				Chat.STREAM_CODEC.serialize(packet.numberFormat, out, context);
		} else {
			out.writeBoolean(false);
		}
	}

	@Override
	public PacketOutUpdateScore createPacketData() {
		return new PacketOutUpdateScore();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateScore.class);
	}

}
