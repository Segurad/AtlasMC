package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_MULTI_BLOCK_CHANGE)
public class PacketOutMultiBlockChange extends AbstractPacket implements PacketPlayOut {
	
	private long section;
	private long[] blocks;
	
	public long getSection() {
		return section;
	}
	
	public void setSection(long section) {
		this.section = section;
	}
	
	public long[] getBlocks() {
		return blocks;
	}
	
	public void setBlocks(long[] blocks) {
		this.blocks = blocks;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_MULTI_BLOCK_CHANGE;
	}

}
