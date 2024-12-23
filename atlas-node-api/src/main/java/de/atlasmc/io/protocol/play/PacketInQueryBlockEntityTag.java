package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_QUERY_BLOCK_ENTITY_TAG, definition = "block_entity_tag_query")
public class PacketInQueryBlockEntityTag extends AbstractPacket implements PacketPlayIn {

	private int transactionID;
	private long position;
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	@Override
	public int getDefaultID() {
		return IN_QUERY_BLOCK_ENTITY_TAG;
	}
	
}
