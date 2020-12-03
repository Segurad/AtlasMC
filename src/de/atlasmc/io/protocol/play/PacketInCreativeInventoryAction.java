package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;

public interface PacketInCreativeInventoryAction extends Packet {
	
	public short Slot();
	public ItemStack ClickedItem();

}
