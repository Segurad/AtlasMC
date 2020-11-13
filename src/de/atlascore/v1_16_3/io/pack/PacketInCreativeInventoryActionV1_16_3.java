package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInCreativeInventoryAction;

public class PacketInCreativeInventoryActionV1_16_3 extends AbstractPacket implements PacketInCreativeInventoryAction {

	public PacketInCreativeInventoryActionV1_16_3() {
		super(0x29, V1_16_3.version);
	}

	private short slot;
	private ItemStack clickedItem;
	
	@Override
	public void read(int length, DataInputStream input) throws IOException {
		slot = input.readShort();
		clickedItem = readSlot(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public short Slot() {
		return slot;
	}

	@Override
	public ItemStack ClickedItem() {
		return clickedItem;
	}

}
