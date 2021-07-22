package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_VEHICLE_MOVE)
public interface PacketInVehicleMove extends PacketPlay, PacketInbound {
	
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	
	@Override
	default int getDefaultID() {
		return IN_VEHICLE_MOVE;
	}

}
