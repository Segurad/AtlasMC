package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutSetBorderCenter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetBorderCenter implements PacketIO<PacketOutSetBorderCenter> {

	@Override
	public void read(PacketOutSetBorderCenter packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.x = in.readDouble();
		packet.z = in.readDouble();
	}

	@Override
	public void write(PacketOutSetBorderCenter packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.z);
	}

	@Override
	public PacketOutSetBorderCenter createPacketData() {
		return new PacketOutSetBorderCenter();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetBorderCenter.class);
	}

}
