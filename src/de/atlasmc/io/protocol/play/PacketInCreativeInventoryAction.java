package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CREATIVE_INVENTORY_ACTION)
public class PacketInCreativeInventoryAction extends AbstractPacket implements PacketPlayIn {
	
	private int slot;
	private ItemStack clickedItem;
	
	public int getSlot() {
		return slot;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public ItemStack getClickedItem() {
		return clickedItem;
	}
	
	public void setClickedItem(ItemStack clickedItem) {
		this.clickedItem = clickedItem;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CREATIVE_INVENTORY_ACTION;
	}

}
