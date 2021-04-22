package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutStatistics extends Packet {
	
	public int[] getStatistics();

	@Override
	default int getDefaultID() {
		return 0x06;
	}
	
}
