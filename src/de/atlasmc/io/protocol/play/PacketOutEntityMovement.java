package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_MOVEMENT)
public interface PacketOutEntityMovement extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	@Override
	default int getDefaultID() {
		return OUT_ENTITY_MOVEMENT;
	}

}
