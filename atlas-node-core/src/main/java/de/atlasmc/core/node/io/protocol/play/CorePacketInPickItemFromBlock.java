package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInPickItemFromBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInPickItemFromBlock implements PacketIO<PacketInPickItemFromBlock> {
	
	@Override
	public void read(PacketInPickItemFromBlock packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slotToUse = readVarInt(in);
	}

	@Override
	public void write(PacketInPickItemFromBlock packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
