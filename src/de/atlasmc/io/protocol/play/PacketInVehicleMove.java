package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInVehicleMove extends Packet {
	
	public double X();
	public double Y();
	public double Z();
	public float Yaw();
	public float Pitch();

}
