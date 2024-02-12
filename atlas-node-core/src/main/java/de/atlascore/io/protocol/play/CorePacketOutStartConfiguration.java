package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutStartConfiguration;
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
