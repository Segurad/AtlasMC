package de.atlascore.io.protocol.play;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetBorderWarningDistance;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.io.AbstractPacket.*;

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
