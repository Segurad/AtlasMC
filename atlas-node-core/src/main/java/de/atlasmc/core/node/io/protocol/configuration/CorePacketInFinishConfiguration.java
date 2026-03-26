package de.atlasmc.core.node.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.configuration.ServerboundFinishConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketInFinishConfiguration implements PacketCodec<ServerboundFinishConfiguration> {

	@Override
	public void deserialize(ServerboundFinishConfiguration packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void serialize(ServerboundFinishConfiguration packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data	
	}

	@Override
	public ServerboundFinishConfiguration createPacketData() {
		return new ServerboundFinishConfiguration();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundFinishConfiguration.class);
	}

}
