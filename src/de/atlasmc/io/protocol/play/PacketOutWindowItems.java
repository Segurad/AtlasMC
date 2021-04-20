package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;

public interface PacketOutWindowItems {
	
	public byte getWindowID();
	public int getCount();
	public ItemStack[] getSlots();

}
