package de.atlasmc.io.protocol.play;

import de.atlasmc.Difficulty;
import de.atlasmc.io.Packet;

public interface PacketOutServerDifficulty extends Packet {
	
	public Difficulty getDifficulty();
	public boolean isLocked();
	
	@Override
	default int getDefaultID() {
		return 0x0D;
	}

}
