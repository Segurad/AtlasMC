package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetBorderWarningDistance;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

public class CorePacketOutSetBorderWarningDistance implements PacketIO<PacketOutSetBorderWarningDistance> {

	@Override
	public void read(PacketOutSetBorderWarningDistance packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.distance = readVarInt(in);
	}

	@Override
	public void write(PacketOutSetBorderWarningDistance packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.distance, out);
	}

	@Override
	public PacketOutSetBorderWarningDistance createPacketData() {
		return new PacketOutSetBorderWarningDistance();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetBorderWarningDistance.class);
	}

}
