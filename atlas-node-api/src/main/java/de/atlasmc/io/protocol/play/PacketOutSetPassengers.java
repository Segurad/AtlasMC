package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_PASSENGERS)
public class PacketOutSetPassengers extends AbstractPacket implements PacketPlayOut {
	
	private int vehicleID;
	private int[] passengers;
	
	public int getVehicleID() {
		return vehicleID;
	}

	public int[] getPassengers() {
		return passengers;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public void setPassengers(int... passengers) {
		this.passengers = passengers;
	}

	@Override
	public int getDefaultID() {
		return OUT_SET_PASSENGERS;
	}

}
