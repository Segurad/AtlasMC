package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_CHUNK_DATA)
public interface PacketOutChunkData extends PacketPlay, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_CHUNK_DATA;
	}

}
