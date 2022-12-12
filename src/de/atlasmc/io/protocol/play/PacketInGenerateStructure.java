package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_GENERATE_STRUCTURE)
public class PacketInGenerateStructure extends AbstractPacket implements PacketPlayIn {
	
	private long position;
	private int levels;
	private boolean keepJigsaws;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public int getLevels() {
		return levels;
	}
	
	public void setLevels(int levels) {
		this.levels = levels;
	}
	
	public boolean getKeepJigsaws() {
		return keepJigsaws;
	}
	
	public void setKeepJigsaws(boolean keepJigsaws) {
		this.keepJigsaws = keepJigsaws;
	}
	
	@Override
	public int getDefaultID() {
		return IN_GENERATE_STRUCTURE;
	}

}
