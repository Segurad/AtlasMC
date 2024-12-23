package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_CREATIVE_MODE_SLOT, definition = "set_creative_mode_slot")
public class PacketInSetCreativeModeSlot extends AbstractPacket implements PacketPlayIn {
	
	public int slot;
	public ItemStack clickedItem;
	
	@Override
	public int getDefaultID() {
		return IN_SET_CREATIVE_MODE_SLOT;
	}

}
