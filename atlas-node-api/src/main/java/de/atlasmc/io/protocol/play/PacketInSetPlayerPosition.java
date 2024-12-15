package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_POSITION, definition = "player_position")
public class PacketInSetPlayerPosition extends AbstractPacket implements PacketPlayIn {

	public double x;
	public double feetY;
	public double z;
	public boolean onGround;
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_POSITION;
	}

}
