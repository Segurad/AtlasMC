package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_MOVE_VEHICLE, definition = "move_vehicle")
public class PacketOutMoveVehicle extends AbstractPacket implements PacketPlayOut {
	
	public double x;
	public double y;
	public double z;
	public float yaw;
	public float pitch;

	@Override
	public int getDefaultID() {
		return OUT_MOVE_VEHICLE;
	}

}
