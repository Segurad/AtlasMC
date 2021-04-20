package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInLockDifficulty extends Packet {
	
	public boolean isLocked();

}
