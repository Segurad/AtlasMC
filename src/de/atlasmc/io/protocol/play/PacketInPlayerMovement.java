package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerMovement extends Packet {
	
	public boolean isOnGround();
	
	@Override
	default int getDefaultID() {
		return 0x15;
	}

}
