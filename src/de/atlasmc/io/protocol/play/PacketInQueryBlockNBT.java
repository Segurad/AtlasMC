package de.atlasmc.io.protocol.play;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInQueryBlockNBT extends Packet {

	public int getTransactionID();
	public SimpleLocation getLocation();
}
