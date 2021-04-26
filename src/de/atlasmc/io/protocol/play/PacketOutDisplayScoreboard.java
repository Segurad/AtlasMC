package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;
import de.atlasmc.scoreboard.Position;

public interface PacketOutDisplayScoreboard extends Packet {
	
	public Position getPosition();
	public String getName();
	
	@Override
	public default int getDefaultID() {
		return 0x43;
	}

}
