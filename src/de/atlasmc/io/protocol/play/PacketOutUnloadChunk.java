package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UNLOAD_CHUNK)
public class PacketOutUnloadChunk extends AbstractPacket implements PacketPlayOut {
	
	private int chunkX, chunkZ;
	
	public int getChunkX() {
		return chunkX;
	}
	
	public void setChunkZ(int chunkZ) {
		this.chunkZ = chunkZ;
	}
	
	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}
	
	public int getChunkZ() {
		return chunkZ;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_UNLOAD_CHUNK;
	}

}
