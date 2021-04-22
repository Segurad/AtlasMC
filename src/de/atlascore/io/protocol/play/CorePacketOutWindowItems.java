package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutWindowItems;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWindowItems extends AbstractPacket implements PacketOutWindowItems {

	private byte windowID;
	private ItemStack[] slots;
	
	public CorePacketOutWindowItems() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutWindowItems(byte windowID, ItemStack[] slots) {
		this();
		this.slots = new ItemStack[slots.length];
		int index = 0;
		for (ItemStack i : slots) {
			this.slots[index] = i.clone();
		}
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		final int count = in.readShort();
		slots = new ItemStack[count];
		NBTNIOReader reader = new NBTNIOReader(in);
		for (int i = 0; i < count; i++) {
			slots[i] = readSlot(in, reader);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		out.writeShort(slots.length);
		NBTNIOWriter writer = new NBTNIOWriter(out);
		for (ItemStack i : slots) {
			writeSlot(i, out, writer);
		}
	}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public int getCount() {
		return slots.length;
	}

	@Override
	public ItemStack[] getSlots() {
		return slots;
	}

}
