package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_AWARD_STATISTICS, definition = "award_stats")
public class PacketOutAwardStatistics extends AbstractPacket implements PacketPlayOut {
	
	public int[] statistics;

	@Override
	public int getDefaultID() {
		return OUT_AWARD_STATISTICS;
	}
	
}
