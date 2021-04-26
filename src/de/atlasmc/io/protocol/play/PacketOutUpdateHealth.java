package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutUpdateHealth extends Packet {
	
	public float getHealth();
	public int getFood();
	public float getSaturation();
	
	@Override
	public default int getDefaultID() {
		return 0x49;
	}

}
