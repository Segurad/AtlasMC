package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_STATISTICS)
public interface PacketOutStatistics extends PacketPlay, PacketOutbound {
	
	public int[] getStatistics();

	@Override
	default int getDefaultID() {
		return OUT_STATISTICS;
	}
	
}
