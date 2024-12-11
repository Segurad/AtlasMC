package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_TELEPORT_ENTITY, definition = "teleport_entity")
public class PacketOutTeleportEntity extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public double x;
	public double y;
	public double z;
	public float yaw;
	public float pitch;
	public boolean onGround;
	
	@Override
	public int getDefaultID() {
		return OUT_TELEPORT_ENTITY;
	}

}
