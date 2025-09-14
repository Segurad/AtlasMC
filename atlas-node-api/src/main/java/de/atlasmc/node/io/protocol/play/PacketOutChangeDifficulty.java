package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.Difficulty;

@DefaultPacketID(packetID = PacketPlay.OUT_CHANGE_DIFFICULTY, definition = "change_difficulty")
public class PacketOutChangeDifficulty extends AbstractPacket implements PacketPlayOut {
	
	public Difficulty difficulty;
	public boolean locked;
	
	@Override
	public int getDefaultID() {
		return OUT_CHANGE_DIFFICULTY;
	}

}
