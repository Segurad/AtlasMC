package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateScore;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateScore.NumberFormatType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateScore implements PacketIO<PacketOutUpdateScore> {

	@Override
	public void read(PacketOutUpdateScore packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entry = readString(in, MAX_IDENTIFIER_LENGTH);
		packet.objective = readString(in, MAX_IDENTIFIER_LENGTH);
		packet.value = readVarInt(in);
		if (in.readBoolean())
			packet.displayName = readTextComponent(in);
		if (in.readBoolean()) {
			packet.formatType = NumberFormatType.getByID(readVarInt(in));
			if (packet.formatType != NumberFormatType.BLANK)
				packet.numberFormat = readTextComponent(in).toComponent();
		}
	}

	@Override
	public void write(PacketOutUpdateScore packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.entry, out);
		writeString(packet.objective, out);
		writeVarInt(packet.value, out);
		if (packet.displayName != null) {
			out.writeBoolean(true);
			writeTextComponent(packet.displayName, out);
		} else {
			out.writeBoolean(false);
		}
		if (packet.formatType != null) {
			out.writeBoolean(true);
			if (packet.formatType != NumberFormatType.BLANK);
				writeTextComponent(packet.numberFormat, out);
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
