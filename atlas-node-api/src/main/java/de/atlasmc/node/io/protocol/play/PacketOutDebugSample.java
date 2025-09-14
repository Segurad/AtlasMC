package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_DEBUG_SAMPLE, definition = "debug_sample")
public class PacketOutDebugSample extends AbstractPacket implements PacketPlayOut {

	public long[] samples;
	public int type;
	
	@Override
	public int getDefaultID() {
		return OUT_DEBUG_SAMPLE;
	}
	
}
