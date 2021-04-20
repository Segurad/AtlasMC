package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerPositionAndRotation extends Packet {
	
	public double getX();
	public double getFeetY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	public boolean OnGround();

}
