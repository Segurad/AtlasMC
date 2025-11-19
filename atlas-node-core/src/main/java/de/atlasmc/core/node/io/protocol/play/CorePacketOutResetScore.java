package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutResetScore;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResetScore implements PacketIO<PacketOutResetScore> {

	@Override
	public void read(PacketOutResetScore packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityName = StringCodec.readString(in);
		if (in.readBoolean())
			packet.objectiveName = StringCodec.readString(in);
	}

	@Override
	public void write(PacketOutResetScore packet, ByteBuf out, ConnectionHandler con) throws IOException {
		StringCodec.writeString(packet.entityName, out);
		if (packet.objectiveName != null) {
			out.writeBoolean(true);
			StringCodec.writeString(packet.objectiveName, out);
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
