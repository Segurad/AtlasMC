package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_LOCK_DIFFICULTY, definition = "lock_difficulty")
public class PacketInLockDifficulty extends AbstractPacket implements PacketPlayIn {
	
	public boolean locked;
	
	@Override
	public int getDefaultID() {
		return IN_LOCK_DIFFICULTY;
	}

}
