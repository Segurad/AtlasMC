package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

/**
 * This packet is send whenever a player crosses a chunk border
 */
@DefaultPacketID(PacketPlay.OUT_SET_CENTER_CHUNK)
public class PacketOutSetCenterChunk extends AbstractPacket implements PacketPlayOut {
	
	private int chunkX, chunkZ;
	
	public int getChunkX() {
		return chunkX;
	}
	
	public int getChunkZ() {
		return chunkZ;
	}
	
	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}
	
	public void setChunkZ(int chunkZ) {
		this.chunkZ = chunkZ;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_CENTER_CHUNK;
	}

}
