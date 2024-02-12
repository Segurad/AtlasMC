package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CHANGE_DIFFICULTY)
public class PacketInChangeDifficulty extends AbstractPacket implements PacketPlayIn {
	
	private int difficulty;
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CHANGE_DIFFICULTY;
	}

}
