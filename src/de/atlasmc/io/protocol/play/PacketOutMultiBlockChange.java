package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutMultiBlockChange extends Packet {
	
	public long getSection();
	public long[] getBlocks();
	
	@Override
	default int getDefaultID() {
		return 0x3B;
	}

}
