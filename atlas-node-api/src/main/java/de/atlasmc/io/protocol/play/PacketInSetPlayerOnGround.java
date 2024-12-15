package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_ON_GROUND, definition = "player_on_ground")
public class PacketInSetPlayerOnGround extends AbstractPacket implements PacketPlayIn {
	
	public boolean onGround;
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_ON_GROUND;
	}

}
