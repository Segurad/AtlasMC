package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_LOCK_DIFFICULTY)
public class PacketInLockDifficulty extends AbstractPacket implements PacketPlayIn {
	
	private boolean locked;
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public int getDefaultID() {
		return IN_LOCK_DIFFICULTY;
	}

}
