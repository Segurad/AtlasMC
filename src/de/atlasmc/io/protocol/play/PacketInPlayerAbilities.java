package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInPlayerAbilities extends Packet {
	
	public byte getFlags();
	
	@Override
	default int getDefaultID() {
		return 0x1A;
	}

}
