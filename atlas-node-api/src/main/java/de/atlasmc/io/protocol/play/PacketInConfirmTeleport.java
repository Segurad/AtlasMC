package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CONFIRM_TELEPORT)
public class PacketInConfirmTeleport extends AbstractPacket implements PacketPlayIn {

	private int teleportID;
	
	public int getTeleportID() {
		return teleportID;
	}
	
	public void setTeleportID(int teleportID) {
		this.teleportID = teleportID;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CONFIRM_TELEPORT;
	}
	
}
