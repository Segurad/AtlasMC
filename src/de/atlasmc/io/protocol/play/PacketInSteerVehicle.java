package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_STEER_VEHICLE)
public interface PacketInSteerVehicle extends PacketPlay, PacketInbound {
	
	public float getSideways();
	public float getForward();
	public byte getFlags();
	
	@Override
	default int getDefaultID() {
		return IN_STEER_VEHICLE;
	}

}
