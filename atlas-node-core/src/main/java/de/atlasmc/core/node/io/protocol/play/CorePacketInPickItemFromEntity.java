package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPickItemFromEntity;
import io.netty.buffer.ByteBuf;

public class CorePacketInPickItemFromEntity implements PacketCodec<PacketInPickItemFromEntity> {

	@Override
	public void deserialize(PacketInPickItemFromEntity packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slotToUse = readVarInt(in);
	}

	@Override
	public void serialize(PacketInPickItemFromEntity packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slotToUse, out);
	}

	@Override
	public PacketInPickItemFromEntity createPacketData() {
		return new PacketInPickItemFromEntity();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPickItemFromEntity.class);
	}

}
