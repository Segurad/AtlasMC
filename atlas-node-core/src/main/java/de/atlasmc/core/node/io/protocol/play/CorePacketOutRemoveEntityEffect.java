package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutRemoveEntityEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRemoveEntityEffect implements PacketIO<PacketOutRemoveEntityEffect> {

	@Override
	public void read(PacketOutRemoveEntityEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.effectID = readVarInt(in);
	}

	@Override
	public void write(PacketOutRemoveEntityEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		writeVarInt(packet.effectID, out);
	}

	@Override
	public PacketOutRemoveEntityEffect createPacketData() {
		return new PacketOutRemoveEntityEffect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRemoveEntityEffect.class);
	}

}
