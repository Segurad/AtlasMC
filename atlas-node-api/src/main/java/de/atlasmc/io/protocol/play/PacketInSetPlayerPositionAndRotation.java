package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_POSITION_AND_ROTATION, definition = "player_position_rotation")
public class PacketInSetPlayerPositionAndRotation extends AbstractPacket implements PacketPlayIn {
	
	public double x; 
	public double feetY; 
	public double z;
	public boolean onGround;
	public float yaw;
	public float pitch;
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_POSITION_AND_ROTATION;
	}

}
