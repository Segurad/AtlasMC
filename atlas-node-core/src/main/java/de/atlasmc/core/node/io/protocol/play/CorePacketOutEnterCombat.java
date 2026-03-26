package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutEnterCombat;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEnterCombat implements PacketCodec<PacketOutEnterCombat> {

	@Override
	public void deserialize(PacketOutEnterCombat packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public void serialize(PacketOutEnterCombat packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// packet does not contain data
	}

	@Override
	public PacketOutEnterCombat createPacketData() {
		return new PacketOutEnterCombat();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEnterCombat.class);
	}

}
