package de.atlasmc.core.node.io.protocol.status;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.status.ServerboundRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInRequest implements PacketCodec<ServerboundRequest> {

	@Override
	public void deserialize(ServerboundRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// not required
	}

	@Override
	public void serialize(ServerboundRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
