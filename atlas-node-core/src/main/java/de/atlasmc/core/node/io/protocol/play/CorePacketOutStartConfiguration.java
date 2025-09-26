package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutStartConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketOutStartConfiguration implements PacketIO<PacketOutStartConfiguration> {

	@Override
	public void read(PacketOutStartConfiguration packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(PacketOutStartConfiguration packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public PacketOutStartConfiguration createPacketData() {
		return new PacketOutStartConfiguration();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutStartConfiguration.class);
	}

}
