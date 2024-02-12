package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_LINK_ENTITIES)
public class PacketOutLinkEntities extends AbstractPacket implements PacketPlayOut {
	
	private int holderEntityID, attachedEntityID;
	
	public int getHolderEntityID() {
		return holderEntityID;
	}
	
	public void setHolderEntityID(int holderEntityID) {
		this.holderEntityID = holderEntityID;
	}
	
	public int getAttachedEntityID() {
		return attachedEntityID;
	}
	
	public void setAttachedEntityID(int attachedEntityID) {
		this.attachedEntityID = attachedEntityID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_LINK_ENTITIES;
	}

}
