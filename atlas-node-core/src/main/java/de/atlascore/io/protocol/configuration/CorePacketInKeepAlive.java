package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketInKeepAlive;
import io.netty.buffer.ByteBuf;

public class CorePacketInKeepAlive implements PacketIO<PacketInKeepAlive> {

	@Override
	public void read(PacketInKeepAlive packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.keepAliveID = in.readLong();
	}

	@Override
	public void write(PacketInKeepAlive packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.keepAliveID);
	}

	@Override
	public PacketInKeepAlive createPacketData() {
		return new PacketInKeepAlive();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInKeepAlive.class);
	}

}
