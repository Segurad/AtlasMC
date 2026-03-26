package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketInSetCreativeModeSlot;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetCreativeModeSlot implements PacketCodec<PacketInSetCreativeModeSlot> {
	
	@Override
	public void deserialize(PacketInSetCreativeModeSlot packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = in.readShort();
		packet.clickedItem = ItemStack.STREAM_CODEC.deserialize(in, con.getCodecContext());
	}

	@Override
	public void serialize(PacketInSetCreativeModeSlot packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeShort(packet.slot);
		ItemStack.STREAM_CODEC.serialize(packet.clickedItem, out, con.getCodecContext());
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
