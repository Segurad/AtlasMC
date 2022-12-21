package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ACKNOWLEDGE_PLAYER_DIGGIN)
public class PacketOutAcknowledgePlayerDigging extends AbstractPacket implements PacketPlayOut {
	
	private long position;
	private int blockState, status;
	private boolean successful;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public int getBlockState() {
		return blockState;
	}
	
	public void setBlockState(int blockState) {
		this.blockState = blockState;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	public boolean isSuccessful() {
		return successful;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ACKNOWLEDGE_PLAYER_DIGGIN;
	}

}
