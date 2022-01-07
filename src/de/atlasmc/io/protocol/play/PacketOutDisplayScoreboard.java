package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.scoreboard.DisplaySlot;

@DefaultPacketID(PacketPlay.OUT_DISPLAY_SCOREBOARD)
public interface PacketOutDisplayScoreboard extends PacketPlay, PacketOutbound {
	
	public DisplaySlot getPosition();
	
	public String getObjective();
	
	public void setPosition(DisplaySlot slot);
	
	public void setObjective(String name);
	
	@Override
	public default int getDefaultID() {
		return OUT_DISPLAY_SCOREBOARD;
	}

}
