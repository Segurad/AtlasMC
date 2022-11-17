package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_ROTATION)
public interface PacketOutEntityRotation extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public float getYaw();
	
	public float getPitch();
	
	public boolean isOnGround();
	
	public void setEntityID(int id);
	
	public void setYaw(float yaw);
	
	public void setPitch(float pitch);
	
	public void setOnGround(boolean onGround);
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_ROTATION;
	}

}
