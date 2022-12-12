package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_TELEPORT_CONFIRM)
public class PacketInTeleportConfirm extends AbstractPacket implements PacketPlayIn {

	private int teleportID;
	
	public int getTeleportID() {
		return teleportID;
	}
	
	public void setTeleportID(int teleportID) {
		this.teleportID = teleportID;
	}
	
	@Override
	public int getDefaultID() {
		return IN_TELEPORT_CONFIRM;
	}
	
}
