package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSetDifficulty extends Packet {
	
	public int getDifficulty();

}
