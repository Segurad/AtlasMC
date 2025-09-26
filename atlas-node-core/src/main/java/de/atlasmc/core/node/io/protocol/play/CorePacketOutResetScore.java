package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.MAX_IDENTIFIER_LENGTH;
import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.writeString;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutResetScore;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResetScore implements PacketIO<PacketOutResetScore> {

	@Override
	public void read(PacketOutResetScore packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityName = readString(in, MAX_IDENTIFIER_LENGTH);
		if (in.readBoolean())
			packet.objectiveName = readString(in, MAX_IDENTIFIER_LENGTH);
	}

	@Override
	public void write(PacketOutResetScore packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.entityName, out);
		if (packet.objectiveName != null) {
			out.writeBoolean(true);
			writeString(packet.objectiveName, out);
		} else {
			out.writeBoolean(false);
		}
	}

	@Override
	public PacketOutResetScore createPacketData() {
		return new PacketOutResetScore();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutResetScore.class);
	}

}
