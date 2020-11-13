package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInSteerVehicle extends Packet {
	
	public float Sideways();
	public float Forward();
	public byte Flags();

}
