package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_RENAME_ITEM, definition = "rename_item")
public class  PacketInRenameItem extends AbstractPacket implements PacketPlayIn {
	
	public String itemName;

	@Override
	public int getDefaultID() {
		return IN_RENAME_ITEM;
	}
	
}
