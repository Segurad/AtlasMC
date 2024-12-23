package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_DEBUG_SAMPLE_SUBSCRIPTION, definition = "debug_sample_subscription")
public class PacketInDebugSampleSubscription extends AbstractPacket implements PacketPlayIn {
	
	public int type;
	
	@Override
	public int getDefaultID() {
		return IN_DEBUG_SAMPLE_SUBSCRIPTION;
	}

}
