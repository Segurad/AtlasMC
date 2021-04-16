package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;
import io.netty.buffer.ByteBuf;

public class PacketOutSetSlotV1_16_3 extends AbstractPacket implements PacketOutSetSlot {

	private byte windowID;
	private short slot;
	private ItemStack item;
	
	public PacketOutSetSlotV1_16_3(byte windowID, short slot, ItemStack item) {
		super(0x15, V1_16_3.version);
		this.windowID = windowID;
		this.slot = slot;
		this.item = item;
	}

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public int getSlot() {
		return slot;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		slot = in.readShort();
		item = readSlot(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		out.writeShort(slot);
		writeSlot(item, out);
	}

}
