package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutAcknowledgeBlockChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAcknowledgeBlockChange implements PacketIO<PacketOutAcknowledgeBlockChange>{

	@Override
	public void read(PacketOutAcknowledgeBlockChange packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.sequence = readVarInt(in);
	}

	@Override
	public void write(PacketOutAcknowledgeBlockChange packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
