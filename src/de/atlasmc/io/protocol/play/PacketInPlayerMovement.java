package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_PLAYER_MOVEMENT)
public interface PacketInPlayerMovement extends PacketPlay, PacketInbound {
	
	public boolean isOnGround();
	
	@Override
	default int getDefaultID() {
		return IN_PLAYER_MOVEMENT;
	}

}
