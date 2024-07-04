package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetCreativeModeSlot;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetCreativeModeSlot implements PacketIO<PacketInSetCreativeModeSlot> {
	
	@Override
	public void read(PacketInSetCreativeModeSlot packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setSlot(in.readShort());
		packet.setClickedItem(readSlot(in));
	}

	@Override
	public void write(PacketInSetCreativeModeSlot packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeShort(packet.getSlot());
		writeSlot(packet.getClickedItem(), out);
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
