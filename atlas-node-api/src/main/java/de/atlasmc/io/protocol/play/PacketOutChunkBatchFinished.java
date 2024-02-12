package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CHUNK_BATCH_FINISHED)
public class PacketOutChunkBatchFinished extends AbstractPacket implements PacketPlayOut {
	
	public int chunks;
	
	@Override
	public int getDefaultID() {
		return OUT_CHUNK_BATCH_FINISHED;
	}

}
