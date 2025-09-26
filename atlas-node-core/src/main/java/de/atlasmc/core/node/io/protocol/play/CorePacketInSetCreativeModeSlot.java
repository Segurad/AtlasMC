package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSetCreativeModeSlot;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetCreativeModeSlot implements PacketIO<PacketInSetCreativeModeSlot> {
	
	@Override
	public void read(PacketInSetCreativeModeSlot packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = in.readShort();
		packet.clickedItem = readSlot(in);
	}

	@Override
	public void write(PacketInSetCreativeModeSlot packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeShort(packet.slot);
		writeSlot(packet.clickedItem, out);
	}
	
	@Override
	public PacketInSetCreativeModeSlot createPacketData() {
		return new PacketInSetCreativeModeSlot();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetCreativeModeSlot.class);
	}

}
