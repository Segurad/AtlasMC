package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_ROTATION, definition = "player_rotation")
public class PacketInSetPlayerRotation extends AbstractPacket implements PacketPlayIn {
	
	public float yaw;
	public float pitch;
	public boolean onGround;
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_ROTATION;
	}

}
