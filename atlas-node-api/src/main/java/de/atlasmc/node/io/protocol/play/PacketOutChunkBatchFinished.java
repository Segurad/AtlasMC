package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_CHUNK_BATCH_FINISHED, definition = "chunk_batch_finished")
public class PacketOutChunkBatchFinished extends AbstractPacket implements PacketPlayOut {
	
	public int chunks;
	
	@Override
	public int getDefaultID() {
		return OUT_CHUNK_BATCH_FINISHED;
	}

}
