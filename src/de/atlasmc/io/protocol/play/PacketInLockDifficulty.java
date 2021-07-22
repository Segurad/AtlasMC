package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_LOCK_DIFFICULTY)
public interface PacketInLockDifficulty extends PacketPlay, PacketInbound {
	
	public boolean isLocked();
	
	@Override
	default int getDefaultID() {
		return IN_LOCK_DIFFICULTY;
	}

}
