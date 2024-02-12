package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.inventory.ItemStack;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetContainerContents;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetContainerContents implements PacketIO<PacketOutSetContainerContents> {

	@Override
	public void read(PacketOutSetContainerContents packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setStateID(readVarInt(in));
		final int count = in.readShort();
		ItemStack[] slots = new ItemStack[count];
		NBTNIOReader reader = new NBTNIOReader(in, true);
		for (int i = 0; i < count; i++) {
			slots[i] = readSlot(in, reader);
		}
		packet.setItems(slots);
		packet.setCarriedItem(readSlot(in, reader));
		reader.close();
	}

	@Override
	public void write(PacketOutSetContainerContents packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		writeVarInt(packet.getStateID(), out);
		out.writeShort(packet.getItems().length);
		NBTNIOWriter writer = new NBTNIOWriter(out, true);
		for (ItemStack i : packet.getItems()) {
			writeSlot(i, out, writer);
		}
		writeSlot(packet.getCarriedItem(), out, writer);
		writer.close();
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
