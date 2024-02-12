package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ACKNOWLEDGE_BLOCK_CHANGE)
public class PacketOutAcknowledgeBlockChange extends AbstractPacket implements PacketPlayOut {
	
	public int sequence;
	
	@Override
	public int getDefaultID() {
		return OUT_ACKNOWLEDGE_BLOCK_CHANGE;
	}

}
