package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInConfigurationAcknowledged;
import io.netty.buffer.ByteBuf;

public class CorePacketInConfigurationAcknowledged implements PacketIO<PacketInConfigurationAcknowledged> {

	@Override
	public void read(PacketInConfigurationAcknowledged packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(PacketInConfigurationAcknowledged packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public PacketInConfigurationAcknowledged createPacketData() {
		return new PacketInConfigurationAcknowledged();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInConfigurationAcknowledged.class);
	}

}
