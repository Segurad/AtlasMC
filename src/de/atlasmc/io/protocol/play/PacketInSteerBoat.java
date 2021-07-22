package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_STEER_BOAT)
public interface PacketInSteerBoat extends PacketPlay, PacketInbound {
	
	public boolean getLeftPaddleTurning();
	public boolean getRightPaddleTurning();
	
	@Override
	default int getDefaultID() {
		return IN_STEER_BOAT;
	}

}
