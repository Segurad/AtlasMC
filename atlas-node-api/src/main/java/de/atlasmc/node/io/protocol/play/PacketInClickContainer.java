package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.inventory.ItemStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

@DefaultPacketID(packetID = PacketPlay.IN_CLICK_CONTAINER, definition = "container_click")
public class PacketInClickContainer extends AbstractPacket implements PacketPlayIn {

	public int windowID;
	public int stateID;
	public int slot;
	public int button;
	public int mode;
	public Int2ObjectMap<ItemStack> slotsChanged;
	public ItemStack carriedItem;
	
	@Override
	public int getDefaultID() {
		return IN_CLICK_CONTAINER;
	}

}
