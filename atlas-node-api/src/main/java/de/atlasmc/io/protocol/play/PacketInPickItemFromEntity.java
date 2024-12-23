package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PICK_ITEM_FROM_ENTITY, definition = "pick_item_from_entity")
public class PacketInPickItemFromEntity extends AbstractPacket implements PacketPlayIn {

	public int slotToUse;
	
	@Override
	public int getDefaultID() {
		return IN_PICK_ITEM_FROM_ENTITY;
	}

}
