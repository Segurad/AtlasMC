package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_STATISTICS)
public class PacketOutStatistics extends AbstractPacket implements PacketPlayOut {
	
	private int[] statistics;
	
	public int[] getStatistics() {
		return statistics;
	}
	
	public void setStatistics(int[] statistics) {
		this.statistics = statistics;
	}

	@Override
	public int getDefaultID() {
		return OUT_STATISTICS;
	}
	
}
