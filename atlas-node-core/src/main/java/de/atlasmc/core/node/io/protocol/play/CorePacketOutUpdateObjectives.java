package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateObjectives;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateObjectives.Mode;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateScore.NumberFormatType;
import de.atlasmc.node.scoreboard.RenderType;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateObjectives implements PacketIO<PacketOutUpdateObjectives> {

	@Override
	public void read(PacketOutUpdateObjectives packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.name = readString(in, MAX_IDENTIFIER_LENGTH);
		packet.mode = Mode.getByID(in.readByte());
		if (packet.mode == Mode.REMOVE) 
			return;
		final CodecContext context = handler.getCodecContext();
		packet.displayName = Chat.STREAM_CODEC.deserialize(in, context);
		packet.renderType = RenderType.getByID(readVarInt(in));
		if (in.readBoolean()) {
			packet.formatType = NumberFormatType.getByID(readVarInt(in));
			if (packet.formatType != NumberFormatType.BLANK)
				packet.numberFormat = Chat.STREAM_CODEC.deserialize(in, context).toComponent();
		}
	}

	@Override
	public void write(PacketOutUpdateObjectives packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.name, out);
		out.writeByte(packet.mode.getID());
		if (packet.mode == Mode.REMOVE) 
			return;
		final CodecContext context = handler.getCodecContext();
		packet.displayName.writeToStream(out, context);
		writeVarInt(packet.renderType.getID(), out);
		if (packet.formatType != null) {
			out.writeBoolean(true);
			if (packet.formatType != NumberFormatType.BLANK);
				packet.numberFormat.writeToStream(out, context);
		} else {
			out.writeBoolean(false);
		}
	}

	@Override
	public PacketOutUpdateObjectives createPacketData() {
		return new PacketOutUpdateObjectives();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateObjectives.class);
	}

}
