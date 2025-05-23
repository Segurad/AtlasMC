package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetHeldItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetHeldItem implements PacketIO<PacketInSetHeldItem> {

	@Override
	public void read(PacketInSetHeldItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = in.readShort();
	}

	@Override
	public void write(PacketInSetHeldItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeShort(packet.slot);
	}

	@Override
	public PacketInSetHeldItem createPacketData() {
		return new PacketInSetHeldItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetHeldItem.class);
	}

}
