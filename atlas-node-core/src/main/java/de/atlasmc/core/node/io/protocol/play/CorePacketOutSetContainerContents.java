package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketOutSetContainerContents;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetContainerContents implements PacketIO<PacketOutSetContainerContents> {

	@Override
	public void read(PacketOutSetContainerContents packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = in.readByte();
		packet.stateID = readVarInt(in);
		final int count = in.readShort();
		final CodecContext context = handler.getCodecContext();
		final ItemStack[] slots = new ItemStack[count];
		for (int i = 0; i < count; i++) {
			slots[i] = ItemStack.STREAM_CODEC.deserialize(in, context);
		}
		packet.setItems(slots);
		packet.carriedItem = ItemStack.STREAM_CODEC.deserialize(in, context);
	}

	@Override
	public void write(PacketOutSetContainerContents packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.windowID);
		writeVarInt(packet.stateID, out);
		out.writeShort(packet.items.length);
		final CodecContext context = handler.getCodecContext();
		for (ItemStack i : packet.items) {
			ItemStack.STREAM_CODEC.serialize(i, out, context);
		}
		ItemStack.STREAM_CODEC.serialize(packet.carriedItem, out, context);
	}

	@Override
	public PacketOutSetContainerContents createPacketData() {
		return new PacketOutSetContainerContents();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetContainerContents.class);
	}

}
