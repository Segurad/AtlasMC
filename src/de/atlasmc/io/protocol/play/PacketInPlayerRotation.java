package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerRotation extends Packet {
	
	public float Yaw();
	public float Pitch();
	public boolean OnGround();

}
