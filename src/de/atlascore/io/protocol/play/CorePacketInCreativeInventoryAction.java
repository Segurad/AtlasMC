package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInCreativeInventoryAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInCreativeInventoryAction extends AbstractPacket implements PacketInCreativeInventoryAction {

	public CorePacketInCreativeInventoryAction() {
		super(CoreProtocolAdapter.VERSION);
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
	public short getSlot() {
		return slot;
	}

	@Override
	public ItemStack getClickedItem() {
		return clickedItem;
	}

}
