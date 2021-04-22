package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityRotation extends Packet {
	
	public int getEntityID();
	public float getYaw();
	public float getPitch();
	public boolean isOnGround();
	
	@Override
	public default int getDefaultID() {
		return 0x29;
	}

}
