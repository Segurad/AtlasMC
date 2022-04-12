package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_TELEPORT)
public interface PacketOutEntityTeleport extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public void setEntityID(int id);
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public float getYaw();
	
	public float getPitch();
	
	public void setLocation(double x, double y, double z, float pitch, float yaw);
	
	public boolean isOnGround();
	
	public void setOnGround(boolean onGround);
	
	@Override
	default int getDefaultID() {
		return OUT_ENTITY_TELEPORT;
	}

}
