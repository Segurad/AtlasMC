package de.atlasmc.io.protocol.play;

import java.util.Map;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CLICK_CONTAINER, definition = "container_click")
public class PacketInClickContainer extends AbstractPacket implements PacketPlayIn {

	public int windowID;
	public int stateID;
	public int slot;
	public int button;
	public int mode;
	public Map<Integer, ItemStack> slotsChanged;
	public ItemStack carriedItem;
	
	@Override
	public int getDefaultID() {
		return IN_CLICK_CONTAINER;
	}

}
