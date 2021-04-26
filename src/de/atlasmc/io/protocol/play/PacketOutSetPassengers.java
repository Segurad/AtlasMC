package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutSetPassengers extends Packet {
	
	public int getVehicleID();
	public int[] getPassengers();
	
	@Override
	public default int getDefaultID() {
		return 0x4B;
	}

}
