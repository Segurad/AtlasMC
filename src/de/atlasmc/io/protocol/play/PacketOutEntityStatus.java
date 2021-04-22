package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityStatus extends Packet {
	
	public int getEntityID();
	public int getStatus();
	
	@Override
	default int getDefaultID() {
		return 0x1A;
	}

}
