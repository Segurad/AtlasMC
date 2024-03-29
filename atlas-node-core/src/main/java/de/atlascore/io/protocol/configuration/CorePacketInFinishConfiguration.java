package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketInFinishConfiguration;
import io.netty.buffer.ByteBuf;

public class CorePacketInFinishConfiguration implements PacketIO<PacketInFinishConfiguration> {

	@Override
	public void read(PacketInFinishConfiguration packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(PacketInFinishConfiguration packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data	
	}

	@Override
	public PacketInFinishConfiguration createPacketData() {
		return new PacketInFinishConfiguration();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInFinishConfiguration.class);
	}

}
