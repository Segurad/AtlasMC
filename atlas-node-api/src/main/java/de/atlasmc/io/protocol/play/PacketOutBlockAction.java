package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_BLOCK_ACTION)
public class PacketOutBlockAction extends AbstractPacket implements PacketPlayOut {
	
	private int actionID;
	private int actionParam;
	private int blockType;
	private long position;
	
	public int getActionID() {
		return actionID;
	}
	
	public void setActionID(int actionID) {
		this.actionID = actionID;
	}
	
	public int getActionParam() {
		return actionParam;
	}
	
	public void setActionParam(int actionParam) {
		this.actionParam = actionParam;
	}
	
	public int getBlockType() {
		return blockType;
	}
	
	public void setBlockType(int blockType) {
		this.blockType = blockType;
	}
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_BLOCK_ACTION;
	}

}
