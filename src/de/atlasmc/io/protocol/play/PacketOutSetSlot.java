package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;

public interface PacketOutSetSlot extends Packet {

	public byte getWindowID();
	public int getSlot();
	public ItemStack getItem();
	
}
