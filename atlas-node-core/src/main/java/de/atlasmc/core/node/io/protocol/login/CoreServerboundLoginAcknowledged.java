package de.atlasmc.core.node.io.protocol.login;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ServerboundLoginAcknowledged;
import io.netty.buffer.ByteBuf;

public class CoreServerboundLoginAcknowledged implements PacketIO<ServerboundLoginAcknowledged> {

	@Override
	public void read(ServerboundLoginAcknowledged packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void write(ServerboundLoginAcknowledged packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public ServerboundLoginAcknowledged createPacketData() {
		return new ServerboundLoginAcknowledged();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundLoginAcknowledged.class);
	}

}
