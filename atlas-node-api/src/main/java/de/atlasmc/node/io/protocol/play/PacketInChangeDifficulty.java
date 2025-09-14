package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CHANGE_DIFFICULTY, definition = "change_difficulty")
public class PacketInChangeDifficulty extends AbstractPacket implements PacketPlayIn {
	
	public int difficulty;
	
	@Override
	public int getDefaultID() {
		return IN_CHANGE_DIFFICULTY;
	}

}
