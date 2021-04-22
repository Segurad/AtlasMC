package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.io.Packet;

public interface PacketOutSpawnEntity extends Packet {
	
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
	
	@Override
	public default int getDefaultID() {
		return 0x00;
	}
	
}
