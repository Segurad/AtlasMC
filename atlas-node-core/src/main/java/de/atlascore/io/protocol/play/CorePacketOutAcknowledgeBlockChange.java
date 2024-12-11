package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutAcknowledgeBlockChange;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

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
