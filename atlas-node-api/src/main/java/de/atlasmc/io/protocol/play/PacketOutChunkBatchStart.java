package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CHUNK_BATCH_START)
public class PacketOutChunkBatchStart extends AbstractPacket implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_CHUNK_BATCH_START;
	}
	
}
