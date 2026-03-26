package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketOutSetCursorItem;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetCursorItem implements PacketCodec<PacketOutSetCursorItem> {

	@Override
	public void deserialize(PacketOutSetCursorItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.item = ItemStack.STREAM_CODEC.deserialize(in, con.getCodecContext());
	}

	@Override
	public void serialize(PacketOutSetCursorItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		ItemStack.STREAM_CODEC.serialize(packet.item, out, con.getCodecContext());
	}

	@Override
	public PacketOutSetCursorItem createPacketData() {
		return new PacketOutSetCursorItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetCursorItem.class);
	}

}
