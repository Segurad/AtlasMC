package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInQueryEntityNBT extends Packet {

	public int getTransactionID();
	public int getEntityID();
	
}