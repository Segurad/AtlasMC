package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_WINDOW_ITEMS)
public interface PacketOutWindowItems extends PacketPlay, PacketOutbound {
	
	public byte getWindowID();
	public int getCount();
	public ItemStack[] getSlots();

	@Override
	default int getDefaultID() {
		return OUT_WINDOW_ITEMS;
	}
	
}
