package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_ENTITY_POSITION_AND_ROTATION, definition = "move_entity_pos_rot")
public class PacketOutUpdateEntityPositionAndRotation extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public int deltaX;
	public int deltaY;
	public int deltaZ;
	public float yaw;
	public float pitch;
	public boolean onGround;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_ENTITY_POSITION_AND_ROTATION;
	}

}
