package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.nbt.tag.NBT;

@DefaultPacketID(packetID = PacketPlay.OUT_TAG_QUERY_RESPONSE, definition = "tag_query")
public class PacketOutTagQueryResponse extends AbstractPacket implements PacketPlayOut {
	
	public int transactionID;
	public NBT nbt;
	
	@Override
	public int getDefaultID() {
		return OUT_TAG_QUERY_RESPONSE;
	}

}
