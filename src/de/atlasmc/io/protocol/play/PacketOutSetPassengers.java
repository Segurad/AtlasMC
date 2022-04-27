package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SET_PASSENGER)
public interface PacketOutSetPassengers extends PacketPlay, PacketOutbound {
	
	public int getVehicleID();
	
	public int[] getPassengers();
	
	public void setVehicleID(int id);
	
	public void setPassengers(int[] passengers);
	
	@Override
	public default int getDefaultID() {
		return OUT_SET_PASSENGER;
	}

}
