package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInUseItem extends Packet {
	
	public int getHand();

	@Override
	default int getDefaultID() {
		return 0x2F;
	}
	
}
