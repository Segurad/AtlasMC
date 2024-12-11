package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.scoreboard.DisplaySlot;

@DefaultPacketID(packetID = PacketPlay.OUT_DISPLAY_OBJECTIVE, definition = "set_display_objective")
public class PacketOutDisplayObjective extends AbstractPacket implements PacketPlayOut {
	
	public DisplaySlot position;
	public String objective;
	
	@Override
	public int getDefaultID() {
		return OUT_DISPLAY_OBJECTIVE;
	}

}
