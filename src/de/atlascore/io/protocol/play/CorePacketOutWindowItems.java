package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.inventory.ItemStack;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutWindowItems;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWindowItems extends CoreAbstractHandler<PacketOutWindowItems> {

	@Override
	public void read(PacketOutWindowItems packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		final int count = in.readShort();
		ItemStack[] slots = new ItemStack[count];
		NBTNIOReader reader = new NBTNIOReader(in);
		for (int i = 0; i < count; i++) {
			slots[i] = readSlot(in, reader);
		}
		packet.setItems(slots);
		reader.close();
	}

	@Override
	public void write(PacketOutWindowItems packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		out.writeShort(packet.getItems().length);
		NBTNIOWriter writer = new NBTNIOWriter(out);
		for (ItemStack i : packet.getItems()) {
			writeSlot(i, out, writer);
		}
		writer.close();
	}

	@Override
	public PacketOutWindowItems createPacketData() {
		return new PacketOutWindowItems();
	}

}
