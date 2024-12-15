package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PICK_ITEM, definition = "pick_item")
public class PacketInPickItem extends AbstractPacket implements PacketPlayIn {
	
	public int slotToUse;

	@Override
	public int getDefaultID() {
		return IN_PICK_ITEM;
	}
	
}
