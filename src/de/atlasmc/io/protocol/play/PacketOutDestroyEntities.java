package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_DESTROY_ENTITIES)
public interface PacketOutDestroyEntities extends PacketPlay, PacketOutbound {
	
	public int[] getEntityIDs();

	@Override
	public default int getDefaultID() {
		return OUT_DESTROY_ENTITIES;
	}
	
}
