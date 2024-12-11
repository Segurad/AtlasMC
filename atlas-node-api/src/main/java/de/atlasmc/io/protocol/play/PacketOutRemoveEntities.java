package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_REMOVE_ENTITIES, definition = "remove_entities")
public class PacketOutRemoveEntities extends AbstractPacket implements PacketPlayOut {
	
	public int[] entityIDs;
	
	public void setEntityIDs(int... ids) {
		this.entityIDs = ids;
	}

	@Override
	public int getDefaultID() {
		return OUT_REMOVE_ENTITIES;
	}
	
}
