package de.atlasmc.io.pack;

import de.atlasmc.SimpleLocation;
import de.atlasmc.io.Packet;

public interface PacketInQueryBlockNBT extends Packet {

	public int getTransactionID();
	public SimpleLocation getLocation();
}
