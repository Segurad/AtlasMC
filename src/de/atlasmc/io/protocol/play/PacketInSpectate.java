package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSpectate extends Packet {
	
	public String getUUID();
	
	@Override
	default int getDefaultID() {
		return 0x2D;
	}

}
