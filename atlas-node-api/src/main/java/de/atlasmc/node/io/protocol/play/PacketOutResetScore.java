package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_RESET_SCORE, definition = "reset_score")
public class PacketOutResetScore extends AbstractPacket implements PacketPlayOut {
	
	public String entityName;
	public String objectiveName;
	
	@Override
	public int getDefaultID() {
		return OUT_RESET_SCORE;
	}

}
