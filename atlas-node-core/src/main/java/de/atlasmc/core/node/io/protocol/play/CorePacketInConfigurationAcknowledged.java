package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInAcknowledgeConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketInConfigurationAcknowledged implements PacketIO<PacketInAcknowledgeConfiguration> {

	@Override
	public void read(PacketInAcknowledgeConfiguration packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(PacketInAcknowledgeConfiguration packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public PacketInAcknowledgeConfiguration createPacketData() {
		return new PacketInAcknowledgeConfiguration();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInAcknowledgeConfiguration.class);
	}

}
