package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UNLOAD_CHUNK)
public interface PacketOutUnloadChunk extends PacketPlay, PacketOutbound {
	
	public int getChunkX();
	public int getChunkZ();
	
	@Override
	default int getDefaultID() {
		return OUT_UNLOAD_CHUNK;
	}

}
