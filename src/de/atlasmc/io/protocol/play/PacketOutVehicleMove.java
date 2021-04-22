package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutVehicleMove extends Packet {
	
	public double getX();
	public double getY();
	public double getZ();
	public float getPitch();
	public float getYaw();
	
	@Override
	default int getDefaultID() {
		return 0x2B;
	}

}
