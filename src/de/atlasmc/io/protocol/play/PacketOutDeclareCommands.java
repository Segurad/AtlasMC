package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutDeclareCommands extends Packet {
	
	@Override
	default int getDefaultID() {
		return 0x10;
	}

}
