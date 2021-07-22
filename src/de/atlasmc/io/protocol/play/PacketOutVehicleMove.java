package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_VEHICLE_MOVE)
public interface PacketOutVehicleMove extends PacketPlay, PacketOutbound {
	
	public double getX();
	public double getY();
	public double getZ();
	public float getPitch();
	public float getYaw();
	
	@Override
	default int getDefaultID() {
		return OUT_VEHICLE_MOVE;
	}

}
