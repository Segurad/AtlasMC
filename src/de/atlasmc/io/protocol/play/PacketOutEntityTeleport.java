package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_TELEPORT)
public interface PacketOutEntityTeleport extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	public boolean isOnGround();
	
	@Override
	default int getDefaultID() {
		return OUT_ENTITY_TELEPORT;
	}

}
