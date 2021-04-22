package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInResourcePackStatus extends Packet {
	
	public int getResult();
	
	@Override
	default int getDefaultID() {
		return 0x21;
	}

}
