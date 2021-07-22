package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SET_PASSENGER)
public interface PacketOutSetPassengers extends PacketPlay, PacketOutbound {
	
	public int getVehicleID();
	public int[] getPassengers();
	
	@Override
	public default int getDefaultID() {
		return OUT_SET_PASSENGER;
	}

}
