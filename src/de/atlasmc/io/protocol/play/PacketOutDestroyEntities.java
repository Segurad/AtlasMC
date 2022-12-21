package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_DESTROY_ENTITIES)
public class PacketOutDestroyEntities extends AbstractPacket implements PacketPlayOut {
	
	private int[] entityIDs;
	
	public int[] getEntityIDs() {
		return entityIDs;
	}
	
	public void setEntityIDs(int... ids) {
		this.entityIDs = ids;
	}

	@Override
	public int getDefaultID() {
		return OUT_DESTROY_ENTITIES;
	}
	
}
