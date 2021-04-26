package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutHeldItemChange extends Packet {
	
	public int getSlot();
	
	@Override
	public default int getDefaultID() {
		return 0x3F;
	}

}
