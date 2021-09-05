package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CREATIVE_INVENTORY_ACTION)
public interface PacketInCreativeInventoryAction extends PacketPlay, PacketInbound {
	
	public short getSlot();
	public ItemStack getClickedItem();
	
	@Override
	default int getDefaultID() {
		return IN_CREATIVE_INVENTORY_ACTION;
	}

}
