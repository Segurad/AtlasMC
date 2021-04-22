package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.io.Packet;

public interface PacketOutSpawnLivingEntity extends Packet {
	
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
	
	@Override
	default int getDefaultID() {
		return 0x02;
	}

}
