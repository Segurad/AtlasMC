package de.atlasmc.core.node.io.protocol.login;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.PacketInLoginAcknowledged;
import io.netty.buffer.ByteBuf;

public class CorePacketInLoginAcknowledged implements PacketIO<PacketInLoginAcknowledged> {

	@Override
	public void read(PacketInLoginAcknowledged packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(PacketInLoginAcknowledged packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public PacketInLoginAcknowledged createPacketData() {
		return new PacketInLoginAcknowledged();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInLoginAcknowledged.class);
	}

}
