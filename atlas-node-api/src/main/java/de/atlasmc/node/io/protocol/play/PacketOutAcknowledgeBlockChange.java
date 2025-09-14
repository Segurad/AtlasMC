package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_ACKNOWLEDGE_BLOCK_CHANGE, definition = "block_changed_ack")
public class PacketOutAcknowledgeBlockChange extends AbstractPacket implements PacketPlayOut {
	
	public int sequence;
	
	@Override
	public int getDefaultID() {
		return OUT_ACKNOWLEDGE_BLOCK_CHANGE;
	}

}
