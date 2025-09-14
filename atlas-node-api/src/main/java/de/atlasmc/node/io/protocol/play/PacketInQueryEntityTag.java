package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_QUERY_ENTITY_TAG, definition = "entity_tag_query")
public class PacketInQueryEntityTag extends AbstractPacket implements PacketPlayIn {

	public int transactionID;
	public int entityID;
	
	@Override
	public int getDefaultID() {
		return IN_QUERY_ENTITY_TAG;
	}
	
}
