package de.atlasmc.io.protocol.play;

import java.util.UUID;

public interface PacketOutSpawnEntity {
	
	public int getEntityID();
	public UUID getUUID();
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	public double getVelocityX();
	public double getVelocityY();
	public double getVelocityZ();
	public int getObjectData();
	public int getType();
	
}
