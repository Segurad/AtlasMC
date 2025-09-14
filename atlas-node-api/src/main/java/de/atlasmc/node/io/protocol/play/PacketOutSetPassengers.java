package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_PASSENGERS, definition = "set_passengers")
public class PacketOutSetPassengers extends AbstractPacket implements PacketPlayOut {
	
	public int vehicleID;
	public int[] passengers;

	public void setPassengers(int... passengers) {
		this.passengers = passengers;
	}

	@Override
	public int getDefaultID() {
		return OUT_SET_PASSENGERS;
	}

}
