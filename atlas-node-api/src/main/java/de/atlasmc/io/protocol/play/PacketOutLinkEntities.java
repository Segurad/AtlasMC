package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_LINK_ENTITIES, definition = "set_entity_link")
public class PacketOutLinkEntities extends AbstractPacket implements PacketPlayOut {
	
	public int holderEntityID;
	public int attachedEntityID;
	
	@Override
	public int getDefaultID() {
		return OUT_LINK_ENTITIES;
	}

}
