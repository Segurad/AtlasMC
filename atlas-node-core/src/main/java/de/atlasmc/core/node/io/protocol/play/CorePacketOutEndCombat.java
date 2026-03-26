package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutEndCombat;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEndCombat implements PacketCodec<PacketOutEndCombat> {

	@Override
	public void deserialize(PacketOutEndCombat packet, ByteBuf in, ConnectionHandler con) throws IOException {
		writeVarInt(packet.duration, in);
	}

	@Override
	public void serialize(PacketOutEndCombat packet, ByteBuf out, ConnectionHandler con) throws IOException {
		packet.duration = readVarInt(out);
	}

	@Override
	public PacketOutEndCombat createPacketData() {
		return new PacketOutEndCombat();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEndCombat.class);
	}

}
