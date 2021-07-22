package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_STATUS)
public interface PacketOutEntityStatus extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public int getStatus();
	
	@Override
	default int getDefaultID() {
		return OUT_ENTITY_STATUS;
	}

}
