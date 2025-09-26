package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.readSlot;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeSlot;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketOutSetContainerContents;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetContainerContents implements PacketIO<PacketOutSetContainerContents> {

	@Override
	public void read(PacketOutSetContainerContents packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = in.readByte();
		packet.stateID = readVarInt(in);
		final int count = in.readShort();
		ItemStack[] slots = new ItemStack[count];
		for (int i = 0; i < count; i++) {
			slots[i] = readSlot(in);
		}
		packet.setItems(slots);
		packet.carriedItem = readSlot(in);
	}

	@Override
	public void write(PacketOutSetContainerContents packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.windowID);
		writeVarInt(packet.stateID, out);
		out.writeShort(packet.items.length);
		for (ItemStack i : packet.items) {
			writeSlot(i, out);
		}
		writeSlot(packet.carriedItem, out);
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
