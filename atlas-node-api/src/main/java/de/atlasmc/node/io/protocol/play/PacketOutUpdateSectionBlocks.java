package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_SECTION_BLOCKS, definition = "section_blocks_update")
public class PacketOutUpdateSectionBlocks extends AbstractPacket implements PacketPlayOut {
	
	public long section;
	public long[] blocks;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_SECTION_BLOCKS;
	}

}
