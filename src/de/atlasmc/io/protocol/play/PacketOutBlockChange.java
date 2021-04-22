package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutBlockChange extends Packet {
	
	public long getPosition();
	public int getBlockStateID();
	
	@Override
	default int getDefaultID() {
		return 0x0B;
	}

}
