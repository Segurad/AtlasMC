package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_HELD_ITEM, definition = "set_held_slot")
public class PacketOutSetHeldItem extends AbstractPacket implements PacketPlayOut {
	
	public int slot;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_HELD_ITEM;
	}

}
