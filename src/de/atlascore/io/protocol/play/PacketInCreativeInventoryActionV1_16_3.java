package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInCreativeInventoryAction;
import io.netty.buffer.ByteBuf;

public class PacketInCreativeInventoryActionV1_16_3 extends AbstractPacket implements PacketInCreativeInventoryAction {

	public PacketInCreativeInventoryActionV1_16_3() {
		super(0x29, V1_16_3.version);
	}

	private short slot;
	private ItemStack clickedItem;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		slot = in.readShort();
		clickedItem = readSlot(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeShort(slot);
		writeSlot(clickedItem, out);
	}

	@Override
	public short Slot() {
		return slot;
	}

	@Override
	public ItemStack ClickedItem() {
		return clickedItem;
	}

}
