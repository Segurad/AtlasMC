package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_SIMULATION_DISTANCE, definition = "set_simulation_distance")
public class PacketOutSetSimulationDistance extends AbstractPacket implements PacketPlayOut {

	public int distance;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_SIMULATION_DISTANCE;
	}
	
}
