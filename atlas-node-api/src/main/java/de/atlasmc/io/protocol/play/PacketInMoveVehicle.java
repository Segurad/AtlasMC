package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_MOVE_VEHICLE, definition = "move_vehicle")
public class PacketInMoveVehicle extends AbstractPacket implements PacketPlayIn {
	
	public double x;
	public double y;
	public double z;
	public float yaw;
	public float pitch;
	
	@Override
	public int getDefaultID() {
		return IN_MOVE_VEHICLE;
	}

}
