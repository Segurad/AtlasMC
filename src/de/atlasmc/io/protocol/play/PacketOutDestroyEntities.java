package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutDestroyEntities extends Packet {
	
	public int[] getEntityIDs();

	@Override
	public default int getDefaultID() {
		return 0x36;
	}
	
}
