package de.atlasmc.io.protocol.play;

import de.atlasmc.block.BlockFace;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_ACTION)
public class PacketInPlayerAction extends AbstractPacket implements PacketPlayIn {
	
	private int status;
	private long position;
	private BlockFace face;
	private int sequence;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public long getPosition() {
		return position;
	}
	
	public BlockFace getFace() {
		return face;
	}
	
	public void setFace(BlockFace face) {
		this.face = face;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_ACTION;
	}
	
}
