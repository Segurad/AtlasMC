package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CHANGE_CONTAINER_SLOT_SATE, definition = "change_slot_state")
public class PacketInChangeConaterSlotState extends AbstractPacket implements PacketPlayIn {

	public int slot;
	public int windowID;
	public boolean enabled;
	
	@Override
	public int getDefaultID() {
		return IN_CHANGE_CONTAINER_SLOT_SATE;
	}
	
}
