package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CONFIRM_TELEPORT, definition = "accept_teleportation")
public class PacketInConfirmTeleport extends AbstractPacket implements PacketPlayIn {

	public int teleportID;
	
	@Override
	public int getDefaultID() {
		return IN_CONFIRM_TELEPORT;
	}
	
}
