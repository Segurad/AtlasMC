package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_MULTI_BLOCK_CHANGE)
public interface PacketOutMultiBlockChange extends PacketPlay, PacketOutbound {
	
	public long getSection();
	public long[] getBlocks();
	
	@Override
	default int getDefaultID() {
		return OUT_MULTI_BLOCK_CHANGE;
	}

}
