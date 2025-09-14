package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_HELD_ITEM, definition = "set_carried_item")
public class PacketInSetHeldItem extends AbstractPacket implements PacketPlayIn {
	
	public int slot;
	
	@Override
	public int getDefaultID() {
		return IN_SET_HELD_ITEM;
	}
	
}
