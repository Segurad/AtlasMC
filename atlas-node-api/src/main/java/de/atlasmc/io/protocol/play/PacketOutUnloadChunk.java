package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UNLOAD_CHUNK, definition = "forget_level_chunk")
public class PacketOutUnloadChunk extends AbstractPacket implements PacketPlayOut {
	
	public int chunkX;
	public int chunkZ;
	
	@Override
	public int getDefaultID() {
		return OUT_UNLOAD_CHUNK;
	}

}
