package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInNameItem extends Packet {
	
	public String getItemName();

	@Override
	default int getDefaultID() {
		return 0x20;
	}
	
}
