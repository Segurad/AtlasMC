package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SPAWN_LIVING_ENTITY)
public interface PacketOutSpawnLivingEntity extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public int getType();
	
	public UUID getUUID();
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public float getYaw();
	
	public float getPitch();
	
	public float getHeadPitch();
	
	public double getVelocityX();
	
	public double getVelocityY();
	
	public double getVelocityZ();
	
	public void setEntity(LivingEntity entity);
	
	@Override
	default int getDefaultID() {
		return OUT_SPAWN_LIVING_ENTITY;
	}

}
