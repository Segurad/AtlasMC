package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutUnloadChunk extends Packet {
	
	public int getChunkX();
	public int getChunkY();

}
