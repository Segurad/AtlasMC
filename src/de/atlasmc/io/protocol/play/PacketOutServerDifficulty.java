package de.atlasmc.io.protocol.play;

import de.atlasmc.Difficulty;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SERVER_DIFFICULTY)
public class PacketOutServerDifficulty extends AbstractPacket implements PacketPlayOut {
	
	private Difficulty difficulty;
	private boolean locked;
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SERVER_DIFFICULTY;
	}

}
