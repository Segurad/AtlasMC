package de.atlasmc.core.node.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.configuration.PacketOutFinishConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketOutFinishConfiguration implements PacketIO<PacketOutFinishConfiguration> {

	@Override
	public void read(PacketOutFinishConfiguration packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(PacketOutFinishConfiguration packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public PacketOutFinishConfiguration createPacketData() {
		return new PacketOutFinishConfiguration();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutFinishConfiguration.class);
	}

}
