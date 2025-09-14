package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_ENTITY_ROTATION, definition = "move_entity_rot")
public class PacketOutUpdateEntityRotation extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public float yaw;
	public float pitch;
	public boolean onGround;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_ENTITY_ROTATION;
	}

}
