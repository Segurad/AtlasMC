package de.atlasmc.core.node.io.protocol.status;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.status.ServerboundRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInRequest implements PacketIO<ServerboundRequest> {

	@Override
	public void read(ServerboundRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// not required
	}

	@Override
	public void write(ServerboundRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// not required
	}

	@Override
	public ServerboundRequest createPacketData() {
		return new ServerboundRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundRequest.class);
	}

}
