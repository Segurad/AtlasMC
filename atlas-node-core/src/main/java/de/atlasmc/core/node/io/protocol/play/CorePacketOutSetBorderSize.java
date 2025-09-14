package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutSetBorderSize;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetBorderSize implements PacketIO<PacketOutSetBorderSize> {

	@Override
	public void read(PacketOutSetBorderSize packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.diameter = in.readDouble();
	}

	@Override
	public void write(PacketOutSetBorderSize packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.diameter);
	}

	@Override
	public PacketOutSetBorderSize createPacketData() {
		return new PacketOutSetBorderSize();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetBorderSize.class);
	}

}
