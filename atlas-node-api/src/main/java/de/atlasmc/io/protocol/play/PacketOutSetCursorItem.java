package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_CURSOR_ITEM, definition = "set_cursor_item")
public class PacketOutSetCursorItem extends AbstractPacket implements PacketPlayOut {

	public ItemStack item;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_CURSOR_ITEM;
	}

}
