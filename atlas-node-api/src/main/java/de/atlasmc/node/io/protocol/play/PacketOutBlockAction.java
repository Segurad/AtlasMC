package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_BLOCK_ACTION, definition = "block_event")
public class PacketOutBlockAction extends AbstractPacket implements PacketPlayOut {
	
	public long position;
	public int actionID;
	public int actionParam;
	public int blockType;
	
	@Override
	public int getDefaultID() {
		return OUT_BLOCK_ACTION;
	}

}
