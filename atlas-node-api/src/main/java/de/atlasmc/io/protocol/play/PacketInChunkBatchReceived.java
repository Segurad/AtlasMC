package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CHUNK_BATCH_RECEIVED, definition = "chunk_batch_received")
public class PacketInChunkBatchReceived extends AbstractPacket implements PacketPlayIn {

	public float chunksPerTick;
	
	@Override
	public int getDefaultID() {
		return IN_CHUNK_BATCH_RECEIVED;
	}
	
}
