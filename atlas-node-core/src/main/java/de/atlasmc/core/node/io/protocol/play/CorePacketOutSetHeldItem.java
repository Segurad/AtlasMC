package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutSetHeldItem;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetHeldItem implements PacketIO<PacketOutSetHeldItem> {

	@Override
	public void read(PacketOutSetHeldItem packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.slot = in.readByte();
	}

	@Override
	public void write(PacketOutSetHeldItem packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.slot);
	}

	@Override
	public PacketOutSetHeldItem createPacketData() {
		return new PacketOutSetHeldItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetHeldItem.class);
	}

}
