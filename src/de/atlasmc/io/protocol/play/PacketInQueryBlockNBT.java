package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_QUERY_BLOCK_NBT)
public interface PacketInQueryBlockNBT extends PacketPlay, PacketInbound {

	public int getTransactionID();
	public long getLocation();
	
	@Override
	default int getDefaultID() {
		return IN_QUERY_BLOCK_NBT;
	}
	
}
