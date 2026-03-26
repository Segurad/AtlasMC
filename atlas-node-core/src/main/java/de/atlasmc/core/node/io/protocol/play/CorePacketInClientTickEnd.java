package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInClientTickEnd;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientTickEnd implements PacketCodec<PacketInClientTickEnd> {

	@Override
	public void deserialize(PacketInClientTickEnd packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// no data
	}

	@Override
	public void serialize(PacketInClientTickEnd packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// no data
	}

	@Override
	public PacketInClientTickEnd createPacketData() {
		return new PacketInClientTickEnd();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInClientTickEnd.class);
	}

}
