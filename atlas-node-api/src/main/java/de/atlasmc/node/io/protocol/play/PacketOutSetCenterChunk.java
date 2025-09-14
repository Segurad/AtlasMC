package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

/**
 * This packet is send whenever a player crosses a chunk border
 */
@DefaultPacketID(packetID = PacketPlay.OUT_SET_CENTER_CHUNK, definition = "set_chunk_cache_center")
public class PacketOutSetCenterChunk extends AbstractPacket implements PacketPlayOut {
	
	public int chunkX;
	public int chunkZ;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_CENTER_CHUNK;
	}

}
