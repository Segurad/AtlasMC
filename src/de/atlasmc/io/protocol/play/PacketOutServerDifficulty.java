package de.atlasmc.io.protocol.play;

import de.atlasmc.Difficulty;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SERVER_DIFFICULTY)
public interface PacketOutServerDifficulty extends PacketPlay, PacketOutbound {
	
	public Difficulty getDifficulty();
	public boolean isLocked();
	
	@Override
	default int getDefaultID() {
		return OUT_SERVER_DIFFICULTY;
	}

}
