package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.IN_QUERY_ENTITY_NBT)
public interface PacketInQueryEntityNBT extends PacketPlay, PacketOutbound {

	public int getTransactionID();
	public int getEntityID();
	
	@Override
	default int getDefaultID() {
		return IN_QUERY_ENTITY_NBT;
	}
	
}
