package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutCollectItem extends Packet {
	
	public int getCollectedID();
	public int getCollectorID();
	public int getPickupCount();
	
	@Override
	public default int getDefaultID() {
		return 0x55;
	}

}
