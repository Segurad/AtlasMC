package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityMovement extends Packet {
	
	public int getEntityID();
	
	@Override
	default int getDefaultID() {
		return 0x2A;
	}

}
