package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutExplosion extends Packet {

	public float getX();
	public float getY();
	public float getZ();
	public float getStrength();
	public byte[] getRecords();
	public float getMotionX();
	public float getMotionY();
	public float getMotionZ();
	
}
