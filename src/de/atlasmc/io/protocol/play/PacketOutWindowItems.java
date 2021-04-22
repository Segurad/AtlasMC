package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;

public interface PacketOutWindowItems extends Packet {
	
	public byte getWindowID();
	public int getCount();
	public ItemStack[] getSlots();

	@Override
	default int getDefaultID() {
		return 0x13;
	}
	
}
