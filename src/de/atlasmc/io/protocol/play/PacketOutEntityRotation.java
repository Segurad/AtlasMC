package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_ROTATION)
public interface PacketOutEntityRotation extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public float getYaw();
	public float getPitch();
	public boolean isOnGround();
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_ROTATION;
	}

}
