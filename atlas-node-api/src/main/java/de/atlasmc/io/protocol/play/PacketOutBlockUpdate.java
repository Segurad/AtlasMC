package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_BLOCK_UPDATE)
public class PacketOutBlockUpdate extends AbstractPacket implements PacketPlayOut {
	
	private long position;
	private int blockStateID;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public int getBlockStateID() {
		return blockStateID;
	}
	
	public void setBlockStateID(int blockStateID) {
		this.blockStateID = blockStateID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_BLOCK_UPDATE;
	}

}
