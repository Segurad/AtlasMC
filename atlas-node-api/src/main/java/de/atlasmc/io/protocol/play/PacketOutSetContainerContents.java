package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_CONTAINER_CONTENT, definition = "container_set_content")
public class PacketOutSetContainerContents extends AbstractPacket implements PacketPlayOut {
	
	public int windowID;
	public int stateID;
	public ItemStack[] items;
	public ItemStack carriedItem;
	
	public void setItems(ItemStack... items) {
		this.items = items;
	}

	@Override
	public int getDefaultID() {
		return OUT_SET_CONTAINER_CONTENT;
	}
	
}
