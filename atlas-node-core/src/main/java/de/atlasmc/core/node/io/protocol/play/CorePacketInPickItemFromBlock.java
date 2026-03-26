package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPickItemFromBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInPickItemFromBlock implements PacketCodec<PacketInPickItemFromBlock> {
	
	@Override
	public void deserialize(PacketInPickItemFromBlock packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slotToUse = readVarInt(in);
	}

	@Override
	public void serialize(PacketInPickItemFromBlock packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slotToUse, out);
	}

	@Override
	public PacketInPickItemFromBlock createPacketData() {
		return new PacketInPickItemFromBlock();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPickItemFromBlock.class);
	}
	
}
