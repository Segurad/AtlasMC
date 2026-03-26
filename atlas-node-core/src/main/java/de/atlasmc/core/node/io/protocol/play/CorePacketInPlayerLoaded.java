package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPlayerLoaded;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerLoaded implements PacketCodec<PacketInPlayerLoaded> {

	@Override
	public void deserialize(PacketInPlayerLoaded packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// no data
	}

	@Override
	public void serialize(PacketInPlayerLoaded packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// no data
	}

	@Override
	public PacketInPlayerLoaded createPacketData() {
		return new PacketInPlayerLoaded();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlayerLoaded.class);
	}

}
