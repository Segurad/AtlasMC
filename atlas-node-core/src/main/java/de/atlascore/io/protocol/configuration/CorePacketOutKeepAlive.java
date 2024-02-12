package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketOutKeepAlive;
import io.netty.buffer.ByteBuf;

public class CorePacketOutKeepAlive implements PacketIO<PacketOutKeepAlive> {

	@Override
	public void read(PacketOutKeepAlive packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.keepAliveID = in.readLong();
	}

	@Override
	public void write(PacketOutKeepAlive packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.keepAliveID);
	}

	@Override
	public PacketOutKeepAlive createPacketData() {
		return new PacketOutKeepAlive();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutKeepAlive.class);
	}

}
