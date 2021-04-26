package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutTimeUpdate extends Packet {
	
	public long getWorldAge();
	public long getTimeOfDay();
	
	@Override
	public default int getDefaultID() {
		return 0x4E;
	}

}
