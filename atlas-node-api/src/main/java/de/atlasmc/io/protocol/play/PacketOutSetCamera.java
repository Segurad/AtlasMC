package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_CAMERA)
public class PacketOutSetCamera extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_CAMERA;
	}

}
