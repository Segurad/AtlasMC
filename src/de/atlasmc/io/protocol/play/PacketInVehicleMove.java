package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInVehicleMove extends Packet {
	
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	
	@Override
	default int getDefaultID() {
		return 0x16;
	}

}
