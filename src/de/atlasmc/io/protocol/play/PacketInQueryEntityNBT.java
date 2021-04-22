package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInQueryEntityNBT extends Packet {

	public int getTransactionID();
	public int getEntityID();
	
	@Override
	default int getDefaultID() {
		return 0x0D;
	}
	
}
