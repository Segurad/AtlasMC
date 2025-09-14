package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_CHUNK_BATCH_START, definition = "chunk_batch_start")
public class PacketOutChunkBatchStart extends AbstractPacket implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_CHUNK_BATCH_START;
	}
	
}
