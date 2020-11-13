package de.atlasmc.io.pack;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;

public interface PacketInClickWindow extends Packet {

	public byte getWindowID();
	public short getSlot();
	public byte getButton();
	public short getActionNumber();
	public int getMode();
	public ItemStack getClickedItem();
}
