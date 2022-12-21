package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.scoreboard.DisplaySlot;

@DefaultPacketID(PacketPlay.OUT_DISPLAY_SCOREBOARD)
public class PacketOutDisplayScoreboard extends AbstractPacket implements PacketPlayOut {
	
	private DisplaySlot position;
	private String objective;
	
	public DisplaySlot getPosition() {
		return position;
	}
	
	public void setPosition(DisplaySlot position) {
		this.position = position;
	}
	
	public String getObjective() {
		return objective;
	}
	
	public void setObjective(String objective) {
		this.objective = objective;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_DISPLAY_SCOREBOARD;
	}

}
