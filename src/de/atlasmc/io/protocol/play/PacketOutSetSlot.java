package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SET_SLOT)
public interface PacketOutSetSlot extends PacketPlay, PacketOutbound {

	public byte getWindowID();
	public int getSlot();
	public ItemStack getItem();
	
	@Override
	default int getDefaultID() {
		return OUT_SET_SLOT;
	}
	
}
