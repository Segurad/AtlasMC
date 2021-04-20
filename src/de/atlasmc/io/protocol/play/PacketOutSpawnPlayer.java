package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.io.Packet;

public interface PacketOutSpawnPlayer extends Packet {
	
	public int getEntityID();
	public UUID getUUID();
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();

}
