package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_SET_DIFFICULTY)
public interface PacketInSetDifficulty extends PacketPlay, PacketInbound {
	
	public int getDifficulty();
	
	@Override
	default int getDefaultID() {
		return IN_SET_DIFFICULTY;
	}

}
