package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SPAWN_ENTITY)
public interface PacketOutSpawnEntity extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public UUID getUUID();
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public float getYaw();
	
	public float getPitch();
	
	public void setLocation(double x, double y, double z, float pitch, float yaw);
	
	public double getVelocityX();
	
	public double getVelocityY();
	
	public double getVelocityZ();
	
	public void setVelocity(double x, double y, double z);
	
	public void setEntity(Entity holder);
	
	public int getObjectData();
	
	public int getType();
	
	@Override
	public default int getDefaultID() {
		return OUT_SPAWN_ENTITY;
	}
	
}
