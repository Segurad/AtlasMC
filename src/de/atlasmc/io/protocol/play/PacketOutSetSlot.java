package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;

public interface PacketOutSetSlot extends Packet {

	public byte getWindowID();
	public int getSlot();
	public ItemStack getItem();
	
	@Override
	default int getDefaultID() {
		return 0x15;
	}
	
}
