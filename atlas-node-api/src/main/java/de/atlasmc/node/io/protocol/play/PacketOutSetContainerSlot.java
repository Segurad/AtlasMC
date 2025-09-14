package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.inventory.ItemStack;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_CONTAINER_SLOT, definition = "container_set_slot")
public class PacketOutSetContainerSlot extends AbstractPacket implements PacketPlayOut {

	public int windowID;
	public int slot;
	public int stateID;
	public ItemStack item;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_CONTAINER_SLOT;
	}
	
}
