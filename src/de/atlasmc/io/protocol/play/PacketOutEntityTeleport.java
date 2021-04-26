package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityTeleport extends Packet {
	
	public int getEntityID();
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	public boolean isOnGround();
	
	@Override
	default int getDefaultID() {
		return 0x56;
	}

}
