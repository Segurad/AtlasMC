package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_QUERY_ENTITY_TAG, definition = "query_entity_tag")
public class PacketInQueryEntityTag extends AbstractPacket implements PacketPlayIn {

	public int transactionID;
	public int entityID;
	
	@Override
	public int getDefaultID() {
		return IN_QUERY_ENTITY_TAG;
	}
	
}
