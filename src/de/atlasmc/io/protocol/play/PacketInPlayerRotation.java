package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerRotation extends Packet {
	
	public float getYaw();
	public float getPitch();
	public boolean isOnGround();

}
