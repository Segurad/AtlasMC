package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutOpenSignEditor extends Packet {
	
	public long getPosition();
	
	@Override
	default int getDefaultID() {
		return 0x2E;
	}

}
