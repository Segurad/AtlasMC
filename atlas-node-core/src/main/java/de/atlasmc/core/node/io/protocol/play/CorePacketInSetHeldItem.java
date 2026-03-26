package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSetHeldItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetHeldItem implements PacketCodec<PacketInSetHeldItem> {

	@Override
	public void deserialize(PacketInSetHeldItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = in.readShort();
	}

	@Override
	public void serialize(PacketInSetHeldItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
