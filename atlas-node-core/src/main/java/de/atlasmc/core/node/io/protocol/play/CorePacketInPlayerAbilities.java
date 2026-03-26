package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerAbilities implements PacketCodec<PacketInPlayerAbilities> {

	@Override
	public void deserialize(PacketInPlayerAbilities packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.flags = in.readByte();
	}

	@Override
	public void serialize(PacketInPlayerAbilities packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.flags);
	}

	@Override
	public PacketInPlayerAbilities createPacketData() {
		return new PacketInPlayerAbilities();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlayerAbilities.class);
	}

}
