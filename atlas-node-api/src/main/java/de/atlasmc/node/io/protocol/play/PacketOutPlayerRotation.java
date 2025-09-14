package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_PLAYER_ROTATION, definition = "player_rotation")
public class PacketOutPlayerRotation extends AbstractPacket implements PacketPlayOut {

	public float yaw;
	public float pitch;
	
	@Override
	public int getDefaultID() {
		return OUT_PLAYER_ROTATION;
	}

}
