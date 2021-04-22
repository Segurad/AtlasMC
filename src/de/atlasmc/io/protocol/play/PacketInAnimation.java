package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInAnimation extends Packet {
	
	public int getHand();
	
	@Override
	public default int getDefaultID() {
		return 0x2C;
	}

}
