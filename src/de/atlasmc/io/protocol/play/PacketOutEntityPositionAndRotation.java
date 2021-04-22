package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityPositionAndRotation extends Packet {
	
	public int getEntityID();
	public short getDeltaX();
	public short getDeltaY();
	public short getDeltaZ();
	public float getPitch();
	public float getYaw();
	public boolean isOnGround();
	
	@Override
	default int getDefaultID() {
		return 0x28;
	}

}
