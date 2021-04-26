package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutSpawnPosition extends Packet {
	
	public long getPosition();
	
	@Override
	public default int getDefaultID() {
		return 0x42;
	}

}
