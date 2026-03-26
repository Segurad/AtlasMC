package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutAcknowledgeBlockChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAcknowledgeBlockChange implements PacketCodec<PacketOutAcknowledgeBlockChange>{

	@Override
	public void deserialize(PacketOutAcknowledgeBlockChange packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.sequence = readVarInt(in);
	}

	@Override
	public void serialize(PacketOutAcknowledgeBlockChange packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.sequence, out);	
	}

	@Override
	public PacketOutAcknowledgeBlockChange createPacketData() {
		return new PacketOutAcknowledgeBlockChange();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutAcknowledgeBlockChange.class);
	}

}
