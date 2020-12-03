package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerPositionAndRotation extends Packet {
	
	public double X();
	public double FeetY();
	public double Z();
	public float Yaw();
	public float Pitch();
	public boolean OnGround();

}
