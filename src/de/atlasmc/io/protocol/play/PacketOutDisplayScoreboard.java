package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.scoreboard.Position;

@DefaultPacketID(PacketPlay.OUT_DISPLAY_SCOREBOARD)
public interface PacketOutDisplayScoreboard extends PacketPlay, PacketOutbound {
	
	public Position getPosition();
	public String getName();
	
	@Override
	public default int getDefaultID() {
		return OUT_DISPLAY_SCOREBOARD;
	}

}
