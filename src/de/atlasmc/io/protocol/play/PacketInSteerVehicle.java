package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSteerVehicle extends Packet {
	
	public float Sideways();
	public float Forward();
	public byte Flags();

}
