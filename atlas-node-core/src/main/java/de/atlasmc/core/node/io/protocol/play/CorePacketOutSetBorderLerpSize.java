package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetBorderLerpSize;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetBorderLerpSize implements PacketIO<PacketOutSetBorderLerpSize> {

	@Override
	public void read(PacketOutSetBorderLerpSize packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.oldDiameter = in.readDouble();
		packet.newDiameter = in.readDouble();
		packet.speed = readVarLong(in);
	}

	@Override
	public void write(PacketOutSetBorderLerpSize packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.oldDiameter);
		out.writeDouble(packet.newDiameter);
		writeVarLong(packet.speed, out);
	}

	@Override
	public PacketOutSetBorderLerpSize createPacketData() {
		return new PacketOutSetBorderLerpSize();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetBorderLerpSize.class);
	}

}
