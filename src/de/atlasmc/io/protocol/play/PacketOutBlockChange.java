package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_BLOCK_CHANGE)
public interface PacketOutBlockChange extends PacketPlay, PacketOutbound {
	
	public long getPosition();
	public int getBlockStateID();
	
	@Override
	default int getDefaultID() {
		return OUT_BLOCK_CHANGE;
	}

}
