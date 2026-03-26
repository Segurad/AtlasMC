package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInAcknowledgeConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketInConfigurationAcknowledged implements PacketCodec<PacketInAcknowledgeConfiguration> {

	@Override
	public void deserialize(PacketInAcknowledgeConfiguration packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void serialize(PacketInAcknowledgeConfiguration packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
