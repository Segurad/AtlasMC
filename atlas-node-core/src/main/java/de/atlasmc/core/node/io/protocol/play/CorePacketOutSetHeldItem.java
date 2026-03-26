package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetHeldItem;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetHeldItem implements PacketCodec<PacketOutSetHeldItem> {

	@Override
	public void deserialize(PacketOutSetHeldItem packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.slot = in.readByte();
	}

	@Override
	public void serialize(PacketOutSetHeldItem packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
