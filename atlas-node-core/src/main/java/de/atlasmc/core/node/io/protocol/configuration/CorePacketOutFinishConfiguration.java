package de.atlasmc.core.node.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.configuration.ClientboundFinishConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketOutFinishConfiguration implements PacketIO<ClientboundFinishConfiguration> {

	@Override
	public void read(ClientboundFinishConfiguration packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(ClientboundFinishConfiguration packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public ClientboundFinishConfiguration createPacketData() {
		return new ClientboundFinishConfiguration();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundFinishConfiguration.class);
	}

}
