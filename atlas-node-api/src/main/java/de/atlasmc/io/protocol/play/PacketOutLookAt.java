package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_LOOK_AT, definition = "player_look_at")
public class PacketOutLookAt extends AbstractPacket implements PacketPlayOut {
	
	public boolean aimWithEyes;
	public boolean aimAtEyes;
	public boolean hasEntity;
	public double x;
	public double y;
	public double z;
	public int entityID;

	@Override
	public int getDefaultID() {
		return OUT_LOOK_AT;
	}

}
